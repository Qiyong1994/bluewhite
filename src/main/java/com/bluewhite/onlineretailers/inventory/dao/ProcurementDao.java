package com.bluewhite.onlineretailers.inventory.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;

public interface ProcurementDao  extends BaseRepository<Procurement, Long>{
	
	/**
	 * 根据父id查询
	 * @param parentId
	 * @return
	 */
	Procurement findByParentId(Long parentId);
	
	/**
	 * 根据类型和时间查询
	 * @param type
	 * @param startTime
	 * @param beginTime
	 * @return
	 */
	List<Procurement> findByTypeAndCreatedAtBetween(int type, Date startTime, Date endTime);

}
