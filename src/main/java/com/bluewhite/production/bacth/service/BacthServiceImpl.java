package com.bluewhite.production.bacth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.service.ProductServiceImpl;
import com.bluewhite.production.bacth.dao.BacthDao;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.finance.dao.PayBDao;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.task.entity.Task;
@Service
public class BacthServiceImpl extends BaseServiceImpl<Bacth, Long> implements BacthService{

	@Autowired
	private BacthDao dao;
	
	@Autowired
	private PayBDao payBDao;
	
	
	@Override
	public PageResult<Bacth> findPages(Bacth param, PageParameter page) {
			  Page<Bacth> pages = dao.findAll((root,query,cb) -> {
		        	List<Predicate> predicate = new ArrayList<>();
		        	//按id过滤
		        	if (param.getId() != null) {
						predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
					}
		        	//按产品id
		        	if(param.getProductId()!=null){
		        		predicate.add(cb.equal(root.get("productId").as(Long.class),param.getId()));
		        	}
		        	//按产品名称
		        	if(!StringUtils.isEmpty(param.getName())){
		        		predicate.add(cb.like(root.get("product").get("name").as(String.class), "%"+param.getName()+"%"));
		        	}
		        	//按产品编号
		        	if(!StringUtils.isEmpty(param.getProductNumber())){
		        		predicate.add(cb.like(root.get("product").get("number").as(String.class), "%"+param.getProductNumber()+"%"));
		        	}
		        	//按批次
		        	if(!StringUtils.isEmpty(param.getBacthNumber())){
		        		predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%"+param.getBacthNumber()+"%"));
		        	}
		        	//按类型
		        	if(!StringUtils.isEmpty(param.getType())){
		        		predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
		        	}
		           	//按类型
		        	if(!StringUtils.isEmpty(param.getStatus())){
		        		predicate.add(cb.equal(root.get("status").as(Integer.class), param.getStatus()));
		        	}
		            //按时间过滤
					if (!StringUtils.isEmpty(param.getOrderTimeBegin()) &&  !StringUtils.isEmpty(param.getOrderTimeEnd()) ) {
						predicate.add(cb.between(root.get("allotTime").as(Date.class),
								param.getOrderTimeBegin(),
								param.getOrderTimeEnd()));
					}
			
		        	
					Predicate[] pre = new Predicate[predicate.size()];
					query.where(predicate.toArray(pre));
		        	return null;
		        }, page);
		        PageResult<Bacth> result = new PageResult<>(pages,page);
		        return result;
		    }

	@Override
	@Transactional
	public int deleteBacth(Long id) {
		Bacth bacth = dao.findOne(id);
		for(Task task : bacth.getTasks()){
			//查询出该任务的所有B工资
			List<PayB> payB = payBDao.findByTaskId(task.getId());
			//删除该任务的所有B工资
			payBDao.delete(payB);
		};
		dao.delete(id);
		return 1;
	}

	@Override
	public int statusBacth(String[] ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			if (ids.length>0) {
				for (int i = 0; i < ids.length; i++) {
					Long id = Long.parseLong(ids[i]);
					Bacth bacth = dao.findOne(id);
					bacth.setStatus(1);
					dao.save(bacth);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public int receiveBacth(String[] ids, String[] numbers) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			if (ids.length>0) {
				for (int i = 0; i < ids.length; i++) {
					Long id = Long.parseLong(ids[i]);
					Bacth bacth = new Bacth();
					Bacth oldBacth = dao.findOne(id);
					oldBacth.setId(null);
					BeanCopyUtils.copyNullProperties(oldBacth,bacth);
					bacth.setNumber(Integer.valueOf(numbers[i]));
					dao.save(bacth);
					count++;
				}
			}
		}
		return count;
	}
}
