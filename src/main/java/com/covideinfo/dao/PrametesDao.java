package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.dto.ActvitityDetailsDto;
import com.covideinfo.dto.GlobalParameterDto;
import com.covideinfo.dto.ParameterDto;
import com.covideinfo.model.DefaultActivityParameters;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.ParameterControlTypeValues;

public interface PrametesDao {

	GlobalParameterDto getGlobalParameterDtoDetails(Long langId);

	List<InternationalizaionLanguages> getInternationalizaionLanguages();

	GlobalParameterDto getGlobalParameterDetails(Long groupId, String controlId, List<Long> valIds, Long activityId,
			String bindVal, Long unitsId);

	String saveGlobalParameterRecords(GlobalParameter gp, List<LanguageSpecificGlobalParameterDetails> lsgpdList,
			List<ParameterControlTypeValues> pctvList);

	List<ParameterDto> getActivityDefaultParameters(Long activityId,Long langId);
	
	List<ParameterDto> getActivityDefaultParameters(Long[] activityIds, Long langId);

	GlobalParameter parameterNameCheckExitOrNot(String value);
}
