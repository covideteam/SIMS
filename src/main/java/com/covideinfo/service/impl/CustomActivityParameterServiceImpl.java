package com.covideinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.CustomActivityParameterDao;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.service.CustomActivityParameterService;

@Service("customActivityParameterService")
public class CustomActivityParameterServiceImpl implements CustomActivityParameterService {
	
	@Autowired
	CustomActivityParameterDao cusActParmDao;

	@Override
	public List<GlobalActivity> getGlobalActivitiesList() {
		return cusActParmDao.getGlobalActivitiesList();
	}

	@Override
	public String saveCustomActivityParameterRecord(Long activityId, Long parameterId, String userName,String parameterValue) {
		return cusActParmDao.saveCustomActivityParameterRecord(activityId, parameterId, userName,parameterValue);
	}

	@Override
	public List<CustomActivityParameters> getCustomActivityParametersList() {
		return cusActParmDao.getCustomActivityParametersList();
	}

	@Override
	public GlobalParameter getGlobalParameterWithId(Long id) {
		return cusActParmDao.getGlobalParameterWithId(id);
	}

	@Override
	public CustomActivityParameters checkActivityAndParameterExistingOrNot(GlobalActivity activity,
			GlobalParameter paramPojo) {
		return cusActParmDao.checkActivityAndParameterExistingOrNot(activity,paramPojo);
	}

}
