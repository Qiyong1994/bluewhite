package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.OrderDao;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.OrderChild;
import com.bluewhite.onlineretailers.inventory.dao.ProcurementDao;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

	@Autowired
	private OrderDao dao;
	@Autowired
	private ProcurementDao procurementDao;
	@Autowired
	private BaseDataDao baseDataDao;
	
	@Override
	public PageResult<Order> findPages(Order param, PageParameter page) {
		Page<Order> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
			}
			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class), "%" + param.getCustomerName() + "%"));
			}
			// 按批次
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按产品name过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.equal(root.get("product").get("name").as(String.class), "%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 按产品编号过滤
			if (!StringUtils.isEmpty(param.getProductNumber())) {
				predicate.add(cb.equal(root.get("productNumber").as(String.class), "%" + param.getProductNumber() + "%"));
			}
			// 按下单日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("orderDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Order> result = new PageResult<Order>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public int deleteOrder(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Order order =  dao.findOne(id);
					Procurement procurement = procurementDao.findByOrderId(id);
					if(procurement!=null){
						throw new ServiceException("批次号："+order.getBacthNumber()+"产品名："+order.getProduct().getName() +"的下单合同已进行生成，无法删除");
					}
					dao.delete(id); 
					count++;
				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public void addOrder(Order order) {
		Order oldOrder = dao.findByBacthNumber(order.getBacthNumber());
		if(oldOrder!=null){
			throw new ServiceException("系统已有"+order.getBacthNumber()+"批次号下单合同，请不要重复添加");
		}
		order.setPrepareEnough(0);
		order.setAudit(0);
		Set<OrderChild> orderChildSet = new HashSet<>();
		// 新增子单
		if (!StringUtils.isEmpty(order.getOrderChild())) { 
			JSONArray jsonArray = JSON.parseArray(order.getOrderChild());
			for (int i = 0; i < jsonArray.size(); i++) {
				OrderChild orderChild = new OrderChild();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				orderChild.setCustomerId(jsonObject.getLong("customerId"));
				orderChild.setUserId(jsonObject.getLong("userId"));
				orderChild.setChildNumber(jsonObject.getInteger("childNumber"));
				orderChild.setChildRemark(jsonObject.getString("childRemark"));
				orderChildSet.add(orderChild);
			}
		}
		order.setOrderChilds(orderChildSet);
		dao.save(order);
	}

	@Override
	public List<Order> findList(Order param) {
		List<Order> result = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按批次
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按下单日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("orderDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return result;
	}

	@Override
	public void updateOrder(Order order) {
		
		
	}

	@Override
	public String getOrderBacthNumber(Date time,Long typeId) {
		List<Order> orderList = dao.findByOrderDate(time);
		String numberDef = null;
		List<Integer> numberList = new ArrayList<>();
		orderList.stream().forEach(o -> {
			String number = o.getBacthNumber().substring(o.getBacthNumber().length() - 2, o.getBacthNumber().length() - 1);
			numberList.add(Integer.parseInt(number));
		});
		// 正序
		numberList.sort(Comparator.naturalOrder());
		for (int i = 0; i < numberList.size(); i++) {
			if (numberList.get(i) != (i + 1)) {
				numberDef = String.valueOf((i + 1));
				break;
			}
		}
		Calendar now = Calendar.getInstance();
		now.setTime(time);
		String year = String.valueOf(now.get(Calendar.YEAR));
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		//获取下单类型
		String orderNumberSuffix = "";
		BaseData numberType = baseDataDao.findOne(typeId);
		if(numberType!=null){
			String[] numberString = numberType.getName().split("-");
			if(numberString.length>0){
				orderNumberSuffix = numberString[1];
			}
		}
		String orderNumber = year + "-" + month + "-" + day + "-"+ (numberDef != null ? numberDef : (orderList.size() + 1)) + orderNumberSuffix;
		return orderNumber;
	}

	@Override
	public int auditOrder(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Order order =  dao.findOne(id);
					if(order.getAudit()==1){
						throw new ServiceException("编号为"+order.getBacthNumber()+"的下单合同已审核请勿多次审核");
					}
					order.setAudit(1);
					dao.save(order);
					count++;
				}
			}
		}
		return count;
	}


}
