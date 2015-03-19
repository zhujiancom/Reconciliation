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
	
	private String name;
	
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
		return name;
	}

	public void setName(String name) {
		this.name = this.prefixName;
		if(scheme != null){
			this.name = this.name+scheme.getName()+"【"+this.count+"】"+"张";
		}
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
			if(stype.equals(SchemeType.EIGHTDISCOUNT) || stype.equals(SchemeType.NODISCOUNT)){
				//1.如果是现金支付
				postAmount = totalAmount;
				return postAmount;
			}
			BigDecimal rate = BigDecimal.ONE.subtract(DigitUtil.precentDown(scheme.getCommission(), new BigDecimal(100)));
			postAmount = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(scheme.getPostPrice(), rate).add(scheme.getSpread()),new BigDecimal(count));
		}
		return postAmount;
	}
	
	/*public static void main(String[] args){
		Map<PairKey<SchemeType,String>,SchemeWrapper> map = new HashMap<PairKey<SchemeType,String>,SchemeWrapper>();
		PairKey<SchemeType,String> p1 = new PairKey<SchemeType,String>(SchemeType.FIFITY,"98");
		PairKey<SchemeType,String> p2 = new PairKey<SchemeType,String>(SchemeType.FIFITY,"99");
		
		Scheme s = new Scheme();
		s.setType(SchemeType.FIFITY);
		s.setName("50代金券");
		s.setCommission(new BigDecimal(1));
		s.setPrice(new BigDecimal(50));
		s.setPostPrice(new BigDecimal(37.5));
		
		SchemeWrapper w1 = new SchemeWrapper("大众点评",s,3);
		map.put(p1, w1);
		SchemeWrapper w2 = new SchemeWrapper("美团",s,3);
		map.put(p2, w2);
		
		Map<PairKey<SchemeType,String>,SchemeWrapper> map2 = new HashMap<PairKey<SchemeType,String>,SchemeWrapper>();
		PairKey<SchemeType,String> p3 = new PairKey<SchemeType,String>(SchemeType.CASH,"00");
		Scheme s1 = new Scheme();
		s1.setType(SchemeType.CASH);
		s1.setName("现金");
		
		
	}*/
	
}
