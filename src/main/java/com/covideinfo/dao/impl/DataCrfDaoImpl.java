package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.DataCrfDao;
import com.covideinfo.dto.CrfDataDto;
import com.covideinfo.dto.DataCrfDetailsPageDto;
import com.covideinfo.dto.RecannulationDataDto;
import com.covideinfo.dto.UserMasterDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.ActivityDraftReviewAudit;
import com.covideinfo.model.ApplicationConfiguration;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.DosingInfoDetails;
import com.covideinfo.model.GlobalGroups;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StaticActivityDataDetails;
import com.covideinfo.model.StaticActivityDetails;
import com.covideinfo.model.StaticActivityValueDetails;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyCheckInActivityDataDetails;
import com.covideinfo.model.StudyCheckOutActivityDataDetails;
import com.covideinfo.model.StudyExecutionActivityDataDetails;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjectReCannulationData;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.SubjectDoseTimePointParametersData;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectVitalParametersData;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.model.SubjectWithdrawDetails;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.VitalTimePoints;

@Repository("dataCrfDao")
public class DataCrfDaoImpl extends AbstractDao<Long, StudyMaster> implements DataCrfDao {

	@SuppressWarnings("unchecked")
	@Override
	public DataCrfDetailsPageDto getDataCrfDetailsPageDtoDetails() {
		DataCrfDetailsPageDto dcdpDto = null;
		List<StudyMaster> smList;
		try {
			smList = getSession().createCriteria(StudyMaster.class).list();
			dcdpDto = new DataCrfDetailsPageDto();
			dcdpDto.setSmList(smList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dcdpDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataCrfDetailsPageDto getStudyCrfDataDetails(Long studyId, Long languageId) {
		DataCrfDetailsPageDto dcdpDto = null;
		List<StudySubjects> studySubList;
		List<StudyPeriodMaster> spmList;
		List<LanguageSpecificGlobalActivityDetails> lspgaList;
		List<StudyVolunteerReporting> stdVolList = null;
		try {
			studySubList = getSession().createCriteria(StudySubjects.class)
					.add(Restrictions.eq("study.id", studyId)).list();
			spmList = getSession().createCriteria(StudyPeriodMaster.class)
					.add(Restrictions.eq("study.id", studyId)).list();
			lspgaList = getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
					.add(Restrictions.eq("inlagId.id", languageId)).list();
			
			stdVolList = getSession().createCriteria(StudyVolunteerReporting.class)
					        .add(Restrictions.isNull("subjectNo"))
					    	.add(Restrictions.eq("sm.id", studyId)).list();
			
			dcdpDto = new DataCrfDetailsPageDto();
			dcdpDto.setLspgaList(lspgaList);
			dcdpDto.setSpmList(spmList);
			dcdpDto.setStudySubList(studySubList);
			dcdpDto.setStdVolList(stdVolList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dcdpDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CrfDataDto getStudyCrfDataDtoDetails(Long studyId, Long langId, Long userId) {
		CrfDataDto crfDto = null;
		List<LanguageSpecificGlobalActivityDetails> lsgadList = null;
		List<LanguageSpecificGlobalParameterDetails> lsgpdList = null;
		List<LanguageSpecificValueDetails> lsgvList = null;
		List<StudySubjectPeriods> subList = null; 
		List<StudyPeriodMaster> spmList = null;
		List<StudyActivityData> saDataList = null;
		List<StudyCheckInActivityDataDetails> stdChkinList = null;
		List<StudyCheckOutActivityDataDetails> stdChkoutList = null;
		List<StudyExecutionActivityDataDetails> stdExList = null;
		List<SubjectDoseTimePoints> sdtpList = null;
		List<SubjectDoseTimePointParametersData> sdtpparamdList = null;
		List<SubjectVitalTimePointsData> stdVitalList = null;
		List<SubjectVitalParametersData> stdVitalParamList = null;
		List<SubjectSampleCollectionTimePointsData> subSapColTpDataList = null;
		List<SubjectMealsTimePointsData> subMealTpDataList = null;
		List<StudyTreatmentWiseSubjects> stwSubjects = null;
		List<DefaultActivitys> defalutActList = null;
		List<Long> subjectIds = null;
		List<Long> studyActIds = null;
		List<Long> studyActDataIds = null;
		StudyMaster study = null;
		ApplicationConfiguration appConfig = null;
		StatusMaster status = null;
		UserMaster user = null;
		List<StudyVolunteerReporting> stdVolList = null;
		List<MealsTimePoints> mealsList = null;
		List<DoseTimePoints> doseList = null;
		List<SampleTimePoints> samplesList = null;
	    List<VitalTimePoints> vitalList = null;
	    Projects project = null;
	    List<StudyActivities> studyActList = null;
	    List<StudyActivityParameters> studyActParamList = null;
	    List<Long> volIdsList = null;
	    List<StaticActivityDataDetails> saddList = null;
		List<StaticActivityValueDetails> savdList = null;
		List<StaticActivityDetails> staticActList = null;
		List<SubjectWithdrawDetails> subjWithDarawList = null;
		List<TreatmentInfo> treatmentsList = null;
		ActivityDraftReviewAudit adrPojo = null;
		List<DosingInfoDetails> druginfList = null;
		List<ProjectsDetails> sampleProList = null;
		List<RecannulationDataDto> recdDtoList = null;
		List<UserMasterDto> usersList = null;
		try {
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					.add(Restrictions.eq("id", studyId)).uniqueResult();
			
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					   .add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
			
			user = (UserMaster) getSession().createCriteria(UserMaster.class)
					.add(Restrictions.eq("id", userId)).uniqueResult();
			
			appConfig = (ApplicationConfiguration) getSession().createCriteria(ApplicationConfiguration.class)
					    .add(Restrictions.eq("configuration", "Pdf Header"))
					    .add(Restrictions.eq("status", status)).uniqueResult();
			
			lsgadList = getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
							.add(Restrictions.eq("inlagId.id", langId)).list();
			
			defalutActList = getSession().createCriteria(DefaultActivitys.class).list();
			
			lsgpdList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
					    .add(Restrictions.eq("inlagId.id", langId)).list();
			
			lsgvList = getSession().createCriteria(LanguageSpecificValueDetails.class)
					.add(Restrictions.eq("inlagId.id", langId)).list();
			
			subjectIds = getSession().createCriteria(StudySubjects.class)
					    .add(Restrictions.eq("study.id", studyId))
					    .setProjection(Projections.property("id")).list();
			
			volIdsList = getSession().createCriteria(StudyVolunteerReporting.class)
					              .add(Restrictions.eq("sm.id", studyId))
					              .setProjection(Projections.property("id")).list();
			
			if(subjectIds != null && subjectIds.size() >0) {
				
				subList = getSession().createCriteria(StudySubjectPeriods.class)
						.add(Restrictions.in("subject.id", subjectIds)).list();
				
				sdtpList = getSession().createCriteria(SubjectDoseTimePoints.class)
					    .add(Restrictions.in("studySubjects.id", subjectIds)).list();
				
				subSapColTpDataList = getSession().createCriteria(SubjectSampleCollectionTimePointsData.class)
			               .add(Restrictions.in("subject.id", subjectIds)).list();
				
				subMealTpDataList = getSession().createCriteria(SubjectMealsTimePointsData.class)
						              .add(Restrictions.in("subject.id", subjectIds)).list();
			}
			spmList = getSession().createCriteria(StudyPeriodMaster.class)
					.add(Restrictions.eq("study.id", studyId)).list();
			
			
			studyActIds = getSession().createCriteria(StudyActivities.class)
					      .add(Restrictions.eq("sm.id", studyId))
					      .setProjection(Projections.property("id")).list();
			
			if(volIdsList != null && volIdsList.size() > 0) {
				studyActDataIds = getSession().createCriteria(StudyActivityData.class)
						.add(Restrictions.in("volunteerId.id", volIdsList))
						.setProjection(Projections.property("id")).list();
				if(studyActDataIds != null && studyActDataIds.size() > 0) {
					
					stdChkinList = getSession().createCriteria(StudyCheckInActivityDataDetails.class)
							       .add(Restrictions.in("saData.id", studyActDataIds)).list();
					
					stdChkoutList = getSession().createCriteria(StudyCheckOutActivityDataDetails.class)
							        .add(Restrictions.in("saData.id", studyActDataIds)).list();
					
					stdExList = getSession().createCriteria(StudyExecutionActivityDataDetails.class)
							     .add(Restrictions.in("saData.id", studyActDataIds)).list();
				}
			}
			
			if(studyActIds.size() > 0) {
				saDataList = getSession().createCriteria(StudyActivityData.class)
						.add(Restrictions.in("sutydActivity.id", studyActIds)).list();
				
				/*studyActDataIds = getSession().createCriteria(StudyActivityData.class)
						.add(Restrictions.in("sutydActivity.id", studyActIds))
						.setProjection(Projections.property("id")).list();
				if(studyActDataIds != null && studyActDataIds.size() > 0) {
					
					stdChkinList = getSession().createCriteria(StudyCheckInActivityDataDetails.class)
							       .add(Restrictions.in("saData.id", studyActDataIds)).list();
					
					stdChkoutList = getSession().createCriteria(StudyCheckOutActivityDataDetails.class)
							        .add(Restrictions.in("saData.id", studyActDataIds)).list();
					
					stdExList = getSession().createCriteria(StudyExecutionActivityDataDetails.class)
							     .add(Restrictions.in("saData.id", studyActDataIds)).list();
				}*/
				
				studyActParamList = getSession().createCriteria(StudyActivityParameters.class)
						.add(Restrictions.in("studyActivity.id", studyActIds)).list();
			}
			
			sdtpparamdList = getSession().createCriteria(SubjectDoseTimePointParametersData.class)
					.add(Restrictions.eq("studyId", studyId)).list();
			
			stdVitalList = getSession().createCriteria(SubjectVitalTimePointsData.class)
					       .add(Restrictions.eq("study.id", studyId)).list();
			
			stdVitalParamList = getSession().createCriteria(SubjectVitalParametersData.class)
					       .add(Restrictions.eq("studyId", studyId)).list();
			
			stwSubjects = getSession().createCriteria(StudyTreatmentWiseSubjects.class)
					.add(Restrictions.eq("sm.id", studyId)).list();
			
			stdVolList = getSession().createCriteria(StudyVolunteerReporting.class)
					         .add(Restrictions.eq("sm.id", studyId)).list();
			
			mealsList = getSession().createCriteria(MealsTimePoints.class)
					     .add(Restrictions.eq("study.id", studyId)).list();
			
			doseList = getSession().createCriteria(DoseTimePoints.class)
					.add(Restrictions.eq("study.id", studyId)).list();
			
			samplesList = getSession().createCriteria(SampleTimePoints.class)
					     .add(Restrictions.eq("study.id", studyId)).list();
			
			vitalList = getSession().createCriteria(VitalTimePoints.class)
					     .add(Restrictions.eq("study.id", studyId)).list();
			if(study != null) {
				project = (Projects) getSession().createCriteria(Projects.class)
						.add(Restrictions.eq("projectNo", study.getProjectNo())).uniqueResult();
				
				if(project != null) {
					
					RoleMaster role = (RoleMaster) getSession().createCriteria(RoleMaster.class)
							              .add(Restrictions.eq("role", "PI")).uniqueResult();
					if(role != null) {
						adrPojo = (ActivityDraftReviewAudit) getSession().createCriteria(ActivityDraftReviewAudit.class)
					            .add(Restrictions.eq("projectId", project.getProjectId()))
					            .add(Restrictions.eq("role", role))
					            .add(Restrictions.eq("status", "APPROVAL")).uniqueResult();
					}
					if(adrPojo == null) {
						Long adrMaxId = (Long) getSession().createCriteria(ActivityDraftReviewAudit.class)
					            .add(Restrictions.eq("projectId", project.getProjectId()))
					            .add(Restrictions.eq("status", "APPROVAL"))
					            .setProjection(Projections.max("id")).uniqueResult();
						if(adrMaxId != null) {
							adrPojo = (ActivityDraftReviewAudit) getSession().createCriteria(ActivityDraftReviewAudit.class)
									.add(Restrictions.eq("id", adrMaxId)).uniqueResult();
						}
					}
					
					List<String> fieldNamesList = new ArrayList<>();
					fieldNamesList.add("LIGHTCONDITION");
					fieldNamesList.add("TYPEOFVACUTAINERUSED");
					fieldNamesList.add("VOLUMNOFBLOOD");
					fieldNamesList.add("TREATMENT");
					sampleProList = getSession().createCriteria(ProjectsDetails.class)
							           .add(Restrictions.eq("projectsId.projectId", project.getProjectId()))
							           .add(Restrictions.eq("type", "sampleInformation"))
							           .add(Restrictions.in("fieldName", fieldNamesList)).list();
				}
				
				studyActList = getSession().createCriteria(StudyActivities.class)
				         .add(Restrictions.eq("sm", study)).list();
				
				subjWithDarawList = getSession().createCriteria(SubjectWithdrawDetails.class)
						                  .add(Restrictions.eq("study", study)).list();
				List<Long> swdIds = getSession().createCriteria(SubjectWithdrawDetails.class)
										.add(Restrictions.eq("study", study))
										.setProjection(Projections.property("id")).list();
				if(swdIds != null && swdIds.size() > 0) {
					saddList = getSession().createCriteria(StaticActivityDataDetails.class)
					           .add(Restrictions.in("subjectWithDrawDetails.id", swdIds)).list();
				}
						                                              
				staticActList = getSession().createCriteria(StaticActivityDetails.class).list();
				savdList = getSession().createCriteria(StaticActivityValueDetails.class).list();
			}
			treatmentsList = getSession().createCriteria(TreatmentInfo.class)
					              .add(Restrictions.eq("study", study)).list();
			
			druginfList = getSession().createCriteria(DosingInfoDetails.class)
					                                .add(Restrictions.eq("study.id", studyId)).list();
			
			recdDtoList = getSession().createCriteria(StudySubjectReCannulationData.class)
					          .add(Restrictions.eq("studyId.id", studyId))
					          .add(Restrictions.eq("reCannulation", true))
					          .createAlias("sampleTimePoint", "tp", JoinType.LEFT_OUTER_JOIN)
					          .createAlias("studyId", "std")
					          .createAlias("subject", "sub")
					          .createAlias("period", "spm")
					          .setProjection(Projections.projectionList()
					        		 .add(Projections.property("std.id"), "studyId")
					        		 .add(Projections.property("spm.id"), "periodId")
					        		 .add(Projections.property("sub.id"), "subjectId")
					        		 .add(Projections.property("tp.id"), "sampleId")
					        		 .add(Projections.property("reason"), "reason")
					        		 .add(Projections.property("createdBy"), "doneBy")
					        		 .add(Projections.property("createdOn"), "recannulationDate")
					        		 .add(Projections.property("reCannulation"), "recannula")
					        		 .add(Projections.property("cannuleRemoved"), "cannulaRemoved"))
					          .setResultTransformer(Transformers.aliasToBean(RecannulationDataDto.class)).list();
					
			usersList = getSession().createCriteria(UserMaster.class)
				       .add(Restrictions.eq("accountNotDisable", true))
				       .setProjection(Projections.projectionList()
				    		  .add(Projections.property("id"), "id")
				    		  .add(Projections.property("username"), "userName")
				    		  .add(Projections.property("fullName"), "userFullName"))
				       .setResultTransformer(Transformers.aliasToBean(UserMasterDto.class)).list();	
			
			crfDto = new CrfDataDto();
			crfDto.setStudy(study);
			crfDto.setAppConfig(appConfig);
			crfDto.setUser(user);
			crfDto.setLsgadList(lsgadList);
			crfDto.setLsgpdList(lsgpdList);
			crfDto.setLsgvList(lsgvList);
			crfDto.setSubList(subList);
			crfDto.setSpmList(spmList);
			crfDto.setSaDataList(saDataList);
			crfDto.setStdChkinList(stdChkinList);
			crfDto.setStdChkoutList(stdChkoutList);
			crfDto.setStdExList(stdExList);
			crfDto.setSdtpList(sdtpList);
			crfDto.setSdtpparamdList(sdtpparamdList);
			crfDto.setStdVitalList(stdVitalList);
			crfDto.setStdVitalParamList(stdVitalParamList);
			crfDto.setSubSapColTpDataList(subSapColTpDataList);
			crfDto.setSubMealTpDataList(subMealTpDataList);
			crfDto.setDefalutActList(defalutActList);
			crfDto.setStwSubjects(stwSubjects);
			crfDto.setStdVolList(stdVolList);
			crfDto.setMealsList(mealsList);
			crfDto.setDoseList(doseList);
			crfDto.setSamplesList(samplesList);
			crfDto.setVitalList(vitalList);
			crfDto.setProject(project);
			crfDto.setStudyActList(studyActList);
			crfDto.setStudyActParamList(studyActParamList);
			crfDto.setSaddList(saddList);
			crfDto.setSavdList(savdList);
			crfDto.setStaticActList(staticActList);
			crfDto.setSubjWithDarawList(subjWithDarawList);
			crfDto.setTreatmentsList(treatmentsList);
			crfDto.setAdrPojo(adrPojo);
			crfDto.setDruginfList(druginfList);
			crfDto.setSampleProList(sampleProList);
			crfDto.setRecdDtoList(recdDtoList);
			crfDto.setUsersList(usersList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return crfDto;
	}

	@Override
	public String getLanguageSpecificGroupName(GlobalGroups groups, Long langId) {
		return (String) getSession().createCriteria(LanguageSpecificGroupDetails.class)
				.add(Restrictions.eq("globlgroupId", groups))
				.add(Restrictions.eq("inlagId.id", langId))
				.setProjection(Projections.property("name")).uniqueResult();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificValueDetails> getLanguageSpecificGlobalValusesList(GlobalParameter parameterId,
			Long langId) {
		List<LanguageSpecificValueDetails> lsgvList = null;
		List<Long> gvIds = null;
		try {
			gvIds = getSession().createCriteria(ParameterControlTypeValues.class)
					.add(Restrictions.eq("globalParameter", parameterId))
					.add(Restrictions.eq("controlType", parameterId.getContentType()))
					.setProjection(Projections.property("globalValues.id")).list();
			if(gvIds != null && gvIds.size() > 0) {
				lsgvList = getSession().createCriteria(LanguageSpecificValueDetails.class)
						.add(Restrictions.in("globalValId.id", gvIds))
						.add(Restrictions.eq("inlagId.id", langId)).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsgvList;
	}

	@Override
	public LanguageSpecificGlobalParameterDetails getLanguageSpecificParametersDetailsRecord(Long langId, long id) {
		return (LanguageSpecificGlobalParameterDetails) getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
				.add(Restrictions.eq("parameterId.id", id))
				.add(Restrictions.eq("inlagId.id", langId)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificGlobalParameterDetails> getLanguageSpecificGlobalParametersFromStudyActivitParameters(
			long id, Long langId, Set<Long> vtparamIds) {
		return getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
				.add(Restrictions.eq("inlagId.id", langId))
				.add(Restrictions.in("parameterId.id", vtparamIds)).list();
	}

	@Override
	public TreatmentInfo getTreatmentInfoDetails(long l) {
		return (TreatmentInfo) getSession().createCriteria(TreatmentInfo.class)
				.add(Restrictions.eq("id", l)).uniqueResult();
	}

}
