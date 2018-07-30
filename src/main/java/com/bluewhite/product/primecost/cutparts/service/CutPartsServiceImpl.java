package com.bluewhite.product.primecost.cutparts.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.cutparts.dao.CutPartsDao;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.primecost.entity.PrimeCost;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.product.product.entity.Product;

@Service
public class CutPartsServiceImpl  extends BaseServiceImpl<CutParts, Long> implements CutPartsService{

	@Autowired
	private CutPartsDao dao;
	@Autowired
	private ProductDao productdao;

	
	@Override
	@Transactional
	public CutParts saveCutParts(CutParts cutParts) throws Exception {
		if(StringUtils.isEmpty(cutParts.getCutPartsNumber())){
			throw new ServiceException("使用片数不能为空");
		}
		if(StringUtils.isEmpty(cutParts.getOneMaterial())){
			throw new ServiceException("单片用料不能为空");
		}
		if(StringUtils.isEmpty(cutParts.getNumber())){
			throw new ServiceException("批量产品数量或模拟批量数不能为空");
		}
		
		cutParts.setAddMaterial(cutParts.getCutPartsNumber()*cutParts.getOneMaterial());
		//当批各单片用料
		if(cutParts.getComposite()==0){
			cutParts.setBatchMaterial(cutParts.getAddMaterial()*(cutParts.getManualLoss()+1)*cutParts.getCutPartsNumber()/cutParts.getCutPartsNumber()*cutParts.getNumber());
		}else{
			cutParts.setBatchMaterial(0.0);
		}
		//当批各单片价格
		if(cutParts.getComposite()==0){
			cutParts.setBatchMaterialPrice(cutParts.getComposite()*cutParts.getProductCost());
		}else{
			cutParts.setBatchMaterialPrice(0.0);
		}
		
		if(cutParts.getComposite()==1){
			cutParts.setComplexBatchMaterial(cutParts.getAddMaterial()*(cutParts.getCompositeManualLoss()+1)*cutParts.getNumber());
			cutParts.setBatchComplexMaterialPrice(cutParts.getComplexBatchMaterial()*cutParts.getProductCost());
			cutParts.setBatchComplexAddPrice(cutParts.getComplexBatchMaterial()*cutParts.getComplexProductCost());
		}
		
		dao.save(cutParts);
		//各单片比全套用料
		List<CutParts> cutPartsList = dao.findByProductId(cutParts.getProductId());
		double scaleMaterial = 0;
		if(cutPartsList.size()>0){
			scaleMaterial =  cutPartsList.stream().mapToDouble(CutParts::getAddMaterial).sum();
		}
		cutParts.setScaleMaterial(cutParts.getAddMaterial()/scaleMaterial);
		cutPartsList.add(cutParts);
		dao.save(cutParts);
		//同时更新产品成本价格表(面料价格(含复合物料和加工费)
		Product product =  productdao.findOne(cutParts.getProductId());
		double batchMaterialPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchMaterialPrice()!=null).mapToDouble(CutParts::getBatchMaterialPrice).sum();
		double batchComplexMaterialPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchComplexMaterialPrice()!=null).mapToDouble(CutParts::getBatchComplexMaterialPrice).sum();
		double batchComplexAddPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchComplexAddPrice()!=null).mapToDouble(CutParts::getBatchComplexAddPrice).sum();
		PrimeCost primeCost = product.getPrimeCost();
		if(primeCost.getId()==null){
			 primeCost = new PrimeCost();
		}
		primeCost.setNumber(cutParts.getNumber());
		primeCost.setCutPartsPrice((batchMaterialPrice+batchComplexMaterialPrice+batchComplexAddPrice)/cutParts.getNumber());
		product.setPrimeCost(primeCost);
		productdao.save(product);
		return cutParts;
	}


	@Override
	public PageResult<CutParts> findPages(CutParts param, PageParameter page) {
		 Page<CutParts> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	//按产品id过滤
	        	if (param.getProductId() != null) {
					predicate.add(cb.equal(root.get("productId").as(Long.class),param.getProductId()));
				}
	        	//按裁片名称过滤
	        	if (!StringUtils.isEmpty(param.getCutPartsName())) {
					predicate.add(cb.like(root.get("cutPartsName").as(String.class),"%"+param.getCutPartsName()+"%"));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        }, page);
		 PageResult<CutParts> result = new PageResult<CutParts>(pages,page);
		return result;
	}


	@Override
	@Transactional
	public void deleteCutParts(CutParts cutParts) {
		//删除
		dao.delete(cutParts.getId());
		//更新其他各单片比全套用料
		List<CutParts> cutPartsList = dao.findByProductId(cutParts.getProductId());
		double scaleMaterial = 0;
		if(cutPartsList.size()>0){
			scaleMaterial =  cutPartsList.stream().mapToDouble(CutParts::getAddMaterial).sum();
		}
		for(CutParts cp : cutPartsList){
			cp.setScaleMaterial(cp.getAddMaterial()/scaleMaterial);
		}
		///同时更新产品成本价格表(面料价格(含复合物料和加工费)
		Product product =  productdao.findOne(cutParts.getProductId());
		double batchMaterialPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchMaterialPrice()!=null).mapToDouble(CutParts::getBatchMaterialPrice).sum();
		double batchComplexMaterialPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchComplexMaterialPrice()!=null).mapToDouble(CutParts::getBatchComplexMaterialPrice).sum();
		double batchComplexAddPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchComplexAddPrice()!=null).mapToDouble(CutParts::getBatchComplexAddPrice).sum();
		product.getPrimeCost().setCutPartsPrice(batchMaterialPrice+batchComplexMaterialPrice+batchComplexAddPrice);
		productdao.save(product);
		dao.save(cutPartsList);
	}

}
