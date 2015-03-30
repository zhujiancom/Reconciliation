package com.rci.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rci.bean.entity.DataFetchMark;
import com.rci.constants.enums.MarkType;
import com.rci.service.BaseService;
import com.rci.service.IFetchMarkService;
import com.rci.tools.DateUtil;

@Service("FetchMarkService")
public class FetchMarkServiceImpl extends BaseService<DataFetchMark, Long>
		implements IFetchMarkService {

	@Override
	public DataFetchMark getMarkRecordByDay(String day) {
		DetachedCriteria dc = DetachedCriteria.forClass(DataFetchMark.class);
		dc.add(Restrictions.eq("rciDate", day)).add(Restrictions.eq("type", MarkType.ORDER_FETCH));
		DataFetchMark mark = baseDAO.queryUniqueByCriteria(dc);
		return mark;
	}

	@Override
	public boolean isSystemInit() {
		DetachedCriteria dc = DetachedCriteria.forClass(DataFetchMark.class);
		dc.add(Restrictions.eq("type", MarkType.SYSTEM_INIT));
		DataFetchMark mark = baseDAO.queryUniqueByCriteria(dc);
		if(mark == null){
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void rwSystemInit() {
		DataFetchMark mark = new DataFetchMark(MarkType.SYSTEM_INIT);
		mark.setMark(1);
		baseDAO.save(mark);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void rwOrderMark(String day) {
		DataFetchMark mark = new DataFetchMark(MarkType.ORDER_FETCH);
		mark.setRciDate(day);
		mark.setMark(1);
		mark.setSavepoint(DateUtil.getCurrentDate());
		baseDAO.save(mark);
	}

}
