package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.bo.StudyCreationBo;
import com.covideinfo.dao.StudyDao;
import com.covideinfo.dto.AllowStudySubjectMealsDto;
import com.covideinfo.dto.DoseSavingDtoDetails;
import com.covideinfo.dto.DoseTimePointsDto;
import com.covideinfo.dto.DosingCollectionTimePointsDto;
import com.covideinfo.dto.DosingDto;
import com.covideinfo.dto.FormGroupsDto;
import com.covideinfo.dto.FromControlDto;
import com.covideinfo.dto.FromValuesDto;
import com.covideinfo.dto.GlobalParameterDetailsDto;
import com.covideinfo.dto.GlobalparameterFromDto;
import com.covideinfo.dto.MealInfoDto;
import com.covideinfo.dto.MealsDto;
import com.covideinfo.dto.ParameterFormDataDto;
import com.covideinfo.dto.SepatationDtoDetails;
import com.covideinfo.dto.VialRackCollectionDto;
import com.covideinfo.enums.StudyDesign;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.AllowStudySubjectMeals;
import com.covideinfo.model.ApplicationMenus;
import com.covideinfo.model.ApplictionSideMenus;
import com.covideinfo.model.Centrifugation;
import com.covideinfo.model.CentrifugationDataMaster;
import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.DosingInfoDetails;
import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.SampleStorageData;
import com.covideinfo.model.SampleStorageDataRack;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySampleCentrifugation;
import com.covideinfo.model.StudySampleCentrifugationDetails;
import com.covideinfo.model.StudySampleProcessing;
import com.covideinfo.model.StudySampleProcessingDetails;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectPeriodStatus;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectReplacedInfo;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.UserWiseStudiesAsignMaster;
import com.covideinfo.model.VitalTimePoints;
import com.covideinfo.model.Volunteer;

@Repository("studyDao")
@Transactional
@SuppressWarnings("unchecked")
public class StudyDaoImpl extends AbstractDao<Long, StudyMaster> implements StudyDao {

	@Autowired
	StudyCreationBo studyCreationBo;

	@Override
	public StudyMaster findByStudyId(Long studyId) {
		// TODO Auto-generated method stub
		return getByKey(studyId);
	}

