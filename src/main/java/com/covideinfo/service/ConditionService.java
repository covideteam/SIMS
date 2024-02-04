package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.ConditionDto;
import com.covideinfo.model.ConditionParameter;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificCondition;

public interface ConditionService {

	ConditionDto getLanguageDetails(MessageSource messageSource,
			List<InternationalizaionLanguages> inLagList, Locale currentLocale, Long langId);

	String saveSubjectWithdrawActivity(String name, List<String> pvalList, List<Long> lagList, String userName, String dropDown);

	List<LanguageSpecificCondition> getLanguageSpecificConditions(Locale currentLocale);

	ConditionParameter getConditionParameterWithId(long id);

	boolean updateStatus(ConditionParameter cp);

	ConditionParameter conditionNameCheckExitOrNot(String value);
}
