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
import com.bluewhite.system.user.entity.User;

/**
 * 加工单由领料单而来，加工单的实际数量等于领料单的实际数量
 * 
 * 加工单 有工序任务 加工单领取部门有领取人部门决定 加工单的任务工序，由实际的领料单决定，领料单中有领取模式，裁剪,绣花,机工,针工,包装
 * 
 * 生产计划部 加工单 1.加工单 2.外发加工单
 * 
 * 加工单 需要分为仓库来源
 * 电子商务部的加工单
 * 如何区分？
 * 当客户是电子商务部订单，加工单属于电子商务仓库
 * 当客户为其他客户时，加工单属于蓝白仓库
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_order_outsource")
public class OrderOutSource extends BaseEntity<Long> {

	/**
	 * 领料单id
	 */
	@Column(name = "material_requisition_id")
	private Long materialRequisitionId;

	/**
	 * 领料单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "material_requisition_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MaterialRequisition materialRequisition;

	/**
	 * 开单时间
	 */
	@Column(name = "open_order_time")
	private Date openOrderTime;

	/**
	 * 工艺单内容填充，用于打印开单 1.棉花规格
	 */
	@Column(name = "fill")
	private String fill;

	/**
	 * 工艺单内容填充，用于打印开单 1.棉花备注
	 */
	@Column(name = "fill_remark")
	private String fillRemark;

	/**
	 * 工艺单内容填充，用于打印开单 棉花克重
	 */
	@Column(name = "gram_weight")
	private Double gramWeight;

	/**
	 * 工艺单内容填充，用于打印开单 棉花总克重（千克）
	 */
	@Column(name = "kilogram_weight")
	private Double kilogramWeight;

	/**
	 * 任务编号
	 */
	@Column(name = "out_source_number")
	private String outSourceNumber;

	/**
	 * 任务工序多对多
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ledger_outsource_task", joinColumns = @JoinColumn(name = "outsource_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
	private Set<BaseData> outsourceTask = new HashSet<BaseData>();

	/**
	 * 任务数量
	 */
	@Column(name = "process_number")
	private Integer processNumber;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 是否外发
	 */
	@Column(name = "outsource")
	private Integer outsource;

	/**
	 * 加工点id
	 */
	@Column(name = "customer_id")
	private Long customerId;

	/**
	 * 加工点
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customer customer;

	/**
	 * （在家加工）加工人id
	 */
	@Column(name = "processing_user_id")
	private Long processingUserId;

	/**
	 * 加工人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processing_user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User processingUser;

	/**
	 * 跟单人id（外协）
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 跟单人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;

	/**
	 * 是否审核
	 */
	@Column(name = "audit")
	private Integer audit;

	/**
	 * 是否出账
	 */
	@Column(name = "charge_off")
	private Integer chargeOff;
	
	/**
     * 归属仓库种类id
     */
    @Column(name = "warehouse_type_id")
    private Long warehouseTypeId;

	/**
	 * 	(申请人申请时)金额
	 */
	@Transient
	private Double money;

	/**
	 * (查询字段) 产品name
	 */
	@Transient
	private String productName;

	/**
	 *	 (查询字段) 跟单人name
	 * 
	 */
	@Transient
	private String userName;

	/**
	 * 	(查询字段) 加工点name
	 * 
	 */
	@Transient
	private String customerName;

	/**
	 * 	(查询字段) 生产计划单id
	 */
	@Transient
	private Long orderId;

	/**
	 * 工序ids
	 */
	@Transient
	private String outsourceTaskIds;

	/**
	 * 	(账单)申请日期
	 */
	@Transient
	private Date expenseDate;

	/**
	 * 到货数量
	 */
	@Transient
	private Integer arrivalNumber;

	/**
	 * 工序id
	 */
	@Transient
	private Long outsourceTaskId;

	/**
	 * 生成入库后剩余数量 
	 * 成品 针工单入库后剩余入库数量 
	 * 皮壳 机工单入库后剩余数量
	 */
	@Transient
	private Integer remainingInventory;

	/**
	 * 针工皮壳出库发货后剩余数量
	 */
	@Transient
	private Integer cotSurplusNumber;

	/**
	 * 针工皮壳库存状态
	 */
	@Transient
	private Integer cotStatus;
	
	/**
	 * 加工单退货数量
	 */
	@Transient
	private Integer refundBillsNumber;
	
	/**
     * 退货后实际数量
     */
    @Transient
    private Integer actualQuantity;
 
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
	
	

	public Long getWarehouseTypeId() {
        return warehouseTypeId;
    }

    public void setWarehouseTypeId(Long warehouseTypeId) {
        this.warehouseTypeId = warehouseTypeId;
    }

    public Integer getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(Integer actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public Integer getRefundBillsNumber() {
        return refundBillsNumber;
    }

    public void setRefundBillsNumber(Integer refundBillsNumber) {
        this.refundBillsNumber = refundBillsNumber;
    }

    public Integer getCotStatus() {
		return cotStatus;
	}

	public void setCotStatus(Integer cotStatus) {
		this.cotStatus = cotStatus;
	}

	public Integer getCotSurplusNumber() {
		return cotSurplusNumber;
	}

	public void setCotSurplusNumber(Integer cotSurplusNumber) {
		this.cotSurplusNumber = cotSurplusNumber;
	}

	public Integer getRemainingInventory() {
		return remainingInventory;
	}

	public void setRemainingInventory(Integer remainingInventory) {
		this.remainingInventory = remainingInventory;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getMaterialRequisitionId() {
		return materialRequisitionId;
	}

	public void setMaterialRequisitionId(Long materialRequisitionId) {
		this.materialRequisitionId = materialRequisitionId;
	}

	public MaterialRequisition getMaterialRequisition() {
		return materialRequisition;
	}

	public void setMaterialRequisition(MaterialRequisition materialRequisition) {
		this.materialRequisition = materialRequisition;
	}

	public Long getOutsourceTaskId() {
		return outsourceTaskId;
	}

	public void setOutsourceTaskId(Long outsourceTaskId) {
		this.outsourceTaskId = outsourceTaskId;
	}

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	public Integer getArrivalNumber() {
		return arrivalNumber;
	}

	public void setArrivalNumber(Integer arrivalNumber) {
		this.arrivalNumber = arrivalNumber;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getChargeOff() {
		return chargeOff;
	}

	public void setChargeOff(Integer chargeOff) {
		this.chargeOff = chargeOff;
	}

	public Long getProcessingUserId() {
		return processingUserId;
	}

	public void setProcessingUserId(Long processingUserId) {
		this.processingUserId = processingUserId;
	}

	public User getProcessingUser() {
		return processingUser;
	}

	public void setProcessingUser(User processingUser) {
		this.processingUser = processingUser;
	}

	public String getOutsourceTaskIds() {
		return outsourceTaskIds;
	}

	public void setOutsourceTaskIds(String outsourceTaskIds) {
		this.outsourceTaskIds = outsourceTaskIds;
	}

	public Set<BaseData> getOutsourceTask() {
		return outsourceTask;
	}

	public void setOutsourceTask(Set<BaseData> outsourceTask) {
		this.outsourceTask = outsourceTask;
	}

	public Integer getOutsource() {
		return outsource;
	}

	public void setOutsource(Integer outsource) {
		this.outsource = outsource;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOutSourceNumber() {
		return outSourceNumber;
	}

	public void setOutSourceNumber(String outSourceNumber) {
		this.outSourceNumber = outSourceNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

	public String getFill() {
		return fill;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}

	public String getFillRemark() {
		return fillRemark;
	}

	public void setFillRemark(String fillRemark) {
		this.fillRemark = fillRemark;
	}

	public Double getGramWeight() {
		return gramWeight;
	}

	public void setGramWeight(Double gramWeight) {
		this.gramWeight = gramWeight;
	}

	public Double getKilogramWeight() {
		return kilogramWeight;
	}

	public void setKilogramWeight(Double kilogramWeight) {
		this.kilogramWeight = kilogramWeight;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getProcessNumber() {
		return processNumber;
	}

	public void setProcessNumber(Integer processNumber) {
		this.processNumber = processNumber;
	}

	public Date getOpenOrderTime() {
		return openOrderTime;
	}

	public void setOpenOrderTime(Date openOrderTime) {
		this.openOrderTime = openOrderTime;
	}

}
