package com.rci.constants;

import com.rci.config.PropertyConstants;
import com.rci.tools.properties.PropertyUtils;

public class BusinessConstant {
	public static final String CASH_NO=(String) PropertyUtils.getProperties(PropertyConstants.CASH);
	public static final String DPTG_NO=(String) PropertyUtils.getProperties(PropertyConstants.DPTG);
	public static final String DPSH_NO=(String) PropertyUtils.getProperties(PropertyConstants.DPSH);
	public static final String MT_NO=(String) PropertyUtils.getProperties(PropertyConstants.MT);
	public static final String ELE_NO=(String) PropertyUtils.getProperties(PropertyConstants.ELE);
	public static final String TDD_NO=(String) PropertyUtils.getProperties(PropertyConstants.TDD);
	public static final String MTWM_NO=(String) PropertyUtils.getProperties(PropertyConstants.MTWM);
	public static final String LS_NO=(String) PropertyUtils.getProperties(PropertyConstants.LS);
	public static final String FREE_NO=(String) PropertyUtils.getProperties(PropertyConstants.FREE);
	
	public static final String BIGSUITA=(String) PropertyUtils.getProperties(PropertyConstants.BIGSUIT_A);
	public static final String BIGSUITB=(String) PropertyUtils.getProperties(PropertyConstants.BIGSUIT_B);
	public static final String SMALLSUIT=(String) PropertyUtils.getProperties(PropertyConstants.SMALLSUIT);
	public static final String SUITA=(String) PropertyUtils.getProperties(PropertyConstants.SUITA);
	public static final String SUITB=(String) PropertyUtils.getProperties(PropertyConstants.SUITB);
	public static final String SUITC=(String) PropertyUtils.getProperties(PropertyConstants.SUITC);
	
}
