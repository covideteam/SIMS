package com.covideinfo.clinical.dao;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import com.covideinfo.bo.SampleCollectionDashBoardColumns;
import com.covideinfo.dto.CentrificationDetailsDto;
import com.covideinfo.dto.CommonTpDetails;
import com.covideinfo.dto.DoseDataDto;
import com.covideinfo.dto.GlobalparameterFromDto;
import com.covideinfo.dto.MealCollectoinDto;
import com.covideinfo.dto.MealsDataSavingDto;
import com.covideinfo.dto.PlannedTimeDetailsDto;
import com.covideinfo.dto.SampleCollectionSavingDto;
import com.covideinfo.dto.SampleCollectoinDto;
import com.covideinfo.dto.VilaRackDtoDetails;
import com.covideinfo.dto.VitalCollectionDataDto;
import com.covideinfo.dto.VitalCollectionSavingDto;
import com.covideinfo.dto.VitalTimePointsCollectionDto;
import com.covideinfo.model.Centrifugation;
import com.covideinfo.model.CentrifugationData;
import com.covideinfo.model.CentrifugationDataMaster;
import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.DosePerameter;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.ECGTimePoints;
import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.LoadedVacutinersInCentrifuge;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.RackWithVials;
import com.covideinfo.model.RackWithVitalSubject;
import com.covideinfo.model.SampleContainer;
import com.covideinfo.model.SampleStorageData;
import com.covideinfo.model.SampleStorateDataMaster;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.SamplesCentrifugation;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.SubejectDosePerameter;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectECGTimePoints;
import com.covideinfo.model.SubjectECGTimePointsData;
import com.covideinfo.model.SubjectMealsTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectPeriodStatus;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectSampleCollectionTimePoints;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectSampleSeparationTimePointsData;
import com.covideinfo.model.SubjectTimePointVitalTests;
import com.covideinfo.model.SubjectVitalTimePoints;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.model.TimePointVitalTests;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.VialSeparationData;
import com.covideinfo.model.VitalTimePoints;
import com.covideinfo.model.Volunteer;

public interface ClinicalDao {

	SubjectSampleCollectionTimePoints subjectSampleCollectionTimePoints(String barcode);
	SubjectSampleCollectionTimePointsData subjectSampleCollectionTimePointsData(SubjectSampleCollectionTimePoints vac);
	List<SubjectSampleCollectionTimePoints> previousSamplesIfAny(StudyMaster study, StudyPeriodMaster period,
			TreatmentInfo treatmentInfo, String subjectNo,int timePointNo, SubjectSampleCollectionTimePoints vac);

	void updateSubjectSampleCollectionTimePoints(SubjectSampleCollectionTimePoints vac);

	SubjectDoseTimePoints subjectDoseTimePoints(String barcode, Long studyId);

	List<SubjectDoseTimePoints> oldSubjectDoseTimePoints(SubjectDoseTimePoints tech);

	List<DosePerameter> dosePerameter(StudyMaster study, TreatmentInfo treatmentInfo);

	void updateClinicalSampleTimePointsPhasesAndVital(SubjectDoseTimePoints tech,
			List<SubjectSampleCollectionTimePoints> afterDose, List<SubjectVitalTimePoints> afterDosevital,
			List<SubjectMealsTimePoints> afterDoseMeals, List<SubejectDosePerameter> subjectDosePerametres,SubjectPeriodStatus sps);

	List<SubjectSampleCollectionTimePoints> getSubjectSamplesTimepoints(SubjectDoseTimePoints tech);

	List<SubjectMealsTimePoints> subjectMealsTimePoints(SubjectDoseTimePoints tech);
	List<SubjectVitalTimePoints> subjectVitalTimePoints(SubjectDoseTimePoints tech);
	List<SubjectECGTimePoints> subjectEcgTimePoints(SubjectDoseTimePoints tech);
	
	
	FromStaticData fromStaticData(Long id);

