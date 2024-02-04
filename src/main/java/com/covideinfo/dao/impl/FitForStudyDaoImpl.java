package com.covideinfo.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.FitForStudyDao;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;

@Repository("fitForStudyDao")
public class FitForStudyDaoImpl extends AbstractDao<Long, StudySubjects> implements FitForStudyDao {

	@Override
	public CustomActivityParameters getCustomActivityParametersRecord(Long activityId) {
		return (CustomActivityParameters) getSession().createCriteria(CustomActivityParameters.class)
				.add(Restrictions.eq("activity.id", activityId)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalParameter> getGlobalparametersList(List<Long> parmIdsList) {
		return getSession().createCriteria(GlobalParameter.class)
				.add(Restrictions.in("id", parmIdsList)).list();
	}

	@Override
	public String inactiveVolunteerRecord(Long volId) {
		String result = "Failed";
		StudyVolunteerReporting svr = null;
		StatusMaster status = null;
		try {
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.INACTIVE.toString())).uniqueResult();
			
			svr = (StudyVolunteerReporting) getSession().createCriteria(StudyVolunteerReporting.class)
					.add(Restrictions.eq("id", volId)).uniqueResult();
			if(svr != null) {
				svr.setStatus(status);
				getSession().merge(svr);
				result = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	
	

}
