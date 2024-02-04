package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.dto.StudyActivityTimePointsRetrivingDto;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyActivityTimePointsCompletionData;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.VitalTimePoints;

public interface StudyActivityTimePointsRetrivingDao {

	StudyActivityTimePointsRetrivingDto getStudyActivityTimePointsRetrivingDtoDetails(Long studyId, String subjectNo,
			Long activityId, long languageId);

	StudyActivityTimePointsCompletionData getStudyActivityTimePointsCompletionData(long id, long id2, Long id3,
			long id4, Long studyId);

	VitalTimePoints getDosingTimePointsRecord(String timePoint, TreatmentInfo tfinfo,
			Long studyId);

	LanguageSpecificGroupDetails getGloablActivityDetails(long id, long languageId);

	List<LanguageSpecificValueDetails> getGlobalValuesList(long id, long id2, long languageId);

}
