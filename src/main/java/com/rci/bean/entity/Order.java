package com.rci.bean.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 票据类，每一笔生意生成一个票据，每日和收银机数据同步
 * @author zj
 *
 */
@Entity
@Table(name="tb_order")
public class Order extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6766934640685300071L;

	private Integer version;
	
	private Long oid;
	
	/* 订单编号   */
	private String orderNo;
	
	/* 开桌时间  */
	private Timestamp openDeskTime;
	
	/* 结账时间  */
	private Timestamp checkoutTime;
	
	/* 票据应收金额    */
	private BigDecimal receivableAmount;

	/* 折扣方案  */
	private DiscountScheme scheme;  //代金券或者是打折
	
//	/* 临时折扣方案  */
//	private BigDecimal tempDiscountRate; //解决收银员自己手动输入打折率
	
	/* 是否有临时折扣方案  */
	private Boolean isTempDiscount;
	
	/* 实收金额   */
	private BigDecimal actualAmount;
	
	/* 实际入账金额  */
	private List<PostOrderAccount> postOrderAccounts;
	
	/* 具体菜单明细  */
	private List<OrderItem> items;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="oid", nullable=false,updatable=false)
	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	@Column(name="order_no")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="order_opendesk_time")
	public Timestamp getOpenDeskTime() {
		return openDeskTime;
	}

	public void setOpenDeskTime(Timestamp openDeskTime) {
		this.openDeskTime = openDeskTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="order_checkout_time")
	public Timestamp getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(Timestamp checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	@Column(name="order_receivable_amount")
	public BigDecimal getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(BigDecimal receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	@OneToOne
	@JoinColumn(name="discount_scheme_id")
	public DiscountScheme getScheme() {
		return scheme;
	}

	public void setScheme(DiscountScheme scheme) {
		this.scheme = scheme;
	}

	@Column(name="is_temp_discount")
	public Boolean getIsTempDiscount() {
		return isTempDiscount;
	}

	public void setIsTempDiscount(Boolean isTempDiscount) {
		this.isTempDiscount = isTempDiscount;
	}

	@Column(name="order_actual_amount")
	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="order")
	public List<PostOrderAccount> getPostOrderAccounts() {
		return postOrderAccounts;
	}

	public void setPostOrderAccounts(List<PostOrderAccount> postOrderAccounts) {
		this.postOrderAccounts = postOrderAccounts;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="order")
	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	@Override
	public Serializable getId() {
		return oid;
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
