package com.rci.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * 折扣方案 ，  与收银机表[cybr_bt_project_detail]同步
 * @author zj
 *
 */
@Entity
@Table(name="tb_discount_scheme")
public class DiscountScheme extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7742003953909536416L;

	private Integer version;
	
	private Long sid;
	
	/* 方案编号 */
	private String sNo;
	
	/* 方案名称  */
	private String sName;
	
	/* 折扣率  */
	private BigDecimal rate;
	
	/* 提成 */
	private BigDecimal commission;
	
	private String remark;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="sid", nullable=false,updatable=false)
	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	@Column(name="scheme_no")
	public String getsNo() {
		return sNo;
	}

	public void setsNo(String sNo) {
		this.sNo = sNo;
	}

	@Column(name="scheme_name")
	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	@Column(name="scheme_rate")
	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@Column(name="scheme_commission")
	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

//	@Override
//	public Serializable getId() {
//		return sid;
//	}

	@Override
	@Version
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion(Integer version) {
		this.version = version;
	}
}
