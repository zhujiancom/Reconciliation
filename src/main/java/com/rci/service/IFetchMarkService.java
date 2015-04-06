package com.rci.service;

import com.rci.bean.entity.DataFetchMark;

public interface IFetchMarkService {
	public DataFetchMark getMarkRecordByDay(String day);
	
	public boolean isSystemInit();
	
	public void rwSystemInit();
	
	public void rwOrderMark(String day);
	
	public void rwUpdateMark(DataFetchMark mark);
	
	public void rwDeleteMark(String day);
}
