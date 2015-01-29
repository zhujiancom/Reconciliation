package com.rci.bean.entity.account;

import java.io.Serializable;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rci.bean.entity.BaseEntity;

/**
 * 账户交易信息
 * @author zj
 *
 */
@Entity
@Table(name="tb_acc_trade_info")
public class TradeInfo extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7332648169748978373L;

	private Integer version;
	
	private Long tid;
	
	/* 交易时间  */
	private Date tradeTime;
	
	/* 交易金额  */
	private BigDecimal tradeAmount;
	
	/* 交易类型 */
	private TradeType tradeType;
	
	/* 交易信息备注  */
	private String remark;
	
	private Account account;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="tid", nullable=false,updatable=false)
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="trade_time")
	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	@Column(name="trade_amount")
	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	@OneToOne
	@JoinColumn(name="trade_type_id")
	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="acc_id")
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

//	@Override
//	public Serializable getId() {
//		return tid;
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
