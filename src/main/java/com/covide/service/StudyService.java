package com.covide.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covide.dto.UserStudyActivityDto;
import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserWiseStudiesAsignMaster;

@Repository("StudyService")
public class StudyService extends AbstractDao<Long, StudyMaster>{
	
	
	@SuppressWarnings("unchecked")
	public UserStudyActivityDto getUserStudiesWithActivities(Long userId,Long languageId) {
		UserStudyActivityDto userStudyActivityDto = new UserStudyActivityDto(); 
		Map<Long,List<StudyActivities>> studyList = new HashMap<>();
		Map<Long,List<Long>> studyListIds = new HashMap<>();
		List<Long> saIds = new ArrayList<>();
		
		List<UserWiseStudiesAsignMaster> userStudies =	getSession().createCriteria(UserWiseStudiesAsignMaster.class)
				.add(Restrictions.eq("userId.id", userId))
				.add(Restrictions.eq("status", true)).list();
		
		userStudyActivityDto.setUserStudies(userStudies);
		
		List<Long> studyIds = new ArrayList<>();
		for(UserWiseStudiesAsignMaster userStudy: userStudies) {
			studyIds.add(userStudy.getStudy().getId());
		}
		
		List<StudyActivities> studyActivities = new ArrayList<>();
		if(studyIds.size() > 0) {
			studyActivities = getSession().createCriteria(StudyActivities.class)
				.add(Restrictions.in("sm.id", studyIds)).list();
		}
		
		if(studyActivities.size() > 0) {
			for(StudyActivities sa : studyActivities) {
				if(studyList.containsKey(sa.getSm().getId())) {
					if(!saIds.contains(sa.getActivityId().getId())) {
						saIds.add(sa.getActivityId().getId());
					}
					List<StudyActivities> studyActivityList = studyList.get(sa.getSm().getId()); 
					List<Long> studyActivityListIds = studyListIds.get(sa.getSm().getId());
					if(!studyActivityListIds.contains(sa.getActivityId().getId())) {
						studyActivityListIds.add(sa.getActivityId().getId());
						studyListIds.put(sa.getSm().getId(), studyActivityListIds);
						
						studyActivityList.add(sa);
						studyList.put(sa.getSm().getId(), studyActivityList);
					}
				}
				else {
					if(!saIds.contains(sa.getActivityId().getId())) {
						saIds.add(sa.getActivityId().getId());
					}
					List<StudyActivities> studyActivityList = new ArrayList<>(); 
					studyActivityList.add(sa);
					studyList.put(sa.getSm().getId(), studyActivityList);
					
					List<Long> studyActivityListIds = new ArrayList<>();
					studyActivityListIds.add(sa.getActivityId().getId());
					studyListIds.put(sa.getSm().getId(), studyActivityListIds);
				}
			}
		}
		
		userStudyActivityDto.setStudyActivities(studyList);
		
		if(saIds.size() > 0) {
			List<LanguageSpecificGlobalActivityDetails> gobalActivities = getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
												.add(Restrictions.in("globalActivity.id", saIds))
												.add(Restrictions.eq("inlagId.id", languageId)).list();
			
			Map<Long,LanguageSpecificGlobalActivityDetails> globalActivityMap = new HashMap<>();
			for(LanguageSpecificGlobalActivityDetails globalActivity : gobalActivities) {
				globalActivityMap.put(globalActivity.getId(), globalActivity);
			}
			userStudyActivityDto.setGobalActivities(globalActivityMap);
		}
		
		return userStudyActivityDto;
	}
	
	
	
}