	Map<TreatmentInfo, List<MealsTimePoints>> preDsoeMealsTimePointsFromPeriod(StudyPeriodMaster sm);

	SubjectMealsTimePoints subjectMealsTimePoints(List<Long> timePointIds, int subjectSeqNo, StudyPeriodMaster sp);

	List<SubjectMealsTimePoints> subjectMealsTimePointsWithIds(List<Long> timePointIdsList);

	void saveSubjectMealsTimePointsData(List<SubjectMealsTimePointsData> subjectTimePoints);

	SubjectMealsTimePoints subjectMealsTimePointsWithId(Long timepointsId);

	void updateSubjectMealsTimePoints(SubjectMealsTimePoints smtp);
////////
	Map<TreatmentInfo, List<MealsTimePoints>> postDsoeMealsTimePointsFromPeriod(StudyPeriodMaster sp);

	SubjectRandamization subjectRandamization(StudyMaster study, StudyPeriodMaster sp, int subjectOrder);

	SubjectMealsTimePoints subjectMealsDinnerTimePoints(TreatmentInfo treatmentInfo, int subjectOrder,
			StudyPeriodMaster sp, SubjectDoseTimePoints tech);

	SubjectMealsTimePoints subjectMealsBreakFastTimePoints(TreatmentInfo treatmentInfo, int subjectOrder,
			StudyPeriodMaster sp);

	List<SubjectDoseTimePoints> oldSubjectDoseTimePointsAfterDose(SubjectDoseTimePoints tech);

	SubjectMealsTimePoints subjectMostResentMealsTimePoint(TreatmentInfo treatmentInfo, int subjectOrder,
			StudyPeriodMaster sp);

void updateClinicalSampleTimePoints(SubjectSampleCollectionTimePoints ssctp, List<SubjectSampleSeparationTimePointsData> sepdata);

	Map<TreatmentInfo, List<VitalTimePoints>> preDsoeVitalTimePointsFromPeriod(StudyPeriodMaster sm);

	Map<TreatmentInfo, List<VitalTimePoints>> postDsoeVitalTimePointsFromPeriod(StudyPeriodMaster sp);

	SubjectVitalTimePoints subjectVitalTimePoints(List<Long> timePointIds, int subjectSeqNo, StudyPeriodMaster period);

	List<TimePointVitalTests> timePointVitalTests(VitalTimePoints vitalTimePointsId);

	SubjectVitalTimePoints subjectVitalTimePoints(Long timePointId);

	void saveSubjectVitalDetials(SubjectVitalTimePoints smtp, SubjectVitalTimePointsData data,
			List<SubjectTimePointVitalTests> test);
	
	Map<TreatmentInfo, List<ECGTimePoints>> preEcgTimePointsFromPeriod(StudyPeriodMaster sm);

	Map<TreatmentInfo, List<ECGTimePoints>> postDsoeEcgTimePointsFromPeriod(StudyPeriodMaster sp);

	SubjectECGTimePoints subjectEcgTimePoints(List<Long> timePointIds, int subjectSeqNo, StudyPeriodMaster period);

	SubjectECGTimePoints subjectECGTimePoints(Long timePointId);

	void saveSubjectEcgDetials(SubjectECGTimePoints smtp, SubjectECGTimePointsData data);

	SubjectMealsTimePoints subjectMealsBreakFastTimePointsPreDose(TreatmentInfo treatmentInfo, int subjectOrder,
			StudyPeriodMaster sp);

	List<SubejectDosePerameter> subejectDosePerameter(SubjectDoseTimePoints sdtp);

	List<SubjectVitalTimePoints> subjectVitalTimePoints(Long periodId, int volid);

	List<SubjectSampleSeparationTimePointsData> subjectSampleSeparationTimePointsData(Long periodId, int volid);

	Map<Integer, List<SubjectMealsTimePoints>> subjectMealsTimePoints(Long periodId, int volid, boolean daywise);

