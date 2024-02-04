package com.covideinfo.service;

import java.util.List;

import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;

public interface CustomActivityParameterService {

	List<GlobalActivity> getGlobalActivitiesList();

	String saveCustomActivityParameterRecord(Long activityId, Long parameterId, String userName, String parameterValue);

	List<CustomActivityParameters> getCustomActivityParametersList();

	GlobalParameter getGlobalParameterWithId(Long id);

	CustomActivityParameters checkActivityAndParameterExistingOrNot(GlobalActivity activity, GlobalParameter paramPojo);


}
