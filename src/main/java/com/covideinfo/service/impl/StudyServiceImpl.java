package com.covideinfo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.StudyDao;
import com.covideinfo.dao.UserDao;
import com.covideinfo.dto.DoseTimePointsDetailsDto;
import com.covideinfo.dto.DoseTimePointsDto;
import com.covideinfo.dto.DosingDto;
import com.covideinfo.dto.DrugDispansingDto;
import com.covideinfo.dto.GlobalparameterFromDto;
import com.covideinfo.dto.MealsDataDto;
import com.covideinfo.dto.MealsDetailsDto;
import com.covideinfo.dto.MealsDto;
import com.covideinfo.dto.RealTimeCommunicationDto;
import com.covideinfo.model.Centrifugation;
import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.DosingInfoDetails;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.Volunteer;
import com.covideinfo.service.StudyActivityService;
import com.covideinfo.service.StudyService;

@Service("studyService")
@PropertySource(value = { "classpath:application.properties" })
public class StudyServiceImpl implements StudyService {
	@SuppressWarnings("unused")
	@Autowired
	private Environment environment;

	@Autowired
	StudyDao studyDao;
	@Autowired
	UserDao userDao;
	
	@Autowired
	StudyActivityService studyActivityService;
	
	@Override
	public StudyMaster findByStudyId(Long studyId) {
		return studyDao.findByStudyId(studyId);
	}

	@Override
	public List<StudyMaster> allUserActiveStudys(UserMaster user) {
		// TODO Auto-generated method stub
		return studyDao.allUserActiveStudys(user);
	}

	@Override
	public String studyPeriodMasterSelectBox(StudyMaster study) {
		List<StudyPeriodMaster> periods = studyDao.studyPeriodMaster(study);
		StringBuilder sb = new StringBuilder();
		sb.append(study.getProjectNo()).append("--");
		sb.append("<td>Select Period </td>")
				.append("<td><select name='periodid' id='periodid' onchange=\"userperiod(this.value)\">");
		sb.append("<option value='-1'>--Select-</option>");
		for (StudyPeriodMaster sp : periods) {
			sb.append("<option value='" + sp.getId() + "'>" + sp.getPeriodNo() + "</option>");
		}
		sb.append("</select></td>");
		return sb.toString();
	}

	@Override
	public StudyPeriodMaster periodById(Long periodId) {
		return studyDao.periodById(periodId);
	}

	@Override
	public List<Volunteer> allStudyVolunteers(StudyMaster sm) {
		// TODO Auto-generated method stub
		return studyDao.allStudyVolunteers(sm);
	}

	@Override
	public StudyPeriodMaster currentPerod(StudyMaster sm) {
		// TODO Auto-generated method stub
		return studyDao.currentPerod(sm);
	}

	@Override
	public List<Centrifugation> centrifugeList() {
		// TODO Auto-generated method stub
		return studyDao.centrifugeList();
	}

	@Override
	public String saveCentrifugation(Long userId, String name, String code, String detials) {
		Centrifugation cen = new Centrifugation(name, code, detials, true);
		cen.setCreatedBy(userDao.findById(userId));
		cen = studyDao.saveCentrifugation(cen);
		StringBuilder sb = new StringBuilder();
		sb.append("<tr><td>");
		sb.append(cen.getName()).append("</td><td>").append(cen.getCode()).append("</td><td>").append(cen.getInsturmentDesc()).append("</td><td>").append(cen.isActiveStatus()).append("</td><td>");
		sb.append("<input type='button' value='Print' onclick=\"printBarcode('"+cen.getId()+"')\" ></td></tr>");
		return sb.toString();
	}

	@Override
	public List<StudyMaster> allStudys() {
		// TODO Auto-generated method stub
		return studyDao.allStudys();
	}

