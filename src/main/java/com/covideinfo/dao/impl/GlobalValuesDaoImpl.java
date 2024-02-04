package com.covideinfo.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.GlobalValuesDao;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificValueDetails;

@Repository("globalValuesDao")
public class GlobalValuesDaoImpl extends AbstractDao<Long, GlobalValues> implements GlobalValuesDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<InternationalizaionLanguages> getInternationalizaionLanguages() {
		return getSession().createCriteria(InternationalizaionLanguages.class).list();
	}

	@Override
	public String saveGlobalValesRecords(GlobalValues gv, List<LanguageSpecificValueDetails> lsvdList) {
		String result ="Failed";
		long gvNo =0;
		try {
			gvNo = (long) getSession().save(gv);
			if(gvNo > 0) {
				for(LanguageSpecificValueDetails lsvd : lsvdList) {
					lsvd.setGlobalValId(gv);
					getSession().save(lsvd);
					result = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificValueDetails> getLanguageSpecificValueDetailsList(Long langId) {
		return getSession().createCriteria(LanguageSpecificValueDetails.class)
				.add(Restrictions.eq("inlagId.id", langId)).list();
	}

	@Override
	public GlobalValues getGlobalValuesWithName(String value) {
		GlobalValues gv=null;
		gv=(GlobalValues) getSession().createCriteria(GlobalValues.class)
				.add(Restrictions.eq("name", value)).uniqueResult();
		return gv;
	}

	@Override
	public InternationalizaionLanguages getInternationalizationLanguageRecord(Long langId) {
		return (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
				.add(Restrictions.eq("id", langId)).uniqueResult();
	}

}
