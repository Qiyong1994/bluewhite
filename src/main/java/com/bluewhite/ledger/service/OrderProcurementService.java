package com.bluewhite.ledger.service;

import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.OrderProcurement;

public interface OrderProcurementService extends BaseCRUDService<OrderProcurement, Long> {
	
	
	/**
	 * 分页查看面料采购订单
	 * 
	 * @param mixed
	 * @param page
	 * @return
	 */
	public PageResult<OrderProcurement> findPages(OrderProcurement orderProcurement, PageParameter page);
	
	/**
	 * （采购部）确认入库单
	 * @param ids
	 * @return
	 */
	public int confirmOrderProcurement(OrderProcurement orderProcurement);
	
	/**
	 * 新增采购单
	 * @param orderProcurement
	 */
	public void saveOrderProcurement(OrderProcurement orderProcurement);
	
	/**
	 * 删除采购单
	 * @param ids
	 * @return
	 */
	public int deleteOrderProcurement(String ids);

	/**
	 * 审核采购单入库
	 * @param ids
	 * @return
	 */
	public int arrivalOrderProcurement(String ids);
	
	/**
	 * 库存不符预警
	 * @return
	 */
	public List<OrderProcurement> warningOrderProcurement(Integer inOut);
	
	/**
	 * 库存不符预警,一键修复
	 * @param ids
	 * @return
	 */
	public int fixOrderProcurement(String ids);
	
	/**
	 * 面辅料库管修改采购入库单
	 * @param orderProcurement
	 */
	public void updateOrderProcurement(OrderProcurement orderProcurement);
	
	/**
	 * 审核采购单进入仓库
	 * @param ids
	 * @return
	 */
	public int auditOrderProcurement(String ids);
	
	/**
	 * 对采购单进行验货
	 * @param ids
	 * @return
	 */
	public int inspectionOrderProcurement(String ids);
	
	/**
	 * 生成退货单
	 * @param orderProcurement
	 */
	public void returnOrderProcurement(OrderProcurement orderProcurement);


}
