package com.bluewhite.onlineretailers.inventory.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
@Service
public interface OnlineOrderService extends BaseCRUDService<OnlineOrder,Long>{
	
	/**
	 * 分页查看销售单
	 * @param onlineOrder
	 * @param page
	 * @return
	 */
	public PageResult<OnlineOrder> findPage(OnlineOrder onlineOrder, PageParameter page);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public int deleteOnlineOrder(String ids);

}
