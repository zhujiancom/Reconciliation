package com.rci.bean.entity;

import java.math.BigDecimal;
import java.util.Date;

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
	
	/* 是否套餐 */
	private String suitFlag;
	
	/* 套餐编号 */
	private String suitNo;
	
	/* 原价 */
	private BigDecimal price;
	
	/* 折扣率 */
	private BigDecimal discountRate;
	
	/* 点菜数量  */
	private Integer count;
	
	/* 退菜数量 */
	private Integer countback;
	
	/* 折扣金额  */
	private BigDecimal discountAmount;
	
	/* 实际金额   */
	private BigDecimal actualAmount;
	
	/* 点菜时间  */
	private Date consumeTime;
	
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

	public String getSuitFlag() {
		return suitFlag;
	}

	public void setSuitFlag(String suitFlag) {
		this.suitFlag = suitFlag;
	}

	public String getSuitNo() {
		return suitNo;
	}

	public void setSuitNo(String suitNo) {
		this.suitNo = suitNo;
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

	@Column(name="item_count_back")
	public Integer getCountback() {
		return countback;
	}

	public void setCountback(Integer countback) {
		this.countback = countback;
	}

	@Column(name="origin_price")
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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
	public Date getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="order_id")
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

//	@Override
//	public Serializable getId() {
//		return odid;
//	}

	@Override
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	/**
	 * 判断是否是属于套餐
	 * @return
	 */
	public Boolean isSuit(){
		return "Y".equalsIgnoreCase(suitFlag);
	}
}
