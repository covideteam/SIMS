package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covide.dto.UserStudyActivityDto;
import com.covide.service.StudyService;
import com.covideinfo.dao.impl.UserWiseProjectsImpl;
import com.covideinfo.dto.ProjectStudyActivitiesDto;
import com.covideinfo.dto.StudyDetailsDto;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserWiseStudiesAsignMaster;

@Service("userWiseProjectsServiceImpl")
public class UserWiseProjectsServiceImpl {
	
	@Autowired
	UserWiseProjectsImpl uwspldDaoImpl;
	
	@Autowired
	StudyService studyService;

	public List<StudyDetailsDto> getUserProjectsDetails(Long userId, Long languageId, Long roleId) {
		List<StudyDetailsDto> stdDtoList = new ArrayList<>();
		List<UserWiseStudiesAsignMaster> uwsaList = null;
		List<StudyMaster> smList = new ArrayList<>();
		List<Long> studyIds = new ArrayList<>();
		List<StudyActivities> stdActList = null;
		Map<Long,LanguageSpecificGlobalActivityDetails> globalActivityMap = new HashMap<>();
		Map<Long,List<StudyActivities>> studyActivitiesMap =new HashMap<>();
		UserStudyActivityDto userStudyActivityDto = studyService.getUserStudiesWithActivities(userId, languageId);
		
		try {
			uwsaList = userStudyActivityDto.getUserStudies();
			studyActivitiesMap = userStudyActivityDto.getStudyActivities();
			globalActivityMap = userStudyActivityDto.getGobalActivities();
			if(uwsaList != null && uwsaList.size() > 0) {
				for(UserWiseStudiesAsignMaster uwsam : uwsaList) {
					if(uwsam.getStudy() != null) {
						if(uwsam.getStudy().getStudyStatus() != null) {
							if(!uwsam.getStudy().getStudyStatus().getStatusCode().equalsIgnoreCase("COMPLETED")) {
								smList.add(uwsam.getStudy());
								studyIds.add(uwsam.getStudy().getId());
							}
						}
					}
				}
			}
			if(smList.size() > 0) {
				for(StudyMaster sm : smList) {
					stdActList = studyActivitiesMap.get(sm.getId());
					if(stdActList != null && stdActList.size() > 0) {
						StudyDetailsDto sdto = new StudyDetailsDto();
						sdto.setStudyId(sm.getId());
						sdto.setStudyName(sm.getProjectNo());
						List<ProjectStudyActivitiesDto> pdDtoList = new ArrayList<>();
						for(StudyActivities sa : stdActList) {
							List<Long> roleIds = new ArrayList<>();
							String roles = sa.getActivityId().getRoleIds();
							String[] tempArr = roles.split("\\,");
							if(tempArr.length > 0) {
								for(String st : tempArr)
									roleIds.add(Long.parseLong(st));
							}
							if(roleIds.contains(roleId)) {
								ProjectStudyActivitiesDto psdto = new ProjectStudyActivitiesDto();
								psdto.setActivityId(sa.getActivityId().getId());
								
								LanguageSpecificGlobalActivityDetails lsga = globalActivityMap.get(sa.getActivityId().getId());
								if(lsga != null)
									psdto.setActivityName(lsga.getName());
								else psdto.setActivityName(sa.getActivityId().getName());
								psdto.setStudyActiviyId(sa.getId());
								pdDtoList.add(psdto);
								
							}
						}
						sdto.setPwsactList(pdDtoList);
						stdDtoList.add(sdto);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return stdDtoList;
	}
}
