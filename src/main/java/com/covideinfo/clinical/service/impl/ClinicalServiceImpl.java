package com.covideinfo.clinical.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.covideinfo.bo.SampleCollectionDashBoard;
import com.covideinfo.bo.SampleCollectionDashBoardColumns;
import com.covideinfo.bo.SampleTimePointsData;
import com.covideinfo.bo.SubjectDoseDashBord;
import com.covideinfo.bo.TimePointInfo;
import com.covideinfo.clinical.dao.ClinicalDao;
import com.covideinfo.clinical.service.ClinicalService;
import com.covideinfo.controllers.TestWebSocket;
import com.covideinfo.dao.DosingInfoDao;
import com.covideinfo.dao.StudyCreationDao;
import com.covideinfo.dao.StudyDao;
import com.covideinfo.dao.UserDao;
import com.covideinfo.dto.AllowStudySubjectMealsDto;
import com.covideinfo.dto.BloodSamplesCollectionDto;
import com.covideinfo.dto.CentrificationDetailsDto;
import com.covideinfo.dto.CentrificationDto;
import com.covideinfo.dto.CommonTimePointDto;
import com.covideinfo.dto.CommonTpDetails;
import com.covideinfo.dto.DoseDataDto;
import com.covideinfo.dto.DoseSavingDtoDetails;
import com.covideinfo.dto.DosingCollectionTimePointsDto;
import com.covideinfo.dto.DosingDto;
import com.covideinfo.dto.GlobalparameterFromDto;
import com.covideinfo.dto.MealCollectoinDto;
import com.covideinfo.dto.MealInfoDto;
import com.covideinfo.dto.MealsDataSavingDto;
import com.covideinfo.dto.MealsTimePointsDto;
import com.covideinfo.dto.PlannedTimeDetailsDto;
import com.covideinfo.dto.RealTimeCommunicationDto;
import com.covideinfo.dto.SampleCollectionDtoDetails;
import com.covideinfo.dto.SampleCollectionSavingDto;
import com.covideinfo.dto.SampleCollectoinDto;
import com.covideinfo.dto.SegrigationDto;
import com.covideinfo.dto.StorageVacutinerDto;
import com.covideinfo.dto.StudyActivityDataDto;
import com.covideinfo.dto.VialRackDto;
import com.covideinfo.dto.VilaRackDtoDetails;
import com.covideinfo.dto.VitalCollectionDataDto;
import com.covideinfo.dto.VitalCollectionDetailsDto;
import com.covideinfo.dto.VitalCollectionSavingDto;
import com.covideinfo.dto.VitalTimePointsCollectionDto;
import com.covideinfo.enums.StaticData;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.Centrifugation;
import com.covideinfo.model.CentrifugationDataMaster;
import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.ECGTimePoints;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.RackWithVials;
import com.covideinfo.model.RackWithVitalSubject;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.SamplesCentrifugation;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.SubejectDosePerameter;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectECGTimePoints;
import com.covideinfo.model.SubjectECGTimePointsData;
import com.covideinfo.model.SubjectMealsTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectPeriodStatus;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectSampleCollectionTimePoints;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectSampleSeparationTimePointsData;
import com.covideinfo.model.SubjectTimePointVitalTests;
import com.covideinfo.model.SubjectVitalTimePoints;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.model.SubjectWithdrawDetails;
import com.covideinfo.model.TimePointVitalTests;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.VitalTimePoints;
import com.covideinfo.model.Volunteer;
import com.covideinfo.model.dummy.SheduleTime;
import com.covideinfo.service.StudyActivityService;
import com.covideinfo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("clinicalService")
@PropertySource(value = { "classpath:application.properties" })
public class ClinicalServiceImpl implements ClinicalService {
	@Autowired
	private Environment environment;
	@Autowired
	ClinicalDao clinicalDao;
	@Autowired
	StudyDao studyDao;
	@Autowired
	UserDao userDao;
	@Autowired
	StudyCreationDao studyCreationDao;
	@Autowired
	StudyActivityService studyActivityService;
	@Autowired
	UserService userService;
	@Autowired
	DosingInfoDao dosingInfoDao;
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public String subjectBarcodeInfo(String barcode, StudyMaster std) {
		int subjectSeqNo = Integer.parseInt(barcode.substring(9, 12));
		Volunteer vol = studyDao.volunteerWithsubjectBarcode(barcode);
//		int periodNo = Integer.parseInt(barcode.substring(8, 9));
		SubjectPeriodStatus subjectPeriodStatus = studyDao.subjectPeriodStatus(std, vol);
		if (subjectPeriodStatus == null) {
			return "1--0--<b>Subject is not participating any Period</b>";
		} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.ENROLLED.toString())) {
			String subNo = vol.getSubjectNo();
			if (subNo.length() == 1) {
				subNo = "00" + subNo;
			} else if (subNo.length() == 2) {
				subNo = "0" + subNo;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("0--");
			sb.append(subjectPeriodStatus.getPeriodNo()).append("--");
			sb.append(vol.getSeqNo()).append("--");
			sb.append("<b>Study : </b>").append(std.getProjectNo()).append(", ");
			sb.append("<b>Subject : </b>").append(vol.getSubjectNo());
			sb.append("<b>Period : </b>").append(subjectPeriodStatus.getPeriodNo());
			return sb.toString();
		} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.SELECTED.toString())) {
			return "1--0--<b>Subject No not Available</b>";
		} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.REPLACED.toString())) {
			return "1--0--Subject has Replaced";
		} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.WITHDRAWN.toString())) {
			return "1--0--Subject has Withdrean";
		} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.DROPED.toString())) {
			return "1--0--Subject has Droped";
		} else {
			return "1--0--Invalied Barcode";
		}
	}

	@Override
	public String vacutainerBarcodeInfoWithSubjct(String barcode) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(environment.getRequiredProperty("dateTimeFormat"));
//		pkSampleCollectionAllowFromTimePoint
		StudyMaster std = studyDao.findByStudyId(Long.parseLong(barcode.substring(2, 8)));
		Volunteer vol = studyDao.volunteerWithSecqNo(std, Integer.parseInt(barcode.substring(9, 12)));
