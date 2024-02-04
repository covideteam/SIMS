package com.covideinfo.dao;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;

import com.covideinfo.dto.PdfGenerationDetailsDto;
import com.covideinfo.dto.StudyActivitiesSavingDto;
import com.covideinfo.dto.StudyProjectDetailDto;
import com.covideinfo.model.ActivityDraftReviewAudit;
import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.DosageForm;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.ECGTimePoints;
import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.SponsorCode;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyGroup;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectECGTimePoints;
import com.covideinfo.model.SubjectMealsTimePoints;
import com.covideinfo.model.SubjectPeriodStatus;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectSampleCollectionTimePoints;
import com.covideinfo.model.SubjectVitalTimePoints;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.VitalTest;
import com.covideinfo.model.VitalTimePoints;
import com.covideinfo.model.Volunteer;

public interface StudyCreationDao {

	List<ProjectsDetails> studyMasterFromProjectsDetails(Projects project);

	int studyMasterMaxSeqNoWithProjectNo(String fieldValue);

	SponsorCode sponsoreCode(String string);

	List<FromStaticData> formStaticData(String string);

	DosageForm dosageFrom(String string, String code);

	StatusMaster statusMaster(String String);

	StudyGroup studyGroup(String projectNo);

	List<VitalTest> vitalTestByCode(List<String> tss);

	FromStaticData formStaticData(String string, String fieldValue);


	Projects meargeProject(Projects project);

	void createVolunteers(List<Volunteer> vols);

	List<StudyPeriodMaster> getPeriod(StudyMaster study, FromStaticData string, StatusMaster activeStatus);

	Map<TreatmentInfo, List<SampleTimePoints>> sampleTimePoints(StudyMaster study);

	boolean saveSubjectSampleTimePoints(List<SubjectSampleCollectionTimePoints> sampleTimepoints);

	void mergeStudy(StudyMaster study);

	Map<TreatmentInfo, List<VitalTimePoints>> vitalTimePoints(StudyMaster study);

	boolean saveSubjectVitalTimePoints(List<SubjectVitalTimePoints> sampleTimepoints);

	boolean saveSubjectEcgTimePoints(List<SubjectECGTimePoints> timepoints);
	
	Map<TreatmentInfo, List<MealsTimePoints>> mealsTimePoints(StudyMaster study);

	boolean saveSubjectMealsTimePoints(List<SubjectMealsTimePoints> sampleTimepoints);

	Map<TreatmentInfo, List<DoseTimePoints>> doseTimePoints(StudyMaster study);

	boolean saveSubjectDoseimePoints(List<SubjectDoseTimePoints> sampleTimepoints);

	List<StudyPeriodMaster> allPeriod(StudyMaster study, StatusMaster activeStatus);

	List<SubjectPeriodStatus> saveSubjectPerioStatus(List<SubjectPeriodStatus> subjectStautsList);

	String saveStudyCompleteInfomation(StudyProjectDetailDto spDto, ActivityDraftReviewAudit adra, Projects poject)throws Exception;

	void saveTestProject(Projects p, List<ProjectsDetails> pdls);

	int projectMaxId();

	void saveSubjectTimePoints(StudyMaster study, List<SubjectPeriodStatus> subjectPerioStatus,  List<SubjectRandamization> rendamization,
			List<SubjectSampleCollectionTimePoints> subjectSampleTimePoints,
			List<SubjectVitalTimePoints> subjectVitalTimePoints, List<SubjectECGTimePoints> subjectEcgTimePoints, List<SubjectMealsTimePoints> subjectMealsTimePoints,
			List<SubjectDoseTimePoints> subjectDoseTimePoints);

	List<SubjectRandamization> createSubjectRandamization(StudyMaster study, SortedMap<Integer, String> subjects);

	List<SampleTimePoints> studyTreatmentRandamization(StudyMaster study, TreatmentInfo ti);

	List<VitalTimePoints> subjectTreatmentVitalTimePoints(StudyMaster study, TreatmentInfo tr);

	List<MealsTimePoints> subjectTreatmentMealsTimePoints(StudyMaster study, TreatmentInfo ti);

	List<DoseTimePoints> subjectTreatmentDoseTimePoints(StudyMaster study, TreatmentInfo ti);

	SubjectRandamization subjectRandamization(StudyPeriodMaster period, Volunteer vol);

	List<ECGTimePoints> subjectTreatmentEcgTimePoints(StudyMaster study, TreatmentInfo ti);

	List<StudyPeriodMaster> allPeriodsExceptAdmission(StudyMaster sm);

	List<Volunteer> studyAllSubjects(StudyMaster sm);

	List<SubjectSampleCollectionTimePoints> subjectSampleCollectionTimePointsSubjectData(Long periodId, int volid);

	StudyPeriodMaster studyPeriod(Long periodId);

	Volunteer volunteerBySeqNo(StudyMaster sm, int seqNo);

	SubjectDoseTimePoints subjectDoseTimePoints(Long periodId, int volid);

	List<SubjectDoseTimePoints> subjectAllDoseTimePoints(SubjectDoseTimePoints sdtp);

		Projects project(String projectNo);

		PdfGenerationDetailsDto getPdfGenerationDetailsDtoDetails(List<Long> actIds, List<Long> parmIds,
				Locale currentLocale);

		List<LanguageSpecificGlobalParameterDetails> getParameterList(List<Long> defalutParmIds,
				InternationalizaionLanguages inlag);

		List<LanguageSpecificGlobalParameterDetails> getNotExistingParameterList(
				LanguageSpecificGlobalActivityDetails exeAct, List<Long> defalutParmIds,
				InternationalizaionLanguages inlag);

		Map<Long, List<LanguageSpecificGlobalParameterDetails>> getLanguageSpecificParmeterDetails(
				Map<Long, List<Long>> resParamMap, InternationalizaionLanguages inlag);

		Map<LanguageSpecificGlobalActivityDetails, List<LanguageSpecificGlobalParameterDetails>> getProjectDetailsPdfGenerationDtoForDefaultActivities(List<Long> defaultActIds, InternationalizaionLanguages inlag);

		String saveStudyActivitiesData(StudyActivitiesSavingDto sasDto, Map<Long, List<Long>> defalultActivities, StudyMaster sm);

		List<LanguageSpecificGlobalParameterDetails> getLanguageSpecificParmetersList(
				InternationalizaionLanguages inalg, List<Long> parmList);

		List<Projects> getProjectWithProjectNo(String prono);

		List<DeviationMessage> deviationMessages(String collectionType);
}
