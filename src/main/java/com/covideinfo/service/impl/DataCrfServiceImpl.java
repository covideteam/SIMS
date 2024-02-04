package com.covideinfo.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.DataCrfDao;
import com.covideinfo.datCrf.HeaderAndFooterDataCrf;
import com.covideinfo.datCrf.HeaderAndFooterForAvanDataCrf;
import com.covideinfo.datCrf.SinglePdfConversionForDataCrf;
import com.covideinfo.dto.ActivityEntryDetailsDto;
import com.covideinfo.dto.CrfDataActivityDto;
import com.covideinfo.dto.CrfDataDto;
import com.covideinfo.dto.DataCrfDetailsPageDto;
import com.covideinfo.dto.DataCrfDtoDetails;
import com.covideinfo.dto.RecannulationDataDto;
import com.covideinfo.dto.SampleInfoDto;
import com.covideinfo.dto.TimeDto;
import com.covideinfo.dto.UserMasterDto;
import com.covideinfo.dto.VitalParameterDto;
import com.covideinfo.model.ActivityDraftReviewAudit;
import com.covideinfo.model.ApplicationConfiguration;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.DosingInfoDetails;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StaticActivityDataDetails;
import com.covideinfo.model.StaticActivityDetails;
import com.covideinfo.model.StaticActivityValueDetails;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyCheckInActivityDataDetails;
import com.covideinfo.model.StudyCheckOutActivityDataDetails;
import com.covideinfo.model.StudyExecutionActivityDataDetails;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.SubjectDoseTimePointParametersData;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectVitalParametersData;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.model.SubjectWithdrawDetails;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.VitalTimePoints;
import com.covideinfo.pdf.report.HeaderAndFooter;
import com.covideinfo.pdf.report.HeaderAndFooterForAdvFirstCoverPage;
import com.covideinfo.service.DataCrfService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

@Service("dataCrfService")
public class DataCrfServiceImpl extends PdfPageEventHelper implements DataCrfService {
	
