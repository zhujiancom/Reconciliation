package com.rci.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
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
	public void rwCreateAccout(Account[] accs) {
		super.rwCreate(accs);
	}
	
	@Override
	public Account getAccountByNo(String no){
		DetachedCriteria dc = DetachedCriteria.forClass(Account.class);
		dc.add(Restrictions.eq("accNo", no));
		Account account = baseDAO.queryUniqueByCriteria(dc);
		return account;
	}
	
	@Override
	public Account getPOSAccount() {
		return getAccountByNo("SYJ");
	}

	@Override
	public Account getDPTGAccount() {
		return getAccountByNo("DPTG");
	}

	@Override
	public Account getDPSHAccount() {
		return getAccountByNo("DPSH");
	}

	@Override
	public Account getMTAccount() {
		return getAccountByNo("MT");
	}

	@Override
	public Account getELEAccount() {
		return getAccountByNo("ELE");
	}

}
