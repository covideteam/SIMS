package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.StudyCreationDao;
import com.covideinfo.dto.MealsTimePointsTempDto;
import com.covideinfo.dto.OtherActivitiesDto;
import com.covideinfo.dto.PdfGenerationDetailsDto;
import com.covideinfo.dto.SampleActivityParmanentTablesDto;
import com.covideinfo.dto.SampleTimePointsDto;
import com.covideinfo.dto.StudyActivitiesSavingDto;
import com.covideinfo.dto.StudyActivitySavingDto;
import com.covideinfo.dto.StudyActivityTimpointsSavingDto;
import com.covideinfo.dto.StudyActivityTimpointsSavingDummyDto;
import com.covideinfo.dto.StudyDynamicActDetailsDto;
import com.covideinfo.dto.StudyProjectDetailDto;
import com.covideinfo.dto.StudySampleCentrifugationDetailsDto;
import com.covideinfo.dto.StudySampleCentrifugationDto;
import com.covideinfo.dto.StudySampleProcessingDetailsDto;
import com.covideinfo.dto.StudySampleProcessingDto;
import com.covideinfo.dto.StudySampleStorageDetailsDto;
import com.covideinfo.dto.StudySampleStorageDto;
import com.covideinfo.dto.VitalTimePointsTempDto;
import com.covideinfo.dummy.ClinicalInfomation;
import com.covideinfo.dummy.SampleProcessingAndStorage;
import com.covideinfo.enums.ParameterCodes;
import com.covideinfo.enums.StaticData;
import com.covideinfo.enums.StudyDesign;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.ActivityDraftReviewAudit;
import com.covideinfo.model.ConditionParameter;
import com.covideinfo.model.DefaultActivityParameters;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.DosageForm;
import com.covideinfo.model.DosePerameter;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.ECGTimePoints;
import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.ProjectDetailsRandomization;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.SponsorCode;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyActivityRules;
import com.covideinfo.model.StudyActivityTimePoints;
import com.covideinfo.model.StudyDiscrepancy;
import com.covideinfo.model.StudyGroup;
import com.covideinfo.model.StudyGroupPeriodMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudyPhases;
import com.covideinfo.model.StudySampleCentrifugation;
import com.covideinfo.model.StudySampleCentrifugationDetails;
import com.covideinfo.model.StudySampleProcessing;
import com.covideinfo.model.StudySampleProcessingDetails;
import com.covideinfo.model.StudySampleStorage;
import com.covideinfo.model.StudySampleStorageDetails;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectECGTimePoints;
import com.covideinfo.model.SubjectMealsTimePoints;
import com.covideinfo.model.SubjectPeriodStatus;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectSampleCollectionTimePoints;
import com.covideinfo.model.SubjectTimePointVitalTests;
import com.covideinfo.model.SubjectVitalTimePoints;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.VitalTest;
import com.covideinfo.model.VitalTimePoints;
import com.covideinfo.model.Volunteer;

