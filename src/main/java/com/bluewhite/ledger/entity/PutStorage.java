package com.bluewhite.ledger.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
import com.bluewhite.product.product.entity.Product;

/**
 * 
 * 入库单
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_put_storage")
public class PutStorage extends BaseEntity<Long> {

	/**
	 * 产品id
	 */
	@Column(name = "product_id")
	private Long productId;

	/**
	 * 产品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Product product;

	/**
	 * 入库单的订单状态 （1=生产入库） （2=调拨入库） （3=退货入库 ） （4=换货入库 ） （5=采购入库） （6=盘亏入库）
	 */
	@Column(name = "in_status")
	private Integer inStatus;

	/**
	 * 仓管指定 入库仓库种类id
	 */
	@Column(name = "in_warehouse_type_id")
	private Long inWarehouseTypeId;

	/**
	 * 入库仓库种类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "in_warehouse_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData inWarehouseType;

	/**
	 * 仓管指定 入库库存id
	 */
	@Column(name = "inventory_id")
	private Long inventoryId;

	/**
	 * 入库库存
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inventory_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Inventory inventory;

	/**
	 * 入库时间
	 */
	@Column(name = "arrival_time")
	private Date arrivalTime;

	/**
	 * 入库数量
	 */
	@Column(name = "arrival_number")
	private Integer arrivalNumber;

	/**
	 * 库位
	 */
	@Column(name = "location")
	private String location;

	/**
	 * 出库后剩余数量
	 */
	@Column(name = "surplus_Number")
	private Integer surplusNumber;

}
