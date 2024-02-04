package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.StudySubjectMealConsumptionDao;
import com.covideinfo.dto.MealDietPlanItemDto;
import com.covideinfo.dto.StudyMealConsumptionDietDto;
import com.covideinfo.dto.StudyMealConsumptionItemsDataDto;
import com.covideinfo.dto.StudyMealsConsumptionDietDtoDetails;
import com.covideinfo.service.StudySubjectMealConsumptionService;

@Service("studySubjectMealConsumptionService")
public class StudySubjectMealConsumptionServiceImpl implements StudySubjectMealConsumptionService {

	@Autowired
	StudySubjectMealConsumptionDao stdSubMealConsumptionDao;

	@Override
	public StudyMealConsumptionDietDto getStudyMealConsumptionDietDtoDetails() {
		return stdSubMealConsumptionDao.getStudyMealConsumptionDietDtoDetails();
	}

	/*This method getting the information of meals related data of already collected subject form data base and other related data form database
	 * And that data will be customized here to as per our display customization*/
	@Override
	public StudyMealConsumptionDietDto getStudyRelatedMealsConsumptionDietData(Long studyId) {
		StudyMealConsumptionDietDto smcdDto = null;
		StudyMealsConsumptionDietDtoDetails smcDeitDto = null;
		List<MealDietPlanItemDto> mealdietList = null;
		Map<Long, List<MealDietPlanItemDto>> mealDietItemsMap = new HashMap<>();//dietPlanId, MealDietPlanItemDto
		List<MealDietPlanItemDto> mdpTempList = null;
		try {
			smcDeitDto = stdSubMealConsumptionDao.getStudyMealsConsumptionDietDtoDetails(studyId);
			if(smcDeitDto != null) {
				mealdietList= smcDeitDto.getMealdietList();
				if(mealdietList != null && mealdietList.size() > 0) {
					for(MealDietPlanItemDto mdpid : mealdietList) {
						if(mealDietItemsMap.containsKey(mdpid.getMealDietplanId())) {
							mdpTempList = mealDietItemsMap.get(mdpid.getMealDietplanId());
							mdpTempList.add(mdpid);
							mealDietItemsMap.put(mdpid.getMealDietplanId(), mdpTempList);
						}else {
							mdpTempList = new ArrayList<>();
							mdpTempList.add(mdpid);
							mealDietItemsMap.put(mdpid.getMealDietplanId(), mdpTempList);
						}
					}
				}
			}
			
			smcdDto = new StudyMealConsumptionDietDto();
			smcdDto.setSubMealTpDataMap(smcDeitDto.getSubMealTpDataMap());
			smcdDto.setMealDietItemsMap(mealDietItemsMap);
			smcdDto.setMealDietPlanIdsMap(smcDeitDto.getMealDietPlanIdsMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smcdDto;
	}
	/*This method passes the data what we are getting form ui that will be passes to the dao layer to save the data*/
	@Override
	public String saveStudyMealConsumptionDietData(Long userId, StudyMealConsumptionItemsDataDto smcitemData) {
		return stdSubMealConsumptionDao.saveStudyMealConsumptionDietData(userId, smcitemData);
	}

	
}