@Repository("studyCreationDao")
public class StudyCreationDaoImpl extends AbstractDao<Long, StudyMaster> implements StudyCreationDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectsDetails> studyMasterFromProjectsDetails(Projects project) {
		return getSession().createCriteria(ProjectsDetails.class)
				.add(Restrictions.eq("projectsId", project))
				.add(Restrictions.eq("status", true))
				.addOrder(Order.asc("type"))
				.addOrder(Order.asc("rowNo"))
				.addOrder(Order.asc("subRowNo"))
				.addOrder(Order.asc("fieldOrder"))
				.list();
	}

	@Override
	public int studyMasterMaxSeqNoWithProjectNo(String fieldValue) {
		int max = 0;
		try {
			max = (int) getSession().createCriteria(StudyMaster.class).add(Restrictions.eq("projectNo", fieldValue))
					.setProjection(Projections.max("seqNo")).uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			return max;
		}
		return max;
	}

	@Override
	public SponsorCode sponsoreCode(String string) {
		return (SponsorCode) getSession().createCriteria(SponsorCode.class).add(Restrictions.eq("sponsorCode", string))
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FromStaticData> formStaticData(String fieldName) {
		return getSession().createCriteria(FromStaticData.class).add(Restrictions.eq("fieldName", fieldName)).list();
	}

	@Override
	public FromStaticData formStaticData(String filedName, String code) {
		return (FromStaticData) getSession().createCriteria(FromStaticData.class)
				.add(Restrictions.eq("fieldName", filedName.toUpperCase().trim()))
				.add(Restrictions.eq("code", code.toUpperCase().trim())).uniqueResult();
	}

	@Override
	public DosageForm dosageFrom(String fieldName, String code) {
		return (DosageForm) getSession().createCriteria(DosageForm.class)
				.add(Restrictions.eq("fieldName", fieldName.toUpperCase()))
				.add(Restrictions.eq("dosaveCode", code.toUpperCase())).uniqueResult();
	}

	@Override
	public StatusMaster statusMaster(String string) {
		return (StatusMaster) getSession().createCriteria(StatusMaster.class).add(Restrictions.eq("statusCode", string))
				.uniqueResult();
	}

	@Override
	public StudyGroup studyGroup(String projectNo) {
		StudyGroup sg = (StudyGroup) getSession().createCriteria(StudyGroup.class)
				.add(Restrictions.eq("groupName", projectNo)).uniqueResult();
		if (sg == null) {
			sg = new StudyGroup();
			sg.setGroupno(1);
		}
		return sg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VitalTest> vitalTestByCode(List<String> tss) {
		List<VitalTest> tests = getSession().createCriteria(VitalTest.class).add(Restrictions.in("testName", tss))
				.list();
		return tests;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public String saveStudyCompleteInfomation(StudyProjectDetailDto spDto, ActivityDraftReviewAudit adra, Projects poject)throws Exception {
		StudyMaster study = null;
		StudyMaster studyPojo = null;
		ClinicalInfomation clincalInfo = null;
		List<TreatmentInfo> trnTreatmentsList = new ArrayList<>();
		long studyId = 0;
		String finalResult ="Failed";
		Long projectId = null;
		try {
			if(spDto != null) {
				if(spDto.getProjects() != null)
					projectId = spDto.getProjects().getProjectId();
				study = spDto.getStudyMaster();
				clincalInfo = spDto.getClinicalInfomation();
				studyPojo = (StudyMaster) getSession().createCriteria(StudyMaster.class)
						.add(Restrictions.eq("projectNo", study.getProjectNo())).uniqueResult();
				if(studyPojo == null) {
					getSession().save(study);
					studyId = study.getId();
				}else {
					study.setId(studyPojo.getId());
				}
				List<StudyGroup> stdGroupList = getSession().createCriteria(StudyGroup.class)
						.add(Restrictions.eq("study", study)).list();
				StudyGroup stdGroup = null;
				if(stdGroupList.size() == 0) {
					stdGroup  = new StudyGroup();
					stdGroup.setCreatedBy(study.getCreatedBy());
					stdGroup.setCreatedOn(new Date());
					stdGroup.setGroupno(1);
					stdGroup.setGroupName("G1");
					stdGroup.setStudy(study);
					getSession().save(stdGroup);
					
				}
				for (StudyPeriodMaster period : spDto.getStudyPeriodMasterList()) {
					StudyPeriodMaster spm = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
							.add(Restrictions.eq("periodName", period.getPeriodName()))
							.add(Restrictions.eq("study", study)).uniqueResult();
					if(spm == null) {
						getSession().save(period);
						StatusMaster status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
								.add(Restrictions.eq("statusCode", "ACTIVE")).uniqueResult();
						StatusMaster newStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
								.add(Restrictions.eq("statusCode", "NEW")).uniqueResult();
						
						StudyGroupPeriodMaster sgpm = new StudyGroupPeriodMaster();
						sgpm.setCreatedBy(period.getCreatedBy());
						sgpm.setCreatedOn(period.getCreatedOn());
						sgpm.setStudyId(study.getId());
						sgpm.setPeriod(period);
						sgpm.setPeriodName(period.getPeriodName());
						sgpm.setPeriodNo(period.getPeriodNo());
						sgpm.setStudyGroup(stdGroup);
						if(period.getPeriodNo() == 1)
							sgpm.setPeriodStatus(status);
						else sgpm.setPeriodStatus(newStatus);
						getSession().save(sgpm);
					}
				}
				for (TreatmentInfo tr : spDto.getTreatmentInfoList()) {
					TreatmentInfo tinf = (TreatmentInfo) getSession().createCriteria(TreatmentInfo.class)
							               .add(Restrictions.eq("study.id", study.getId()))
							               .add(Restrictions.eq("randamizationCode", tr.getRandamizationCode())).uniqueResult();
					if(tinf == null) {
						getSession().save(tr);
						trnTreatmentsList.add(tr);
					}else trnTreatmentsList.add(tinf);
				}
			    //DosingTimePoints
				List<DoseTimePoints> dtpList = getDoseTimePointsWithNonTimePointParmeters(spDto.getDoseTimePoints(), spDto.getNonTimePointDosingParamMap());
				if(dtpList != null && dtpList.size() > 0) {
					Collections.sort(dtpList);
					for(DoseTimePoints dtp : dtpList) {
						getSession().save(dtp);
					}
				}
				//DoseParameter
				Map<Integer, List<Long>> nonTimePointDoseParmsMap = spDto.getNonTimePointDosingParamMap();
				for(Map.Entry<Integer, List<Long>> nond : nonTimePointDoseParmsMap.entrySet()) {
					Integer treatmentNo = nond.getKey();
					List<Long> parmeterIds = nond.getValue();
					if(parmeterIds != null && parmeterIds.size() > 0)
						addingNonTimePointDoseParameters(parmeterIds, study, treatmentNo, trnTreatmentsList);
				}
				//Sample Timpoints Saving
				List<SampleTimePointsDto> stpDtolist = getSampleTimePointDto(clincalInfo.getSampleTimePoints());
				if(stpDtolist != null && stpDtolist.size() > 0) {
					Collections.sort(stpDtolist);
					for(SampleTimePointsDto stpd : stpDtolist) {
						SampleTimePoints stp = new SampleTimePoints();
						BeanUtils.copyProperties(stpd, stp);
						getSession().save(stp);
					}
				}
			
				//Meals Time Points
				List<MealsTimePointsTempDto> mtpDtolist = getMealsTimePointsDto(clincalInfo.getMealsTimePoints());
				if(mtpDtolist != null && mtpDtolist.size() > 0) {
					Collections.sort(mtpDtolist);
					for(MealsTimePointsTempDto mtpt : mtpDtolist) {
						MealsTimePoints mtp = new MealsTimePoints();
						BeanUtils.copyProperties(mtpt, mtp);
						getSession().save(mtp);
					}
				}
			
				List<VitalTimePointsTempDto> vtptDtoList = getVitalTimePointsData(clincalInfo.getVitalTimePoints());
				if(vtptDtoList != null && vtptDtoList.size() > 0) {
					Collections.sort(vtptDtoList);
					for(VitalTimePointsTempDto vtpt : vtptDtoList) {
						VitalTimePoints vtp = new VitalTimePoints();
						BeanUtils.copyProperties(vtpt, vtp);
					    getSession().save(vtp);
					}
				}
						
				
				//Ecg Records, SkinAdhection, SkinSesitivity Data Saving
				Map<Integer, List<StudyActivityTimpointsSavingDto>> ecgMap = clincalInfo.getTwSatEcgMap();
				Map<Integer, List<StudyActivityTimpointsSavingDto>> twskadhMap = clincalInfo.getSkinAdhFinalMap();
				Map<Integer, List<StudyActivityTimpointsSavingDto>> twskSenhMap = clincalInfo.getSkinSenFinalMap();
				String ecgResult = saveStudyActivityTimpointsSavingDtoDetails(ecgMap, study, "ECG");
				String skinadhResult = saveStudyActivityTimpointsSavingDtoDetails(twskadhMap, study, "SkinAdhection");
				String skinSensitivityResult = saveStudyActivityTimpointsSavingDtoDetails(twskSenhMap, study, "SkinSensitivity");
				//Without Time Points OtherActivities Data Saving
				List<OtherActivitiesDto> otherActWithoutTimePointsList = clincalInfo.getOtherActWithoutTimePointParamList();
				if(otherActWithoutTimePointsList != null) {
					Map<Integer, TreatmentInfo> tinfoMap = new HashMap<>();
					/*List<TreatmentInfo> tinfoList = getSession().createCriteria(TreatmentInfo.class)
							.add(Restrictions.eq("study.id", study.getId())).list();*/
					
					if(trnTreatmentsList != null && trnTreatmentsList.size() > 0) {
						for(TreatmentInfo tinf : trnTreatmentsList) 
							tinfoMap.put(Integer.parseInt(tinf.getTreatmentNo()), tinf);
					}
					for(OtherActivitiesDto oaDto : otherActWithoutTimePointsList) {
						List<StudyPeriodMaster> spmList = getSession().createCriteria(StudyPeriodMaster.class)
								                      .add(Restrictions.eq("study", study)).list();
						if(spmList != null && spmList.size() > 0) {
							String result ="Failed";
							if(oaDto.getTreatmentNo() == 0) {
								result = saveOtherActivityParametersData(tinfoMap, oaDto, spmList, study);
							}else {
								Map<Integer, TreatmentInfo> tempMap = new HashMap<>();
								tempMap.put(oaDto.getTreatmentNo(), tinfoMap.get(oaDto.getTreatmentNo()));
								result = saveOtherActivityParametersData(tempMap, oaDto, spmList, study);
							}
						}
					}
				}
				//Other Activities With Time points
				List<OtherActivitiesDto> otherActWithTimePointsList = clincalInfo.getOtherActWithTimePointParamList();
				if(otherActWithTimePointsList != null && otherActWithTimePointsList.size() > 0) {
					Collections.sort(otherActWithTimePointsList);
					Map<Integer, TreatmentInfo> tinfoMap = new HashMap<>();
					List<TreatmentInfo> tinfoList = getSession().createCriteria(TreatmentInfo.class).list();
					if(tinfoList != null && tinfoList.size() > 0) {
						for(TreatmentInfo tinf : tinfoList) 
							tinfoMap.put(Integer.parseInt(tinf.getTreatmentNo()), tinf);
					}
					for(OtherActivitiesDto oaDto : otherActWithTimePointsList) {
			        	if(oaDto != null) {
			        		List<StudyPeriodMaster> spmList = getSession().createCriteria(StudyPeriodMaster.class)
				                      .add(Restrictions.eq("study", study)).list();
							if(spmList != null && spmList.size() > 0) {
								String result ="Failed";
								if(oaDto.getTreatmentNo() == 0) {
									result = saveTimePointWiseOtherActivityData(tinfoMap, oaDto, spmList, study);
								}else {
									Map<Integer, TreatmentInfo> tempMap = new HashMap<>();
									tempMap.put(oaDto.getTreatmentNo(), tinfoMap.get(oaDto.getTreatmentNo()));
									result = saveTimePointWiseOtherActivityData(tinfoMap, oaDto, spmList, study);
								}
							}
			        	}
			        }
				}
				//Sample Storage Coditions saving
				SampleProcessingAndStorage spas = spDto.getSampleProcessingAndStorage();
				if(spas != null) {
					StudySampleCentrifugationDto sscDto = spas.getSscDto();
					List<StudySampleCentrifugationDetailsDto> sscdDtoList = spas.getSscdDtoList();
					StudySampleProcessingDto sspDto = spas.getSspDto();
					List<StudySampleProcessingDetailsDto> sspDtoList = spas.getSspDtoList();
					StudySampleStorageDto sssDto = spas.getSssDto();
					List<StudySampleStorageDetailsDto> sssdDtoList = spas.getSssdDtoList();
					
					if(sscDto != null) {
						StudySampleCentrifugation ssc = new StudySampleCentrifugation();
						BeanUtils.copyProperties(sscDto, ssc);
						ssc.setCreatedBy(study.getCreatedBy());
						ssc.setCreatedOn(study.getCreatedOn());
						ssc.setStudy(study);
						ssc.setTreatment(null); //present we are not saving treatment
						getSession().save(ssc);
						
						if(sscdDtoList != null && sscdDtoList.size() > 0) {
							for(StudySampleCentrifugationDetailsDto sscfdDto : sscdDtoList) {
								StudySampleCentrifugationDetails sscfd = new StudySampleCentrifugationDetails();
								BeanUtils.copyProperties(sscfdDto, sscfd);
								sscfd.setCreatedBy(study.getCreatedBy());
								sscfd.setCreatedOn(study.getCreatedOn());
								sscfd.setCentrifugation(ssc);
								getSession().save(sscfd);
							}
						}
						
						//Processing storage
						if(sspDto != null) {
							StudySampleProcessing ssp = new StudySampleProcessing();
							BeanUtils.copyProperties(sspDto, ssp);
							ssp.setCreatedBy(study.getCreatedBy());
							ssp.setCreatedOn(study.getCreatedOn());
							ssp.setStudy(study);
							ssp.setTreatment(null);//present treatment not storing
							getSession().save(ssp);
							
							if(sspDtoList != null && sspDtoList.size() > 0) {
								for(StudySampleProcessingDetailsDto sspdDto : sspDtoList) {
									StudySampleProcessingDetails sspd = new StudySampleProcessingDetails();
									BeanUtils.copyProperties(sspdDto, sspd);
									sspd.setCreatedBy(study.getCreatedBy());
									sspd.setCreatedOn(study.getCreatedOn());
									sspd.setProcessing(ssp);
									getSession().save(sspd);
								}
							}
						}
						
						//Strorage 
						if(sssDto != null) {
							StudySampleStorage sss = new StudySampleStorage();
							BeanUtils.copyProperties(sssDto, sss);
							sss.setCreatedBy(study.getCreatedBy());
							sss.setCreatedOn(study.getCreatedOn());
							sss.setStudy(study);
							sss.setTreatment(null); //present treatment not storing
							getSession().save(sss);
							ConditionParameter storagecp = null;
							ConditionParameter tpcp = null;
							if(sssdDtoList != null && sssdDtoList.size() > 0) {
								for(StudySampleStorageDetailsDto sssdDto : sssdDtoList) {
									StudySampleStorageDetails sssd = new StudySampleStorageDetails();
									BeanUtils.copyProperties(sssdDto, sssd);
									sssd.setCreatedOn(study.getCreatedOn());
									sssd.setCreatedBy(study.getCreatedBy());
									if(sssdDto.getStorageCondition() != null) {
										storagecp = (ConditionParameter) getSession().createCriteria(ConditionParameter.class)
												.add(Restrictions.eq("id", sssdDto.getStorageCondition())).uniqueResult();
									}
									sssd.setStorageCondition(storagecp);
									if(sssdDto.getTimePointCondition() != null) {
										tpcp = (ConditionParameter) getSession().createCriteria(ConditionParameter.class)
												.add(Restrictions.eq("id", sssdDto.getTimePointCondition())).uniqueResult();
									}
									sssd.setTimePointCondition(tpcp);
								}
							}
						}
						
					}
				}
				//Study Randomization Table Data Saving
				List<ProjectDetailsRandomization> pdrList = getSession().createCriteria(ProjectDetailsRandomization.class)
			                .add(Restrictions.eq("projectNo", projectId)).list();
				if(pdrList.size() > 0) {
					
					Map<String, TreatmentInfo> radomizedTrMap = new HashMap<>();
					List<TreatmentInfo> trInfoList = trnTreatmentsList;
					if(trInfoList == null || trInfoList.size() == 0) {
						trInfoList = getSession().createCriteria(TreatmentInfo.class)
								.add(Restrictions.eq("study.id", study.getId())).list();
					}
					if(trInfoList.size() > 0) {
						for(TreatmentInfo tinf : trInfoList) {
							radomizedTrMap.put(tinf.getRandamizationCode(), tinf);
						}
					}
					if(radomizedTrMap.size() > 0) {
						for(ProjectDetailsRandomization pdr : pdrList) { 
							TreatmentInfo tf = radomizedTrMap.get(pdr.getRandomizationCode());
							StudyPeriodMaster spm = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
									.add(Restrictions.eq("periodName", pdr.getPeriod()))
									.add(Restrictions.eq("study", study)).uniqueResult();
							
							SubjectRandamization sr = new SubjectRandamization();
							sr.setCretedBy(pdr.getCreatedBy());
							sr.setCreatedOn(pdr.getCreatedOn());
							sr.setPeriod(spm);
							sr.setTreatmentInfo(tf);
							sr.setRadamizationCode(pdr.getRandomizationCode());
							sr.setSubjectNo(pdr.getSubjectNo());
							getSession().save(sr);

							//StudyTeatmentWiseSubjects
							StudyTreatmentWiseSubjects stwsPojo = (StudyTreatmentWiseSubjects) getSession().createCriteria(StudyTreatmentWiseSubjects.class)
									.add(Restrictions.eq("treatment", tf))
									.add(Restrictions.eq("period", spm)).uniqueResult();
							if(stwsPojo == null) {
								StudyTreatmentWiseSubjects stws = new StudyTreatmentWiseSubjects();
								stws.setCreatedBy(pdr.getCreatedBy());
								stws.setCreatedOn(pdr.getCreatedOn());
								stws.setPeriod(spm);
								stws.setSm(study);
								stws.setTreatment(tf);
								stws.setSubjects(pdr.getSubjectNo());
								getSession().save(stws);
							}else {
								String subNos = stwsPojo.getSubjects();
								stwsPojo.setSubjects(subNos+","+pdr.getSubjectNo());
								getSession().merge(stwsPojo);
							}
						}
					}
				}
			}
			if(study != null) {
				StudyActivitiesSavingDto sasDto = saveStudyDesignActivityDetails(study, spDto.getRestrictionComplains(),
						spDto.getInclusionCriteria(), spDto.getExclusionCriteria(), spDto.getDefaultActMap());
				String result = saveStudyActivitiesData(sasDto, spDto.getDefaultActMap(), study);
				if(result.equals("success")) {
					finalResult = saveActivityDraftReviewAudit(adra,poject);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalResult;
}

	@SuppressWarnings("unchecked")
	private String saveActivityDraftReviewAudit(ActivityDraftReviewAudit adra,Projects poject) {
		List<StudyDiscrepancy> studyDiscrepancyList = null;
		try {
			getSession().save(adra);
			getSession().update(poject);
			if(poject.getRoleId()==0) { //0 = Last level approval, RoleId= Approval Level
				poject = changeProjectsStatsu(poject, StudyStatus.APPROVED.toString());
			}
			studyDiscrepancyList= getSession().createCriteria(StudyDiscrepancy.class)
									.add(Restrictions.eq("project.id", poject.getProjectId()))
									.add(Restrictions.eq("isResponseSubmitted", false)).list();
			
			for(StudyDiscrepancy studyDiscrepancy: studyDiscrepancyList) {
				if(studyDiscrepancy.getResponse() != null){
					studyDiscrepancy.setResponseSubmitted(true);
				}
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";
		}
	}

private Projects changeProjectsStatsu(Projects project, String status) {
		if(status != null) {
			StatusMaster st = (StatusMaster) getSession().createCriteria(StatusMaster.class).add(Restrictions.eq("statusCode", status)).uniqueResult();
			project.setStatus(st);
		}
		getSession().merge(project);
		return project;
	}

private StudyActivitiesSavingDto saveStudyDesignActivityDetails(StudyMaster study, Map<Integer, List<ProjectsDetails>> rescompMap,
		Map<Integer, List<ProjectsDetails>> inclusionMap, Map<Integer, List<ProjectsDetails>> exclusionMap, Map<Long, List<Long>> defaultActMap) {
	StudyActivitiesSavingDto sasdto = null;
	StudyActivitySavingDto sasDto = null;
	List<Long> parmIds = new ArrayList<>();
	List<Long> actIds = new ArrayList<>();
	List<Long> pIds = null;
	List<GlobalActivity> actList = null;
	Map<Long, GlobalParameter> gpMap = null;
	Map<Long, GlobalActivity> actMap = new HashMap<>();
	Map<StudyActivities, List<StudyActivityParameters>> saMap = new HashMap<>();
	GlobalActivity incAct = null;
	GlobalActivity exeAct = null;
	List<TreatmentInfo> tminfoList = null;
	List<StudyPeriodMaster> spmList = null;
	Map<String, TreatmentInfo> tmMap = new HashMap<>();
	List<GlobalValues> gvList = null;
	Map<String, GlobalValues> gvMap = new HashMap<>();
	Map<String, Map<Long, Map<Long, StudyActivityRules>>> saruleMap = new HashMap<>();
	SampleActivityParmanentTablesDto saptDto = null;
	Map<String, Map<Long, Map<Long, List<GlobalParameter>>>> twsapMap = new HashMap<>(); //treatmentNo, activityId, period, parmList
	Map<String, Map<Long, Map<Long, StudyActivities>>> tsadMap = new HashMap<>();//treatmentNo, activityId, period, parmList
	List<StudyActivities> defalutSaList = new ArrayList<>();
	StudyDynamicActDetailsDto sdaDto = null;
	Long defaultIdsForIncAndExcId = null;
	try {	
		if(rescompMap != null && rescompMap.size() > 0) {
			for(Map.Entry<Integer, List<ProjectsDetails>> rescMap : rescompMap.entrySet()) {
				List<ProjectsDetails> prodList = rescMap.getValue();
				if(prodList != null) {
					for(ProjectsDetails prod : prodList) {
						if(prod.getFieldName().equalsIgnoreCase(StudyDesign.RESTRICTIONACTIVITY.toString())) {
							if(!actIds.contains(Long.parseLong(prod.getFieldValue())))
								actIds.add(Long.parseLong(prod.getFieldValue()));
						}
					}
				}
			}
		}
	    pIds = getPrameterIds(rescompMap);
		if(pIds != null && pIds.size() > 0)
			parmIds.addAll(pIds);
		pIds = getPrameterIds(inclusionMap);
		if(pIds != null && pIds.size() > 0)
			parmIds.addAll(pIds);
		pIds = getPrameterIds(exclusionMap);
		if(pIds != null && pIds.size() > 0)
			parmIds.addAll(pIds);
		sasDto = getStudyActivitySavingDtoDetailsForStudyActivities(parmIds, actIds, study);
		if(sasDto != null) {
			actList = sasDto.getActList();
			gpMap = sasDto.getGpMap();
			tminfoList = sasDto.getTminfoList();
			gvList = sasDto.getGvList();
			spmList = sasDto.getSpList();
			defaultIdsForIncAndExcId = sasDto.getDefaultIdsForIncAndExcId();
			if(tminfoList != null) {
				for(TreatmentInfo tinfo : tminfoList) {
					tmMap.put(tinfo.getTreatmentNo(), tinfo);
				}
			}
			if(actList != null) {
				for(GlobalActivity ga : actList) {
					actMap.put(ga.getId(), ga);
					if(ga.getActivityCode().equals(com.covideinfo.enums.StudyActivities.ExclusionCriteria.toString())) {
						exeAct = ga;
					}else if(ga.getActivityCode().equals(com.covideinfo.enums.StudyActivities.InclusionCriteria.toString())) {
						incAct = ga;
					}
				}
			}
			for(GlobalValues gv : gvList) {
				gvMap.put(gv.getName(), gv);
			}
			//RestrictionsActivity
			Map<String, Map<Long, List<Long>>> resDefalutMap = new HashMap<>();
			if(rescompMap != null && rescompMap.size() > 0) {
				for(Map.Entry<Integer, List<ProjectsDetails>> rescMap : rescompMap.entrySet()) {
					List<ProjectsDetails> prodList = rescMap.getValue();
					Collections.sort(prodList);
					if(prodList != null) {
						String activityId = "";
						String treatmentNo = "";
						List<Long> paramIds = new ArrayList<>();
						for(ProjectsDetails prod : prodList) {
							if(StudyDesign.RESTRICTIONACTIVITY.toString().equalsIgnoreCase(prod.getFieldName())) {
								activityId = prod.getFieldValue();
							}else if(StudyDesign.TREATMENT.toString().equalsIgnoreCase(prod.getFieldName())) {
								treatmentNo = prod.getFieldValue();
							}else if(StudyDesign.PARAMETER.toString().equalsIgnoreCase(prod.getFieldName())) {
								if(!paramIds.contains(Long.parseLong(prod.getFieldValue())))
									paramIds.add(Long.parseLong(prod.getFieldValue()));
							}
						}
						if(!treatmentNo.equals("")) {
							if(treatmentNo.equals("0")) {
								saptDto = getStudyActivitiesData(tmMap, activityId, paramIds, study,spmList,actMap,gpMap, twsapMap, tsadMap, resDefalutMap);
							}else {
								Map<String, TreatmentInfo> tm = new HashMap<>();
								tm.put(treatmentNo, tmMap.get(treatmentNo));
								saptDto = getStudyActivitiesData(tm, activityId, paramIds, study,spmList,actMap,gpMap, twsapMap, tsadMap, resDefalutMap);
							}
						}
					}
				}
			}
			//Rules
			
			//Inclusion Activity
			List<Long> defaultParmIds = null;
			StudyPeriodMaster spm = null;
			if(spmList != null) {
				for(StudyPeriodMaster sp : spmList) {
					if(sp.getPeriodNo() == 1)
						spm = sp;
				}
			}
			if(inclusionMap != null && inclusionMap.size() > 0) {
				sdaDto = getStudyActivitRecordsDetails(inclusionMap, incAct, study, spm, gpMap, gvMap, saMap, defaultIdsForIncAndExcId);
			}
			
			//Exclusion Criteria
			if(exclusionMap != null && exclusionMap.size() > 0) 
				sdaDto = getStudyActivitRecordsDetails(exclusionMap, exeAct, study, spm, gpMap, gvMap, saMap, defaultIdsForIncAndExcId);
		}
		if(saMap.size() > 0) {
			sasdto = new StudyActivitiesSavingDto();
			sasdto.setSaMap(saMap);
			sasdto.setSaruleMap(saruleMap);
			sasdto.setTsadMap(tsadMap);
			sasdto.setTwsapMap(twsapMap);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return sasdto;
	}

private StudyDynamicActDetailsDto getStudyActivitRecordsDetails(
			Map<Integer, List<ProjectsDetails>> mapObj, GlobalActivity incAct, StudyMaster study,
			StudyPeriodMaster spm, Map<Long, GlobalParameter> gpMap, Map<String, GlobalValues> gvMap, Map<StudyActivities, List<StudyActivityParameters>> saMap, Long defaultIdsForIncAndExcId) {
		StudyDynamicActDetailsDto sdadDto = null;
		List<Long> defaultActIds = new ArrayList<>();
//		Map<StudyActivities, List<StudyActivityParameters>> map = new HashMap<>();
		Map<String, Map<Long, Map<Long, StudyActivityRules>>> saruleMap = new HashMap<>();
		List<Long> defaultParmIds = null;
		GlobalParameter defalultGp = null;
		try {
			if(defaultIdsForIncAndExcId != null)
				defalultGp = getGlobalPrameterRecord(defaultIdsForIncAndExcId);
			defaultActIds.add(incAct.getId());
			StudyActivities sa = new StudyActivities();
			sa.setActivityId(incAct);
			sa.setCreatedBy(study.getCreatedBy());
			sa.setCreatedOn(new Date());
			sa.setSm(study);
			sa.setStudyPeriod(spm);
			
			List<StudyActivityParameters> sapList = new ArrayList<>();
			for(Map.Entry<Integer, List<ProjectsDetails>> objMap : mapObj.entrySet()) {
				List<ProjectsDetails> pdList = objMap.getValue();
				if(pdList != null && pdList.size() > 0) {
					defaultParmIds = new ArrayList<>();
					String parameterId = "";
					String gender ="";
					for(ProjectsDetails pd : pdList) {
						if(pd.getFieldName().equalsIgnoreCase(StudyDesign.PARAMETER.toString())) {
							parameterId = pd.getFieldValue();
							defaultParmIds.add(Long.parseLong(parameterId));
						}else if(pd.getFieldName().equalsIgnoreCase(StudyDesign.GENDER.toString())) {
							gender = pd.getFieldValue();
						}
					}
					if(!parameterId.equals("") && !gender.equals("")) {
						StudyActivityParameters sap = new StudyActivityParameters();
						sap.setCreatedBy(study.getCreatedBy());
						sap.setCreatedOn(study.getCreatedOn());
						sap.setParameterId(gpMap.get(Long.parseLong(parameterId)));
						sapList.add(sap);
						Map<Long, Map<Long, StudyActivityRules>> starMap = null;
						Map<Long, StudyActivityRules> sactRMap = null;
						GlobalParameter gp = null;
						if(gender.equalsIgnoreCase("MALE") || gender.equalsIgnoreCase("FEMALE")) {
							if(saruleMap.containsKey(gender)) {
								starMap = saruleMap.get(gender);
								if(starMap.containsKey(incAct.getId())) {
									sactRMap = starMap.get(incAct.getId());
									if(sactRMap != null && sactRMap.size() > 0) {
										if(!sactRMap.containsKey(Long.parseLong(parameterId))) {
											gp = gpMap.get(Long.parseLong(parameterId));
											StudyActivityRules sarule  = getStudyActivityRulesDetails(incAct,gp, parameterId,gender,gvMap);
											sactRMap.put(gp.getId(), sarule);
											starMap.put(incAct.getId(), sactRMap);
											saruleMap.put(gender, starMap);
										}
									}else {
										sactRMap = new HashMap<>();
										gp = gpMap.get(Long.parseLong(parameterId));
										StudyActivityRules sarule  = getStudyActivityRulesDetails(incAct,gp, parameterId,gender,gvMap);
										sactRMap.put(gp.getId(), sarule);
										starMap.put(incAct.getId(), sactRMap);
										saruleMap.put(gender, starMap);
									}
								}else {
									sactRMap = new HashMap<>();
									gp = gpMap.get(Long.parseLong(parameterId));
									StudyActivityRules sarule  = getStudyActivityRulesDetails(incAct,gp, parameterId,gender,gvMap);
									sactRMap.put(gp.getId(), sarule);
									starMap.put(incAct.getId(), sactRMap);
									saruleMap.put(gender, starMap);
								}
								
							}else {
								starMap = new HashMap<>();
								sactRMap = new HashMap<>();
								gp = gpMap.get(Long.parseLong(parameterId));
								StudyActivityRules sarule  = getStudyActivityRulesDetails(incAct,gp, parameterId,gender,gvMap);
								sactRMap.put(gp.getId(), sarule);
								starMap.put(incAct.getId(), sactRMap);
								saruleMap.put(gender, starMap);
							}
						}
						parameterId = "";
						gender = "";
					}
				}
			}
			if(defaultIdsForIncAndExcId != null && mapObj != null &&  mapObj.size() > 0 && incAct.getActivityCode().equals(com.covideinfo.enums.StudyActivities.ExclusionCriteria.toString())) {
				if(!defaultParmIds.contains(defaultIdsForIncAndExcId)) {
					StudyActivityParameters sap = new StudyActivityParameters();
					sap.setCreatedBy(study.getCreatedBy());
					sap.setCreatedOn(study.getCreatedOn());
					sap.setParameterId(defalultGp);
					sapList.add(sap);
				}
			}
			saMap.put(sa, sapList);
			sdadDto = new StudyDynamicActDetailsDto();
			sdadDto.setMap(saMap);
			sdadDto.setSaruleMap(saruleMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdadDto;
	}

private GlobalParameter getGlobalPrameterRecord(Long defaultIdsForIncAndExcId) {
	return (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
			 .add(Restrictions.eq("id", defaultIdsForIncAndExcId)).uniqueResult();
}

private StudyActivityRules getStudyActivityRulesDetails(GlobalActivity exeAct, GlobalParameter gp,
			String parameterId, String gender, Map<String, GlobalValues> gvMap) {
		StudyActivityRules sarule = new StudyActivityRules();
		try {
			sarule.setSourceActivity(exeAct);
			sarule.setRuleType("Dependent");
			sarule.setDestinationActivity(exeAct);
			sarule.setCondition("Dynamic");
			
			sarule.setDestParameter(gp);
			String genderStr = "";
			if(gp.getContentType().getCode().equals("SB") || gp.getContentType().getCode().equals("CB") || gp.getContentType().getCode().equals("RB")) {
				if(gender.equalsIgnoreCase("MALE"))
					genderStr = "Male";
				else genderStr = "Female";
				sarule.setGlobalValId(gvMap.get(genderStr));
			}
			sarule.setParameterAction("Hide");
			sarule.setKeyName(genderStr);
			sarule.setKeyValue("Hide");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sarule;
	}

private SampleActivityParmanentTablesDto getStudyActivitiesData(Map<String, TreatmentInfo> tmMap, String activityId,
			List<Long> paramIds, StudyMaster study, List<StudyPeriodMaster> spmList,
			Map<Long, GlobalActivity> actMap, Map<Long, GlobalParameter> gpMap, Map<String, Map<Long, Map<Long, List<GlobalParameter>>>> twsapMap,
			Map<String, Map<Long, Map<Long, StudyActivities>>> tsadMap, Map<String, Map<Long, List<Long>>> resDefalutMap) {
		SampleActivityParmanentTablesDto saptDto = null;
		try {
			if(spmList != null) {
				for(Map.Entry<String, TreatmentInfo> tmap : tmMap.entrySet()){
					for(StudyPeriodMaster spm : spmList) {
						StudyActivities sadDto = new StudyActivities();
						GlobalActivity ga = actMap.get(Long.parseLong(activityId));
						sadDto.setCreatedBy(study.getCreatedBy());
						sadDto.setCreatedOn(study.getCreatedOn());
						sadDto.setActivityId(ga);
						sadDto.setTreatment(tmap.getValue());
						sadDto.setSm(study);
						sadDto.setStudyPeriod(spm);
						Map<Long, Map<Long, StudyActivities>> samap = null;
						Map<Long, StudyActivities> psaMap = null;
						if(tsadMap.containsKey(tmap.getKey())) {
							samap = tsadMap.get(tmap.getKey());
							if(samap.containsKey(Long.parseLong(activityId))) {
								psaMap = samap.get(Long.parseLong(activityId));
								if(!psaMap.containsKey(spm.getId())) {
									psaMap.put(spm.getId(), sadDto);
									samap.put(Long.parseLong(activityId), psaMap);
									tsadMap.put(tmap.getKey(), samap);
								}
							}else {
								samap = new HashMap<>();
								psaMap = new HashMap<>();
								psaMap.put(spm.getId(), sadDto);
								samap.put(Long.parseLong(activityId), psaMap);
								tsadMap.put(tmap.getKey(), samap);
							}
						}else {
							samap = new HashMap<>();
							psaMap = new HashMap<>();
							psaMap.put(spm.getId(), sadDto);
							samap.put(Long.parseLong(activityId), psaMap);
							tsadMap.put(tmap.getKey(), samap);
						}
						for(Long paid : paramIds) {
							Map<Long, Map<Long, List<GlobalParameter>>> twgpMap = null;
							Map<Long, List<GlobalParameter>> pwgpMap = null;
							List<GlobalParameter> gpList = null;
							if(twsapMap.containsKey(tmap.getKey())) {
								twgpMap = twsapMap.get(tmap.getKey());
								if(twgpMap.containsKey(Long.parseLong(activityId))) {
								    pwgpMap = twgpMap.get(Long.parseLong(activityId));
								    if(pwgpMap.containsKey(spm.getId())) {
								    	gpList = pwgpMap.get(spm.getId());
								    	GlobalParameter gparm = gpMap.get(paid);
								    	gpList.add(gparm);
								    	pwgpMap.put(spm.getId(), gpList);
								    	twgpMap.put(Long.parseLong(activityId), pwgpMap);
								    	twsapMap.put(tmap.getKey(), twgpMap);
								    }else {
								    	gpList = new ArrayList<>();
								    	GlobalParameter gparm = gpMap.get(paid);
								    	gpList.add(gparm);
								    	pwgpMap.put(spm.getId(), gpList);
								    	twgpMap.put(Long.parseLong(activityId), pwgpMap);
								    	twsapMap.put(tmap.getKey(), twgpMap);
								    }
								}else {
									pwgpMap = new HashMap<>();
							    	gpList = new ArrayList<>();
							    	GlobalParameter gparm = gpMap.get(paid);
							    	gpList.add(gparm);
							    	pwgpMap.put(spm.getId(), gpList);
							    	twgpMap.put(Long.parseLong(activityId), pwgpMap);
							    	twsapMap.put(tmap.getKey(), twgpMap);
								}
							}else {
								pwgpMap = new HashMap<>();
						    	gpList = new ArrayList<>();
						    	twgpMap = new HashMap<>();
						    	GlobalParameter gparm = gpMap.get(paid);
						    	gpList.add(gparm);
						    	pwgpMap.put(spm.getId(), gpList);
						    	twgpMap.put(Long.parseLong(activityId), pwgpMap);
						    	twsapMap.put(tmap.getKey(), twgpMap);
							}
							/*//Default parameters purpose
							Map<Long, List<Long>> pidsMap = null;
							List<Long> pids = null;
							if(resDefalutMap.containsKey(tmap.getKey())) {
								pidsMap = resDefalutMap.get(tmap.getKey());
								if(pidsMap.containsKey(Long.parseLong(activityId))) {
									pids = pidsMap.get(Long.parseLong(activityId));
									pids.add(paid);
									pidsMap.put(Long.parseLong(activityId), pids);
									resDefalutMap.put(tmap.getKey(), pidsMap);
								}else {
									pids = new ArrayList<>();
									pids.add(paid);
									pidsMap.put(Long.parseLong(activityId), pids);
									resDefalutMap.put(tmap.getKey(), pidsMap);
								}
							}else {
								pids = new ArrayList<>();
								pidsMap = new HashMap<>();
								pids.add(paid);
								pidsMap.put(Long.parseLong(activityId), pids);
								resDefalutMap.put(tmap.getKey(), pidsMap);
							}*/
						}
					  }
					}
				saptDto = new SampleActivityParmanentTablesDto();
				saptDto.setTsadMap(tsadMap);
				saptDto.setTwsaMap(twsapMap);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saptDto;
	}

@SuppressWarnings("unchecked")
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

private List<Long> getPrameterIds(Map<Integer, List<ProjectsDetails>> rescompMap) {
	List<Long> parmIds = new ArrayList<>();
	if(rescompMap != null && rescompMap.size() > 0) {
		for(Map.Entry<Integer, List<ProjectsDetails>> rescMap : rescompMap.entrySet()) {
			List<ProjectsDetails> prodList = rescMap.getValue();
			if(prodList != null) {
				for(ProjectsDetails prod : prodList) {
					if(prod.getFieldName().equalsIgnoreCase(StudyDesign.PARAMETER.toString())) {
						parmIds.add(Long.parseLong(prod.getFieldValue()));
					}
				}
			}
		}
	}
	return parmIds;
}

private String saveTimePointWiseOtherActivityData(Map<Integer, TreatmentInfo> tinfoMap, OtherActivitiesDto oaDto,
			List<StudyPeriodMaster> spmList, StudyMaster study) {
	    String result = "Failed";
		try {
			for(Map.Entry<Integer, TreatmentInfo> tinf : tinfoMap.entrySet()) {
				for(StudyPeriodMaster spm : spmList) {
					StudyActivities sa = new StudyActivities();
					GlobalActivity ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
							            .add(Restrictions.eq("id", oaDto.getActivityId())).uniqueResult();
					
					/*TreatmentInfo tinfo = (TreatmentInfo) getSession().createCriteria(TreatmentInfo.class)
							.add(Restrictions.eq("treatmentNo", oaDto.getTreatmentNo()+""))
							.add(Restrictions.eq("study", study)).uniqueResult();*/
					sa.setActivityId(ga);
					sa.setCreatedBy(study.getCreatedBy());
					sa.setCreatedOn(study.getCreatedOn());
					sa.setSm(study);
					sa.setStudyPeriod(spm);
					sa.setTreatment(tinf.getValue());
					long saNo = (long) getSession().save(sa);
					if(saNo > 0) {
						StudyActivityTimePoints satp = new StudyActivityTimePoints();
						satp.setCreatedBy(study.getCreatedBy());
						satp.setCreatedOn(satp.getCreatedOn());
						satp.setStudyActivity(sa);
						satp.setTimePoint(oaDto.getTimePoint());
						long satpNo = (long) getSession().save(satp);
						long spNo =0;
						if(satpNo > 0) {
							GlobalParameter gp = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
									.add(Restrictions.eq("id", oaDto.getParameterId())).uniqueResult();
							String phaseStr = gp.getParameterPhase();
							Map<Long, List<String>> phaseMap = new HashMap<>();
							List<String> phaseCodeList = null;
							if(phaseStr != null && !phaseStr.equals("")) {
								String[] tempArr = phaseStr.split("\\,");
								if(tempArr.length > 0) {
									for(String st : tempArr) {
										if(st != null && !st.equals("")) {
											StudyPhases pha = (StudyPhases) getSession().createCriteria(StudyPhases.class)
													  .add(Restrictions.eq("id", Long.parseLong(st))).uniqueResult();
											if(pha != null) {
												if(phaseMap.containsKey(gp.getId())) {
													phaseCodeList = phaseMap.get(gp.getId());
													phaseCodeList.add(pha.getCode());
													phaseMap.put(gp.getId(), phaseCodeList);
												}else {
													phaseCodeList = new ArrayList<>();
													phaseCodeList.add(pha.getCode());
													phaseMap.put(gp.getId(), phaseCodeList);
												}
											}
										}
									}
								}
							}
							if(phaseMap.size() > 0) {
								List<String> phaList = phaseMap.get(gp.getId());
								if(phaList.contains(com.covideinfo.enums.StudyPhases.SCI.toString()) || 
										phaList.contains(com.covideinfo.enums.StudyPhases.SCO.toString())
										|| phaList.contains(com.covideinfo.enums.StudyPhases.SE.toString())
										|| phaList.contains(com.covideinfo.enums.StudyPhases.STDL.toString())) { 
									spNo  = saveStudyActivityParameter(study, gp, sa, satp);
								}else if(spm.getPeriodName().equals("P1") && phaList.contains(com.covideinfo.enums.StudyPhases.P1O.toString())) {
									spNo = saveStudyActivityParameter(study, gp, sa, satp);
								}else if(spm.getPeriodName().equals("P2") && phaList.contains(com.covideinfo.enums.StudyPhases.P20.toString())) {
									spNo = saveStudyActivityParameter(study, gp, sa, satp);
								}else if(spm.getPeriodName().equals("P3") && phaList.contains(com.covideinfo.enums.StudyPhases.P3O.toString())) {
									spNo = saveStudyActivityParameter(study, gp, sa, satp);
								}else if(spm.getPeriodName().equals("P4") && phaList.contains(com.covideinfo.enums.StudyPhases.P4O.toString())) {
									spNo = saveStudyActivityParameter(study, gp, sa, satp);
								}
							}else {
								spNo = saveStudyActivityParameter(study, gp, sa, satp);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

private long saveStudyActivityParameter(StudyMaster study, GlobalParameter gp, StudyActivities sa,
		StudyActivityTimePoints satp) {
	long sapNo = 0;
	try {
		StudyActivityParameters sap = new StudyActivityParameters();
		sap.setCreatedBy(study.getCreatedBy());
		sap.setCreatedOn(study.getCreatedOn());
		sap.setParameterId(gp);
		sap.setStudyActivity(sa);
		sap.setStudyActivityTimePoint(satp);
		sapNo = (long) getSession().save(sap);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return sapNo;
}

	private List<VitalTimePointsTempDto> getVitalTimePointsData(List<VitalTimePoints> vitalTimePoints) {
		List<VitalTimePointsTempDto> vtptDtoList = new ArrayList<>();
		if(vitalTimePoints != null && vitalTimePoints.size() > 0) {
			for(VitalTimePoints vtp : vitalTimePoints) {
				VitalTimePointsTempDto vtpt = new VitalTimePointsTempDto();
				BeanUtils.copyProperties(vtp, vtpt);
				if(vtp.getSign() != null)
					vtpt.setVtpVal(Double.parseDouble(vtp.getSign()+vtp.getTimePoint()));
				vtpt.setVtpVal(Double.parseDouble(vtpt.getTimePoint()));
				vtptDtoList.add(vtpt);
			}
		}
		return vtptDtoList;
	}

	private List<MealsTimePointsTempDto> getMealsTimePointsDto(List<MealsTimePoints> mealsTimePoints) {
		List<MealsTimePointsTempDto> mtpdtoList = new ArrayList<>();
		try {
			if(mealsTimePoints != null && mealsTimePoints.size() > 0) {
				for(MealsTimePoints mtp : mealsTimePoints)
				{   MealsTimePointsTempDto mtpDto = new MealsTimePointsTempDto();
					BeanUtils.copyProperties(mtp, mtpDto);
					if(mtp.getSign() != null && !mtp.getSign().equals(""))
						mtpDto.setMealsTp(Double.parseDouble(mtp.getSign()+mtp.getTimePoint()));
					else mtpDto.setMealsTp(Double.parseDouble(mtp.getTimePoint()));
					mtpdtoList.add(mtpDto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mtpdtoList;
	}

	private List<SampleTimePointsDto> getSampleTimePointDto(List<SampleTimePoints> sampleTimePoints) {
		List<SampleTimePointsDto> stpDtoList = new ArrayList<>();
		try {
			if(sampleTimePoints != null && sampleTimePoints.size() > 0) {
				for(SampleTimePoints stp : sampleTimePoints)
				{   SampleTimePointsDto stpDto = new SampleTimePointsDto();
					BeanUtils.copyProperties(stp, stpDto);
					if(stp.getSign() != null)
						stpDto.setStp(Double.parseDouble(stp.getSign()+stp.getTimePoint()));
					else stpDto.setStp(Double.parseDouble(stp.getTimePoint()));
					stpDtoList.add(stpDto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stpDtoList;
	}

	private List<DoseTimePoints> getDoseTimePointsWithNonTimePointParmeters(List<DoseTimePoints> dtpList, Map<Integer, List<Long>> nonTimePointDoseParmsMap) {
		List<DoseTimePoints> finalDtpList = new ArrayList<>();
		//Non-Dosing Parameters dosetimepoints
		Set<Long> ntparamIds = new HashSet<>();
		for(Map.Entry<Integer, List<Long>> nond : nonTimePointDoseParmsMap.entrySet()) {
			List<Long> parmeterIds = nond.getValue();
			if(parmeterIds != null && parmeterIds.size() > 0)
				ntparamIds.addAll(parmeterIds);
		}
		if(dtpList != null && dtpList.size() > 0) {
			for (DoseTimePoints dtp : dtpList) {
				String paramStr = "";
				if(ntparamIds != null && ntparamIds.size() > 0) {
					for(Long  pid : ntparamIds) {
						if(paramStr.equals(""))
							paramStr = pid+"";
						else paramStr = paramStr +","+pid;
					}
				}
				String parm = dtp.getParameters();
				if(parm != null && !parm.equals("")) {
					parm = parm +","+paramStr;
				}else parm = paramStr;
				dtp.setParameters(parm);
				finalDtpList.add(dtp);
			}
			
		}
		return finalDtpList;
	}

	@SuppressWarnings("unchecked")
	private void addingNonTimePointDoseParameters(List<Long> paramIds,
			StudyMaster study, Integer treatmentNo, List<TreatmentInfo> trnTreatmentsList) {
		try {
			if(paramIds != null && paramIds.size() > 0) {
				/*List<TreatmentInfo> tinfList = getSession().createCriteria(TreatmentInfo.class)
						.add(Restrictions.eq("study.id", study.getId())).list();*/
				Map<Integer, TreatmentInfo> tinfMap = new HashMap<>();
//				if(tinfList != null && tinfList.size() > 0) {
				if(trnTreatmentsList != null && trnTreatmentsList.size() > 0) {
					for(TreatmentInfo tf : trnTreatmentsList) {
						tinfMap.put(Integer.parseInt(tf.getTreatmentNo()), tf);
					}
				}
				if(treatmentNo == 0) {
						saveNonDosingParameters(trnTreatmentsList, study, paramIds);
				}else {
					List<TreatmentInfo> trinfList = new ArrayList<>();
					TreatmentInfo tf = tinfMap.get(treatmentNo);
					if(tf != null)
						trinfList.add(tf);
					saveNonDosingParameters(trinfList,study, paramIds);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveNonDosingParameters(List<TreatmentInfo> trinfList, 
			StudyMaster study, List<Long> doseParamIds) {
		try {
			StatusMaster actStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
			if(trinfList != null && trinfList.size() > 0) {
				for(TreatmentInfo tf : trinfList) {
					if(doseParamIds != null && doseParamIds.size() > 0) {
						for(Long dp : doseParamIds) {
							GlobalParameter gp = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
									.add(Restrictions.eq("id", dp)).uniqueResult();
							
							DosePerameter dpPojo = new DosePerameter();
							dpPojo.setCreatedBy(study.getCreatedBy());
							dpPojo.setCreatedOn(study.getCreatedOn());
							dpPojo.setDosePerameterStatus(actStatus);
							dpPojo.setParameter(gp);
							dpPojo.setStudy(study);
							dpPojo.setTreatment(tf);
							getSession().save(dpPojo);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/*	private void saveStudyDoseTimePoints(DoseTimePoints dtp, List<StudyPeriodMaster> spmList, StudyMaster study) {
		try {
			if(spmList != null && spmList.size() > 0) {
				GlobalActivity ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
						.add(Restrictions.eq("activityCode", com.covideinfo.enums.StudyActivities.DosingCollection.toString())).uniqueResult();
				for(StudyPeriodMaster spm : spmList) {
					StudyActivities sa = new StudyActivities();
					sa.setCreatedBy(study.getCreatedBy());
					sa.setCreatedOn(study.getCreatedOn());
					sa.setActivityId(ga);
					sa.setSm(study);
					sa.setStudyPeriod(spm);
					sa.setTreatment(dtp.getTreatmentInfo());
				    long saNo = (long) getSession().save(sa);
				    if(saNo > 0) {
				    	StudyActivityTimePoints satp = new StudyActivityTimePoints();
				    	satp.setCreatedBy(study.getCreatedBy());
				    	satp.setCreatedOn(study.getCreatedOn());
				    	satp.setStudyActivity(sa);
				    	satp.setTimePoint(dtp.getTimePoint());
				    	satp.setWindowPeriod(dtp.getWindowPeriod());
				    	satp.setWindowPeriodSign(dtp.getWindowPeriodSign());
				    	satp.setWindowPeriodType(dtp.getWindowPeriodType().getCode());
				    	long satpNo = (long) getSession().save(satp);
				    	if(satpNo > 0) {
				    		StudyActivityParameters sap = new StudyActivityParameters();
				    		sap.setCreatedBy(study.getCreatedBy());
				    		sap.setCreatedOn(study.getCreatedOn());
				    		sap.setStudyActivity(sa);
				    		sap.setStudyActivityTimePoint(satp);
				    		if(dtp.getParameters() != null && !dtp.getParameters().equals("")) {
				    			String[] tempArr = dtp.getParameters().split("\\,");
					    		if(tempArr.length > 0) {
					    			for(String st : tempArr) {
					    				if(st != null && !st.equals("")) {
					    					GlobalParameter gp = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
					    							.add(Restrictions.eq("id", Long.parseLong(st))).uniqueResult();
					    					sap.setParameterId(gp);
					    				}
					    			}
					    		}
				    		}
				    		long sapNo = (long) getSession().save(sap);
				    	}
				    }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}*/

	@SuppressWarnings("unused")
	private List<Double> sortedTimePoints(List<Double> timePoints) {
		// sort the list in ascending order
        Collections.sort(timePoints, new Comparator<Double>() {
            @Override
            public int compare(Double a, Double b) {
                return Double.compare(a, b);
            }
        });
        return timePoints;
	}

	private String saveOtherActivityParametersData(Map<Integer, TreatmentInfo> tinfoMap, OtherActivitiesDto oaDto,
			List<StudyPeriodMaster> spmList, StudyMaster study) {
		String result ="Failed";
		try {
			for(Map.Entry<Integer, TreatmentInfo> tmap : tinfoMap.entrySet()) {
				for(StudyPeriodMaster spm : spmList) {
					StudyActivities sa = new StudyActivities();
					GlobalActivity ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
							            .add(Restrictions.eq("id", oaDto.getActivityId())).uniqueResult();
					
					
					sa.setActivityId(ga);
					sa.setCreatedBy(study.getCreatedBy());
					sa.setCreatedOn(study.getCreatedOn());
					sa.setSm(study);
					sa.setStudyPeriod(spm);
					sa.setTreatment(tmap.getValue());
					long saNo = (long) getSession().save(sa);
					if(saNo > 0) {
						GlobalParameter gp = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
								.add(Restrictions.eq("id", oaDto.getParameterId())).uniqueResult();
						
						StudyActivityParameters sap = new StudyActivityParameters();
						sap.setCreatedBy(study.getCreatedBy());
						sap.setCreatedOn(sap.getCreatedOn());
						sap.setParameterId(gp);
						sap.setStudyActivity(sa);
						long sapNo = (long) getSession().save(sap);
						if(sapNo > 0)
							result ="success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "Failed";
			return result;
		}
		return result;
	}

	/*@SuppressWarnings("unchecked")
	private String saveVitalTimePointsInStudyActivityTimePointsTable(List<VitalTimePoints> vitalTimePoints,
			StudyMaster study) {
	    String result = "Failed";
	    if(vitalTimePoints.size() > 0) {
	    	List<StudyPeriodMaster> spmList = getSession().createCriteria(StudyPeriodMaster.class)
						        .add(Restrictions.eq("study", study)).list();
	    	GlobalActivity ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
	    			.add(Restrictions.eq("activityCode", com.covideinfo.enums.StudyActivities.StudyExecutionVitals.toString())).uniqueResult();
			if(spmList.size() > 0) {
				for(StudyPeriodMaster spm : spmList) {
					List<Double> vitalList = new ArrayList<>();
					Map<String, VitalTimePoints> vitalTpMap = new HashMap<>();
					for (VitalTimePoints vital : vitalTimePoints) {
					     vitalList.add(Double.parseDouble(vital.getTimePoint()));
					     vitalTpMap.put(vital.getTimePoint(), vital);
					}
					if(vitalList != null && vitalList.size() > 0) {
						List<Double> vitalsortList = sortedTimePoints(vitalList);
						if(vitalsortList != null && vitalsortList.size() > 0) {
							for(Double d : vitalsortList) {
					        	DecimalFormat decimalFormat = new DecimalFormat("000.000");
					        	String formattedValue = decimalFormat.format(d);
					        	VitalTimePoints vital = vitalTpMap.get(formattedValue);
					        	if(vital != null) {
					        		StudyActivities sa = new StudyActivities();
									sa.setCreatedBy(study.getCreatedBy());
									sa.setCreatedOn(study.getCreatedOn());
									sa.setSm(study);
									sa.setStudyPeriod(spm);
									sa.setTreatment(vital.getTreatmentInfo());
									sa.setActivityId(ga);
									long saNo = (long) getSession().save(sa);
									if(saNo > 0) {
										StudyActivityTimePoints satp = new StudyActivityTimePoints();
										satp.setCreatedBy(study.getCreatedBy());
										satp.setCreatedOn(study.getCreatedOn());
										if(vital.isOrthostatic())
											satp.setOrthoSatatic("Yes");
										else satp.setOrthoSatatic("Yes");
										if(vital.getOrthostaticPosition() != null)
											satp.setOrthoStaticPosition(vital.getOrthostaticPosition().getCode());
										satp.setPosition(vital.getVitalPosition().getCode());
										satp.setStudyActivity(sa);
										satp.setTimePoint(vital.getTimePoint());
										satp.setTimePointSign(vital.getSign());
										satp.setWindowPeriod(vital.getWindowPeriod());
										satp.setWindowPeriodSign(vital.getWindowPeriodSign());
										satp.setWindowPeriodType(vital.getWindowPeriodType().toString());
										long satpNo = (long) getSession().save(satp);
										if(satpNo > 0) {
											String[] paramArr = vital.getParameterIds().split("\\,");
											List<Long> paramIds = new ArrayList<>();
											if(paramArr.length > 0) {
												for(String st : paramArr)
													paramIds.add(Long.parseLong(st));
											}
											List<GlobalParameter> gpList = null;
											if(paramIds.size() > 0) {
												gpList =  getSession().createCriteria(GlobalParameter.class)
													.add(Restrictions.in("id", paramIds)).list();
												if(gpList != null && gpList.size() > 0) {
													for(GlobalParameter gp : gpList) {
														StudyActivityParameters sap = new StudyActivityParameters();
														sap.setCreatedBy(study.getCreatedBy());
														sap.setCreatedOn(sap.getCreatedOn());
														sap.setParameterId(gp);
														sap.setStudyActivity(sa);
														sap.setStudyActivityTimePoint(satp);
														getSession().save(sap);
													}
												}
											}
										}
									}
					        	}
					        }
						}
					}
				}
			}
	    }
		return result;
	}*/

	@SuppressWarnings("unchecked")
	private String saveStudyActivityTimpointsSavingDtoDetails(Map<Integer, List<StudyActivityTimpointsSavingDto>> sactMap, StudyMaster study, String activity) {
		String finalResult = "Failed";
		List<Long> defaultActIds = new ArrayList<>();
		if(sactMap != null && sactMap.size() >0) {
			String activityName = "";
			if(activity.equals("ECG"))
				activityName = com.covideinfo.enums.StudyActivities.EcgTimePoints.toString();
			else if(activity.equals("SkinAdhection"))
				activityName = com.covideinfo.enums.StudyActivities.SkinAdhesion.toString();
			else if(activity.equals("SkinSensitivity"))
				activityName = com.covideinfo.enums.StudyActivities.SkinSensivity.toString();
			for(Map.Entry<Integer, List<StudyActivityTimpointsSavingDto>> map : sactMap.entrySet()) {
				TreatmentInfo tf = (TreatmentInfo) getSession().createCriteria(TreatmentInfo.class)
						.add(Restrictions.eq("study", study))
						.add(Restrictions.eq("treatmentNo", map.getKey()+"")).uniqueResult();
				List<StudyPeriodMaster> spmList = getSession().createCriteria(StudyPeriodMaster.class)
						.add(Restrictions.eq("study", study)).list();
				GlobalActivity ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
						.add(Restrictions.eq("activityCode", activityName)).uniqueResult();
				if(spmList.size() > 0) {
					long saNo = 0;
					for(StudyPeriodMaster spm : spmList) {
						StudyActivities sa = new StudyActivities();
						sa.setActivityId(ga);
						sa.setCreatedBy(study.getCreatedBy());
						sa.setCreatedOn(study.getCreatedOn());
						sa.setSm(study);
						sa.setStudyPeriod(spm);
						sa.setTreatment(tf);
						saNo = (long) getSession().save(sa);
						if(saNo > 0) {
							List<StudyActivityTimpointsSavingDummyDto> sastsDtoList = getStudyActivityTimePointsSavingDataDetails(map.getValue());
							if(sastsDtoList != null && sastsDtoList.size() > 0) {
								Collections.sort(sastsDtoList);
								for(StudyActivityTimpointsSavingDummyDto satsdd : sastsDtoList) {
									StudyActivityTimpointsSavingDto satdto = new StudyActivityTimpointsSavingDto();
									BeanUtils.copyProperties(satsdd, satdto);
									if(satdto != null) {
										String[] parmArr = satdto.getParameters().split("\\,");
										StudyActivityTimePoints satp = new StudyActivityTimePoints();
										long satpNo =0;
										satp.setCreatedBy(study.getCreatedBy());
										satp.setCreatedOn(study.getCreatedOn());
										satp.setOrthoSatatic(satdto.getOrthoStatic());
										satp.setOrthoStaticPosition(satdto.getOrthoStaticPosition());
										satp.setPosition(satdto.getPosition());
										satp.setTimePoint(satdto.getTimePoint());
										satp.setWindowPeriod(satdto.getWindowPeriod());
										satp.setWindowPeriodSign(satdto.getWindowperiodSign());
										satp.setWindowPeriodType(satdto.getWindowPeriodType());
										satp.setTimePointSign(satdto.getSign());
										satp.setStudyActivity(sa);
										satpNo = (long) getSession().save(satp);
										
										boolean flag = true;
										if(satpNo > 0) {
											if(parmArr.length > 0) {
												for(String st : parmArr) {
													if(st != null && !st.equals("")) {
														defaultActIds.add(Long.parseLong(st));
														long sapNo = 0;
														GlobalParameter gp = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
																.add(Restrictions.eq("id", Long.parseLong(st))).uniqueResult();					
														StudyActivityParameters sap = new StudyActivityParameters();
														sap.setCreatedBy(study.getCreatedBy());
														sap.setCreatedOn(study.getCreatedOn());
														sap.setParameterId(gp);
														sap.setStudyActivity(sa);
														sap.setStudyActivityTimePoint(satp);
														sapNo = (long) getSession().save(sap);
														if(sapNo < 0 || sapNo == 0  )
															flag = false;
													}
												}
											}
											//Default Parmeters
											DefaultActivitys da = (DefaultActivitys) getSession().createCriteria(DefaultActivitys.class)
													.add(Restrictions.eq("activity", ga)).uniqueResult();
											if(da != null) {
												List<GlobalParameter> gpList = getSession().createCriteria(DefaultActivityParameters.class)
														.add(Restrictions.not(Restrictions.in("parameter.id", defaultActIds)))
														.add(Restrictions.eq("defalutActId", da))
														.setProjection(Projections.property("parameter")).list();
												if(gpList.size() > 0) {
													for(GlobalParameter gparm : gpList) {
														StudyActivityParameters sap = new StudyActivityParameters();
														sap.setCreatedBy(study.getCreatedBy());
														sap.setCreatedOn(study.getCreatedOn());
														sap.setParameterId(gparm);
														sap.setStudyActivity(sa);
														long sapNo = (long) getSession().save(sap);
														if(sapNo < 0 || sapNo == 0  )
															flag = false;
													}
												}
												
											}
										}
										if(saNo > 0 && satpNo > 0 && flag)
											finalResult = "success";
									}
								}
							}
						}
					}
				}
			}
		}else finalResult = "success";
		return finalResult;
	}

	private List<StudyActivityTimpointsSavingDummyDto> getStudyActivityTimePointsSavingDataDetails(
			List<StudyActivityTimpointsSavingDto> satsDtoList) {
		List<StudyActivityTimpointsSavingDummyDto>  satsdDtoList = new ArrayList<>();
		try {
			if(satsDtoList != null && satsDtoList.size() > 0) {
				for(StudyActivityTimpointsSavingDto sassd : satsDtoList) {
					StudyActivityTimpointsSavingDummyDto satsdd = new StudyActivityTimpointsSavingDummyDto();
					BeanUtils.copyProperties(sassd, satsdd);
					if(satsdd.getTimePoint() != null && !satsdd.getTimePoint().equals(""))
						satsdd.setSatsTp(Double.parseDouble(sassd.getTimePoint()));
					else satsdd.setSatsTp(Double.parseDouble("0.0"));
					satsdDtoList.add(satsdd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return satsdDtoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveStudyActivitiesData(StudyActivitiesSavingDto sasDto, Map<Long, List<Long>> defalultActivities, StudyMaster study) {
		String result1 = "Failed";
		String result2 = "Failed";
		String result = "Failed";
		boolean flag = false;
		List<StudyPhases> phaseList = null;
		Map<Long, StudyPhases> phaseMap = new HashMap<>();
		try {
			phaseList = getSession().createCriteria(StudyPhases.class).list();
			if(phaseList != null && phaseList.size() > 0) {
				for(StudyPhases sph : phaseList) {
					phaseMap.put(sph.getId(), sph);
				}
			}
			if(sasDto != null) {
				for(Entry<StudyActivities, List<StudyActivityParameters>> map : sasDto.getSaMap().entrySet()) {
					StudyActivities sa = map.getKey();
					List<StudyActivityParameters> sapList = map.getValue();
					if(!sa.getActivityId().getActivityCode().equalsIgnoreCase(com.covideinfo.enums.StudyActivities.InclusionCriteria.toString())
								&& !sa.getActivityId().getActivityCode().equalsIgnoreCase(com.covideinfo.enums.StudyActivities.ExclusionCriteria.toString())) {
						getSession().save(sa);
						for(StudyActivityParameters sap : sapList) {
							sap.setStudyActivity(sa);
							long sapNo = (long) getSession().save(sap);
							if(sapNo > 0)
								flag = true;
						}
					}else {
					    List<TreatmentInfo> tfList = getSession().createCriteria(TreatmentInfo.class)
					    		                     .add(Restrictions.eq("study.id", study.getId())).list();
					    if(tfList != null && tfList.size() > 0) {
					    	for(TreatmentInfo tinf : tfList) {
					    		StudyActivities saPojo = new StudyActivities();
					    		BeanUtils.copyProperties(sa, saPojo);
					    		saPojo.setTreatment(tinf);
					    		getSession().save(saPojo);
					    		for(StudyActivityParameters sap : sapList) {
									sap.setStudyActivity(saPojo);
									long sapNo = (long) getSession().save(sap);
									if(sapNo > 0)
										flag = true;
								}
					    	}
					    }
					}
					
				}
				if(flag) {
					if(sasDto.getSaruleMap().size() > 0) {
						for(Map.Entry<String, Map<Long, Map<Long, StudyActivityRules>>> sarMap : sasDto.getSaruleMap().entrySet()) {
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
											.add(Restrictions.eq("parameterAction", sar.getParameterAction()))
											.add(Restrictions.eq("keyName", gender))
											.add(Restrictions.eq("keyValue", sar.getKeyValue())).uniqueResult();
									if(sarPojo == null) {
										long sarNo = (long) getSession().save(sar);
										if(sarNo > 0) 
											result1 = "success";
									}
								}
							}
						}
					}else result1 ="success";
				}
				Map<String, Map<Long, Map<Long, StudyActivities>>> sactMap = sasDto.getTsadMap();
				Map<String, Map<Long, Map<Long, List<GlobalParameter>>>> sactParmMap  = sasDto.getTwsapMap();
				if(sactMap.size() > 0) {
					for(Map.Entry<String, Map<Long, Map<Long, StudyActivities>>> saMap : sactMap.entrySet()) {
						Map<Long, Map<Long, StudyActivities>> sacMap = saMap.getValue();
						Map<Long, Map<Long, List<GlobalParameter>>> tgpMap = sactParmMap.get(saMap.getKey());
						for(Map.Entry<Long, Map<Long, StudyActivities>> asacMap : sacMap.entrySet()) {
							Map<Long, StudyActivities> stdactMap = asacMap.getValue();
							Map<Long, List<GlobalParameter>> actgpMap = tgpMap.get(asacMap.getKey());
							for(Map.Entry<Long, StudyActivities> sa : stdactMap.entrySet()) {
								StudyActivities saPojo = sa.getValue();
								List<GlobalParameter> gpList = actgpMap.get(sa.getKey());
								if(gpList != null) {
									long saNo = (long) getSession().save(saPojo);
									if(saNo > 0) {
										for(GlobalParameter gp : gpList) {
											Map<Long, List<String>> paramPhaseMap = getParmeterPhasesDetails(gp, phaseMap);
											long spNo =0;
											if(paramPhaseMap.size() > 0) {
												List<String> phaList = paramPhaseMap.get(gp.getId());
												if(phaList.contains(com.covideinfo.enums.StudyPhases.SCI.toString()) || 
														phaList.contains(com.covideinfo.enums.StudyPhases.SCO.toString())
														|| phaList.contains(com.covideinfo.enums.StudyPhases.SE.toString())
														|| phaList.contains(com.covideinfo.enums.StudyPhases.STDL.toString())) { 
													spNo  = saveStudyActParameter(saPojo, gp);
												}else if(saPojo.getStudyPeriod().getPeriodName().equals("P1") || phaList.contains(com.covideinfo.enums.StudyPhases.P1O.toString())) {
													spNo = saveStudyActParameter(saPojo, gp);
												}else if(saPojo.getStudyPeriod().getPeriodName().equals("P2") || phaList.contains(com.covideinfo.enums.StudyPhases.P20.toString())) {
													spNo = saveStudyActParameter(saPojo, gp);
												}else if(saPojo.getStudyPeriod().getPeriodName().equals("P3") || phaList.contains(com.covideinfo.enums.StudyPhases.P3O.toString())) {
													spNo = saveStudyActParameter(saPojo, gp);
												}else if(saPojo.getStudyPeriod().getPeriodName().equals("P4") || phaList.contains(com.covideinfo.enums.StudyPhases.P4O.toString())) {
													spNo = saveStudyActParameter(saPojo, gp);
												}
											}else {
												spNo = saveStudyActParameter(saPojo, gp);
											}
											if(spNo > 0)
												result2 = "success";
										}
									}
								}
							}
						}
					}
				}else result2 = "success";
				List<StudyActivities> dfSaList = sasDto.getDefalutSaList();
				if(dfSaList != null) {
					for(StudyActivities sa : dfSaList)
						getSession().save(sa);
				}
			}else {
				result1 = "success";
				result2 = "success";
			}
			if(result1.equals("success") && result2.equals("success")) {
				//Default Activites saving
				if(defalultActivities != null && defalultActivities.size() > 0) {
					Long gpId = (Long) getSession().createCriteria(GlobalParameter.class)
					           .add(Restrictions.eq("parameterCode", ParameterCodes.IENP.toString()))
					           .setProjection(Projections.property("id")).uniqueResult();
					for(Map.Entry<Long, List<Long>> dactMap : defalultActivities.entrySet()) { //activityId, parameterIds
						List<Long> gpIdsList = dactMap.getValue();
						GlobalActivity ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
								.add(Restrictions.eq("id", dactMap.getKey())).uniqueResult();
						
						if(ga != null) {
							if(ga.isEligibleForNextProcess() == true) {
								if(gpIdsList != null && gpIdsList.size() > 0) {
									if(gpId != null) {
										if(!gpIdsList.contains(gpId))
											gpIdsList.add(gpId);
									}
								}
							}
						}
						List<GlobalParameter> gpList  = getSession().createCriteria(GlobalParameter.class)
								.add(Restrictions.in("id", gpIdsList)).list();
						
						List<TreatmentInfo> tfmList = getSession().createCriteria(TreatmentInfo.class)
								.add(Restrictions.eq("study", study)).list();
						
						List<StudyPeriodMaster> spmList = getSession().createCriteria(StudyPeriodMaster.class)
								            .add(Restrictions.eq("study", study)).list();
						
						if(gpList != null && gpList.size() > 0) {
							boolean saFlag = true;
							for(TreatmentInfo tfm : tfmList) {
								for(StudyPeriodMaster sp : spmList) {
									StudyActivities sa = new StudyActivities();
									sa.setActivityId(ga);
									sa.setCreatedBy(study.getCreatedBy());
									sa.setCreatedOn(study.getCreatedOn());
									sa.setSm(study);
									sa.setStudyPeriod(sp);
									sa.setTreatment(tfm);
									long saNo = (long) getSession().save(sa);
									
									for(GlobalParameter gp : gpList) {
										StudyActivityParameters sap = new StudyActivityParameters();
										sap.setCreatedBy(study.getCreatedBy());
										sap.setCreatedOn(study.getCreatedOn());
										sap.setParameterId(gp);
										sap.setStudyActivity(sa);
										
										Map<Long, List<String>> paramPhaseMap = getParmeterPhasesDetails(gp, phaseMap);
										long spNo =0;
										if(paramPhaseMap.size() > 0) {
											List<String> phaList = paramPhaseMap.get(gp.getId());
											if(phaList.contains(com.covideinfo.enums.StudyPhases.SCI.toString()) || 
													phaList.contains(com.covideinfo.enums.StudyPhases.SCO.toString())
													|| phaList.contains(com.covideinfo.enums.StudyPhases.SE.toString())
													|| phaList.contains(com.covideinfo.enums.StudyPhases.STDL.toString())) { 
												spNo = (long) getSession().save(sap);
											}else if(sa.getStudyPeriod().getPeriodName().equals("P1") || phaList.contains(com.covideinfo.enums.StudyPhases.P1O.toString())) {
												spNo = (long) getSession().save(sap);
											}else if(sa.getStudyPeriod().getPeriodName().equals("P2") || phaList.contains(com.covideinfo.enums.StudyPhases.P20.toString())) {
												spNo = (long) getSession().save(sap);
											}else if(sa.getStudyPeriod().getPeriodName().equals("P3") || phaList.contains(com.covideinfo.enums.StudyPhases.P3O.toString())) {
												spNo = (long) getSession().save(sap);
											}else if(sa.getStudyPeriod().getPeriodName().equals("P4") || phaList.contains(com.covideinfo.enums.StudyPhases.P4O.toString())) {
												spNo = (long) getSession().save(sap);
											}
										}else 
											spNo = (long) getSession().save(sap);
										
										if(saNo <= 0 || spNo <= 0)
											saFlag = false;
									}
								}
							}
							if(saFlag)
								result = "success";
						}
						
					}
					result = "success";
				}else result = "success";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private Map<Long, List<String>> getParmeterPhasesDetails(GlobalParameter gp, Map<Long, StudyPhases> phaseMap) {
		Map<Long, List<String>> paramPhaseMap = new HashMap<>();
		String phaseStr = "";
		List<String> phaseCodeList = null;
		try {
			phaseStr = gp.getParameterPhase();
			if(phaseStr != null && !phaseStr.equals("")) {
				String[] tempArr = phaseStr.split("\\,");
				if(tempArr.length > 0) {
					for(String st : tempArr) {
						if(st != null && !st.equals("")) {
							StudyPhases pha = phaseMap.get(Long.parseLong(st));
							if(pha != null) {
								if(paramPhaseMap.containsKey(gp.getId())) {
									phaseCodeList = paramPhaseMap.get(gp.getId());
									phaseCodeList.add(pha.getCode());
									paramPhaseMap.put(gp.getId(), phaseCodeList);
								}else {
									phaseCodeList = new ArrayList<>();
									phaseCodeList.add(pha.getCode());
									paramPhaseMap.put(gp.getId(), phaseCodeList);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramPhaseMap;
	}

	private long saveStudyActParameter(StudyActivities saPojo, GlobalParameter gp) {
		long spNo = 0;
		try {
			StudyActivityParameters sap = new StudyActivityParameters();
			sap.setCreatedBy(saPojo.getCreatedBy());
			sap.setCreatedOn(saPojo.getCreatedOn());
			sap.setParameterId(gp);
			sap.setStudyActivity(saPojo);
			spNo = (long) getSession().save(sap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spNo;
	}

	@Override
	public Projects meargeProject(Projects project) {
		// TODO Auto-generated method stub
		getSession().merge(project);
		return project;
	}

	@Override
	public void createVolunteers(List<Volunteer> vols) {
//		Volunteer vol = vols.get(0);
//		StudyPeriodMaster sp = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
//				.add(Restrictions.eq("study", vol.getStudy()))
//				.add(Restrictions.eq("periodNo", 1)).uniqueResult();
//		StatusMaster subjectStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
//				.add(Restrictions.eq("statusCode", StudyStatus.ENROLLED.toString())).uniqueResult();
		for (Volunteer volunteer : vols) {

			getSession().save(volunteer);
//			SubjectPeriodStatus sps = new SubjectPeriodStatus();
//			sps.setStudy(volunteer.getStudy());
//			sps.setPeriod(sp);
//			sps.setPeriodNo(sp.getPeriodNo());
//			sps.setVolunteer(volunteer);
//			sps.setSubjectStatus(subjectStatus);
//			getSession().save(sps);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyPeriodMaster> getPeriod(StudyMaster study, FromStaticData type, StatusMaster activeStatus) {
		return getSession().createCriteria(StudyPeriodMaster.class).add(Restrictions.eq("study", study))
				.add(Restrictions.eq("periodType", type)).add(Restrictions.eq("periodStatus", activeStatus)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<TreatmentInfo, List<SampleTimePoints>> sampleTimePoints(StudyMaster study) {
		List<TreatmentInfo> treatments = getSession().createCriteria(TreatmentInfo.class)
				.add(Restrictions.eq("study", study)).list();
		Map<TreatmentInfo, List<SampleTimePoints>> map = new HashMap<TreatmentInfo, List<SampleTimePoints>>();
		for (TreatmentInfo tr : treatments) {
			List<SampleTimePoints> sampleTimePoints = getSession().createCriteria(SampleTimePoints.class)
					.add(Restrictions.eq("treatmentInfo", tr)).addOrder(Order.asc("timePointNo")).list();
			if (sampleTimePoints.size() > 0) {
				map.put(tr, sampleTimePoints);
			}
		}
		return map;
	}

	@Override
	public boolean saveSubjectSampleTimePoints(List<SubjectSampleCollectionTimePoints> sampleTimepoints) {
		for (SubjectSampleCollectionTimePoints sstp : sampleTimepoints) {
			if (sstp.getTreatmentInfo() != null)
				System.out.println(sstp.getSubjectSampleCollectionTimePointId() + "\t" + sstp.getPeriod().getPeriodNo()
						+ "\t" + sstp.getSubjectNo() + "\t" + sstp.getTimePointType().getCode() + "\t"
						+ sstp.getTimePointNo() + "\t" + sstp.getTimePoint() + "\t" + sstp.getVacutainer() + "\t"
						+ sstp.getTreatmentInfo().getTreatmentNo());
			else
				System.out.println(sstp.getSubjectSampleCollectionTimePointId() + "\t" + sstp.getPeriod().getPeriodNo()
						+ "\t" + sstp.getSubjectNo() + "\t" + sstp.getTimePointType().getCode() + "\t"
						+ sstp.getTimePointNo() + "\t" + sstp.getTimePoint() + "\t" + sstp.getVacutainer());
			getSession().save(sstp);
		}
		return true;
	}

	@Override
	public void mergeStudy(StudyMaster study) {
		// TODO Auto-generated method stub
		getSession().merge(study);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<TreatmentInfo, List<VitalTimePoints>> vitalTimePoints(StudyMaster study) {
		List<TreatmentInfo> treatments = getSession().createCriteria(TreatmentInfo.class)
				.add(Restrictions.eq("study", study)).list();
		Map<TreatmentInfo, List<VitalTimePoints>> map = new HashMap<TreatmentInfo, List<VitalTimePoints>>();
		for (TreatmentInfo tr : treatments) {
			List<VitalTimePoints> sampleTimePoints = getSession().createCriteria(VitalTimePoints.class)
					.add(Restrictions.eq("treatmentInfo", tr)).addOrder(Order.asc("timePointNo")).list();
			if (sampleTimePoints.size() > 0) {
				map.put(tr, sampleTimePoints);
			}
		}
		return map;
	}

	@Override
	public boolean saveSubjectVitalTimePoints(List<SubjectVitalTimePoints> timepoints) {
		for (SubjectVitalTimePoints sstp : timepoints) {
			getSession().save(sstp);
			List<SubjectTimePointVitalTests> list = sstp.getTest();
			for(SubjectTimePointVitalTests s : list) {
				getSession().save(s);
			}
		}
		return true;
	}

	@Override
	public boolean saveSubjectEcgTimePoints(List<SubjectECGTimePoints> timepoints) {
		for (SubjectECGTimePoints sstp : timepoints) {
			getSession().save(sstp);
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<TreatmentInfo, List<MealsTimePoints>> mealsTimePoints(StudyMaster study) {
		List<TreatmentInfo> treatments = getSession().createCriteria(TreatmentInfo.class)
				.add(Restrictions.eq("study", study)).list();
		Map<TreatmentInfo, List<MealsTimePoints>> map = new HashMap<TreatmentInfo, List<MealsTimePoints>>();
		for (TreatmentInfo tr : treatments) {
			List<MealsTimePoints> sampleTimePoints = getSession().createCriteria(MealsTimePoints.class)
					.add(Restrictions.eq("treatmentInfo", tr)).addOrder(Order.asc("timePointNo")).list();
			if (sampleTimePoints.size() > 0) {
				map.put(tr, sampleTimePoints);
			}
		}
		return map;
	}

	@Override
	public boolean saveSubjectMealsTimePoints(List<SubjectMealsTimePoints> timepoints) {
		for (SubjectMealsTimePoints sstp : timepoints)
			getSession().save(sstp);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<TreatmentInfo, List<DoseTimePoints>> doseTimePoints(StudyMaster study) {
		List<TreatmentInfo> treatments = getSession().createCriteria(TreatmentInfo.class)
				.add(Restrictions.eq("study", study)).list();
		Map<TreatmentInfo, List<DoseTimePoints>> map = new HashMap<TreatmentInfo, List<DoseTimePoints>>();
		for (TreatmentInfo tr : treatments) {
			List<DoseTimePoints> sampleTimePoints = getSession().createCriteria(DoseTimePoints.class)
					.add(Restrictions.eq("treatmentInfo", tr)).addOrder(Order.asc("timePointNo")).list();
			if (sampleTimePoints.size() > 0) {
				map.put(tr, sampleTimePoints);
			}
		}
		return map;
	}

	@Override
	public boolean saveSubjectDoseimePoints(List<SubjectDoseTimePoints> timepoints) {
		for (SubjectDoseTimePoints sstp : timepoints)
			getSession().save(sstp);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyPeriodMaster> allPeriod(StudyMaster study, StatusMaster activeStatus) {
		return getSession().createCriteria(StudyPeriodMaster.class).add(Restrictions.eq("study", study))
				.add(Restrictions.eq("periodStatus", activeStatus)).list();
	}

	@Override
	public List<SubjectPeriodStatus> saveSubjectPerioStatus(List<SubjectPeriodStatus> subjectStautsList) {
		for (SubjectPeriodStatus subjectPeriodStatus : subjectStautsList) {
			getSession().save(subjectPeriodStatus);
		}
		return subjectStautsList;
	}

	@Override
	public void saveTestProject(Projects p, List<ProjectsDetails> pdls) {
		// TODO Auto-generated method stub
		getSession().save(p);
		for (ProjectsDetails projectsDetails : pdls) {
			getSession().save(projectsDetails);
		}
	}

	@Override
	public int projectMaxId() {
		// TODO Auto-generated method stub
		try {
			Long id = (Long) getSession().createCriteria(Projects.class).setProjection(Projections.max("id"))
					.uniqueResult();
			return id.intValue();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void saveSubjectTimePoints(StudyMaster study, 
			List<SubjectPeriodStatus> subjectPerioStatus, List<SubjectRandamization> rendamization,
			List<SubjectSampleCollectionTimePoints> subjectSampleTimePoints,
			List<SubjectVitalTimePoints> subjectVitalTimePoints, List<SubjectECGTimePoints> subjectEcgTimePoints, 
			List<SubjectMealsTimePoints> subjectMealsTimePoints,
			List<SubjectDoseTimePoints> subjectDoseTimePoints) {
//		createVolunteers(vols);
		saveSubjectPerioStatus(subjectPerioStatus);
		saveRandamization(rendamization);
		study.setSubjectSamplesTimePoints(saveSubjectSampleTimePoints(subjectSampleTimePoints));
		study.setSubjectVitalsTimePoits(saveSubjectVitalTimePoints(subjectVitalTimePoints));
		study.setSubjectEcgTimePoits(saveSubjectEcgTimePoints(subjectEcgTimePoints));
		study.setSubjectMealsTimePoits(saveSubjectMealsTimePoints(subjectMealsTimePoints));
		study.setSubjectDoseingTimepoints(saveSubjectDoseimePoints(subjectDoseTimePoints));
		mergeStudy(study);
	}

	private void saveRandamization(List<SubjectRandamization> rendamization) {
		for (SubjectRandamization subjectRandamization : rendamization) {
			getSession().save(subjectRandamization);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectRandamization> createSubjectRandamization(StudyMaster study, SortedMap<Integer, String> subjects) {
		List<SubjectRandamization> list = new ArrayList<>();
		StatusMaster activeStatus = statusMaster(StudyStatus.ACTIVE.toString());
		FromStaticData fsd = formStaticData(StudyDesign.PERIODTYPE.toString(), StaticData.PERIOD.toString());
		List<TreatmentInfo> treatmentInfo = getSession().createCriteria(TreatmentInfo.class)
				.add(Restrictions.eq("study", study)).list();
		Map<Integer, TreatmentInfo> treatments = new HashMap<>();
		for (TreatmentInfo t : treatmentInfo) {
			treatments.put(Integer.parseInt(t.getTreatmentNo()), t);
		}

		List<StudyPeriodMaster> perios = getPeriod(study, fsd, activeStatus);
		int count = 1;
		for (StudyPeriodMaster sp : perios) {
			for (Map.Entry<Integer, String> subject : subjects.entrySet()) {
				SubjectRandamization sr = new SubjectRandamization();
//				sr.setVolunteer(v);
				sr.setSubjectNo(subject.getValue());
				sr.setSeqNo(subject.getKey());
				sr.setPeriod(sp);
				TreatmentInfo t = treatments.get(count);
				if (t == null) {
					count = 1;
					t = treatments.get(count);
				}
				count++;
				sr.setTreatmentInfo(t);
				sr.setRadamizationCode(t.getRandamizationCode());
				list.add(sr);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SampleTimePoints> studyTreatmentRandamization(StudyMaster study, TreatmentInfo tr) {
		List<SampleTimePoints> sampleTimePoints = new ArrayList<>();
		if (tr != null)
			sampleTimePoints = getSession().createCriteria(SampleTimePoints.class).add(Restrictions.eq("study", study))
					.add(Restrictions.eq("treatmentInfo", tr)).addOrder(Order.asc("timePointNo")).list();
		if (sampleTimePoints.size() == 0)
			sampleTimePoints = getSession().createCriteria(SampleTimePoints.class).add(Restrictions.eq("study", study))
					.addOrder(Order.asc("timePointNo")).list();
		return sampleTimePoints;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VitalTimePoints> subjectTreatmentVitalTimePoints(StudyMaster study, TreatmentInfo tr) {
		List<VitalTimePoints> vitalTimePoints = new ArrayList<>();
		if (tr != null)
			vitalTimePoints = getSession().createCriteria(VitalTimePoints.class).add(Restrictions.eq("study", study))
					.add(Restrictions.eq("treatmentInfo", tr)).addOrder(Order.asc("timePointNo")).list();
		if (vitalTimePoints.size() == 0)
			vitalTimePoints = getSession().createCriteria(VitalTimePoints.class).add(Restrictions.eq("study", study))
					.addOrder(Order.asc("timePointNo")).list();
		return vitalTimePoints;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ECGTimePoints> subjectTreatmentEcgTimePoints(StudyMaster study, TreatmentInfo tr) {
		List<ECGTimePoints> vitalTimePoints = new ArrayList<>();
		if (tr != null)
			vitalTimePoints = getSession().createCriteria(ECGTimePoints.class).add(Restrictions.eq("study", study))
					.add(Restrictions.eq("treatmentInfo", tr)).addOrder(Order.asc("timePointNo")).list();
		if (vitalTimePoints.size() == 0)
			vitalTimePoints = getSession().createCriteria(ECGTimePoints.class).add(Restrictions.eq("study", study))
					.addOrder(Order.asc("timePointNo")).list();
		return vitalTimePoints;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MealsTimePoints> subjectTreatmentMealsTimePoints(StudyMaster study, TreatmentInfo tr) {
		List<MealsTimePoints> mealsTimePoints = new ArrayList<>();
		if (tr != null)
			mealsTimePoints = getSession().createCriteria(MealsTimePoints.class).add(Restrictions.eq("study", study))
					.add(Restrictions.eq("treatmentInfo", tr)).addOrder(Order.asc("timePointNo")).list();
		if (mealsTimePoints.size() == 0)
			mealsTimePoints = getSession().createCriteria(MealsTimePoints.class).add(Restrictions.eq("study", study))
					.addOrder(Order.asc("timePointNo")).list();
		return mealsTimePoints;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DoseTimePoints> subjectTreatmentDoseTimePoints(StudyMaster study, TreatmentInfo tr) {
		List<DoseTimePoints> doseTimePoints = new ArrayList<>();
		if (tr != null)
			doseTimePoints = getSession().createCriteria(DoseTimePoints.class).add(Restrictions.eq("study", study))
					.add(Restrictions.eq("treatmentInfo", tr)).addOrder(Order.asc("timePointNo")).list();
		if (doseTimePoints.size() == 0)
			doseTimePoints = getSession().createCriteria(DoseTimePoints.class).add(Restrictions.eq("study", study))
					.addOrder(Order.asc("timePointNo")).list();
		return doseTimePoints;
	}

	@Override
	public SubjectRandamization subjectRandamization(StudyPeriodMaster period, Volunteer vol) {
		// TODO Auto-generated method stub
		return (SubjectRandamization) getSession().createCriteria(SubjectRandamization.class)
				.add(Restrictions.eq("period", period)).add(Restrictions.eq("volunteer", vol)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyPeriodMaster> allPeriodsExceptAdmission(StudyMaster study) {
		return getSession().createCriteria(StudyPeriodMaster.class).add(Restrictions.eq("study", study))
				.add(Restrictions.ne("periodNo", 0)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Volunteer> studyAllSubjects(StudyMaster sm) {
		return getSession().createCriteria(Volunteer.class).add(Restrictions.eq("study", sm)).add(Restrictions.ne("seqNo", 0)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectSampleCollectionTimePoints> subjectSampleCollectionTimePointsSubjectData(Long periodId,
			int subjectOrder) {
		List<SubjectSampleCollectionTimePoints> list = getSession().createCriteria(SubjectSampleCollectionTimePoints.class)
				.add(Restrictions.eq("period.id", periodId))
				.add(Restrictions.eq("subjectOrder", subjectOrder))
				.addOrder(Order.asc("timePointNo"))
				.list();
		for(SubjectSampleCollectionTimePoints ssctp : list) {
			if(ssctp.getTimePointType().getCode().equals(StaticData.PREDOSE.toString()))
				ssctp.setSign("-");
			else
				ssctp.setSign("");
		}
		return list;
	}

	@Override
	public StudyPeriodMaster studyPeriod(Long periodId) {
		// TODO Auto-generated method stub
		return (StudyPeriodMaster) getSession().get(StudyPeriodMaster.class, periodId);
	}

	@Override
	public Volunteer volunteerBySeqNo(StudyMaster sm, int seqNo) {
		// TODO Auto-generated method stub
		return (Volunteer) getSession().createCriteria(Volunteer.class)
				.add(Restrictions.eq("study", sm))
				.add(Restrictions.eq("seqNo", seqNo)).uniqueResult();
	}

	@Override
	public SubjectDoseTimePoints subjectDoseTimePoints(Long periodId, int subjectOrder) {
		SubjectDoseTimePoints sdtp = (SubjectDoseTimePoints) getSession().createCriteria(SubjectDoseTimePoints.class)
				.add(Restrictions.eq("period.id", periodId))
				.add(Restrictions.eq("subjectOrder", subjectOrder))
				.add(Restrictions.eq("timePointNo", 1))
				.uniqueResult();
		return sdtp;
	}

	@Override
	public List<SubjectDoseTimePoints> subjectAllDoseTimePoints(SubjectDoseTimePoints sdtp) {
//		List<SubjectDoseTimePoints> list = getSession().createCriteria(SubjectDoseTimePoints.class)
//				.add(Restrictions.eq("period.id", sdtp.getPeriod().getId()))
//				.add(Restrictions.eq("subjectOrder", sdtp.getSubjectOrder()))
//				.addOrder(Order.asc("timePointNo"))
//				.list();
//		return list;
		return null;
	}
	
	@Override
	public Projects project(String projectNo) {
		// TODO Auto-generated method stub
		return (Projects) getSession().createCriteria(Projects.class).add(Restrictions.eq("projectNo", projectNo)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PdfGenerationDetailsDto getPdfGenerationDetailsDtoDetails(List<Long> actIds, List<Long> parmIds,
			Locale currentLocale) {
		PdfGenerationDetailsDto pgdDto = null;
		List<LanguageSpecificGlobalActivityDetails> gaList = null;
		List<LanguageSpecificGlobalParameterDetails> gpList = null;
		InternationalizaionLanguages inlag = null;
		List<ParameterControlTypeValues> pctvList = null;
		Map<Long, Map<Long, List<LanguageSpecificValueDetails>>> controlMap = new HashMap<>();
		List<String> strList = new ArrayList<>();
		List<Long> actList = null;
		try {
			strList.add(com.covideinfo.enums.StudyActivities.InclusionCriteria.toString());
			strList.add(com.covideinfo.enums.StudyActivities.ExclusionCriteria.toString());
			actList = getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.in("activityCode", strList))
					.setProjection(Projections.property("id")).list();
			if(actList.size() > 0) {
				actIds.addAll(actList);
			}
			 inlag = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
						.add(Restrictions.eq("countryCode", currentLocale.getCountry())).uniqueResult();
			 
			 gaList = getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
					 .add(Restrictions.in("globalActivity.id", actIds))
			 		 .add(Restrictions.eq("inlagId", inlag)).list();
			 
			 gpList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
					 .add(Restrictions.in("parameterId.id", parmIds))
					 .add(Restrictions.eq("inlagId", inlag)).list();
			 
			 pctvList = getSession().createCriteria(ParameterControlTypeValues.class)
					 .add(Restrictions.in("globalParameter.id", parmIds)).list();
			 if(pctvList.size() > 0) {
				 for(ParameterControlTypeValues pctv : pctvList) {
					 if(pctv.getControlType().getCode().equals("RB") || pctv.getControlType().getCode().equals("SB")
							 || pctv.getControlType().getCode().equals("CB")) {
						 if(!controlMap.containsKey(pctv.getGlobalParameter().getId())) {
							 Map<Long, List<LanguageSpecificValueDetails>> gvMap = new HashMap<>();
							 List<LanguageSpecificValueDetails> lsvdList = getSession().createCriteria(LanguageSpecificValueDetails.class)
									 .add(Restrictions.eq("inlagId", inlag))
									 .add(Restrictions.eq("globalValId", pctv.getGlobalValues())).list();
							 gvMap.put(pctv.getGlobalParameter().getId(), lsvdList);
							 controlMap.put(pctv.getControlType().getId(), gvMap);
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

	@Override
	public List<LanguageSpecificGlobalParameterDetails> getParameterList(List<Long> defalutParmIds,
			InternationalizaionLanguages inlag) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificGlobalParameterDetails> getNotExistingParameterList(
			LanguageSpecificGlobalActivityDetails exeAct, List<Long> defalutParmIds,
			InternationalizaionLanguages inlag) {
		List<LanguageSpecificGlobalParameterDetails> lsgpList = null;
		List<Long> parmIds = null;
		DefaultActivitys deact = null;
		try {
			deact = (DefaultActivitys) getSession().createCriteria(DefaultActivitys.class)
					.add(Restrictions.eq("activity", exeAct)).uniqueResult();
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
	public Map<LanguageSpecificGlobalActivityDetails, List<LanguageSpecificGlobalParameterDetails>> getProjectDetailsPdfGenerationDtoForDefaultActivities(
			List<Long> defaultActIds, InternationalizaionLanguages inlag) {
		Map<LanguageSpecificGlobalActivityDetails, List<LanguageSpecificGlobalParameterDetails>> lsgaMap = new HashMap<>();
		try {
			List<Long> actIds = getSession().createCriteria(DefaultActivitys.class)
					.add(Restrictions.not(Restrictions.in("activity.id", defaultActIds)))
					.setProjection(Projections.property("activity.id")).list();
			if(actIds.size() > 0) {
				for(Long actId : actIds) {
					LanguageSpecificGlobalActivityDetails lsga = (LanguageSpecificGlobalActivityDetails) getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
							.add(Restrictions.eq("globalActivity.id", actId))
							.add(Restrictions.eq("inlagId", inlag)).uniqueResult();
					if(lsga != null) {
						List<LanguageSpecificGlobalParameterDetails> pList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
								.add(Restrictions.eq("inlagId", inlag)).list();
						lsgaMap.put(lsga, pList);
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
	public List<LanguageSpecificGlobalParameterDetails> getLanguageSpecificParmetersList(
			InternationalizaionLanguages inalg, List<Long> parmList) {
		return getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
				.add(Restrictions.in("parameterId.id", parmList))
			    .add(Restrictions.eq("inlagId", inalg)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Projects> getProjectWithProjectNo(String prono) {
		return getSession().createCriteria(Projects.class)
				.add(Restrictions.eq("projectNo", prono)).list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DeviationMessage> deviationMessages(String collectionType) {
		List<DeviationMessage>  devMList = null;
		List<String> devStrList = new ArrayList<>();
		devStrList.add(collectionType);
		devStrList.add("ALL");
		try {
			devMList = getSession().createCriteria(DeviationMessage.class).add(Restrictions.in("collectionType", devStrList))
							.add(Restrictions.eq("activeStatus", true)).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devMList;
	}	

}
