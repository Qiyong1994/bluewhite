package com.bluewhite.onlineretailers.inventory.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;
@Service
public interface ProcurementService  extends BaseCRUDService<Procurement,Long>{
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	PageResult<Procurement> findPage(Procurement param, PageParameter page);
	
	/**
	 * 新增单据
	 * @param procurement
	 */
	Procurement saveProcurement(Procurement procurement);
	
	/**
	 * 反冲单据
	 * @param ids
	 * @return
	 */
	int deleteProcurement(String ids);
	
	/**
	 * 根据时间和类型获取单据
	 * @param type
	 * @param date
	 * @param beginTime
	 * @return
	 */
	List<Procurement> findByTypeAndCreatedAt(int type, Date startTime, Date endTime);

}
