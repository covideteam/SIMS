package com.covideinfo.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.CpurActivityReviewDao;
import com.covideinfo.dto.CpuActivitiesDetailsDto;
import com.covideinfo.dto.DiscrepancyResponseDto;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.DiscripancyReviewDeatails;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.SubjectDoseTimePointParametersData;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectVitalParametersData;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.model.UserMaster;

@Repository("cpurActivityReviewDao")
public class CpurActivityReviewDaoImpl extends AbstractDao<Long, StudyMaster> implements CpurActivityReviewDao {

	@SuppressWarnings("unchecked")
	@Override
	public CpuActivitiesDetailsDto getCpuReviewActivityData(Long projectId, Long activityId, Long userId,
			Long roleId, Long languageId, String type) {
		CpuActivitiesDetailsDto cpuDetailsDto = null;
		List<SubjectMealsTimePointsData> mealsDataList = null;
		List<SubjectSampleCollectionTimePointsData> samplesDtaList = null;
		List<SubjectVitalTimePointsData> vitalsDtaList = null;
		List<SubjectVitalParametersData> vitalParmsList = null;
		List<SubjectDoseTimePoints> doseDataList = null;
		List<SubjectDoseTimePointParametersData> doseParamList = null;
		UserMaster user = null;
		GlobalActivity ga = null;
		List<Long> periodIds = null;
		List<LanguageSpecificGlobalParameterDetails> lspgpdList = new ArrayList<>();
		Map<Long, List<LanguageSpecificGroupDetails>> lspgMap = new HashMap<>();
		LanguageSpecificGlobalParameterDetails lspgd = null;
		DraftReviewStage submitdrs = null;
		DraftReviewStage approvedrs = null;
		DraftReviewStage sendCommentsdrs = null;
		List<DiscripancyReviewDeatails> drdList = null;
		try {
			ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					 .add(Restrictions.eq("id", activityId)).uniqueResult();
			
			user = (UserMaster) getSession().createCriteria(UserMaster.class)
					  .add(Restrictions.eq("id", userId)).uniqueResult();
			
			if(type != null && type.equals("review")) {
				approvedrs = (DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
						  .add(Restrictions.eq("name", "StudyDataReview"))
						  .add(Restrictions.eq("action", "APPROVAL"))
						  .add(Restrictions.eq("fromRole.id", roleId)).uniqueResult();
				
				sendCommentsdrs = (DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
						  .add(Restrictions.eq("name", "StudyDataReview"))
						  .add(Restrictions.eq("action", "SENDCOMMENTS"))
						  .add(Restrictions.eq("fromRole.id", roleId)).uniqueResult();
			}else {
				submitdrs = (DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
						  .add(Restrictions.eq("name", "StudyDataReview"))
						  .add(Restrictions.eq("action", "SUBMIT"))
						  .add(Restrictions.isNull("fromRole"))
						  .add(Restrictions.eq("toRole.id", roleId)).uniqueResult();
				
			}
			if(ga != null) {
				periodIds = getSession().createCriteria(StudyPeriodMaster.class)
						      .add(Restrictions.eq("study.id", projectId))
						      .setProjection(Projections.property("id")).list();
				if(periodIds != null) {
					if(ga.getActivityCode().equals(StudyActivities.MealsCollection.toString())) {
						
						mealsDataList = getSession().createCriteria(SubjectMealsTimePointsData.class)
								            .add(Restrictions.in("studyPeriodMaster.id", periodIds)).list();
						
					}else if(ga.getActivityCode().equals(StudyActivities.SampleCollection.toString())) {
						
						samplesDtaList = getSession().createCriteria(SubjectSampleCollectionTimePointsData.class)
								               .add(Restrictions.in("studyPeriodMaster.id", periodIds)).list();
						
					}else if(ga.getActivityCode().equals(StudyActivities.StudyExecutionVitals.toString())) {
						
						vitalsDtaList = getSession().createCriteria(SubjectVitalTimePointsData.class)
								             .add(Restrictions.in("period.id", periodIds)).list();
						
						List<Long> vitalIds = getSession().createCriteria(SubjectVitalTimePointsData.class)
					             .add(Restrictions.in("period.id", periodIds))
					             .setProjection(Projections.property("id")).list();
						
						if(vitalIds != null && vitalIds.size() > 0) {
							vitalParmsList = getSession().createCriteria(SubjectVitalParametersData.class)
									               .add(Restrictions.in("subjVitalTpData.id", vitalIds)).list();
							
							if(vitalParmsList != null && vitalParmsList.size() > 0) {
								for(SubjectVitalParametersData svpd : vitalParmsList) {
									lspgd = (LanguageSpecificGlobalParameterDetails) getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
	                                         .add(Restrictions.eq("parameterId", svpd.getParameter()))
	                                         .add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
									if(lspgd != null) {
										lspgpdList.add(lspgd);
										if(lspgd.getParameterId().getGroups() != null) {
											LanguageSpecificGroupDetails lsgd = (LanguageSpecificGroupDetails) getSession().createCriteria(LanguageSpecificGroupDetails.class)
													.add(Restrictions.eq("globlgroupId", lspgd.getParameterId().getGroups()))
													.add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
											
											if(lsgd != null) {
												List<LanguageSpecificGroupDetails> lsgpList = null;
												if(lspgMap.containsKey(lspgd.getParameterId().getId())) {
													lsgpList = lspgMap.get(lspgd.getParameterId().getId());
													lsgpList.add(lsgd);
													lspgMap.put(lspgd.getParameterId().getId(), lsgpList);
												}else {
													lsgpList = new ArrayList<>();
													lsgpList.add(lsgd);
													lspgMap.put(lspgd.getParameterId().getId(), lsgpList);
												}
												
											}
										}
									}
								}
							}
						}
					}else if(ga.getActivityCode().equals(StudyActivities.DosingCollection.toString())) {
						
						doseDataList = getSession().createCriteria(SubjectDoseTimePoints.class)
								           .add(Restrictions.in("period.id", periodIds)).list();
						
						List<Long> doseIds = getSession().createCriteria(SubjectDoseTimePoints.class)
						           				.add(Restrictions.in("period.id", periodIds))
						           				.setProjection(Projections.property("id")).list();
						if(doseIds != null && doseIds.size() > 0) {
							doseParamList = getSession().createCriteria(SubjectDoseTimePointParametersData.class)
									           .add(Restrictions.in("subjectDoseTimePoint.id", doseIds)).list();
							
							if(doseParamList != null && doseParamList.size() > 0) {
								for(SubjectDoseTimePointParametersData sdtppd : doseParamList) {
									lspgd = (LanguageSpecificGlobalParameterDetails) getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
											                                         .add(Restrictions.eq("parameterId", sdtppd.getParameter()))
											                                         .add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
									if(lspgd != null) {
										lspgpdList.add(lspgd);
										if(lspgd.getParameterId().getGroups() != null) {
											LanguageSpecificGroupDetails lsgd = (LanguageSpecificGroupDetails) getSession().createCriteria(LanguageSpecificGroupDetails.class)
													.add(Restrictions.eq("globlgroupId", lspgd.getParameterId().getGroups()))
													.add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
											
											if(lsgd != null) {
												List<LanguageSpecificGroupDetails> lsgpList = null;
												if(lspgMap.containsKey(lspgd.getParameterId().getId())) {
													lsgpList = lspgMap.get(lspgd.getParameterId().getId());
													lsgpList.add(lsgd);
													lspgMap.put(lspgd.getParameterId().getId(), lsgpList);
												}else {
													lsgpList = new ArrayList<>();
													lsgpList.add(lsgd);
													lspgMap.put(lspgd.getParameterId().getId(), lsgpList);
												}
												
											}
										}
									}
								}
							}
						}
					}else if(ga.getActivityCode().equals(StudyActivities.EcgTimePoints.toString())) {
						
					}else if(ga.getActivityCode().equals(StudyActivities.SkinAdhesion.toString())) {
						
					}else if(ga.getActivityCode().equals(StudyActivities.SkinSensivity.toString())) {
						
					}else if(ga.getActivityCode().equals(StudyActivities.OtherActivity.toString())) {
						
					}
				}
				drdList = getSession().createCriteria(DiscripancyReviewDeatails.class)
						       .add(Restrictions.eq("studyId", projectId))
						       .add(Restrictions.eq("activityId", activityId)).list();
			}
			cpuDetailsDto = new CpuActivitiesDetailsDto();
			cpuDetailsDto.setMealsDataList(mealsDataList);
			cpuDetailsDto.setSamplesDtaList(samplesDtaList);
			cpuDetailsDto.setVitalsDtaList(vitalsDtaList);
			cpuDetailsDto.setVitalParmsList(vitalParmsList);
			cpuDetailsDto.setDoseDataList(doseDataList);
			cpuDetailsDto.setDoseParamList(doseParamList);
			cpuDetailsDto.setUser(user);
			cpuDetailsDto.setLspgpdList(lspgpdList);
			cpuDetailsDto.setSubmitdrs(submitdrs);
			cpuDetailsDto.setApprovedrs(approvedrs);
			cpuDetailsDto.setSendCommentsdrs(sendCommentsdrs);
			cpuDetailsDto.setDrdList(drdList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpuDetailsDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificValueDetails> getGlobalValuesList(long parameterId, long controlTypId,
			Long languageId) {
		List<LanguageSpecificValueDetails> lsvdList = null;
		List<Long> gvIds = null;
		try {
			gvIds = getSession().createCriteria(ParameterControlTypeValues.class)
					.add(Restrictions.eq("globalParameter.id", parameterId))
					.add(Restrictions.eq("controlType.id", controlTypId))
					.setProjection(Projections.property("globalValues.id")).list();
			
			lsvdList = getSession().createCriteria(LanguageSpecificValueDetails.class)
					.add(Restrictions.in("globalValId.id", gvIds))
					.add(Restrictions.eq("inlagId.id", languageId)).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsvdList;
	}

	@Override
	public String saveStaticDiscrepancyDetails(Long studyId, Long activityId, Long activityDataId, String fieldName,
			List<String> descComments, String activityType, Long userId, String parameterId) {
		String result = "success";
		SubjectMealsTimePointsData smtpData = null;
		SubjectSampleCollectionTimePointsData ssctpData = null;
		SubjectVitalTimePointsData svtpData = null;
		SubjectVitalParametersData svpData = null;
		SubjectDoseTimePoints sdtp = null;
		SubjectDoseTimePointParametersData sdpData = null;
		UserMaster user = null;
		List<DiscripancyReviewDeatails> disrdList = new ArrayList<>();
		StatusMaster status = null;
		try {
			user = (UserMaster) getSession().createCriteria(UserMaster.class)
					   .add(Restrictions.eq("id", userId)).uniqueResult();
			
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
				    .add(Restrictions.eq("statusCode", StudyStatus.OPEN.toString())).uniqueResult();
			if(activityType.equals("Meals")) {
				smtpData = (SubjectMealsTimePointsData) getSession().createCriteria(SubjectMealsTimePointsData.class)
						           .add(Restrictions.eq("id", activityDataId)).uniqueResult();
				if(smtpData != null) {
					for(String st : descComments) {
						DiscripancyReviewDeatails drd = saveDiscripancyReviewDeatails(descComments, activityId, user, st, fieldName, studyId);
						drd.setSmtpData(smtpData);
						disrdList.add(drd);
					}
				}
			}else if(activityType.equals("Samples")) {
				ssctpData = (SubjectSampleCollectionTimePointsData) getSession().createCriteria(SubjectSampleCollectionTimePointsData.class)
						         .add(Restrictions.eq("id", activityDataId)).uniqueResult();
				if(ssctpData != null) {
					for(String st : descComments) {
						DiscripancyReviewDeatails drd = saveDiscripancyReviewDeatails(descComments, activityId, user, st, fieldName, studyId);
						drd.setSsctpData(ssctpData);
						disrdList.add(drd);
					}
				}
			}else if(activityType.equals("Vitals")) {
				svtpData = (SubjectVitalTimePointsData) getSession().createCriteria(SubjectVitalTimePointsData.class)
						       .add(Restrictions.eq("id", activityDataId)).uniqueResult();
				if(svtpData != null) {
					for(String st : descComments) {
						if(parameterId.equals("0")) {
							DiscripancyReviewDeatails drd = saveDiscripancyReviewDeatails(descComments, activityId, user, st, fieldName, studyId);
							drd.setSvtpData(svtpData);
							disrdList.add(drd);
						}else {
							String[] tempArr = parameterId.split("_");
							if(tempArr.length > 0) {
								svpData = (SubjectVitalParametersData) getSession().createCriteria(SubjectVitalParametersData.class)
										      .add(Restrictions.eq("parameter.id", Long.parseLong(tempArr[1])))
										      .add(Restrictions.eq("subjVitalTpData", svtpData)).uniqueResult();
								if(svpData != null) {
									DiscripancyReviewDeatails drd = saveDiscripancyReviewDeatails(descComments, activityId, user, st, fieldName, studyId);
									drd.setSvtpData(svtpData);
									drd.setSubVitalParmData(svpData);
									disrdList.add(drd);
								}
							}
						}
						
					}
				}
			}else if(activityType.equals("dosing")) {
				sdtp = (SubjectDoseTimePoints) getSession().createCriteria(SubjectDoseTimePoints.class)
					       .add(Restrictions.eq("id", activityDataId)).uniqueResult();
				if(sdtp != null) {
					for(String st : descComments) {
						if(parameterId.equals("0")) {
							DiscripancyReviewDeatails drd = saveDiscripancyReviewDeatails(descComments, activityId, user, st, fieldName, studyId);
							drd.setSubjectDoseId(sdtp);
							disrdList.add(drd);
						}else {
							String[] tempArr = parameterId.split("_");
							if(tempArr.length > 0) {
								sdpData = (SubjectDoseTimePointParametersData) getSession().createCriteria(SubjectDoseTimePointParametersData.class)
										      .add(Restrictions.eq("parameter.id", Long.parseLong(tempArr[1])))
										      .add(Restrictions.eq("subjectDoseTimePoint", sdtp)).uniqueResult();
								if(sdpData != null) {
									DiscripancyReviewDeatails drd = saveDiscripancyReviewDeatails(descComments, activityId, user, st, fieldName, studyId);
									drd.setSubjectDoseId(sdtp);
									drd.setDosingParamId(sdpData);
									disrdList.add(drd);
								}
							}
						}
						
					}
				}
			}
			if(disrdList.size() > 0) {
				for(DiscripancyReviewDeatails drd : disrdList) {
					drd.setStatus(status);
					getSession().save(drd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "Failed";
		}
		return result;
	}

	private DiscripancyReviewDeatails saveDiscripancyReviewDeatails(List<String> descComments, Long activityId,
			UserMaster user, String st, String fieldName, Long studyId) {
		DiscripancyReviewDeatails drd = new DiscripancyReviewDeatails();
		try {
			drd.setActivityId(activityId);
			drd.setCommentedBy(user);
			drd.setCommentedOn(new Date());
			drd.setComments(st);
			drd.setFiledName(fieldName);
			drd.setStudyId(studyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return drd;
	}

	@Override
	public DiscrepancyResponseDto saveStaticDiscrepancyResponseDetails(Long desId, String currentVal, String newVal, String response,
			Long userId, String activityType) {
		DiscrepancyResponseDto drdDto = null;
		String result = "success";
		DiscripancyReviewDeatails drd = null;
		UserMaster user = null;
		StatusMaster status = null;
		SubjectMealsTimePointsData smtpData = null;
		SubjectSampleCollectionTimePointsData ssctpData = null;
		SubjectVitalTimePointsData svtpData = null;
		SubjectVitalParametersData svpData = null;
		SubjectDoseTimePoints sdtp = null;
		SubjectDoseTimePointParametersData sdpData = null;
		SubjectDoseTimePoints sdtpPojo = null;
		try {
			drd = (DiscripancyReviewDeatails) getSession().createCriteria(DiscripancyReviewDeatails.class)
					.add(Restrictions.eq("id", desId)).uniqueResult();
			user = (UserMaster) getSession().createCriteria(UserMaster.class)
					    .add(Restrictions.eq("id", userId)).uniqueResult();
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					    .add(Restrictions.eq("statusCode", StudyStatus.CLOSE.toString())).uniqueResult();
			if(drd != null) {
				drd.setResponse(response);
				drd.setResponseGivenBy(user);
				drd.setResponseGivenDate(new Date());
				drd.setStatus(status);
				getSession().merge(drd);
				if((currentVal != null && !currentVal.equals("undefined")) && (newVal != null && !newVal.equals("undefined"))) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String strDate = "";
					Date finalDate = null;
					if(activityType.equals("Meals")) {
						smtpData = drd.getSmtpData();
						if(smtpData != null) {
							if(drd.getFiledName().equalsIgnoreCase("Start_Time_"+smtpData.getId())) {
								if(smtpData.getStartTime() != null) {
									strDate = sdf.format(smtpData.getStartTime());
									if(strDate != null && !strDate.equals("")) {
										strDate = strDate+" "+newVal+":00";
										finalDate = sdf2.parse(strDate);
									}
								}
								smtpData.setStartTime(finalDate);
								smtpData.setActualtime(finalDate);
							}else if(drd.getFiledName().equalsIgnoreCase("End_Time_"+smtpData.getId())) {
								if(smtpData.getEndTime() != null) {
									strDate = sdf.format(smtpData.getEndTime());
									if(strDate != null && !strDate.equals("")) {
										strDate = strDate+" "+newVal+":00";
										finalDate = sdf2.parse(strDate);
									}
								}
								smtpData.setEndTime(finalDate);
							}else if(drd.getFiledName().equalsIgnoreCase("Consumption_"+smtpData.getId())) 
								smtpData.setConsumption(newVal);
							else if(drd.getFiledName().equalsIgnoreCase("Comments_"+smtpData.getId())) 
								smtpData.setComments(newVal);
							getSession().merge(smtpData);
						}
					}else if(activityType.equals("Samples")) {
						ssctpData = drd.getSsctpData();
						if(drd.getFiledName().equalsIgnoreCase("actualTime_"+ssctpData.getId())) {
							if(ssctpData != null) {
								if(ssctpData.getActualtime() != null) {
									strDate = sdf.format(ssctpData.getActualtime());
									if(strDate != null && !strDate.equals("")) {
										strDate = strDate+" "+newVal+":00";
										finalDate = sdf2.parse(strDate);
										ssctpData.setActualtime(finalDate);
										getSession().merge(ssctpData);
									}
								}
							}
						}
					}else if(activityType.equals("Vitals")) {
						svtpData = drd.getSvtpData();
						svpData = drd.getSubVitalParmData();
						if(svtpData != null) {
							if(drd.getFiledName().equalsIgnoreCase("Start_Time_"+svtpData.getId())) {
								if(svtpData.getStartTime() != null) {
									strDate = sdf.format(svtpData.getStartTime());
									if(strDate != null && !strDate.equals("")) {
										strDate = strDate+" "+newVal+":00";
										finalDate = sdf2.parse(strDate);
									}
								}
								svtpData.setStartTime(finalDate);
								svtpData.setActualtime(finalDate);
								getSession().merge(svtpData);
							}else if(drd.getFiledName().equalsIgnoreCase("End_Time_"+svtpData.getId())) {
								if(svtpData.getEndTime() != null) {
									strDate = sdf.format(svtpData.getEndTime());
									if(strDate != null && !strDate.equals("")) {
										strDate = strDate+" "+newVal+":00";
										finalDate = sdf2.parse(strDate);
										svtpData.setEndTime(finalDate);
										getSession().merge(svtpData);
									}
								}
							}else {
								if(svpData != null) {
									if(drd.getFiledName().equals(svtpData.getId()+"_"+svpData.getParameter().getId())) {
										if(svpData.getParameter().getContentType().getCode().equals("RB")||svpData.getParameter().getContentType().getCode().equals("CB")
												|| svpData.getParameter().getContentType().getCode().equals("SB")) {
											GlobalValues gv = (GlobalValues) getSession().createCriteria(GlobalValues.class)
													             .add(Restrictions.eq("id", Long.parseLong(newVal))).uniqueResult();
											if(gv != null) {
												svpData.setGlobalValue(gv);
												getSession().merge(svpData);
											}
										}else {
											svpData.setParameterValue(newVal);
											getSession().merge(svpData);
										}
									}
								}
								
							}
						}
					}else if(activityType.equals("dosing")) {
						sdtp = drd.getSubjectDoseId();
						sdpData = drd.getDosingParamId();
						if(drd.getFiledName().equalsIgnoreCase("actualTime_"+sdtp.getId())) {
							if(sdtp.getActualTime() != null) {
								strDate = sdf.format(sdtp.getActualTime());
								if(strDate != null && !strDate.equals("")) {
									strDate = strDate+" "+newVal+":00";
									finalDate = sdf2.parse(strDate);
									sdtp.setActualTime(finalDate);
									getSession().merge(sdtp);
									Long minId = (Long) getSession().createCriteria(DoseTimePoints.class)
											      .add(Restrictions.eq("study", sdtp.getStudySubjects().getStudy()))
											      .add(Restrictions.eq("treatmentInfo", sdtp.getDoseTimePoints().getTreatmentInfo()))
											      .setProjection(Projections.property("id")).uniqueResult();
									if(minId != null) {
										if(sdtp.getDoseTimePoints().getId() == minId)
											sdtpPojo = sdtp;
									}
									
								}
							}
						}else {
							if(sdpData != null) {
								if(drd.getFiledName().equals(sdtp.getId()+"_"+sdpData.getParameter().getId())) {
									if(sdpData.getParameter().getContentType().getCode().equals("RB")||sdpData.getParameter().getContentType().getCode().equals("CB")
											|| sdpData.getParameter().getContentType().getCode().equals("SB")) {
										GlobalValues gv = (GlobalValues) getSession().createCriteria(GlobalValues.class)
												             .add(Restrictions.eq("id", Long.parseLong(newVal))).uniqueResult();
										if(gv != null) {
											sdpData.setGlobalValue(gv);
											getSession().merge(sdpData);
										}
									}else {
										sdpData.setParameterValue(newVal);
										getSession().merge(sdpData);
									}
								}
							}
						}
					}
				}
			}
			drdDto = new DiscrepancyResponseDto();
			drdDto.setResult(result);
			drdDto.setSdtp(sdtpPojo);
		} catch (Exception e) {
			e.printStackTrace();
			return drdDto;
		}
		return drdDto;
	}

}
