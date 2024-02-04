package com.covideinfo.clinical.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;

import com.covideinfo.bo.SampleCollectionDashBoard;
import com.covideinfo.bo.SampleTimePointsData;
import com.covideinfo.bo.SubjectDoseDashBord;
import com.covideinfo.dto.CentrificationDto;
import com.covideinfo.dto.DoseDataDto;
import com.covideinfo.dto.MealCollectoinDto;
import com.covideinfo.dto.MealsTimePointsDto;
import com.covideinfo.dto.SampleCollectionDtoDetails;
import com.covideinfo.dto.SampleCollectoinDto;
import com.covideinfo.dto.SegrigationDto;
import com.covideinfo.dto.StorageVacutinerDto;
import com.covideinfo.dto.VialRackDto;
import com.covideinfo.dto.VitalCollectionDataDto;
import com.covideinfo.dto.VitalCollectionDetailsDto;
import com.covideinfo.model.CentrifugationDataMaster;
import com.covideinfo.model.ECGTimePoints;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubejectDosePerameter;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectTimePointVitalTests;
import com.covideinfo.model.SubjectVitalTimePoints;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.VitalTimePoints;

public interface ClinicalService {

	String subjectBarcodeInfo(String barcode, StudyMaster sm);

	String vacutainerBarcodeInfoWithSubjct(String barcode) throws Exception;

	String saveSample(StudyMaster sm, Date date, Long userId, String subject, String subjectTime, String vacutainer,
			String vacutainerTime, String timePoint, String collectionTimeWithSec, String collectionTime,
			String modeOfCollection, String message, String deviationStatus, String deviationStatusMsg);

	String sachetBarcodeInfo(String barcode, Long studyId);

	String appropriateBox(String barcode);

	String subjectBarcodeInfoFroDose(String barcode, String sachet);

	String saveDosing(Long userId, Date date, DoseDataDto dto, MessageSource messageSource, Locale currentLocale,
			Long languageId, String dateFormat);

	Map<TreatmentInfo, List<MealsTimePoints>> preDsoeMealsTimePointsFromPeriod(StudyPeriodMaster sm);

	String subjectMealsInfo(String barcode, StudyMaster sm, Long periodId, String selecteTimePointIds);

	String measlStartTimesSave(Long userId, MealCollectoinDto mealsCollectionDate, Date date);

	/*ResultDto measlEndTimesSave(Long subjectPeriodTimePointCollectedDataId,Long timepointsId, String runningTimeWithSeconds, String ranningTime,
			Long userId, String consumption, String consumptionVal, String comment, String subjectNo);*/

	Map<TreatmentInfo, List<MealsTimePoints>> postDsoeMealsTimePointsFromPeriod(StudyPeriodMaster currentPeriod);

	String vacutainerForSampleSeparation(String barcode);

//	String vitalTimePointsOfSubject(String barcode, StudyMaster sm);

	Map<TreatmentInfo, List<VitalTimePoints>> preDsoeVitalTimePointsFromPeriod(StudyPeriodMaster currentPeriod);

	Map<TreatmentInfo, List<VitalTimePoints>> postDsoeVitalTimePointsFromPeriod(StudyPeriodMaster currentPeriod);

	String subjectVitalInfo(String barcode, StudyMaster sm, Long periodId, String selecteTimePointIds);

	SubjectVitalTimePoints subjectVitalTimePoints(Long timePointId);

	String saveVitalTimes(StudyMaster sm, SubjectVitalTimePoints smtp, List<SubjectTimePointVitalTests> test,
			Long userId, String ranningTime, String subjectScanTime);

	Map<TreatmentInfo, List<ECGTimePoints>> preDsoeEcgTimePointsFromPeriod(StudyPeriodMaster currentPeriod);

	Map<TreatmentInfo, List<ECGTimePoints>> postDsoeEcgTimePointsFromPeriod(StudyPeriodMaster currentPeriod);

