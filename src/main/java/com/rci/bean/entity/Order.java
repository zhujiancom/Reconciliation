package com.rci.bean.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.persistence.Transient;

import com.rci.bean.scheme.PairKey;
import com.rci.bean.scheme.SchemeWrapper;
import com.rci.constants.enums.SchemeType;

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
	
	/* 付费编号 */
	private String payNo;
	
	/* 开桌时间  */
	private Date openDeskTime;
	
	/* 结账时间  */
	private Date checkoutTime;
	
	private String day;
	
	/* 订单原价   */
	@Deprecated
	private BigDecimal receivableAmount;
	private BigDecimal originPrice;
	
	/* 订单支付方式 */
//	private List<String> paymodeList = new ArrayList<String>();
	private Map<String,BigDecimal> paymodeMapping = new HashMap<String,BigDecimal>();

	/* 折扣方案  */
	@Deprecated
	private DiscountScheme scheme;  //代金券或者是打折
	private String schemeName;
	
//	/* 临时折扣方案  */
//	private BigDecimal tempDiscountRate; //解决收银员自己手动输入打折率
	
	/* 是否有临时折扣方案  */
	@Deprecated
	private Boolean isTempDiscount;
	/* 是否具有单品折扣  */
	private Boolean singleDiscount;
	
	/* 实收金额   */
	@Deprecated
	private BigDecimal actualAmount;
	private BigDecimal realAmount;
	
	/* 入账金额  */
	private List<PostOrderAccount> postOrderAccounts;
	
	/* 具体菜单明细  */
	private List<OrderItem> items;
	
	/* 记录该订单使用的方案 */
	private Map<PairKey<SchemeType,String>,SchemeWrapper> schemes;
	
	/* 该单有异常 */
	private Integer unusual = 0;
	
	/* 不可打折金额   */
	private BigDecimal nodiscountAmount;

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

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="order_opendesk_time")
	public Date getOpenDeskTime() {
		return openDeskTime;
	}

	public void setOpenDeskTime(Date openDeskTime) {
		this.openDeskTime = openDeskTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="order_checkout_time")
	public Date getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	@Column(name="day")
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	@Column(name="order_receivable_amount")
	public BigDecimal getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(BigDecimal receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	@OneToOne
	@JoinColumn(name="scheme_id")
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

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="order")
	public List<PostOrderAccount> getPostOrderAccounts() {
		return postOrderAccounts;
	}

	public void setPostOrderAccounts(List<PostOrderAccount> postOrderAccounts) {
		this.postOrderAccounts = postOrderAccounts;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="order")
	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

//	@Override
//	public Serializable getId() {
//		return oid;
//	}

	@Override
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name="origin_price")
	public BigDecimal getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(BigDecimal originPrice) {
		this.originPrice = originPrice;
	}

	@Column(name="scheme_name")
	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	@Column(name="single_discount")
	public Boolean getSingleDiscount() {
		return singleDiscount;
	}

	public void setSingleDiscount(Boolean singleDiscount) {
		this.singleDiscount = singleDiscount;
	}

	@Column(name="real_amount")
	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	@Column(name="unusual")
	public Integer getUnusual() {
		return unusual;
	}

	public void setUnusual(Integer unusual) {
		this.unusual = unusual;
	}

	@Column(name="nodiscount_amount")
	public BigDecimal getNodiscountAmount() {
		return nodiscountAmount;
	}

	public void setNodiscountAmount(BigDecimal nodiscountAmount) {
		this.nodiscountAmount = nodiscountAmount;
	}

	@Transient
	public Map<String, BigDecimal> getPaymodeMapping() {
		return paymodeMapping;
	}

	public void setPaymodeMapping(Map<String, BigDecimal> paymodeMapping) {
		this.paymodeMapping = paymodeMapping;
	}


	public void addPayMode(String paymodeNo,BigDecimal amount){
		paymodeMapping.put(paymodeNo, amount);
	}

	@Transient
	public Map<PairKey<SchemeType, String>, SchemeWrapper> getSchemes() {
		return schemes;
	}

	public void setSchemes(Map<PairKey<SchemeType, String>, SchemeWrapper> schemes) {
		this.schemes = schemes;
	}
}
