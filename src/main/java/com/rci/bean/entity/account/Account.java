package com.rci.bean.entity.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rci.bean.entity.BaseEntity;

/**
 * 账户信息
 * @author zj
 *
 */
@Entity
@Table(name="tb_acc_account")
public class Account extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4314983525797755059L;

	private Integer version;
	
	private Long aid;
	
	/* 账户编号 */
	private String accNo;
	
	/* 账户名称  */
	private String accountName;
	
	/* 账户总额 */
	private BigDecimal amount;
	
	/* 账户总收入 */
	private BigDecimal earningAmount;
	
	/* 账户总支出 */
	private BigDecimal expenseAmount;
	
	/* 今日收入总额  */
	private BigDecimal todayAmount;
	
	/* 昨日收入总额  */
	private BigDecimal yestodayAmount;
	
	/* 交易流水  */
	private List<TradeInfo> trades;
	
	/* 最后交易时间 */
	private Date lastOperateTime;
	
	public Account(){}
	public Account(String accNo){
		this.accNo = accNo;
	}
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="aid", nullable=false,updatable=false)
	public Long getAid() {
		return aid;
	}

	public void setAid(Long aid) {
		this.aid = aid;
	}

	@Column(name="acc_no")
	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	@Column(name="acc_name")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name="acc_amount")
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name="acc_earning_amount")
	public BigDecimal getEarningAmount() {
		return earningAmount;
	}

	public void setEarningAmount(BigDecimal earningAmount) {
		this.earningAmount = earningAmount;
	}

	@Column(name="acc_expense_amount")
	public BigDecimal getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(BigDecimal expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	@Column(name="acc_today_amount")
	public BigDecimal getTodayAmount() {
		return todayAmount;
	}

	public void setTodayAmount(BigDecimal todayAmount) {
		this.todayAmount = todayAmount;
	}

	@Column(name="acc_yestoday_amount")
	public BigDecimal getYestodayAmount() {
		return yestodayAmount;
	}

	public void setYestodayAmount(BigDecimal yestodayAmount) {
		this.yestodayAmount = yestodayAmount;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="account")
	public List<TradeInfo> getTrades() {
		return trades;
	}

	public void setTrades(List<TradeInfo> trades) {
		this.trades = trades;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="acc_last_oper_time")
	public Date getLastOperateTime() {
		return lastOperateTime;
	}

	public void setLastOperateTime(Date lastOperateTime) {
		this.lastOperateTime = lastOperateTime;
	}

//	@Override
//	public Serializable getId() {
//		return aid;
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
