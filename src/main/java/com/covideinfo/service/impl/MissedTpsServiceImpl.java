package com.covideinfo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.MissedTpsDao;
import com.covideinfo.dto.MisedTpsDto;
import com.covideinfo.dto.MissedTimePointsDetailsDto;
import com.covideinfo.dto.MissedTimePointsDto;
import com.covideinfo.dto.StudyPeriodsDto;
import com.covideinfo.dto.SubjectRandomizationDto;
import com.covideinfo.dto.TimeDto;
import com.covideinfo.dto.VitalTimePointsDto;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.DosingInfo;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.service.MissedTpsService;

@Service("missedTpsService")
public class MissedTpsServiceImpl implements MissedTpsService {
	
	@Autowired
	MissedTpsDao missedTpsDao;

	@Override
	public MissedTimePointsDto getMissedTimePointsDtoDetails() {
		return missedTpsDao.getMissedTimePointsDtoDetails();
	}

	@Override
	public MissedTimePointsDto generateMissedTimepointsReport(Long studyId, Locale currentLocale, String dateStr) {
		MissedTimePointsDto mtpDto = new MissedTimePointsDto();
		MissedTimePointsDetailsDto mtpdDto = null;
		List<SubjectDoseTimePoints> sdtpList = null;
		List<SubjectMealsTimePointsData> smtpList = null;
		List<SubjectSampleCollectionTimePointsData> sctpList = null;
		List<SubjectVitalTimePointsData> svtpList;
		List<MealsTimePoints> mealTpsList = null;
		List<VitalTimePointsDto> vitalList = null;
		List<SampleTimePoints> stpList = null;
		List<DoseTimePoints> doseList = null;
		List<StudySubjects> subjectsList = null;
		List<Long> periodIds = null;
		DosingInfo doseInfo = null; 
		Integer washoutPeriodDays =0;
		List<SubjectRandomizationDto> subradDtoList = null;
		Map<Long, String> periodNamesMap = new HashMap<>();//periodId, periodName
		Map<Long, Integer> periodNoMap = new HashMap<>(); //priodId, periodNo
		List<StudyPeriodsDto> spmList = null;
		List<SubjectDoseTimePoints> pwSubDoseList = null;
		Map<Long, SubjectDoseTimePoints> pwDosedMap = new HashMap<>();//periodId, maxDosedTimeRecord
		Map<Long, Map<String, Long>> rmzMap = new HashMap<>();//periodId, subject, treatmentId
		Map<Long, Map<String, Map<String, SubjectDoseTimePoints>>> sdtpdMap = new HashMap<>();//periodId, timepoint, Subject, DosingData
		Map<Long, Map<String, SubjectMealsTimePointsData>> smtpdMap = new HashMap<>();// periodId, timePoint, subjectmealDataPojo
		Map<Long, Map<String, Map<Integer, SubjectSampleCollectionTimePointsData>>> ssctdMap = new HashMap<>();// periodId, timePoint, vacutainerNo, subjectmealDataPojo
		Map<Long, Map<String, Map<String, SubjectVitalTimePointsData>>> svtpDataMap = new HashMap<>();// periodId, timePoint, positionNo, subjectmealDataPojo
		
		
		Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> dosetpMap = new HashMap<>();//periodId, subject, timepoint, scheduledTime
		Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> mtpMap = new HashMap<>();//periodId, subject, timepoint, scheduledTime
		Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> stpMap = new HashMap<>();//periodId, subject, timepoint, scheduledTime
		Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> vtpMap = new HashMap<>();//periodId, subject, timepoint, scheduledTime
		List<MisedTpsDto> tempTpList = null;
		try {
			mtpdDto = missedTpsDao.generateMissedTimepointsReport(studyId, currentLocale);
			if(mtpdDto != null) {
				doseInfo = mtpdDto.getDoseInfo();
				subradDtoList = mtpdDto.getSubradDtoList();
				periodIds = mtpdDto.getPeriodIds();
				subjectsList = mtpdDto.getSubjectsList();
				sdtpList = mtpdDto.getSdtpList();
				doseList = mtpdDto.getDoseList();
				smtpList = mtpdDto.getSmtpList();
				mealTpsList = mtpdDto.getMealTpsList();
				sctpList = mtpdDto.getSctpList();
				stpList = mtpdDto.getStpList();
				svtpList = mtpdDto.getSvtpList();
				vitalList = mtpdDto.getVitalList();
				spmList = mtpdDto.getSpmList();
				pwSubDoseList = mtpdDto.getPwSubDoseList();
				washoutPeriodDays = mtpdDto.getWashoutPeriodDays();
				
				if(spmList != null && spmList.size() > 0) {
					for(StudyPeriodsDto spm : spmList) {
						periodNamesMap.put(spm.getId(), spm.getPeriodName());
						periodNoMap.put(spm.getId(), spm.getPeriodNo());
					}
				}
				if(pwSubDoseList != null && pwSubDoseList.size() > 0) {
					for(SubjectDoseTimePoints sdtp : pwSubDoseList) {
						pwDosedMap.put(sdtp.getPeriod().getId(), sdtp);
					}
				}
				
				//Randomization
				if(subradDtoList != null && subradDtoList.size() > 0) {
					Map<String, Long> subMap = null;
					if(periodIds != null && periodIds.size() > 0) {
						for(Long periodId : periodIds) {
							for(SubjectRandomizationDto srdto : subradDtoList) {
								if(rmzMap.containsKey(periodId)) {
									subMap = rmzMap.get(periodId);
									if(!subMap.containsKey(srdto.getSubjectNo())) {
										subMap.put(srdto.getSubjectNo(), srdto.getTreatmentId());
										rmzMap.put(srdto.getPeriodId(), subMap);
									}
								}else {
									subMap = new HashMap<>();
									subMap.put(srdto.getSubjectNo(), srdto.getTreatmentId());
									rmzMap.put(srdto.getPeriodId(), subMap);
								}
							}
						}
					}
				}
				
				//Dosing Data
				if(sdtpList!= null && sdtpList.size() > 0) {
					Map<String, Map<String, SubjectDoseTimePoints>> sdtpTempMap = null;
					Map<String, SubjectDoseTimePoints> subDoseMap = null;
					for(SubjectDoseTimePoints sdtp : sdtpList) {
						String subNo = "";
						String actualSubNo = "";
						if(sdtp.getStudySubjects().getStdSubjectId() != null) {
							subNo = sdtp.getStudySubjects().getReportingId().getSubjectNo();
							actualSubNo = sdtp.getStudySubjects().getStdSubjectId().getSubjectNo();
						}else {
							subNo = sdtp.getStudySubjects().getReportingId().getSubjectNo();
							actualSubNo = sdtp.getStudySubjects().getSubjectNo();
						}
						boolean flag = false;
						if(rmzMap.get(sdtp.getPeriod().getId()) != null) {
							Long treatmentId = rmzMap.get(sdtp.getPeriod().getId()).get(actualSubNo);
							if(treatmentId != null) {
								if(treatmentId == sdtp.getDoseTimePoints().getTreatmentInfo().getId())
									flag = true;
							}
						}
						if(flag) {
							if(sdtpdMap.containsKey(sdtp.getPeriod().getId())) {
								sdtpTempMap = sdtpdMap.get(sdtp.getPeriod().getId());
								if(sdtpTempMap.containsKey(sdtp.getDoseTimePoints().getTimePoint())) {
									subDoseMap = sdtpTempMap.get(sdtp.getDoseTimePoints().getTimePoint());
									if(!subDoseMap.containsKey(subNo)) {
										subDoseMap.put(subNo, sdtp);
										sdtpTempMap.put(sdtp.getDoseTimePoints().getTimePoint(), subDoseMap);
										sdtpdMap.put(sdtp.getPeriod().getId(), sdtpTempMap);
									}
								}else {
									subDoseMap = new HashMap<>();
									subDoseMap.put(subNo, sdtp);
									sdtpTempMap.put(sdtp.getDoseTimePoints().getTimePoint(), subDoseMap);
									sdtpdMap.put(sdtp.getPeriod().getId(), sdtpTempMap);
								}
							}else {
								sdtpTempMap = new HashMap<>();
								subDoseMap = new HashMap<>();
								subDoseMap.put(subNo, sdtp);
								sdtpTempMap.put(sdtp.getDoseTimePoints().getTimePoint(), subDoseMap);
								sdtpdMap.put(sdtp.getPeriod().getId(), sdtpTempMap);
							}
						}
					}
				}
				
				//Meals
				if(smtpList != null && smtpList.size() > 0) {
					Map<String, SubjectMealsTimePointsData> smtpdTemp = null;
					for(SubjectMealsTimePointsData smtp : smtpList) {
						if(smtp.getStartTime() != null && smtp.getEndTime() != null) {
							if(smtpdMap.containsKey(smtp.getStudyPeriodMaster().getId())) {
								smtpdTemp = smtpdMap.get(smtp.getStudyPeriodMaster().getId());
								smtpdTemp.put(smtp.getMealsTimePoint().getSign()+smtp.getMealsTimePoint().getTimePoint(), smtp);
								smtpdMap.put(smtp.getStudyPeriodMaster().getId(), smtpdTemp);
							}else {
								smtpdTemp = new HashMap<>();
								smtpdTemp.put(smtp.getMealsTimePoint().getSign()+smtp.getMealsTimePoint().getTimePoint(), smtp);
								smtpdMap.put(smtp.getStudyPeriodMaster().getId(), smtpdTemp);
							}
						}
					}
				}
				if(sctpList != null && sctpList.size() > 0) {
					Map<String, Map<Integer, SubjectSampleCollectionTimePointsData>> scdTpMap = null;
					Map<Integer, SubjectSampleCollectionTimePointsData> vacSampMap = null;
					for(SubjectSampleCollectionTimePointsData ssctp : sctpList) {
						if(ssctp.getActualtime() != null) {
							if(ssctdMap.containsKey(ssctp.getStudyPeriodMaster().getId())) {
								scdTpMap = ssctdMap.get(ssctp.getStudyPeriodMaster().getId());
								if(scdTpMap.containsKey(ssctp.getSampleTimePoint().getSign()+ssctp.getSampleTimePoint().getTimePoint())) {
									vacSampMap = scdTpMap.get(ssctp.getSampleTimePoint().getSign()+ssctp.getSampleTimePoint().getTimePoint());
									vacSampMap.put(ssctp.getSampleTimePoint().getVacutainerNo(), ssctp);
									scdTpMap.put(ssctp.getSampleTimePoint().getSign()+ssctp.getSampleTimePoint().getTimePoint(), vacSampMap);
									ssctdMap.put(ssctp.getStudyPeriodMaster().getId(), scdTpMap);
								}else {
									vacSampMap = new HashMap<>();
									vacSampMap.put(ssctp.getSampleTimePoint().getVacutainerNo(), ssctp);
									scdTpMap.put(ssctp.getSampleTimePoint().getSign()+ssctp.getSampleTimePoint().getTimePoint(), vacSampMap);
									ssctdMap.put(ssctp.getStudyPeriodMaster().getId(), scdTpMap);
								}
							}else {
								scdTpMap = new HashMap<>();
								vacSampMap = new HashMap<>();
								scdTpMap = new HashMap<>();
								vacSampMap.put(ssctp.getSampleTimePoint().getVacutainerNo(), ssctp);
								scdTpMap.put(ssctp.getSampleTimePoint().getSign()+ssctp.getSampleTimePoint().getTimePoint(), vacSampMap);
								ssctdMap.put(ssctp.getStudyPeriodMaster().getId(), scdTpMap);
							}
					}
				}
				//VitalData
				if(svtpList != null && svtpList.size() > 0) {
					Map<String, Map<String, SubjectVitalTimePointsData>> vitaTpMap = null;
					Map<String, SubjectVitalTimePointsData> vpMap = null;
					for(SubjectVitalTimePointsData svtpd : svtpList) {
						if(svtpDataMap.containsKey(svtpd.getPeriod().getId())) {
							vitaTpMap = svtpDataMap.get(svtpd.getPeriod().getId());
							if(vitaTpMap.containsKey(svtpd.getTimepoint().getSign()+svtpd.getTimepoint().getTimePoint())) {
								vpMap = vitaTpMap.get(svtpd.getTimepoint().getSign()+svtpd.getTimepoint().getTimePoint());
								if(!vpMap.containsKey(svtpd.getColltedPosition())) {
									vpMap.put(svtpd.getColltedPosition(), svtpd);
									vitaTpMap.put(svtpd.getTimepoint().getSign()+svtpd.getTimepoint().getTimePoint(), vpMap);
									svtpDataMap.put(svtpd.getPeriod().getId(), vitaTpMap);
								}
							}else {
								vpMap = new HashMap<>();
								vpMap.put(svtpd.getColltedPosition(), svtpd);
								vitaTpMap.put(svtpd.getTimepoint().getSign()+svtpd.getTimepoint().getTimePoint(), vpMap);
								svtpDataMap.put(svtpd.getPeriod().getId(), vitaTpMap);
							}
						}else {
							vpMap = new HashMap<>();
							vitaTpMap = new HashMap<>();
							vpMap.put(svtpd.getColltedPosition(), svtpd);
							vitaTpMap.put(svtpd.getTimepoint().getSign()+svtpd.getTimepoint().getTimePoint(), vpMap);
							svtpDataMap.put(svtpd.getPeriod().getId(), vitaTpMap);
						}
					}
				}
					
				if(subjectsList != null && subjectsList.size() > 0) {
					//Subjects
					for(StudySubjects sub : subjectsList) {
//						Long treatmentId = null;
						String subjNo = "";
						String actualSubNo = "";
						if(sub.getStdSubjectId() != null) {
							subjNo = sub.getStdSubjectId().getReportingId().getSubjectNo();
							actualSubNo = sub.getStdSubjectId().getSubjectNo();
						}else {
							subjNo = sub.getReportingId().getSubjectNo();
							actualSubNo = sub.getSubjectNo();
						}
						//periods
						if(periodIds != null && periodIds.size() > 0) {
							for(Long periodId : periodIds) {
								
								/*//Subject treatment
								if(rmzMap.get(periodId) != null) {
									if(rmzMap.get(periodId).get(actualSubNo) != null) 
										treatmentId = rmzMap.get(periodId).get(actualSubNo);
								}*/
								
								//Dosing
								if(doseList != null && doseList.size() > 0) {
									Map<String, Map<String, List<MisedTpsDto>>> subjDoseMap = null; 
									Map<String, List<MisedTpsDto>> tpdoseMap = null;
									for(DoseTimePoints dp : doseList) {
										MisedTpsDto msDto = null;
										if(doseInfo != null) 
											msDto = getCaluclatedDateAndTime(doseInfo, dp.getTimePoint(), dateStr, pwDosedMap, periodNoMap, washoutPeriodDays, periodId);
										if(sdtpdMap.containsKey(periodId)) {
											if(sdtpdMap.get(periodId) != null) {
												if(sdtpdMap.get(periodId).get(dp.getTimePoint()) != null) {
													if(sdtpdMap.get(periodId).get(dp.getTimePoint()).get(actualSubNo) == null) 
														dosetpMap = getDosingMissedSubjects(dosetpMap, subjDoseMap, tpdoseMap, periodId, dp, actualSubNo, msDto, tempTpList, subjNo);
												}else 
													dosetpMap = getDosingMissedSubjects(dosetpMap, subjDoseMap, tpdoseMap, periodId, dp, actualSubNo, msDto, tempTpList, subjNo);
											}else 
												dosetpMap = getDosingMissedSubjects(dosetpMap, subjDoseMap, tpdoseMap, periodId, dp, actualSubNo, msDto, tempTpList, subjNo);
										}else 
											dosetpMap = getDosingMissedSubjects(dosetpMap, subjDoseMap, tpdoseMap, periodId, dp, actualSubNo, msDto, tempTpList, subjNo);
									}
								}
								//Meals
								if(mealTpsList != null && mealTpsList.size() > 0) { 
									Map<String, Map<String, List<MisedTpsDto>>> subTempMap = null;
									Map<String, List<MisedTpsDto>> tempMap = null;
									for(MealsTimePoints mtp : mealTpsList) {
										if(smtpdMap.containsKey(periodId)) {
											SubjectMealsTimePointsData smtpd = null;
											if(smtpdMap != null && smtpdMap.size() > 0) 
												if(smtpdMap.get(periodId) != null) {
													smtpd = smtpdMap.get(periodId).get(mtp.getSign()+mtp.getTimePoint());
											}
											if(smtpd == null) {
												MisedTpsDto mtDto = getMisedTpsDtoRecord(sdtpdMap, subjNo, periodId, dateStr, doseInfo, mtp.getTimePoint(), mtp.getSign(), pwDosedMap, periodNoMap, washoutPeriodDays);
												if(mtpMap.containsKey(periodId)) {
													subTempMap = mtpMap.get(periodId);
													if(subTempMap.containsKey(subjNo)) {
														tempMap = subTempMap.get(subjNo);
														if(!tempMap.containsKey(mtp.getSign()+mtp.getTimePoint())) {
															tempTpList = new ArrayList<>();
															tempTpList.add(mtDto);
															tempMap.put(mtp.getSign()+mtp.getTimePoint(), tempTpList);
															subTempMap.put(subjNo, tempMap);
															mtpMap.put(periodId, subTempMap);
														}/*else {
															tpsList = new ArrayList<>();
															tpsList.add(mtDto);
															tempMap.put(mtp.getSign()+mtp.getTimePoint(), tpsList);
															subTempMap.put(subjNo, tempMap);
															mtpMap.put(periodId, subTempMap);
														}*/
													}else {
														tempTpList = new ArrayList<>();
														tempMap = new HashMap<>();
														tempTpList.add(mtDto);
														tempMap.put(mtp.getSign()+mtp.getTimePoint(), tempTpList);
														subTempMap.put(subjNo, tempMap);
														mtpMap.put(periodId, subTempMap);
													}
												}else {
													tempTpList = new ArrayList<>();
													tempMap = new HashMap<>();
													subTempMap = new HashMap<>();
													tempTpList.add(mtDto);
													tempMap.put(mtp.getSign()+mtp.getTimePoint(), tempTpList);
													subTempMap.put(subjNo, tempMap);
													mtpMap.put(periodId, subTempMap);
												}
											}
										}
									}
								}
								//Samples
								if(stpList != null && stpList.size() > 0) { 
									Map<String, Map<String, List<MisedTpsDto>>> subTempMap = null;
									Map<String, List<MisedTpsDto>> tempMap = null;
									for(SampleTimePoints stp : stpList) {
										for(int i=1; i<=stp.getVacutainerNo(); i++) {
											if(smtpdMap.containsKey(periodId)) {
												SubjectSampleCollectionTimePointsData ssctpd = null;
												if(ssctdMap.size() > 0) {
													if(ssctdMap.get(periodId) != null) {
														if(ssctdMap.get(periodId).get(stp.getSign()+stp.getTimePoint()) != null) {
															ssctpd = ssctdMap.get(periodId).get(stp.getSign()+stp.getTimePoint()).get(i);
														}
													}
												}
												if(ssctpd == null) {
													MisedTpsDto mtDto = getMisedTpsDtoRecord(sdtpdMap, subjNo, periodId, dateStr, doseInfo, stp.getTimePoint(), stp.getSign(), pwDosedMap, periodNoMap, washoutPeriodDays);
													mtDto.setVacutainerNo(stp.getVacutainerNo());
													if(stpMap.containsKey(periodId)) {
														subTempMap = stpMap.get(periodId);
														if(subTempMap.containsKey(subjNo)) {
															tempMap = subTempMap.get(subjNo);
															if(tempMap.containsKey(stp.getSign()+stp.getTimePoint())) {
																tempTpList = tempMap.get(stp.getSign()+stp.getTimePoint());
																tempTpList.add(mtDto);
																tempMap.put(stp.getSign()+stp.getTimePoint(), tempTpList);
																subTempMap.put(subjNo, tempMap);
																stpMap.put(periodId, subTempMap);
															}else {
																tempTpList = new ArrayList<>();
																tempTpList.add(mtDto);
																tempMap.put(stp.getSign()+stp.getTimePoint(), tempTpList);
																subTempMap.put(subjNo, tempMap);
																mtpMap.put(periodId, subTempMap);
															}
														}else {
															tempTpList = new ArrayList<>();
															tempMap = new HashMap<>();
															tempTpList.add(mtDto);
															tempMap.put(stp.getSign()+stp.getTimePoint(), tempTpList);
															subTempMap.put(subjNo, tempMap);
															stpMap.put(periodId, subTempMap);
														}
													}else {
														tempTpList = new ArrayList<>();
														tempMap = new HashMap<>();
														subTempMap = new HashMap<>();
														tempTpList.add(mtDto);
														tempMap.put(stp.getSign()+stp.getTimePoint(), tempTpList);
														subTempMap.put(subjNo, tempMap);
														stpMap.put(periodId, subTempMap);
													}
												}
											}
										}
									}
								}
								//vitals
								if(vitalList != null && vitalList.size() > 0) { 
									Map<String, Map<String, List<MisedTpsDto>>> subTempMap = null;
									Map<String, List<MisedTpsDto>> tempMap = null;
									for(VitalTimePointsDto vtp : vitalList) {
										List<String> postitions = new ArrayList<>();
										if(vtp.isOrthostatic()) {
											postitions.add(vtp.getOrthostaticPosition());
											postitions.add(vtp.getVitalPosition());
										}else 
											postitions.add(vtp.getVitalPosition());
										
										for(int i=0; i<postitions.size(); i++) {
											if(smtpdMap.containsKey(periodId)) {
												SubjectVitalTimePointsData svtpData = null;
												if(svtpDataMap.size() > 0) {
													if(svtpDataMap.get(periodId) != null) {
														if(svtpDataMap.get(periodId).get(vtp.getSign()+vtp.getTimePoint()) != null) {
															svtpData = svtpDataMap.get(periodId).get(vtp.getSign()+vtp.getTimePoint()).get(postitions.get(i));
														}
													}
												}
												if(svtpData == null) {
													MisedTpsDto mtDto = getMisedTpsDtoRecord(sdtpdMap, subjNo, periodId, dateStr, doseInfo, vtp.getTimePoint(), vtp.getSign(), pwDosedMap, periodNoMap, washoutPeriodDays);
													mtDto.setPosition(postitions.get(i));
													if(vtpMap.containsKey(periodId)) {
														subTempMap = vtpMap.get(periodId);
														if(subTempMap.containsKey(subjNo)) {
															tempMap = subTempMap.get(subjNo);
															if(tempMap.containsKey(vtp.getSign()+vtp.getTimePoint())) {
																tempTpList = tempMap.get(vtp.getSign()+vtp.getTimePoint());
																tempTpList.add(mtDto);
																tempMap.put(vtp.getSign()+vtp.getTimePoint(), tempTpList);
																subTempMap.put(subjNo, tempMap);
																vtpMap.put(periodId, subTempMap);
															}else {
																tempTpList = new ArrayList<>();
																tempTpList.add(mtDto);
																tempMap.put(vtp.getSign()+vtp.getTimePoint(), tempTpList);
																subTempMap.put(subjNo, tempMap);
																vtpMap.put(periodId, subTempMap);
															}
														}else {
															tempTpList = new ArrayList<>();
															tempMap = new HashMap<>();
															tempTpList.add(mtDto);
															tempMap.put(vtp.getSign()+vtp.getTimePoint(), tempTpList);
															subTempMap.put(subjNo, tempMap);
															vtpMap.put(periodId, subTempMap);
														}
													}else {
														tempTpList = new ArrayList<>();
														tempMap = new HashMap<>();
														subTempMap = new HashMap<>();
														tempTpList.add(mtDto);
														tempMap.put(vtp.getSign()+vtp.getTimePoint(), tempTpList);
														subTempMap.put(subjNo, tempMap);
														vtpMap.put(periodId, subTempMap);
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
			mtpDto.setActList(mtpdDto.getActList());
			mtpDto.setSpmList(mtpdDto.getSpmList());
			mtpDto.setDosetpMap(dosetpMap);
			mtpDto.setMtpMap(mtpMap);
			mtpDto.setStpMap(stpMap);
			mtpDto.setVtpMap(vtpMap);
			mtpDto.setPriodNamesMap(periodNamesMap);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return mtpDto;
	}

private Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> getDosingMissedSubjects(
			Map<Long, Map<String, Map<String, List<MisedTpsDto>>>> dosetpMap,
			Map<String, Map<String, List<MisedTpsDto>>> subjDoseMap, Map<String, List<MisedTpsDto>> tpdoseMap, Long pid,
			DoseTimePoints dp, String actualSubNo, MisedTpsDto msDto, List<MisedTpsDto> tempTpList, String subjNo) {
		try {
			if(dosetpMap.containsKey(pid)) {
				subjDoseMap = dosetpMap.get(pid);
				if(subjDoseMap.containsKey(actualSubNo)) {
					tpdoseMap = subjDoseMap.get(actualSubNo);
					if(!tpdoseMap.containsKey(dp.getTimePoint())) {
						tempTpList = new ArrayList<>();
						tempTpList.add(msDto);
						tpdoseMap.put(dp.getTimePoint(), tempTpList);
						subjDoseMap.put(subjNo, tpdoseMap);
						dosetpMap.put(pid, subjDoseMap);
					}
					
				}else {
					tempTpList = new ArrayList<>();
					tpdoseMap = new HashMap<>();
					tempTpList.add(msDto);
					tpdoseMap.put(dp.getTimePoint(), tempTpList);
					subjDoseMap.put(subjNo, tpdoseMap);
					dosetpMap.put(pid, subjDoseMap);
				}
			}else {
				tempTpList = new ArrayList<>();
				tpdoseMap = new HashMap<>();
				subjDoseMap = new HashMap<>();
				tempTpList.add(msDto);
				tpdoseMap.put(dp.getTimePoint(), tempTpList);
				subjDoseMap.put(subjNo, tpdoseMap);
				dosetpMap.put(pid, subjDoseMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dosetpMap;
	}

private MisedTpsDto getCaluclatedDateAndTime(DosingInfo doseInfo, String timePoint, String dateStr, Map<Long, SubjectDoseTimePoints> pwDosedMap, Map<Long, Integer> periodNoMap, Integer washoutPeriodDays, Long periodId) {
	MisedTpsDto mtDto = new MisedTpsDto();
	Calendar dcal = Calendar.getInstance();
		try {
			Date dosedDate = null;
			if(pwDosedMap.get(periodId) != null) {
				if(periodNoMap.get(periodId) != 1) {
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dtStr = sdf1.format(pwDosedMap.get(periodId).getActualTime());
					dosedDate = sdf.parse(dtStr+" 00:00:00");
					Calendar cal = Calendar.getInstance();
					cal.setTime(doseInfo.getDosingDate());
					dcal.setTime(dosedDate);
					dcal.add(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
					dcal.add(Calendar.MINUTE, cal.get(Calendar.MINUTE));
				}else {
					dosedDate = doseInfo.getDosingDate();
					dcal.setTime(dosedDate);
				}
			}
			int periodNo = (periodNoMap.get(periodId)-1);
			dcal.add(Calendar.DAY_OF_MONTH, (periodNo*washoutPeriodDays)); 
//			dcal.setTime(doseInfo.getDosingDate());
			SimpleDateFormat sdfDate = new SimpleDateFormat(dateStr);
			String tpDate = sdfDate.format(dcal.getTime());
			String hours = dcal.get(Calendar.HOUR_OF_DAY)+"";
			String minutes = dcal.get(Calendar.MINUTE)+"";
			if(hours.length() == 1)
				hours = "0"+hours;
			if(minutes.length() ==1)
				minutes = "0"+minutes;
			String time = hours +":"+minutes;
			
			mtDto.setTime(time);
			mtDto.setTimePoint(timePoint);
			mtDto.setTpDate(tpDate);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mtDto;
	}

private MisedTpsDto getMisedTpsDtoRecord(Map<Long, Map<String, Map<String, SubjectDoseTimePoints>>> sdtpdMap, String subjNo,
			Long periodId, String dateStr, DosingInfo doseInfo, String timePoint, String sign, Map<Long, SubjectDoseTimePoints> pwDosedMap, Map<Long, Integer> periodNoMap, Integer washoutPeriodDays) {
		MisedTpsDto mtDto = new MisedTpsDto();
		try {
			SubjectDoseTimePoints sdtp = null;
			Calendar dcal = null;
			if(sdtpdMap.get(periodId) != null) {
				if(sdtpdMap.get(periodId)!= null) {
					Map<String, Map<String, SubjectDoseTimePoints>> tpdoseMap = sdtpdMap.get(periodId);
					if(tpdoseMap != null && tpdoseMap.size() > 0) {
						for(Map.Entry<String, Map<String, SubjectDoseTimePoints>> tpdMap : tpdoseMap.entrySet()) {
							if(tpdMap.getValue() != null && tpdMap.getValue().size() > 0) {
								if(sdtp == null)
									sdtp = tpdMap.getValue().get(subjNo);
							}
						}
					}
				}
			}
			TimeDto tdDto = getTimeDetails(timePoint);
			Date dosedDate = null;
	    	if(sdtp != null) {
	    		if(periodNoMap.get(periodId) != null) {
	    			if(periodNoMap.get(periodId) != 1) {
	    				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String dtStr = sdf1.format(pwDosedMap.get(periodId).getActualTime());
						Date date = sdf.parse(dtStr+" 00:00:00");
						Calendar cal = Calendar.getInstance();
						cal.setTime(sdtp.getActualTime());
						
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(date);
						cal2.add(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
						cal2.add(Calendar.MINUTE, cal.get(Calendar.MINUTE));
						dosedDate = cal2.getTime();
	    			}else {
	    				dosedDate = sdtp.getActualTime();
	    			}
	    		}
	    	}else if(doseInfo != null) {
	    			if(sign != null && !sign.equals("")) 
	    				dosedDate = doseInfo.getDosingDate();
			}
	    	dcal = getDateAndTime(tdDto, dosedDate, sign);
	    	if(dcal != null) {
	    		int periodNo = (periodNoMap.get(periodId)-1);
	    		dcal.add(Calendar.DAY_OF_MONTH, (periodNo*washoutPeriodDays)); 
	    		
	    		SimpleDateFormat sdfDate = new SimpleDateFormat(dateStr);
				String tpDate = sdfDate.format(dcal.getTime());
				String hours = dcal.get(Calendar.HOUR_OF_DAY)+"";
				String minutes = dcal.get(Calendar.MINUTE)+"";
				if(hours.length() == 1)
					hours = "0"+hours;
				if(minutes.length() ==1)
					minutes = "0"+minutes;
				String time = hours +":"+minutes;
				
				mtDto.setTime(time);
				mtDto.setTimePoint(sign+timePoint);
				mtDto.setTpDate(tpDate);
			}else {
				mtDto.setTime("");
				mtDto.setTimePoint(sign+timePoint);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mtDto;
	}

private Calendar getDateAndTime(TimeDto tdDto, Date doseDate, String sign) {
	Calendar dcal = null;
	try {
		if(doseDate != null) {
			dcal = Calendar.getInstance();
			dcal.setTime(doseDate);
			if(tdDto.getHours() != 0) {
				if(sign != null && !sign.equals(""))
					dcal.add(Calendar.HOUR_OF_DAY, -tdDto.getHours());
				else dcal.add(Calendar.HOUR_OF_DAY, tdDto.getHours());
			}
			if(tdDto.getMinutes() != 0) {
				if(sign != null && !sign.equals(""))
					dcal.add(Calendar.MINUTE, -tdDto.getMinutes());
				else dcal.add(Calendar.MINUTE, tdDto.getMinutes());
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return dcal;
}

	private TimeDto getTimeDetails(String timePoint) {
	TimeDto tdto = null;
	int hours =0;
	int minutes = 0;
	try {
		if(timePoint != null && !timePoint.equals("")) {
			String[] tempArr = timePoint.split("\\.");
			if(tempArr.length > 0) {
				if(tempArr[0].length() > 0) {
					hours = Integer.parseInt(tempArr[0]);	
				}
				
				int fractionMins = 0;
				if(tempArr.length == 2 && tempArr[1].length() > 0) {
					Float time = (Float.parseFloat("0."+tempArr[1]) * 60);
					fractionMins = Math.round(time);
				}
				if(fractionMins < 59) {
					minutes = minutes + fractionMins;
				}else {
					int quotient = fractionMins/60;
					int remainder = fractionMins % 60;
					if(quotient != 0)
						hours = hours + quotient;
					if(remainder != 0)
						minutes = minutes + remainder;
				}
			}
		}
		tdto = new TimeDto();
		tdto.setHours(hours);
		tdto.setMinutes(minutes);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return tdto;
}

	@Override
	public String generateMealsMenuPdf(Long studyId, Long periodId, String activityCode, HttpServletRequest request,
			HttpServletResponse response, Locale currentLocale, Long langId, String dateStr, String dateStrWithTime,
			MessageSource messageSource, Long userId) {
		String fileName = "";
		MissedTimePointsDto mtpDto = null;
		try {
			mtpDto = generateMissedTimepointsReport(studyId, currentLocale, dateStr);
			if(mtpDto != null) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fileName;
	}


}
