package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.ProjectCrfGenerationDao;
import com.covideinfo.dto.BlankPdfDto;
import com.covideinfo.dto.InclusionDto;
import com.covideinfo.dto.PdfGenerationDetailsDto;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.ActivityDraftReviewAudit;
import com.covideinfo.model.ApplicationConfiguration;
import com.covideinfo.model.DefaultActivityParameters;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalGroups;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.StaticActivityDetails;
import com.covideinfo.model.StaticActivityValueDetails;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyPhases;
import com.covideinfo.model.UserMaster;

@Repository("projectCrfGenerationDao")
public class ProjectCrfGenerationDaoImpl extends AbstractDao<Long, Projects> implements ProjectCrfGenerationDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Projects> getProjectsRecords() {
		return getSession().createCriteria(Projects.class).list();
	}


	@Override
	public Projects getProjectsRecord(Long projectNo) {
		return (Projects) getSession().createCriteria(Projects.class)
				.add(Restrictions.eq("projectId", projectNo)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PdfGenerationDetailsDto getPdfGenerationDetailsDtoDetails(List<Long> actIds, List<Long> parmIds,
			Long langId) {
		PdfGenerationDetailsDto pgdDto = null;
		List<LanguageSpecificGlobalActivityDetails> gaList = null;
		List<LanguageSpecificGlobalParameterDetails> gpList = null;
		InternationalizaionLanguages inlag = null;
		List<ParameterControlTypeValues> pctvList = null;
		Map<Long, Map<Long, List<LanguageSpecificValueDetails>>> controlMap = new HashMap<>();
		try {
			 inlag = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
						.add(Restrictions.eq("id", langId)).uniqueResult();
			 
			 gaList = getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
					 .add(Restrictions.in("globalActivity.id", actIds))
			 		 .add(Restrictions.eq("inlagId", inlag)).list();
			 if(parmIds.size() > 0) {
				 gpList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
					 .add(Restrictions.in("parameterId.id", parmIds))
					 .add(Restrictions.eq("inlagId", inlag)).list();
				 
				 pctvList = getSession().createCriteria(ParameterControlTypeValues.class)
						 .add(Restrictions.in("globalParameter.id", parmIds)).list();
			 }
			 if(pctvList.size() > 0) {
				 for(ParameterControlTypeValues pctv : pctvList) {
					 if(pctv.getControlType().getCode().equals("RB") || pctv.getControlType().getCode().equals("SB")
							 || pctv.getControlType().getCode().equals("CB")) {
						 List<LanguageSpecificValueDetails> lsvdList = getSession().createCriteria(LanguageSpecificValueDetails.class)
								 .add(Restrictions.eq("inlagId", inlag))
								 .add(Restrictions.eq("globalValId", pctv.getGlobalValues())).list();
						 if(!controlMap.containsKey(pctv.getGlobalParameter().getId())) {
							 Map<Long, List<LanguageSpecificValueDetails>> gvMap = new HashMap<>();
							 gvMap.put(pctv.getGlobalParameter().getId(), lsvdList);
							 controlMap.put(pctv.getControlType().getId(), gvMap);
						 }else {
							 Map<Long, List<LanguageSpecificValueDetails>> gvMap = controlMap.get(pctv.getControlType().getId());
							 if(gvMap.containsKey(pctv.getGlobalParameter().getId())) {
								 gvMap.put(pctv.getGlobalParameter().getId(), lsvdList);
								 controlMap.put(pctv.getControlType().getId(), gvMap);
							 }else {
								 gvMap = new HashMap<>();
								 controlMap.put(pctv.getControlType().getId(), gvMap);
							 }
							 
						 }
					 }
				 }
			 }
			 
			pgdDto = new PdfGenerationDetailsDto();
			pgdDto.setGaList(gaList);
			pgdDto.setGpList(gpList);
			pgdDto.setControlMap(controlMap);
			pgdDto.setInlag(inlag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pgdDto;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, List<LanguageSpecificGlobalParameterDetails>> getLanguageSpecificParmeterDetails(
			Map<Long, List<Long>> resParamMap, InternationalizaionLanguages inlag) {
		Map<Long, List<LanguageSpecificGlobalParameterDetails>> lsMap = new HashMap<>();
		List<LanguageSpecificGlobalParameterDetails> lspgpdList = null;
		DefaultActivitys deact = null;
		try {
			for(Map.Entry<Long, List<Long>> map : resParamMap.entrySet()) {
				List<Long> pIds = map.getValue();
				deact = (DefaultActivitys) getSession().createCriteria(DefaultActivitys.class)
						.add(Restrictions.eq("activity.id", map.getKey())).uniqueResult();
				if(deact != null) {
					List<Long> parmIds = getSession().createCriteria(DefaultActivityParameters.class)
							.add(Restrictions.eq("defalutActId", deact))
							.add(Restrictions.not(Restrictions.in("parameter.id", pIds)))
							.setProjection(Projections.property("parameter.id")).list();
					if(parmIds.size() > 0) {
						lspgpdList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
								.add(Restrictions.eq("inlagId", inlag))
								.add(Restrictions.in("parameterId.id", parmIds)).list();
						if(lspgpdList.size() > 0) {
							if(lsMap.containsKey(deact.getActivity().getId())) {
								List<LanguageSpecificGlobalParameterDetails> lspList = lsMap.get(deact.getActivity().getId());
								lspList.addAll(lspgpdList);
								lsMap.put(deact.getActivity().getId(), lspList);
							}else {
								List<LanguageSpecificGlobalParameterDetails> lspList = new ArrayList<>();
								lspList.addAll(lspgpdList);
								lsMap.put(deact.getActivity().getId(), lspList);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsMap;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificGlobalParameterDetails> getNotExistingParameterList(
			LanguageSpecificGlobalActivityDetails langspactId, List<Long> defalutParmIds,
			InternationalizaionLanguages inlag) {
		List<LanguageSpecificGlobalParameterDetails> lsgpList = null;
		List<Long> parmIds = null;
		DefaultActivitys deact = null;
		try {
			deact = (DefaultActivitys) getSession().createCriteria(DefaultActivitys.class)
					.add(Restrictions.eq("activity", langspactId.getGlobalActivity())).uniqueResult();
			if(deact != null) {
				parmIds = getSession().createCriteria(DefaultActivityParameters.class)
						.add(Restrictions.eq("defalutActId", deact))
						.add(Restrictions.not(Restrictions.in("parameter.id", defalutParmIds)))
						.setProjection(Projections.property("parameter.id")).list();
				
				lsgpList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
						.add(Restrictions.eq("inlagId", inlag))
						.add(Restrictions.in("parameterId.id", parmIds)).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lsgpList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, Map<LanguageSpecificGlobalActivityDetails, List<LanguageSpecificGlobalParameterDetails>>> getProjectDetailsPdfGenerationDtoForDefaultActivities(
			List<Long> defaultActIds, InternationalizaionLanguages inlag) {
		Map<Long, Map<LanguageSpecificGlobalActivityDetails, List<LanguageSpecificGlobalParameterDetails>>> lsgaMap = new HashMap<>();
		Map<LanguageSpecificGlobalActivityDetails, List<LanguageSpecificGlobalParameterDetails>> tempMap = new HashMap<>();
		try {
			List<DefaultActivitys> defalultActsList = getSession().createCriteria(DefaultActivitys.class)
					.add(Restrictions.not(Restrictions.in("activity.id", defaultActIds))).list();
			if(defalultActsList != null && defalultActsList.size() > 0) {
				for(DefaultActivitys deact : defalultActsList) {
					List<Long> deParmIds =  getSession().createCriteria(DefaultActivityParameters.class)
							.add(Restrictions.eq("defalutActId", deact))
							.setProjection(Projections.property("parameter.id")).list();
					if(deParmIds != null && deParmIds.size() > 0) {
						
						LanguageSpecificGlobalActivityDetails lsga = (LanguageSpecificGlobalActivityDetails) getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
								.add(Restrictions.eq("globalActivity", deact.getActivity()))
								.add(Restrictions.eq("inlagId", inlag)).uniqueResult();
						
						List<LanguageSpecificGlobalParameterDetails> pList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
								.add(Restrictions.in("parameterId.id", deParmIds))
								.add(Restrictions.eq("inlagId", inlag)).list();
						tempMap.put(lsga, pList);
						lsgaMap.put(lsga.getGlobalActivity().getId(), tempMap);
					}
					 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsgaMap;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificValueDetails> getLanguageSpecificGlobalValusesList(GlobalParameter parameterId, 
			InternationalizaionLanguages inalg) {
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
						.add(Restrictions.eq("inlagId", inalg)).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsgvList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public InclusionDto getGlobalParametersList(List<Long> parmIds, Long langId, String actrCode) {
		InclusionDto indto = null;
		List<LanguageSpecificGlobalParameterDetails> lspgpList = null;
		LanguageSpecificGlobalActivityDetails lsga = null;
		InternationalizaionLanguages inlag = null;
		GlobalActivity ga = null;
		try {
			inlag = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
					.add(Restrictions.eq("id", langId)).uniqueResult();
			
			ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("activityCode", actrCode)).uniqueResult();
			
			lsga = (LanguageSpecificGlobalActivityDetails) getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
					.add(Restrictions.eq("globalActivity", ga))
					.add(Restrictions.eq("inlagId", inlag)).uniqueResult();
			
			
			
			lspgpList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
					.add(Restrictions.in("parameterId.id", parmIds))
					.add(Restrictions.eq("inlagId", inlag)).list();
			
			indto = new InclusionDto();
			indto.setInlag(inlag);
			indto.setLsga(lsga);
			indto.setLspgpList(lspgpList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indto;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificGlobalParameterDetails> getDefaultParameterDetails(List<Long> parmIds,
			GlobalActivity globalActivity, Locale currentLocale) {
		List<LanguageSpecificGlobalParameterDetails> lsgpList = null;
		DefaultActivitys dfa = null;
		List<Long> dfparmIds = null;
		InternationalizaionLanguages inlag = null;
		try {
			dfa = (DefaultActivitys) getSession().createCriteria(DefaultActivitys.class)
					.add(Restrictions.eq("activity", globalActivity)).uniqueResult();
			inlag = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
					.add(Restrictions.eq("countryCode", currentLocale.getCountry())).uniqueResult();
			if(dfa != null) {
				dfparmIds = getSession().createCriteria(DefaultActivityParameters.class)
						     .add(Restrictions.not(Restrictions.in("parameter.id", parmIds)))
						     .setProjection(Projections.property("parameter.id")).list();
				if(dfparmIds != null && dfparmIds.size() > 0) {
					lsgpList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
							.add(Restrictions.in("parameterId.id", dfparmIds))
							.add(Restrictions.eq("inlagId", inlag)).list();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsgpList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public BlankPdfDto getBlankPdfDtoDetails(Long projectNo, String userName) {
		Map<Long, StudyPhases> phasesMap = new HashMap<>();
		Projects project = null;
		String noOfPeriods = "";
		BlankPdfDto bpDto = null;
		UserMaster user = null;
		ApplicationConfiguration appConfig = null;
		ActivityDraftReviewAudit adRaudit;
		StatusMaster sm = null;
		Long auditMaxId = null;
		List<StudyPhases> phasesList = null;
		List<StaticActivityDetails> staticActList = null;
		List<StaticActivityValueDetails> staticActValList = null;
		List<ProjectsDetails> sampleProList = null;
		try {
			project = (Projects) getSession().createCriteria(Projects.class)
					.add(Restrictions.eq("projectId", projectNo)).uniqueResult();
			
			user = (UserMaster) getSession().createCriteria(UserMaster.class)
					.add(Restrictions.eq("username", userName)).uniqueResult();
			
			sm =  (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
			
			appConfig =  (ApplicationConfiguration) getSession().createCriteria(ApplicationConfiguration.class)
					.add(Restrictions.eq("configuration", "Pdf Header"))
					.add(Restrictions.eq("status", sm)).uniqueResult();
			
			auditMaxId = (Long) getSession().createCriteria(ActivityDraftReviewAudit.class)
					.add(Restrictions.eq("projectId", projectNo))
					.add(Restrictions.eq("status", "APPROVAL"))
					.setProjection(Projections.max("id")).uniqueResult();
			
			adRaudit = (ActivityDraftReviewAudit) getSession().createCriteria(ActivityDraftReviewAudit.class)
					.add(Restrictions.eq("id", auditMaxId)).uniqueResult();
			
			phasesList = getSession().createCriteria(StudyPhases.class).list();
			
			if(project != null) {
				noOfPeriods = (String) getSession().createCriteria(ProjectsDetails.class)
						.add(Restrictions.eq("type", "StudyInformation"))
						.add(Restrictions.eq("fieldName", "noOfPeriods"))
						.add(Restrictions.eq("projectsId", project))
						.setProjection(Projections.property("fieldValue")).uniqueResult();
				
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
			if(phasesList != null) {
				for(StudyPhases ph : phasesList) {
					phasesMap.put(ph.getId(), ph);
				}
			}
			staticActList = getSession().createCriteria(StaticActivityDetails.class).list();
			staticActValList = getSession().createCriteria(StaticActivityValueDetails.class).list();
			
			bpDto = new BlankPdfDto();
			bpDto.setProject(project);
			bpDto.setNoOfPeriods(noOfPeriods);
			bpDto.setUser(user);
			bpDto.setAppConfig(appConfig);
			bpDto.setAdrAudit(adRaudit);
			bpDto.setPhasesMap(phasesMap);
			bpDto.setStaticActList(staticActList);
			bpDto.setStaticActValList(staticActValList);
			bpDto.setSampleProList(sampleProList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bpDto;
	}


	@Override
	public String getLanguageSpecificGroupName(GlobalGroups groups, InternationalizaionLanguages inalg) {
		return (String) getSession().createCriteria(LanguageSpecificGroupDetails.class)
				.add(Restrictions.eq("globlgroupId", groups))
				.add(Restrictions.eq("inlagId", inalg))
				.setProjection(Projections.property("name")).uniqueResult();
	}


	@Override
	public LanguageSpecificGlobalParameterDetails getlanguageSpecificGlobalParameterDetails(Long paramId, InternationalizaionLanguages inlag) {
		return (LanguageSpecificGlobalParameterDetails) getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
				.add(Restrictions.eq("parameterId.id", paramId))
				.add(Restrictions.eq("inlagId", inlag)).uniqueResult();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificGlobalActivityDetails> getLanguageSpecificGlobalActivitiesBasedOnCode(Long langId) {
		List<LanguageSpecificGlobalActivityDetails> lspgaList = null;
		List<String> actList = new ArrayList<>();
		List<Long> actIds = null;
		try {
			actList.add("SkinAdhesion");
			actList.add("SkinSensivity");
			actList.add("InclusionCriteria");
			actList.add("ExclusionCriteria");
			actList.add("StudyExecutionVitals");
			actList.add("EcgTimePoints");
			actList.add("DosingCollection");
			actList.add("MealsCollection");
			actList.add("SampleCollection");
			actList.add("BAGGAGERETURN");
			actList.add(StudyActivities.StudyWithDraw.toString());
			
			actIds = getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.in("activityCode", actList))
					.setProjection(Projections.property("id")).list();
			if(actIds != null && actIds.size() > 0) {
				lspgaList = getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
						     .add(Restrictions.in("globalActivity.id", actIds))
						     .add(Restrictions.eq("inlagId.id", langId)).list();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lspgaList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultActivitys> getDefaultActivitiesList() {
		return getSession().createCriteria(DefaultActivitys.class).list();
	}


	@Override
	public ApplicationConfiguration getApplicationConfigurationRecord(String configStr) {
		StatusMaster sm = (StatusMaster) getSession().createCriteria(StatusMaster.class)
				.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
		return (ApplicationConfiguration) getSession().createCriteria(ApplicationConfiguration.class)
				.add(Restrictions.eq("configuration", configStr))
				.add(Restrictions.eq("status", sm)).uniqueResult();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificGlobalParameterDetails> getLanguageSpecificGlobalParametersFromStudyActivitParameters(
			long projectId, InternationalizaionLanguages inalg) {
		List<LanguageSpecificGlobalParameterDetails> lspgpList = null;
		List<Long> paramIds = new ArrayList<>();
		List<String> pdList = null;
		
		try {
			pdList = getSession().createCriteria(ProjectsDetails.class)
					.add(Restrictions.eq("projectsId.projectId", projectId))
					.add(Restrictions.eq("type", "vitalTimepointInformation"))
					.add(Restrictions.eq("fieldName", "PARAMETER"))
					.setProjection(Projections.property("fieldValue")).list();
			if(pdList != null && pdList.size() > 0) {
				for(String st : pdList) {
					if(st != null && !st.equals("")) {
						String[] tempArr = st.split("\\,");
						if(tempArr.length > 0) {
							for(String pid : tempArr) {
								if(!paramIds.contains(Long.parseLong(pid)))
									paramIds.add(Long.parseLong(pid));
									
							}
						}
					}
					
				}
			}
			if(paramIds != null && paramIds.size() > 0) {
				lspgpList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
						.add(Restrictions.in("parameterId.id", paramIds))
						.add(Restrictions.eq("inlagId", inalg)).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lspgpList;
	}


	@Override
	public Map<String,String> getProjectTitleFromPojectDetails(Long projectNo) {
		Map<String, String> projectMap = new HashMap<>();
		String projectTitle = "";
		String projectNumber = "";
		String sponsorCode = "";
		try {
			projectTitle = (String) getSession().createCriteria(ProjectsDetails.class)
					.add(Restrictions.eq("projectsId.projectId", projectNo))
					.add(Restrictions.eq("fieldName", "studyTitle"))
					.setProjection(Projections.property("fieldValue")).uniqueResult();
			
			projectNumber = (String) getSession().createCriteria(ProjectsDetails.class)
					.add(Restrictions.eq("projectsId.projectId", projectNo))
					.add(Restrictions.eq("fieldName", "projectNumber"))
					.setProjection(Projections.property("fieldValue")).uniqueResult();
			
			sponsorCode = (String) getSession().createCriteria(ProjectsDetails.class)
					.add(Restrictions.eq("projectsId.projectId", projectNo))
					.add(Restrictions.eq("fieldName", "sponsorCode"))
					.setProjection(Projections.property("fieldValue")).uniqueResult();
			
			projectMap.put("projectTitle", projectTitle);
			projectMap.put("projectNumber", projectNumber);
			projectMap.put("sponsorCode", sponsorCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectMap;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificGlobalParameterDetails> getLanguageSpecificParameters(List<Long> paramIds,
			InternationalizaionLanguages inlag) {
		return getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
				.add(Restrictions.in("parameterId.id", paramIds))
				.add(Restrictions.eq("inlagId", inlag)).list();
	}

	
}
