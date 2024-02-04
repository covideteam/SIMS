package com.covideinfo.dao;

import java.util.List;
import java.util.Map;

import com.covideinfo.dto.DoseSavingDtoDetails;
import com.covideinfo.dto.DoseTimePointsDto;
import com.covideinfo.dto.DosingCollectionTimePointsDto;
import com.covideinfo.dto.DosingDto;
import com.covideinfo.dto.GlobalparameterFromDto;
import com.covideinfo.dto.MealInfoDto;
import com.covideinfo.dto.MealsDto;
import com.covideinfo.dto.SampleTimepointDto;
import com.covideinfo.dto.SepatationDtoDetails;
import com.covideinfo.dto.VialRackCollectionDto;
import com.covideinfo.model.Centrifugation;
import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySampleCentrifugation;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectPeriodStatus;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectReplacedInfo;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.VitalTimePoints;
import com.covideinfo.model.Volunteer;

public interface StudyDao {

	StudyMaster findByStudyId(Long studyId);

	Volunteer volunteerWithSecqNo(StudyMaster std, int subjectSeqNo);
	
	Volunteer volunteerWithsubjectBarcode(String barcode);
	
	SubjectPeriodStatus subjectPeriodStatus(StudyMaster std, Volunteer vol);

	SubjectReplacedInfo standsubjectReplacedInfoSampleCollectionVac(StudyMaster std, Volunteer vol);

	List<StudyMaster> allUserActiveStudys(UserMaster user);

	List<StudyPeriodMaster> studyPeriodMaster(StudyMaster study);

	StudyPeriodMaster periodById(Long periodId);

	List<Volunteer> allStudyVolunteers(StudyMaster sm);

	StudyPeriodMaster currentPerod(StudyMaster sm);

	SubjectPeriodStatus subjectPeriodStatusWithPerod(Long periodId, Volunteer vol);

	List<Centrifugation> centrifugeList();

	Centrifugation centrifugationWithId(Long barcodeId);

	Centrifugation saveCentrifugation(Centrifugation cen);

	List<StudyMaster> allStudys();

	List<StudyPeriodMaster> studyPeriodMasterWithStudyId(Long studyId);

	List<SampleTimePoints> allSampleTimePoints(Long studyId);

	List<MealsTimePoints> allMealsTimePoints(Long studyId);

	List<DoseTimePoints> allDoseTimePoints(Long studyId);

	StudySubjects studySubject(String string, String string2);

	DoseTimePoints doseTimePoint(Long parseLong);

	com.covideinfo.model.StudyActivities studyActivity(long id, String string);

	List<StudyPeriodMaster> getStudyPeriodMasterWithStudy(StudyMaster study);

	DoseTimePointsDto getDoseTimePointsDtoDetails(Long studyId, String activityCode);

	SubjectDoseTimePoints getSubjectDoseTimePointsRecord(Long key);

	MealsDto getMealsTimePointsDtoDetails(Long studyId, String subjectNo);

	DosingCollectionTimePointsDto getDosingCollectionData(Long periodId, Long userId, String subjectNo, long doseId, String studyId, String replaceWith, Long devMsgId);

	StudySampleCentrifugation studySampleCentrifugationDetails(Long studyId);

	List<StudyPeriodMaster> getStudyPeriodMasterList(long id);

	List<DeepfreezerShelf> getDeepfreezerShelfList();

	List<SampleTimePoints> getSampleTimePointDataWithStudyId(long id);

	VialRackCollectionDto getSampleTimePointDataWithStudyIdForKeyAndValue(long id, VialRackCollectionDto dto);

	List<VitalTimePoints> getVitalTimePoint(Long studyId);

	GlobalparameterFromDto getGlobalParameterDetails(Long languageId, List<Long> vptParamIds, String activityCode);

	List<MealsTimePoints> treamentMealsTimePoints(long id);

	MealInfoDto getMealsDetails(Long studyId);

	DosingDto getDosingDtoDetails(Long studyId, String collectionStr);

	DoseSavingDtoDetails getDoseSavingDtoDetails(String studyId, String subjectId, Long perodId, Long userId,
			String replaceWith, String tpId, Long devMessageId);

	SepatationDtoDetails getSepatationDtoDetails(Long studyId);

	void saveApplicationSideMenuds();

	StudySubjects studySubject(Long studyId, String subjectNo);

	SubjectRandamization subjectRandamization(Long periodId, String subjectNo);

	List<SampleTimePoints> sampleTimePoints(Long studyId, SubjectRandamization subjectRandamization, boolean statndby);

	SampleTimePoints sampleTimePointsWithId(Long timePointId);

	


}
