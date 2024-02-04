package com.covideinfo.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.CustomActivityParameterDao;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;

@Repository("customActivityParameterDao")
public class CustomActivityParameterDaoImpl extends AbstractDao<Long, GlobalActivity> implements CustomActivityParameterDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalActivity> getGlobalActivitiesList() {
		return getSession().createCriteria(GlobalActivity.class).list();
	}

	@Override
	public String saveCustomActivityParameterRecord(Long activityId, Long parameterId, String userName,String parameterValue) {
		String result = "Failed";
		GlobalActivity ga = null;
		GlobalParameter gp = null;
		long capNo = 0;
		try {
			ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", activityId)).uniqueResult();
			
			gp = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
					.add(Restrictions.eq("id", parameterId)).uniqueResult();
			CustomActivityParameters cap = new CustomActivityParameters();
			cap.setActivity(ga);
			cap.setParameter(gp);
			cap.setParameterValue(parameterValue);
			cap.setCreatedBy(userName);
			cap.setCreatedOn(new Date());
			capNo = (long) getSession().save(cap);
			if(capNo > 0)
				result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomActivityParameters> getCustomActivityParametersList() {
		return getSession().createCriteria(CustomActivityParameters.class).list();
	}

	@Override
	public GlobalParameter getGlobalParameterWithId(Long id) {
		GlobalParameter pojo=null;
		pojo=(GlobalParameter) getSession().createCriteria(GlobalParameter.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		return pojo;
	}

	@Override
	public CustomActivityParameters checkActivityAndParameterExistingOrNot(GlobalActivity activity,
			GlobalParameter paramPojo) {
		CustomActivityParameters pojoval=null;
		pojoval=(CustomActivityParameters) getSession().createCriteria(CustomActivityParameters.class)
				.add(Restrictions.eq("activity", activity))
				.add(Restrictions.eq("parameter", paramPojo))
				.uniqueResult();
		return pojoval;
	}

}
