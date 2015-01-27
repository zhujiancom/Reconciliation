package com.rci.bean.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 票据详细信息
 * @author zj
 *
 */
@Entity
@Table(name="tb_order_item")
public class OrderItem extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5921663072004888953L;

	private Integer version;
	
	private Long odid;
	
	/* 菜品  */
	private Dish dish;
	
	/* 折扣率 */
	private BigDecimal discountRate;
	
	/* 数量  */
	private Integer count;
	
	/* 折扣金额  */
	private BigDecimal discountAmount;
	
	/* 实际金额   */
	private BigDecimal actualAmount;
	
	/* 消费时间  */
	private Timestamp consumeTime;
	
	/* 对应票据  */
	private Order order;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="odid", nullable=false,updatable=false)
	public Long getOdid() {
		return odid;
	}

	public void setOdid(Long odid) {
		this.odid = odid;
	}

	@OneToOne
	@JoinColumn(name="did")
	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	@Column(name="item_discount_rate")
	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	@Column(name="item_count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name="item_discount_amount")
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Column(name="item_actual_amount")
	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	@Column(name="item_consume_time")
	public Timestamp getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Timestamp consumeTime) {
		this.consumeTime = consumeTime;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="order_item_id")
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public Serializable getId() {
		return odid;
	}

	@Override
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion(Integer version) {
		this.version = version;
	}
}
