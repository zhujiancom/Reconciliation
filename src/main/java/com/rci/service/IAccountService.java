package com.rci.service;

import com.rci.bean.entity.account.Account;

public interface IAccountService {
	public void rwCreateAccout(Account acc);
	
	public Account getPOSAccount();
	
	public Account getDPTGAccount();
	
	public Account getDPSHAccount();
	
	public Account getMTAccount();
	
	public Account getELEAccount();
}
