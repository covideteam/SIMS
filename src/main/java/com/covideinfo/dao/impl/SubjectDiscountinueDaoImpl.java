package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.SubjectDiscountinueDao;
import com.covideinfo.dto.StudyWitdrawalSavingDto;
import com.covideinfo.dto.StudyWithDrawDetailsDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.StaticActivityDataDetails;
import com.covideinfo.model.StaticActivityDetails;
import com.covideinfo.model.StaticActivityValueDetails;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectWithdrawDetails;
import com.covideinfo.model.UserMaster;

@Repository("subjectDiscountinueDao")
public class SubjectDiscountinueDaoImpl extends AbstractDao<Long, StudySubjects> implements SubjectDiscountinueDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalParameter> getGlobalparametersList(List<Long> parmIdsList) {
		return getSession().createCriteria(GlobalParameter.class)
				.add(Restrictions.in("id", parmIdsList)).list();
	}

	
	@Override
	public CustomActivityParameters getCustomActivityParametersRecord(Long activityId) {
		return (CustomActivityParameters) getSession().createCriteria(CustomActivityParameters.class)
				.add(Restrictions.eq("activity.id", activityId)).uniqueResult();
	}

	@Override
	public StudySubjects getStudySubjectRecord(Long volId) {
		return (StudySubjects) getSession().createCriteria(StudySubjects.class)
				.add(Restrictions.eq("id", volId)).uniqueResult();
	}

	@Override
	public String updateStudySubjectRecord(StudySubjects ssub) {
		String result = "Failed";
		try {
			getSession().update(ssub);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}


	@SuppressWarnings("unchecked")
	@Override
	public StudyWithDrawDetailsDto getStudyWithDrawDetailsDtoDetails(Long studyId) {
		StudyWithDrawDetailsDto swddDto = null;
		List<StaticActivityDetails> actList = null;
		List<StaticActivityValueDetails> actValList = null;
		List<String> actTypeList = new ArrayList<>();
		List<Long> actIds = null;
		try {
			actTypeList.add("Withdrawal");
			actTypeList.add("DroupOut");
			actList = getSession().createCriteria(StaticActivityDetails.class)
					        .add(Restrictions.in("activityType", actTypeList)).list();
			
			actIds = getSession().createCriteria(StaticActivityDetails.class)
			        .add(Restrictions.in("activityType", actTypeList))
			        .setProjection(Projections.property("id")).list();
			if(actIds != null && actIds.size() > 0) {
				actValList = getSession().createCriteria(StaticActivityValueDetails.class)
						          .add(Restrictions.in("stActDetailsId.id", actIds)).list();
			}
			swddDto = new StudyWithDrawDetailsDto();
			swddDto.setActList(actList);
			swddDto.setActValList(actValList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return swddDto;
	}


	@SuppressWarnings("unchecked")
	@Override
	public String saveStaticActivityDataDetails(StudyWitdrawalSavingDto swsDto, Long userId) {
		String result ="Failed";
		StudyVolunteerReporting svr = null;
		StudyMaster study = null;
		GlobalActivity ga = null;
		List<StaticActivityDetails> sadList = null;
		List<Long> paramValIds = new ArrayList<>();
		UserMaster user = null;
		boolean savingFlag = false;
		List<StaticActivityValueDetails> savdList = null;
		boolean finalFlag = false;
		StatusMaster status = null;
		StudySubjects subject = null;
		StudyPeriodMaster spm = null;
		StatusMaster activeStatus = null;
		try {
			if(swsDto.getParameterValueIds().size() > 0) {
				for(String st : swsDto.getParameterValueIds()) {
					if(st != null && !st.equals("0")) {
						String[] tempArr = st.split("\\@@@");
						if(tempArr.length > 0) {
							paramValIds.add(Long.parseLong(tempArr[1]));
						}
					}
				}
			}
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					   .add(Restrictions.eq("id", swsDto.getProjectId())).uniqueResult();
			
			user = (UserMaster) getSession().createCriteria(UserMaster.class)
					   .add(Restrictions.eq("id", userId)).uniqueResult();
			
			if(study != null) {
				svr = (StudyVolunteerReporting) getSession().createCriteria(StudyVolunteerReporting.class)
						   .add(Restrictions.eq("id", swsDto.getVolunteer()))
						   .add(Restrictions.eq("sm", study)).uniqueResult();
				
				ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
						 .add(Restrictions.eq("id", swsDto.getActivityId())).uniqueResult();
				
				
				sadList = getSession().createCriteria(StaticActivityDetails.class)
						     .add(Restrictions.in("id", swsDto.getParameterIds())).list();
				
				if(paramValIds != null && paramValIds.size() > 0) {
					savdList = getSession().createCriteria(StaticActivityValueDetails.class)
						       .add(Restrictions.in("id", paramValIds)).list();
				}
				
				subject = (StudySubjects) getSession().createCriteria(StudySubjects.class)
		                  .add(Restrictions.eq("reportingId", svr)).uniqueResult();
				
				activeStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
								.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
				if(subject != null) {
					spm = (StudyPeriodMaster) getSession().createCriteria(StudySubjectPeriods.class)
			                   .add(Restrictions.eq("subject", subject))
			                   .add(Restrictions.eq("status", activeStatus))
			                   .setProjection(Projections.property("periodId")).uniqueResult();
				}else {
					spm = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
							  .add(Restrictions.eq("study", study))
							  .add(Restrictions.eq("periodNo", 1)).uniqueResult();
				}
			}
			if((sadList != null && sadList.size() > 0 ) || (savdList != null && savdList.size() > 0)) {
				SubjectWithdrawDetails swdPojo = (SubjectWithdrawDetails) getSession().createCriteria(SubjectWithdrawDetails.class)
						                              .add(Restrictions.eq("study", study))
						                              .add(Restrictions.eq("globalActivity", ga))
						                              .add(Restrictions.eq("studyVolunteer", svr)).uniqueResult();
				if(swdPojo == null) {
					SubjectWithdrawDetails swd = new SubjectWithdrawDetails();
					swd.setCreatedBy(user.getUsername());
					swd.setStudy(study);
					swd.setCreatedOn(new Date());
					swd.setGlobalActivity(ga);
					swd.setStudyVolunteer(svr);
					swd.setPeriod(spm);
					swd.setWithdrawType(swsDto.getType());
					swd.setWithdrawLevel(swsDto.getWithdrawLevel());
					swd.setReason("");
					swd.setWithdrawComments(swsDto.getComments());
					long swdNo = (long) getSession().save(swd);
					if(swdNo > 0) {
						if(sadList != null && sadList.size() > 0 ) {
							for(StaticActivityDetails sad : sadList) {
								try {
									StaticActivityDataDetails sadd = new StaticActivityDataDetails();
									sadd.setCreatedBy(user.getUsername());
									sadd.setCreatedOn(new Date());
									sadd.setReason("");
									sadd.setStaticActDetailsId(sad);
									sadd.setSubjectWithDrawDetails(swd);
									Long saddNo = (Long) getSession().save(sadd);
									if(saddNo > 0)
										savingFlag = true;
								} catch (Exception e) {
									e.printStackTrace();
									savingFlag = false;
								}
								
							}
						}
						if(savdList != null && savdList.size() > 0) {
							for(StaticActivityValueDetails savd : savdList) {
								try {
									StaticActivityDataDetails sadd = new StaticActivityDataDetails();
									sadd.setCreatedBy(user.getUsername());
									sadd.setCreatedOn(new Date());
									sadd.setReason("");
									sadd.setStaticActDetailsId(savd.getStActDetailsId());
									sadd.setSaticActValueDetailsId(savd);
									sadd.setSubjectWithDrawDetails(swd);
									Long saddNo = (Long) getSession().save(sadd);
									if(saddNo > 0)
										savingFlag = true;
								} catch (Exception e) {
									e.printStackTrace();
									savingFlag = false;
								}
								
							}
						}
					}
					if(swdNo > 0 && savingFlag) {
						if(subject != null) {
							if(spm != null) {
								List<SubjectDoseTimePoints> sdtpList = getSession().createCriteria(SubjectDoseTimePoints.class)
		                                 .add(Restrictions.eq("studySubjects", subject))
		                                 .add(Restrictions.eq("period", spm)).list();
								
								if(sdtpList != null && sdtpList.size() > 0) {
									subject.setSubjectDiscontinue("");
				    				subject.setUpatedBy(user.getUsername());
				    				subject.setUpdatedOn(new Date());
				    				if(swsDto.getType().equals("Withdrawal")) {
				    					subject.setUpdateReason("Subject Withdrawn From "+swsDto.getWithdrawLevel());
				    					subject.setSubjectStatus("Withdraw");
				    				}else { 
				    					subject.setUpdateReason("Subject DropOut From "+swsDto.getWithdrawLevel());
				    					subject.setSubjectStatus("DropOut");
				    				}
				    				/*if(swsDto.getWithdrawLevel().equals("Study")) 
				    					subject.setSubjectStatus("inactive");*/
				    				getSession().merge(subject);
				    				finalFlag = true;
								}else {
									subject.setSubjectReplace("Yes");
				    				subject.setSubjectDiscontinue("");
				    				subject.setUpatedBy(user.getUsername());
				    				subject.setUpdatedOn(new Date());
				    				if(swsDto.getType().equals("Withdrawal")) {
				    					subject.setUpdateReason("Subject Withdrawn From "+swsDto.getWithdrawLevel());
				    					subject.setSubjectStatus("Withdraw");
				    				}else { 
				    					subject.setUpdateReason("Subject DropOut From "+swsDto.getWithdrawLevel());
				    					subject.setSubjectStatus("DropOut");
				    				}
				    				getSession().merge(subject);
				    				finalFlag = true;
				    				
								}
							}
							
						}else {
							status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
									     .add(Restrictions.eq("statusCode", StudyStatus.INACTIVE.toString())).uniqueResult();
							svr.setStatus(status);
							if(swsDto.getType().equals("Withdrawal")) 
							   svr.setComments("Volunteer withdrawn from "+swsDto.getWithdrawLevel()+".");
							else svr.setComments("Volunteer Dropout from "+swsDto.getWithdrawLevel()+".");
							svr.setUpdatedBy(user.getUsername());
							svr.setUpdatedOn(new Date());
							getSession().merge(svr);
							finalFlag = true;
						}
					}
				}else result="Duplicate";
			}
			if(finalFlag) {
				result = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "Failed";
		}
		return result;
	}

}
