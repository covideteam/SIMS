package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.UserWiseStudiesAsignMaster;

@Repository("userWiseProjectsImpl")
public class UserWiseProjectsImpl extends AbstractDao<Long, UserMaster> {

	@SuppressWarnings("unchecked")
	public List<UserWiseStudiesAsignMaster> getUserWiseStudiesList(Long userId) {
		return getSession().createCriteria(UserWiseStudiesAsignMaster.class)
				.add(Restrictions.eq("userId.id", userId))
				.add(Restrictions.eq("status", true)).list();
	}

	@SuppressWarnings("unchecked")
	public List<StudyActivities> getStudyActivitiesList(StudyMaster study) {
		List<Long> saIds = new ArrayList<>();
		List<StudyActivities> finalstudyActivities  = new ArrayList<>();
		List<StudyActivities> studyActivities  = getSession().createCriteria(StudyActivities.class)
				.add(Restrictions.eq("sm", study)).list();
		
		if(studyActivities.size() > 0) {
			for(StudyActivities sa : studyActivities) {
				if(sa.getActivityId() != null) {
					if(!saIds.contains(sa.getActivityId().getId())) {
						saIds.add(sa.getActivityId().getId());
						finalstudyActivities.add(sa);
					}
				}
			}
		}
		return finalstudyActivities;
	}

	public LanguageSpecificGlobalActivityDetails getLanguageSpecificGlobalActivityDetailsRecord(Long languageId, GlobalActivity globalActivity) {
		return (LanguageSpecificGlobalActivityDetails) getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
				.add(Restrictions.eq("globalActivity", globalActivity))
				.add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
	}
	

}
