package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.CpurActivityReviewDao;
import com.covideinfo.dto.CpuActivitiesDetailsDto;
import com.covideinfo.dto.CpuActivitiesReviewDataDto;
import com.covideinfo.dto.DiscrepancyResponseDto;
import com.covideinfo.dto.DosingDataDto;
import com.covideinfo.dto.FormGroupsDto;
import com.covideinfo.dto.FromControlDto;
import com.covideinfo.dto.FromValuesDto;
import com.covideinfo.dto.MealsReviewDto;
import com.covideinfo.dto.MessageDto;
import com.covideinfo.dto.ParameterFormDataDto;
import com.covideinfo.dto.SampleCollectionDetailsDto;
import com.covideinfo.dto.StaticActivityParamtersValuesDto;
import com.covideinfo.dto.StaticFormsDataDto;
import com.covideinfo.dto.VitalTimePointsDataDto;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.model.DiscripancyReviewDeatails;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.SubjectDoseTimePointParametersData;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectVitalParametersData;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.model.UserMaster;
import com.covideinfo.scheduler.threads.SampleScheduleTimesCalculationThread;
import com.covideinfo.service.CpurActivityReviewService;
import com.covideinfo.service.SampleScheduleCalculationService;

@Service("cpurActivityReviewService")
public class CpurActivityReviewServiceImpl implements CpurActivityReviewService {
	
	@Autowired
	CpurActivityReviewDao cpureviewDao;

