package com.rci.datafetch;

public class SQLGen {
	public static final String QUERY_DISH="select ch_dishno,vch_dishname,ch_typeno,num_price1 from dbo.v_bt_dish";
	public static final String QUERY_DISH_By_TYPENO="select ch_dishno,vch_dishname,ch_typeno,num_price1 from dbo.v_bt_dish where ch_typeno=?";
	public static final String QUERY_DISH_TYPE="select ch_typeno,vch_typename from dbo.cybr_bt_dish_type";
	public static final String QUERY_DISH_TYPE_BY_NO="select ch_typeno,vch_typename from dbo.cybr_bt_dish_type where ch_typeno=?";
	public static final String QUERY_DISCOUNT_SCHEME="select distinct master.ch_projectno,master.vch_projectname,detail.int_discount \n"
													+ "from dbo.cybr_bt_project master \n"
													+ "left join \n"
													+ "dbo.cybr_bt_project_detail detail \n"
													+ "on master.ch_projectno = detail.ch_projectno";
	public static final String QUERY_PAYMODE="select ch_paymodeno,vch_paymode from dbo.v_bt_paymode where ch_incomeflag='N'";
	
	/* 查询order detail 信息*/
	public static final String QUERY_ORDERITEM="select ord.ch_billno 'billno',ord.ch_payno 'payno',ord.ch_dishno 'dishno',ord.ch_suitflag 'suitflag',ord.ch_suitno 'suitno',ord.num_num count,\n"
												+ "ord.num_back countback,ord.num_price 'price',ord.int_discount 'discount',ord.dt_operdate 'consumeTime' from dbo.v_u_orderdish ord \n"
												+ "where ord.ch_billno=?";
	/* 查询order 信息*/
//	public static final String QUERY_ORDER="select tab.ch_billno 'billno',tab.ch_payno 'payno',detail.ch_paymodeno 'paymode', \n"
//											+"cmaster.num_cost 'originamount',tab.dt_service_begin 'opendesktime',\n"
//											+ "cmaster.dt_operdate 'checkouttime',detail.num_realamount 'realamount', \n"
//											+ "master.vch_disproject 'project' \n"
//											+ "from dbo.v_u_table tab \n"
//											+ "join dbo.v_u_checkout_master cmaster \n"
//											+ "on tab.ch_billno=cmaster.ch_billno \n"
//											+ "join dbo.v_u_checkout_detail detail \n"
//											+ "on cmaster.ch_payno = detail.ch_payno \n"
//											+ "join dbo.v_u_master master \n"
//											+ "on master.ch_billno=cmaster.ch_billno \n"
//											+ "where cmaster.dt_operdate between ? and ?";
	
	public static final String QUERY_ORDER="select tab.ch_billno 'billno',tab.ch_payno 'payno',detail.ch_paymodeno 'paymode', \n"
			+"cmaster.num_cost 'originamount',tab.dt_service_begin 'opendesktime',\n"
			+ "cmaster.dt_operdate 'checkouttime',detail.num_realamount 'realamount', \n"
			+ "from dbo.v_u_table tab \n"
			+ "join dbo.v_u_checkout_master cmaster \n"
			+ "on tab.ch_billno=cmaster.ch_billno \n"
			+ "join dbo.v_u_checkout_detail detail \n"
			+ "on cmaster.ch_payno = detail.ch_payno \n"
			+ "where cmaster.dt_operdate between ? and ?";
	
	public static final String QUERY_SCHEME_DISHTYPE_REF="select ch_typeno from dbo.cybr_bt_project_detail where ch_projectno=?";
	
	
}
