package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.MaterialOutStorage;
import com.bluewhite.ledger.entity.OutStorage;

public interface OutStorageService  extends BaseCRUDService<OutStorage,Long>{
	
	/**
	 * 生成出库单
	 * @param outStorage
	 */
	public void saveOutStorage(OutStorage outStorage);
	
	/**
	 * 删除出库单
	 * @param ids
	 * @return
	 */
	public int deleteOutStorage(String ids);
	
	/**
	 * 分页查看出库单
	 * @param page
	 * @param outStorage
	 * @return
	 */
	public PageResult<OutStorage> findPages(PageParameter page, OutStorage outStorage);

}