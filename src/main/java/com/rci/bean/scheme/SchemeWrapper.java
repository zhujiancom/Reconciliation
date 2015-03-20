package com.rci.bean.scheme;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rci.bean.entity.Scheme;
import com.rci.constants.enums.SchemeType;
import com.rci.tools.DigitUtil;

public class SchemeWrapper {
	private static final Log logger = LogFactory.getLog(SchemeWrapper.class);
	
	private Scheme scheme;
	
	private Integer count;
	
	private String prefixName;
	
	private BigDecimal totalAmount;
	
	public SchemeWrapper(){}
	
	public SchemeWrapper(String prefixName,BigDecimal totalAmount){
		this.prefixName = prefixName;
		this.totalAmount = totalAmount;
	}
	
	public SchemeWrapper(Scheme s){
		this.scheme = s;
		this.prefixName = "";
		this.count = 0;
	}
	
	public SchemeWrapper(String prefixName,Scheme s){
		this(s);
		this.prefixName = prefixName;
	}
	
	public SchemeWrapper(String pn,Scheme s,Integer c){
		this(pn,s);
		this.count = c;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getPrefixName() {
		return prefixName;
	}

	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}

	public String getName() {
		StringBuffer name = new StringBuffer(this.prefixName);
		SchemeType stype = scheme.getType();
		if(SchemeType.EIGHTDISCOUNT.equals(stype) || SchemeType.NODISCOUNT.equals(stype)){
			name.append(scheme.getName()).append(totalAmount);
		}else{
			name.append(scheme.getName()).append("【").append(+this.count).append("】张");
		}
		
		return name.toString();
	}
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void add(Integer amount){
		this.count += amount;
	}
	
	public BigDecimal calculatePostAmount(){
		BigDecimal postAmount = new BigDecimal(0);
		if(scheme == null){
			logger.debug("---  scheme is not exist --- prefix name is "+prefixName);
			return null;
		}else{
			SchemeType stype = scheme.getType();
			if(stype.equals(SchemeType.VIRTUALPAY) || stype.equals(SchemeType.EIGHTDISCOUNT) || stype.equals(SchemeType.NODISCOUNT)){
				//1.如果是现金支付,饿了么，淘点点支付
				postAmount = totalAmount;
			}else{
				BigDecimal rate = BigDecimal.ONE.subtract(DigitUtil.precentDown(scheme.getCommission(), new BigDecimal(100)));
				postAmount = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(scheme.getPostPrice(), rate).add(scheme.getSpread()),new BigDecimal(count));
			}
		}
		return postAmount;
	}
	
//	public static void main(String[] args){
//		StringBuffer sb = new StringBuffer("");
//		sb.append("a");
//		System.out.println(sb.toString());
//	}
	
}
