package com.covideinfo.dao.impl;

import java.util.List;
import java.util.Locale;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.ConditionDao;
import com.covideinfo.model.ConditionParameter;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificCondition;

@Repository("conditionDao")
public class ConditionDaoImpl extends AbstractDao<Long, ConditionParameter> implements ConditionDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificCondition> getLanguageSpecificSubjectWithdrawActivityList() {
		 List<LanguageSpecificCondition> list=null;
		 list=getSession().createCriteria(LanguageSpecificCondition.class).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InternationalizaionLanguages> getInternationalizaionLanguages() {
		List<InternationalizaionLanguages> list=null;
		 list=getSession().createCriteria(InternationalizaionLanguages.class).list();
		return list;
	}

	@Override
	public String saveSubjectWithdrawActivity(ConditionParameter swa,
			List<LanguageSpecificCondition> lsvdList) {
		String result ="Failed";
		long swaNo =0;
		try {
			swaNo = (long) getSession().save(swa);
			if(swaNo > 0) {
				for(LanguageSpecificCondition lsvd : lsvdList) {
					lsvd.setSujectWithdrawActivity(swa);
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
	public List<LanguageSpecificCondition> getLanguageSpecificConditions(Locale currentLocale) {
		 List<LanguageSpecificCondition> lagscList = null;
	        InternationalizaionLanguages inalg = null;
	        try {
	            inalg = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
	                    .add(Restrictions.eq("countryCode", currentLocale.getCountry())).uniqueResult();
	            if(inalg != null) {
	                lagscList = getSession().createCriteria(LanguageSpecificCondition.class)
	                        .add(Restrictions.eq("inlagId", inalg)).list();
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return lagscList;
	}

	@Override
	public ConditionParameter getConditionParameterWithId(long id) {
		ConditionParameter cp=null;
		cp=(ConditionParameter) getSession().createCriteria(ConditionParameter.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		return cp;
	}

	@Override
	public boolean updateStatus(ConditionParameter cp) {
		boolean flag=false;
		try {
			getSession().update(cp);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public InternationalizaionLanguages getInternationalizaionLanguageRecord(Long langId) {
		return (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
				.add(Restrictions.eq("id", langId)).uniqueResult();
	}

	@Override
	public ConditionParameter conditionNameCheckExitOrNot(String value) {
		return (ConditionParameter) getSession().createCriteria(ConditionParameter.class)
				.add(Restrictions.eq("name", value)).uniqueResult();
	}
}
