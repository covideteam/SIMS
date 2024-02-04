package com.covideinfo.dao;

import java.util.List;
import java.util.Set;

import com.covideinfo.dto.CrfDataDto;
import com.covideinfo.dto.DataCrfDetailsPageDto;
import com.covideinfo.model.GlobalGroups;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.TreatmentInfo;

public interface DataCrfDao {

	DataCrfDetailsPageDto getDataCrfDetailsPageDtoDetails();

	DataCrfDetailsPageDto getStudyCrfDataDetails(Long studyId, Long languageId);

	CrfDataDto getStudyCrfDataDtoDetails(Long studyId, Long langId, Long userId);

	String getLanguageSpecificGroupName(GlobalGroups groups, Long langId);

	List<LanguageSpecificValueDetails> getLanguageSpecificGlobalValusesList(GlobalParameter parameterId, Long langId);

	LanguageSpecificGlobalParameterDetails getLanguageSpecificParametersDetailsRecord(Long langId, long id);

	List<LanguageSpecificGlobalParameterDetails> getLanguageSpecificGlobalParametersFromStudyActivitParameters(long id,
			Long langId, Set<Long> vtparamIds);

	TreatmentInfo getTreatmentInfoDetails(long l);

}
