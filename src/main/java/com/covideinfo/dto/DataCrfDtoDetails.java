package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StaticActivityDataDetails;
import com.covideinfo.model.StaticActivityDetails;
import com.covideinfo.model.StaticActivityValueDetails;
import com.covideinfo.model.StudyCheckInActivityDataDetails;
import com.covideinfo.model.StudyCheckOutActivityDataDetails;
import com.covideinfo.model.StudyExecutionActivityDataDetails;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
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

public class DataCrfDtoDetails implements Serializable {
	
	
	private static final long serialVersionUID = 5879802260714362626L;
	private StudyMaster study;
	private  Projects project;
	private ApplicationConfiguration appConfig; 
	private UserMaster user;
	private Map<Long, LanguageSpecificGlobalActivityDetails> gaMap; //actid, lspactivity
	private Map<Long, LanguageSpecificGlobalParameterDetails> gpMap;// gpid, lsgparamter
	private Map<Long, LanguageSpecificValueDetails> gvMap;//gloablValueId, languageSpecificvalues
	private Map<Long, StudySubjects> subMap;//volunteerid, SubjectPojo
	private Map<Integer, StudyPeriodMaster> spmMap;// periodId, periodPojo
	/*private Map<Long, Map<Long, Map<Long, Map<Long, List<StudyCheckInActivityDataDetails>>>>> chickInMap;//subjectId, periodId, treatment, activityId, List of StudyCheckInActivityDataDetails
	private Map<Long, Map<Long, Map<Long, Map<Long, List<StudyCheckOutActivityDataDetails>>>>> checkOutMap;//subjectId, periodId, treatment, activityId, List of StudyCheckInActivityDataDetails
	private Map<Long, Map<Long, Map<Long, Map<Long, List<StudyExecutionActivityDataDetails>>>>> stdexeMap;//subjectId, periodId, treatment, activityId, List of StudyCheckInActivityDataDetails
	*/
	private Map<Long, Map<Long, Map<Long, List<StudyCheckInActivityDataDetails>>>> chickInMap;//subjectId, periodId,  activityId, List of StudyCheckInActivityDataDetails
	private Map<Long, Map<Long, Map<Long, List<StudyCheckOutActivityDataDetails>>>> checkOutMap;//subjectId, periodId,  activityId, List of StudyCheckInActivityDataDetails
	private Map<Long, Map<Long, Map<Long, List<StudyExecutionActivityDataDetails>>>> stdexeMap;//subjectId, periodId,  activityId, List of StudyCheckInActivityDataDetails
	
