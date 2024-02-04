package com.covideinfo.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.InternationalizationDao;
import com.covideinfo.model.InternationalizaionLanguages;

@Repository("internationalizationDao")
public class InternationalizationDaoImpl extends AbstractDao<Long, InternationalizaionLanguages> implements InternationalizationDao {

	@Override
	public InternationalizaionLanguages getInternationalizaionLanguageRecord(String langCode) {
		return (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
				.add(Restrictions.eq("langCode", langCode)).uniqueResult();
	}

	
	
}
