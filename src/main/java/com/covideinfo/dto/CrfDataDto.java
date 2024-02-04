package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

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

public class CrfDataDto implements Serializable {
	
	private static final long serialVersionUID = 4050233423513593468L;
	private StudyMaster study;
	private UserMaster user;
	private List<LanguageSpecificGlobalActivityDetails> lsgadList;
	private List<LanguageSpecificGlobalParameterDetails> lsgpdList; 
	private List<LanguageSpecificValueDetails> lsgvList;
	private List<StudySubjectPeriods> subList;
	private List<StudyPeriodMaster> spmList;
	private List<StudyActivityData> saDataList;
	private List<StudyCheckInActivityDataDetails> stdChkinList;
	private List<StudyCheckOutActivityDataDetails> stdChkoutList;
	private List<StudyExecutionActivityDataDetails> stdExList;
	private List<SubjectDoseTimePoints> sdtpList;
	private List<SubjectDoseTimePointParametersData> sdtpparamdList;
	private List<SubjectVitalTimePointsData> stdVitalList;
	private List<SubjectVitalParametersData> stdVitalParamList;
	private List<SubjectSampleCollectionTimePointsData> subSapColTpDataList;
	private List<SubjectMealsTimePointsData> subMealTpDataList;
	private ApplicationConfiguration appConfig;
	private List<DefaultActivitys> defalutActList;
	private List<StudyTreatmentWiseSubjects> stwSubjects;
	private List<StudyVolunteerReporting> stdVolList;
	private List<MealsTimePoints> mealsList;
	private List<DoseTimePoints> doseList;
	private List<SampleTimePoints> samplesList;
    private List<VitalTimePoints> vitalList;
    private  Projects project;
    private List<StudyActivities> studyActList;
    private List<StudyActivityParameters> studyActParamList;
    private List<StaticActivityDataDetails> saddList;
	private List<StaticActivityValueDetails> savdList;
	private List<StaticActivityDetails> staticActList;
	private List<SubjectWithdrawDetails> subjWithDarawList;
	private List<TreatmentInfo> treatmentsList;
	private ActivityDraftReviewAudit adrPojo;
	private List<DosingInfoDetails> druginfList;
	private List<ProjectsDetails> sampleProList;
	private List<RecannulationDataDto> recdDtoList;
	private List<UserMasterDto> usersList;
   
	
   
	
	public List<LanguageSpecificGlobalActivityDetails> getLsgadList() {
		return lsgadList;
	}
	public void setLsgadList(List<LanguageSpecificGlobalActivityDetails> lsgadList) {
		this.lsgadList = lsgadList;
	}
	public List<LanguageSpecificGlobalParameterDetails> getLsgpdList() {
		return lsgpdList;
	}
	public void setLsgpdList(List<LanguageSpecificGlobalParameterDetails> lsgpdList) {
		this.lsgpdList = lsgpdList;
	}
	public List<LanguageSpecificValueDetails> getLsgvList() {
		return lsgvList;
	}
	public void setLsgvList(List<LanguageSpecificValueDetails> lsgvList) {
		this.lsgvList = lsgvList;
	}
	public List<StudySubjectPeriods> getSubList() {
		return subList;
	}
	public void setSubList(List<StudySubjectPeriods> subList) {
		this.subList = subList;
	}
	public List<StudyPeriodMaster> getSpmList() {
		return spmList;
	}
	public void setSpmList(List<StudyPeriodMaster> spmList) {
		this.spmList = spmList;
	}
	public List<StudyActivityData> getSaDataList() {
		return saDataList;
	}
	public void setSaDataList(List<StudyActivityData> saDataList) {
		this.saDataList = saDataList;
	}
	public List<StudyCheckInActivityDataDetails> getStdChkinList() {
		return stdChkinList;
	}
	public void setStdChkinList(List<StudyCheckInActivityDataDetails> stdChkinList) {
		this.stdChkinList = stdChkinList;
	}
	public List<StudyCheckOutActivityDataDetails> getStdChkoutList() {
		return stdChkoutList;
	}
	public void setStdChkoutList(List<StudyCheckOutActivityDataDetails> stdChkoutList) {
		this.stdChkoutList = stdChkoutList;
	}
	public List<StudyExecutionActivityDataDetails> getStdExList() {
		return stdExList;
	}
	public void setStdExList(List<StudyExecutionActivityDataDetails> stdExList) {
		this.stdExList = stdExList;
	}
	public List<SubjectDoseTimePoints> getSdtpList() {
		return sdtpList;
	}
	public void setSdtpList(List<SubjectDoseTimePoints> sdtpList) {
		this.sdtpList = sdtpList;
	}
	
	public List<SubjectDoseTimePointParametersData> getSdtpparamdList() {
		return sdtpparamdList;
	}
	public void setSdtpparamdList(List<SubjectDoseTimePointParametersData> sdtpparamdList) {
		this.sdtpparamdList = sdtpparamdList;
	}
	
