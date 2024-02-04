package com.covideinfo.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.StudyActivityDao;
import com.covideinfo.dto.DefaulltActparamDto;
import com.covideinfo.dto.FormGroupsDto;
import com.covideinfo.dto.FromControlDto;
import com.covideinfo.dto.FromValuesDto;
import com.covideinfo.dto.GlobalParameterDetailsDto;
import com.covideinfo.dto.GlobalparameterFromDto;
import com.covideinfo.dto.MessageDto;
import com.covideinfo.dto.ParameterFormDataDto;
import com.covideinfo.dto.ParameterModelDto;
import com.covideinfo.dto.StudyActivityDto;
import com.covideinfo.dto.StudyActivityFormDataDto;
import com.covideinfo.dto.StudyActivitySavingDto;
import com.covideinfo.dto.StudyDesingDto;
import com.covideinfo.enums.ParameterCodes;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.DefaultActivityParameters;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyActivityRules;
import com.covideinfo.model.StudyActivityTimePoints;
import com.covideinfo.model.StudyActivityTimePointsCompletionData;
import com.covideinfo.model.StudyCheckInActivityDataDetails;
import com.covideinfo.model.StudyCheckOutActivityDataDetails;
import com.covideinfo.model.StudyExecutionActivityDataDetails;
import com.covideinfo.model.StudyGroup;
import com.covideinfo.model.StudyGroupPeriodMaster;
import com.covideinfo.model.StudyGroups;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.SubjectActivityData;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectWithdrawDetails;
import com.covideinfo.model.TreatmentInfo;

@Repository("studyActivityDao")
public class StudyActivityDaoImpl extends AbstractDao<Long, GlobalParameter> implements StudyActivityDao {