	List<SampleTimePoints> sampleTimePoints(TreatmentInfo tr);

	List<TreatmentInfo> treatmentInfo(StudyMaster sm);

	Map<String, List<SubjectSampleCollectionTimePoints>> allSubjectTimePoints(Long periodId);

	List<SampleTimePoints> sampleTimePoints(StudyMaster sm);

	List<DoseTimePoints> doseTimePoints(TreatmentInfo tr);
	
	List<DoseTimePoints> doseTimePoints(StudyMaster sm);

	Map<String, List<SubjectDoseTimePoints>> allSubjectDoseTimePoints(Long periodId);

	List<MealsTimePoints> mealsTimePoints(TreatmentInfo tr);

	List<MealsTimePoints> mealsTimePoints(StudyMaster sm);

	Map<String, List<SubjectMealsTimePoints>> allSubjectMealPoints(Long periodId);

	List<ECGTimePoints> ecgTimePoints(TreatmentInfo tr);

	List<ECGTimePoints> ecgTimePoints(StudyMaster sm);

	Map<String, List<SubjectECGTimePoints>> allSubjectEcgPoints(Long periodId);

	List<VitalTimePoints> vitalTimePoints(TreatmentInfo tr);

	List<VitalTimePoints> vitalTimePoints(StudyMaster sm);

	Map<String, List<SubjectVitalTimePoints>> allSubjectVitalPoints(Long periodId);

	Map<Integer, List<SubjectECGTimePoints>> subjectEcgTimePoints(Long periodId, int volid, boolean daywise);
	
	////////////
	List<Volunteer> studyVolunters(StudyMaster sm);

	List<SubjectDoseTimePoints> subjectDoseTimePoints(DoseTimePoints timepoint, Long periodId);

	SubjectDoseTimePoints subjectDoseTimePoints(Long id);

	void updateSubjectDoseTimePoints(SubjectDoseTimePoints subjectDoseTimePoints);

	SubjectMealsTimePoints subjectMealsTimePointsForFasting(SubjectDoseTimePoints tech);

	SubjectMealsTimePoints subjectMealsTimePointsForFed(SubjectDoseTimePoints tech);

	List<SampleCollectionDashBoardColumns> sampleCollectionTimePointsHeadding(StudyMaster sm);

	List<SampleCollectionDashBoardColumns> sampleCollectionTimePointsTreatmentTimes(StudyMaster sm,
			List<SampleCollectionDashBoardColumns> columns, TreatmentInfo tr);

	Centrifugation centrifugationWithBarcode(String barcode);

	SamplesCentrifugation samplesCentrifugation(StudyMaster study);

	Instrument centrifugationWithId(Long centrifugeId);

	SamplesCentrifugation sampleCentrifugation(Long sampleCentrifugationId);

	Long saveCentrifugationData(CentrifugationDataMaster master, List<LoadedVacutinersInCentrifuge> vacutaineres);

	String saveCentrifugationData(String runningTimeWithSeconds, Long centrifugeDataId);

	CentrifugationDataMaster centrifugationDataMasterWithId(Long centrifugeDataMasterId);

	List<CentrifugationData> centrifugationData(CentrifugationDataMaster master);
////
Long saveSampleSeparationStart(CentrifugationDataMaster master, List<SubjectSampleCollectionTimePoints> sampleTimePoints, List<SubjectSampleSeparationTimePointsData> sepdata);

	List<CentrifugationData> saveSampleSeparationEndTime(CentrifugationDataMaster master);

	List<LoadedVacutinersInCentrifuge> loadedVacutinersInCentrifuge(CentrifugationData obj);

	List<SubjectSampleSeparationTimePointsData> subjectSampleSeparationTimePointsData(CentrifugationData obj);

	CentrifugationData centrifugationDataWithId(Long centrifugeDataId);

	Object storageMaster(SampleStorateDataMaster storageMaster);

