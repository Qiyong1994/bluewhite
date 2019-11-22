package com.bluewhite.personnel.officeshare.entity;

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

/**
 * 办公用品库存
 * @author zhangliang
 *
 */
@Entity
@Table(name = "person_office_supplies")
public class OfficeSupplies extends BaseEntity<Long>{
	
	/**
     * 物品名
     */
	@Column(name = "name")
    private String name;
	
	/**
     * 价格
     */
	@Column(name = "price")
    private Double price;
	
	
	/**
	 * 单位id
	 */
	@Column(name = "unit_id")
	private Long unitId;
	
	/**
	 * 单位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData unit;
	
	/**
     * 类型(1.办公用品，2.机械配件)
     */
	@Column(name = "type")
    private Integer type;
	
	/**
	 * 库存数量
	 * 
	 */
	@Column(name = "inventory_number")
	private Integer inventoryNumber;
	
	/**
	 * 库位
	 * 
	 */
	@Column(name = "location")
	private String location;
	
	/**
	 * 库值
	 * 
	 */
	@Column(name = "library_value")
	private Double libraryValue;
	
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

	public Double getLibraryValue() {
		return libraryValue;
	}

	public void setLibraryValue(Double libraryValue) {
		this.libraryValue = libraryValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public BaseData getUnit() {
		return unit;
	}

	public void setUnit(BaseData unit) {
		this.unit = unit;
	}


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getInventoryNumber() {
		return inventoryNumber;
	}

	public void setInventoryNumber(Integer inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
	


}
