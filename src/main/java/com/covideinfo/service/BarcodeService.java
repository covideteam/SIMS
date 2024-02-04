package com.covideinfo.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

import com.covideinfo.dto.DoseTimePointsDto;
import com.covideinfo.dto.GlobalparameterFromDto;
import com.covideinfo.dto.MealsTimePointsDto;
import com.covideinfo.dto.SampleCollectionDto;
import com.covideinfo.dto.SeparationVacutinerDto;
import com.covideinfo.dto.VialRackCollectionDto;
import com.covideinfo.dto.VitalTimePointsDto;
import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.SampleContainer;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySampleCentrifugation;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectSampleCollectionTimePoints;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.Volunteer;
import com.covideinfo.model.dummy.BarcodeBoxData;
import com.covideinfo.model.dummy.BarcodeSubjectContainerData;

public interface BarcodeService {

	List<StudyMaster> getStudyMasterList();

	List<StudyPeriodMaster> getStudyPeriodMasterList();

	StudyPeriodMaster getStudyPeriodMasterWithId(Long period);

	String generateVacutainersBarcodeAll(Long period, String realPath);

	Object clinicalSampleTimePointsExceptDose(StudyMaster sm);

//	String generateVacutainersBarcodeAllTimePointWise(Long periodId, Long timePoint, String realPath);

	String generateVacutainersBarcodeAllSubjectWise(Long periodId, String subjectNo, String realPath);

	StudyMaster findByStudyId(Long activeStudyId);

	List<Volunteer> avilableBedNos(StudyMaster sm);

	List<StudyPeriodMaster> allStudyPeriodsWithSubEle(StudyMaster sm);

	String generateSubjectBarcodeAll(StudyMaster sm, String realPath, SubjectRandamization st, List<StudyPeriodMaster> psmLista);

	String generateSubjectBarcode(StudyMaster sm, String subjectNo, String realPath, StudyPeriodMaster psm, SubjectRandamization st);

	String generateSachetBarcode(Long periodId, String realPath);

	String generateVialBarcodePeriodWise(Long periodId, String realPath);

	List<SubjectSampleCollectionTimePoints> subjectSampleTimePoints(StudyMaster sm, long period, String volId);

	String generateVacutainersBarcodeAllSubjectWiseForTimePoint(Long periodId, String subjctNO, Long timePointId,
			String realPath);

	String generateSubjectContainerBarCodePrint(BarcodeSubjectContainerData bsData, String username,
			HttpServletRequest request);

	String generateBoxBarCodePrint(BarcodeBoxData bsData, String username, HttpServletRequest request);

	String generateCentrifugationBarcode(Long varcodeId, HttpServletRequest request);

	String generateVacutainersBarcodeAllTimePointWise(Long periodId, Long timepointId, String realPath);

	SortedMap<Integer, String> subjectBarcodes(List<StudySubjects> subjects, StudyMaster study);

	SortedMap<Integer, StudyPeriodMaster> studyPeriodList(Long studyId);

	SortedMap<Long, SampleTimePoints> allSampleTimepoints(Long studyId, SampleCollectionDto dto);

	SortedMap<Long, SortedMap<String, List<SubjectSampleCollectionTimePointsData>>> collectedData(Long studyId,
			SampleCollectionDto dto);

	SortedMap<String, StudyPeriodMaster> subjectPeriods(Long studyId, SortedMap<Integer, String> subjectBarocodes);

	Map<String, String> replacedSubjects(List<StudySubjects> subjects, StudyMaster study);

	List<String> droppendSubjects(List<StudySubjects> subjects, StudyMaster study);

	Map<Long, SubjectSampleCollectionTimePointsData> timePointCollectedData(SampleCollectionDto dto);

	SortedMap<String, SubjectDoseTimePoints> subjectDoseTimes(Long studyId);

	MealsTimePointsDto allMealTimepoints(Long studyId);

