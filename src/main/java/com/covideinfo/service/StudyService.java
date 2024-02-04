package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

import com.covideinfo.dto.DoseTimePointsDetailsDto;
import com.covideinfo.dto.DosingDto;
import com.covideinfo.dto.MealsDetailsDto;
import com.covideinfo.model.Centrifugation;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.Volunteer;

public interface StudyService {

	StudyMaster findByStudyId(Long activeStudyId);

	List<StudyMaster> allUserActiveStudys(UserMaster user);

	String studyPeriodMasterSelectBox(StudyMaster study);

	StudyPeriodMaster periodById(Long periodId);

	List<Volunteer> allStudyVolunteers(StudyMaster sm);

	StudyPeriodMaster currentPerod(StudyMaster sm);

	List<Centrifugation> centrifugeList();

	String saveCentrifugation(Long userId, String name, String code, String detials);

	List<StudyMaster> allStudys();

	DoseTimePointsDetailsDto getDoseTimePointsDtoDetails(Long studyId, String activityCode, Long languageId, Locale currentLocale, String mealType);

	MealsDetailsDto getMealsTimePoints(Long studyId, String subjectNo);

	DosingDto getDosingDtoDetails(Long studyId, String collectionStr);

	void saveApplicationSideMenuds();

	List<StudyPeriodMaster> getStudyPeriodMasterList(Long id);


}
