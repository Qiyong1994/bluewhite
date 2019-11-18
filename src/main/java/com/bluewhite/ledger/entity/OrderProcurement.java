package com.bluewhite.ledger.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.system.user.entity.User;

/**
 * 采购（采购面辅料订单）(由生产下单用料转化得到) 当生产耗料库存不足时，生成采购单
 * 
 * 采购单作为库存记录单使用，所以一个物料的库存同时拥有多个采购单，采购单内容包括物料的库位，价格
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_order_procurement")
public class OrderProcurement extends BaseEntity<Long> {

	/**
	 * 采购单编号(批次+产品名称+物料名称+订货客户名称生成的新编号)
	 */
	@Column(name = "order_procurement_number")
	private String orderProcurementNumber;

	/**
	 * 物料名id
	 */
	@Column(name = "materiel_id")
	private Long materielId;

	/**
	 * 物料
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "materiel_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Materiel materiel;

	/**
	 * 订单id
	 * 
	 */
	@Column(name = "order_id")
	private Long orderId;

	/**
	 * 订单（该订单所定的采购单）
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Order order;
	
	/**
	 * 约定面料价格
	 */
	@Column(name = "convention_price")
	private Double conventionPrice;
	
	/**
	 * 实际面料价格
	 */
	@Column(name = "price")
	private Double price;
	
	/**
	 * 约定平方克重
	 */
	@Column(name = "convention_square_gram")
	private Double conventionSquareGram;
	
	/**
	 * 实际平方克重
	 */
	@Column(name = "square_gram")
	private Double squareGram;

	/**
	 * 下单数量
	 */
	@Column(name = "place_order_number")
	private Double placeOrderNumber;

	/**
	 * 下单日期
	 */
	@Column(name = "place_order_time")
	private Date placeOrderTime;

	/**
	 * 预计到货日期
	 */
	@Column(name = "expect_arrival_time")
	private Date expectArrivalTime;

	/**
	 * 预计付款日期
	 */
	@Column(name = "expect_payment_time")
	private Date expectPaymentTime;


	/**
	 * 虚拟库存 剩余数量
	 */
	@Column(name = "residue_number")
	private Double residueNumber;

	/**
	 * 客户id
	 * 
	 */
	@Column(name = "customer_id")
	private Long customerId;

	/**
	 * 客户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customer customer;

	/**
	 * 订料人id
	 * 
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 订料人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;

	
	/**
	 * 根据客户来的新编号
	 */
	@Column(name = "new_code")
	private String newCode;

	/**
	 * 是否审核（0=未审核，1=已审核）审核成功后 物料仓库查看
	 */
	@Column(name = "audit")
	private Integer audit;
	
	/****物料仓库参数****/
	
	/**
	 * 是否验货
	 */
	@Column(name = "inspection")
	private Integer inspection;
	
	/**
	 * 是否到货（0=否，1=是）
	 */
	@Column(name = "arrival")
	private Integer arrival;
	
	/**
	 * 到货日期
	 */
	@Column(name = "arrival_time")
	private Date arrivalTime;

	/**
	 * 到货数量
	 */
	@Column(name = "arrival_number")
	private Double arrivalNumber;
	
	/**
	 * 入库操作人id
	 * 
	 */
	@Column(name = "user_storage_id")
	private Long userStorageId;

	/**
	 * 入库操作人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_storage_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User userStorage;

	/**
	 * 入库库存是否有出入(0=否，1=是)
	 */
	@Column(name = "in_out_error")
	private Integer inOutError;
	
	/**
	 * 库位
	 * 
	 */
	@Column(name = "materiel_location")
	private String materielLocation;
	
	/**
	 * 是否接受不相符到货
	 *  1.生产和销售接收
	 *  2.退回供应商 
	 *  3.降价接受 
	 *  4.部分接受，部分退货 
	 *  5.部分接受，部分延期付款
	 */
	@Column(name = "discrepancy_arrival")
	private Integer discrepancyArrival;
	
	/**
	 * 退货数量
	 */
	@Column(name = "return_number")
	private Double returnNumber;
	
	/**
	 * 部分接受延期付款数量
	 */
	@Column(name = "part_delay_number")
	private Double partDelayNumber;
	
	/**
	 * 部分接受延期付款日期
	 */
	@Column(name = "part_delay_time")
	private Date part_delay_time;
	
	/**
	 * 偷克重产生被偷价值
	 */
	@Column(name = "gram_price")
	private Double gram_price;
	
	/**
	 * 占用供应商资金利息 (日利息0.00022)
	 */
	@Column(name = "interest")
	private Double interest;
	

	/**** 用于财务应付帐单参数 ****/

	/**
	 * 付款日要付金额
	 */
	@Column(name = "payment_money")
	private Double paymentMoney;
	
	
	
	


	/**
	 * 订单（下单合同）生产用料id
	 */
	@Transient
	private Long orderMaterialId;

	/**
	 * 产品name
	 */
	@Transient
	private String productName;

	/**
	 * 订料人
	 */
	@Transient
	private String userName;

	/**
	 * 客户
	 */
	@Transient
	private String customerName;

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
	
	
	

	public Double getConventionPrice() {
		return conventionPrice;
	}

	public void setConventionPrice(Double conventionPrice) {
		this.conventionPrice = conventionPrice;
	}

	public Double getConventionSquareGram() {
		return conventionSquareGram;
	}

	public void setConventionSquareGram(Double conventionSquareGram) {
		this.conventionSquareGram = conventionSquareGram;
	}

	public Integer getInspection() {
		return inspection;
	}

	public void setInspection(Integer inspection) {
		this.inspection = inspection;
	}

	public Integer getDiscrepancyArrival() {
		return discrepancyArrival;
	}

	public void setDiscrepancyArrival(Integer discrepancyArrival) {
		this.discrepancyArrival = discrepancyArrival;
	}

	public Double getPaymentMoney() {
		return paymentMoney;
	}

	public void setPaymentMoney(Double paymentMoney) {
		this.paymentMoney = paymentMoney;
	}

	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

	public Date getExpectPaymentTime() {
		return expectPaymentTime;
	}

	public void setExpectPaymentTime(Date expectPaymentTime) {
		this.expectPaymentTime = expectPaymentTime;
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

	public Long getUserStorageId() {
		return userStorageId;
	}

	public void setUserStorageId(Long userStorageId) {
		this.userStorageId = userStorageId;
	}

	public User getUserStorage() {
		return userStorage;
	}

	public void setUserStorage(User userStorage) {
		this.userStorage = userStorage;
	}

	public Integer getInOutError() {
		return inOutError;
	}

	public void setInOutError(Integer inOutError) {
		this.inOutError = inOutError;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Double getResidueNumber() {
		return residueNumber;
	}

	public void setResidueNumber(Double residueNumber) {
		this.residueNumber = residueNumber;
	}

	public Integer getArrival() {
		return arrival;
	}

	public void setArrival(Integer arrival) {
		this.arrival = arrival;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getNewCode() {
		return newCode;
	}

	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}

	public Double getSquareGram() {
		return squareGram;
	}

	public void setSquareGram(Double squareGram) {
		this.squareGram = squareGram;
	}

	public Date getExpectArrivalTime() {
		return expectArrivalTime;
	}

	public void setExpectArrivalTime(Date expectArrivalTime) {
		this.expectArrivalTime = expectArrivalTime;
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

	public Long getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Long materielId) {
		this.materielId = materielId;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public String getMaterielLocation() {
		return materielLocation;
	}

	public void setMaterielLocation(String materielLocation) {
		this.materielLocation = materielLocation;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPlaceOrderNumber() {
		return placeOrderNumber;
	}

	public Double getArrivalNumber() {
		return arrivalNumber;
	}

	public void setArrivalNumber(Double arrivalNumber) {
		this.arrivalNumber = arrivalNumber;
	}

	public void setPlaceOrderNumber(Double placeOrderNumber) {
		this.placeOrderNumber = placeOrderNumber;
	}

	public String getOrderProcurementNumber() {
		return orderProcurementNumber;
	}

	public void setOrderProcurementNumber(String orderProcurementNumber) {
		this.orderProcurementNumber = orderProcurementNumber;
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

	public Long getOrderMaterialId() {
		return orderMaterialId;
	}

	public void setOrderMaterialId(Long orderMaterialId) {
		this.orderMaterialId = orderMaterialId;
	}

	public Date getPlaceOrderTime() {
		return placeOrderTime;
	}

	public void setPlaceOrderTime(Date placeOrderTime) {
		this.placeOrderTime = placeOrderTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
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

}