//		SubjectReplacedInfo sriStandBy = studyDao.standsubjectReplacedInfoSampleCollectionVac(std, vol);

		String result = "Invalied";
		SubjectSampleCollectionTimePoints vac = clinicalDao.subjectSampleCollectionTimePoints(barcode);
		SubjectPeriodStatus sps = studyDao.subjectPeriodStatus(std, vol);
		if (vac != null) {
			if (vac.getSubjectSampleCollectionTimePointsData() != null) {
				return "1--0--0--Sample Collection already Done for the Subjct : " + vol.getSubjectNo()
						+ " Of Time Pint : " + vac.getTimePoint();
			} else if (!vac.getTimePointType().getCode().equals(StaticData.PREDOSE.toString())
					&& !sps.isSubjectDoseStatus()) {
				return "1--0--0--Dose not done for the Subjct : " + vol.getSubjectNo();
			}

			StringBuilder sb = new StringBuilder();
			boolean oldflag = false;
			if (vac.getTimePointNo() != 1) {
				List<SubjectSampleCollectionTimePoints> oldTimePoints = clinicalDao.previousSamplesIfAny(vac.getStudy(),
						vac.getPeriod(), vac.getTreatmentInfo(), vac.getSubjectNo(), vac.getTimePointNo(), vac);
				if (oldTimePoints.size() > 0) {
					sb.append("2--");
					boolean flag = false;
					for (SubjectSampleCollectionTimePoints sscp : oldTimePoints) {
						System.out.println(sscp.getTimePoint() + "\t" + sscp.getTimePointType().getCode() + "\t"
								+ sscp.getSubjectSampleCollectionTimePointId());
						if (flag) {
							if (sscp.getTimePointType().getCode().equals(StaticData.PREDOSE.toString()))
								sb.append(",-").append(sscp.getTimePoint())
										.append(" Vacutainer-" + sscp.getVacutainer());
							else
								sb.append(",").append(sscp.getTimePoint())
										.append(" Vacutainer-" + sscp.getVacutainer());
						} else {
							if (sscp.getTimePointType().getCode().equals(StaticData.PREDOSE.toString()))
								sb.append("-").append(sscp.getTimePoint())
										.append(" Vacutainer-" + sscp.getVacutainer());
							else
								sb.append("").append(sscp.getTimePoint()).append(" Vacutainer-" + sscp.getVacutainer());
							flag = true;
						}
						sb.append("\n");
					}
					oldflag = true;

				}
			}
			if (!oldflag) {
				sb.append("0--0--");
			} else {
				sb.append("--");
			}
			sb.append(vac.getTimePoint() + "--");
			sb.append("<b>Study : </b>").append(vac.getStudy().getProjectNo()).append(", ");
			sb.append("<b>Period : </b>").append(vac.getPeriod().getPeriodNo()).append(", ");
			sb.append("<b>Subject : </b>").append(vol.getSubjectNo());
			sb.append("<b>Sequency : </b>").append(vac.getTimePointType().getFieldValue()).append(", ");
			sb.append("<b>Time Point : </b>").append(vac.getTimePoint());
			if (vac.getTimePointType().getCode().equals(StaticData.POSTDOSE.toString()))
				sb.append("--1--").append(vac.getScheduleTime());
			else
				sb.append("--0--0000");

			result = sb.toString();
		} else {
			result = "1--0--0--Somthig went wrong on server";
		}
		System.out.println(result);
		return result;
	}

	private String deviation(Date scDate, Date acDate, String sign, int windowPeriod, String windowType) {
		if (scDate != null && acDate != null) {
			if (windowType.equals(StaticData.HOURS.toString()))
				windowPeriod = windowPeriod * 60;
			else if (windowType.equals(StaticData.DAYS.toString()))
				windowPeriod = windowPeriod * 60 * 24;
			Integer deviation = 0;
			if (sign.trim().equals("+")) {
				deviation = caliculateDeviation(scDate, acDate, true);
				if (deviation != null && deviation != 0) {
					return deviation.toString();
				} else {
					Calendar cal = Calendar.getInstance();
					cal.setTime(scDate);
					cal.add(Calendar.MINUTE, windowPeriod);
					Date scduelDateWithWindow = cal.getTime();
					deviation = caliculateDeviation(scduelDateWithWindow, acDate, false);
					if (deviation != null && deviation != 0) {
						return deviation.toString();
					}
				}
			} else if (sign.trim().equals("-")) {
				deviation = caliculateDeviation(scDate, acDate, false);
				if (deviation != null && deviation != 0) {
					return deviation.toString();
				} else {
					Calendar cal = Calendar.getInstance();
					cal.setTime(scDate);
					cal.add(Calendar.MINUTE, -windowPeriod);
					Date scduelDateWithWindow = cal.getTime();
					deviation = caliculateDeviation(scduelDateWithWindow, acDate, true);
					if (deviation != null && deviation != 0) {
						return deviation.toString();
					}
				}
			} else {
				Calendar cal = Calendar.getInstance();
				cal.setTime(scDate);
				cal.add(Calendar.MINUTE, -windowPeriod);
				Date scduelDateWithWindow = cal.getTime();
				deviation = caliculateDeviation(scDate, acDate, true);
				if (deviation != null && deviation != 0) {
					return deviation.toString();
				} else {
					cal = Calendar.getInstance();
					cal.setTime(scDate);
					cal.add(Calendar.MINUTE, windowPeriod);
					scduelDateWithWindow = cal.getTime();
					deviation = caliculateDeviation(scDate, acDate, false);
					if (deviation != null && deviation != 0) {
						return deviation.toString();
					}
				}
			}
		}

		return "";
	}

	private Integer caliculateDeviation(Date scDate, Date acDate, boolean startTime) {
		try {
			Date d1 = scDate;
			Date d2 = acDate;

			// Calucalte time difference
			// in milliseconds
			long difference_In_Time = d1.getTime() - d2.getTime();

			// Calucalte time difference in
			// seconds, minutes, hours, years,
			// and days
			long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
			long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
			long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
			long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));
			Long diff = (((difference_In_Years * 365) * 24) * 60) + ((difference_In_Days * 24) * 60)
					+ (difference_In_Hours * 60) + difference_In_Minutes;
			int deviation = diff.intValue();
			if (startTime) {
				if (deviation > 0)
					return -(deviation);
				else
					return 0;
			} else {
				if (deviation < 0)
					return -(deviation);
				else
					return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String sachetBarcodeInfo(String barcode, Long studyId) {
//		String result = "Invalied";
//		SubjectDoseTimePoints tech = clinicalDao.subjectDoseTimePoints(barcode, studyId);
//		StudyMaster study = tech.getStudy();
//		StudyPeriodMaster sp = tech.getPeriod();
//		int subjectSeqNo = Integer.parseInt(barcode.substring(9, 12));
//		Volunteer vol = studyDao.volunteerWithSecqNo(study, subjectSeqNo);
//		SubjectPeriodStatus sps = studyDao.subjectPeriodStatus(study, vol);
//		if (!sps.isSubjectDoseStatus()) { // 00.00
//			boolean flag = true;
//			if (tech.getTimePointNo() != 1) {
//				List<SubjectDoseTimePoints> oldTimePoints = clinicalDao.oldSubjectDoseTimePoints(tech);
//				if (oldTimePoints.size() > 0) {
//					flag = false;
//					StringBuilder sb = new StringBuilder();
//					for (SubjectDoseTimePoints s : oldTimePoints) {
//						if (flag) {
//							sb.append(",").append(s.getTimePoint());
//						} else {
//							sb.append(s.getTimePoint());
//							flag = true;
//						}
//					}
//					return "1--" + sb.toString() + "--Previous dosing not done for the timepoints : " + sb.toString();
//				}
//			}
//
//			SubjectRandamization sr = clinicalDao.subjectRandamization(study, sp, tech.getSubjectOrder());
//			SimpleDateFormat dateFormat = new SimpleDateFormat(environment.getProperty("dateFormat"));
//			SimpleDateFormat sdf = new SimpleDateFormat(environment.getProperty("dateTimeFormat"));
//			if (tech.getFastingCriteria() != null && !tech.getFastingCriteria().equals("0")) {
//				SubjectMealsTimePoints smtp = clinicalDao.subjectMealsTimePointsForFasting(tech);
//				if (smtp != null && smtp.getSubjectMealsTimePointsData() != null) {
//					if (smtp.getSubjectMealsTimePointsData().getEndON() != null) {
//						String endDate = dateFormat.format(smtp.getSubjectMealsTimePointsData().getEndON());
//						Date dinnerEndTime = null;
//						try {
//							dinnerEndTime = sdf
//									.parse(endDate + " " + smtp.getSubjectMealsTimePointsData().getEndTime());
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//						String[] fastrintime = tech.getFastingCriteria().split("\\:");
//						int min = Integer.parseInt(fastrintime[1]);
//						min += (60 * Integer.parseInt(fastrintime[0]));
//						Date currentDate = new Date();
//						long duration = currentDate.getTime() - dinnerEndTime.getTime();
//
////						long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
//						long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
////					long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
////						long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
//						if (diffInMinutes < min) {
//							return "1--0--Subject Fastring criteria Failed.";
//						}
//					} else {
//						return "1--0--Subject Dinner end time not avilable";
//					}
//				} else {
//					return "1--0--Subject Dinner time not avilable";
//				}
//			}
//			if (tech.getFedCriteria() != null && !tech.getFedCriteria().equals("0")) {
//				SubjectMealsTimePoints smtpFast = clinicalDao.subjectMealsTimePointsForFed(tech);
//				if (smtpFast != null && smtpFast.getSubjectMealsTimePointsData() != null) {
//					if (smtpFast.getSubjectMealsTimePointsData().getEndTime() != null) {
//						String startDate = dateFormat.format(smtpFast.getSubjectMealsTimePointsData().getStartedOn());
//						Date breakfalstStartTime = null;
//						try {
//							breakfalstStartTime = sdf
//									.parse(startDate + " " + smtpFast.getSubjectMealsTimePointsData().getStartTime());
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						if (breakfalstStartTime != null) {
//							String[] feadtime = tech.getFedCriteria().split("\\:");
//							int feadtimemin = Integer.parseInt(feadtime[1]);
//							feadtimemin += (60 * Integer.parseInt(feadtime[0]));
//
//							Date currentDate = new Date();
//							Long duration = currentDate.getTime() - breakfalstStartTime.getTime();
//
//							long diffInMinutesfead = TimeUnit.MILLISECONDS.toMinutes(duration);
//							if (diffInMinutesfead < feadtimemin) {
//								return "1--0--Subject Fed criteria Failed.";
//							}
//						} else {
//							return "1--0--Error";
//						}
//					} else {
//						return "1--0--Subject " + smtpFast.getMealsType().getFieldValue() + " End time not avilable";
//					}
//				} else {
//					return "1--0--Subject " + smtpFast.getMealsType().getFieldValue() + " time not avilable";
//				}
//			}
//			StringBuilder sb = new StringBuilder();
//			sb.append("0").append("--").append(tech.getTimePoint()).append("--");
//			sb.append("<b>Study : </b>").append(tech.getStudy().getProjectNo()).append(", ");
//			sb.append("<b>Period : </b>").append(tech.getPeriod().getPeriodNo()).append(", ");
//			if (tech.getTreatmentInfo() != null)
//				sb.append("<b>Treatment : </b>").append(tech.getTreatmentInfo().getTreatmentNo()).append(", ");
//			sb.append("<b>Subject : </b>").append(tech.getSubjectNo());
//			sb.append("<b>Timepoint : </b>").append(tech.getTimePoint());
//			result = sb.toString();
//		} else
//			return "1--" + tech.getTimePoint() + "--Dose Done alredy for the timepoint : " + tech.getTimePoint();
//		return result;
		return null;
	}

	@Override
	public String saveDosing(Long userId, Date date, DoseDataDto dto, MessageSource messageSource, Locale currentLocale,
			Long languageId, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
		SimpleDateFormat timeFormata = new SimpleDateFormat(environment.getRequiredProperty("timeFormat"));
		SimpleDateFormat dateTimeSdf = new SimpleDateFormat(environment.getRequiredProperty("dateTimeFormat"));
		String[] subjectBarcodeSplit = null;
		String[] sachetBarcodeSplit = null;
		StudyPeriodMaster period = null;
		UserMaster user = null;
		StudySubjects subject = null;
		StudySubjects replaceSubject = null;
		String replaceWith = "";
		DoseTimePoints dosingTimePoint = null;
		DeviationMessage devMsg = null;
		DoseSavingDtoDetails dsDto = null;
		try {
			subjectBarcodeSplit = dto.getSubjectBarcode().substring(0, dto.getSubjectBarcode().length() - 1)
					.split("\\a");
			sachetBarcodeSplit = dto.getSachetBarcode().substring(0, dto.getSachetBarcode().length() - 1).split("\\a");
			if (dto.isReplaceStatus()) {
				replaceWith = dto.getReplaceSubject();
				if (replaceWith.equals("-1")) {
					replaceWith = sachetBarcodeSplit[3];
				}
			}
			dsDto = studyDao.getDoseSavingDtoDetails(subjectBarcodeSplit[2], subjectBarcodeSplit[1], dto.getPerodId(),
					userId, replaceWith, sachetBarcodeSplit[4], dto.getDevationMsgId());
			if (dsDto != null) {
				period = dsDto.getPeriod();
				user = dsDto.getUser();
				subject = dsDto.getSubject();
				devMsg = dsDto.getDvm();
				replaceSubject = dsDto.getReplaceSubject();
				dosingTimePoint = dsDto.getDosingTimePoint();
			}
			if (replaceSubject != null) {
				replaceSubject.setStdSubjectId(subject);
				replaceSubject.setSubjectStatus("Replaced");
			}
			dosingTimePoint = studyDao.doseTimePoint(Long.parseLong(sachetBarcodeSplit[4]));

			SubjectDoseTimePoints tech = new SubjectDoseTimePoints();
			tech.setDoseTimePoints(dosingTimePoint);
			tech.setStudySubjects(subject);
			tech.setActualTime(dateTimeSdf.parse(sdf.format(date) + " " + dto.getCollectionTime()));
			tech.setSachetScanTime(dateTimeSdf.parse(sdf.format(date) + " " + dto.getSachetBarcodeScanTime()));
			tech.setSubjectScanTime(dateTimeSdf.parse(sdf.format(date) + " " + dto.getSubjectBarcodeScanTime()));
			tech.setSubmissionTime(date);
			tech.setDeviationMsg(devMsg);
			tech.setDoseDoneBy(user);
			tech.setFastCriteraComments(dto.getFastCriteraComments());
			tech.setFeadCriteraComments(dto.getFeadCriteraComments());
			if (!dosingTimePoint.getTimePoint().equals("000.000")) {
				DoseTimePoints doseTimePoints = clinicalDao.doseTimePoints(dosingTimePoint.getStudy().getId(),
						dosingTimePoint.getTreatmentInfo().getId());
				SubjectDoseTimePoints subjectDoseTime = clinicalDao.subjectDoseTimePoints(doseTimePoints.getId(),
						subject.getId());

				Date doseActualTime = subjectDoseTime.getActualTime();

				String timePoint = dosingTimePoint.getTimePoint();
				String[] hrmim = timePoint.split("\\.");
				int min = Integer.parseInt(hrmim[0]) * 60;
				min += ((Integer.parseInt(hrmim[1]) * 6) / 100);
				Calendar c = Calendar.getInstance();
				c.setTime(doseActualTime);
				c.add(Calendar.MINUTE, min);

				tech.setScheduleTime(c.getTime());
				String sign = dosingTimePoint.getWindowPeriodSign();
				Date scDate = c.getTime();
				Date acDate = tech.getActualTime();
				String calDeviation = deviation(scDate, acDate, sign, dosingTimePoint.getWindowPeriod(),
						dosingTimePoint.getWindowPeriodType().getCode());
				tech.setDiviation(calDeviation);
			}
			tech = clinicalDao.saveSubjectDoseTimePoints(dto, tech, subject, replaceSubject);

			/*saveSubjectActivityData(tech, dto, messageSource, currentLocale, user.getUsername(), languageId,
					dateFormat);*/

			Map<String, String> s = new HashMap<>();
			s.put("id", tech.getId() + "");
			s.put("actualTime", timeFormata.format(tech.getActualTime()));
			s.put("color", "red");
			String json = "";
			try {
				json = new ObjectMapper().writeValueAsString(s);
			} catch (JsonProcessingException e1) { // TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Map<String, SseEmitter> map = TestWebSocket.doseTimes;
			for (Map.Entry<String, SseEmitter> emitters : map.entrySet()) {
				SseEmitter emitter = emitters.getValue();
				try {
					emitter.send(SseEmitter.event().name("doseTimes").data(json));
				} catch (IOException e) { // TODO Auto-generated catch block
					TestWebSocket.doseTimes.remove(user.getUsername());
				}
			}
		} catch (Exception e) { // TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	/*private void saveSubjectActivityData(SubjectDoseTimePoints data, DoseDataDto colletion, MessageSource messageSource,
			Locale currentLocale, String username, Long languageId, String dateFormat) {
		try {
			Map<Long, List<String>> parameterValuesMap = new HashMap<>();
			for (int i = 0; i < colletion.getPerameterIds().size(); i++) {
				String[] peramValues = colletion.getPerameterValue().get(i).split("\\,");
				List<String> values = new ArrayList<>();
				for (String s : peramValues) {
					values.add(s);
				}
				parameterValuesMap.put(colletion.getPerameterIds().get(i), values);
			}
			SubjectActivityData subjectActivityData = new SubjectActivityData();
			subjectActivityData.setStudyId(colletion.getStudyId());
			subjectActivityData.setActivityId(colletion.getActivityId());
			subjectActivityData.setStudyActivityId(colletion.getStudyActivityId());
			subjectActivityData.setParameterValuesMap(parameterValuesMap);
			subjectActivityData.setTimePointId(data.getId());
			studyActivityService.saveStudyActivityFromsData(subjectActivityData, messageSource, currentLocale, username,
					languageId, dateFormat);
		} catch (Exception e) {
			// TODO: handle exceptione
			e.printStackTrace();
		}
	}*/

	private SheduleTime calicualteSchuduleTime(int doseHr, int doseMin, String doseDate, int min, String code) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(environment.getRequiredProperty("dateTimeFormat"));

		try {
			Date date = dateFormat.parse(doseDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			SheduleTime st = new SheduleTime();
			if (code.equals(StaticData.POSTDOSE.toString()) || code.equals(StaticData.AMBULATORY.toString())) {
				doseMin += min;
				int hr = doseMin / 60;
				doseMin = doseMin % 60;
				doseHr += hr;
				doseHr = doseHr % 24;
				int days = doseHr / 24;
				cal.add(Calendar.DATE, days);
				st.setDate(dateFormat.format(cal.getTime()));
				if (doseHr < 10 && doseMin < 10)
					st.setTime("0" + doseHr + ":" + "0" + doseMin);
				else if (doseHr < 10)
					st.setTime("0" + doseHr + ":" + doseMin);
				else if (doseMin < 10)
					st.setTime(doseHr + ":" + "0" + doseMin);
				else
					st.setTime(doseHr + ":" + doseMin);
			} else {
				if (doseMin < min) {
					int hr = min / 60;
					doseHr = doseHr - hr;
					doseMin = doseMin + (hr * 60);
					doseMin = doseMin - min;
					int days = hr / 24;
					cal.add(Calendar.DATE, -days);
					st.setDate(dateFormat.format(cal.getTime()));
					if (doseHr < 10 && doseMin < 10)
						st.setTime("0" + doseHr + ":" + "0" + doseMin);
					else if (doseHr < 10)
						st.setTime("0" + doseHr + ":" + doseMin);
					else if (doseMin < 10)
						st.setTime(doseHr + ":" + "0" + doseMin);
					else
						st.setTime(doseHr + ":" + doseMin);
				} else {
					doseMin = doseMin - min;
					st.setDate(dateFormat.format(date));
					if (doseHr < 10 && doseMin < 10)
						st.setTime("0" + doseHr + ":" + "0" + doseMin);
					else if (doseHr < 10)
						st.setTime("0" + doseHr + ":" + doseMin);
					else if (doseMin < 10)
						st.setTime(doseHr + ":" + "0" + doseMin);
					else
						st.setTime(doseHr + ":" + doseMin);
				}
			}
			return st;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}

	}

	@Override
	public Map<TreatmentInfo, List<MealsTimePoints>> preDsoeMealsTimePointsFromPeriod(StudyPeriodMaster sm) {
		return clinicalDao.preDsoeMealsTimePointsFromPeriod(sm);
	}

	@Override
	public Map<TreatmentInfo, List<MealsTimePoints>> postDsoeMealsTimePointsFromPeriod(StudyPeriodMaster sp) {
		return clinicalDao.postDsoeMealsTimePointsFromPeriod(sp);
	}

	@Override
	public String subjectMealsInfo(String barcode, StudyMaster std, Long periodId, String selecteTimePointIds) {
//		SubjectMealsTimePoints smtp = clinicalDao.subjectMealsTimePoints()
//		SimpleDateFormat sdf = new SimpleDateFormat(environment.getRequiredProperty("dateTimeFormat"));
		Long stdid = Long.parseLong(barcode.substring(2, 8));
		if (stdid != std.getId())
			return "1--Subject not belongs to Current Study.";
		int subjectSeqNo = Integer.parseInt(barcode.substring(9, 12));
		Volunteer vol = studyDao.volunteerWithsubjectBarcode(barcode);
		if (vol == null) {
			return "1--0--<b>Subject is not allowcated to Volunteer</b>";
		} else if (vol.getEnrollSatatus().getStatusCode().equals(StudyStatus.ENROLLED.toString())) {
			SubjectPeriodStatus subjectPeriodStatus = studyDao.subjectPeriodStatusWithPerod(periodId, vol);
			if (subjectPeriodStatus == null) {
				return "1--0--<b>Subject is not participating any Period</b>";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.ENROLLED.toString())) {
				String[] arr = selecteTimePointIds.split("\\,");
				List<Long> timePointIds = new ArrayList<>();
				for (String s : arr)
					timePointIds.add(Long.parseLong(s));
				SubjectMealsTimePoints smtp = clinicalDao.subjectMealsTimePoints(timePointIds, subjectSeqNo,
						subjectPeriodStatus.getPeriod());
				if (smtp != null) {
					String bedNo = vol.getSubjectNo();
					StringBuilder sb = new StringBuilder();
					if (smtp.getSubjectMealsTimePointsData() == null) {
						sb.append("0--1--").append(smtp.getId()).append("--");
					} else if (smtp.getSubjectMealsTimePointsData() != null
							&& smtp.getSubjectMealsTimePointsData().getEndTime() == null) {
						sb.append("0--2--").append(smtp.getId()).append("--");
					} else {
						sb.append("0--3--").append("0").append("--");
					}
					sb.append("<tr id=\"row" + smtp.getId() + "\">");
					sb.append("<th>" + bedNo + "</tH>");
					sb.append("<th>" + smtp.getMealsType().getFieldValue() + "</th>");
					sb.append("<th>" + smtp.getTimePoint() + "<input type='hidden' name='subjectInfo' value=\""
							+ smtp.getId() + "\" /></th>");

//					<th>Start Time</th><th>End Time</th><th>Consumption</th><th>Comment</th></tr>

					if (smtp.getSubjectMealsTimePointsData() == null) {
						sb.append("<td id=\"startTime" + smtp.getId()
								+ "\"><input type='button' value='Start' onclick=\"startTime(\'" + smtp.getId()
								+ "\' )\" disabled='disabled'/></td>");
						sb.append("<td id='endTime" + smtp.getId()
								+ "'><input type='button' value = 'End' disabled='disabled'/></td>");
						sb.append("<td><select name=\"cosumption" + smtp.getId() + "\"  disabled='disabled'>"
								+ "<option value='-1'>-Select-</option>" + "<option value='Full'>Full</option>"
								+ "<option value='Nothing'>Nothing</option>" + "<option value='Others'>Others</option>"
								+ "</select></td>");
						sb.append("<td><input type='text' id=\"message" + smtp.getId() + "\" name=\"message"
								+ smtp.getId() + "\" disabled='disabled'/></td>");
						sb.append("<font color='red' id=\"messageMsg" + smtp.getId() + "\" ></font></td>");
					} else {
						sb.append("<td>" + smtp.getSubjectMealsTimePointsData().getStartTime() + "</td>");
						if (smtp.getSubjectMealsTimePointsData().getEndTime() == null) {
							sb.append("<td id=\"endTime" + smtp.getId()
									+ "\"><input type='button' value='End'  onclick=\"endTime(\'" + smtp.getId()
									+ "\')\" /> <font color='red' id=\"endTimeMsg" + smtp.getId() + "\" ></font></td>");
							sb.append("<td><select name=\"cosumption" + smtp.getId() + "\" id=\"cosumption"
									+ smtp.getId() + "\" onchange=\"chekConsumption(\'" + smtp.getId() + "\')\" >"
									+ "<option value='-1'>-Select-</option>" + "<option value='"
									+ StaticData.FULL.toString() + "'>Full</option>" + "<option value='"
									+ StaticData.HALF.toString() + "'>Half</option>" + "<option value='"
									+ StaticData.NOTHING.toString() + "'>Nothing</option>" + "<option value='"
//									+ StaticData.OTHERS.toString() + "'>Others</option>"
									+ "</select><div id=\"otherCosumption" + smtp.getId()
									+ "\" ></div><font color='red' id=\"cosumptionMsg" + smtp.getId()
									+ "\" ></font></td>");
							sb.append("<td><input type='text' id=\"message" + smtp.getId() + "\" name=\"message"
									+ smtp.getId() + "\" />");
							sb.append("<font color='red' id=\"messageMsg" + smtp.getId() + "\" ></font></td>");
							sb.append(
									"<td><input type='button' value='Save' class='btn btn-primary' onclick=\"submitEndTime('"
											+ smtp.getId() + "')\"/></td>");

						} else {
							sb.append("<td>" + smtp.getSubjectMealsTimePointsData().getEndTime() + "</td>");
							try {
								/*
								 * sb.append("<td>" +
								 * smtp.getSubjectMealsTimePointsData().getCompletion().getFieldValue() +
								 * "</td>");
								 */
							} catch (Exception e) {
								sb.append("<td>Full ot</td>");
							}
						}
					}
					sb.append("</tr>");
					System.out.println(sb.toString());
					return sb.toString();
				} else
					return "1--Meals Time points not avitalble fot the Subject";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.SELECTED.toString())) {
				return "1--0--<b>Subject No not Available</b>";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.REPLACED.toString())) {
				return "1--0--Subject has Replaced";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode()
					.equals(StudyStatus.WITHDRAWN.toString())) {
				return "1--0--Subject has Withdrean";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.DROPED.toString())) {
				return "1--0--Subject has Droped";
			} else {
				return "1--0--Invalied Barcode";
			}
		}
		return null;
	}

	@Override
	public String measlStartTimesSave(Long userId, MealCollectoinDto mcd, Date date) {
		String result = "Failed";
		MealsDataSavingDto mdsDto = clinicalDao.getMealsDataSavingDtoDetails(mcd, userId);
		List<String> starTimesList = null;
		List<String> endTimesList = null;
		StudyMaster study = null;
		List<StudyPeriodMaster> spmList = null;
		List<StudySubjects> subjectsList = null;
		List<TreatmentInfo> treatmentList = null;
		List<MealsTimePoints> mealsList = null;
		List<SubjectMealsTimePointsData> smtpDataList = null;
		List<SubjectMealsTimePointsData> savingSubMDataList = new ArrayList<>();
		List<SubjectMealsTimePointsData> updateSubMDataList = new ArrayList<>();
		Map<Long, StudyPeriodMaster> spmMap = new HashMap<>();
		Map<Long, MealsTimePoints> mtpMap = new HashMap<>();
		Map<String, StudySubjects> subMap = new HashMap<>();
		Map<Long, Map<Long, Map<String, SubjectMealsTimePointsData>>> smtpMap = new HashMap<>(); // mealId, periodId,
																									// subjectNo,
																									// submtpPojo
		Map<String, Map<Long, String>> devMap = new HashMap<>();
		Map<String, Map<Long, String>> deviationMap = new HashMap<>();
		try {
			if (mdsDto != null) {
				starTimesList = mcd.getStartMealsData();
				endTimesList = mcd.getEndMealsData();
				study = mdsDto.getStudy();
				spmList = mdsDto.getSpmList();
				subjectsList = mdsDto.getSubjectsList();
				treatmentList = mdsDto.getTreatmentList();
				mealsList = mdsDto.getMealsList();
				smtpDataList = mdsDto.getSmtpDataList();
			}
			if (spmList != null && spmList.size() > 0) {
				for (StudyPeriodMaster spm : spmList) {
					spmMap.put(spm.getId(), spm);
				}
			}
			if (mealsList != null && mealsList.size() > 0) {
				for (MealsTimePoints mtp : mealsList) {
					mtpMap.put(mtp.getId(), mtp);
				}
			}
			if (subjectsList != null && subjectsList.size() > 0) {
				for (StudySubjects sub : subjectsList) {
					subMap.put(sub.getSubjectNo(), sub);
				}
			}
			Map<Long, Map<String, SubjectMealsTimePointsData>> tempMap1 = null;
			Map<String, SubjectMealsTimePointsData> tempMap2 = null;
			if (smtpDataList != null && smtpDataList.size() > 0) {
				for (SubjectMealsTimePointsData smt : smtpDataList) {
					if(smt.getEndTime() == null) {
						if (smtpMap.containsKey(smt.getMealsTimePoint().getId())) {
							tempMap1 = smtpMap.get(smt.getMealsTimePoint().getId());
							if (tempMap1.containsKey(smt.getStudyPeriodMaster().getId())) {
								if (!tempMap2.containsKey(smt.getSubject().getSubjectNo())) {
									tempMap2.put(smt.getSubject().getSubjectNo(), smt);
									tempMap1.put(smt.getStudyPeriodMaster().getId(), tempMap2);
									smtpMap.put(smt.getMealsTimePoint().getId(), tempMap1);
								}
							} else {
								tempMap2.put(smt.getSubject().getSubjectNo(), smt);
								tempMap1.put(smt.getStudyPeriodMaster().getId(), tempMap2);
								smtpMap.put(smt.getMealsTimePoint().getId(), tempMap1);
							}
						} else {
							tempMap2 = new HashMap<>();
							tempMap1 = new HashMap<>();
							tempMap2.put(smt.getSubject().getSubjectNo(), smt);
							tempMap1.put(smt.getStudyPeriodMaster().getId(), tempMap2);
							smtpMap.put(smt.getMealsTimePoint().getId(), tempMap1);
						}
					}
				}
			}
			if (starTimesList != null && starTimesList.size() > 0) {
				if (mcd.getTimeDeviationTime() != null && mcd.getTimeDeviationTime().size() > 0) {
					for (String dvStr : mcd.getTimeDeviationTime()) {
						String[] tArr = dvStr.split("\\@@@");
						if (tArr.length > 0) {
							// For Deviation msg set to mealsrecord
							Map<Long, String> dMap = new HashMap<>();
							dMap.put(Long.parseLong(tArr[1]), tArr[2]);
							devMap.put(tArr[0], dMap);

							// Fro Raise Deviations
							Map<Long, String> tempMap = new HashMap<>();
							tempMap.put(Long.parseLong(tArr[1]), tArr[2] + "@@@@" + tArr[3]);
							deviationMap.put(tArr[0], tempMap);

						}
					}
				}

				// timpoint, subject, value, periodId
				for (String st : starTimesList) {
					if (st != null && !st.equals("")) {
						String[] arr = st.split("\\@@@");
						if (arr.length > 0) {
							MealsTimePoints mtpPojo = mtpMap.get(Long.parseLong(arr[0]));
							SubjectMealsTimePointsData smtp = new SubjectMealsTimePointsData();
							SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String actDateStr = sd.format(new Date());
							Date actualDate = sdf.parse(actDateStr + " " + arr[2]+":00");
							SubjectDoseTimePoints sdtp = clinicalDao.getSubjectDoseTimepointRecord(subMap.get(arr[1]),
									spmMap.get(Long.parseLong(arr[3])), study);
							String sign = mtpMap.get(Long.parseLong(arr[0])).getSign();
							if (sign == null || sign.equals(""))
								sign = "+";
							if (sdtp != null && sign.trim().equals("+")) {
								Date doseDate = sdtp.getActualTime();
								String timePoint = mtpPojo.getTimePoint();
								String[] hrmim = timePoint.split("\\.");
								int min = Integer.parseInt(hrmim[0]) * 60;
								min += ((Integer.parseInt(hrmim[1]) * 6) / 100);
								Calendar c = Calendar.getInstance();
								c.setTime(doseDate);
								c.add(Calendar.MINUTE, min);
								smtp.setScheduleTime(c.getTime());
							}
							smtp.setActualtime(actualDate);
							smtp.setStartTime(actualDate);
							smtp.setComments("");
							smtp.setCreatedBy(mdsDto.getUser());
							smtp.setCreatedOn(new Date());
							smtp.setDeviation("");
							smtp.setMealsTimePoint(mtpMap.get(Long.parseLong(arr[0])));
							smtp.setStartedBy(mdsDto.getUser());
							smtp.setStartedOn(new Date());
							smtp.setSubject(subMap.get(arr[1]));
							smtp.setStudyPeriodMaster(spmMap.get(Long.parseLong(arr[3])));
							if (devMap != null && devMap.size() > 0) {
								if (devMap.get(arr[1]) != null)
									smtp.setDeviation(devMap.get(arr[1]).get(Long.parseLong(arr[0])));
							}

							savingSubMDataList.add(smtp);
						}
					}
				}
			}

			if (endTimesList != null && endTimesList.size() > 0) {
				// timePoint, subjectno, value, periodId, consumptionMsg, comments
				for (String st : endTimesList) {
					if (st != null && !st.equals("")) {
						String[] arr = st.split("\\@@@");
						if (arr.length > 0) {
							SubjectMealsTimePointsData smtp = smtpMap.get(Long.parseLong(arr[0]))
									.get(Long.parseLong(arr[3])).get(arr[1]);
							if (smtp != null) {
								SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String actDateStr = sd.format(new Date());
								Date endDate = sdf.parse(actDateStr + " " + arr[2]+":00");
								if(!arr[5].equals("0"))
									smtp.setComments(arr[5]);
								smtp.setUpdatedBy(mdsDto.getUser());
								smtp.setUpdatedOn(new Date());
								smtp.setDeviation("");
								smtp.setEndBy(mdsDto.getUser());
								smtp.setEndTime(endDate);
								smtp.setConsumption(arr[4]);
								smtp.setEndON(new Date());
								updateSubMDataList.add(smtp);
							}
						}
					}
				}
			}
			result = clinicalDao.saveSubjectsMealsData(savingSubMDataList, updateSubMDataList, mcd, mdsDto.getUser(),
					deviationMap);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/*
	 * @Override public ResultDto measlEndTimesSave(Long
	 * subjectPeriodTimePointCollectedDataId, Long timepointsId, String
	 * runningTimeWithSeconds, String ranningTime, Long userId, String consumption,
	 * String consumptionVal, String comment, String subjectNo) { Date onyDate = new
	 * Date(); UserMaster user = userDao.findById(userId); SimpleDateFormat sdf =
	 * new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
	 * SimpleDateFormat dateTimeFormatSeconds = new SimpleDateFormat(
	 * environment.getRequiredProperty("dateTimeFormatSeconds"));
	 * SubjectMealsTimePointsData subjectMealsTimePointsData = clinicalDao
	 * .subjectMealsTimePointsDataById(subjectPeriodTimePointCollectedDataId); //
	 * StudyMaster sm =
	 * subjectMealsTimePointsData.getStudyPeriodMaster().getStudy(); try {
	 * subjectMealsTimePointsData
	 * .setEndTime(dateTimeFormatSeconds.parse(sdf.format(onyDate) + " " +
	 * runningTimeWithSeconds)); } catch (ParseException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } subjectMealsTimePointsData.setEndBy(user);
	 * subjectMealsTimePointsData.setEndON(onyDate); subjectMealsTimePointsData
	 * .setCompletion(studyCreationDao.formStaticData(StudyDesign.CONSUMPTION.
	 * toString(), consumption));
	 * subjectMealsTimePointsData.setCompletionOther(consumptionVal);
	 * subjectMealsTimePointsData.setActualtime(subjectMealsTimePointsData.
	 * getEndTime()); subjectMealsTimePointsData.setComments(comment);
	 * subjectMealsTimePointsData.setEndTimeOnly(ranningTime);
	 * clinicalDao.updateSubjectMealsTimePointsData(subjectMealsTimePointsData);
	 * 
	 * ResultDto dto = new ResultDto(); dto.setSuccess(true + "");
	 * dto.setMessage("End Time Saved Successfully"); dto.getMealSubjectData()
	 * .put(subjectMealsTimePointsData.getSubject().getSubjectNo() + "," +
	 * subjectMealsTimePointsData.getStudyPeriodMaster().getId() + "," +
	 * subjectMealsTimePointsData.getMealsTimePoint().getId(),
	 * subjectMealsTimePointsData); try {
	 * 
	 * Map<String, String> s = new HashedMap(); s.put("id", "end_" +
	 * subjectMealsTimePointsData.getId()); s.put("actualTime",
	 * runningTimeWithSeconds); s.put("color", "blue"); String json = ""; try { json
	 * = new ObjectMapper().writeValueAsString(s); } catch (JsonProcessingException
	 * e1) { // TODO Auto-generated catch block e1.printStackTrace(); } Map<String,
	 * SseEmitter> map = TestWebSocket.mealCollectionEnd; for (Map.Entry<String,
	 * SseEmitter> emitters : map.entrySet()) { SseEmitter emitter =
	 * emitters.getValue(); try {
	 * emitter.send(SseEmitter.event().name("mealCollectionEnd").data(json)); }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * TestWebSocket.mealCollectionEnd.remove(user.getUsername()); } } } catch
	 * (Exception e) { // TODO: handle exception e.printStackTrace(); } return dto;
	 * }
	 */

	@Override
	public String vacutainerForSampleSeparation(String barcode) {
		SubjectSampleCollectionTimePoints ssctp = clinicalDao.subjectSampleCollectionTimePoints(barcode);
		if (ssctp != null) {
			if (ssctp.getSubjectSampleCollectionTimePointsData() != null
					&& ssctp.getSubjectSampleCollectionTimePointsData().getActualtime() != null) {
				if (ssctp.getSubjectSampleCollectionTimePointsData().getVialActualTime() == null) {
					StringBuilder sb = new StringBuilder();
					sb.append("0--").append(ssctp.getSampleTimePointId().getNoOfVials()).append("--");
					sb.append("<b>Study : </b>").append(ssctp.getStudy().getProjectNo()).append(", ");
					sb.append("<b>Period : </b>").append(ssctp.getPeriod().getPeriodNo()).append(", ");
					sb.append("<b>Subject : </b>").append(ssctp.getSubjectNo());
					sb.append("<b>Type : </b>").append(ssctp.getTimePointType().getFieldValue()).append(", ");
					sb.append("<b>Time Point : </b>").append(ssctp.getTimePoint()).append("(")
							.append(ssctp.getVacutainer()).append(")");
					if (ssctp.getTreatmentInfo() != null)
						sb.append("<b>Treatment : </b>").append(ssctp.getTreatmentInfo().getTreatmentName());
					sb.append("--").append(ssctp.getStudy().getProjectNo()).append("--")
							.append(ssctp.getPeriod().getPeriodNo());
					sb.append("--").append(ssctp.getTimePoint()).append("--").append(ssctp.getSubjectNo());
					return sb.toString();
				} else {
					return "1--0--Sample Separation done before";
				}
			} else {
				return "1--0--Sample Collection not does for timepoint : " + ssctp.getTimePoint();
			}

		} else {
			return "1--0--Invalied Barcode";
		}
	}

	@Override
	public String saveSampleSaperation(Date date, String vacutainer1, Map<Integer, String> vials, int noOfVials,
			Long studyId, Long userId, String vacutainerScanTime, Map<Integer, String> vialsScanTime) {
		try {
			UserMaster user = userDao.findById(userId);
			SimpleDateFormat sdf = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
			SimpleDateFormat sdfTime = new SimpleDateFormat(environment.getRequiredProperty("dateTimeFormatSeconds"));
			SimpleDateFormat timeFormat = new SimpleDateFormat(environment.getRequiredProperty("timeFormat"));
			String currnetdate = sdf.format(new Date());
			SubjectSampleCollectionTimePoints ssctp = clinicalDao.subjectSampleCollectionTimePoints(vacutainer1);

			ssctp.getSubjectSampleCollectionTimePointsData().setVialActualTime(date);
			ssctp.getSubjectSampleCollectionTimePointsData().setSeparatedBy(user);
			List<SubjectSampleSeparationTimePointsData> sepdata = new ArrayList<>();
			vials.forEach((k, v) -> {
				SubjectSampleSeparationTimePointsData ssstpd = new SubjectSampleSeparationTimePointsData();
				ssstpd.setVialBarocde(v);
				ssstpd.setSubjectSampleCollectionTimPoints(ssctp);
				try {
					ssstpd.setVacutinerScanTime(sdfTime.parse(date + " " + vacutainerScanTime));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					ssstpd.setVacutinerScanTime(new Date());
				}
				try {
					ssstpd.setVialScanTimetime(sdfTime.parse(date + " " + vialsScanTime.get(k)));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					ssstpd.setVacutinerScanTime(new Date());
				}
				ssstpd.setVialNo(k);
				ssstpd.setSeparatedBy(ssctp.getSubjectSampleCollectionTimePointsData().getSeparatedBy());
				ssstpd.setSeparationDate(date);
				sepdata.add(ssstpd);
			});

			/*
			 * String t1 = vac.getScheduleTime(); String t2 = vac.getActualTime(); String[]
			 * t1a = t1.split("\\:"); String[] t2a = t2.split("\\:"); int dh =
			 * Integer.parseInt(t2a[0]) - Integer.parseInt(t1a[0]); int dm =
			 * Integer.parseInt(t2a[1]) - Integer.parseInt(t1a[1]); int diff = ((dh* 60) +
			 * dm); if(diff > 2) vac.setDeviationOfCollection(diff-2); else if(diff < 2)
			 * vac.setDeviationOfCollection(diff+2);
			 */
			clinicalDao.updateClinicalSampleTimePoints(ssctp, sepdata);
			if (TestWebSocket.sampleSaparation.size() > 0) {
				try {

					Map<String, String> s = new HashedMap();
					s.put("id", ssctp.getSubjectSampleCollectionTimePointId() + "");
					s.put("actualTime",
							timeFormat.format(ssctp.getSubjectSampleCollectionTimePointsData().getVialActualTime()));
					s.put("color", "blue");
					String json = "";
					try {
						json = new ObjectMapper().writeValueAsString(s);
					} catch (JsonProcessingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Map<String, SseEmitter> map = TestWebSocket.sampleSaparation;
					for (Map.Entry<String, SseEmitter> emitters : map.entrySet()) {
						SseEmitter emitter = emitters.getValue();
						try {
							emitter.send(SseEmitter.event().name("sampleSaparation").data(json));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							TestWebSocket.sampleSaparation.remove(user.getUsername());
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
			return "Sample Saparation Done successfully For time Point : " + ssctp.getTimePoint() + " Of Subject : "
					+ ssctp.getSubjectNo();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "Faild Separation data.";
		}
	}

	@Override
	public Map<TreatmentInfo, List<VitalTimePoints>> preDsoeVitalTimePointsFromPeriod(StudyPeriodMaster sm) {
		return clinicalDao.preDsoeVitalTimePointsFromPeriod(sm);
	}

	@Override
	public Map<TreatmentInfo, List<VitalTimePoints>> postDsoeVitalTimePointsFromPeriod(StudyPeriodMaster sp) {
		return clinicalDao.postDsoeVitalTimePointsFromPeriod(sp);
	}

	@Override
	public String subjectVitalInfo(String barcode, StudyMaster std, Long periodId, String selecteTimePointIds) {
		Long stdid = Long.parseLong(barcode.substring(2, 8));
		if (stdid != std.getId())
			return "1--Subject not belongs to Current Study.";
		int subjectSeqNo = Integer.parseInt(barcode.substring(9, 12));
		Volunteer vol = studyDao.volunteerWithsubjectBarcode(barcode);
		if (vol == null) {
			return "1--0--<b>Subject is not allowcated to Volunteer</b>";
		} else if (vol.getEnrollSatatus().getStatusCode().equals(StudyStatus.ENROLLED.toString())) {
			SubjectPeriodStatus subjectPeriodStatus = studyDao.subjectPeriodStatusWithPerod(periodId, vol);
			if (subjectPeriodStatus == null) {
				return "1--0--<b>Subject is not participating any Period</b>";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.ENROLLED.toString())) {
				String[] arr = selecteTimePointIds.split("\\,");
				List<Long> timePointIds = new ArrayList<>();
				for (String s : arr)
					timePointIds.add(Long.parseLong(s));
				SubjectVitalTimePoints smtp = clinicalDao.subjectVitalTimePoints(timePointIds, subjectSeqNo,
						subjectPeriodStatus.getPeriod());
				if (smtp != null) {
					if (smtp.getSubjectVitalTimePointsData() == null) {
						String bedNo = vol.getSubjectNo();
						StringBuilder sb = new StringBuilder();
						if (smtp.getSubjectVitalTimePointsData() == null) {
							sb.append("0--0--").append(smtp.getId()).append("--");
						} else {
							sb.append("0--1--").append(smtp.getId()).append("--");
						}
						sb.append(subjectPeriodStatus.getPeriodNo()).append("--");
						sb.append(vol.getSeqNo()).append("--");
						sb.append("<b>Study : </b>").append(std.getProjectNo()).append(", ");
						sb.append("<b>Subject : </b>").append(vol.getSubjectNo());
						sb.append("<b>Period : </b>").append(subjectPeriodStatus.getPeriodNo());
						sb.append("--");
						sb.append("<table><tr><th>Test Name  </th><th>Result</th></tr>");
						if (smtp.isOrthostatic()) {
							sb.append("<tr><th>Position</th><th>").append(smtp.getOrthostaticPosition())
									.append("</tr>");
						}
						List<TimePointVitalTests> test = clinicalDao.timePointVitalTests(smtp.getVitalTimePointsId());

						for (TimePointVitalTests timePointVitalTests : test) {
							if (timePointVitalTests.getTestId().getTestName().equals(StaticData.BP.toString())) {
								sb.append("<tr><th>").append(timePointVitalTests.getTestId().getTestName()).append(
										"<td><input type='text' name='systolic' id='systolic' value='' onchange=\"checkValue('systolic')\"/>/<input type='text' name='diastolic' id='diastolic' value='' />")
										.append("<font color='red' id='systolicmsg'></font><font color='red' id='diastolicmsg'></font></td>");
							} else if (timePointVitalTests.getTestId().getTestName()
									.equals(StaticData.WELLBEING.toString()))
								sb.append("<tr><th>").append(timePointVitalTests.getTestId().getTestName())
										.append("<td><input type='radio' name='"
												+ timePointVitalTests.getTestId().getTestName() + "' id='"
												+ timePointVitalTests.getTestId().getTestName()
												+ "' value='Yes'  onchange=\"checkValue('"
												+ timePointVitalTests.getTestId().getTestName()
												+ "')\">Yes &nbsp;<input type='radio' name='"
												+ timePointVitalTests.getTestId().getTestName() + "' id='"
												+ timePointVitalTests.getTestId().getTestName()
												+ "' value='No'  onchange=\"checkValue('"
												+ timePointVitalTests.getTestId().getTestName()
												+ "')\">No <br/><font color='red' id='"
												+ timePointVitalTests.getTestId().getTestName() + "msg'></font></td>");
							else
								sb.append("<tr><th>").append(timePointVitalTests.getTestId().getTestName())
										.append("<td><input type='text' name='"
												+ timePointVitalTests.getTestId().getTestName() + "' id='"
												+ timePointVitalTests.getTestId().getTestName()
												+ "'   onchange=\"checkValue('"
												+ timePointVitalTests.getTestId().getTestName()
												+ "')\"/><font color='red' id='"
												+ timePointVitalTests.getTestId().getTestName() + "msg'></font></td>");
						}

						sb.append("</table>");
						StringBuilder sb2 = new StringBuilder();
						boolean testcondition = false;
						for (TimePointVitalTests timePointVitalTests : test) {
							if (!timePointVitalTests.getTestId().getTestName()
									.equals(StaticData.WELLBEING.toString())) {
								if (!timePointVitalTests.getTestId().getTestName().equals(StaticData.BP.toString())) {
									if (testcondition) {
										sb2.append(",,").append(timePointVitalTests.getTestId().getTestName())
												.append(",").append(timePointVitalTests.getTestId().getMinimum() + ","
														+ timePointVitalTests.getTestId().getMaximum());
//										testNames.append(",").append("systolic").append(",").append("diastolic");
									} else {
										sb2.append(timePointVitalTests.getTestId().getTestName()).append(",")
												.append(timePointVitalTests.getTestId().getMinimum() + ","
														+ timePointVitalTests.getTestId().getMaximum());
										testcondition = true;
//										testNames.append("systolic").append(",").append("diastolic");
									}
								} else {
									String[] min = timePointVitalTests.getTestId().getMinimum().split("\\/");
									String[] max = timePointVitalTests.getTestId().getMaximum().split("\\/");
									if (testcondition) {
										sb2.append(",,").append("systolic,").append(min[0] + "," + max[0]);
										sb2.append(",,").append("diastolic,").append(min[1] + "," + max[1]);
//										testNames.append(",").append(timePointVitalTests.getTestId().getTestName());
									} else {
										sb2.append("systolic,").append(min[0] + "," + max[0]);
										sb2.append(",,").append("diastolic,").append(min[1] + "," + max[1]);
										testcondition = true;
//										testNames.append(timePointVitalTests.getTestId().getTestName());
									}
								}
							}
						}
						System.out.println(sb2.toString());
						sb.append("--").append(sb2.toString());
						sb.append("--").append(smtp.getId());
						System.out.println(sb.toString());
						return sb.toString();
					} else {
						return "1--Vital Time points data collected before.";
					}

				} else
					return "1--Vital Time points not avitalble fot the Subject";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.SELECTED.toString())) {
				return "1--<b>Subject No not Available</b>";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.REPLACED.toString())) {
				return "1--Subject has Replaced";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode()
					.equals(StudyStatus.WITHDRAWN.toString())) {
				return "1--Subject has Withdrean";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.DROPED.toString())) {
				return "1--Subject has Droped";
			} else {
				return "1--Invalied Barcode";
			}
		}
		return null;
	}

	@Override
	public SubjectVitalTimePoints subjectVitalTimePoints(Long timePointId) {
		// TODO Auto-generated method stub
		return clinicalDao.subjectVitalTimePoints(timePointId);
	}

	@Override
	public String saveVitalTimes(StudyMaster sm, SubjectVitalTimePoints smtp, List<SubjectTimePointVitalTests> test,
			Long userId, String ranningTime, String subjectScanTime) {
//		try {
//			UserMaster user = userDao.findById(userId);
//			Date date = new Date();
//			SimpleDateFormat dateFormat = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
//			SubjectVitalTimePointsData data = new SubjectVitalTimePointsData();
////			data.setSubjectVitalTimePoints(smtp);
//			data.setActualtime(ranningTime);
//			data.setActualDate(dateFormat.format(date));
//			data.setCollectedBy(user);
//			data.setSubmissionTime(ranningTime);
//			data.setSubjectScanTime(subjectScanTime);
//			if (smtp.getTimePointType().getCode().equals(StaticData.POSTDOSE.toString())) {
//				SimpleDateFormat csdf = new SimpleDateFormat(environment.getRequiredProperty("clinicalDateTimeFormat"));
//				String sign = smtp.getWindowPeriodSign();
//
//				Date scDate = null;
//				try {
//					scDate = csdf.parse(smtp.getScheduleDate() + " " + smtp.getScheduleTime());
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				Date acDate = null;
//				try {
//					acDate = csdf.parse(data.getActualDate() + " " + data.getActualtime());
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				if (scDate != null && acDate != null) {
//					String calDeviation = deviation(scDate, acDate, sign, smtp.getWindowPeriod(),
//							smtp.getWindowPeriodType().getCode());
//					data.setDeviation(calDeviation);
//				}
//			}
//			smtp.setCollectionStatus(true);
//			smtp.setSubjectVitalTimePointsData(data);
//			clinicalDao.saveSubjectVitalDetials(smtp, data, test);
//
//			try {
//
//				Map<String, String> s = new HashedMap();
//				s.put("id", "start_" + smtp.getId());
//				s.put("actualTime", smtp.getStartTime());
//				s.put("color", "blue");
//				String json = "";
//				try {
//					json = new ObjectMapper().writeValueAsString(s);
//				} catch (JsonProcessingException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				Map<String, SseEmitter> map = TestWebSocket.vitalCollectionStart;
//				for (Map.Entry<String, SseEmitter> emitters : map.entrySet()) {
//					SseEmitter emitter = emitters.getValue();
//					try {
//						emitter.send(SseEmitter.event().name("vitalCollectionStart").data(json));
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						TestWebSocket.vitalCollectionStart.remove(user.getUsername());
//					}
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			try {
//
//				Map<String, String> s = new HashedMap();
//				s.put("id", "endt_" + smtp.getId());
//				s.put("actualTime", smtp.getEndTime());
//				s.put("color", "blue");
//				String json = "";
//				try {
//					json = new ObjectMapper().writeValueAsString(s);
//				} catch (JsonProcessingException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				Map<String, SseEmitter> map = TestWebSocket.vitalCollectionEnd;
//				for (Map.Entry<String, SseEmitter> emitters : map.entrySet()) {
//					SseEmitter emitter = emitters.getValue();
//					try {
//						emitter.send(SseEmitter.event().name("vitalCollectionEnd").data(json));
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						TestWebSocket.vitalCollectionEnd.remove(user.getUsername());
//					}
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			return "Subject Vital Detial saved Successfully";
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			return null;
//		}
		return null;

	}

	@Override
	public Map<TreatmentInfo, List<ECGTimePoints>> preDsoeEcgTimePointsFromPeriod(StudyPeriodMaster sm) {
		return clinicalDao.preEcgTimePointsFromPeriod(sm);
	}

	@Override
	public Map<TreatmentInfo, List<ECGTimePoints>> postDsoeEcgTimePointsFromPeriod(StudyPeriodMaster sp) {
		return clinicalDao.postDsoeEcgTimePointsFromPeriod(sp);
	}

	@Override
	public String subjectEcgInfo(String barcode, StudyMaster std, Long periodId, String selecteTimePointIds) {
		Long stdid = Long.parseLong(barcode.substring(2, 8));
		if (stdid != std.getId())
			return "1--Subject not belongs to Current Study.";
		int subjectSeqNo = Integer.parseInt(barcode.substring(9, 12));
		Volunteer vol = studyDao.volunteerWithsubjectBarcode(barcode);
		if (vol == null) {
			return "1--0--<b>Subject is not allowcated to Volunteer</b>";
		} else if (vol.getEnrollSatatus().getStatusCode().equals(StudyStatus.ENROLLED.toString())) {
			SubjectPeriodStatus subjectPeriodStatus = studyDao.subjectPeriodStatusWithPerod(periodId, vol);
			if (subjectPeriodStatus == null) {
				return "1--0--<b>Subject is not participating any Period</b>";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.ENROLLED.toString())) {
				String[] arr = selecteTimePointIds.split("\\,");
				List<Long> timePointIds = new ArrayList<>();
				for (String s : arr)
					timePointIds.add(Long.parseLong(s));
				SubjectECGTimePoints smtp = clinicalDao.subjectEcgTimePoints(timePointIds, subjectSeqNo,
						subjectPeriodStatus.getPeriod());
				if (smtp != null) {
					if (smtp.getSubjectECGTimePointsData() == null) {
						StringBuilder sb = new StringBuilder();
						if (smtp.getSubjectECGTimePointsData() == null) {
							sb.append("0--0--");
						} else {
							sb.append("0--1--");
						}
						sb.append(subjectPeriodStatus.getPeriodNo()).append("--");
						sb.append(vol.getSeqNo()).append("--");
						sb.append("<b>Study : </b>").append(std.getProjectNo()).append(", ");
						sb.append("<b>Subject : </b>").append(vol.getSubjectNo());
						sb.append("<b>Period : </b>").append(subjectPeriodStatus.getPeriodNo());
						sb.append("--").append(smtp.getId());
						System.out.println(sb.toString());
						return sb.toString();
					} else {
						return "1--ECG Time points data saved before avitalble fot the Subject";
					}

				} else
					return "1--ECG Time points not avitalble fot the Subject";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.SELECTED.toString())) {
				return "1--0--<b>Subject No not Available</b>";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.REPLACED.toString())) {
				return "1--0--Subject has Replaced";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode()
					.equals(StudyStatus.WITHDRAWN.toString())) {
				return "1--0--Subject has Withdrean";
			} else if (subjectPeriodStatus.getSubjectStatus().getStatusCode().equals(StudyStatus.DROPED.toString())) {
				return "1--0--Subject has Droped";
			} else {
				return "1--0--Invalied Barcode";
			}
		}
		return null;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public String saveECGTimes(StudyMaster sm, Long timePointId, Long subtimePointId, Long userId, String startTime,
			String endTime) {
		// TODO Auto-generated method stub
		UserMaster user = userDao.findById(userId);
		Date date = new Date();
		SubjectECGTimePoints smtp = clinicalDao.subjectECGTimePoints(subtimePointId);
		SimpleDateFormat dateFormat = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
		SubjectECGTimePointsData data = new SubjectECGTimePointsData();
		data.setStartTime(startTime);
		data.setEndTime(endTime);
		data.setActualDate(dateFormat.format(date));
		data.setCollectedBy(user);
		if (smtp.getTimePointType().equals(StaticData.POSTDOSE.toString())) {
			SimpleDateFormat csdf = new SimpleDateFormat(environment.getRequiredProperty("clinicalDateTimeFormat"));
			String sign = smtp.getWindowPeriodSign();

			Date scDate = null;
			try {
				scDate = csdf.parse(smtp.getScheduleDate() + " " + smtp.getScheduleTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date acDate = null;
			try {
				acDate = csdf.parse(data.getActualDate() + " " + data.getEndTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (scDate != null && acDate != null) {
				String calDeviation = deviation(scDate, acDate, sign, smtp.getWindowPeriod(),
						smtp.getWindowPeriodType().getCode());
				data.setDeviation(calDeviation);
			}
		}
		smtp.setCollectionStatus(true);
		smtp.setSubjectECGTimePointsData(data);
		clinicalDao.saveSubjectEcgDetials(smtp, data);
		try {

			Map<String, String> s = new HashedMap();
			s.put("id", "start_" + smtp.getId());
			s.put("actualTime", smtp.getSubjectECGTimePointsData().getStartTime());
			s.put("color", "blue");
			String json = "";
			try {
				json = new ObjectMapper().writeValueAsString(s);
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Map<String, SseEmitter> map = TestWebSocket.ecgCollectionDashStart;
			for (Map.Entry<String, SseEmitter> emitters : map.entrySet()) {
				SseEmitter emitter = emitters.getValue();
				try {
					emitter.send(SseEmitter.event().name("ecgCollectionDashStart").data(json));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					TestWebSocket.ecgCollectionDashStart.remove(user.getUsername());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {

			Map<String, String> s = new HashedMap();
			s.put("id", "endt_" + smtp.getId());
			s.put("actualTime", smtp.getSubjectECGTimePointsData().getEndTime());
			s.put("color", "blue");
			String json = "";
			try {
				json = new ObjectMapper().writeValueAsString(s);
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Map<String, SseEmitter> map = TestWebSocket.ecgCollectionDashEnd;
			for (Map.Entry<String, SseEmitter> emitters : map.entrySet()) {
				SseEmitter emitter = emitters.getValue();
				try {
					emitter.send(SseEmitter.event().name("ecgCollectionDashEnd").data(json));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					TestWebSocket.ecgCollectionDashEnd.remove(user.getUsername());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "Subject ECG Detial saved Successfully";

	}

	@Override
	public List<SampleCollectionDashBoard> sampleTimePointsDataOfPeriodHeading(StudyMaster sm) {
		List<SampleCollectionDashBoard> head = new ArrayList<>();
		List<SampleCollectionDashBoardColumns> columns = clinicalDao.sampleCollectionTimePointsHeadding(sm);
		head.add(new SampleCollectionDashBoard("Order No", columns));
		SortedMap<Integer, Integer> map = new TreeMap<>();
		if (sm.isTreatmentSpecificSampleTimepoints()) {
			SortedMap<Long, List<SampleCollectionDashBoardColumns>> rows = new TreeMap<>();
			List<TreatmentInfo> treatments = clinicalDao.treatmentInfo(sm);
			for (TreatmentInfo tr : treatments) {
				List<SampleCollectionDashBoardColumns> timePontsColumns = clinicalDao
						.sampleCollectionTimePointsTreatmentTimes(sm, columns, tr);
				timePontsColumns.forEach((tpc) -> {
					if (map.containsKey(tpc.getCount()) && map.get(tpc.getCount()) > tpc.getNoOfVacutainers()) {
						map.put(tpc.getCount(), tpc.getNoOfVacutainers());
					} else
						map.put(tpc.getCount(), tpc.getNoOfVacutainers());
				});
				rows.put(tr.getId(), timePontsColumns);
			}
			for (TreatmentInfo tre : treatments) {
				List<SampleCollectionDashBoardColumns> timePontsColumns = rows.get(tre.getId());
				timePontsColumns.forEach((tpc) -> {
					if (map.containsKey(tpc.getCount()))
						tpc.setNoOfVacutainers(map.get(tpc.getCount()));
				});
				Collections.sort(timePontsColumns);
				head.add(new SampleCollectionDashBoard("Time Point", tre.getTreatmentName(), timePontsColumns));
			}

		} else {
			List<SampleTimePoints> timePoints = clinicalDao.sampleTimePoints(sm);
			List<SampleCollectionDashBoardColumns> result = new ArrayList<>();
			timePoints.forEach((tp) -> {
				result.add(new SampleCollectionDashBoardColumns(tp.getTimePointNo(), tp.getTimePoint(),
						tp.getNoOfVacutainer()));
			});
			Collections.sort(result);
			head.add(new SampleCollectionDashBoard("Time Point", result));
		}
		head.add(new SampleCollectionDashBoard("Subject No", columns));

		return head;
	}

	@Override
	public List<SampleTimePointsData> sampleTimePointsDataOfPeriod(Long periodId, StudyMaster sm) {
		SimpleDateFormat timeFormat = new SimpleDateFormat(environment.getRequiredProperty("timeFormat"));
		List<SampleTimePointsData> list = new ArrayList<>();
		Map<String, List<SubjectSampleCollectionTimePoints>> allSubjectTimePoints = clinicalDao
				.allSubjectTimePoints(periodId);
		allSubjectTimePoints.forEach((k, v) -> {
			SampleTimePointsData data = new SampleTimePointsData();
			data.setSubjectNo(k);
			List<TimePointInfo> timePointInfo = new ArrayList<>();
			v.stream().forEach((timepoint) -> {
				TimePointInfo tpf = new TimePointInfo();
				tpf.setId(timepoint.getSubjectSampleCollectionTimePointId());
				if (timepoint.getScheduleTime() != null && !timepoint.getScheduleTime().equals("")) {
					tpf.setScheduleTime(timepoint.getScheduleTime());
				}
				if (timepoint.getSubjectSampleCollectionTimePointsData() != null) {
					tpf.setCollected(
							timeFormat.format(timepoint.getSubjectSampleCollectionTimePointsData().getActualtime()));
					if (timepoint.getSubjectSampleCollectionTimePointsData().getDiviationMessage() != null)
						tpf.setColor("red");
				}
				timePointInfo.add(tpf);
			});
			data.setTimePointInfo(timePointInfo);
			list.add(data);
		});
		return list;
	}

	@Override
	public List<SampleTimePointsData> dosingTimePointsOfPeriodHeading(StudyMaster sm) {
		List<SampleTimePointsData> list = new ArrayList<>();
		SampleTimePointsData orders = new SampleTimePointsData();
		orders.setSubjectNo("Order No");
		list.add(orders);
		Set<Integer> ord = new HashSet<>();
		if (sm.isTreatmentWiseNoOfDosing()) {
			List<TreatmentInfo> treatments = clinicalDao.treatmentInfo(sm);
			for (TreatmentInfo tr : treatments) {
				SampleTimePointsData timePoint = new SampleTimePointsData();
				timePoint.setSubjectNo("Time Point");
				timePoint.setTreatmentName(tr.getTreatmentName());
				List<DoseTimePoints> timePoints = clinicalDao.doseTimePoints(tr);
				List<String> times = new ArrayList<>();
				timePoints.stream().forEach((doseTimePoint) -> {
					times.add(doseTimePoint.getTimePoint());
					ord.add(doseTimePoint.getTimePointNo());
				});
				timePoint.setTimePoint(times);
				list.add(timePoint);
			}
		} else {
			SampleTimePointsData timePoint = new SampleTimePointsData();
			timePoint.setSubjectNo("Time Point");
			timePoint.setTreatmentName("");
			List<DoseTimePoints> timePoints = clinicalDao.doseTimePoints(sm);
			List<String> times = new ArrayList<>();
			timePoints.stream().forEach((sampleTimePoint) -> {
				times.add(sampleTimePoint.getTimePoint());
				ord.add(sampleTimePoint.getTimePointNo());
			});
			timePoint.setTimePoint(times);
			list.add(timePoint);
		}
		List<String> o = new ArrayList<>();
		ord.stream().forEachOrdered((or) -> o.add(or.toString()));
		orders.setTimePoint(o);

		SampleTimePointsData subno = new SampleTimePointsData();
		subno.setSubjectNo("Subject No");
		subno.setTimePoint(o);
		list.add(subno);

		return list;
	}

	@Override
	public Map<Integer, String> dosingSubjectHeading(StudyMaster sm) {
		Map<Integer, String> hedding = new TreeMap<>();
		List<Volunteer> vols = clinicalDao.studyVolunters(sm);
		vols.stream().forEach((vol) -> {
			hedding.put(vol.getSeqNo(), vol.getSubjectNo());
		});
		return hedding;
	}

	@Override
	public List<SampleTimePointsData> doseTimePointsDataOfPeriod(Long periodId, StudyMaster sm) {
		List<SampleTimePointsData> list = new ArrayList<>();
		/*
		 * Map<String, List<SubjectDoseTimePoints>> allSubjectTimePoints =
		 * clinicalDao.allSubjectDoseTimePoints(periodId);
		 * allSubjectTimePoints.forEach((k, v) -> { SampleTimePointsData data = new
		 * SampleTimePointsData(); data.setSubjectNo(k); List<TimePointInfo>
		 * timePointInfo = new ArrayList<>(); v.stream().forEach((timepoint) -> {
		 * TimePointInfo tpf = new TimePointInfo(); tpf.setId(timepoint.getId()); if
		 * (timepoint.getScheduleTime() != null &&
		 * !timepoint.getScheduleTime().equals("")) {
		 * tpf.setScheduleTime(timepoint.getScheduleTime()); } if
		 * (timepoint.getActualtime() != null) {
		 * tpf.setCollected(timepoint.getActualtime()); //
		 * if(timepoint.getDiviationMessage() != null) // tpf.setColor("red"); } if
		 * (timepoint.getSupervisedBy() == null) { tpf.setColor("red");
		 * tpf.setRequiredReview(true); tpf.setReviewUrlId(timepoint.getId()); }
		 * timePointInfo.add(tpf); }); data.setTimePointInfo(timePointInfo);
		 * list.add(data); });
		 */
		return list;
	}

	@Override
	public List<SampleTimePointsData> sampleSeparationTimePointsDataOfPeriod(Long periodId, StudyMaster sm) {
		SimpleDateFormat timeFormat = new SimpleDateFormat(environment.getRequiredProperty("timeFormat"));
		List<SampleTimePointsData> list = new ArrayList<>();
		Map<String, List<SubjectSampleCollectionTimePoints>> allSubjectTimePoints = clinicalDao
				.allSubjectTimePoints(periodId);
		allSubjectTimePoints.forEach((k, v) -> {
			SampleTimePointsData data = new SampleTimePointsData();
			data.setSubjectNo(k);
			List<TimePointInfo> timePointInfo = new ArrayList<>();
			v.stream().forEach((timepoint) -> {
				TimePointInfo tpf = new TimePointInfo();
				tpf.setId(timepoint.getSubjectSampleCollectionTimePointId());
				if (timepoint.getScheduleTime() != null && !timepoint.getScheduleTime().equals("")) {
					tpf.setScheduleTime(timepoint.getScheduleTime());
				}
				if (timepoint.getSubjectSampleCollectionTimePointsData() != null
						&& timepoint.getSubjectSampleCollectionTimePointsData().getVialActualTime() != null) {
					tpf.setCollected(timeFormat
							.format(timepoint.getSubjectSampleCollectionTimePointsData().getVialActualTime()));
				}
				timePointInfo.add(tpf);
			});
			data.setTimePointInfo(timePointInfo);
			list.add(data);
		});
		return list;
	}

	@Override
	public List<SampleTimePointsData> mealTimePointsDataOfPeriodHeading(StudyMaster sm) {
		List<SampleTimePointsData> list = new ArrayList<>();
		SampleTimePointsData orders = new SampleTimePointsData();
		orders.setSubjectNo("Order No");
		list.add(orders);
		Set<Integer> ord = new HashSet<>();
		if (sm.isTreatmentWiseMealsTimepoints()) {
			List<TreatmentInfo> treatments = clinicalDao.treatmentInfo(sm);
			for (TreatmentInfo tr : treatments) {
				SampleTimePointsData timePoint = new SampleTimePointsData();
				timePoint.setSubjectNo("Time Point");
				timePoint.setTreatmentName(tr.getTreatmentName());
				List<MealsTimePoints> timePoints = clinicalDao.mealsTimePoints(tr);
				List<String> times = new ArrayList<>();
				timePoints.stream().forEach((sampleTimePoint) -> {
					times.add(sampleTimePoint.getTimePoint() + " " + sampleTimePoint.getMealsType().getFieldValue());
					ord.add(sampleTimePoint.getTimePointNo());
				});
				timePoint.setTimePoint(times);
				list.add(timePoint);
			}
		} else {
			SampleTimePointsData timePoint = new SampleTimePointsData();
			timePoint.setSubjectNo("Time Point");
			timePoint.setTreatmentName("");
			List<MealsTimePoints> timePoints = clinicalDao.mealsTimePoints(sm);
			List<String> times = new ArrayList<>();
			timePoints.stream().forEach((sampleTimePoint) -> {
				times.add(sampleTimePoint.getSign() + sampleTimePoint.getTimePoint() + " "
						+ sampleTimePoint.getMealsType().getFieldValue());
				ord.add(sampleTimePoint.getTimePointNo());
			});
			timePoint.setTimePoint(times);
			list.add(timePoint);
		}
		List<String> o = new ArrayList<>();
		ord.stream().forEachOrdered((or) -> o.add(or.toString()));
		orders.setTimePoint(o);

		SampleTimePointsData subno = new SampleTimePointsData();
		subno.setSubjectNo("Subject No");
		subno.setTimePoint(o);
		list.add(subno);

		return list;
	}

	@Override
	public List<SampleTimePointsData> mealTimePointsDataOfPeriod(Long periodId, StudyMaster sm) {
		/*
		 * List<SampleTimePointsData> list = new ArrayList<>(); Map<String,
		 * List<SubjectMealsTimePoints>> allSubjectTimePoints =
		 * clinicalDao.allSubjectMealPoints(periodId); allSubjectTimePoints.forEach((k,
		 * v) -> { SampleTimePointsData data = new SampleTimePointsData();
		 * data.setSubjectNo(k); List<TimePointInfo> timePointInfo = new ArrayList<>();
		 * v.stream().forEach((timepoint) -> { TimePointInfo tpf = new TimePointInfo();
		 * tpf.setId(timepoint.getId() + ""); if (timepoint.getScheduleTime() != null &&
		 * !timepoint.getScheduleTime().equals("")) {
		 * tpf.setScheduleTime(timepoint.getScheduleTime()); } if
		 * (timepoint.getSubjectMealsTimePointsData() != null &&
		 * timepoint.getSubjectMealsTimePointsData().getActualtime() != null) {
		 * tpf.setStart(timepoint.getSubjectMealsTimePointsData().getStartTime());
		 * tpf.setEnd(timepoint.getSubjectMealsTimePointsData().getEndTime()); }
		 * timePointInfo.add(tpf); }); data.setTimePointInfo(timePointInfo);
		 * list.add(data); }); return list;
		 */
		return null;
	}

	@Override
	public List<SampleTimePointsData> ecgTimePointsDataOfPeriodHeading(StudyMaster sm) {
		List<SampleTimePointsData> list = new ArrayList<>();
		SampleTimePointsData orders = new SampleTimePointsData();
		orders.setSubjectNo("Order No");
		list.add(orders);
		Set<Integer> ord = new HashSet<>();
		if (sm.isTreatmentWiseEcgTimepoints()) {
			List<TreatmentInfo> treatments = clinicalDao.treatmentInfo(sm);
			for (TreatmentInfo tr : treatments) {
				SampleTimePointsData timePoint = new SampleTimePointsData();
				timePoint.setSubjectNo("Time Point");
				timePoint.setTreatmentName(tr.getTreatmentName());
				List<ECGTimePoints> timePoints = clinicalDao.ecgTimePoints(tr);
				List<String> times = new ArrayList<>();
				timePoints.stream().forEach((sampleTimePoint) -> {
					times.add(sampleTimePoint.getTimePoint());
					ord.add(sampleTimePoint.getTimePointNo());
				});
				timePoint.setTimePoint(times);
				list.add(timePoint);
			}
		} else {
			SampleTimePointsData timePoint = new SampleTimePointsData();
			timePoint.setSubjectNo("Time Point");
			timePoint.setTreatmentName("");
			List<ECGTimePoints> timePoints = clinicalDao.ecgTimePoints(sm);
			List<String> times = new ArrayList<>();
			timePoints.stream().forEach((sampleTimePoint) -> {
				times.add(sampleTimePoint.getSign() + sampleTimePoint.getTimePoint());
				ord.add(sampleTimePoint.getTimePointNo());
			});
			timePoint.setTimePoint(times);
			list.add(timePoint);
		}
		List<String> o = new ArrayList<>();
		ord.stream().forEachOrdered((or) -> o.add(or.toString()));
		orders.setTimePoint(o);

		SampleTimePointsData subno = new SampleTimePointsData();
		subno.setSubjectNo("Subject No");
		subno.setTimePoint(o);
		list.add(subno);

		return list;
	}

	@Override
	public List<SampleTimePointsData> ecgTimePointsDataOfPeriod(Long periodId, StudyMaster sm) {
		List<SampleTimePointsData> list = new ArrayList<>();
		Map<String, List<SubjectECGTimePoints>> allSubjectTimePoints = clinicalDao.allSubjectEcgPoints(periodId);
		allSubjectTimePoints.forEach((k, v) -> {
			SampleTimePointsData data = new SampleTimePointsData();
			data.setSubjectNo(k);
			List<TimePointInfo> timePointInfo = new ArrayList<>();
			v.stream().forEach((timepoint) -> {
				TimePointInfo tpf = new TimePointInfo();
				tpf.setId(timepoint.getId() + "");
				if (timepoint.getScheduleTime() != null && !timepoint.getScheduleTime().equals("")) {
					tpf.setScheduleTime(timepoint.getScheduleTime());
				}
				if (timepoint.getSubjectECGTimePointsData() != null
						&& timepoint.getSubjectECGTimePointsData().getActualDate() != null) {
					tpf.setStart(timepoint.getSubjectECGTimePointsData().getStartTime());
					tpf.setEnd(timepoint.getSubjectECGTimePointsData().getEndTime());
				}
				timePointInfo.add(tpf);
			});
			data.setTimePointInfo(timePointInfo);
			list.add(data);
		});
		return list;
	}

	@Override
	public List<SampleTimePointsData> vitalTimePointsDataOfPeriodHeading(StudyMaster sm) {
		List<SampleTimePointsData> list = new ArrayList<>();
		SampleTimePointsData orders = new SampleTimePointsData();
		orders.setSubjectNo("Order No");
		list.add(orders);
		Set<Integer> ord = new HashSet<>();
		if (sm.isTreatmentWiseMealsTimepoints()) {
			List<TreatmentInfo> treatments = clinicalDao.treatmentInfo(sm);
			for (TreatmentInfo tr : treatments) {
				SampleTimePointsData timePoint = new SampleTimePointsData();
				timePoint.setSubjectNo("Time Point");
				timePoint.setTreatmentName(tr.getTreatmentName());
				List<VitalTimePoints> timePoints = clinicalDao.vitalTimePoints(tr);
				List<String> times = new ArrayList<>();
				timePoints.stream().forEach((sampleTimePoint) -> {
					times.add(sampleTimePoint.getTimePoint());
					ord.add(sampleTimePoint.getTimePointNo());
				});
				timePoint.setTimePoint(times);
				list.add(timePoint);
			}
		} else {
			SampleTimePointsData timePoint = new SampleTimePointsData();
			timePoint.setSubjectNo("Time Point");
			timePoint.setTreatmentName("");
			List<VitalTimePoints> timePoints = clinicalDao.vitalTimePoints(sm);
			List<String> times = new ArrayList<>();
			timePoints.stream().forEach((sampleTimePoint) -> {
				times.add(sampleTimePoint.getSign() + sampleTimePoint.getTimePoint());
				ord.add(sampleTimePoint.getTimePointNo());
			});
			timePoint.setTimePoint(times);
			list.add(timePoint);
		}
		List<String> o = new ArrayList<>();
		ord.stream().forEachOrdered((or) -> o.add(or.toString()));
		orders.setTimePoint(o);

		SampleTimePointsData subno = new SampleTimePointsData();
		subno.setSubjectNo("Subject No");
		subno.setTimePoint(o);
		list.add(subno);

		return list;
	}

	@Override
	public List<SampleTimePointsData> vitalTimePointsDataOfPeriod(Long periodId, StudyMaster sm) {
		List<SampleTimePointsData> list = new ArrayList<>();
//		Map<String, List<SubjectVitalTimePoints>> allSubjectTimePoints = clinicalDao.allSubjectVitalPoints(periodId);
//		allSubjectTimePoints.forEach((k, v) -> {
//			SampleTimePointsData data = new SampleTimePointsData();
//			data.setSubjectNo(k);
//			List<TimePointInfo> timePointInfo = new ArrayList<>();
//			v.stream().forEach((timepoint) -> {
//				TimePointInfo tpf = new TimePointInfo();
//				tpf.setId(timepoint.getId() + "");
//				if (timepoint.getScheduleTime() != null && !timepoint.getScheduleTime().equals("")) {
//					tpf.setScheduleTime(timepoint.getScheduleTime());
//				}
//				if (timepoint.getSubjectVitalTimePointsData() != null
//						&& timepoint.getSubjectVitalTimePointsData().getActualtime() != null) {
//					tpf.setStart(timepoint.getSubjectVitalTimePointsData().getSubjectScanTime());
//					tpf.setEnd(timepoint.getSubjectVitalTimePointsData().getSubmissionTime());
//				}
//				timePointInfo.add(tpf);
//			});
//			data.setTimePointInfo(timePointInfo);
//			list.add(data);
//		});
		return list;
	}

	@Override
	public List<SubjectDoseDashBord> doseTimePointsDataOfPeriodWithSuperviceStatus(Long periodid, StudyMaster sm,
			Map<Integer, String> heading) {
		// TODO Auto-generated method stub
		List<SubjectDoseDashBord> list = new ArrayList<>();
		if (sm.isTreatmentWiseNoOfDosing()) {
			List<TreatmentInfo> treatments = clinicalDao.treatmentInfo(sm);
			treatments.stream().forEach((tr) -> {
				List<DoseTimePoints> doseTimePoints = clinicalDao.doseTimePoints(tr);
				doseTimePoints.stream().forEach((timepoint) -> {
					SubjectDoseDashBord sddb = new SubjectDoseDashBord();
					sddb.setTimepoint(timepoint.getTimePoint());
					sddb.setTimepoint(tr.getTreatmentName());
					List<SubjectDoseTimePoints> subjectDoseTimePoints = clinicalDao.subjectDoseTimePoints(timepoint,
							periodid);
					Map<Integer, SubjectDoseTimePoints> map = new TreeMap<>();
					subjectDoseTimePoints.stream().forEach((stimePoint) -> {
//						map.put(stimePoint.getSubjectOrder(), stimePoint);
					});

					heading.forEach((k, v) -> {
						if (!map.containsKey(k)) {
							map.put(k, null);
						}
					});
					sddb.setTimePointSubjectData(map);
					list.add(sddb);
				});
			});
		} else {
			List<DoseTimePoints> doseTimePoints = clinicalDao.doseTimePoints(sm);
			doseTimePoints.stream().forEach((timepoint) -> {
				SubjectDoseDashBord sddb = new SubjectDoseDashBord();
				sddb.setTimepoint(timepoint.getTimePoint());
				List<SubjectDoseTimePoints> subjectDoseTimePoints = clinicalDao.subjectDoseTimePoints(timepoint,
						periodid);
				Map<Integer, SubjectDoseTimePoints> map = new HashMap<>();
				subjectDoseTimePoints.stream().forEach((stimePoint) -> {
//					map.put(stimePoint.getSubjectOrder(), stimePoint);
				});
				heading.forEach((k, v) -> {
					if (!map.containsKey(k)) {
						map.put(k, null);
					}
				});
				sddb.setTimePointSubjectData(map);
				list.add(sddb);
			});
		}
		return list;
	}

	@Override
	public SubjectDoseTimePoints saveDoseSuperviseInfoTest(UserMaster user, String id) {
		SubjectDoseTimePoints subjectDoseTimePoints = clinicalDao.subjectDoseTimePoints(Long.parseLong(id));
		subjectDoseTimePoints.setSupervisedBy(user);
		subjectDoseTimePoints.setSupervisedOn(new Date());
		clinicalDao.updateSubjectDoseTimePoints(subjectDoseTimePoints);
		return subjectDoseTimePoints;
	}

	@Override
	public List<SubejectDosePerameter> subejectDosePerameter(SubjectDoseTimePoints v) {
		// TODO Auto-generated method stub
		return clinicalDao.subejectDosePerameter(v);
	}

	@Override
	public String centrifugeInstumentInfo(String barcode) {
		// TODO Auto-generated method stub
		Centrifugation cen = clinicalDao.centrifugationWithBarcode(barcode);
		StringBuilder sb = new StringBuilder();
		sb.append("0--").append(cen.getId()).append("--");
		sb.append("Name : ").append(cen.getName()).append("\n");
		sb.append("Code : ").append(cen.getCode()).append("\n");
		sb.append("Details : ").append(cen.getInsturmentDesc());
		return sb.toString();
	}

	@Override
	public String centrifugeVacutainerInfo(String barcode, String studyId) {
		SubjectSampleCollectionTimePoints sstp = clinicalDao.subjectSampleCollectionTimePoints(barcode);
		SamplesCentrifugation scenf = clinicalDao.samplesCentrifugation(sstp.getStudy());
		StringBuilder sb = new StringBuilder();
		sb.append("0--");
		sb.append("<tr id=\"" + barcode + "\"><td>").append(sstp.getStudy().getProjectNo()).append("</td><td>")
				.append(sstp.getPeriod().getPeriodNo()).append("</td><td>").append(sstp.getSubjectNo())
				.append("</td><td>");
		sb.append(sstp.getTimePoint()).append("(").append(sstp.getVacutainer()).append(")");
		sb.append("</td><td>").append(
				"<input type='button' name='centrifugationVacutinerButton' value='Remove' onclick=\"removeVacutiner('"
						+ barcode + "')\" />")
				.append("</td></tr>");
		sb.append("--").append(scenf.getSpeed()).append("--").append(scenf.getProcessTime()).append("--")
				.append(scenf.getTemperature()).append("--").append(scenf.getId()).append("--")
				.append(sstp.getPeriod().getId());
		return sb.toString();
	}

	@Override
	public String saveSample(StudyMaster sm, Date date, Long userId, String subject, String subjectTime,
			String vacutainer, String vacutainerTime, String timePoint, String collectionTimeWithSec,
			String collectionTime, String modeOfCollection, String message, String deviationStatus,
			String deviationStatusMsg) {
		SimpleDateFormat sdf = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
		SimpleDateFormat dateTimeFormatSeconds = new SimpleDateFormat(
				environment.getRequiredProperty("dateTimeFormatSeconds"));
		SimpleDateFormat timeFormat = new SimpleDateFormat(environment.getRequiredProperty("timeFormat"));
		String currnetdate = sdf.format(new Date());

		
		 /* try { UserMaster user = userDao.findById(userId);
		  SubjectSampleCollectionTimePoints vac =
		  clinicalDao.subjectSampleCollectionTimePoints(vacutainer);
		  SubjectSampleCollectionTimePointsData data = new
		  SubjectSampleCollectionTimePointsData(); try {
		  data.setSubjectScanTime(dateTimeFormatSeconds.parse(currnetdate + " " +
		  subjectTime)); } catch (ParseException e1) { // TODO Auto-generated catch
		  block e1.printStackTrace(); data.setSubjectScanTime(new Date()); } try {
		  data.setVacutinerScanTime(dateTimeFormatSeconds.parse(currnetdate + " " +
		  vacutainerTime)); } catch (ParseException e1) { // TODO Auto-generated catch
		  block e1.printStackTrace(); data.setVacutinerScanTime(new Date()); } try {
		  data.setCollectionTime(dateTimeFormatSeconds.parse(currnetdate + " " +
		  collectionTimeWithSec)); } catch (ParseException e1) { // TODO Auto-generated
		  catch block e1.printStackTrace(); data.setCollectionTime(new Date()); } try {
		  data.setActualtime(dateTimeFormatSeconds.parse(currnetdate + " " +
		  collectionTimeWithSec)); } catch (ParseException e1) { // TODO Auto-generated
		  catch block e1.printStackTrace(); data.setSubjectScanTime(new Date()); } try
		  { data.setSubjectScanTime(dateTimeFormatSeconds.parse(currnetdate + " " +
		  subjectTime)); } catch (ParseException e1) { // TODO Auto-generated catch
		  block e1.printStackTrace(); data.setSubjectScanTime(new Date()); }
		  
		  data.setModeOfCollection(modeOfCollection); data.setMessage(message);
		  
		  data.setCollectedBy(user); if (deviationStatus.equals(1)) { Long id =
		  Long.parseLong(deviationStatusMsg);
		  data.setDiviationMessage(clinicalDao.fromStaticData(id));
		  }
		  data.setSubmissionTime(date); if
		  (!vac.getTimePointType().getCode().equals(StaticData.PREDOSE.toString())) {
		  SimpleDateFormat csdf = new
		  SimpleDateFormat(environment.getRequiredProperty("clinicalDateTimeFormat"));
		  String sign = vac.getWindowPeriodSign(); Date scDate =
		  csdf.parse(vac.getScheduleDate() + " " + vac.getScheduleTime()); Date acDate
		  = data.getActualtime(); String calDeviation = deviation(scDate, acDate, sign,
		  vac.getWindowPeriod(), vac.getWindowPeriodType().getCode());
		  data.setDeviation(calDeviation); }
		  vac.setSubjectSampleCollectionTimePointsData(data);
		  clinicalDao.updateSubjectSampleCollectionTimePoints(vac);
		  
		  try { 
			  Map<String, String> s = new HashedMap(); 
			  s.put("id", vac.getSubjectSampleCollectionTimePointId()); 
			  s.put("actualTime",timeFormat.format(vac.getSubjectSampleCollectionTimePointsData().getActualtime())); 
			  if (vac.getSubjectSampleCollectionTimePointsData().getDeviation() != null && 
					  !vac.getSubjectSampleCollectionTimePointsData().getDeviation().trim().equals("")) 
				  s.put("color", "red"); 
			  else 
				  s.put("color", "blue"); 
			  String json = "";
			  try { 
				  json = new ObjectMapper().writeValueAsString(s); 
			  }catch (JsonProcessingException e1) { // TODO Auto-generated catch block
				  e1.printStackTrace(); 
			} 
		  Map<String, SseEmitter> map = TestWebSocket.sampleCollection; 
		  for (Map.Entry<String, SseEmitter> emitters : map.entrySet()) { 
			  SseEmitter emitter = emitters.getValue(); 
			  try {
				  emitter.send(SseEmitter.event().name("sampleCollection").data(json)); 
			  } catch (IOException e) { // TODO Auto-generated catch block
				  	TestWebSocket.sampleCollection.remove(user.getUsername()); } 
			  } 
		  } catch (Exception e) { 
//			  return "1"; 
		  }*/
		  
		return null;
	}
		  

	@Override
	public String saveSampleCollection(Long userId, SampleCollectoinDto scd, Date date) {
		String result = "Failed";
		UserMaster user = null;
		SampleTimePoints stp = null;
		StudyPeriodMaster spm = null;
		StudySubjects subject = null;
//		TreatmentInfo treatment = null;
		DoseTimePoints doseTimePont = null;
		SubjectDoseTimePoints subjecDoseInfo = null;
		StudyMaster study = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
			String[] vacutainerSplit = scd.getVacutainer().substring(0, scd.getVacutainer().length() - 1).split("a");
			String[] subjectSplit = scd.getSubject().substring(0, scd.getSubject().length() - 1).split("a");
			SampleCollectionSavingDto scsDto = clinicalDao.getSampleCollectionSavingDtoDetails(scd.getStudyId(), userId,
					subjectSplit[1], Long.parseLong(vacutainerSplit[1]), Long.parseLong(vacutainerSplit[1]),
					Long.parseLong(vacutainerSplit[4]));
			if (scsDto != null) {
				user = scsDto.getUser();
				stp = scsDto.getSampletp();
				spm = scsDto.getSpm();
				subject = scsDto.getSubject();
				study = scsDto.getStudy();
//				treatment = scsDto.getTreatment();
			}
			SubjectSampleCollectionTimePointsData data = new SubjectSampleCollectionTimePointsData();
			data.setSampleTimePoint(stp);
			data.setStudyPeriodMaster(spm);
			data.setSubject(subject);
			Date collectionDate = null;
			Date subjectScanTime = null;
			Date vacutainerScanTime = null;
            if(scd.getCollectionTime() != null && !scd.getCollectionTime().equals("")) {
                String crDate = sdf.format(new Date());
                crDate = crDate + " "+scd.getCollectionTime()+":00";
                collectionDate = sdf2.parse(crDate);
            }
            if(scd.getSubjectTime() != null && !scd.getSubjectTime().equals("")) {
                String crDate = sdf.format(new Date());
                crDate = crDate + " "+scd.getSubjectTime()+":00";
                subjectScanTime = sdf2.parse(crDate);
            }
            if(scd.getVacutainerTime() != null && !scd.getVacutainerTime().equals("")) {
                String crDate = sdf.format(new Date());
                crDate = crDate + " "+scd.getVacutainerTime()+":00";
                vacutainerScanTime = sdf2.parse(crDate);
            }
            data.setActualtime(collectionDate);
            data.setSubjectScanTime(subjectScanTime);
			data.setVacutinerScanTime(vacutainerScanTime);
		    data.setCollectedBy(user);
			data.setSubmissionTime(date);
			data.setDeviation(scd.getDeviationTime());
//			data.setSampleReason(scd.getSampleReason());
			data.setModeOfCollection(scd.getModeOfCollection());
			doseTimePont = clinicalDao.doseTimePoints(study.getId(), stp.getTreatmentInfo().getId());
			if (doseTimePont != null) {
				subjecDoseInfo = clinicalDao.subjectDoseTimePoints(doseTimePont, subject);
				if (subjecDoseInfo != null) {
					String timePoint = stp.getTimePoint();
					String[] hrmim = timePoint.split("\\.");
					int min = 0;
					
					if(hrmim.length > 0  && hrmim[0].trim().length() > 0) {
						min += Integer.parseInt(hrmim[0].trim()) * 60;	
					}
					
					if(hrmim.length > 1 && hrmim[1].trim().length() > 0) {
						min += ((Integer.parseInt(hrmim[1].trim()) * 6) / 100);	
					}
					
					Calendar c = Calendar.getInstance();
					c.setTime(subjecDoseInfo.getActualTime());
					c.add(Calendar.MINUTE, min);

					data.setScheduleTime(c.getTime());
				}
			}
			result = clinicalDao.saveSubjectSampleTimePoint(data, scd, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String appropriateBox(String barcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String subjectBarcodeInfoFroDose(String barcode, String sachet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveVitalCollection(Long userId, VitalCollectionDataDto vcDto, MessageSource messageSource,
			Locale currentLocale, Long languageId, String dateFormat) {
		String result = "Failed";
		StudyMaster study = null;
		StudyPeriodMaster period = null;
		UserMaster user = null;
		String[] subjectSplit = null;
		StudySubjects subject = null;
		VitalTimePoints vac = null;
		VitalCollectionSavingDto vcsDto = null;
		DoseTimePoints doseTimePoint = null;
		SubjectDoseTimePoints subjecDoseInfo = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			subjectSplit = vcDto.getSubject().substring(0, vcDto.getSubject().length() - 1).split("a");
			vcsDto = clinicalDao.getVitalCollectionSavingDtoDetails(Long.parseLong(subjectSplit[2]), subjectSplit[1],
					vcDto.getPerodId(), userId, vcDto.getTimePointPk());
			if (vcsDto != null) {
				study = vcsDto.getStudy();
				period = vcsDto.getPeriod();
				user = vcsDto.getUser();
				subject = vcsDto.getSubject();
				vac = vcsDto.getVac();
			}
			SubjectVitalTimePointsData data = new SubjectVitalTimePointsData();
			data.setTimepoint(vac);
			data.setPeriod(period);
			data.setSubject(subject);
			data.setSubmissionTime(new Date());
			data.setStudy(study);
			Date scannedDate = null;
			Date actualTime = null;
			Date startTime = null;
			Date endTime = null;
			String crDate = "";
            if(vcDto.getSubjectTime() != null && !vcDto.getSubjectTime().equals("")) {
            	crDate = sdf.format(new Date());
                crDate = crDate + " "+vcDto.getSubjectTime()+":00";
                scannedDate = sdf2.parse(crDate);
            }else {
            	 crDate = sdf.format(new Date());
            	 crDate = crDate + " 00:00:00";
                 scannedDate = sdf2.parse(crDate);
            }
            
           if(vcDto.getStartTime() != null && !vcDto.getStartTime().equals("")) {
        	    crDate = sdf.format(new Date());
                crDate = crDate + " "+vcDto.getStartTime()+":00";
                startTime = sdf2.parse(crDate);
            }else {
            	crDate = sdf.format(new Date());
            	crDate = crDate + " 00:00:00";
                startTime = sdf2.parse(crDate);
            }
            if(vcDto.getEndTime() != null && !vcDto.getEndTime().equals("")) {
                crDate = sdf.format(new Date());
                crDate = crDate + " "+vcDto.getEndTime()+":00";
                endTime = sdf2.parse(crDate);
            }else {
            	crDate = sdf.format(new Date());
            	crDate = crDate + " 00:00:00";
            	endTime = sdf2.parse(crDate);
            }
            data.setSubjectScanTime(scannedDate);
			data.setActualtime(actualTime);
			data.setStartTime(startTime);
			data.setEndTime(endTime);
			data.setSubmissionTime(new Date());
			data.setCollectedBy(user);
			data.setDeviation(vcDto.getTimeDeviationTime());
			data.setColltedPosition(vcDto.getPositionType());
			doseTimePoint = clinicalDao.doseTimePoints(study.getId(), vac.getTreatmentInfo().getId());
			if (doseTimePoint != null) {
				subjecDoseInfo = clinicalDao.subjectDoseTimePoints(doseTimePoint, subject);
				if (subjecDoseInfo != null) {
					String timePoint = vac.getTimePoint();
					String[] hrmim = timePoint.split("\\.");
					int min = Integer.parseInt(hrmim[0].trim()) * 60;
					min += ((Integer.parseInt(hrmim[1].trim()) * 6) / 100);
					Calendar c = Calendar.getInstance();
					c.setTime(subjecDoseInfo.getActualTime());
					c.add(Calendar.MINUTE, min);

					data.setScheduleTime(c.getTime());
				}
			}
			result = clinicalDao.saveSubjectVitalTimePoint(data, vcDto, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Instrument instumentInfo(Long insturmentId) {
		// TODO Auto-generated method stub
		return clinicalDao.instumentInfo(insturmentId);
	}

	@Override
	public CentrifugationDataMaster centrifugationDataMaster(Long centrifugeId) {
		// TODO Auto-generated method stub
		return clinicalDao.centrifugationDataMaster(centrifugeId);
	}

	@Override
	public SubjectDoseTimePoints saveDosingCollectionData(Long userId, Date date, DoseDataDto dto,
			MessageSource messageSource, Locale currentLocale, Long languageId, String dateFormat) {
		boolean flag = false;
		String result = "Failed";
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		DosingCollectionTimePointsDto dctpDto = null;
		StudyPeriodMaster period = null;
		UserMaster user = null;
		String[] subjectBarcodeSplit = null;
		String[] sachetBarcodeSplit = null;
		StudySubjects subject = null;
		StudySubjects replaceSubject = null;
		DoseTimePoints dosingTimePoint = null;
		long currentDoseId = 0;
		DoseTimePoints firstDose = null;
		SubjectDoseTimePoints sdtp = null;
		String replaceWith = "";
		DeviationMessage devMsg = null;
		SubjectDoseTimePoints sdpd = null;
		try {
			subjectBarcodeSplit = dto.getSubjectBarcode().substring(0, dto.getSubjectBarcode().length() - 1).split("a");
			sachetBarcodeSplit = dto.getSachetBarcode().substring(0, dto.getSachetBarcode().length() - 1).split("a");
			currentDoseId = Long.parseLong(sachetBarcodeSplit[4]);
			if (dto.isReplaceStatus())
				replaceWith = dto.getReplaceSubject();
			dctpDto = studyDao.getDosingCollectionData(dto.getPerodId(), userId, subjectBarcodeSplit[1], currentDoseId,
					subjectBarcodeSplit[2], replaceWith, dto.getDevationMsgId());
			if (dctpDto != null) {
				sdtp = dctpDto.getSdtp();
				period = dctpDto.getPeriod();
				user = dctpDto.getUser();
				subject = dctpDto.getSubject();
				dosingTimePoint = dctpDto.getDosingTimePoint();
				firstDose = dctpDto.getFirstDose();
				replaceSubject = dctpDto.getReplaceSubject();
				devMsg = dctpDto.getDevMsg();
			}
			if (replaceSubject != null) {
				subject.setStdSubjectId(replaceSubject);
				subject.setSubjectStatus("Replaced");
			}
			SubjectDoseTimePoints tech = new SubjectDoseTimePoints();
			tech.setDoseTimePoints(dosingTimePoint);
			tech.setStudySubjects(replaceSubject);
			tech.setActualTime(sdf.parse(sd.format(date) + " " + dto.getCollectionTime()));
			tech.setSachetScanTime(sdf.parse(sd.format(date) + " " + dto.getSachetBarcodeScanTime()));
			tech.setSubjectScanTime(sdf.parse(sd.format(date) + " " + dto.getSubjectBarcodeScanTime()));
			tech.setSubmissionTime(date);
			tech.setDeviationMsg(devMsg);
			tech.setDoseDoneBy(user);
			tech.setFastCriteraComments(dto.getFastCriteraComments());
			tech.setFeadCriteraComments(dto.getFeadCriteraComments());
			tech.setPeriod(period);
			tech.setCreatedBy(user);
			tech.setCreatedOn(new Date());
			tech.setStudySubjects(subject);
			tech.setDiviation(dto.getTimeDeviationTime());
			// Schedule time calculation for second dose onwards
			if (sdtp != null && sdtp.getDoseTimePoints().getId() != currentDoseId) {
				String[] firstDoseArr = firstDose.getTimePoint().split("\\.");
				String[] curDoseArr = dosingTimePoint.getTimePoint().split("\\.");
				int hrsdiff = Integer.parseInt(curDoseArr[0]) - Integer.parseInt(firstDoseArr[0]);
				int minDiff = Integer.parseInt(curDoseArr[1]) - Integer.parseInt(firstDoseArr[1]);
				Date doseDate = sdtp.getActualTime();
				SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
				String strDate = sdformat.format(doseDate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(doseDate);
				int totalHrs = 0;
				int totalMins = 0;
				if (hrsdiff > 24) {
					int days = hrsdiff / 24;
					totalHrs = hrsdiff % 24;
					Double doubleVal = Double.parseDouble("0." + minDiff);
					Double hours = (doubleVal * 60) / 1;
					String hoursStr = hours + "";
					if (hoursStr.contains(".")) {
						String[] hoursArr = (hoursStr).split("\\.");
						if (hoursArr.length > 0) {
							totalHrs = totalHrs + Integer.parseInt(hoursArr[0]);
							totalMins = Integer.parseInt(hoursArr[1]);
						}
					} else
						totalMins = Integer.parseInt(hoursStr);

					if (totalMins > 59) {
						int minquotient = totalMins / 60;
						int minremainder = totalMins % 60;
						totalHrs = totalHrs + minquotient;
						if (totalHrs > 24) {
							int days1 = totalHrs / 24;
							int hrs1 = totalHrs % 24;
							cal.add(Calendar.DATE, days + days1);
							tech.setScheduleTime(
									sdf.parse(sd.format(cal.getTime()) + " " + hrs1 + ":" + minremainder + ":" + "00"));
						} else {
							tech.setScheduleTime(
									sdf.parse(sd.format(strDate) + " " + totalHrs + ":" + minremainder + ":" + "00"));
						}
					} else {
						cal.add(Calendar.DATE, days);
						tech.setScheduleTime(
								sdf.parse(sd.format(cal.getTime()) + " " + totalHrs + ":" + totalMins + ":" + "00"));
					}
				} else {
					tech.setScheduleTime(
							sdf.parse(sd.format(cal.getTime()) + " " + hrsdiff + ":" + minDiff + ":" + "00"));
				}
			} else
				tech.setScheduleTime(tech.getActualTime());

			if (dto.isTimeDeviation())
				tech.setDiviation(dto.getTimeDeviationTime());
			else
				tech.setDiviation("");
			sdpd = clinicalDao.saveSubjectDoseTimePointsCollectionData(dto, tech, subject);
		} catch (Exception e) { // TODO: handle exception
			e.printStackTrace();
			return sdpd;
		}

		return sdpd;
	}

	@Override
	public List<CentrifugationDataMaster> sentrifugationDataMasters(Long studyId) {
		return clinicalDao.centrifugationDataMasterList(studyId);
	}

	@Override
	public StorageVacutinerDto periodWiseSampleTimePoitns(Long studyId) {
		// TODO Auto-generated method stub
		StorageVacutinerDto dto = new StorageVacutinerDto();
		dto.setPeriods(studyDao.studyPeriodMasterWithStudyId(studyId));
		dto.setStudy(studyDao.findByStudyId(studyId));
		List<SampleTimePoints> timePoints = clinicalDao.sampleTimePoints(dto.getStudy());
		List<SampleTimePoints> onlytimePoints = null;
		if (dto.getStudy().isTreatmentSpecificSampleTimepoints()) {
			onlytimePoints = timePoints;
		} else {
			List<TreatmentInfo> treatmentList = clinicalDao.treatmentInfo(dto.getStudy());
			for (TreatmentInfo treatment : treatmentList) {
				onlytimePoints = clinicalDao.sampleTimePoints(treatment);
				if (onlytimePoints.size() > 0) {
					break;
				}
			}

		}
		dto.setTimePoitns(timePoints);
		Collections.sort(onlytimePoints, new Comparator<SampleTimePoints>() {
			@Override
			public int compare(SampleTimePoints o1, SampleTimePoints o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.getTimePointNo(), o2.getTimePointNo());
			}
		});
		Map<String, List<Long>> timePointTimePointIdsMap = new HashMap<>();
		int maxVialNo = 0;
		Map<Long, String> timePointsMap = new HashMap<>();
		for (SampleTimePoints timepoint : timePoints) {
			if (maxVialNo < timepoint.getNoOfVials())
				maxVialNo = timepoint.getNoOfVials();
			timePointsMap.put(timepoint.getId(), timepoint.getSign() + timepoint.getTimePoint());
			List<Long> mp = timePointTimePointIdsMap.get(timepoint.getSign() + timepoint.getTimePoint());
			if (mp == null)
				mp = new ArrayList<>();
			mp.add(timepoint.getId());
			timePointTimePointIdsMap.put(timepoint.getSign() + timepoint.getTimePoint(), mp);
		}
		dto.setTimePointsMap(timePointsMap);
		dto.setOnlytimePoints(onlytimePoints);
		dto.setTimePointTimePointIdsMap(timePointTimePointIdsMap);

		for (int count = 1; count <= maxVialNo; count++) {
			dto.getVialNos().add(count);
		}
		List<Long> insIds = new ArrayList<>();
		List<Instrument> alldefreezers = clinicalDao.alldDefreezers("DEEPFREEZER");
		alldefreezers.forEach((depf) -> {
			insIds.add(depf.getId());
			dto.getDeepfreezers().put(depf.getId(), depf);
		});
		List<Deepfreezer> depfs = clinicalDao.deepfreezers(insIds);
		List<Long> rackIds = new ArrayList<>();
		depfs.forEach((depf) -> {
			StringBuffer sb = new StringBuffer();
			sb.append("07").append("a").append(depf.getId()).append("n");
			dto.getRacks().put(sb.toString(), depf);
			rackIds.add(depf.getId());
		});
		List<DeepfreezerShelf> shelfs = clinicalDao.deepfreezerShelfs(insIds);
		shelfs.forEach((depf) -> {
			StringBuffer sb = new StringBuffer();
			sb.append("09").append("a").append(depf.getId()).append("n");
			dto.getShelfs().put(sb.toString(), depf);
		});

		Map<Long, String> rackTimePointMap = new HashMap<>();
		rackIds.forEach((rack) -> {
			List<RackWithVials> vialRacks = clinicalDao.rackWithVials(rack);
			vialRacks.forEach((rv) -> {
				if (rv.getSampleTimePoint() != null) {
					rackTimePointMap.put(rv.getReack().getId(), rv.getSampleTimePoint());
				}
			});
			dto.getRackVials().put(rack, vialRacks);
		});
		dto.setRackTimePointMap(rackTimePointMap);

		return dto;
	}

	@Override
	public String vialRackSave(Long userId, VialRackDto vialRackDto, Date date, MessageSource messageSource,
			Locale currentLocale, Long languageId, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
		SimpleDateFormat timeFormata = new SimpleDateFormat(environment.getRequiredProperty("timeFormat"));
		SimpleDateFormat dateTimeSdf = new SimpleDateFormat(environment.getRequiredProperty("dateTimeFormat"));
		VilaRackDtoDetails vrdDto = null;
		UserMaster loginUser = null;
		List<String> barcodeVialDataData = null;
		String firstVal = "";
		String[] dataval = null;
//		Map<Long, SampleTimePoints> mapda = new HashMap<>();
		StudyPeriodMaster periodpojo = null;
		Deepfreezer deepreezer = null;
		StudyMaster study = null;
		List<Long> tpIds = new ArrayList<>();
		List<String> subNos = new ArrayList<>();
		List<StudySubjects> subjectsList = null;
		List<SampleTimePoints> sampList = null;
		Map<String, StudySubjects> subMap = new HashMap<>();
		Map<Long, SampleTimePoints> sampMap = new HashMap<>();
		try {
			List<String> vialDataList = vialRackDto.getVialDataList();
			for (String getdata : vialDataList) {
				String[] fechdata = getdata.split("\\_");
				String[] barsplit = fechdata[0].split("a");
				subNos.add(barsplit[3]);
				tpIds.add(Long.parseLong(barsplit[4]));

			}
			barcodeVialDataData = vialRackDto.getVialDataList();
			firstVal = barcodeVialDataData.get(0);
			if (firstVal != null && !firstVal.equals(""))
				dataval = firstVal.split("\\_");
//			 mapda = clinicalDao.getSampleTimePointsWithStudyForKeyVal(vialRackDto.getStudyId());
			String barcodesplit[] = dataval[0].split("a");
			vrdDto = clinicalDao.getVilaRackDtoDetails(vialRackDto.getStudyId(), userId, tpIds, subNos,
					vialRackDto.getRackId(), Long.parseLong(barcodesplit[1]));
			if (vrdDto != null) {
				loginUser = vrdDto.getUser();
				periodpojo = vrdDto.getPeriodpojo();
				deepreezer = vrdDto.getDeepreezer();
				study = vrdDto.getStudy();
				subjectsList = vrdDto.getSubjectsList();
				sampList = vrdDto.getSampList();
				if (subjectsList != null && subjectsList.size() > 0) {
					for (StudySubjects sub : subjectsList) {
						subMap.put(sub.getSubjectNo(), sub);
					}
				}
				if (sampList != null && sampList.size() > 0) {
					for (SampleTimePoints stp : sampList) {
						sampMap.put(stp.getId(), stp);
					}
				}
			}
			RackWithVials rwvpojo = new RackWithVials();
			rwvpojo.setPeriod(periodpojo);
			rwvpojo.setReack(deepreezer);
			rwvpojo.setStudy(study);
			rwvpojo.setVialNo(vialRackDto.getAliqut());
			rwvpojo.setSampleTimePoint(vialRackDto.getTimePoint());
//			rwvpojo.setTimepoint(timepoint);
			rwvpojo.setRackScanTime(dateTimeSdf.parse(sdf.format(date) + " " + vialRackDto.getRackScanTime()));
			rwvpojo.setCreatedBy(loginUser);
			rwvpojo.setCreatedOn(new Date());

			List<RackWithVitalSubject> rwvsList = new ArrayList<>();
			for (String getdata : vialDataList) {
				String[] fechdata = getdata.split("\\_");
				String scantime = fechdata[1];
				String[] barsplit = fechdata[0].split("a");
				StudySubjects subid = subMap.get(barsplit[3]);
				if (subid != null) {
					RackWithVitalSubject rwvspojo = new RackWithVitalSubject();
					rwvspojo.setRackWithVials(rwvpojo);
					rwvspojo.setScanTime(dateTimeSdf.parse(sdf.format(date) + " " + scantime));
					rwvspojo.setStudy(rwvpojo.getStudy());
					rwvspojo.setSubject(subid);
					rwvspojo.setTimePoint(sampMap.get(Long.parseLong(barsplit[4])));
					rwvspojo.setVialBarcode(fechdata[0]);
					rwvsList.add(rwvspojo);
				} else {
					return "{\"Success\": \"" + false + "\",\"Message\":\"Subject Not In Study\"}";
				}

			}
			boolean flag = clinicalDao.vialRackSave(rwvpojo, rwvsList);
			if (flag) {
				return "{\"Success\": \"" + true + "\",\"Message\":\"Vial Rack Collection saved successfully\"}";
			} else {
				return "{\"Success\": \"" + false + "\",\"Message\":\"Sample Collection Faild\"}";
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "{\"Success\": \"" + false + "\",\"Message\":\"Sample Collection Faild\"}";
		}
	}

	@Override
	public SegrigationDto separationVacutinerDto(Long studyId) {
		// TODO Auto-generated method stub
		SegrigationDto dto = new SegrigationDto();
		List<DeepfreezerShelf> depfs = clinicalDao.deepfreezersShellfAll();
		depfs.forEach((depf) -> {
			StringBuffer sb = new StringBuffer();
			sb.append("07").append("a").append(depf.getId()).append("n");
			dto.getShelfs().put(sb.toString(), depf);
		});
		List<RackWithVials> rackWithVials = clinicalDao.rackWithVialsWithStudyId(studyId);
		for (RackWithVials rwv : rackWithVials) {
			String key = rwv.getPeriod().getId() + "," + rwv.getVialNo();
			List<RackWithVials> tempList = dto.getRacks().get(key);
			if (tempList == null) {
				tempList = new ArrayList<>();
			}
			tempList.add(rwv);
			dto.getRacks().put(key, tempList);
			List<RackWithVitalSubject> subjects = clinicalDao.rackWithVitalSubject(rwv.getId());
			List<StudySubjects> subjctsList = new ArrayList<>();
			for (RackWithVitalSubject sub : subjects) {
				// key=periodId,treatmentId,timePointId,vialNo,subjetNo value=studysubject
				dto.getSubjects()
						.put(rwv.getPeriod().getId() + "," + sub.getTimePoint().getTreatmentInfo().getId() + ","
								+ sub.getTimePoint().getId() + "," + rwv.getVialNo() + ","
								+ sub.getSubject().getSubjectNo(), sub.getSubject());
				subjctsList.add(sub.getSubject());
			}
			dto.getRackWiseSubects().put(rwv.getId(), subjctsList);
		}
//
//		List<SubjectSampleCollectionTimePointsData> list = clinicalDao
//				.periodSubjectSampleCollectionTimePointsData(studyId);
//		for (SubjectSampleCollectionTimePointsData lis : list) {
//			StringBuffer sb = new StringBuffer();
//			sb.append("05");
//			sb.append("a");
//			sb.append(lis.getStudyPeriodMaster().getId());
//			sb.append("a");
//			sb.append(lis.getSampleTimePoint().getTreatmentInfo().getId());
//			sb.append("a");
//			sb.append(lis.getSubject().getSubjectNo());
//			sb.append("a");
//			sb.append(lis.getSampleTimePoint().getId());
//			sb.append("a");
//			int vials = lis.getSampleTimePoint().getNoOfVials();
//			for (int vial = 1; vial <= vials; vial++) {
//				if (vial < 10)
//					sb.append("0" + vial);
//				else
//					sb.append(vial);
//			}
//			sb.append("n");
//			dto.getVialdata().put(sb.toString(), lis);
//		}
		return dto;
	}

	@Override
	public List<String> noOfVials(Long studyId) {
		// TODO Auto-generated method stub
		int noOfVials = clinicalDao.noOfVials(studyId);
		List<String> list = new ArrayList<>();
		for (int i = 1; i <= noOfVials; i++) {
			list.add(i + "");
		}
		return list;
	}

	@Override
	public Map<Long, List<CentrifugationDataMaster>> centrifugationDataMasterAll(Long studyId) {
		// TODO Auto-generated method stub
		return clinicalDao.centrifugationDataMasterAll(studyId);
	}

	@Override
	public Map<String, String> getSubjectTreatmentWithStudyAndPeriod(Long studyId, List<StudySubjects> subjects) {
		Map<String, String> subjectTreatment = new HashMap<>();
		List<StudyTreatmentWiseSubjects> stwsList = clinicalDao.getStudyTreatmentWiseSubjectsWithStudy(studyId);
		for (StudyTreatmentWiseSubjects subjecttre : stwsList) {
			String[] arry = subjecttre.getSubjects().split(",");
			for (int i = 0; i < arry.length; i++) {
				subjectTreatment.put(arry[i] + subjecttre.getPeriod().getPeriodNo(),
						subjecttre.getTreatment().getTreatmentNo());
			}
		}
		return subjectTreatment;
	}

	@Override
	public SampleCollectionDtoDetails getSampleCollectionDtoDetails(Long studyId) {
		SampleCollectionDtoDetails scdd = null;
		CommonTimePointDto ctpDto = null;
		BloodSamplesCollectionDto bscDto = null;
		Map<Long, Map<Long, SampleTimePoints>> twSamplesMap = new HashMap<>();// treatment, sampleId, SampleTimePoints
//		Map<String, Map<Long, Map<Long, Map<Long, SubjectSampleCollectionTimePointsData>>>> sampColDataMap = new HashMap<>(); // SubjectNo, periodId, treatmentId,sampleId,SamplePojo
		Map<String, Map<Long, Map<Long, Map<Long, RealTimeCommunicationDto>>>> sampColDataMap = new HashMap<>(); // SubjectNo, periodId, treatmentId,sampleId,SamplePojo
		Map<Long, Integer> samplesOrderMap = new HashMap<>();// sampleId, orderNo
		Map<Long, SampleTimePoints> samplesMap = new HashMap<>(); // sampleId, SampleTimePointPojo
		Map<Integer, Long> orderSampleIdsMap = new HashMap<>(); // orderNo, SampleId
		Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap = null; // subjectNo, periodId, treatement,SubjectDosePojo
		List<SampleTimePoints> samplesList = null;
		List<SubjectSampleCollectionTimePointsData> sctpdList = null;
		Map<String, StudyPeriodMaster> stdPeriodMap = null;
		PlannedTimeDetailsDto ptdDto = null;
		try {
			ctpDto = getCommonDetails(studyId, "SAMPLES");
			if (ctpDto != null) {
				stdPeriodMap = ctpDto.getStdPeriodMap();
				dosedMap = ctpDto.getDosedMap();
			}
			bscDto = clinicalDao.getBloodSamplesCollectionDtoDetails(studyId, stdPeriodMap);
			if (bscDto != null) {
				samplesList = bscDto.getSamplesList();
				sctpdList = bscDto.getSctpdList();
			}
			ptdDto = clinicalDao.getPlannedTimeDetailsDtoDetails(studyId, ctpDto.getStdPeriodMap(), ctpDto.getStudy().getProjectNo());
			if(ptdDto != null) {
				if(ctpDto != null)
					ptdDto.setWashoutDays(ctpDto.getStudy().getWashoutPeriod());
			}
			Set<Long> sampleIds = new HashSet<>();
			Map<Long, SampleTimePoints> stpTemp = null;
			if (samplesList != null && samplesList.size() > 0) {
				for (SampleTimePoints stp : samplesList) {
					samplesMap.put(stp.getId(), stp);
					sampleIds.add(stp.getId());
					if (twSamplesMap.containsKey(stp.getTreatmentInfo().getId())) {
						stpTemp = twSamplesMap.get(stp.getTreatmentInfo().getId());
						stpTemp.put(stp.getId(), stp);
						twSamplesMap.put(stp.getTreatmentInfo().getId(), stpTemp);
					} else {
						stpTemp = new HashMap<>();
						stpTemp.put(stp.getId(), stp);
						twSamplesMap.put(stp.getTreatmentInfo().getId(), stpTemp);
					}
				}
			}
			if (sampleIds != null && sampleIds.size() > 0) {
				int count = 1;
				for (Long sid : sampleIds) {
					samplesOrderMap.put(sid, count);
					orderSampleIdsMap.put(count, sid);
					count++;
				}
			}
			/*Map<Long, Map<Long, Map<Long, SubjectSampleCollectionTimePointsData>>> periodSctpMap = null;
			Map<Long, Map<Long, SubjectSampleCollectionTimePointsData>> trSctpMap = null;
			Map<Long, SubjectSampleCollectionTimePointsData> sampIdSctpMap = null;*/
			Map<Long, Map<Long, Map<Long, RealTimeCommunicationDto>>> periodSctpMap = null;
			Map<Long, Map<Long, RealTimeCommunicationDto>> trSctpMap = null;
			Map<Long, RealTimeCommunicationDto> sampIdSctpMap = null;
			if (sctpdList != null && sctpdList.size() > 0) {
				for (SubjectSampleCollectionTimePointsData ssctp : sctpdList) {
					RealTimeCommunicationDto rcDto = new RealTimeCommunicationDto();
					rcDto.setPeriodId(ssctp.getStudyPeriodMaster().getId());
					rcDto.setSubjectNo(ssctp.getSubject().getSubjectNo());
					rcDto.setSubjectVitalId(ssctp.getId());
					rcDto.setTimePointId(ssctp.getSampleTimePoint().getId());
					rcDto.setTreatmentId(ssctp.getSampleTimePoint().getTreatmentInfo().getId());
					if (sampColDataMap.containsKey(ssctp.getSubject().getSubjectNo())) {
						periodSctpMap = sampColDataMap.get(ssctp.getSubject().getSubjectNo());
						if (periodSctpMap.containsKey(ssctp.getStudyPeriodMaster().getId())) {
							trSctpMap = periodSctpMap.get(ssctp.getStudyPeriodMaster().getId());
							if (trSctpMap.containsKey(ssctp.getSampleTimePoint().getTreatmentInfo().getId())) {
								sampIdSctpMap = trSctpMap.get(ssctp.getSampleTimePoint().getTreatmentInfo().getId());
								sampIdSctpMap.put(ssctp.getSampleTimePoint().getId(), rcDto);
								trSctpMap.put(ssctp.getSampleTimePoint().getTreatmentInfo().getId(), sampIdSctpMap);
								periodSctpMap.put(ssctp.getStudyPeriodMaster().getId(), trSctpMap);
								sampColDataMap.put(ssctp.getSubject().getSubjectNo(), periodSctpMap);
							} else {
								sampIdSctpMap = new HashMap<>();
								sampIdSctpMap.put(ssctp.getSampleTimePoint().getId(), rcDto);
								trSctpMap.put(ssctp.getSampleTimePoint().getTreatmentInfo().getId(), sampIdSctpMap);
								periodSctpMap.put(ssctp.getStudyPeriodMaster().getId(), trSctpMap);
								sampColDataMap.put(ssctp.getSubject().getSubjectNo(), periodSctpMap);
							}
						} else {
							sampIdSctpMap = new HashMap<>();
							trSctpMap = new HashMap<>();
							sampIdSctpMap.put(ssctp.getSampleTimePoint().getId(), rcDto);
							trSctpMap.put(ssctp.getSampleTimePoint().getTreatmentInfo().getId(), sampIdSctpMap);
							periodSctpMap.put(ssctp.getStudyPeriodMaster().getId(), trSctpMap);
							sampColDataMap.put(ssctp.getSubject().getSubjectNo(), periodSctpMap);
						}
					} else {
						sampIdSctpMap = new HashMap<>();
						trSctpMap = new HashMap<>();
						periodSctpMap = new HashMap<>();
						sampIdSctpMap.put(ssctp.getSampleTimePoint().getId(), rcDto);
						trSctpMap.put(ssctp.getSampleTimePoint().getTreatmentInfo().getId(), sampIdSctpMap);
						periodSctpMap.put(ssctp.getStudyPeriodMaster().getId(), trSctpMap);
						sampColDataMap.put(ssctp.getSubject().getSubjectNo(), periodSctpMap);
					}
				}
			}
			scdd = new SampleCollectionDtoDetails();
			scdd.setCtpDto(ctpDto);
			scdd.setTwSamplesMap(twSamplesMap);
			scdd.setSamplesMap(samplesMap);
			scdd.setSamplesOrderMap(samplesOrderMap);
			scdd.setSampColDataMap(sampColDataMap);
			scdd.setOrderSampleIdsMap(orderSampleIdsMap);
			scdd.setDosedMap(dosedMap);
			scdd.setPtdDto(ptdDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scdd;
	}

	private CommonTimePointDto getCommonDetails(Long studyId, String collectionType) {
		CommonTimePointDto ctpDto = null;
		CommonTpDetails ctpd = null;
		StudyMaster study = null;
		Map<String, StudySubjects> subMap = new HashMap<>(); // subjNo, studySubject
		Map<String, StudyPeriodMaster> stdPeriodMap = new HashMap<>(); // subjectNo, periodPojo
		Map<String, Map<Long, List<TreatmentInfo>>> subperiodwiseTreatMap = new HashMap<>(); // subjectNo, periodId,
																								// List treatmentPojo
		Map<String, StudySubjects> dropedSubMap = new HashMap<>(); // subjectNo, StudySubject
		Map<String, StudySubjects> replaceSubMap = new HashMap<>(); // subjectNo, StudySubject
		Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> pwdoseMap = null;
		Map<Long, TreatmentInfo> treatmentMap = new HashMap<>();
		List<StudySubjects> subList = null;
		List<SubjectRandamization> subRzList = null;
		List<TreatmentInfo> treatmentList = null;
		List<StudySubjectPeriods> studySubjectsPeriodList = null;
		Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap = null;
		List<String> dropSubList = null;
		DosingDto dsDto = null;
		Map<String, Map<Long, SubjectDoseTimePoints>> dosedWithoutTreatmentMap = null;// subjectNo, periodId,
																						// subjectDosePojo
		Map<String, SubjectWithdrawDetails> subWithdrawnMap = new HashMap<>();// SubjectNo, SubjectWithdrawDetails
		List<SubjectWithdrawDetails> swdrList = null;
		Map<Long, StudySubjects> subVolMap = new HashMap<>();// volId, StudySubjects
		Long minTreatmentId = null;
		List<StudyActivityDataDto> sadataDtoList = null;
		Map<Long, Map<Long, String>> subCannulaMap = new HashMap<>();//volId, periodId, Done
		Map<String, Long> subVolIdsMap = null;//Subject, volId
		Map<Long, Long> saActIdsMap = null;
		Map<Long, List<Long>> volSactIdsMap = new HashMap<>(); //volId, List of studyActivitiesId
		try {
			ctpd = clinicalDao.getBloodSampleCollectionDetails(studyId, collectionType);
			if (ctpd != null) {
				study = ctpd.getStudy();
				pwdoseMap = ctpd.getPwdoseMap();
				subList = ctpd.getSubList();
				subRzList = ctpd.getSubRzList();
				treatmentList = ctpd.getTreatmentList();
				studySubjectsPeriodList = ctpd.getStudySubjectsPeriodList();
				dosedMap = ctpd.getDosedMap();
				dropSubList = ctpd.getDropOutSubList();
				dsDto = ctpd.getDsDto();
				dosedWithoutTreatmentMap = ctpd.getDosedWithoutTreatmentMap();
				swdrList = ctpd.getSwdrList();
				sadataDtoList = ctpd.getSadataDtoList();
				saActIdsMap = ctpd.getSaActIdsMap();
				subVolIdsMap = ctpd.getSubVolIdsMap();
			}
			Map<Long, String> cannulaTempMap = null;
			List<Long> studyActIdsList = null;
			if(sadataDtoList != null && sadataDtoList.size() > 0) {
				for(StudyActivityDataDto sadDto : sadataDtoList) {
					if(volSactIdsMap.containsKey(sadDto.getVolunteerId())) {
						studyActIdsList = volSactIdsMap.get(sadDto.getVolunteerId());
						studyActIdsList.add(sadDto.getStudyActivityId());
						volSactIdsMap.put(sadDto.getVolunteerId(), studyActIdsList);
					}else{
						studyActIdsList = new ArrayList<>();
						studyActIdsList.add(sadDto.getStudyActivityId());
						volSactIdsMap.put(sadDto.getVolunteerId(), studyActIdsList);
					}
				}
			}
			if(subVolIdsMap != null && subVolIdsMap.size() > 0) {
				for(Map.Entry<String, Long> volMap : subVolIdsMap.entrySet()) {
					Long volId = volMap.getValue();
					List<Long> studyActIdList = volSactIdsMap.get(volId);
					if(studyActIdList != null && studyActIdList.size() > 0) {
						for(Long saId : studyActIdList) {
							Long periodId = null;
							if(saActIdsMap != null && saActIdsMap.size() > 0) {
								periodId = saActIdsMap.get(saId);
							}
							if(periodId != null) {
								if(subCannulaMap.containsKey(volId)) {
									cannulaTempMap = subCannulaMap.get(volId);
									cannulaTempMap.put(periodId, "Done");
									subCannulaMap.put(volId, cannulaTempMap);
								}else {
									cannulaTempMap = new HashMap<>();
									cannulaTempMap.put(periodId, "Done");
									subCannulaMap.put(volId, cannulaTempMap);
								}
							}
						}
					}
				}
			}
			if (subList != null && subList.size() > 0) {
				for (StudySubjects ss : subList) {
					if (ss.getStdSubjectId() != null) {
						if (ss.getStdSubjectId().getSubjectDiscontinue().equals("Yes"))
							dropedSubMap.put(ss.getStdSubjectId().getSubjectNo(), ss.getStdSubjectId());
						if (ss.getStdSubjectId().getSubjectReplace().equals("Yes"))
							replaceSubMap.put(ss.getStdSubjectId().getSubjectNo(), ss.getStdSubjectId());

//						subMap.put(ss.getStdSubjectId().getSubjectNo(), ss.getStdSubjectId());
						subMap.put(ss.getSubjectNo(), ss);
						subVolMap.put(ss.getReportingId().getId(), ss);
					} else {
						if (ss.getSubjectDiscontinue().equals("Yes"))
							dropedSubMap.put(ss.getSubjectNo(), ss);
						if (ss.getSubjectReplace().equals("Yes"))
							replaceSubMap.put(ss.getSubjectNo(), ss);
						subMap.put(ss.getSubjectNo(), ss);
						subVolMap.put(ss.getReportingId().getId(), ss);
					}

				}
			}
			if (studySubjectsPeriodList != null && studySubjectsPeriodList.size() > 0) {
				for (StudySubjectPeriods ssp : studySubjectsPeriodList) {
					stdPeriodMap.put(ssp.getSubject().getSubjectNo(), ssp.getPeriodId());
				}
			}
			if (subRzList != null && subRzList.size() > 0) {
				Map<Long, List<TreatmentInfo>> tpMap = null;
				List<TreatmentInfo> tinfList = null;
				for (SubjectRandamization srz : subRzList) {
					if (subperiodwiseTreatMap.containsKey(srz.getSubjectNo())) {
						tpMap = subperiodwiseTreatMap.get(srz.getSubjectNo());
						if (tpMap.containsKey(srz.getPeriod().getId())) {
							tinfList = tpMap.get(srz.getPeriod().getId());
							tinfList.add(srz.getTreatmentInfo());
							tpMap.put(srz.getPeriod().getId(), tinfList);
							subperiodwiseTreatMap.put(srz.getSubjectNo(), tpMap);
						} else {
							tinfList = new ArrayList<>();
							tinfList.add(srz.getTreatmentInfo());
							tpMap.put(srz.getPeriod().getId(), tinfList);
							subperiodwiseTreatMap.put(srz.getSubjectNo(), tpMap);
						}
					} else {
						tinfList = new ArrayList<>();
						tpMap = new HashMap<>();
						tinfList.add(srz.getTreatmentInfo());
						tpMap.put(srz.getPeriod().getId(), tinfList);
						subperiodwiseTreatMap.put(srz.getSubjectNo(), tpMap);
					}
				}
			}
			if (treatmentList != null && treatmentList.size() > 0) {
				for (TreatmentInfo tinf : treatmentList) {
					treatmentMap.put(tinf.getId(), tinf);
				}
			}
			if (treatmentMap.size() > 0)
				minTreatmentId = Collections.min(treatmentMap.keySet());
			if (swdrList != null && swdrList.size() > 0) {
				for (SubjectWithdrawDetails swd : swdrList) {
					if(subVolMap.get(swd.getStudyVolunteer().getId()) != null)
						subWithdrawnMap.put(subVolMap.get(swd.getStudyVolunteer().getId()).getSubjectNo(), swd);
				}
			}
			ctpDto = new CommonTimePointDto();
			ctpDto.setSubMap(subMap);
			ctpDto.setStudy(study);
			ctpDto.setPwdoseMap(pwdoseMap);
			ctpDto.setDropedSubMap(dropedSubMap);
			ctpDto.setReplaceSubMap(replaceSubMap);
			ctpDto.setStdPeriodMap(stdPeriodMap);
			ctpDto.setSubperiodwiseTreatMap(subperiodwiseTreatMap);
			ctpDto.setTreatmentMap(treatmentMap);
			ctpDto.setDosedMap(dosedMap);
			ctpDto.setDropOutSubList(dropSubList);
			ctpDto.setDsDto(dsDto);
			ctpDto.setDosedWithoutTreatmentMap(dosedWithoutTreatmentMap);
			ctpDto.setSubWithdrawnMap(subWithdrawnMap);
			ctpDto.setMinTreatmentId(minTreatmentId);
			ctpDto.setSubCannulaMap(subCannulaMap);
			ctpDto.setSubVolIdsMap(subVolIdsMap);
			StatusMaster approvedStatus = studyCreationDao.statusMaster(StudyStatus.APPROVED.toString());
			ctpDto.setPlannedDoseTime(dosingInfoDao.dosingInfo(study, approvedStatus.getId()));			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ctpDto;
	}

	@Override
	public VitalCollectionDetailsDto getVitalCollectionDetailsDtoDetails(Long studyId, Long languageId) {
		VitalCollectionDetailsDto vcdDto = null;
		CommonTimePointDto ctpDto = null;
		Map<Long, List<VitalTimePoints>> preDoseVtpMap = new HashMap<>(); // treatment, List of Vitals
		Map<Long, List<VitalTimePoints>> postDoseVtpMap = new HashMap<>(); // treatment, List of Vitals
		Map<Long, VitalTimePoints> vitalMap = new HashMap<>(); // vitalTpId, vitalTimePoint
//		Map<String, Map<Long, Map<Long, Map<Long, SubjectVitalTimePointsData>>>> svtpData = new HashMap<>(); // SubjectNo, periodId, treatmentId, vitalId, subjectVitaltimePointData
		Map<String, Map<Long, Map<Long, Map<Long, List<RealTimeCommunicationDto>>>>> svtpData = new HashMap<>(); // SubjectNo, periodId, treatmentId, vitalId, subjectVitaltimePointData
		Map<Long, GlobalparameterFromDto> parameterMap = null; //// vitaltpId, parameters
		Map<Long, Integer> previtalOrderMap = new HashMap<>();// VitalId, orderNo
		Map<Long, Integer> postvitalOrderMap = new HashMap<>();// VitalId, orderNo
		Map<Long, VitalTimePoints> preVitalsMap = new HashMap<>(); // vitalId, VitalPojo
		Map<Long, VitalTimePoints> postVitalsMap = new HashMap<>(); // vitalId, VitalPojo
		Map<Integer, Long> preorderVitalIdsMap = new HashMap<>(); // orderNo, SampleId
		Map<Integer, Long> postorderVitalIdsMap = new HashMap<>(); // orderNo, SampleId
		VitalTimePointsCollectionDto vtpcd = null;
		Map<String, StudyPeriodMaster> spmMap = null;
		List<VitalTimePoints> vtpList = null;
		List<SubjectVitalTimePointsData> svtpDataList = null;
		PlannedTimeDetailsDto ptdDto = null;
		String projectNo = "";
		try {
			ctpDto = getCommonDetails(studyId, "VITALS");
			if (ctpDto != null) {
				spmMap = ctpDto.getStdPeriodMap();
				projectNo = ctpDto.getStudy().getProjectNo();
			}

			vtpcd = clinicalDao.getVitalTimePointsCollectionDtoDetails(studyId, spmMap);
			ptdDto = clinicalDao.getPlannedTimeDetailsDtoDetails(studyId, spmMap, projectNo);
			if(ptdDto != null) {
				if(ctpDto != null)
					ptdDto.setWashoutDays(ctpDto.getStudy().getWashoutPeriod());
			}
			if (vtpcd != null) {
				vtpList = vtpcd.getVtpList();
				svtpDataList = vtpcd.getSvtpData(); 
			}
			parameterMap = getVitalGlobalParameters(languageId, vtpList);
			List<VitalTimePoints> vptTempList = null;
			Set<Long> preVitalIdsSet = new HashSet<>();
			Set<Long> postVitalIdsSet = new HashSet<>();
			if (vtpList != null && vtpList.size() > 0) {
				for (VitalTimePoints vtp : vtpList) {
					vitalMap.put(vtp.getId(), vtp);
					if (vtp.getSign() != null && !vtp.getSign().equals("")) {
						preVitalIdsSet.add(vtp.getId());
						if (preDoseVtpMap.containsKey(vtp.getTreatmentInfo().getId())) {
							vptTempList = preDoseVtpMap.get(vtp.getTreatmentInfo().getId());
							vptTempList.add(vtp);
							preDoseVtpMap.put(vtp.getTreatmentInfo().getId(), vptTempList);
						} else {
							vptTempList = new ArrayList<>();
							vptTempList.add(vtp);
							preDoseVtpMap.put(vtp.getTreatmentInfo().getId(), vptTempList);
						}
					} else {
						postVitalIdsSet.add(vtp.getId());
						if (postDoseVtpMap.containsKey(vtp.getTreatmentInfo().getId())) {
							vptTempList = postDoseVtpMap.get(vtp.getTreatmentInfo().getId());
							vptTempList.add(vtp);
							postDoseVtpMap.put(vtp.getTreatmentInfo().getId(), vptTempList);
						} else {
							vptTempList = new ArrayList<>();
							vptTempList.add(vtp);
							postDoseVtpMap.put(vtp.getTreatmentInfo().getId(), vptTempList);
						}
					}
				}
			}
			int preCount = 1;
			int postCount = 1;
			if (preVitalIdsSet != null && preVitalIdsSet.size() > 0) {
				for (Long pvtId : preVitalIdsSet) {
					preorderVitalIdsMap.put(preCount, pvtId);
					previtalOrderMap.put(pvtId, preCount);
					preVitalsMap.put(pvtId, vitalMap.get(pvtId));
					preCount++;
				}
			}
			if (postVitalIdsSet != null && postVitalIdsSet.size() > 0) {
				for (Long pvtId : postVitalIdsSet) {
					postorderVitalIdsMap.put(preCount, pvtId);
					postvitalOrderMap.put(pvtId, preCount);
					postVitalsMap.put(pvtId, vitalMap.get(pvtId));
					postCount++;
				}
			}

//			Map<Long, Map<Long, Map<Long, SubjectVitalTimePointsData>>> periodVtpMap = null;
//			Map<Long, Map<Long, SubjectVitalTimePointsData>> trVtppMap = null;
//			Map<Long, SubjectVitalTimePointsData> sampIdVtppMap = null;
			Map<Long, Map<Long, Map<Long, List<RealTimeCommunicationDto>>>> periodVtpMap = null;
			Map<Long, Map<Long, List<RealTimeCommunicationDto>>> trVtppMap = null;
			Map<Long, List<RealTimeCommunicationDto>> sampIdVtppMap = null;
			List<RealTimeCommunicationDto> rcdList = null;
			if (svtpDataList != null && svtpDataList.size() > 0) {
				for (SubjectVitalTimePointsData svtpd : svtpDataList) {
					RealTimeCommunicationDto vrtcDto = new RealTimeCommunicationDto();
					vrtcDto.setPeriodId(svtpd.getPeriod().getId());
					vrtcDto.setSubjectNo(svtpd.getSubject().getSubjectNo());
					vrtcDto.setSubjectVitalId(svtpd.getId());
					vrtcDto.setTimePointId(svtpd.getTimepoint().getId());
					vrtcDto.setTreatmentId(svtpd.getTimepoint().getTreatmentInfo().getId());
					vrtcDto.setCollectedPosition(svtpd.getColltedPosition());
					vrtcDto.setTimePoint(svtpd.getTimepoint().getTimePoint());
					if (svtpData.containsKey(svtpd.getSubject().getSubjectNo())) {
						periodVtpMap = svtpData.get(svtpd.getSubject().getSubjectNo());
						if (periodVtpMap.containsKey(svtpd.getPeriod().getId())) {
							trVtppMap = periodVtpMap.get(svtpd.getPeriod().getId());
							if (trVtppMap.containsKey(svtpd.getTimepoint().getTreatmentInfo().getId())) {
								sampIdVtppMap = trVtppMap.get(svtpd.getTimepoint().getTreatmentInfo().getId());
								if (!sampIdVtppMap.containsKey(svtpd.getTimepoint().getId())) {
									rcdList = new ArrayList<>();
									rcdList.add(vrtcDto);
									sampIdVtppMap.put(svtpd.getTimepoint().getId(), rcdList);
									trVtppMap.put(svtpd.getTimepoint().getTreatmentInfo().getId(), sampIdVtppMap);
									periodVtpMap.put(svtpd.getPeriod().getId(), trVtppMap);
									svtpData.put(svtpd.getSubject().getSubjectNo(), periodVtpMap);
								}else {
									rcdList = sampIdVtppMap.get(svtpd.getTimepoint().getId());
									rcdList.add(vrtcDto);
									sampIdVtppMap.put(svtpd.getTimepoint().getId(), rcdList);
									trVtppMap.put(svtpd.getTimepoint().getTreatmentInfo().getId(), sampIdVtppMap);
									periodVtpMap.put(svtpd.getPeriod().getId(), trVtppMap);
									svtpData.put(svtpd.getSubject().getSubjectNo(), periodVtpMap);
								}
							} else {
								trVtppMap = new HashMap<>();
								sampIdVtppMap = new HashMap<>();
								rcdList = new ArrayList<>();
								rcdList.add(vrtcDto);
								sampIdVtppMap.put(svtpd.getTimepoint().getId(), rcdList);
								trVtppMap.put(svtpd.getTimepoint().getTreatmentInfo().getId(), sampIdVtppMap);
								periodVtpMap.put(svtpd.getPeriod().getId(), trVtppMap);
								svtpData.put(svtpd.getSubject().getSubjectNo(), periodVtpMap);
							}
						} else {
							trVtppMap = new HashMap<>();
							sampIdVtppMap = new HashMap<>();
							rcdList = new ArrayList<>();
							rcdList.add(vrtcDto);
							sampIdVtppMap.put(svtpd.getTimepoint().getId(), rcdList);
							trVtppMap.put(svtpd.getTimepoint().getTreatmentInfo().getId(), sampIdVtppMap);
							periodVtpMap.put(svtpd.getPeriod().getId(), trVtppMap);
							svtpData.put(svtpd.getSubject().getSubjectNo(), periodVtpMap);
						}
					} else {
						sampIdVtppMap = new HashMap<>();
						trVtppMap = new HashMap<>();
						periodVtpMap = new HashMap<>();
						rcdList = new ArrayList<>();
						rcdList.add(vrtcDto);
						sampIdVtppMap.put(svtpd.getTimepoint().getId(), rcdList);
						trVtppMap.put(svtpd.getTimepoint().getTreatmentInfo().getId(), sampIdVtppMap);
						periodVtpMap.put(svtpd.getPeriod().getId(), trVtppMap);
						svtpData.put(svtpd.getSubject().getSubjectNo(), periodVtpMap);
					}
				}
			}
			vcdDto = new VitalCollectionDetailsDto();
			vcdDto.setSvtpData(svtpData);
			vcdDto.setVitalMap(vitalMap);
			vcdDto.setPostDoseVtpMap(postDoseVtpMap);
			vcdDto.setPreDoseVtpMap(preDoseVtpMap);
			vcdDto.setParameterMap(parameterMap);
			vcdDto.setCtpDto(ctpDto);
			vcdDto.setPreorderVitalIdsMap(preorderVitalIdsMap);
			vcdDto.setPostDoseVtpMap(postDoseVtpMap);
			vcdDto.setPreVitalsMap(preVitalsMap);
			vcdDto.setPostvitalOrderMap(postvitalOrderMap);
			vcdDto.setPostVitalsMap(postVitalsMap);
			vcdDto.setPostorderVitalIdsMap(postorderVitalIdsMap);
			vcdDto.setPrevitalOrderMap(previtalOrderMap);
			vcdDto.setPtdDto(ptdDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vcdDto;
	}

	private Map<Long, GlobalparameterFromDto> getVitalGlobalParameters(Long languageId, List<VitalTimePoints> vptList) {
		Map<Long, GlobalparameterFromDto> gpDtoMap = new HashMap<>();
		Map<Long, List<Long>> vptParamMap = new HashMap<>();
		List<Long> allPramIds = new ArrayList<>();
		try {
			if (vptList != null && vptList.size() > 0) {
				for (VitalTimePoints vpt : vptList) {
					List<Long> vptParamIds = new ArrayList<>();
					String parametersStr = vpt.getParameterIds();
					if (parametersStr != null && !parametersStr.equals("")) {
						String[] tempArr = parametersStr.split("\\,");
						if (tempArr.length > 0) {
							for (String st : tempArr)
								vptParamIds.add(Long.parseLong(st));
						}
					}
					if (vptParamIds.size() > 0) {
						vptParamMap.put(vpt.getId(), vptParamIds);
						allPramIds.addAll(vptParamIds);
					}
				}
				if (vptParamMap.size() > 0) {
					gpDtoMap = clinicalDao.getGlobalParameterDetails(languageId, vptParamMap,StudyActivities.StudyExecutionVitals.toString(), allPramIds);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gpDtoMap;
	}
	
	/*
	 * All the Collected Sample Vacutainer barcodes of particular study
	 * 
	 * @see com.covideinfo.clinical.service.ClinicalService#colectedSampleVacutainers(java.lang.Long)
	 */
	@Override
	public CentrificationDto collectedSampleVacutainers(Long studyId) {
		CentrificationDto cfd = null;
		List<SubjectSampleCollectionTimePointsData> ssctpdList = null;
		Map<String, Map<Long, Map<Long, SubjectSampleCollectionTimePointsData>>> ssctpdMap = new HashMap<>();
		Map<Long, Map<Long, SubjectSampleCollectionTimePointsData>> periodMap = null;
		Map<Long, SubjectSampleCollectionTimePointsData> tpMap = null;
		CentrificationDetailsDto cfdPojo = null;
		StudyMaster sm = null;
		List<Long> spmIdsList = null;
		try {
			cfdPojo = clinicalDao.getCollectedSamplesList(studyId);
			if(cfdPojo != null) {
				ssctpdList = cfdPojo.getSsctpdList();
				sm = cfdPojo.getSm();
				spmIdsList = cfdPojo.getSpmIdsList();
				if(ssctpdList != null && ssctpdList.size() > 0) {
					for(SubjectSampleCollectionTimePointsData ssctpd : ssctpdList) {
						if(ssctpdMap.containsKey(ssctpd.getSubject().getSubjectNo())) {
							periodMap = ssctpdMap.get(ssctpd.getSubject().getSubjectNo());
							if(periodMap.containsKey(ssctpd.getStudyPeriodMaster().getId())) {
								tpMap = periodMap.get(ssctpd.getStudyPeriodMaster().getId());
								if(!tpMap.containsKey(ssctpd.getSampleTimePoint().getId())) {
									tpMap.put(ssctpd.getSampleTimePoint().getId(), ssctpd);
									periodMap.put(ssctpd.getStudyPeriodMaster().getId(), tpMap);
									ssctpdMap.put(ssctpd.getSubject().getSubjectNo(), periodMap);
								}
							}else {
								tpMap = new HashMap<>();
								tpMap.put(ssctpd.getSampleTimePoint().getId(), ssctpd);
								periodMap.put(ssctpd.getStudyPeriodMaster().getId(), tpMap);
								ssctpdMap.put(ssctpd.getSubject().getSubjectNo(), periodMap);
							}
						}else {
							tpMap = new HashMap<>();
							periodMap = new HashMap<>();
							tpMap.put(ssctpd.getSampleTimePoint().getId(), ssctpd);
							periodMap.put(ssctpd.getStudyPeriodMaster().getId(), tpMap);
							ssctpdMap.put(ssctpd.getSubject().getSubjectNo(), periodMap);
						}
					}
				}
				cfd = new CentrificationDto();
				cfd.setSsctpMap(ssctpdMap);
				cfd.setSm(sm);
				cfd.setSpmIdsList(spmIdsList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cfd;
	}

	/*
	 * All the Instrument with instrument type match
	 * @see com.covideinfo.clinical.service.ClinicalService#getInstrumentList(java.lang.String)
	 */
	@Override
	public List<Instrument> getInstrumentList(String centrifuge) {
		return clinicalDao.getInstrumentListWithType(centrifuge);
	}

	/*
	 * To get all Subjects Meal Time points and collected meal data for to check dinner and freak fast status check
	 * @see com.covideinfo.clinical.service.ClinicalService#allMealTimepoints(java.lang.Long)
	 */
	@Override
	public MealsTimePointsDto allMealTimepoints(Long studyId) {
		//This Dto will Use to Transfer required study data to meals js page
		MealsTimePointsDto dto = new MealsTimePointsDto();
		//This Dto is used for getting data from database and customize getting data and put into MealsTimePointsDto
		MealInfoDto mealsInfoDto = null;
		StudyMaster study = null;
		Map<Long, MealsTimePoints> mealTimePoints = new HashMap<>(); 
		// unused variable
//		Map<String, List<Long>> mealTimePointIds = new HashMap<>();
		Map<Long, List<MealsTimePoints>> preDoseMap = new HashMap<>(); //treatmentId, mealsList
		Map<Long, List<MealsTimePoints>> postDoseMap = new HashMap<>();//treatmentId, mealsList
		Map<Long, Map<Long, Map<String, RealTimeCommunicationDto>>> smtpMap = new HashMap<>(); // periodId, mealId, subjectNo, subdoseDonePojo
		List<MealsTimePoints> timepoints =  null;
		List<SubjectMealsTimePointsData> submtpDtataList = null;
		List<TreatmentInfo> treatmentList = null;
		Map<Long, TreatmentInfo> treatmentMap = new HashMap<>();
		List<SubjectRandamization> srmzList = null;
		List<StudySubjects> subjectsList = null;
		Map<String, StudySubjects> subMap = new HashMap<>();// subjectNo, StubjectPojo
		Map<String, Map<Long, List<Long>>> twsubMap = new HashMap<>(); //SubjectNo, PeriodId, List of TreatmentPojo
		List<StudySubjectPeriods> ssubPeriodList = null;
		Map<String, StudyPeriodMaster> subjectPeriods = new HashMap<>(); //SubjectNo, periodMaster
		List<SubjectMealsTimePointsData> subColDatalList = null;
		Map<String, RealTimeCommunicationDto> collectedDataMap = new HashMap<>();
		Map<String, Map<Long, String>> subjectDoseMap = null;
		Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap = null;
		DosingDto dsDto = null;
		Map<String, Long> devionCodeMap = new HashMap<>();
		Long minTreatmentId = null;
		PlannedTimeDetailsDto ptdDto = null;
		Map<String, StudyPeriodMaster> spmMap = new HashMap<>();
		List<StudyPeriodMaster> spmList = null;
		String projectNo = "";
		Map<Long, Map<String, Map<String, Integer>>> pwMealAllowMap = new HashMap<>(); // periodId, timePoint, subjects, AllowTime
		List<AllowStudySubjectMealsDto> alsmList = null;
		Map<String, Map<Long, Map<String, Long>>> subEndTpsMap = new HashMap<>();//timePoint, PeriodId, bar code, treatmentId
		try {
			mealsInfoDto = studyDao.getMealsDetails(studyId);
			if(mealsInfoDto != null) {
				timepoints = mealsInfoDto.getTimepoints();
				submtpDtataList = mealsInfoDto.getSubmDataList();
				treatmentList = mealsInfoDto.getTreatmentList();
				study = mealsInfoDto.getStudy();
				subjectsList = mealsInfoDto.getSubjectsList();
				srmzList = mealsInfoDto.getSrmzList();
				ssubPeriodList = mealsInfoDto.getSsubPeriodList();
				subColDatalList = mealsInfoDto.getSubColDatalList();
				subjectDoseMap = mealsInfoDto.getSubjectDoseMap();
				dosedMap = mealsInfoDto.getDosedMap();
				dsDto = mealsInfoDto.getDsDto();
				spmList = mealsInfoDto.getSpmList();
				projectNo = mealsInfoDto.getStudy().getProjectNo();
				alsmList = mealsInfoDto.getAlsmList();
			}
			if(spmList != null && spmList.size() > 0) {
				for(StudyPeriodMaster spm : spmList) {
					spmMap.put(spm.getPeriodName(), spm);
				}
			}
			ptdDto = clinicalDao.getPlannedTimeDetailsDtoDetails(studyId, spmMap, projectNo);
			if(ptdDto != null) {
				if(mealsInfoDto != null)
					ptdDto.setWashoutDays(mealsInfoDto.getStudy().getWashoutPeriod());
			}
			dto.setPtdDto(ptdDto);
			dto.setTinfList(treatmentList);
			dto.setSubjectDoseMap(subjectDoseMap);
			if(ssubPeriodList != null && ssubPeriodList.size() > 0) {
				for(StudySubjectPeriods ssp : ssubPeriodList) {
					/*if(ssp.getSubject().getStdSubjectId() != null) 
						subjectPeriods.put(ssp.getSubject().getStdSubjectId().getSubjectNo(), ssp.getPeriodId());
					else 
						subjectPeriods.put(ssp.getSubject().getSubjectNo(), ssp.getPeriodId());*/
					subjectPeriods.put(ssp.getSubject().getSubjectNo(), ssp.getPeriodId());
				}
			}
			dto.setSubjectPerods(subjectPeriods);
			
			Map<Long, List<Long>> temptrMap = null;
			List<Long> trTempList = null;
			if(srmzList != null && srmzList.size() > 0) {
				for(SubjectRandamization sr : srmzList) {
					if(twsubMap.containsKey(sr.getSubjectNo())) {
						temptrMap = twsubMap.get(sr.getSubjectNo());
						if(temptrMap.containsKey(sr.getPeriod().getId())) {
							trTempList = temptrMap.get(sr.getPeriod().getId());
							trTempList.add(sr.getTreatmentInfo().getId());
							temptrMap.put(sr.getPeriod().getId(), trTempList);
							twsubMap.put(sr.getSubjectNo(), temptrMap);
						}else {
							trTempList = new ArrayList<>();
							trTempList.add(sr.getTreatmentInfo().getId());
							temptrMap.put(sr.getPeriod().getId(), trTempList);
							twsubMap.put(sr.getSubjectNo(), temptrMap);
						}
					}else {
						trTempList = new ArrayList<>();
						temptrMap = new HashMap<>();
						trTempList.add(sr.getTreatmentInfo().getId());
						temptrMap.put(sr.getPeriod().getId(), trTempList);
						twsubMap.put(sr.getSubjectNo(), temptrMap);
					}
				}
			}
			dto.setTwsubMap(twsubMap);
		
			
			if(subjectsList != null && subjectsList.size() > 0) {
				for(StudySubjects ss : subjectsList) {
					/*if(ss.getStdSubjectId() != null) {
						subMap.put(ss.getStdSubjectId().getSubjectNo(), ss.getStdSubjectId());
					}else {
						subMap.put(ss.getSubjectNo(), ss);
					}*/
					subMap.put(ss.getSubjectNo(), ss);
				}
			}
			dto.setSubMap(subMap);
			
			Map<Long, Map<String, RealTimeCommunicationDto>> smtpTempMap = null;
			Map<String, RealTimeCommunicationDto> smtTemp1 = null;
			if(submtpDtataList != null && submtpDtataList.size() > 0) {
				for(SubjectMealsTimePointsData smtpd : submtpDtataList) {
					RealTimeCommunicationDto rcDto = getRealTimeCommunicationDtoRecord(smtpd);
					if(smtpMap.containsKey(smtpd.getStudyPeriodMaster().getId())) {
						smtpTempMap = smtpMap.get(smtpd.getStudyPeriodMaster().getId());
						if(smtpTempMap.containsKey(smtpd.getMealsTimePoint().getId())) {
							smtTemp1 = smtpTempMap.get(smtpd.getMealsTimePoint().getId());
							if(!smtTemp1.containsKey(smtpd.getSubject().getSubjectNo())) {
								smtTemp1.put(smtpd.getSubject().getSubjectNo(), rcDto);
								smtpTempMap.put(smtpd.getMealsTimePoint().getId(), smtTemp1);
								smtpMap.put(smtpd.getStudyPeriodMaster().getId(), smtpTempMap);
							}
						}else {
							smtTemp1 = smtpTempMap.get(smtpd.getMealsTimePoint().getId());
							if(smtTemp1 == null)
								smtTemp1 = new HashMap<>();
							smtTemp1.put(smtpd.getSubject().getSubjectNo(), rcDto);
							smtpTempMap.put(smtpd.getMealsTimePoint().getId(), smtTemp1);
							smtpMap.put(smtpd.getStudyPeriodMaster().getId(), smtpTempMap);
						}
					}else {
						smtTemp1 = new HashMap<>();
						smtpTempMap = new HashMap<>();
						smtTemp1.put(smtpd.getSubject().getSubjectNo(), rcDto);
						smtpTempMap.put(smtpd.getMealsTimePoint().getId(), smtTemp1);
						smtpMap.put(smtpd.getStudyPeriodMaster().getId(), smtpTempMap);
					}
				}
			}
			if(treatmentList != null && treatmentList.size() > 0) {
				for(TreatmentInfo tin : treatmentList) {
					treatmentMap.put(tin.getId(), tin);
				}
			}
			dto.setTreatmentMap(treatmentMap);
			if(timepoints != null && timepoints.size() > 0) {
				for(MealsTimePoints mtp : timepoints) {
					mealTimePoints.put(mtp.getId(), mtp);
					List<MealsTimePoints> mtTempList = null;
					if(mtp.getSign() == null || mtp.getSign().trim().equals("")) {
						if(postDoseMap.containsKey(mtp.getTreatmentInfo().getId())) {
							mtTempList = postDoseMap.get(mtp.getTreatmentInfo().getId());
							mtTempList.add(mtp);
							Collections.sort(mtTempList);
							postDoseMap.put(mtp.getTreatmentInfo().getId(), mtTempList);
						}else {
							mtTempList = new ArrayList<>();
							mtTempList.add(mtp);
							postDoseMap.put(mtp.getTreatmentInfo().getId(), mtTempList);
						}
					}else if(mtp.getSign().trim().equals("-")) {
						if(preDoseMap.containsKey(mtp.getTreatmentInfo().getId())) {
							mtTempList = preDoseMap.get(mtp.getTreatmentInfo().getId());
							mtTempList.add(mtp);
							Collections.sort(mtTempList);
							preDoseMap.put(mtp.getTreatmentInfo().getId(), mtTempList);
						}else {
							mtTempList = new ArrayList<>();
							mtTempList.add(mtp);
							preDoseMap.put(mtp.getTreatmentInfo().getId(), mtTempList);
						}
					}
				}
			}
			Map<Long, Map<String, Long>> subEndPeriodMap = null;
			Map<String, Long> subEndBarcodMap = null;
			if(subColDatalList != null && subColDatalList.size() > 0) {
				for(SubjectMealsTimePointsData smtd : subColDatalList) {
					int hours = 0;
					int minutes = 0;
					Calendar cal = Calendar.getInstance();
					cal.setTime(smtd.getStartTime());
					hours = cal.get(Calendar.HOUR);
					minutes = cal.get(Calendar.MINUTE);
					smtd.setStartTimeOnly(hours+":"+minutes);
					RealTimeCommunicationDto rcDto = getRealTimeCommunicationDtoRecord(smtd);
					collectedDataMap.put(smtd.getSubject().getSubjectNo()+","+smtd.getStudyPeriodMaster().getId()
							+","+smtd.getMealsTimePoint().getTreatmentInfo().getId()+","+smtd.getMealsTimePoint().getId(), rcDto);
					
					if(smtd.getStartTime() != null && smtd.getEndTime() == null) {
						if(subEndTpsMap.containsKey(smtd.getMealsTimePoint().getSign()+smtd.getMealsTimePoint().getTimePoint())) {
							subEndPeriodMap = subEndTpsMap.get(smtd.getMealsTimePoint().getSign()+smtd.getMealsTimePoint().getTimePoint());
							if(subEndPeriodMap.containsKey(smtd.getStudyPeriodMaster().getId())) {
								subEndBarcodMap = subEndPeriodMap.get(smtd.getStudyPeriodMaster().getId());
								if(smtd.getSubject().getStdSubjectId() != null)
									subEndBarcodMap.put("02a"+smtd.getSubject().getReportingId().getSubjectNo()+"a"+smtd.getStudyPeriodMaster().getStudy().getId()+"n", smtd.getMealsTimePoint().getTreatmentInfo().getId());
								else 
									subEndBarcodMap.put("02a"+smtd.getSubject().getSubjectNo()+"a"+smtd.getStudyPeriodMaster().getStudy().getId()+"n", smtd.getMealsTimePoint().getTreatmentInfo().getId());
								subEndPeriodMap.put(smtd.getStudyPeriodMaster().getId(), subEndBarcodMap);
								subEndTpsMap.put(smtd.getMealsTimePoint().getSign()+smtd.getMealsTimePoint().getTimePoint(), subEndPeriodMap);
							}else {
								subEndBarcodMap = new HashMap<>();
								if(smtd.getSubject().getStdSubjectId() != null)
									subEndBarcodMap.put("02a"+smtd.getSubject().getReportingId().getSubjectNo()+"a"+smtd.getStudyPeriodMaster().getStudy().getId()+"n", smtd.getMealsTimePoint().getTreatmentInfo().getId());
								else 
									subEndBarcodMap.put("02a"+smtd.getSubject().getSubjectNo()+"a"+smtd.getStudyPeriodMaster().getStudy().getId()+"n", smtd.getMealsTimePoint().getTreatmentInfo().getId());
								subEndPeriodMap.put(smtd.getStudyPeriodMaster().getId(), subEndBarcodMap);
								subEndTpsMap.put(smtd.getMealsTimePoint().getSign()+smtd.getMealsTimePoint().getTimePoint(), subEndPeriodMap);
							}
						}else {
							subEndPeriodMap = new HashMap<>();
							subEndBarcodMap = new HashMap<>();
							if(smtd.getSubject().getStdSubjectId() != null)
								subEndBarcodMap.put("02a"+smtd.getSubject().getReportingId().getSubjectNo()+"a"+smtd.getStudyPeriodMaster().getStudy().getId()+"n", smtd.getMealsTimePoint().getTreatmentInfo().getId());
							else 
								subEndBarcodMap.put("02a"+smtd.getSubject().getSubjectNo()+"a"+smtd.getStudyPeriodMaster().getStudy().getId()+"n", smtd.getMealsTimePoint().getTreatmentInfo().getId());
							subEndPeriodMap.put(smtd.getStudyPeriodMaster().getId(), subEndBarcodMap);
							subEndTpsMap.put(smtd.getMealsTimePoint().getSign()+smtd.getMealsTimePoint().getTimePoint(), subEndPeriodMap);
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
			if(treatmentMap.size() > 0)
				minTreatmentId = Collections.min(treatmentMap.keySet());
			
			Map<String, Map<String, Integer>> tpAlsmMap = null;
			Map<String, Integer> subAlsmMap = null;
			if(alsmList != null && alsmList.size() > 0) {
				for(AllowStudySubjectMealsDto alsm : alsmList) {
				   if(pwMealAllowMap.containsKey(alsm.getPeriodId())) {
					   tpAlsmMap = pwMealAllowMap.get(alsm.getPeriodId());
					   if(tpAlsmMap.containsKey(alsm.getTpSign()+alsm.getTimePoint())) {
						   subAlsmMap = tpAlsmMap.get(alsm.getTpSign()+alsm.getTimePoint());
						   subAlsmMap.put(alsm.getSubjects(), alsm.getAllowedTime());
						   tpAlsmMap.put(alsm.getTpSign()+alsm.getTimePoint(), subAlsmMap);
						   pwMealAllowMap.put(alsm.getPeriodId(), tpAlsmMap);
					   }else {
						   subAlsmMap = new HashMap<>();
						   subAlsmMap.put(alsm.getSubjects(), alsm.getAllowedTime());
						   tpAlsmMap.put(alsm.getTpSign()+alsm.getTimePoint(), subAlsmMap);
						   pwMealAllowMap.put(alsm.getPeriodId(), tpAlsmMap);
					   }
				   }else {
					   subAlsmMap = new HashMap<>();
					   tpAlsmMap = new HashMap<>();
					   subAlsmMap.put(alsm.getSubjects(), alsm.getAllowedTime());
					   tpAlsmMap.put(alsm.getTpSign()+alsm.getTimePoint(), subAlsmMap);
					   pwMealAllowMap.put(alsm.getPeriodId(), tpAlsmMap);
				   }
				}
			}
			dto.setPwMealAllowMap(pwMealAllowMap);
			dto.setCollectedDataMap(collectedDataMap);
			dto.setMealsTimpointsMap(mealTimePoints);
			dto.setPreDoseMap(preDoseMap);
			dto.setPostDoseMap(postDoseMap);
			dto.setSmtpMap(smtpMap);
			dto.setStudy(study);
			dto.setDosedMap(dosedMap);
			dto.setDsDto(dsDto);
			dto.setMinTreatmentId(minTreatmentId);
			dto.setSubEndTpsMap(subEndTpsMap);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	private RealTimeCommunicationDto getRealTimeCommunicationDtoRecord(SubjectMealsTimePointsData smtd) {
		RealTimeCommunicationDto rcDto = new RealTimeCommunicationDto();
		try {
			rcDto.setPeriodId(smtd.getStudyPeriodMaster().getId());
			rcDto.setSubjectNo(smtd.getSubject().getSubjectNo());
			rcDto.setSubjectVitalId(smtd.getId());
			rcDto.setTimePointId(smtd.getMealsTimePoint().getId());
			rcDto.setTreatmentId(smtd.getMealsTimePoint().getTreatmentInfo().getId());
			rcDto.setStudyId(smtd.getStudyPeriodMaster().getStudy().getId());
			if(smtd.getStartTime() != null)
				rcDto.setMealsSatartTime(smtd.getStartTime());
			if(smtd.getEndTime() != null)
				rcDto.setMealsEndTime(smtd.getEndTime());
			rcDto.setTimePoint(smtd.getMealsTimePoint().getTimePoint());
			if(smtd.getSubject().getStdSubjectId()!= null)
				rcDto.setReplacedSubjectId(smtd.getSubject().getStdSubjectId().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rcDto;
	}
}