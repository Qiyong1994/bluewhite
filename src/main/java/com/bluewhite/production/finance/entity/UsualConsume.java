package com.bluewhite.production.finance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 日常消费实体类
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_usual_consume")
public class UsualConsume extends BaseEntity<Long> {
	
	/**
	 * 部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	@Column(name = "type")
	private Integer type;
	/**
	 * 房租
	 */
	@Column(name = "chummage")	
	private Double chummage;
	/**
	 * 水电
	 */
	@Column(name = "hydropower")
	private Double hydropower;
	/**
	 * 后勤
	 */
	@Column(name = "logistics")
	private Double logistics;
	
	/**
	 * 日期
	 */
	@Column(name = "consume_date")
	private Date consumeDate;
	
	/**
	 * 最近人消费后勤
	 */
	@Transient
	private Double  peopleLogistics;
	
	/**
	 * 最近包装车间人数
	 */
	@Transient
	private Double  peopleNumber;
	
	/**
	 * 当月房租设定
	 */
	@Transient
	private Double  monthChummage;
	
	/**
	 * 当月水电
	 */
	@Transient
	private Double  monthHydropower;
	
	/**
	 * 当月后勤餐饮保障
	 */
	@Transient
	private Double  monthLogistics;
	
	/**
	 * 设备折旧
	 */
	@Transient
	private Double  equipment;
	
	/**
	 * 查询字段开始时间
	 */
	@Transient
	private Date orderTimeBegin;
	/**
	 * 查询字段结束时间
	 */
	@Transient
	private Date orderTimeEnd;
	
	
	

	public Double getEquipment() {
		return equipment;
	}
	public void setEquipment(Double equipment) {
		this.equipment = equipment;
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
	public Date getConsumeDate() {
		return consumeDate;
	}
	public void setConsumeDate(Date consumeDate) {
		this.consumeDate = consumeDate;
	}
	public Double getPeopleLogistics() {
		return peopleLogistics;
	}
	public void setPeopleLogistics(Double peopleLogistics) {
		this.peopleLogistics = peopleLogistics;
	}
	public Double getPeopleNumber() {
		return peopleNumber;
	}
	public void setPeopleNumber(Double peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	public Double getMonthChummage() {
		return monthChummage;
	}
	public void setMonthChummage(Double monthChummage) {
		this.monthChummage = monthChummage;
	}
	public Double getMonthHydropower() {
		return monthHydropower;
	}
	public void setMonthHydropower(Double monthHydropower) {
		this.monthHydropower = monthHydropower;
	}
	public Double getMonthLogistics() {
		return monthLogistics;
	}
	public void setMonthLogistics(Double monthLogistics) {
		this.monthLogistics = monthLogistics;
	}

	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Double getChummage() {
		return chummage;
	}
	public void setChummage(Double chummage) {
		this.chummage = chummage;
	}
	public Double getHydropower() {
		return hydropower;
	}
	public void setHydropower(Double hydropower) {
		this.hydropower = hydropower;
	}
	public Double getLogistics() {
		return logistics;
	}
	public void setLogistics(Double logistics) {
		this.logistics = logistics;
	}
	
	
	
	

}