	@Override
	public DoseTimePointsDetailsDto getDoseTimePointsDtoDetails(Long studyId, String activityCode, Long languageId, Locale currentLocale, String mealType) {
		DoseTimePointsDto dtpDto = null;
		DoseTimePointsDetailsDto dtpdDto = null;
		SortedMap<Integer, String> barcodesMap = new TreeMap<>();
		Map<String, String> subjectTypesMap = null;
		Map<String, String> replacedBySubjects = null;
		Map<Long, String> replaceAvailSubjects = null;
		List<String> droppedSubjects = null;
		List<StudySubjectPeriods> ssubPeriods = null;
		List<DoseTimePoints> dpList = null;
		List<SubjectDoseTimePoints> sdtpIdsList = null;
		List<TreatmentInfo> treatmentList = null;
		List<StudyPeriodMaster> periodList = null;
		SortedMap<String, Map<Long, Map<Long, Map<Long, DoseTimePoints>>>> timePoints = null;
		List<SubjectMealsTimePointsData> mealsData = null;
		Map<String, Map<String, SubjectMealsTimePointsData>> mealsMap = null;
		Map<String, StudyPeriodMaster> subjectPeriodsMap = null; //subject , periodId
		StudyMaster study = null;
		Map<Long, String> treatmentMap = null;
		Map<Long, Long> firstDoseMap = null; //treatment, doseId
		Map<Long, SubjectDoseTimePoints> dosedTimePoint = null;
		GlobalparameterFromDto gpDto = null;
		GlobalActivity ga = null;
		Map<Integer, Long> doseInfoMap = new HashMap<>();
		Map<Long, String> allDoseMap = new HashMap<>(); //doseId, dosetimePoint
		Map<Long, Integer> orderedDoseMap = new HashMap<>();
		Map<Long, List<Long>> trwDoseIdsMap = new HashMap<>(); //treatmentId, ListofdoseIds
		Map<String, Map<Long, List<Long>>> subwTrMap = new HashMap<>(); //subjectNo, periodNo, treatementId
		List<StudyTreatmentWiseSubjects> studyTreatmentWiseSubjectsList = new ArrayList<>(); // SubjectNo, treatmentId
//		Map<String, Map<Long, List<SubjectDoseTimePoints>>> sdtpMap = new HashMap<>(); //subject, doseTimePointId, SubjectDoseTimePointsList
		Map<String, Map<Long, List<RealTimeCommunicationDto>>> sdtpMap = new HashMap<>(); //subject, doseTimePointId, SubjectDoseTimePointsList
		Map<Long, DoseTimePoints> dpMap = new HashMap<>();
		DosingDto dsDto = null;
		Map<String, Long> devionCodeMap = new HashMap<>();
		Map<String, StudySubjects> subMap = new HashMap<>(); //subjectNo, StudySubjects
		List<StudySubjects> subList = null;
		List<DosingInfoDetails> drugInfiList = null;
		Map<String, DrugDispansingDto> drugInfoMap = new HashMap<>();
		String projectType = "";
		try {
			dtpDto = studyDao.getDoseTimePointsDtoDetails(studyId, activityCode);
			if(dtpDto != null) {
				ga = dtpDto.getGa();
				study = dtpDto.getStudy();
				ssubPeriods = dtpDto.getSsubPeriods();
				sdtpIdsList = dtpDto.getSdtpIdsList();
				treatmentList = dtpDto.getTreatmentList();
				periodList = dtpDto.getPeriodsList();
				mealsData = dtpDto.getMealsData();
				dsDto = dtpDto.getDsDto();
				studyTreatmentWiseSubjectsList = dtpDto.getSubjectTreatmentWiseList();
				subList = dtpDto.getSubjects();
				drugInfiList = dtpDto.getDrugInfiList();
				projectType = dtpDto.getProjectType();
				
				gpDto = getDynamicDoseParameters(studyId, languageId, currentLocale, ga, dtpDto.getDpList());
				/*Map<Long, List<SubjectDoseTimePoints>> tempSdtpMap = null;
				List<SubjectDoseTimePoints> tempSdtpList = null;*/
				Map<Long, List<RealTimeCommunicationDto>> tempSdtpMap = null;
				List<RealTimeCommunicationDto> tempSdtpList = null;
				if(sdtpIdsList != null && sdtpIdsList.size() > 0) {
					for(SubjectDoseTimePoints sdtp : sdtpIdsList) {
						RealTimeCommunicationDto rtcDto = new RealTimeCommunicationDto();
						rtcDto.setPeriodId(sdtp.getPeriod().getId());
						rtcDto.setSubjectNo(sdtp.getStudySubjects().getSubjectNo());
						rtcDto.setSubjectVitalId(sdtp.getId());
						rtcDto.setTimePointId(sdtp.getDoseTimePoints().getId());
						rtcDto.setTreatmentId(sdtp.getDoseTimePoints().getTreatmentInfo().getId());
						if(sdtpMap.containsKey(sdtp.getStudySubjects().getSubjectNo())) {
							tempSdtpMap = sdtpMap.get(sdtp.getStudySubjects().getSubjectNo());
							if(tempSdtpMap.containsKey(sdtp.getDoseTimePoints().getId())) {
								tempSdtpList = tempSdtpMap.get(sdtp.getDoseTimePoints().getId());
								tempSdtpList.add(rtcDto);
								tempSdtpMap.put(sdtp.getDoseTimePoints().getId(), tempSdtpList);
								sdtpMap.put(sdtp.getStudySubjects().getSubjectNo(), tempSdtpMap);
							}else {
								tempSdtpList = new ArrayList<>();
								tempSdtpList.add(rtcDto);
								tempSdtpMap.put(sdtp.getDoseTimePoints().getId(), tempSdtpList);
								sdtpMap.put(sdtp.getStudySubjects().getSubjectNo(), tempSdtpMap);
							}
						}else {
							tempSdtpMap = new HashMap<>();
							tempSdtpList = new ArrayList<>();
							tempSdtpList.add(rtcDto);
							tempSdtpMap.put(sdtp.getDoseTimePoints().getId(), tempSdtpList);
							sdtpMap.put(sdtp.getStudySubjects().getSubjectNo(), tempSdtpMap);
						}
						
					}
				}
				if(drugInfiList != null && drugInfiList.size() > 0) {
					for(DosingInfoDetails dinf : drugInfiList) {
						DrugDispansingDto dispDto  = new DrugDispansingDto();
						dispDto.setBatchNo(dinf.getBatchNo());
						dispDto.setExpDate(dinf.getExpDate());
						dispDto.setNameOfIp(dinf.getNameOfIp());
						dispDto.setNoOfUnits(dinf.getNoOfUnits());
						dispDto.setStudyNumber(dinf.getStudy().getProjectNo());
						dispDto.setTreatmentCode(dinf.getTinfo().getRandamizationCode());
						dispDto.setTreatmentId(dinf.getTinfo().getId());
						drugInfoMap.put(dinf.getTinfo().getRandamizationCode(), dispDto);
					}
				}
				dpList = dtpDto.getDpList();
				Set<Long> allDoseIds = new HashSet<>();
				List<Long> dIds = null;
				if(dpList != null) {
					for(DoseTimePoints dtp : dpList) {
						allDoseIds.add(dtp.getId());
						allDoseMap.put(dtp.getId(), dtp.getTimePoint());
						dpMap.put(dtp.getId(), dtp);
						//For dose timepoints separation with treatmentwise for dose completion checking
						if(trwDoseIdsMap.containsKey(dtp.getTreatmentInfo().getId())){
							dIds = trwDoseIdsMap.get(dtp.getTreatmentInfo().getId());
							dIds.add(dtp.getId());
							trwDoseIdsMap.put(dtp.getTreatmentInfo().getId(), dIds);
						}else{
							dIds = new ArrayList<>();
							dIds.add(dtp.getId());
							trwDoseIdsMap.put(dtp.getTreatmentInfo().getId(), dIds);
						}
					}
				}
				//Study subjects Treatmentwise
				Map<Long, List<Long>> pstempMap = null;
				List<Long> tinfTempList = null;
				if(studyTreatmentWiseSubjectsList != null && studyTreatmentWiseSubjectsList.size() > 0) {
					for(StudyTreatmentWiseSubjects stws : studyTreatmentWiseSubjectsList) {
						String subjects = stws.getSubjects();
						if(subjects != null && !subjects.equals("")) {
							String[] tempArr = subjects.split("\\,");
							if(tempArr.length > 0) {
								for(String st : tempArr) {
									if(subwTrMap.containsKey(st)) {
										pstempMap = subwTrMap.get(st);
										if(pstempMap.containsKey(stws.getPeriod().getId())) {
											tinfTempList = pstempMap.get(stws.getPeriod().getId());
											tinfTempList.add(stws.getTreatment().getId());
											pstempMap.put(stws.getPeriod().getId(), tinfTempList);
											subwTrMap.put(st, pstempMap);
										}else {
											if(pstempMap == null || pstempMap.size() == 0)
												pstempMap = new HashMap<>();
											tinfTempList = new ArrayList<>();
											tinfTempList.add(stws.getTreatment().getId());
											pstempMap.put(stws.getPeriod().getId(), tinfTempList);
											subwTrMap.put(st, pstempMap);
										}
									}else {
										pstempMap = new HashMap<>();
										tinfTempList = new ArrayList<>();
										tinfTempList.add(stws.getTreatment().getId());
										pstempMap.put(stws.getPeriod().getId(), tinfTempList);
										subwTrMap.put(st, pstempMap);
									}
								}
							}
						}
					}
				}
				if(trwDoseIdsMap != null && trwDoseIdsMap.size() > 0) {
					for(Map.Entry<Long, List<Long>> twd : trwDoseIdsMap.entrySet()) {
						Set<Long> doseIds = new HashSet<>();
						doseIds.addAll(twd.getValue());
						int count =1;
						if(doseIds != null && doseIds.size() > 0) {
							for(Long l : doseIds) {
								orderedDoseMap.put(l, count);
								count++;
							}
						}
					}
				}
				//Deviation messages
				if(dsDto != null) {
					List<DeviationMessage> devMsgList =  dsDto.getDevMsgList();
					if(devMsgList != null && devMsgList.size() > 0) {
						for(DeviationMessage dvm : devMsgList) {
							devionCodeMap.put(dvm.getDeveloperCode(), dvm.getId());
						}
						dsDto.setDevionCodeMap(devionCodeMap);
					}
				}
//				timePoints = getDoseTimePoints(dpList, treatmentList, periodList, ssubPeriods, sdtpMap);
			    barcodesMap = getBarcodes(dtpDto.getSubjects(), dtpDto.getStudy());
				subjectTypesMap = getSubjectTypes(dtpDto.getSubjects(), dtpDto.getStudy());
				replacedBySubjects = getReplacedSubjects(dtpDto.getSubjects(), dtpDto.getStudy());
				replaceAvailSubjects = getReplacementAvailSubjects(dtpDto.getSubjects(), dtpDto.getStudy());
				droppedSubjects = getDropOutSubjects(dtpDto.getSubjects(), dtpDto.getStudy());
				mealsMap = getMealsDetails(mealsData, ssubPeriods);
				subjectPeriodsMap = getStudySubjectsPeriods(ssubPeriods);
				treatmentMap = getTreatmentDetails(treatmentList);
				firstDoseMap = getFirstDoseMapDetails(dpList);
//				dosedTimePoint = getDosedTimePoint(firstDoseMap);
				
				
				
				if(subList != null && subList.size() > 0) {
					for(StudySubjects ss : subList) {
						subMap.put(ss.getSubjectNo(), ss);
					}
				}
				
				dtpdDto = new DoseTimePointsDetailsDto();
				dtpdDto.setTreatmentProductInformation(getProductDetails(treatmentList));
				dtpdDto.setStudy(study);
				dtpdDto.setSubjectBarocodes(barcodesMap);
				dtpdDto.setSubjectTypes(subjectTypesMap);
				dtpdDto.setReplacedSubjects(replacedBySubjects);
				dtpdDto.setReplaceAvailSubjects(replaceAvailSubjects);
				dtpdDto.setDroppedSubjects(droppedSubjects);
				dtpdDto.setTimePoints(timePoints);
				dtpdDto.setMealsDetials(mealsMap);
				dtpdDto.setSubjectPeriodsMap(subjectPeriodsMap);
				dtpdDto.setTreatment(treatmentMap);
				dtpdDto.setFirstDoseMap(firstDoseMap);
				dtpdDto.setDosedTimePoint(dosedTimePoint);
				dtpdDto.setPerameters(gpDto);
				dtpdDto.setAllDoseMap(allDoseMap);
				dtpdDto.setDoseInfoMap(doseInfoMap);
				dtpdDto.setOrderedDoseMap(orderedDoseMap);
				dtpdDto.setDoseList(dpList);
				dtpdDto.setTrwDoseIdsMap(trwDoseIdsMap);
				dtpdDto.setSubwTrMap(subwTrMap);
				dtpdDto.setSdtpMap(sdtpMap);
				dtpdDto.setDpMap(dpMap);
				dtpdDto.setMealType(mealType);
				dtpdDto.setDsDto(dsDto);
				dtpdDto.setSubMap(subMap);
				dtpdDto.setDrugInfoMap(drugInfoMap);
				dtpdDto.setProjectType(projectType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtpdDto;
	}
	private GlobalparameterFromDto getDynamicDoseParameters(Long studyId, Long languageId, Locale currentLocale, GlobalActivity ga, List<DoseTimePoints> doseList) {
		GlobalparameterFromDto gpDto = null;
		try {
			if (ga != null && (doseList != null && doseList.size() > 0)) {
				for(DoseTimePoints dp : doseList) {
					String doseStr = dp.getParameters();
					List<Long> doseIds = new ArrayList<>();
					if(doseStr != null && !doseStr.equals("")) {
						String[] tempArr = doseStr.split("\\,");
						if(tempArr.length > 0) {
							for(String st : tempArr) {
								if(st != null && !st.equals("")) {
									doseIds.add(Long.parseLong(st));
								}
							}
						}
					}
					if(doseIds != null && doseIds.size() > 0) {
						gpDto = studyActivityService.getLanguageSpecificGlobalParameters(doseIds, languageId, ga.getId());
					}
				}
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gpDto;
	}

	private SortedMap<String, Map<Long, Map<Long, Map<Long, DoseTimePoints>>>> getDoseTimePoints(List<DoseTimePoints> dpList,
			List<TreatmentInfo> treatmentList, List<StudyPeriodMaster> periodList,
			List<StudySubjectPeriods> ssubPeriods, Map<Long, List<SubjectDoseTimePoints>> sdtpMap) {
			SortedMap<String, Map<Long, Map<Long, Map<Long, DoseTimePoints>>>> timePoints = new TreeMap<>();//subject, period, treatment, dosetimePointPojo
			Map<Long, Map<Long, Map<Long, DoseTimePoints>>> doseMap1 = null;; 
			Map<Long, Map<Long, DoseTimePoints>> doseMap2 = null;
			Map<Long, DoseTimePoints> doseMap3 = null;
			if(dpList != null && dpList.size() > 0) {
//					if(treatmentList != null && treatmentList.size() > 0) {
//					    if(periodList != null && periodList.size() > 0) {
					    	 if(ssubPeriods != null && ssubPeriods.size() > 0) {
					    			for(TreatmentInfo ti : treatmentList) {
					    			  for(StudySubjectPeriods ssp : ssubPeriods) {
					    				  for(DoseTimePoints dose : dpList) {
				    					   	doseMap1 = timePoints.get(ssp.getSubject().getSubjectNo());
				    					  	if(doseMap1 == null)
				    					  		doseMap1 = new HashMap<>();
				    					  	doseMap2 = doseMap1.get(ssp.getPeriodId().getId());
				    					  	if(doseMap2 == null)
				    					  		doseMap2 = new HashMap<>();
				    					    doseMap3 = doseMap2.get(ti.getId());
					    				    if(doseMap3 == null)
					    				    	doseMap3 = new HashMap<>();
						    				  
								    		 if(sdtpMap.containsKey(dose.getId())) {
						    					List<SubjectDoseTimePoints> sdtpList = sdtpMap.get(dose.getId());
						    					if(sdtpList != null && sdtpList.size() > 0) {
						    						for(SubjectDoseTimePoints sdt : sdtpList) {
						    							if(sdt.getDoseTimePoints().getTreatmentInfo().getId() == ti.getId()) {
						    								if(sdt.getStudySubjects().getId() != ssp.getSubject().getId()){
						    									doseMap3.put(dose.getId(), dose);
							    								doseMap2.put(dose.getTreatmentInfo().getId(), doseMap3);
							    								doseMap1.put(ssp.getPeriodId().getId(), doseMap2);
							    								timePoints.put(ssp.getSubject().getSubjectNo(), doseMap1);
							    							}
						    							}else {
						    								doseMap3.put(dose.getId(), dose);
						    								doseMap2.put(ti.getId(), doseMap3);
						    								doseMap1.put(ssp.getPeriodId().getId(), doseMap2);
						    								timePoints.put(ssp.getSubject().getSubjectNo(), doseMap1);
						    							}
						    						}
						    					}else {
						    						doseMap3.put(dose.getId(), dose);
				    								doseMap2.put(ti.getId(), doseMap3);
				    								doseMap1.put(ssp.getPeriodId().getId(), doseMap2);
				    								timePoints.put(ssp.getSubject().getSubjectNo(), doseMap1);
						    					}
						    				}else {
						    					doseMap3.put(dose.getId(), dose);
			    								doseMap2.put(ti.getId(), doseMap3);
			    								doseMap1.put(ssp.getPeriodId().getId(), doseMap2);
			    								timePoints.put(ssp.getSubject().getSubjectNo(), doseMap1);
						    				}
						    			}
						    		}
						    	}
					    	 }
//					    }
//					}
				}
			  
		return timePoints;
	}

	private List<String> getDropOutSubjects(List<StudySubjects> subjects, StudyMaster study) {
		List<String> droppedSubjects = new ArrayList<>();
		subjects.stream().forEach((subject) -> {
			if (subject.getSubjectStatus() != null && subject.getSubjectStatus().equals("DropOut"))
				droppedSubjects.add(subject.getSubjectNo());
		});
		return droppedSubjects;
	}

	private Map<Long, String> getReplacementAvailSubjects(List<StudySubjects> subjects, StudyMaster study) {
		Map<Long, String> replaceAvaileSubMap = new HashMap<>();
		subjects.stream().forEach((subject) -> {
			if (subject.getStdSubjectId() == null && !subject.getSubjectReplace().equals("") && subject.getSubjectReplace().equals("Yes"))
				replaceAvaileSubMap.put(subject.getId(), subject.getSubjectNo());
		});
		return replaceAvaileSubMap;
	}

	private Map<String, String> getReplacedSubjects(List<StudySubjects> subjects, StudyMaster study) {
		Map<String, String> replacedBySubjects = new HashMap<>();
		subjects.stream().forEach((subject) -> {
			if (subject.getStdSubjectId() != null)
				replacedBySubjects.put(subject.getSubjectNo(), subject.getStdSubjectId().getSubjectNo());
		});
		return replacedBySubjects;
	}

	@SuppressWarnings("unused")
	private Map<String, String> getSubjectTypes(List<StudySubjects> subjects, StudyMaster study) {
		Map<String, String> map = new HashMap<>();
		subjects.stream().forEach((subject) -> {
			try {
				int sub = Integer.parseInt(subject.getSubjectNo());
				map.put(subject.getSubjectNo(), "not");
			} catch (NumberFormatException e) {
				map.put(subject.getSubjectNo(), "standby");
			}
		});
		return map;
	}

	public SortedMap<Integer, String> getBarcodes(List<StudySubjects> subjects, StudyMaster study) {
		SortedMap<Integer, String> barcodesMap = new TreeMap<>();
		int count = 1;
		for (StudySubjects subject : subjects) {
			StringBuilder sb = new StringBuilder();
			sb.append("02");
			sb.append("a");
			sb.append(subject.getSubjectNo());
			sb.append("a");
			sb.append(study.getId());
			sb.append("n");
			barcodesMap.put(count++, sb.toString());
		}
		return barcodesMap;
	}

	private Map<String, Map<String, SubjectMealsTimePointsData>> getMealsDetails(
			List<SubjectMealsTimePointsData> mealsData, List<StudySubjectPeriods> ssubPeriods) {
		Map<String, Map<String, SubjectMealsTimePointsData>> map = new HashMap<>();
		if(ssubPeriods != null && ssubPeriods.size() > 0) {
			for (StudySubjectPeriods ssp : ssubPeriods) {
				Map<String, SubjectMealsTimePointsData> mp = new HashMap<>();
				if (mealsData != null && mealsData.size() > 0) {
					for (SubjectMealsTimePointsData smtd : mealsData) {
						mp.put(smtd.getMealsTimePoint().getMealsType().getCode(), smtd);
					}
				}
				map.put(ssp.getPeriodId().getId() + "," + ssp.getSubject().getSubjectNo(), mp);
			}
		}
		return map;
	}

	private Map<String, StudyPeriodMaster> getStudySubjectsPeriods(List<StudySubjectPeriods> ssubPeriods) {
		Map<String, StudyPeriodMaster> subjectPeriodsMap = new HashMap<>();
		if (ssubPeriods != null && ssubPeriods.size() > 0) {
			for (StudySubjectPeriods ssp : ssubPeriods) {
				subjectPeriodsMap.put(ssp.getSubject().getSubjectNo(), ssp.getPeriodId());
			}

		}
		return subjectPeriodsMap;
	}

	private Map<Long, String> getTreatmentDetails(List<TreatmentInfo> treatmentList) {
		Map<Long, String> treatmentMap = new HashMap<>();
		if (treatmentList != null && treatmentList.size() > 0) {
			for (TreatmentInfo tinf : treatmentList) {
				treatmentMap.put(tinf.getId(), tinf.getRandamizationCode());
			}
		}
		return treatmentMap;
	}
	
	private Map<Long, String> getProductDetails(List<TreatmentInfo> treatmentList) {
		Map<Long, String> treatmentMap = new HashMap<>();
		if (treatmentList != null && treatmentList.size() > 0) {
			for (TreatmentInfo tinf : treatmentList) {
				treatmentMap.put(tinf.getId(), tinf.getTreatmentName());
			}
		}
		return treatmentMap;
	}

	private Map<Long, Long> getFirstDoseMapDetails(List<DoseTimePoints> dpList) {
		Map<Long, Long> firstDoseMap = new HashMap<>();
		Map<Long, List<Long>> twdoseList = new HashMap<>();
		List<Long> tempIds = null;
		if (dpList != null && dpList.size() > 0) {
			for (DoseTimePoints dp : dpList) {
				if(twdoseList.containsKey(dp.getTreatmentInfo().getId())) {
					tempIds = twdoseList.get(dp.getTreatmentInfo().getId());
					tempIds.add(dp.getId());
					twdoseList.put(dp.getTreatmentInfo().getId(), tempIds);
				}else{
					tempIds = new ArrayList<>();
					tempIds.add(dp.getId());
					twdoseList.put(dp.getTreatmentInfo().getId(), tempIds);
				}
			}
		}
		if(twdoseList != null && twdoseList.size() > 0) {
			for(Map.Entry<Long, List<Long>> twdids : twdoseList.entrySet()) {
				List<Long> doseIdsList = twdids.getValue();
				Long minId = Collections.min(doseIdsList);
				firstDoseMap.put(twdids.getKey(), minId);
			}
		}
		return firstDoseMap;
	}

	/*private Map<Long, SubjectDoseTimePoints> getDosedTimePoint(Map<String, Long> firstDoseMap) {
		Map<Long, SubjectDoseTimePoints> dosedMap = new HashMap<>();
		if (firstDoseMap.size() > 0) {
			for (Map.Entry<String, Long> dmap : firstDoseMap.entrySet()) {
				SubjectDoseTimePoints sdtp = studyDao.getSubjectDoseTimePointsRecord(dmap.getValue());
				if (sdtp != null)
					dosedMap.put(sdtp.getId(), sdtp);
			}
		}
		return dosedMap;
	}*/

	@Override
	public MealsDetailsDto getMealsTimePoints(Long studyId, String subjectNo) {
		List<MealsTimePoints> mealsList = null;
		List<SubjectDoseTimePoints> sdtpList = null;
		MealsDetailsDto mealsDto = null;
		MealsDto mealdto = null;
		List<SubjectMealsTimePointsData> smtpDataList = null;
		List<MealsDataDto> mealsDataList = new ArrayList<>();
		MealsDataDto mdto = null;
		try {
			mealdto = studyDao.getMealsTimePointsDtoDetails(studyId, subjectNo);
			if(mealdto != null) {
				mealsList = mealdto.getMealsList();
				smtpDataList = mealdto.getMtpdataList();
				sdtpList = mealdto.getSdtpList();
				if(smtpDataList != null && smtpDataList.size() > 0) {
					Map<Long, SubjectMealsTimePointsData> smtpdMap = new HashMap<>();
					for(SubjectMealsTimePointsData smtpd : smtpDataList) {
						smtpdMap.put(smtpd.getMealsTimePoint().getId(), smtpd);
					}
					Date startTime = null;
					Date endTime = null;
					if(sdtpList != null && sdtpList.size() > 0) {
						for(MealsTimePoints mtp : mealsList) {
							if(smtpdMap.get(mtp.getId()) != null) {
								startTime = smtpdMap.get(mtp.getId()).getStartTime();
								endTime = smtpdMap.get(mtp.getId()).getEndTime();
								mdto = getMealsDataDtoDetails(startTime, endTime, mtp);
								mealsDataList.add(mdto);
							}else {
								startTime = smtpdMap.get(mtp.getId()).getStartTime();
								endTime = smtpdMap.get(mtp.getId()).getEndTime();
								mdto = getMealsDataDtoDetails(startTime, endTime, mtp);
								mealsDataList.add(mdto);
							}
						}
					}else {
						for(MealsTimePoints mtp : mealsList) {
							if(mtp.getSign().equals("-")) {
								if(smtpdMap.get(mtp.getId()) != null) {
									startTime = smtpdMap.get(mtp.getId()).getStartTime();
									endTime = smtpdMap.get(mtp.getId()).getEndTime();
								}
								mdto = getMealsDataDtoDetails(startTime, endTime, mtp);
								mealsDataList.add(mdto);
							}
						}
					}
				}else {
					for(MealsTimePoints mtp : mealsList) {	
						if(mtp.getSign().equals("-")) {
							Date startTime = null;
							Date endTime = null;
							mdto = getMealsDataDtoDetails(startTime, endTime, mtp);
							mealsDataList.add(mdto);
						}
					}
				}
			}
			mealsDto = new MealsDetailsDto();
			mealsDto.setMealDataMap(mealsDataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mealsDto;
	}
	private MealsDataDto getMealsDataDtoDetails(Date startTime, Date endTime, MealsTimePoints mtp) {
		MealsDataDto mdto = new MealsDataDto();
		try {
			if(startTime == null || endTime == null) {
				SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
				String stTime = "";
				String endtime = "";
				if(startTime != null) {
					stTime = localDateFormat.format(startTime);
				}
				if(endTime != null) {
					endtime = localDateFormat.format(endTime);
				}
				
				mdto.setTimepointId(mtp.getId());
				mdto.setEndTime(endtime);
				mdto.setStartTime(stTime);
				mdto.setTimePoint(mtp.getTimePoint());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mdto;
	}

	@Override
	public DosingDto getDosingDtoDetails(Long studyId, String collectionStr) {
		return studyDao.getDosingDtoDetails(studyId, collectionStr);
		
	}

	@Override
	public void saveApplicationSideMenuds() {
		studyDao.saveApplicationSideMenuds();
		
	}

	@Override
	public List<StudyPeriodMaster> getStudyPeriodMasterList(Long id) {
		return studyDao.getStudyPeriodMasterList(id);
	}
	
	
}
