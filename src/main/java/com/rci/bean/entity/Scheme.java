package com.rci.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 打折或代金券方案
 * 
 * @author zj
 * 
 */
@Entity
@Table(name="tb_scheme")
public class Scheme extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3609443205685104360L;

	private Integer version;

	private Long sid;
	
	/* 活动或代金券名称 */
	private String name;
	
	/* 实际买入价格  */
	private BigDecimal postPrice;

	/* 抵用价格  */
	private BigDecimal price;
	
	/* 打折率或抽成  */
	private BigDecimal commission;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="sid", nullable=false,updatable=false)
	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	@Column(name="scheme_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="post_price")
	public BigDecimal getPostPrice() {
		return postPrice;
	}

	public void setPostPrice(BigDecimal postPrice) {
		this.postPrice = postPrice;
	}

	@Column(name="price")
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name="commission")
	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
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
