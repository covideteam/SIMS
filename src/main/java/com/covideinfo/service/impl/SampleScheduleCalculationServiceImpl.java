package com.covideinfo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.SampleScheduleCalculationDao;
import com.covideinfo.dto.SubjectTimePointsDto;
import com.covideinfo.dto.TimeDto;
import com.covideinfo.model.StudySubjectDeviations;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.SampleScheduleCalculationService;

@Service("sampleScheduleCalculationService")
public class SampleScheduleCalculationServiceImpl implements SampleScheduleCalculationService {
	
	@Autowired
	SampleScheduleCalculationDao sscDao;

	@Override
	public String calculatescheduleTimes(SubjectDoseTimePoints sdtp, Long userId, String operation) {
		String result = "Failed";
		SubjectTimePointsDto stpdto = null;
		List<SubjectMealsTimePointsData> mealsDtaList = null;
		List<SubjectSampleCollectionTimePointsData> samDataList = null;
		List<SubjectVitalTimePointsData> svDataList = null;
		UserMaster user = null;
		List<SubjectMealsTimePointsData> updateMealsList = new ArrayList<>();
		List<SubjectSampleCollectionTimePointsData> updateSamDataList = new ArrayList<>();
		List<SubjectVitalTimePointsData> updatesvDataList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<StudySubjectDeviations> ssdList = new ArrayList<>();
		try {
			if(sdtp != null && sdtp.getActualTime() != null) {
				stpdto = sscDao.getSubjectTimePointsDtoDetails(sdtp, userId);
				Calendar cal = Calendar.getInstance();
				if(stpdto != null) {
					mealsDtaList = stpdto.getMealsDtaList();
					samDataList = stpdto.getSamDataList();
					svDataList = stpdto.getSvDataList();
					user = stpdto.getUser();
				}
				//MealTimePoints
				if(mealsDtaList != null && mealsDtaList.size() > 0) {
					for(SubjectMealsTimePointsData smtpd : mealsDtaList) {
						cal.setTime(sdtp.getActualTime());
						if(operation.equals("calculateScheduleTimes")) {
							if(smtpd.getScheduleTime() == null && smtpd.getStudyPeriodMaster().getId() == sdtp.getPeriod().getId()) {
								TimeDto tdDto = getTimeDetails(smtpd.getMealsTimePoint().getTimePoint());
								if(tdDto != null) {
									if(tdDto.getHours() != 0)
										cal.add(Calendar.HOUR_OF_DAY, -tdDto.getHours());
									if(tdDto.getMinutes() != 0)
										cal.add(Calendar.MINUTE, -tdDto.getMinutes());
									
									smtpd.setScheduleTime(cal.getTime());
									smtpd.setUpdatedBy(user);
									smtpd.setUpdatedOn(new Date());
									smtpd.setUpdateReason("Scheduled Times Calculated By Schedular");
									updateMealsList.add(smtpd);
									
									//Deviation
									Calendar actualScheduledDate = Calendar.getInstance();
									String actualTimeStr = sdf.format(cal.getTime());
									actualScheduledDate.setTime(sdf.parse(actualTimeStr));
									String collectedTime = sdf.format(smtpd.getActualtime());
									StudySubjectDeviations ssdPojo = createDeviation(sdf.parse(collectedTime), smtpd.getStudyPeriodMaster().getStudy().getId(), smtpd.getStudyPeriodMaster().getPeriodName(), smtpd.getId(), smtpd.getMealsTimePoint().getSign()+smtpd.getMealsTimePoint().getTimePoint(), smtpd.getSubject(), user,  
																				tdDto, actualScheduledDate, stpdto, "MealsCollection", smtpd.getMealsTimePoint().getWindowPeriodSign(), 
																				smtpd.getMealsTimePoint().getWindowPeriod(), "MINUTES");
								    if(ssdPojo != null)
								    	ssdList.add(ssdPojo);
								}
							}
						}else {
							if(smtpd.getStudyPeriodMaster().getId() == sdtp.getPeriod().getId()) {
								TimeDto tdDto = getTimeDetails(smtpd.getMealsTimePoint().getTimePoint());
								if(tdDto != null) {
									if(tdDto.getHours() != 0)
										cal.add(Calendar.HOUR_OF_DAY, -tdDto.getHours());
									if(tdDto.getMinutes() != 0)
										cal.add(Calendar.MINUTE, -tdDto.getMinutes());
									smtpd.setScheduleTime(cal.getTime());
									smtpd.setUpdatedBy(user);
									smtpd.setUpdatedOn(new Date());
									smtpd.setUpdateReason("Scheduled Times Updated By Schedular");
									updateMealsList.add(smtpd);
									
									//Deviation
									Calendar actualScheduledDate = Calendar.getInstance();
									String actualTimeStr = sdf.format(cal.getTime());
									actualScheduledDate.setTime(sdf.parse(actualTimeStr));
									String collectedTime = sdf.format(smtpd.getActualtime());
									StudySubjectDeviations ssdPojo  = createDeviation(sdf.parse(collectedTime), smtpd.getStudyPeriodMaster().getStudy().getId(), smtpd.getStudyPeriodMaster().getPeriodName(), smtpd.getId(), smtpd.getMealsTimePoint().getSign()+smtpd.getMealsTimePoint().getTimePoint(), smtpd.getSubject(), user,  tdDto, actualScheduledDate, stpdto, "MealsCollection", smtpd.getMealsTimePoint().getWindowPeriodSign(), smtpd.getMealsTimePoint().getWindowPeriod(), "MINUTES");
									if(ssdPojo != null)
									   ssdList.add(ssdPojo);
								}
							}
						}
						
					}
				}
				//SampleTimePoints
				if(samDataList != null && samDataList.size() > 0) {
					cal.setTime(sdtp.getActualTime());
					for(SubjectSampleCollectionTimePointsData sctpd : samDataList) {
						if(operation.equals("calculateScheduleTimes")) {
							if(sctpd.getScheduleTime() == null && sctpd.getStudyPeriodMaster().getId() == sdtp.getPeriod().getId()) {
								TimeDto tdDto = getTimeDetails(sctpd.getSampleTimePoint().getTimePoint());
								if(tdDto != null) {
									if(tdDto.getHours() != 0)
										cal.add(Calendar.HOUR_OF_DAY, -tdDto.getHours());
									if(tdDto.getMinutes() != 0)
										cal.add(Calendar.MINUTE, -tdDto.getMinutes());
									sctpd.setScheduleTime(cal.getTime());
									sctpd.setUpdatedBy(user);
									sctpd.setUpdatedOn(new Date());
									sctpd.setUpdateReason("Scheduled Times Calulated By Schedular");
									updateSamDataList.add(sctpd);
									
									//Deviation
									Calendar actualScheduledDate = Calendar.getInstance();
									String actualTimeStr = sdf.format(cal.getTime());
									actualScheduledDate.setTime(sdf.parse(actualTimeStr));
									String collectedTime = sdf.format(sctpd.getActualtime());
									StudySubjectDeviations ssdPojo = createDeviation(sdf.parse(collectedTime), sctpd.getStudyPeriodMaster().getStudy().getId(), sctpd.getStudyPeriodMaster().getPeriodName(), sctpd.getId(), sctpd.getSampleTimePoint().getSign()+sctpd.getSampleTimePoint().getTimePoint(), sctpd.getSubject(), user,  tdDto, actualScheduledDate, stpdto, "SampleCollection", sctpd.getSampleTimePoint().getWindowPeriodSign(), sctpd.getSampleTimePoint().getWindowPeriod(), sctpd.getSampleTimePoint().getWindowPeriodType().getCode());
									if(ssdPojo != null)
									    ssdList.add(ssdPojo);
								}
							}
						}else {
							if(sctpd.getStudyPeriodMaster().getId() == sdtp.getPeriod().getId()) {
								TimeDto tdDto = getTimeDetails(sctpd.getSampleTimePoint().getTimePoint());
								if(tdDto != null) {
									if(tdDto.getHours() != 0)
										cal.add(Calendar.HOUR_OF_DAY, -tdDto.getHours());
									if(tdDto.getMinutes() != 0)
										cal.add(Calendar.MINUTE, -tdDto.getMinutes());
									sctpd.setScheduleTime(cal.getTime());
									sctpd.setUpdatedBy(user);
									sctpd.setUpdatedOn(new Date());
									sctpd.setUpdateReason("Scheduled Times Updated By Schedular");
									updateSamDataList.add(sctpd);
									
									//Deviation
									Calendar actualScheduledDate = Calendar.getInstance();
									String actualTimeStr = sdf.format(cal.getTime());
									actualScheduledDate.setTime(sdf.parse(actualTimeStr));
									String collectedTime = sdf.format(sctpd.getActualtime());
									StudySubjectDeviations ssdPojo = createDeviation(sdf.parse(collectedTime), sctpd.getStudyPeriodMaster().getStudy().getId(), sctpd.getStudyPeriodMaster().getPeriodName(), sctpd.getId(), sctpd.getSampleTimePoint().getSign()+sctpd.getSampleTimePoint().getTimePoint(), sctpd.getSubject(), user,  tdDto, actualScheduledDate, stpdto, "SampleCollection", sctpd.getSampleTimePoint().getWindowPeriodSign(), sctpd.getSampleTimePoint().getWindowPeriod(), sctpd.getSampleTimePoint().getWindowPeriodType().getCode());
									if(ssdPojo != null)
									    ssdList.add(ssdPojo);
								}
							}
						}
					}
				}
				//vitalTimePoints
				if(svDataList != null && svDataList.size() > 0) {
					cal.setTime(sdtp.getActualTime());
					for(SubjectVitalTimePointsData svtpd : svDataList) {
						if(operation.equals("calculateScheduleTimes")) {
							if(svtpd.getScheduleTime() == null && svtpd.getPeriod().getId() == sdtp.getPeriod().getId()) {
								TimeDto tdDto = getTimeDetails(svtpd.getTimepoint().getTimePoint());
								if(tdDto != null) {
									if(tdDto.getHours() != 0)
										cal.add(Calendar.HOUR_OF_DAY, -tdDto.getHours());
									if(tdDto.getMinutes() != 0)
										cal.add(Calendar.MINUTE, -tdDto.getMinutes());
									svtpd.setScheduleTime(cal.getTime());
									svtpd.setUpdatedBy(user);
									svtpd.setUpdatedOn(new Date());
									svtpd.setUpdateReason("Scheduled Times Calculated By Schedular");
									updatesvDataList.add(svtpd);
									
									//Deviation
									Calendar actualScheduledDate = Calendar.getInstance();
									String actualTimeStr = sdf.format(cal.getTime());
									actualScheduledDate.setTime(sdf.parse(actualTimeStr));
									String collectedTime = sdf.format(svtpd.getStartTime());
									StudySubjectDeviations ssdPojo = createDeviation(sdf.parse(collectedTime), svtpd.getPeriod().getStudy().getId(), svtpd.getPeriod().getPeriodName(), svtpd.getId(), svtpd.getTimepoint().getSign()+svtpd.getTimepoint().getTimePoint(), svtpd.getSubject(), user,  tdDto, actualScheduledDate, stpdto, "VitalCollection", svtpd.getTimepoint().getWindowPeriodSign(), svtpd.getTimepoint().getWindowPeriod(), svtpd.getTimepoint().getWindowPeriodType().getCode());
									if(ssdPojo != null)
									    ssdList.add(ssdPojo);
								}
							}
						}else {
							if(svtpd.getPeriod().getId() == sdtp.getPeriod().getId()) {
								TimeDto tdDto = getTimeDetails(svtpd.getTimepoint().getTimePoint());
								if(tdDto != null) {
									if(tdDto.getHours() != 0)
										cal.add(Calendar.HOUR_OF_DAY, -tdDto.getHours());
									if(tdDto.getMinutes() != 0)
										cal.add(Calendar.MINUTE, -tdDto.getMinutes());
									svtpd.setScheduleTime(cal.getTime());
									svtpd.setUpdatedBy(user);
									svtpd.setUpdatedOn(new Date());
									svtpd.setUpdateReason("Scheduled Times Updated By Schedular");
									updatesvDataList.add(svtpd);
									
									//Deviation
									Calendar actualScheduledDate = Calendar.getInstance();
									String actualTimeStr = sdf.format(cal.getTime());
									actualScheduledDate.setTime(sdf.parse(actualTimeStr));
									String collectedTime = sdf.format(svtpd.getStartTime());
									StudySubjectDeviations ssdPojo = createDeviation(sdf.parse(collectedTime), svtpd.getPeriod().getStudy().getId(), svtpd.getPeriod().getPeriodName(), svtpd.getId(), svtpd.getTimepoint().getSign()+svtpd.getTimepoint().getTimePoint(), svtpd.getSubject(), user,  tdDto, actualScheduledDate, stpdto, "VitalCollection", svtpd.getTimepoint().getWindowPeriodSign(), svtpd.getTimepoint().getWindowPeriod(), svtpd.getTimepoint().getWindowPeriodType().getCode());
									if(ssdPojo != null)
									    ssdList.add(ssdPojo);
								}
							}
						}
					}
				}
				result = sscDao.updateScheduledTimePointsData(updateMealsList, updateSamDataList, updatesvDataList, ssdList);
			}else result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private StudySubjectDeviations createDeviation(Date actualTime, Long studyId, String periodName, Long recordId, String timepoint, StudySubjects subject, UserMaster user,  
			TimeDto tdDto, Calendar actualScheduledDate, SubjectTimePointsDto stpDto, String type, String windowPeriodSign, 
			int windowPeriod, String windowPeriodType) {
		Calendar cal = Calendar.getInstance();
		String deviationTime = "";
		StudySubjectDeviations ssd = null;
		try {
			cal.setTime(actualTime);
			if(actualScheduledDate != null) {
				int collectedHrs = cal.get(Calendar.HOUR_OF_DAY);
				int collectedMinutes = cal.get(Calendar.MINUTE);
				int doseScheduledHrs = actualScheduledDate.get(Calendar.HOUR_OF_DAY);
				int doseScheduledMinutes = actualScheduledDate.get(Calendar.MINUTE);
				//PLUSANDMINUS, MINUS, PLUS
				if(windowPeriodSign.equals("PLUSANDMINUS")) {
					if(windowPeriodType.equalsIgnoreCase("DAYS")) {
						Calendar actualScheduledPlusDate = actualScheduledDate;
						Calendar actualScheduledMinusDate = actualScheduledDate;
						actualScheduledPlusDate.add(Calendar.DAY_OF_MONTH, windowPeriod);
						actualScheduledMinusDate.add(Calendar.DAY_OF_MONTH, -windowPeriod);
						deviationTime = getDeviationTimeForPusAndMinusCondition(cal, actualScheduledMinusDate, actualScheduledPlusDate, collectedHrs, collectedMinutes, doseScheduledHrs, doseScheduledMinutes);
					}else if(windowPeriodType.equalsIgnoreCase("HOURS")) {
						Calendar actualScheduledPlusDate = actualScheduledDate;
						Calendar actualScheduledMinusDate = actualScheduledDate;
						actualScheduledPlusDate.add(Calendar.HOUR_OF_DAY, windowPeriod);
						actualScheduledMinusDate.add(Calendar.HOUR_OF_DAY, -windowPeriod);
						deviationTime = getDeviationTimeForPusAndMinusCondition(cal, actualScheduledMinusDate, actualScheduledPlusDate, collectedHrs, collectedMinutes, doseScheduledHrs, doseScheduledMinutes);
					}else if(windowPeriodType.equalsIgnoreCase("MINUTES")) {
						Calendar actualScheduledPlusDate = actualScheduledDate;
						Calendar actualScheduledMinusDate = actualScheduledDate;
						actualScheduledPlusDate.add(Calendar.MINUTE, windowPeriod);
						actualScheduledMinusDate.add(Calendar.MINUTE, -windowPeriod);
						deviationTime = getDeviationTimeForPusAndMinusCondition(cal, actualScheduledMinusDate, actualScheduledPlusDate, collectedHrs, collectedMinutes, doseScheduledHrs, doseScheduledMinutes);
					}
				}else if(windowPeriodSign.equals("MINUS")) {
					if(windowPeriodType.equalsIgnoreCase("DAYS")) {
						Calendar actualScheduledMinusDate = actualScheduledDate;
						actualScheduledMinusDate.add(Calendar.DAY_OF_MONTH, -windowPeriod);
						deviationTime = getDeviationsTime(cal, actualScheduledMinusDate, collectedHrs, collectedMinutes);
					}else if(windowPeriodType.equalsIgnoreCase("HOURS")) {
						Calendar actualScheduledMinusDate = actualScheduledDate;
						actualScheduledMinusDate.add(Calendar.HOUR_OF_DAY, -windowPeriod);
						deviationTime = getDeviationsTime(cal, actualScheduledMinusDate, collectedHrs, collectedMinutes);
					}else if(windowPeriodType.equalsIgnoreCase("MINUTES")) {
						Calendar actualScheduledMinusDate = actualScheduledDate;
						actualScheduledMinusDate.add(Calendar.MINUTE, -windowPeriod);
						deviationTime = getDeviationsTime(cal, actualScheduledMinusDate, collectedHrs, collectedMinutes);
					}
				}else if(windowPeriodSign.equals("PLUS")) {
					if(windowPeriodType.equalsIgnoreCase("DAYS")) {
						Calendar actualScheduledPlusDate = actualScheduledDate;
						actualScheduledPlusDate.add(Calendar.DAY_OF_MONTH, windowPeriod);
						deviationTime = getDeviationsTime(cal, actualScheduledPlusDate, collectedHrs, collectedMinutes);
					}else if(windowPeriodType.equalsIgnoreCase("HOURS")) {
						Calendar actualScheduledPlusDate = actualScheduledDate;
						actualScheduledPlusDate.add(Calendar.HOUR_OF_DAY, windowPeriod);
						deviationTime = getDeviationsTime(cal, actualScheduledPlusDate, collectedHrs, collectedMinutes);
					}else if(windowPeriodType.equalsIgnoreCase("MINUTES")) {
						Calendar actualScheduledPlusDate = actualScheduledDate;
						actualScheduledPlusDate.add(Calendar.MINUTE, windowPeriod);
						deviationTime = getDeviationsTime(cal, actualScheduledPlusDate, collectedHrs, collectedMinutes);
					}
				}
	
				if(deviationTime != null && !deviationTime.equals("")) {
					ssd = new StudySubjectDeviations();
					if(type.equals("MealsCollection"))
						ssd.setActivity(stpDto.getMealActvity());
					else if(type.equals("SampleCollection"))
						ssd.setActivity(stpDto.getSampleActivity());
					else if(type.equals("VitalCollection"))
						ssd.setActivity(stpDto.getVitalCollectionActivity());
					ssd.setStudyId(studyId);
					ssd.setPeriod(periodName);
					ssd.setDeviationRecordId(recordId);
					ssd.setTimePoint(timepoint);
					ssd.setStatus(stpDto.getNewStatus());
					ssd.setCreatedBy(user);
					ssd.setCreatedOn(new Date());
					ssd.setDevMsgId(stpDto.getDm());
					ssd.setSubject(subject);
					ssd.setDeviationTime(deviationTime);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ssd;
	}

	private String getDeviationTimeForPusAndMinusCondition(Calendar cal, Calendar actualScheduledMinusDate,
			Calendar actualScheduledPlusDate, int collectedHrs, int collectedMinutes, int doseScheduledHrs,
			int doseScheduledMinutes) {
		String time ="";
		String timeStr = "";
		long differenceInMillis = 0;
		int diffDays = 0;
		int hours = 0;
		int minutes = 0;
		try {
			if(cal.after(actualScheduledMinusDate) && cal.before(actualScheduledPlusDate)) {
				time = calculateDeviationTime(collectedHrs, doseScheduledHrs, collectedMinutes, doseScheduledMinutes, timeStr, "", 0);
			}else {
				if(cal.after(actualScheduledMinusDate)) {
					if(cal.before(actualScheduledPlusDate) && !cal.equals(actualScheduledPlusDate)) {
						differenceInMillis = actualScheduledPlusDate.getTimeInMillis() - cal.getTimeInMillis();
						diffDays = (int) ((differenceInMillis / (1000 * 60 * 60 * 24)) %365);
						hours = (int) ((differenceInMillis / (1000 * 60 * 60)) % 24);
						minutes = (int) ((differenceInMillis / (1000 * 60)) % 60);
							//-ve condition
							time = calculateDeviationTime(collectedHrs, hours, collectedMinutes, minutes, timeStr, "-", diffDays);
					}else {
						//+ve condition
						differenceInMillis = cal.getTimeInMillis() - actualScheduledPlusDate.getTimeInMillis();
						diffDays = (int) ((differenceInMillis / (1000 * 60 * 60 * 24)) %365);
						hours = (int) ((differenceInMillis / (1000 * 60 * 60)) % 24);
						minutes = (int) ((differenceInMillis / (1000 * 60)) % 60);
						if(!cal.equals(actualScheduledPlusDate) && cal.after(actualScheduledPlusDate)) {
							time = calculateDeviationTime(collectedHrs, hours, collectedMinutes, minutes, timeStr, "", diffDays);
						}
					}
				}else {
					//-ve condition
					differenceInMillis = actualScheduledMinusDate.getTimeInMillis() - cal.getTimeInMillis();
					diffDays = (int) ((differenceInMillis / (1000 * 60 * 60 * 24)) %365);
					hours = (int) ((differenceInMillis / (1000 * 60 * 60)) % 24);
					minutes = (int) ((differenceInMillis / (1000 * 60)) % 60);
					if(!cal.equals(actualScheduledMinusDate)) {
						time = calculateDeviationTime(collectedHrs, hours, collectedMinutes, minutes, timeStr, "-", diffDays);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	private String getDeviationsTime(Calendar collectedTime, Calendar scheduledTime, int collectedHrs, int collectedMinutes) {
		long differenceInMillis = 0;
		int diffDays = 0;
		int hours = 0;
		int minutes = 0;
		String time ="";
		String str ="";
		try {
			if(!collectedTime.equals(scheduledTime)) {
				if(collectedTime.after(scheduledTime)) {
					//+ve condition
					differenceInMillis = collectedTime.getTimeInMillis() - scheduledTime.getTimeInMillis();
					diffDays = (int) ((differenceInMillis / (1000 * 60 * 60 * 24)) %365);
					hours = (int) ((differenceInMillis / (1000 * 60 * 60)) % 24);
					minutes = (int) ((differenceInMillis / (1000 * 60)) % 60);
						//-ve condition
					time = calculateDeviationTime(collectedHrs, hours, collectedMinutes, minutes, str, "", diffDays);
				
				}else {
					//-ve condition
					differenceInMillis = scheduledTime.getTimeInMillis() - collectedTime.getTimeInMillis();
					diffDays = (int) ((differenceInMillis / (1000 * 60 * 60 * 24)) %365);
					hours = (int) ((differenceInMillis / (1000 * 60 * 60)) % 24);
					minutes = (int) ((differenceInMillis / (1000 * 60)) % 60);
					if(!collectedTime.equals(scheduledTime)) {
						time = calculateDeviationTime(collectedHrs, hours, collectedMinutes, minutes, str, "-", diffDays);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}


	private String calculateDeviationTime(int collectedHrs, int doseScheduledHrs, int collectedMinutes,
			int doseScheduledMinutes, String finalsStr, String sign, int diffDays) {
		finalsStr = "";
		try {
			if(collectedHrs != doseScheduledHrs) {
				if(collectedHrs < doseScheduledHrs) {
					//-ve senario
					if(collectedMinutes != doseScheduledMinutes) {
						if(collectedMinutes < doseScheduledMinutes) 
							finalsStr = getFinalHours(finalsStr, doseScheduledHrs, collectedHrs, doseScheduledMinutes, collectedMinutes, diffDays, "-");
						else 
							finalsStr = getFinalHours(finalsStr, doseScheduledHrs, collectedHrs, collectedMinutes, doseScheduledMinutes, diffDays, "-");
					}else 
						finalsStr = getFinalHours(finalsStr, doseScheduledHrs, collectedHrs, 1, 1, diffDays, "-");
				}else {
					//+ve Hours
					if(collectedMinutes != doseScheduledMinutes) {
						if(collectedMinutes < doseScheduledMinutes) 
							finalsStr = getFinalHours(finalsStr, collectedHrs, doseScheduledHrs, doseScheduledMinutes, collectedMinutes, diffDays, "");
						else 
							finalsStr = getFinalHours(finalsStr, collectedHrs, doseScheduledHrs, collectedMinutes, doseScheduledMinutes, diffDays, "");
					}else 
						finalsStr = getFinalHours(finalsStr, collectedHrs, doseScheduledHrs, 1, 1, diffDays, "");
				}
			}else {
				if(collectedMinutes != doseScheduledMinutes) {
					if(collectedMinutes < doseScheduledMinutes) 
						finalsStr = getFinalHours(finalsStr, 1, 1, doseScheduledMinutes, collectedMinutes, diffDays, "-");
					else 
						finalsStr = getFinalHours(finalsStr, 1, 1, collectedMinutes, doseScheduledMinutes, diffDays, "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalsStr;
	}

	private String getFinalHours(String finalsStr, int hour1, int hour2, int mins1, int mins2, int diffDays, String sign) {
		int hrs = hour1 - hour2;
		int mins = mins1 - mins2;
		if(diffDays != 0) {
			if(sign.equals(""))
				hrs = hrs +(diffDays *24);
			else {
				if(hrs < (diffDays *24)) {
					hrs = (diffDays *24) - hrs;
				}else hrs = hrs - (diffDays *24);
			}
		}
		if(hrs < 9) 
			finalsStr += "0"+hrs+":";
		else finalsStr += hrs+":";
		if(mins < 9)
			finalsStr += "0"+mins;
		else finalsStr += mins;
		return sign+finalsStr;
	}

	private TimeDto getTimeDetails(String timePoint) {
		TimeDto tdto = null;
		int hours =0;
		int minutes = 0;
		try {
			if(timePoint != null && !timePoint.equals("")) {
				String[] tempArr = timePoint.split("\\.");
				if(tempArr.length > 0) {
					if(tempArr[1] != null && !tempArr[1].equals("")) {
						hours = Integer.parseInt(tempArr[0]);
						Double time = (Double.parseDouble("0."+tempArr[1]) * 60);
//						String timeStr = time+"";
						int fractionMins = (int) Math.round(time);
						/*if(timeStr != null && !timeStr.equals("")) {
							if(timeStr.contains(".")) {
								String[] tArr = timeStr.split("\\.");
								if(tArr.length > 0) {
									if(!tArr[0].equals("")) 
										fractionMins = Integer.parseInt(tArr[0]);
								}
							}else 
								fractionMins = Integer.parseInt(timeStr);
						}*/
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
			}
			tdto = new TimeDto();
			tdto.setHours(hours);
			tdto.setMinutes(minutes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tdto;
	}

}
