package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.dto.DefaultActivityDto;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyActivityDataDetailsDiscrepancy;
import com.covideinfo.model.StudyActivityDataReviewAudit;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyCheckInActivityDataDetails;
import com.covideinfo.model.StudyCheckOutActivityDataDetails;
import com.covideinfo.model.StudyExecutionActivityDataDetails;
import com.covideinfo.model.StudyPhases;

public interface DefaultActivitysDao {

	List<GlobalActivity> getGlobalActivityList();

	List<GlobalParameter> getGlobalParameterList();

	List<StudyPhases> getStudyPhasesList();

	List<DefaultActivitys> getDefaultActivitysList();

	DefaultActivitys getDefaultActivitysExit(long actid, List<Long> paramid, long phaseid);

	List<StudyActivityData> getStudyActivityDataList();

	List<StudyCheckInActivityDataDetails> getStudyCheckInActivityDataDetailsWithIdList(long id);

	List<StudyCheckOutActivityDataDetails> getStudyCheckOutActivityDataDetailsWithIdList(long id);

	List<StudyExecutionActivityDataDetails> getStudyExecutionActivityDataDetailsWithIdList(long id);

	StudyActivityData getStudyActivityDataWithId(long id);

	StatusMaster getStatusMaster(long sid);

	boolean updateStudyActivityData(StudyActivityData saData);

	DraftReviewStage getDraftReviewStageWithFromRoleAndActionName(long id, String accept);

	boolean saveStudyActivityDataReviewAudit(StudyActivityDataReviewAudit sadra);

	DefaultActivitys getPahsesWithSAD_Id(StudyActivityData saData);

	StudyCheckInActivityDataDetails getStudyCheckInActivityDataDetailsWithSadIdAndSaddId(long id, long did);

	StudyCheckOutActivityDataDetails getStudyCheckOutActivityDataDetailsWithSadIdAndSaddId(long id, long did);

	StudyExecutionActivityDataDetails getStudyExecutionActivityDataDetailsWithSadIdAndSaddId(long id, long did);

	boolean saveDiscrepancy(StudyActivityDataDetailsDiscrepancy sdpojo);

	StudyActivityDataDetailsDiscrepancy getStudyActivityDataDetailsDiscrepancyWithId(long id);

	boolean updateDiscrepancy(StudyActivityDataDetailsDiscrepancy saddd);

	boolean getStudyActivityDataDetailsDiscrepancyCheck(StudyActivityData saData);

	InternationalizaionLanguages getInternationalizaionLanguagesWithCountryCode(String lang);

	LanguageSpecificValueDetails getLanguageSpecificValueDetail(GlobalValues gvalue,
			InternationalizaionLanguages lanuage);
	LanguageSpecificGlobalParameterDetails getLanguageSpecificGlobalParameterDetailsWithLang(
			StudyActivityParameters saParameter, InternationalizaionLanguages lanuage);

	List<ParameterControlTypeValues> getParameterControlTypeValuesWithGparameter(GlobalParameter parameterId);
	DefaultActivityDto getDefaultActivityDtoDetails();

	boolean saveDefaultActivity(long actid, List<Long> paramid, long phaseid, /*String tableName,*/ String subjectAllotment, String submitControlsString, String username, String getUrl, String saveUrl, String rejectUrl);

	DefaultActivitys getDefaultActivitysWithGlobalActivity(long id);


}
