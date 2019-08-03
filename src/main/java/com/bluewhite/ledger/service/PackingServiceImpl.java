package com.bluewhite.ledger.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.OrderDao;
import com.bluewhite.ledger.dao.PackingChildDao;
import com.bluewhite.ledger.dao.PackingDao;
import com.bluewhite.ledger.dao.PackingMaterialsDao;
import com.bluewhite.ledger.dao.SendGoodsDao;
import com.bluewhite.ledger.entity.Bill;
import com.bluewhite.ledger.entity.Mixed;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.Packing;
import com.bluewhite.ledger.entity.PackingChild;
import com.bluewhite.ledger.entity.PackingMaterials;
import com.bluewhite.ledger.entity.SendGoods;

@Service
public class PackingServiceImpl extends BaseServiceImpl<Packing, Long> implements PackingService {

	@Autowired
	private PackingDao dao;
	@Autowired
	private SendGoodsDao sendGoodsDao;
	@Autowired
	private PackingChildDao packingChildDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private PackingMaterialsDao packingMaterialsDao;
	@Autowired
	private MixedService mixedService;

	@Override
	public PageResult<Packing> findPages(Packing param, PageParameter page) {
		Page<Packing> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按客户id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customerId").as(Long.class), param.getCustomerId()));
			}
			// 按是否发货过滤
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}
			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class),
						"%" + param.getCustomerName() + "%"));
			}
			// 按商品名称过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				Join<Packing, PackingChild> join = root
						.join(root.getModel().getList("packingChilds", PackingChild.class), JoinType.LEFT);
				predicate.add(cb.like(join.get("product").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 按批次号过滤
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				Join<Packing, PackingChild> join = root
						.join(root.getModel().getList("packingChilds", PackingChild.class), JoinType.LEFT);
				predicate.add(cb.like(join.get("batchNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按发货贴包日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("packingDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Packing> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public String getPackingNumber(Date sendDate) {
		List<Packing> packingList = dao.findByPackingDate(sendDate);
		String numberDef = null;
		List<Integer> numberList = new ArrayList<>();
		packingList.stream().forEach(p -> {
			String number = p.getNumber().substring(p.getBacthNumber().length() - 2, p.getBacthNumber().length() - 1);
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
		now.setTime(sendDate);
		String year = String.valueOf(now.get(Calendar.YEAR));
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		String yearString = year.substring(year.length() - 2, year.length()); // 截取最后两位
		String packingNumber = yearString + "N" + month + "Y" + day + "R"
				+ (numberDef != null ? numberDef : (packingList.size() + 1)) + "D";
		return packingNumber;
	}

	@Override
	@Transactional
	public Packing addPacking(Packing packing) {
		packing.setFlag(0);
		// 新增子单
		if (!StringUtils.isEmpty(packing.getChildPacking())) {
			JSONArray jsonArray = JSON.parseArray(packing.getChildPacking());
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				PackingChild packingChild = new PackingChild();
				if (jsonObject.getLong("packingChildId") != null) {
					packingChild = packingChildDao.findOne(jsonObject.getLong("packingChildId"));
					if (packingChild.getFlag() == 1) {
						throw new ServiceException("贴报单已发货，无法修改");
					}
				}
				packingChild.setFlag(0);
				packingChild.setCount(jsonObject.getInteger("count"));
				// 改变待发货单的已发数量
				SendGoods sendGoods = sendGoodsDao.findOne(jsonObject.getLong("sendGoodsId"));
				sendGoods.setSendNumber(sendGoods.getNumber() + packingChild.getCount());
				sendGoodsDao.save(sendGoods);
				packingChild.setSendGoodsId(sendGoods.getId());
				packingChild.setCustomerId(packing.getCustomerId());
				packingChild.setBacthNumber(sendGoods.getBacthNumber());
				packingChild.setProductId(sendGoods.getProductId());
				packing.getPackingChilds().add(packingChild);
			}
		}
		// 新增贴包物
		if (!StringUtils.isEmpty(packing.getChildPacking())) {
			JSONArray jsonArrayMaterials = JSON.parseArray(packing.getPackingMaterialsJson());
			for (int i = 0; i < jsonArrayMaterials.size(); i++) {
				PackingMaterials packingMaterials = new PackingMaterials();
				JSONObject jsonObject = jsonArrayMaterials.getJSONObject(i);
				if (jsonObject.getLong("packingMaterialsId") != null) {
					packingMaterials = packingMaterialsDao.findOne(jsonObject.getLong("packingMaterialsId"));
				}
				packingMaterials.setPackagingId(jsonObject.getLong("packagingId"));
				packingMaterials.setPackagingCount(jsonObject.getInteger("packagingCount"));
				packing.getPackingMaterials().add(packingMaterials);
			}
		}
		dao.save(packing);
		return packing;
	}

	@Override
	public int sendPacking(String ids, Date time) {
		int count = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (!StringUtils.isEmpty(ids)) {
			String[] idStrings = ids.split(",");
			for (String id : idStrings) {
				Long idLong = Long.valueOf(id);
				Packing packing = dao.findOne(idLong);
				if (packing.getFlag() == 1) {
					throw new ServiceException("贴报单已发货，请勿重复发货");
				}
				// 已发货
				packing.setFlag(1);
				List<PackingChild> packingChildList = packing.getPackingChilds();
				for (PackingChild pc : packingChildList) {
					// 生成销售编号
					pc.setSaleNumber(Constants.XS + "-" + sdf.format(time == null ? packing.getPackingDate() : time)
							+ "-" + SalesUtils.get0LeftString((int) packingChildDao.count(), 8));
					pc.setSendDate(time == null ? packing.getPackingDate() : time);
					// 已发货
					pc.setFlag(1);
					// 未审核
					pc.setAudit(0);
					// 不转批次
					pc.setNewBacth(0);
					// 未拥有版权
					pc.setCopyright(0);
					// 未收货
					pc.setDelivery(1);
					// 业务员未确认数据
					pc.setDeliveryStatus(0);
					// 价格
					pc.setPrice(0.0);
					// 判定是否拥有版权
					if (pc.getProduct().getName().contains(Constants.LX)
							|| pc.getProduct().getName().contains(Constants.KT)
							|| pc.getProduct().getName().contains(Constants.MW)
							|| pc.getProduct().getName().contains(Constants.BM)
							|| pc.getProduct().getName().contains(Constants.LP)
							|| pc.getProduct().getName().contains(Constants.AB)
							|| pc.getProduct().getName().contains(Constants.ZMJ)
							|| pc.getProduct().getName().contains(Constants.XXYJN)) {
						pc.setCopyright(1);
					}
					// 判定是否更换客户发货，更换客户发货变成新批次，->Y
					Order order = orderDao.findByBacthNumber(pc.getBacthNumber());
					if (order.getCustomerId() != pc.getPacking().getCustomerId()) {
						pc.setBacthNumber(pc.getBacthNumber().substring(0, pc.getBacthNumber().length() - 1) + "Y");
						pc.setNewBacth(1);
					}
				}
				dao.save(packing);
				count++;
			}
		}
		return count;
	}

	@Override
	public PageResult<PackingChild> findPackingChildPage(PackingChild param, PageParameter page) {
		Page<PackingChild> pages = packingChildDao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class),
						"%" + param.getCustomerName() + "%"));
			}

			// 是否发货
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}

			// 是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
			}

			// 是否转批次
			if (param.getNewBacth() != null) {
				predicate.add(cb.equal(root.get("newBacth").as(Integer.class), param.getNewBacth()));
			}

			// 是否有版权
			if (param.getCopyright() != null) {
				predicate.add(cb.equal(root.get("copyright").as(Integer.class), param.getCopyright()));
			}

			// 是否业务员确认
			if (param.getDeliveryStatus() != null) {
				predicate.add(cb.equal(root.get("deliveryStatus").as(Integer.class), param.getDeliveryStatus()));
			}

			// 按产品name过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(
						cb.equal(root.get("product").get("name").as(Long.class), "%" + param.getProductName() + "%"));
			}
			// 按批次查找
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按发货日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("sendDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<PackingChild> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public List<PackingChild> getPackingChildPrice(PackingChild packingChild) {
		return packingChildDao.findByProductIdAndCustomerIdAndAudit(packingChild.getProductId(),
				packingChild.getCustomerId(), 1);
	}

	@Override
	@Transactional
	public int deletePacking(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Packing packing = dao.findOne(id);
					if (packing.getFlag() == 1) {
						throw new ServiceException("贴报单已发货，无法删除，请先核对发货单");
					}
					List<PackingChild> packingChildList = packing.getPackingChilds();
					packingChildList.stream().forEach(p -> {
						SendGoods sendGoods = p.getSendGoods();
						sendGoods.setSendNumber(sendGoods.getSendNumber() - p.getCount());
						sendGoodsDao.save(sendGoods);
					});
					dao.delete(packing);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public int deletePackingChild(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					PackingChild packingChild = packingChildDao.findOne(id);
					if (packingChild.getFlag() == 1) {
						throw new ServiceException("贴报单已发货，无法删除，请先核对发货单");
					}
					SendGoods sendGoods = packingChild.getSendGoods();
					sendGoods.setSendNumber(sendGoods.getSendNumber() - packingChild.getCount());
					sendGoodsDao.save(sendGoods);
					packingChildDao.delete(packingChild);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public PackingChild updateFinancePackingChild(PackingChild packingChild) {
		if (packingChild.getId() != null) {
			PackingChild oldPackingChild = packingChildDao.findOne(packingChild.getId());
			if (oldPackingChild.getAudit() == 1) {
				throw new ServiceException("销售单已审核，无法修改");
			}
			// 计算总价
			oldPackingChild.setSumPrice(NumUtils.mul(oldPackingChild.getCount(), packingChild.getPrice()));
			oldPackingChild.setPrice(packingChild.getPrice());
			oldPackingChild.setRemark(packingChild.getRemark());
			packingChildDao.save(oldPackingChild);
		}
		return packingChild;
	}

	@Override
	public PackingChild updateUserPackingChild(PackingChild packingChild) {
		if (packingChild.getId() != null) {
			PackingChild oldPackingChild = packingChildDao.findOne(packingChild.getId());
			if (oldPackingChild.getDeliveryStatus() == 1) {
				throw new ServiceException("销售单已确认，无法修改");
			}
			if (oldPackingChild.getAudit() == 1) {
				throw new ServiceException("销售单已审核，无法修改");
			}
			// 根据收货数量确认状态
			if (packingChild.getDeliveryNumber() != null) {
				if (oldPackingChild.getCount() == packingChild.getDeliveryNumber()) {
					packingChild.setDelivery(3);
				} else {
					packingChild.setDelivery(2);
				}
			}
			oldPackingChild.setDeliveryNumber(packingChild.getDeliveryNumber());
			oldPackingChild.setDeliveryCollectionDate(packingChild.getDeliveryCollectionDate());
			oldPackingChild.setDeliveryDate(packingChild.getDeliveryDate());
			oldPackingChild.setDisputeRemark(packingChild.getDisputeRemark());
			packingChildDao.save(oldPackingChild);
		}
		return packingChild;
	}

	@Override
	@Transactional
	public int deletePackingMaterials(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					packingMaterialsDao.delete(id);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public int auditPackingChild(String ids, Integer audit) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					PackingChild packingChild = packingChildDao.findOne(id);
					if (packingChild.getDeliveryStatus() == 0) {
						throw new ServiceException("业务员未确认到货数量，无法审核");
					}
					if (audit == 1 && packingChild.getAudit() == 1) {
						throw new ServiceException("发货单已审核，请勿多次审核");
					}
					if (audit == 0 && packingChild.getAudit() == 0) {
						throw new ServiceException("发货单未审核，无需取消审核");
					}
					// 审核成功后,生成账单
					if (audit == 1) {
						// 货款总值
						packingChild.setOffshorePay(NumUtils.mul(packingChild.getCount(), packingChild.getPrice()));
						// 客户认可货款
						packingChild
								.setAcceptPay(NumUtils.mul(packingChild.getDeliveryNumber(), packingChild.getPrice()));
						// 争议货款
						packingChild.setDisputePay(
								NumUtils.sub(packingChild.getOffshorePay(), packingChild.getAcceptPay()));
					}
					packingChild.setAudit(audit);
					packingChildDao.save(packingChild);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public List<Bill> collectBill(Bill bill) {
		List<PackingChild> pList = findPackingChildList(bill);
		List<Mixed> mixedList = mixedService.findList(bill);
		Map<Long, List<PackingChild>> mapPList = pList.stream()
				.collect(Collectors.groupingBy(PackingChild::getCustomerId, Collectors.toList()));
		Map<Long, List<Mixed>> mapMixedList = mixedList.stream()
				.collect(Collectors.groupingBy(Mixed::getCustomerId, Collectors.toList()));

		return null;
	}

	@Override
	public List<PackingChild> findPackingChildList(Bill param) {
		List<PackingChild> result = packingChildDao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按客户id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customerId").as(Long.class), param.getCustomerId()));
			}
			// 是否发货
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}
			// 是否审核
			predicate.add(cb.equal(root.get("audit").as(Boolean.class), param.isAudit()));

			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class),
						"%" + param.getCustomerName() + "%"));
			}
			// 按产品name过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(
						cb.equal(root.get("product").get("name").as(Long.class), "%" + param.getProductName() + "%"));
			}
			// 按发货日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("sendDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return result;
	}

	@Override
	public int auditUserPackingChild(String ids, Integer deliveryStatus) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					PackingChild packingChild = packingChildDao.findOne(id);
					if (packingChild.getAudit() == 0) {
						if (deliveryStatus == 1 && packingChild.getDeliveryStatus() == 1) {
							throw new ServiceException("销售单已被确认，请勿多次确认");
						}
						if (deliveryStatus == 0 && packingChild.getDeliveryStatus() == 0) {
							throw new ServiceException("销售单未确认，无需取消");
						}
					}
					if (packingChild.getAudit() == 1) {
						throw new ServiceException("发货单已审核，无法操作");
					}
					packingChild.setDeliveryStatus(deliveryStatus);
					packingChildDao.save(packingChild);
					count++;
				}
			}
		}
		return count;
	}
}
