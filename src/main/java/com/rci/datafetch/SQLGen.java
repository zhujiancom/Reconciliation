package com.rci.datafetch;

public class SQLGen {
	public static final String QUERY_DISH="select ch_dishno,vch_dishname,ch_typeno,num_price1,dt_build from dbo.cybr_bt_dish";
	public static final String QUERY_DISH_By_TYPENO="select ch_dishno,vch_dishname,ch_typeno,num_price1,dt_build from dbo.cybr_bt_dish where ch_typeno=?";
	public static final String QUERY_DISH_TYPE="select ch_typeno,vch_typename from dbo.cybr_bt_dish_type";
	public static final String QUERY_DISH_TYPE_BY_NO="select ch_typeno,vch_typename from dbo.cybr_bt_dish_type where ch_typeno=?";
	public static final String QUERY_DISCOUNT_SCHEME="select distinct master.ch_projectno,master.vch_projectname,detail.int_discount \n"
													+ "from dbo.cybr_bt_project master \n"
													+ "left join \n"
													+ "dbo.cybr_bt_project_detail detail \n"
													+ "on master.ch_projectno = detail.ch_projectno";
	public static final String QUERY_PAYMODE="select ch_paymodeno,vch_paymode from dbo.cybr_bt_paymode where ch_pointflag='N'";
}
