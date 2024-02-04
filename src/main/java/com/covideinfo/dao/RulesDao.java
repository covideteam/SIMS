package com.covideinfo.dao;

import java.util.List;
import java.util.Locale;

import com.covideinfo.dto.ParameterDetailDto;
import com.covideinfo.dto.RulesDetailsDto;
import com.covideinfo.dto.RulesDto;
import com.covideinfo.dto.RulesValuesDto;
import com.covideinfo.dto.StudyActivityRulesDetailsDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.StudyActivityRuleMessages;
import com.covideinfo.model.StudyActivityRules;

public interface RulesDao {
	
	RulesDetailsDto getRulesDetailsDtoDetails(Long langId);

	List<LanguageSpecificGlobalParameterDetails> getActivityParametersList(Long activityId, Long langId);

	RulesValuesDto getRulesValuesDtoDetails(Long parameterId, Locale currentLocale, Long controlId);

	RulesDto getRulesDtoDetails(Long sourceActivity, Long sourcePramId, List<Long> lagList);

	boolean saveRulesData(StudyActivityRules sarule, List<StudyActivityRuleMessages> sarmList);

	long saveRulesConditionData(StudyActivityRules sarule);

	RulesDto getRulesDtoDetailsForDependent(Long sourceActivity, Long sourcePramId, Long destActivity,
			Long destRuleParam, List<Long> multiParam);

	StudyActivityRulesDetailsDto getStudyActivityRulesDetailsDto(Long activityId, Long langId);

	List<LanguageSpecificGlobalParameterDetails> getParameterListBasedOnIdsList(String multiParam, Long lagnId);

	List<GlobalActivity> getGlobalActivityList(Long acivityId);

	RulesDto getRulesDtoForDepedentActivities(Long sourceActivity, List<Long> destMultList);

	List<GlobalActivity> getGlobalActivitiesListBasedOnIds(List<Long> depIds);

	LanguageSpecificValueDetails getLanguageSpecificValueDetailsWithId(Long checkedLspId);

	ParameterDetailDto getParameterType(Long parameterId, Long langId);

	GlobalValues getglobalValueRecord(Long checkedLspId);

	LanguageSpecificValueDetails getlsgvdetails(Long globalValId, Long langId);

	List<LanguageSpecificGlobalActivityDetails> getLanguageSpecificGlobalActivitiesList(List<Long> depIds,
			Long langId);

	RulesDto getRulesDtoDetailsForDependentActivities(Long sourceActivity, Long sourcePramId, List<Long> lagList,
			Long depParameter, List<Long> multiParam, Long destActivity, Long destRuleParam);

	LanguageSpecificGlobalParameterDetails getlanguageSpecificGlobalParameterDetails(GlobalParameter destParameter,
			Long langId);

	LanguageSpecificGlobalActivityDetails getlanguageSpecificGlobalActivitiesDetailsRecord(
			GlobalActivity destinationActivity, Long langId);

	

}