	SortedMap<Long, SortedMap<String, List<SubjectMealsTimePointsData>>> collectedData(Long studyId,
			MealsTimePointsDto dto);

	Map<Long, SubjectMealsTimePointsData> timePointCollectedData(MealsTimePointsDto dto);

	Map<String, SubjectMealsTimePointsData> subjectPeriodTimePointCollectedData(
			SortedMap<Long, SortedMap<String, List<SubjectMealsTimePointsData>>> collectedData);

	SortedMap<Long, DoseTimePoints> allDoseTimepoints(Long studyId);

	SortedMap<Long, TreatmentInfo> allTreatment(Long studyId);

	List<StudySubjects> allStudySubjects(Long studyId);

	Map<String, String> subjectStatusMap(List<StudySubjects> subjects, StudyMaster study);

	Map<Long, String> discontinuedSubjects(DoseTimePointsDto dto, StudyMaster study);

	GlobalparameterFromDto dynamicParameters(Long studyId, Long languageId, Locale currentLocale,
			String activityName);

	List<VitalTimePointsDto> allVitalTimePoints(Long studyId);

//	List<SubjectVitalTimePointsData> subjectVitalTimePointsData(VitalTimePointsCollectionDto dto);

	Map<Long, VitalTimePointsDto> vitalTimePointsMap(List<VitalTimePointsDto> vitalTimPoints);

	SortedMap<String, SubjectDoseTimePoints> subjectAllDoseTimes(long id);

	SortedMap<String, SubjectDoseTimePoints> subjectDoseTimes(
			SortedMap<String, SubjectDoseTimePoints> subjectDoseTimes);

	Map<String, Map<String, SubjectMealsTimePointsData>> mealDetails(List<StudySubjects> subjects,Long studyId);

//	List<Instrument> getInstrumentList(String centrifuge);

	String generateinstrumentBarcode(Long id, String realPath, HttpServletRequest request);

	StudyTreatmentWiseSubjects getStudyTreatmentWiseSubjectsWithPeriodAndStudy(StudyMaster sm, StudyPeriodMaster psm, String subjectNo);

	TreatmentInfo getTreatmentInfoWithId(TreatmentInfo treatment);

	SubjectRandamization getSubjectRandamizationWithPeriodAndSubject(StudyPeriodMaster psm, String string);

	String stdVacutainerBarcodeForAllPrint(Long periodId, String realPath);

	String generateVialBarcodeAll(Long periodId, String realPath);

	SeparationVacutinerDto separationVacutinerDto(Long studyId);

	List<Deepfreezer> getDeepfreezerList();

	String generateDeepfreezerBarcode(Long id, String realPath, HttpServletRequest request);

	List<Long> getStudyPeriodMasterWithStudy(StudyMaster sm);

	List<SubjectRandamization> getSubjectRandamizationWithPeriods(List<Long> psmList);

	List<StudyPeriodMaster> getStudyPeriodMasterWithStudyList(StudyMaster sm);

	SortedMap<Long, StudyPeriodMaster> studyPeriodListSampleCollection(Long id);

	StudySampleCentrifugation studySampleCentrifugationDetails(Long studyId);

	List<Instrument> getInstrumentList();

//	CentrificationDto colectedSampleVacutainers(Long studyId);

//	List<StudyPeriodMaster> getStudyPeriodMasterList(long id);

	List<DeepfreezerShelf> getDeepfreezerShelfList();

	String generateDeepfreezerShelfBarcode(Long id, String realPath, HttpServletRequest request);

	List<SampleTimePoints> getSampleTimePointDataWithStudyId(long id);

	VialRackCollectionDto getSampleTimePointDataWithStudyIdForKeyAndValue(long id, VialRackCollectionDto dto);
	String generateSampleContainerBarcode(SampleContainer result, String realPath, HttpServletRequest request);

	Map<Long, GlobalparameterFromDto> getVitalGlobalParameters(Long studyId, Long languageId);



}