	String subjectEcgInfo(String barcode, StudyMaster sm, Long periodId, String selecteTimePointIds);

	String saveECGTimes(StudyMaster sm, Long timePointId, Long subtimePointId, Long userId, String startTime,
			String endTime);

	String saveSampleSaperation(Date date, String vacutainer, Map<Integer, String> vials, int noOfVials, Long studyId,
			Long userId, String vacutainerScanTime, Map<Integer, String> vialScan);

	List<SampleCollectionDashBoard> sampleTimePointsDataOfPeriodHeading(StudyMaster sm);

	List<SampleTimePointsData> sampleTimePointsDataOfPeriod(Long periodId, StudyMaster sm);

	List<SampleTimePointsData> dosingTimePointsOfPeriodHeading(StudyMaster sm);

	List<SampleTimePointsData> doseTimePointsDataOfPeriod(Long periodId, StudyMaster sm);

	List<SampleTimePointsData> sampleSeparationTimePointsDataOfPeriod(Long periodId, StudyMaster sm);

	List<SampleTimePointsData> mealTimePointsDataOfPeriodHeading(StudyMaster sm);

	List<SampleTimePointsData> mealTimePointsDataOfPeriod(Long periodId, StudyMaster sm);

	List<SampleTimePointsData> ecgTimePointsDataOfPeriodHeading(StudyMaster sm);

	List<SampleTimePointsData> ecgTimePointsDataOfPeriod(Long periodId, StudyMaster sm);

	List<SampleTimePointsData> vitalTimePointsDataOfPeriodHeading(StudyMaster sm);

	List<SampleTimePointsData> vitalTimePointsDataOfPeriod(Long periodId, StudyMaster sm);

	Map<Integer, String> dosingSubjectHeading(StudyMaster sm);

	List<SubjectDoseDashBord> doseTimePointsDataOfPeriodWithSuperviceStatus(Long id, StudyMaster sm,
			Map<Integer, String> heading);

	SubjectDoseTimePoints saveDoseSuperviseInfoTest(UserMaster user, String id);

	List<SubejectDosePerameter> subejectDosePerameter(SubjectDoseTimePoints v);

	String centrifugeInstumentInfo(String barcode);

	String centrifugeVacutainerInfo(String barcode, String studyId);

	String saveSampleCollection(Long userId, SampleCollectoinDto colletion, Date date);

	String saveVitalCollection(Long userId, VitalCollectionDataDto colletion,MessageSource messageSource,
			Locale currentLocale, Long languageId, String dateFormat);
	
	Instrument instumentInfo(Long insturmentId);

	CentrifugationDataMaster centrifugationDataMaster(Long centrifugeId);

	SubjectDoseTimePoints saveDosingCollectionData(Long userId, Date date, DoseDataDto doseDataDto, MessageSource messageSource,
			Locale currentLocale, Long languageId, String dateFormat);

	List<CentrifugationDataMaster> sentrifugationDataMasters(Long studyId);

	StorageVacutinerDto periodWiseSampleTimePoitns(Long studyId);

	String vialRackSave(Long userId, VialRackDto vialRackDto, Date date, MessageSource messageSource,
			Locale currentLocale, Long languageId, String dateFormat);
	SegrigationDto separationVacutinerDto(Long studyId);

	List<String> noOfVials(Long studyId);

	Object centrifugationDataMasterAll(Long studyId);

	Map<String, String> getSubjectTreatmentWithStudyAndPeriod(Long studyId, List<StudySubjects> subjects);

	SampleCollectionDtoDetails getSampleCollectionDtoDetails(Long studyId);

	VitalCollectionDetailsDto getVitalCollectionDetailsDtoDetails(Long studyId, Long languageId);

	List<Instrument> getInstrumentList(String string);

	CentrificationDto collectedSampleVacutainers(Long studyId);

	MealsTimePointsDto allMealTimepoints(Long id);

}
