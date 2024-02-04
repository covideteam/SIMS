package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.GlobalParameterDto;
import com.covideinfo.dto.ParameterDto;
import com.covideinfo.model.GlobalParameter;

public interface ParameterService {

	GlobalParameterDto getPrameterDetails(MessageSource messageSource, Locale currentLocale,Long langId);

	String saveGlobalParameterDetails(String name, List<String> pvalList, List<Long> lagList, Long groupId, String controlId, List<Long> valIds, String bindVal, 
			Long activityId, String userName, int order, String controlName,Long unitsId, List<String> phaseList, String paramMandatoryVal);

	List<ParameterDto> getActivityDefaultParameters(Long activityId,Long langId);
	
	List<ParameterDto> getActivityDefaultParameters(Long[] activityIds, Long langId);

	GlobalParameter parameterNameCheckExitOrNot(String value);
}
