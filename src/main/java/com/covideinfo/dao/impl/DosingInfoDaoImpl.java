package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.DosingInfoDao;
import com.covideinfo.dto.DataInfoPdfGenerationDto;
import com.covideinfo.dto.DosingDataInfoDto;
import com.covideinfo.dto.TimePointDtoDetails;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.DosingInfo;
import com.covideinfo.model.DosingInfoData;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.UserMaster;

@Repository("dosingInfoDao")
public class DosingInfoDaoImpl extends AbstractDao<Long, StudyMaster> implements DosingInfoDao {

	@SuppressWarnings("unchecked")
	public TimePointDtoDetails getTimePointDtoDetails(long projectId) {
		TimePointDtoDetails tpDto = null;
		Projects project = null;
		List<ProjectsDetails> dosetpList = null;
		List<ProjectsDetails> sampleTpList = null;
		List<ProjectsDetails> mealTpList = null;
		List<ProjectsDetails> vtpList = null;
		List<ProjectsDetails> treatmentList = null;
		DosingInfo doseInfo = null;
		List<DosingInfoData> dosInfoDataList = null;
		List<ProjectsDetails> skinSensList = null;
		List<ProjectsDetails> skinAdhList = null;
		List<ProjectsDetails> ecgList = null;
		List<ProjectsDetails> otherActList = null;
		Map<String, List<ProjectsDetails>> paramMap = new HashMap<>();
		List<ProjectsDetails> paramTempList = null;
		int noOfSubjects = 0;
		int noOfStandbySubjects = 0;
		try {
			project = (Projects) getSession().createCriteria(Projects.class)
					    .add(Restrictions.eq("projectId", projectId)).uniqueResult();
			if(project != null) {
				dosetpList = getSession().createCriteria(ProjectsDetails.class)
						        .add(Restrictions.eq("type", "DosingTimepoints"))
						        .add(Restrictions.eq("fieldName", "doseTimePoint"))
						        .add(Restrictions.eq("projectsId", project)).list();
				
				sampleTpList = getSession().createCriteria(ProjectsDetails.class)
								.add(Restrictions.eq("type", "sampleTimePoins"))
						        .add(Restrictions.eq("fieldName", "TIMEPOINT"))
						        .add(Restrictions.eq("projectsId", project)).list();
				
				mealTpList = getSession().createCriteria(ProjectsDetails.class)
								.add(Restrictions.eq("type", "mealsTimePointInformation"))
						        .add(Restrictions.eq("fieldName", "TIMEPOINT"))
						        .add(Restrictions.eq("projectsId", project)).list();
				
				vtpList = getSession().createCriteria(ProjectsDetails.class)
								.add(Restrictions.eq("type", "vitalTimepointInformation"))
						        .add(Restrictions.eq("fieldName", "TIMEPOINT"))
						        .add(Restrictions.eq("projectsId", project)).list();
				
				skinSensList = getSession().createCriteria(ProjectsDetails.class)
						.add(Restrictions.eq("type", "SkinSensivity"))
				        .add(Restrictions.eq("fieldName", "TIMEPOINT"))
				        .add(Restrictions.eq("projectsId", project)).list();
				
				skinAdhList = getSession().createCriteria(ProjectsDetails.class)
						.add(Restrictions.eq("type", "SkinAdhesion"))
				        .add(Restrictions.eq("fieldName", "TIMEPOINT"))
				        .add(Restrictions.eq("projectsId", project)).list();
				
				ecgList = getSession().createCriteria(ProjectsDetails.class)
						.add(Restrictions.eq("type", "ecgTimePoins"))
				        .add(Restrictions.eq("fieldName", "TIMEPOINT"))
				        .add(Restrictions.eq("projectsId", project)).list();
				
				otherActList = getSession().createCriteria(ProjectsDetails.class)
						.add(Restrictions.eq("type", "OtherActivity"))
				        .add(Restrictions.eq("fieldName", "TIMEPOINT"))
				        .add(Restrictions.eq("projectsId", project)).list();
				
				List<String> trStrList = new ArrayList<>();
				trStrList.add("randomizationCode");
				trStrList.add("treatmentName");
				trStrList.add("TREATMENT");
				/*treatmentList = getSession().createCriteria(ProjectsDetails.class)
								.add(Restrictions.eq("type", "TreatmentWiseInformation"))
						        .add(Restrictions.eq("fieldName", "treatmentName"))
						        .add(Restrictions.eq("projectsId", project)).list();*/
				
				
				treatmentList = getSession().createCriteria(ProjectsDetails.class)
						           .add(Restrictions.eq("type", "TreatmentWiseInformation"))
						           .add(Restrictions.in("fieldName", trStrList))
						           .add(Restrictions.eq("projectsId", project)).list();
				
				doseInfo = (DosingInfo) getSession().createCriteria(DosingInfo.class)
						         .add(Restrictions.eq("projects", project)).uniqueResult();
				
				dosInfoDataList = getSession().createCriteria(DosingInfoData.class)
						            .add(Restrictions.eq("dosingInfo", doseInfo))
						            .add(Restrictions.eq("projectId", project.getProjectId())).list();
				
				//Prameters
				paramTempList = getSession().createCriteria(ProjectsDetails.class)
						            .add(Restrictions.eq("fieldName", "PARAMETERDESC"))
						            .add(Restrictions.eq("type", "DosingParameters"))
						            .add(Restrictions.eq("projectsId", project)).list();
				
				if(paramTempList != null && paramTempList.size() > 0) 
					paramMap.put("DosingParameters", paramTempList);
				
				paramTempList = getSession().createCriteria(ProjectsDetails.class)
			            .add(Restrictions.eq("fieldName", "PARAMETER"))
			            .add(Restrictions.eq("type", "vitalTimepointInformation"))
			            .add(Restrictions.eq("projectsId", project)).list();
				
				if(paramTempList != null && paramTempList.size() > 0) 
					paramMap.put("vitalTimepointInformation", paramTempList);
				
				paramTempList = getSession().createCriteria(ProjectsDetails.class)
			            .add(Restrictions.eq("fieldName", "PARAMETERS"))
			            .add(Restrictions.eq("type", "SkinSensivity"))
			            .add(Restrictions.eq("projectsId", project)).list();
				
				if(paramTempList != null && paramTempList.size() > 0) 
					paramMap.put("SkinSensivity", paramTempList);
				
				paramTempList = getSession().createCriteria(ProjectsDetails.class)
			            .add(Restrictions.eq("fieldName", "PARAMETERS"))
			            .add(Restrictions.eq("type", "SkinAdhesion"))
			            .add(Restrictions.eq("projectsId", project)).list();
				
				if(paramTempList != null && paramTempList.size() > 0) 
					paramMap.put("SkinAdhesion", paramTempList);
				
				paramTempList = getSession().createCriteria(ProjectsDetails.class)
			            .add(Restrictions.eq("fieldName", "PARAMETER"))
			            .add(Restrictions.eq("type", "OtherActivity"))
			            .add(Restrictions.eq("projectsId", project)).list();
				
				if(paramTempList != null && paramTempList.size() > 0) 
					paramMap.put("OtherActivity", paramTempList);
				
				  String subjectNos = (String) getSession().createCriteria(ProjectsDetails.class)
						.add(Restrictions.eq("fieldName", "noOfSubjects"))
			            .add(Restrictions.eq("type", "StudyInformation"))
			            .add(Restrictions.eq("projectsId", project))
			            .setProjection(Projections.property("fieldValue")).uniqueResult();
				  
				  if(subjectNos != null && !subjectNos.equals(""))
					  noOfSubjects = Integer.parseInt(subjectNos);
				  
				  String standByno = (String) getSession().createCriteria(ProjectsDetails.class)
							.add(Restrictions.eq("fieldName", "noOfStandBys"))
				            .add(Restrictions.eq("type", "StudyInformation"))
				            .add(Restrictions.eq("projectsId", project))
				            .setProjection(Projections.property("fieldValue")).uniqueResult();
					  
					  if(standByno != null && !standByno.equals(""))
						  noOfStandbySubjects = Integer.parseInt(standByno);
				  
			}
			tpDto = new TimePointDtoDetails();
			tpDto.setProject(project);
			tpDto.setDoseList(dosetpList);
			tpDto.setMealsList(mealTpList);
			tpDto.setSampleList(sampleTpList);
			tpDto.setVitalsList(vtpList);
			tpDto.setTreatmentList(treatmentList);
			tpDto.setDoseInfo(doseInfo);
			tpDto.setDosInfoDataList(dosInfoDataList);
			tpDto.setSkinAdhList(skinAdhList);
			tpDto.setSkinSensList(skinSensList);
			tpDto.setEcgList(ecgList);
			tpDto.setOtherActList(otherActList);
			tpDto.setParamMap(paramMap);
			tpDto.setNoOfStandbySubjects(noOfStandbySubjects);
			tpDto.setNoOfSubjects(noOfSubjects);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tpDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DosingDataInfoDto getDosingInfoRecord(Long projectId) {
		DosingInfo dosingInfo = null;
		List<DosingInfoData> dosinDataList = null;
		DosingDataInfoDto dosingDto = null;
		Projects project = null;
		try {
			project = (Projects) getSession().createCriteria(Projects.class)
					     .add(Restrictions.eq("projectId", projectId)).uniqueResult();
			
			dosingInfo = (DosingInfo) getSession().createCriteria(DosingInfo.class)
					          .add(Restrictions.eq("projects.projectId", projectId)).uniqueResult();
			if(dosingInfo != null) {
				dosinDataList = getSession().createCriteria(DosingInfoData.class)
						           .add(Restrictions.eq("dosingInfo", dosingInfo)).list();
			}
			dosingDto = new DosingDataInfoDto();
			dosingDto.setDosinDataList(dosinDataList);
			dosingDto.setDosingInfo(dosingInfo);
			dosingDto.setPorject(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dosingDto;
	}

	@Override
	public String saveDoseInfoData(DosingInfo doseinfo, List<DosingInfoData> finalDoseDataList) {
		String result = "Failed";
		long doseinfoNo = 0;
		long doseDataNo = 1L;
		try {
			doseinfoNo = (long) getSession().save(doseinfo);
			if(doseinfoNo > 0) {
				for(DosingInfoData dinfd : finalDoseDataList) {
					long dataNo = (long) getSession().save(dinfd);
					if(dataNo <= 0)
						doseDataNo = dataNo;
				}
			}
			if(doseinfoNo > 0 && doseDataNo > 0)
				result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}

		return result;
	}

	@Override
	public String updateDosingInfoData(DosingInfo doseinfo, List<DosingInfoData> updatefinalDoseDataList,
			boolean updateDosiInfoFlag, boolean updateDoseDataInfoFlag) {
		String result = "Failed";
		boolean flag1 = false;
		boolean flag2 = false;
		try {
			if(updateDosiInfoFlag) {
				getSession().update(doseinfo);
				flag1 = true;
			}else flag1 = true;
			if(updateDoseDataInfoFlag) {
				if(updatefinalDoseDataList != null && updatefinalDoseDataList.size() > 0) {
					for(DosingInfoData dinf : updatefinalDoseDataList) {
						getSession().merge(dinf);
						flag2 = true;
					}
				}
			}else flag2 = true;
			if(flag1 && flag2)
				result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataInfoPdfGenerationDto getDataInfoPdfGenerationDtoDetails(Long projectId, Long langId, Long userId) {
		DataInfoPdfGenerationDto dinfpgDto = null;
		List<SubjectRandamization> subRandomList = new ArrayList<>();
		List<StudySubjects> stdSubjList = null;
		StudyMaster study = null;
		Projects project = null;
		List<StudySubjectPeriods> sspList = null;
		StatusMaster actStatus = null;
		List<Long> subjectIds = null;
		UserMaster user = null;
		try {
			
			user = (UserMaster)getSession().createCriteria(UserMaster.class)
					  .add(Restrictions.eq("id", userId)).uniqueResult();
			
			actStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					        .add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
			
			project = (Projects) getSession().createCriteria(Projects.class)
					      .add(Restrictions.eq("projectId", projectId)).uniqueResult();
			if(project != null) {
				study = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					       .add(Restrictions.eq("projectNo", project.getProjectNo())).uniqueResult();
				if(study != null) {
					stdSubjList = getSession().createCriteria(StudySubjects.class)
							           .add(Restrictions.eq("study", study)).list();
					
					subjectIds = getSession().createCriteria(StudySubjects.class)
							         .add(Restrictions.eq("study", study))
							         .setProjection(Projections.property("id")).list();
					
					if(subjectIds != null && subjectIds.size() > 0) {
						sspList = getSession().createCriteria(StudySubjectPeriods.class)
					            .add(Restrictions.eq("status", actStatus))
					            .add(Restrictions.in("subject.id", subjectIds)).list();
						
						if(sspList != null && sspList.size() > 0) {
							for(StudySubjectPeriods ssp : sspList) {
								SubjectRandamization srz = (SubjectRandamization) getSession().createCriteria(SubjectRandamization.class)
										                      .add(Restrictions.eq("period", ssp.getPeriodId()))
										                      .add(Restrictions.eq("subjectNo", ssp.getSubject().getSubjectNo())).uniqueResult();
								if(srz != null)
									subRandomList.add(srz);
							}
						}
					}
					
					
					
				}
			}
			
			
			dinfpgDto = new DataInfoPdfGenerationDto();
			dinfpgDto.setStudy(study);
			dinfpgDto.setStdSubjList(stdSubjList);
			dinfpgDto.setSubRandomList(subRandomList);
			dinfpgDto.setUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dinfpgDto;
	}

	/*
	 * Method get dosing Date and time with study project number and project should be approve from Dosing_info.
	 * @see com.covideinfo.dao.DosingInfoDao#dosingInfo(com.covideinfo.model.StudyMaster, java.lang.Long)
	 * @author  Swami.tammireddi
	 * @since   2023-06-22 
	 * @return planned dosing date. If data not found returns null value.
	 */
	@Override
	public Date dosingInfo(StudyMaster study, Long statusId) {
		Long projectId = (Long) getSession().createCriteria(Projects.class).add(Restrictions.eq("projectNo", study.getProjectNo()))
				.add(Restrictions.eq("status.id", statusId))
				.setProjection(Projections.property("projectId")).uniqueResult();
		if(projectId != null) {
			try {
				return (Date) getSession().createCriteria(DosingInfo.class)
						.add(Restrictions.eq("projects.projectId", projectId)).setProjection(Projections.property("dosingDate")).uniqueResult();
			}catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return null;
	}
	
	
}
