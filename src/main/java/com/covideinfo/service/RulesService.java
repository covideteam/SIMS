package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

import com.covideinfo.dto.ParameterDetailDto;
import com.covideinfo.dto.RulesDetails;
import com.covideinfo.dto.RulesDetailsDto;
import com.covideinfo.dto.RulesGlobalParameterDto;
import com.covideinfo.dto.ValuesDto;
import com.covideinfo.model.GlobalActivity;

public interface RulesService {
	
	RulesDetailsDto getRulesDetailsDtoDetails(Long langId);

	List<RulesGlobalParameterDto> getActivityParametersList(Long activityId, Long langId);

	List<ValuesDto> getValuesDetails(Long parameterId, Locale currentLocale, Long controlId);

	String saveActivityRules(Long sourceActivity, String ruleType, String validationCon, Long sourcePramId,
			String conditionVal, Long destActivity, String applyRuleFor, String depconditionVal, Long destRuleParam,
			String paramAction, List<Long> multiParam, String valueOne, String valueTwo, List<String> ruleMsgList,
			List<Long> lagList, Long checkedLspId, String userName, Long depParameter, Long checkedLspId2,
			Long selectboxVal, Long depcheckedLspId, String depvalueOne, String deparamconditionVal, String tbCondition,
			Long depselectboxVal, List<Long> destMultList, Long depcheckedLspId2);

	RulesDetails getRulesDetails(Long activityId, Long langId);

	List<GlobalActivity> getGlobalActivityList(Long acivityId);

	ParameterDetailDto getParameterType(Long parameterId, Long langId);

	

	

}
