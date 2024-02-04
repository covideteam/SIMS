package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.dto.SubjectTimePointsDto;
import com.covideinfo.model.StudySubjectDeviations;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectVitalTimePointsData;

public interface SampleScheduleCalculationDao {

	SubjectTimePointsDto getSubjectTimePointsDtoDetails(SubjectDoseTimePoints sdtp, Long userId);

	String updateScheduledTimePointsData(List<SubjectMealsTimePointsData> updateMealsList,
			List<SubjectSampleCollectionTimePointsData> updateSamDataList,
			List<SubjectVitalTimePointsData> updatesvDataList, List<StudySubjectDeviations> ssdList);

}
