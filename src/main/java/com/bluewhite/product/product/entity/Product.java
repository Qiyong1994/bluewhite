package com.bluewhite.product.product.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.sys.entity.Files;

/**
 * 蓝白产品
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "sys_product")
public class Product extends BaseEntity<Long> {

	/**
	 * 产品编号
	 */
	@Column(name = "number")
	private String number;

	/**
	 * 产品名
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 产品照片
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Files> fileSet = new HashSet<>();

	/**
	 * 各个部门自己的产品编号
	 */
	@Column(name = "department_number")
	private String departmentNumber;

	/**
	 * 产品来源部门
	 */
	@Column(name = "origin_department")
	private String originDepartment;

	/**
	 * 产品分类(1=成品，2=皮壳)
	 */
	@Column(name = "product_type")
	private Integer productType;

	/**
	 * 产品本身外发价格
	 */
	@Transient
	private Double hairPrice;

	/**
	 * 当部门预计生产价格
	 */
	@Transient
	private Double departmentPrice;

	/**
	 * 产品本身外发价格
	 */
	@Transient
	private Double puncherHairPrice;

	/**
	 * 当部门预计生产价格
	 */
	@Transient
	private Double puncherDepartmentPrice;

	/**
	 * 工序部门类型
	 */
	@Transient
	private Integer type;

	/**
	 * 针工价格
	 */
	@Transient
	private Double deedlePrice;

	/**
	 * 照片ids
	 */
	@Transient
	private String fileIds;
	
	/**
	 * 仓库类型id
	 */
	@Transient
	private Long warehouse;

	/**
	 * 库存数量
	 */
	@Transient
	private List<Map<String, Object>> mapList = new ArrayList<>();
	
	


	public Long getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Long warehouse) {
		this.warehouse = warehouse;
	}

	public List<Map<String, Object>> getMapList() {
		return mapList;
	}

	public void setMapList(List<Map<String, Object>> mapList) {
		this.mapList = mapList;
	}

	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getDepartmentNumber() {
		return departmentNumber;
	}

	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	public String getOriginDepartment() {
		return originDepartment;
	}

	public void setOriginDepartment(String originDepartment) {
		this.originDepartment = originDepartment;
	}

	public Double getPuncherHairPrice() {
		return puncherHairPrice;
	}

	public void setPuncherHairPrice(Double puncherHairPrice) {
		this.puncherHairPrice = puncherHairPrice;
	}

	public Double getPuncherDepartmentPrice() {
		return puncherDepartmentPrice;
	}

	public void setPuncherDepartmentPrice(Double puncherDepartmentPrice) {
		this.puncherDepartmentPrice = puncherDepartmentPrice;
	}

	public Double getDeedlePrice() {
		return deedlePrice;
	}

	public void setDeedlePrice(Double deedlePrice) {
		this.deedlePrice = deedlePrice;
	}

	public Double getHairPrice() {
		return hairPrice;
	}

	public void setHairPrice(Double hairPrice) {
		this.hairPrice = hairPrice;
	}

	public Double getDepartmentPrice() {
		return departmentPrice;
	}

	public void setDepartmentPrice(Double departmentPrice) {
		this.departmentPrice = departmentPrice;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Files> getFileSet() {
		return fileSet;
	}

	public void setFileSet(Set<Files> fileSet) {
		this.fileSet = fileSet;
	}

}
