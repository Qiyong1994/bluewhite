package com.bluewhite.personnel.roomboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.dao.AdvertisementDao;
import com.bluewhite.personnel.roomboard.entity.Advertisement;

@Service
public class AdvertisementServiceImpl extends BaseServiceImpl<Advertisement, Long> implements AdvertisementService {
	@Autowired
	private AdvertisementDao dao;
	@Autowired
	private BaseDataDao baseDataDao;

	/*
	 * 分页查询
	 */
	@Override
	public PageResult<Advertisement> findPage(Advertisement advertisement, PageParameter page) {
		Page<Advertisement> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按平台
			if (advertisement.getPlatformId() != null) {
				predicate.add(cb.equal(root.get("platformId").as(Long.class), advertisement.getPlatformId()));
			}
			// 按类型
			if (advertisement.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), advertisement.getType()));
			}
			// 按培训类型
			if (advertisement.getMold() != null) {
				predicate.add(cb.equal(root.get("mold").as(Integer.class), advertisement.getMold()));
			}
			if (advertisement.getRecruitId() != null) {
				predicate.add(cb.equal(root.get("recruitId").as(Long.class), advertisement.getRecruitId()));
			}
			// 应聘人
			if(!StringUtils.isEmpty(advertisement.getApplyName())){
				predicate.add(cb.like(root.get("recruit").get("name").as(String.class), "%"+advertisement.getApplyName()+"%"));
			}
			// 招聘人
			if(!StringUtils.isEmpty(advertisement.getRecruitmentName())){
				predicate.add(cb.like(root.get("recruit").get("recruitName").as(String.class), "%"+advertisement.getRecruitmentName()+"%"));
			}
			// 按日期
			if (!StringUtils.isEmpty(advertisement.getOrderTimeBegin()) && !StringUtils.isEmpty(advertisement.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("startTime").as(Date.class), advertisement.getOrderTimeBegin(),
						advertisement.getOrderTimeEnd()));
			}
			// 按日期
			/*if (!StringUtils.isEmpty(advertisement.getOrderTimeBegin2()) && !StringUtils.isEmpty(advertisement.getOrderTimeEnd2())) {
				predicate.add(cb.between(root.get("endTime").as(Date.class), advertisement.getOrderTimeBegin2(),
						advertisement.getOrderTimeEnd2()));
			}*/
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Advertisement> result = new PageResult<>(pages, page);
		return result;
	}

	/*
	 * 新增修改
	 */
	@Override
	public Advertisement addAdvertisement(Advertisement advertisement) {

		return dao.save(advertisement);
	}

	/*
	 * 汇总单个人培训费用
	 */
	@Override
	public Advertisement findRecruitId(Long recruitId) {
		List<Advertisement> advertisements = dao.findByRecruitIdAndTypeAndMold(recruitId, 1,0);
		double price = 0;
		for (Advertisement advertisement : advertisements) {
			price += advertisement.getPrice();
		}
		Advertisement advertisement = new Advertisement();
		advertisement.setPrice(price);
		return advertisement;
	}

}
