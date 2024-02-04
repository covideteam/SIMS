package com.covideinfo.service.impl;

import java.sql.Blob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.StudyActivityDao;
import com.covideinfo.dto.FormGroupsDto;
import com.covideinfo.dto.FromControlDto;
import com.covideinfo.dto.FromValuesDto;
import com.covideinfo.dto.GlobalParameterDetailsDto;
import com.covideinfo.dto.GlobalparameterFromDto;
import com.covideinfo.dto.MessageDto;
import com.covideinfo.dto.ParameterFormDataDto;
import com.covideinfo.dto.ParameterModelDto;
import com.covideinfo.dto.RulesDetails;
import com.covideinfo.dto.SampleActivityParmanentTablesDto;
import com.covideinfo.dto.StudyActivitiesSavingDto;
import com.covideinfo.dto.StudyActivityDto;
import com.covideinfo.dto.StudyActivityFormDataDto;
import com.covideinfo.dto.StudyActivitySavingDto;
import com.covideinfo.dto.StudyDesignParametersDto;
import com.covideinfo.dto.StudyDesingActivityDetailsDto;
import com.covideinfo.dto.StudyDesingDto;
import com.covideinfo.dto.StudyDynamicActDetailsDto;
import com.covideinfo.enums.ControlTypes;
import com.covideinfo.enums.StudyDesign;
import com.covideinfo.enums.WorkFlowTypes;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyActivityRules;
import com.covideinfo.model.StudyCheckInActivityDataDetails;
import com.covideinfo.model.StudyCheckOutActivityDataDetails;
import com.covideinfo.model.StudyExecutionActivityDataDetails;
import com.covideinfo.model.StudyGroupPeriodMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.SubjectActivityData;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.service.RulesService;
import com.covideinfo.service.StudyActivityService;
import com.covideinfo.service.WorkflowService;

@Service("studyActivityService")
@SuppressWarnings("unused")
public class StudyActivityServiceImpl implements StudyActivityService {
	
	@Autowired
	StudyActivityDao sudyActivityDao;
	
	@Autowired
	WorkflowService workflowService;
	
	@Autowired
	RulesService rulesService;	
	
