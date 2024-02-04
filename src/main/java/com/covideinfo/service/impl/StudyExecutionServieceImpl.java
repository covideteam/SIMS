package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.StudyExeCutionDao;
import com.covideinfo.dto.ActivityDto;
import com.covideinfo.dto.ActvitityDetailsDto;
import com.covideinfo.dto.StudyExecutionDto;
import com.covideinfo.dto.VolunteerDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.UserWiseStudiesAsignMaster;
import com.covideinfo.service.StudyExecutionServiece;

@Service("studyExecutionServiece")
public class StudyExecutionServieceImpl implements StudyExecutionServiece {
	
	@Autowired
	StudyExeCutionDao sexeDao;

	@Override
	public List<StudyMaster> getStudyMasterList(Long roleId) {
		List<StudyMaster> smList = new ArrayList<>();
		List<UserWiseStudiesAsignMaster> uwsamList = null;
		List<Long> studyIds = new ArrayList<>();
		try {
			uwsamList = sexeDao.UserWiseStudiesAsignMaster(roleId);
			if(uwsamList != null && uwsamList.size() > 0) {
				for(UserWiseStudiesAsignMaster uwsam : uwsamList) {
					if(uwsam.getStudy() != null) {
						if(!studyIds.contains(uwsam.getStudy().getId())) {
							studyIds.add(uwsam.getStudy().getId());
							smList.add(uwsam.getStudy());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smList;
	}
	
	@Override
	public ActivityDto getActivityDto(Long studyId, Long roleId) {
		ActivityDto acdto = null;
		List<StudyActivities> satList = null;
		List<ActvitityDetailsDto> acdDtoList = new ArrayList<>();
		try {
			satList = sexeDao.getStudyActivityList(studyId);
			if(satList != null && satList.size() > 0) {
				for(StudyActivities sact : satList) {
					List<Long> roleIds = new ArrayList<>();
					String roles = sact.getActivityId().getRoleIds();
					String[] tempArr = roles.split("\\,");
					if(tempArr.length > 0) {
						for(String st : tempArr)
							roleIds.add(Long.parseLong(st));
					}
					if(roleIds.contains(roleId)) {
						ActvitityDetailsDto actd = new ActvitityDetailsDto();
						actd.setStudyActivityId(sact.getId());
						actd.setActivityId(sact.getActivityId().getId());
						actd.setActivityName(sact.getActivityId().getName());
						actd.setGetUrl(sact.getActivityId().getGetUrl());
						actd.setPostUrl(sact.getActivityId().getSaveUrl());
						actd.setRejectUrl(sact.getActivityId().getRejectUrl());
						actd.setShowSubject(sact.getActivityId().isSubjectsInput());
						actd.setOrderNo(sact.getActivityId().getOrderNo());
						acdDtoList.add(actd);
					}
				}
			}
			if(acdDtoList != null && acdDtoList.size() > 0)
				Collections.sort(acdDtoList);
			acdto = new ActivityDto();
			acdto.setActdList(acdDtoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return acdto;
	}
	@Override
	public List<VolunteerDto> getVolunteerList(Long studyId, Long activityId, String param, Long languageId,Boolean isSubjectScanned) {
		List<VolunteerDto> voldtoList = new ArrayList<>();
		StudyExecutionDto sedto = null;
		List<StudyVolunteerReporting> svrList = null;
		List<StudyPeriodMaster> spmList = null;
		List<StudySubjectPeriods> sspList = null;
		List<SubjectRandamization> srzList = null;
		Long treatementOnId = null;
		Map<String, StudyPeriodMaster> periodMap = new HashMap<>();
		Map<Long, StudyPeriodMaster> periodsMap = new HashMap<>();
		Map<String, StudySubjects> subjectMap = new HashMap<>();
		Map<String, Long> subPeriodMap = new HashMap<>();
		Map<String, Long> teatmentMap = new HashMap<>();
		Map<String, StudySubjectPeriods> subjectSpmMap = new HashMap<>();
		try {
			sedto = sexeDao.getVolunteerList(studyId, activityId, param, languageId,isSubjectScanned);
			if(sedto != null) {
				svrList = sedto.getSvrList();
				spmList = sedto.getSpmList();
				sspList = sedto.getSspList();
				srzList = sedto.getSrzList();
				treatementOnId = sedto.getTreatmentOneId();
				
				if(spmList != null && spmList.size() > 0) {
					for(StudyPeriodMaster spm : spmList) {
						periodMap.put(spm.getPeriodName(), spm);
						periodsMap.put(spm.getId(), spm);
					}
				}
				
				if(sspList != null && sspList.size() > 0) {
					for(StudySubjectPeriods ssp : sspList) {
						subPeriodMap.put(ssp.getSubject().getReportingId().getSubjectNo(), ssp.getPeriodId().getId());
						subjectMap.put(ssp.getSubject().getSubjectNo(), ssp.getSubject());
						subjectSpmMap.put(ssp.getSubject().getSubjectNo(), ssp);
						if(ssp.getSubject().getReportingId().getSubjectNo() != null && !ssp.getSubject().getReportingId().getSubjectNo().equals("")) {
							if(!subjectSpmMap.containsKey(ssp.getSubject().getReportingId().getSubjectNo())) {
								subjectSpmMap.put(ssp.getSubject().getReportingId().getSubjectNo(), ssp);
							}
						}
					}
				}
				if(srzList != null && srzList.size() > 0) {
					for(SubjectRandamization srz : srzList) {
						teatmentMap.put(srz.getSubjectNo(), srz.getTreatmentInfo().getId());
					}
				}
				
				List<Long> svrIds = new ArrayList<>();
				List<String> subjNos = new ArrayList<>();
				if(svrList != null && svrList.size() > 0) {
					for(StudyVolunteerReporting svr : svrList) {
						if(!svrIds.contains(svr.getId())) {
							VolunteerDto vdto = new VolunteerDto();
							vdto.setGender(svr.getGenderId().getName());
							vdto.setGenderId(svr.getGenderId().getId());
							if(svr.getSubjectNo() != null && !svr.getSubjectNo().equals("")) {
								if(subjectSpmMap.get(svr.getSubjectNo()) != null && subjectSpmMap.get(svr.getSubjectNo()).getStatus().getStatusCode().equals(StudyStatus.ACTIVE.toString()) && !subjectSpmMap.get(svr.getSubjectNo()).getSubject().getSubjectReplace().equals("Replaced")) {
									if(!subjNos.contains(svr.getSubjectNo())) {
										vdto.setVolId(svr.getId());
										Long periodId = periodMap.get("P1").getId();
										if(subPeriodMap.get(svr.getSubjectNo()) != null)
											periodId = subPeriodMap.get(svr.getSubjectNo());
										vdto.setPeriodId(periodId);
										vdto.setVounteerNo(svr.getVolunteerId()+"("+svr.getSubjectNo()+")");
										vdto.setPeriodNumber(periodsMap.get(periodId).getPeriodName());
										if(teatmentMap.get(svr.getSubjectNo()) != null)
											vdto.setTreatmentId(teatmentMap.get(svr.getSubjectNo()));
										else vdto.setTreatmentId(treatementOnId);
										
										voldtoList.add(vdto);
										svrIds.add(svr.getId());
										subjNos.add(svr.getSubjectNo());
									}
								}
							}else {
								vdto.setVolId(svr.getId());
								Long periodId = periodMap.get("P1").getId();
								vdto.setPeriodId(periodId);
								vdto.setVounteerNo(svr.getVolunteerId());
								vdto.setPeriodNumber(periodsMap.get(periodId).getPeriodName());
								vdto.setTreatmentId(treatementOnId);
								voldtoList.add(vdto);
								svrIds.add(svr.getId());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return voldtoList;
	}

	

	

}