	private Map<Long, Map<Long, Map<Long, List<SubjectDoseTimePoints>>>> sdtpMap; //SubjectId, Period, treatment, dosetimepoint
	private Map<Long, Map<Long, Map<Long, List<SubjectDoseTimePointParametersData>>>> doseParamMap;//SubjectId , Period, treatment, doseparameters
//	private Map<Long, Map<Long, Map<Long, List<SubjectVitalTimePointsData>>>> vitalMap; //SubjectId, Period, treatment, Listst of vitals
//	private Map<Long, Map<Long, List<SubjectVitalTimePointsData>>> vitalMap; //SubjectId, Period, treatment, Listst of vitals
	private Map<Long, Map<Long, Map<Long, Map<String, SubjectVitalTimePointsData>>>> vitalMap; //SubjectId, Period, timePointId, positionsMap
//	private Map<Long, Map<Long, List<SubjectVitalParametersData>>> vitalParamMap; //SubjectId, Period, List of Vitalparameters
	private Map<Long, Map<Long, List<SubjectVitalParametersData>>> vitalParamMap; //vitalDataId, vitalTpId,  VitalparametersPojo
	//	private Map<Long, Map<Long, Map<Long, List<SubjectSampleCollectionTimePointsData>>>> sampCollMap;//SubjectId, Period, treatment, List of samplecollections
	private Map<Long, Map<Long, List<SubjectSampleCollectionTimePointsData>>> sampCollMap;//SubjectId, Period, treatment, List of samplecollections
	private Map<Long, Map<Long, Map<Long, List<SubjectMealsTimePointsData>>>> mealsCollMap;
	private Map<Long, DefaultActivitys> defalutActMap;
	private Map<Long, Map<Long, TreatmentInfo>> subwtrMap;
	private Map<Long, StudyVolunteerReporting> stdVolMap;
	private Map<Long, List<MealsTimePoints>> mealsMap;//treatmentId, mealsList
	private Map<Long, List<DoseTimePoints>> doseMap; // treatmentId, doseList
	private Map<Long, List<SampleTimePoints>> samplesMap; //treatmentId, samplesList
	private Map<Long, List<VitalTimePoints>> vtpMap; //treatmentId, vitalsList
	private Map<Long, Map<Long, Set<Long>>> sactMap; //periodId, treatementId, List of activityIds
	private Map<Long, Map<Long, Map<Long, List<Long>>>> sactParamMap; // period, treatmentId, actId,  List of parameterids
	private Map<String, StudySubjects> stdSubjetsMap;// subjectNo, SubjectPojo
	private Map<Long, List<StaticActivityDataDetails>> staticDataActMap;
	private Map<Long, List<StaticActivityValueDetails>> staticActValsMap;
	private Map<String, List<StaticActivityDetails>> saticActMap;
	private Map<Long, Map<Long, SubjectWithdrawDetails>> subjectWithDrawMap;
	private Map<Long, TreatmentInfo> treatmentsMap;
	private Long treatmentMinId;
	private ActivityDraftReviewAudit adrPojo;
	private Map<Long, Map<Long, Map<Long, ActivityEntryDetailsDto>>> actPerformedMap; // volId, periodId, activityId, entryDetails 
	private Map<String, DosingInfoDetails> drugInfMap; //RandomizationCode, DosingInfoDetails Pojo
	private Map<Long, SampleInfoDto> sampleInfoMap;
	private  Map<Long, Map<Long, Map<Long, List<RecannulationDataDto>>>> rcDataWithTpMap;//volunteerId, PeriodId, sampleId, RecannulationDataDetailsDto
    private Map<Long, Map<Long, List<RecannulationDataDto>>> rcDataWithoutTpMap; //VolunteerId, PeriodId, RecannulationDataDetailsDto
    private Map<String, String> userNamesMap;// username, userFullName
	
