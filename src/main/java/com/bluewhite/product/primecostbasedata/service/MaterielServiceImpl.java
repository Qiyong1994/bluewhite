package com.bluewhite.product.primecostbasedata.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.product.primecostbasedata.dao.BaseOneDao;
import com.bluewhite.product.primecostbasedata.dao.BaseThreeDao;
import com.bluewhite.product.primecostbasedata.dao.MaterielDao;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.BaseThree;
import com.bluewhite.product.primecostbasedata.entity.Materiel;

@Service
public class MaterielServiceImpl extends BaseServiceImpl<Materiel, Long> implements MaterielService{
	
	@Autowired
	private MaterielDao dao;
	
	@Autowired
	private BaseOneDao baseOneDao;

	@Autowired
	private BaseThreeDao baseThreeDao;
	
	@Override
	public List<Materiel> findPages(Materiel materiel) {
			  List<Materiel> pages = dao.findAll((root,query,cb) -> {
		        	List<Predicate> predicate = new ArrayList<>();
		        	//按id过滤
		        	if (materiel.getId() != null) {
						predicate.add(cb.equal(root.get("id").as(Long.class),materiel.getId()));
					}
		        	//按编号过滤
		        	if (materiel.getNumber() != null) {
						predicate.add(cb.like(root.get("type").as(String.class),"%"+materiel.getNumber()+"%"));
					}
		        	//按产品名称过滤
		        	if (materiel.getName() != null) {
						predicate.add(cb.like(root.get("name").as(String.class),"%"+StringUtil.specialStrKeyword(materiel.getName())+"%"));
					}
					Predicate[] pre = new Predicate[predicate.size()];
					query.where(predicate.toArray(pre));
		        	return null;
		        });
		        return pages;
	}

	@Override
	public List<BaseOne> findPagesBaseOne(BaseOne baseOne) {
		  List<BaseOne> pages = baseOneDao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (baseOne.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),baseOne.getId()));
				}
	        	//按类型过滤
	        	if (baseOne.getType()!= null) {
					predicate.add(cb.equal(root.get("type").as(String.class),baseOne.getType()));
				}
	        	//按产品名称过滤
	        	if (baseOne.getName() != null) {
					predicate.add(cb.like(root.get("name").as(String.class),"%"+StringUtil.specialStrKeyword(baseOne.getName())+"%"));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        });
	        return pages;
	}

	@Override
	public Double getBaseThreeOne(Long typeId, Double number) {
		List<BaseThree> baseThreeList = baseThreeDao.findAll();
		BaseThree BaseThree = null;
		for(BaseThree bt : baseThreeList){
			if(bt.getOrdinaryLaser()==number){
				BaseThree = bt;
				break;
			}
		}
		
		double returnNumber = 0 ;
		switch (typeId.intValue()) {
		case 71://普通激光切割
			returnNumber = BaseThree.getTextualOrdinaryLight();
			break;
		case 72://绣花激光切割
			returnNumber = BaseThree.getTextualPositionLight();
			break;
		case 73://手工电烫
			returnNumber = BaseThree.getTextualPerm();
			break;
		case 74://设备电烫
			break;
		case 75://冲床
			returnNumber = BaseThree.getTextualPuncher();
			break;
		case 76://电推
			returnNumber = BaseThree.getTextualClippers();
			break;
		case 77://手工剪刀
			returnNumber = BaseThree.getTextualScissors();
			break;
		case 78://绣花领取
			break;
		default:
			break;
		}
		return returnNumber;
	}
	
	
	
	

}
