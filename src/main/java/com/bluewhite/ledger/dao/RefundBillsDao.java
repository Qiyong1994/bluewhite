package com.bluewhite.ledger.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.RefundBills;
import java.lang.Long;

public interface RefundBillsDao  extends BaseRepository<RefundBills, Long>{
	
	
	/**
	 * 通过工序和订单id查找退货单
	 * @param commodityId
	 * @return
	 * nativeQuery 表示原生sql 支持limit
	 */
	@Query(nativeQuery=true,value ="SELECT b.return_number FROM ledger_refundBills_task a , ledger_refund_bills b WHERE b.material_requisition_id = ?1 AND a.task_id= ?2 AND b.audit = 1")
	List<Integer> getReturnNumber(Long materialRequisitionId,Long taskId);
	
	/**
	 * 根据加工单id查找退货单
	 * @param orderoutsourceid
	 * @return
	 */
	List<RefundBills> findByOrderOutSourceId(Long orderoutsourceid);
	

}
