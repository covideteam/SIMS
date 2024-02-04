package com.covideinfo.dao;

import java.util.List;
import java.util.Locale;

import com.covideinfo.dto.ConditionDto;
import com.covideinfo.model.ConditionParameter;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificCondition;

public interface ConditionDao {

	List<LanguageSpecificCondition> getLanguageSpecificSubjectWithdrawActivityList();

	List<InternationalizaionLanguages> getInternationalizaionLanguages();

	String saveSubjectWithdrawActivity(ConditionParameter swa,
			List<LanguageSpecificCondition> lsvdList);

	List<LanguageSpecificCondition> getLanguageSpecificConditions(Locale currentLocale);
	
	ConditionParameter getConditionParameterWithId(long id);

	boolean updateStatus(ConditionParameter cp);

	InternationalizaionLanguages getInternationalizaionLanguageRecord(Long langId);

	ConditionParameter conditionNameCheckExitOrNot(String value);
}