	@Override
	public List<GlobalParameterDetailsDto> getStudyActivityDetails(Long activityId, Long languageId, Long studyId) {
		StudyActivityDto saDto = null;
		List<GlobalParameterDetailsDto> gpdDtoList = new ArrayList<>();
		LanguageSpecificGlobalActivityDetails activity = null;
		InternationalizaionLanguages inlg = null;
		Map<Long, List<LanguageSpecificGroupDetails>> lspgMap = null;
		Map<Long, List<StudyActivityParameters>> sapMap = null;
		Map<Long, LanguageSpecificGlobalParameterDetails> gpMap = null;
		Map<Long,List<LanguageSpecificValueDetails>> parameterValueList = null;
		List<StudyActivities> saList = null;
		CustomActivityParameters cap = null;
		try {
			saDto = sudyActivityDao.getStudyActivityDtoDetails(activityId, languageId, studyId);
			if(saDto != null) {
				saList = saDto.getSaList();
				sapMap = saDto.getSapMap();
				gpMap = saDto.getGpMap();
				lspgMap = saDto.getLspgMap();
				cap = saDto.getCustmActParm();
				parameterValueList = saDto.getLanguageValues();
				inlg = saDto.getInlg();
				if(saList != null && saList.size() > 0) {
					activity = saDto.getLanSpecificAcitvity();
					for(StudyActivities sa : saList) {
						GlobalParameterDetailsDto gpdDto = new GlobalParameterDetailsDto();
						gpdDto.setStudyAtivityId(sa.getId());
						gpdDto.setActivityName(activity.getName());
						gpdDto.setActivityCode(activity.getGlobalActivity().getActivityCode());
						gpdDto.setActivityId(activity.getGlobalActivity().getId());
						gpdDto.setGetUrl(activity.getGlobalActivity().getGetUrl());
						gpdDto.setPostUrl(activity.getGlobalActivity().getSaveUrl());
						gpdDto.setShowSubjectField(activity.getGlobalActivity().isSubjectsInput());
						gpdDto.setPeriodId(sa.getStudyPeriod().getId());
						gpdDto.setTreatmentId(sa.getTreatment().getId());
						if(cap != null)
							gpdDto.setCustomParameterId(cap.getParameter().getId());
						List<ParameterFormDataDto> pfdDtoList = new ArrayList<>();
						List<StudyActivityParameters> sapList = sapMap.get(sa.getId());
						if(sapList != null && sapList.size() > 0) {
							for(StudyActivityParameters sap : sapList) {
								if(sap.getParameterId() != null) {
									LanguageSpecificGlobalParameterDetails lsgpd = gpMap.get(sap.getParameterId().getId());
									if(lsgpd != null) {
										List<LanguageSpecificGroupDetails> lsgdList = null;
										ParameterFormDataDto pfdto = new ParameterFormDataDto();
										pfdto.setParameterId(lsgpd.getParameterId().getId());
										pfdto.setParameterName(lsgpd.getName());
										pfdto.setOrderNo(lsgpd.getParameterId().getOrderNo());
										pfdto.setMandatory(lsgpd.getParameterId().isMandatory());
										if(lsgpd.getParameterId().getGroups() != null)
											lsgdList = lspgMap.get(lsgpd.getParameterId().getGroups().getId());
//										List<FormGroupsDto> fgDtoList = new ArrayList<>();
										LanguageSpecificGroupDetails lsgd = null;
										if(lsgdList != null && lsgdList.size() > 0) {
											lsgd = lsgdList.get(0);
										}
										FormGroupsDto fgdto = new FormGroupsDto();
										if(sap.getParameterId().getGroups() != null) {
											fgdto.setGroupId(sap.getParameterId().getGroups().getId());
											fgdto.setGroupName(sap.getParameterId().getGroups().getName());	
											fgdto.setOrderNo(sap.getParameterId().getGroups().getOrderNo());
										}else {
											fgdto.setGroupId(0L);
											fgdto.setGroupName("");
											fgdto.setOrderNo(0);
										}
										pfdto.setGroup(fgdto);
										String controlType = lsgpd.getParameterId().getContentType().getCode();
										FromControlDto fcDto = null;
										List<FromValuesDto> valuesDto = new ArrayList<>();
										if(controlType.equals("SB") || controlType.equals("RB") || controlType.equals("CB")) {
											fcDto = new FromControlDto();
											List<LanguageSpecificValueDetails> gvList = parameterValueList.get(lsgpd.getParameterId().getId()); //sudyActivityDao.getGlobalValuesList(lsgpd.getParameterId().getId(),lsgpd.getParameterId().getContentType().getId(), inlg);
											//List<LanguageSpecificValueDetails> gvList = sudyActivityDao.getGlobalValuesList(lsgpd.getParameterId().getId(),lsgpd.getParameterId().getContentType().getId(), inlg);
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
										pfdDtoList.add(pfdto);
									}
									Collections.sort(pfdDtoList);
									gpdDto.setParameterDto(pfdDtoList);
								}
							}
						}
						gpdDtoList.add(gpdDto);
					}					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gpdDtoList;
	}
	
	@Override
	public MessageDto saveStudyActivityFromsData(SubjectActivityData sad,
			MessageSource messageSource, Locale currentLocale, String userName,Long languageId, String dateFormat, String dateStrWithTime) {
		MessageDto mdto = null;
		List<StudyActivityParameters> sapList = null;
		StudyPeriodMaster spm = null;
		List<ParameterControlTypeValues> pctvList = null;
		StudyActivities stdActivity = null;
		StudyMaster sm = null;
		StudyActivityFormDataDto safDto = null;
		Map<Long, StudyActivityParameters> sapMap = new HashMap<>();
		Map<Long, ParameterControlTypeValues> gpMap = new HashMap<>();
		Map<Long, GlobalValues> gvMap = new HashMap<>();
		List<StudyCheckInActivityDataDetails> schinSaveList = new ArrayList<>();
		List<StudyCheckOutActivityDataDetails> schoutSaveList = new ArrayList<>();
		List<StudyExecutionActivityDataDetails> sexeSaveList = new ArrayList<>();
		List<Long> parmIdsList = new ArrayList<>();
		Map<Long, String> parmMapVals = new HashMap<>();
		Map<Long, Blob> parmBlobs = new HashMap<>();
		StudyVolunteerReporting svr = null;
		StudySubjects subject = null;
		List<ParameterModelDto> paramList = sad.getPmDto();
 		Long activityId = sad.getActivityId();
		Long studyId = sad.getStudyId();
		Long volId = sad.getVolId();
		String type = sad.getType();
		StudyGroupPeriodMaster sgpm = null;
		boolean subjectAllot = false;
		DefaultActivitys dfact = null;
		Long elegibleForNextProcessParameterId = null;
		try {
			if(paramList != null && paramList.size() > 0) { //ParamList contains parmeter and that parmeter value
				for(ParameterModelDto pmd : paramList) {
					parmIdsList.add(pmd.getParameterId());
					if(pmd.getParameterValue() != null && !pmd.getParameterValue().equals(""))
						parmMapVals.put(pmd.getParameterId(), pmd.getParameterValue());
					/*if(pmd.getFile() != null) {
						byte[] fileContent = new byte[(int) pmd.getFile().length()];
						Blob blob = new SerialBlob(fileContent);
						parmBlobs.put(pmd.getParameterId(), blob);
					}*/
						
				}
				
			}else if(sad.getParameterValuesMap() != null){
				Map<Long, List<String>> peramValues = sad.getParameterValuesMap();
				peramValues.forEach((peramId, values) -> {
					parmIdsList.add(peramId);
					List<String> valueList = values;
					StringBuffer sb = new StringBuffer();
					boolean flag = false;
					for(String value : valueList) {
						if(flag)
							sb.append(",").append(value);
						else {
							sb.append(value); flag= true;
						}
					}
					parmMapVals.put(peramId, sb.toString());
				});
			}
			if(parmIdsList != null && parmIdsList.size() > 0) {
				safDto = sudyActivityDao.getStudyActivityFormDataDetails(activityId, sad.getStudyActivityId(), studyId, volId, type, parmIdsList);
				if(safDto != null) {
					sapList = safDto.getSapList();
					stdActivity = safDto.getStdActivity();
					spm = safDto.getSpm();
					sm = safDto.getSm();
					svr = safDto.getVolId();
					subject = safDto.getSubject();
					pctvList = safDto.getPctvList();
					sgpm = safDto.getSgpm();
					elegibleForNextProcessParameterId = safDto.getElegibleForNextProcessParameterId();
					
					String message = sudyActivityDao.CheckDependentActivitiesDataEntryCompletion(sm, activityId, svr, 
								subject, messageSource, languageId, spm, currentLocale, sad.getStudyActivityId());
					if(message == "") {
						if(sapList != null && sapList.size() > 0) {
							for(StudyActivityParameters sap : sapList) {
								sapMap.put(sap.getParameterId().getId(), sap);
							}
						}
						if(pctvList != null && pctvList.size() > 0) {
							for(ParameterControlTypeValues gp : pctvList) {
								gpMap.put(gp.getGlobalParameter().getId(), gp);
								gvMap.put(gp.getGlobalValues().getId(), gp.getGlobalValues());
							}
						}
						DraftReviewStage workFlow =	workflowService.GetFirstWorkflow(WorkFlowTypes.StudyDataReview);
						StudyActivityData sadata = new StudyActivityData();
						sadata.setCreatedBy(userName);
						sadata.setCreatedOn(new Date());
//						sadata.setStatus(status);
						sadata.setStdGoupPeriod(sgpm);
						sadata.setSutydActivity(stdActivity);
						sadata.setSubjectId(subject);
						sadata.setVolunteerId(svr);
						if(workFlow!=null) {
							sadata.setRoleId(workFlow.getToRole().getId());	
						}
						/*if(sad.getActivitySartDate() != null && !sad.getActivitySartDate().equals("")) {
							SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
							Date stDate = sdf2.parse(sad.getActivitySartDate());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String dateStr = sdf.format(stDate);
							Date startDate = sdf.parse(dateStr);
							sadata.setActvityStartDate(startDate);
						}*/
						
						SimpleDateFormat startTimeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String dateStr = startTimeDateFormat.format(sad.getActivitySartDate());
						Date startDate = startTimeDateFormat.parse(dateStr);
						sadata.setActvityStartDate(startDate);
						String phase = "";
						dfact = sudyActivityDao.getDefaultActivityRecod(stdActivity.getActivityId());
						if(sad.getSaveTable() != null && !sad.getSaveTable().equals("")) {
							phase = sad.getSaveTable();
						}
						if(dfact != null) {
							phase = dfact.getStudyPhases().getCode();
							if(dfact.getSubjectAllotment().equals("Yes"))
								subjectAllot = true;
						}
						for(Long asp : parmIdsList) {
							StudyActivityParameters sap = sapMap.get(asp);
							
							if(phase.equals("SCI") || phase.equalsIgnoreCase("P1O")) {
								StudyCheckInActivityDataDetails scadd = new StudyCheckInActivityDataDetails();
								scadd.setCreatedBy(userName);
								scadd.setCreatedOn(new Date());
								scadd.setDeviation(false);
								scadd.setSaParameter(sapMap.get(sap.getParameterId().getId()));
								if(gpMap.get(sap.getParameterId().getId()) !=null) {
									String paramCode = gpMap.get(sap.getParameterId().getId()).getControlType().getCode();
									if(paramCode.equals(ControlTypes.RB.toString()) || paramCode.equals(ControlTypes.CB.toString()) || paramCode.equals(ControlTypes.SB.toString())) {
//										scadd.setGlobalValues(gpMap.get(sap.getParameterId().getId()).getGlobalValues());
										scadd.setGlobalValues(gvMap.get(Long.parseLong(parmMapVals.get(sap.getParameterId().getId()))));
									}else if(paramCode.equals(ControlTypes.ET.toString())){
										String parmVal = String.valueOf(gpMap.get(sap.getParameterId().getId()).getGlobalValues().getId());
										if(parmVal != null && !parmVal.equals("")) {
											SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
											Date endDate = startTimeDateFormat.parse(parmVal);
											scadd.setValue(sdf.format(endDate));
//											scadd.setValue(parmVal);
										}else {
											Date date = new Date();
											SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
											scadd.setValue(sdf.format(date));
										}
									}else if(paramCode.equals(ControlTypes.FU.toString())){
										Blob blob = null;
										if(parmMapVals.get(sap.getParameterId().getId()) != null) {
											scadd.setFile(parmBlobs.get(sap.getParameterId().getId()));
										}
									}else if(paramCode.equals(ControlTypes.ST.toString())){
										String parmVal = parmMapVals.get(sap.getParameterId().getId());
										if(parmVal != null && !parmVal.equals("")) {
											Date stDate = startTimeDateFormat.parse(parmVal);
											DateFormat df = new SimpleDateFormat("HH:mm");
								        	String timeString = df.format(startDate);
											scadd.setValue(timeString);
										}
									}else {
										scadd.setValue(parmMapVals.get(sap.getParameterId().getId()));
									}
								}else {
									if(sap.getParameterId().getContentType().getCode().equals(ControlTypes.ST.toString()) || sap.getParameterId().getContentType().getCode().equals(ControlTypes.ET.toString())) {
										String paramVal = parmMapVals.get(sap.getParameterId().getId());
										if(sap.getParameterId().getContentType().getCode().equals(ControlTypes.ET.toString())) {
											SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
											if(paramVal != null && !paramVal.equals("")) {
												Date endDate = startTimeDateFormat.parse(paramVal);
												scadd.setValue(sdf.format(endDate));
											}else {
												Date date = new Date();
												scadd.setValue(sdf.format(date));
											}
										}else {
											if(paramVal != null && !paramVal.equals("")) {
												Date stDate = startTimeDateFormat.parse(paramVal);
												DateFormat df = new SimpleDateFormat("HH:mm");
									        	String timeString = df.format(startDate);
												scadd.setValue(timeString);
											}
										}
									}else
										scadd.setValue(parmMapVals.get(sap.getParameterId().getId()));
										
								}
								schinSaveList.add(scadd);
							}else if(phase.equals("SCO")) {	
								StudyCheckOutActivityDataDetails scoutadd = new StudyCheckOutActivityDataDetails();
								scoutadd.setCreatedBy(userName);
								scoutadd.setCreatedOn(new Date());
								scoutadd.setDeviation(false);
								scoutadd.setSaParameter(sapMap.get(sap.getParameterId().getId()));
								if(gpMap.get(sap.getParameterId().getId()) !=null) {
									String paramCode = gpMap.get(sap.getParameterId().getId()).getControlType().getCode();
									if(paramCode.equals(ControlTypes.RB.toString()) || paramCode.equals(ControlTypes.CB.toString()) || paramCode.equals(ControlTypes.SB.toString())) {
//										scoutadd.setGlobalValues(gpMap.get(sap.getParameterId().getId()).getGlobalValues());
										scoutadd.setGlobalValues(gvMap.get(Long.parseLong(parmMapVals.get(sap.getParameterId().getId()))));
										
									}else if(paramCode.equals(ControlTypes.ET.toString())){
										String parmVal = String.valueOf(gpMap.get(sap.getParameterId().getId()).getGlobalValues().getId());
										SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
										if(parmVal != null && !parmVal.equals("")) {
											Date endDate = startTimeDateFormat.parse(parmVal);
											scoutadd.setValue(sdf.format(endDate));
										}else {
											Date date = new Date();
											scoutadd.setValue(sdf.format(date));
										}
									}else if(paramCode.equals(ControlTypes.FU.toString())){
										Blob blob = null;
										if(parmMapVals.get(sap.getParameterId().getId()) != null) {
											scoutadd.setFile(parmBlobs.get(sap.getParameterId().getId()));
										}
									}else if(paramCode.equals(ControlTypes.ST.toString())){
										String parmVal = parmMapVals.get(sap.getParameterId().getId());
										if(parmVal != null && !parmVal.equals("")) {
											Date stDate = startTimeDateFormat.parse(parmVal);
											DateFormat df = new SimpleDateFormat("HH:mm");
								        	String timeString = df.format(startDate);
								        	scoutadd.setValue(timeString);
										}
									}else {
										scoutadd.setValue(parmMapVals.get(sap.getParameterId().getId()));
									}
								}else {
									if(sap.getParameterId().getContentType().getCode().equals(ControlTypes.ST.toString()) || sap.getParameterId().getContentType().getCode().equals(ControlTypes.ET.toString())) {
										String paramVal = parmMapVals.get(sap.getParameterId().getId());
										if(sap.getParameterId().getContentType().getCode().equals(ControlTypes.ET.toString())) {
											SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
											if(paramVal != null && !paramVal.equals("")) {
												Date endDate = startTimeDateFormat.parse(paramVal);
												scoutadd.setValue(sdf.format(endDate));
											}else {
												Date date = new Date();
												scoutadd.setValue(sdf.format(date));
											}
										}else {
											if(paramVal != null && !paramVal.equals("")) {
												Date stDate = startTimeDateFormat.parse(paramVal);
												DateFormat df = new SimpleDateFormat("HH:mm");
									        	String timeString = df.format(startDate);
									        	scoutadd.setValue(timeString);
											}
										}
									}else
										scoutadd.setValue(parmMapVals.get(sap.getParameterId().getId()));
								}
								schoutSaveList.add(scoutadd);
							}else { //SE
								StudyExecutionActivityDataDetails sead = new StudyExecutionActivityDataDetails();
								sead.setCreatedBy(userName);
								sead.setCreatedOn(new Date());
								sead.setDeviation(false);
								sead.setSaParameter(sapMap.get(sap.getParameterId().getId()));
								if(gpMap.get(sap.getParameterId().getId()) !=null) {
									String paramCode = gpMap.get(sap.getParameterId().getId()).getControlType().getCode();
									if(paramCode.equals(ControlTypes.RB.toString()) || paramCode.equals(ControlTypes.CB.toString()) || paramCode.equals(ControlTypes.SB.toString())) {
//										sead.setGlobalValues(gpMap.get(sap.getParameterId().getId()).getGlobalValues());
										sead.setGlobalValues(gvMap.get(Long.parseLong(parmMapVals.get(sap.getParameterId().getId()))));
									}else if(paramCode.equals(ControlTypes.ET.toString())){
										String parmVal = String.valueOf(gpMap.get(sap.getParameterId().getId()).getGlobalValues().getId());
										SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
										if(parmVal != null && !parmVal.equals("")) {
											Date endDate = startTimeDateFormat.parse(parmVal);
											sead.setValue(sdf.format(endDate));
											sead.setValue(parmVal);
										}else {
											Date date = new Date();
											sead.setValue(sdf.format(date));
										}
									}else if(paramCode.equals(ControlTypes.FU.toString())){
										Blob blob = null;
										if(parmMapVals.get(sap.getParameterId().getId()) != null) {
											sead.setFile(parmBlobs.get(sap.getParameterId().getId()));
										}
									}else if(paramCode.equals(ControlTypes.ST.toString())){
										String parmVal = parmMapVals.get(sap.getParameterId().getId());
										if(parmVal != null && !parmVal.equals("")) {
											Date stDate = startTimeDateFormat.parse(parmVal);
											DateFormat df = new SimpleDateFormat("HH:mm");
								        	String timeString = df.format(startDate);
								        	sead.setValue(timeString);
										}
									}else {
										sead.setValue(parmMapVals.get(sap.getParameterId().getId()));
									}
								}else {
									if(sap.getParameterId().getContentType().getCode().equals(ControlTypes.ST.toString()) || sap.getParameterId().getContentType().getCode().equals(ControlTypes.ET.toString())) {
										String paramVal = parmMapVals.get(sap.getParameterId().getId());
										if(sap.getParameterId().getContentType().getCode().equals(ControlTypes.ET.toString())) {
											SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
											if(paramVal != null && !paramVal.equals("")) {
												Date endDate = startTimeDateFormat.parse(paramVal);
												sead.setValue(sdf.format(endDate));
											}else {
												Date date = new Date();
												sead.setValue(sdf.format(date));
											}
										}else {
											if(paramVal != null && !paramVal.equals("")) {
												Date stDate = startTimeDateFormat.parse(paramVal);
												DateFormat df = new SimpleDateFormat("HH:mm");
									        	String timeString = df.format(startDate);
									        	sead.setValue(timeString);
											}
										}
									}else
										sead.setValue(parmMapVals.get(sap.getParameterId().getId()));
								}
								sexeSaveList.add(sead);
								
							}
						}
						mdto  = sudyActivityDao.saveStudyActivityDataDetails(sadata, schinSaveList, schoutSaveList, sexeSaveList, sad.getTimePointId(), subjectAllot, 
								userName, sad, dateFormat, elegibleForNextProcessParameterId, parmMapVals);
					}else {
						mdto = new MessageDto();
						mdto.setMealsMsg(message);
						mdto.setMsgFlag(false);
					}
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return mdto;
}

	@Override
	public StudyDesingActivityDetailsDto getStudyDesingActivityDetailsDtoDetails(Long activityId,
			Long languageId) {
		StudyDesingActivityDetailsDto sdadDto = null;
		StudyDesingDto sdDto = null;
		List<LanguageSpecificGlobalParameterDetails> lsgpdList = null;
		GlobalActivity activity = null;
		try {
			sdDto = sudyActivityDao.getStudyDesingDtoDetails(activityId, languageId);
			if(sdDto != null) {
				activity = sdDto.getActivity();
				lsgpdList = sdDto.getLsgpdList();
				if(lsgpdList != null) {
					List<StudyDesignParametersDto> sdpList = new ArrayList<>();
					sdadDto = new StudyDesingActivityDetailsDto();
					sdadDto.setActivityId(activity.getId());
					sdadDto.setActivityName(activity.getName());
					for(LanguageSpecificGlobalParameterDetails lsgp : lsgpdList) {
						StudyDesignParametersDto sdpDto = new StudyDesignParametersDto();
						sdpDto.setParameterId(lsgp.getParameterId().getId());
						sdpDto.setParameterName(lsgp.getName());
						sdpDto.setOrderNo(lsgp.getParameterId().getOrderNo());
						sdpList.add(sdpDto);
					}
					Collections.sort(sdpList);
					sdadDto.setParameterDto(sdpList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdadDto;
	}
	
	@Override
	public StudyDesingActivityDetailsDto getActivityParamters(Long activityId,
			Long languageId) {
		StudyDesingActivityDetailsDto sdadDto = null;
		StudyDesingDto sdDto = null;
		List<LanguageSpecificGlobalParameterDetails> lsgpdList = null;
		GlobalActivity activity = null;
		try {
			sdDto = sudyActivityDao.getActivityParamters(activityId, languageId);
			if(sdDto != null) {
				activity = sdDto.getActivity();
				lsgpdList = sdDto.getLsgpdList();
				if(lsgpdList != null) {
					List<StudyDesignParametersDto> sdpList = new ArrayList<>();
					sdadDto = new StudyDesingActivityDetailsDto();
					sdadDto.setActivityId(activity.getId());
					sdadDto.setActivityName(activity.getName());
					for(LanguageSpecificGlobalParameterDetails lsgp : lsgpdList) {
						StudyDesignParametersDto sdpDto = new StudyDesignParametersDto();
						sdpDto.setParameterId(lsgp.getParameterId().getId());
						sdpDto.setParameterName(lsgp.getName());
						sdpDto.setOrderNo(lsgp.getParameterId().getOrderNo());
						sdpDto.setMandatory(lsgp.getParameterId().isMandatory());
						sdpList.add(sdpDto);
					}
					Collections.sort(sdpList);
					sdadDto.setParameterDto(sdpList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdadDto;
	}


	@Override
	public StudyActivitiesSavingDto saveStudyDesignActivityDetails(StudyMaster study, Map<Integer, List<ProjectsDetails>> rescompMap,
			Map<Integer, List<ProjectsDetails>> inclusionMap, Map<Integer, List<ProjectsDetails>> exclusionMap, Map<Long, List<Long>> defaultActMap)throws Exception {
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
			sasDto = sudyActivityDao.getStudyActivitySavingDtoDetailsForStudyActivities(parmIds, actIds, study);
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
				defalultGp = sudyActivityDao.getGlobalPrameterRecord(defaultIdsForIncAndExcId);
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


	@Override
	public GlobalparameterFromDto globalparameterFromDtoerFromDto(Long activityId, Long languageId, Long studyId,
			Locale currentLocale) {
		GlobalparameterFromDto gpfdto = new GlobalparameterFromDto();
		List<GlobalParameterDetailsDto> gpdDtoList = getStudyActivityDetails(activityId, languageId, studyId);
		RulesDetails rules = rulesService.getRulesDetails(activityId, languageId);
		gpfdto = new GlobalparameterFromDto();
		gpfdto.setGpdDtoList(gpdDtoList);
		gpfdto.setRules(rules);
		return gpfdto;
	}



	@Override
	public GlobalparameterFromDto getLanguageSpecificGlobalParameters(List<Long> doseIds, Long languageId, long actId) {
		return sudyActivityDao.getLanguageSpecificGlobalParameters(doseIds, languageId, actId);
	}
}
