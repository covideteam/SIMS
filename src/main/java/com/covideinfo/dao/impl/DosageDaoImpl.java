package com.covideinfo.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.DosageDao;
import com.covideinfo.model.DosageForm;

@Repository("dosageDao")
public class DosageDaoImpl extends AbstractDao<Long, DosageForm> implements DosageDao {

	@Override
	public boolean saveDosage(DosageForm df, String username) {
		// TODO Auto-generated method stub
		try {
			getSession().save(df);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DosageForm> getDosageIdCheck(String dosid) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(DosageForm.class).add(Restrictions.eq("dosageid", dosid)).list();
	}

	

	
}
