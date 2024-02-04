package com.covideinfo.clinical.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.bo.SampleCollectionDashBoardColumns;
import com.covideinfo.clinical.dao.ClinicalDao;
import com.covideinfo.dto.BloodSamplesCollectionDto;
import com.covideinfo.dto.CentrificationDetailsDto;
import com.covideinfo.dto.CommonTpDetails;
import com.covideinfo.dto.CpuOnlineData;
import com.covideinfo.dto.DoseDataDto;
import com.covideinfo.dto.DosingDto;
import com.covideinfo.dto.FormGroupsDto;
import com.covideinfo.dto.FromControlDto;
import com.covideinfo.dto.FromValuesDto;
import com.covideinfo.dto.GlobalParameterDetailsDto;
import com.covideinfo.dto.GlobalparameterFromDto;
import com.covideinfo.dto.MealCollectoinDto;
import com.covideinfo.dto.MealsDataSavingDto;
import com.covideinfo.dto.MealsRealTimeCommunicationDto;
import com.covideinfo.dto.ParameterFormDataDto;
import com.covideinfo.dto.PlannedTimeDetailsDto;
import com.covideinfo.dto.RealTimeCommunicationDto;
import com.covideinfo.dto.SampleCollectionSavingDto;
import com.covideinfo.dto.SampleCollectoinDto;
import com.covideinfo.dto.StudyActivityDataDto;
import com.covideinfo.dto.VilaRackDtoDetails;
import com.covideinfo.dto.VitalCollectionDataDto;
import com.covideinfo.dto.VitalCollectionSavingDto;
import com.covideinfo.dto.VitalTimePointsCollectionDto;
import com.covideinfo.enums.StaticData;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.enums.StudyDesign;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.Centrifugation;
import com.covideinfo.model.CentrifugationData;
import com.covideinfo.model.CentrifugationDataMaster;
import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.DosePerameter;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.DosingInfo;
import com.covideinfo.model.ECGTimePoints;
import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.InstrumentType;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.LoadedVacutinersInCentrifuge;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.Projects;
import com.covideinfo.model.RackWithVials;
import com.covideinfo.model.RackWithVitalSubject;
import com.covideinfo.model.SampleContainer;
import com.covideinfo.model.SampleContainerVials;
import com.covideinfo.model.SampleStorageData;
import com.covideinfo.model.SampleStorageDataRack;
import com.covideinfo.model.SampleStorateDataMaster;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.SamplesCentrifugation;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyGroupPeriodMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectDeviations;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.SubejectDosePerameter;
import com.covideinfo.model.SubjectDoseTimePointParametersData;
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
import com.covideinfo.model.SubjectVitalParametersData;
import com.covideinfo.model.SubjectVitalTimePoints;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.model.SubjectWithdrawDetails;
import com.covideinfo.model.TimePointVitalTests;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.VialSeparationData;
import com.covideinfo.model.VitalTimePoints;
import com.covideinfo.model.Volunteer;
import com.covideinfo.util.CorsConfigurationProcess;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository("clinicalDao")
@PropertySource(value = { "classpath:application.properties" })
@SuppressWarnings("unchecked")
public class ClinicalDaoImpl extends AbstractDao<Long, StudyMaster> implements ClinicalDao {
	@Autowired
	private Environment environment;
	
	private ReentrantLock mutex = new ReentrantLock();

	@Override
	public SubjectSampleCollectionTimePoints subjectSampleCollectionTimePoints(String barcode) {
		SubjectSampleCollectionTimePoints cstpp = (SubjectSampleCollectionTimePoints) getSession()
				.get(SubjectSampleCollectionTimePoints.class, barcode);
		return cstpp;
	}

	@Override
	public SubjectSampleCollectionTimePointsData subjectSampleCollectionTimePointsData(
			SubjectSampleCollectionTimePoints vac) {
		// TODO Auto-generated method stub
		return (SubjectSampleCollectionTimePointsData) getSession()
				.createCriteria(SubjectSampleCollectionTimePointsData.class)
				.add(Restrictions.eq("subjectSampleCollectionTimPoints", vac)).uniqueResult();
	}

	@Override
	public List<SubjectSampleCollectionTimePoints> previousSamplesIfAny(StudyMaster study, StudyPeriodMaster period,
			TreatmentInfo treatmentInfo, String subjectNo, int timePointNo, SubjectSampleCollectionTimePoints vac) {
		List<SubjectSampleCollectionTimePoints> result = new ArrayList<>();

		Criteria cri = getSession().createCriteria(SubjectSampleCollectionTimePoints.class)
				.add(Restrictions.eq("study", vac.getStudy())).add(Restrictions.eq("period", vac.getPeriod()))
				.add(Restrictions.eq("subjectNo", subjectNo))
				.add(Restrictions.eq("timePointNo", vac.getTimePointNo() - 1))
				.add(Restrictions.isNull("subjectSampleCollectionTimePointsData"))
				.add(Restrictions.lt("timePointNo", timePointNo));
		if (vac.getTreatmentInfo() != null)
			cri.add(Restrictions.eq("treatmentInfo", vac.getTreatmentInfo()));

		List<SubjectSampleCollectionTimePoints> ssct = cri.list();
		if (ssct.size() > 0) {
			cri = getSession().createCriteria(SubjectSampleCollectionTimePoints.class)
					.add(Restrictions.eq("study", vac.getStudy())).add(Restrictions.eq("period", vac.getPeriod()))
					.add(Restrictions.eq("subjectNo", subjectNo))
					.add(Restrictions.isNull("subjectSampleCollectionTimePointsData"))
					.add(Restrictions.lt("timePointNo", timePointNo));
			if (vac.getTreatmentInfo() != null)
				cri.add(Restrictions.eq("treatmentInfo", vac.getTreatmentInfo()));
			List<SubjectSampleCollectionTimePoints> list = cri.list();
			for (SubjectSampleCollectionTimePoints ssctp : list) {
				if (ssctp.getSubjectSampleCollectionTimePointsData() == null
						|| ssctp.getSubjectSampleCollectionTimePointsData().getActualtime() == null) {
					result.add(ssctp);
				} else
					break;
			}
		}

		return result;
	}

	@Override
	public void updateSubjectSampleCollectionTimePoints(SubjectSampleCollectionTimePoints vac) {
		getSession().save(vac.getSubjectSampleCollectionTimePointsData());
		getSession().merge(vac);
	}