	public List<SubjectVitalTimePointsData> getStdVitalList() {
		return stdVitalList;
	}
	public void setStdVitalList(List<SubjectVitalTimePointsData> stdVitalList) {
		this.stdVitalList = stdVitalList;
	}
	public List<SubjectVitalParametersData> getStdVitalParamList() {
		return stdVitalParamList;
	}
	public void setStdVitalParamList(List<SubjectVitalParametersData> stdVitalParamList) {
		this.stdVitalParamList = stdVitalParamList;
	}
	public List<SubjectSampleCollectionTimePointsData> getSubSapColTpDataList() {
		return subSapColTpDataList;
	}
	public void setSubSapColTpDataList(List<SubjectSampleCollectionTimePointsData> subSapColTpDataList) {
		this.subSapColTpDataList = subSapColTpDataList;
	}
	public List<SubjectMealsTimePointsData> getSubMealTpDataList() {
		return subMealTpDataList;
	}
	public List<UserMasterDto> getUsersList() {
		return usersList;
	}
	public void setUsersList(List<UserMasterDto> usersList) {
		this.usersList = usersList;
	}
	public void setSubMealTpDataList(List<SubjectMealsTimePointsData> subMealTpDataList) {
		this.subMealTpDataList = subMealTpDataList;
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
	public List<DefaultActivitys> getDefalutActList() {
		return defalutActList;
	}
	public void setDefalutActList(List<DefaultActivitys> defalutActList) {
		this.defalutActList = defalutActList;
	}
	public List<StudyTreatmentWiseSubjects> getStwSubjects() {
		return stwSubjects;
	}
	public void setStwSubjects(List<StudyTreatmentWiseSubjects> stwSubjects) {
		this.stwSubjects = stwSubjects;
	}
	public List<StudyVolunteerReporting> getStdVolList() {
		return stdVolList;
	}
	public void setStdVolList(List<StudyVolunteerReporting> stdVolList) {
		this.stdVolList = stdVolList;
	}
	public List<MealsTimePoints> getMealsList() {
		return mealsList;
	}
	public List<DoseTimePoints> getDoseList() {
		return doseList;
	}
	public List<SampleTimePoints> getSamplesList() {
		return samplesList;
	}
	public List<VitalTimePoints> getVitalList() {
		return vitalList;
	}
	public void setMealsList(List<MealsTimePoints> mealsList) {
		this.mealsList = mealsList;
	}
	public void setDoseList(List<DoseTimePoints> doseList) {
		this.doseList = doseList;
	}
	public void setSamplesList(List<SampleTimePoints> samplesList) {
		this.samplesList = samplesList;
	}
	public void setVitalList(List<VitalTimePoints> vitalList) {
		this.vitalList = vitalList;
	}
	public Projects getProject() {
		return project;
	}
	public void setProject(Projects project) {
		this.project = project;
	}
	public List<StudyActivities> getStudyActList() {
		return studyActList;
	}
	public List<StudyActivityParameters> getStudyActParamList() {
		return studyActParamList;
	}
	public void setStudyActList(List<StudyActivities> studyActList) {
		this.studyActList = studyActList;
	}
	public void setStudyActParamList(List<StudyActivityParameters> studyActParamList) {
		this.studyActParamList = studyActParamList;
	}
	public List<StaticActivityDataDetails> getSaddList() {
		return saddList;
	}
	public void setSaddList(List<StaticActivityDataDetails> saddList) {
		this.saddList = saddList;
	}
	public List<StaticActivityValueDetails> getSavdList() {
		return savdList;
	}
	public void setSavdList(List<StaticActivityValueDetails> savdList) {
		this.savdList = savdList;
	}
	public List<StaticActivityDetails> getStaticActList() {
		return staticActList;
	}
	public void setStaticActList(List<StaticActivityDetails> staticActList) {
		this.staticActList = staticActList;
	}
	public List<SubjectWithdrawDetails> getSubjWithDarawList() {
		return subjWithDarawList;
	}
	public void setSubjWithDarawList(List<SubjectWithdrawDetails> subjWithDarawList) {
		this.subjWithDarawList = subjWithDarawList;
	}
	public List<TreatmentInfo> getTreatmentsList() {
		return treatmentsList;
	}
	public void setTreatmentsList(List<TreatmentInfo> treatmentsList) {
		this.treatmentsList = treatmentsList;
	}
	public ActivityDraftReviewAudit getAdrPojo() {
		return adrPojo;
	}
	public void setAdrPojo(ActivityDraftReviewAudit adrPojo) {
		this.adrPojo = adrPojo;
	}
	public List<DosingInfoDetails> getDruginfList() {
		return druginfList;
	}
	public void setDruginfList(List<DosingInfoDetails> druginfList) {
		this.druginfList = druginfList;
	}
	public List<ProjectsDetails> getSampleProList() {
		return sampleProList;
	}
	public void setSampleProList(List<ProjectsDetails> sampleProList) {
		this.sampleProList = sampleProList;
	}
	public List<RecannulationDataDto> getRecdDtoList() {
		return recdDtoList;
	}
	public void setRecdDtoList(List<RecannulationDataDto> recdDtoList) {
		this.recdDtoList = recdDtoList;
	}
	
	
	
	

}
