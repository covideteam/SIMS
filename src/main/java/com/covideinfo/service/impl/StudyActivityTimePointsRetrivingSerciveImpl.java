package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.StudyActivityTimePointsRetrivingDao;
import com.covideinfo.dto.FormGroupsDto;
import com.covideinfo.dto.FromControlDto;
import com.covideinfo.dto.FromValuesDto;
import com.covideinfo.dto.GlobalParameterDetailsDto;
import com.covideinfo.dto.ParameterFormDataDto;
import com.covideinfo.dto.StudyActivityTimePointsDto;
import com.covideinfo.dto.StudyActivityTimePointsRetrivingDto;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.StudyActivityTimePoints;
import com.covideinfo.model.StudyActivityTimePointsCompletionData;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.VitalTimePoints;
import com.covideinfo.service.StudyActivityTimePointsRetrivingSercive;

@Service("studyActivityTimePointsRetrivingSerciveImpl")
public class StudyActivityTimePointsRetrivingSerciveImpl implements StudyActivityTimePointsRetrivingSercive {
	
	@Autowired
	StudyActivityTimePointsRetrivingDao satprDao;

	@Override
	public GlobalParameterDetailsDto getStudyActivityTimePointsDtoDetails(Long studyId, String subjectNo, Long activityId, long languageId) {
		StudyActivityTimePointsRetrivingDto satprDto = null;
		LanguageSpecificGlobalActivityDetails lsga = null;
		List<LanguageSpecificGlobalParameterDetails> lspgpdList = null;
		List<StudyActivityTimePoints> satpList = null;
		List<SubjectDoseTimePoints> dosetpList = null;
		StudyPeriodMaster spm = null;
		TreatmentInfo tfinfo = null;
		com.covideinfo.model.StudyActivities sa = null;
		GlobalActivity ga = null;
		VitalTimePoints vitalTimePoints = null;
		GlobalParameterDetailsDto gpdDto = new GlobalParameterDetailsDto();
		try {
			satprDto = satprDao.getStudyActivityTimePointsRetrivingDtoDetails(studyId, subjectNo, activityId, languageId);
			if(satprDto != null ) {
				lsga = satprDto.getLsga();
				lspgpdList = satprDto.getLspgpdList();
				satpList = satprDto.getSatpList();
				dosetpList = satprDto.getDosetpList();
				ga = satprDto.getGa();
				sa = satprDto.getSa();
				spm = satprDto.getSpm();
				tfinfo = satprDto.getTfinfo();
				List<StudyActivityTimePointsDto> satpDtoList = new ArrayList<>();
				if(lsga != null) {
						if(dosetpList != null && dosetpList.size() > 0) {
							List<String> subList = new ArrayList<>();
							for(SubjectDoseTimePoints sdt : dosetpList) {
								if(satpList != null && satpList.size() > 0) {
									for(StudyActivityTimePoints satp : satpList) {
										StudyActivityTimePointsCompletionData satpcData = satprDao.getStudyActivityTimePointsCompletionData(sa.getId(), satp.getId(), spm.getId(), tfinfo.getId(), studyId);
										if(satpcData == null) {
											StudyActivityTimePointsDto satpDto = new StudyActivityTimePointsDto();
											Calendar cal = Calendar.getInstance();
											cal.setTime(sdt.getActualTime());
											Date date1 = cal.getTime();
											String[] tempArr = satp.getTimePoint().split("\\.");
											int doseMinutes = 0;
											int doseHours = 0;
											if(tempArr.length > 0) {
												doseHours = Integer.parseInt(tempArr[0]) ;
												doseMinutes = (((Integer.parseInt(tempArr[1])*6)/100));
											}
											if(satp.getTimePointSign().equals("-")) {
												cal.add(Calendar.HOUR, -doseHours);
												cal.add(Calendar.MINUTE, -doseMinutes);
											}else {
												cal.add(Calendar.HOUR, doseHours);
												cal.add(Calendar.MINUTE, doseMinutes);
											}
											Date date2 = cal.getTime();
											int result = date1.compareTo(date2);
											boolean flag = false;
									        if (result == 0) { // present time
									        	flag = true;
									        }else if (result > 0) { //date1 is after date2
									            flag = true;
									        }
									        if(flag) {
									        	satpDto.setOrthoStatic(satp.getOrthoSatatic());
									        	satpDto.setOrthoStaticPosition(satp.getOrthoStaticPosition());
									        	satpDto.setPosition(satp.getPosition());
									        	satpDto.setSign(satp.getTimePointSign());
									        	satpDto.setTimePoint(satp.getTimePoint());
									        	satpDto.setWindowPeriod(satp.getWindowPeriod());
									        	satpDto.setWindowPeriodType(satp.getWindowPeriodType());
									        	if(ga.getActivityCode().equalsIgnoreCase(StudyActivities.DosingCollection.toString())) {
//									        		satpDto.setParameters(parameters);
									        	}else if(ga.getActivityCode().equalsIgnoreCase(StudyActivities.StudyExecutionVitals.toString())) {
									        		vitalTimePoints = satprDao.getDosingTimePointsRecord(satp.getTimePoint(), tfinfo, studyId);
									        		if(vitalTimePoints != null) {
									        			satpDto.setParameters(vitalTimePoints.getParameterIds());
									        		}
									        	}
									        	satpDtoList.add(satpDto);
									        }
										}
									}
								}
							}
							gpdDto.setStudyAtivityId(sa.getId());
							gpdDto.setActivityName(lsga.getName());
							gpdDto.setActivityCode(lsga.getGlobalActivity().getActivityCode());
							gpdDto.setActivityId(lsga.getGlobalActivity().getId());
							gpdDto.setGetUrl(lsga.getGlobalActivity().getGetUrl());
							gpdDto.setPostUrl(lsga.getGlobalActivity().getSaveUrl());
							List<ParameterFormDataDto> pfdDtoList = new ArrayList<>();
							if(satpList != null && satpList.size() > 0) {
								for(LanguageSpecificGlobalParameterDetails lsgpd : lspgpdList) {
									if(lsgpd != null) {
										LanguageSpecificGroupDetails lsg = null;
										ParameterFormDataDto pfdto = new ParameterFormDataDto();
										pfdto.setParameterId(lsgpd.getParameterId().getId());
										pfdto.setParameterName(lsgpd.getName());
										pfdto.setOrderNo(lsgpd.getParameterId().getOrderNo());
										if(lsgpd.getParameterId().getGroups() != null)
											lsg = satprDao.getGloablActivityDetails(lsgpd.getParameterId().getGroups().getId(), languageId);
										FormGroupsDto fgdto = new FormGroupsDto();
										if(lsgpd.getParameterId().getGroups() != null) {
											fgdto.setGroupId(lsgpd.getParameterId().getGroups().getId());
											fgdto.setGroupName(lsg.getName());	
											fgdto.setOrderNo(lsgpd.getParameterId().getGroups().getOrderNo());
										}else {
											fgdto.setGroupId(0L);
											fgdto.setGroupName("");
										}
										pfdto.setGroup(fgdto);
										String controlType = lsgpd.getParameterId().getContentType().getCode();
										FromControlDto fcDto = null;
										List<FromValuesDto> valuesDto = new ArrayList<>();
										if(controlType.equals("SB") || controlType.equals("RB") || controlType.equals("CB")) {
											fcDto = new FromControlDto();
											List<LanguageSpecificValueDetails> gvList = satprDao.getGlobalValuesList(lsgpd.getParameterId().getId(),lsgpd.getParameterId().getContentType().getId(), languageId);
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
								}
								Collections.sort(pfdDtoList);
								gpdDto.setParameterDto(pfdDtoList);
								gpdDto.setSatpDtoList(satpDtoList);
							}
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gpdDto;
	}

}