	List<SubjectSampleCollectionTimePointsData> periodSubjectSampleCollectionTimePointsData(Long studyId, Long id, String k);

	SortedMap<String, StudyPeriodMaster> subjectPeriods(Long studyId, SortedMap<Integer, String> subjectBarocodes);

	List<String> droppedSubjects(Long studyId);

	
	SampleTimePoints sampleTimePointsWithId(long parseLong);

	StudySubjects studySubject(long parseLong, String string);

	DoseTimePoints doseTimePoints(Long studyId, Long treatmentId);

	SubjectDoseTimePoints subjectDoseTimePoints(DoseTimePoints doseTimePont, StudySubjects subject);

	String saveSubjectSampleTimePoint(SubjectSampleCollectionTimePointsData data, SampleCollectoinDto scd, UserMaster user);
////
List<StudySubjects> allstudySubject(long studyId);

	SortedMap<String, SubjectDoseTimePoints> subjectDoseTimes(Long studyId);

	List<SubjectMealsTimePointsData> periodSubjectMealCollectionTimePointsData(Long id, String string, Long studyId);

	List<MealsTimePoints> mealsTimePointsWithIds(List<Long> timePointIdsList);

	SortedMap<Long, TreatmentInfo> allTreatment(Long studyId);

	SubjectDoseTimePoints subjectDoseTimePoints(long id, long id2);

	SubjectDoseTimePoints saveSubjectDoseTimePoints(DoseDataDto dto, SubjectDoseTimePoints tech,StudySubjects subject, StudySubjects replaceSubject);

	StudyPeriodMaster periodOne(long id);

	List<SubjectRandamization> subjectRandamizationByPeriod(Long id);

	List<VitalTimePoints> allVitalTimePoints(Long studyId);

	List<SubjectVitalTimePointsData> subjectVitalTimePointsData(List<StudySubjects> subjects);

	VitalTimePoints vitalTimePointsWithId(Long timePointPk);

	String saveSubjectVitalTimePoint(SubjectVitalTimePointsData data,
			VitalCollectionDataDto colletion, UserMaster user);

	SortedMap<String, SubjectDoseTimePoints> subjectAllDoseTimes(Long studyId);
	List<SubjectMealsTimePointsData> subjectPreDoseMeals(StudyPeriodMaster p, List<Long> mealIds, StudySubjects s);
	List<Long> preDoseMeals(Long studyId);

	Instrument instumentInfo(Long insturmentId);

	CentrifugationDataMaster centrifugationDataMaster(Long centrifugeId);

	CentrifugationData centrifugationDataWithSampleTimePoit(long id);

	SubjectSampleCollectionTimePointsData subjectSampleCollectionTimePointsData(String vac, Long studyId);
	
	List<LoadedVacutinersInCentrifuge> loadedVacutinersInCentrifuges(
			List<Long> subjectSampleCollectionTimePointsDataIds);

	Long saveSampleSeparationData(CentrifugationDataMaster master, List<VialSeparationData> vialData);

	Long saveSampleStorageData(CentrifugationDataMaster master, SampleStorageData data);

	SubjectDoseTimePoints saveSubjectDoseTimePointsCollectionData(DoseDataDto dto, SubjectDoseTimePoints tech,
			StudySubjects replaceSubject);
			
			
	StudyPeriodMaster studyperiodById(Long parseLong);

	DeviationMessage diviationMessage(Long id);

	List<SubjectSampleCollectionTimePointsData> subjectSampleCollectionTimePointsDataByPeriodId(Long id);
	
	List<CentrifugationDataMaster> centrifugationDataMasterList(long id);
	
	List<Instrument> alldDefreezers(String string);
	
	List<Deepfreezer> deepfreezers(List<Long> insIds);
	
	SortedMap<String, StudyPeriodMaster> subjectPeriods(Long studyId);
	
	void updateSubjectMealsTimePointsData(SubjectMealsTimePointsData subjectMealsTimePointsData);
	
