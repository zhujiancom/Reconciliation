package com.rci.service.impl;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.account.Account;
import com.rci.service.BaseService;
import com.rci.service.IAccountService;

@Service("AccountService")
public class AccountServiceImpl extends BaseService<Account, Long>  implements IAccountService {
	@Override
	public void rwCreateAccout(Account acc) {
		super.rwCreate(acc);
	}
	
	@Override
	public Account getPOSAccount() {
		
		return null;
	}

	@Override
	public Account getDPTGAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getDPSHAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getMTAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getELEAccount() {
		// TODO Auto-generated method stub
		return null;
	}

}