	@Override
	public Volunteer volunteerWithsubjectBarcode(String barcode) {
		try {
			Long studyId = Long.parseLong(barcode.substring(2, 8));
			int seqNo = Integer.parseInt(barcode.substring(9, 12));
			Volunteer v = (Volunteer) getSession().createCriteria(Volunteer.class)
					.add(Restrictions.eq("study.id", studyId)).add(Restrictions.eq("seqNo", seqNo)).uniqueResult();
			return v;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Volunteer volunteerWithSecqNo(StudyMaster sm, int parseInt) {
		try {
			Volunteer v = (Volunteer) getSession().createCriteria(Volunteer.class).add(Restrictions.eq("study", sm))
					.add(Restrictions.eq("seqNo", parseInt)).uniqueResult();
			return v;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SubjectPeriodStatus subjectPeriodStatus(StudyMaster std, Volunteer vol) {
//		StatusMaster subjectStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
//				.add(Restrictions.eq("statusCode", StudyStatus.ENROLLED.toString())).uniqueResult();
		SubjectPeriodStatus sps = (SubjectPeriodStatus) getSession().createCriteria(SubjectPeriodStatus.class)
				.add(Restrictions.eq("study", std)).add(Restrictions.eq("volunteer", vol))
				.add(Restrictions.ne("periodNo", 0))
//				.add(Restrictions.eq("subjectStatus", subjectStatus))
				.add(Restrictions.eq("activeStatus", true)).uniqueResult();
		return sps;
	}

	@Override
	public SubjectPeriodStatus subjectPeriodStatusWithPerod(Long periodId, Volunteer vol) {
//		StatusMaster subjectStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
//				.add(Restrictions.eq("statusCode", StudyStatus.ENROLLED.toString())).uniqueResult();
		SubjectPeriodStatus sps = (SubjectPeriodStatus) getSession().createCriteria(SubjectPeriodStatus.class)
				.add(Restrictions.eq("period.id", periodId)).add(Restrictions.eq("volunteer", vol))
				.add(Restrictions.eq("activeStatus", true)).uniqueResult();
		return sps;
	}

	@Override
	public SubjectReplacedInfo standsubjectReplacedInfoSampleCollectionVac(StudyMaster std, Volunteer vol) {
		return (SubjectReplacedInfo) getSession().createCriteria(SubjectReplacedInfo.class)
				.add(Restrictions.eq("study", std)).add(Restrictions.eq("replacedWith", vol))
				.add(Restrictions.eq("activeStatus", true)).uniqueResult();
	}

	@Override
	public List<StudyMaster> allUserActiveStudys(UserMaster user) {
		List<StudyMaster> studys = getSession().createCriteria(UserWiseStudiesAsignMaster.class)
				.add(Restrictions.eq("userId", user)).setProjection(Projections.property("studyMaster")).list();
		return studys;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyPeriodMaster> studyPeriodMaster(StudyMaster study) {
		FromStaticData periodType = (FromStaticData) getSession().createCriteria(FromStaticData.class)
				.add(Restrictions.eq("code", StudyDesign.ADMISSION.toString())).uniqueResult();
		List<StudyPeriodMaster> list = getSession().createCriteria(StudyPeriodMaster.class)
				.add(Restrictions.eq("study", study)).add(Restrictions.ne("periodType", periodType)).list();
		return list;
	}

	@Override
	public StudyPeriodMaster periodById(Long periodId) {
		// TODO Auto-generated method stub
		return (StudyPeriodMaster) getSession().get(StudyPeriodMaster.class, periodId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Volunteer> allStudyVolunteers(StudyMaster sm) {
		return getSession().createCriteria(Volunteer.class).add(Restrictions.eq("study", sm)).list();
	}

	@Override
	public StudyPeriodMaster currentPerod(StudyMaster sm) {
		return (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
				.add(Restrictions.eq("study", sm)).add(Restrictions.eq("currentPeriod", true)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Centrifugation> centrifugeList() {
		// TODO Auto-generated method stub
		return getSession().createCriteria(Centrifugation.class).add(Restrictions.eq("activeStatus", true)).list();
	}

	@Override
	public Centrifugation centrifugationWithId(Long barcodeId) {
		// TODO Auto-generated method stub
		return (Centrifugation) getSession().get(Centrifugation.class, barcodeId);
	}

	@Override
	public Centrifugation saveCentrifugation(Centrifugation cen) {
		getSession().save(cen);
		StringBuilder sb = new StringBuilder();
		sb.append("06");
		sb.append(studyCreationBo.addPrefixZeros(cen.getId() + "", 6));
		cen.setCentrifugationBarcode(studyCreationBo.addSiffixZeros(sb.toString(), 23));
		return cen;

	}

	@Override
	public List<StudyMaster> allStudys() {
		// TODO Auto-generated method stub
		return getSession().createCriteria(StudyMaster.class).list();
	}

	@Override
	public List<StudyPeriodMaster> studyPeriodMasterWithStudyId(Long studyId) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(StudyPeriodMaster.class).add(Restrictions.eq("study.id", studyId)).list();
	}

	@Override
	public List<SampleTimePoints> allSampleTimePoints(Long studyId) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(SampleTimePoints.class).add(Restrictions.eq("study.id", studyId)).list();
	}

	@Override
	public List<MealsTimePoints> allMealsTimePoints(Long studyId) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(MealsTimePoints.class).add(Restrictions.eq("study.id", studyId)).list();
	}

	@Override
	public List<DoseTimePoints> allDoseTimePoints(Long studyId) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(DoseTimePoints.class).add(Restrictions.eq("study.id", studyId)).list();
	}

	@Override
	public StudySubjects studySubject(String studyId, String subjectNo) {
		// TODO Auto-generated method stub
		return (StudySubjects) getSession().createCriteria(StudySubjects.class)
				.add(Restrictions.eq("study.id", Long.parseLong(studyId))).add(Restrictions.eq("subjectNo", subjectNo))
				.uniqueResult();
	}

	@Override
	public DoseTimePoints doseTimePoint(Long parseLong) {
		// TODO Auto-generated method stub
		return (DoseTimePoints) getSession().get(DoseTimePoints.class, parseLong);
	}

	@Override
	public StudyActivities studyActivity(long id, String string) {
		// TODO Auto-generated method stub
		GlobalActivity activityId = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
				.add(Restrictions.eq("activityCode", string)).uniqueResult();
		return (StudyActivities) getSession().createCriteria(StudyActivities.class).add(Restrictions.eq("sm.id", id))
				.add(Restrictions.eq("activityId", activityId)).uniqueResult();
	}

	@Override
	public List<StudyPeriodMaster> getStudyPeriodMasterWithStudy(StudyMaster study) {
		return getSession().createCriteria(StudyPeriodMaster.class).add(Restrictions.eq("study", study)).list();
	}

	@Override
	public DoseTimePointsDto getDoseTimePointsDtoDetails(Long studyId, String activityCode) {
		DoseTimePointsDto dtpDto = null;
		StudyMaster study = null;
		List<StudySubjects> ssubList = null;
		List<Long> subjectIdsList = null;
		List<StudySubjectPeriods> ssubPeriods = null;
		StatusMaster sm = null;
		List<DoseTimePoints> dpList = null;
		List<SubjectDoseTimePoints> sdtpIdsList = null;
		List<TreatmentInfo> treatmentList = null;
		List<StudyPeriodMaster> periodList = null;
		List<SubjectMealsTimePointsData> mealsTimePoints = null;
		GlobalActivity ga = null;
		List<StudyTreatmentWiseSubjects> studyTreatmentWiseSubjectsList = null;
		DosingDto dsDto = null;
		List<DosingInfoDetails> drugInfiList = null;
		String projectType = "";
		Projects project = null;
		try {
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class).add(Restrictions.eq("id", studyId))
					.uniqueResult();
			
			sm = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();

			ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("activityCode", activityCode)).uniqueResult();
			if (study != null) {
				project = (Projects) getSession().createCriteria(Projects.class)
						      .add(Restrictions.eq("projectNo", study.getProjectNo())).uniqueResult();
				
				if(project != null) {
					projectType = (String) getSession().createCriteria(ProjectsDetails.class)
									 .add(Restrictions.eq("projectsId", project))
							         .add(Restrictions.eq("fieldName", "studyDesign"))
							         .setProjection(Projections.property("fieldValue")).uniqueResult();
				}
				
				drugInfiList = getSession().createCriteria(DosingInfoDetails.class)
						         .add(Restrictions.eq("study", study)).list();
				ssubList = getSession().createCriteria(StudySubjects.class).add(Restrictions.eq("study", study)).list();

				// add code from Code Optimization
				subjectIdsList = new ArrayList<>();
				for (StudySubjects ss : ssubList) {
					if (ss.getSubjectStatus().equalsIgnoreCase("Withdraw")
							|| ss.getSubjectStatus().equalsIgnoreCase("DropOut")
							|| ss.getSubjectStatus().equalsIgnoreCase("Replaced")
							|| ss.getSubjectStatus().equalsIgnoreCase("active"))
						subjectIdsList.add(ss.getId());
				}
				// Commented code for Code Optimization
				/*
				 * List<String> strList = new ArrayList<>(); strList.add("Withdraw");
				 * strList.add("DropOut"); strList.add("Replaced"); strList.add("active");
				 * subjectIdsList =
				 * getSession().createCriteria(StudySubjects.class).add(Restrictions.eq("study",
				 * study)) .add(Restrictions.in("subjectStatus",
				 * strList)).setProjection(Projections.property("id")) .list();
				 */

				if (subjectIdsList != null && subjectIdsList.size() > 0) {
					ssubPeriods = getSession().createCriteria(StudySubjectPeriods.class)
							.add(Restrictions.in("subject.id", subjectIdsList)).add(Restrictions.eq("status", sm))
							.list();

					sdtpIdsList = getSession().createCriteria(SubjectDoseTimePoints.class)
							.add(Restrictions.in("studySubjects.id", subjectIdsList)).list();

					mealsTimePoints = getSession().createCriteria(SubjectMealsTimePointsData.class)
							.add(Restrictions.in("subject.id", subjectIdsList)).list();

					studyTreatmentWiseSubjectsList = getSession().createCriteria(StudyTreatmentWiseSubjects.class)
							.add(Restrictions.eq("sm", study)).list();
				}
				dpList = getSession().createCriteria(DoseTimePoints.class).add(Restrictions.eq("study", study)).list();

				treatmentList = getSession().createCriteria(TreatmentInfo.class).add(Restrictions.eq("study", study))
						.list();

				periodList = getSession().createCriteria(StudyPeriodMaster.class).add(Restrictions.eq("study", study))
						.list();

				dsDto = getDosingDtoDetails(studyId, "DOSING");
				dsDto.setStudy(study);
				dtpDto = new DoseTimePointsDto();
				dtpDto.setStudy(study);
				dtpDto.setSubjects(ssubList);
				dtpDto.setSsubPeriods(ssubPeriods);
				dtpDto.setDpList(dpList);
				dtpDto.setSdtpIdsList(sdtpIdsList);
				dtpDto.setTreatmentList(treatmentList);
				dtpDto.setPeriodsList(periodList);
				dtpDto.setMealsData(mealsTimePoints);
				dtpDto.setGa(ga);
				dtpDto.setSubjectTreatmentWiseList(studyTreatmentWiseSubjectsList);
				dtpDto.setDsDto(dsDto);
				dtpDto.setDrugInfiList(drugInfiList);
				dtpDto.setProjectType(projectType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtpDto;
	}

	@Override
	public SubjectDoseTimePoints getSubjectDoseTimePointsRecord(Long doseId) {
		return (SubjectDoseTimePoints) getSession().createCriteria(SubjectDoseTimePoints.class)
				.add(Restrictions.eq("doseTimePoints.id", doseId)).uniqueResult();
	}

	@Override
	public MealsDto getMealsTimePointsDtoDetails(Long studyId, String subjectNo) {
		TreatmentInfo treatment = null;
		StudySubjects studySubject = null;
		StudyPeriodMaster spm = null;
		StatusMaster status = null;
		List<MealsTimePoints> mealsList = null;
		MealsDto mealdto = null;
		List<SubjectMealsTimePointsData> smtpDataList = null;
		List<SubjectDoseTimePoints> sdtpList = null;
		try {
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();

			studySubject = (StudySubjects) getSession().createCriteria(StudySubjects.class)
					.add(Restrictions.eq("subjectNo", subjectNo)).uniqueResult();

			if (studySubject != null) {
				spm = (StudyPeriodMaster) getSession().createCriteria(StudySubjectPeriods.class)
						.add(Restrictions.eq("subject", studySubject)).add(Restrictions.eq("status", status))
						.setProjection(Projections.property("periodId")).uniqueResult();
			}
			if (spm != null) {
				treatment = (TreatmentInfo) getSession().createCriteria(SubjectRandamization.class)
						.add(Restrictions.eq("period", spm))
						.add(Restrictions.eq("subjectNo", studySubject.getSubjectNo()))
						.setProjection(Projections.property("treatmentInfo")).uniqueResult();

				smtpDataList = getSession().createCriteria(SubjectMealsTimePointsData.class)
						.add(Restrictions.eq("studyPeriodMaster", spm)).add(Restrictions.eq("subject", studySubject))
						.list();

				if (treatment != null) {
					mealsList = getSession().createCriteria(MealsTimePoints.class)
							.add(Restrictions.eq("study.id", studyId)).add(Restrictions.eq("treatmentInfo", treatment))
							.list();

					sdtpList = getSession().createCriteria(SubjectDoseTimePoints.class)
							.add(Restrictions.eq("period", spm)).add(Restrictions.eq("studySubjects", studySubject))
							.list();

					if (mealsList != null && mealsList.size() > 0) {
						mealdto = new MealsDto();
						mealdto.setMealsList(mealsList);
						mealdto.setMtpdataList(smtpDataList);
						mealdto.setSdtpList(sdtpList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mealdto;
	}

	@Override
	public DosingCollectionTimePointsDto getDosingCollectionData(Long periodId, Long userId, String subjectNo,
			long doseId, String studyId, String replaceWith, Long devMsgId) {
		DosingCollectionTimePointsDto dctpDto = null;
		SubjectDoseTimePoints sdtp = null;
		StudyPeriodMaster spm = null;
		UserMaster user = null;
		StudySubjects subject = null;
		DoseTimePoints dosingTimePoint = null;
		DoseTimePoints firstDose = null;
		TreatmentInfo treatment = null;
		StudySubjects replaceSubject = null;
		DeviationMessage devMsg = null;
		long minId = 0;
		try {
			spm = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
					.add(Restrictions.eq("id", periodId)).uniqueResult();
			if (spm != null) {

				subject = (StudySubjects) getSession().createCriteria(StudySubjects.class)
						.add(Restrictions.eq("study", spm.getStudy())).add(Restrictions.eq("subjectNo", subjectNo))
						.uniqueResult();

				treatment = (TreatmentInfo) getSession().createCriteria(SubjectRandamization.class)
						.add(Restrictions.eq("subjectNo", subjectNo)).add(Restrictions.eq("period", spm))
						.setProjection(Projections.property("treatmentInfo")).uniqueResult();

				if (treatment != null) {
					minId = (long) getSession().createCriteria(DoseTimePoints.class)
							.add(Restrictions.eq("treatmentInfo", treatment))
							.add(Restrictions.eq("study", spm.getStudy())).setProjection(Projections.min("id"))
							.uniqueResult();
					if (minId != 0) {
						firstDose = (DoseTimePoints) getSession().createCriteria(DoseTimePoints.class)
								.add(Restrictions.eq("id", minId)).uniqueResult();
					}
				}
				if (firstDose != null) {
					sdtp = (SubjectDoseTimePoints) getSession().createCriteria(SubjectDoseTimePoints.class)
							.add(Restrictions.eq("period.id", periodId)).add(Restrictions.eq("studySubjects", subject))
							.add(Restrictions.eq("doseTimePoints", firstDose)).uniqueResult();
				}

			}
			user = (UserMaster) getSession().createCriteria(UserMaster.class).add(Restrictions.eq("id", userId))
					.uniqueResult();

			dosingTimePoint = (DoseTimePoints) getSession().createCriteria(DoseTimePoints.class)
					.add(Restrictions.eq("id", doseId)).uniqueResult();

			if (replaceWith != null && !replaceWith.equals("")) {
				replaceSubject = (StudySubjects) getSession().createCriteria(StudySubjects.class)
						.add(Restrictions.eq("subjectNo", replaceWith))
						.add(Restrictions.eq("study.id", Long.parseLong(studyId))).uniqueResult();
			}

			devMsg = (DeviationMessage) getSession().createCriteria(DeviationMessage.class)
					.add(Restrictions.eq("id", devMsgId)).uniqueResult();

			dctpDto = new DosingCollectionTimePointsDto();
			dctpDto.setSdtp(sdtp);
			dctpDto.setPeriod(spm);
			dctpDto.setUser(user);
			dctpDto.setSubject(subject);
			dctpDto.setDosingTimePoint(dosingTimePoint);
			dctpDto.setFirstDose(firstDose);
			dctpDto.setDevMsg(devMsg);
			dctpDto.setReplaceSubject(replaceSubject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dctpDto;
	}

	@Override
	public StudySampleCentrifugation studySampleCentrifugationDetails(Long studyId) {
		StudySampleCentrifugation ssc = (StudySampleCentrifugation) getSession()
				.createCriteria(StudySampleCentrifugation.class).add(Restrictions.eq("study.id", studyId))
				.uniqueResult();
		StudySampleCentrifugationDetails sscd = (StudySampleCentrifugationDetails) getSession()
				.createCriteria(StudySampleCentrifugationDetails.class).add(Restrictions.eq("centrifugation", ssc))
				.uniqueResult();
//		ssc.setStudySampleCentrifugationDetails(sscd);
		StudySampleProcessing processing = (StudySampleProcessing) getSession()
				.createCriteria(StudySampleProcessing.class).add(Restrictions.eq("study.id", studyId)).uniqueResult();
		StudySampleProcessingDetails sspd = (StudySampleProcessingDetails) getSession()
				.createCriteria(StudySampleProcessingDetails.class).add(Restrictions.eq("processing", processing))
				.uniqueResult();
		if (sspd != null)
			ssc.setSamplePeparation(true);
		return ssc;

	}

	@Override
	public List<StudyPeriodMaster> getStudyPeriodMasterList(long id) {
		List<StudyPeriodMaster> list = getSession().createCriteria(StudyPeriodMaster.class)
				.add(Restrictions.eq("study.id", id)).list();
		return list;
	}

	@Override
	public List<DeepfreezerShelf> getDeepfreezerShelfList() {
		// TODO Auto-generated method stub
		return getSession().createCriteria(DeepfreezerShelf.class).list();
	}

	@Override
	public List<SampleTimePoints> getSampleTimePointDataWithStudyId(long id) {
		List<SampleTimePoints> list = getSession().createCriteria(SampleTimePoints.class)
				.add(Restrictions.eq("study.id", id)).list();
		return list;
	}

	@Override
	public VialRackCollectionDto getSampleTimePointDataWithStudyIdForKeyAndValue(long studyId,
			VialRackCollectionDto dto) {
		Map<Long, String> timPointsMap = new HashMap<>();
		Map<String, SampleTimePoints> samplecollectedData = new HashMap<>();
		Map<Long, Map<Long, Map<Long, SampleStorageData>>> storageMap = new HashMap<>();// periodId, rackId, vitalId,
																						// SampleStorageData
		List<SampleTimePoints> list = null;
		List<SampleStorageDataRack> ssdrList = null;
		try {
			list = getSession().createCriteria(SampleTimePoints.class).add(Restrictions.eq("study.id", studyId)).list();
			for (SampleTimePoints addid : list) {
				samplecollectedData.put("" + addid.getId(), addid);
				timPointsMap.put(addid.getId(), addid.getSign() + addid.getTimePoint());
			}
			dto.setTimePointsMap(timPointsMap);
			dto.setSamplecollectedData(samplecollectedData);

			ssdrList = getSession().createCriteria(SampleStorageDataRack.class).add(Restrictions.eq("studyId", studyId))
					.list();
			Map<Long, Map<Long, SampleStorageData>> rackMap = null;
			Map<Long, SampleStorageData> tpMap = new HashMap<>();
			if (ssdrList != null && ssdrList.size() > 0) {
				for (SampleStorageDataRack ssdr : ssdrList) {
					if (storageMap.containsKey(ssdr.getSampleStorageData().getPeriod().getId())) {
						rackMap = storageMap.get(ssdr.getRackWithVials().getReack().getId());
						if (rackMap.containsKey(ssdr.getSampleStorageData().getTimePoint().getId())) {
							tpMap = rackMap.get(ssdr.getSampleStorageData().getTimePoint().getId());
							if (!tpMap.containsKey(ssdr.getSampleStorageData().getTimePoint().getId())) {
								tpMap.put(ssdr.getSampleStorageData().getTimePoint().getId(),
										ssdr.getSampleStorageData());
								rackMap.put(ssdr.getRackWithVials().getReack().getId(), tpMap);
								storageMap.put(ssdr.getSampleStorageData().getPeriod().getId(), rackMap);
							}
						} else {
							tpMap = new HashMap<>();
							tpMap.put(ssdr.getSampleStorageData().getTimePoint().getId(), ssdr.getSampleStorageData());
							rackMap.put(ssdr.getRackWithVials().getReack().getId(), tpMap);
							storageMap.put(ssdr.getSampleStorageData().getPeriod().getId(), rackMap);
						}
					} else {
						tpMap = new HashMap<>();
						rackMap = new HashMap<>();
						tpMap.put(ssdr.getSampleStorageData().getTimePoint().getId(), ssdr.getSampleStorageData());
						rackMap.put(ssdr.getRackWithVials().getReack().getId(), tpMap);
						storageMap.put(ssdr.getSampleStorageData().getPeriod().getId(), rackMap);
					}
				}
			}
			dto.setStorageMap(storageMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	@Override
	public List<VitalTimePoints> getVitalTimePoint(Long studyId) {
		return getSession().createCriteria(VitalTimePoints.class).add(Restrictions.eq("study.id", studyId)).list();
	}

	@Override
	public GlobalparameterFromDto getGlobalParameterDetails(Long languageId, List<Long> vptParamIds,
			String activityCode) {
		GlobalparameterFromDto gpfDto = null;
		List<GlobalParameterDetailsDto> gpdDtoList = new ArrayList<>();
		List<LanguageSpecificGlobalParameterDetails> gpList = null;
		LanguageSpecificGlobalActivityDetails ga = null;
		GlobalActivity act = null;
		try {
			act = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("activityCode", activityCode)).uniqueResult();

			ga = (LanguageSpecificGlobalActivityDetails) getSession()
					.createCriteria(LanguageSpecificGlobalActivityDetails.class)
					.add(Restrictions.eq("globalActivity", act)).add(Restrictions.eq("inlagId.id", languageId))
					.uniqueResult();

			gpList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
					.add(Restrictions.eq("inlagId.id", languageId)).add(Restrictions.in("parameterId.id", vptParamIds))
					.list();
			if (gpList != null && gpList.size() > 0) {
				GlobalParameterDetailsDto gpDto = new GlobalParameterDetailsDto();
				List<ParameterFormDataDto> parameterDto = new ArrayList<>();
				for (LanguageSpecificGlobalParameterDetails lsgp : gpList) {
					String ctype = lsgp.getParameterId().getContentType().getCode();
					FromControlDto controlType = null;
					List<FromValuesDto> valuesDto = new ArrayList<>();
					if (ctype.equals("RB") || ctype.equals("SB") || ctype.equals("CB")) {
						List<LanguageSpecificValueDetails> gvList = null;
						List<Long> gvIds = getSession().createCriteria(ParameterControlTypeValues.class)
								.add(Restrictions.eq("globalParameter", lsgp.getParameterId()))
								.add(Restrictions.eq("controlType", lsgp.getParameterId().getContentType()))
								.setProjection(Projections.property("globalValues.id")).list();
						if (gvIds.size() > 0) {
							gvList = getSession().createCriteria(LanguageSpecificValueDetails.class)
									.add(Restrictions.in("globalValId.id", gvIds))
									.add(Restrictions.eq("inlagId.id", languageId)).list();
							for (LanguageSpecificValueDetails lsgv : gvList) {
								FromValuesDto fvDto = new FromValuesDto();
								fvDto.setOrder(lsgv.getGlobalValId().getOrderNo());
								fvDto.setValueId(lsgv.getGlobalValId().getId());
								fvDto.setValueName(lsgv.getName());
								valuesDto.add(fvDto);
							}
						}
					}
					controlType = new FromControlDto();
					controlType.setContentCode(ctype);
					controlType.setValuesDto(valuesDto);

					FormGroupsDto group = null;
					if (lsgp.getParameterId().getGroups() != null) {
						LanguageSpecificGroupDetails groupPojo = (LanguageSpecificGroupDetails) getSession()
								.createCriteria(LanguageSpecificGroupDetails.class)
								.add(Restrictions.eq("globlgroupId", lsgp.getParameterId().getGroups()))
								.add(Restrictions.eq("inlagId", languageId)).uniqueResult();
						if (groupPojo != null) {
							group = new FormGroupsDto();
							group.setGroupId(groupPojo.getGloblgroupId().getId());
							group.setGroupName(groupPojo.getName());
							group.setOrderNo(groupPojo.getGloblgroupId().getOrderNo());
						}
					}
					ParameterFormDataDto pfDto = new ParameterFormDataDto();
					pfDto.setControlType(controlType);
					pfDto.setGroup(group);
					pfDto.setOrderNo(lsgp.getParameterId().getOrderNo());
					pfDto.setParameterId(lsgp.getParameterId().getId());
					pfDto.setParameterName(lsgp.getName());
					parameterDto.add(pfDto);
				}
				gpDto.setActivityId(ga.getGlobalActivity().getId());
				gpDto.setActivityName(ga.getName());
				gpDto.setParameterDto(parameterDto);
				gpdDtoList.add(gpDto);
			}
			gpfDto = new GlobalparameterFromDto();
			gpfDto.setGpdDtoList(gpdDtoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gpfDto;
	}

	@Override
	public List<MealsTimePoints> treamentMealsTimePoints(long id) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(MealsTimePoints.class).add(Restrictions.eq("treatmentInfo.id", id)).list();
	}

	@Override
	public MealInfoDto getMealsDetails(Long studyId) {
		MealInfoDto mealInfDto = null;
		List<StudySubjects> subjectsList = null;
		boolean doseDone = false;
		List<Long> mealDoneIds = new ArrayList<>();
		List<SubjectDoseTimePoints> doseList = null;
		StudyMaster study = null;
		List<Long> doseIds = null;
		List<MealsTimePoints> timepoints = null;
		List<SubjectMealsTimePointsData> submDataList = null;
		List<Long> mealIds = null;
		List<TreatmentInfo> treatmentList = null;
		List<SubjectRandamization> srmzList = null;
		List<Long> studyPeriodIds = null;
		List<StudySubjectPeriods> ssubPeriodList = null;
		List<Long> subjectIds = null;
		StatusMaster status = null;
		List<SubjectMealsTimePointsData> subColDatalList = new ArrayList<>();
		Map<String, Map<Long, String>> subjectDoseMap = new HashMap<>(); // Subject, periodId, Done/NotDone
		Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap = new HashMap<>();
		DosingDto dsDto = null;
		List<StudyPeriodMaster> spmList = null;
		Long projectId = null;
		List<AllowStudySubjectMealsDto> alsmList = null;
		try {
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class).add(Restrictions.eq("id", studyId))
					.uniqueResult();
			
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
			
			spmList = getSession().createCriteria(StudyPeriodMaster.class)
					        .add(Restrictions.eq("study.id", studyId)).list();
			if (study != null) {
				projectId = (Long) getSession().createCriteria(Projects.class)
						         .add(Restrictions.eq("projectNo", study.getProjectNo()))
						         .setProjection(Projections.property("projectId")).uniqueResult();
				
				List<String> strList = new ArrayList<>();
				strList.add("DropOut");
				strList.add("active");
				strList.add("Withdraw");
				strList.add("Replaced");
				subjectsList = getSession().createCriteria(StudySubjects.class)
						.add(Restrictions.in("subjectStatus", strList)).add(Restrictions.eq("study", study)).list();

				subjectIds = getSession().createCriteria(StudySubjects.class).add(Restrictions.eq("study", study))
						.add(Restrictions.in("subjectStatus", strList)).setProjection(Projections.property("id"))
						.list();

				if (subjectIds != null && subjectIds.size() > 0) {
					ssubPeriodList = getSession().createCriteria(StudySubjectPeriods.class)
							.add(Restrictions.in("subject.id", subjectIds)).add(Restrictions.eq("status", status))
							.list();
					// code added from optimization
					// code start
					Set<Long> periodIds = new HashSet<>();
					ssubPeriodList.forEach((subPeroid) -> {
						periodIds.add(subPeroid.getPeriodId().getId());
					});
					List<SubjectRandamization> srzList = getSession().createCriteria(SubjectRandamization.class)
							.add(Restrictions.in("period.id", new ArrayList<>(periodIds))).list();
					Map<String, SubjectRandamization> subjectRandamizationMap = new HashMap<>();
					Set<Long> treatementIdSet = new HashSet<>();
					if(srzList.size() > 0) {
						for (SubjectRandamization subjectRandamization : srzList) {
							subjectRandamizationMap.put(
									subjectRandamization.getSubjectNo() + "_" + subjectRandamization.getPeriod().getId(),
									subjectRandamization);
							treatementIdSet.add(subjectRandamization.getTreatmentInfo().getId());
						}
					}else {
						Long treatmentId = (Long) getSession().createCriteria(TreatmentInfo.class)
								              .add(Restrictions.eq("study.id", studyId))
								              .setProjection(Projections.min("id")).uniqueResult();
						if(treatmentId != null)
							treatementIdSet.add(treatmentId);
					}
					List<Long> treatementIds = new ArrayList<>(treatementIdSet);
					/*List<Long> allSubjectsDoseTimePointsIds = getSession().createCriteria(DoseTimePoints.class)
							 .add(Restrictions.in("treatmentInfo.id", treatementIds))
							 .setProjection(Projections.min("id")).list();*/
					List<Long> allSubjectsDoseTimePointsIds = new ArrayList<>();
					List<Object[]> dosIds = null;
					if(treatementIds.size() > 0) {
						dosIds = getSession().createCriteria(DoseTimePoints.class)
		                        .add(Restrictions.eq("study", study))
		                        .add(Restrictions.in("treatmentInfo.id", treatementIds))
		                        .setProjection(Projections.projectionList().add(Projections.min("id")).add(Projections.groupProperty("treatmentInfo.id")))
		                        .list();
		                for(Object[] arr : dosIds) {
		                	allSubjectsDoseTimePointsIds.add((Long)arr[0]);
		                }
					}
					List<SubjectDoseTimePoints> sdtpList = getSession().createCriteria(SubjectDoseTimePoints.class)
							.add(Restrictions.in("doseTimePoints.id", allSubjectsDoseTimePointsIds)).list();
					Map<String, SubjectDoseTimePoints> subjectDoseTimePointsMap = new HashMap<>();
					for (SubjectDoseTimePoints subjectDoseTimePoint : sdtpList) {
						subjectDoseTimePointsMap.put(subjectDoseTimePoint.getStudySubjects().getId() + "_"
								+ subjectDoseTimePoint.getPeriod().getId(), subjectDoseTimePoint);
					}
					// code end

					Map<Long, String> tempMap = null;
					if (ssubPeriodList != null && ssubPeriodList.size() > 0) {
						for (StudySubjectPeriods ssp : ssubPeriodList) {
							if (subjectDoseMap.containsKey(ssp.getSubject().getSubjectNo())) {
								tempMap = subjectDoseMap.get(ssp.getSubject().getSubjectNo());
								if (!tempMap.containsKey(ssp.getPeriodId().getId())) {
									String result = getDosingString(ssp);
									tempMap.put(ssp.getPeriodId().getId(), result);
									subjectDoseMap.put(ssp.getSubject().getSubjectNo(), tempMap);
								}
							} else {
								String result = getDosingString(ssp);
								tempMap = new HashMap<>();
								tempMap.put(ssp.getPeriodId().getId(), result);
								subjectDoseMap.put(ssp.getSubject().getSubjectNo(), tempMap);
							}
							//code added from optimization
							SubjectRandamization srz = null;
							if(ssp.getSubject().getStdSubjectId() != null)
								srz = subjectRandamizationMap.get(ssp.getSubject().getStdSubjectId().getSubjectNo()+"_"+ssp.getPeriodId().getId());
							else 
								srz = subjectRandamizationMap.get(ssp.getSubject().getSubjectNo()+"_"+ssp.getPeriodId().getId());

							// code commented for optimized code
							/*
							 * SubjectRandamization srz = (SubjectRandamization) getSession()
							 * .createCriteria(SubjectRandamization.class) .add(Restrictions.eq("subjectNo",
							 * ssp.getSubject().getSubjectNo())) .add(Restrictions.eq("period",
							 * ssp.getPeriodId())).uniqueResult();
							 */
							if (srz != null) {
								// code commented for optimized code
								/*
								 * Long minId = (Long) getSession().createCriteria(DoseTimePoints.class)
								 * .add(Restrictions.eq("treatmentInfo", srz.getTreatmentInfo()))
								 * .setProjection(Projections.min("id")).uniqueResult(); if (minId != null) {
								 */
								// code added from optimization
								SubjectDoseTimePoints sdtp = subjectDoseTimePointsMap
										.get(ssp.getSubject().getId() + "_" + ssp.getPeriodId().getId());
								// code commented for optimized code
//									SubjectDoseTimePoints sdtp = (SubjectDoseTimePoints) getSession()
//											.createCriteria(SubjectDoseTimePoints.class)
//											.add(Restrictions.eq("studySubjects", ssp.getSubject()))
//											.add(Restrictions.eq("period", ssp.getPeriodId()))
//											.add(Restrictions.eq("doseTimePoints.id", minId)).uniqueResult();
								if (sdtp != null) {
									Map<Long, Map<Long, SubjectDoseTimePoints>> pdMap = new HashMap<>();
									Map<Long, SubjectDoseTimePoints> trMap = new HashMap<>();
									trMap.put(srz.getTreatmentInfo().getId(), sdtp);
									pdMap.put(srz.getPeriod().getId(), trMap);
									dosedMap.put(sdtp.getStudySubjects().getSubjectNo(), pdMap);
								}
//								}
							}
						}
					}

				}

				studyPeriodIds = getSession().createCriteria(StudyPeriodMaster.class)
						.add(Restrictions.eq("study", study)).setProjection(Projections.property("id")).list();

				if (studyPeriodIds != null && studyPeriodIds.size() > 0) {
					srmzList = getSession().createCriteria(SubjectRandamization.class)
							.add(Restrictions.in("period.id", studyPeriodIds)).list();
				}

				treatmentList = getSession().createCriteria(TreatmentInfo.class).add(Restrictions.eq("study", study))
						.list();

				doseIds = getSession().createCriteria(DoseTimePoints.class).add(Restrictions.eq("study", study))
						.setProjection(Projections.property("id")).list();
				if (doseIds != null && doseIds.size() > 0) {
					doseList = getSession().createCriteria(SubjectDoseTimePoints.class)
							.add(Restrictions.in("doseTimePoints.id", doseIds)).list();
					if (doseList != null && doseList.size() > 0)
						doseDone = true;
				}
				timepoints = getSession().createCriteria(MealsTimePoints.class).add(Restrictions.eq("study", study))
						.list();
				// code added from optimization
				mealIds = new ArrayList<>();
				for (MealsTimePoints mtp : timepoints) {
					mealIds.add(mtp.getId());
				}
				// code commented for optimized code
//				mealIds = getSession().createCriteria(MealsTimePoints.class).add(Restrictions.eq("study", study))
//						.setProjection(Projections.property("id")).list();
				if (mealIds != null && mealIds.size() > 0) {
					submDataList = getSession().createCriteria(SubjectMealsTimePointsData.class)
							.add(Restrictions.isNotNull("endTime")).add(Restrictions.in("mealsTimePoint.id", mealIds))
							.list();

					subColDatalList = getSession().createCriteria(SubjectMealsTimePointsData.class)
							.add(Restrictions.isNotNull("startTime")).add(Restrictions.isNull("endTime"))
							.add(Restrictions.in("mealsTimePoint.id", mealIds)).list();
				}
				dsDto = getDosingDtoDetails(studyId, "MEALS");
				dsDto.setStudy(study);
			}
			alsmList = getSession().createCriteria(AllowStudySubjectMeals.class)
					       .add(Restrictions.eq("studyId.id", studyId))
					       .add(Restrictions.eq("status.id", status.getId()))
					       .createAlias("period", "p")
					       .createAlias("mealId", "meal")
					       .setProjection(Projections.projectionList()
					    		  .add(Projections.property("id"), "id")
					    		  .add(Projections.property("p.id"), "periodId")
					    		  .add(Projections.property("meal.sign"), "tpSign")
					    		  .add(Projections.property("meal.timePoint"), "timePoint")
					    		  .add(Projections.property("subjects"), "subjects")
					    		  .add(Projections.property("allowedTime"), "allowedTime"))
					       .setResultTransformer(Transformers.aliasToBean(AllowStudySubjectMealsDto.class)).list();
					       
			
			mealInfDto = new MealInfoDto();
			mealInfDto.setTreatmentSpecificMeals(study.isTreatmentWiseMealsTimepoints());
			mealInfDto.setDoseDone(doseDone);
			mealInfDto.setMealDoneIds(mealDoneIds);
			mealInfDto.setSubmDataList(submDataList);
			mealInfDto.setTimepoints(timepoints);
			mealInfDto.setTreatmentList(treatmentList);
			mealInfDto.setSubjectsList(subjectsList);
			mealInfDto.setSrmzList(srmzList);
			mealInfDto.setStudy(study);
			mealInfDto.setSsubPeriodList(ssubPeriodList);
			mealInfDto.setSubColDatalList(subColDatalList);
			mealInfDto.setSubjectDoseMap(subjectDoseMap);
			mealInfDto.setDosedMap(dosedMap);
			mealInfDto.setDsDto(dsDto);
			mealInfDto.setSpmList(spmList);
			mealInfDto.setProjectId(projectId);
			mealInfDto.setAlsmList(alsmList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mealInfDto;
	}

	private String getDosingString(StudySubjectPeriods ssp) {
		String result = "NotDone";
		List<SubjectDoseTimePoints> subdoseList = null;
		try {
			subdoseList = getSession().createCriteria(SubjectDoseTimePoints.class)
					.add(Restrictions.eq("period", ssp.getPeriodId()))
					.add(Restrictions.eq("studySubjects", ssp.getSubject())).list();
			if (subdoseList != null && subdoseList.size() > 0)
				result = "Done";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public DosingDto getDosingDtoDetails(Long studyId, String collectionStr) {
		DosingDto dsDto = null;
		List<DeviationMessage> devMsgList = null;
		StudyMaster study = null;
		List<String> typesList = new ArrayList<>();
		try {
			typesList.add(collectionStr);
			typesList.add("ALL");
			// Already we have study object and study assigned after getDosingDtoDetails()
			// call
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class).add(Restrictions.eq("id", studyId))
					.uniqueResult();

			devMsgList = getSession().createCriteria(DeviationMessage.class)
					.add(Restrictions.in("collectionType", typesList)).list();

			dsDto = new DosingDto();
			dsDto.setStudy(study);
			dsDto.setDevMsgList(devMsgList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsDto;
	}

	@Override
	public DoseSavingDtoDetails getDoseSavingDtoDetails(String studyId, String subjectId, Long perodId, Long userId,
			String replaceWith, String tpId, Long devMessageId) {
		DoseSavingDtoDetails dsDto = null;
		StudyPeriodMaster period = null;
		UserMaster user = null;
		DoseTimePoints dosingTimePoint = null;
		StudySubjects subject = null;
		StudySubjects replaceSubject = null;
		DeviationMessage dvm = null;
		try {
			period = (StudyPeriodMaster) getSession().get(StudyPeriodMaster.class, perodId);
			user = (UserMaster) getSession().createCriteria(UserMaster.class).add(Restrictions.eq("id", userId))
					.uniqueResult();

			subject = (StudySubjects) getSession().createCriteria(StudySubjects.class)
					.add(Restrictions.eq("study.id", Long.parseLong(studyId)))
					.add(Restrictions.eq("subjectNo", subjectId)).uniqueResult();

			replaceSubject = (StudySubjects) getSession().createCriteria(StudySubjects.class)
					.add(Restrictions.eq("study.id", Long.parseLong(studyId)))
					.add(Restrictions.eq("subjectNo", replaceWith)).uniqueResult();

			dosingTimePoint = (DoseTimePoints) getSession().get(DoseTimePoints.class, Long.parseLong(tpId));

			dvm = (DeviationMessage) getSession().createCriteria(DeviationMessage.class)
					.add(Restrictions.eq("id", devMessageId)).uniqueResult();

			dsDto = new DoseSavingDtoDetails();
			dsDto.setPeriod(period);
			dsDto.setUser(user);
			dsDto.setSubject(subject);
			dsDto.setReplaceSubject(replaceSubject);
			dsDto.setDosingTimePoint(dosingTimePoint);
			dsDto.setDvm(dvm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsDto;
	}

	@Override
	public SepatationDtoDetails getSepatationDtoDetails(Long studyId) {
		SepatationDtoDetails spDto = null;
		StudyMaster study = null;
		List<StudyPeriodMaster> periodList = null;
		List<SampleTimePoints> timepoints = null;
		List<CentrifugationDataMaster> centrifugationList = null;
		StatusMaster status = null;
		List<StudySubjects> subjectsList = null;
		try {
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class).add(Restrictions.eq("id", studyId))
					.uniqueResult();
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.NEW.toString())).uniqueResult();
			if (study != null) {
				periodList = getSession().createCriteria(StudyPeriodMaster.class).add(Restrictions.eq("study", study))
						.list();

				timepoints = getSession().createCriteria(SampleTimePoints.class)
						.add(Restrictions.eq("study.id", studyId)).addOrder(Order.asc("id")).list();
				centrifugationList = getSession().createCriteria(CentrifugationDataMaster.class)
						.add(Restrictions.eq("study", study)).add(Restrictions.eq("status", status)).list();
				subjectsList = getSession().createCriteria(StudySubjects.class).add(Restrictions.eq("study", study))
						.list();
			}

			spDto = new SepatationDtoDetails();
			spDto.setStudy(study);
			spDto.setPeriodList(periodList);
			spDto.setTimepoints(timepoints);
			spDto.setCentrifugationList(centrifugationList);
			spDto.setSubjectsList(subjectsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spDto;
	}

	@Override
	public void saveApplicationSideMenuds() {
		applicationMenus();
		applicationSideMenus();
	}

	private void applicationMenus() {

		Long count = null;
		try {
			count = (Long) getSession().createCriteria(ApplicationMenus.class).setProjection(Projections.rowCount())
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count != null) {
			List<ApplicationMenus> amList = new ArrayList<>();

			amList.add(new ApplicationMenus("Site Administrator", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("User Configuration", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Administration", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Instrument", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Pharmacy", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Study", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Barcode", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Review", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Reports", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Logs", "superadmin", new Date(),true));

			for (ApplicationMenus am : amList) {
				ApplicationMenus amPojo = (ApplicationMenus) getSession().createCriteria(ApplicationMenus.class)
						                        .add(Restrictions.eq("name", am.getName())).uniqueResult();
				if(amPojo == null) {
					getSession().save(am);
				}else {
					amPojo.setName(am.getName());
					getSession().merge(amPojo);
				}
				
			}
		}

	}

	private void applicationSideMenus() {
		Long count = null;
		try {
			count = (Long) getSession().createCriteria(ApplictionSideMenus.class).setProjection(Projections.rowCount())
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count != null) {
			List<ApplictionSideMenus> asmList = new ArrayList<>();
			List<ApplicationMenus> apmList = getSession().createCriteria(ApplicationMenus.class).list();
			Map<Long, ApplicationMenus> amMap = new HashMap<>();
			if (apmList != null && apmList.size() > 0) {
				for (ApplicationMenus apm : apmList)
					amMap.put(apm.getId(), apm);

				//Site Administration and User Coniguration
				asmList.add(new ApplictionSideMenus("Module Access", amMap.get(1L), "superadmin", new Date(), "/modulesAccess/getModuleAccessForm",true));
				asmList.add(new ApplictionSideMenus("User Creation", amMap.get(2L), "superadmin", new Date(), "/user/createUser",true));
				asmList.add(new ApplictionSideMenus("Role Creation", amMap.get(2L), "superadmin", new Date(), "/userRole/createRole",true));
				
				//Administration 
				asmList.add(new ApplictionSideMenus("Units", amMap.get(3L), "superadmin", new Date(), "/units/unitsForm",true));
				asmList.add(new ApplictionSideMenus("Values", amMap.get(3L), "superadmin", new Date(), "/values/valuesPage",true));
				asmList.add(new ApplictionSideMenus("Groups", amMap.get(3L), "superadmin", new Date(), "/groups/groupPage",true));
				asmList.add(new ApplictionSideMenus("Activity", amMap.get(3L), "superadmin", new Date(), "/globalAtivity/activityPage",true));
				asmList.add(new ApplictionSideMenus("Parameters", amMap.get(3L), "superadmin", new Date(), "/parameters/parmetersPage",true));
				asmList.add(new ApplictionSideMenus("Default Activity", amMap.get(3L), "superadmin", new Date(), "/defaultActivity/defaultActivityForm",true));
				asmList.add(new ApplictionSideMenus("Custom Activity Parameters", amMap.get(3L), "superadmin", new Date(), "/customActParams/createCustomActivityParameters",true));
				asmList.add(new ApplictionSideMenus("Rule", amMap.get(3L), "superadmin", new Date(), "/rules/rulesPage",true));
				asmList.add(new ApplictionSideMenus("Condition", amMap.get(3L), "superadmin", new Date(), "/condition/conditionForm",true));
				asmList.add(new ApplictionSideMenus("Group Design", amMap.get(3L), "superadmin", new Date(), "/groups/groupDesignActivity",true));
				asmList.add(new ApplictionSideMenus("Diet Plan", amMap.get(3L), "superadmin", new Date(), "/mealDiet/mealDietPlanList",true));
				
				//Instrument
				asmList.add(new ApplictionSideMenus("Instrument", amMap.get(4L), "superadmin", new Date(), "/instrument/instrumentForm",true));
				asmList.add(new ApplictionSideMenus("Deep Freezer", amMap.get(4L), "superadmin", new Date(), "/instrument/deepfreezerForm",false));
				//Pharmacy
				asmList.add(new ApplictionSideMenus("Drug List", amMap.get(5L), "superadmin", new Date(), "/drugReception/drugListForm",false));
				asmList.add(new ApplictionSideMenus("Drug Reception Approval", amMap.get(5L), "superadmin", new Date(), "/drugReception/drugApprovalList",false));
				asmList.add(new ApplictionSideMenus("Drug Reception", amMap.get(5L), "superadmin", new Date(), "/drugReception/drugReceptionForm",false));
				asmList.add(new ApplictionSideMenus("Documentary Requirements", amMap.get(5L), "superadmin", new Date(), "/documentryRequ/documentryRequirements",false));
				asmList.add(new ApplictionSideMenus("Drug Dispensing List", amMap.get(5L), "superadmin", new Date(), "/studyDrugDispensing/studyDrugDispensingList",false));
				asmList.add(new ApplictionSideMenus("Line Clearance Activity", amMap.get(5L), "superadmin", new Date(), "/pharmacyData/lineClearanceActivityList",false));
				asmList.add(new ApplictionSideMenus("Dispensed IP Handover", amMap.get(5L), "superadmin", new Date(), "/pharmacyData/dispensedIPHandoverList",false));
				asmList.add(new ApplictionSideMenus("IP Retention", amMap.get(5L), "superadmin", new Date(), "/pharmacyData/ipRetentionList",false));
				asmList.add(new ApplictionSideMenus("Status Of Unused Dispensed", amMap.get(5L), "superadmin", new Date(), "/pharmacyData/statusOfUnusedDispensedList",false));
				asmList.add(new ApplictionSideMenus("IP Discard", amMap.get(5L), "superadmin", new Date(), "/pharmacyData/ipDiscardList",false));
				//Study
				asmList.add(new ApplictionSideMenus("Randomization", amMap.get(6L), "superadmin", new Date(), "/randomization/uploadRandomizationForm",true));
				asmList.add(new ApplictionSideMenus("Project List", amMap.get(6L), "superadmin", new Date(), "/studydesign/studyDesignPage",true));
				asmList.add(new ApplictionSideMenus("Assign Study", amMap.get(6L), "superadmin", new Date(), "/delegation/delegationPage",true));
				asmList.add(new ApplictionSideMenus("Study Execution", amMap.get(6L), "superadmin", new Date(), "/studyExe/studyExePage",true));
				asmList.add(new ApplictionSideMenus("Deviations", amMap.get(6L), "superadmin", new Date(), "/deviations/deviationPage/Submit",true));
				asmList.add(new ApplictionSideMenus("Study Groups", amMap.get(6L), "superadmin", new Date(), "/studyGroups/studyGroupsForm",true));
				asmList.add(new ApplictionSideMenus("Meal Menu", amMap.get(6L), "superadmin", new Date(), "/stdMealDietConfig/studyMealDietConfiguration",true));
				asmList.add(new ApplictionSideMenus("Left Over Meal Record", amMap.get(6L), "superadmin", new Date(), "/subMealCon/studyMealConsumptionPage",true));
				asmList.add(new ApplictionSideMenus("Drug Dispensing Details", amMap.get(6L), "superadmin", new Date(), "/drugDispanse/drugDispanseEntry",true));
				asmList.add(new ApplictionSideMenus("Allow Meals", amMap.get(6L), "superadmin", new Date(), "/allowMeal/allowMealsPage",true));
				
				//Barcodes
				asmList.add(new ApplictionSideMenus("Barcodes", amMap.get(7L), "superadmin", new Date(), "/barcodelabels/barcodelabelsPage",true));
				asmList.add(new ApplictionSideMenus("Subject Container", amMap.get(7L), "superadmin", new Date(), "/barcodelabels/stdSubjectContainerPrint",false));
				asmList.add(new ApplictionSideMenus("Box", amMap.get(7L), "superadmin", new Date(), "/barcodelabels/boxBarCodePrintPage",false));
				asmList.add(new ApplictionSideMenus("Instrument", amMap.get(7L), "superadmin", new Date(), "/barcodelabels/instrumentBarCode",true));
				asmList.add(new ApplictionSideMenus("Deep Freezer", amMap.get(7L), "superadmin", new Date(), "/barcodelabels/deepFreezerBarCode",false));
				asmList.add(new ApplictionSideMenus("Centrifuge", amMap.get(7L), "superadmin", new Date(), "/barcodelabels/centrifugation",false));
				
				//Review
				asmList.add(new ApplictionSideMenus("Projects", amMap.get(8L), "superadmin", new Date(), "/studydesign/projectApprovalList",true));
				asmList.add(new ApplictionSideMenus("Randomization", amMap.get(8L), "superadmin", new Date(), "/randomization/randomizationApprovalList",true));
				asmList.add(new ApplictionSideMenus("Study Review", amMap.get(8L), "superadmin", new Date(), "/studyExe/studyReview",true));
				asmList.add(new ApplictionSideMenus("Response to Study Review", amMap.get(8L), "superadmin", new Date(), "/studyExe/responseToStudyReview",true));
				asmList.add(new ApplictionSideMenus("Deviations", amMap.get(8L), "superadmin", new Date(), "/deviations/deviationPage/Review",true));
				
				//Reports
				asmList.add(new ApplictionSideMenus("Drug Dispensing PDF", amMap.get(9L), "superadmin", new Date(), "/drugDispansePdf/generateDrugDispansePdf",true));
				asmList.add(new ApplictionSideMenus("Data Crf", amMap.get(9L), "superadmin", new Date(), "/crfData/studyCrfData",true));
				asmList.add(new ApplictionSideMenus("Meal Menu", amMap.get(9L), "superadmin", new Date(), "/mealMenuReports/generateMealMenuReport",true));
				asmList.add(new ApplictionSideMenus("Missed Timepoints", amMap.get(9L), "superadmin", new Date(), "/missedtpsReports/generateMissedTimepointsReport",true));
				
				//Logs
				asmList.add(new ApplictionSideMenus("Audit Log ", amMap.get(10L), "superadmin", new Date(), "/auditlog/auditLogTrigerDataSearch",true));
				asmList.add(new ApplictionSideMenus("Activity Log ", amMap.get(10L), "superadmin", new Date(), "/auditlog/activityLoginDataSearch",true));
				asmList.add(new ApplictionSideMenus("Login Log ", amMap.get(10L), "superadmin", new Date(), "/auditlog/userLogInfoPage",true));
			
				for (ApplictionSideMenus asm : asmList) {
					ApplictionSideMenus asmPojo = (ApplictionSideMenus) getSession().createCriteria(ApplictionSideMenus.class)
							                         .add(Restrictions.eq("appMenu.id", asm.getAppMenu().getId()))
							                         .add(Restrictions.eq("controllerName", asm.getControllerName()))
							                         .add(Restrictions.eq("name", asm.getName())).uniqueResult();
					if(asmPojo == null) {
						getSession().save(asm);
					}else {
						if(!asm.getName().equalsIgnoreCase(asmPojo.getName())) {
							asmPojo.setName(asm.getName());
							asmPojo.setAppMenu(asm.getAppMenu());
							asmPojo.setControllerName(asm.getControllerName());
							getSession().merge(asmPojo);
						}
					}
							                         
					
				}

			}

		}

	}

	@Override
	public StudySubjects studySubject(Long studyId, String subjectNo) {
		// TODO Auto-generated method stub
		return (StudySubjects) getSession().createCriteria(StudySubjects.class)
				.add(Restrictions.eq("study.id", studyId)).add(Restrictions.eq("subjectNo", subjectNo)).uniqueResult();
	}

	@Override
	public SubjectRandamization subjectRandamization(Long periodId, String subjectNo) {
		return (SubjectRandamization) getSession().createCriteria(SubjectRandamization.class)
				.add(Restrictions.eq("period.id", periodId)).add(Restrictions.eq("subjectNo", subjectNo))
				.uniqueResult();
	}

	@Override
	public List<SampleTimePoints> sampleTimePoints(Long studyId, SubjectRandamization subjectRandamization,
			boolean statndby) {
		// TODO Auto-generated method stub
		if (subjectRandamization != null) {
			return (List<SampleTimePoints>) getSession().createCriteria(SampleTimePoints.class)
					.add(Restrictions.eq("study.id", studyId))
					.add(Restrictions.eq("treatmentInfo.id", subjectRandamization.getTreatmentInfo().getId()))
					.addOrder(Order.asc("id")).list();
		} else {
			List<Object[]> timePointsList = null;
			if (!statndby) {
				timePointsList = getSession().createCriteria(SampleTimePoints.class)
						.add(Restrictions.eq("study.id", studyId)).setProjection(Projections.projectionList()
								.add(Projections.min("id")).add(Projections.groupProperty("timePoint")))
						.addOrder(Order.asc("timePoint")).list();
			} else {
				timePointsList = getSession()
						.createCriteria(SampleTimePoints.class).add(Restrictions.eq("study.id", studyId))
						.add(Restrictions.eq("sign", "-")).setProjection(Projections.projectionList()
								.add(Projections.min("id")).add(Projections.groupProperty("timePoint")))
						.addOrder(Order.asc("timePoint")).list();
			}
			List<Long> ids = new ArrayList<>();
			for (Object[] arr : timePointsList) {
				ids.add((Long) arr[0]);
			}

			if (ids.size() > 0)
				return (List<SampleTimePoints>) getSession().createCriteria(SampleTimePoints.class)
						.add(Restrictions.in("id", ids)).addOrder(Order.asc("id")).list();
			else
				return new ArrayList<>();
		}
	}

	@Override
	public SampleTimePoints sampleTimePointsWithId(Long timePointId) {
		// TODO Auto-generated method stub
		return  (SampleTimePoints) getSession().createCriteria(SampleTimePoints.class).add(Restrictions.eq("id", timePointId)).uniqueResult();
	}

	
}
