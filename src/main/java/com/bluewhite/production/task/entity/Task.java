package com.bluewhite.production.task.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.system.user.entity.User;
/**
 * 产品批次任务
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_task")
public class Task  extends BaseEntity<Long>{
	
	/**
	 * 批次id  
	 */
	@Column(name = "bacth_id")
	private Long bacthId;
	
	/**
	 * 批次   任务多对一批次
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bacth_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Bacth Bacth;
	
	
	/**
	 * 领取任务人员ids
	 */
	@Transient
	private String userIds;
	
	/**
	 * 领取任务人员
	 */
	@OneToMany(mappedBy = "task")
	private Set<User> users = new HashSet<User>();
	
	/**
	 * 产品名称
	 */
	@Column(name = "product_name")
	private String productName;
	
	/**
	 * 工序名称
	 */
	@Column(name = "procedure_name")
	private String procedureName;
	
    /**
     * 任务数量
     */
	@Column(name = "number")
    private Integer number;
 
    /**
     * 任务状态(0=开始 ，1=结束)
     */
	@Column(name = "status")
    private Integer  status;
    
    /**
     * 预计完成时间
     */
	@Column(name = "expect_time")
    private Double expectTime;
	
    /**
     * 实际任务完成时间
     */
	@Column(name = "task_time")
    private Double taskTime;
	/**
	 * 任务价值
	 */
	@Column(name = "task_price")
	private Double taskPrice;
	
	/**
	 * b工资净值
	 */
	@Column(name = "b_price")
	private Double BPrice;
	
	/**
	 * 工序所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	@Column(name = "type")
	private Integer type;
	
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
    
	
	/**
	 * 查询字段 批次号
	 */
	@Transient
	private String bacthNumber;
	
	
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getBacthNumber() {
		return bacthNumber;
	}
	public void setBacthNumber(String bacthNumber) {
		this.bacthNumber = bacthNumber;
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
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public String getProcedureName() {
		return procedureName;
	}
	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}
	public Double getTaskPrice() {
		return taskPrice;
	}
	public void setTaskPrice(Double taskPrice) {
		this.taskPrice = taskPrice;
	}
	public Double getBPrice() {
		return BPrice;
	}
	public void setBPrice(Double bPrice) {
		BPrice = bPrice;
	}
	public Long getBacthId() {
		return bacthId;
	}
	public void setBacthId(Long bacthId) {
		this.bacthId = bacthId;
	}
	public Bacth getBacth() {
		return Bacth;
	}
	public void setBacth(Bacth bacth) {
		Bacth = bacth;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Double getExpectTime() {
		return expectTime;
	}
	public void setExpectTime(Double expectTime) {
		this.expectTime = expectTime;
	}
	public Double getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(Double taskTime) {
		this.taskTime = taskTime;
	}
    
    
    
    
    
    


}