	SubjectMealsTimePointsData subjectMealsTimePointsDataById(Long subjectPeriodTimePointCollectedDataId);

	List<GlobalParameter> globalParameter(List<Long> perameterIds);
	
	Map<Long, SampleTimePoints> getSampleTimePointsWithStudyForKeyVal(long studyid);
	
	boolean vialRackSave(List<RackWithVials> rwvList);
	
	StudyPeriodMaster getStudyPeriodMasterWithPeriodId(long periodid);
	
	Deepfreezer getDeepfreezerWithId(long shefid);
	
	List<DeepfreezerShelf> deepfreezerShelfs(List<Long> insIds);
	
	List<RackWithVials> rackWithVials(Long rack);
	
	DeepfreezerShelf deepfreezerShelfsById(long parseLong);
	
	Deepfreezer deepfreezers(long parseLong);
	
	List<RackWithVials> rackWithVialsWithRackId(long parseLong);
	
	boolean vialRackSave(RackWithVials rwvpojo, List<RackWithVitalSubject> rwvsList);
	
	StudySubjects getStudySubjectsWithStudyAndSubject(StudyMaster study, String string);
	List<Deepfreezer> deepfreezersAll();
	List<DeepfreezerShelf> deepfreezersShellfAll();
	List<RackWithVials> rackWithVialsWithStudyId(Long studyId);
	List<SubjectSampleCollectionTimePointsData> periodSubjectSampleCollectionTimePointsData(Long studyId);
	int noOfVials(Long studyId);
	List<RackWithVitalSubject> rackWithVitalSubject(long id);
	SampleContainer saveSampleContainer(SampleContainer container);
	Map<Long, List<CentrifugationDataMaster>> centrifugationDataMasterAll(Long studyId);
	
	List<StudyTreatmentWiseSubjects> getStudyTreatmentWiseSubjectsWithStudy(Long studyId);
	
	MealsDataSavingDto getMealsDataSavingDtoDetails(MealCollectoinDto mealsCollectionDate, Long userId);
	
	String saveSubjectsMealsData(List<SubjectMealsTimePointsData> savingSubMDataList,
			List<SubjectMealsTimePointsData> updateSubMDataList, MealCollectoinDto mcd, UserMaster userMaster, Map<String, Map<Long, String>> devMap);
	
	SubjectDoseTimePoints getSubjectDoseTimepointRecord(StudySubjects studySubjects,
			StudyPeriodMaster studyPeriodMaster, StudyMaster study);
	
	CommonTpDetails getBloodSampleCollectionDetails(Long studyId, String collectionType);
	
	com.covideinfo.dto.BloodSamplesCollectionDto getBloodSamplesCollectionDtoDetails(Long studyId, Map<String, StudyPeriodMaster> stdPeriodMap);
	SampleCollectionSavingDto getSampleCollectionSavingDtoDetails(Long studyId, Long userId, String string,
			long parseLong, long parseLong2, long parseLong3);
	
	VitalTimePointsCollectionDto getVitalTimePointsCollectionDtoDetails(Long studyId, Map<String, StudyPeriodMaster> spmMap);
	VitalCollectionSavingDto getVitalCollectionSavingDtoDetails(long studyId, String subjNo, Long perodId,
			Long userId, Long timePointPk);
	CentrificationDetailsDto getCollectedSamplesList(Long studyId);
	
	VilaRackDtoDetails getVilaRackDtoDetails(Long studyId, Long userId, List<Long> tpIds, List<String> subNos,
			Long rackId, long periodId);
	Map<Long, GlobalparameterFromDto> getGlobalParameterDetails(Long languageId, Map<Long, List<Long>> vptParamMap,
			String string, List<Long> allPramIds);
	List<Instrument> getInstrumentListWithType(String centrifuge);
	PlannedTimeDetailsDto getPlannedTimeDetailsDtoDetails(Long studyId, Map<String, StudyPeriodMaster> spmMap,
			String projectNo);
	
	
}

	
	