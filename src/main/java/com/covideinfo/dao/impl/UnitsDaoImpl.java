package com.covideinfo.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.UnitsDao;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.UnitsMaster;

@Repository("unitsDao")
public class UnitsDaoImpl extends AbstractDao<Long, GlobalActivity> implements UnitsDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<InternationalizaionLanguages> getLanguagesList() {
		return getSession().createCriteria(InternationalizaionLanguages.class).list();
	}

	@Override
	public UnitsMaster checkUnitsName(String name) {
		return (UnitsMaster) getSession().createCriteria(UnitsMaster.class)
				.add(Restrictions.eq("unitsCode", name)).uniqueResult();
	}

	@Override
	public boolean saveUnitDetails(UnitsMaster units) {
		long unitsNo = 0;
		boolean flag = true;
		try {
			unitsNo = (long) getSession().save(units);
			if(unitsNo > 0)
				flag = true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnitsMaster> geUnitsMasterList() {
		List<UnitsMaster> list=null;
		list=getSession().createCriteria(UnitsMaster.class).list();
		return list;
	}

}
