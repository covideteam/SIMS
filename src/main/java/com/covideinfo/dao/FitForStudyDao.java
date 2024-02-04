package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.GlobalParameter;

public interface FitForStudyDao {

	CustomActivityParameters getCustomActivityParametersRecord(Long activityId);

	List<GlobalParameter> getGlobalparametersList(List<Long> parmIdsList);

	String inactiveVolunteerRecord(Long volId);

}
