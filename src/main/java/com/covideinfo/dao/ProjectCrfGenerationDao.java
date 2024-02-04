package com.covideinfo.dao;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.covideinfo.dto.BlankPdfDto;
import com.covideinfo.dto.InclusionDto;
import com.covideinfo.dto.PdfGenerationDetailsDto;
import com.covideinfo.model.ApplicationConfiguration;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalGroups;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.Projects;

public interface ProjectCrfGenerationDao {

	
	Projects getProjectsRecord(Long projectNo);

	PdfGenerationDetailsDto getPdfGenerationDetailsDtoDetails(List<Long> actIds, List<Long> parmIds,
			Long langId);

	Map<Long, List<LanguageSpecificGlobalParameterDetails>> getLanguageSpecificParmeterDetails(
			Map<Long, List<Long>> resParamMap, InternationalizaionLanguages inlag);

	List<LanguageSpecificGlobalParameterDetails> getNotExistingParameterList(
			LanguageSpecificGlobalActivityDetails incAct, List<Long> defalutParmIds,
			InternationalizaionLanguages inlag);

	Map<Long, Map<LanguageSpecificGlobalActivityDetails, List<LanguageSpecificGlobalParameterDetails>>> getProjectDetailsPdfGenerationDtoForDefaultActivities(
			List<Long> defaultActIds, InternationalizaionLanguages inlag);

	List<Projects> getProjectsRecords();

	List<LanguageSpecificValueDetails> getLanguageSpecificGlobalValusesList(GlobalParameter parameterId, InternationalizaionLanguages inalg);

	InclusionDto getGlobalParametersList(List<Long> parmIds, Long langId, String actrCode);

	List<LanguageSpecificGlobalParameterDetails> getDefaultParameterDetails(List<Long> parmIds,
			GlobalActivity globalActivity, Locale currentLocale);

	BlankPdfDto getBlankPdfDtoDetails(Long projectNo, String userName);

	String getLanguageSpecificGroupName(GlobalGroups groups, InternationalizaionLanguages inalg);

	LanguageSpecificGlobalParameterDetails getlanguageSpecificGlobalParameterDetails(Long paramId, InternationalizaionLanguages inlag);

	List<LanguageSpecificGlobalActivityDetails> getLanguageSpecificGlobalActivitiesBasedOnCode(Long langId);

	List<DefaultActivitys> getDefaultActivitiesList();

	ApplicationConfiguration getApplicationConfigurationRecord(String string);

	List<LanguageSpecificGlobalParameterDetails> getLanguageSpecificGlobalParametersFromStudyActivitParameters(
			long activityId, InternationalizaionLanguages inalg);

	Map<String, String> getProjectTitleFromPojectDetails(Long projectNo);

	List<LanguageSpecificGlobalParameterDetails> getLanguageSpecificParameters(List<Long> paramIds,
			InternationalizaionLanguages inlag);

}
