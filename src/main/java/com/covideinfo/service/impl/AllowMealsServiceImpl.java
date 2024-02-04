package com.covideinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.AllowMealsDao;
import com.covideinfo.dto.AllowMealsDataDto;
import com.covideinfo.dto.AllowMealsRecordsDto;
import com.covideinfo.dto.StudyMasterDto;
import com.covideinfo.service.AllowMealsService;

@Service("allowMealsService")
public class AllowMealsServiceImpl implements AllowMealsService {
	
	@Autowired
	AllowMealsDao allowMealDao;

	@Override
	public List<StudyMasterDto> getStudyMasterList() {
		return allowMealDao.getStudyMasterList();
	}

	@Override
	public AllowMealsRecordsDto getAllowMealsRecords(Long projectId) {
		return allowMealDao.getAllowMealsRecords(projectId);
	}

	@Override
	public String saveAllowMealsRecord(Long userId, AllowMealsDataDto almDto) {
		return allowMealDao.saveAllowMealsRecord(userId, almDto);
	}

	@Override
	public String inactiveAllowMealRecord(Long inactId, Long userId) {
		return allowMealDao.inactiveAllowMealRecord(inactId, userId);
	}

}