	@SuppressWarnings("unchecked")
	@Override
	public StudyActivityDto getStudyActivityDtoDetails(Long activityId, Long languageId, Long studyId) {
		StudyActivityDto saDto = null;
		GlobalActivity  activity = null;
		LanguageSpecificGlobalActivityDetails lsadPojo = null;
		List<StudyActivities> saList = null;
		Map<Long, List<StudyActivityParameters>> sapMap = new HashMap<>();
		Map<Long, LanguageSpecificGlobalParameterDetails> gpMap = new HashMap<>();
		Map<Long, List<LanguageSpecificGroupDetails>> lspgMap = new HashMap<>();
		InternationalizaionLanguages inalg = null;
		CustomActivityParameters custmActParm = null;
		Map<Long,List<LanguageSpecificValueDetails>> parameterValueList = new HashMap<>();
		List<LanguageSpecificGroupDetails> languageGroups = new ArrayList<>();
		List<LanguageSpecificValueDetails> lsvdList = new ArrayList<>();
		try
		{
			inalg = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
					.add(Restrictions.eq("id", languageId)).uniqueResult();
			
			
			activity = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", activityId)).uniqueResult();
			
			custmActParm = (CustomActivityParameters) getSession().createCriteria(CustomActivityParameters.class)
					.add(Restrictions.eq("activity", activity)).uniqueResult();
			
			saList = getSession().createCriteria(StudyActivities.class)
					.add(Restrictions.eq("activityId", activity))
					.add(Restrictions.eq("sm.id", studyId)).list();
			
			if(saList.size() > 0) {
				for(StudyActivities sa : saList) {
					List<StudyActivityParameters> sapList = getSession().createCriteria(StudyActivityParameters.class)
							.add(Restrictions.eq("studyActivity", sa)).list();
					sapMap.put(sa.getId(), sapList);
				}
			}
			lsadPojo = (LanguageSpecificGlobalActivityDetails) getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
					.add(Restrictions.eq("globalActivity", activity))
					.add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
			
			if(sapMap.size() > 0) {
				List<Long> parameters = new ArrayList<>();
				List<Long> groups = new ArrayList<>();
				for(Map.Entry<Long, List<StudyActivityParameters>> sap : sapMap.entrySet()) {
					List<StudyActivityParameters> sapList = sap.getValue();
					if(sapList != null && sapList.size() > 0) {
						for(StudyActivityParameters gp : sapList) {
							parameters.add(gp.getParameterId().getId());
							if(gp.getParameterId().getGroups() != null) {
								groups.add(gp.getParameterId().getGroups().getId());
							}
						}
					}
				}
				
				List<ParameterControlTypeValues> parameterValues = getSession().createCriteria(ParameterControlTypeValues.class)
						.add(Restrictions.in("globalParameter.id", parameters)).list();
				
				List<Long> gvIds = new ArrayList<>();
				Map<Long,List<Long>> paramValues = new HashMap<>();
				
				for(ParameterControlTypeValues pv  : parameterValues) {
					gvIds.add(pv.getGlobalValues().getId());
					if(paramValues.containsKey(pv.getGlobalParameter().getId())) {
						List<Long> pvs = paramValues.get(pv.getGlobalParameter().getId());
						pvs.add(pv.getGlobalValues().getId());
						paramValues.put(pv.getGlobalParameter().getId(), pvs);
					}
					else {
						List<Long> pvs = new ArrayList<>();
						pvs.add(pv.getGlobalValues().getId());
						paramValues.put(pv.getGlobalParameter().getId(), pvs);
					}
				}
				
				List<LanguageSpecificGlobalParameterDetails> lsgp = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
						.add(Restrictions.in("parameterId.id", parameters))
						.add(Restrictions.eq("inlagId.id", languageId)).list();
				
				if(groups!=null && groups.size() >0) {
					languageGroups = getSession().createCriteria(LanguageSpecificGroupDetails.class)
							.add(Restrictions.in("globlgroupId.id", groups))
							.add(Restrictions.eq("inlagId.id", languageId)).list();	
				}
				
				if(gvIds!=null && gvIds.size() >0) {
					lsvdList = getSession().createCriteria(LanguageSpecificValueDetails.class)
							.add(Restrictions.in("globalValId.id", gvIds))
							.add(Restrictions.eq("inlagId.id", languageId)).list();
				}
				
				for(Entry<Long,List<Long>> paramValue :paramValues.entrySet()) {
					for(Long value : paramValue.getValue()) {
						for(LanguageSpecificValueDetails valueParam : lsvdList) {
							if(value.equals(valueParam.getGlobalValId().getId())) {
								if(parameterValueList.containsKey(paramValue.getKey())) {
									List<LanguageSpecificValueDetails> lsvd = parameterValueList.get(paramValue.getKey());
									lsvd.add(valueParam);
									parameterValueList.put(paramValue.getKey(), lsvd);
								}
								else {
									List<LanguageSpecificValueDetails> lsvd = new ArrayList<>();
									lsvd.add(valueParam);
									parameterValueList.put(paramValue.getKey(), lsvd);	
								}
								break;
							}
						}
					}
				}
				
				for(LanguageSpecificGlobalParameterDetails lsgpd : lsgp) {
					gpMap.put(lsgpd.getParameterId().getId(), lsgpd);
					if(lsgpd.getParameterId().getGroups() != null) {
						for(LanguageSpecificGroupDetails lg : languageGroups) {
							if(lg.getGloblgroupId().equals(lsgpd.getParameterId().getGroups())) {
								List<LanguageSpecificGroupDetails> lsgpList = null;
								if(lspgMap.containsKey(lsgpd.getParameterId().getId())) {
									lsgpList = lspgMap.get(lsgpd.getParameterId().getId());
									lsgpList.add(lg);
									lspgMap.put(lsgpd.getParameterId().getId(), lsgpList);
								}else {
									lsgpList = new ArrayList<>();
									lsgpList.add(lg);
									lspgMap.put(lsgpd.getParameterId().getId(), lsgpList);
								}
								break;
							}
						}	
					}
				}
			}
			
			saDto = new StudyActivityDto();
			saDto.setLanguageValues(parameterValueList);
			saDto.setLanSpecificAcitvity(lsadPojo);
			saDto.setGpMap(gpMap);
			saDto.setLspgMap(lspgMap);
			saDto.setSapMap(sapMap);
			saDto.setSaList(saList);
			saDto.setInlg(inalg);
			saDto.setCustmActParm(custmActParm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}

	@Override
	public boolean saveStudyActivities(StudyActivities sa, List<StudyActivityParameters> saveList) {
		boolean flag = false;
		long saNo = 0;
		boolean sapFlag = false;
		try {
			saNo = (long) getSession().save(sa);
			if(saNo > 0) {
				for(StudyActivityParameters sap : saveList) {
					sap.setStudyActivity(sa);
					getSession().save(sap);
					sapFlag = true;
				}
			}
			if(saNo > 0 && sapFlag)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public StudyActivityFormDataDto getStudyActivityFormDataDetails(Long activityId,Long studyActivityId, Long studyId, Long volId, String type, List<Long> pIds) {
		StudyActivityFormDataDto  safDto = null;
		List<StudyActivityParameters> sapList = null;
		StudyActivities stdActivity = null;
		List<GlobalParameter> gpList = new ArrayList<>();
		List<ParameterControlTypeValues> pctvList = null;
		StudyMaster sm = null;
		StudyPeriodMaster spm = null;
		StudyVolunteerReporting svr = null;
		StudySubjects subject = null;
		StatusMaster activeStatus = null;
		StudyGroupPeriodMaster sgpm = null;
		TreatmentInfo treatment = null;
		try {
			activeStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", "ACTIVE")).uniqueResult();
			
			sm = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					.add(Restrictions.eq("id", studyId)).uniqueResult();
			
			pctvList = getSession().createCriteria(ParameterControlTypeValues.class)
					.add(Restrictions.in("globalParameter.id", pIds)).list();
			
			svr = (StudyVolunteerReporting) getSession().createCriteria(StudyVolunteerReporting.class)
					.add(Restrictions.eq("id", volId)).uniqueResult();
			
			if(svr != null) {
				subject = (StudySubjects) getSession().createCriteria(StudySubjects.class)
						.add(Restrictions.eq("study.id", studyId))
						.add(Restrictions.eq("reportingId", svr)).uniqueResult();
				if(subject != null) {
					spm = (StudyPeriodMaster) getSession().createCriteria(StudySubjectPeriods.class)
							.add(Restrictions.eq("subject", subject))
							.add(Restrictions.eq("status", activeStatus))
							.setProjection(Projections.property("periodId")).uniqueResult();
					
					treatment = (TreatmentInfo) getSession().createCriteria(SubjectRandamization.class)
							    .add(Restrictions.eq("subjectNo", subject.getSubjectNo()))
							    .add(Restrictions.eq("period", spm))
							    .setProjection(Projections.property("treatmentInfo")).uniqueResult();
					//if subject exists take statudyactivity id directly passed studyActivityId
					stdActivity = (StudyActivities) getSession().createCriteria(StudyActivities.class)
							.add(Restrictions.eq("id", studyActivityId)).uniqueResult();

					sapList = getSession().createCriteria(StudyActivityParameters.class)
							.add(Restrictions.in("parameterId.id", pIds)).list();
				}else {
					spm = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
							.add(Restrictions.eq("study.id", studyId))
						    .add(Restrictions.eq("periodName", "P1")).uniqueResult();
					
					//if subject not exist only volunteer take records without treatement period one least id record and save data that study activityid
					Long saMinId =  (Long) getSession().createCriteria(StudyActivities.class)
							              .add(Restrictions.eq("activityId.id", activityId))
							              .add(Restrictions.eq("sm.id", studyId))
							              .add(Restrictions.eq("studyPeriod", spm))
							              .setProjection(Projections.min("id")).uniqueResult();
					if(saMinId != null) {
						stdActivity = (StudyActivities) getSession().createCriteria(StudyActivities.class)
								.add(Restrictions.eq("id", studyActivityId)).uniqueResult();

						sapList = getSession().createCriteria(StudyActivityParameters.class)
								.add(Restrictions.in("parameterId.id", pIds)).list();
					}
					
				}
			}
			if(spm != null) {
				sgpm = (StudyGroupPeriodMaster) getSession().createCriteria(StudyGroupPeriodMaster.class)
						.add(Restrictions.eq("studyId", studyId))
						.add(Restrictions.eq("period", spm)).uniqueResult();
			}
			
			safDto = new StudyActivityFormDataDto();
			safDto.setStdActivity(stdActivity);
			safDto.setPctvList(pctvList);
			safDto.setSm(sm);
			safDto.setSapList(sapList);
			safDto.setGpList(gpList);
			safDto.setSpm(spm);
			safDto.setVolId(svr);
			safDto.setSubject(subject);
			safDto.setSgpm(sgpm);
			safDto.setTreatment(treatment);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return safDto;
	}*/
	@SuppressWarnings("unchecked")
	@Override
	public StudyActivityFormDataDto getStudyActivityFormDataDetails(Long activityId,Long studyActivityId, Long studyId, Long volId, String type, List<Long> pIds) {
		StudyActivityFormDataDto  safDto = null;
		List<StudyActivityParameters> sapList = null;
		StudyActivities stdActivity = null;
		List<GlobalParameter> gpList = new ArrayList<>();
		List<ParameterControlTypeValues> pctvList = null;
		StudyMaster sm = null;
		StudyPeriodMaster spm = null;
		StudyVolunteerReporting svr = null;
		StudySubjects subject = null;
		StatusMaster activeStatus = null;
		StudyGroupPeriodMaster sgpm = null;
		TreatmentInfo treatment = null;
		Long elegibleForNextProcessParameterId = null;
		try {
			activeStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", "ACTIVE")).uniqueResult();
			
			sm = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					.add(Restrictions.eq("id", studyId)).uniqueResult();
			
			pctvList = getSession().createCriteria(ParameterControlTypeValues.class)
					.add(Restrictions.in("globalParameter.id", pIds)).list();
			
			svr = (StudyVolunteerReporting) getSession().createCriteria(StudyVolunteerReporting.class)
					.add(Restrictions.eq("id", volId)).uniqueResult();
			
			if(svr != null) {
				subject = (StudySubjects) getSession().createCriteria(StudySubjects.class)
						.add(Restrictions.eq("study.id", studyId))
						.add(Restrictions.eq("reportingId", svr)).uniqueResult();
				if(subject != null) {
					spm = (StudyPeriodMaster) getSession().createCriteria(StudySubjectPeriods.class)
							.add(Restrictions.eq("subject", subject))
							.add(Restrictions.eq("status", activeStatus))
							.setProjection(Projections.property("periodId")).uniqueResult();
					
					treatment = (TreatmentInfo) getSession().createCriteria(SubjectRandamization.class)
							    .add(Restrictions.eq("subjectNo", subject.getSubjectNo()))
							    .add(Restrictions.eq("period", spm))
							    .setProjection(Projections.property("treatmentInfo")).uniqueResult();
				}else {
					spm = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
							.add(Restrictions.eq("study.id", studyId))
						    .add(Restrictions.eq("periodName", "P1")).uniqueResult();
				}
			}
			if(spm != null) {
				sgpm = (StudyGroupPeriodMaster) getSession().createCriteria(StudyGroupPeriodMaster.class)
						.add(Restrictions.eq("studyId", studyId))
						.add(Restrictions.eq("period", spm)).uniqueResult();
			}
			
			stdActivity = (StudyActivities) getSession().createCriteria(StudyActivities.class)
					.add(Restrictions.eq("id", studyActivityId)).uniqueResult();
           if(stdActivity != null) {
        	   sapList = getSession().createCriteria(StudyActivityParameters.class)
   					.add(Restrictions.in("parameterId.id", pIds))
   					.add(Restrictions.eq("studyActivity", stdActivity)).list();
           }
           elegibleForNextProcessParameterId = (Long) getSession().createCriteria(GlobalParameter.class)
		           .add(Restrictions.eq("parameterCode", ParameterCodes.IENP.toString()))
		           .setProjection(Projections.property("id")).uniqueResult();
           
			safDto = new StudyActivityFormDataDto();
			safDto.setStdActivity(stdActivity);
			safDto.setPctvList(pctvList);
			safDto.setSm(sm);
			safDto.setSapList(sapList);
			safDto.setGpList(gpList);
			safDto.setSpm(spm);
			safDto.setVolId(svr);
			safDto.setSubject(subject);
			safDto.setSgpm(sgpm);
			safDto.setTreatment(treatment);
			safDto.setElegibleForNextProcessParameterId(elegibleForNextProcessParameterId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return safDto;
	}
	@Override
	public MessageDto saveStudyActivityDataDetails(StudyActivityData sadata,
			List<StudyCheckInActivityDataDetails> schinSaveList, List<StudyCheckOutActivityDataDetails> schoutSaveList,
			List<StudyExecutionActivityDataDetails> sexeSaveList, Long timePointId, boolean subjectAllot, 
			String userName, SubjectActivityData sad, String dateFormat, Long elegibleForNextProcessParameterId, Map<Long, String> parmMapVals) {
		MessageDto  mdto = null;
		boolean flag = false;
		long sadataNo = 0;
		boolean schinFlag = false;
		boolean schoutFlag = false;
		boolean sexeFlag = false;
		boolean continueFlag = false;
		StudySubjectPeriods ssp = null;
		StudySubjects sub = null;
		StatusMaster activeStatus = null;
		StatusMaster inactiveStatus = null;
		boolean inclusionFlag = false;
		GlobalActivity gaAct = null;
		try {
			//Subject Exists or not Checking
			 if(subjectAllot) {
				 sub = (StudySubjects)getSession().createCriteria(StudySubjects.class)
						              .add(Restrictions.eq("reportingId.id", sad.getVolId())).uniqueResult();
				 if(sub != null ) {
					 activeStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
							                 .add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
					 ssp = (StudySubjectPeriods) getSession().createCriteria(StudySubjectPeriods.class)
							                     .add(Restrictions.eq("subject", sub))
							                     .add(Restrictions.eq("status", activeStatus)).uniqueResult();
				 }
				 if(sub == null || (ssp != null && ssp.getPeriodId().getPeriodNo() == 1)) {
					 mdto = saveVolunteerSubjectsNumber(sad, userName);
					 if(mdto !=null) {
						 continueFlag = mdto.isMsgFlag();
					 }
				 }else 
					 continueFlag = true;
			 }else continueFlag = true;
			 //Check Inclusion and Exclusion Criteria
			 gaAct = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					         .add(Restrictions.eq("id", sad.getActivityId())).uniqueResult();
		     if(gaAct != null && gaAct.getActivityCode().equals(com.covideinfo.enums.StudyActivities.ExclusionCriteria.toString()))
		    	 inclusionFlag = saveInclusionExclusionCriteriaCondition(sad, userName);
		     else inclusionFlag = true;
		     //Study Activities Saving part
			 if(continueFlag && inclusionFlag) {
				 if(mdto == null)
					 mdto = new MessageDto();
				  sadataNo = (long) getSession().save(sadata);
				  if(sadataNo > 0) {
			    	 if(schinSaveList != null && schinSaveList.size() > 0) {
			    		 for(StudyCheckInActivityDataDetails sciadd : schinSaveList) {
			    			 sciadd.setSaData(sadata);
			    			 getSession().save(sciadd);
			    			 schinFlag = true;
			    		 }
			    	 }else schinFlag = true;
			    	 if(schoutSaveList != null && schoutSaveList.size() > 0) {
			    		 for(StudyCheckOutActivityDataDetails scodd : schoutSaveList) {
			    			 scodd.setSaData(sadata);
			    			 getSession().save(scodd);
			    			 schoutFlag = true;
			    		 }
			    	 }else schoutFlag = true;
			    	 if(sexeSaveList != null && sexeSaveList.size() > 0) {
			    		 for(StudyExecutionActivityDataDetails sead : sexeSaveList) {
			    			 sead.setSaData(sadata);
			    			 getSession().save(sead);
			    			 sexeFlag = true;
			    		 }
			    	 }else sexeFlag = true;
			    	 if(sadataNo > 0 && schinFlag && schoutFlag && sexeFlag) {
			    		//Checking for eligible for next process is allowed to volunteer or inactive volunteer
						 if(elegibleForNextProcessParameterId != null) {
							 String paramVal = parmMapVals.get(elegibleForNextProcessParameterId);
							 String parmVal = "";
							 GlobalValues gvalue = null;
							 if(paramVal != null && !paramVal.equals("")) {
								gvalue =  (GlobalValues) getSession().createCriteria(GlobalValues.class)
												.add(Restrictions.eq("id", Long.parseLong(paramVal))).uniqueResult();
							 }
							 if(gvalue != null)
								 parmVal = gvalue.getName();
							 if(parmVal != null && !parmVal.equals("") && !parmVal.equals("Yes")) {
								 StudyVolunteerReporting repotingVol = sadata.getVolunteerId();
								 if(repotingVol != null) {
									 StatusMaster inactStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
											                            .add(Restrictions.eq("statusCode", StudyStatus.INACTIVE.toString())).uniqueResult();
									 repotingVol.setStatus(inactStatus);
									 getSession().merge(repotingVol);
								 }
							 }
						 }
			    		//Study Activity Time points Completed information Storing for Records Retrieving time
			    		 StudyActivityTimePoints satp = (StudyActivityTimePoints) getSession().createCriteria(StudyActivityTimePoints.class)
				    			 .add(Restrictions.eq("studyActivity", sadata.getSutydActivity()))
				    			 .add(Restrictions.eq("id", timePointId)).uniqueResult();
				    	 if(satp != null) {
				    		 StudyActivityTimePointsCompletionData satpcd = new StudyActivityTimePointsCompletionData();
				    		 satpcd.setCreatedBy(sadata.getCreatedBy());
				    		 satpcd.setCreatedOn(sadata.getCreatedOn());
				    		 satpcd.setPeriodId(sadata.getStdGoupPeriod().getPeriod().getId());
				    		 satpcd.setStatus("Completed");
				    		 satpcd.setStudyActivityId(sadata.getSutydActivity().getId());
				    		 satpcd.setStudyId(sadata.getSutydActivity().getSm().getId());
				    		 satpcd.setStuydActTimePointId(timePointId);
				    		 satpcd.setTreatmentId(sadata.getSutydActivity().getTreatment().getId());
				    		 long satpcdNo = (long) getSession().save(satpcd);
				    		 if(satpcdNo > 0)
				    			 flag = true;
				    	 }else flag = true;
			    	 }
			     }
			     if(flag) {
			    	 if(sadata.getSutydActivity().getActivityId().getActivityCode().equals(com.covideinfo.enums.StudyActivities.BAGGAGERETURN.toString())) {
			    		
			    		 GlobalActivity ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
			    				                  .add(Restrictions.eq("activityCode", com.covideinfo.enums.StudyActivities.StudyWithDraw.toString())).uniqueResult();
			    		 
			    		 SubjectWithdrawDetails swd = (SubjectWithdrawDetails) getSession().createCriteria(SubjectWithdrawDetails.class)
			    				                            .add(Restrictions.eq("study", sadata.getSutydActivity().getSm()))
			    				                            .add(Restrictions.eq("globalActivity", ga))
			    				                            .add(Restrictions.eq("studyVolunteer", sadata.getVolunteerId())).uniqueResult();
			    		 if(swd != null) {
			    			 StatusMaster	completedStatus =  (StatusMaster) getSession().createCriteria(StatusMaster.class)
					                 .add(Restrictions.eq("statusCode", StudyStatus.COMPLETED.toString())).uniqueResult();
			    			 
			    			 StudySubjects subject = (StudySubjects) getSession().createCriteria(StudySubjects.class)
					                   .add(Restrictions.eq("reportingId", sadata.getVolunteerId())).uniqueResult();
			    			 if(subject != null) {
			    				 boolean flag1 = false;
		    				     boolean flag2 = false;
				    			 if(swd.getWithdrawLevel().equals("Study")) {
			    					 subject.setSubjectStatus("inactive");
			    					 getSession().merge(subject);
			    					 flag1 = true;
			    					 flag2 = true;
			    				 }else {
			    					 activeStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
							                 .add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
			    					 
			    					 StudySubjectPeriods ssPeriod = (StudySubjectPeriods) getSession().createCriteria(StudySubjectPeriods.class)
						                        .add(Restrictions.eq("subject", subject))
						                        .add(Restrictions.eq("status", activeStatus)).uniqueResult();
			    					 flag1 = true;
			    					if(ssPeriod != null) {
			    						ssPeriod.setStatus(completedStatus);
			    						getSession().merge(ssPeriod);
			    						flag2 = true;
			    					}else flag2 = true;
			    				 }
				    			 if(flag1 && flag2) {
				    				 mdto.setMealsMsg("success");
				    				 mdto.setMsgFlag(true);
				    			 }
			    			 }else {
			    				 StudyVolunteerReporting svrData =  sadata.getVolunteerId();
			    				 if(svrData != null) {
			    					 inactiveStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
							                 .add(Restrictions.eq("statusCode", StudyStatus.INACTIVE.toString())).uniqueResult();
			    					 svrData.setStatus(inactiveStatus);
			    					 getSession().merge(svrData);
			    					 if(mdto.getMealsMsg() == null || mdto.getMealsMsg().equals(""))
			    						 mdto.setMealsMsg("success");
				    				 mdto.setMsgFlag(true);
			    				 }
			    			 }
			    			 
			    		 }else {
			    			 if(mdto.getMealsMsg() == null || mdto.getMealsMsg().equals(""))
			    				 mdto.setMealsMsg("success");
		    				 mdto.setMsgFlag(true);
			    		 }
			    	 }else {
			    		 if(mdto.getMealsMsg() == null || mdto.getMealsMsg().equals(""))
			    			 mdto.setMealsMsg("success");
	    				 mdto.setMsgFlag(true);
			    	 }
			     }else {
			    	 if(mdto.getMealsMsg() == null || mdto.getMealsMsg().equals(""))
			    		 mdto.setMealsMsg("Failed");
    				 mdto.setMsgFlag(true);
			     }
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			 mdto = new MessageDto();
			 mdto.setMealsMsg("Failed");
			 mdto.setMsgFlag(true);
			return mdto;
		}
		return mdto;
	}

	@SuppressWarnings("unchecked")
	private boolean saveInclusionExclusionCriteriaCondition(SubjectActivityData sad, String userName) {
		boolean inclusionFlag = false;
		String finalResult ="Failed";
		List<ParameterModelDto> paramList = sad.getPmDto();
		List<Long> parmIdsList = new ArrayList<>();
		Map<Long, String> parmMapVals = new HashMap<>();
		List<GlobalParameter> gpList = null;
		long paramId = 0; 
		String gvId = "";
		GlobalValues gValue = null;
		String gvValueStr = "";
		boolean flag = true;
		try {
			if(paramList != null && paramList.size() > 0) {//List of Parameter and parameter Values
				for(ParameterModelDto pmd : paramList) {
					parmIdsList.add(pmd.getParameterId());
					parmMapVals.put(pmd.getParameterId(), pmd.getParameterValue());
				}
			}
			GlobalParameter gpPojo = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
			                    .add(Restrictions.eq("parameterCode", ParameterCodes.STUDYELIGIBLITY.toString())).uniqueResult();
			if(gpPojo != null) {
				paramId = gpPojo.getId();
				gvId = parmMapVals.get(paramId);
				if(gvId != null && !gvId.equals(""))
					gValue = (GlobalValues) getSession().createCriteria(GlobalValues.class)
					              .add(Restrictions.eq("id", Long.parseLong(gvId))).uniqueResult();
				if(gValue != null)
					gvValueStr = gValue.getName();
			}
			
			if(gvValueStr != null && !gvValueStr.equals("") && !gvValueStr.equals("Fit for participation in the study.")) {
				finalResult = inactiveVolunteerRecord(sad.getVolId());
				flag = false;
			}
			if(flag)
    			finalResult = "success";

			if(finalResult.equals("success"))
				inclusionFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return inclusionFlag;
		}
		return inclusionFlag;
	}

	private String inactiveVolunteerRecord(Long volId) {
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

	private MessageDto saveVolunteerSubjectsNumber(SubjectActivityData sad, String userName) {
		String finalResult ="Failed";
		MessageDto  mdto = new MessageDto();
		Integer noOfSubjects = null;
		long subjectsCount = 0;
		StudySubjects subPojo = null;
		StudyGroups studyGroups = null;
		String subNo = "";
		long standByCount =0;
		int studyStandbyCount = 0;
		try {
			noOfSubjects = (Integer) getSession().createCriteria(StudyMaster.class)
					            .add(Restrictions.eq("id", sad.getStudyId()))
					            .setProjection(Projections.property("noOfSubjects")).uniqueResult();
			
			studyStandbyCount = (Integer) getSession().createCriteria(StudyMaster.class)
		            .add(Restrictions.eq("id", sad.getStudyId()))
		            .setProjection(Projections.property("noOfStandBySubjects")).uniqueResult();
			
			if(noOfSubjects != null) {
				subPojo = (StudySubjects) getSession().createCriteria(StudySubjects.class)
		                  .add(Restrictions.eq("reportingId.id", sad.getVolId()))
		                  .add(Restrictions.eq("study.id", sad.getStudyId())).uniqueResult();
				if(subPojo == null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date1 = sdf.parse(sdf.format(new Date()));
					Calendar c = Calendar.getInstance();
					c.setTime(date1);
					c.add(Calendar.DAY_OF_MONTH, 1);
					studyGroups = (StudyGroups) getSession().createCriteria(StudyGroups.class)
		                      .add(Restrictions.eq("study.id", sad.getStudyId()))
		                      .add(Restrictions.eq("status", "Active"))
		                      .add(Restrictions.between("createdOn", date1, c.getTime())).uniqueResult();
					
					subjectsCount = (long) getSession().createCriteria(StudySubjects.class)
				             .add(Restrictions.eq("study.id", sad.getStudyId()))
				             .setProjection(Projections.rowCount()).uniqueResult();
					
					standByCount = (long) getSession().createCriteria(StudySubjects.class)
				               .add(Restrictions.eq("study.id", sad.getStudyId()))
				               .add(Restrictions.like("subjectNo", "S", MatchMode.ANYWHERE))
				               .setProjection(Projections.rowCount()).uniqueResult();
					if(studyGroups != null) {
							subNo = getSubjectNumber((subjectsCount-standByCount), studyGroups.getNoofSubjects(), standByCount, studyGroups.getNoofStandbys());
							if(subNo.equals("")) {
								subNo = generateStudyLevelSubjects((subjectsCount-standByCount), noOfSubjects, standByCount, studyStandbyCount, studyGroups.getNoofStandbys());
							}
					        if(subNo.equals("")) {
					        	mdto.setMealsMsg("Study Level Subject Number Allotment is Completed.");
					        	mdto.setMsgFlag(false);
					        }
					}else 
						subNo = generateStudyLevelSubjects((subjectsCount-standByCount), noOfSubjects, standByCount, studyStandbyCount, 0);
					if(!subNo.equals("")) {
						finalResult = subjectAllotment(sad, userName, subNo);
						mdto.setMealsMsg(finalResult);
					    if(!finalResult.equals("Failed"))
					    	mdto.setMsgFlag(true);
					    else mdto.setMsgFlag(false);
					}else {
						mdto.setMealsMsg("Study Level Subject Number Allotment is Completed.");
						mdto.setMsgFlag(false);
					}
				}else {
					mdto.setMealsMsg("Subject Number Alredy Exists.");
					mdto.setMsgFlag(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mdto;
	}

	private String generateStudyLevelSubjects(long currentSubCount, Integer noOfSubjects, long standByCount, int studyStandbyCount, int groupStandBySubjects) {
		String subNo = "";
		long subCount =0;
		try {
			subCount = (currentSubCount+1);
			if(subCount <= noOfSubjects) {
				if(subCount < 10)
					subNo = "0"+subCount;
				else subNo = subCount+"";
			}else {
				if((standByCount+1) <=  (groupStandBySubjects + studyStandbyCount))
					subNo = "S"+(standByCount+1);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subNo;
	}

	private String getSubjectNumber(long subjectsCount, Integer noOfSubjects, long standByCount, int groupStandbys) {
		String subNo = "";
		long subCount =0;
		try {
			subCount = (subjectsCount+1);
			if(subCount <= noOfSubjects) {
				if(subCount < 10)
					subNo = "0"+subCount;
				else subNo = subCount+"";
			}else {
				if((standByCount+1) <=  groupStandbys)
					subNo = "S"+(standByCount+1);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subNo;
	}

	@SuppressWarnings("unchecked")
	private String subjectAllotment(SubjectActivityData sad, String userName, String subjectNo) {
		String result ="Failed";
		StudySubjects ss = null;
		StudyVolunteerReporting svr = null;
		StudyGroup studyGroup = null;
		List<StudyPeriodMaster> spmList = null;
		long ssNo = 0;
		try {
			svr = (StudyVolunteerReporting) getSession().createCriteria(StudyVolunteerReporting.class)
					.add(Restrictions.eq("id", sad.getVolId())).uniqueResult();
			if(svr != null) {
				Long maxId = (Long) getSession().createCriteria(StudyGroup.class)
						.add(Restrictions.eq("study", svr.getSm()))
						.setProjection(Projections.max("id")).uniqueResult();
				if(maxId != null) {
					studyGroup = (StudyGroup) getSession().createCriteria(StudyGroup.class)
								.add(Restrictions.eq("id", maxId)).uniqueResult();
				}
				
				ss = new StudySubjects();
				ss.setCreatedBy(userName);
				ss.setCreatedOn(new Date());
				ss.setReportingId(svr);
				ss.setSubjectNo(subjectNo);
				ss.setStudy(svr.getSm());
				ss.setGroupId(studyGroup);
				ssNo = (long) getSession().save(ss);
				if(ssNo > 0) {
					spmList = getSession().createCriteria(StudyPeriodMaster.class)
							.add(Restrictions.eq("study", ss.getStudy())).list();
					if(spmList != null && spmList.size() > 0) {
						for(StudyPeriodMaster spm : spmList) {
							StudySubjectPeriods ssp = new StudySubjectPeriods();
							ssp.setCreatedBy(userName);
							ssp.setCreatedOn(new Date());
							ssp.setPeriodId(spm);
							ssp.setSubject(ss);
							StatusMaster status = null;
							if(spm.getPeriodNo() == 1) {
								status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
										.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
							}else {
								status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
										.add(Restrictions.eq("statusCode", StudyStatus.NEW.toString())).uniqueResult();
							}
							ssp.setStatus(status);
							long sspNo = (long) getSession().save(ssp);
							if(sspNo > 0) 
								result ="success";
						}
					}
				}
				if(result.equals("success")) {
					StudyVolunteerReporting svrPojo = (StudyVolunteerReporting) getSession().createCriteria(StudyVolunteerReporting.class)
							.add(Restrictions.eq("id", ss.getReportingId().getId())).uniqueResult();
					if(svrPojo != null) {
						svrPojo.setUpdatedBy(userName);
						svrPojo.setUpdatedOn(new Date());
						svrPojo.setSubjectNo(ss.getSubjectNo());
						getSession().merge(svrPojo);
						result ="Subject Number "+ss.getSubjectNo()+ " Alloted to Volunteer : "+ss.getReportingId().getVolunteerId();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result ="Failed";
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificValueDetails> getGlobalValuesList(long paramId, long controlId, InternationalizaionLanguages inlg) {
		List<LanguageSpecificValueDetails> lsvdList = null;
		List<Long> gvIds = null;
		try {
			gvIds = getSession().createCriteria(ParameterControlTypeValues.class)
					.add(Restrictions.eq("globalParameter.id", paramId))
					.add(Restrictions.eq("controlType.id", controlId))
					.setProjection(Projections.property("globalValues.id")).list();
			
			lsvdList = getSession().createCriteria(LanguageSpecificValueDetails.class)
					.add(Restrictions.in("globalValId.id", gvIds))
					.add(Restrictions.eq("inlagId", inlg)).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsvdList;
	}

	@Override
	public DefaultActivitys getDefaultActivityRecod(GlobalActivity activityId) {
		return (DefaultActivitys) getSession().createCriteria(DefaultActivitys.class)
				.add(Restrictions.eq("activity", activityId)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public StudyDesingDto getStudyDesingDtoDetails(Long activityId, Long languageId) {
		 StudyDesingDto sdDto = null;
		 GlobalActivity activity = null;
		 List<GlobalParameter> gpList = null;
		 List<LanguageSpecificGlobalParameterDetails> lsgpdList = new ArrayList<>();
		 List<GlobalParameter> gpNotLinkToActivityList = null;
		 try {
			activity = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", activityId)).uniqueResult();
			
			if(activity != null) {
				gpList = getSession().createCriteria(GlobalParameter.class)
						.add(Restrictions.eq("activity", activity)).list();
				
				if(gpList != null && gpList.size() > 0) {
					for(GlobalParameter gp : gpList) {
						LanguageSpecificGlobalParameterDetails lspgp = (LanguageSpecificGlobalParameterDetails) getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
								.add(Restrictions.eq("parameterId", gp))
								.add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
						if(lspgp != null) {
							lsgpdList.add(lspgp);
						}
					}
				}
				gpNotLinkToActivityList = getSession().createCriteria(GlobalParameter.class)
							.add(Restrictions.isNull("activity")).list();
				if(gpNotLinkToActivityList != null && gpNotLinkToActivityList.size() >0) {
					for(GlobalParameter gp : gpNotLinkToActivityList) {
						LanguageSpecificGlobalParameterDetails lspgp = (LanguageSpecificGlobalParameterDetails) getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
								.add(Restrictions.eq("parameterId", gp))
								.add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
						if(lspgp != null) {
							lsgpdList.add(lspgp);
							gpList.add(gp);
						}
					}
				}
				
				DefaultActivitys defaultActivity = (DefaultActivitys)getSession().createCriteria(DefaultActivitys.class)
											.add(Restrictions.eq("activity.id", activityId)).uniqueResult();
				
				List<GlobalParameter> globalParameters = getSession().createCriteria(DefaultActivityParameters.class).add(Restrictions.eq("defalutActId", defaultActivity))
							.setProjection(Projections.property("parameter")).list();
				if(globalParameters!=null) {
					for(GlobalParameter gp : globalParameters) {
						LanguageSpecificGlobalParameterDetails lspgp = (LanguageSpecificGlobalParameterDetails) getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
								.add(Restrictions.eq("parameterId", gp))
								.add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
						if(lspgp != null) {
							lsgpdList.add(lspgp);
							gpList.add(gp);
						}
					}
				}
			}
			sdDto = new StudyDesingDto();
			sdDto.setActivity(activity);
			sdDto.setGpList(gpList);
			sdDto.setLsgpdList(lsgpdList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdDto;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public StudyDesingDto getActivityParamters(Long activityId, Long languageId) {
		 StudyDesingDto sdDto = null;
		 GlobalActivity activity = null;
		 List<GlobalParameter> gpList = null;
		 List<LanguageSpecificGlobalParameterDetails> lsgpdList = new ArrayList<>();
		 List<Long> parmIdsList = new ArrayList<>();
		 try {
			activity = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", activityId)).uniqueResult();
			
			if(activity != null) {
				gpList = getSession().createCriteria(GlobalParameter.class)
						.add(Restrictions.eq("activity", activity)).list();
				
				if(gpList != null && gpList.size() > 0) {
					for(GlobalParameter gp : gpList) {
						LanguageSpecificGlobalParameterDetails lspgp = (LanguageSpecificGlobalParameterDetails) getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
								.add(Restrictions.eq("parameterId", gp))
								.add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
						if(lspgp != null) {
							parmIdsList.add(lspgp.getParameterId().getId());
							lsgpdList.add(lspgp);
						}
					}
				}
								
				DefaultActivitys defaultActivity = (DefaultActivitys)getSession().createCriteria(DefaultActivitys.class)
											.add(Restrictions.eq("activity.id", activityId)).uniqueResult();
				
				List<GlobalParameter> globalParameters = getSession().createCriteria(DefaultActivityParameters.class).add(Restrictions.eq("defalutActId", defaultActivity))
							.setProjection(Projections.property("parameter")).list();
				if(globalParameters!=null) {
					for(GlobalParameter gp : globalParameters) {
						LanguageSpecificGlobalParameterDetails lspgp = (LanguageSpecificGlobalParameterDetails) getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
								.add(Restrictions.eq("parameterId", gp))
								.add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
						if(lspgp != null) {
							if(!parmIdsList.contains(lspgp.getParameterId().getId())) {
								lsgpdList.add(lspgp);
								gpList.add(gp);
							}
						}
					}
				}
			}
			sdDto = new StudyDesingDto();
			sdDto.setActivity(activity);
			sdDto.setGpList(gpList);
			sdDto.setLsgpdList(lsgpdList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String CheckDependentActivitiesDataEntryCompletion(StudyMaster sm, Long activityId, StudyVolunteerReporting svr,
			StudySubjects subject, MessageSource messageSource, Long languageId, StudyPeriodMaster spm, Locale currentLocale,Long studyActivityId) {
		String message ="";
		StudyActivityRules sarPojo = null;
		GlobalActivity gact = null;
		StudyActivityData sadataPojo = null;
		boolean duplicate = false;
		LanguageSpecificGlobalActivityDetails lspga = null;
		List<Long> dependentActIds = new ArrayList<>();
		List<LanguageSpecificGlobalActivityDetails> lsgaList = null;
		String dactMsg1 = "";
		String dactMsg2 = "";
		StringBuilder sb = new StringBuilder();
		String volNo = "";
		StudyActivities sa = null;
		TreatmentInfo treatment = null;
		try {
			gact = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", activityId)).uniqueResult();
			
			lspga = (LanguageSpecificGlobalActivityDetails) getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
					.add(Restrictions.eq("inlagId.id", languageId))
					.add(Restrictions.eq("globalActivity", gact)).uniqueResult();
			
			//Check Dependent Activities
			sa = (StudyActivities) getSession().createCriteria(StudyActivities.class)
							.add(Restrictions.eq("id", studyActivityId)).uniqueResult();
			
			if(subject != null) {
				treatment = (TreatmentInfo) getSession().createCriteria(SubjectRandamization.class)
		                .add(Restrictions.eq("period", spm))
		                .add(Restrictions.eq("subjectNo", subject.getSubjectNo()))
		                .setProjection(Projections.property("treatmentInfo")).uniqueResult();
			}else {
				Long trMinId = (Long) getSession().createCriteria(TreatmentInfo.class)
		    		       .add(Restrictions.eq("study", sm))
		    		       .setProjection(Projections.min("id")).uniqueResult();
				if(trMinId != null) {
					 treatment = (TreatmentInfo) getSession().createCriteria(TreatmentInfo.class)
			    		       .add(Restrictions.eq("id", trMinId)).uniqueResult();
				}
			   
			}
			if(gact.getAllowMultiple() != null && !gact.getAllowMultiple().equals("Yes")) {
				if(svr != null) {
					List<StudyActivityData> volunteerStudyActivityData = (List<StudyActivityData>)getSession().createCriteria(StudyActivityData.class)
							.add(Restrictions.eq("volunteerId", svr))
							.add(Restrictions.eq("sutydActivity", sa)).list();
		
					for(StudyActivityData activityData:volunteerStudyActivityData) {
						if(activityData.getSutydActivity().getActivityId().getId() == gact.getId()) {
							sadataPojo = activityData;
							break;
						}
					}
				}else if(subject != null) {
					List<StudyActivityData> volunteerStudyActivityData = (List<StudyActivityData>)getSession().createCriteria(StudyActivityData.class)
							.add(Restrictions.eq("subjectId", subject))
							.add(Restrictions.eq("sutydActivity", sa)).list();
		
					for(StudyActivityData activityData:volunteerStudyActivityData) {
						if(activityData.getSutydActivity().getActivityId().getId() == gact.getId()) {
							sadataPojo = activityData;
							break;
						}
					}
				}
				if(sadataPojo != null) {
					String stdDataThis =messageSource.getMessage("label.stdDataThis", null,currentLocale);
					String doneMsg =messageSource.getMessage("label.stdDataAlready", null,currentLocale);
//					message ="This "+lspga.getName()+" Data Entry Already Done for this Subject.";
					message =stdDataThis+" "+lspga.getName()+" "+doneMsg;
					duplicate = true;
				}
			}else duplicate = false;
			if(duplicate == false) {
				sarPojo = (StudyActivityRules) getSession().createCriteria(StudyActivityRules.class)
						.add(Restrictions.eq("sourceActivity.id", activityId))
						.add(Restrictions.eq("ruleType", "DependentActivities")).uniqueResult();
				
				if(sarPojo != null) {
					String actIds = sarPojo.getDependentActivities();
					List<Long> activityIds = new ArrayList<>();
					if(actIds != null && !actIds.equals("")) {
						String[] arr = actIds.split("\\,");
						if(arr.length > 0) {
							for(String st : arr) {
								activityIds.add(Long.parseLong(st));
							}
						}
						if(activityIds.size() > 0) {
							for(Long pid : activityIds) {
								List<StudyActivityData> sadataList = null;
								StudyVolunteerReporting svrPojo = svr;
								if(svrPojo == null) {
									if(subject != null)
										svrPojo = subject.getReportingId();
								}
									
								if(svrPojo != null) {
									 
									/*StudyActivities saPojo = (StudyActivities) getSession().createCriteria(StudyActivities.class)
											                 .add(Restrictions.eq("studyPeriod", spm))
											                 .add(Restrictions.eq("activityId.id", pid))
											                 .add(Restrictions.eq("treatment", treatment)).uniqueResult();
									
									 sadata = (StudyActivityData) getSession().createCriteria(StudyActivityData.class)
												.add(Restrictions.eq("sutydActivity", saPojo))
												.add(Restrictions.eq("volunteerId", svrPojo)).uniqueResult();*/
									 
									 List<Long> saIdsList =  getSession().createCriteria(StudyActivities.class)
							                 .add(Restrictions.eq("studyPeriod", spm))
							                 .add(Restrictions.eq("activityId.id", pid))
							                 .setProjection(Projections.property("id")).list();
									 if(saIdsList != null && saIdsList.size() > 0) {
										 sadataList =  getSession().createCriteria(StudyActivityData.class)
													.add(Restrictions.in("sutydActivity.id", saIdsList))
													.add(Restrictions.eq("volunteerId", svrPojo)).list();
									 }
								}
								
								if(sadataList != null && sadataList.size() == 0) {
									dependentActIds.add(pid);
								}
							}
							
						}
					}
				}
			}
			if(dependentActIds.size() > 0) {
				lsgaList = getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
                                          .add(Restrictions.in("globalActivity.id", dependentActIds))
                                          .add(Restrictions.eq("inlagId.id", languageId)).list();
				
				if(lsgaList != null && lsgaList.size() > 0) {
					Collections.sort(lsgaList);
					for(LanguageSpecificGlobalActivityDetails lsga : lsgaList) {
						boolean flag = true;
						if(lsga.getGlobalActivity().getActivityCode().equals(com.covideinfo.enums.StudyActivities.Reporting.toString())) {
							if(svr != null)
								volNo = svr.getVolunteerId();
							else if(subject != null)
								volNo = subject.getReportingId().getVolunteerId();
							if(volNo != null)
								flag = false;
						}
						if(flag) {
							if(sb.length() == 0)
								sb.append(lsga.getName());
							else {
								sb.append("\n ,");
								sb.append(lsga.getName());
							}
						}
					}
					if(lsgaList.size() > 1) 
						dactMsg2 = messageSource.getMessage("label.stdActMsg1Multi", null,currentLocale);
					else 
						dactMsg2 =messageSource.getMessage("label.stdActMsg1", null,currentLocale);
					
					if(sb.length() > 0) {
						dactMsg1 =messageSource.getMessage("label.stdActmsg2", null,currentLocale);
						message = dactMsg1+" :\n "+dactMsg2+" :\n "+sb.toString();
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public String getActivitySubjects(Long studyId, TreatmentInfo treatmentInfo,StudyPeriodMaster studyPeriodMaster) {
		return (String) getSession().createCriteria(StudyTreatmentWiseSubjects.class)
				.add(Restrictions.eq("sm.id", studyId))
				.add(Restrictions.eq("treatment", treatmentInfo))
				.add(Restrictions.eq("period", studyPeriodMaster))
				.setProjection(Projections.property("subjects")).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public StudyActivitySavingDto getStudyActivitySavingDtoDetailsForStudyActivities(List<Long> parmIds, List<Long> actIds, StudyMaster sm) {
		StudyActivitySavingDto sadto = null;
		List<GlobalActivity> actList = null;
		Map<Long, GlobalParameter> gpMap = new HashMap<>();
		List<String> strList = new ArrayList<>();
		List<GlobalParameter> gpList = null;
		List<TreatmentInfo> tminfoList = null;
		List<StudyPeriodMaster> spmList = null;
		List<GlobalValues> gvList = null;
		List<String> genderList = new ArrayList<>();
		Long defaultIdsForIncAndExcId = null;
		try {
			genderList.add("Male");
			genderList.add("Female");
			
			strList.add(com.covideinfo.enums.StudyActivities.ExclusionCriteria.toString());
			strList.add(com.covideinfo.enums.StudyActivities.InclusionCriteria.toString());
			
			spmList = getSession().createCriteria(StudyPeriodMaster.class)
					.add(Restrictions.eq("study", sm)).list();
			
			tminfoList = getSession().createCriteria(TreatmentInfo.class)
					.add(Restrictions.eq("study", sm)).list();
			
			actList = getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.in("activityCode", strList)).list();
			
			gvList = getSession().createCriteria(GlobalValues.class)
					.add(Restrictions.in("name", genderList)).list();
			
			if(actIds.size() > 0) {
				List<GlobalActivity> gaList = getSession().createCriteria(GlobalActivity.class)
						.add(Restrictions.in("id", actIds)).list();
				if(gaList.size() > 0)
					actList.addAll(gaList);
			}
			if(parmIds != null && parmIds.size() > 0) {
				Long gpId = (Long) getSession().createCriteria(GlobalParameter.class)
						           .add(Restrictions.eq("parameterCode", ParameterCodes.IENP.toString()))
						           .setProjection(Projections.property("id")).uniqueResult();
				if(gpId != null) {
					if(!parmIds.contains(gpId))
						parmIds.add(gpId);
				}
				gpList = getSession().createCriteria(GlobalParameter.class)
						.add(Restrictions.in("id", parmIds)).list();
			}
			
			if(gpList != null && gpList.size() > 0) {
				for(GlobalParameter gp : gpList) {
					gpMap.put(gp.getId(), gp);
				}
			}
			defaultIdsForIncAndExcId = (Long) getSession().createCriteria(GlobalParameter.class)
											.add(Restrictions.eq("parameterCode", ParameterCodes.STUDYELIGIBLITY.toString()))
											.setProjection(Projections.property("id")).uniqueResult();
			
			sadto = new StudyActivitySavingDto();
			sadto.setActList(actList);
			sadto.setGpMap(gpMap);
			sadto.setSpList(spmList);
			sadto.setTminfoList(tminfoList);
			sadto.setGvList(gvList);
			sadto.setDefaultIdsForIncAndExcId(defaultIdsForIncAndExcId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sadto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalParameter> getDefaultParameterRecordsList(List<Long> defaultParmIds, GlobalActivity actId) {
		List<GlobalParameter> gpList = null;
		DefaultActivitys da = null;
		try {
			da = (DefaultActivitys) getSession().createCriteria(DefaultActivitys.class)
					.add(Restrictions.eq("activity", actId)).uniqueResult();
			if(da != null) {
				if(defaultParmIds != null && defaultParmIds.size() > 0) {
					gpList = getSession().createCriteria(DefaultActivityParameters.class)
							.add(Restrictions.eq("defalutActId", da))
							.add(Restrictions.not(Restrictions.in("parameter.id", defaultParmIds)))
							.setProjection(Projections.property("parameter")).list();
				}else {
					gpList = getSession().createCriteria(DefaultActivityParameters.class)
							.add(Restrictions.eq("defalutActId", da))
							.setProjection(Projections.property("parameter")).list();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gpList;
	}

	@Override
	public String saveStudyActivitiesAndRules(Map<StudyActivities, List<StudyActivityParameters>> saMap,
			Map<String, Map<Long, Map<Long, StudyActivityRules>>> saruleMap) {
		String result = "Failed";
		try {
			boolean flag = false;
			for(Map.Entry<StudyActivities, List<StudyActivityParameters>> map : saMap.entrySet()) {
				StudyActivities sa = map.getKey();
				List<StudyActivityParameters> sapList = map.getValue();
				getSession().save(sa);
				for(StudyActivityParameters sap : sapList) {
					sap.setStudyActivity(sa);
					long sapNo = (long) getSession().save(sap);
					if(sapNo > 0)
						flag = true;
				}
			}
			if(flag) {
				if(saruleMap.size() > 0) {
					for(Map.Entry<String, Map<Long, Map<Long, StudyActivityRules>>> sarMap : saruleMap.entrySet()) {
						String gender = sarMap.getKey();
						Map<Long, Map<Long, StudyActivityRules>> asarMap = sarMap.getValue();
						for(Map.Entry<Long, Map<Long, StudyActivityRules>> asaMap : asarMap.entrySet()) {
							Long actId = asaMap.getKey();
							Map<Long, StudyActivityRules> map3 = asaMap.getValue();
							for(Map.Entry<Long, StudyActivityRules> map4 : map3.entrySet()) {
								Long parmId = map4.getKey();
								StudyActivityRules sar= map4.getValue();
								StudyActivityRules sarPojo = (StudyActivityRules)getSession().createCriteria(StudyActivityRules.class)
										.add(Restrictions.eq("ruleType", "Dependent"))
										.add(Restrictions.eq("condition", "Dynamic"))
										.add(Restrictions.eq("sourceActivity.id", actId))
										.add(Restrictions.eq("destinationActivity.id", actId))
										.add(Restrictions.eq("sourceParameter.id", parmId))
										.add(Restrictions.eq("Hide", sar.getParameterAction()))
										.add(Restrictions.eq("keyName", gender))
										.add(Restrictions.eq("keyValue", sar.getKeyValue())).uniqueResult();
								if(sarPojo == null) {
									long sarNo = (long) getSession().save(sar);
									if(sarNo > 0) 
										result = "success";
								}
							}
						}
					}
				}else result ="success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, List<GlobalParameter>> getDefaultActivityParameterDetailsForRestrictionCompliance(
			Map<Long, List<Long>> resDefalutMap) {
		Map<Long, List<GlobalParameter>> defalutParamMap = new HashMap<>();
		try {
			for(Map.Entry<Long, List<Long>> map : resDefalutMap.entrySet()) {
				DefaultActivitys da = (DefaultActivitys) getSession().createCriteria(DefaultActivitys.class)
						.add(Restrictions.eq("activity.id", map.getKey())).uniqueResult();
				if(da != null) {
					List<GlobalParameter> gpList = getSession().createCriteria(DefaultActivityParameters.class)
							.add(Restrictions.eq("defalutActId", da))
							.add(Restrictions.not(Restrictions.in("parameter.id", map.getValue()))).list();
					if(gpList.size() > 0) {
						defalutParamMap.put(map.getKey(), gpList);
					}
				}
				List<GlobalParameter> gList = getSession().createCriteria(GlobalParameter.class)
						.add(Restrictions.not(Restrictions.in("id", map.getValue())))
						.add(Restrictions.eq("activity.id", map.getKey())).list();
				if(gList != null && gList.size() > 0) {
					if(defalutParamMap.containsKey(map.getKey())) {
						List<GlobalParameter> list = defalutParamMap.get(map.getKey());
						list.addAll(gList);
						defalutParamMap.put(map.getKey(), list);
					}else {
						List<GlobalParameter> list = new ArrayList<>();
						list.addAll(gList);
						defalutParamMap.put(map.getKey(), list);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defalutParamMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DefaulltActparamDto getDefaultParameterRecords(Long activityId, List<Long> paramIds) {
		DefaulltActparamDto gapDto = null;
		List<GlobalParameter> gpList = null;
		GlobalActivity ga = null;
		try {
			ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", activityId)).uniqueResult();
			
			gpList = getSession().createCriteria(GlobalParameter.class)
					.add(Restrictions.in("id", paramIds)).list();
			
			gapDto = new DefaulltActparamDto();
			gapDto.setGa(ga);
			gapDto.setGpList(gpList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gapDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GlobalparameterFromDto getLanguageSpecificGlobalParameters(List<Long> doseIds,
			Long languageId, long actId) {
		GlobalparameterFromDto gpfDto = null;
		List<GlobalParameterDetailsDto> gpdDtoList = new ArrayList<>();
		
		
		List<LanguageSpecificGlobalParameterDetails> gpList = null;
		LanguageSpecificGlobalActivityDetails ga = null;
		try {
			
			ga = (LanguageSpecificGlobalActivityDetails) getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
					.add(Restrictions.eq("globalActivity.id", actId))
					.add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
			
			gpList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
						.add(Restrictions.eq("inlagId.id", languageId))
						.add(Restrictions.in("parameterId.id", doseIds)).list();
			if(gpList != null && gpList.size() > 0) {
				GlobalParameterDetailsDto gpDto = new GlobalParameterDetailsDto();
				List<ParameterFormDataDto> parameterDto = new ArrayList<>();
				for(LanguageSpecificGlobalParameterDetails lsgp : gpList) {
					String ctype = lsgp.getParameterId().getContentType().getCode();
					FromControlDto controlType = null;
					List<FromValuesDto> valuesDto = new ArrayList<>();
					if(ctype.equals("RB") || ctype.equals("SB") || ctype.equals("CB")) {
						 List<LanguageSpecificValueDetails> gvList  = null;
						 List<GlobalValues> gvArrList = getSession().createCriteria(ParameterControlTypeValues.class)
								         .add(Restrictions.eq("globalParameter", lsgp.getParameterId()))
								         .add(Restrictions.eq("controlType", lsgp.getParameterId().getContentType()))
								         .setProjection(Projections.property("globalValues")).list();
						 List<Long> gvIds = new ArrayList<>();
						 if(gvArrList != null && gvArrList.size() > 0) {
							 for(GlobalValues gv : gvArrList) {
								 gvIds.add(gv.getId());
							 }
						 }
						 if(gvIds != null && gvIds.size() > 0) {
							 gvList = getSession().createCriteria(LanguageSpecificValueDetails.class)
									 .add(Restrictions.in("globalValId.id", gvIds))
									 .add(Restrictions.eq("inlagId.id", languageId)).list();
							 if(gvList.size() > 0) {
								 for(LanguageSpecificValueDetails lsgv : gvList) {
									 FromValuesDto fvDto = new FromValuesDto();
									 fvDto.setOrder(lsgv.getGlobalValId().getOrderNo());
									 fvDto.setValueId(lsgv.getGlobalValId().getId());
									 fvDto.setValueName(lsgv.getName());
									 valuesDto.add(fvDto);
								 }
							 }
						 }
					}
					 controlType = new FromControlDto();
					 controlType.setContentCode(ctype);
					 controlType.setValuesDto(valuesDto);
					 
					 FormGroupsDto group = null;
					 if(lsgp.getParameterId().getGroups() != null) {
						 LanguageSpecificGroupDetails groupPojo = (LanguageSpecificGroupDetails) getSession().createCriteria(LanguageSpecificGroupDetails.class)
								 .add(Restrictions.eq("globlgroupId", lsgp.getParameterId().getGroups()))
								 .add(Restrictions.eq("inlagId", languageId)).uniqueResult();
						 if(groupPojo != null) {
							 group = new FormGroupsDto();
							 group.setGroupId(groupPojo.getGloblgroupId().getId());
							 group.setGroupName(groupPojo.getName());
							 group.setOrderNo(groupPojo.getGloblgroupId().getOrderNo());
						 }
					 }
					 ParameterFormDataDto pfDto = new ParameterFormDataDto();
					 pfDto.setControlType(controlType);
					 pfDto.setGroup(group);
					 pfDto.setOrderNo(lsgp.getParameterId().getOrderNo());
					 pfDto.setParameterId(lsgp.getParameterId().getId());
					 pfDto.setParameterName(lsgp.getName());
					 parameterDto.add(pfDto); 
				}
				gpDto.setActivityId(ga.getGlobalActivity().getId());
				gpDto.setActivityName(ga.getName());
				gpDto.setParameterDto(parameterDto);
				gpdDtoList.add(gpDto);
			}
			 gpfDto = new GlobalparameterFromDto();
			 gpfDto.setGpdDtoList(gpdDtoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gpfDto;
	}

	@Override
	public StudyVolunteerReporting getVolunteerId(String st) {
		return (StudyVolunteerReporting) getSession().createCriteria(StudySubjects.class)
				.add(Restrictions.eq("subjectNo", st))
				.setProjection(Projections.property("reportingId")).uniqueResult();
	}

	@Override
	public GlobalParameter getGlobalPrameterRecord(Long defaultIdsForIncAndExcId) {
		return (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
				 .add(Restrictions.eq("id", defaultIdsForIncAndExcId)).uniqueResult();
	}


}
