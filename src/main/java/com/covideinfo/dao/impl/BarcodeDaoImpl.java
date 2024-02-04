package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.BarcodeDao;
import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.InstrumentType;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectSampleCollectionTimePoints;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.Volunteer;

@Repository("/bracodeDao")
public class BarcodeDaoImpl extends AbstractDao<String, SubjectSampleCollectionTimePoints> implements BarcodeDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyMaster> getStudyMasterList() {
		List<StudyMaster> slist = null;
		slist = getSession().createCriteria(StudyMaster.class).list();

		return slist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyPeriodMaster> getStudyPeriodMasterList() {

		List<StudyPeriodMaster> spmlist = null;
		spmlist = getSession().createCriteria(StudyPeriodMaster.class).list();
		return spmlist;
	}

	@Override
	public StudyPeriodMaster getStudyPeriodMasterWithId(Long period) {
		StudyPeriodMaster spm = null;
		spm = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
				.add(Restrictions.eq("id", period)).uniqueResult();
		return spm;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectSampleCollectionTimePoints> getVacutainerBarcods(StudyMaster study, Long period) {
		TreatmentInfo treatment = (TreatmentInfo) getSession().createCriteria(TreatmentInfo.class)
				.add(Restrictions.eq("study", study)).add(Restrictions.eq("treatmentNo", 1 + "")).uniqueResult();

		List<SubjectSampleCollectionTimePoints> sslist = null;
		sslist = getSession().createCriteria(SubjectSampleCollectionTimePoints.class)
				.add(Restrictions.eq("study", study)).add(Restrictions.eq("period.id", period))
//					.add(Restrictions.in("subjectNo", volList))
				.add(Restrictions.eq("treatmentInfo", treatment))
				// .add(Restrictions.ne("type", "Dose")).addOrder(Order.asc("subjectOrder"))
				.addOrder(Order.asc("subjectOrder"))
//					.addOrder(Order.asc("batchNo"))
				.list();
//		}
		for (SubjectSampleCollectionTimePoints s : sslist) {
			System.out.println(s.getTimePoint() + "\t" + s.getSubjectNo());
		}
		return sslist;
	}

	@Override
	public Object clinicalSampleTimePointsExceptDose(StudyMaster sm) {
		return getSession().createCriteria(SampleTimePoints.class).add(Restrictions.eq("study", sm)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectSampleCollectionTimePoints> clinicalSampleTimePointsPhaseById(StudyPeriodMaster spmpojo,
			SampleTimePoints stp) {
		List<SubjectSampleCollectionTimePoints> list = new ArrayList<>();
//		for(Volunteer v : volList) {
		List<SubjectSampleCollectionTimePoints> list1 = getSession()
				.createCriteria(SubjectSampleCollectionTimePoints.class)
				.add(Restrictions.eq("study", spmpojo.getStudy())).add(Restrictions.eq("period", spmpojo))
				.add(Restrictions.eq("sampleTimePointId", stp)).addOrder(Order.asc("subjectNo"))
//					.addOrder(Order.asc("subjectNo"))
//					.addOrder(Order.asc("count"))
				.list();
		list.addAll(list1);
//		}
		return list;
	}

	@Override
	public StudyMaster findByStudyId(Long activeStudyId) {
		return (StudyMaster) getSession().createCriteria(StudyMaster.class).add(Restrictions.eq("id", activeStudyId))
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Volunteer> avilableBedNos(StudyMaster sm) {
		return getSession().createCriteria(Volunteer.class).add(Restrictions.eq("study", sm)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyPeriodMaster> allStudyPeriodsWithSubEle(StudyMaster sm) {
		return getSession().createCriteria(StudyPeriodMaster.class)
				// .add(Restrictions.ne("periodDesc", "Check In"))
				.add(Restrictions.eq("study", sm)).list();
	}

	@Override
	public Volunteer volunteer(long volid) {
		return (Volunteer) getSession().get(Volunteer.class, volid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectDoseTimePoints> getSachetBarcodeDataWithPeriod(StudyMaster sm, long period) {
		return getSession().createCriteria(SubjectDoseTimePoints.class).add(Restrictions.eq("study", sm))
				.add(Restrictions.eq("period.id", period)).list();
	}

	@Override
	public SampleTimePoints getSampleTimePointsWithTiompointId(Long timePoint) {
		// TODO Auto-generated method stub
		return (SampleTimePoints) getSession().createCriteria(SampleTimePoints.class)
				.add(Restrictions.eq("id", timePoint)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectSampleCollectionTimePoints> getVacutainerBarcodsSubjectWise(StudyMaster sm,
			StudyPeriodMaster spm, String bedNo) {
		List<SubjectSampleCollectionTimePoints> list = new ArrayList<>();
		List<SubjectSampleCollectionTimePoints> list1 = getSession()
				.createCriteria(SubjectSampleCollectionTimePoints.class).add(Restrictions.eq("study", sm))
				.add(Restrictions.eq("period", spm)).add(Restrictions.eq("subjectNo", bedNo))
//					.addOrder(Order.asc("subjectNo"))
				.addOrder(Order.asc("subjectOrder")).list();
		list.addAll(list1);
//		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectSampleCollectionTimePoints> subjectSampleTimePoints(StudyMaster sm, long period, String volId) {
		List<SubjectSampleCollectionTimePoints> list = new ArrayList<>();
//		Volunteer v = studyDao.volunteer(volid);
//		List<Volunteer> volList = studyDao.studyVolunteers(sm);
//		for(Volunteer v : volList) {
		List<SubjectSampleCollectionTimePoints> list1 = getSession()
				.createCriteria(SubjectSampleCollectionTimePoints.class).add(Restrictions.eq("study", sm))
				.add(Restrictions.eq("period.id", period)).add(Restrictions.eq("subjectNo", volId))
//					.addOrder(Order.asc("subjectNo"))
				.addOrder(Order.asc("subjectNo")).list();
		list.addAll(list1);
		return list1;
	}

	@Override
	public List<SubjectSampleCollectionTimePoints> subjectSampleTimePointsSubjectWiseForTimePoint(String timePont) {
		List<SubjectSampleCollectionTimePoints> cstppl = new ArrayList<>();
		SubjectSampleCollectionTimePoints cstpp = (SubjectSampleCollectionTimePoints) getSession()
				.get(SubjectSampleCollectionTimePoints.class, timePont);
		Hibernate.initialize(cstpp.getStudy());
		Hibernate.initialize(cstpp.getPeriod());
		cstppl.add(cstpp);
		return cstppl;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SampleTimePoints> sampleTimePoints(Long studyId) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(SampleTimePoints.class).add(Restrictions.eq("study.id", studyId))
				.addOrder(Order.asc("id")).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DoseTimePoints> doseTimePoints(long studyId) {
		return getSession().createCriteria(DoseTimePoints.class).add(Restrictions.eq("study.id", studyId)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectRandamization> subjectRandamizationOfPeriod(Long periodId) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(SubjectRandamization.class).add(Restrictions.eq("period.id", periodId))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Instrument> getInstrumentList() {
		List<Instrument> list = null;
		list = getSession().createCriteria(Instrument.class).list();
		return list;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Instrument> getInstrumentListWithType(String type) {
//		List<Long> instrumentType = getSession().createCriteria(InstrumentType.class)
//				.add(Restrictions.eq("instrumentType", type)).setProjection(Projections.property("id")).list();
//		List<Instrument> list = new ArrayList<>();
//		if (instrumentType.size() > 0) {
//			list = getSession().createCriteria(Instrument.class)
//					.add(Restrictions.in("instrumentType.id", instrumentType)).list();
//		}
//		return list;
//	}

	@Override
	public Instrument getInstrumentWithId(Long id) {
		Instrument pojo = null;
		pojo = (Instrument) getSession().createCriteria(Instrument.class).add(Restrictions.eq("id", id)).uniqueResult();
		return pojo;
	}

	@Override
	public StudyTreatmentWiseSubjects getStudyTreatmentWiseSubjectsWithPeriodAndStudy(StudyMaster sm,
			StudyPeriodMaster psm, String subjectNo) {
		StudyTreatmentWiseSubjects pojo = null;
		pojo = (StudyTreatmentWiseSubjects) getSession().createCriteria(StudyTreatmentWiseSubjects.class)
				.add(Restrictions.eq("sm", sm)).add(Restrictions.eq("period", psm))
				.add(Restrictions.eq("subjects", subjectNo)).uniqueResult();
		return pojo;
	}

	@Override
	public TreatmentInfo getStudyTreatmentWiseSubjectsWithPeriodAndStudy(TreatmentInfo treatment) {
		TreatmentInfo pojo = null;
		pojo = (TreatmentInfo) getSession().createCriteria(TreatmentInfo.class).add(Restrictions.eq("id", treatment))
				.uniqueResult();
		return pojo;
	}

	@Override
	public SubjectRandamization getSubjectRandamizationWithPeriodAndSubject(StudyPeriodMaster psm, String string) {
		SubjectRandamization pojo = null;
		pojo = (SubjectRandamization) getSession().createCriteria(SubjectRandamization.class)
				.add(Restrictions.eq("period", psm)).add(Restrictions.eq("subjectNo", string)).uniqueResult();
		return pojo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Deepfreezer> getDeepfreezerList() {
		List<Deepfreezer> deeplist = null;
		deeplist = getSession().createCriteria(Deepfreezer.class).list();
		return deeplist;
	}

	@Override
	public Deepfreezer getDeepfreezerWithId(Long id) {
		Deepfreezer deep = null;
		deep = (Deepfreezer) getSession().createCriteria(Deepfreezer.class).add(Restrictions.eq("id", id))
				.uniqueResult();
		return deep;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getStudyPeriodMasterWithStudy(StudyMaster sm) {
		List<Long> list = null;
		list = getSession().createCriteria(StudyPeriodMaster.class).add(Restrictions.eq("study", sm))
				.setProjection(Projections.property("id")).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectRandamization> getSubjectRandamizationWithPeriods(List<Long> psmList) {
		List<SubjectRandamization> list = null;
		list = getSession().createCriteria(SubjectRandamization.class).add(Restrictions.in("period.id", psmList))
				.addOrder(Order.asc("id")).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyPeriodMaster> getStudyPeriodMasterWithStudyList(StudyMaster sm) {
		List<StudyPeriodMaster> list = null;
		list = getSession().createCriteria(StudyPeriodMaster.class).add(Restrictions.eq("study", sm)).list();
		return list;
	}

	@Override
	public SubjectRandamization getSubjectRandamizationWithSubjectAndPeriod(String key, StudyPeriodMaster ppdata) {
		SubjectRandamization subject = null;
		subject = (SubjectRandamization) getSession().createCriteria(SubjectRandamization.class)
				.add(Restrictions.eq("subjectNo", key)).add(Restrictions.eq("period", ppdata)).uniqueResult();
		return subject;
	}

	@Override
	public DeepfreezerShelf getDeepfreezerShelfById(Long id) {
		// TODO Auto-generated method stub
		return (DeepfreezerShelf) getSession().get(DeepfreezerShelf.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectRandamization> getSubjectRandamizationWithPeriodForVial(StudyPeriodMaster pp) {
		List<SubjectRandamization> list = null;
		list = getSession().createCriteria(SubjectRandamization.class).add(Restrictions.eq("period", pp)).list();
		return list;
	}

	
}
