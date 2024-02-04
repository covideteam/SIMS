package com.covideinfo.service.impl;

import java.util.List;
import java.util.Locale;

import org.hibernate.criterion.Restrictions;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.model.InternationalizaionLanguages;

@Repository("LanguageService")
public class LanguageService extends AbstractDao<Long, InternationalizaionLanguages> {
	
	@SuppressWarnings("unchecked")
	public List<InternationalizaionLanguages> getLanguages()
	{
		return (List<InternationalizaionLanguages>)getSession().createCriteria(InternationalizaionLanguages.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public InternationalizaionLanguages getLanguage(Locale locale)
	{
		return (InternationalizaionLanguages)getSession()
				.createCriteria(InternationalizaionLanguages.class)
				.add(Restrictions.eq("countryCode", locale.getCountry())).uniqueResult();
	}
}