	@Override
	public SubjectDoseTimePoints subjectDoseTimePoints(String barcode, Long studyId) {
		SubjectDoseTimePoints sdtp = (SubjectDoseTimePoints) getSession().get(SubjectDoseTimePoints.class, barcode);
		return sdtp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectDoseTimePoints> oldSubjectDoseTimePoints(SubjectDoseTimePoints tech) {
//		List<SubjectDoseTimePoints> list = getSession().createCriteria(SubjectDoseTimePoints.class)
//				.add(Restrictions.eq("study", tech.getStudy())).add(Restrictions.eq("period", tech.getPeriod()))
//				.add(Restrictions.eq("treatmentInfo", tech.getTreatmentInfo()))
//				.add(Restrictions.eq("subjectNo", tech.getSubjectNo()))
//				.add(Restrictions.ne("timePoint", tech.getTimePoint()))
//				.add(Restrictions.lt("timePointNo", tech.getTimePointNo())).add(Restrictions.isNull("actualtime"))
//				.list();
//		return list;
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DosePerameter> dosePerameter(StudyMaster study, TreatmentInfo treatmentInfo) {
		Criteria cri = getSession().createCriteria(DosePerameter.class).add(Restrictions.eq("study", study));
		if (study.isTreatmentWiseDoseParametres())
			cri.add(Restrictions.eq("treatment", treatmentInfo));
		List<DosePerameter> list = cri.list();
		return list;
	}

	@Override
	public void updateClinicalSampleTimePointsPhasesAndVital(SubjectDoseTimePoints tech,
			List<SubjectSampleCollectionTimePoints> afterDose, List<SubjectVitalTimePoints> afterDosevital,
			List<SubjectMealsTimePoints> afterDoseMeals, List<SubejectDosePerameter> subjectDosePerametres,
			SubjectPeriodStatus sps) {
		getSession().merge(tech);
		getSession().merge(sps);
		for (SubjectSampleCollectionTimePoints obj : afterDose)
			getSession().merge(obj);
		for (SubjectVitalTimePoints obj : afterDosevital)
			getSession().merge(obj);
		for (SubjectMealsTimePoints obj : afterDoseMeals)
			getSession().merge(obj);
		for (SubejectDosePerameter sdp : subjectDosePerametres)
			getSession().save(sdp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectSampleCollectionTimePoints> getSubjectSamplesTimepoints(SubjectDoseTimePoints tech) {
		// TODO Auto-generated method stub
//		List<SubjectSampleCollectionTimePoints> timepoints = getSession()
//				.createCriteria(SubjectSampleCollectionTimePoints.class).add(Restrictions.eq("study", tech.getStudy()))
//				.add(Restrictions.eq("period", tech.getPeriod()))
//				.add(Restrictions.eq("treatmentInfo", tech.getTreatmentInfo()))
//				.add(Restrictions.eq("subjectOrder", tech.getSubjectOrder())).list();
//		return timepoints;
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectMealsTimePoints> subjectMealsTimePoints(SubjectDoseTimePoints tech) {
//		List<SubjectMealsTimePoints> timepoints = getSession().createCriteria(SubjectMealsTimePoints.class)
//				.add(Restrictions.eq("study", tech.getStudy())).add(Restrictions.eq("period", tech.getPeriod()))
//				.add(Restrictions.eq("treatmentInfo", tech.getTreatmentInfo()))
//				.add(Restrictions.eq("subjectOrder", tech.getSubjectOrder())).list();
//		return timepoints;
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectVitalTimePoints> subjectVitalTimePoints(SubjectDoseTimePoints tech) {
//		List<SubjectVitalTimePoints> timepoints = getSession().createCriteria(SubjectVitalTimePoints.class)
//				.add(Restrictions.eq("study", tech.getStudy())).add(Restrictions.eq("period", tech.getPeriod()))
//				.add(Restrictions.eq("treatmentInfo", tech.getTreatmentInfo()))
//				.add(Restrictions.eq("subjectOrder", tech.getSubjectOrder())).list();
//		return timepoints;
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectECGTimePoints> subjectEcgTimePoints(SubjectDoseTimePoints tech) {
//		List<SubjectECGTimePoints> timepoints = getSession().createCriteria(SubjectECGTimePoints.class)
//				.add(Restrictions.eq("study", tech.getStudy())).add(Restrictions.eq("period", tech.getPeriod()))
//				.add(Restrictions.eq("treatmentInfo", tech.getTreatmentInfo()))
//				.add(Restrictions.eq("subjectOrder", tech.getSubjectOrder())).list();
//		return timepoints;
		return null;
	}

	@Override
	public FromStaticData fromStaticData(Long id) {
		// TODO Auto-generated method stub
		return (FromStaticData) getSession().get(FromStaticData.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<TreatmentInfo, List<MealsTimePoints>> preDsoeMealsTimePointsFromPeriod(StudyPeriodMaster sp) {
		FromStaticData preDoseMeals = (FromStaticData) getSession().createCriteria(FromStaticData.class)
				.add(Restrictions.eq("fieldName", StudyDesign.TIMEPOINTTYPE.toString()))
				.add(Restrictions.eq("code", StaticData.PREDOSE.toString())).uniqueResult();
		List<MealsTimePoints> list = getSession().createCriteria(MealsTimePoints.class)
				.add(Restrictions.eq("study", sp.getStudy())).add(Restrictions.eq("timePointType", preDoseMeals))
				.list();
		Map<Long, TreatmentInfo> treatments = new HashMap<>();
		Map<Long, List<MealsTimePoints>> treatmentTimePoints = new HashMap<>();
		boolean treatmentWise = true;
		for (MealsTimePoints mtp : list) {
			if (mtp.getTreatmentInfo() != null) {
				treatments.put(mtp.getTreatmentInfo().getId(), mtp.getTreatmentInfo());
				List<MealsTimePoints> mtps = treatmentTimePoints.get(mtp.getTreatmentInfo().getId());
				if (mtps == null)
					mtps = new ArrayList<>();
				mtps.add(mtp);
				treatmentTimePoints.put(mtp.getTreatmentInfo().getId(), mtps);
			} else {
				treatmentWise = false;
			}
		}
		Map<TreatmentInfo, List<MealsTimePoints>> treatmentTimePointsList = new HashMap<>();
		if (treatmentWise) {
			for (Map.Entry<Long, List<MealsTimePoints>> m : treatmentTimePoints.entrySet()) {
				List<MealsTimePoints> result = new ArrayList<>();
				List<MealsTimePoints> mtps = m.getValue();
				for (MealsTimePoints mtp : mtps) {
					List<SubjectMealsTimePoints> timePoints = getSession().createCriteria(SubjectMealsTimePoints.class)
							.add(Restrictions.eq("period", sp)).add(Restrictions.eq("treatmentInfo.id", m.getKey()))
							.add(Restrictions.eq("mealsTimePointsId", mtp))
							.add(Restrictions.isNull("subjectMealsTimePointsData")).list();
					if (timePoints.size() > 0)
						result.add(mtp);
				}
				treatmentTimePointsList.put(treatments.get(m.getKey()), mtps);
			}
		} else {
			treatmentTimePointsList.put(null, list);
		}
		return treatmentTimePointsList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<TreatmentInfo, List<MealsTimePoints>> postDsoeMealsTimePointsFromPeriod(StudyPeriodMaster sp) {
		FromStaticData postDoseMeals = (FromStaticData) getSession().createCriteria(FromStaticData.class)
				.add(Restrictions.eq("fieldName", StudyDesign.TIMEPOINTTYPE.toString()))
				.add(Restrictions.eq("code", StaticData.POSTDOSE.toString())).uniqueResult();
		List<MealsTimePoints> list = getSession().createCriteria(MealsTimePoints.class)
				.add(Restrictions.eq("study", sp.getStudy())).add(Restrictions.eq("timePointType", postDoseMeals))
				.list();

		Map<Long, List<MealsTimePoints>> treatmentTimePoints = new HashMap<>();
		Map<Long, MealsTimePoints> timePointIdMap = new HashMap<>();
		boolean treatmentWise = true;
		for (MealsTimePoints mtp : list) {
			timePointIdMap.put(mtp.getId(), mtp);
			if (mtp.getTreatmentInfo() != null) {
				List<MealsTimePoints> mtps = treatmentTimePoints.get(mtp.getTreatmentInfo().getId());
				if (mtps == null)
					mtps = new ArrayList<>();
				mtps.add(mtp);
				treatmentTimePoints.put(mtp.getTreatmentInfo().getId(), mtps);
			} else {
				treatmentWise = false;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat(environment.getRequiredProperty("clinicalDateTimeFormat"));
		Date date = new Date();
		Set<Long> ids = new HashSet<>();
		if (treatmentWise) {
			for (Map.Entry<Long, List<MealsTimePoints>> m : treatmentTimePoints.entrySet()) {
				List<MealsTimePoints> mtps = m.getValue();
				for (MealsTimePoints mtp : mtps) {
					if (!ids.contains(mtp.getId())) {
						List<SubjectMealsTimePoints> timePoints = getSession()
								.createCriteria(SubjectMealsTimePoints.class).add(Restrictions.eq("period", sp))
								.add(Restrictions.eq("treatmentInfo.id", m.getKey()))
								.add(Restrictions.eq("mealsTimePointsId", mtp))
								.add(Restrictions.eq("timePointCollectionStatus", false)).list();
						if (timePoints.size() > 0) {
							for (SubjectMealsTimePoints smtp : timePoints) {
								String time = smtp.getScheduleDate() + " " + smtp.getScheduleTime();
								try {
									Date timeDate = sdf.parse(time);
									Calendar c = Calendar.getInstance();
									c.setTime(timeDate);
									c.add(Calendar.HOUR, -1);
									Date start = c.getTime();
									c.setTime(timeDate);
									c.add(Calendar.HOUR, 1);
									Date end = c.getTime();
									int diff1 = start.compareTo(date);
									boolean flag1 = false;
									if (diff1 < 0) {
										flag1 = true;
									}
									boolean flag2 = false;
									int diff12 = end.compareTo(date);
									if (diff12 > 0) {
										flag2 = true;
									}
									if (flag1 && flag2) {
										ids.add(mtp.getId());
										break;
									}
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}

				}
			}
		} else {
			for (Map.Entry<Long, MealsTimePoints> map : timePointIdMap.entrySet()) {
				if (!ids.contains(map.getValue().getId())) {
					List<SubjectMealsTimePoints> timePoints = getSession().createCriteria(SubjectMealsTimePoints.class)
							.add(Restrictions.eq("period", sp))
							.add(Restrictions.eq("mealsTimePointsId", map.getValue()))
							.add(Restrictions.eq("timePointCollectionStatus", false)).list();
					if (timePoints.size() > 0) {
						for (SubjectMealsTimePoints smtp : timePoints) {
							String time = smtp.getScheduleDate() + " " + smtp.getScheduleTime();
							try {
								Date timeDate = sdf.parse(time);
								Calendar c = Calendar.getInstance();
								c.setTime(timeDate);
								c.add(Calendar.HOUR, -1);
								Date start = c.getTime();
								c.setTime(timeDate);
								c.add(Calendar.HOUR, 1);
								Date end = c.getTime();
								int diff1 = start.compareTo(date);
								boolean flag1 = false;
								if (diff1 < 0) {
									flag1 = true;
								}
								boolean flag2 = false;
								int diff12 = end.compareTo(date);
								if (diff12 > 0) {
									flag2 = true;
								}
								if (flag1 && flag2) {
									ids.add(map.getValue().getId());
									break;
								}
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
//			treatmentTimePointsList.put(null, list);
		}

		Map<TreatmentInfo, List<MealsTimePoints>> treatmentTimePointsList = new HashMap<>();
		List<MealsTimePoints> tempTimePoints = new ArrayList<>();
		for (Long timePointId : ids) {
			MealsTimePoints mtp = timePointIdMap.get(timePointId);
			if (mtp.getTreatmentInfo() != null) {
				List<MealsTimePoints> mtps = treatmentTimePoints.get(mtp.getTreatmentInfo().getId());
				if (mtps == null)
					mtps = new ArrayList<>();
				mtps.add(mtp);
				treatmentTimePoints.put(mtp.getTreatmentInfo().getId(), mtps);
			} else {
				tempTimePoints.add(mtp);
			}
		}

		if (!treatmentWise) {
			treatmentTimePointsList.put(null, list);
		}
		return treatmentTimePointsList;

	}

	@Override
	public SubjectMealsTimePoints subjectMealsTimePoints(List<Long> timePointIds, int subjectSeqNo,
			StudyPeriodMaster sp) {
		return (SubjectMealsTimePoints) getSession().createCriteria(SubjectMealsTimePoints.class)
				.add(Restrictions.in("mealsTimePointsId.id", timePointIds)).add(Restrictions.eq("period", sp))
				.add(Restrictions.eq("subjectOrder", subjectSeqNo)).uniqueResult();
	}

	@Override
	public List<SubjectMealsTimePoints> subjectMealsTimePointsWithIds(List<Long> timePointIdsList) {
		List<SubjectMealsTimePoints> list = new ArrayList<>();
		if (timePointIdsList.size() > 0) {
			list = getSession().createCriteria(SubjectMealsTimePoints.class)
					.add(Restrictions.in("id", timePointIdsList)).list();
		}
		return list;
	}

	@Override
	public void saveSubjectMealsTimePointsData(List<SubjectMealsTimePointsData> subjectTimePoints) {
		for (SubjectMealsTimePointsData subjectMealsTimePoints : subjectTimePoints) {
			getSession().save(subjectMealsTimePoints);
		}
	}

	@Override
	public SubjectMealsTimePoints subjectMealsTimePointsWithId(Long timepointsId) {
		return (SubjectMealsTimePoints) getSession().get(SubjectMealsTimePoints.class, timepointsId);
	}

	@Override
	public void updateSubjectMealsTimePoints(SubjectMealsTimePoints smtp) {
		getSession().merge(smtp.getSubjectMealsTimePointsData());
		getSession().merge(smtp);
	}

	@Override
	public SubjectRandamization subjectRandamization(StudyMaster study, StudyPeriodMaster sp, int subjectOrder) {
		return (SubjectRandamization) getSession().createCriteria(SubjectRandamization.class)
				.add(Restrictions.eq("period", sp)).add(Restrictions.eq("seqNo", subjectOrder)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public SubjectMealsTimePoints subjectMealsDinnerTimePoints(TreatmentInfo treatmentInfo, int subjectOrder,
			StudyPeriodMaster sp, SubjectDoseTimePoints tech) {
		FromStaticData preDoseMeals = (FromStaticData) getSession().createCriteria(FromStaticData.class)
				.add(Restrictions.eq("fieldName", StudyDesign.MEALSTTYPE.toString()))
				.add(Restrictions.eq("code", StaticData.DINNER.toString())).uniqueResult();
		if (tech.getDoseTimePoints().getTimePointNo() == 1) {
			Criteria cri = getSession().createCriteria(SubjectMealsTimePoints.class).add(Restrictions.eq("period", sp))
					.add(Restrictions.eq("subjectOrder", subjectOrder)).add(Restrictions.eq("mealsType", preDoseMeals))
//					.add(Restrictions.isNotNull("subjectMealsTimePointsData"))
					.addOrder(Order.desc("timePointNo"));
			if (treatmentInfo != null)
				cri.add(Restrictions.eq("treatmentInfo", treatmentInfo));
			else
				cri.add(Restrictions.isNull("treatmentInfo"));
			List<SubjectMealsTimePoints> list = cri.list();
			for (SubjectMealsTimePoints sb : list) {
				System.out.println(
						sb.getId() + "\t" + sb.getSubjectMealsTimePointsData() + "\t" + sb.getMealsType().getCode());
			}
			if (list.size() > 0)
				return list.get(0);
//			for (SubjectMealsTimePoints sb : list) {
//				System.out.println(
//						sb.getId() + "\t" + sb.getSubjectMealsTimePointsData() + "\t" + sb.getMealsType().getCode());
//				if (sb.getMealsType().getCode().equals(StaticData.DINNER.toString())) {
//					return sb;
//				}
//			}
			return null;
		} else {
			return subjectMostResentMealsTimePoint(treatmentInfo, subjectOrder, sp);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public SubjectMealsTimePoints subjectMealsBreakFastTimePointsPreDose(TreatmentInfo treatmentInfo, int subjectOrder,
			StudyPeriodMaster sp) {
		FromStaticData preDoseMeals = (FromStaticData) getSession().createCriteria(FromStaticData.class)
				.add(Restrictions.eq("fieldName", StudyDesign.TIMEPOINTTYPE.toString()))
				.add(Restrictions.eq("code", StaticData.PREDOSE.toString())).uniqueResult();
		Criteria cri = getSession().createCriteria(SubjectMealsTimePoints.class).add(Restrictions.eq("period", sp))
				.add(Restrictions.eq("timePointType", preDoseMeals)).add(Restrictions.eq("subjectOrder", subjectOrder))
				.addOrder(Order.desc("timePointNo"));
		if (treatmentInfo != null)
			cri.add(Restrictions.eq("treatmentInfo", treatmentInfo));
		else
			cri.add(Restrictions.isNull("treatmentInfo"));
		List<SubjectMealsTimePoints> list = cri.list();
		SubjectMealsTimePoints sb = list.get(0);
		if (sb.getMealsType().getCode().equals(StaticData.HIGHFATBREAKFAST.toString())
				|| sb.getMealsType().getCode().equals(StaticData.NORMALBREAKFAST.toString())
				|| sb.getMealsType().getCode().equals(StaticData.STANDARDBREAKFAST.toString())) {
			return sb;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SubjectMealsTimePoints subjectMealsBreakFastTimePoints(TreatmentInfo treatmentInfo, int subjectOrder,
			StudyPeriodMaster sp) {
		Criteria cri = getSession().createCriteria(SubjectMealsTimePoints.class).add(Restrictions.eq("period", sp))
				.add(Restrictions.eq("subjectOrder", subjectOrder)).addOrder(Order.desc("timePointNo"));
		if (treatmentInfo != null)
			cri.add(Restrictions.eq("treatmentInfo", treatmentInfo));
		else
			cri.add(Restrictions.isNull("treatmentInfo"));
		List<SubjectMealsTimePoints> list = cri.list();
		SubjectMealsTimePoints sb = list.get(0);
		if (sb.getMealsType().getCode().equals(StaticData.HIGHFATBREAKFAST.toString())
				|| sb.getMealsType().getCode().equals(StaticData.NORMALBREAKFAST.toString())
				|| sb.getMealsType().getCode().equals(StaticData.STANDARDBREAKFAST.toString())) {
			return sb;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectDoseTimePoints> oldSubjectDoseTimePointsAfterDose(SubjectDoseTimePoints tech) {
//		List<SubjectDoseTimePoints> list = getSession().createCriteria(SubjectDoseTimePoints.class)
//				.add(Restrictions.eq("study", tech.getDoseTimePoints().getStudy())).add(Restrictions.eq("period", tech.getPeriod()))
//				.add(Restrictions.eq("treatmentInfo", tech.getTreatmentInfo()))
//				.add(Restrictions.eq("subjectNo", tech.getSubjectNo())).add(Restrictions.ne("timePointNo", 1))
//				.add(Restrictions.lt("timePointNo", tech.getTimePointNo())).addOrder(Order.desc("timePointNo")).list();
//		return list;
		return null;
	}

	@Override
	public SubjectMealsTimePoints subjectMostResentMealsTimePoint(TreatmentInfo treatmentInfo, int subjectOrder,
			StudyPeriodMaster sp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
		SimpleDateFormat timeFormat = new SimpleDateFormat(environment.getRequiredProperty("timeFormat"));
		Date d = new Date();
		String date = dateFormat.format(d);
		String time = timeFormat.format(d);
		Criteria cri = getSession().createCriteria(SubjectMealsTimePoints.class).add(Restrictions.eq("period", sp))
				.add(Restrictions.eq("subjectOrder", subjectOrder)).add(Restrictions.eq("scheduleDate", date))

				.add(Restrictions.le("scheduleTime", time)).addOrder(Order.desc("timePointNo"));
		if (treatmentInfo != null)
			cri.add(Restrictions.eq("treatmentInfo", treatmentInfo));
		else
			cri.add(Restrictions.isNull("treatmentInfo"));
		List<SubjectMealsTimePoints> list = cri.list();
		for (SubjectMealsTimePoints sb : list) {
			System.out.println(
					sb.getId() + "\t" + sb.getSubjectMealsTimePointsData() + "\t" + sb.getMealsType().getCode());
		}
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updateClinicalSampleTimePoints(SubjectSampleCollectionTimePoints ssctp,
			List<SubjectSampleSeparationTimePointsData> sepdata) {
		// TODO Auto-generated method stub
		getSession().merge(ssctp.getSubjectSampleCollectionTimePointsData());
		getSession().merge(ssctp);
		for (SubjectSampleSeparationTimePointsData obj : sepdata)
			getSession().save(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<TreatmentInfo, List<VitalTimePoints>> preDsoeVitalTimePointsFromPeriod(StudyPeriodMaster sp) {
		FromStaticData preDoseMeals = (FromStaticData) getSession().createCriteria(FromStaticData.class)
				.add(Restrictions.eq("fieldName", StudyDesign.TIMEPOINTTYPE.toString()))
				.add(Restrictions.eq("code", StaticData.PREDOSE.toString())).uniqueResult();
		List<VitalTimePoints> list = getSession().createCriteria(VitalTimePoints.class)
				.add(Restrictions.eq("study", sp.getStudy())).add(Restrictions.eq("timePointType", preDoseMeals))
				.list();
		Map<Long, TreatmentInfo> treatments = new HashMap<>();
		Map<Long, List<VitalTimePoints>> treatmentTimePoints = new HashMap<>();
		boolean treatmentWise = true;
		for (VitalTimePoints mtp : list) {
			if (mtp.getTreatmentInfo() != null) {
				treatments.put(mtp.getTreatmentInfo().getId(), mtp.getTreatmentInfo());
				List<VitalTimePoints> mtps = treatmentTimePoints.get(mtp.getTreatmentInfo().getId());
				if (mtps == null)
					mtps = new ArrayList<>();
				mtps.add(mtp);
				treatmentTimePoints.put(mtp.getTreatmentInfo().getId(), mtps);
			} else {
				treatmentWise = false;
			}
		}
		Map<TreatmentInfo, List<VitalTimePoints>> treatmentTimePointsList = new HashMap<>();
		if (treatmentWise) {
			for (Map.Entry<Long, List<VitalTimePoints>> m : treatmentTimePoints.entrySet()) {
				List<VitalTimePoints> result = new ArrayList<>();
				List<VitalTimePoints> mtps = m.getValue();
				for (VitalTimePoints mtp : mtps) {
					List<SubjectVitalTimePoints> timePoints = getSession().createCriteria(SubjectVitalTimePoints.class)
							.add(Restrictions.eq("period", sp)).add(Restrictions.eq("treatmentInfo.id", m.getKey()))
							.add(Restrictions.eq("vitalTimePointsId", mtp))
							.add(Restrictions.isNull("subjectVitalTimePointsData")).list();
					if (timePoints.size() > 0)
						result.add(mtp);
				}
				treatmentTimePointsList.put(treatments.get(m.getKey()), mtps);
			}
		} else {
			treatmentTimePointsList.put(null, list);
		}
		return treatmentTimePointsList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<TreatmentInfo, List<VitalTimePoints>> postDsoeVitalTimePointsFromPeriod(StudyPeriodMaster sp) {
		FromStaticData postDoseMeals = (FromStaticData) getSession().createCriteria(FromStaticData.class)
				.add(Restrictions.eq("fieldName", StudyDesign.TIMEPOINTTYPE.toString()))
				.add(Restrictions.eq("code", StaticData.POSTDOSE.toString())).uniqueResult();
		List<VitalTimePoints> list = getSession().createCriteria(VitalTimePoints.class)
				.add(Restrictions.eq("study", sp.getStudy())).add(Restrictions.eq("timePointType", postDoseMeals))
				.list();

		Map<Long, List<VitalTimePoints>> treatmentTimePoints = new HashMap<>();
		Map<Long, VitalTimePoints> timePointIdMap = new HashMap<>();
		boolean treatmentWise = true;
		for (VitalTimePoints mtp : list) {
			timePointIdMap.put(mtp.getId(), mtp);
			if (mtp.getTreatmentInfo() != null) {
				List<VitalTimePoints> mtps = treatmentTimePoints.get(mtp.getTreatmentInfo().getId());
				if (mtps == null)
					mtps = new ArrayList<>();
				mtps.add(mtp);
				treatmentTimePoints.put(mtp.getTreatmentInfo().getId(), mtps);
			} else {
				treatmentWise = false;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat(environment.getRequiredProperty("clinicalDateTimeFormat"));
		Date date = new Date();
		Set<Long> ids = new HashSet<>();
		if (treatmentWise) {
			for (Map.Entry<Long, List<VitalTimePoints>> m : treatmentTimePoints.entrySet()) {
				List<VitalTimePoints> mtps = m.getValue();
				for (VitalTimePoints mtp : mtps) {
					if (!ids.contains(mtp.getId())) {
						List<SubjectVitalTimePoints> timePoints = getSession()
								.createCriteria(SubjectVitalTimePoints.class).add(Restrictions.eq("period", sp))
								.add(Restrictions.eq("treatmentInfo.id", m.getKey()))
								.add(Restrictions.eq("vitalTimePointsId", mtp))
								.add(Restrictions.isNull("subjectVitalTimePointsData")).list();
						if (timePoints.size() > 0) {
							for (SubjectVitalTimePoints smtp : timePoints) {
								String time = smtp.getScheduleDate() + " " + smtp.getScheduleTime();
								try {
									Date timeDate = sdf.parse(time);
									Calendar c = Calendar.getInstance();
									c.setTime(timeDate);
									c.add(Calendar.HOUR, -1);
									Date start = c.getTime();
									c.setTime(timeDate);
									c.add(Calendar.HOUR, 1);
									Date end = c.getTime();
									int diff1 = start.compareTo(date);
									boolean flag1 = false;
									if (diff1 < 0) {
										flag1 = true;
									}
									boolean flag2 = false;
									int diff12 = end.compareTo(date);
									if (diff12 > 0) {
										flag2 = true;
									}
									if (flag1 && flag2) {
										ids.add(mtp.getId());
										break;
									}
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}

				}
			}
		} else {
			for (Map.Entry<Long, VitalTimePoints> map : timePointIdMap.entrySet()) {
				if (!ids.contains(map.getValue().getId())) {
					List<SubjectVitalTimePoints> timePoints = getSession().createCriteria(SubjectVitalTimePoints.class)
							.add(Restrictions.eq("period", sp))
							.add(Restrictions.eq("vitalTimePointsId", map.getValue()))
							.add(Restrictions.isNull("subjectVitalTimePointsData")).list();
					if (timePoints.size() > 0) {
						for (SubjectVitalTimePoints smtp : timePoints) {
							String time = smtp.getScheduleDate() + " " + smtp.getScheduleTime();
							try {
								Date timeDate = sdf.parse(time);
								Calendar c = Calendar.getInstance();
								c.setTime(timeDate);
								c.add(Calendar.HOUR, -1);
								Date start = c.getTime();
								c.setTime(timeDate);
								c.add(Calendar.HOUR, 1);
								Date end = c.getTime();
								int diff1 = start.compareTo(date);
								boolean flag1 = false;
								if (diff1 < 0) {
									flag1 = true;
								}
								boolean flag2 = false;
								int diff12 = end.compareTo(date);
								if (diff12 > 0) {
									flag2 = true;
								}
								if (flag1 && flag2) {
									ids.add(map.getValue().getId());
									break;
								}
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
//			treatmentTimePointsList.put(null, list);
		}

		Map<TreatmentInfo, List<VitalTimePoints>> treatmentTimePointsList = new HashMap<>();
		List<VitalTimePoints> tempTimePoints = new ArrayList<>();
		for (Long timePointId : ids) {
			VitalTimePoints mtp = timePointIdMap.get(timePointId);
			if (mtp.getTreatmentInfo() != null) {
				List<VitalTimePoints> mtps = treatmentTimePoints.get(mtp.getTreatmentInfo().getId());
				if (mtps == null)
					mtps = new ArrayList<>();
				mtps.add(mtp);
				treatmentTimePoints.put(mtp.getTreatmentInfo().getId(), mtps);
			} else {
				tempTimePoints.add(mtp);
			}
		}

		if (!treatmentWise) {
			treatmentTimePointsList.put(null, list);
		}
		return treatmentTimePointsList;

	}

	@Override
	public SubjectVitalTimePoints subjectVitalTimePoints(List<Long> timePointIds, int subjectSeqNo,
			StudyPeriodMaster sp) {
		return (SubjectVitalTimePoints) getSession().createCriteria(SubjectVitalTimePoints.class)
				.add(Restrictions.in("vitalTimePointsId.id", timePointIds)).add(Restrictions.eq("period", sp))
				.add(Restrictions.eq("subjectOrder", subjectSeqNo)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TimePointVitalTests> timePointVitalTests(VitalTimePoints vitalTimePointsId) {
		return getSession().createCriteria(TimePointVitalTests.class)
				.add(Restrictions.eq("vitalTimePointId", vitalTimePointsId)).list();
	}

	@Override
	public SubjectVitalTimePoints subjectVitalTimePoints(Long timePointId) {
		// TODO Auto-generated method stub
		SubjectVitalTimePoints obj = (SubjectVitalTimePoints) getSession().get(SubjectVitalTimePoints.class,
				timePointId);
		Hibernate.initialize(obj.getTest());
		return obj;
	}

	@Override
	public void saveSubjectVitalDetials(SubjectVitalTimePoints smtp, SubjectVitalTimePointsData data,
			List<SubjectTimePointVitalTests> test) {
		getSession().save(data);
		getSession().merge(smtp);

		for (SubjectTimePointVitalTests tet : test)
			getSession().merge(tet);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<TreatmentInfo, List<ECGTimePoints>> preEcgTimePointsFromPeriod(StudyPeriodMaster sp) {
		FromStaticData preDoseMeals = (FromStaticData) getSession().createCriteria(FromStaticData.class)
				.add(Restrictions.eq("fieldName", StudyDesign.TIMEPOINTTYPE.toString()))
				.add(Restrictions.eq("code", StaticData.PREDOSE.toString())).uniqueResult();
		List<ECGTimePoints> list = getSession().createCriteria(ECGTimePoints.class)
				.add(Restrictions.eq("study", sp.getStudy())).add(Restrictions.eq("timePointType", preDoseMeals))
				.list();
		Map<Long, TreatmentInfo> treatments = new HashMap<>();
		Map<Long, List<ECGTimePoints>> treatmentTimePoints = new HashMap<>();
		boolean treatmentWise = true;
		for (ECGTimePoints mtp : list) {
			if (mtp.getTreatmentInfo() != null) {
				treatments.put(mtp.getTreatmentInfo().getId(), mtp.getTreatmentInfo());
				List<ECGTimePoints> mtps = treatmentTimePoints.get(mtp.getTreatmentInfo().getId());
				if (mtps == null)
					mtps = new ArrayList<>();
				mtps.add(mtp);
				treatmentTimePoints.put(mtp.getTreatmentInfo().getId(), mtps);
			} else {
				treatmentWise = false;
			}
		}
		Map<TreatmentInfo, List<ECGTimePoints>> treatmentTimePointsList = new HashMap<>();
		if (treatmentWise) {
			for (Map.Entry<Long, List<ECGTimePoints>> m : treatmentTimePoints.entrySet()) {
				List<ECGTimePoints> result = new ArrayList<>();
				List<ECGTimePoints> mtps = m.getValue();
				for (ECGTimePoints mtp : mtps) {
					List<SubjectECGTimePoints> timePoints = getSession().createCriteria(SubjectECGTimePoints.class)
							.add(Restrictions.eq("period", sp)).add(Restrictions.eq("treatmentInfo.id", m.getKey()))
							.add(Restrictions.eq("ecgTimePointsId", mtp))
							.add(Restrictions.isNull("subjectECGTimePointsData")).list();
					if (timePoints.size() > 0)
						result.add(mtp);
				}
				treatmentTimePointsList.put(treatments.get(m.getKey()), mtps);
			}
		} else {
			treatmentTimePointsList.put(null, list);
		}
		return treatmentTimePointsList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<TreatmentInfo, List<ECGTimePoints>> postDsoeEcgTimePointsFromPeriod(StudyPeriodMaster sp) {
		FromStaticData postDoseMeals = (FromStaticData) getSession().createCriteria(FromStaticData.class)
				.add(Restrictions.eq("fieldName", StudyDesign.TIMEPOINTTYPE.toString()))
				.add(Restrictions.eq("code", StaticData.POSTDOSE.toString())).uniqueResult();
		List<ECGTimePoints> list = getSession().createCriteria(ECGTimePoints.class)
				.add(Restrictions.eq("study", sp.getStudy())).add(Restrictions.eq("timePointType", postDoseMeals))
				.list();

		Map<Long, List<ECGTimePoints>> treatmentTimePoints = new HashMap<>();
		Map<Long, ECGTimePoints> timePointIdMap = new HashMap<>();
		boolean treatmentWise = true;
		for (ECGTimePoints mtp : list) {
			timePointIdMap.put(mtp.getId(), mtp);
			if (mtp.getTreatmentInfo() != null) {
				List<ECGTimePoints> mtps = treatmentTimePoints.get(mtp.getTreatmentInfo().getId());
				if (mtps == null)
					mtps = new ArrayList<>();
				mtps.add(mtp);
				treatmentTimePoints.put(mtp.getTreatmentInfo().getId(), mtps);
			} else {
				treatmentWise = false;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat(environment.getRequiredProperty("clinicalDateTimeFormat"));
		Date date = new Date();
		Set<Long> ids = new HashSet<>();
		if (treatmentWise) {
			for (Map.Entry<Long, List<ECGTimePoints>> m : treatmentTimePoints.entrySet()) {
				List<ECGTimePoints> mtps = m.getValue();
				for (ECGTimePoints mtp : mtps) {
					if (!ids.contains(mtp.getId())) {
						List<SubjectECGTimePoints> timePoints = getSession().createCriteria(SubjectECGTimePoints.class)
								.add(Restrictions.eq("period", sp)).add(Restrictions.eq("treatmentInfo.id", m.getKey()))
								.add(Restrictions.eq("vitalTimePointsId", mtp))
								.add(Restrictions.isNull("subjectECGTimePointsData")).list();
						if (timePoints.size() > 0) {
							for (SubjectECGTimePoints smtp : timePoints) {
								String time = smtp.getScheduleDate() + " " + smtp.getScheduleTime();
								try {
									Date timeDate = sdf.parse(time);
									Calendar c = Calendar.getInstance();
									c.setTime(timeDate);
									c.add(Calendar.HOUR, -1);
									Date start = c.getTime();
									c.setTime(timeDate);
									c.add(Calendar.HOUR, 1);
									Date end = c.getTime();
									int diff1 = start.compareTo(date);
									boolean flag1 = false;
									if (diff1 < 0) {
										flag1 = true;
									}
									boolean flag2 = false;
									int diff12 = end.compareTo(date);
									if (diff12 > 0) {
										flag2 = true;
									}
									if (flag1 && flag2) {
										ids.add(mtp.getId());
										break;
									}
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}

				}
			}
		} else {
			for (Map.Entry<Long, ECGTimePoints> map : timePointIdMap.entrySet()) {
				if (!ids.contains(map.getValue().getId())) {
					List<SubjectECGTimePoints> timePoints = getSession().createCriteria(SubjectECGTimePoints.class)
							.add(Restrictions.eq("period", sp))
							.add(Restrictions.eq("vitalTimePointsId", map.getValue()))
							.add(Restrictions.isNull("subjectECGTimePointsData")).list();
					if (timePoints.size() > 0) {
						for (SubjectECGTimePoints smtp : timePoints) {
							String time = smtp.getScheduleDate() + " " + smtp.getScheduleTime();
							try {
								Date timeDate = sdf.parse(time);
								Calendar c = Calendar.getInstance();
								c.setTime(timeDate);
								c.add(Calendar.HOUR, -1);
								Date start = c.getTime();
								c.setTime(timeDate);
								c.add(Calendar.HOUR, 1);
								Date end = c.getTime();
								int diff1 = start.compareTo(date);
								boolean flag1 = false;
								if (diff1 < 0) {
									flag1 = true;
								}
								boolean flag2 = false;
								int diff12 = end.compareTo(date);
								if (diff12 > 0) {
									flag2 = true;
								}
								if (flag1 && flag2) {
									ids.add(map.getValue().getId());
									break;
								}
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
//			treatmentTimePointsList.put(null, list);
		}

		Map<TreatmentInfo, List<ECGTimePoints>> treatmentTimePointsList = new HashMap<>();
		List<ECGTimePoints> tempTimePoints = new ArrayList<>();
		for (Long timePointId : ids) {
			ECGTimePoints mtp = timePointIdMap.get(timePointId);
			if (mtp.getTreatmentInfo() != null) {
				List<ECGTimePoints> mtps = treatmentTimePoints.get(mtp.getTreatmentInfo().getId());
				if (mtps == null)
					mtps = new ArrayList<>();
				mtps.add(mtp);
				treatmentTimePoints.put(mtp.getTreatmentInfo().getId(), mtps);
			} else {
				tempTimePoints.add(mtp);
			}
		}

		if (!treatmentWise) {
			treatmentTimePointsList.put(null, list);
		}
		return treatmentTimePointsList;

	}

	@Override
	public SubjectECGTimePoints subjectEcgTimePoints(List<Long> timePointIds, int subjectSeqNo, StudyPeriodMaster sp) {
		return (SubjectECGTimePoints) getSession().createCriteria(SubjectECGTimePoints.class)
				.add(Restrictions.in("ecgTimePointsId.id", timePointIds)).add(Restrictions.eq("period", sp))
				.add(Restrictions.eq("subjectOrder", subjectSeqNo)).uniqueResult();
	}

	@Override
	public SubjectECGTimePoints subjectECGTimePoints(Long timePointId) {
		// TODO Auto-generated method stub
		return (SubjectECGTimePoints) getSession().get(SubjectECGTimePoints.class, timePointId);
	}

	@Override
	public void saveSubjectEcgDetials(SubjectECGTimePoints smtp, SubjectECGTimePointsData data) {
		getSession().save(data);
		getSession().merge(smtp);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubejectDosePerameter> subejectDosePerameter(SubjectDoseTimePoints sdtp) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(SubejectDosePerameter.class).add(Restrictions.eq("doseTimePoint", sdtp))
				.list();
//		List<SubejectDosePerameter> list =  getSession().createCriteria(SubejectDosePerameter.class)
//				.add(Restrictions.eq("study.id", sdtp.getStudy().getId()))
//				.add(Restrictions.eq("period.id", sdtp.getPeriod().getId()))
//				.add(Restrictions.eq("subjectOrder", sdtp.getSubjectOrder())).list();
//		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectVitalTimePoints> subjectVitalTimePoints(Long periodId, int subjectOrder) {
		List<SubjectVitalTimePoints> list = getSession().createCriteria(SubjectVitalTimePoints.class)
				.add(Restrictions.eq("period.id", periodId)).add(Restrictions.eq("subjectOrder", subjectOrder))
				.addOrder(Order.asc("timePointNo")).list();
//		SimpleDateFormat timeFormatSeconds = new SimpleDateFormat(environment.getRequiredProperty("timeFormatSeconds"));
//		SimpleDateFormat timeFormat = new SimpleDateFormat(environment.getRequiredProperty("timeFormat"));
//		for (SubjectVitalTimePoints svt : list) {
//			List<SubjectTimePointVitalTests> test = getSession().createCriteria(SubjectTimePointVitalTests.class)
//					.add(Restrictions.eq("subjectVitalTimePoints", svt)).addOrder(Order.asc("testId")).list();
//			svt.setTest(test);
//			if (svt.getSubjectVitalTimePointsData() != null) {
//				try {
//					svt.setStartTime(timeFormat
//							.format(timeFormatSeconds.parse(svt.getSubjectVitalTimePointsData().getSubjectScanTime())));
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				try {
//					svt.setEndTime(timeFormat
//							.format(timeFormatSeconds.parse(svt.getSubjectVitalTimePointsData().getSubmissionTime())));
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectSampleSeparationTimePointsData> subjectSampleSeparationTimePointsData(Long periodId,
			int subjectOrder) {
		// TODO Auto-generated method stub
		List<String> list = getSession().createCriteria(SubjectSampleCollectionTimePoints.class)
				.add(Restrictions.eq("period.id", periodId)).add(Restrictions.eq("subjectOrder", subjectOrder))
				.addOrder(Order.asc("timePointNo"))
				.setProjection(Projections.property("subjectSampleCollectionTimePointId")).list();
		if (list.size() > 0) {
			List<SubjectSampleSeparationTimePointsData> result = getSession()
					.createCriteria(SubjectSampleSeparationTimePointsData.class)
					.add(Restrictions.in("subjectSampleCollectionTimPoints.id", list)).list();
			return result;
		} else
			return new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, List<SubjectMealsTimePoints>> subjectMealsTimePoints(Long periodId, int subjectOrder,
			boolean daywise) {
		/*
		 * List<SubjectMealsTimePoints> list =
		 * getSession().createCriteria(SubjectMealsTimePoints.class)
		 * .add(Restrictions.eq("period.id",
		 * periodId)).add(Restrictions.eq("subjectOrder", subjectOrder))
		 * .addOrder(Order.asc("timePointNo")).list(); Map<Integer,
		 * List<SubjectMealsTimePoints>> map = new HashMap<>(); if (daywise) {
		 * SubjectDoseTimePoints sujectDosingTimePoint = (SubjectDoseTimePoints)
		 * getSession()
		 * .createCriteria(SubjectDoseTimePoints.class).add(Restrictions.eq("period.id",
		 * periodId)) .add(Restrictions.eq("subjectOrder",
		 * subjectOrder)).add(Restrictions.eq("timePointNo", "00.00")) .uniqueResult();
		 * SimpleDateFormat dateTimeFormat = new
		 * SimpleDateFormat(environment.getRequiredProperty("dateTimeFormat"));
		 * list.stream().forEach((subjectMealTimePoint) -> { if
		 * (sujectDosingTimePoint.getActualDate() != null) { String doseDate =
		 * sujectDosingTimePoint.getActualDate() + " " +
		 * sujectDosingTimePoint.getActualtime(); String schedule =
		 * subjectMealTimePoint.getScheduleDate() + " " +
		 * subjectMealTimePoint.getScheduleTime(); try { Date dose =
		 * dateTimeFormat.parse(doseDate); Date timepoint =
		 * dateTimeFormat.parse(schedule); Integer days =
		 * caliculateDasyBetweenDates(dose, timepoint); List<SubjectMealsTimePoints> m =
		 * map.get(days); if (m == null) m = new ArrayList<>();
		 * m.add(subjectMealTimePoint); map.put(days, m); } catch (Exception e) { //
		 * TODO: handle exception e.printStackTrace(); } } else { String timepoint =
		 * subjectMealTimePoint.getTimePoint(); // String s = } }); } else { //
		 * list.add(new FromStaticData(StudyDesign.MEALSTTYPE.toString(),
		 * "Normalbreakfast", // StaticData.NORMALBREAKFAST.toString())); //
		 * list.add(new FromStaticData(StudyDesign.MEALSTTYPE.toString(),
		 * "Standardbreakfast", // StaticData.STANDARDBREAKFAST.toString())); //
		 * list.add(new FromStaticData(StudyDesign.MEALSTTYPE.toString(),
		 * "Highfatbreakfast", // StaticData.HIGHFATBREAKFAST.toString())); //
		 * list.add(new FromStaticData(StudyDesign.MEALSTTYPE.toString(), "Lunch",
		 * StaticData.LUNCH.toString())); // list.add(new
		 * FromStaticData(StudyDesign.MEALSTTYPE.toString(), "Snacks",
		 * StaticData.SNACKS.toString())); // list.add(new
		 * FromStaticData(StudyDesign.MEALSTTYPE.toString(), "Dinner",
		 * StaticData.DINNER.toString()));
		 * 
		 * int dinnerCount = 0; int breakfastCount = 0; int lunchCount = 0; int
		 * snacksCount = 0; for (SubjectMealsTimePoints meal : list) { if
		 * (meal.getMealsType().getCode().equals(StaticData.DINNER.toString())) {
		 * dinnerCount++; if
		 * (meal.getTimePointType().getCode().equals(StaticData.PREDOSE.toString())) {
		 * meal.setMeal("Dinner-" + dinnerCount + "\n(" + meal.getSign() +
		 * meal.getTimePoint() + " hr or before)"); } else { meal.setMeal("Dinner-" +
		 * dinnerCount + "\n(" + meal.getTimePoint() + " hr)"); } } else if
		 * (meal.getMealsType().getCode().equals(StaticData.LUNCH.toString())) {
		 * dinnerCount++; if
		 * (meal.getTimePointType().getCode().equals(StaticData.PREDOSE.toString())) {
		 * meal.setMeal("Lunch-" + dinnerCount + "\n(" + meal.getSign() +
		 * meal.getTimePoint() + " hr or before)"); } else { meal.setMeal("Lunch-" +
		 * dinnerCount + "\n(" + meal.getTimePoint() + " hr)"); } } else if
		 * (meal.getMealsType().getCode().equals(StaticData.SNACKS.toString())) {
		 * dinnerCount++; if
		 * (meal.getTimePointType().getCode().equals(StaticData.PREDOSE.toString())) {
		 * meal.setMeal("Snacks-" + dinnerCount + "\n(" + meal.getSign() +
		 * meal.getTimePoint() + " hr or before)"); } else { meal.setMeal("Snacks-" +
		 * dinnerCount + "\n(" + meal.getTimePoint() + " hr)"); } } else if
		 * (meal.getMealsType().getCode().equals(StaticData.NORMALBREAKFAST.toString())
		 * ||
		 * meal.getMealsType().getCode().equals(StaticData.HIGHFATBREAKFAST.toString())
		 * ||
		 * meal.getMealsType().getCode().equals(StaticData.STANDARDBREAKFAST.toString())
		 * ) { dinnerCount++; if
		 * (meal.getTimePointType().getCode().equals(StaticData.PREDOSE.toString())) {
		 * meal.setMeal("Breakfast-" + dinnerCount + "\n(" + meal.getSign() +
		 * meal.getTimePoint() + " hr or before)"); } else { meal.setMeal("Breakfast-" +
		 * dinnerCount + "\n(" + meal.getTimePoint() + " hr)"); } }
		 * 
		 * } map.put(0, list); } return map;
		 */
		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, List<SubjectECGTimePoints>> subjectEcgTimePoints(Long periodId, int subjectOrder,
			boolean daywise) {
		/*
		 * List<SubjectECGTimePoints> list =
		 * getSession().createCriteria(SubjectECGTimePoints.class)
		 * .add(Restrictions.eq("period.id",
		 * periodId)).add(Restrictions.eq("subjectOrder", subjectOrder))
		 * .addOrder(Order.asc("timePointNo")).list(); Map<Integer,
		 * List<SubjectECGTimePoints>> map = new HashMap<>(); if (daywise) {
		 * SubjectDoseTimePoints sujectDosingTimePoint = (SubjectDoseTimePoints)
		 * getSession()
		 * .createCriteria(SubjectDoseTimePoints.class).add(Restrictions.eq("period.id",
		 * periodId)) .add(Restrictions.eq("subjectOrder",
		 * subjectOrder)).add(Restrictions.eq("timePointNo", "00.00")) .uniqueResult();
		 * SimpleDateFormat dateTimeFormat = new
		 * SimpleDateFormat(environment.getRequiredProperty("dateTimeFormat"));
		 * list.stream().forEach((subjectMealTimePoint) -> { if
		 * (sujectDosingTimePoint.getActualDate() != null) { String doseDate =
		 * sujectDosingTimePoint.getActualDate() + " " +
		 * sujectDosingTimePoint.getActualtime(); String schedule =
		 * subjectMealTimePoint.getScheduleDate() + " " +
		 * subjectMealTimePoint.getScheduleTime(); try { Date dose =
		 * dateTimeFormat.parse(doseDate); Date timepoint =
		 * dateTimeFormat.parse(schedule); Integer days =
		 * caliculateDasyBetweenDates(dose, timepoint); List<SubjectECGTimePoints> m =
		 * map.get(days); if (m == null) m = new ArrayList<>();
		 * m.add(subjectMealTimePoint); map.put(days, m); } catch (Exception e) { //
		 * TODO: handle exception e.printStackTrace(); } } else { String timepoint =
		 * subjectMealTimePoint.getTimePoint(); // String s = } }); } else { map.put(0,
		 * list); } return map;
		 */
		return null;
	}

	private Integer caliculateDasyBetweenDates(Date scDate, Date acDate) {
		try {
			Date d1 = scDate;
			Date d2 = acDate;

			// Calucalte time difference
			// in milliseconds
			long difference_In_Time = d1.getTime() - d2.getTime();

			// Calucalte time difference in
			// seconds, minutes, hours, years,
			// and days
//			long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
//			long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
			Long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
//			long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));

			return difference_In_Days.intValue();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SampleTimePoints> sampleTimePoints(TreatmentInfo tr) {
		return getSession().createCriteria(SampleTimePoints.class).add(Restrictions.eq("treatmentInfo", tr)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreatmentInfo> treatmentInfo(StudyMaster sm) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(TreatmentInfo.class).add(Restrictions.eq("study", sm)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<SubjectSampleCollectionTimePoints>> allSubjectTimePoints(Long periodId) {
		// TODO Auto-generated method stub
		List<Integer> subjectno = getSession().createCriteria(SubjectSampleCollectionTimePoints.class)
				.add(Restrictions.eq("period.id", periodId)).setProjection(Projections.groupProperty("subjectOrder"))
				.addOrder(Order.asc("subjectOrder")).list();
		Map<String, List<SubjectSampleCollectionTimePoints>> map = new HashMap<>();
		if (subjectno.size() > 0) {
			subjectno.stream().forEach((subjectOrder) -> {
				List<SubjectSampleCollectionTimePoints> lis = getSession()
						.createCriteria(SubjectSampleCollectionTimePoints.class)
						.add(Restrictions.eq("period.id", periodId)).add(Restrictions.eq("subjectOrder", subjectOrder))
						.addOrder(Order.asc("timePointNo")).list();
				map.put(lis.get(0).getSubjectNo(), lis);
			});
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SampleTimePoints> sampleTimePoints(StudyMaster sm) {
		return getSession().createCriteria(SampleTimePoints.class).add(Restrictions.eq("study", sm)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DoseTimePoints> doseTimePoints(TreatmentInfo tr) {
		return getSession().createCriteria(DoseTimePoints.class).add(Restrictions.eq("treatmentInfo", tr)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DoseTimePoints> doseTimePoints(StudyMaster sm) {
		return getSession().createCriteria(DoseTimePoints.class).add(Restrictions.eq("study", sm)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<SubjectDoseTimePoints>> allSubjectDoseTimePoints(Long periodId) {
		// TODO Auto-generated method stub
		List<Integer> subjectno = getSession().createCriteria(SubjectDoseTimePoints.class)
				.add(Restrictions.eq("period.id", periodId)).setProjection(Projections.groupProperty("subjectOrder"))
				.addOrder(Order.asc("subjectOrder")).list();
		Map<String, List<SubjectDoseTimePoints>> map = new HashMap<>();
		if (subjectno.size() > 0) {
			subjectno.stream().forEach((subjectOrder) -> {
				List<SubjectDoseTimePoints> lis = getSession().createCriteria(SubjectDoseTimePoints.class)
						.add(Restrictions.eq("period.id", periodId)).add(Restrictions.eq("subjectOrder", subjectOrder))
						.addOrder(Order.asc("timePointNo")).list();
				map.put(lis.get(0).getStudySubjects().getSubjectNo(), lis);
			});
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MealsTimePoints> mealsTimePoints(TreatmentInfo tr) {
		return getSession().createCriteria(MealsTimePoints.class).add(Restrictions.eq("treatmentInfo", tr))
				.addOrder(Order.asc("timePointNo")).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MealsTimePoints> mealsTimePoints(StudyMaster sm) {
		return getSession().createCriteria(MealsTimePoints.class).add(Restrictions.eq("study", sm))
				.addOrder(Order.asc("timePointNo")).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<SubjectMealsTimePoints>> allSubjectMealPoints(Long periodId) {
		// TODO Auto-generated method stub
		List<Integer> subjectno = getSession().createCriteria(SubjectMealsTimePoints.class)
				.add(Restrictions.eq("period.id", periodId)).setProjection(Projections.groupProperty("subjectOrder"))
				.addOrder(Order.asc("subjectOrder")).list();
		Map<String, List<SubjectMealsTimePoints>> map = new HashMap<>();
		if (subjectno.size() > 0) {
			subjectno.stream().forEach((subjectOrder) -> {
				List<SubjectMealsTimePoints> lis = getSession().createCriteria(SubjectMealsTimePoints.class)
						.add(Restrictions.eq("period.id", periodId)).add(Restrictions.eq("subjectOrder", subjectOrder))
						.addOrder(Order.asc("timePointNo")).list();
				map.put(lis.get(0).getSubjectNo(), lis);
			});
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ECGTimePoints> ecgTimePoints(TreatmentInfo tr) {
		return getSession().createCriteria(ECGTimePoints.class).add(Restrictions.eq("treatmentInfo", tr))
				.addOrder(Order.asc("timePointNo")).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ECGTimePoints> ecgTimePoints(StudyMaster sm) {
		return getSession().createCriteria(ECGTimePoints.class).add(Restrictions.eq("study", sm))
				.addOrder(Order.asc("timePointNo")).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<SubjectECGTimePoints>> allSubjectEcgPoints(Long periodId) {
		// TODO Auto-generated method stub
		List<Integer> subjectno = getSession().createCriteria(SubjectECGTimePoints.class)
				.add(Restrictions.eq("period.id", periodId)).setProjection(Projections.groupProperty("subjectOrder"))
				.addOrder(Order.asc("subjectOrder")).list();
		Map<String, List<SubjectECGTimePoints>> map = new HashMap<>();
		if (subjectno.size() > 0) {
			subjectno.stream().forEach((subjectOrder) -> {
				List<SubjectECGTimePoints> lis = getSession().createCriteria(SubjectECGTimePoints.class)
						.add(Restrictions.eq("period.id", periodId)).add(Restrictions.eq("subjectOrder", subjectOrder))
						.addOrder(Order.asc("timePointNo")).list();
				map.put(lis.get(0).getSubjectNo(), lis);
			});
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VitalTimePoints> vitalTimePoints(TreatmentInfo tr) {
		return getSession().createCriteria(VitalTimePoints.class).add(Restrictions.eq("treatmentInfo", tr))
				.addOrder(Order.asc("timePointNo")).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VitalTimePoints> vitalTimePoints(StudyMaster sm) {
		return getSession().createCriteria(VitalTimePoints.class).add(Restrictions.eq("study", sm))
				.addOrder(Order.asc("timePointNo")).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<SubjectVitalTimePoints>> allSubjectVitalPoints(Long periodId) {
		// TODO Auto-generated method stub
		List<Integer> subjectno = getSession().createCriteria(SubjectVitalTimePoints.class)
				.add(Restrictions.eq("period.id", periodId)).setProjection(Projections.groupProperty("subjectOrder"))
				.addOrder(Order.asc("subjectOrder")).list();
		Map<String, List<SubjectVitalTimePoints>> map = new HashMap<>();
		if (subjectno.size() > 0) {
			subjectno.stream().forEach((subjectOrder) -> {
				List<SubjectVitalTimePoints> lis = getSession().createCriteria(SubjectVitalTimePoints.class)
						.add(Restrictions.eq("period.id", periodId)).add(Restrictions.eq("subjectOrder", subjectOrder))
						.addOrder(Order.asc("timePointNo")).list();
				map.put(lis.get(0).getSubjectNo(), lis);
			});
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Volunteer> studyVolunters(StudyMaster sm) {
		// TODO Auto-generated method stub
		StatusMaster enrollSatatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
				.add(Restrictions.eq("statusCode", StudyStatus.ENROLLED.toString())).uniqueResult();
		return getSession().createCriteria(Volunteer.class).add(Restrictions.eq("study", sm))
				.add(Restrictions.eq("volunteerType", "Subject")).add(Restrictions.eq("enrollSatatus", enrollSatatus))
				.list();
	}

	@Override
	public List<SubjectDoseTimePoints> subjectDoseTimePoints(DoseTimePoints timepoint, Long periodId) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(SubjectDoseTimePoints.class)
				.add(Restrictions.eq("doseTimePoints", timepoint)).add(Restrictions.eq("period.id", periodId)).list();
	}

	@Override
	public SubjectDoseTimePoints subjectDoseTimePoints(Long id) {
		// TODO Auto-generated method stub
		return (SubjectDoseTimePoints) getSession().get(SubjectDoseTimePoints.class, id);
	}

	@Override
	public void updateSubjectDoseTimePoints(SubjectDoseTimePoints subjectDoseTimePoints) {
		// TODO Auto-generated method stub
		getSession().merge(subjectDoseTimePoints);
	}

	@Override
	public SubjectMealsTimePoints subjectMealsTimePointsForFasting(SubjectDoseTimePoints tech) {

//		Criteria cri = getSession().createCriteria(SubjectMealsTimePoints.class)
//				.add(Restrictions.eq("period", tech.getPeriod()))
//				.add(Restrictions.eq("subjectOrder", tech.getSubjectOrder()));
//		if (tech.getDoseTimePoints().getTimePointNo() == 1) {
//			FromStaticData preDoseMeals = (FromStaticData) getSession().createCriteria(FromStaticData.class)
//					.add(Restrictions.eq("fieldName", StudyDesign.TIMEPOINTTYPE.toString()))
//					.add(Restrictions.eq("code", StaticData.PREDOSE.toString())).uniqueResult();
//			cri.add(Restrictions.eq("timePointType", preDoseMeals));
//			cri.addOrder(Order.desc("timePointNo"));
//			if (tech.getTreatmentInfo() != null)
//				cri.add(Restrictions.eq("treatmentInfo", tech.getTreatmentInfo()));
//			else
//				cri.add(Restrictions.isNull("treatmentInfo"));
//
//			List<SubjectMealsTimePoints> list = cri.list();
//			if (tech.getFedCriteria() != null && !tech.getFedCriteria().equals("0")) {
//				if (list.size() > 1)
//					return list.get(1);
//			} else {
//				if (list.size() > 0)
//					return list.get(0);
//			}
//		} else {
//			FromStaticData postDoseMeals = (FromStaticData) getSession().createCriteria(FromStaticData.class)
//					.add(Restrictions.eq("fieldName", StudyDesign.TIMEPOINTTYPE.toString()))
//					.add(Restrictions.eq("code", StaticData.POSTDOSE.toString())).uniqueResult();
//			cri.add(Restrictions.eq("timePointType", postDoseMeals));
//			cri.addOrder(Order.desc("timePointNo"));
//			if (tech.getTreatmentInfo() != null)
//				cri.add(Restrictions.eq("treatmentInfo", tech.getTreatmentInfo()));
//			else
//				cri.add(Restrictions.isNull("treatmentInfo"));
//
//			String[] scheduleTime = tech.getScheduleTime().split("\\:");
//			int time = (Integer.parseInt(scheduleTime[0]) * 60) + (Integer.parseInt(scheduleTime[1]));
//			List<SubjectMealsTimePoints> list = cri.list();
//			SubjectMealsTimePoints smtp1 = null;
//			SubjectMealsTimePoints smtp2 = null;
//			for (SubjectMealsTimePoints smtp : list) {
//				String[] mealScheduleTime = smtp.getScheduleTime().split("\\:");
//				int mealTime = (Integer.parseInt(mealScheduleTime[0]) * 60) + (Integer.parseInt(mealScheduleTime[1]));
//				if (mealTime < time) {
//					if (smtp1 == null)
//						smtp1 = smtp;
//					else {
//						smtp2 = smtp;
//						break;
//					}
//				}
//			}
//			if (tech.getFedCriteria() != null && !tech.getFedCriteria().equals("0")) {
//				return smtp2;
//			} else
//				return smtp1;
//
//		}
		return null;
	}

	@Override
	public SubjectMealsTimePoints subjectMealsTimePointsForFed(SubjectDoseTimePoints tech) {
//		Criteria cri = getSession().createCriteria(SubjectMealsTimePoints.class)
//				.add(Restrictions.eq("period", tech.getPeriod()))
//				.add(Restrictions.eq("subjectOrder", tech.getSubjectOrder()));
//		if (tech.getTimePointNo() == 1) {
//			FromStaticData preDoseMeals = (FromStaticData) getSession().createCriteria(FromStaticData.class)
//					.add(Restrictions.eq("fieldName", StudyDesign.TIMEPOINTTYPE.toString()))
//					.add(Restrictions.eq("code", StaticData.PREDOSE.toString())).uniqueResult();
//			cri.add(Restrictions.eq("timePointType", preDoseMeals));
//			cri.addOrder(Order.desc("timePointNo"));
//			if (tech.getTreatmentInfo() != null)
//				cri.add(Restrictions.eq("treatmentInfo", tech.getTreatmentInfo()));
//			else
//				cri.add(Restrictions.isNull("treatmentInfo"));
//
//			List<SubjectMealsTimePoints> list = cri.list();
//			if (list.size() > 0)
//				return list.get(0);
//		} else {
//			FromStaticData postDoseMeals = (FromStaticData) getSession().createCriteria(FromStaticData.class)
//					.add(Restrictions.eq("fieldName", StudyDesign.TIMEPOINTTYPE.toString()))
//					.add(Restrictions.eq("code", StaticData.POSTDOSE.toString())).uniqueResult();
//			cri.add(Restrictions.eq("timePointType", postDoseMeals));
//			cri.addOrder(Order.desc("timePointNo"));
//			if (tech.getTreatmentInfo() != null)
//				cri.add(Restrictions.eq("treatmentInfo", tech.getTreatmentInfo()));
//			else
//				cri.add(Restrictions.isNull("treatmentInfo"));
//
//			String[] scheduleTime = tech.getScheduleTime().split("\\:");
//			int time = (Integer.parseInt(scheduleTime[0]) * 60) + (Integer.parseInt(scheduleTime[1]));
//			List<SubjectMealsTimePoints> list = cri.list();
//			for (SubjectMealsTimePoints smtp : list) {
//				String[] mealScheduleTime = smtp.getScheduleTime().split("\\:");
//				int mealTime = (Integer.parseInt(mealScheduleTime[0]) * 60) + (Integer.parseInt(mealScheduleTime[1]));
//				if (mealTime < time)
//					return smtp;
//			}
//		}
		return null;
	}

	@Override
	public List<SampleCollectionDashBoardColumns> sampleCollectionTimePointsHeadding(StudyMaster sm) {
//		ProjectionList columns = Projections.projectionList().add(Projections.property("timePointNo"))
//				.add(Projections.property("noOfVacutainer"));
		Criteria cri = getSession().createCriteria(SampleTimePoints.class).add(Restrictions.eq("study", sm));
//				.setProjection(columns);
		List<SampleTimePoints> list = cri.list();
		List<SampleCollectionDashBoardColumns> result = new ArrayList<>();
		for (SampleTimePoints arr : list) {
			System.out.println(arr.getTimePointNo() + "\t" + arr.getNoOfVacutainer());
		}
		SortedMap<Integer, Integer> set = new TreeMap<>();
		for (SampleTimePoints arr : list) {
			if (set.containsKey(arr.getTimePointNo())) {
				if (set.get(arr.getTimePointNo()) < arr.getNoOfVacutainer())
					set.put(arr.getTimePointNo(), arr.getNoOfVacutainer());
			} else
				set.put(arr.getTimePointNo(), arr.getNoOfVacutainer());

		}
		set.forEach((k, v) -> {
			result.add(new SampleCollectionDashBoardColumns(k, k + "", v));
		});
		Collections.sort(result);
		result.forEach(k -> System.out.println(k));
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SampleCollectionDashBoardColumns> sampleCollectionTimePointsTreatmentTimes(StudyMaster sm,
			List<SampleCollectionDashBoardColumns> columns, TreatmentInfo tr) {
		SortedMap<Integer, SampleCollectionDashBoardColumns> map = new TreeMap<>();
		columns.forEach((c) -> {
			map.put(c.getCount(), c);
		});
		SortedMap<Integer, SampleTimePoints> timepointmap = new TreeMap<>();
		List<SampleTimePoints> stps = getSession().createCriteria(SampleTimePoints.class)
				.add(Restrictions.eq("treatmentInfo", tr)).addOrder(Order.asc("timePointNo")).list();
		stps.forEach((c) -> {
			timepointmap.put(c.getTimePointNo(), c);
		});
		List<SampleCollectionDashBoardColumns> result = new ArrayList<>();
		map.forEach((k, v) -> {
			if (timepointmap.containsKey(k))
				result.add(new SampleCollectionDashBoardColumns(k, timepointmap.get(k).getTimePoint(),
						timepointmap.get(k).getNoOfVacutainer()));
			else
				result.add(new SampleCollectionDashBoardColumns(k, "", 0));
		});
		return result;
	}

	@Override
	public Centrifugation centrifugationWithBarcode(String barcode) {
		// TODO Auto-generated method stub
		return (Centrifugation) getSession().createCriteria(Centrifugation.class)
				.add(Restrictions.eq("centrifugationBarcode", barcode)).uniqueResult();
	}

	@Override
	public SamplesCentrifugation samplesCentrifugation(StudyMaster study) {
		// TODO Auto-generated method stub
		return (SamplesCentrifugation) getSession().createCriteria(SamplesCentrifugation.class)
				.add(Restrictions.eq("study", study)).uniqueResult();
	}

	@Override
	public Instrument centrifugationWithId(Long centrifugeId) {
		// TODO Auto-generated method stub
		return (Instrument) getSession().get(Instrument.class, centrifugeId);
	}

	@Override
	public SamplesCentrifugation sampleCentrifugation(Long sampleCentrifugationId) {
		return (SamplesCentrifugation) getSession().get(SamplesCentrifugation.class, sampleCentrifugationId);
	}

	@Override
	public Long saveCentrifugationData(CentrifugationDataMaster master,
			List<LoadedVacutinersInCentrifuge> vacutaineres) {
		StatusMaster status = null;
		try {
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.NEW.toString())).uniqueResult();
			master.setStatus(status);
			getSession().save(master);
			vacutaineres.stream().forEach((v) -> {
				getSession().save(v);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return master.getId();
	}

	@Override
	public String saveCentrifugationData(String runningTimeWithSeconds, Long centrifugeDataId) {
		// TODO Auto-generated method stub
		CentrifugationDataMaster data = (CentrifugationDataMaster) getSession().get(CentrifugationDataMaster.class,
				centrifugeDataId);
		SimpleDateFormat sd = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
		try {
			data.setCentrifugeEndTime(sd.parse(sd.format(new Date()) + runningTimeWithSeconds));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			data.setCentrifugeEndTime(new Date());
		}
		return data.getId() + "";
	}

	@Override
	public CentrifugationDataMaster centrifugationDataMasterWithId(Long centrifugeDataMasterId) {
		// TODO Auto-generated method stub
		return (CentrifugationDataMaster) getSession().get(CentrifugationDataMaster.class, centrifugeDataMasterId);
	}

	@Override
	public List<CentrifugationData> centrifugationData(CentrifugationDataMaster master) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(CentrifugationData.class)
				.add(Restrictions.eq("centrifugationDataMaster", master)).list();
	}

	@Override
	public Long saveSampleSeparationStart(CentrifugationDataMaster master,
			List<SubjectSampleCollectionTimePoints> sampleTimePoints,
			List<SubjectSampleSeparationTimePointsData> sepdata) {
		getSession().merge(master);
		for (SubjectSampleCollectionTimePoints ssctp : sampleTimePoints) {
			getSession().merge(ssctp.getSubjectSampleCollectionTimePointsData());
		}
		for (SubjectSampleSeparationTimePointsData obj : sepdata)
			getSession().save(obj);
		return master.getId();
	}

	@Override
	public List<CentrifugationData> saveSampleSeparationEndTime(CentrifugationDataMaster master) {
		// TODO Auto-generated method stub
		getSession().merge(master);
		return getSession().createCriteria(CentrifugationData.class)
				.add(Restrictions.eq("centrifugationDataMaster", master)).list();
	}

	@Override
	public List<LoadedVacutinersInCentrifuge> loadedVacutinersInCentrifuge(CentrifugationData obj) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(LoadedVacutinersInCentrifuge.class)
				.add(Restrictions.eq("centrifugationData", obj)).list();
	}

	@Override
	public List<SubjectSampleSeparationTimePointsData> subjectSampleSeparationTimePointsData(CentrifugationData obj) {
		return getSession().createCriteria(SubjectSampleSeparationTimePointsData.class)
				.add(Restrictions.eq("centrifugationData", obj)).list();
	}

	@Override
	public CentrifugationData centrifugationDataWithId(Long centrifugeDataId) {
		// TODO Auto-generated method stub
		return (CentrifugationData) getSession().get(CentrifugationData.class, centrifugeDataId);
	}

	@Override
	public Object storageMaster(SampleStorateDataMaster storageMaster) {
		// TODO Auto-generated method stub
		return getSession().save(storageMaster);
	}

	@Override
	public List<SubjectSampleCollectionTimePointsData> periodSubjectSampleCollectionTimePointsData(Long studyId,
			Long id, String subjectNo) {
		// TODO Auto-generated method stub
		try {
			List<Long> subjectIds = getSession().createCriteria(StudySubjects.class)
					.add(Restrictions.eq("study.id", studyId)).add(Restrictions.eq("subjectNo", subjectNo))
					.setProjection(Projections.property("id")).list();
			DetachedCriteria detached = DetachedCriteria.forClass(StudySubjects.class)
					.add(Restrictions.eq("study.id", studyId)).add(Restrictions.eq("subjectNo", subjectNo))
					.setProjection(Projections.property("id"));
			return getSession().createCriteria(SubjectSampleCollectionTimePointsData.class)
					.add(Restrictions.eq("studyPeriodMaster.id", id)).add(Property.forName("subject.id").eq(detached))
					.list();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public StudySubjects studySubjects(Long studyId, String subjctNo) {
		return (StudySubjects) getSession().createCriteria(StudySubjects.class)
				.add(Restrictions.eq("study.id", studyId)).add(Restrictions.eq("subjectNo", subjctNo)).uniqueResult();
	}

	@Override
	public SortedMap<String, StudyPeriodMaster> subjectPeriods(Long studyId,
			SortedMap<Integer, String> subjectBarocodes) {
		SortedMap<String, StudyPeriodMaster> subjectPeriods = new TreeMap<>();
		DetachedCriteria detached = DetachedCriteria.forClass(StatusMaster.class)
				.add(Restrictions.eq("statusCode", "ACTIVE")).setProjection(Projections.property("id"));
		subjectBarocodes.forEach((k, v) -> {
			StudySubjects subject = studySubjects(studyId, v.substring(0, v.length() - 1).split("a")[1]);
//			StudySubjects subject = (StudySubjects) getSession().createCriteria(StudySubjects.class)
//					.add(Restrictions.eq("study.id", studyId))
//					.add(Restrictions.eq("subjectNo", v.substring(0, v.length() - 1).split("a")[1])).uniqueResult();
			if (subject != null) {
				subjectPeriods.put(subject.getSubjectNo(),
						(StudyPeriodMaster) getSession().createCriteria(StudyGroupPeriodMaster.class)
								.add(Restrictions.eq("studyGroup.id", subject.getGroupId().getId()))
								.add(Property.forName("periodStatus.id").eq(detached))
								.setProjection(Projections.property("period")).uniqueResult());
			}
		});
		return subjectPeriods;
	}

	@Override
	public List<String> droppedSubjects(Long studyId) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(StudySubjects.class).add(Restrictions.eq("study.id", studyId))
				.add(Restrictions.eq("subjectStatus", "DropOut")).setProjection(Projections.property("subjectNo"))
				.list();
	}

	@Override
	public SampleTimePoints sampleTimePointsWithId(long id) {
		// TODO Auto-generated method stub
		return (SampleTimePoints) getSession().get(SampleTimePoints.class, id);
	}

	@Override
	public StudySubjects studySubject(long parseLong, String string) {
		// TODO Auto-generated method stub
		StudySubjects pojo = null;
		pojo = (StudySubjects) getSession().createCriteria(StudySubjects.class)
				.add(Restrictions.eq("study.id", parseLong)).add(Restrictions.eq("subjectNo", string)).uniqueResult();
		return pojo;
	}

	@Override
	public DoseTimePoints doseTimePoints(Long studyId, Long treatmentId) {
		// TODO Auto-generated method stub
		Long id = (Long) getSession().createCriteria(DoseTimePoints.class).add(Restrictions.eq("study.id", studyId))
				.add(Restrictions.eq("treatmentInfo.id", treatmentId)).setProjection(Projections.min("id"))
				.uniqueResult();
		return (DoseTimePoints) getSession().get(DoseTimePoints.class, id);
	}

	@Override
	public SubjectDoseTimePoints subjectDoseTimePoints(DoseTimePoints doseTimePont, StudySubjects subject) {
		return (SubjectDoseTimePoints) getSession().createCriteria(SubjectDoseTimePoints.class)
				.add(Restrictions.eq("doseTimePoints", doseTimePont)).add(Restrictions.eq("studySubjects", subject))
				.uniqueResult();
	}

	@Override
	public String saveSubjectSampleTimePoint(SubjectSampleCollectionTimePointsData data, SampleCollectoinDto scd,
			UserMaster user) {
		String result = "Failed";
		long stpNo = 0;
		boolean flag = false;
		boolean tdFlag = false;
		GlobalActivity ga = null;
		StatusMaster newStatus = null;
		DeviationMessage dm = null;
		DeviationMessage dmPojo = null;
		DeviationMessage sampleDm = null;
		SubjectSampleCollectionTimePointsData sstpDtata = null;
		
		try {
			sstpDtata = (SubjectSampleCollectionTimePointsData) getSession()
					.createCriteria(SubjectSampleCollectionTimePointsData.class)
					.add(Restrictions.eq("studyPeriodMaster", data.getStudyPeriodMaster()))
					.add(Restrictions.eq("sampleTimePoint", data.getSampleTimePoint()))
					.add(Restrictions.eq("subject", data.getSubject())).uniqueResult();
			if (sstpDtata == null) {
				dmPojo = (DeviationMessage) getSession().createCriteria(DeviationMessage.class)
						.add(Restrictions.eq("id", scd.getSampleDevCodeCommentsId())).uniqueResult();
				
				if(scd.getSampleDevCodeId() != null) {
					dm = (DeviationMessage) getSession().createCriteria(DeviationMessage.class)
							.add(Restrictions.eq("id", scd.getSampleDevCodeId())).uniqueResult();
				}
				if(scd.getDeviationCommentsId() != null) {
					sampleDm = (DeviationMessage) getSession().createCriteria(DeviationMessage.class)
							.add(Restrictions.eq("id", scd.getDeviationCommentsId())).uniqueResult();
				}
				data.setSampleDevComments(sampleDm);
				data.setSampleDeviationId(dmPojo);
				data.setDiviationMessage(dm);
				stpNo = (long) getSession().save(data);

				newStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
						.add(Restrictions.eq("statusCode", StudyStatus.NEW.toString())).uniqueResult();

				ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
						.add(Restrictions.eq("activityCode", StudyActivities.SampleCollection.toString()))
						.uniqueResult();
				if (scd.isTimeDeviation() || scd.isSkipDeviation()) {
					if (scd.getSampleDevCodeId() != null && scd.getSampleDevCodeId() != 0) {
						
						StudySubjectDeviations ssd = new StudySubjectDeviations();
						ssd.setActivity(ga);
						ssd.setStudyId(scd.getStudyId());
						ssd.setPeriod(data.getStudyPeriodMaster().getPeriodName());
						ssd.setDeviationRecordId(data.getId());
						ssd.setStatus(newStatus);
						ssd.setTimePoint(data.getSampleTimePoint().getTimePoint());
						ssd.setCreatedBy(user);
						ssd.setCreatedOn(new Date());
						ssd.setDevMsgId(dm);
						ssd.setSubject(data.getSubject());
						long ssdNo = (long) getSession().save(ssd);
						if (ssdNo > 0)
							tdFlag = true;
					} else
						tdFlag = true;
				} else
					tdFlag = true;
				if (stpNo > 0 && tdFlag) {
					result = "success";
					String jsonData = "";
					// passing saving data to Real time Communication
					try {
						RealTimeCommunicationDto rcDto = new RealTimeCommunicationDto();
						rcDto.setPeriodId(data.getStudyPeriodMaster().getId());
						rcDto.setSubjectNo(data.getSubject().getSubjectNo());
						rcDto.setSubjectVitalId(data.getId());
						rcDto.setTimePointId(data.getSampleTimePoint().getId());
						rcDto.setTreatmentId(data.getSampleTimePoint().getTreatmentInfo().getId());
						jsonData = new ObjectMapper().writeValueAsString(rcDto);
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
						jsonData = "";
					}
					/**
					 * Create CpuOnlineData and assign Activity, ServiceName and result json data
					 */
					try {
//						mutex.lock();
						CpuOnlineData vitalData = new CpuOnlineData();
						vitalData.setActivity("Sample Collection");
						vitalData.setServiceName("rtcSampleCollectionData");
						vitalData.setJsonData(jsonData);
						CorsConfigurationProcess ccp = new CorsConfigurationProcess();
						ccp.sendDataToClinets(vitalData);
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
//						  mutex.unlock();
					}
				}
					
			} else
				result = "Duplicate";
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	public List<StudySubjects> allstudySubject(long studyId) {
		List<StudySubjects> subList = null;
		try {
			subList = getSession().createCriteria(StudySubjects.class).add(Restrictions.eq("study.id", studyId))
//					.setProjection(Projections.property("subject"))
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subList;
	}

	@Override
	public SortedMap<String, SubjectDoseTimePoints> subjectAllDoseTimes(Long studyId) {
		// TODO Auto-generated method stub
		SortedMap<String, SubjectDoseTimePoints> timePoints = new TreeMap<>();
//		List<StudySubjects> subjects = allstudySubject(studyId);
		List<StudyPeriodMaster> periods = studyPerods(studyId);
		for (StudyPeriodMaster period : periods) {
			List<SubjectDoseTimePoints> doseTimePoints = getSession().createCriteria(SubjectDoseTimePoints.class)
					.add(Restrictions.eq("period.id", period.getId())).list();
			doseTimePoints.stream().forEach((doseTimePoint) -> {
				timePoints.put(period.getId() + "," + doseTimePoint.getStudySubjects().getStdSubjectId().getSubjectNo(),
						doseTimePoint);
			});
		}
		return timePoints;
	}

	@Override
	public SortedMap<String, SubjectDoseTimePoints> subjectDoseTimes(Long studyId) {
		// TODO Auto-generated method stub
		SortedMap<String, SubjectDoseTimePoints> timePoints = new TreeMap<>();
//		List<StudySubjects> subjects = allstudySubject(studyId);
		List<StudyPeriodMaster> periods = studyPerods(studyId);
		for (StudyPeriodMaster period : periods) {
			List<SubjectDoseTimePoints> doseTimePoints = subjectZeroHrTimePoints(period.getId());

			for (SubjectDoseTimePoints dodd : doseTimePoints) {
				if (period != null) {
					timePoints.put(period.getId() + "," + dodd.getStudySubjects().getSubjectNo(), dodd);
				}
			}
		}
		return timePoints;
	}

	private List<StudyPeriodMaster> studyPerods(Long studyId) {
		return getSession().createCriteria(StudyPeriodMaster.class).add(Restrictions.eq("study.id", studyId)).list();
	}

	private List<SubjectDoseTimePoints> subjectZeroHrTimePoints(Long id) {
		// TODO Auto-generated method stub
		/*
		 * DetachedCriteria detachedCriteria =
		 * DetachedCriteria.forClass(DoseTimePoints.class)
		 * .add(Restrictions.eq("timePoint",
		 * "000.000")).setProjection(Projections.property("id"));
		 */
		List<Long> dtp = getSession().createCriteria(DoseTimePoints.class).add(Restrictions.eq("timePoint", "000.000"))
				.setProjection(Projections.property("id")).list();

		/*
		 * List<SubjectDoseTimePoints> subList =
		 * getSession().createCriteria(SubjectDoseTimePoints.class)
		 * .add(Restrictions.eq("period.id",
		 * id)).add(Restrictions.in("doseTimePoints.id", dtp)).list();
		 */

		List<SubjectDoseTimePoints> subList = null;
		if (dtp.size() > 0) {
			subList = getSession().createCriteria(SubjectDoseTimePoints.class).add(Restrictions.eq("period.id", id))
					.add(Restrictions.in("doseTimePoints.id", dtp)).list();
		} else {
			subList = getSession().createCriteria(SubjectDoseTimePoints.class).add(Restrictions.eq("period.id", id))
					.list();
		}
		return subList;
	}

	@Override
	public List<SubjectMealsTimePointsData> periodSubjectMealCollectionTimePointsData(Long id, String subjectNo,
			Long studyId) {
		// TODO Auto-generated method stub
		try {
			DetachedCriteria detached = DetachedCriteria.forClass(StudySubjects.class)
					.add(Restrictions.eq("study.id", studyId)).add(Restrictions.eq("subjectNo", subjectNo))
					.setProjection(Projections.property("id"));
			Long subid = (Long) getSession().createCriteria(StudySubjects.class)
					.add(Restrictions.eq("study.id", studyId)).add(Restrictions.eq("subjectNo", subjectNo))
					.setProjection(Projections.property("id")).uniqueResult();
			List<SubjectMealsTimePointsData> list = getSession().createCriteria(SubjectMealsTimePointsData.class)
					.add(Restrictions.eq("studyPeriodMaster.id", id)).add(Property.forName("subject.id").eq(detached))
					.list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public List<MealsTimePoints> mealsTimePointsWithIds(List<Long> timePointIdsList) {
		List<MealsTimePoints> list = new ArrayList<>();
		if (timePointIdsList.size() > 0) {
			list = getSession().createCriteria(MealsTimePoints.class).add(Restrictions.in("id", timePointIdsList))
					.list();
		}
		return list;
	}

	@Override
	public SortedMap<Long, TreatmentInfo> allTreatment(Long studyId) {
		List<TreatmentInfo> list = getSession().createCriteria(TreatmentInfo.class)
				.add(Restrictions.eq("study.id", studyId)).list();
		SortedMap<Long, TreatmentInfo> treatmentMap = new TreeMap<>();
		list.stream().forEach((treatment) -> {
			treatmentMap.put(treatment.getId(), treatment);
		});
		return treatmentMap;
	}

	@Override
	public SubjectDoseTimePoints subjectDoseTimePoints(long timePointId, long subjectId) {
		return (SubjectDoseTimePoints) getSession().createCriteria(SubjectDoseTimePoints.class)
				.add(Restrictions.eq("doseTimePoints.id", timePointId))
				.add(Restrictions.eq("studySubjects.id", subjectId)).uniqueResult();
	}

	@Override
	public SubjectDoseTimePoints saveSubjectDoseTimePoints(DoseDataDto dto, SubjectDoseTimePoints tech,
			StudySubjects subject, StudySubjects replaceSubject) {
		/*
		 * List<SubjectActivityTestPerameterDate> perameterValues = new ArrayList<>();
		 * 
		 * for (int i = 0; i < dto.getPerameterIds().size(); i++) { // Long peramId =
		 * colletion.getPerameterIds().get(i); // String[] peramValues =
		 * colletion.getPerameterValue().get(i).split("\\,");
		 * SubjectActivityTestPerameterDate subjectPerameterValue = new
		 * SubjectActivityTestPerameterDate();
		 * subjectPerameterValue.setSubjectDoseTimePoints(tech);
		 * subjectPerameterValue.setGlobalParameter( (GlobalParameter)
		 * getSession().get(GlobalParameter.class, dto.getPerameterIds().get(i)));
		 * subjectPerameterValue.setGlobalParameterValues(dto.getPerameterValue().get(i)
		 * ); perameterValues.add(subjectPerameterValue); }
		 * 
		 * getSession().save(tech); getSession().merge(subject);
		 * getSession().merge(replaceSubject);
		 * perameterValues.stream().forEach((subjectVitalTimePointsTestPerameterDate) ->
		 * { getSession().save(subjectVitalTimePointsTestPerameterDate); });
		 */
		return tech;
	}

//	@Override
	public StudyPeriodMaster periodOne(long studyId) {
		// TODO Auto-generated method stub
		return (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
				.add(Restrictions.eq("study.id", studyId)).add(Restrictions.eq("periodNo", 1)).uniqueResult();
	}

	@Override
	public List<SubjectRandamization> subjectRandamizationByPeriod(Long id) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(SubjectRandamization.class).add(Restrictions.eq("period.id", id)).list();
	}

	@Override
	public List<VitalTimePoints> allVitalTimePoints(Long studyId) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(VitalTimePoints.class).add(Restrictions.eq("study.id", studyId)).list();
	}

	@Override
	public List<SubjectVitalTimePointsData> subjectVitalTimePointsData(List<StudySubjects> subjects) {
		// TODO Auto-generated method stub
		List<Long> subjectIds = new ArrayList<>();
		subjects.forEach((subject) -> {
			subjectIds.add(subject.getId());
		});
		List<SubjectVitalTimePointsData> list = getSession().createCriteria(SubjectVitalTimePointsData.class)
				.add(Restrictions.in("subject.id", subjectIds)).list();
		return list;
	}

	@Override
	public VitalTimePoints vitalTimePointsWithId(Long timePointPk) {
		// TODO Auto-generated method stub
		return (VitalTimePoints) getSession().get(VitalTimePoints.class, timePointPk);
	}

	@Override
	public String saveSubjectVitalTimePoint(SubjectVitalTimePointsData data, VitalCollectionDataDto vcDto,
			UserMaster user) {
		long vitalNo = 0;
		String result = "Failed";
		boolean tdFlag = false;
		GlobalActivity ga = null;
		StatusMaster newStatus = null;
		DeviationMessage dm = null;
		try {
			SubjectVitalTimePointsData svtpData = (SubjectVitalTimePointsData) getSession()
					.createCriteria(SubjectVitalTimePointsData.class).add(Restrictions.eq("subject", data.getSubject()))
					.add(Restrictions.eq("period", data.getPeriod()))
					.add(Restrictions.eq("timepoint", data.getTimepoint()))
					.add(Restrictions.eq("study", data.getStudy()))
					.add(Restrictions.eq("colltedPosition", vcDto.getPositionType())).uniqueResult();
			if (svtpData == null) {
				vitalNo = (long) getSession().save(data);

				ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
						.add(Restrictions.eq("activityCode", StudyActivities.StudyExecutionVitals.toString()))
						.uniqueResult();

				newStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
						.add(Restrictions.eq("statusCode", StudyStatus.NEW.toString())).uniqueResult();

				if (vitalNo > 0) {
					boolean parFlag = true;
					if (vcDto.getPerameterValue() != null && vcDto.getPerameterValue().size() > 0) {
						for (String str : vcDto.getPerameterValue()) {
							if (!str.equals("0")) {
								String[] tempArr = str.split("\\@@@");
								if (tempArr.length > 0) {
									SubjectVitalParametersData svpd = null;
									GlobalParameter gp = (GlobalParameter) getSession()
											.createCriteria(GlobalParameter.class)
											.add(Restrictions.eq("id", Long.parseLong(tempArr[0]))).uniqueResult();
									if (gp != null) {
										svpd = new SubjectVitalParametersData();
										svpd.setCreatedBy(user.getCreatedBy());
										svpd.setCreatedOn(new Date());
										svpd.setParameter(gp);
										svpd.setStudyId(data.getStudy().getId());
										svpd.setSubjVitalTpData(data);

										String controlType = gp.getContentType().getCode();
										if (controlType.equals("RB") || controlType.equals("CB")
												|| controlType.equals("SB")) {

											GlobalValues gv = (GlobalValues) getSession()
													.createCriteria(GlobalValues.class)
													.add(Restrictions.eq("id", Long.parseLong(tempArr[1])))
													.uniqueResult();

											svpd.setGlobalValue(gv);
											svpd.setParameterValue("");
											long sdtpdNo = (long) getSession().save(svpd);
											if (sdtpdNo <= 0)
												parFlag = false;
										} else {
											svpd.setParameterValue(tempArr[1]);
											long sdtpdNo = (long) getSession().save(svpd);
											if (sdtpdNo <= 0)
												parFlag = false;
										}
									}
								}
							}
						}
					} else
						parFlag = true;

					if (parFlag) {
						if (vcDto.isTpSkipDeviation() || vcDto.isTimeDeviation()) {
							if (vcDto.getTimeDeviationId() != null && vcDto.getTimeDeviationId() != 0) {
								dm = (DeviationMessage) getSession().createCriteria(DeviationMessage.class)
										.add(Restrictions.eq("id", vcDto.getTimeDeviationId())).uniqueResult();
								StudySubjectDeviations ssd = new StudySubjectDeviations();
								ssd.setActivity(ga);
								ssd.setStudyId(vcDto.getStudyId());
								ssd.setPeriod(data.getPeriod().getPeriodName());
								ssd.setDeviationRecordId(data.getId());
								ssd.setTimePoint(data.getTimepoint().getTimePoint());
								ssd.setStatus(newStatus);
								ssd.setCreatedBy(user);
								ssd.setCreatedOn(new Date());
								ssd.setDevMsgId(dm);
								ssd.setSubject(data.getSubject());
								long ssdNo = (long) getSession().save(ssd);
								if (ssdNo > 0)
									tdFlag = true;
							} else
								tdFlag = true;

						} else
							tdFlag = true;
					}
				}
				if (vitalNo > 0 && tdFlag) {
					result = "success";
					String jsonData = "";
					// passing saving data to Real time Communication
					try {
						RealTimeCommunicationDto vrtcDto = new RealTimeCommunicationDto();
						vrtcDto.setPeriodId(data.getPeriod().getId());
						vrtcDto.setSubjectNo(data.getSubject().getSubjectNo());
						vrtcDto.setSubjectVitalId(data.getId());
						vrtcDto.setTimePointId(data.getTimepoint().getId());
						vrtcDto.setTreatmentId(data.getTimepoint().getTreatmentInfo().getId());
						vrtcDto.setCollectedPosition(data.getColltedPosition());
						vrtcDto.setTimePoint(data.getTimepoint().getTimePoint());
						jsonData = new ObjectMapper().writeValueAsString(vrtcDto);
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
						jsonData = "";
					}
					/**
					 * Create CpuOnlineData and assign Activity, ServiceName and result json data
					 */
					try {
//						mutex.lock();
						CpuOnlineData vitalData = new CpuOnlineData();
						vitalData.setActivity("Vital Collection");
						vitalData.setServiceName("rtcvitalCollectionData");
						vitalData.setJsonData(jsonData);
						CorsConfigurationProcess ccp = new CorsConfigurationProcess();
						ccp.sendDataToClinets(vitalData);
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
//						  mutex.unlock();
					}
					

				}
			} else
				result = "Duplicate";
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public List<Long> preDoseMeals(Long studyId) {
		// TODO Auto-generated method stub
		List<Long> list = new ArrayList<>();
		List<MealsTimePoints> mealIds = getSession().createCriteria(MealsTimePoints.class)
				.add(Restrictions.eq("study.id", studyId)).add(Restrictions.eq("sign", "-"))
				.addOrder(Order.desc("timePointNo")).list();
		for (MealsTimePoints meal : mealIds) {
			if (meal.getMealsType().getCode().equals(StaticData.NORMALBREAKFAST.toString())
					|| meal.getMealsType().getCode().equals(StaticData.STANDARDBREAKFAST.toString())
					|| meal.getMealsType().getCode().equals(StaticData.HIGHFATBREAKFAST.toString())) {
				list.add(meal.getId());
			}

			if (meal.getMealsType().getCode().equals(StaticData.DINNER.toString())) {
				list.add(meal.getId());
				break;
			}

		}
		return list;
	}

	@Override
	public List<SubjectMealsTimePointsData> subjectPreDoseMeals(StudyPeriodMaster p, List<Long> mealIds,
			StudySubjects s) {
		return getSession().createCriteria(SubjectMealsTimePointsData.class)
				.add(Restrictions.eq("studyPeriodMaster.id", p.getId()))
				.add(Restrictions.in("mealsTimePoint.id", mealIds)).list();
	}

	@Override
	public Instrument instumentInfo(Long insturmentId) {
		// TODO Auto-generated method stub
		return (Instrument) getSession().get(Integer.class, insturmentId);
	}

	@Override
	public CentrifugationDataMaster centrifugationDataMaster(Long centrifugeId) {
		// TODO Auto-generated method stub
		return (CentrifugationDataMaster) getSession().get(CentrifugationDataMaster.class, centrifugeId);
	}

	@Override
	public CentrifugationData centrifugationDataWithSampleTimePoit(long id) {
		// TODO Auto-generated method stub
		return (CentrifugationData) getSession().createCriteria(CentrifugationData.class)
				.add(Restrictions.eq("sampleTimePoints.id", id)).uniqueResult();
	}

	@Override
	public SubjectSampleCollectionTimePointsData subjectSampleCollectionTimePointsData(String vac, Long studyId) {
		SubjectSampleCollectionTimePointsData finalStpDataPojo = null;
		SampleTimePoints stp = null;
		SampleTimePoints barcodePojo = null;
		try {
			vac = vac.replaceAll("a", "#");
			String barcodeSplit[] = vac.split("\\#");
			StudySubjects subject = studySubjects(studyId, barcodeSplit[3]);
			if(subject.getSubjectReplace() != null && !subject.getSubjectReplace().equals("") && subject.getSubjectReplace().equals("Yes")) {
				subject = getReplacedSubject(subject);
			}
			if (!barcodeSplit[2].trim().equals("0")) {
				finalStpDataPojo = (SubjectSampleCollectionTimePointsData) getSession()
						.createCriteria(SubjectSampleCollectionTimePointsData.class)
						.add(Restrictions.eq("studyPeriodMaster.id", Long.parseLong(barcodeSplit[1])))
						.add(Restrictions.eq("subject.id", subject.getId()))
						.add(Restrictions.eq("sampleTimePoint.id", Long.parseLong(barcodeSplit[4].replace("n", ""))))
						.uniqueResult();
			} else {
				String subjectNo = "";
				if (subject != null) {
					if (subject.getStdSubjectId() != null)
						subjectNo = subject.getStdSubjectId().getSubjectNo();
					else
						subjectNo = subject.getSubjectNo();
				} else
					subjectNo = barcodeSplit[3];
				TreatmentInfo treatmentInfo = (TreatmentInfo) getSession().createCriteria(SubjectRandamization.class)
						.add(Restrictions.eq("period.id", Long.parseLong(barcodeSplit[1])))
						.add(Restrictions.eq("subjectNo", subjectNo))
						.setProjection(Projections.property("treatmentInfo")).uniqueResult();
				barcodePojo = (SampleTimePoints) getSession().createCriteria(SampleTimePoints.class)
						.add(Restrictions.eq("id", Long.parseLong(barcodeSplit[4].replace("n", ""))))
						.uniqueResult();
				if (treatmentInfo != null) {
					if (barcodePojo != null) {
						stp = (SampleTimePoints) getSession().createCriteria(SampleTimePoints.class)
								.add(Restrictions.eq("study.id", studyId))
								.add(Restrictions.eq("treatmentInfo", treatmentInfo))
								.add(Restrictions.eq("sign", barcodePojo.getSign()))
								.add(Restrictions.eq("timePoint", barcodePojo.getTimePoint())).uniqueResult();
					}
					if (stp != null) {
						finalStpDataPojo = (SubjectSampleCollectionTimePointsData) getSession()
								.createCriteria(SubjectSampleCollectionTimePointsData.class)
								.add(Restrictions.eq("studyPeriodMaster.id", Long.parseLong(barcodeSplit[1])))
								.add(Restrictions.eq("subject.id", subject.getId()))
								.add(Restrictions.eq("sampleTimePoint", stp)).uniqueResult();
					}
				}else {
					finalStpDataPojo = (SubjectSampleCollectionTimePointsData) getSession()
							.createCriteria(SubjectSampleCollectionTimePointsData.class)
							.add(Restrictions.eq("studyPeriodMaster.id", Long.parseLong(barcodeSplit[1])))
							.add(Restrictions.eq("subject.id", subject.getId()))
							.add(Restrictions.eq("sampleTimePoint", barcodePojo)).uniqueResult();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalStpDataPojo;
	}

	private StudySubjects getReplacedSubject(StudySubjects subject) {
		return (StudySubjects) getSession().createCriteria(StudySubjects.class)
				 .add(Restrictions.eq("stdSubjectId", subject)).uniqueResult();
	}

	@Override
	public List<LoadedVacutinersInCentrifuge> loadedVacutinersInCentrifuges(
			List<Long> subjectSampleCollectionTimePointsDataIds) {
		return getSession()
				.createCriteria(LoadedVacutinersInCentrifuge.class).add(Restrictions
						.in("subjectSampleCollectionTimePointsData.id", subjectSampleCollectionTimePointsDataIds))
				.list();
	}

	@Override
	public Long saveSampleSeparationData(CentrifugationDataMaster master, List<VialSeparationData> vialData) {
		boolean flag = false;
		StatusMaster status = null;
		try {
			getSession().merge(master);
			for (VialSeparationData v : vialData) {
				getSession().save(v);
				flag = true;
			}
			if (flag) {
				status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
						.add(Restrictions.eq("statusCode", StudyStatus.COMPLETED.toString())).uniqueResult();
				master.setStatus(status);
				getSession().merge(master);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return master.getId();
	}

	@Override
	public Long saveSampleStorageData(CentrifugationDataMaster master, SampleStorageData data) {
		// TODO Auto-generated method stub
		getSession().save(data);
		List<SampleStorageDataRack> sampleStorageDataRack = data.getSampleStorageDataRack();
		if (sampleStorageDataRack != null) {
			sampleStorageDataRack.forEach((rack) -> {
				getSession().save(rack);
			});
		}
		return data.getId();
	}

	@Override
	public SubjectDoseTimePoints saveSubjectDoseTimePointsCollectionData(DoseDataDto dto, SubjectDoseTimePoints tech,
			StudySubjects replaceSubject) {
		boolean flag = false;
		long techNo = 0;
		boolean subjFlag = false;
		GlobalActivity ga = null;
		StatusMaster newStatus = null;
		boolean devFlag = false;
		SubjectDoseTimePoints sdtp = null;
		SubjectDoseTimePoints sdtpPojo = null;
		Long replacedSubjectId = null;
		try {
			sdtpPojo = (SubjectDoseTimePoints) getSession().createCriteria(SubjectDoseTimePoints.class)
					.add(Restrictions.eq("period", tech.getPeriod()))
					.add(Restrictions.eq("doseTimePoints", tech.getDoseTimePoints()))
					.add(Restrictions.eq("studySubjects", tech.getStudySubjects())).uniqueResult();

			if (sdtpPojo == null) {
				techNo = (long) getSession().save(tech);

				ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
						.add(Restrictions.eq("activityCode", StudyActivities.DosingCollection.toString()))
						.uniqueResult();

				newStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
						.add(Restrictions.eq("statusCode", StudyStatus.NEW.toString())).uniqueResult();

				if (dto.isReplaceStatus() && replaceSubject != null) {
					getSession().update(replaceSubject);
					StudyVolunteerReporting svrPojo = (StudyVolunteerReporting) getSession()
							.createCriteria(StudyVolunteerReporting.class)
							.add(Restrictions.eq("id", replaceSubject.getReportingId().getId())).uniqueResult();
					if (svrPojo != null) {
						svrPojo.setUpdatedBy(tech.getCreatedBy().getUsername());
						svrPojo.setUpdatedOn(new Date());
						svrPojo.setSubjectNo(
								(1000 + Integer.parseInt(replaceSubject.getStdSubjectId().getSubjectNo())) + "");
						getSession().merge(svrPojo);
						
						StudySubjects sub = (StudySubjects) getSession().createCriteria(StudySubjects.class)
								                  .add(Restrictions.eq("id", replaceSubject.getStdSubjectId().getId())).uniqueResult();
						if(sub != null) {
							replacedSubjectId = sub.getId();
							sub.setSubjectReplace("Replaced");
							sub.setUpatedBy(tech.getCreatedBy().getUsername());
							sub.setUpdatedOn(new Date());
							sub.setUpdateReason("Subject replaced with "+replaceSubject.getSubjectNo());
							getSession().merge(sub);
						}
						
						subjFlag = true;
					}
				} else
					subjFlag = true;
				if (techNo > 0 && subjFlag) {
					boolean parFlag = true;
					if (dto.getPerameterValue() != null && dto.getPerameterValue().size() > 0) {
						for (String str : dto.getPerameterValue()) {
							if (!str.equals("0")) {
								String[] tempArr = str.split("\\@@@");
								if (tempArr.length > 0) {
									if((!tempArr[0].equals("") && !tempArr[0].equals("undefined")) && (!tempArr[1].equals("") && !tempArr[1].equals("undefined"))) {
										SubjectDoseTimePointParametersData sdtpd = null;
										sdtpd = (SubjectDoseTimePointParametersData) getSession().createCriteria(SubjectDoseTimePointParametersData.class)
												         .add(Restrictions.eq("subjectDoseTimePoint.id", tech.getId()))
												         .add(Restrictions.eq("parameter.id", Long.parseLong(tempArr[0]))).uniqueResult();
										if(sdtpd == null) {
											GlobalParameter gp = (GlobalParameter) getSession()
													.createCriteria(GlobalParameter.class)
													.add(Restrictions.eq("id", Long.parseLong(tempArr[0]))).uniqueResult();
											if (gp != null) {
												sdtpd = new SubjectDoseTimePointParametersData();
												sdtpd.setCreatedBy(tech.getCreatedBy());
												sdtpd.setCreatedOn(new Date());
												sdtpd.setParameter(gp);
												sdtpd.setStudyId(dto.getStudyId());
												sdtpd.setSubjectDoseTimePoint(tech);

												String controlType = gp.getContentType().getCode();
												if (controlType.equals("RB") || controlType.equals("CB")
														|| controlType.equals("SB")) {

													GlobalValues gv = (GlobalValues) getSession()
															.createCriteria(GlobalValues.class)
															.add(Restrictions.eq("id", Long.parseLong(tempArr[1])))
															.uniqueResult();

													sdtpd.setGlobalValue(gv);
													sdtpd.setParameterValue("");
													long sdtpdNo = (long) getSession().save(sdtpd);
													if (sdtpdNo <= 0)
														parFlag = false;
												} else {
													sdtpd.setParameterValue(tempArr[1]);
													long sdtpdNo = (long) getSession().save(sdtpd);
													if (sdtpdNo <= 0)
														parFlag = false;
												}
											}
										} else
											parFlag = true;
									}
								} else
									parFlag = true;
							} else
								parFlag = true;
						}
					} else
						parFlag = true;
					if (parFlag) {
						if (dto.isTimeDeviation() || dto.isCriteriaDeviation()) {
							// Raise Deviation
							if (dto.getCriteriaDeviationTimeCodeId() != null
									&& dto.getCriteriaDeviationTimeCodeId() != 0) {
								DeviationMessage dm = (DeviationMessage) getSession()
										.createCriteria(DeviationMessage.class)
										.add(Restrictions.eq("id", dto.getCriteriaDeviationTimeCodeId()))
										.uniqueResult();
								StudySubjectDeviations ssd = new StudySubjectDeviations();
								ssd.setActivity(ga);
								ssd.setStudyId(dto.getStudyId());
								ssd.setPeriod(tech.getPeriod().getPeriodName());
								ssd.setTimePoint(tech.getDoseTimePoints().getTimePoint());
								ssd.setDeviationRecordId(tech.getId());
								ssd.setStatus(newStatus);
								ssd.setCreatedBy(tech.getCreatedBy());
								ssd.setCreatedOn(new Date());
								ssd.setDevMsgId(dm);
								ssd.setSubject(tech.getStudySubjects());
								long ssdNo = (long) getSession().save(ssd);
								if (ssdNo > 0)
									devFlag = true;
							}
							devFlag = true;
						} else
							devFlag = true;
					}
				} else
					subjFlag = true;
				if (techNo > 0 && subjFlag && devFlag) {
					sdtp = tech;
					/*String jsonData = "";
					// passing saving data to Real time Communication
					try {
						RealTimeCommunicationDto rtcDto = new RealTimeCommunicationDto();
						rtcDto.setPeriodId(tech.getPeriod().getId());
						rtcDto.setSubjectNo(tech.getStudySubjects().getSubjectNo());
						rtcDto.setSubjectVitalId(tech.getId());
						rtcDto.setTimePointId(tech.getDoseTimePoints().getId());
						rtcDto.setTreatmentId(tech.getDoseTimePoints().getTreatmentInfo().getId());
						rtcDto.setActualTime(tech.getActualTime());
						rtcDto.setTimePoint(tech.getDoseTimePoints().getTimePoint());
						rtcDto.setReplacedSubjectId(replacedSubjectId);
						jsonData = new ObjectMapper().writeValueAsString(rtcDto);
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
						jsonData = "";
					}
					*//**
					 * Create CpuOnlineData and assign Activity, ServiceName and result json data
					 *//*
					try {
						mutex.lock();
						CpuOnlineData vitalData = new CpuOnlineData();
						vitalData.setActivity("Dosing Collection");
						vitalData.setServiceName("rtcDosingCollectionData");
						vitalData.setJsonData(jsonData);
						CorsConfigurationProcess ccp = new CorsConfigurationProcess();
						ccp.sendDataToClinets(vitalData);
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						  mutex.unlock();
					}*/
				}
			} else
				sdtp = sdtpPojo;
		} catch (Exception e) {
			e.printStackTrace();
			return sdtp;
		}
		return sdtp;
	}

	public StudyPeriodMaster studyperiodById(Long id) {
		// TODO Auto-generated method stub
		return (StudyPeriodMaster) getSession().get(StudyPeriodMaster.class, id);
	}

	@Override
	public DeviationMessage diviationMessage(Long id) {
		// TODO Auto-generated method stub
		return (DeviationMessage) getSession().get(DeviationMessage.class, id);
	}

	@Override
	public List<SubjectSampleCollectionTimePointsData> subjectSampleCollectionTimePointsDataByPeriodId(Long id) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(SubjectSampleCollectionTimePointsData.class)
				.add(Restrictions.eq("studyPeriodMaster.id", id)).list();
	}

	@Override
	public List<CentrifugationDataMaster> centrifugationDataMasterList(long id) {
		return getSession().createCriteria(CentrifugationDataMaster.class).add(Restrictions.eq("study.id", id)).list();
	}

	@Override
	public List<Instrument> alldDefreezers(String instrumentType) {
		InstrumentType instrumentt = (InstrumentType) getSession().createCriteria(InstrumentType.class)
				.add(Restrictions.eq("instrumentType", instrumentType)).uniqueResult();
		if (instrumentt != null) {
			return getSession().createCriteria(Instrument.class)
					.add(Restrictions.eq("instrumentType.id", instrumentt.getId())).list();
		} else
			return null;
	}

	@Override
	public List<Deepfreezer> deepfreezers(List<Long> insIds) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(Deepfreezer.class).add(Restrictions.in("instrument.id", insIds)).list();
	}

	@Override
	public SortedMap<String, StudyPeriodMaster> subjectPeriods(Long studyId) {
		SortedMap<String, StudyPeriodMaster> subjectPeriods = new TreeMap<>();
		DetachedCriteria detached = DetachedCriteria.forClass(StatusMaster.class)
				.add(Restrictions.eq("statusCode", "ACTIVE")).setProjection(Projections.property("id"));
		List<StudySubjects> subjects = allstudySubject(studyId);
		subjects.forEach((subject) -> {
			if (subject != null) {
				subjectPeriods.put(subject.getSubjectNo(),
						(StudyPeriodMaster) getSession().createCriteria(StudyGroupPeriodMaster.class)
								.add(Restrictions.eq("studyGroup.id", subject.getGroupId().getId()))
								.add(Property.forName("periodStatus.id").eq(detached))
								.setProjection(Projections.property("period")).uniqueResult());
			}
		});
		return subjectPeriods;
	}

	@Override
	public void updateSubjectMealsTimePointsData(SubjectMealsTimePointsData subjectMealsTimePointsData) {
		// TODO Auto-generated method stub
		getSession().merge(subjectMealsTimePointsData);
	}

	@Override
	public SubjectMealsTimePointsData subjectMealsTimePointsDataById(Long subjectPeriodTimePointCollectedDataId) {
		// TODO Auto-generated method stub
		return (SubjectMealsTimePointsData) getSession().get(SubjectMealsTimePointsData.class,
				subjectPeriodTimePointCollectedDataId);
	}

	@Override
	public List<GlobalParameter> globalParameter(List<Long> perameterIds) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(GlobalParameter.class).add(Restrictions.in("id", perameterIds)).list();
	}

	@Override
	public Map<Long, SampleTimePoints> getSampleTimePointsWithStudyForKeyVal(long studyid) {
		Map<Long, SampleTimePoints> samplecollectedData = new HashMap<>();
		List<SampleTimePoints> list = getSession().createCriteria(SampleTimePoints.class)
				.add(Restrictions.eq("study.id", studyid)).list();
		for (SampleTimePoints addid : list) {
			samplecollectedData.put(addid.getId(), addid);
		}
		return samplecollectedData;
	}

	@Override
	public boolean vialRackSave(List<RackWithVials> rwvList) {
		boolean flag = false;
		try {
			for (RackWithVials addval : rwvList) {
				getSession().save(addval);
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public StudyPeriodMaster getStudyPeriodMasterWithPeriodId(long periodid) {
		StudyPeriodMaster pojo = null;
		pojo = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
				.add(Restrictions.eq("id", periodid)).uniqueResult();
		return pojo;
	}

	@Override
	public Deepfreezer getDeepfreezerWithId(long shefid) {
		Deepfreezer deep = null;
		deep = (Deepfreezer) getSession().createCriteria(Deepfreezer.class).add(Restrictions.eq("id", shefid))
				.uniqueResult();
		return deep;
	}

	public List<DeepfreezerShelf> deepfreezerShelfs(List<Long> insIds) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(DeepfreezerShelf.class).add(Restrictions.in("instrument.id", insIds)).list();
	}

	@Override
	public List<RackWithVials> rackWithVials(Long rack) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(RackWithVials.class).add(Restrictions.eq("reack.id", rack)).list();
	}

	@Override
	public DeepfreezerShelf deepfreezerShelfsById(long id) {
		// TODO Auto-generated method stub
		return (DeepfreezerShelf) getSession().get(DeepfreezerShelf.class, id);
	}

	@Override
	public Deepfreezer deepfreezers(long id) {
		return (Deepfreezer) getSession().get(Deepfreezer.class, id);
	}

	@Override
	public List<RackWithVials> rackWithVialsWithRackId(long rackId) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(RackWithVials.class).add(Restrictions.eq("reack.id", rackId)).list();
	}

	@Override
	public boolean vialRackSave(RackWithVials rwvpojo, List<RackWithVitalSubject> rwvsList) {
		boolean flag = false;
		try {
			getSession().save(rwvpojo);
			for (RackWithVitalSubject addval : rwvsList) {
				getSession().save(addval);
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public StudySubjects getStudySubjectsWithStudyAndSubject(StudyMaster study, String string) {
		StudySubjects deep = null;
		deep = (StudySubjects) getSession().createCriteria(StudySubjects.class).add(Restrictions.eq("study", study))
				.add(Restrictions.eq("subjectNo", string)).uniqueResult();
		return deep;
	}

	public List<Deepfreezer> deepfreezersAll() {
		// TODO Auto-generated method stub
		return getSession().createCriteria(Deepfreezer.class).list();
	}

	@Override
	public List<DeepfreezerShelf> deepfreezersShellfAll() {
		return getSession().createCriteria(DeepfreezerShelf.class).list();
	}

	@Override
	public List<RackWithVials> rackWithVialsWithStudyId(Long studyId) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(RackWithVials.class).add(Restrictions.eq("study.id", studyId)).list();
	}

	@Override
	public List<SubjectSampleCollectionTimePointsData> periodSubjectSampleCollectionTimePointsData(Long studyId) {
		// TODO Auto-generated method stub
		List<StudySubjects> subjects = allstudySubject(studyId);
		List<Long> ids = new ArrayList<>();
		for (StudySubjects subject : subjects) {
			ids.add(subject.getId());
		}
		if (ids.size() > 0) {
			List<SubjectSampleCollectionTimePointsData> list = getSession()
					.createCriteria(SubjectSampleCollectionTimePointsData.class).add(Restrictions.in("subject.id", ids))
					.list();
			return list;
		} else
			return new ArrayList<>();

	}

	@Override
	public int noOfVials(Long studyId) {
		// TODO Auto-generated method stub
		return (int) getSession().createCriteria(SampleTimePoints.class).add(Restrictions.eq("study.id", studyId))
				.setProjection(Projections.max("noOfVials")).uniqueResult();
	}

	@Override
	public List<RackWithVitalSubject> rackWithVitalSubject(long id) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(RackWithVitalSubject.class).add(Restrictions.eq("rackWithVials.id", id))
				.list();
	}

	@Override
	public SampleContainer saveSampleContainer(SampleContainer container) {
		// TODO Auto-generated method stub
		getSession().save(container);
		List<SampleContainerVials> vialsList = container.getVialsList();
		for (SampleContainerVials scv : vialsList) {
			getSession().save(scv);
		}
		return container;
	}

	@Override
	public Map<Long, List<CentrifugationDataMaster>> centrifugationDataMasterAll(Long studyId) {
		// TODO Auto-generated method stub
		Map<Long, List<CentrifugationDataMaster>> map = new HashMap<>();
		List<StudyPeriodMaster> periods = getSession().createCriteria(StudyPeriodMaster.class)
				.add(Restrictions.eq("study.id", studyId)).list();
		for (StudyPeriodMaster sp : periods) {
			map.put(sp.getId(), getSession().createCriteria(CentrifugationDataMaster.class)
					.add(Restrictions.eq("period.id", sp.getId())).list());
		}
		return map;
	}

	@Override
	public List<StudyTreatmentWiseSubjects> getStudyTreatmentWiseSubjectsWithStudy(Long studyId) {
		return getSession().createCriteria(StudyTreatmentWiseSubjects.class).add(Restrictions.eq("sm.id", studyId))
				.list();
	}

	@Override
	public MealsDataSavingDto getMealsDataSavingDtoDetails(MealCollectoinDto mcd, Long userId) {
		MealsDataSavingDto mdsDto = null;
		StudyMaster study = null;
		List<StudyPeriodMaster> spmList = null;
		List<StudySubjects> subjectsList = null;
		List<TreatmentInfo> treatmentList = null;
		List<MealsTimePoints> mealsList = null;
		UserMaster user = null;
		List<SubjectMealsTimePointsData> smtpDataList = null;
		try {
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					.add(Restrictions.eq("id", mcd.getStudyId())).uniqueResult();
			if (study != null) {
				spmList = getSession().createCriteria(StudyPeriodMaster.class).add(Restrictions.eq("study", study))
						.list();

				subjectsList = getSession().createCriteria(StudySubjects.class)
						.add(Restrictions.in("subjectNo", mcd.getSubNoList())).add(Restrictions.eq("study", study))
						.list();

				treatmentList = getSession().createCriteria(TreatmentInfo.class).add(Restrictions.eq("study", study))
						.list();

				mealsList = getSession().createCriteria(MealsTimePoints.class).add(Restrictions.eq("study", study))
						.list();

				user = (UserMaster) getSession().createCriteria(UserMaster.class).add(Restrictions.eq("id", userId))
						.uniqueResult();

				if (mcd.getTimePointIds() != null && mcd.getTimePointIds().size() > 0) {
					smtpDataList = getSession().createCriteria(SubjectMealsTimePointsData.class)
							.add(Restrictions.in("mealsTimePoint.id", mcd.getTimePointIds())).list();
				}
			}

			mdsDto = new MealsDataSavingDto();
			mdsDto.setStudy(study);
			mdsDto.setSpmList(spmList);
			mdsDto.setSubjectsList(subjectsList);
			mdsDto.setTreatmentList(treatmentList);
			mdsDto.setMealsList(mealsList);
			mdsDto.setUser(user);
			mdsDto.setSmtpDataList(smtpDataList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mdsDto;
	}

	@Override
	public String saveSubjectsMealsData(List<SubjectMealsTimePointsData> savingSubMDataList,
			List<SubjectMealsTimePointsData> updateSubMDataList, MealCollectoinDto mcd, UserMaster user,
			Map<String, Map<Long, String>> devMap) {
		String result = "Failed";
		long smtdNo = 0;
		boolean smtFlag = false;
		boolean falg = false;
		Map<Long, Long> smtpdIds = new HashMap<>(); // For deviation purpose
		Map<Long, String> tpsMap = new HashMap<>();// For timpoints //tpId, tpstr
		Map<Long, String> tpPeriodMap = new HashMap<>(); // tpId, periodName
		List<RealTimeCommunicationDto> rcmDtoList = new ArrayList<>();
		try {
			if (savingSubMDataList != null && savingSubMDataList.size() > 0) {
				for (SubjectMealsTimePointsData smtd : savingSubMDataList) {
					SubjectMealsTimePointsData smtpData = (SubjectMealsTimePointsData) getSession().createCriteria(SubjectMealsTimePointsData.class)
							                                  .add(Restrictions.eq("subject", smtd.getSubject()))
							                                  .add(Restrictions.eq("studyPeriodMaster", smtd.getStudyPeriodMaster()))
							                                  .add(Restrictions.eq("mealsTimePoint", smtd.getMealsTimePoint())).uniqueResult();
					if(smtpData == null) {
						smtdNo = (long) getSession().save(smtd);
						if (smtdNo > 0) {
							smtpdIds.put(smtd.getMealsTimePoint().getId(), smtd.getId());
							tpsMap.put(smtd.getMealsTimePoint().getId(), smtd.getMealsTimePoint().getTimePoint());
							tpPeriodMap.put(smtd.getMealsTimePoint().getId(), smtd.getStudyPeriodMaster().getPeriodName());
						}
						RealTimeCommunicationDto rcDto = getRealTimeCommunicationDtoRecord(smtd);
						rcmDtoList.add(rcDto);
					}
					
				}
			}
			if (updateSubMDataList != null && updateSubMDataList.size() > 0) {
				for (SubjectMealsTimePointsData smd : updateSubMDataList) {
					getSession().update(smd);
					smtFlag = true;
					RealTimeCommunicationDto rcDto = getRealTimeCommunicationDtoRecord(smd);
					rcmDtoList.add(rcDto);
				}
			}
			if (smtdNo > 0 || smtFlag) {
				if (mcd.isTimeDeviation()) {
					// Raise Deviation for Time Points Skipping
					if (devMap != null && devMap.size() > 0) {
						for (Map.Entry<String, Map<Long, String>> dev : devMap.entrySet()) {
							for (Map.Entry<Long, String> devation : dev.getValue().entrySet()) {
								String[] tempArr = devation.getValue().split("\\@@@@");
								if (tempArr.length > 0) {
									if (tempArr[1] != null && !tempArr[1].equals("0")) {
										StudySubjects sub = (StudySubjects) getSession()
												.createCriteria(StudySubjects.class)
												.add(Restrictions.eq("subjectNo", dev.getKey()))
												.add(Restrictions.eq("study.id", mcd.getStudyId())).uniqueResult();

										GlobalActivity ga = (GlobalActivity) getSession()
												.createCriteria(GlobalActivity.class).add(Restrictions
														.eq("activityCode", StudyActivities.MealsCollection.toString()))
												.uniqueResult();

										DeviationMessage dm = (DeviationMessage) getSession()
												.createCriteria(DeviationMessage.class)
												.add(Restrictions.eq("id", Long.parseLong(tempArr[1]))).uniqueResult();

										StatusMaster newStatus = (StatusMaster) getSession()
												.createCriteria(StatusMaster.class)
												.add(Restrictions.eq("statusCode", StudyStatus.NEW.toString()))
												.uniqueResult();

										StudySubjectDeviations ssd = new StudySubjectDeviations();
										ssd.setActivity(ga);
										ssd.setPeriod(tpPeriodMap.get(devation.getKey()));
										ssd.setTimePoint(tpsMap.get(devation.getKey()));
										ssd.setDeviationRecordId(smtpdIds.get(devation.getKey()));
										ssd.setStudyId(mcd.getStudyId());
										ssd.setStatus(newStatus);
										ssd.setCreatedBy(user);
										ssd.setCreatedOn(new Date());
										ssd.setDevMsgId(dm);
										ssd.setSubject(sub);
										long ssdNo = (long) getSession().save(ssd);
										if (ssdNo > 0)
											falg = true;
									} else
										falg = true;
								}
								falg = true;
							}
						}
					} else
						falg = true;

				} else
					falg = true;
				if (falg) {
					result = "success";
					String jsonData = "";
					// passing saving data to Real time Communication
					try {
						MealsRealTimeCommunicationDto mtcDto = new MealsRealTimeCommunicationDto();
						mtcDto.setRtcDtoList(rcmDtoList);
						jsonData = new ObjectMapper().writeValueAsString(mtcDto);
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
						jsonData = "";
					}
					/**
					 * Create CpuOnlineData and assign Activity, ServiceName and result json data
					 */
					try {
//						mutex.lock();
						CpuOnlineData vitalData = new CpuOnlineData();
						vitalData.setActivity("Meals Collection");
						vitalData.setServiceName("rtcMealsCollectionData");
						vitalData.setJsonData(jsonData);
						CorsConfigurationProcess ccp = new CorsConfigurationProcess();
						ccp.sendDataToClinets(vitalData);
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
//						  mutex.unlock();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	private RealTimeCommunicationDto getRealTimeCommunicationDtoRecord(SubjectMealsTimePointsData smtd) {
		RealTimeCommunicationDto rcDto = new RealTimeCommunicationDto();
		try {
			rcDto.setPeriodId(smtd.getStudyPeriodMaster().getId());
			rcDto.setSubjectNo(smtd.getSubject().getSubjectNo());
			rcDto.setSubjectVitalId(smtd.getId());
			rcDto.setTimePointId(smtd.getMealsTimePoint().getId());
			rcDto.setTreatmentId(smtd.getMealsTimePoint().getTreatmentInfo().getId());
			rcDto.setStudyId(smtd.getStudyPeriodMaster().getStudy().getId());
			if(smtd.getStartTime() != null)
				rcDto.setMealsSatartTime(smtd.getStartTime());
			if(smtd.getEndTime() != null)
				rcDto.setMealsEndTime(smtd.getEndTime());
			if(smtd.getSubject().getStdSubjectId() != null)
				rcDto.setReplacedSubjectId(smtd.getSubject().getStdSubjectId().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rcDto;
	}

	@Override
	public SubjectDoseTimePoints getSubjectDoseTimepointRecord(StudySubjects studySubjects,
			StudyPeriodMaster studyPeriodMaster, StudyMaster study) {
		SubjectRandamization subr = null;
		Long doseMinId = null;
		SubjectDoseTimePoints sdtp = null;
		try {
			if(studySubjects.getStdSubjectId() != null) {
				subr = (SubjectRandamization) getSession().createCriteria(SubjectRandamization.class)
						.add(Restrictions.eq("subjectNo", studySubjects.getStdSubjectId().getSubjectNo()))
						.add(Restrictions.eq("period", studyPeriodMaster)).uniqueResult();
			}else {
				subr = (SubjectRandamization) getSession().createCriteria(SubjectRandamization.class)
						.add(Restrictions.eq("subjectNo", studySubjects.getSubjectNo()))
						.add(Restrictions.eq("period", studyPeriodMaster)).uniqueResult();
			}
			if (subr != null) {
				doseMinId = (Long) getSession().createCriteria(DoseTimePoints.class)
						.add(Restrictions.eq("study", study))
						.add(Restrictions.eq("treatmentInfo", subr.getTreatmentInfo()))
						.setProjection(Projections.min("id")).uniqueResult();
				if (doseMinId != null) {
					sdtp = (SubjectDoseTimePoints) getSession().createCriteria(SubjectDoseTimePoints.class)
							.add(Restrictions.eq("period", studyPeriodMaster))
							.add(Restrictions.eq("studySubjects", studySubjects))
							.add(Restrictions.eq("doseTimePoints.id", doseMinId)).uniqueResult();

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdtp;
	}

	/*
	 * @SuppressWarnings("unused")
	 * 
	 * @Override
	 */
	public CommonTpDetails getBloodSampleCollectionDetails2(Long studyId, String collectionType) {
		Date startTime = new Date();
		CommonTpDetails ctpd = null;
		StudyMaster study = null;
		List<StudySubjects> subList = null;
		List<SubjectRandamization> subRzList = null;
		List<TreatmentInfo> treatmentList = null;
		Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> pwdoseMap = new HashMap<>();// subjectNo, periodId,
																								// treatment,
																								// subjectDose
		List<StudySubjectPeriods> studySubjectsPeriodList = null;
		List<Long> periodIds = null;
		StatusMaster status = null;
		Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap = new HashMap<>();
		List<Long> subjectIds = null;
		List<StudySubjectPeriods> ssubPeriodList = null;
		List<String> dropOutSubList = null;
		DosingDto dsDto = null;
		Map<String, Map<Long, SubjectDoseTimePoints>> dosedWithoutTreatmentMap = new HashMap<>();// subjectNo, periodId,
																									// subjectDosePojo
		List<SubjectWithdrawDetails> swdrList = null;
		try {
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class).add(Restrictions.eq("id", studyId))
					.uniqueResult();
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
			if (study != null) {

				dropOutSubList = getSession().createCriteria(StudySubjects.class)
						.add(Restrictions.eq("study.id", studyId)).add(Restrictions.eq("subjectStatus", "DropOut"))
						.setProjection(Projections.property("subjectNo")).list();

				subList = getSession().createCriteria(StudySubjects.class).add(Restrictions.eq("study", study)).list();

				subjectIds = getSession().createCriteria(StudySubjects.class).add(Restrictions.eq("study", study))
						.add(Restrictions.eq("subjectStatus", "active")).setProjection(Projections.property("id"))
						.list();

				periodIds = getSession().createCriteria(StudyPeriodMaster.class).add(Restrictions.eq("study", study))
						.setProjection(Projections.property("id")).list();
				if (periodIds != null) {
					subRzList = getSession().createCriteria(SubjectRandamization.class)
							.add(Restrictions.in("period.id", periodIds)).list();

					studySubjectsPeriodList = getSession().createCriteria(StudySubjectPeriods.class)
							.add(Restrictions.in("periodId.id", periodIds)).add(Restrictions.eq("status", status))
							.list();
				}
				treatmentList = getSession().createCriteria(TreatmentInfo.class).add(Restrictions.eq("study", study))
						.list();
				if (treatmentList != null && treatmentList.size() > 0) {
					for (TreatmentInfo tinf : treatmentList) {
						Long doseMinId = (Long) getSession().createCriteria(DoseTimePoints.class)
								.add(Restrictions.eq("study", study)).add(Restrictions.eq("treatmentInfo", tinf))
								.setProjection(Projections.min("id")).uniqueResult();
						if (doseMinId != null && (periodIds != null && periodIds.size() > 0)) {
							for (Long pid : periodIds) {
								List<SubjectDoseTimePoints> sdtpList = getSession()
										.createCriteria(SubjectDoseTimePoints.class)
										.add(Restrictions.eq("period.id", pid))
										.add(Restrictions.eq("doseTimePoints.id", doseMinId)).list();
								if (sdtpList != null && sdtpList.size() > 0) {
									Map<Long, Map<Long, SubjectDoseTimePoints>> tempMap1 = null;
									Map<Long, SubjectDoseTimePoints> tempMap2 = null;
									for (SubjectDoseTimePoints sdtp : sdtpList) {
										if (pwdoseMap.containsKey(sdtp.getStudySubjects().getSubjectNo())) {
											tempMap1 = pwdoseMap.get(sdtp.getStudySubjects().getSubjectNo());
											if (tempMap1.containsKey(sdtp.getPeriod().getId())) {
												tempMap2 = tempMap1.get(sdtp.getPeriod().getId());
												if (tempMap2.containsKey(
														sdtp.getDoseTimePoints().getTreatmentInfo().getId())) {
													tempMap2.put(sdtp.getDoseTimePoints().getTreatmentInfo().getId(),
															sdtp);
													tempMap1.put(sdtp.getPeriod().getId(), tempMap2);
													pwdoseMap.put(sdtp.getStudySubjects().getSubjectNo(), tempMap1);
												} else {
													if (tempMap2 == null)
														tempMap2 = new HashMap<>();
													tempMap2.put(sdtp.getDoseTimePoints().getTreatmentInfo().getId(),
															sdtp);
													tempMap1.put(sdtp.getPeriod().getId(), tempMap2);
													pwdoseMap.put(sdtp.getStudySubjects().getSubjectNo(), tempMap1);
												}
											} else {
												if (tempMap2 == null)
													tempMap2 = new HashMap<>();
												if (tempMap1 == null)
													tempMap1 = new HashMap<>();
												tempMap2.put(sdtp.getDoseTimePoints().getTreatmentInfo().getId(), sdtp);
												tempMap1.put(sdtp.getPeriod().getId(), tempMap2);
												pwdoseMap.put(sdtp.getStudySubjects().getSubjectNo(), tempMap1);
											}

										} else {
											tempMap2 = new HashMap<>();
											tempMap1 = new HashMap<>();
											tempMap2.put(sdtp.getDoseTimePoints().getTreatmentInfo().getId(), sdtp);
											tempMap1.put(sdtp.getPeriod().getId(), tempMap2);
											pwdoseMap.put(sdtp.getStudySubjects().getSubjectNo(), tempMap1);
										}

									}
								}
							}
						}
					}
				}

			}

			if (subjectIds != null && subjectIds.size() > 0) {

				ssubPeriodList = getSession().createCriteria(StudySubjectPeriods.class)
						.add(Restrictions.in("subject.id", subjectIds)).add(Restrictions.eq("status", status)).list();

				Map<Long, String> tempMap = null;
				if (ssubPeriodList != null && ssubPeriodList.size() > 0) {
					for (StudySubjectPeriods ssp : ssubPeriodList) {
						SubjectRandamization srz = (SubjectRandamization) getSession()
								.createCriteria(SubjectRandamization.class)
								.add(Restrictions.eq("subjectNo", ssp.getSubject().getSubjectNo()))
								.add(Restrictions.eq("period", ssp.getPeriodId())).uniqueResult();
						if (srz != null) {
							Long minId = (Long) getSession().createCriteria(DoseTimePoints.class)
									.add(Restrictions.eq("treatmentInfo", srz.getTreatmentInfo()))
									.setProjection(Projections.min("id")).uniqueResult();
							if (minId != null) {
								SubjectDoseTimePoints sdtp = (SubjectDoseTimePoints) getSession()
										.createCriteria(SubjectDoseTimePoints.class)
										.add(Restrictions.eq("studySubjects", ssp.getSubject()))
										.add(Restrictions.eq("period", ssp.getPeriodId()))
										.add(Restrictions.eq("doseTimePoints.id", minId)).uniqueResult();
								if (sdtp != null) {
									Map<Long, Map<Long, SubjectDoseTimePoints>> pdMap = new HashMap<>();
									Map<Long, SubjectDoseTimePoints> pdMapForNonTreatment = new HashMap<>();
									Map<Long, SubjectDoseTimePoints> trMap = new HashMap<>();
									trMap.put(srz.getTreatmentInfo().getId(), sdtp);
									pdMap.put(srz.getPeriod().getId(), trMap);
									dosedMap.put(sdtp.getStudySubjects().getSubjectNo(), pdMap);
									pdMapForNonTreatment.put(srz.getPeriod().getId(), sdtp);
									dosedWithoutTreatmentMap.put(sdtp.getStudySubjects().getSubjectNo(),
											pdMapForNonTreatment);
								}

							}
						}
					}
				}

			}

			// Deviation messages
			dsDto = getDosingDtoDetails(studyId, collectionType);
			dsDto.setStudy(study);
			Map<String, Long> devionCodeMap = new HashMap<>();
			// Deviation messages
			if (dsDto != null) {
				List<DeviationMessage> devMsgList = dsDto.getDevMsgList();
				if (devMsgList != null && devMsgList.size() > 0) {
					for (DeviationMessage dvm : devMsgList) {
						devionCodeMap.put(dvm.getDeveloperCode(), dvm.getId());
					}
					dsDto.setDevionCodeMap(devionCodeMap);
				}
			}
			swdrList = getSession().createCriteria(SubjectWithdrawDetails.class).add(Restrictions.eq("study", study))
					.list();
			ctpd = new CommonTpDetails();
			ctpd.setStudy(study);
			ctpd.setSubList(subList);
			ctpd.setSubRzList(subRzList);
			ctpd.setTreatmentList(treatmentList);
			ctpd.setPwdoseMap(pwdoseMap);
			ctpd.setStudySubjectsPeriodList(studySubjectsPeriodList);
			ctpd.setDosedMap(dosedMap);
			ctpd.setDropOutSubList(dropOutSubList);
			ctpd.setDsDto(dsDto);
			ctpd.setDosedWithoutTreatmentMap(dosedWithoutTreatmentMap);
			ctpd.setSwdrList(swdrList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("EXE time : " + (new Date().getTime() - startTime.getTime()));
		return ctpd;
	}

	@Override
	@SuppressWarnings("unused")
	public CommonTpDetails getBloodSampleCollectionDetails(Long studyId, String collectionType) {
//		Date startTime = new Date();
		CommonTpDetails ctpd = null;
		StudyMaster study = null;
		List<StudySubjects> subList = null;
		List<SubjectRandamization> subRzList = null;
		List<TreatmentInfo> treatmentList = null;
		Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> pwdoseMap = new HashMap<>();// subjectNo,
																								// periodId,treatment,subjectDose
		List<StudySubjectPeriods> studySubjectsPeriodList = null;
		List<Long> periodIds = null;
		StatusMaster status = null;
		Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap = new HashMap<>();
		List<Long> subjectIds = new ArrayList<>();
		List<StudySubjectPeriods> ssubPeriodList = null;
		List<String> dropOutSubList = new ArrayList<>();
		DosingDto dsDto = null;
		Map<String, Map<Long, SubjectDoseTimePoints>> dosedWithoutTreatmentMap = new HashMap<>();// subjectNo, // periodId,subjectDosePojo
		List<SubjectWithdrawDetails> swdrList = null;
		List<Long> volIds = new ArrayList<>();
		Map<String, Long> subVolIdsMap = new HashMap<>();//Subject, volId
		Map<Long, Long> saActIdsMap = new HashMap<>(); //StudyActivitiesId, PeriodId
		List<StudyActivityDataDto> sadataList = null;
		List<Long> saIds = new ArrayList<>();
		try {
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class).add(Restrictions.eq("id", studyId))
					.uniqueResult();
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
			if (study != null) {
				subList = getSession().createCriteria(StudySubjects.class).add(Restrictions.eq("study.id", studyId))
						.list();
				for (StudySubjects subejct : subList) {
					if (subejct.getSubjectStatus().equals("DropOut"))
						dropOutSubList.add(subejct.getSubjectNo());
					else if (subejct.getSubjectStatus().equals("active"))
						subjectIds.add(subejct.getId());
					volIds.add(subejct.getReportingId().getId());
					subVolIdsMap.put(subejct.getSubjectNo(), subejct.getReportingId().getId());
				}

				periodIds = getSession().createCriteria(StudyPeriodMaster.class).add(Restrictions.eq("study", study))
						.setProjection(Projections.property("id")).list();
				if (periodIds != null) {
					subRzList = getSession().createCriteria(SubjectRandamization.class)
							.add(Restrictions.in("period.id", periodIds)).list();

					studySubjectsPeriodList = getSession().createCriteria(StudySubjectPeriods.class)
							.add(Restrictions.in("periodId.id", periodIds)).add(Restrictions.eq("status", status))
							.list();
				}
				treatmentList = getSession().createCriteria(TreatmentInfo.class).add(Restrictions.eq("study", study))
						.list();
				List<Long> treatmentIds = new ArrayList<>();
				treatmentList.stream().forEach((tinf) -> {
					treatmentIds.add(tinf.getId());
				});
				
				List<Long> doseMinIds = new ArrayList<>();
				List<Object[]> doseTimePointsList = getSession().createCriteria(DoseTimePoints.class)
						.add(Restrictions.eq("study", study))
						.add(Restrictions.in("treatmentInfo.id", treatmentIds))
						.setProjection(Projections.projectionList().add(Projections.min("id")).add(Projections.groupProperty("treatmentInfo.id")))
						.list();
				for(Object[] arr : doseTimePointsList) {
					doseMinIds.add((Long)arr[0]);
				}
				/*List<Long> doseMinIds = new ArrayList<>();
				List<DoseTimePoints> doseTimePointsList = getSession().createCriteria(DoseTimePoints.class)
						.add(Restrictions.eq("study", study))
						.list();
				
				Map<Long,List<Long>> treamentDoseIds = new HashMap<>(); 
				Map<Long,Long> treatmentDoses = new HashMap<>();
				
				for(DoseTimePoints doseTimepoint: doseTimePointsList) {
					if(treamentDoseIds.containsKey(doseTimepoint.getTreatmentInfo().getId())) {
						List<Long> doseIds = treamentDoseIds.get(doseTimepoint.getTreatmentInfo().getId());
						doseIds.add(doseTimepoint.getId());
					    Long minId = Collections.min(doseIds);
						treamentDoseIds.put(doseTimepoint.getTreatmentInfo().getId(),doseIds);
						treatmentDoses.put(doseTimepoint.getTreatmentInfo().getId(), minId);
					}
					else {
						List<Long> doseIds = new ArrayList<>();
						doseIds.add(doseTimepoint.getId());
						treamentDoseIds.put(doseTimepoint.getTreatmentInfo().getId(),doseIds);
						treatmentDoses.put(doseTimepoint.getTreatmentInfo().getId(), doseTimepoint.getId());
					}
				}
				for(Map.Entry<Long, Long> dosIds : treatmentDoses.entrySet()) {
					doseMinIds.add(dosIds.getValue());
				}*/
				List<SubjectDoseTimePoints> sdtpList = getSession().createCriteria(SubjectDoseTimePoints.class)
						.add(Restrictions.in("doseTimePoints.id", doseMinIds)).list();
				if (sdtpList != null && sdtpList.size() > 0) {
					Map<Long, Map<Long, SubjectDoseTimePoints>> tempMap1 = null;
					Map<Long, SubjectDoseTimePoints> tempMap2 = null;
					for (SubjectDoseTimePoints sdtp : sdtpList) {
						if (pwdoseMap.containsKey(sdtp.getStudySubjects().getSubjectNo())) {
							tempMap1 = pwdoseMap.get(sdtp.getStudySubjects().getSubjectNo());
							if (tempMap1.containsKey(sdtp.getPeriod().getId())) {
								tempMap2 = tempMap1.get(sdtp.getPeriod().getId());
								if (tempMap2.containsKey(sdtp.getDoseTimePoints().getTreatmentInfo().getId())) {
									tempMap2.put(sdtp.getDoseTimePoints().getTreatmentInfo().getId(), sdtp);
									tempMap1.put(sdtp.getPeriod().getId(), tempMap2);
									pwdoseMap.put(sdtp.getStudySubjects().getSubjectNo(), tempMap1);
								} else {
									if (tempMap2 == null)
										tempMap2 = new HashMap<>();
									tempMap2.put(sdtp.getDoseTimePoints().getTreatmentInfo().getId(), sdtp);
									tempMap1.put(sdtp.getPeriod().getId(), tempMap2);
									pwdoseMap.put(sdtp.getStudySubjects().getSubjectNo(), tempMap1);
								}
							} else {
								if (tempMap2 == null)
									tempMap2 = new HashMap<>();
								if (tempMap1 == null)
									tempMap1 = new HashMap<>();
								tempMap2.put(sdtp.getDoseTimePoints().getTreatmentInfo().getId(), sdtp);
								tempMap1.put(sdtp.getPeriod().getId(), tempMap2);
								pwdoseMap.put(sdtp.getStudySubjects().getSubjectNo(), tempMap1);
							}

						} else {
							tempMap2 = new HashMap<>();
							tempMap1 = new HashMap<>();
							tempMap2.put(sdtp.getDoseTimePoints().getTreatmentInfo().getId(), sdtp);
							tempMap1.put(sdtp.getPeriod().getId(), tempMap2);
							pwdoseMap.put(sdtp.getStudySubjects().getSubjectNo(), tempMap1);
						}

					}
				}

				if (subjectIds != null && subjectIds.size() > 0) {
					List<SubjectRandamization> srzList = getSession().createCriteria(SubjectRandamization.class)
							.add(Restrictions.in("period.id", periodIds)).list();
					Map<String, SubjectRandamization> subRandamizations = new HashMap<>();
					srzList.forEach((srz) -> {
						System.out.println(srz.getSubjectNo() + "_" + srz.getPeriod().getId());
						subRandamizations.put(srz.getSubjectNo() + "_" + srz.getPeriod().getId(), srz);
					});

					List<SubjectDoseTimePoints> sdtpListd = getSession().createCriteria(SubjectDoseTimePoints.class)
							.add(Restrictions.in("doseTimePoints.id", doseMinIds)).list();
					SubjectRandamization subRandamizatoin = null;
					for (SubjectDoseTimePoints sdtp : sdtpListd) {
						System.out.println(sdtp.getStudySubjects().getSubjectNo() + "_" + sdtp.getPeriod().getId());
						subRandamizatoin = subRandamizations
								.get(sdtp.getStudySubjects().getSubjectNo() + "_" + sdtp.getPeriod().getId());
						if (subRandamizatoin != null) {
							Map<Long, Map<Long, SubjectDoseTimePoints>> pdMap = new HashMap<>();
							Map<Long, SubjectDoseTimePoints> pdMapForNonTreatment = new HashMap<>();
							Map<Long, SubjectDoseTimePoints> trMap = new HashMap<>();
							trMap.put(subRandamizatoin.getTreatmentInfo().getId(), sdtp);
							pdMap.put(subRandamizatoin.getPeriod().getId(), trMap);
							dosedMap.put(sdtp.getStudySubjects().getSubjectNo(), pdMap);
							pdMapForNonTreatment.put(subRandamizatoin.getPeriod().getId(), sdtp);
							dosedWithoutTreatmentMap.put(sdtp.getStudySubjects().getSubjectNo(), pdMapForNonTreatment);
						}

					}
				}

				// Deviation messages
				dsDto = getDosingDtoDetails(studyId, collectionType);
				dsDto.setStudy(study);
				Map<String, Long> devionCodeMap = new HashMap<>();
				// Deviation messages
				if (dsDto != null) {
					List<DeviationMessage> devMsgList = dsDto.getDevMsgList();
					if (devMsgList != null && devMsgList.size() > 0) {
						for (DeviationMessage dvm : devMsgList) {
							devionCodeMap.put(dvm.getDeveloperCode(), dvm.getId());
						}
						dsDto.setDevionCodeMap(devionCodeMap);
					}
				}
				swdrList = getSession().createCriteria(SubjectWithdrawDetails.class)
						.add(Restrictions.eq("study", study)).list();
				
				if(volIds.size() > 0) {
					GlobalActivity goblaAct = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
							                       .add(Restrictions.eq("activityCode", "Cannulation")).uniqueResult();
					if(goblaAct != null) {
						List<com.covideinfo.model.StudyActivities> saList = getSession().createCriteria(com.covideinfo.model.StudyActivities.class)
				                  .add(Restrictions.eq("sm.id", studyId))
				                  .add(Restrictions.eq("activityId.id", goblaAct.getId())).list();
						if(saList != null && saList.size() > 0) {
							for(com.covideinfo.model.StudyActivities sa : saList) {
								saIds.add(sa.getId());
								saActIdsMap.put(sa.getId(), sa.getStudyPeriod().getId());
							}
						}
					}
					if(saIds.size() > 0) {
						sadataList = getSession().createCriteria(StudyActivityData.class)
								       .add(Restrictions.in("volunteerId.id", volIds))
								       .add(Restrictions.in("sutydActivity.id", saIds))
						               .setProjection(Projections.projectionList()
						            		.add(Projections.property("id"), "satudyActivityDataId")
						            		.add(Projections.property("sutydActivity.id"), "studyActivityId")
						            		.add(Projections.property("volunteerId.id"), "volunteerId"))
						               .setResultTransformer(Transformers.aliasToBean(StudyActivityDataDto.class)).list();
					}
					
							          
				}
				ctpd = new CommonTpDetails();
				ctpd.setStudy(study);
				ctpd.setSubList(subList);
				ctpd.setSubRzList(subRzList);
				ctpd.setTreatmentList(treatmentList);
				ctpd.setPwdoseMap(pwdoseMap);
				ctpd.setStudySubjectsPeriodList(studySubjectsPeriodList);
				ctpd.setDosedMap(dosedMap);
				ctpd.setDropOutSubList(dropOutSubList);
				ctpd.setDsDto(dsDto);
				ctpd.setDosedWithoutTreatmentMap(dosedWithoutTreatmentMap);
				ctpd.setSwdrList(swdrList);
				ctpd.setSubVolIdsMap(subVolIdsMap);
				ctpd.setSaActIdsMap(saActIdsMap);
				ctpd.setSadataDtoList(sadataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ctpd;
	}

	public DosingDto getDosingDtoDetails(Long studyId, String collectionStr) {
		DosingDto dsDto = null;
		List<DeviationMessage> devMsgList = null;
		StudyMaster study = null;
		List<String> typesList = new ArrayList<>();
		try {
			typesList.add(collectionStr);
			typesList.add("ALL");
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
	public BloodSamplesCollectionDto getBloodSamplesCollectionDtoDetails(Long studyId,
			Map<String, StudyPeriodMaster> stdPeriodMap) {
		BloodSamplesCollectionDto bscd = null;
		List<SampleTimePoints> samplesList = null;
		List<SubjectSampleCollectionTimePointsData> sctpdList = new ArrayList<>();
		//commented code for Code Optimization
//		List<Long> scdIds = new ArrayList<>();
		try {
			samplesList = getSession().createCriteria(SampleTimePoints.class).add(Restrictions.eq("study.id", studyId))
					.list();

			if (stdPeriodMap != null && stdPeriodMap.size() > 0) {
				List<Long> periodIds = new ArrayList<>();
				for (Map.Entry<String, StudyPeriodMaster> subp : stdPeriodMap.entrySet()) {
					//add code from Code Optimization
					periodIds.add(subp.getValue().getId());
					//commented code for Code Optimization
					/*List<SubjectSampleCollectionTimePointsData> ssctList = getSession()
							.createCriteria(SubjectSampleCollectionTimePointsData.class)
							.add(Restrictions.eq("studyPeriodMaster", subp.getValue())).list();
					if (ssctList != null && ssctList.size() > 0) {
						for (SubjectSampleCollectionTimePointsData scd : ssctList) {
							if (!scdIds.contains(scd.getId())) {
								scdIds.add(scd.getId());
								sctpdList.add(scd);
							}
						}
					}*/
				}
				
				//add code from Code Optimization
				//code Start
//				Map<Long, List<SubjectSampleCollectionTimePointsData>> periodsSubjectSampleCollectionTimePointsData = new HashMap<>();
				if(periodIds != null && periodIds.size() > 0){
					sctpdList = getSession()
							.createCriteria(SubjectSampleCollectionTimePointsData.class)
							.add(Restrictions.in("studyPeriodMaster.id", periodIds)).list();
//					for(SubjectSampleCollectionTimePointsData sd : sctpdList) {
//						sctpdList.add(sd);
//						List<SubjectSampleCollectionTimePointsData> list = periodsSubjectSampleCollectionTimePointsData.get(sd.getStudyPeriodMaster().getId());
//						if(list == null)
//							list = new ArrayList<>();
//						list.add(sd);
//						periodsSubjectSampleCollectionTimePointsData.put(sd.getStudyPeriodMaster().getId(), list);
//					}
				}
				//code End
			}
			bscd = new BloodSamplesCollectionDto();
			bscd.setSamplesList(samplesList);
			bscd.setSctpdList(sctpdList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bscd;
	}

	@Override
	public SampleCollectionSavingDto getSampleCollectionSavingDtoDetails(Long studyId, Long userId, String subNo,
			long periodId, long treatmentId, long sampleId) {
		SampleCollectionSavingDto scsDto = null;
		StudySubjects subject = null;
		StudyPeriodMaster spm = null;
		TreatmentInfo treatment = null;
		SampleTimePoints sampletp = null;
		SampleTimePoints finalsampletp = null;
		UserMaster user = null;
		StudyMaster study = null;
		try {
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class).add(Restrictions.eq("id", studyId))
					.uniqueResult();
			if (study != null) {
				subject = (StudySubjects) getSession().createCriteria(StudySubjects.class)
						.add(Restrictions.eq("study", study)).add(Restrictions.eq("subjectNo", subNo)).uniqueResult();
			}
			spm = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
					.add(Restrictions.eq("id", periodId)).uniqueResult();
			// Treatment is getting based on Randamization because vacutainer barcode having
			// timepoint id is not treatment specific
			if (subject != null && spm != null) {
				if (subject.getStdSubjectId() != null) {
					treatment = (TreatmentInfo) getSession().createCriteria(SubjectRandamization.class)
							.add(Restrictions.eq("period", spm))
							.add(Restrictions.eq("subjectNo", subject.getStdSubjectId().getSubjectNo()))
							.setProjection(Projections.property("treatmentInfo")).uniqueResult();
				} else {
					treatment = (TreatmentInfo) getSession().createCriteria(SubjectRandamization.class)
							.add(Restrictions.eq("period", spm))
							.add(Restrictions.eq("subjectNo", subject.getSubjectNo()))
							.setProjection(Projections.property("treatmentInfo")).uniqueResult();
				}

			}
			/*
			 * treatment = (TreatmentInfo) getSession().createCriteria(TreatmentInfo.class)
			 * .add(Restrictions.eq("id", treatmentId)).uniqueResult();
			 */

			sampletp = (SampleTimePoints) getSession().createCriteria(SampleTimePoints.class)
					.add(Restrictions.eq("id", sampleId)).uniqueResult();
			// Actual Treatment SampleId Record is getting based on Randamization because
			// vacutainer barcode having timepoint id is not treatment specific
			if (sampletp != null && treatment != null) {
				finalsampletp = (SampleTimePoints) getSession().createCriteria(SampleTimePoints.class)
						.add(Restrictions.eq("treatmentInfo", treatment))
						.add(Restrictions.eq("sign", sampletp.getSign()))
						.add(Restrictions.eq("timePoint", sampletp.getTimePoint()))
						.add(Restrictions.eq("vacutainerNo", sampletp.getVacutainerNo()))
						.add(Restrictions.eq("study", sampletp.getStudy())).uniqueResult();
			} else {
				if (sampletp != null)
					finalsampletp = sampletp;
			}

			user = (UserMaster) getSession().createCriteria(UserMaster.class).add(Restrictions.eq("id", userId))
					.uniqueResult();

			scsDto = new SampleCollectionSavingDto();
			scsDto.setStudy(study);
			scsDto.setSubject(subject);
			scsDto.setSpm(spm);
			scsDto.setTreatment(treatment);
			scsDto.setSampletp(finalsampletp);
			scsDto.setUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scsDto;
	}

	@Override
	public VitalTimePointsCollectionDto getVitalTimePointsCollectionDtoDetails(Long studyId,
			Map<String, StudyPeriodMaster> stdPeriodMap) {
		VitalTimePointsCollectionDto vtpcDto = null;
		List<VitalTimePoints> vtpList = null;
		List<SubjectVitalTimePointsData> svtpDataList = new ArrayList<>();
		try {
			vtpList = getSession().createCriteria(VitalTimePoints.class).add(Restrictions.eq("study.id", studyId))
					.list();
			List<Long> ids = new ArrayList<>();
			if (stdPeriodMap != null && stdPeriodMap.size() > 0) {
				for (Map.Entry<String, StudyPeriodMaster> subp : stdPeriodMap.entrySet()) {
					List<SubjectVitalTimePointsData> svtpdList = getSession()
							.createCriteria(SubjectVitalTimePointsData.class)
							.add(Restrictions.eq("period", subp.getValue())).add(Restrictions.eq("study.id", studyId))
							.list();
					if (svtpdList != null && svtpdList.size() > 0) {
						for (SubjectVitalTimePointsData svtp : svtpdList) {
							if (!ids.contains(svtp.getId())) {
								svtpDataList.add(svtp);
								ids.add(svtp.getId());
							}
						}
					}
				}
			}
			vtpcDto = new VitalTimePointsCollectionDto();
			vtpcDto.setVtpList(vtpList);
			vtpcDto.setSvtpData(svtpDataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vtpcDto;
	}

	@Override
	public VitalCollectionSavingDto getVitalCollectionSavingDtoDetails(long studyId, String subjectNo, Long periodId,
			Long userId, Long timePointPk) {
		VitalCollectionSavingDto vcDto = null;
		StudyMaster study = null;
		UserMaster user = null;
		StudySubjects subject = null;
		VitalTimePoints vac = null;
		VitalTimePoints finalvac = null;
		StudyPeriodMaster period = null;
		TreatmentInfo treatment = null;
		try {
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class).add(Restrictions.eq("id", studyId))
					.uniqueResult();

			user = (UserMaster) getSession().createCriteria(UserMaster.class).add(Restrictions.eq("id", userId))
					.uniqueResult();

			period = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
					.add(Restrictions.eq("id", periodId)).uniqueResult();

			vac = (VitalTimePoints) getSession().createCriteria(VitalTimePoints.class)
					.add(Restrictions.eq("id", timePointPk)).uniqueResult();
			if (study != null) {
				subject = (StudySubjects) getSession().createCriteria(StudySubjects.class)
						.add(Restrictions.eq("subjectNo", subjectNo)).add(Restrictions.eq("study", study))
						.uniqueResult();
			}
			// Treatment is getting based on Randamization because vial barcode having
			// timepoint id is not treatment specific
			if (subject != null && period != null) {
				if (subject.getStdSubjectId() == null) {
					treatment = (TreatmentInfo) getSession().createCriteria(SubjectRandamization.class)
							.add(Restrictions.eq("period", period))
							.add(Restrictions.eq("subjectNo", subject.getSubjectNo()))
							.setProjection(Projections.property("treatmentInfo")).uniqueResult();
				} else {
					treatment = (TreatmentInfo) getSession().createCriteria(SubjectRandamization.class)
							.add(Restrictions.eq("period", period))
							.add(Restrictions.eq("subjectNo", subject.getStdSubjectId().getSubjectNo()))
							.setProjection(Projections.property("treatmentInfo")).uniqueResult();
				}
			}
			/*
			 * treatment = (TreatmentInfo) getSession().createCriteria(TreatmentInfo.class)
			 * .add(Restrictions.eq("id", treatmentId)).uniqueResult();
			 */

			// Actual Treatment SampleId Record is getting based on Randamization because
			// vial barcode having timepoint id is not treatment specific
			if (vac != null && treatment != null) {
				finalvac = (VitalTimePoints) getSession().createCriteria(VitalTimePoints.class)
						.add(Restrictions.eq("sign", vac.getSign())).add(Restrictions.eq("treatmentInfo", treatment))
						.add(Restrictions.eq("timePoint", vac.getTimePoint()))
						.add(Restrictions.eq("study", vac.getStudy())).uniqueResult();
			} else {
				// For Standby subjects
				if (vac != null && treatment == null) {
					finalvac = vac;
				}
			}
			vcDto = new VitalCollectionSavingDto();
			vcDto.setStudy(study);
			vcDto.setUser(user);
			vcDto.setSubject(subject);
			vcDto.setPeriod(period);
			vcDto.setVac(finalvac);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return vcDto;
	}

	@Override
	public CentrificationDetailsDto getCollectedSamplesList(Long studyId) {
		CentrificationDetailsDto crdd = null;
		List<SubjectSampleCollectionTimePointsData> ssctpdList = null;
		List<Long> spmIdsList = null;
		StudyMaster sm = null;
		try {
			sm = (StudyMaster) getSession().createCriteria(StudyMaster.class).add(Restrictions.eq("id", studyId))
					.uniqueResult();

			spmIdsList = getSession().createCriteria(StudyPeriodMaster.class).add(Restrictions.eq("study.id", studyId))
					.setProjection(Projections.property("id")).list();
			if (spmIdsList != null && spmIdsList.size() > 0) {
				ssctpdList = getSession().createCriteria(SubjectSampleCollectionTimePointsData.class)
						.add(Restrictions.in("studyPeriodMaster.id", spmIdsList)).list();
			}
			crdd = new CentrificationDetailsDto();
			crdd.setSm(sm);
			crdd.setSsctpdList(ssctpdList);
			crdd.setSpmIdsList(spmIdsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return crdd;
	}

	@Override
	public VilaRackDtoDetails getVilaRackDtoDetails(Long studyId, Long userId, List<Long> tpIds, List<String> subNos,
			Long rackId, long periodId) {
		VilaRackDtoDetails vrDto = null;
		UserMaster user = null;
		StudyPeriodMaster periodpojo = null;
		Deepfreezer deepreezer = null;
		StudyMaster study = null;
		List<StudySubjects> subjectsList = null;
		List<SampleTimePoints> sampList = null;
		try {
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class).add(Restrictions.eq("id", studyId))
					.uniqueResult();

			user = (UserMaster) getSession().createCriteria(UserMaster.class).add(Restrictions.eq("id", userId))
					.uniqueResult();
			if (study != null) {
				periodpojo = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
						.add(Restrictions.eq("id", periodId)).uniqueResult();

				deepreezer = (Deepfreezer) getSession().createCriteria(Deepfreezer.class)
						.add(Restrictions.eq("id", rackId)).uniqueResult();

				subjectsList = getSession().createCriteria(StudySubjects.class).add(Restrictions.eq("study", study))
						.add(Restrictions.in("subjectNo", subNos)).list();

				sampList = getSession().createCriteria(SampleTimePoints.class).add(Restrictions.in("id", tpIds)).list();
			}

			vrDto = new VilaRackDtoDetails();
			vrDto.setStudy(study);
			vrDto.setUser(user);
			vrDto.setPeriodpojo(periodpojo);
			vrDto.setDeepreezer(deepreezer);
			vrDto.setSubjectsList(subjectsList);
			vrDto.setSampList(sampList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vrDto;
	}

	@Override
	public Map<Long, GlobalparameterFromDto> getGlobalParameterDetails(Long languageId,
			Map<Long, List<Long>> vptParamMap, String activityCode, List<Long> allPramIds) {
		Map<Long, GlobalparameterFromDto> gpDtoMap = new HashMap<>();
		GlobalparameterFromDto gpfDto = null;
		List<LanguageSpecificGlobalParameterDetails> gpList = null;
		LanguageSpecificGlobalActivityDetails ga = null;
		GlobalActivity act = null;
		Map<Long, LanguageSpecificGlobalParameterDetails> paramMap = new HashMap<>();
		try {
			act = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("activityCode", activityCode)).uniqueResult();

			ga = (LanguageSpecificGlobalActivityDetails) getSession()
					.createCriteria(LanguageSpecificGlobalActivityDetails.class)
					.add(Restrictions.eq("globalActivity", act)).add(Restrictions.eq("inlagId.id", languageId))
					.uniqueResult();

			gpList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
					.add(Restrictions.eq("inlagId.id", languageId)).add(Restrictions.in("parameterId.id", allPramIds))
					.list();

			if (gpList.size() > 0) {
				for (LanguageSpecificGlobalParameterDetails lsgp : gpList) {
					paramMap.put(lsgp.getParameterId().getId(), lsgp);
				}
			}
			if (vptParamMap.size() > 0) {
				for (Map.Entry<Long, List<Long>> pids : vptParamMap.entrySet()) {
					List<GlobalParameterDetailsDto> gpdDtoList = new ArrayList<>();
					GlobalParameterDetailsDto gpDto = new GlobalParameterDetailsDto();
					List<ParameterFormDataDto> parameterDto = new ArrayList<>();
					for (Long pid : pids.getValue()) {
						LanguageSpecificGlobalParameterDetails lsgp = paramMap.get(pid);
						if (lsgp != null) {
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
							pfDto.setMandatory(lsgp.getParameterId().isMandatory());
							parameterDto.add(pfDto);
						}
					}
					gpDto.setActivityId(ga.getGlobalActivity().getId());
					gpDto.setActivityName(ga.getName());
					gpDto.setParameterDto(parameterDto);
					gpdDtoList.add(gpDto);

					gpfDto = new GlobalparameterFromDto();
					gpfDto.setGpdDtoList(gpdDtoList);
					gpDtoMap.put(pids.getKey(), gpfDto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gpDtoMap;
	}

	/*
	 * All the Instrument with instrument type match
	 * @see com.covideinfo.clinical.dao.ClinicalDao#getInstrumentListWithType(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Instrument> getInstrumentListWithType(String type) {
		List<Long> instrumentType = getSession().createCriteria(InstrumentType.class)
				.add(Restrictions.eq("instrumentType", type)).setProjection(Projections.property("id")).list();
		List<Instrument> list = new ArrayList<>();
		if (instrumentType.size() > 0) {
			list = getSession().createCriteria(Instrument.class)
					.add(Restrictions.in("instrumentType.id", instrumentType)).list();
		}
		return list;
	}
	@Override
	public PlannedTimeDetailsDto getPlannedTimeDetailsDtoDetails(Long studyId, Map<String, StudyPeriodMaster> spmMap,
			String projectNo) {
		PlannedTimeDetailsDto ptdDto = null;
		Date reportingDate = null;
		Map<Long, Date> reportingMap = new HashMap<>();
		StatusMaster approvedStatus = null;
		Date plannedDate = null;
		Map<Long, String> priodNamesMap = new HashMap<>();
		Map<String, Long> priodIdsMap = new HashMap<>();
		Date doseDate = null;
		Map<Long, Date> periodWiseDoseMap = new HashMap<>();
		Map<Long, Integer> periodNoIdsMap = new HashMap<>();
		Map<Integer, Long> periodNoMap = new HashMap<>();
		Map<String, Map<Long, Date>> subjectDoseMap = new HashMap<>();//subjectNo, periodId, Date
		List<Long> subjectIdsList = null;
		Set<Long> maxSubjectDoseIds = new HashSet<>();
		List<StudyPeriodMaster> spmList = null;
		try {
			approvedStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
			           .add(Restrictions.eq("statusCode", StudyStatus.APPROVED.toString())).uniqueResult();
			
			subjectIdsList = getSession().createCriteria(StudySubjects.class)
                    .add(Restrictions.eq("study.id", studyId))
                    .setProjection(Projections.property("id")).list();
			
			spmList = getSession().createCriteria(StudyPeriodMaster.class)
					       .add(Restrictions.eq("study.id", studyId)).list();
			
			if (spmMap != null && spmMap.size() > 0) {
				for (Map.Entry<String, StudyPeriodMaster> subp : spmMap.entrySet()) {
					/*if(subp.getValue().getPeriodNo() == 1) {
						reportingDate = (Date) getSession().createCriteria(StudyVolunteerReporting.class)
								       .add(Restrictions.eq("sm.id", studyId))
								       .add(Restrictions.eq("period.id", subp.getValue().getId()))
								       .setProjection(Projections.min("createdOn")).uniqueResult();
					}else {
						reportingDate = (Date) getSession().createCriteria(StudyVolunteerReporting.class)
							       .add(Restrictions.eq("sm.id", studyId))
							       .add(Restrictions.eq("period.id", subp.getValue().getId()))
							       .setProjection(Projections.min("updatedOn")).uniqueResult();
					}
					reportingMap.put(subp.getValue().getId(), reportingDate);*/
					/*priodNamesMap.put(subp.getValue().getId(), subp.getValue().getPeriodName());
					priodIdsMap.put(subp.getValue().getPeriodName(), subp.getValue().getId());*/
				
						/*doseDate = (Date) getSession().createCriteria(SubjectDoseTimePoints.class)
				                 .add(Restrictions.eq("period.id", subp.getValue().getId()))
				                 .setProjection(Projections.max("actualTime")).uniqueResult();
						periodWiseDoseMap.put(subp.getValue().getId(), doseDate);
						periodNoIdsMap.put(subp.getValue().getId(), subp.getValue().getPeriodNo());
						periodNoMap.put(subp.getValue().getPeriodNo(), subp.getValue().getId());
					
						 */
						
						if(subjectIdsList.size() > 0) {
							/*List<Long> maxDoseIds = getSession().createCriteria(SubjectDoseTimePoints.class)
				                                        .add(Restrictions.eq("period.id", subp.getValue().getId()))
				                                        .add(Restrictions.in("studySubjects.id", subjectIdsList))
				                                        .setProjection(Projections.projectionList()
				                                        		.add(Projections.min("id"))).list();*/
									
//						    List<Long> maxSubjectDoseIds = new ArrayList<>();
							List<Object[]> dosMinIds = null;
							dosMinIds =getSession().createCriteria(SubjectDoseTimePoints.class)
                                        .add(Restrictions.eq("period.id", subp.getValue().getId()))
                                        .add(Restrictions.in("studySubjects.id", subjectIdsList))
				                        .setProjection(Projections.projectionList().add(Projections.min("id")).add(Projections.groupProperty("studySubjects.id")))
				                        .list();
				                for(Object[] arr : dosMinIds) {
				                	maxSubjectDoseIds.add((Long)arr[0]);
				                }
						}
						
				}
			}
			Long projectId = (Long) getSession().createCriteria(Projects.class).add(Restrictions.eq("projectNo", projectNo))
					.add(Restrictions.eq("status.id", approvedStatus.getId()))
					.setProjection(Projections.property("projectId")).uniqueResult();
			if(projectId != null) {
				try {
					plannedDate= (Date) getSession().createCriteria(DosingInfo.class)
							.add(Restrictions.eq("projects.projectId", projectId)).setProjection(Projections.property("dosingDate")).uniqueResult();
				}catch (Exception e) {
					e.printStackTrace();
				}			
			}
			if(maxSubjectDoseIds.size() > 0) {
				List<SubjectDoseTimePoints> subDoseList = getSession().createCriteria(SubjectDoseTimePoints.class)
															.add(Restrictions.in("id", maxSubjectDoseIds)).list();
				if(subDoseList.size() > 0) {
					Map<Long, Date> tempMap = null;
					for(SubjectDoseTimePoints sdtp : subDoseList) {
						if(subjectDoseMap.containsKey(sdtp.getStudySubjects().getSubjectNo())) {
							tempMap = subjectDoseMap.get(sdtp.getStudySubjects().getSubjectNo());
							tempMap.put(sdtp.getPeriod().getId(), sdtp.getActualTime());
							subjectDoseMap.put(sdtp.getStudySubjects().getSubjectNo(), tempMap);
						}else {
							tempMap = new HashMap<>();
							tempMap.put(sdtp.getPeriod().getId(), sdtp.getActualTime());
							subjectDoseMap.put(sdtp.getStudySubjects().getSubjectNo(), tempMap);
						}
					}
				}
			}
			if(spmList != null && spmList.size() > 0) {
				Set<Long> spmIds = new HashSet<>();
				for(StudyPeriodMaster spm : spmList) {
				    spmIds.add(spm.getId());
				    priodNamesMap.put(spm.getId(), spm.getPeriodName());
					priodIdsMap.put(spm.getPeriodName(), spm.getId());
					periodNoIdsMap.put(spm.getId(), spm.getPeriodNo());
					periodNoMap.put(spm.getPeriodNo(), spm.getId());
					
					if(spm.getPeriodNo() == 1) {
						reportingDate = (Date) getSession().createCriteria(StudyVolunteerReporting.class)
								       .add(Restrictions.eq("sm.id", studyId))
								       .add(Restrictions.eq("period.id", spm.getId()))
								       .setProjection(Projections.min("createdOn")).uniqueResult();
					}else {
						reportingDate = (Date) getSession().createCriteria(StudyVolunteerReporting.class)
							       .add(Restrictions.eq("sm.id", studyId))
							       .add(Restrictions.eq("period.id", spm.getId()))
							       .setProjection(Projections.min("updatedOn")).uniqueResult();
					}
					reportingMap.put(spm.getId(), reportingDate);
					
					Date subDoseDate = (Date) getSession().createCriteria(SubjectDoseTimePoints.class)
                            .add(Restrictions.eq("period.id", spm.getId()))
                            .setProjection(Projections.max("actualTime")).uniqueResult();
					if(subDoseDate != null) {
						periodWiseDoseMap.put(spm.getId(), subDoseDate);
					}
				} 
			}
			
			ptdDto = new PlannedTimeDetailsDto();
			ptdDto.setPlannedDate(plannedDate);
			ptdDto.setRepotingDateMap(reportingMap);
			ptdDto.setPriodNamesMap(priodNamesMap);
			ptdDto.setPeriodWiseDoseMap(periodWiseDoseMap);
			ptdDto.setPriodIdsMap(priodIdsMap);
			ptdDto.setPeriodNoIdsMap(periodNoIdsMap);
			ptdDto.setPeriodNoMap(periodNoMap);
			ptdDto.setSubjectDoseMap(subjectDoseMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ptdDto;
	}

}