  @Autowired
  DataCrfDao dataCrfDao;

@Override
public DataCrfDetailsPageDto getDataCrfDetailsPageDtoDetails() {
	return dataCrfDao.getDataCrfDetailsPageDtoDetails();
}

@Override
public DataCrfDetailsPageDto getStudyCrfDataDetails(Long studyId, Long languageId) {
	return dataCrfDao.getStudyCrfDataDetails(studyId, languageId);
}

@Override
public DataCrfDtoDetails getStudyCrfDataDtoDetails(Long studyId, Long langId, Long userId) {
	CrfDataDto crfDto = null;
	DataCrfDtoDetails dcdDto = null;
	
	List<LanguageSpecificGlobalActivityDetails> lsgadList = null;
	List<LanguageSpecificGlobalParameterDetails> lsgpdList = null;
	List<LanguageSpecificValueDetails> lsgvList = null;
	List<StudySubjectPeriods> subList = null;
	List<StudyPeriodMaster> spmList = null;
	List<StudyCheckInActivityDataDetails> stdChkinList = null;
	List<StudyCheckOutActivityDataDetails> stdChkoutList = null; 
	List<StudyExecutionActivityDataDetails> stdExList = null;
	List<SubjectDoseTimePoints> sdtpList = null;
	List<SubjectDoseTimePointParametersData> sdtpparamdList = null;
	List<SubjectVitalTimePointsData> stdVitalList = null;
	List<SubjectVitalParametersData> stdVitalParamList = null;
	List<SubjectSampleCollectionTimePointsData> subSapColTpDataList = null;
	List<SubjectMealsTimePointsData> subMealTpDataList = null;
	StudyMaster study = null;
	ApplicationConfiguration appConfig = null;
	UserMaster user = null;
	List<DefaultActivitys> defalutActList = null;
	List<StudyTreatmentWiseSubjects> stwSubjects = null;
	
	Map<Long, LanguageSpecificGlobalActivityDetails> gaMap = new HashMap<>(); //actid, lspactivity
	Map<Long, LanguageSpecificGlobalParameterDetails> gpMap = new HashMap<>();// gpid, lsgparamter
	Map<Long, LanguageSpecificValueDetails> gvMap = new HashMap<>();//gloablValueId, languageSpecificvalues
	Map<Long, StudySubjects> subMap = new HashMap<>();//SubjectId, SubjectPojo
	Map<Integer, StudyPeriodMaster> spmMap = new HashMap<>();// periodId, periodPojo
	Map<Long, Map<Long, Map<Long, List<StudyCheckInActivityDataDetails>>>> chickInMap = new HashMap<>();//subjectId, periodId,  activityId, List of StudyCheckInActivityDataDetails
	Map<Long, Map<Long, Map<Long, List<StudyCheckOutActivityDataDetails>>>> checkOutMap = new HashMap<>();//subjectId, periodId,  activityId, List of StudyCheckInActivityDataDetails
	Map<Long, Map<Long, Map<Long, List<StudyExecutionActivityDataDetails>>>> stdexeMap = new HashMap<>();//subjectId, periodId,  activityId, List of StudyCheckInActivityDataDetails
	Map<Long, Map<Long, Map<Long, List<SubjectDoseTimePoints>>>> sdtpMap = new HashMap<>(); //SubjectId, Period, treatment, dosetimepoint
	Map<Long, Map<Long, Map<Long, List<SubjectDoseTimePointParametersData>>>> doseParamMap = new HashMap<>();//SubjectId , Period, treatment, doseparameters
	/*Map<Long, Map<Long, List<SubjectVitalTimePointsData>>> vitalMap = new HashMap<>(); //SubjectId, Period, treatment, Listst of vitals
	Map<Long, Map<Long, List<SubjectVitalParametersData>>> vitalParamMap = new HashMap<>(); //SubjectId, Period, treatment, List of Vitalparameters
*/	Map<Long, Map<Long, Map<Long, Map<String, SubjectVitalTimePointsData>>>> vitalMap = new HashMap<>(); //SubjectId, Period, timepointId,position, vtalDataRecord
	Map<Long, Map<Long, List<SubjectVitalParametersData>>> vitalParamMap = new HashMap<>(); //vitalDataId, vtpId , List of VitalparametersPojo
	Map<Long, Map<Long, List<SubjectSampleCollectionTimePointsData>>> sampCollMap = new HashMap<>();//SubjectId, Period, treatment, List of samplecollections
	Map<Long, Map<Long, Map<Long, List<SubjectMealsTimePointsData>>>> mealsCollMap = new HashMap<>(); //SubjectId, period, treatment, List of Mealscolleciton
	Map<Long, List<DoseTimePoints>> doseMap = new HashMap<>(); //periodId, treatmentId, doseList
	Map<Long, List<SampleTimePoints>> samplesMap = new HashMap<>(); //periodId, treatmentId, samplesList
	Map<Long, List<MealsTimePoints>> mealsMap = new HashMap<>();//periodId, treatmentid, mealsList
	Map<Long, List<VitalTimePoints>> vtpMap = new HashMap<>(); //periodId, treatmentId, vitalsList
	Map<Long, DefaultActivitys> defalutActMap = new HashMap<>();
	Map<Long, Map<Long, TreatmentInfo>> subwtrMap = new HashMap<>();//ReportingId, periodId, treatment
	Map<Long, StudyVolunteerReporting> stdVolMap = new HashMap<>();
	Map<Long, Map<Long, Set<Long>>> sactMap = new HashMap<>(); //periodId, treatmentId, List of activityIds
	Map<Long, Map<Long, Map<Long, List<Long>>>> sactParamMap = new HashMap<>(); // period, treatmentId, actId,  List of parameterids
	Map<String, StudySubjects> stdSubjetsMap = new HashMap<>();//subjectNo, SubjectPojo
	List<StudyVolunteerReporting> stdVolList = null;
	Map<Long, List<StaticActivityDataDetails>> staticDataActMap = new HashMap<>();
	Map<Long, List<StaticActivityValueDetails>> staticActValsMap = new HashMap<>();
	Map<String, List<StaticActivityDetails>> saticActMap = new HashMap<>();
	Map<Long, Map<Long, SubjectWithdrawDetails>> subjectWithDrawMap = new HashMap<>();//volId, periodId,  SubjectWithdrawDetails
	List<StaticActivityDataDetails> saddList = null;
	List<StaticActivityValueDetails> savdList = null;
	List<StaticActivityDetails> staticActList = null;
	List<SubjectWithdrawDetails> subjWithDarawList = null;
	List<MealsTimePoints> mealsList = null;
	List<DoseTimePoints> doseList = null;
	List<SampleTimePoints> samplesList = null;
    List<VitalTimePoints> vitalList = null;
    Projects project = null;
    List<StudyActivities> studyActList = null;
    List<StudyActivityParameters> studyActParamList = null;
    Map<String, StudySubjects> subjectsMap = new HashMap<>();
    Map<Long, TreatmentInfo> treatmentsMap = new HashMap<>();
    List<TreatmentInfo> treatmentsList = null;
    Long treatmentMinId = null;
    ActivityDraftReviewAudit adrPojo = null;
    List<DosingInfoDetails> druginfList = null;
    Map<Long, Map<Long, Map<Long, ActivityEntryDetailsDto>>> actPerformedMap = new HashMap<>(); // volId, periodId, activityId, entryDetails 
    Map<String, DosingInfoDetails> drugInfMap = new HashMap<>(); //RandomizationCode, DosingInfoDetails Pojo
    List<ProjectsDetails> sampleProList = null;
    Map<Long, SampleInfoDto> sampleInfoMap = new HashMap<>();//treatmentId, sampleInfodetailsDto
    Map<Long, Map<Long, Map<Long, List<RecannulationDataDto>>>> rcDataWithTpMap = new HashMap<>();//volunteerId, PeriodId, sampleId, RecannulationDataDetailsDto List
    Map<Long, Map<Long, List<RecannulationDataDto>>> rcDataWithoutTpMap = new HashMap<>(); //VolunteerId, PeriodId, RecannulationDataDetailsDto List
    List<RecannulationDataDto> recdDtoList = null;
    Map<String, String> userNamesMap = new HashMap<>();// username, userFullName
    List<UserMasterDto> usersList = null;
    try {
		crfDto = dataCrfDao.getStudyCrfDataDtoDetails(studyId, langId, userId);
		if(crfDto != null) {
			study = crfDto.getStudy();
			appConfig = crfDto.getAppConfig();
			user = crfDto.getUser();
			lsgadList = crfDto.getLsgadList();
			lsgpdList = crfDto.getLsgpdList();
			lsgvList = crfDto.getLsgvList();
			subList = crfDto.getSubList();
			spmList = crfDto.getSpmList();
			stdChkinList = crfDto.getStdChkinList();
			stdChkoutList = crfDto.getStdChkoutList();
			stdExList = crfDto.getStdExList();
			sdtpList = crfDto.getSdtpList();
			sdtpparamdList = crfDto.getSdtpparamdList();
			stdVitalList = crfDto.getStdVitalList();
			stdVitalParamList= crfDto.getStdVitalParamList();
			subSapColTpDataList = crfDto.getSubSapColTpDataList();
			subMealTpDataList= crfDto.getSubMealTpDataList();
			defalutActList = crfDto.getDefalutActList();
			stdVolList = crfDto.getStdVolList();
			stwSubjects = crfDto.getStwSubjects();
			mealsList = crfDto.getMealsList();
			doseList = crfDto.getDoseList();
			samplesList = crfDto.getSamplesList();
			vitalList = crfDto.getVitalList();
			project = crfDto.getProject();
			studyActList = crfDto.getStudyActList();
			studyActParamList = crfDto.getStudyActParamList();
			saddList = crfDto.getSaddList();
			savdList = crfDto.getSavdList();
			staticActList = crfDto.getStaticActList();
			subjWithDarawList = crfDto.getSubjWithDarawList();
			treatmentsList = crfDto.getTreatmentsList();
			adrPojo = crfDto.getAdrPojo();
			druginfList = crfDto.getDruginfList();
			sampleProList = crfDto.getSampleProList();
			recdDtoList = crfDto.getRecdDtoList();
			usersList = crfDto.getUsersList();
		}
			Map<Long, Set<Long>> satTempMap = null;
			Set<Long> actIds = null;
			if(studyActList != null && studyActList.size() > 0) {
				for(StudyActivities sa : studyActList) {
					if(sactMap.containsKey(sa.getStudyPeriod().getId())) {
						satTempMap = sactMap.get(sa.getStudyPeriod().getId());
						if(satTempMap.containsKey(sa.getTreatment().getId())) {
							actIds = satTempMap.get(sa.getTreatment().getId());
							actIds.add(sa.getActivityId().getId());
							satTempMap.put(sa.getTreatment().getId(), actIds);
							sactMap.put(sa.getStudyPeriod().getId(), satTempMap);
						}else {
							actIds = new HashSet<>();
							actIds.add(sa.getActivityId().getId());
							satTempMap.put(sa.getTreatment().getId(), actIds);
							sactMap.put(sa.getStudyPeriod().getId(), satTempMap);
						}
					}else {
						actIds = new HashSet<>();
						satTempMap = new HashMap<>();
						actIds.add(sa.getActivityId().getId());
						satTempMap.put(sa.getTreatment().getId(), actIds);
						sactMap.put(sa.getStudyPeriod().getId(), satTempMap);
					}
				}
			}
			
			Map<Long, Map<Long, List<Long>>> sapTempMap = null;
			Map<Long, List<Long>> sapMap = null;
			List<Long> sapList = null;
			if(studyActParamList != null && studyActParamList.size() > 0) {
				for(StudyActivityParameters sap : studyActParamList) {
					if(sactParamMap.containsKey(sap.getStudyActivity().getStudyPeriod().getId())) {
						sapTempMap = sactParamMap.get(sap.getStudyActivity().getStudyPeriod().getId());
						if(sapTempMap.containsKey(sap.getStudyActivity().getTreatment().getId())) {
							sapMap = sapTempMap.get(sap.getStudyActivity().getTreatment().getId());
							if(sapMap.containsKey(sap.getStudyActivity().getActivityId().getId())) {
								sapList = sapMap.get(sap.getStudyActivity().getActivityId().getId());
								sapList.add(sap.getParameterId().getId());
								sapMap.put(sap.getStudyActivity().getActivityId().getId(), sapList);
								sapTempMap.put(sap.getStudyActivity().getTreatment().getId(), sapMap);
								sactParamMap.put(sap.getStudyActivity().getStudyPeriod().getId(), sapTempMap);
							}else {
								sapList = new ArrayList<>();
								sapList.add(sap.getParameterId().getId());
								sapMap.put(sap.getStudyActivity().getActivityId().getId(), sapList);
								sapTempMap.put(sap.getStudyActivity().getTreatment().getId(), sapMap);
								sactParamMap.put(sap.getStudyActivity().getStudyPeriod().getId(), sapTempMap);
							}
						}else {
							sapList = new ArrayList<>();
							sapMap = new HashMap<>();
							sapList.add(sap.getParameterId().getId());
							sapMap.put(sap.getStudyActivity().getActivityId().getId(), sapList);
							sapTempMap.put(sap.getStudyActivity().getTreatment().getId(), sapMap);
							sactParamMap.put(sap.getStudyActivity().getStudyPeriod().getId(), sapTempMap);
						}
						
						
					}else {
						sapList = new ArrayList<>();
						sapMap = new HashMap<>();
						sapTempMap = new HashMap<>();
						sapList.add(sap.getParameterId().getId());
						sapMap.put(sap.getStudyActivity().getActivityId().getId(), sapList);
						sapTempMap.put(sap.getStudyActivity().getTreatment().getId(), sapMap);
						sactParamMap.put(sap.getStudyActivity().getStudyPeriod().getId(), sapTempMap);
					}
				}
			}
			
			if(lsgadList != null && lsgadList.size() > 0) {
				for(LanguageSpecificGlobalActivityDetails lsga : lsgadList) {
					gaMap.put(lsga.getGlobalActivity().getId(), lsga);
				}
			}
			if(lsgpdList != null && lsgpdList.size() > 0) {
				for(LanguageSpecificGlobalParameterDetails lsgp : lsgpdList) {
					gpMap.put(lsgp.getParameterId().getId(), lsgp);
				}
			}
			
			if(lsgvList != null && lsgvList.size() > 0) {
				for(LanguageSpecificValueDetails lsgv : lsgvList) {
					gvMap.put(lsgv.getGlobalValId().getId(), lsgv);
				}
			}
			if(subList!= null && subList.size() > 0) {
				for(StudySubjectPeriods sub : subList) {
					if(!subMap.containsKey(sub.getSubject().getReportingId().getId())) {
						stdSubjetsMap.put(sub.getSubject().getSubjectNo(), sub.getSubject());
						subMap.put(sub.getSubject().getReportingId().getId(), sub.getSubject());
						subjectsMap.put(sub.getSubject().getSubjectNo(), sub.getSubject());
					}
				}
			}
			if(spmList != null && spmList.size() > 0) {
				for(StudyPeriodMaster spm : spmList) {
					spmMap.put(spm.getPeriodNo(), spm);
				}
			}
			if(defalutActList != null && defalutActList.size() > 0) {
				for(DefaultActivitys da : defalutActList) {
					defalutActMap.put(da.getActivity().getId(), da);
				}
			}
			if(stdVolList != null && stdVolList.size() > 0) {
				for(StudyVolunteerReporting svr : stdVolList) {
					stdVolMap.put(svr.getId(), svr);
				}
			}
			Map<String, Map<Long, TreatmentInfo>> subtpMap = new HashMap<>();//subjectNo, periodId, TreatmentInfo
			 Map<Long, TreatmentInfo> ptrTempMap = null;
			if(stwSubjects != null && stwSubjects.size() > 0) {
				for(StudyTreatmentWiseSubjects stws : stwSubjects) {
					String subStr = stws.getSubjects();
					if(subStr != null && !subStr.equals("")) {
						String[] tempArr = subStr.split("\\,");
						if(tempArr.length > 0) {
							for(String st : tempArr) {
								if(subtpMap.containsKey(st)) {
									ptrTempMap = subtpMap.get(st);
									if(ptrTempMap.containsKey(stws.getPeriod().getId())) {
										ptrTempMap.put(stws.getPeriod().getId(), stws.getTreatment());
										subtpMap.put(st, ptrTempMap);
									}else {
										ptrTempMap.put(stws.getPeriod().getId(), stws.getTreatment());
										subtpMap.put(st, ptrTempMap);
									}
								}else {
									ptrTempMap = new HashMap<>();
									ptrTempMap.put(stws.getPeriod().getId(), stws.getTreatment());
									subtpMap.put(st, ptrTempMap);
								}
							}
						}
					}
				}
			}
			if(subjectsMap != null && subjectsMap.size() > 0) {
				for(Map.Entry<String, StudySubjects> sub : subjectsMap.entrySet()) {
					StudySubjects subPojo = sub.getValue();
					if(subPojo != null) {
						String subNo = "";
						if(subPojo.getStdSubjectId() != null)
							subNo = subPojo.getStdSubjectId().getSubjectNo();
						else subNo = subPojo.getSubjectNo();
						subwtrMap.put(subPojo.getReportingId().getId(), subtpMap.get(subNo));
					}
				}
			}
			List<MealsTimePoints> mtList = null;
			if(mealsList != null && mealsList.size() > 0) {
				for(MealsTimePoints mtp : mealsList) {
					if(mealsMap.containsKey(mtp.getTreatmentInfo().getId())) {
						mtList = mealsMap.get(mtp.getTreatmentInfo().getId());
						mtList.add(mtp);
						mealsMap.put(mtp.getTreatmentInfo().getId(), mtList);
					}else {
						mtList = new ArrayList<>();
						mtList.add(mtp);
						mealsMap.put(mtp.getTreatmentInfo().getId(), mtList);
					}
				}
			}
			List<DoseTimePoints> dosList = null;
			if(doseList != null && doseList.size() > 0) {
				for(DoseTimePoints dtp : doseList) {
					if(doseMap.containsKey(dtp.getTreatmentInfo().getId())) {
						dosList = doseMap.get(dtp.getTreatmentInfo().getId());
						dosList.add(dtp);
						doseMap.put(dtp.getTreatmentInfo().getId(), dosList);
					}else {
						dosList = new ArrayList<>();
						dosList.add(dtp);
						doseMap.put(dtp.getTreatmentInfo().getId(), dosList);
					}
				}
			}
			List<SampleTimePoints> stpList = null;
			if(samplesList != null && samplesList.size() > 0) {
				for(SampleTimePoints stp : samplesList) {
					if(samplesMap.containsKey(stp.getTreatmentInfo().getId())) {
						stpList = samplesMap.get(stp.getTreatmentInfo().getId());
						stpList.add(stp);
						samplesMap.put(stp.getTreatmentInfo().getId(), stpList);
					}else {
						stpList = new ArrayList<>();
						stpList.add(stp);
						samplesMap.put(stp.getTreatmentInfo().getId(), stpList);
					}
				}
			}
			
			
			List<VitalTimePoints> vtList = null;
			if(vitalList != null && vitalList.size() > 0) {
				for(VitalTimePoints stp : vitalList) {
					if(vtpMap.containsKey(stp.getTreatmentInfo().getId())) {
						vtList = vtpMap.get(stp.getTreatmentInfo().getId());
						vtList.add(stp);
						vtpMap.put(stp.getTreatmentInfo().getId(), vtList);
					}else {
						vtList = new ArrayList<>();
						vtList.add(stp);
						vtpMap.put(stp.getTreatmentInfo().getId(), vtList);
					}
				}
			}
			//Data Entry Details Map
			Map<Long, StudyActivityData> sadatamap = new HashMap<>();
			//Checkin Data 
			Map<Long, Map<Long, List<StudyCheckInActivityDataDetails>>> schkinTempMap = null;
			Map<Long, List<StudyCheckInActivityDataDetails>> schkinTempMap2 = null;
			List<StudyCheckInActivityDataDetails> schkinTempList = null;
			if(stdChkinList != null && stdChkinList.size() > 0) {
				for(StudyCheckInActivityDataDetails sadd : stdChkinList) {
					if(!sadatamap.containsKey(sadd.getSaData().getId()))
						sadatamap.put(sadd.getSaData().getId(), sadd.getSaData());
					if(chickInMap.get(sadd.getSaData().getVolunteerId().getId()) != null) {
						schkinTempMap = chickInMap.get(sadd.getSaData().getVolunteerId().getId());
						if(schkinTempMap != null) {
							schkinTempMap2 = schkinTempMap.get(sadd.getSaData().getSutydActivity().getStudyPeriod().getId());
							if(schkinTempMap2 != null) {
								schkinTempList = schkinTempMap2.get(sadd.getSaData().getSutydActivity().getActivityId().getId());
								if(schkinTempList == null)
									schkinTempList = new ArrayList<>();
								schkinTempList.add(sadd);
								schkinTempMap2.put(sadd.getSaData().getSutydActivity().getActivityId().getId(), schkinTempList);
								schkinTempMap.put(sadd.getSaData().getSutydActivity().getStudyPeriod().getId(), schkinTempMap2);
								chickInMap.put(sadd.getSaData().getVolunteerId().getId(), schkinTempMap);
							}else {
								schkinTempMap2 = new HashMap<>();
								schkinTempList = new ArrayList<>();
								schkinTempList.add(sadd);
								schkinTempMap2.put(sadd.getSaData().getSutydActivity().getActivityId().getId(), schkinTempList);
								schkinTempMap.put(sadd.getSaData().getSutydActivity().getStudyPeriod().getId(), schkinTempMap2);
								chickInMap.put(sadd.getSaData().getVolunteerId().getId(), schkinTempMap);
							}
						}else {
							schkinTempMap = new HashMap<>();
							schkinTempMap2 = new HashMap<>();
							schkinTempList = new ArrayList<>();
							schkinTempList.add(sadd);
							schkinTempMap2.put(sadd.getSaData().getSutydActivity().getActivityId().getId(), schkinTempList);
							schkinTempMap.put(sadd.getSaData().getSutydActivity().getStudyPeriod().getId(), schkinTempMap2);
							chickInMap.put(sadd.getSaData().getVolunteerId().getId(), schkinTempMap);
						}
						
					}else {
						schkinTempList = new ArrayList<>();
						schkinTempMap = new HashMap<>();
						schkinTempMap2 = new HashMap<>();
						schkinTempList.add(sadd);
						schkinTempMap2.put(sadd.getSaData().getSutydActivity().getActivityId().getId(), schkinTempList);
						schkinTempMap.put(sadd.getSaData().getSutydActivity().getStudyPeriod().getId(), schkinTempMap2);
						chickInMap.put(sadd.getSaData().getVolunteerId().getId(), schkinTempMap);
					}
				
				}
			}
			//Study checkout Data
			Map<Long, Map<Long, List<StudyCheckOutActivityDataDetails>>> schkOutTempMap = null;
			Map<Long, List<StudyCheckOutActivityDataDetails>> schOutTempMap2 = null;
			List<StudyCheckOutActivityDataDetails> schkOutTempList = null;
			if(stdChkoutList != null && stdChkoutList.size() > 0) {
				for(StudyCheckOutActivityDataDetails sco : stdChkoutList) {
					if(!sadatamap.containsKey(sco.getSaData().getId()))
						sadatamap.put(sco.getSaData().getId(), sco.getSaData());
					if(checkOutMap.get(sco.getSaData().getVolunteerId().getId()) != null) {
						schkOutTempMap = checkOutMap.get(sco.getSaData().getVolunteerId().getId());
						if(schkOutTempMap != null) {
							schOutTempMap2 = schkOutTempMap.get(sco.getSaData().getSutydActivity().getStudyPeriod().getId());
							if(schOutTempMap2 != null) {
								schkOutTempList = schOutTempMap2.get(sco.getSaData().getSutydActivity().getActivityId().getId());
								if(schkOutTempList == null)
									schkOutTempList = new ArrayList<>();
								schkOutTempList.add(sco);
								schOutTempMap2.put(sco.getSaData().getSutydActivity().getActivityId().getId(), schkOutTempList);
								schkOutTempMap.put(sco.getSaData().getSutydActivity().getStudyPeriod().getId(), schOutTempMap2);
								checkOutMap.put(sco.getSaData().getVolunteerId().getId(), schkOutTempMap);
							}else {
								schOutTempMap2 = new HashMap<>();
								schkOutTempList = new ArrayList<>();
								schkOutTempList.add(sco);
								schOutTempMap2.put(sco.getSaData().getSutydActivity().getActivityId().getId(), schkOutTempList);
								schkOutTempMap.put(sco.getSaData().getSutydActivity().getStudyPeriod().getId(), schOutTempMap2);
								checkOutMap.put(sco.getSaData().getVolunteerId().getId(), schkOutTempMap);
							}
						}else {
							schkOutTempMap = new HashMap<>();
							schOutTempMap2 = new HashMap<>();
							schkOutTempList = new ArrayList<>();
							schkOutTempList.add(sco);
							schOutTempMap2.put(sco.getSaData().getSutydActivity().getActivityId().getId(), schkOutTempList);
							schkOutTempMap.put(sco.getSaData().getSutydActivity().getStudyPeriod().getId(), schOutTempMap2);
							checkOutMap.put(sco.getSaData().getVolunteerId().getId(), schkOutTempMap);
						}
					}else {
						schkOutTempMap = new HashMap<>();
						schOutTempMap2 = new HashMap<>();
						schkOutTempList = new ArrayList<>();
						schkOutTempList.add(sco);
						schOutTempMap2.put(sco.getSaData().getSutydActivity().getActivityId().getId(), schkOutTempList);
						schkOutTempMap.put(sco.getSaData().getSutydActivity().getStudyPeriod().getId(), schOutTempMap2);
						checkOutMap.put(sco.getSaData().getVolunteerId().getId(), schkOutTempMap);
					}
					
				}
			}
			//StudyExecutionData
	
			Map<Long, Map<Long, List<StudyExecutionActivityDataDetails>>> stdexeTempMap = null;
			Map<Long, List<StudyExecutionActivityDataDetails>> stdexeTempMap2 = null;
			List<StudyExecutionActivityDataDetails> stdexeTempList = null;
			if(stdExList != null && stdExList.size() > 0) {
				for(StudyExecutionActivityDataDetails sexe : stdExList) {
					if(!sadatamap.containsKey(sexe.getSaData().getId()))
						sadatamap.put(sexe.getSaData().getId(), sexe.getSaData());
					if(stdexeMap.get(sexe.getSaData().getVolunteerId().getId()) != null) {
						stdexeTempMap = stdexeMap.get(sexe.getSaData().getVolunteerId().getId());
						if(stdexeTempMap != null && stdexeTempMap.size() > 0) {
							stdexeTempMap2 = stdexeTempMap.get(sexe.getSaData().getSutydActivity().getStudyPeriod().getId());
								if(stdexeTempMap2 != null) {
									stdexeTempList = stdexeTempMap2.get(sexe.getSaData().getSutydActivity().getActivityId().getId());
									if(stdexeTempList == null)
										stdexeTempList = new ArrayList<>();
									stdexeTempList.add(sexe);
									stdexeTempMap2.put(sexe.getSaData().getSutydActivity().getActivityId().getId(), stdexeTempList);
									stdexeTempMap.put(sexe.getSaData().getSutydActivity().getStudyPeriod().getId(), stdexeTempMap2);
									stdexeMap.put(sexe.getSaData().getVolunteerId().getId(), stdexeTempMap);
								
								}else {
									stdexeTempMap2 = new HashMap<>();
									stdexeTempList = new ArrayList<>();
									stdexeTempList.add(sexe);
									stdexeTempMap2.put(sexe.getSaData().getSutydActivity().getActivityId().getId(), stdexeTempList);
									stdexeTempMap.put(sexe.getSaData().getSutydActivity().getStudyPeriod().getId(), stdexeTempMap2);
									stdexeMap.put(sexe.getSaData().getVolunteerId().getId(), stdexeTempMap);
								}
						}else {
							stdexeTempMap2 = new HashMap<>();
							stdexeTempList = new ArrayList<>();
							stdexeTempList.add(sexe);
							stdexeTempMap2.put(sexe.getSaData().getSutydActivity().getActivityId().getId(), stdexeTempList);
							stdexeTempMap.put(sexe.getSaData().getSutydActivity().getStudyPeriod().getId(), stdexeTempMap2);
							stdexeMap.put(sexe.getSaData().getVolunteerId().getId(), stdexeTempMap);
					}
				}else {
					stdexeTempMap = new HashMap<>();
					stdexeTempMap2 = new HashMap<>();
					stdexeTempList = new ArrayList<>();
					stdexeTempList.add(sexe);
					stdexeTempMap2.put(sexe.getSaData().getSutydActivity().getActivityId().getId(), stdexeTempList);
					stdexeTempMap.put(sexe.getSaData().getSutydActivity().getStudyPeriod().getId(), stdexeTempMap2);
					stdexeMap.put(sexe.getSaData().getVolunteerId().getId(), stdexeTempMap);
				}
					
			}
		}
		//Data Entry Details Purpose
		Map<Long, Map<Long, ActivityEntryDetailsDto>> pwActPerformedMap = null;
		Map<Long, ActivityEntryDetailsDto> actIdPerformedMap = null;
		if(sadatamap.size() > 0) {
			for(Map.Entry<Long, StudyActivityData> sadMap : sadatamap.entrySet()) {
				ActivityEntryDetailsDto aedDto = new ActivityEntryDetailsDto();
				aedDto.setPerformedBy(sadMap.getValue().getCreatedBy());
				aedDto.setPerformedOn(sadMap.getValue().getCreatedOn());
				if(actPerformedMap.containsKey(sadMap.getValue().getVolunteerId().getId())) {
					pwActPerformedMap = actPerformedMap.get(sadMap.getValue().getVolunteerId().getId());
					if(pwActPerformedMap.containsKey(sadMap.getValue().getSutydActivity().getStudyPeriod().getId())) {
						actIdPerformedMap = pwActPerformedMap.get(sadMap.getValue().getSutydActivity().getStudyPeriod().getId());
						actIdPerformedMap.put(sadMap.getValue().getSutydActivity().getActivityId().getId(), aedDto);
						pwActPerformedMap.put(sadMap.getValue().getSutydActivity().getStudyPeriod().getId(), actIdPerformedMap);
						actPerformedMap.put(sadMap.getValue().getVolunteerId().getId(), pwActPerformedMap);
					}else {
						actIdPerformedMap = new HashMap<>();
						actIdPerformedMap.put(sadMap.getValue().getSutydActivity().getActivityId().getId(), aedDto);
						pwActPerformedMap.put(sadMap.getValue().getSutydActivity().getStudyPeriod().getId(), actIdPerformedMap);
						actPerformedMap.put(sadMap.getValue().getVolunteerId().getId(), pwActPerformedMap);
					}
				}else {
					actIdPerformedMap = new HashMap<>();
					pwActPerformedMap = new HashMap<>();
					actIdPerformedMap.put(sadMap.getValue().getSutydActivity().getActivityId().getId(), aedDto);
					pwActPerformedMap.put(sadMap.getValue().getSutydActivity().getStudyPeriod().getId(), actIdPerformedMap);
					actPerformedMap.put(sadMap.getValue().getVolunteerId().getId(), pwActPerformedMap);
				}
			}
		}
		
			/*if(stdexeMap.size() > 0) {
				for(Map.Entry<Long, Map<Long, Map<Long, List<StudyExecutionActivityDataDetails>>>> map1 : stdexeMap.entrySet()) {
					Map<Long, Map<Long, List<StudyExecutionActivityDataDetails>>> inMap1 = map1.getValue();
					for(Map.Entry<Long, Map<Long, List<StudyExecutionActivityDataDetails>>> map2 : inMap1.entrySet()) {
						Map<Long, List<StudyExecutionActivityDataDetails>>inMap2 = map2.getValue();
						for(Map.Entry<Long, List<StudyExecutionActivityDataDetails>> map3 : inMap2.entrySet()) {
							List<StudyExecutionActivityDataDetails> seaList = map3.getValue();
							if(map3.getKey() == 26) {
								for(StudyExecutionActivityDataDetails sea : seaList) {
									System.out.println("Vol Id : "+sea.getSaData().getVolunteerId().getId()+"Study Act Data : "+sea.getSaData().getId()+"==>Act Name : "+sea.getSaData().getSutydActivity().getActivityId().getName()+"==> Act Period :"+sea.getSaData().getSutydActivity().getStudyPeriod().getPeriodName()+"==> Value : "+sea.getValue());
								}
							}
							
						}
					}
				}
			}*/
			//Dosing Time Points
			Map<Long, Map<Long, List<SubjectDoseTimePoints>>> sdoseTpTempMap = null;
			Map<Long, List<SubjectDoseTimePoints>> sdtpTemp = null;
			List<SubjectDoseTimePoints> stdtpTempList = null;
			if(sdtpList != null && sdtpList.size() > 0) {
				for(SubjectDoseTimePoints sdtp : sdtpList) {
					if(sdtpMap.containsKey(sdtp.getStudySubjects().getId())) {
						sdoseTpTempMap = sdtpMap.get(sdtp.getStudySubjects().getId());
						if(sdoseTpTempMap.containsKey(sdtp.getPeriod().getId())){
							sdtpTemp = sdoseTpTempMap.get(sdtp.getPeriod().getId());
							if(sdtpTemp.containsKey(sdtp.getDoseTimePoints().getTreatmentInfo().getId())) {
								stdtpTempList = sdtpTemp.get(sdtp.getDoseTimePoints().getTreatmentInfo().getId());
								stdtpTempList.add(sdtp);
								sdtpTemp.put(sdtp.getDoseTimePoints().getTreatmentInfo().getId(), stdtpTempList);
								sdoseTpTempMap.put(sdtp.getPeriod().getId(), sdtpTemp);
								sdtpMap.put(sdtp.getStudySubjects().getId(), sdoseTpTempMap);
							}else {
								stdtpTempList = new ArrayList<>();
								stdtpTempList.add(sdtp);
								sdtpTemp.put(sdtp.getDoseTimePoints().getTreatmentInfo().getId(), stdtpTempList);
								sdoseTpTempMap.put(sdtp.getPeriod().getId(), sdtpTemp);
								sdtpMap.put(sdtp.getStudySubjects().getId(), sdoseTpTempMap);
							}
						}else {
							stdtpTempList = new ArrayList<>();
							sdtpTemp = new HashMap<>();
							stdtpTempList.add(sdtp);
							sdtpTemp.put(sdtp.getDoseTimePoints().getTreatmentInfo().getId(), stdtpTempList);
							sdoseTpTempMap.put(sdtp.getPeriod().getId(), sdtpTemp);
							sdtpMap.put(sdtp.getStudySubjects().getId(), sdoseTpTempMap);

						}
					}else {
						stdtpTempList = new ArrayList<>();
						sdtpTemp = new HashMap<>();
						sdoseTpTempMap = new HashMap<>();
						stdtpTempList.add(sdtp);
						sdtpTemp.put(sdtp.getDoseTimePoints().getTreatmentInfo().getId(), stdtpTempList);
						sdoseTpTempMap.put(sdtp.getPeriod().getId(), sdtpTemp);
						sdtpMap.put(sdtp.getStudySubjects().getId(), sdoseTpTempMap);

					}
				}
			}
			
			//Dosing parameters
			Map<Long, Map<Long, List<SubjectDoseTimePointParametersData>>> doseParamTempMap1 = null;
			Map<Long, List<SubjectDoseTimePointParametersData>> doseParamTempMap2 = null;
			List<SubjectDoseTimePointParametersData> doseParamTempList = null;
			if(sdtpparamdList != null && sdtpparamdList.size() > 0) {
				for(SubjectDoseTimePointParametersData sdp : sdtpparamdList) {
					if(sdp.getSubjectDoseTimePoint().getStudySubjects() != null) {
						if(doseParamMap.containsKey(sdp.getSubjectDoseTimePoint().getStudySubjects().getId())) {
							doseParamTempMap1 = doseParamMap.get(sdp.getSubjectDoseTimePoint().getStudySubjects().getId());
							if(doseParamTempMap1.containsKey(sdp.getSubjectDoseTimePoint().getPeriod().getId())) {
								doseParamTempMap2 = doseParamTempMap1.get(sdp.getSubjectDoseTimePoint().getPeriod().getId());
								if(doseParamTempMap2.containsKey(sdp.getSubjectDoseTimePoint().getDoseTimePoints().getTreatmentInfo().getId())) {
									doseParamTempList = doseParamTempMap2.get(sdp.getSubjectDoseTimePoint().getDoseTimePoints().getTreatmentInfo().getId());
									doseParamTempList.add(sdp);
									doseParamTempMap2.put(sdp.getSubjectDoseTimePoint().getDoseTimePoints().getTreatmentInfo().getId(), doseParamTempList);
									doseParamTempMap1.put(sdp.getSubjectDoseTimePoint().getPeriod().getId(), doseParamTempMap2);
									doseParamMap.put(sdp.getSubjectDoseTimePoint().getStudySubjects().getId(), doseParamTempMap1);
								}else {
									doseParamTempList = new ArrayList<>();
									doseParamTempList.add(sdp);
									doseParamTempMap2.put(sdp.getSubjectDoseTimePoint().getDoseTimePoints().getTreatmentInfo().getId(), doseParamTempList);
									doseParamTempMap1.put(sdp.getSubjectDoseTimePoint().getPeriod().getId(), doseParamTempMap2);
									doseParamMap.put(sdp.getSubjectDoseTimePoint().getStudySubjects().getId(), doseParamTempMap1);
								}
							}else {
								doseParamTempList = new ArrayList<>();
								doseParamTempMap2 = new HashMap<>();
								doseParamTempList.add(sdp);
								doseParamTempMap2.put(sdp.getSubjectDoseTimePoint().getDoseTimePoints().getTreatmentInfo().getId(), doseParamTempList);
								doseParamTempMap1.put(sdp.getSubjectDoseTimePoint().getPeriod().getId(), doseParamTempMap2);
								doseParamMap.put(sdp.getSubjectDoseTimePoint().getStudySubjects().getId(), doseParamTempMap1);
							}
						}else {
							doseParamTempList = new ArrayList<>();
							doseParamTempMap2 = new HashMap<>();
							doseParamTempMap1 = new HashMap<>();
							doseParamTempList.add(sdp);
							doseParamTempMap2.put(sdp.getSubjectDoseTimePoint().getDoseTimePoints().getTreatmentInfo().getId(), doseParamTempList);
							doseParamTempMap1.put(sdp.getSubjectDoseTimePoint().getPeriod().getId(), doseParamTempMap2);
							doseParamMap.put(sdp.getSubjectDoseTimePoint().getStudySubjects().getId(), doseParamTempMap1);
						}
					}
				}
			}
			//Vitals
			Map<Long, Map<Long, Map<String, SubjectVitalTimePointsData>>> vtpdPeriodMap = null;
			Map<Long, Map<String, SubjectVitalTimePointsData>> vitalTpMap = null;
			Map<String, SubjectVitalTimePointsData> positionVitalMap = null;
			if(stdVitalList != null && stdVitalList.size() > 0) {
				for(SubjectVitalTimePointsData svt : stdVitalList) {
					if(vitalMap.containsKey(svt.getSubject().getId())) {
						vtpdPeriodMap = vitalMap.get(svt.getSubject().getId());
						if(vtpdPeriodMap.containsKey(svt.getPeriod().getId())) {
							vitalTpMap = vtpdPeriodMap.get(svt.getPeriod().getId());
							if(vitalTpMap.containsKey(svt.getTimepoint().getId())) {
								positionVitalMap = vitalTpMap.get(svt.getTimepoint().getId());
								positionVitalMap.put(svt.getColltedPosition(), svt);
								vitalTpMap.put(svt.getTimepoint().getId(), positionVitalMap);
								vtpdPeriodMap.put(svt.getPeriod().getId(), vitalTpMap);
								vitalMap.put(svt.getSubject().getId(), vtpdPeriodMap);
							}else {
								positionVitalMap = new HashMap<>();
								positionVitalMap.put(svt.getColltedPosition(), svt);
								vitalTpMap.put(svt.getTimepoint().getId(), positionVitalMap);
								vtpdPeriodMap.put(svt.getPeriod().getId(), vitalTpMap);
								vitalMap.put(svt.getSubject().getId(), vtpdPeriodMap);
							}
						}else {
							vitalTpMap = new HashMap<>();
							positionVitalMap = new HashMap<>();
							positionVitalMap.put(svt.getColltedPosition(), svt);
							vitalTpMap.put(svt.getTimepoint().getId(), positionVitalMap);
							vtpdPeriodMap.put(svt.getPeriod().getId(), vitalTpMap);
							vitalMap.put(svt.getSubject().getId(), vtpdPeriodMap);
						}
					}else {
						vtpdPeriodMap = new HashMap<>();
						vitalTpMap = new HashMap<>();
						positionVitalMap = new HashMap<>();
						positionVitalMap.put(svt.getColltedPosition(), svt);
						vitalTpMap.put(svt.getTimepoint().getId(), positionVitalMap);
						vtpdPeriodMap.put(svt.getPeriod().getId(), vitalTpMap);
						vitalMap.put(svt.getSubject().getId(), vtpdPeriodMap);
					}
				}
			}
			Map<Long, List<SubjectVitalParametersData>> vtpParamMap = null;
			List<SubjectVitalParametersData> vparamList = null;
			if(stdVitalParamList != null && stdVitalParamList.size() > 0) {
				for(SubjectVitalParametersData svtp : stdVitalParamList) {
					if(vitalParamMap.containsKey(svtp.getSubjVitalTpData().getId())) {
						vtpParamMap = vitalParamMap.get(svtp.getSubjVitalTpData().getId());
						if(vtpParamMap.containsKey(svtp.getSubjVitalTpData().getTimepoint().getId())) {
							vparamList = vtpParamMap.get(svtp.getSubjVitalTpData().getTimepoint().getId());
							vparamList.add(svtp);
							vtpParamMap.put(svtp.getSubjVitalTpData().getTimepoint().getId(), vparamList);
							vitalParamMap.put(svtp.getSubjVitalTpData().getId(), vtpParamMap);
						}else {
							vparamList = new ArrayList<>();
							vparamList.add(svtp);
							vtpParamMap.put(svtp.getSubjVitalTpData().getTimepoint().getId(), vparamList);
							vitalParamMap.put(svtp.getSubjVitalTpData().getId(), vtpParamMap);
						}
					}else {
						vtpParamMap = new HashMap<>();
						vparamList = new ArrayList<>();
						vparamList.add(svtp);
						vtpParamMap.put(svtp.getSubjVitalTpData().getTimepoint().getId(), vparamList);
						vitalParamMap.put(svtp.getSubjVitalTpData().getId(), vtpParamMap);
					}
				}
			}
			
			//SampleCollection
			Map<Long, List<SubjectSampleCollectionTimePointsData>> sampCollTempMap1 = null;
			List<SubjectSampleCollectionTimePointsData> sampCollTempList = null;
			if(subSapColTpDataList != null && subSapColTpDataList.size() > 0) {
				for(SubjectSampleCollectionTimePointsData ssctd : subSapColTpDataList) {
					if(sampCollMap.containsKey(ssctd.getSubject().getId())) {
						sampCollTempMap1 = sampCollMap.get(ssctd.getSubject().getId());
						if(sampCollTempMap1 != null && sampCollTempMap1.containsKey(ssctd.getStudyPeriodMaster().getId())) {
							sampCollTempList = sampCollTempMap1.get(ssctd.getStudyPeriodMaster().getId());
							sampCollTempList.add(ssctd);
							sampCollTempMap1.put(ssctd.getStudyPeriodMaster().getId(), sampCollTempList);
							sampCollMap.put(ssctd.getSubject().getId(), sampCollTempMap1);
						}else {
							sampCollTempList = new ArrayList<>();
							sampCollTempList.add(ssctd);
							if(sampCollTempMap1 == null)
								sampCollTempMap1 = new HashMap<>();
							sampCollTempMap1.put(ssctd.getStudyPeriodMaster().getId(), sampCollTempList);
							sampCollMap.put(ssctd.getSubject().getId(), sampCollTempMap1);
						}
					}else {
						sampCollTempList = new ArrayList<>();
						sampCollTempMap1 = new HashMap<>();
						sampCollTempList.add(ssctd);
						sampCollTempMap1.put(ssctd.getStudyPeriodMaster().getId(), sampCollTempList);
						sampCollMap.put(ssctd.getSubject().getId(), sampCollTempMap1);
					}
				}
			}
			//Meals Collection
			Map<Long, Map<Long, List<SubjectMealsTimePointsData>>> mealCollTempMap1 = null;
			Map<Long, List<SubjectMealsTimePointsData>> mealCollTempMap2 = null;
			List<SubjectMealsTimePointsData> mealCollTempList = null;
			if(subMealTpDataList != null && subMealTpDataList.size() > 0) {
				for(SubjectMealsTimePointsData smpd : subMealTpDataList) {
					if(mealsCollMap.containsKey(smpd.getSubject().getId())) {
						mealCollTempMap1 = mealsCollMap.get(smpd.getSubject().getId());
						if(mealCollTempMap1.containsKey(smpd.getStudyPeriodMaster().getId())) {
							mealCollTempMap2 = mealCollTempMap1.get(smpd.getStudyPeriodMaster().getId());
							if(mealCollTempMap2 != null && mealCollTempMap2.containsKey(smpd.getMealsTimePoint().getTreatmentInfo().getId())) {
								mealCollTempList = mealCollTempMap2.get(smpd.getMealsTimePoint().getTreatmentInfo().getId());
								mealCollTempList.add(smpd);
								mealCollTempMap2.put(smpd.getMealsTimePoint().getTreatmentInfo().getId(), mealCollTempList);
								mealCollTempMap1.put(smpd.getStudyPeriodMaster().getId(), mealCollTempMap2);
								mealsCollMap.put(smpd.getSubject().getId(), mealCollTempMap1);
							}else {
								mealCollTempList = new ArrayList<>();
								mealCollTempList.add(smpd);
								mealCollTempMap2.put(smpd.getMealsTimePoint().getTreatmentInfo().getId(), mealCollTempList);
								mealCollTempMap1.put(smpd.getStudyPeriodMaster().getId(), mealCollTempMap2);
								mealsCollMap.put(smpd.getSubject().getId(), mealCollTempMap1);
							}
						}else {
							mealCollTempList = new ArrayList<>();
							mealCollTempMap2 = new HashMap<>();
							mealCollTempList.add(smpd);
							mealCollTempMap2.put(smpd.getMealsTimePoint().getTreatmentInfo().getId(), mealCollTempList);
							mealCollTempMap1.put(smpd.getStudyPeriodMaster().getId(), mealCollTempMap2);
							mealsCollMap.put(smpd.getSubject().getId(), mealCollTempMap1);
						}
					}else {
						mealCollTempList = new ArrayList<>();
						mealCollTempMap2 = new HashMap<>();
						mealCollTempMap1 = new HashMap<>();
						mealCollTempList.add(smpd);
						mealCollTempMap2.put(smpd.getMealsTimePoint().getTreatmentInfo().getId(), mealCollTempList);
						mealCollTempMap1.put(smpd.getStudyPeriodMaster().getId(), mealCollTempMap2);
						mealsCollMap.put(smpd.getSubject().getId(), mealCollTempMap1);
					}
				}
			}
		
		//Static Activities 
		List<StaticActivityDataDetails> tempSaddList = null;
		if(saddList != null && saddList.size() > 0) {
			for(StaticActivityDataDetails sadd : saddList) {
				if(staticDataActMap.containsKey(sadd.getSubjectWithDrawDetails().getId())) {
					tempSaddList = staticDataActMap.get(sadd.getSubjectWithDrawDetails().getId());
					tempSaddList.add(sadd);
					staticDataActMap.put(sadd.getSubjectWithDrawDetails().getId(), tempSaddList);
				}else {
					tempSaddList = new ArrayList<>();
					tempSaddList.add(sadd);
					staticDataActMap.put(sadd.getSubjectWithDrawDetails().getId(), tempSaddList);
				}
			}
		}
		List<StaticActivityValueDetails> tempSavdList = null;
		if(savdList != null && savdList.size() > 0) {
			for(StaticActivityValueDetails savd : savdList) {
				if(staticActValsMap.containsKey(savd.getStActDetailsId().getId())) {
					tempSavdList = staticActValsMap.get(savd.getStActDetailsId().getId());
					tempSavdList.add(savd);
					staticActValsMap.put(savd.getStActDetailsId().getId(), tempSavdList);
				}else {
					tempSavdList = new ArrayList<>();
					tempSavdList.add(savd);
					staticActValsMap.put(savd.getStActDetailsId().getId(), tempSavdList);
				}
			}
		}
		List<StaticActivityDetails> tempActList = null;
		if(staticActList != null && staticActList.size() > 0) {
			for(StaticActivityDetails sad : staticActList) {
				if(saticActMap.containsKey(sad.getActivityType())) {
					tempActList = saticActMap.get(sad.getActivityType());
					tempActList.add(sad);
					saticActMap.put(sad.getActivityType(), tempActList);
				}else {
					tempActList = new ArrayList<>();
					tempActList.add(sad);
					saticActMap.put(sad.getActivityType(), tempActList);
				}
			}
		}
		Map<Long, SubjectWithdrawDetails> swdMap = null;
		if(subjWithDarawList != null && subjWithDarawList.size() > 0) {
			for(SubjectWithdrawDetails swd : subjWithDarawList) {
				if(subjectWithDrawMap.containsKey(swd.getStudyVolunteer().getId())) {
					swdMap = subjectWithDrawMap.get(swd.getStudyVolunteer().getId());
					swdMap.put(swd.getPeriod().getId(), swd);
					subjectWithDrawMap.put(swd.getStudyVolunteer().getId(), swdMap);
				}else {
					swdMap = new HashMap<>();
					swdMap.put(swd.getPeriod().getId(), swd);
					subjectWithDrawMap.put(swd.getStudyVolunteer().getId(), swdMap);
				}
			}
		}
		if(treatmentsList != null && treatmentsList.size() > 0) {
			for(TreatmentInfo  tinf : treatmentsList) {
				treatmentsMap.put(tinf.getId(), tinf);
			}
		}
		if(treatmentsMap.size() > 0) 
			treatmentMinId = Collections.min(treatmentsMap.keySet());
		
		if(druginfList != null && druginfList.size() > 0) {
			for(DosingInfoDetails dinf : druginfList) {
				drugInfMap.put(dinf.getTinfo().getRandamizationCode(), dinf);
			}
		}
		if(sampleProList != null && sampleProList.size() > 0) {
			Map<Integer, List<ProjectsDetails>> proMap = new HashMap<>();
			List<ProjectsDetails> tempList = null;
			for(ProjectsDetails pro : sampleProList) {
				if(proMap.containsKey(pro.getSubRowNo())) {
					tempList = proMap.get(pro.getSubRowNo());
					tempList.add(pro);
					proMap.put(pro.getSubRowNo(), tempList);
				}else {
					tempList = new ArrayList<>();
					tempList.add(pro);
					proMap.put(pro.getSubRowNo(), tempList);
				}
			}
			if(proMap.size() > 0) {
				for(Map.Entry<Integer, List<ProjectsDetails>> pdMap : proMap.entrySet()) {
					List<ProjectsDetails> pdList = pdMap.getValue();
					if(pdList != null) {
						SampleInfoDto  sid = new SampleInfoDto();
						String treatment = "";
						for(ProjectsDetails pd : pdList) {
							if(pd.getFieldName().equals("VOLUMNOFBLOOD"))
								sid.setBloodVolume(pd.getFieldValue());
							if(pd.getFieldName().equals("TYPEOFVACUTAINERUSED"))
								sid.setTypeOfVacutainer(pd.getFieldValue());
							if(pd.getFieldName().equals("LIGHTCONDITION"))
								sid.setLightCondition(pd.getFieldValue());
							if(pd.getFieldName().equals("TREATMENT"))
								treatment = pd.getFieldValue();
						}
						if(!treatment.equals("")) {
							if(treatment.equals("0")) {
								for(Map.Entry<Long, TreatmentInfo> trmap : treatmentsMap.entrySet()) {
									sid.setTreatment(trmap.getValue().getTreatmentName());
									sampleInfoMap.put(trmap.getValue().getId(), sid);
								}
							}else {
								TreatmentInfo tinf = treatmentsMap.get(Long.parseLong(treatment));
								sid.setTreatment(tinf.getTreatmentName());
								sampleInfoMap.put(tinf.getId(), sid);
							}
						}
					}
				}
			}
		}
		
		 Map<Long, Map<Long, List<RecannulationDataDto>>> periodRcDataMap = null;
		 Map<Long, List<RecannulationDataDto>> tpRcDataMap = null;
		 List<RecannulationDataDto> rcDataTempList = null;
		 Map<Long, List<RecannulationDataDto>> rcdPeriodMap = null;
		 
		if(recdDtoList != null && recdDtoList.size() > 0) {
			for(RecannulationDataDto rcd : recdDtoList) {
				if(rcd.getSampleId() != null) {
					if(rcDataWithTpMap.containsKey(rcd.getSubjectId())) {
						periodRcDataMap = rcDataWithTpMap.get(rcd.getSubjectId());
						if(periodRcDataMap.containsKey(rcd.getPeriodId())) {
							tpRcDataMap = periodRcDataMap.get(rcd.getPeriodId());
							if(tpRcDataMap.containsKey(rcd.getSampleId())) {
								rcDataTempList = tpRcDataMap.get(rcd.getSampleId());
								rcDataTempList.add(rcd);
								tpRcDataMap.put(rcd.getSampleId(), rcDataTempList);
								periodRcDataMap.put(rcd.getPeriodId(), tpRcDataMap);
								rcDataWithTpMap.put(rcd.getSubjectId(), periodRcDataMap);
							}else {
								rcDataTempList = new ArrayList<>();
								rcDataTempList.add(rcd);
								tpRcDataMap.put(rcd.getSampleId(), rcDataTempList);
								periodRcDataMap.put(rcd.getPeriodId(), tpRcDataMap);
								rcDataWithTpMap.put(rcd.getSubjectId(), periodRcDataMap);
							}
						}else {
							tpRcDataMap = new HashMap<>();
							rcDataTempList = new ArrayList<>();
							rcDataTempList.add(rcd);
							tpRcDataMap.put(rcd.getSampleId(), rcDataTempList);
							periodRcDataMap.put(rcd.getPeriodId(), tpRcDataMap);
							rcDataWithTpMap.put(rcd.getSubjectId(), periodRcDataMap);
						}
					}else {
						periodRcDataMap = new HashMap<>();
						tpRcDataMap = new HashMap<>();
						rcDataTempList = new ArrayList<>();
						rcDataTempList.add(rcd);
						tpRcDataMap.put(rcd.getSampleId(), rcDataTempList);
						periodRcDataMap.put(rcd.getPeriodId(), tpRcDataMap);
						rcDataWithTpMap.put(rcd.getSubjectId(), periodRcDataMap);
					}
				}else {
					if(rcDataWithoutTpMap.containsKey(rcd.getSubjectId())) {
						rcdPeriodMap = rcDataWithoutTpMap.get(rcd.getSubjectId());
						if(rcdPeriodMap.containsKey(rcd.getPeriodId())) {
							rcDataTempList = rcdPeriodMap.get(rcd.getPeriodId());
							rcDataTempList.add(rcd);
							rcdPeriodMap.put(rcd.getPeriodId(), rcDataTempList);
							rcDataWithoutTpMap.put(rcd.getSubjectId(), rcdPeriodMap);
						}else {
							rcdPeriodMap = new HashMap<>();
							rcDataTempList = new ArrayList<>();
							rcDataTempList.add(rcd);
							rcdPeriodMap.put(rcd.getPeriodId(), rcDataTempList);
							rcDataWithoutTpMap.put(rcd.getSubjectId(), rcdPeriodMap);
						}
					}
				}
			}
		}
		if(usersList != null && usersList.size() > 0) {
			for(UserMasterDto userDto : usersList) {
				userNamesMap.put(userDto.getUserName(), userDto.getUserFullName());
			}
		}
		dcdDto = new DataCrfDtoDetails();
		dcdDto.setStudy(study);
		dcdDto.setAppConfig(appConfig);
		dcdDto.setUser(user);
		dcdDto.setGaMap(gaMap);
		dcdDto.setGpMap(gpMap);
		dcdDto.setGvMap(gvMap);
		dcdDto.setSubMap(subMap);
		dcdDto.setSpmMap(spmMap);
		dcdDto.setChickInMap(chickInMap);
		dcdDto.setCheckOutMap(checkOutMap);
		dcdDto.setStdexeMap(stdexeMap);
		dcdDto.setSdtpMap(sdtpMap);
		dcdDto.setDoseParamMap(doseParamMap);
		dcdDto.setVitalMap(vitalMap);
		dcdDto.setVitalParamMap(vitalParamMap);
		dcdDto.setSampCollMap(sampCollMap);
		dcdDto.setMealsCollMap(mealsCollMap);
		dcdDto.setDefalutActMap(defalutActMap);
		dcdDto.setSubwtrMap(subwtrMap);
		dcdDto.setStdVolMap(stdVolMap);
		dcdDto.setDoseMap(doseMap);
		dcdDto.setSamplesMap(samplesMap);
		dcdDto.setVitalMap(vitalMap);
		dcdDto.setMealsMap(mealsMap);
		dcdDto.setProject(project);
		dcdDto.setSactMap(sactMap);
		dcdDto.setSactParamMap(sactParamMap);
		dcdDto.setStdSubjetsMap(stdSubjetsMap);
		dcdDto.setVtpMap(vtpMap);
		dcdDto.setStaticActValsMap(staticActValsMap);
		dcdDto.setStaticDataActMap(staticDataActMap);
		dcdDto.setSaticActMap(saticActMap);
		dcdDto.setSubjectWithDrawMap(subjectWithDrawMap);
		dcdDto.setTreatmentsMap(treatmentsMap);
		dcdDto.setTreatmentMinId(treatmentMinId);
		dcdDto.setAdrPojo(adrPojo);
		dcdDto.setActPerformedMap(actPerformedMap);
		dcdDto.setDrugInfMap(drugInfMap);
		dcdDto.setSampleInfoMap(sampleInfoMap);
		dcdDto.setRcDataWithoutTpMap(rcDataWithoutTpMap);
		dcdDto.setRcDataWithTpMap(rcDataWithTpMap);
		dcdDto.setUserNamesMap(userNamesMap);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return dcdDto;
}

public List<String> generateCoverPagesForDataCrf(HttpServletRequest request, Locale currentLocale,
		MessageSource messageSource, Long langId, DataCrfDtoDetails dcdDto, String dateStr, StudyVolunteerReporting svr, StudyPeriodMaster spm, String dateStrWithTime) {
	List<String> fileList = new ArrayList<>();
	Map<String, String> proMap = null;
String fileName = "";
String fname = "";
/*Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
Font heading = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD);*/
@SuppressWarnings("unused")
int fontNos = FontFactory.registerDirectory("C:\\WINDOWS\\Fonts");
Font calibri = FontFactory.getFont("Calibri");
Font mainHeading = new Font(calibri.getFamily(), 14, Font.BOLD);
Font subHeading = new Font(calibri.getFamily(), 12, Font.BOLD);
//Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
Font regular = new Font(calibri.getFamily(), 10);
Font regularWithUnderLine = new Font(calibri.getFamily(), 10, Font.UNDERLINE);
Font heading = new Font(calibri.getFamily(), 10, Font.BOLD); 
try {
	ApplicationConfiguration appConfig = dcdDto.getAppConfig();
	if(appConfig != null) {
		if(appConfig.getConfigCode().equals("ADV")) {
			fileName = generateFirstCoverPageAdv(request, currentLocale, messageSource, proMap, langId, dcdDto, dateStr, regular, heading, mainHeading, subHeading, svr, spm, dateStrWithTime, regularWithUnderLine);
//			fname = generateSecondCoverPageAdv(request, currentLocale, messageSource, proMap, langId, user, appConfig, dateStr);
		}else {
			fileName = generateFirstCoverPage(request, currentLocale, messageSource, proMap, langId, dcdDto, dateStr, regular, heading, mainHeading, subHeading, svr, spm, dateStrWithTime);
			fname = generateSecondCoverPage(request, currentLocale, messageSource, proMap, langId, dcdDto, dateStr, regular, heading, mainHeading, subHeading, svr, spm, dateStrWithTime);
		}
	}
	if(fileName != null && !fileName.equals(""))
		fileList.add(fileName);
	if(fname != null && !fname.equals(""))
		fileList.add(fname);
} catch (Exception e) {
	e.printStackTrace();
}
return fileList;
}

@SuppressWarnings("unused")
private String generateSecondCoverPage(HttpServletRequest request, Locale currentLocale, MessageSource messageSource,
		Map<String, String> proMap, Long langId, DataCrfDtoDetails dcdDto, String dateStr, Font regular, Font heading,
		Font mainHeading, Font subHeading, StudyVolunteerReporting svr, StudyPeriodMaster spm, String dateStrWithTime) throws DocumentException, FileNotFoundException {
	String advimg = request.getServletContext().getRealPath("/static/images/company.PNG");
	String img = request.getServletContext().getRealPath("/static/images/AvantSanteLog.jpg");
	String realPath = request.getSession().getServletContext().getRealPath("/");
	String path = realPath+"//BlankCrfGeneration";
	File file = new File(path);
	if(!file.exists())
		file.mkdirs();
	String filepath = path+"/FinalBlankPdf_CoverPage2_"+svr.getId()+"_"+spm.getPeriodNo()+".pdf";
	Document document = new Document();
//    document.setPageSize(PageSize.A4);
	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
	document.addTitle("FinalBlankPdf");
	document.setPageSize(PageSize.A4);
//	document.setPageSize(PageSize.A4.rotate());
	document.setMargins(40, 40, 20, 90); //A4
//	document.setMargins(10f, 10f, 100f, 0f);//A4 Rotation
	document.setMarginMirroring(false); 
	
	String periodNo = spm.getPeriodNo()+"";
	if(periodNo.length() == 1)
		periodNo = "0"+periodNo;
	
	String projectNumber = "";
	String projectTitle = "";
	String sponsorCode = "";
	if(dcdDto.getStudy() != null) {
		projectNumber = dcdDto.getStudy().getProjectNo();
		projectTitle = dcdDto.getStudy().getStudyTitle();
		sponsorCode = dcdDto.getStudy().getSponsorCode();
	}
	ApplicationConfiguration appConfig = dcdDto.getAppConfig();
	if(appConfig != null) {
		if(appConfig.getConfigCode().equals("ADV")) {
			HeaderAndFooter event1 = new HeaderAndFooter(advimg, projectNumber, periodNo, dcdDto.getUser(), dateStr, regular, heading,mainHeading, subHeading, dateStrWithTime);
			writer.setPageEvent(event1);
		}else {
			HeaderAndFooterForAvanDataCrf event1 = new HeaderAndFooterForAvanDataCrf(img, dcdDto, dateStr, regular, heading, mainHeading, subHeading, periodNo, messageSource, currentLocale, svr, dateStrWithTime);
			writer.setPageEvent(event1);                                        
		}
	}else {
		HeaderAndFooter event1 = new HeaderAndFooter(img, projectNumber, periodNo, dcdDto.getUser(), dateStr, regular, heading, mainHeading, subHeading, dateStrWithTime);
		writer.setPageEvent(event1);
	}
	document.open();
	
	PdfPCell cell = null;
	PdfPTable table = null;
	String lableStr = "";
	
	table = new PdfPTable(4);
	table.setWidthPercentage(90f);
	table.setWidths(new int[]{10,40,10,40});
	
	lableStr =messageSource.getMessage("label.coverPage2.title", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, subHeading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(4);
	table.addCell(cell);
	
	//FirstRow
	lableStr =messageSource.getMessage("label.coverPage2.Abbreviations", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(4);
	table.addCell(cell);
	
	//SecondRow
	lableStr =messageSource.getMessage("label.coverPage2.bmi", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.bmiAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.m", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.mAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Third Row
	lableStr =messageSource.getMessage("label.coverPage2.bpm", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.bpmAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.min", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.minAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Fourth Row
	lableStr =messageSource.getMessage("label.coverPage2.cm", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.cmAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.mL", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.mLAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	
	//Fifth Row
	lableStr =messageSource.getMessage("label.coverPage2.ECG", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.ECGAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.mmHg", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.mmHgAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Sixth Row
	lableStr =messageSource.getMessage("label.coverPage2.h", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.hAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	Paragraph p = new Paragraph();
	p.add("m");
	Chunk superScript = new Chunk("2");
	superScript.setFont(heading);
	superScript.setTextRise(6f);
	p.add(superScript);
	cell = new PdfPCell();
	cell.addElement(p);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.m2Abr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Seventh Row
	lableStr =messageSource.getMessage("label.cvoerPage2.kg", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.kgAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.N/A", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.N/AAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	
	//Eight Row
	lableStr =messageSource.getMessage("label.coverPage2.brpm", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPge2.brpmAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	Chunk dchunk = new Chunk("o");
	dchunk.setFont(heading);
	dchunk.setTextRise(6f);
	Paragraph p1 = new Paragraph();
	p1.add(dchunk);
	p1.add("C");
	p1.setFont(heading);
	cell = new PdfPCell();
	cell.addElement(p1);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage2.degreeAbr", null,currentLocale);
	cell = new PdfPCell(new Phrase(" =   "+lableStr, regular));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	
	
	document.add(table);
	document.close();
	return filepath;
}

@SuppressWarnings("unused")
private String generateFirstCoverPage(HttpServletRequest request, Locale currentLocale, MessageSource messageSource,
		Map<String, String> proMap, Long langId, DataCrfDtoDetails dcdDto, String dateStr, Font regular, Font heading,
		Font mainHeading, Font subHeading, StudyVolunteerReporting svr, StudyPeriodMaster spm, String dateStrWithTime) throws FileNotFoundException, DocumentException {
	String advimg = request.getServletContext().getRealPath("/static/images/company.PNG");
	String img = request.getServletContext().getRealPath("/static/images/AvantSanteLog.jpg");
	String realPath = request.getSession().getServletContext().getRealPath("/");
	String path = realPath+"//BlankCrfGeneration";
	File file = new File(path);
	if(!file.exists())
		file.mkdirs();
	String filepath = path+"/FinalBlankPdf_CoverPage_"+svr.getId()+"_"+spm.getPeriodNo()+".pdf";
	Document document = new Document();
//    document.setPageSize(PageSize.A4);
	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath)); 
	document.addTitle("FinalBlankPdf");
	document.setPageSize(PageSize.A4);
//	document.setPageSize(PageSize.A4.rotate());
	document.setMargins(40, 40, 20, 90); //A4
//	document.setMargins(10f, 10f, 100f, 0f);//A4 Rotation
	document.setMarginMirroring(false);
	
	String periodNo = spm.getPeriodNo()+"";
	if(periodNo.length() == 1)
		periodNo = "0"+periodNo;
	
	String projectNumber = "";
	String projectTitle = "";
	String sponsorCode = "";
	if(dcdDto.getStudy() != null) {
		projectNumber = dcdDto.getStudy().getProjectNo();
		projectTitle = dcdDto.getStudy().getStudyTitle();
		sponsorCode = dcdDto.getStudy().getSponsorCode();
	}
	ApplicationConfiguration appConfig = dcdDto.getAppConfig();
	if(appConfig != null) {
		if(appConfig.getConfigCode().equals("ADV")) {
			HeaderAndFooter event1 = new HeaderAndFooter(advimg, projectNumber, periodNo, dcdDto.getUser(), dateStr, regular, heading, mainHeading, subHeading, dateStrWithTime);
			writer.setPageEvent(event1);
		}else {
			HeaderAndFooterForAvanDataCrf event1 = new HeaderAndFooterForAvanDataCrf(img, dcdDto, dateStr, regular, heading, mainHeading, subHeading, periodNo, messageSource, currentLocale, svr, dateStrWithTime);
			writer.setPageEvent(event1);
		}
	}else {
		HeaderAndFooter event1 = new HeaderAndFooter(img, projectNumber, periodNo, dcdDto.getUser(),  dateStr, regular, heading, mainHeading, subHeading, dateStrWithTime);
		writer.setPageEvent(event1);
	}
	document.open();
	
	PdfPCell cell = null;
	PdfPTable table = null;
	String lableStr = "";
	
	table = new PdfPTable(4);
	table.setWidthPercentage(100f);
	
	lableStr =messageSource.getMessage("label.coverPage.title", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, subHeading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(4);
	table.addCell(cell);
	
	//First Row
	lableStr =messageSource.getMessage("label.coverPage.date", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setColspan(3);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Second Row
	lableStr =messageSource.getMessage("label.coverPage.proTitle", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase(projectTitle, regular));
	cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setColspan(3);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Third Row
	lableStr =messageSource.getMessage("label.coverPage.subReg", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage.subin", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setFixedHeight(25f);
	table.addCell(cell);
	
	//Forth Row
	
	PdfPTable tempTab = new PdfPTable(4);
	tempTab.setWidthPercentage(100f);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	tempTab.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage.name", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(2);
	tempTab.addCell(cell);
	
	lableStr =messageSource.getMessage("label.coverPage.singedBy", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	tempTab.addCell(cell);
	
	cell = new PdfPCell(tempTab);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setColspan(4);
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Fifth Row
	lableStr =messageSource.getMessage("label.coverPage.preparedBy", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(2);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Sixth Row
	lableStr =messageSource.getMessage("label.coverPage.pireviewed", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(2);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	//Seventh Row
	lableStr =messageSource.getMessage("label.coverPage.qareviewedBy", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(2);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setPadding(7f);
    cell.setNoWrap(false);
	table.addCell(cell);
	
	
	document.add(table);
	document.close();
	return filepath;
}

@SuppressWarnings("unused")
private String generateFirstCoverPageAdv(HttpServletRequest request, Locale currentLocale, MessageSource messageSource,
		Map<String, String> proMap, Long langId, DataCrfDtoDetails dcdDto, String dateStr, Font regular, Font heading,
		Font mainHeading, Font subHeading, StudyVolunteerReporting svr, StudyPeriodMaster spm, String dateStrWithTime, Font regularWithUnderLine) throws FileNotFoundException, DocumentException {
	String advimg = request.getServletContext().getRealPath("/static/images/company.PNG");
	String realPath = request.getSession().getServletContext().getRealPath("/");
	String path = realPath+"//DataCrfGeneration";
	File file = new File(path);
	if(!file.exists())
		file.mkdirs();
	String filepath = path+"/FinalDataCrf_firstCoverPage1_"+svr.getId()+"_"+spm.getPeriodNo()+".pdf";
	Document document = new Document();
//    document.setPageSize(PageSize.A4);
	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
	document.addTitle("FinalBlankPdf");
	document.setPageSize(PageSize.A4);
//	document.setPageSize(PageSize.A4.rotate()); 
	document.setMargins(40, 40, 20, 170); //A4
//	document.setMargins(10f, 10f, 100f, 0f);//A4 Rotation
	document.setMarginMirroring(true);
	document.open();
	
	String periodNo = spm.getPeriodNo()+"";
	if(periodNo.length() == 1)
		periodNo = "0"+periodNo;
	
	String projectNumber = "";
	String projectTitle = "";
	String sponsorCode = "";
	if(dcdDto.getStudy() != null) {
		projectNumber = dcdDto.getStudy().getProjectNo();
		projectTitle = dcdDto.getStudy().getStudyTitle();
		sponsorCode = dcdDto.getStudy().getSponsorCode();
	}
	PdfPTable footTab = new PdfPTable(4);
	footTab.setWidthPercentage(100f);
	footTab.setTotalWidth(500f);
	PdfPCell cell = null;
	String lableStr ="";
	
	cell = new PdfPCell(new Phrase("", heading));
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    lableStr =messageSource.getMessage("label.advCoverName", null,currentLocale);
    cell = new PdfPCell(new Phrase(lableStr, heading));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    lableStr =messageSource.getMessage("label.advCoverJobTitle", null,currentLocale);
    cell = new PdfPCell(new Phrase(lableStr, heading));
    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
	
    lableStr =messageSource.getMessage("label.advCoverSignedBy", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    
    //First Row
    
    lableStr =messageSource.getMessage("label.advCoverPreferedBy", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, heading));
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    lableStr ="";
    if(dcdDto.getProject() != null) { 
    	if(dcdDto.getProject().getCreatedBy() != null) {
    		if(dcdDto.getUserNamesMap() != null && dcdDto.getUserNamesMap().size() > 0) {
    			lableStr = dcdDto.getUserNamesMap().get(dcdDto.getProject().getCreatedBy());
    			if(lableStr == null || lableStr.equals(""))
    				lableStr = dcdDto.getProject().getCreatedBy();
    		}
    	}
    }else lableStr = dcdDto.getProject().getCreatedBy();
     cell = new PdfPCell(new Phrase(lableStr, regular));
	 cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 cell.setPaddingTop(3f);
     cell.setPaddingBottom(7f);
     cell.setPaddingLeft(7f);
     cell.setPaddingRight(7f);
//	cell.setPadding(7f);
     cell.setNoWrap(false);
     footTab.addCell(cell);
	
    lableStr = "Project Incharge";
    cell = new PdfPCell(new Phrase(lableStr, regular));
    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    
    lableStr = "";
    if(dcdDto.getProject() != null) {
    	SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
    	lableStr = sdf.format(dcdDto.getProject().getCreatedOn());
    }
    cell = new PdfPCell(new Phrase(lableStr, regular));
    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    
	
	//Second Row
    lableStr =messageSource.getMessage("label.advCoverApproveBy", null,currentLocale);
    cell = new PdfPCell(new Phrase(lableStr, heading));
    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    lableStr ="";
    if(dcdDto.getAdrPojo() != null) 
    	lableStr = dcdDto.getAdrPojo().getUser().getFullName();
    cell = new PdfPCell(new Phrase(lableStr, regular));
    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
    
    
    lableStr ="";
    if(dcdDto.getAdrPojo() != null) 
    	lableStr = dcdDto.getAdrPojo().getRole().getRole();
    cell = new PdfPCell(new Phrase(lableStr, regular));
    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
	
    lableStr ="";
    if(dcdDto.getAdrPojo() != null) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
		lableStr = sdf.format(dcdDto.getAdrPojo().getDateOfActivity());
    }
    cell = new PdfPCell(new Phrase(lableStr, regular));
    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
    footTab.addCell(cell);
	
	
	HeaderAndFooterForAdvFirstCoverPage firstcoverpageEvent = new HeaderAndFooterForAdvFirstCoverPage(advimg, projectNumber, periodNo, dcdDto.getUser(), dateStr, regular, heading, svr, messageSource, currentLocale, mainHeading, dcdDto, dateStrWithTime, footTab);
	writer.setPageEvent(firstcoverpageEvent);
	
//	PdfPCell cell = null;
	PdfPTable table = null;
//	String lableStr = "";
	Chunk chunk = null;
	
	table = new PdfPTable(4);
	table.setWidthPercentage(100f);
	

	
	lableStr =messageSource.getMessage("label.advCover.proNo", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr+" "+projectNumber, heading));
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(4);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase("", regular));
	cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
	cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBorder(Rectangle.NO_BORDER);
	/*cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);*/
//	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(4);
	cell.setFixedHeight(20f);
	table.addCell(cell);
	
	//First Row
	lableStr =messageSource.getMessage("label.advCoverStudyTitle", null,currentLocale);
	cell = new PdfPCell(new Phrase(lableStr, subHeading));
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setColspan(4);
	table.addCell(cell);
	
	cell = new PdfPCell(new Phrase(projectTitle, regular));
	cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setPaddingTop(3f);
    cell.setPaddingBottom(7f);
    cell.setPaddingLeft(7f);
    cell.setPaddingRight(7f);
//	cell.setPadding(7f);
    cell.setNoWrap(false);
	cell.setColspan(4);
	table.addCell(cell);
	document.add(table);
	document.close();
	return filepath;
}

@Override
public List<String> generateDataCrf(Long periodId, Long subjectId, Long activityId, DataCrfDtoDetails dcdDto,
		MessageSource messageSource, Long langId, String dateStr, Locale currentLocale, HttpServletRequest request, HttpServletResponse response, String dateStrWithTime, String reportType, List<Long> volIds) {
	List<String> filesList = null;
	List<String> finalList = new ArrayList<>();
	String fileName = "";
	try {
//		Map<Long, StudySubjects> subjectsMap = new HashMap<>();
		Map<Integer, StudyPeriodMaster> spmMap = new HashMap<>();
		if(periodId == 0)
			spmMap.putAll(dcdDto.getSpmMap());
//		else spmMap.put(periodId, dcdDto.getSpmMap().get(periodId)); 
		List<Long> coverSubIds = new ArrayList<>();
		if(reportType.equals("subjects")) {
			Map<Long, StudyVolunteerReporting> svrMap = new HashMap<>();
			if(subjectId == 0) {
				for(Map.Entry<Long, StudySubjects> sub : dcdDto.getSubMap().entrySet()) {
					svrMap.put(sub.getValue().getReportingId().getId(), sub.getValue().getReportingId());
				}
			}else {
				svrMap.put(subjectId, dcdDto.getSubMap().get(subjectId).getReportingId());
			}
			if(svrMap != null && svrMap.size() >0) {
				for(Map.Entry<Long, StudyVolunteerReporting> ss : svrMap.entrySet()) {
					filesList = new ArrayList<>();
					for(Map.Entry<Integer, StudyPeriodMaster> spm : spmMap.entrySet()) {
						List<String> coverFilesList = null;
						if(!coverSubIds.contains(ss.getKey())) {
							coverFilesList = generateCoverPagesForDataCrf(request, currentLocale, messageSource, langId, dcdDto, dateStr, ss.getValue(), spm.getValue(), dateStrWithTime);
							coverSubIds.add(ss.getKey());
						}
						fileName = generateCrf(ss.getValue(), spm.getValue(), messageSource, currentLocale, langId, dateStr, request, response, dcdDto, dateStrWithTime);
					    if(!fileName.equals("")) {
					    	if(coverFilesList != null && coverFilesList.size() > 0)
					    		filesList.addAll(coverFilesList);
					    	filesList.add(fileName);
					    }
					}
					if(filesList != null && filesList.size() > 0) {
						SinglePdfConversionForDataCrf gsp = new SinglePdfConversionForDataCrf();
						String fname = gsp.convertSinglePdf(request, response, filesList, messageSource, currentLocale, ss.getValue());
						if(!fname.equals(""))
							finalList.add(fname);
					}
				}
			}else {
				/*Font red = new Font(FontFamily.TIMES_ROMAN, 13, Font.BOLD, BaseColor.RED);
				String lableStr2 =messageSource.getMessage("label.nodataAvl", null,currentLocale);
				cell = new PdfPCell(new Phrase(lableStr2, red));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				mainTab.addCell(cell);
				document.add(mainTab);*/
			}
		}else {
			if(volIds != null && volIds.size() > 0) {
				for(Long vol : volIds) {
					if(vol != 0) {
						filesList = new ArrayList<>();
						StudyVolunteerReporting svrPojo = dcdDto.getStdVolMap().get(vol);
						for(Map.Entry<Integer, StudyPeriodMaster> spm : spmMap.entrySet()) {
							List<String> coverFilesList = null;
							if(!coverSubIds.contains(svrPojo.getId())) {
								coverFilesList = generateCoverPagesForDataCrf(request, currentLocale, messageSource, langId, dcdDto, dateStr, svrPojo, spm.getValue(), dateStrWithTime);
								coverSubIds.add(svrPojo.getId());
							}
							fileName = generateCrf(svrPojo, spm.getValue(), messageSource, currentLocale, langId, dateStr, request, response, dcdDto, dateStrWithTime);
						    if(!fileName.equals("")) {
						    	if(coverFilesList != null && coverFilesList.size() > 0)
						    		filesList.addAll(coverFilesList);
						    	filesList.add(fileName);
						    }
						}
						if(filesList != null && filesList.size() > 0) {
							SinglePdfConversionForDataCrf gsp = new SinglePdfConversionForDataCrf();
							String fname = gsp.convertSinglePdf(request, response, filesList, messageSource, currentLocale, svrPojo);
							if(!fname.equals(""))
								finalList.add(fname);
						}
					}
				}
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return finalList;
}

private String generateCrf(StudyVolunteerReporting svr, StudyPeriodMaster spm, MessageSource messageSource, Locale currentLocale,
		Long langId, String dateStr, HttpServletRequest request, HttpServletResponse response,
		DataCrfDtoDetails dcdDto, String dateStrWithTime) {
	String filepath = "";
	String periodNo = spm.getPeriodNo()+"";
	if(periodNo.length() == 1)
		periodNo = "0"+periodNo;
	TreatmentInfo treatmentInfo = null;
	try {
		String chkcbPath = request.getServletContext().getRealPath("/static/images/checkedCB.png");
		String unchkcbPath = request.getServletContext().getRealPath("/static/images/uncheckedCB.png");
		String chkrdPath = request.getServletContext().getRealPath("/static/images/radiobtnChecked8.png"); 
		String unchkrdPath = request.getServletContext().getRealPath("/static/images/radioUncheck.png");
		String advimg = request.getServletContext().getRealPath("/static/images/company.PNG");
		String img = request.getServletContext().getRealPath("/static/images/AvantSanteLog.jpg");
		String realPath = request.getSession().getServletContext().getRealPath("/");
		
		@SuppressWarnings("unused")
		int fontNos = FontFactory.registerDirectory("C:\\WINDOWS\\Fonts");
		Font calibri = FontFactory.getFont("Calibri");
		Font mainHeading = new Font(calibri.getFamily(), 14, Font.BOLD);
		Font subHeading = new Font(calibri.getFamily(), 12, Font.BOLD);
//		Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
		Font regular = new Font(calibri.getFamily(), 9);
		Font heading = new Font(calibri.getFamily(), 9, Font.BOLD);
		Font actHeadFont = new Font(calibri.getFamily(), 12, Font.BOLD, BaseColor.BLACK);
		BaseColor bgColor = new BaseColor(211, 211, 211); //#D3D3D3
		
		
		//Checkbox image
		Image unchkdCbImage = Image.getInstance(unchkcbPath);
		unchkdCbImage.setAlignment(Element.ALIGN_CENTER);
		unchkdCbImage.scaleAbsolute(8, 8);
		
		Image chkedCbImage = Image.getInstance(chkcbPath);
		chkedCbImage.setAlignment(Element.ALIGN_CENTER);
		chkedCbImage.scaleAbsolute(12, 12);
		//Radio Image
		Image unchkradioimg = Image.getInstance(unchkrdPath);
		unchkradioimg.scaleAbsolute(8, 8);
		
		Image chkedradioimg = Image.getInstance(chkrdPath);
		chkedradioimg.scaleAbsolute(8, 8);
		
		String path = realPath+"//DataCrf";
		File file = new File(path);
		if(!file.exists())
			file.mkdirs();
		filepath = path+"/FinalDataPdf_"+svr.getId()+"_"+periodNo+".pdf";
		Document document = new Document();
//	    document.setPageSize(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
		document.addTitle("FinalCRFf");
		document.setPageSize(PageSize.A4);
//		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(40, 40, 20, 80); //A4
//		document.setMargins(10f, 10f, 100f, 0f);//A4 Rotation
		document.setMarginMirroring(false);
//		ApplicationConfiguration apConfig = projectCrfGenerationDao.getApplicationConfigurationRecord("Pdf Header");
		ApplicationConfiguration appConfig = dcdDto.getAppConfig();
		HeaderAndFooterDataCrf advHeader = null;
		HeaderAndFooterForAvanDataCrf avanHeader = null;
		if(appConfig != null) {
			if(appConfig.getConfigCode().equals("ADV")) {
				advHeader = new HeaderAndFooterDataCrf(advimg,dcdDto, periodNo, dcdDto.getUser(), dateStr, regular, heading, mainHeading, subHeading, svr, dateStrWithTime);
				writer.setPageEvent(advHeader);
			}else {
				avanHeader = new HeaderAndFooterForAvanDataCrf(img, dcdDto, dateStr, regular, heading, mainHeading, subHeading, periodNo, messageSource, currentLocale, svr, dateStrWithTime);
				writer.setPageEvent(avanHeader);
			}
		}else {
			advHeader = new HeaderAndFooterDataCrf(advimg, dcdDto, periodNo, dcdDto.getUser(), dateStr, regular, heading, mainHeading, subHeading,svr, dateStrWithTime);
			writer.setPageEvent(advHeader);
		}
		document.open();
		
		PdfPTable mainTab = new PdfPTable(2);
		mainTab.setWidthPercentage(100f);
		PdfPCell cell = null;
		
		List<LanguageSpecificGlobalActivityDetails> gaList = new ArrayList<>();
//		TreatmentInfo treatmentInfo = dataCrfDao.getTreatmentInfoDetails(1L);
		if(dcdDto.getSubwtrMap() != null && dcdDto.getSubwtrMap().size() > 0) {
			if(dcdDto.getSubwtrMap().get(svr.getId()) != null)
				treatmentInfo = dcdDto.getSubwtrMap().get(svr.getId()).get(spm.getId());
		}
		Map<Long, Map<Long, Set<Long>>> sactMap = dcdDto.getSactMap();
		Map<Long, Map<Long, Map<Long, List<Long>>>> sactParamMap = dcdDto.getSactParamMap();
		if(treatmentInfo != null) {
			for(Map.Entry<Long, LanguageSpecificGlobalActivityDetails> actMap : dcdDto.getGaMap().entrySet()) {
				if(actMap.getValue().getGlobalActivity().getActivityCode() != null) {
					if(actMap.getValue().getGlobalActivity().getActivityCode().equals(com.covideinfo.enums.StudyActivities.DosingCollection.toString())
							|| actMap.getValue().getGlobalActivity().getActivityCode().equals(com.covideinfo.enums.StudyActivities.MealsCollection.toString())
							|| actMap.getValue().getGlobalActivity().getActivityCode().equals(com.covideinfo.enums.StudyActivities.SampleCollection.toString())
							|| actMap.getValue().getGlobalActivity().getActivityCode().equals(com.covideinfo.enums.StudyActivities.StudyExecutionVitals.toString())) {
							gaList.add(actMap.getValue());
						}
				}
			}
			/*if(sactMap != null && sactMap.size() > 0) {
				Map<Long, Set<Long>> tempMap = sactMap.get(spm.getId()); 
				if(tempMap != null && tempMap.size() > 0) {
					Set<Long> actIds = tempMap.get(treatmentInfo.getId());
					if(actIds != null && actIds.size() > 0) {
						for(Long actNo : actIds) {
							LanguageSpecificGlobalActivityDetails lsgad = dcdDto.getGaMap().get(actNo);
							if(lsgad != null) 
								gaList.add(lsgad);
						}
					}
				}
			}*/
		}
		if(treatmentInfo == null) {
			Long treatmentId = dcdDto.getTreatmentMinId();
			if(treatmentId != null) {
				treatmentInfo = dcdDto.getTreatmentsMap().get(treatmentId);
			}
		}
		if(sactMap != null && sactMap.size() > 0) {
			Map<Long, Set<Long>> tempMap = sactMap.get(spm.getId()); 
			if(tempMap != null && tempMap.size() > 0) {
				Set<Long> actIds = tempMap.get(treatmentInfo.getId());
				if(actIds != null && actIds.size() > 0) {
					for(Long actNo : actIds) {
						LanguageSpecificGlobalActivityDetails lsgad = dcdDto.getGaMap().get(actNo);
						if(lsgad != null) 
							gaList.add(lsgad);
					}
				}
			}
		}
		int count =0;
		if(gaList != null && gaList.size() > 0) {
			Collections.sort(gaList);
			SimpleDateFormat sdf = new SimpleDateFormat(dateStr);
			if(!appConfig.getConfigCode().equals("ADV")) {
				//Reporting Date and time and Check-in time and date
				String lableStr =messageSource.getMessage("label.avanPdf.redt", null,currentLocale);
				cell = new PdfPCell(new Phrase(lableStr+" :", subHeading));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
			    cell.setNoWrap(false);
				mainTab.addCell(cell);
				
				String reportingDate = sdf.format(svr.getCreatedOn());
				cell = new PdfPCell(new Phrase(reportingDate, subHeading));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//					cell.setPadding(7f);
			    cell.setNoWrap(false);
				mainTab.addCell(cell);
				
				
				String lableStr2 =messageSource.getMessage("label.avanChicken.text", null,currentLocale);
				cell = new PdfPCell(new Phrase(lableStr2+" :", subHeading));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//					cell.setPadding(7f);
				mainTab.addCell(cell);
				
				StudySubjects subject = dcdDto.getSubMap().get(svr.getId());
				String chickinDate = "";
				if(subject != null)
					chickinDate = sdf.format(subject.getCreatedOn());
				cell = new PdfPCell(new Phrase(chickinDate, subHeading));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//					cell.setPadding(7f);
			    cell.setNoWrap(false);
				mainTab.addCell(cell);
				document.add(mainTab);
				
				PdfPTable spTab = new PdfPTable(1);
				spTab.setWidthPercentage(100f);
				cell = new PdfPCell(new Phrase(""));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(10f);
			    cell.setNoWrap(false);
				spTab.addCell(cell);
				document.add(spTab);
			}
			
			if(count !=0) {
				PdfPTable spaceTab = new PdfPTable(1);
				spaceTab.setWidthPercentage(100f);
				cell = new PdfPCell(new Phrase(""));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
			    cell.setNoWrap(false);
			    cell.setFixedHeight(7f);
				spaceTab.addCell(cell);
				document.add(spaceTab);
				count++;
			}
			if(count == 0)
				count++;
			for(LanguageSpecificGlobalActivityDetails lsga : gaList) {
				//if show pdf yes
				if(lsga.getGlobalActivity().isShowInPDF()) {
					List<StudyCheckInActivityDataDetails> schkInList = new ArrayList<>();
					List<StudyCheckOutActivityDataDetails> schkOutList = new ArrayList<>();
					List<StudyExecutionActivityDataDetails> sexeList = new ArrayList<>();
//					TreatmentInfo tf = dcdDto.getSubwtrMap().get(svr.getId()).get(spm.getId());
					Map<Long,Map<Long, List<StudyCheckInActivityDataDetails>>> schkInMap = dcdDto.getChickInMap().get(svr.getId());
					Map<Long,Map<Long, List<StudyCheckOutActivityDataDetails>>> schkOutMap = dcdDto.getCheckOutMap().get(svr.getId());
					Map<Long,Map<Long, List<StudyExecutionActivityDataDetails>>> sexeMap = dcdDto.getStdexeMap().get(svr.getId());
//					if(tf != null) {
						if(schkInMap != null && schkInMap.size() > 0) {
							Map<Long, List<StudyCheckInActivityDataDetails>> tempMap1 = schkInMap.get(spm.getId());
							if(tempMap1 != null && tempMap1.size() > 0) {
								List<StudyCheckInActivityDataDetails> tempList = tempMap1.get(lsga.getGlobalActivity().getId());
								if(tempList != null)
									schkInList.addAll(tempList);
							}
						}
							
						
						if(schkOutMap != null && schkOutMap.size() > 0) { 
							Map<Long, List<StudyCheckOutActivityDataDetails>> tempMap1 = schkOutMap.get(spm.getId());
							if(tempMap1 != null && tempMap1.size() > 0) {
								List<StudyCheckOutActivityDataDetails> tempList = tempMap1.get(lsga.getGlobalActivity().getId());
								if(tempList != null)
									schkOutList.addAll(tempList);
							}
						}
						
						if(sexeMap != null && sexeMap.size() > 0) {
							Map<Long, List<StudyExecutionActivityDataDetails>> tempMap1 = sexeMap.get(spm.getId());
							if(tempMap1 != null && tempMap1.size() > 0) {
								List<StudyExecutionActivityDataDetails> tempList = tempMap1.get(lsga.getGlobalActivity().getId());
								if(tempList != null)
									sexeList.addAll(tempList);
							}
						}
							
						
//					}
					CrfDataActivityDto cdaDto = null;
					if(schkInList != null && schkInList.size() > 0) {
						cdaDto = getCrfDataActivityDtoDetails(schkInList, schkOutList, sexeList, dcdDto.getGpMap(), svr.getId());
					}else if(schkOutList != null && schkOutList.size() > 0) {
						cdaDto = getCrfDataActivityDtoDetails(schkInList, schkOutList, sexeList, dcdDto.getGpMap(), svr.getId());
					}else if(sexeList != null && sexeList.size() > 0) {
						cdaDto = getCrfDataActivityDtoDetails(schkInList, schkOutList, sexeList, dcdDto.getGpMap(), svr.getId());
					}
					
					//Checking activity Phase
					DefaultActivitys dac = dcdDto.getDefalutActMap().get(lsga.getGlobalActivity().getId());
					String phase = "";
					if(dac != null)
					   phase = dac.getStudyPhases().getCode();
					boolean phaseFlag = false;
					if(phase != null && (phase.equals("P"+periodNo+"O") || phase.equals("SCI") || phase.equals("SCO") || phase.equals("SE"))) {
						phaseFlag = true;
					}else phaseFlag = true;
					if(phaseFlag) {
						boolean flag  = true;
						if((lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("InclusionCriteria") ||
								lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("ExclusionCriteria")) && spm.getPeriodNo() > 1) {
							flag = false;
						}
						if(lsga.getGlobalActivity().getActivityCode().equals(com.covideinfo.enums.StudyActivities.StudyWithDraw.toString())) {
							document.setPageSize(PageSize.A4);
							generateDataActivityPdfForStaticActivities(lsga, cdaDto, dcdDto, langId, document,unchkdCbImage, chkedCbImage, unchkradioimg, chkedradioimg, regular, heading, bgColor, mainHeading, subHeading, actHeadFont, spm, sactParamMap, treatmentInfo, dateStrWithTime, svr.getId());
							activitySeparation(document);
							advHeader.setRotation(PdfPage.PORTRAIT);
							flag = false;
						}
						if(flag) {
							if(!lsga.getGlobalActivity().getActivityCode().equals(com.covideinfo.enums.StudyActivities.SkinSensivity.toString())
									&& !lsga.getGlobalActivity().getActivityCode().equals(com.covideinfo.enums.StudyActivities.SkinAdhesion.toString())
									&& !lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("DosingCollection")
									&& !lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("MealsCollection")
									&& !lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("SampleCollection")
									&& !lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("StudyExecutionVitals")) {
								document.setPageSize(PageSize.A4);
								mainTab = new PdfPTable(2);
								mainTab.setWidthPercentage(100f);
								generateDataActivityPdf(lsga, cdaDto, dcdDto, langId, document,unchkdCbImage, chkedCbImage, unchkradioimg, chkedradioimg, regular, heading, bgColor, mainHeading, subHeading, actHeadFont, spm, sactParamMap, treatmentInfo, dateStrWithTime, svr.getId());
								activitySeparation(document);
								advHeader.setRotation(PdfPage.PORTRAIT);
							}
							//DosingTime Points
							if(lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("DosingCollection")) {
								Map<Long, Map<Long, Map<Long,List<SubjectDoseTimePoints>>>> sdtpMap = dcdDto.getSdtpMap();
								if(sdtpMap != null && sdtpMap.size() > 0){
									StudySubjects subject = dcdDto.getSubMap().get(svr.getId());
									if(subject != null) {
										if(sdtpMap.get(subject.getId()) != null) {
//											if(sdtpMap.get(subject.getId()).get(spm.getId()) !=  null) {
												document.setPageSize(PageSize.A4.rotate());
												document.newPage();
												if(!dcdDto.getAppConfig().getConfigCode().equals("ADV")) {
													dosingRecordForAvan(document, regular, unchkdCbImage, chkedCbImage, chkedradioimg, unchkradioimg, heading, currentLocale, messageSource, langId, lsga, bgColor, mainHeading, subHeading, actHeadFont, dcdDto, svr, spm, dateStrWithTime, dateStr);
												}else {
													dosingRecordForAdvity(document, regular, unchkdCbImage, chkedCbImage, chkedradioimg, unchkradioimg, heading, currentLocale, messageSource, langId, lsga, bgColor, mainHeading, subHeading, actHeadFont, dcdDto, svr, spm, dateStrWithTime, dateStr);
												}
												activitySeparation(document);
												advHeader.setRotation(PdfPage.LANDSCAPE);
												document = placeLogoForModifiedDocument(document, advimg);
//											}
										}
									}
									
								}
							}
							
							//MealsRecords 
							if(lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("MealsCollection")) { 
								document.setPageSize(PageSize.A4.rotate());
								document.newPage();
								advHeader.setRotation(PdfPage.LANDSCAPE);
								generateMealRecordPdf(dcdDto, document, regular, unchkdCbImage, chkedCbImage, chkedradioimg, unchkradioimg, heading, currentLocale, messageSource, lsga, bgColor, appConfig, mainHeading, subHeading, actHeadFont, svr, spm, dateStr, dateStrWithTime);
//								activitySeparation(document);
								advHeader.setRotation(PdfPage.LANDSCAPE);
								document = placeLogoForModifiedDocument(document, advimg);
							}
								//Blood Samples Or Sample Points
							if(lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("SampleCollection")) { 
								document.setPageSize(PageSize.A4.rotate());
								document.newPage();
								generateBloodSheetPdf(dcdDto, document, regular, chkedCbImage, unchkdCbImage, chkedradioimg, unchkradioimg, heading, regular, mainHeading, subHeading, messageSource, currentLocale, langId, actHeadFont, bgColor, lsga, svr, spm, dateStr, dateStrWithTime);
								activitySeparation(document);
								advHeader.setRotation(PdfPage.LANDSCAPE);
								document = placeLogoForModifiedDocument(document, advimg);
							}
														
							//Vitals
							if(lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("StudyExecutionVitals")) {
								Map<Long, List<VitalTimePoints>> vtpMap = dcdDto.getVtpMap();
								if(vtpMap != null && vtpMap.size() > 0) {
									document.setPageSize(PageSize.A4.rotate());
									document.newPage();
									generateVitalPdf(dcdDto, document, regular, unchkdCbImage, chkedCbImage, chkedradioimg, unchkradioimg, heading,langId, messageSource, currentLocale, lsga, bgColor, mainHeading, subHeading, actHeadFont, svr, spm, dateStrWithTime, dateStr);
									activitySeparation(document);
									advHeader.setRotation(PdfPage.LANDSCAPE);
									document = placeLogoForModifiedDocument(document, advimg);
								}
							}
							//SkinAdhection
							/*if(lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("SkinAdhesion")) {
								Map<Integer, List<StudyActivityTimpointsSavingDto>> skinAdhMap = getTreatmentWiseTimePoints(clinalInfo.getSkinAdhFinalMap());
								if(skinAdhMap.size() > 0) {
									for(Map.Entry<Integer, List<StudyActivityTimpointsSavingDto>> skAdhMap : skinAdhMap.entrySet()) {
										generateSkinAdhectionPdf(skAdhMap.getValue(), document, regular, unchkImage, unchkradioimg, heading,pdpgDto.getInalg(), studyCreationBo, "SkingAdhection", messageSource, currentLocale, lsga, bgColor, mainHeading, subHeading, actHeadFont);
									}
								}
							}*/
							//SkinSensitivity
							/*if(lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("SkinSensivity")) {
								Map<Integer, List<StudyActivityTimpointsSavingDto>> skinSenMap = getTreatmentWiseTimePoints(clinalInfo.getSkinSenFinalMap());
								if(skinSenMap.size() > 0) {
									for(Map.Entry<Integer, List<StudyActivityTimpointsSavingDto>> skinSen : skinSenMap.entrySet()) {
										generateSkinAdhectionPdf(skinSen.getValue(), document, regular, unchkImage, unchkradioimg, heading, pdpgDto.getInalg(), studyCreationBo, "SkinSensitivity", messageSource, currentLocale, lsga, bgColor, mainHeading, subHeading, actHeadFont);
									}
								}
							}*/
							//Ecg TimePoints
							/*if(lsga.getGlobalActivity().getActivityCode().equalsIgnoreCase("EcgTimePoints")) {
								Map<Integer, List<StudyActivityTimpointsSavingDto>> ecgMap = getTreatmentWiseTimePoints(clinalInfo.getTwSatEcgMap());
								if(ecgMap.size() > 0) {
									for(Map.Entry<Integer, List<StudyActivityTimpointsSavingDto>> ecg : ecgMap.entrySet()) {
										generateSkinAdhectionPdf(ecg.getValue(), document, regular, unchkImage, unchkradioimg, heading, pdpgDto.getInalg(), studyCreationBo, "EcgCollection", messageSource, currentLocale, lsga, bgColor, mainHeading, subHeading, actHeadFont);
									}
								}
							}*/
							
						}
					}
				}
			}
		}
		document.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return filepath;
}

private Document placeLogoForModifiedDocument(Document document, String imgLoc) {
	try {
		Image img = Image.getInstance(imgLoc);
		img.setAbsolutePosition(40, 550); 
	    img.scaleAbsolute(80, 30);
	    document.add(img);
	} catch (Exception e) {
		e.printStackTrace();
		return document;
	}
	return document;
	
}

private void activitySeparation(Document document) {
	PdfPCell cell = null;
	try {
		PdfPTable spTab2 = new PdfPTable(1);
	    spTab2.setWidthPercentage(100f);
		cell = new PdfPCell(new Phrase(""));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setFixedHeight(7f);
	    cell.setNoWrap(false);
	    spTab2.addCell(cell);
		document.add(spTab2);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}

private void generateDataActivityPdfForStaticActivities(LanguageSpecificGlobalActivityDetails lsga,
		CrfDataActivityDto cdaDto, DataCrfDtoDetails dcdDto, Long langId, Document document, Image unchkdCbImage,
		Image chkedCbImage, Image unchkradioimg, Image chkedradioimg, Font regular, Font heading, BaseColor bgColor,
		Font mainHeading, Font subHeading, Font actHeadFont, StudyPeriodMaster spm,
		Map<Long, Map<Long, Map<Long, List<Long>>>> sactParamMap, TreatmentInfo treatmentInfo, String dateStrWithTime, long volId) throws DocumentException {
	PdfPTable mainTab = new PdfPTable(2);
	mainTab.setWidthPercentage(100f);
	PdfPCell cell = null;
	ActivityEntryDetailsDto aedDto = null;
	SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
	Map<Long, SubjectWithdrawDetails> subjectWithDrawMap = dcdDto.getSubjectWithDrawMap().get(lsga.getGlobalActivity().getId());
	if(subjectWithDrawMap != null && subjectWithDrawMap.size() > 0) {
		SubjectWithdrawDetails swd = subjectWithDrawMap.get(spm.getId());
		if(swd != null) {
			Map<Long, StaticActivityDataDetails> saddMap = new HashMap<>();
			List<StaticActivityDataDetails> saddList = dcdDto.getStaticDataActMap().get(swd.getId());
			if(saddList != null && saddList.size() > 0) {
				for(StaticActivityDataDetails sadd : saddList) {
					saddMap.put(sadd.getStaticActDetailsId().getId(), sadd);
				}
			}
			List<StaticActivityDetails> sadList = dcdDto.getSaticActMap().get(swd.getWithdrawType());
			if(sadList != null && sadList.size() > 0) {
				if(lsga.getGlobalActivity().isHeadding()) {
					String[] words = lsga.getName().split(" ");
					StringBuilder builder = new StringBuilder();
					for (int i = 0; i < words.length; i++) {
						String st = "";
						if(!words[i].trim().equals("")) {
							if(i==0)
							     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
								else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
							    builder.append(st);
						}
					}
					cell = new PdfPCell(new Phrase(lsga.getName()+"", actHeadFont));
//						cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setBackgroundColor(bgColor);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    
//					    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setColspan(2);
				    mainTab.addCell(cell);
				    mainTab.getDefaultCell().setBackgroundColor(bgColor);
				}
				for(StaticActivityDetails sad : sadList) {
					Paragraph p = new Paragraph();
					List<StaticActivityValueDetails> savdList = dcdDto.getStaticActValsMap().get(sad.getId());
					if(savdList != null && savdList.size() > 0) {
						PdfPTable table = new PdfPTable(savdList.size());
						table.setWidthPercentage(100f);
						
						cell = new PdfPCell(new Phrase(sad.getQueryName(), regular));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						cell.setPadding(7f);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						mainTab.addCell(cell);
						for(StaticActivityValueDetails savd : savdList) {
							Phrase pp = new Phrase(100);
							Chunk radoButton = null;
							if(saddMap.get(savd.getStActDetailsId().getId()) != null && saddMap.get(savd.getStActDetailsId().getId()).getSaticActValueDetailsId().getId() == savd.getId()) {
								radoButton = new Chunk(chkedradioimg, 0, 0, true);
								p.add(radoButton);
							}else {
								radoButton = new Chunk(unchkradioimg, 0, 0, true);
								p.add(radoButton);
							}
							p.add(" ");
							p.setAlignment(Element.WRITABLE_DIRECT);
							Paragraph pg = new Paragraph(savd.getValueName()+" ", regular);
//									pg.setAlignment(Element.ALIGN_CENTER);
							pg.setAlignment(Element.WRITABLE_DIRECT);
							pp.add(pg);
							p.add(pp);
						}
						cell = new PdfPCell();
						cell.addElement(p);
						cell.setNoWrap(false);
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
					    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						mainTab.addCell(cell);
					}else {
						Phrase pp = new Phrase(100);
						Chunk radoButton = null;
						if(saddMap.get(sad.getId()) != null) {
							radoButton = new Chunk(chkedCbImage, 0, 0, true);
							p.add(radoButton);
						}else {
							radoButton = new Chunk(unchkdCbImage, 0, 0, true);
							p.add(radoButton);
						}
						p.add(" ");
						p.setAlignment(Element.WRITABLE_DIRECT);
						Paragraph pg = new Paragraph(sad.getQueryName()+" ", regular);
//						pg.setAlignment(Element.ALIGN_CENTER);
						pg.setAlignment(Element.WRITABLE_DIRECT);
						pp.add(pg);
						p.add(pp);
						cell = new PdfPCell();
						cell.addElement(p);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						cell.setPadding(7f);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setColspan(2);
						mainTab.addCell(cell);
					}
				}
				cell = new PdfPCell(new Phrase("Comments :", regular));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				mainTab.addCell(cell);
				
				cell = new PdfPCell(new Phrase(swd.getWithdrawComments(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				mainTab.addCell(cell);
			}
			if(dcdDto != null && dcdDto.getActPerformedMap().size() > 0) {
				if(dcdDto.getActPerformedMap().get(volId) != null) {
					if(dcdDto.getActPerformedMap().get(volId).get(spm.getId()) != null) {
						if(dcdDto.getActPerformedMap().get(volId).get(spm.getId()).get(lsga.getGlobalActivity().getId()) != null)
							aedDto = dcdDto.getActPerformedMap().get(volId).get(spm.getId()).get(lsga.getGlobalActivity().getId());
					}
				}
			}
			if(aedDto != null && aedDto.getPerformedBy() != null) {
				if(dcdDto.getUserNamesMap() != null && dcdDto.getUserNamesMap().size() > 0) {
					cell = new PdfPCell(new Phrase("Performed By : "+dcdDto.getUserNamesMap().get(aedDto.getPerformedBy()), regular));
				}else cell = new PdfPCell(new Phrase("Performed By : "+aedDto.getPerformedBy(), regular));
			}else cell = new PdfPCell(new Phrase("Performed By : "+"", regular));
//			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		    cell.setBackgroundColor(bgColor);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setNoWrap(false);
		    mainTab.addCell(cell);
		    
		    String date = "";
		    if(aedDto != null && aedDto.getPerformedOn() != null) {
		    	date = sdf.format(aedDto.getPerformedOn());
		    }
		    cell = new PdfPCell(new Phrase("Performed Date : "+date, regular));
//		    cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		    cell.setBackgroundColor(bgColor);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setNoWrap(false);
		    mainTab.addCell(cell);
		    document.add(mainTab);
		}
	}
	mainTab.setHeaderRows(1);
	document.add(mainTab);
}

private void generateVitalPdf(DataCrfDtoDetails dcdDto, Document document, Font regular, Image unchkdCbImage,
		Image chkedCbImage, Image chkedradioimg, Image unchkradioimg, Font heading, Long langId,
		MessageSource messageSource, Locale currentLocale, LanguageSpecificGlobalActivityDetails lsga,
		BaseColor bgColor, Font mainHeading, Font subHeading, Font actHeadFont, StudyVolunteerReporting svr,
		StudyPeriodMaster spm, String dateStrWithTime, String dateString) throws DocumentException {
	PdfPTable mainTab = new PdfPTable(1);
	mainTab.setWidthPercentage(100f);
	PdfPCell cell = null;
	String headStr = "";
	int columns =7;
	Map<String, List<VitalTimePoints>> positionVptMap = new HashMap<>();
	try {
		StudySubjects subject = dcdDto.getSubMap().get(svr.getId());
		TreatmentInfo tinf = null;
		Map<Long, TreatmentInfo> tfMap = null;
		if(subject != null) {
			tfMap = dcdDto.getSubwtrMap().get(subject.getReportingId().getId());
			if(tfMap != null && tfMap.size() > 0) {
				if(tfMap != null && tfMap.size() > 0) {
					tinf = tfMap.get(spm.getId());
				}
			}
		}
		Map<String, Integer> positionMaxParamMap = new HashMap<>();
		Map<Integer, Set<Long>> vtalParamIdsMap = new HashMap<>();
		List<VitalTimePoints> vitalTpList = new ArrayList<>();
		int maxParamNo = 0;
		if(tinf != null) {
			vitalTpList = dcdDto.getVtpMap().get(tinf.getId());
			if(vitalTpList == null || vitalTpList.size() ==0) {
				if(tfMap != null && tfMap.size() > 0) {
					for(Map.Entry<Long, TreatmentInfo> tmap : tfMap.entrySet()) {
						List<VitalTimePoints> vitalTpTempList = dcdDto.getVtpMap().get(tmap.getValue().getId());
						if(vitalTpTempList != null && vitalTpTempList.size() > 0) {
							vitalTpList.addAll(vitalTpTempList);
						}
					}
				}
			}
			List<VitalTimePoints> tempVtpList = null;
			if(vitalTpList != null && vitalTpList.size() > 0) {
				for(VitalTimePoints vtp : vitalTpList) {
					Set<String> positionList = new HashSet<>();
					String paramStr = vtp.getParameterIds();
					Integer paramIdsCount = 0;
					Set<Long> paramIds = new HashSet<>();
					if(paramStr != null && !paramStr.equals("")) {
						String[] temp = paramStr.split("\\,");
						paramIdsCount = temp.length;
						if(temp.length > 0) {
							for(String st : temp) {
								paramIds.add(Long.parseLong(st));
							}
						}
					}
					if(vtp.isOrthostatic()) {
						if(vtp.getOrthostaticPosition() != null) { 
							positionList.add(vtp.getOrthostaticPosition().getFieldValue());
							if(maxParamNo < paramIdsCount) {
								maxParamNo = paramIdsCount;
								VitalParameterDto vpDto = getMaxParametersMapDetails(vtp.getOrthostaticPosition().getFieldValue(), paramIdsCount, paramIds, vtalParamIdsMap, positionMaxParamMap);
								positionMaxParamMap = vpDto.getPositionMaxParamMap();
								vtalParamIdsMap = vpDto.getVtalParamIdsMap();
							}
						}
						positionList.add(vtp.getVitalPosition().getFieldValue());
						if(maxParamNo < paramIdsCount) {
							maxParamNo = paramIdsCount;
							VitalParameterDto vpDto = getMaxParametersMapDetails(vtp.getVitalPosition().getFieldValue(), paramIdsCount, paramIds, vtalParamIdsMap, positionMaxParamMap);
							positionMaxParamMap = vpDto.getPositionMaxParamMap();
							vtalParamIdsMap = vpDto.getVtalParamIdsMap();
						}
							
					}else {
						positionList.add(vtp.getVitalPosition().getFieldValue());
						if(maxParamNo < paramIdsCount) {
							maxParamNo = paramIdsCount;
							VitalParameterDto vpDto = getMaxParametersMapDetails(vtp.getVitalPosition().getFieldValue(), paramIdsCount, paramIds, vtalParamIdsMap, positionMaxParamMap);
							positionMaxParamMap = vpDto.getPositionMaxParamMap();
							vtalParamIdsMap = vpDto.getVtalParamIdsMap();
						}
							
						
					}
					
					if(positionList.size() > 0) {
						for(String st : positionList) {
							if(positionVptMap.containsKey(st)) {
								tempVtpList = positionVptMap.get(st);
								tempVtpList.add(vtp);
								positionVptMap.put(st, tempVtpList);
							}else {
								tempVtpList = new ArrayList<>();
								tempVtpList.add(vtp);
								positionVptMap.put(st, tempVtpList);
							}
						}
					}
				}
			}
			Map<Long, Map<String, SubjectVitalTimePointsData>> subvtDataMap = null;
			if(dcdDto.getVitalMap().get(subject.getId()) != null ) {
				if(dcdDto.getVitalMap().get(subject.getId()).get(spm.getId()) != null) {
						subvtDataMap = dcdDto.getVitalMap().get(subject.getId()).get(spm.getId()); //vtpId, Position, subjectVitalDataPojo
				}
			}
			if(positionVptMap.size() > 0) {
				for(Map.Entry<String, List<VitalTimePoints>> vtpMap : positionVptMap.entrySet()) {
					int defalultRows = 2;
					List<VitalTimePoints> vitalList = vtpMap.getValue();
					PdfPTable vitalTab = null;
					if(vitalList != null && vitalList.size() > 0) {
			    		Collections.sort(vitalList);
						List<LanguageSpecificGlobalParameterDetails> lsgpList = null;
						Set<Long> vtparamIds = new HashSet<>();
						if(vtalParamIdsMap.size() > 0)
							vtparamIds = vtalParamIdsMap.get(maxParamNo);
						vitalTab = new PdfPTable(columns+maxParamNo);
						vitalTab.setWidthPercentage(100f);
						/*for(VitalTimePoints  vt : vitalList) {
							String vtpStr = vt.getParameterIds();
							if(vtpStr != null && !vtpStr.equals("")) {
								String[] tempArr = vtpStr.split("\\,");
								if(tempArr.length > 0) {
									for(String st : tempArr) {
										if(st != null && !st.equals("")) {
											vtparamIds.add(Long.parseLong(st));
										}
									}
										
								}
							}
						}*/
						
						if(vtparamIds.size() > 0)
							lsgpList = dataCrfDao.getLanguageSpecificGlobalParametersFromStudyActivitParameters(dcdDto.getStudy().getId(), langId, vtparamIds);
						/*PdfPTable vitalTab = null;
						if(lsgpList != null && lsgpList.size() > 0) {
					    	vitalTab = new PdfPTable(lsgpList.size()+4);
					    	columns = lsgpList.size()+4;
					    }else 
					    	vitalTab = new PdfPTable(columns);
						vitalTab.setWidthPercentage(100f);*/
						
						if(lsga.getGlobalActivity().isHeadding()) {
							@SuppressWarnings("unused")
							Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(255,255,255));
							String[] words = lsga.getName().split(" ");
							StringBuilder builder = new StringBuilder();
							for (int i = 0; i < words.length; i++) {
								String st = "";
								if(!words[i].trim().equals("")) {
									if(i==0)
									     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
										else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
									    builder.append(st);
								}
							}
							cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						    cell.setBackgroundColor(bgColor);//#776858
						    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setPaddingTop(3f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
//						    cell.setPadding(7f);
						    cell.setNoWrap(false);
						    cell.setColspan(columns+maxParamNo);
						    vitalTab.addCell(cell);
						    vitalTab.getDefaultCell().setBackgroundColor(bgColor);
						}
						
						if(positionVptMap.size() > 1) {
							cell = new PdfPCell(new Phrase("Position :"+vtpMap.getKey(), actHeadFont));
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
//						    cell.setBackgroundColor(bgColor);//#776858
						    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setPaddingTop(3f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
//						    cell.setPadding(7f);
						    cell.setNoWrap(false);
						    cell.setColspan(columns+maxParamNo);
						    vitalTab.addCell(cell);
//						    vitalTab.getDefaultCell();
						    defalultRows =3;
						}
						headStr =messageSource.getMessage("label.vitalDate", null,currentLocale);
						cell = new PdfPCell(new Phrase(headStr, heading));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    vitalTab.addCell(cell);
					    
						headStr =messageSource.getMessage("label.vitalSamplingTime", null,currentLocale);
						cell = new PdfPCell(new Phrase(headStr, heading));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    vitalTab.addCell(cell);
					    
					    headStr =messageSource.getMessage("label.vitalScheduleTime", null,currentLocale);
						cell = new PdfPCell(new Phrase(headStr, heading));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    vitalTab.addCell(cell);
					    
					    headStr =messageSource.getMessage("label.vitalStartTime", null,currentLocale);
						cell = new PdfPCell(new Phrase(headStr, heading));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    vitalTab.addCell(cell);
					    
					    if(lsgpList != null && lsgpList.size() > 0) {
					    	for(LanguageSpecificGlobalParameterDetails lsgp : lsgpList) {
					    		headStr =lsgp.getName();
					    		String[] words = headStr.split(" ");
								StringBuilder builder2 = new StringBuilder();
								for (int i = 0; i < words.length; i++) {
									String str = "";
									if(!words[i].trim().equals("")) {
										if(i==0)
										     str = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
											else str = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
										    builder2.append(str);
									}
								}
					    		cell = new PdfPCell(new Phrase(builder2+"", heading));
					    		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							    cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//							    cell.setPadding(7f);
							    cell.setNoWrap(false);
							    vitalTab.addCell(cell);
					    	}
					    }
					    
					    headStr =messageSource.getMessage("label.vitalEndTime", null,currentLocale);
						cell = new PdfPCell(new Phrase(headStr, heading));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    vitalTab.addCell(cell);
					    
					    String headStr1 =messageSource.getMessage("label.performedBy", null,currentLocale);
					    headStr =messageSource.getMessage("label.performedOn", null,currentLocale);
						cell = new PdfPCell(new Phrase(headStr1+"\n & \n"+headStr, heading));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    cell.setColspan(2);
					    vitalTab.addCell(cell);
					    
//					    vitalTab.getDefaultCell().setBackgroundColor(new BaseColor(162, 159, 159));
					    vitalTab.getDefaultCell().setBackgroundColor(new BaseColor(162, 159, 159));
					    List<String> vitalSignList = new ArrayList<>();
					    SubjectDoseTimePoints sdtp = null;
					    Date doseDate = null;
						if(dcdDto.getSdtpMap() != null) {
					    	if(subject != null) {
					    		if(dcdDto.getSdtpMap().get(subject.getId()) != null) {
					    			if(dcdDto.getSdtpMap().get(subject.getId()).get(spm.getId()) != null) {
					    				List<SubjectDoseTimePoints> subDList = new ArrayList<>();
					    				if(tinf != null) {
					    					if(tfMap != null && tfMap.size() > 0) {
					    						for(Map.Entry<Long, TreatmentInfo> tmap : tfMap.entrySet()) {
					    							List<SubjectDoseTimePoints> subDTempList = dcdDto.getSdtpMap().get(subject.getId()).get(spm.getId()).get(tmap.getValue().getId());
					    							if(subDTempList != null && subDTempList.size() > 0) {
					    								subDList.addAll(subDTempList);
					    							}
					    						}
					    					}
					    					if(subDList != null && subDList.size() > 0) {
					    			    		sdtp = subDList.get(0);
					    			    		doseDate = sdtp.getActualTime();
					    			    	}
					    				}
					    			}
					    		}
					    	}
					    }	
						Map<String, String> tolarenceTpsMap = new HashMap<>();
						int tpCount =1;
					    for(VitalTimePoints vtp : vitalList) {
					    	SubjectVitalTimePointsData svtpData = null;
					    	Calendar dcal = null;
					    	if(subvtDataMap != null && subvtDataMap.size() > 0) {
					    		if(subvtDataMap.get(vtp.getId()) != null)
					    			svtpData = subvtDataMap.get(vtp.getId()).get(vtpMap.getKey());
					    	}
					    	if(vtp.getSign().equals("")) {
					    		if(!vitalSignList.contains("+"))
					    			vitalSignList.add("+");
					    	}else {
					    		if(!vitalSignList.contains(vtp.getSign()))
					    			vitalSignList.add(vtp.getSign());
				    		}
					    	String tpDate = "";
					    	TimeDto tdDto = getTimeDetails(vtp.getTimePoint());
					    	if(sdtp != null) {
					    		if(tdDto != null) {
									dcal = getDateAndTimeForVitals(tdDto, sdtp.getActualTime(), vtp);
									if(dcal != null) {
										SimpleDateFormat sdf = new SimpleDateFormat(dateString);
										tpDate = sdf.format(dcal.getTime());
									}
								}
							}
					    	cell = new PdfPCell(new Phrase(tpDate, regular));
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setPaddingTop(3f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
//						    cell.setPadding(7f);
						    cell.setNoWrap(false);
						    vitalTab.addCell(cell);
						    
					    	cell = new PdfPCell(new Phrase(vtp.getSign()+vtp.getTimePoint(), regular));
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setPaddingTop(3f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
//						    cell.setPadding(7f);
						    cell.setNoWrap(false);
						    vitalTab.addCell(cell);
						    
						    String scheduleTime = "";
						    if(doseDate != null) {
								Calendar cal = getDateAndTimeForVitals(tdDto, doseDate, vtp);
								String hours = cal.get(Calendar.HOUR_OF_DAY)+"";
								String minutes = cal.get(Calendar.MINUTE)+"";
								if(hours.length() == 1)
									hours = "0"+hours;
								if(minutes.length() ==1)
									minutes = "0"+minutes;
								scheduleTime = hours+":"+minutes;
							}
						    else if(svtpData != null && svtpData.getScheduleTime() != null) {
						    	scheduleTime = getOnlyTime(svtpData.getScheduleTime());
						    }
						    cell = new PdfPCell(new Phrase(scheduleTime, regular));
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPaddingTop(3f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
						    cell.setNoWrap(false);
						    vitalTab.addCell(cell);
						    
						    String actualTime = "";
						    if(svtpData != null) {
						    	actualTime = "";
						    	if(svtpData.getStartTime() != null) {
						    		actualTime = getOnlyTime(svtpData.getStartTime());
						    	}
						    }
						    cell = new PdfPCell(new Phrase(actualTime, regular));
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPaddingTop(3f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
						    cell.setNoWrap(false);
						    vitalTab.addCell(cell);
						    Map<Long, SubjectVitalParametersData> paramDataMap = new HashMap<>();
						    List<SubjectVitalParametersData> svpdList = null;
						    if(svtpData != null) {
				    			svpdList = dcdDto.getVitalParamMap().get(svtpData.getId()).get(vtp.getId());
				    			if(svpdList != null && svpdList.size() > 0) {
				    				for(SubjectVitalParametersData svpdPojo : svpdList) {
				    					paramDataMap.put(svpdPojo.getParameter().getId(), svpdPojo);
				    				}
				    			}
				    		}
						     if(lsgpList != null && lsgpList.size() > 0) {
						    	for(int j=0; j<lsgpList.size(); j++) {
						    		SubjectVitalParametersData svpd = null;
						    		LanguageSpecificGlobalParameterDetails lsgp = lsgpList.get(j);
						    		
						    		if(paramDataMap.get(lsgp.getParameterId().getId()) != null)
						    			svpd = paramDataMap.get(lsgp.getParameterId().getId());
								    String unitsStr = "";
					    			if(lsgp.getParameterId().getUnitsId() != null)
						    			unitsStr = lsgp.getParameterId().getUnitsId().getUnitsCode();
					    			String paramVal = "";
								    if(svpd != null) {
								    	if(svpd.getGlobalValue() != null)
								    		paramVal = svpd.getGlobalValue().getName();
								    	else paramVal = svpd.getParameterValue();
								    }
								    
						    		cell = new PdfPCell(new Phrase(paramVal+" "+unitsStr, regular));
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setPaddingTop(3f);
				    			    cell.setPaddingBottom(7f);
				    			    cell.setPaddingLeft(7f);
				    			    cell.setPaddingRight(7f);
//									cell.setPadding(7f);
								    cell.setNoWrap(false);
								    vitalTab.addCell(cell);
						    	}
						    }
						    
						     String endTime = "";
							    if(svtpData != null) {
							    	endTime = "";
							    	if(svtpData.getEndTime() != null) {
							    		endTime = getOnlyTime(svtpData.getEndTime());
							    	}
							    }
							    cell = new PdfPCell(new Phrase(endTime, regular));
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//								cell.setPadding(7f);
							    cell.setNoWrap(false);
							    vitalTab.addCell(cell);
						    
						    String dataStr ="";
						    if(svtpData != null) {
						    	String userStr = "";
						    	String dateStr = "";
						    	if(svtpData.getCollectedBy() != null)
						    		userStr = svtpData.getCollectedBy().getFullName();
						    	if(svtpData.getCreatedOn() != null) {
						    		SimpleDateFormat sdfDate = new SimpleDateFormat(dateString);
						    		dateStr = sdfDate.format(svtpData.getCreatedOn());
						    	}
						    	dataStr = userStr+"\n"+dateStr;	
						    }
						    cell = new PdfPCell(new Phrase(dataStr, regular));
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPaddingTop(3f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
						    cell.setNoWrap(false);
						    cell.setColspan(2);
						    vitalTab.addCell(cell);
						    
						    
						    String windowPeriodSign = "";
						    if(vtp.getWindowPeriodSign().equals("MINUS"))
						    	windowPeriodSign = "-";
						    else if(vtp.getWindowPeriodSign().equals("PLUS"))
						    	windowPeriodSign = "+";
						    else windowPeriodSign = "+/-";
						    
						    if(tolarenceTpsMap.containsKey(windowPeriodSign+" "+vtp.getWindowPeriod()+"")) {
						    	String tpStr = tolarenceTpsMap.get(windowPeriodSign+" "+vtp.getWindowPeriod()+"");
						    	if(tpCount == 16) {
						    		tpStr = tpStr.trim()+", \n"+vtp.getSign().trim()+vtp.getTimePoint();
						    		tpCount = 0;
						    	}else tpStr = tpStr.trim()+","+vtp.getSign().trim()+vtp.getTimePoint();
						    	tolarenceTpsMap.put(windowPeriodSign+" "+vtp.getWindowPeriod()+"", tpStr);
						    }else {
						    	String tpStr = vtp.getSign().trim()+vtp.getTimePoint();
						    	tolarenceTpsMap.put(windowPeriodSign+" "+vtp.getWindowPeriod()+"", tpStr);
						    }
					    }
					    /*if(vitalSignList.size() > 0) {
							for(String st : vitalSignList) {
								if(st.equals("+"))
									headStr =messageSource.getMessage("label.sbCol.plus", null,currentLocale);
								else if(st.equals("-"))
									headStr =messageSource.getMessage("label.sbCol.minus", null,currentLocale);
								else if(st.equals("+/-"))
									headStr =messageSource.getMessage("label.sbCol.plusorminus", null,currentLocale);
								cell = new PdfPCell(new Phrase(headStr, regular));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							    cell.setBorder(Rectangle.NO_BORDER);
							    cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setColspan(columns);
//							    cell.setPadding(7f);
							    cell.setNoWrap(false);
							    vitalTab.addCell(cell);
							}
						}*/
						if(tolarenceTpsMap.size() > 0) {
							int colNo = columns+maxParamNo;
							int labelColNo = 0;
							if(tolarenceTpsMap.size() ==1) {
								if(maxParamNo > 0) 
									labelColNo = colNo-2;
								for(Map.Entry<String, String> trMap : tolarenceTpsMap.entrySet()) {
									headStr =messageSource.getMessage("label.sbCol.tne", null,currentLocale);
									cell = new PdfPCell(new Phrase(headStr.trim(), heading));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								    cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//								    cell.setPadding(7f);
								    cell.setNoWrap(false);
								    if(labelColNo > 0)
								    	cell.setColspan(colNo-labelColNo);
								    else cell.setColspan(2);
								    vitalTab.addCell(cell);
								    
								    cell = new PdfPCell(new Phrase(trMap.getKey(), regular));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
								    cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//								    cell.setPadding(7f);
								    cell.setNoWrap(false);
								    if(labelColNo > 0)
								    	cell.setColspan(labelColNo);
								    else cell.setColspan(colNo-2);
								    vitalTab.addCell(cell);
								}
							}else {
								if(maxParamNo > 0) 
									labelColNo = colNo-1;
								headStr =messageSource.getMessage("label.sbCol.tne", null,currentLocale);
								cell = new PdfPCell(new Phrase(headStr.trim(), heading));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
							    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							    cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//								    cell.setPadding(7f);
							    cell.setNoWrap(false);
							    if(labelColNo > 0)
							    	cell.setColspan(colNo-labelColNo);
							    else 
							    	cell.setColspan(1);
							    
							    vitalTab.addCell(cell);
							    
							    for(Map.Entry<String, String> trMap : tolarenceTpsMap.entrySet()) {
							    	cell = new PdfPCell(new Phrase(trMap.getKey(), regular));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								    cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//									    cell.setPadding(7f);
								    cell.setNoWrap(false);
								    if(labelColNo > 0)
								    	cell.setColspan(colNo-labelColNo);
								    else 
								    	cell.setColspan(1);
								    vitalTab.addCell(cell);
								    
								    cell = new PdfPCell(new Phrase(trMap.getValue(), regular));
									cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								    cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//									    cell.setPadding(7f);
								    cell.setNoWrap(false);
								    cell.setColspan(colNo-1);
								    vitalTab.addCell(cell);
							    }
						    }
					    }
					    cell = new PdfPCell(new Phrase("", regular));
					    cell.setBorder(Rectangle.NO_BORDER);
					    cell.setFixedHeight(10f);
					    cell.setColspan(columns+maxParamNo);
					    vitalTab.addCell(cell);
					    
					    
					    cell = new PdfPCell(new Phrase("Reviewed by (PI/Designee): _________________________\n                                                         (Sign & Date)", regular));
					    cell.setBorder(Rectangle.NO_BORDER);
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//								cell.setPadding(7f);
					    cell.setNoWrap(false);
					    cell.setColspan(columns+maxParamNo);
					    vitalTab.addCell(cell);
					    
					    vitalTab.setHeaderRows(defalultRows);
					    cell = new PdfPCell();
					    cell.addElement(vitalTab);
					    cell.setBorder(Rectangle.NO_BORDER);
					    mainTab.addCell(cell);
//						document.add(vitalTab);
					}
				}
			}
		}
		document.add(mainTab);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}



private Calendar getDateAndTimeForVitals(TimeDto tdDto, Date doseDate, VitalTimePoints vtp) {
	Calendar dcal = Calendar.getInstance();
	try {
		if(doseDate != null)
			dcal.setTime(doseDate);
		if(tdDto.getHours() != 0) {
			if(vtp.getSign() != null && !vtp.getSign().equals(""))
				dcal.add(Calendar.HOUR_OF_DAY, -tdDto.getHours());
			else dcal.add(Calendar.HOUR_OF_DAY, tdDto.getHours());
		}
		if(tdDto.getMinutes() != 0) {
			if(vtp.getSign() != null && !vtp.getSign().equals(""))
				dcal.add(Calendar.MINUTE, -tdDto.getMinutes());
			else dcal.add(Calendar.MINUTE, tdDto.getMinutes());
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return dcal;
}

private TimeDto getTimeDetails(String timePoint) {
	TimeDto tdto = null;
	int hours =0;
	int minutes = 0;
	try {
		if(timePoint != null && !timePoint.equals("")) {
			String[] tempArr = timePoint.split("\\.");
			if(tempArr.length > 0) {
				if(tempArr[0].length() > 0) {
					hours = Integer.parseInt(tempArr[0]);	
				}
				
				int fractionMins = 0;
				if(tempArr.length == 2 && tempArr[1].length() > 0) {
					Float time = (Float.parseFloat("0."+tempArr[1]) * 60);
					fractionMins = Math.round(time);
				}
				if(fractionMins < 59) {
					minutes = minutes + fractionMins;
				}else {
					int quotient = fractionMins/60;
					int remainder = fractionMins % 60;
					if(quotient != 0)
						hours = hours + quotient;
					if(remainder != 0)
						minutes = minutes + remainder;
				}
			}
		}
		tdto = new TimeDto();
		tdto.setHours(hours);
		tdto.setMinutes(minutes);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return tdto;
}

private VitalParameterDto getMaxParametersMapDetails(String position, Integer paramIdsCount, Set<Long> paramIds,
		Map<Integer, Set<Long>> vtalParamIdsMap, Map<String, Integer> positionMaxParamMap) {
	VitalParameterDto vpDto = new VitalParameterDto();
	try {
		if(positionMaxParamMap.containsKey(position)) {
			if(positionMaxParamMap.get(position) < paramIdsCount) { 
				positionMaxParamMap.put(position, paramIdsCount);
				vtalParamIdsMap.put(paramIdsCount, paramIds);
			}
		}else {
			positionMaxParamMap.put(position, paramIdsCount);
			vtalParamIdsMap.put(paramIdsCount, paramIds);
		}
		vpDto.setPositionMaxParamMap(positionMaxParamMap);
		vpDto.setVtalParamIdsMap(vtalParamIdsMap);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return vpDto;
}

@SuppressWarnings("unused")
private void generateBloodSheetPdf(DataCrfDtoDetails dcdDto, Document document, Font regular, Image chkedCbImage,
		Image unchkdCbImage, Image chkedradioimg, Image unchkradioimg, Font heading, Font regular2, Font mainHeading,
		Font subHeading, MessageSource messageSource, Locale currentLocale, Long langId, Font actHeadFont,
		BaseColor bgColor, LanguageSpecificGlobalActivityDetails lsga, StudyVolunteerReporting svr,
		StudyPeriodMaster spm, String dateStr, String dateStrWithTime) throws DocumentException {
	PdfPCell cell = null;
	PdfPTable hstable = null;
	String headStr = null;

	hstable = new PdfPTable(11);
	hstable.setWidthPercentage(100);
	List<Long> sampleIdsList = new ArrayList<>();
	Map<Long, String> tpsStrMap = new HashMap<>();
	try {
		StudySubjects subject = dcdDto.getSubMap().get(svr.getId());
		TreatmentInfo tinf = null;
		Map<Long, TreatmentInfo> tfMap = null;
		if(subject != null) {
			tfMap = dcdDto.getSubwtrMap().get(subject.getReportingId().getId());
			if(tfMap != null && tfMap.size() > 0) {
				if(tfMap != null && tfMap.size() > 0) {
					tinf = tfMap.get(spm.getId());
				}
			}
		}
		if(tinf != null) {
			//Dosing Data
			List<Long> doseIds = new ArrayList<>();
			Long doseMinId = null;
			SubjectDoseTimePoints sdtpPojo = null;
			Map<Long, SubjectDoseTimePoints> sdtMap = new HashMap<>();
			Map<Long, Map<Long, Map<Long,List<SubjectDoseTimePoints>>>> sdtpMap = dcdDto.getSdtpMap();
			if(sdtpMap != null && sdtpMap.size() > 0){
				Map<Long, Map<Long, List<SubjectDoseTimePoints>>> doseMap = sdtpMap.get(subject.getId());
				if(doseMap != null && doseMap.size() > 0) {
					Map<Long, List<SubjectDoseTimePoints>> twdoseMap = doseMap.get(spm.getId());
					if(twdoseMap != null && twdoseMap.size() > 0) {
						List<SubjectDoseTimePoints> sdtList = twdoseMap.get(tinf.getId());
						if(sdtList != null && sdtList.size() > 0) {
							List<SubjectDoseTimePoints> subDtpList = new ArrayList<>();
							if(dcdDto.getSdtpMap().get(subject.getId()) != null ) {
								if(dcdDto.getSdtpMap().get(subject.getId()).get(spm.getId()) != null) {
									if(tfMap != null && tfMap.size() > 0) {
										for(Map.Entry<Long, TreatmentInfo> tmap : tfMap.entrySet()) {
											List<SubjectDoseTimePoints> subDtpTempList = dcdDto.getSdtpMap().get(subject.getId()).get(spm.getId()).get(tmap.getValue().getId()); //SubjectId, Period, treatment, Listst of vitals
											if(subDtpTempList != null && subDtpTempList.size() > 0) {
												subDtpList.addAll(subDtpTempList);
											}
										}
									}
								}
							}
							if(subDtpList != null && subDtpList.size() > 0) {
					    		for(SubjectDoseTimePoints sdtp : subDtpList) {
					    			doseIds.add(sdtp.getDoseTimePoints().getId());
					    			sdtMap.put(sdtp.getDoseTimePoints().getId(), sdtp);
					    		}
					    	}
						}
					}
				}
			}
		    if(doseIds.size() > 0)
		    	doseMinId = Collections.min(doseIds);
		    if(doseMinId != null)
		    	sdtpPojo = sdtMap.get(doseMinId);
		    //Sample Info Map 
		    Map<Long, SampleInfoDto> sinfMap = dcdDto.getSampleInfoMap();
		    SampleInfoDto sinfDto = null;
		    if(tinf != null)
		    	sinfDto = sinfMap.get(tinf.getId());
			//Sample Data 
			Map<String, SubjectSampleCollectionTimePointsData> sampColMap = new HashMap<>();
			List<SubjectSampleCollectionTimePointsData> ssctpdList = null;
			if(dcdDto.getSampCollMap() != null && dcdDto.getSampCollMap().size() > 0) {
				if(dcdDto.getSampCollMap().get(subject.getId()) != null) {
					if(dcdDto.getSampCollMap().get(subject.getId()).get(spm.getId()) != null) {
//						ssctpdList = dcdDto.getSampCollMap().get(subject.getId()).get(spm.getId()).get(tinf.getId());
						ssctpdList = dcdDto.getSampCollMap().get(subject.getId()).get(spm.getId());
					}
						
				}
			}
			if(ssctpdList != null) {
				for(SubjectSampleCollectionTimePointsData ssctp : ssctpdList) {
					sampColMap.put(ssctp.getSampleTimePoint().getSign()+ssctp.getSampleTimePoint().getTimePoint(), ssctp);
				}
			}
			
			if(lsga.getGlobalActivity().isHeadding()) {
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(255,255,255));
				String[] words = lsga.getName().split(" ");
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < words.length; i++) {
					String st = "";
					if(i==0)
				     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
					else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
				    builder.append(st);
				}
				cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			    cell.setBackgroundColor(bgColor);//#776858
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setColspan(11);
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
				hstable.addCell(cell);
				hstable.getDefaultCell().setBackgroundColor(bgColor);
			}
			if(sdtpPojo != null && sdtpPojo.getActualTime() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(dateStr);
				cell = new PdfPCell(new Phrase("Dosing Date: "+sdf.format(sdtpPojo.getActualTime()), regular));
			}else cell = new PdfPCell(new Phrase("Dosing Date: ", regular));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(3);
			hstable.addCell(cell);
			
			if(sdtpPojo != null && sdtpPojo.getActualTime() != null) {
				Calendar cal = Calendar.getInstance();
		    	cal.setTime(sdtpPojo.getActualTime());
		    	String hours = cal.get(Calendar.HOUR_OF_DAY)+"";
		    	String minutes = cal.get(Calendar.MINUTE)+"";
		    	if(hours.length() == 1)
					hours = "0"+hours;
				if(minutes.length() ==1)
					minutes = "0"+minutes;
		    	String time = hours+":"+minutes;
		    	cell = new PdfPCell(new Phrase("Dosing Time: "+time, regular));
			}else
				cell = new PdfPCell(new Phrase("Dosing Time: ", regular));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(3);
			hstable.addCell(cell);
			
			if(sinfDto != null)
				cell = new PdfPCell(new Phrase("Light Condition: "+sinfDto.getLightCondition(), regular));
			else cell = new PdfPCell(new Phrase("Light Condition:  ", regular));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(5);
			hstable.addCell(cell);
			
			if(sinfDto != null)
				cell = new PdfPCell(new Phrase("Pre and Post Dose Blood Volume: "+sinfDto.getBloodVolume()+"mL", regular));
			else cell = new PdfPCell(new Phrase("Pre and Post Dose Blood Volume: ", regular));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(3);
			hstable.addCell(cell);
			
			if(sinfDto != null)
				cell = new PdfPCell(new Phrase("Type of Vacutainer Used: "+sinfDto.getTypeOfVacutainer(), regular));
			else cell = new PdfPCell(new Phrase("Type of Vacutainer Used: ", regular));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(5);
			hstable.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Ice bath required: ", regular));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(3);
			hstable.addCell(cell);
			
			for(int i=1; i<=2; i++) {
				if(i==1) {
					headStr =messageSource.getMessage("label.date", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr.trim(), heading));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setRowspan(2);
				    hstable.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase("Time Point \n (Hours)", heading));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setRowspan(2);
				    hstable.addCell(cell);
				    
					cell = new PdfPCell(new Phrase("Scheduled time", heading));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setRowspan(2);
				    hstable.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase("I.D check", heading));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setColspan(2);
				    cell.setRowspan(1);
				    hstable.addCell(cell);
				    
					cell = new PdfPCell(new Phrase("Sample Collected on Scheduled Time", heading));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setRowspan(1);
				    cell.setColspan(2);
				    hstable.addCell(cell);
				    
					cell = new PdfPCell(new Phrase("If no record the Actual time", heading));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setRowspan(2);
				    hstable.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase("#Code for Deviation Comments", heading));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setRowspan(2);
				    hstable.addCell(cell);
				    
				    
				    String headStr1 =messageSource.getMessage("label.performedBy", null,currentLocale);
				    headStr =messageSource.getMessage("label.performedOn", null,currentLocale);
				    
					cell = new PdfPCell(new Phrase(headStr1+"\n & \n"+headStr.trim(), heading));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setColspan(2);
				    cell.setRowspan(2);
				    hstable.addCell(cell);
				}
				if(i==2) {
			    	for(int j=1; j<=11; j++) {
			    		if(j==4) {
		    			  	cell = new PdfPCell(new Phrase("Sub ID", heading));
		    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
		    			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    			    cell.setPaddingTop(3f);
		    			    cell.setPaddingBottom(7f);
		    			    cell.setPaddingLeft(7f);
		    			    cell.setPaddingRight(7f);
//		    			    cell.setPadding(7f);
		    			    cell.setNoWrap(false);
		    			    hstable.addCell(cell);	
		    			}
			    		if(j==5) {
			    			cell = new PdfPCell(new Phrase("Tube ID", heading));
		    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
		    			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    			    cell.setPaddingTop(3f);
		    			    cell.setPaddingBottom(7f);
		    			    cell.setPaddingLeft(7f);
		    			    cell.setPaddingRight(7f);
//		    			    cell.setPadding(7f);
		    			    cell.setNoWrap(false);
		    			    hstable.addCell(cell);
			    		}
			    		
			    		if(j==6) {
			    			headStr =messageSource.getMessage("label.gaYes", null,currentLocale);
		    				cell = new PdfPCell(new Phrase(headStr.trim(), heading));
		    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
		    			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    			    cell.setPaddingTop(3f);
		    			    cell.setPaddingBottom(7f);
		    			    cell.setPaddingLeft(7f);
		    			    cell.setPaddingRight(7f);
//		    			    cell.setPadding(7f);
		    			    cell.setNoWrap(false);
		    			    hstable.addCell(cell);	
		    		   }
			    	   if(j==7) {
			    		   	headStr =messageSource.getMessage("label.gaNo", null,currentLocale);
		    				cell = new PdfPCell(new Phrase(headStr.trim(), heading));
		    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
		    			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    			    cell.setPaddingTop(3f);
		    			    cell.setPaddingBottom(7f);
		    			    cell.setPaddingLeft(7f);
		    			    cell.setPaddingRight(7f);
//		    			    cell.setPadding(7f);
		    			    cell.setNoWrap(false);
		    			    hstable.addCell(cell);
			    	   }
			    	}
			    }
			}
			hstable.getDefaultCell().setBackgroundColor(new BaseColor(184, 207, 241));
			List<SampleTimePoints> sampleTimePoints = dcdDto.getSamplesMap().get(tinf.getId());
			int count =1;
			List<String> stpStrSignsList = new ArrayList<>();
			Map<String, String> tolarenceTpsMap = new HashMap<>();
			int tpCount =0;
			SubjectDoseTimePoints sdtp = null;
			Date doseDate = null;
			if(dcdDto.getSdtpMap() != null) {
		    	if(subject != null) {
		    		if(dcdDto.getSdtpMap().get(subject.getId()) != null) {
		    			if(dcdDto.getSdtpMap().get(subject.getId()).get(spm.getId()) != null) {
		    				if(tinf != null) {
		    					List<SubjectDoseTimePoints> subDList = dcdDto.getSdtpMap().get(subject.getId()).get(spm.getId()).get(tinf.getId());
		    			    	if(subDList != null && subDList.size() > 0) {
		    			    		sdtp = subDList.get(0);
		    			    		doseDate = sdtp.getActualTime();
		    			    	}
		    				}
		    			}
		    		}
		    	}
		    }
		    if(sampleTimePoints !=  null && sampleTimePoints.size() > 0) {
		    	Calendar dcal = null;
		    	Collections.sort(sampleTimePoints);
		    	for (SampleTimePoints stp : sampleTimePoints) {
					sampleIdsList.add(stp.getId());
					tpsStrMap.put(stp.getId(), stp.getSign()+stp.getTimePoint());
					SubjectSampleCollectionTimePointsData ssct = sampColMap.get(stp.getSign()+stp.getTimePoint());
					SimpleDateFormat sdf = new SimpleDateFormat(dateStr);
					headStr = "";
					String tpDate = "";
					TimeDto tdDto = getTimeDetails(stp.getTimePoint());
			    	if(sdtp != null) {
			    		if(tdDto != null)
			    			dcal = getDateAndTimeForSamples(tdDto, sdtp.getActualTime(), stp);
						if(dcal != null) 
						   tpDate = sdf.format(dcal.getTime());
			    	}
					cell = new PdfPCell(new Phrase(tpDate, regular));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    hstable.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase(stp.getSign()+stp.getTimePoint(), regular));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    hstable.addCell(cell);
				    
				    
				    String scheduledTime = "";
				    Date sheduleDate = null;
				    if(doseDate != null) {
				    	Calendar cal = getDateAndTimeForSamples(tdDto, doseDate, stp);
						String hours = cal.get(Calendar.HOUR_OF_DAY)+"";
						String minutes = cal.get(Calendar.MINUTE)+"";
						if(hours.length() == 1)
							hours = "0"+hours;
						if(minutes.length() ==1)
							minutes = "0"+minutes;
						scheduledTime = hours+":"+minutes;
				    }
				    else if(ssct != null && ssct.getScheduleTime() != null) {
						if(ssct.getScheduleTime() != null)
							scheduledTime = getOnlyTime(ssct.getScheduleTime());
				    }
				    cell = new PdfPCell(new Phrase(scheduledTime, regular));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    hstable.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase("", regular));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    hstable.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase("", regular));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    hstable.addCell(cell);
				    
				    String windowPeriodSign = "";
				    if(stp.getWindowPeriodSign().equals("MINUS"))
				    	windowPeriodSign = "-";
				    else if(stp.getWindowPeriodSign().equals("PLUS"))
				    	windowPeriodSign = "+";
				    else windowPeriodSign = "+/-";
				    
				    if(tolarenceTpsMap.containsKey(windowPeriodSign+" "+stp.getWindowPeriod()+"")) {
				    	String tpStr = tolarenceTpsMap.get(windowPeriodSign+" "+stp.getWindowPeriod()+"");
				    	if(tpCount == 16) {
				    		tpStr = tpStr.trim()+", \n"+stp.getSign().trim()+stp.getTimePoint();
				    		tpCount = 0;
				    	}else tpStr = tpStr.trim()+","+stp.getSign().trim()+stp.getTimePoint();
				    	tolarenceTpsMap.put(windowPeriodSign+" "+stp.getWindowPeriod()+"", tpStr);
				    }else {
				    	String tpStr = stp.getSign().trim()+stp.getTimePoint();
				    	tolarenceTpsMap.put(windowPeriodSign+" "+stp.getWindowPeriod()+"", tpStr);
				    }
				    tpCount++;
				   if(ssct != null) {
				    	if(ssct.getActualtime() != null && ssct.getScheduleTime() != null) {
						    /*String timePoint = meal.getTimePoint();
							String[] hrmim = timePoint.split("\\.");
							int min = Integer.parseInt(hrmim[0]) * 60;
							min += ((Integer.parseInt(hrmim[1]) * 6) / 100);*/
				    		Calendar c = Calendar.getInstance();
							c.setTime(ssct.getScheduleTime());
				    		if(stp.getWindowPeriodSign().equals("PLUS")) 
				    			c.add(Calendar.MINUTE, stp.getWindowPeriod());
				    		else if(stp.getWindowPeriodSign().equals("MINUS"))
				    			c.add(Calendar.MINUTE, -stp.getWindowPeriod());
				    		else if(stp.getWindowPeriodSign().equals("PLUSANDMINUS")) {
				    			//Plus Condition
				    			Calendar plusCal = Calendar.getInstance();
				    			plusCal.setTime(ssct.getScheduleTime());
				    			plusCal.add(Calendar.MINUTE, stp.getWindowPeriod());
				    			Map<String, Long> datesMap = getDatesDifference(plusCal.getTime(), ssct.getScheduleTime());
				    			String plusStr = "";
				    			String minusStr = "";
				    			if(datesMap != null && datesMap.size() > 0) {
				    				int schHour = c.get(Calendar.HOUR_OF_DAY);
				    				int schMinutes = c.get(Calendar.MINUTE);
				    				Long hours = datesMap.get("Hours");
						            Long minutes = datesMap.get("Minutes");
				    				if(schHour == Integer.parseInt(hours+"")) {
				    					if(schMinutes >= Integer.parseInt(minutes+""))
				    						plusStr = "Yes";
				    					else plusStr = "No";
				    				}else plusStr = "No";
				    			}
				    			
				    			//Minus condition
				    			Calendar minusCal = Calendar.getInstance();
				    			minusCal.setTime(ssct.getScheduleTime());
				    			minusCal.add(Calendar.MINUTE, -stp.getWindowPeriod());
				    			Map<String, Long> minusdatesMap = getDatesDifference(plusCal.getTime(), ssct.getScheduleTime());
				    			if(minusdatesMap != null && minusdatesMap.size() > 0) {
				    				int schHour = c.get(Calendar.HOUR_OF_DAY);
				    				int schMinutes = c.get(Calendar.MINUTE);
				    				Long hours = minusdatesMap.get("Hours");
						            Long minutes = minusdatesMap.get("Minutes");
				    				if(schHour == Integer.parseInt(hours+"")) {
				    					if(schMinutes <= Integer.parseInt(minutes+""))
				    						minusStr = "Yes";
				    					else minusStr = "No";
				    				}else minusStr = "No";
				    			}
				    			if(plusStr.equals("No") || minusStr.equals("No")) {
				    				cell = new PdfPCell(unchkdCbImage);
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								    cell.setPaddingTop(4f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//									    cell.setPadding(7f);
								    cell.setNoWrap(false);
								    hstable.addCell(cell);
								    
								    cell = new PdfPCell(chkedCbImage);
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								    cell.setPaddingTop(4f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//									    cell.setPadding(7f);
								    cell.setNoWrap(false);
								    hstable.addCell(cell);
				    			}else {
				    				cell = new PdfPCell(chkedCbImage);
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								    cell.setPaddingTop(4f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//									    cell.setPadding(7f);
								    cell.setNoWrap(false);
								    hstable.addCell(cell);
								    
								    cell = new PdfPCell(unchkdCbImage);
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								    cell.setPaddingTop(4f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//									    cell.setPadding(7f);
								    cell.setNoWrap(false);
								    hstable.addCell(cell);
				    			}
				    		}
				    		if(!stp.getWindowPeriodSign().equals("PLUSANDMINUS")) {
				    			Map<String, Long> datesMap = getDatesDifference(c.getTime(), ssct.getScheduleTime());
								if(datesMap != null && datesMap.size() > 0) {
									Long hours = datesMap.get("Hours");
						            Long minutes = datesMap.get("Minutes");
						            boolean hrFlag = false;
						            boolean minFlag = false;
						            if(hours != 0)
						            	hrFlag = true;
						            if(minutes != 0)
						            	minFlag = true;
						            if(hrFlag || minFlag) {
						            	cell = new PdfPCell(unchkdCbImage);
										cell.setHorizontalAlignment(Element.ALIGN_CENTER);
										cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									    cell.setPaddingTop(4f);
									    cell.setPaddingBottom(7f);
									    cell.setPaddingLeft(7f);
									    cell.setPaddingRight(7f);
//										    cell.setPadding(7f);
									    cell.setNoWrap(false);
									    hstable.addCell(cell);
									    
									    cell = new PdfPCell(chkedCbImage);
										cell.setHorizontalAlignment(Element.ALIGN_CENTER);
										cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									    cell.setPaddingTop(4f);
									    cell.setPaddingBottom(7f);
									    cell.setPaddingLeft(7f);
									    cell.setPaddingRight(7f);
//										    cell.setPadding(7f);
									    cell.setNoWrap(false);
									    hstable.addCell(cell);
						            }else {
						            	cell = new PdfPCell(chkedCbImage);
										cell.setHorizontalAlignment(Element.ALIGN_CENTER);
										cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									    cell.setPaddingTop(4f);
									    cell.setPaddingBottom(7f);
									    cell.setPaddingLeft(7f);
									    cell.setPaddingRight(7f);
//										    cell.setPadding(7f);
									    cell.setNoWrap(false);
									    hstable.addCell(cell);
									    
									    cell = new PdfPCell(unchkdCbImage);
										cell.setHorizontalAlignment(Element.ALIGN_CENTER);
										cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									    cell.setPaddingTop(4f);
									    cell.setPaddingBottom(7f);
									    cell.setPaddingLeft(7f);
									    cell.setPaddingRight(7f);
//										    cell.setPadding(7f);
									    cell.setNoWrap(false);
									    hstable.addCell(cell);
						            }
								}
				    		}
							
				    	}else {
				    		cell = new PdfPCell(unchkdCbImage);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setPaddingTop(4f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
//							    cell.setPadding(7f);
						    cell.setNoWrap(false);
						    hstable.addCell(cell);
						    
						    cell = new PdfPCell(unchkdCbImage);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setPaddingTop(4f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
//							    cell.setPadding(7f);
						    cell.setNoWrap(false);
						    hstable.addCell(cell);
				    	}
				    }else {
				    	cell = new PdfPCell(unchkdCbImage);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(4f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    hstable.addCell(cell);
					    
					    cell = new PdfPCell(unchkdCbImage);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(4f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    hstable.addCell(cell);
				    }
				    
				    String actualTime = "";
				    if(ssct != null) {
				    	actualTime = "";
				    	if(ssct.getActualtime() != null) {
				    		Calendar cal = Calendar.getInstance();
				    		cal.setTime(ssct.getActualtime());
				    		String hours = cal.get(Calendar.HOUR_OF_DAY)+"";
				    		String minutes = cal.get(Calendar.MINUTE)+"";
				    		if(hours.length() == 1)
								hours = "0"+hours;
							if(minutes.length() ==1)
								minutes = "0"+minutes;
				    		actualTime = hours+":"+minutes;
				    	}
				    }
				    
				    cell = new PdfPCell(new Phrase(actualTime, regular));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    hstable.addCell(cell);
				   
				    String devComments = "";
				    if(ssct != null && ssct.getSampleDeviationId() != null) {
//				    	devComments = ssct.getSampleDevComments().getCode();
				    	devComments = ssct.getSampleDeviationId().getCode();
				    }
				    cell = new PdfPCell(new Phrase(devComments, regular));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    hstable.addCell(cell);
				    
				    String dataStr1 = "";
				    String dataStr2 = "";
				    if(ssct != null) {
				    	if(ssct.getCollectedBy() != null)
				    		dataStr1 = ssct.getCollectedBy().getFullName();
				    }
				    if(ssct != null) {
				    	if(ssct.getCreatedOn() != null) {
				    		SimpleDateFormat sdfDate = new SimpleDateFormat(dateStr);
				    		dataStr2 = sdfDate.format(ssct.getCreatedOn());
				    	}
				    }
				    cell = new PdfPCell(new Phrase(dataStr1+"\n"+dataStr2, regular));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setColspan(2);
				    hstable.addCell(cell);
				    count++;
				}
			}
			cell = new PdfPCell(new Phrase("#Please mention reasons for sample collection deviations (DV-Difficulty with Veins, CB- Cannula Blocked, SRL-Subject Reported Late and AOR-Any Other Reason), CR- Cannula Removed, RCD-Re-cannulation Done, SNR- Subject Not Reported, TFP-Taken by fresh prick.", regular));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(11);
		    hstable.addCell(cell);
		    
		    String rcDataWithTpStr = "";
		    String rcDataWithoutTPStr = "";
		    List<RecannulationDataDto> rcDataWithTp = new ArrayList<>();
		    List<RecannulationDataDto> rcDataWithoutTp = new ArrayList<>();
		    if(subject != null) {
		    	if(dcdDto.getRcDataWithTpMap() != null) {
		    		if(dcdDto.getRcDataWithTpMap().get(subject.getId()) != null) {
			    		if(dcdDto.getRcDataWithTpMap().get(subject.getId()).get(spm.getId()) != null) {
			    			if(sampleIdsList.size() > 0) {
			    				for(Long spId : sampleIdsList) {
			    					List<RecannulationDataDto> rcDataList = dcdDto.getRcDataWithTpMap().get(subject.getId()).get(spm.getId()).get(spId);
			    					if(rcDataList != null)
			    						rcDataWithTp.addAll(rcDataList); 
			    				}
			    			}
			    		}
			    	}
		    	}
		    	if(dcdDto.getRcDataWithoutTpMap() != null) {
		    		if(dcdDto.getRcDataWithoutTpMap().get(subject.getId()) != null) {
		    			if(dcdDto.getRcDataWithoutTpMap().get(subject.getId()).get(spm.getId()) != null) {
		    				List<RecannulationDataDto> rcDataList = dcdDto.getRcDataWithoutTpMap().get(subject.getId()).get(spm.getId());
	    					if(rcDataList != null)
	    						rcDataWithoutTp.addAll(rcDataList); 
		    			}
		    		}
		    	}
		    	
		    }
		    if(rcDataWithTp.size() > 0 || rcDataWithoutTp.size() > 0) {
		    	PdfPTable rcdTable = new PdfPTable(3);
		    	rcdTable.setWidthPercentage(100f);
		    	rcdTable.setWidths(new int[] {20, 60, 20});
		    	cell = new PdfPCell(new Phrase("Time Point", heading));
    			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    			cell.setBorder(Rectangle.NO_BORDER);
    		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		    cell.setBackgroundColor(bgColor);//#776858
    		    cell.setPaddingTop(3f);
    		    cell.setPaddingBottom(7f);
    		    cell.setPaddingLeft(7f);
    		    cell.setPaddingRight(7f);
    		    cell.setNoWrap(false);
    		    rcdTable.addCell(cell);
    		    
    		    cell = new PdfPCell(new Phrase("Reason", heading));
    			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    			cell.setBorder(Rectangle.NO_BORDER);
    		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		    cell.setBackgroundColor(bgColor);//#776858
    		    cell.setPaddingTop(3f);
    		    cell.setPaddingBottom(7f);
    		    cell.setPaddingLeft(7f);
    		    cell.setPaddingRight(7f);
    		    cell.setNoWrap(false);
    		    rcdTable.addCell(cell);
    		    
    		    cell = new PdfPCell(new Phrase("Date & Time", heading));
    			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    			cell.setBorder(Rectangle.NO_BORDER);
    		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		    cell.setBackgroundColor(bgColor);//#776858
    		    cell.setPaddingTop(3f);
    		    cell.setPaddingBottom(7f);
    		    cell.setPaddingLeft(7f);
    		    cell.setPaddingRight(7f);
    		    cell.setNoWrap(false);
    		    rcdTable.addCell(cell);
    		    rcdTable.getDefaultCell().setBackgroundColor(bgColor);
		    	
		    	if(rcDataWithTp.size() > 0) {
		    		SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
		    		for(RecannulationDataDto rcd : rcDataWithTp) {
		    			String rcdDate = sdf.format(rcd.getRecannulationDate());
		    			cell = new PdfPCell(new Phrase(tpsStrMap.get(rcd.getSampleId()), regular));
		    			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    			cell.setBorder(Rectangle.NO_BORDER);
		    		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    		    cell.setPaddingTop(3f);
		    		    cell.setPaddingBottom(7f);
		    		    cell.setPaddingLeft(7f);
		    		    cell.setPaddingRight(7f);
		    		    cell.setNoWrap(false);
		    		    rcdTable.addCell(cell);
		    		    
		    		    cell = new PdfPCell(new Phrase(rcd.getReason(), regular));
		    			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    			cell.setBorder(Rectangle.NO_BORDER);
		    		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    		    cell.setPaddingTop(3f);
		    		    cell.setPaddingBottom(7f);
		    		    cell.setPaddingLeft(7f);
		    		    cell.setPaddingRight(7f);
		    		    cell.setNoWrap(false);
		    		    rcdTable.addCell(cell);
		    		    
		    		    cell = new PdfPCell(new Phrase(rcdDate, regular));
		    			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    			cell.setBorder(Rectangle.NO_BORDER);
		    		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    		    cell.setPaddingTop(3f);
		    		    cell.setPaddingBottom(7f);
		    		    cell.setPaddingLeft(7f);
		    		    cell.setPaddingRight(7f);
		    		    cell.setNoWrap(false);
		    		    rcdTable.addCell(cell);
		    		}
		    	}
		    	if(rcDataWithoutTp.size() > 0) {
		    		SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
		    		for(RecannulationDataDto rcd : rcDataWithoutTp) {
		    			String rcdDate = sdf.format(rcd.getRecannulationDate());
		    			
		    			cell = new PdfPCell(new Phrase("", regular));
		    			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    			cell.setBorder(Rectangle.NO_BORDER);
		    		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    		    cell.setPaddingTop(3f);
		    		    cell.setPaddingBottom(7f);
		    		    cell.setPaddingLeft(7f);
		    		    cell.setPaddingRight(7f);
		    		    cell.setNoWrap(false);
		    		    rcdTable.addCell(cell);
		    			
		    		    cell = new PdfPCell(new Phrase(rcd.getReason(), regular));
		    			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    			cell.setBorder(Rectangle.NO_BORDER);
		    		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    		    cell.setPaddingTop(3f);
		    		    cell.setPaddingBottom(7f);
		    		    cell.setPaddingLeft(7f);
		    		    cell.setPaddingRight(7f);
		    		    cell.setNoWrap(false);
		    		    rcdTable.addCell(cell);
		    		    
		    		    cell = new PdfPCell(new Phrase(rcdDate, regular));
		    			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    			cell.setBorder(Rectangle.NO_BORDER);
		    		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    		    cell.setPaddingTop(3f);
		    		    cell.setPaddingBottom(7f);
		    		    cell.setPaddingLeft(7f);
		    		    cell.setPaddingRight(7f);
		    		    cell.setNoWrap(false);
		    		    rcdTable.addCell(cell);
		    		}
		    	}
		    	
		    	rcdTable.setHeaderRows(1);
		    	cell = new PdfPCell(rcdTable);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			    cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setColspan(11);
			    hstable.addCell(cell);
		    }  
		    cell = new PdfPCell(new Phrase("Comments : ", regular));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(2);
		    hstable.addCell(cell);
		    
		    cell = new PdfPCell(new Phrase(" ", regular));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(9);
		    hstable.addCell(cell);
		    
			if(tolarenceTpsMap.size() > 0) {
				if(tolarenceTpsMap.size() ==1) {
					for(Map.Entry<String, String> trMap : tolarenceTpsMap.entrySet()) {
						headStr =messageSource.getMessage("label.sbCol.tne", null,currentLocale);
						cell = new PdfPCell(new Phrase(headStr.trim(), heading));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    cell.setColspan(2);
					    hstable.addCell(cell);
					    
					    cell = new PdfPCell(new Phrase(trMap.getKey(), regular));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    cell.setColspan(9);
					    hstable.addCell(cell);
					}
				}else {
					headStr =messageSource.getMessage("label.sbCol.tne", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr.trim(), heading));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//					    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setColspan(11);
				    hstable.addCell(cell);
				    for(Map.Entry<String, String> trMap : tolarenceTpsMap.entrySet()) {
				    	cell = new PdfPCell(new Phrase(trMap.getKey(), regular));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    hstable.addCell(cell);
					    
					    cell = new PdfPCell(new Phrase(trMap.getValue(), regular));
						cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    cell.setColspan(10);
					    hstable.addCell(cell);
				    }
			    }
		    }
		    
			if(stpStrSignsList.size() > 0) {
				for(String st : stpStrSignsList) {
					if(st.equals("+"))
						headStr =messageSource.getMessage("label.sbCol.plus", null,currentLocale);
					else if(st.equals("-"))
						headStr =messageSource.getMessage("label.sbCol.minus", null,currentLocale);
					else if(st.equals("+/-"))
						headStr =messageSource.getMessage("label.sbCol.plusorminus", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr, regular));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setBorder(Rectangle.NO_BORDER);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    cell.setColspan(11);
//				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    hstable.addCell(cell);
				}
				
			}
			
			cell = new PdfPCell(new Phrase("", regular));
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(11);
			cell.setFixedHeight(35f);
			hstable.addCell(cell);
			
			cell = new PdfPCell(new Phrase("      Reviewed by:      _____________________________ \n  (PI/PIC/Designee)                (Sign & Date) ", regular));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(Rectangle.NO_BORDER);
//			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(11);
		    hstable.addCell(cell);
		    
			hstable.setHeaderRows(5);
			document.add(hstable);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

private Calendar getDateAndTimeForSamples(TimeDto tdDto, Date actualTime, SampleTimePoints stp) {
	Calendar dcal = Calendar.getInstance();
	try {
		if(actualTime != null)
			dcal.setTime(actualTime);
		if(tdDto.getHours() != 0) {
			if(stp.getSign() != null && !stp.getSign().equals(""))
				dcal.add(Calendar.HOUR_OF_DAY, -tdDto.getHours());
			else dcal.add(Calendar.HOUR_OF_DAY, tdDto.getHours());
		}
		if(tdDto.getMinutes() != 0) {
			if(stp.getSign() != null && !stp.getSign().equals(""))
				dcal.add(Calendar.MINUTE, -tdDto.getMinutes());
			else dcal.add(Calendar.MINUTE, tdDto.getMinutes());
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return dcal;
}

@SuppressWarnings("unused")
private void generateMealRecordPdf(DataCrfDtoDetails dcdDto, Document document, Font regular, Image unchkdCbImage,
		Image chkedCbImage, Image chkedradioimg, Image unchkradioimg, Font heading, Locale currentLocale,
		MessageSource messageSource, LanguageSpecificGlobalActivityDetails lsga, BaseColor bgColor,
		ApplicationConfiguration appConfig, Font mainHeading, Font subHeading, Font actHeadFont,
		StudyVolunteerReporting svr, StudyPeriodMaster spm, String dateStr, String dateStrWithTime) throws DocumentException {
	PdfPCell cell = null;
	PdfPTable hstable = null;
	String headStr = null;
	int columns = 0;
	if(appConfig != null) {
		if(appConfig.getConfigCode().equals("ADV")) { 
			hstable = new PdfPTable(9);
			columns = 9;
		}else {
			hstable = new PdfPTable(9);
			columns = 9;
		}
	}
	hstable.setWidthPercentage(100f);
	
	StudySubjects subject = dcdDto.getSubMap().get(svr.getId());
	TreatmentInfo tinf = null;
	Map<Long, TreatmentInfo> tfMap = null;
	if(subject != null) {
		tfMap = dcdDto.getSubwtrMap().get(subject.getReportingId().getId());
		if(tfMap != null && tfMap.size() > 0) {
			tinf = tfMap.get(spm.getId());
		}
	}
	Map<String, SubjectMealsTimePointsData> smtpDataMap = new HashMap<>();
	if(tinf != null) {
		Map<Long, Map<Long, Map<Long,List<SubjectMealsTimePointsData>>>> stdMealMap = dcdDto.getMealsCollMap();
		if(stdMealMap != null && stdMealMap.size() > 0){
			Map<Long, Map<Long, List<SubjectMealsTimePointsData>>> mealsMap = stdMealMap.get(subject.getId());
			if(mealsMap != null && mealsMap.size() > 0) {
				Map<Long, List<SubjectMealsTimePointsData>> twmealsMap = mealsMap.get(spm.getId());
				if(twmealsMap != null && twmealsMap.size() > 0) {
					List<SubjectMealsTimePointsData> mealsList = new ArrayList<>();
					if(tfMap != null && tfMap.size() > 0) {
						for(Map.Entry<Long, TreatmentInfo> trmap : tfMap.entrySet()) {
							List<SubjectMealsTimePointsData> mealsListTemp =  twmealsMap.get(trmap.getValue().getId());
							if(mealsListTemp != null && mealsListTemp.size() > 0) {
								mealsList.addAll(mealsListTemp);
							}
						}
					}
					if(mealsList != null && mealsList.size() > 0) {
						for(SubjectMealsTimePointsData smtpd : mealsList) {
							smtpDataMap.put(smtpd.getMealsTimePoint().getSign()+smtpd.getMealsTimePoint().getTimePoint(), smtpd);
						}
					}
				}
			}
		}
	}
//	if(tinf != null) {
		if(lsga.getGlobalActivity().isHeadding()) {
			String[] words = lsga.getName().split(" ");
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < words.length; i++) {
				String st = "";
				if(i==0)
			     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
				else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
			    builder.append(st);
			}
			cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setBackgroundColor(bgColor);//#776858
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(columns);
		    hstable.addCell(cell);
		    hstable.getDefaultCell().setBackgroundColor(bgColor);
		}
		for(int i=1; i<=2; i++) {
			if(i == 1) {
				headStr =messageSource.getMessage("label.date", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr.trim(), heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
				cell.setRowspan(2);
				hstable.addCell(cell);
				
				
				headStr =messageSource.getMessage("crf.mealType", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr.trim(), heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
				cell.setRowspan(2);
				hstable.addCell(cell);
				
				
				headStr =messageSource.getMessage("label.meals.scheduleTime", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr, heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
				cell.setRowspan(2);
				hstable.addCell(cell);
				
				headStr =messageSource.getMessage("crf.actualtime", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr, heading));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
				cell.setColspan(2);
				hstable.addCell(cell);
				
				String consumed = messageSource.getMessage("label.meals.consume", null,currentLocale);
				String asperStr = messageSource.getMessage("label.meals.aspermeal", null,currentLocale);
				String menu = messageSource.getMessage("label.meals.menu", null,currentLocale);
				String yesStr = messageSource.getMessage("label.gaYes", null,currentLocale);
				String noStr = messageSource.getMessage("label.gaNo", null,currentLocale);
				headStr = consumed+"\n "+asperStr+" "+ menu+"\n ("+yesStr+"/*"+noStr+")";
				cell = new PdfPCell(new Phrase(headStr.trim(), heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
				cell.setRowspan(2);
				hstable.addCell(cell);
				
				
				headStr = messageSource.getMessage("label.randomizationUpComments", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr.trim(), heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
				cell.setRowspan(2);
				hstable.addCell(cell);
				
				String headStr1 =messageSource.getMessage("label.performedBy", null,currentLocale);
				headStr =messageSource.getMessage("label.performedOn", null,currentLocale);
				cell = new PdfPCell(new Phrase(headStr1.trim()+"\n & \n"+headStr.trim(), heading));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
				cell.setRowspan(2);
				cell.setColspan(2);
				hstable.addCell(cell);
			}else {
				for(int j=1; j<=8; j++) {
					if(j==4) {
						headStr = messageSource.getMessage("crf.startTime", null,currentLocale);
						cell = new PdfPCell(new Phrase(headStr, heading));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						cell.setPadding(7f);
					    cell.setNoWrap(false);
					    hstable.addCell(cell);
					}
					if(j==5) {
						headStr = messageSource.getMessage("crf.endTime", null,currentLocale);
						cell = new PdfPCell(new Phrase(headStr, heading));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						cell.setPadding(7f);
					    cell.setNoWrap(false);
					    hstable.addCell(cell);
					}
				}
			}
		}
		hstable.getDefaultCell().setBackgroundColor(new BaseColor(162, 159, 159));
		SubjectDoseTimePoints sdtp = null;
		Date doseDate = null;
	    if(dcdDto.getSdtpMap() != null) {
	    	if(subject != null) {
	    		if(dcdDto.getSdtpMap().get(subject.getId()) != null) {
	    			if(dcdDto.getSdtpMap().get(subject.getId()).get(spm.getId()) != null) {
	    				if(tfMap != null && tfMap.size() > 0) {
	    					List<SubjectDoseTimePoints> subDList = new ArrayList<>();
	    			    	for(Map.Entry<Long, TreatmentInfo> trmap : tfMap.entrySet()) {
	    			    		List<SubjectDoseTimePoints> subDTempList = dcdDto.getSdtpMap().get(subject.getId()).get(spm.getId()).get(trmap.getValue().getId());
	    			    		if(subDTempList != null && subDTempList.size() > 0) {
	    			    			subDList.addAll(subDTempList);
	    			    		}
							}
	    			    	if(subDList != null && subDList.size() > 0) {
	    			    		sdtp = subDList.get(0);
	    			    		doseDate = sdtp.getActualTime();
							}
	    				}
	    			}
	    		}
	    	}
	    }
		List<MealsTimePoints> mealsTimePoints = dcdDto.getMealsMap().get(tinf.getId());
		if(mealsTimePoints != null && mealsTimePoints.size() > 0) {
			Collections.sort(mealsTimePoints);
			SimpleDateFormat sdf = new SimpleDateFormat(dateStr);
			Calendar dcal = null;
			for (MealsTimePoints meal : mealsTimePoints) {
				SubjectMealsTimePointsData smtpd = smtpDataMap.get(meal.getSign()+meal.getTimePoint());
				headStr = "";
				String tpDate = "";
				TimeDto tdDto = getTimeDetails(meal.getTimePoint());
		    	if(sdtp != null) {
		    		dcal = getDateAndTime(tdDto, sdtp.getActualTime(), meal);
		    	}
		    	if(dcal != null) {
		    		SimpleDateFormat sdfDate = new SimpleDateFormat(dateStr);
					tpDate = sdfDate.format(dcal.getTime());
		    	}
		    	cell = new PdfPCell(new Phrase(tpDate, regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
				
				String sign = meal.getSign();
				if(sign == null || sign.equals(""))
					sign = "+";
				headStr = meal.getMealsType().getFieldValue() + "\n(" + sign + meal.getTimePoint() + " hr)";
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
				
				headStr = "";
				Date sheduleDate = null;
				if(doseDate != null) {
					Calendar cal = getDateAndTime(tdDto, doseDate, meal);
					String hours = cal.get(Calendar.HOUR_OF_DAY)+"";
					String minutes = cal.get(Calendar.MINUTE)+"";
					if(hours.length() == 1)
						hours = "0"+hours;
					if(minutes.length() ==1)
						minutes = "0"+minutes;
					headStr = hours+":"+minutes;
				}
				else if(smtpd != null) {
					if(smtpd.getScheduleTime() != null)
						headStr = getOnlyTime(smtpd.getScheduleTime());
				}
				cell = new PdfPCell(new Phrase(headStr, regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
				
				headStr = "";
				if(smtpd != null) {
					if(smtpd.getStartTime() !=null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(smtpd.getStartTime());
						String hours = cal.get(Calendar.HOUR_OF_DAY)+"";
						String minutes = cal.get(Calendar.MINUTE)+"";
						if(hours.length() == 1)
							hours = "0"+hours;
						if(minutes.length() ==1)
							minutes = "0"+minutes;
						headStr = hours +":"+minutes;
					}
				}
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
				
				headStr = "";
				if(smtpd != null) {
					if(smtpd.getEndTime() !=null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(smtpd.getEndTime());
						String hours = cal.get(Calendar.HOUR_OF_DAY)+"";
						String minutes = cal.get(Calendar.MINUTE)+"";
						if(hours.length() == 1)
							hours = "0"+hours;
						if(minutes.length() ==1)
							minutes = "0"+minutes;
						headStr = hours +":"+minutes;
					}
				}
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
			    
			    //Yes or No
			    headStr = "";
			    String yesStr = messageSource.getMessage("label.gaYes", null,currentLocale);
				String noStr = messageSource.getMessage("label.gaNo", null,currentLocale);
				if(smtpd != null) {
			    	if(smtpd.getActualtime() != null && smtpd.getScheduleTime() != null) {
					    /*String timePoint = meal.getTimePoint();
						String[] hrmim = timePoint.split("\\.");
						int min = Integer.parseInt(hrmim[0]) * 60;
						min += ((Integer.parseInt(hrmim[1]) * 6) / 100);*/
			    		Calendar c = Calendar.getInstance();
						c.setTime(smtpd.getScheduleTime());
			    		if(meal.getWindowPeriodSign().equals("PLUS")) 
			    			c.add(Calendar.MINUTE, meal.getWindowPeriod());
			    		else if(meal.getWindowPeriodSign().equals("MINUS"))
			    			c.add(Calendar.MINUTE, -meal.getWindowPeriod());
			    		else if(meal.getWindowPeriodSign().equals("PLUSANDMINUS")) {
			    			//Plus Condition
			    			Calendar plusCal = Calendar.getInstance();
			    			plusCal.setTime(smtpd.getScheduleTime());
			    			plusCal.add(Calendar.MINUTE, meal.getWindowPeriod());
			    			Map<String, Long> datesMap = getDatesDifference(plusCal.getTime(), smtpd.getStartTime());
			    			String plusStr = "";
			    			String minusStr = "";
			    			if(datesMap != null && datesMap.size() > 0) {
			    				int schHour = c.get(Calendar.HOUR_OF_DAY);
			    				int schMinutes = c.get(Calendar.MINUTE);
			    				Long hours = datesMap.get("Hours");
					            Long minutes = datesMap.get("Minutes");
			    				if(schHour == Integer.parseInt(hours+"")) {
			    					if(schMinutes >= Integer.parseInt(minutes+""))
			    						plusStr = "Yes";
			    					else plusStr = "No";
			    				}else plusStr = "No";
			    			}
			    			
			    			//Minus condition
			    			Calendar minusCal = Calendar.getInstance();
			    			minusCal.setTime(smtpd.getScheduleTime());
			    			minusCal.add(Calendar.MINUTE, -meal.getWindowPeriod());
			    			Map<String, Long> minusdatesMap = getDatesDifference(plusCal.getTime(), smtpd.getStartTime());
			    			if(minusdatesMap != null && minusdatesMap.size() > 0) {
			    				int schHour = c.get(Calendar.HOUR_OF_DAY);
			    				int schMinutes = c.get(Calendar.MINUTE);
			    				Long hours = minusdatesMap.get("Hours");
					            Long minutes = minusdatesMap.get("Minutes");
			    				if(schHour == Integer.parseInt(hours+"")) {
			    					if(schMinutes <= Integer.parseInt(minutes+""))
			    						minusStr = "Yes";
			    					else minusStr = "No";
			    				}else minusStr = "No";
			    			}
			    			if(plusStr.equals("No") || minusStr.equals("No"))
			    				headStr = noStr;
			    			else headStr = yesStr;
			    		}
			    		
						Map<String, Long> datesMap = getDatesDifference(c.getTime(), smtpd.getStartTime());
						if(datesMap != null && datesMap.size() > 0) {
							Long hours = datesMap.get("Hours");
				            Long minutes = datesMap.get("Minutes");
				            boolean hrFlag = false;
				            boolean minFlag = false;
				            if(hours != 0)
				            	hrFlag = true;
				            if(minutes != 0)
				            	minFlag = true;
				            if(hrFlag || minFlag)
				            	headStr = noStr;
				            else headStr = yesStr;
						}
			    	}else headStr = "";
			    }
			   cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPaddingTop(3f);
			    cell.setPaddingBottom(7f);
			    cell.setPaddingLeft(7f);
			    cell.setPaddingRight(7f);
//				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
				
			    if(smtpd != null && smtpd.getComments() != null)
			    	headStr = smtpd.getComments();
				cell = new PdfPCell(new Phrase(headStr.trim(), regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    hstable.addCell(cell);
			    
			    String headStr1 = "";
			    String headStr2 = "";
			    if(smtpd != null  && smtpd.getCreatedBy() != null) {
			    	headStr1 = smtpd.getCreatedBy().getFullName();
			    } 
			    if(smtpd != null && smtpd.getCreatedOn() != null) {
			    	SimpleDateFormat sdfDate = new SimpleDateFormat(dateStr);
			    	headStr2 = sdfDate.format(smtpd.getCreatedOn());
			    }
			    
				cell = new PdfPCell(new Phrase(headStr1.trim()+"\n"+headStr2, regular));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setColspan(2);
			    hstable.addCell(cell);
			}
		}
		headStr = messageSource.getMessage("label.meals.col1", null,currentLocale);
		cell = new PdfPCell(new Phrase(headStr.trim(), regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
//		cell.setPadding(7f);
	    cell.setNoWrap(false);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(9);
		hstable.addCell(cell);
		
		headStr = messageSource.getMessage("label.randomizationUpComments", null,currentLocale);
		cell = new PdfPCell(new Phrase(headStr.trim()+": ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(Rectangle.NO_BORDER);
//		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    cell.setNoWrap(false);
	    hstable.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("", regular));
	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    cell.setColspan(8);
	    cell.setNoWrap(false);
	    hstable.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("      Reviewed by:      _____________________________ \n  (PI/PIC/Designee)                (Sign & Date) ", regular));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(Rectangle.NO_BORDER);
//		cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    cell.setNoWrap(false);
	    cell.setColspan(9);
	    hstable.addCell(cell);
		
		hstable.setHeaderRows(3);
		document.add(hstable);
}

private String getOnlyTime(Date scheduleTime) {
	String time = "";
	try {
		Calendar cal = Calendar.getInstance();
		cal.setTime(scheduleTime);
		String hours = cal.get(Calendar.HOUR_OF_DAY)+"";
		String minutes = cal.get(Calendar.MINUTE)+"";
		if(hours.length() == 1)
			hours = "0"+hours;
		if(minutes.length() ==1)
			minutes = "0"+minutes;
		time = hours +":"+minutes;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return time;
}

private Calendar getDateAndTime(TimeDto tdDto, Date doseDate, MealsTimePoints meal) {
	Calendar dcal = Calendar.getInstance();
	try {
		if(doseDate != null)
			dcal.setTime(doseDate);
		if(tdDto.getHours() != 0) {
			if(meal.getSign() != null && !meal.getSign().equals(""))
				dcal.add(Calendar.HOUR_OF_DAY, -tdDto.getHours());
			else dcal.add(Calendar.HOUR_OF_DAY, tdDto.getHours());
		}
		if(tdDto.getMinutes() != 0) {
			if(meal.getSign() != null && !meal.getSign().equals(""))
				dcal.add(Calendar.MINUTE, -tdDto.getMinutes());
			else dcal.add(Calendar.MINUTE, tdDto.getMinutes());
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return dcal;
}

@SuppressWarnings("unused")
private Map<String, Long> getDatesDifference(Date date1, Date date2) {
		Map<String, Long> datesMap = new HashMap<>();
        try {   
            // Calucalte time difference in milliseconds   
            Long time_difference = date2.getTime() - date1.getTime();  
            // Calucalte time difference in days  
            Long days_difference = (time_difference / (1000*60*60*24)) % 365;   
            // Calucalte time difference in years  
            Long years_difference = (time_difference / (1000l*60*60*24*365));   
            // Calucalte time difference in seconds  
            Long seconds_difference = (time_difference / 1000)% 60;   
            // Calucalte time difference in minutes  
            Long minutes_difference = (time_difference / (1000*60)) % 60;   
              
            // Calucalte time difference in hours  
            Long hours_difference = (time_difference / (1000*60*60)) % 24;   
            // Show difference in years, in days, hours, minutes, and seconds   
            datesMap.put("Hours", hours_difference);
            datesMap.put("Minutes", minutes_difference);
            datesMap.put("Seconds", seconds_difference);
            datesMap.put("Days", days_difference);
        }   
        // Catch parse exception   
        catch (Exception e) {   
            e.printStackTrace();   
        } 
        return datesMap;
    

}

private void dosingRecordForAdvity(Document document, Font regular, Image unchkdCbImage, Image chkedCbImage,
		Image chkedradioimg, Image unchkradioimg, Font heading, Locale currentLocale, MessageSource messageSource,
		Long langId, LanguageSpecificGlobalActivityDetails lsga, BaseColor bgColor, Font mainHeading, Font subHeading,
		Font actHeadFont, DataCrfDtoDetails dcdDto, StudyVolunteerReporting svr, StudyPeriodMaster spm, String dateStrWithTime, String dateStr) throws DocumentException {
	PdfPCell cell = null;
	PdfPTable hstable = null;
	String headStr = null;
	hstable = new PdfPTable(4);
	hstable.setWidthPercentage(100f);
	try {
		StudySubjects subject = dcdDto.getSubMap().get(svr.getId());
		TreatmentInfo tinf = null;
		Map<Long, TreatmentInfo> tfMap = null;
		if(subject != null) {
			tfMap = dcdDto.getSubwtrMap().get(subject.getReportingId().getId());
			if(tfMap != null && tfMap.size() > 0) {
				if(tfMap != null && tfMap.size() > 0) {
					tinf = tfMap.get(spm.getId());
				}
			}
		}
		String dosingComments = "";
		SubjectDoseTimePoints sdtpPojo = null;
		if(tinf != null) {
			Map<Long, Map<Long, Map<Long,List<SubjectDoseTimePoints>>>> sdtpMap = dcdDto.getSdtpMap();
			List<SubjectDoseTimePoints> subDtpList = null;
			List<SubjectDoseTimePointParametersData> subDtpParamList  = null;
			Map<Long, SubjectDoseTimePoints> sdtMap = new HashMap<>();
	    	Map<Long, Map<Long, SubjectDoseTimePointParametersData>> sdtParamMap = new HashMap<>();
			if(sdtpMap != null && sdtpMap.size() > 0){
				Map<Long, Map<Long, List<SubjectDoseTimePoints>>> doseMap = sdtpMap.get(subject.getId());
				if(doseMap != null && doseMap.size() > 0) {
					Map<Long, List<SubjectDoseTimePoints>> twdoseMap = doseMap.get(spm.getId());
					if(twdoseMap != null && twdoseMap.size() > 0) {
						List<SubjectDoseTimePoints> sdtList = twdoseMap.get(tinf.getId());
//						int count =1;
						if(sdtList != null && sdtList.size() > 0) {
							if(dcdDto.getSdtpMap().get(subject.getId()) != null ) {
								if(dcdDto.getSdtpMap().get(subject.getId()).get(spm.getId()) != null) {
									subDtpList = dcdDto.getSdtpMap().get(subject.getId()).get(spm.getId()).get(tinf.getId()); //SubjectId, Period, treatment, Listst of vitals
									if(dcdDto.getDoseParamMap() != null) {
										if(dcdDto.getDoseParamMap().get(subject.getId()) != null) {
											if(dcdDto.getDoseParamMap().get(subject.getId()).get(spm.getId()) != null) {
												subDtpParamList  = dcdDto.getDoseParamMap().get(subject.getId()).get(spm.getId()).get(tinf.getId()); //SubjectId, Period, treatment, List of Vitalparameters
											}
										}
									}
								}
							}
							if(subDtpList != null && subDtpList.size() > 0) {
					    		for(SubjectDoseTimePoints sdtp : subDtpList) {
					    			sdtMap.put(sdtp.getDoseTimePoints().getId(), sdtp);
					    		}
					    	}
					    	Map<Long, SubjectDoseTimePointParametersData> tempMap = null;
					    	if(subDtpParamList != null && subDtpParamList.size() > 0) {
					    		for(SubjectDoseTimePointParametersData sdtpd : subDtpParamList) {
					    			tempMap = sdtParamMap.get(sdtpd.getSubjectDoseTimePoint().getDoseTimePoints().getId());
					    			if(tempMap == null)
					    				tempMap = new HashMap<>();
					    			tempMap.put(sdtpd.getParameter().getId(), sdtpd);
					    			sdtParamMap.put(sdtpd.getSubjectDoseTimePoint().getDoseTimePoints().getId(), tempMap);
					    		}
					    	}
						}
					}
				}
			}
			    	if(lsga.getGlobalActivity().isHeadding()) {
						@SuppressWarnings("unused")
						Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(255,255,255));
						String[] words = lsga.getName().split(" ");
						StringBuilder builder = new StringBuilder();
						for (int i = 0; i < words.length; i++) {
							String st = "";
							if(i==0)
						     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
							else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
						    builder.append(st);
						}
						cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					    cell.setBackgroundColor(bgColor);//#776858
					    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setColspan(4);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//										    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    hstable.addCell(cell);
					    hstable.getDefaultCell().setBackgroundColor(bgColor);
					}
			    	headStr = messageSource.getMessage("label.advDose.doseCal", null,currentLocale);
					headStr = headStr+" " + dcdDto.getStudy().getDosageFrom().getDoseForm();
					cell = new PdfPCell(new Phrase(headStr.trim(), regular));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//								    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setColspan(4);
					hstable.addCell(cell);
					
					headStr = messageSource.getMessage("crf.date", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr.trim(), regular));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//								    cell.setPadding(7f);
				    cell.setNoWrap(false);
					hstable.addCell(cell);
					
					
					headStr = messageSource.getMessage("label.advDose.sdt", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr.trim(), regular));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//								    cell.setPadding(7f);
				    cell.setNoWrap(false);
					hstable.addCell(cell);
					
					
					headStr = messageSource.getMessage("label.advDose.adt", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr.trim(), regular));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//								    cell.setPadding(7f);
				    cell.setNoWrap(false);
					hstable.addCell(cell);
					
					headStr = messageSource.getMessage("label.advDose.vu", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr.trim(), regular));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//								    cell.setPadding(7f);
				    cell.setNoWrap(false);
					hstable.addCell(cell);
					
					DosingInfoDetails dosingInf = null;
					if(dcdDto.getDrugInfMap() != null) {
						if(tinf != null) {
							dosingInf = dcdDto.getDrugInfMap().get(tinf.getRandamizationCode());
						}
					}
					if(sdtMap != null && sdtMap.size() > 0) {
						for(Map.Entry<Long, SubjectDoseTimePoints> dmap : sdtMap.entrySet()) {
							SimpleDateFormat sdf = new SimpleDateFormat(dateStr);
							String randomizationCode = "";
//									for(SubjectDoseTimePoints dtp : sdtList) {
							sdtpPojo = dmap.getValue();
								if(randomizationCode.equals(""))
									randomizationCode = sdtpPojo.getDoseTimePoints().getTreatmentInfo().getRandamizationCode();
//										SubjectDoseTimePoints sdtpPojo = sdtMap.get(dtp.getDoseTimePoints().getId());
								headStr = "";
								if(sdtpPojo != null) {
									dosingComments = sdtpPojo.getDeviationMsg().getMessage();
									if(sdtpPojo.getActualTime() != null) {
										headStr = sdf.format(sdtpPojo.getActualTime());
									}
								}
								cell = new PdfPCell(new Phrase(headStr.trim(), regular));
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							    cell.setVerticalAlignment(Element.ALIGN_CENTER);
							    cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//												    cell.setPadding(7f);
							    cell.setNoWrap(false);
							    hstable.addCell(cell);
								
							    String scheduleTime = "";
							    if(sdtpPojo != null) {
									if(sdtpPojo.getScheduleTime() != null) {
										Calendar cal = Calendar.getInstance();
										cal.setTime(sdtpPojo.getScheduleTime());
										String hours = cal.get(Calendar.HOUR_OF_DAY)+"";
										String minutes = cal.get(Calendar.MINUTE)+"";
										if(hours.length() == 1)
											hours = "0"+hours;
										if(minutes.length() ==1)
											minutes = "0"+minutes;
										scheduleTime = hours+":"+minutes;
									}
								}
								cell = new PdfPCell(new Phrase(scheduleTime, regular));
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							    cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//												    cell.setPadding(7f);
							    cell.setNoWrap(false);
							    hstable.addCell(cell);

							    String actualTime = "";
							    if(sdtpPojo != null) {
									if(sdtpPojo.getActualTime() != null) {
										Calendar cal = Calendar.getInstance();
										cal.setTime(sdtpPojo.getActualTime());
										String hours = cal.get(Calendar.HOUR_OF_DAY)+"";
										String minutes = cal.get(Calendar.MINUTE)+"";
										if(hours.length() == 1)
											hours = "0"+hours;
										if(minutes.length() ==1)
											minutes = "0"+minutes;
										actualTime = hours+":"+minutes;
									}
								}
								cell = new PdfPCell(new Phrase(actualTime, regular));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							    cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//												    cell.setPadding(7f);
							    cell.setNoWrap(false);
							    hstable.addCell(cell);
								
								headStr = sdtpPojo.getDoseTimePoints().getTreatmentInfo().getDose();
								cell = new PdfPCell(new Phrase(headStr.trim(), regular));
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							    cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//												    cell.setPadding(7f);
							    cell.setNoWrap(false);
							    hstable.addCell(cell);

						    PdfPTable table = new PdfPTable(3);
							table.setWidthPercentage(30f);
							table.setWidths(new int[]{5, 10, 5});
							
							cell = new PdfPCell(new Phrase("Name of IP : ", regular));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPaddingTop(7f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
						    table.addCell(cell);
						    
						    String nameOfIp = "";
						    if(dosingInf != null) {
						    	nameOfIp = dosingInf.getNameOfIp();
						    }
						    cell = new PdfPCell(new Phrase(nameOfIp, regular));
						    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setPaddingTop(7f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
						    cell.setColspan(2);
						    table.addCell(cell);
							
							cell = new PdfPCell(new Phrase("No. of Units : ", regular));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPaddingTop(7f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
						    table.addCell(cell);
						    
						    String noOfUnits = "";
						    if(dosingInf != null) {
						    	noOfUnits = dosingInf.getNoOfUnits();
						    }
						    cell = new PdfPCell(new Phrase(noOfUnits, regular));
						    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setPaddingTop(7f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
						    table.addCell(cell);
						    
						    PdfPTable table2 = new PdfPTable(1);
						    table2.setWidthPercentage(30f);
//								    table2.setWidths(new int[]{20});
						    
						    Font blodText = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD);
					 	    cell = new PdfPCell(new Phrase(randomizationCode, blodText));
						    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						    cell.setVerticalAlignment(Element.ALIGN_CENTER);
						    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						    cell.setBorderWidth(2f);
						    cell.setPaddingTop(7f);
						    cell.setPaddingBottom(12f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
						    cell.setRowspan(3);
						    table2.addCell(cell);
						    
						    cell = new PdfPCell();
						    cell.addElement(table2);
						    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						    cell.setVerticalAlignment(Element.ALIGN_CENTER);
						    cell.setPaddingTop(15f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
						    cell.setRowspan(3);
						    table.addCell(cell);
						    
						    cell = new PdfPCell(new Phrase("Batch No : ", regular));
						    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setPaddingTop(7f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
						    table.addCell(cell);
						    
						    String batchNo = "";
						    if(dosingInf != null) {
						    	batchNo = dosingInf.getBatchNo()+"";
						    }
						    cell = new PdfPCell(new Phrase(batchNo, regular));
						    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setPaddingTop(7f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
						    table.addCell(cell);
						    
						    cell = new PdfPCell(new Phrase("Expiry Date : ", regular));
						    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setPaddingTop(7f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
						    table.addCell(cell);
						    
						    String expDate = "";
						    if(dosingInf != null) {
						    	if(dosingInf.getExpDate() != null)
						    		expDate = sdf.format(dosingInf.getExpDate());
						    }
						    cell = new PdfPCell(new Phrase(expDate, regular));
						    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						    cell.setPaddingTop(7f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
						    table.addCell(cell);
						    
						    
						    cell = new PdfPCell(table);
						    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPaddingTop(10f);
						    cell.setPaddingBottom(10f);
						    cell.setPaddingLeft(90f);
						    cell.setPaddingRight(90f);
						    cell.setColspan(4);
						    hstable.addCell(cell);
							
							document.add(new Paragraph("\n"));
//									int incNo = 1;
						}
					}else {
						SimpleDateFormat sdf = new SimpleDateFormat(dateStr);
						cell = new PdfPCell(new Phrase("", regular));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					    cell.setVerticalAlignment(Element.ALIGN_CENTER);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//										    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    hstable.addCell(cell);
						
						
						cell = new PdfPCell(new Phrase("", regular));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//										    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    hstable.addCell(cell);
						
						
						cell = new PdfPCell(new Phrase("", regular));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//										    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    hstable.addCell(cell);
						
						cell = new PdfPCell(new Phrase(tinf.getDose(), regular));
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//										    cell.setPadding(7f);
					    cell.setNoWrap(false);
					    hstable.addCell(cell);
						
						PdfPTable table = new PdfPTable(3);
					table.setWidthPercentage(30f);
					table.setWidths(new int[]{5, 10, 5});
					
					cell = new PdfPCell(new Phrase("Name of IP : ", regular));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(7f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    table.addCell(cell);
				    
				    String nameOfIp = "";
				    if(dosingInf != null) {
				    	nameOfIp = dosingInf.getNameOfIp();
				    }
				    cell = new PdfPCell(new Phrase(nameOfIp, regular));
				    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(7f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    cell.setColspan(2);
				    table.addCell(cell);
					
					cell = new PdfPCell(new Phrase("No. of Units : ", regular));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(7f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    table.addCell(cell);
				    
				    String noOfUnits = "";
				    if(dosingInf != null) {
				    	noOfUnits = dosingInf.getNoOfUnits();
				    }
				    cell = new PdfPCell(new Phrase(noOfUnits, regular));
				    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(7f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    table.addCell(cell);
				    
				    PdfPTable table2 = new PdfPTable(1);
				    table2.setWidthPercentage(30f);
//						    table2.setWidths(new int[]{20});
				    
				    Font blodText = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD);
			 	    cell = new PdfPCell(new Phrase(tinf.getRandamizationCode(), blodText));
				    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    cell.setVerticalAlignment(Element.ALIGN_CENTER);
				    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setBorderWidth(2f);
				    cell.setPaddingTop(7f);
				    cell.setPaddingBottom(12f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    cell.setRowspan(3);
				    table2.addCell(cell);
				    
				    cell = new PdfPCell();
				    cell.addElement(table2);
				    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    cell.setVerticalAlignment(Element.ALIGN_CENTER);
				    cell.setPaddingTop(15f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    cell.setRowspan(3);
				    table.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase("Batch No : ", regular));
				    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(7f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    table.addCell(cell);
				    
				    String batchNo = "";
				    if(dosingInf != null) {
				    	batchNo = dosingInf.getBatchNo()+"";
				    }
				    cell = new PdfPCell(new Phrase(batchNo, regular));
				    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(7f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    table.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase("Expiry Date : ", regular));
				    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(7f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    table.addCell(cell);
				    
				    String expDate = "";
				    if(dosingInf != null) {
				    	if(dosingInf.getExpDate() != null)
				    		expDate = sdf.format(dosingInf.getExpDate());
				    }
				    cell = new PdfPCell(new Phrase(expDate, regular));
				    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    cell.setPaddingTop(7f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
				    table.addCell(cell);
				    
				    
				    cell = new PdfPCell(table);
				    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(10f);
				    cell.setPaddingBottom(10f);
				    cell.setPaddingLeft(90f);
				    cell.setPaddingRight(90f);
				    cell.setColspan(4);
				    hstable.addCell(cell);
					
					document.add(new Paragraph("\n"));
					}
					
					 Map<Long, Map<Long, Map<Long, List<SubjectDoseTimePointParametersData>>>> doseParamMap = dcdDto.getDoseParamMap();
					    if(doseParamMap != null && doseParamMap.size() > 0) {
					    	Map<Long, Map<Long, List<SubjectDoseTimePointParametersData>>> pwdoseParamMap = doseParamMap.get(subject.getId());
					        if(pwdoseParamMap != null && pwdoseParamMap.size() > 0) {
					        	Map<Long, List<SubjectDoseTimePointParametersData>> twdoseParamMap = pwdoseParamMap.get(spm.getId());
					        	if(twdoseParamMap != null && twdoseParamMap.size() > 0) {
					        		List<SubjectDoseTimePointParametersData> sdtparamList = twdoseParamMap.get(tinf.getId());
					        		if(sdtparamList != null && sdtparamList.size() > 0) {
					        			for(SubjectDoseTimePointParametersData param : sdtparamList) {
					        				LanguageSpecificGlobalParameterDetails lsgp = dataCrfDao.getLanguageSpecificParametersDetailsRecord(langId, param.getParameter().getId());
					        				if(lsgp != null) {
//								        			    	labelStr =  messageSource.getMessage("label.dosing.parm", null,currentLocale);
							    				cell = new PdfPCell(new Phrase(lsgp.getName(), regular));
							    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							    				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);;
							    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
//									    				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
							    				cell.setPaddingTop(3f);
							    			    cell.setPaddingBottom(7f);
							    			    cell.setPaddingLeft(7f);
							    			    cell.setPaddingRight(7f);
//										    				cell.setPadding(7f);
							    			    cell.setNoWrap(false);
							    			    if(lsgp.getParameterId().getContentType().getCode().equals("LABEL"))
							    			    	cell.setColspan(4);
							    			    else cell.setColspan(3);
							    				hstable.addCell(cell);
					        			    	String type = lsgp.getParameterId().getContentType().getCode();
					        			    	List<LanguageSpecificValueDetails> lspgvList = null;
							    				if(type.equals("CB") || type.equals("RB") || type.equals("SB")) {
							    					lspgvList = dataCrfDao.getLanguageSpecificGlobalValusesList(lsgp.getParameterId(), langId);
							    					PdfPTable conTab = new PdfPTable(1);
							    					conTab.setWidthPercentage(100f);
							    					Paragraph p = new Paragraph();
							    					Paragraph pg = new Paragraph();
							    					if(type.equals("RB")) {
							    						for(LanguageSpecificValueDetails lsvd : lspgvList) {
							    							Phrase pp = new Phrase(100);
					    									if(lsvd.getGlobalValId().getId() == param.getGlobalValue().getId()) {
//									    										p.add(new Chunk(unchkradioimg, 0, -2));
//									    										p.setAlignment(Element.ALIGN_TOP);
					    										Chunk radoButton = new Chunk(chkedradioimg, 0, 0, true);
					    										p.add(radoButton);
					    										p.add(" ");
					    										p.setAlignment(Element.WRITABLE_DIRECT);
					    										Paragraph pgr = new Paragraph(lsvd.getName()+" ", regular);
//									    										pg.setAlignment(Element.ALIGN_CENTER);
					    										pg.setAlignment(Element.WRITABLE_DIRECT);
					    										pp.add(pgr);
					    										p.add(pp);
					    									}else {
					    										Chunk radoButton = new Chunk(unchkradioimg, 0, 0, true);
					    										p.add(radoButton);
					    										p.add(" ");
					    										p.setAlignment(Element.WRITABLE_DIRECT);
					    										Paragraph pgr = new Paragraph(lsvd.getName()+" ", regular);
//									    										pg.setAlignment(Element.ALIGN_CENTER);
					    										pg.setAlignment(Element.WRITABLE_DIRECT);
					    										pp.add(pgr);
					    										p.add(pp);
					    									}
							    						}
							    						cell = new PdfPCell();
							    						cell.addElement(p);
							    						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							    						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							    						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							    						cell.setNoWrap(false);
							    						cell.setPaddingTop(3f);
							    					    cell.setPaddingBottom(7f);
							    					    cell.setPaddingLeft(7f);
							    					    cell.setPaddingRight(7f);
							    						hstable.addCell(cell);
							    					}else if(type.equals("CB")) {
							    						for(LanguageSpecificValueDetails lsvd : lspgvList) {
							    							Phrase pp = new Phrase(100);
					    									if(lsvd.getGlobalValId().getId() == param.getGlobalValue().getId()) {
//									    										p.add(new Chunk(unchkImage, 0, -2));
//									    										p.setAlignment(Element.ALIGN_TOP);
					    										Chunk chunkCheckBoxNo1 = new Chunk(chkedCbImage, 0, 0, false);
					    										p.add(chunkCheckBoxNo1);
					    										p.add(" ");
					    										p.setAlignment(Element.WRITABLE_DIRECT);
					    										Paragraph pgc = new Paragraph(lsvd.getName()+" ", regular);
					    										pg.setAlignment(Element.WRITABLE_DIRECT);
					    										pp.add(pgc);
					    										p.add(pp);
					    									}else {
//									    										p.add(new Chunk(unchkImage, 0, -2));
//									    										p.setAlignment(Element.ALIGN_TOP);
					    										Chunk chunkCheckBoxNo2 = new Chunk(unchkdCbImage, 0, 0, false);
					    										p.add(chunkCheckBoxNo2);
					    										p.add(" ");
					    										p.setAlignment(Element.WRITABLE_DIRECT);
					    										Paragraph pgc = new Paragraph(lsvd.getName()+" ", regular);
					    										pg.setAlignment(Element.WRITABLE_DIRECT);
					    										pp.add(pgc);
					    										p.add(pp);
					    									}
							    						}
							    					}else if(type.equals("SB")) {
							    						PdfPTable gvTab = new PdfPTable(1);
							    						for(LanguageSpecificValueDetails lsvd : lspgvList) {
					    									if(lsvd.getGlobalValId().getId() == param.getGlobalValue().getId()) {
					    										cell = new PdfPCell(new Phrase(lsvd.getName(), regular));
					    										cell.setBorder(Rectangle.NO_BORDER);
					    										cell.setNoWrap(false);
					    										cell.setPaddingTop(3f);
					    									    cell.setPaddingBottom(7f);
					    									    cell.setPaddingLeft(7f);
					    									    cell.setPaddingRight(7f);
//									    										cell.setPadding(7f);
					    									    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					    										cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    										gvTab.addCell(cell);
					    									}else {
					    										cell = new PdfPCell(new Phrase("", regular));
					    										cell.setBorder(Rectangle.NO_BORDER);
					    										cell.setNoWrap(false);
					    										cell.setPaddingTop(3f);
					    									    cell.setPaddingBottom(7f);
					    									    cell.setPaddingLeft(7f);
					    									    cell.setPaddingRight(7f);
//									    										cell.setPadding(7f);
					    									    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					    										cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    										gvTab.addCell(cell);
					    									}
							    						}
							    						cell = new PdfPCell(gvTab);
							    						cell.setBorder(Rectangle.NO_BORDER);
							    						cell.setNoWrap(false);
							    						cell.setPaddingTop(3f);
							    					    cell.setPaddingBottom(7f);
							    					    cell.setPaddingLeft(7f);
							    					    cell.setPaddingRight(7f);
							    					    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							    						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							    						conTab.addCell(cell);
							    						
							    						cell = new PdfPCell(conTab);
							    						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							    						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							    						cell.setPaddingTop(3f);
							    					    cell.setPaddingBottom(7f);
							    					    cell.setPaddingLeft(7f);
							    					    cell.setPaddingRight(7f);
//									    						cell.setPadding(7f);
							    					    cell.setNoWrap(false);
							    						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							    						hstable.addCell(cell);
							    					}
							    				}else {
							    					if(!lsgp.getParameterId().getContentType().getCode().equals("LABEL")) {
							    						cell = new PdfPCell((new Phrase("", regular)));
									    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									    				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									    				cell.setPaddingTop(3f);
									    			    cell.setPaddingBottom(7f);
									    			    cell.setPaddingLeft(7f);
									    			    cell.setPaddingRight(7f);
//												    				cell.setPadding(7f);
									    			    cell.setNoWrap(false);
									    			    hstable.addCell(cell);
								    	
							    					}
							    				}
					        			    }
					        			}
					        		}
					        	}
					        }
					    }

					headStr = messageSource.getMessage("label.randomizationUpComments", null,currentLocale);
					cell = new PdfPCell(new Phrase(headStr.trim()+": "+dosingComments, regular));
					cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//									cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setColspan(4);
				    hstable.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase("", regular));
				    cell.setBorder(Rectangle.NO_BORDER);
				    cell.setFixedHeight(10f);
				    cell.setColspan(4);
				    hstable.addCell(cell);
				    
				    String doneBy = "";
				    String doneDate = "";
				    if(sdtpPojo != null) {
				    	doneBy = sdtpPojo.getCreatedBy().getFullName();
				        if(sdtpPojo.getCreatedOn() != null) {
				        	SimpleDateFormat sdfStr = new SimpleDateFormat(dateStr);
				        	doneDate = sdfStr.format(sdtpPojo.getCreatedOn());
				        }
					}
				    PdfPTable stable = new PdfPTable(6);
				    stable.setWidthPercentage(100f);
				    
				    cell = new PdfPCell(new Phrase("Dosed by: "+doneBy+" & "+doneDate+"\n                _________________________\n                          (Sign & Date)", regular));
				    cell.setBorder(Rectangle.NO_BORDER);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//									cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setColspan(2);
				    stable.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase("Supervised by: _________________________\n                                (Sign & Date)", regular));
				    cell.setBorder(Rectangle.NO_BORDER);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//									cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setColspan(2);
				    stable.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase("Reviewed by (PI/Designee): _________________________\n                                                      (Sign & Date)", regular));
				    cell.setBorder(Rectangle.NO_BORDER);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(3f);
				    cell.setPaddingBottom(7f);
				    cell.setPaddingLeft(7f);
				    cell.setPaddingRight(7f);
//									cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setColspan(2);
				    stable.addCell(cell);
				    
				    cell = new PdfPCell(stable);
				    cell.setBorder(Rectangle.NO_BORDER);
				    cell.setColspan(4);
				    hstable.addCell(cell);
				   
				    hstable.setHeaderRows(1);
					document.add(hstable);
//						}
//					}
//				}
//			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

private void dosingRecordForAvan(Document document, Font regular, Image unchkdCbImage, Image chkedCbImage, Image chkedradioimg,
		Image unchkradioimg, Font heading, Locale currentLocale, MessageSource messageSource, Long langId,
		LanguageSpecificGlobalActivityDetails lsga, BaseColor bgColor, Font mainHeading, Font subHeading,
		Font actHeadFont, DataCrfDtoDetails dcdDto, StudyVolunteerReporting svr, StudyPeriodMaster spm, String dateStrWithTime, String dateStr) throws DocumentException {
	PdfPCell cell = null;
	String labelStr = null;
	
	PdfPTable hstable = new PdfPTable(5);
	hstable.setWidthPercentage(100f);
	StudySubjects subject = null;
	TreatmentInfo tinf = null;
	Map<Long, TreatmentInfo> tfMap = null;
	try {
		subject = dcdDto.getSubMap().get(svr.getId());
		if(subject != null) {
			tfMap = dcdDto.getSubwtrMap().get(subject.getReportingId().getId());
			if(tfMap != null && tfMap.size() > 0) {
				/*Map.Entry<Long,TreatmentInfo> entry = tfMap.entrySet().iterator().next();
				tinf = entry.getValue();*/
				if(tfMap != null && tfMap.size() > 0) {
					tinf = tfMap.get(spm.getId());
				}
			}
			if(tinf != null) {
				Map<Long, Map<Long, Map<Long,List<SubjectDoseTimePoints>>>> sdtpMap = dcdDto.getSdtpMap();
				if(sdtpMap != null && sdtpMap.size() > 0){
					Map<Long, Map<Long, List<SubjectDoseTimePoints>>> doseMap = sdtpMap.get(subject.getId());
					if(doseMap != null && doseMap.size() > 0) {
						Map<Long, List<SubjectDoseTimePoints>> twdoseMap = doseMap.get(spm.getId());
						if(twdoseMap != null && twdoseMap.size() > 0) {
							List<SubjectDoseTimePoints> sdtList = twdoseMap.get(tinf.getId());
							int count =1;
							if(sdtList != null && sdtList.size() > 0) {
								for(SubjectDoseTimePoints dtp : sdtList) {
									if(count != 1) {
										PdfPTable space = new PdfPTable(1);
										space.setWidthPercentage(100f);
										
										cell = new PdfPCell(new Phrase(""));
										cell.setBorder(Rectangle.NO_BORDER);
										cell.setFixedHeight(7f);
										space.addCell(cell);
//											document.add(space);
									}
									if(lsga.getGlobalActivity().isHeadding()) {
										String[] words = lsga.getName().split(" ");
										StringBuilder builder = new StringBuilder();
										for (int i = 0; i < words.length; i++) {
											String st = "";
											if(i==0)
										     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
											else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
										    builder.append(st);
										}
										cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
										cell.setHorizontalAlignment(Element.ALIGN_LEFT);
										cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
										cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									    cell.setBackgroundColor(bgColor);//#776858
									    cell.setColspan(5);
									    cell.setPaddingTop(3f);
									    cell.setPaddingBottom(7f);
									    cell.setPaddingLeft(7f);
									    cell.setPaddingRight(7f);
//											    cell.setPadding(7f);
									    cell.setNoWrap(false);
										hstable.addCell(cell);
										hstable.getDefaultCell().setBackgroundColor(bgColor);
									}
									    
									//First Row
									labelStr =  messageSource.getMessage("crf.date", null,currentLocale);
									if(dtp.getActualTime() != null) {
										SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
										String doseDate = sdf.format(dtp.getActualTime());
										cell = new PdfPCell(new Phrase(labelStr+":  "+doseDate, heading));
									}
									else cell = new PdfPCell(new Phrase(labelStr+":  ", heading));
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setColspan(5);
									cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//											cell.setPadding(7f);
								    cell.setNoWrap(false);
								    hstable.addCell(cell);
									
									//Second Row
									labelStr =  messageSource.getMessage("label.dosing.Pf", null,currentLocale);
									cell = new PdfPCell(new Phrase(labelStr+"    ", heading));
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
									cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//											cell.setPadding(7f);
								    cell.setNoWrap(false);
								    hstable.addCell(cell);
									
									
									cell = new PdfPCell(new Phrase(dtp.getDoseTimePoints().getTreatmentInfo().getRandamizationCode()+"- "+dtp.getDoseTimePoints().getTreatmentInfo().getTreatmentName(), regular));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//											cell.setPadding(7f);
								    cell.setNoWrap(false);
									cell.setColspan(4);
									hstable.addCell(cell);
			
									//Third Row
									labelStr =  messageSource.getMessage("label.dosing.Strength", null,currentLocale);
									cell = new PdfPCell(new Phrase(labelStr+"    ", heading));
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
									cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//											cell.setPadding(7f);
								    cell.setNoWrap(false);
								    hstable.addCell(cell);
									
									cell = new PdfPCell(new Phrase(dtp.getDoseTimePoints().getTreatmentInfo().getStreanth(), regular));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//											cell.setPadding(7f);
								    cell.setNoWrap(false);
									cell.setColspan(2);
									hstable.addCell(cell);
									
									labelStr =  messageSource.getMessage("label.dosing.dose", null,currentLocale);
									cell = new PdfPCell(new Phrase(labelStr+"    ", heading));
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
									cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//											cell.setPadding(7f);
								    cell.setNoWrap(false);
								    hstable.addCell(cell);
									
									cell = new PdfPCell(new Phrase(dtp.getDoseTimePoints().getTreatmentInfo().getDose(), regular));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//											cell.setPadding(7f);
								    cell.setNoWrap(false);
								    hstable.addCell(cell);
									
									labelStr =  messageSource.getMessage("label.dosing.Admintime", null,currentLocale);
									cell = new PdfPCell(new Phrase(labelStr, heading));
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
									cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//													cell.setPadding(7f);
								    cell.setNoWrap(false);
								    hstable.addCell(cell);
									
									cell = new PdfPCell(new Phrase(dtp.getDoseTimePoints().getTimePoint(), regular));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//													cell.setPadding(7f);
								    cell.setNoWrap(false);
									cell.setColspan(2);
									hstable.addCell(cell);
									
									labelStr =  messageSource.getMessage("label.dosing.awc", null,currentLocale);
									cell = new PdfPCell(new Phrase(labelStr, heading));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
									cell.setPadding(7f);
								    cell.setNoWrap(false);
								    hstable.addCell(cell);
									
									cell = new PdfPCell(new Phrase(dtp.getDoseTimePoints().getTreatmentInfo().getMountOfWaterConsumedWithTheDose(), regular));
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//													cell.setPadding(7f);
								    cell.setNoWrap(false);
								    hstable.addCell(cell);
								    
								    Map<Long, Map<Long, Map<Long, List<SubjectDoseTimePointParametersData>>>> doseParamMap = dcdDto.getDoseParamMap();
								    if(doseParamMap != null && doseParamMap.size() > 0) {
								    	Map<Long, Map<Long, List<SubjectDoseTimePointParametersData>>> pwdoseParamMap = doseParamMap.get(svr.getId());
								        if(pwdoseParamMap != null && pwdoseParamMap.size() > 0) {
								        	Map<Long, List<SubjectDoseTimePointParametersData>> twdoseParamMap = pwdoseParamMap.get(spm.getId());
								        	if(twdoseParamMap != null && twdoseParamMap.size() > 0) {
								        		List<SubjectDoseTimePointParametersData> sdtparamList = twdoseParamMap.get(dtp.getDoseTimePoints().getTreatmentInfo().getId());
								        		if(sdtparamList != null && sdtparamList.size() > 0) {
								        			for(SubjectDoseTimePointParametersData param : sdtparamList) {
								        				LanguageSpecificGlobalParameterDetails lsgp = dataCrfDao.getLanguageSpecificParametersDetailsRecord(langId, param.getParameter().getId());
								        			    if(lsgp != null) {
//									        			    	labelStr =  messageSource.getMessage("label.dosing.parm", null,currentLocale);
										    				cell = new PdfPCell(new Phrase(lsgp.getName(), heading));
										    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
										    				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);;
										    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
										    				cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
										    				cell.setPaddingTop(3f);
										    			    cell.setPaddingBottom(7f);
										    			    cell.setPaddingLeft(7f);
										    			    cell.setPaddingRight(7f);
//											    				cell.setPadding(7f);
										    			    cell.setNoWrap(false);
										    				cell.setColspan(4);
										    				hstable.addCell(cell);
								        			    	String type = lsgp.getParameterId().getContentType().getCode();
								        			    	List<LanguageSpecificValueDetails> lspgvList = null;
										    				if(type.equals("CB") || type.equals("RB") || type.equals("SB")) {
										    					lspgvList = dataCrfDao.getLanguageSpecificGlobalValusesList(lsgp.getParameterId(), langId);
											    				if(lspgvList != null && lspgvList.size() > 0) {
											    					PdfPTable radTab = new PdfPTable(lspgvList.size());
												    				List<String> selectBoxList = new ArrayList<>();
												    				Chunk selectChunk = null;
											    					for(LanguageSpecificValueDetails lsgv : lspgvList) {
											    						Phrase pp = new Phrase(100);
													    				Paragraph p = new Paragraph();
													    				if(type.equals("RB")) {
													    					Chunk chunkCheckBoxYes = null;
													    					if(param.getGlobalValue().getId() == lsgv.getId()) {
													    						chunkCheckBoxYes = new Chunk(chkedradioimg, 0, 0, false);
													    	    				p.add(chunkCheckBoxYes);
													    	    				p.add(" ");
													    	    				Paragraph pg = new Paragraph(lsgv.getName(), regular);
													    	    				pg.setAlignment(Element.ALIGN_LEFT);
													    	    				pp.add(pg);
													    	    				p.add(pp);
													    	    				p.setAlignment(Element.ALIGN_LEFT);
														    				}else {
														    					chunkCheckBoxYes = new Chunk(unchkradioimg, 0, 0, false);
													    	    				p.add(chunkCheckBoxYes);
													    	    				p.add(" ");
													    	    				Paragraph pg = new Paragraph(lsgv.getName(), regular);
													    	    				pg.setAlignment(Element.ALIGN_LEFT);
													    	    				pp.add(pg);
													    	    				p.add(pp);
													    	    				p.setAlignment(Element.ALIGN_LEFT);
														    				}
												    	    				cell = new PdfPCell();
												    	    				cell.addElement(p);
												    	    				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
												    	    				cell.setBorder(Rectangle.NO_BORDER);
												    	    				cell.setPaddingTop(3f);
												    	    			    cell.setPaddingBottom(7f);
												    	    			    cell.setPaddingLeft(7f);
												    	    			    cell.setPaddingRight(7f);
//													    	    				cell.setPadding(7f);
												    	    			    cell.setNoWrap(false);
												    	    				radTab.addCell(cell);
											    						}else if(type.equals("CB")) {
											    							Chunk chunkCheckBoxYes = null;
											    							if(param.getGlobalValue().getId() == lsgv.getId()) {
													    						chunkCheckBoxYes = new Chunk(chkedCbImage, 0, 0, false);
													    	    				p.add(chunkCheckBoxYes);
													    	    				p.add(" ");
													    	    				Paragraph pg = new Paragraph(lsgv.getName(), regular);
													    	    				pg.setAlignment(Element.ALIGN_LEFT);
													    	    				pp.add(pg);
													    	    				p.add(pp);
													    	    				p.setAlignment(Element.ALIGN_LEFT);
														    				}else {
														    					chunkCheckBoxYes = new Chunk(unchkdCbImage, 0, 0, false);
													    	    				p.add(chunkCheckBoxYes);
													    	    				p.add(" ");
													    	    				Paragraph pg = new Paragraph(lsgv.getName(), regular);
													    	    				pg.setAlignment(Element.ALIGN_LEFT);
													    	    				pp.add(pg);
													    	    				p.add(pp);
													    	    				p.setAlignment(Element.ALIGN_LEFT);
														    				}
												    	    				
												    	    				cell = new PdfPCell();
												    	    				cell.addElement(p);
												    	    				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
												    	    				cell.setBorder(Rectangle.NO_BORDER);
												    	    				cell.setPaddingTop(3f);
												    	    			    cell.setPaddingBottom(7f);
												    	    			    cell.setPaddingLeft(7f);
												    	    			    cell.setPaddingRight(7f);
//													    	    				cell.setPadding(7f);
												    	    			    cell.setNoWrap(false);
												    	    				radTab.addCell(cell);
											    						}else
											    							selectBoxList.add(lsgv.getName());
											    					}
											    					if(type.equals("CB") || type.equals("RB")) {
											    						cell = new PdfPCell(radTab);
													    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
													    				cell.setVerticalAlignment(Element.ALIGN_CENTER);
													    				cell.setPaddingTop(3f);
													    			    cell.setPaddingBottom(7f);
													    			    cell.setPaddingLeft(7f);
													    			    cell.setPaddingRight(7f);
//														    				cell.setPadding(7f);
													    			    cell.setNoWrap(false);
													    			    hstable.addCell(cell);
											    					}else {
											    						selectChunk = new Chunk();
											    						for(String st : selectBoxList) {
											    							selectChunk.append(st);
											    							selectChunk.append("\n");
											    							selectChunk.setFont(regular);
											    						}
											    						cell = new PdfPCell();
											    						cell.addElement(selectChunk);
													    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
													    				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
													    				cell.setPaddingTop(3f);
													    			    cell.setPaddingBottom(7f);
													    			    cell.setPaddingLeft(7f);
													    			    cell.setPaddingRight(7f);
//														    				cell.setPadding(7f);
													    			    cell.setNoWrap(false);
													    			    hstable.addCell(cell);
											    							
											    					}
											    				}
										    				}else {
										    					cell = new PdfPCell((new Phrase("", regular)));
											    				cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
											    				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
											    				cell.setPaddingTop(3f);
											    			    cell.setPaddingBottom(7f);
											    			    cell.setPaddingLeft(7f);
											    			    cell.setPaddingRight(7f);
//												    				cell.setPadding(7f);
											    			    cell.setNoWrap(false);
											    			    hstable.addCell(cell);
										    				}
								        			    }
								        			}
								        		}
								        	}
								        }
								    }
								   
								    labelStr =  messageSource.getMessage("label.dosing.Describe", null,currentLocale);
									cell = new PdfPCell(new Phrase(labelStr, heading));
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setBackgroundColor(new BaseColor(162, 159, 159));//#B8CFF1
									cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//										cell.setPadding(7f);
								    cell.setNoWrap(false);
								    hstable.addCell(cell);
									
									cell = new PdfPCell(new Phrase("", regular));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//										cell.setPadding(7f);
								    cell.setNoWrap(false);
									cell.setColspan(5);
									hstable.addCell(cell);
									
									cell = new PdfPCell(new Phrase("", regular));
									cell.setBorder(Rectangle.NO_BORDER);
									cell.setFixedHeight(10f);
									cell.setColspan(5);
									hstable.addCell(cell);
									}
									hstable.setHeaderRows(1);
									document.add(hstable);
							}
						}
					}
				}
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

private CrfDataActivityDto getCrfDataActivityDtoDetails(List<StudyCheckInActivityDataDetails> schkInList, List<StudyCheckOutActivityDataDetails> schkOutList, 
		List<StudyExecutionActivityDataDetails> sexeList, Map<Long, LanguageSpecificGlobalParameterDetails> gpMap, long volunteerId) {
	CrfDataActivityDto cadDto = null;
	List<LanguageSpecificGlobalParameterDetails> lsgpTempList = new ArrayList<>();
	Map<Long, String> paramValsMap = new HashMap<>();
	try {
		if(schkInList != null && schkInList.size() > 0) {
			for(StudyCheckInActivityDataDetails schkin : schkInList) {
				if(schkin.getSaData().getVolunteerId().getId() == volunteerId) {
					lsgpTempList.add(gpMap.get(schkin.getSaParameter().getParameterId().getId()));
					if(schkin.getValue() != null && !schkin.getValue().equals(""))
						paramValsMap.put(schkin.getSaParameter().getParameterId().getId(), schkin.getValue());
					else {
						if(schkin.getGlobalValues() != null)
							paramValsMap.put(schkin.getSaParameter().getParameterId().getId(), schkin.getGlobalValues().getId()+"");
						else 
							paramValsMap.put(schkin.getSaParameter().getParameterId().getId(), "");
					}
				}
			}
		}else if(schkOutList != null && schkOutList.size() > 0) {
			for(StudyCheckOutActivityDataDetails schkOut : schkOutList) {
				if(schkOut.getSaData().getVolunteerId().getId() == volunteerId) {
					lsgpTempList.add(gpMap.get(schkOut.getSaParameter().getParameterId().getId()));
					if(schkOut.getValue() != null && !schkOut.getValue().equals(""))
						paramValsMap.put(schkOut.getSaParameter().getParameterId().getId(), schkOut.getValue());
					else {
						if(schkOut.getGlobalValues() != null)
							paramValsMap.put(schkOut.getSaParameter().getParameterId().getId(), schkOut.getGlobalValues().getId()+"");
						else 
							paramValsMap.put(schkOut.getSaParameter().getParameterId().getId(), "");
					}
						
				
				}
			}
		}else if(sexeList != null && sexeList.size() > 0) {
			for(StudyExecutionActivityDataDetails sexe : sexeList) {
				if(sexe.getSaData().getVolunteerId().getId() == volunteerId) {
					lsgpTempList.add(gpMap.get(sexe.getSaParameter().getParameterId().getId()));
					if(sexe.getValue() != null && !sexe.getValue().equals(""))
						paramValsMap.put(sexe.getSaParameter().getParameterId().getId(), sexe.getValue());
					else {
						if(sexe.getGlobalValues() != null)
							paramValsMap.put(sexe.getSaParameter().getParameterId().getId(), sexe.getGlobalValues().getId()+"");
						else paramValsMap.put(sexe.getSaParameter().getParameterId().getId(), "");
					}
				
				}
			}
		}
		
		cadDto = new CrfDataActivityDto();
		cadDto.setParmetersList(lsgpTempList);
		cadDto.setParamValsMap(paramValsMap);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return cadDto;
}

private void generateDataActivityPdf(LanguageSpecificGlobalActivityDetails lsga,
		CrfDataActivityDto cdaDto, DataCrfDtoDetails dcdDto, Long langId, Document document,
		Image unchkdCbImage, Image chkedCbImage, Image unchkradioimg, Image chkedradioimg, Font regular, Font heading,
		BaseColor bgColor, Font mainHeading, Font subHeading, Font actHeadFont, StudyPeriodMaster spm, Map<Long, Map<Long, Map<Long, List<Long>>>> sactParamMap, TreatmentInfo treatmentInfo, String dateStrWithTime, long volId) throws DocumentException {
	PdfPTable mainTab = new PdfPTable(2);
	mainTab.setWidthPercentage(100f);
	PdfPCell cell = null;
	ActivityEntryDetailsDto aedDto = null;
	SimpleDateFormat sdf = new SimpleDateFormat(dateStrWithTime);
	List<LanguageSpecificGlobalParameterDetails> lsgpList = new ArrayList<>();
	List<Long> gpList = sactParamMap.get(spm.getId()).get(treatmentInfo.getId()).get(lsga.getGlobalActivity().getId());
	if(gpList != null && gpList.size() > 0) {
		for(Long pno : gpList)
			lsgpList.add(dcdDto.getGpMap().get(pno));
	}
//	List<LanguageSpecificGlobalParameterDetails> lsgpList = cdaDto.getParmetersList();
	Map<Long, String> parmValsMap = null;
	List<Long> gropIds = new ArrayList<>();
	if(cdaDto != null)
		parmValsMap = cdaDto.getParamValsMap();
	Map<Integer, List<LanguageSpecificGlobalParameterDetails>> parametersMap = getGroupAndNonGroupParameters(lsgpList);
	if(parametersMap.size() > 0) {
		if(lsga.getGlobalActivity().isHeadding()) {
			String[] words = lsga.getName().split(" ");
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < words.length; i++) {
				String st = "";
				if(!words[i].trim().equals("")) {
					if(i==0)
					     st = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
						else st = " "+Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1, words[i].length());
					    builder.append(st);
				}
			}
			cell = new PdfPCell(new Phrase(lsga.getName()+"", actHeadFont));
//			cell = new PdfPCell(new Phrase(builder+"", actHeadFont));
			cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setBackgroundColor(bgColor);
		    cell.setPaddingTop(3f);
		    cell.setPaddingBottom(7f);
		    cell.setPaddingLeft(7f);
		    cell.setPaddingRight(7f);
		    
//		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setColspan(2);
		    mainTab.addCell(cell);
		    mainTab.getDefaultCell().setBackgroundColor(bgColor);
		}
		for(Map.Entry<Integer, List<LanguageSpecificGlobalParameterDetails>> parmMap : parametersMap.entrySet()) {
			Map<Long, List<LanguageSpecificGlobalParameterDetails>> groupParmMap = new HashMap<>();
			List<LanguageSpecificGlobalParameterDetails> paramList = parmMap.getValue();
			Collections.sort(paramList);
			List<LanguageSpecificGlobalParameterDetails> tempList = null;
			for(LanguageSpecificGlobalParameterDetails lsgp : paramList) {
				if(lsgp.getParameterId().getGroups() != null) {
					if(groupParmMap.containsKey(lsgp.getParameterId().getGroups().getId())) {
						tempList = groupParmMap.get(lsgp.getParameterId().getGroups().getId());
						tempList.add(lsgp);
						Collections.sort(tempList);
						groupParmMap.put(lsgp.getParameterId().getGroups().getId(), tempList);
					}else {
						tempList = new ArrayList<>();
						tempList.add(lsgp);
						Collections.sort(tempList);
						groupParmMap.put(lsgp.getParameterId().getGroups().getId(), tempList);
					}
				}else {
					if(groupParmMap.containsKey(0L)) {
						tempList = groupParmMap.get(0L);
						tempList.add(lsgp);
						Collections.sort(tempList);
						groupParmMap.put(0L, tempList);
					}else {
						tempList = new ArrayList<>();
						tempList.add(lsgp);
						Collections.sort(tempList);
						groupParmMap.put(0L, tempList);
					}
				}
			}
			String groupName ="";
			for(Map.Entry<Long, List<LanguageSpecificGlobalParameterDetails>> pmap : groupParmMap.entrySet()) {
				List<LanguageSpecificGlobalParameterDetails> lsgpdList = pmap.getValue();
				if(lsgpdList != null) {
					for(LanguageSpecificGlobalParameterDetails lsgpd : lsgpdList) {
						if(lsgpd.getParameterId().getGroups() != null) {
							boolean flag = false;
							if(groupName.equals("")) {
								groupName = dataCrfDao.getLanguageSpecificGroupName(lsgpd.getParameterId().getGroups(), langId);
								gropIds.add(lsgpd.getParameterId().getGroups().getId());
								flag = true;
							}else {
								if(!gropIds.contains(lsgpd.getParameterId().getGroups().getId())) {
									groupName = dataCrfDao.getLanguageSpecificGroupName(lsgpd.getParameterId().getGroups(), langId);
									gropIds.add(lsgpd.getParameterId().getGroups().getId());
									flag = true;
								}else flag = false;
							}
							if(flag) {
								cell = new PdfPCell(new Phrase(groupName, subHeading));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_CENTER);
							    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							    cell.setBackgroundColor(bgColor);//#B3E2E7
							    cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//							    cell.setPadding(7f);
							    cell.setNoWrap(false);
							    cell.setColspan(2);
							    mainTab.addCell(cell);
							}
						}
						String contrlType = lsgpd.getParameterId().getContentType().getCode();
						 String paramName = lsgpd.getName();
						 if(paramName.contains("&#8805;")) {
							 paramName =  paramName.replaceAll("&#8805;", ">=");
						 }
						 if(paramName.contains("&#8804; "))
							 paramName = paramName.replaceAll("&#8804;", "<=");
						cell = new PdfPCell(new Phrase(paramName, regular));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPaddingTop(3f);
					    cell.setPaddingBottom(7f);
					    cell.setPaddingLeft(7f);
					    cell.setPaddingRight(7f);
//						cell.setPadding(7f);
						cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
						cell.setNoWrap(false);
						if(lsgpd.getParameterId().getContentType().getCode().equals("LABEL"))
							cell.setColspan(2);
						mainTab.addCell(cell);
						
						if(contrlType.equals("RB") || contrlType.equals("CB") || contrlType.equals("SB")) {
							List<LanguageSpecificValueDetails> lsvdList = dataCrfDao.getLanguageSpecificGlobalValusesList(lsgpd.getParameterId(), langId);
							TreeMap<Integer, LanguageSpecificValueDetails> valuesMap = new TreeMap<>();
							if(lsvdList != null && lsvdList.size() > 0) {
								for(LanguageSpecificValueDetails lsvd : lsvdList) {
									valuesMap.put(lsvd.getGlobalValId().getOrderNo(), lsvd);
								}
							}
							PdfPTable conTab = new PdfPTable(1);
							conTab.setWidthPercentage(100f);
							Paragraph p = new Paragraph();
							boolean noDataFlag = false;
							if(contrlType.equals("RB")) {
								for(Map.Entry<Integer, LanguageSpecificValueDetails> lsvdMap : valuesMap.entrySet()) {
									LanguageSpecificValueDetails lsvd = lsvdMap.getValue();
									Phrase pp = new Phrase(100);
									if(parmValsMap != null && parmValsMap.size() > 0) {
										if(parmValsMap.get(lsgpd.getParameterId().getId()) != null) {
											if(lsvd.getGlobalValId().getId() == Long.parseLong(parmValsMap.get(lsgpd.getParameterId().getId()))) {
//												p.add(new Chunk(unchkradioimg, 0, -2));
//												p.setAlignment(Element.ALIGN_TOP);
												Chunk radoButton = new Chunk(chkedradioimg, 0, 0, true);
												p.add(radoButton);
												p.add(" ");
												p.setAlignment(Element.WRITABLE_DIRECT);
												Paragraph pg = new Paragraph(lsvd.getName()+" ", regular);
//												pg.setAlignment(Element.ALIGN_CENTER);
												pg.setAlignment(Element.WRITABLE_DIRECT);
												pp.add(pg);
												p.add(pp);
											}else {
												Chunk radoButton = new Chunk(unchkradioimg, 0, 0, true);
												p.add(radoButton);
												p.add(" ");
												p.setAlignment(Element.WRITABLE_DIRECT);
												Paragraph pg = new Paragraph(lsvd.getName()+" ", regular);
//												pg.setAlignment(Element.ALIGN_CENTER);
												pg.setAlignment(Element.WRITABLE_DIRECT);
												pp.add(pg);
												p.add(pp);
											}
										}else {
											Chunk radoButton = new Chunk(unchkradioimg, 0, 0, true);
											p.add(radoButton);
											p.add(" ");
											p.setAlignment(Element.WRITABLE_DIRECT);
											Paragraph pg = new Paragraph(lsvd.getName()+" ", regular);
//											pg.setAlignment(Element.ALIGN_CENTER);
											pg.setAlignment(Element.WRITABLE_DIRECT);
											pp.add(pg);
											p.add(pp);
										}
										
									}else {
										Chunk radoButton = new Chunk(unchkradioimg, 0, 0, true);
										p.add(radoButton);
										p.add(" ");
										p.setAlignment(Element.WRITABLE_DIRECT);
										Paragraph pg = new Paragraph(lsvd.getName()+" ", regular);
//										pg.setAlignment(Element.ALIGN_CENTER);
										pg.setAlignment(Element.WRITABLE_DIRECT);
										pp.add(pg);
										p.add(pp);
										noDataFlag = true;
									}
								}
							}else if(contrlType.equals("CB")) {
								for(Map.Entry<Integer, LanguageSpecificValueDetails> lsvdMap : valuesMap.entrySet()) {
									LanguageSpecificValueDetails lsvd = lsvdMap.getValue();
									Phrase pp = new Phrase(100);
									if(parmValsMap != null && parmValsMap.size() > 0) {
										if(parmValsMap.get(lsgpd.getParameterId().getId()) != null) {
											if(lsvd.getGlobalValId().getId() == Long.parseLong(parmValsMap.get(lsgpd.getParameterId().getId()))) {
//												p.add(new Chunk(unchkImage, 0, -2));
//												p.setAlignment(Element.ALIGN_TOP);
												Chunk chunkCheckBoxNo = new Chunk(chkedCbImage, 0, 0, false);
												p.add(chunkCheckBoxNo);
												p.add(" ");
												p.setAlignment(Element.WRITABLE_DIRECT);
												Paragraph pg = new Paragraph(lsvd.getName()+" ", regular);
												pg.setAlignment(Element.WRITABLE_DIRECT);
												pp.add(pg);
												p.add(pp);
											}else {
//												p.add(new Chunk(unchkImage, 0, -2));
//												p.setAlignment(Element.ALIGN_TOP);
												Chunk chunkCheckBoxNo = new Chunk(unchkdCbImage, 0, 0, false);
												p.add(chunkCheckBoxNo);
												p.add(" ");
												p.setAlignment(Element.WRITABLE_DIRECT);
												Paragraph pg = new Paragraph(lsvd.getName()+" ", regular);
												pg.setAlignment(Element.WRITABLE_DIRECT);
												pp.add(pg);
												p.add(pp);
											}
										}else {
											Chunk chunkCheckBoxNo = new Chunk(unchkdCbImage, 0, 0, false);
											p.add(chunkCheckBoxNo);
											p.add(" ");
											p.setAlignment(Element.WRITABLE_DIRECT);
											Paragraph pg = new Paragraph(lsvd.getName()+" ", regular);
											pg.setAlignment(Element.WRITABLE_DIRECT);
											pp.add(pg);
											p.add(pp);
										}
										
									}else {
										Chunk chunkCheckBoxNo = new Chunk(unchkdCbImage, 0, 0, false);
										p.add(chunkCheckBoxNo);
										p.add(" ");
										p.setAlignment(Element.WRITABLE_DIRECT);
										Paragraph pg = new Paragraph(lsvd.getName()+" ", regular);
										pg.setAlignment(Element.WRITABLE_DIRECT);
										pp.add(pg);
										p.add(pp);
									}
								}
							}else if(contrlType.equals("SB")) {
								PdfPTable gvTab = new PdfPTable(1);
								for(Map.Entry<Integer, LanguageSpecificValueDetails> lsvdMap : valuesMap.entrySet()) {
									LanguageSpecificValueDetails lsvd = lsvdMap.getValue();
									if(parmValsMap != null && parmValsMap.size() > 0) {
										if(parmValsMap.get(lsgpd.getParameterId().getId()) != null) {
											if(lsvd.getGlobalValId().getId() == Long.parseLong(parmValsMap.get(lsgpd.getParameterId().getId()))) {
												cell = new PdfPCell(new Phrase(lsvd.getName(), regular));
												cell.setBorder(Rectangle.NO_BORDER);
												cell.setNoWrap(false);
												cell.setPaddingTop(3f);
											    cell.setPaddingBottom(7f);
											    cell.setPaddingLeft(7f);
											    cell.setPaddingRight(7f);
//												cell.setPadding(7f);
											    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
												cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
												gvTab.addCell(cell);
											}else {
												cell = new PdfPCell(new Phrase("", regular));
												cell.setBorder(Rectangle.NO_BORDER);
												cell.setNoWrap(false);
												cell.setPaddingTop(3f);
											    cell.setPaddingBottom(7f);
											    cell.setPaddingLeft(7f);
											    cell.setPaddingRight(7f);
//												cell.setPadding(7f);
											    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
												cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
												gvTab.addCell(cell);
											}
										}else {
											cell = new PdfPCell(new Phrase("N/A", regular));
											cell.setBorder(Rectangle.NO_BORDER);
											cell.setNoWrap(false);
											cell.setPaddingTop(3f);
										    cell.setPaddingBottom(7f);
										    cell.setPaddingLeft(7f);
										    cell.setPaddingRight(7f);
//											cell.setPadding(7f);
										    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
											cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
											gvTab.addCell(cell);
										}
										
									}else {
										cell = new PdfPCell(new Phrase("N/A", regular));
										cell.setBorder(Rectangle.NO_BORDER);
										cell.setNoWrap(false);
										cell.setPaddingTop(3f);
									    cell.setPaddingBottom(7f);
									    cell.setPaddingLeft(7f);
									    cell.setPaddingRight(7f);
//										cell.setPadding(7f);
									    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
										cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
										gvTab.addCell(cell);
									}
								}
								cell = new PdfPCell(gvTab);
								cell.setBorder(Rectangle.NO_BORDER);
								cell.setNoWrap(false);
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
							    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								conTab.addCell(cell);
								
								cell = new PdfPCell(conTab);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setPaddingTop(3f);
							    cell.setPaddingBottom(7f);
							    cell.setPaddingLeft(7f);
							    cell.setPaddingRight(7f);
//								cell.setPadding(7f);
							    cell.setNoWrap(false);
								cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
								mainTab.addCell(cell);
							}
							if(noDataFlag) {
								Phrase pp = new Phrase(100);
								Chunk radoButton = new Chunk(chkedradioimg, 0, 0, true);
								p.add(radoButton);
								p.add(" ");
								p.setAlignment(Element.WRITABLE_DIRECT);
								Paragraph pg = new Paragraph("N/A", regular);
//								pg.setAlignment(Element.ALIGN_CENTER);
								pg.setAlignment(Element.WRITABLE_DIRECT);
								pp.add(pg);
								p.add(pp);
							}
								
							cell = new PdfPCell();
							cell.addElement(p);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
							cell.setNoWrap(false);
							cell.setPaddingTop(3f);
						    cell.setPaddingBottom(7f);
						    cell.setPaddingLeft(7f);
						    cell.setPaddingRight(7f);
//							cell.setPadding(7f);
							if(contrlType.equals("CB"))
								cell.setColspan(2);
							mainTab.addCell(cell);
						}else {
							if(!contrlType.equals("LABEL")) {
								if(parmValsMap != null && parmValsMap.size() > 0) {
									if(parmValsMap.get(lsgpd.getParameterId().getId()) != null) {
										if(contrlType.equals("ST") || contrlType.equals("ET")) {
											String time = parmValsMap.get(lsgpd.getParameterId().getId());
											cell = new PdfPCell(new Phrase(time, regular));
											cell.setHorizontalAlignment(Element.ALIGN_LEFT);
											cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
											cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
											cell.setNoWrap(false);
											cell.setPaddingTop(3f);
										    cell.setPaddingBottom(7f);
										    cell.setPaddingLeft(7f);
										    cell.setPaddingRight(7f);
//											cell.setPadding(7f);
											mainTab.addCell(cell);
											
										}else {
											String pval = "N/A";
											if(parmValsMap.get(lsgpd.getParameterId().getId()) != null && !parmValsMap.get(lsgpd.getParameterId().getId()).equals(""))
												pval = parmValsMap.get(lsgpd.getParameterId().getId());
											cell = new PdfPCell(new Phrase(pval, regular));
											cell.setHorizontalAlignment(Element.ALIGN_LEFT);
											cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
											cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
											cell.setNoWrap(false);
											cell.setPaddingTop(3f);
										    cell.setPaddingBottom(7f);
										    cell.setPaddingLeft(7f);
										    cell.setPaddingRight(7f);
//											cell.setPadding(7f);
											mainTab.addCell(cell);
										}
									}else {
										cell = new PdfPCell(new Phrase("N/A", regular));
										cell.setHorizontalAlignment(Element.ALIGN_LEFT);
										cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
										cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
										cell.setNoWrap(false);
										cell.setPaddingTop(3f);
									    cell.setPaddingBottom(7f);
									    cell.setPaddingLeft(7f);
									    cell.setPaddingRight(7f);
//										cell.setPadding(7f);
										mainTab.addCell(cell);
									}
									
								}else {
									cell = new PdfPCell(new Phrase("N/A", regular));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
									cell.setNoWrap(false);
									cell.setPaddingTop(3f);
								    cell.setPaddingBottom(7f);
								    cell.setPaddingLeft(7f);
								    cell.setPaddingRight(7f);
//									cell.setPadding(7f);
									mainTab.addCell(cell);
								}
							}
						}
					}
					if(dcdDto != null && dcdDto.getActPerformedMap().size() > 0) {
						if(dcdDto.getActPerformedMap().get(volId) != null) {
							if(dcdDto.getActPerformedMap().get(volId).get(spm.getId()) != null) {
								if(dcdDto.getActPerformedMap().get(volId).get(spm.getId()).get(lsga.getGlobalActivity().getId()) != null)
									aedDto = dcdDto.getActPerformedMap().get(volId).get(spm.getId()).get(lsga.getGlobalActivity().getId());
							}
						}
					}
				}
			}
		}
		if(aedDto != null && aedDto.getPerformedBy() != null) {
			if(dcdDto.getUserNamesMap() != null && dcdDto.getUserNamesMap().size() > 0) {
				cell = new PdfPCell(new Phrase("Performed By : "+dcdDto.getUserNamesMap().get(aedDto.getPerformedBy()), regular));
			}else cell = new PdfPCell(new Phrase("Performed By : "+aedDto.getPerformedBy(), regular));
		}else cell = new PdfPCell(new Phrase("Performed By : "+"", regular));
//		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	    cell.setBackgroundColor(bgColor);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    cell.setNoWrap(false);
	    mainTab.addCell(cell);
	    
	    String date = "";
	    if(aedDto != null && aedDto.getPerformedOn() != null) {
	    	date = sdf.format(aedDto.getPerformedOn());
	    }
	    cell = new PdfPCell(new Phrase("Performed Date : "+date, regular));
//	    cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	    cell.setBackgroundColor(bgColor);
	    cell.setPaddingTop(3f);
	    cell.setPaddingBottom(7f);
	    cell.setPaddingLeft(7f);
	    cell.setPaddingRight(7f);
	    cell.setNoWrap(false);
	    mainTab.addCell(cell);
	}
	mainTab.setHeaderRows(1);
	document.add(mainTab);

	

	
}

private Map<Integer, List<LanguageSpecificGlobalParameterDetails>> getGroupAndNonGroupParameters(
		List<LanguageSpecificGlobalParameterDetails> lsgpList) {
	Map<Integer, List<LanguageSpecificGlobalParameterDetails>> map = new HashMap<>();
	List<LanguageSpecificGlobalParameterDetails> tempList = null;
	try {
		if(lsgpList != null && lsgpList.size() > 0) {
			for(LanguageSpecificGlobalParameterDetails lsgpd : lsgpList) {
				if(lsgpd.getParameterId().getGroups() != null) {
					if(map.containsKey(1)) {
						tempList = map.get(1);
						tempList.add(lsgpd);
						Collections.sort(tempList);
						map.put(1, tempList);
					}else {
						tempList = new ArrayList<>();
						tempList.add(lsgpd);
						Collections.sort(tempList);
						map.put(1, tempList);
					}
				}else {
					if(map.containsKey(2)) {
						tempList = map.get(2);
						tempList.add(lsgpd);
						Collections.sort(tempList);
						map.put(2, tempList);
					}else {
						tempList = new ArrayList<>();
						tempList.add(lsgpd);
						Collections.sort(tempList);
						map.put(2, tempList);
					}
				}
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return map;
}
}