	public Map<String, StudySubjects> getStdSubjetsMap() {
		return stdSubjetsMap;
	}
	public void setStdSubjetsMap(Map<String, StudySubjects> stdSubjetsMap) {
		this.stdSubjetsMap = stdSubjetsMap;
	}
	public Map<Long, LanguageSpecificGlobalActivityDetails> getGaMap() {
		return gaMap;
	}
	public void setGaMap(Map<Long, LanguageSpecificGlobalActivityDetails> gaMap) {
		this.gaMap = gaMap;
	}
	public Map<Long, LanguageSpecificGlobalParameterDetails> getGpMap() {
		return gpMap;
	}
	public void setGpMap(Map<Long, LanguageSpecificGlobalParameterDetails> gpMap) {
		this.gpMap = gpMap;
	}
	public Map<Long, LanguageSpecificValueDetails> getGvMap() {
		return gvMap;
	}
	public void setGvMap(Map<Long, LanguageSpecificValueDetails> gvMap) {
		this.gvMap = gvMap;
	}
	public Map<Long, StudySubjects> getSubMap() {
		return subMap;
	}
	public void setSubMap(Map<Long, StudySubjects> subMap) {
		this.subMap = subMap;
	}
	public Map<Integer, StudyPeriodMaster> getSpmMap() {
		return spmMap;
	}
	public void setSpmMap(Map<Integer, StudyPeriodMaster> spmMap) {
		this.spmMap = spmMap;
	}
	public Map<Long, Map<Long, Map<Long, List<SubjectDoseTimePoints>>>> getSdtpMap() {
		return sdtpMap;
	}
	public void setSdtpMap(Map<Long, Map<Long, Map<Long, List<SubjectDoseTimePoints>>>> sdtpMap) {
		this.sdtpMap = sdtpMap;
	}
	public Map<Long, Map<Long, Map<Long, List<SubjectDoseTimePointParametersData>>>> getDoseParamMap() {
		return doseParamMap;
	}
	public void setDoseParamMap(Map<Long, Map<Long, Map<Long, List<SubjectDoseTimePointParametersData>>>> doseParamMap) {
		this.doseParamMap = doseParamMap;
	}
	
	
	public Map<Long, Map<Long, List<SubjectVitalParametersData>>> getVitalParamMap() {
		return vitalParamMap;
	}
	public void setVitalParamMap(Map<Long, Map<Long, List<SubjectVitalParametersData>>> vitalParamMap) {
		this.vitalParamMap = vitalParamMap;
	}
	public Map<Long, Map<Long, Map<Long, Map<String, SubjectVitalTimePointsData>>>> getVitalMap() {
		return vitalMap;
	}
	public void setVitalMap(Map<Long, Map<Long, Map<Long, Map<String, SubjectVitalTimePointsData>>>> vitalMap) {
		this.vitalMap = vitalMap;
	}
	public Map<Long, Map<Long, List<SubjectSampleCollectionTimePointsData>>> getSampCollMap() {
		return sampCollMap;
	}
	public void setSampCollMap(Map<Long, Map<Long, List<SubjectSampleCollectionTimePointsData>>> sampCollMap) {
		this.sampCollMap = sampCollMap;
	}
	public Map<Long, Map<Long, Map<Long, List<SubjectMealsTimePointsData>>>> getMealsCollMap() {
		return mealsCollMap;
	}
	public void setMealsCollMap(Map<Long, Map<Long, Map<Long, List<SubjectMealsTimePointsData>>>> mealsCollMap) {
		this.mealsCollMap = mealsCollMap;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public ApplicationConfiguration getAppConfig() {
		return appConfig;
	}
	public void setAppConfig(ApplicationConfiguration appConfig) {
		this.appConfig = appConfig;
	}
	public UserMaster getUser() {
		return user;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}
	public Map<Long, DefaultActivitys> getDefalutActMap() {
		return defalutActMap;
	}
	public void setDefalutActMap(Map<Long, DefaultActivitys> defalutActMap) {
		this.defalutActMap = defalutActMap;
	}
	public Map<Long, Map<Long, TreatmentInfo>> getSubwtrMap() {
		return subwtrMap;
	}
	public void setSubwtrMap(Map<Long, Map<Long, TreatmentInfo>> subwtrMap) {
		this.subwtrMap = subwtrMap;
	}
	public Map<Long, Map<Long, Map<Long, List<StudyCheckInActivityDataDetails>>>> getChickInMap() {
		return chickInMap;
	}
	public Map<Long, Map<Long, Map<Long, List<StudyCheckOutActivityDataDetails>>>> getCheckOutMap() {
		return checkOutMap;
	}
	public Map<Long, Map<Long, Map<Long, List<StudyExecutionActivityDataDetails>>>> getStdexeMap() {
		return stdexeMap;
	}
	public void setChickInMap(Map<Long, Map<Long, Map<Long, List<StudyCheckInActivityDataDetails>>>> chickInMap) {
		this.chickInMap = chickInMap;
	}
	public void setCheckOutMap(Map<Long, Map<Long, Map<Long, List<StudyCheckOutActivityDataDetails>>>> checkOutMap) {
		this.checkOutMap = checkOutMap;
	}
	public void setStdexeMap(Map<Long, Map<Long, Map<Long, List<StudyExecutionActivityDataDetails>>>> stdexeMap) {
		this.stdexeMap = stdexeMap;
	}
	public Map<Long, StudyVolunteerReporting> getStdVolMap() {
		return stdVolMap;
	}
	public void setStdVolMap(Map<Long, StudyVolunteerReporting> stdVolMap) {
		this.stdVolMap = stdVolMap;
	}
	public Map<Long, List<MealsTimePoints>> getMealsMap() {
		return mealsMap;
	}
	public Map<Long, List<DoseTimePoints>> getDoseMap() {
		return doseMap;
	}
	public Map<Long, List<SampleTimePoints>> getSamplesMap() {
		return samplesMap;
	}
	public Map<Long, List<VitalTimePoints>> getVtpMap() {
		return vtpMap;
	}
	public void setMealsMap(Map<Long, List<MealsTimePoints>> mealsMap) {
		this.mealsMap = mealsMap;
	}
	public void setDoseMap(Map<Long, List<DoseTimePoints>> doseMap) {
		this.doseMap = doseMap;
	}
	public void setSamplesMap(Map<Long, List<SampleTimePoints>> samplesMap) {
		this.samplesMap = samplesMap;
	}
	public void setVtpMap(Map<Long, List<VitalTimePoints>> vtpMap) {
		this.vtpMap = vtpMap;
	}
	public Projects getProject() {
		return project;
	}
	public void setProject(Projects project) {
		this.project = project;
	}
	public Map<Long, Map<Long, Set<Long>>> getSactMap() {
		return sactMap;
	}
	public Map<Long, Map<Long, Map<Long, List<Long>>>> getSactParamMap() {
		return sactParamMap;
	}
	public void setSactMap(Map<Long, Map<Long, Set<Long>>> sactMap) {
		this.sactMap = sactMap;
	}
	public void setSactParamMap(Map<Long, Map<Long, Map<Long, List<Long>>>> sactParamMap) {
		this.sactParamMap = sactParamMap;
	}
	public Map<Long, List<StaticActivityDataDetails>> getStaticDataActMap() {
		return staticDataActMap;
	}
	public void setStaticDataActMap(Map<Long, List<StaticActivityDataDetails>> staticDataActMap) {
		this.staticDataActMap = staticDataActMap;
	}
	public Map<Long, List<StaticActivityValueDetails>> getStaticActValsMap() {
		return staticActValsMap;
	}
	public void setStaticActValsMap(Map<Long, List<StaticActivityValueDetails>> staticActValsMap) {
		this.staticActValsMap = staticActValsMap;
	}
	public Map<String, List<StaticActivityDetails>> getSaticActMap() {
		return saticActMap;
	}
	public void setSaticActMap(Map<String, List<StaticActivityDetails>> saticActMap) {
		this.saticActMap = saticActMap;
	}
	public Map<Long, Map<Long, SubjectWithdrawDetails>> getSubjectWithDrawMap() {
		return subjectWithDrawMap;
	}
	public void setSubjectWithDrawMap(Map<Long, Map<Long, SubjectWithdrawDetails>> subjectWithDrawMap) {
		this.subjectWithDrawMap = subjectWithDrawMap;
	}
	public Map<Long, TreatmentInfo> getTreatmentsMap() {
		return treatmentsMap;
	}
	public void setTreatmentsMap(Map<Long, TreatmentInfo> treatmentsMap) {
		this.treatmentsMap = treatmentsMap;
	}
	public Long getTreatmentMinId() {
		return treatmentMinId;
	}
	public void setTreatmentMinId(Long treatmentMinId) {
		this.treatmentMinId = treatmentMinId;
	}
	public ActivityDraftReviewAudit getAdrPojo() {
		return adrPojo;
	}
	public void setAdrPojo(ActivityDraftReviewAudit adrPojo) {
		this.adrPojo = adrPojo;
	}
	public Map<Long, Map<Long, Map<Long, ActivityEntryDetailsDto>>> getActPerformedMap() {
		return actPerformedMap;
	}
	public void setActPerformedMap(Map<Long, Map<Long, Map<Long, ActivityEntryDetailsDto>>> actPerformedMap) {
		this.actPerformedMap = actPerformedMap;
	}
	public Map<String, DosingInfoDetails> getDrugInfMap() {
		return drugInfMap;
	}
	public void setDrugInfMap(Map<String, DosingInfoDetails> drugInfMap) {
		this.drugInfMap = drugInfMap;
	}
	public Map<Long, SampleInfoDto> getSampleInfoMap() {
		return sampleInfoMap;
	}
	public void setSampleInfoMap(Map<Long, SampleInfoDto> sampleInfoMap) {
		this.sampleInfoMap = sampleInfoMap;
	}
	public Map<Long, Map<Long, Map<Long, List<RecannulationDataDto>>>> getRcDataWithTpMap() {
		return rcDataWithTpMap;
	}
	public void setRcDataWithTpMap(Map<Long, Map<Long, Map<Long, List<RecannulationDataDto>>>> rcDataWithTpMap) {
		this.rcDataWithTpMap = rcDataWithTpMap;
	}
	public Map<Long, Map<Long, List<RecannulationDataDto>>> getRcDataWithoutTpMap() {
		return rcDataWithoutTpMap;
	}
	public void setRcDataWithoutTpMap(Map<Long, Map<Long, List<RecannulationDataDto>>> rcDataWithoutTpMap) {
		this.rcDataWithoutTpMap = rcDataWithoutTpMap;
	}
	public Map<String, String> getUserNamesMap() {
		return userNamesMap;
	}
	public void setUserNamesMap(Map<String, String> userNamesMap) {
		this.userNamesMap = userNamesMap;
	}
	
	
	
	
	
	
	
	

}
