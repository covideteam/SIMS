package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectSampleCollectionTimePoints;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.Volunteer;

public interface BarcodeDao {

	List<StudyMaster> getStudyMasterList();

	List<StudyPeriodMaster> getStudyPeriodMasterList();

	StudyPeriodMaster getStudyPeriodMasterWithId(Long period);

	List<SubjectSampleCollectionTimePoints> getVacutainerBarcods(StudyMaster study, Long period);

	Object clinicalSampleTimePointsExceptDose(StudyMaster sm);

	List<SubjectSampleCollectionTimePoints> clinicalSampleTimePointsPhaseById(StudyPeriodMaster spmpojo, SampleTimePoints stp);

	StudyMaster findByStudyId(Long activeStudyId);

	List<Volunteer> avilableBedNos(StudyMaster sm);

	List<StudyPeriodMaster> allStudyPeriodsWithSubEle(StudyMaster sm);

	Volunteer volunteer(long volid);

	List<SubjectDoseTimePoints> getSachetBarcodeDataWithPeriod(StudyMaster sm, long period);

	SampleTimePoints getSampleTimePointsWithTiompointId(Long timePoint);

	List<SubjectSampleCollectionTimePoints> getVacutainerBarcodsSubjectWise(StudyMaster sm, StudyPeriodMaster spm,
			String bedNo);

	List<SubjectSampleCollectionTimePoints> subjectSampleTimePoints(StudyMaster sm, long period, String volId);

	List<SubjectSampleCollectionTimePoints> subjectSampleTimePointsSubjectWiseForTimePoint(String timePont);

	List<SampleTimePoints> sampleTimePoints(Long sm);

	List<DoseTimePoints> doseTimePoints(long id);

	List<SubjectRandamization> subjectRandamizationOfPeriod(Long periodId);

	List<Instrument> getInstrumentList();

	Instrument getInstrumentWithId(Long id);

	StudyTreatmentWiseSubjects getStudyTreatmentWiseSubjectsWithPeriodAndStudy(StudyMaster sm, StudyPeriodMaster psm, String subjectNo);

	TreatmentInfo getStudyTreatmentWiseSubjectsWithPeriodAndStudy(TreatmentInfo treatment);

	SubjectRandamization getSubjectRandamizationWithPeriodAndSubject(StudyPeriodMaster psm, String string);

	List<Deepfreezer> getDeepfreezerList();

	Deepfreezer getDeepfreezerWithId(Long id);

	List<Long> getStudyPeriodMasterWithStudy(StudyMaster sm);

	List<SubjectRandamization> getSubjectRandamizationWithPeriods(List<Long> psmList);

	List<StudyPeriodMaster> getStudyPeriodMasterWithStudyList(StudyMaster sm);

	SubjectRandamization getSubjectRandamizationWithSubjectAndPeriod(String string, StudyPeriodMaster ppdata);

//	List<Instrument> getInstrumentListWithType(String centrifuge);

	DeepfreezerShelf getDeepfreezerShelfById(Long id);

	List<SubjectRandamization> getSubjectRandamizationWithPeriodForVial(StudyPeriodMaster pp);

}
