package com.covideinfo.dao;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.DefaulltActparamDto;
import com.covideinfo.dto.GlobalparameterFromDto;
import com.covideinfo.dto.MessageDto;
import com.covideinfo.dto.StudyActivityDto;
import com.covideinfo.dto.StudyActivityFormDataDto;
import com.covideinfo.dto.StudyActivitySavingDto;
import com.covideinfo.dto.StudyDesingDto;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyActivityRules;
import com.covideinfo.model.StudyCheckInActivityDataDetails;
import com.covideinfo.model.StudyCheckOutActivityDataDetails;
import com.covideinfo.model.StudyExecutionActivityDataDetails;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.SubjectActivityData;
import com.covideinfo.model.TreatmentInfo;

public interface StudyActivityDao {

	StudyActivityDto getStudyActivityDtoDetails(Long activityId, Long languageId, Long studyId);

	boolean saveStudyActivities(StudyActivities sa, List<StudyActivityParameters> saveList);

	StudyActivityFormDataDto getStudyActivityFormDataDetails(Long activityId,Long studyActivityId, Long studyId, Long volId, String type, List<Long> parmIdsList);

	MessageDto saveStudyActivityDataDetails(StudyActivityData sadata, List<StudyCheckInActivityDataDetails> schinSaveList,
			List<StudyCheckOutActivityDataDetails> schoutSaveList, List<StudyExecutionActivityDataDetails> sexeSaveList, 
			Long timePointId, boolean subjectAllot, String userName,SubjectActivityData sad, String dateFormat, Long elegibleForNextProcessParameterId, Map<Long, String> parmMapVals);

	List<LanguageSpecificValueDetails> getGlobalValuesList(long paramId, long controlId, InternationalizaionLanguages inlg);

	DefaultActivitys getDefaultActivityRecod(GlobalActivity activityId);

	StudyDesingDto getStudyDesingDtoDetails(Long activityId, Long languageId);

	String CheckDependentActivitiesDataEntryCompletion(StudyMaster sm, Long activityId, StudyVolunteerReporting svr,
			StudySubjects subject, MessageSource messageSource, Long languageId, StudyPeriodMaster spm, Locale currentLocale,Long studyActivityId);

	String getActivitySubjects(Long studyId, TreatmentInfo treatmentInfo, StudyPeriodMaster studyPeriodMaster);

	StudyActivitySavingDto getStudyActivitySavingDtoDetailsForStudyActivities(List<Long> parmIds, List<Long> actIds, StudyMaster study);

	List<GlobalParameter> getDefaultParameterRecordsList(List<Long> defaultParmIds, GlobalActivity incAct);

	String saveStudyActivitiesAndRules(Map<StudyActivities, List<StudyActivityParameters>> saMap,
			Map<String, Map<Long, Map<Long, StudyActivityRules>>> saruleMap);

	Map<Long, List<GlobalParameter>> getDefaultActivityParameterDetailsForRestrictionCompliance(
			Map<Long, List<Long>> resDefalutMap);

	StudyDesingDto getActivityParamters(Long activityId, Long languageId);

	DefaulltActparamDto getDefaultParameterRecords(Long key, List<Long> value);

	GlobalparameterFromDto getLanguageSpecificGlobalParameters(List<Long> doseIds,
			Long languageId, long actId);

	StudyVolunteerReporting getVolunteerId(String st);

	GlobalParameter getGlobalPrameterRecord(Long defaultIdsForIncAndExcId);
}
