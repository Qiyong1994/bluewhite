package com.bluewhite.ledger.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;

/**
 * 退货单
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_refund_bills")
public class RefundBills extends BaseEntity<Long>{
	
	/**
	 * 加工单id
	 * 
	 */
	@Column(name = "order_outSource_Id")
	private Long orderOutSourceId;

	/**
	 *  加工单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_outSource_Id", referencedColumnName = "id", insertable = false, updatable = false)
	private OrderOutSource OrderOutSource;
	
	/**
	 * 生产计划单id
	 * 冗余字段，便于查询
	 */
	@Column(name = "order_id")
	private Long orderId;
 	
	/**
	 * 退货工序 任务工序多对多
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ledger_refundBills_task", joinColumns = @JoinColumn(name = "refund_bills_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
	private Set<BaseData> outsourceTask = new HashSet<BaseData>();
	
	/**
	 * 退货数量
	 */
	@Column(name = "return_number")
	private Integer returnNumber;
	
	/**
	 * 退货日期
	 */
	@Column(name = "return_time")
	private Date returnTime;
	
	/**
	 * 退货原因
	 */
	@Column(name = "return_remark")
	private String returnRemark;
	
	/**
	 * 工序ids
	 */
	@Transient
	private String outsourceTaskIds;
	
	/**
	 * 查询字段
	 */
	@Transient
	private Date orderTimeBegin;
	/**
	 * 查询字段
	 */
	@Transient
	private Date orderTimeEnd;
	
	
	

	public String getOutsourceTaskIds() {
		return outsourceTaskIds;
	}

	public void setOutsourceTaskIds(String outsourceTaskIds) {
		this.outsourceTaskIds = outsourceTaskIds;
	}

	public Date getOrderTimeBegin() {
		return orderTimeBegin;
	}

	public void setOrderTimeBegin(Date orderTimeBegin) {
		this.orderTimeBegin = orderTimeBegin;
	}

	public Date getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(Date orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Set<BaseData> getOutsourceTask() {
		return outsourceTask;
	}

	public void setOutsourceTask(Set<BaseData> outsourceTask) {
		this.outsourceTask = outsourceTask;
	}

	public Long getOrderOutSourceId() {
		return orderOutSourceId;
	}

	public void setOrderOutSourceId(Long orderOutSourceId) {
		this.orderOutSourceId = orderOutSourceId;
	}

	public OrderOutSource getOrderOutSource() {
		return OrderOutSource;
	}

	public void setOrderOutSource(OrderOutSource orderOutSource) {
		OrderOutSource = orderOutSource;
	}

	public Integer getReturnNumber() {
		return returnNumber;
	}

	public void setReturnNumber(Integer returnNumber) {
		this.returnNumber = returnNumber;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public String getReturnRemark() {
		return returnRemark;
	}

	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
	}
	
	
	
	
	

	
}
