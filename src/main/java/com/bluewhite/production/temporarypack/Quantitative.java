
package com.bluewhite.production.temporarypack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.ledger.entity.PackingMaterials;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.system.user.entity.User;

/**
 * 量化单
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_quantitative")
public class Quantitative extends BaseEntity<Long> {
	
	/**
	 * 量化编号
	 */
	@Column(name = "quantitative_number")
	private String quantitativeNumber;
	
	/**
	 * 包装时间
	 */
	@Column(name = "time")
	private Date time;
	
	/**
	 * 包装数量
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 子单list
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "quantitative_id")
	private List<QuantitativeChild> quantitativeChilds = new ArrayList<>();
	
	/**
	 * 包装物list
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "quantitative_id")
	private List<PackingMaterials> packingMaterials = new ArrayList<>();
	
	/**
	 * 贴包人id
	 */
	@Column(name = "user_id")
	private Long userid;
	/**
	 * 铁包人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	
	/**
	 * 是否发货
	 */
	@Column(name = "flag")
	private Integer flag;
	
	/**
	 * 是否打印
	 */
	@Column(name = "print")
	private Integer print;
	
	/**
	 * 产品名称
	 */
	@Transient
	private String name;
	
	/**
	 * 产品名称
	 */
	@Transient
	private String productName;

	/**
	 * 产品编号
	 */
	@Transient
	private String productNumber;

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
	 * 子单
	 */
	@Transient
	private String child;
	
	/**
	 * 批次号
	 */
	@Transient
	private String bacthNumber;
	
	/**
	 * 新增包装物json数据
	 */
	@Transient
	private String packingMaterialsJson;
	
	
	
	
	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getPrint() {
		return print;
	}

	public void setPrint(Integer print) {
		this.print = print;
	}

	public String getPackingMaterialsJson() {
		return packingMaterialsJson;
	}

	public void setPackingMaterialsJson(String packingMaterialsJson) {
		this.packingMaterialsJson = packingMaterialsJson;
	}

	public List<PackingMaterials> getPackingMaterials() {
		return packingMaterials;
	}

	public void setPackingMaterials(List<PackingMaterials> packingMaterials) {
		this.packingMaterials = packingMaterials;
	}

	public String getBacthNumber() {
		return bacthNumber;
	}

	public void setBacthNumber(String bacthNumber) {
		this.bacthNumber = bacthNumber;
	}

	public List<QuantitativeChild> getQuantitativeChilds() {
		return quantitativeChilds;
	}

	public void setQuantitativeChilds(List<QuantitativeChild> quantitativeChilds) {
		this.quantitativeChilds = quantitativeChilds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
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

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getQuantitativeNumber() {
		return quantitativeNumber;
	}

	public void setQuantitativeNumber(String quantitativeNumber) {
		this.quantitativeNumber = quantitativeNumber;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}