package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.StudyMealDietConfigDao;
import com.covideinfo.dto.MealDietPlanDto;
import com.covideinfo.dto.MealsInfoDetailsDto;
import com.covideinfo.dto.StudyMealTimePointDietDto;
import com.covideinfo.dto.StudyMealsDietConfiguraionDetailsDto;
import com.covideinfo.dto.StudyMealsDietConfiguraionDto;
import com.covideinfo.dto.StudyPeriodsDto;
import com.covideinfo.model.MealDietPlan;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.StudyMealTimePointDiet;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.service.StudyMealDietConfigService;

@Service("studyMealDietConfigService")
public class StudyMealDietConfigServiceImpl implements StudyMealDietConfigService {
	
	@Autowired
	StudyMealDietConfigDao studyMealDietConfigDao;

	/*
	 This method used to customize the data as per our need and place into dto and passes this data to controller
	 */
	@Override
	public StudyMealsDietConfiguraionDto getStudyMealsDietConfiguraionDtoDetails() {
		StudyMealsDietConfiguraionDetailsDto smdcdDto = null;
		StudyMealsDietConfiguraionDto smdDto = null;
		try {
			smdcdDto = studyMealDietConfigDao.getStudyMealsDietConfiguraionDtoDetails();
			smdDto = new StudyMealsDietConfiguraionDto();
			smdDto.setSmList(smdcdDto.getSmList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return smdDto;
	}

	@Override
	public StudyMealsDietConfiguraionDto getStudyRelatedMealsConfigurationDetails(Long studyId) {
		StudyMealsDietConfiguraionDetailsDto smdcdDto = null;
		StudyMealsDietConfiguraionDto smdDto = null;
		List<StudyPeriodMaster> spmList = null;
		List<StudyPeriodsDto> spmDtoList = new ArrayList<>();
		List<MealDietPlan> mdplanList = null;
		Map<String, List<MealDietPlanDto>> mealPlanMap = new HashMap<>();//MealType, MealDietPlanDto
		List<MealDietPlanDto> tempMealPlanList = null;
		List<MealsTimePoints> mealstpList = null;
		List<MealsInfoDetailsDto> mealDtoList = new ArrayList<>();
		List<StudyMealTimePointDiet> smtpDietList = null;
		Map<Long, Map<String, Long>> mealTPDietMap = new HashMap<>();//PeriodId, MealTimePointId, MealDietId
		Map<Long, String> mealTPStrDietMap = new HashMap<>();//PeriodId, mealTimePoint, MealDietId
		Map<String, Long> mealtpDietTempMap = null;
		Long treatmentId = null;
		try {
			smdcdDto = studyMealDietConfigDao.getStudyRelatedMealsConfigurationDetails(studyId);
			if(smdcdDto != null) {
				spmList = smdcdDto.getSpmList();
				mdplanList = smdcdDto.getMdplanList();
				mealstpList = smdcdDto.getMealstpList();
				smtpDietList = smdcdDto.getSmtpDietList();
				treatmentId = smdcdDto.getTreatmentId();
			}
			if(spmList != null && spmList.size() > 0) {
				for(StudyPeriodMaster spm : spmList) {
					StudyPeriodsDto spmDto = new StudyPeriodsDto();
					spmDto.setId(spm.getId());
					spmDto.setPeriodName(spm.getPeriodName());
					spmDto.setPeriodNo(spm.getPeriodNo());
					spmDtoList.add(spmDto);
				}
			}
			if(mdplanList != null && mdplanList.size() > 0) {
				for(MealDietPlan mdpl : mdplanList) {
					MealDietPlanDto mpid = new MealDietPlanDto();
					mpid.setId(mdpl.getId());
					mpid.setMealTitle(mdpl.getMealTitle());
					mpid.setMealType(mdpl.getMealType().getCode());
					if(mealPlanMap.containsKey(mpid.getMealType())) {
						tempMealPlanList = mealPlanMap.get(mpid.getMealType());
						tempMealPlanList.add(mpid);
						mealPlanMap.put(mdpl.getMealType().getCode(), tempMealPlanList);
					}else {
						tempMealPlanList = new ArrayList<>();
						tempMealPlanList.add(mpid);
						mealPlanMap.put(mdpl.getMealType().getCode(), tempMealPlanList);
					}
					
				}
			}
			if(mealstpList != null && mealstpList.size() > 0) {
				List<String> mealsTps = new ArrayList<>();
				for(MealsTimePoints mtp : mealstpList) {
					if(!mealsTps.contains(mtp.getSign()+mtp.getTimePoint())) {
						MealsInfoDetailsDto minfDto = new MealsInfoDetailsDto();
						minfDto.setId(mtp.getId());
						minfDto.setMealType(mtp.getMealsType().getCode());
						minfDto.setSign(mtp.getSign());
						minfDto.setTimePoint(mtp.getTimePoint());
						mealDtoList.add(minfDto);
						mealsTps.add(mtp.getSign()+mtp.getTimePoint());
						
					}
				}
			}
			/*Here we are considering only one treatment(if any treatment) time point to display*/
//			Long treatmentId = 0L;
			if(smtpDietList != null && smtpDietList.size() > 0) {
				for(StudyMealTimePointDiet smtpd : smtpDietList) {
					/*if(treatmentId == 0)
						treatmentId = smtpd.getMealtimePoint().getTreatmentInfo().getId();*/
					if(treatmentId == smtpd.getMealtimePoint().getTreatmentInfo().getId()) {
						if(mealTPStrDietMap.containsKey(smtpd.getStudyPeriod().getId())) {
							String tpStr = mealTPStrDietMap.get(smtpd.getStudyPeriod().getId());
							if(tpStr != null && !tpStr.equals("")) {
								if(!(smtpd.getMealtimePoint().getSign()+smtpd.getMealtimePoint().getTimePoint()).equals(tpStr)) {
									if(mealTPDietMap.containsKey(smtpd.getStudyPeriod().getId())) {
										mealtpDietTempMap = mealTPDietMap.get(smtpd.getStudyPeriod().getId());
										mealtpDietTempMap.put(smtpd.getMealtimePoint().getId()+"_"+smtpd.getMealtimePoint().getMealsType().getCode(), smtpd.getMealDietPlan().getId());
										mealTPDietMap.put(smtpd.getStudyPeriod().getId(), mealtpDietTempMap);
									}else {
										mealtpDietTempMap = new HashMap<>();
										mealtpDietTempMap.put(smtpd.getMealtimePoint().getId()+"_"+smtpd.getMealtimePoint().getMealsType().getCode(), smtpd.getMealDietPlan().getId());
										mealTPDietMap.put(smtpd.getStudyPeriod().getId(), mealtpDietTempMap);
									}
									mealTPStrDietMap.put(smtpd.getStudyPeriod().getId(), smtpd.getMealtimePoint().getSign()+smtpd.getMealtimePoint().getTimePoint());
								
								}
							}else {
								if(mealTPDietMap.containsKey(smtpd.getStudyPeriod().getId())) {
									mealtpDietTempMap = mealTPDietMap.get(smtpd.getStudyPeriod().getId());
									mealtpDietTempMap.put(smtpd.getMealtimePoint().getId()+"_"+smtpd.getMealtimePoint().getMealsType().getCode(), smtpd.getMealDietPlan().getId());
									mealTPDietMap.put(smtpd.getStudyPeriod().getId(), mealtpDietTempMap);
								}else {
									mealtpDietTempMap = new HashMap<>();
									mealtpDietTempMap.put(smtpd.getMealtimePoint().getId()+"_"+smtpd.getMealtimePoint().getMealsType().getCode(), smtpd.getMealDietPlan().getId());
									mealTPDietMap.put(smtpd.getStudyPeriod().getId(), mealtpDietTempMap);
								}
								mealTPStrDietMap.put(smtpd.getStudyPeriod().getId(), smtpd.getMealtimePoint().getSign()+smtpd.getMealtimePoint().getTimePoint());
							}
							
						}else {
							if(mealTPDietMap.containsKey(smtpd.getStudyPeriod().getId())) {
								mealtpDietTempMap = mealTPDietMap.get(smtpd.getStudyPeriod().getId());
								mealtpDietTempMap.put(smtpd.getMealtimePoint().getId()+"_"+smtpd.getMealtimePoint().getMealsType().getCode(), smtpd.getMealDietPlan().getId());
								mealTPDietMap.put(smtpd.getStudyPeriod().getId(), mealtpDietTempMap);
							}else {
								mealtpDietTempMap = new HashMap<>();
								mealtpDietTempMap.put(smtpd.getMealtimePoint().getId()+"_"+smtpd.getMealtimePoint().getMealsType().getCode(), smtpd.getMealDietPlan().getId());
								mealTPDietMap.put(smtpd.getStudyPeriod().getId(), mealtpDietTempMap);
							}
							mealTPStrDietMap.put(smtpd.getStudyPeriod().getId(), smtpd.getMealtimePoint().getSign()+smtpd.getMealtimePoint().getTimePoint());
						}
					}
					
					/*if(mealTPDietMap.containsKey(smtpd.getStudyPeriod().getId())) {
						mealtpDietTempMap = mealTPDietMap.get(smtpd.getStudyPeriod().getId());
						mealtpDietTempMap.put(smtpd.getMealtimePoint().getId()+"_"+smtpd.getMealtimePoint().getMealsType().getCode(), smtpd.getMealDietPlan().getId());
						mealTPDietMap.put(smtpd.getStudyPeriod().getId(), mealtpDietTempMap);
					}else {
						mealtpDietTempMap = new HashMap<>();
						mealtpDietTempMap.put(smtpd.getMealtimePoint().getId()+"_"+smtpd.getMealtimePoint().getMealsType().getCode(), smtpd.getMealDietPlan().getId());
						mealTPDietMap.put(smtpd.getStudyPeriod().getId(), mealtpDietTempMap);
					}*/
				}
			}
			
			smdDto = new StudyMealsDietConfiguraionDto();
			smdDto.setStudyPeriods(spmDtoList);
			smdDto.setMealDtoList(mealDtoList);
			smdDto.setMealPlanMap(mealPlanMap);
			smdDto.setMealTPDietMap(mealTPDietMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return smdDto;
	}

	@Override
	public boolean saveStudyMealDietCofigurationData(Long projectId, List<String> mealdietList, String userName) {
		boolean flag = false;
		List<StudyMealTimePointDietDto> smtpdDtoList = new ArrayList<>();
		try {
			if(mealdietList.size() > 0) {
				for(String st : mealdietList) {
					String[] arr = st.split("\\@@@");
					if(arr.length > 0) {
						if((arr[0] != null && !arr[0].equals("")) && (arr[1] != null && !arr[1].equals("")) && (arr[2] != null && !arr[2].equals(""))) {
							StudyMealTimePointDietDto smtpdDto = new StudyMealTimePointDietDto();
							smtpdDto.setCreatedOn(new Date());
							smtpdDto.setPeriodId(Long.parseLong(arr[0]));
							smtpdDto.setMealId(Long.parseLong(arr[1]));
							smtpdDto.setDietId(Long.parseLong(arr[2]));
							smtpdDto.setUserName(userName);
							smtpdDtoList.add(smtpdDto);
						}
					}
				}
			}
			if(smtpdDtoList.size() > 0) {
				flag = studyMealDietConfigDao.saveStudyMealTimePointDiet(projectId, smtpdDtoList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