	@Override
	public CpuActivitiesReviewDataDto getCpuReviewActivityData(Long projectId, Long activityId, Long userId,
			Long roleId, Long languageId, String type) {
		CpuActivitiesReviewDataDto cpuReviewDto = null;
		CpuActivitiesDetailsDto cpuadDto = null;
		List<SubjectMealsTimePointsData> mealsDataList = null;
		List<SubjectSampleCollectionTimePointsData> samplesDtaList = null;
		List<SubjectVitalTimePointsData> vitalsDtaList = null;
		List<SubjectVitalParametersData> vitalParmsList = null;
		List<SubjectDoseTimePoints> doseDataList = null;
		List<SubjectDoseTimePointParametersData> doseParamList = null;
		UserMaster user = null;
		List<StaticFormsDataDto> sfdDtoList = new ArrayList<>();
		List<LanguageSpecificGlobalParameterDetails> lspgpdList = null;
		Map<Long, LanguageSpecificGlobalParameterDetails> lsgpdMap = new HashMap<>();
		Map<Long, List<ParameterFormDataDto>> parametersMap = new HashMap<>();
		List<ParameterFormDataDto> pfDtoList = null;
		Map<Long, List<LanguageSpecificGroupDetails>> lspgMap = null;
		//CupuData
		Map<Long, MealsReviewDto> mealsDataMap = new HashMap<>();
		Map<Long, SampleCollectionDetailsDto> samplesDataMap = new HashMap<>();
		Map<Long, VitalTimePointsDataDto> vitalDataMap = new HashMap<>();
		Map<Long, DosingDataDto> doseDataMap = new HashMap<>();
		DraftReviewStage submitdrs = null;
		DraftReviewStage approvedrs = null;
		DraftReviewStage sendCommentsdrs = null;
		Map<Long, Map<Long, Map<String, List<DiscripancyReviewDeatails>>>> drdActMap = new HashMap<>();//activityId,dataactId fieldName, descripencyList;
		List<DiscripancyReviewDeatails> drdList = null;
		String activityCode = "";
		Map<Long, StaticActivityParamtersValuesDto> paramDataMap = new HashMap<>();
		try {
			cpuadDto = cpureviewDao.getCpuReviewActivityData(projectId, activityId, userId,roleId, languageId, type);
			if(cpuadDto != null) {
				mealsDataList = cpuadDto.getMealsDataList();
				samplesDtaList = cpuadDto.getSamplesDtaList();
				vitalsDtaList = cpuadDto.getVitalsDtaList();
				vitalParmsList = cpuadDto.getVitalParmsList();
				doseDataList = cpuadDto.getDoseDataList();
				doseParamList = cpuadDto.getDoseParamList();
				user = cpuadDto.getUser();
				lspgpdList = cpuadDto.getLspgpdList();
				lspgMap = cpuadDto.getLspgMap();
				submitdrs = cpuadDto.getSubmitdrs();
				approvedrs = cpuadDto.getApprovedrs();
				sendCommentsdrs = cpuadDto.getSendCommentsdrs();
				drdList = cpuadDto.getDrdList();
			}
			if(lspgpdList != null && lspgpdList.size() > 0) {
				for(LanguageSpecificGlobalParameterDetails lsgp : lspgpdList) {
					lsgpdMap.put(lsgp.getParameterId().getId(), lsgp);
				}
			}
			if(mealsDataList != null && mealsDataList.size() > 0) {
				activityCode = StudyActivities.MealsCollection.toString();
				for(SubjectMealsTimePointsData smtpd : mealsDataList) {
					StaticFormsDataDto sfdDto = new StaticFormsDataDto();
					sfdDto.setCreatedBy(smtpd.getCreatedBy().getFullName());
					sfdDto.setCreatedOn(smtpd.getCreatedOn());
					sfdDto.setId(smtpd.getId());
					sfdDto.setPeriod(smtpd.getStudyPeriodMaster().getPeriodName());
					sfdDto.setSubjectNo(smtpd.getSubject().getSubjectNo());
					sfdDto.setTimePoint(smtpd.getMealsTimePoint().getSign()+smtpd.getMealsTimePoint().getTimePoint());
					sfdDtoList.add(sfdDto);
					
					MealsReviewDto mrd = new MealsReviewDto();
					mrd.setMealsDataId(smtpd.getId());
					mrd.setPeriodName(smtpd.getStudyPeriodMaster().getPeriodName());
					mrd.setComments(smtpd.getComments());
					mrd.setConsumption(smtpd.getConsumption());
					mrd.setTimePoint(smtpd.getMealsTimePoint().getSign()+smtpd.getMealsTimePoint().getTimePoint());
					Calendar cal = Calendar.getInstance();
					String hour = "";
					String minutes = "";
					if(smtpd.getStartTime() != null) {
						cal.setTime(smtpd.getStartTime());
						int hr = cal.get(Calendar.HOUR);
						if(hr < 10)
							hour = "0"+hr;
						else hour = hr+"";
						int min = cal.get(Calendar.MINUTE);
						if(min < 10)
							minutes = "0"+min;
						else minutes = min+"";
						
						mrd.setStartTime(hour+":"+minutes);
					}else mrd.setStartTime("");
					
					if(smtpd.getEndTime() != null) {
						cal.setTime(smtpd.getEndTime());
						int hr = cal.get(Calendar.HOUR);
						if(hr < 10)
							hour = "0"+hr;
						else hour = hr+"";
						int min = cal.get(Calendar.MINUTE);
						if(min < 10)
							minutes = "0"+min;
						else minutes = min+"";
						mrd.setEndTime(hour+":"+minutes);
					}else mrd.setEndTime("");
					mealsDataMap.put(smtpd.getId(), mrd);
				}
			}
			if(samplesDtaList != null && samplesDtaList.size() > 0) {
				activityCode = StudyActivities.SampleCollection.toString();
				for(SubjectSampleCollectionTimePointsData sscd : samplesDtaList) {
					StaticFormsDataDto sfdDto = new StaticFormsDataDto();
					sfdDto.setCreatedBy(sscd.getCollectedBy().getFullName());
					sfdDto.setCreatedOn(sscd.getCreatedOn());
					sfdDto.setId(sscd.getId());
					sfdDto.setPeriod(sscd.getStudyPeriodMaster().getPeriodName());
					sfdDto.setSubjectNo(sscd.getSubject().getSubjectNo());
					sfdDto.setTimePoint(sscd.getSampleTimePoint().getSign()+sscd.getSampleTimePoint().getTimePoint());
					sfdDtoList.add(sfdDto);
					
					SampleCollectionDetailsDto scdDto = new SampleCollectionDetailsDto();
					Calendar cal = Calendar.getInstance();
					String hour = "";
					String minutes = "";
					if(sscd.getActualtime() != null) {
						cal.setTime(sscd.getActualtime());
						int hr = cal.get(Calendar.HOUR);
						if(hr < 10)
							hour = "0"+hr;
						else hour = hr+"";
						int min = cal.get(Calendar.MINUTE);
						if(min < 10)
							minutes = "0"+min;
						else minutes = min+"";
						scdDto.setCollectionTime(hour+":"+minutes);
					}else scdDto.setCollectionTime("");
					scdDto.setPeriodName(sscd.getStudyPeriodMaster().getPeriodName());
					scdDto.setSampleDataId(sscd.getId());
					scdDto.setTimePoint(sscd.getSampleTimePoint().getSign()+sscd.getSampleTimePoint().getTimePoint());
					samplesDataMap.put(sscd.getId(), scdDto);
				}
			}
			//vitalparameter
			if(vitalParmsList != null && vitalParmsList.size() > 0) {
				Map<Long, List<SubjectVitalParametersData>> vtpdMap = new HashMap<>();
				List<SubjectVitalParametersData> tempList = null;
				for(SubjectVitalParametersData vtpd : vitalParmsList) {
					if(vtpdMap.containsKey(vtpd.getSubjVitalTpData().getId())) {
						tempList = vtpdMap.get(vtpd.getSubjVitalTpData().getId());
						tempList.add(vtpd);
						vtpdMap.put(vtpd.getSubjVitalTpData().getId(), tempList);
					}else {
						tempList = new ArrayList<>();
						tempList.add(vtpd);
						vtpdMap.put(vtpd.getSubjVitalTpData().getId(), tempList);
					}
				}
				if(vtpdMap.size() > 0) {
					for(Map.Entry<Long, List<SubjectVitalParametersData>> param : vtpdMap.entrySet()) {
						List<SubjectVitalParametersData> svpdList = param.getValue();
						if(svpdList != null && svpdList.size() > 0) {
							for(SubjectVitalParametersData svpd : svpdList) {
								
								StaticActivityParamtersValuesDto sapvd = new StaticActivityParamtersValuesDto();
								if(svpd.getGlobalValue() != null)
									sapvd.setGlobalValId(svpd.getGlobalValue().getId());
								else sapvd.setParamterValue(svpd.getParameterValue());
								paramDataMap.put(svpd.getParameter().getId(), sapvd);
								
								if(parametersMap.containsKey(param.getKey())) {
									pfDtoList = parametersMap.get(param.getKey());
									pfDtoList = getParmeterDetails(pfDtoList, svpd.getParameter(), lsgpdMap, languageId, lspgMap);
									if(pfDtoList != null && pfDtoList.size() > 0)
										Collections.sort(pfDtoList);
									parametersMap.put(svpd.getSubjVitalTpData().getId(), pfDtoList);
								}else {
									pfDtoList = new ArrayList<>();
									pfDtoList = getParmeterDetails(pfDtoList, svpd.getParameter(), lsgpdMap, languageId, lspgMap);
									if(pfDtoList != null && pfDtoList.size() > 0)
										Collections.sort(pfDtoList);
									parametersMap.put(svpd.getSubjVitalTpData().getId(), pfDtoList);
								}
							}
						}
					}
				}
			 }
			if(vitalsDtaList != null && vitalsDtaList.size() > 0) {
				activityCode = StudyActivities.StudyExecutionVitals.toString();
				for(SubjectVitalTimePointsData svtp : vitalsDtaList) {
					StaticFormsDataDto sfdDto = new StaticFormsDataDto();
					sfdDto.setCreatedBy(svtp.getCollectedBy().getFullName());
					sfdDto.setCreatedOn(svtp.getCreatedOn());
					sfdDto.setId(svtp.getId());
					sfdDto.setPeriod(svtp.getPeriod().getPeriodName());
					sfdDto.setSubjectNo(svtp.getSubject().getSubjectNo());
					sfdDto.setTimePoint(svtp.getTimepoint().getSign()+svtp.getTimepoint().getTimePoint());
					sfdDtoList.add(sfdDto);
					
					VitalTimePointsDataDto vtpDto = new VitalTimePointsDataDto();
					Calendar cal = Calendar.getInstance();
					String hour = "";
					String minutes = "";
					if(svtp.getStartTime() != null) {
						cal.setTime(svtp.getStartTime());
						int hr = cal.get(Calendar.HOUR);
						if(hr < 10)
							hour = "0"+hr;
						else hour = hr+"";
						int min = cal.get(Calendar.MINUTE);
						if(min < 10)
							minutes = "0"+min;
						else minutes = min+"";
						
						vtpDto.setStartTime(hour+":"+minutes);
					}else vtpDto.setStartTime("");
					
					if(svtp.getEndTime() != null) {
						cal.setTime(svtp.getEndTime());
						int hr = cal.get(Calendar.HOUR);
						if(hr < 10)
							hour = "0"+hr;
						else hour = hr+"";
						int min = cal.get(Calendar.MINUTE);
						if(min < 10)
							minutes = "0"+min;
						else minutes = min+"";
						vtpDto.setEndTime(hour+":"+minutes);
					}else vtpDto.setEndTime("");
					vtpDto.setParameterDto(parametersMap.get(svtp.getId()));
					vtpDto.setPeriodName(svtp.getPeriod().getPeriodName());
					vtpDto.setTimePoint(svtp.getTimepoint().getSign()+svtp.getTimepoint().getTimePoint());
					vtpDto.setVitalDataId(svtp.getId());
					vitalDataMap.put(svtp.getId(), vtpDto);
				}
			}
			if(doseParamList != null && doseParamList.size() > 0) {
				Map<Long, List<SubjectDoseTimePointParametersData>> sdpdMap = new HashMap<>();
				List<SubjectDoseTimePointParametersData> tempList = null;
				for(SubjectDoseTimePointParametersData sdpd : doseParamList) {
					
					StaticActivityParamtersValuesDto sapvd = new StaticActivityParamtersValuesDto();
					if(sdpd.getGlobalValue() != null)
						sapvd.setGlobalValId(sdpd.getGlobalValue().getId());
					else sapvd.setParamterValue(sdpd.getParameterValue());
					paramDataMap.put(sdpd.getParameter().getId(), sapvd);
					
					if(sdpdMap.containsKey(sdpd.getSubjectDoseTimePoint().getId())) {
						tempList = sdpdMap.get(sdpd.getSubjectDoseTimePoint().getId());
						tempList.add(sdpd);
						sdpdMap.put(sdpd.getSubjectDoseTimePoint().getId(), tempList);
					}else {
						tempList = new ArrayList<>();
						tempList.add(sdpd);
						sdpdMap.put(sdpd.getSubjectDoseTimePoint().getId(), tempList);
					}
				}
				if(sdpdMap.size() > 0) {
					for(Map.Entry<Long, List<SubjectDoseTimePointParametersData>> param : sdpdMap.entrySet()) {
						List<SubjectDoseTimePointParametersData> sdtppdList = param.getValue();
						if(sdtppdList != null && sdtppdList.size() > 0) {
							for(SubjectDoseTimePointParametersData sdpd : sdtppdList) {
								if(parametersMap.containsKey(param.getKey())) {
									pfDtoList = parametersMap.get(param.getKey());
									pfDtoList = getParmeterDetails(pfDtoList, sdpd.getParameter(), lsgpdMap, languageId, lspgMap);
									if(pfDtoList != null && pfDtoList.size() > 0)
										Collections.sort(pfDtoList);
									parametersMap.put(sdpd.getSubjectDoseTimePoint().getId(), pfDtoList);
								}else {
									pfDtoList = new ArrayList<>();
									pfDtoList = getParmeterDetails(pfDtoList, sdpd.getParameter(), lsgpdMap, languageId, lspgMap);
									if(pfDtoList != null && pfDtoList.size() > 0)
										Collections.sort(pfDtoList);
									parametersMap.put(sdpd.getSubjectDoseTimePoint().getId(), pfDtoList);
								}
							}
						}
					}
				}
			}
			
			 if(doseDataList != null && doseDataList.size() > 0) {
				 activityCode = StudyActivities.DosingCollection.toString();
				for(SubjectDoseTimePoints sdtp : doseDataList) {
					StaticFormsDataDto sfdDto = new StaticFormsDataDto();
					sfdDto.setCreatedBy(sdtp.getCreatedBy().getFullName());
					sfdDto.setCreatedOn(sdtp.getCreatedOn());
					sfdDto.setId(sdtp.getId());
					sfdDto.setPeriod(sdtp.getPeriod().getPeriodName());
					sfdDto.setSubjectNo(sdtp.getStudySubjects().getSubjectNo());
					sfdDto.setTimePoint(sdtp.getDoseTimePoints().getTimePoint());
					sfdDtoList.add(sfdDto);
					
					DosingDataDto doseDto = new DosingDataDto();
					Calendar cal = Calendar.getInstance();
					String hour = "";
					String minutes = "";
					if(sdtp.getActualTime() != null) {
						cal.setTime(sdtp.getActualTime());
						int hr = cal.get(Calendar.HOUR);
						if(hr < 10)
							hour = "0"+hr;
						else hour = hr+"";
						int min = cal.get(Calendar.MINUTE);
						if(min < 10)
							minutes = "0"+min;
						else minutes = min+"";
						doseDto.setDosingTime(hour+":"+minutes);
					}else doseDto.setDosingTime("");
					doseDto.setDosingDataId(sdtp.getId());
					doseDto.setParameterDto(parametersMap.get(sdtp.getId()));
					doseDto.setPeriodName(sdtp.getPeriod().getPeriodName());
					doseDto.setTimePoint(sdtp.getDoseTimePoints().getTimePoint());
					doseDataMap.put(sdtp.getId(), doseDto);
				}
			}
			
			 Map<Long, Map<String, List<DiscripancyReviewDeatails>>> dataActMap = null;
			 Map<String, List<DiscripancyReviewDeatails>> drdMap = null;
			 List<DiscripancyReviewDeatails> drdtempList = null;
			 if(drdList != null && drdList.size() > 0) {
				 for(DiscripancyReviewDeatails drd : drdList) {
					 Long dataActId = null;
					 if(drd.getSmtpData() != null)
						 dataActId = drd.getSmtpData().getId();
					 else if(drd.getSsctpData() != null)
						 dataActId = drd.getSsctpData().getId();
					 else if(drd.getSvtpData() != null)
						 dataActId = drd.getSvtpData().getId();
					 else if(drd.getSubjectDoseId() != null)
						 dataActId = drd.getSubjectDoseId().getId();
					 if(drdActMap.containsKey(drd.getActivityId())) {
						 dataActMap = drdActMap.get(drd.getActivityId());
						 if(dataActMap.containsKey(dataActId)) {
							 drdMap = dataActMap.get(dataActId);
							 if(drdMap.containsKey(drd.getFiledName())) {
								 drdtempList = drdMap.get(drd.getFiledName());
								 drdtempList.add(drd);
								 drdMap.put(drd.getFiledName(), drdtempList);
								 dataActMap.put(dataActId, drdMap);
								 drdActMap.put(drd.getActivityId(), dataActMap);
							 }else {
								 drdtempList = new ArrayList<>();
								 drdtempList.add(drd);
								 drdMap.put(drd.getFiledName(), drdtempList);
								 dataActMap.put(dataActId, drdMap);
								 drdActMap.put(drd.getActivityId(), dataActMap);
							 }
						 }else {
							 drdtempList = new ArrayList<>();
							 drdMap = new HashMap<>();
							 drdtempList.add(drd);
							 drdMap.put(drd.getFiledName(), drdtempList);
							 dataActMap.put(dataActId, drdMap);
							 drdActMap.put(drd.getActivityId(), dataActMap);
						 }
						 
					 }else {
						 drdtempList = new ArrayList<>();
						 drdMap = new HashMap<>();
						 dataActMap = new HashMap<>();
						 drdtempList.add(drd);
						 drdMap.put(drd.getFiledName(), drdtempList);
						 dataActMap.put(dataActId, drdMap);
						 drdActMap.put(drd.getActivityId(), dataActMap);
					 }
					 
				 }
			 }
			
			 cpuReviewDto = new CpuActivitiesReviewDataDto();
			 cpuReviewDto.setSfdDtoList(sfdDtoList);
			 cpuReviewDto.setUser(user);
			 cpuReviewDto.setMealsDataMap(mealsDataMap);
			 cpuReviewDto.setSamplesDataMap(samplesDataMap);
			 cpuReviewDto.setDoseDataMap(doseDataMap);
			 cpuReviewDto.setVitalDataMap(vitalDataMap);
			 cpuReviewDto.setSubmitdrs(submitdrs);
			 cpuReviewDto.setApprovedrs(approvedrs);
			 cpuReviewDto.setSendCommentsdrs(sendCommentsdrs);
			 cpuReviewDto.setDrdActMap(drdActMap);
			 cpuReviewDto.setActivityCode(activityCode);
			 cpuReviewDto.setParamDataMap(paramDataMap);
				 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpuReviewDto;
	}

	private List<ParameterFormDataDto> getParmeterDetails(List<ParameterFormDataDto> pfDtoList,
			GlobalParameter parameter, Map<Long, LanguageSpecificGlobalParameterDetails> lsgpdMap, Long languageId, Map<Long, List<LanguageSpecificGroupDetails>> lspgMap) {
		try {
			List<LanguageSpecificGroupDetails> lsgdList = null;
			ParameterFormDataDto pfdto = new ParameterFormDataDto();
			pfdto.setParameterId(parameter.getId());
			pfdto.setParameterName(lsgpdMap.get(parameter.getId()).getName());
			pfdto.setOrderNo(parameter.getOrderNo());
			FormGroupsDto fgdto = new FormGroupsDto();
			if(parameter.getGroups() != null)
				lsgdList = lspgMap.get(parameter.getId());
            if(lsgdList != null && lsgdList.size() > 0) {
				if(parameter.getGroups() != null) {
					fgdto.setGroupId(parameter.getGroups().getId());
					fgdto.setGroupName(parameter.getGroups().getName());	
					fgdto.setOrderNo(parameter.getGroups().getOrderNo());
				}else {
					fgdto.setGroupId(0L);
					fgdto.setGroupName("");
					fgdto.setOrderNo(0);
				}
			}
			pfdto.setGroup(fgdto);
			String controlType = parameter.getContentType().getCode();
			FromControlDto fcDto = null;
			List<FromValuesDto> valuesDto = new ArrayList<>();
			if(controlType.equals("SB") || controlType.equals("RB") || controlType.equals("CB")) {
				fcDto = new FromControlDto();
				List<LanguageSpecificValueDetails> gvList = cpureviewDao.getGlobalValuesList(parameter.getId(),parameter.getContentType().getId(), languageId);
				if(gvList != null && gvList.size() > 0) {
					for(LanguageSpecificValueDetails lspvd : gvList) {
						FromValuesDto fvdto = new FromValuesDto();
						fvdto.setValueId(lspvd.getGlobalValId().getId());
						fvdto.setValueName(lspvd.getName());
						fvdto.setOrder(lspvd.getGlobalValId().getOrderNo());
						valuesDto.add(fvdto);
					}
				}
				
			}
			if(fcDto == null)
				fcDto = new FromControlDto();
			fcDto.setContentCode(controlType);
			fcDto.setValuesDto(valuesDto);
			
			pfdto.setControlType(fcDto);
			pfDtoList.add(pfdto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pfDtoList;
	}

	@Override
	public MessageDto saveStaticDiscrepancyDetails(Long studyId, Long activityId, Long activityDataId, String fieldName,
			List<String> descComments, String activityType, Long userId, String parameterId) {
		MessageDto mdto = null;
		String result = "Failed";
		try {
			result = cpureviewDao.saveStaticDiscrepancyDetails(studyId, activityId, activityDataId, fieldName, descComments, activityType, userId, parameterId);
			mdto = new MessageDto();
			mdto.setMealsMsg(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mdto;
	}

	@Override
	public MessageDto saveStaticDiscrepancyResponseDetails(Long desId, String currentVal, String newVal, String response,
			Long userId, String activityType, SampleScheduleCalculationService sscService) {
		DiscrepancyResponseDto drdDto = null;
		String result = "";
		MessageDto mdto = null;
		try {
			drdDto = cpureviewDao.saveStaticDiscrepancyResponseDetails(desId, currentVal, newVal, response,userId, activityType);
			if(drdDto != null) {
				result = drdDto.getResult();
				if(result.equals("success") && drdDto.getSdtp() != null) {
					SampleScheduleTimesCalculationThread.calculateScheduleTimesBasedOnSubjectDoseTime(drdDto.getSdtp(), sscService, userId, "updateScheduleTimes");
				}
			}
			mdto = new MessageDto();
			mdto.setMealsMsg(result);
		} catch (Exception e) {
			e.printStackTrace();
			mdto = new MessageDto();
			mdto.setMealsMsg("Failed");
			return mdto;
		}
		return mdto;
	}

}
