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
import javax.persistence.Table;

/**
 * 每张票据的最终入账金额对应关系表
 * @author zj
 *
 */
@Entity
@Table(name="tb_post_order_account_ref")
public class PostOrderAccount extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3531208118430580767L;

	private Integer version;
	
	private Long poaid;
	
	/* 入账账户  */
	private Long accountId;
	
	/* 实际入账金额  */
	private BigDecimal postAmount;
	
	/* 入账时间  */
	private Date postTime;
	
	/* 对应票据  */
	private Order order;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="poaid", nullable=false,updatable=false)
	public Long getPoaid() {
		return poaid;
	}

	public void setPoaid(Long poaid) {
		this.poaid = poaid;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getPostAmount() {
		return postAmount;
	}

	public void setPostAmount(BigDecimal postAmount) {
		this.postAmount = postAmount;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
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
//		return poaid;
//	}

	@Override
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion(Integer version) {
		this.version = version;
	}
}
