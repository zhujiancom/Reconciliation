package com.rci.bean.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rci.constants.enums.SchemeType;

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
	
	private SchemeType type;
	
	/* 活动或代金券名称 */
	private String name;
	
	/* 实际买入价格  */
	private BigDecimal postPrice;

	/* 抵用价格  */
	private BigDecimal price;
	
	/* 打折率或抽成  */
	private BigDecimal commission;
	
	/* 差价 */
	private BigDecimal spread;
	
	
	private Paymode paymode;
	
	public Scheme(){}
	
	public Scheme(SchemeType type,String name){
		this.type = type;
		this.name = name;
	}
	
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

	@Enumerated(EnumType.STRING)
	public SchemeType getType() {
		return type;
	}

	public void setType(SchemeType type) {
		this.type = type;
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

	@Column(name="spread")
	public BigDecimal getSpread() {
		return spread;
	}

	public void setSpread(BigDecimal spread) {
		this.spread = spread;
	}

	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},optional=true)
	@JoinColumn(name="paymode_id")
	public Paymode getPaymode() {
		return paymode;
	}

	public void setPaymode(Paymode paymode) {
		this.paymode = paymode;
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
