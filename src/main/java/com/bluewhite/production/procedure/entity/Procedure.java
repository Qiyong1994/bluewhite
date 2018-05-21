package com.bluewhite.production.procedure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;

/**
 * 工序实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_procedure")
public class Procedure extends BaseEntity<Long> {
	
	/**
	 * 工序名称
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 工序时间
	 */
	@Column(name = "working_time")
	private Double workingTime;
	
	/**
	 * 产品id
	 */
	@Column(name = "product_id")
	private Long productId;
	
	/**
	 * 是否删除
	 */
	@Column(name = "isDel")
	private Integer isDel;
	
	/**
	 * 工序类型id
	 */
	@Column(name = "procedure_type_id")
	private Long procedureTypeId;
	
	/**
	 * 工序
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "procedure_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData procedureType;
	
	
	/**
	 * 工序所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	private Integer type;
	
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(Double workingTime) {
		this.workingTime = workingTime;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Long getProcedureTypeId() {
		return procedureTypeId;
	}

	public void setProcedureTypeId(Long procedureTypeId) {
		this.procedureTypeId = procedureTypeId;
	}

	public BaseData getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(BaseData procedureType) {
		this.procedureType = procedureType;
	}
	
	


}