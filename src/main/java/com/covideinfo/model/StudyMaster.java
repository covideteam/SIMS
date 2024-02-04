package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*@SuppressWarnings("serial")*/
@Entity
@Table(name = "study_master")
public class StudyMaster implements Serializable {

	
	private static final long serialVersionUID = 4925474673687916810L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_master_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "studyId")
	private Long id;

	private String projectNo;
	private String versionNo;
	private int seqNo;//auto increment for each study specific
//	@ManyToOne
//	@JoinColumn(name = "sponsorCode")
//	private SponsorCode sponsorCode;
	private String sponsorCode;
	@ManyToOne
	@JoinColumn(name = "studyCategory")
	private FromStaticData studyCategory; //Pilot / Peyote 
	@ManyToOne
	@JoinColumn(name = "studyType")
	private FromStaticData studyType; // fast/fed/fast&fed
	@ManyToOne
	@JoinColumn(name = "studyDesing")
	private FromStaticData studyDesing; // Open Label/Single Blind/Double Blind
	@Column(name = "studyTitle", length = 8000)
	private String studyTitle;
	private int noOfPeriods;
	private int washoutPeriod;
	@ManyToOne
	@JoinColumn(name = "dosageFrom")
	private DosageForm dosageFrom; 
	
	@ManyToOne
	@JoinColumn(name = "treatmentCodeOnSachet")
	private FromStaticData treatmentCodeOnSachet; // Show/Hide
	private int noOfSubjects;
	private int noOfStandBySubjects;
	@ManyToOne
	@JoinColumn(name = "doseType")
	private FromStaticData doseType; // Single/Multiple
	private int noOfTreatments;
	private int preDoseHr;
	private int postDoseHr;
	private boolean treatmentWiseNoOfDosing;
	private boolean treatmentSpecificSampleTimepoints; // Yes/No
	private boolean treatmentWiseDoseParametres; // Yes/No
	private boolean treatmentWiseMealsTimepoints;
	private boolean treatmentWiseVitalTimepoints;
	private boolean treatmentWiseEcgTimepoints;
	@ManyToOne
	@JoinColumn(name = "studyStatus")
	private StatusMaster studyStatus; // Locked/Design/Available/Active/In-Active

	@ManyToOne
	@JoinColumn(name = "studyState")
	private StatusMaster studyState; // New State/Design/Execution/Completed/
	
	private boolean subjectSamplesTimePoints;
	private boolean subjectVitalsTimePoits;
	private boolean subjectEcgTimePoits;
	private boolean subjectMealsTimePoits;
	private boolean subjectDoseingTimepoints;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	public boolean isSubjectEcgTimePoits() {
		return subjectEcgTimePoits;
	}
	public void setSubjectEcgTimePoits(boolean subjectEcgTimePoits) {
		this.subjectEcgTimePoits = subjectEcgTimePoits;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	
	public String getSponsorCode() {
		return sponsorCode;
	}
	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}
	public FromStaticData getStudyCategory() {
		return studyCategory;
	}
	public void setStudyCategory(FromStaticData studyCategory) {
		this.studyCategory = studyCategory;
	}
	public FromStaticData getStudyType() {
		return studyType;
	}
	public void setStudyType(FromStaticData studyType) {
		this.studyType = studyType;
	}
	public FromStaticData getStudyDesing() {
		return studyDesing;
	}
	public void setStudyDesing(FromStaticData studyDesing) {
		this.studyDesing = studyDesing;
	}
	public String getStudyTitle() {
		return studyTitle;
	}
	public void setStudyTitle(String studyTitle) {
		this.studyTitle = studyTitle;
	}
	public int getNoOfPeriods() {
		return noOfPeriods;
	}
	public void setNoOfPeriods(int noOfPeriods) {
		this.noOfPeriods = noOfPeriods;
	}
	public int getWashoutPeriod() {
		return washoutPeriod;
	}
	public void setWashoutPeriod(int washoutPeriod) {
		this.washoutPeriod = washoutPeriod;
	}
	public DosageForm getDosageFrom() {
		return dosageFrom;
	}
	public void setDosageFrom(DosageForm dosageFrom) {
		this.dosageFrom = dosageFrom;
	}
	public FromStaticData getTreatmentCodeOnSachet() {
		return treatmentCodeOnSachet;
	}
	public void setTreatmentCodeOnSachet(FromStaticData treatmentCodeOnSachet) {
		this.treatmentCodeOnSachet = treatmentCodeOnSachet;
	}
	public int getNoOfSubjects() {
		return noOfSubjects;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public void setNoOfSubjects(int noOfSubjects) {
		this.noOfSubjects = noOfSubjects;
	}
	public int getNoOfStandBySubjects() {
		return noOfStandBySubjects;
	}
	public void setNoOfStandBySubjects(int noOfStandBySubjects) {
		this.noOfStandBySubjects = noOfStandBySubjects;
	}
	public FromStaticData getDoseType() {
		return doseType;
	}
	public void setDoseType(FromStaticData doseType) {
		this.doseType = doseType;
	}
	public int getNoOfTreatments() {
		return noOfTreatments;
	}
	public void setNoOfTreatments(int noOfTreatments) {
		this.noOfTreatments = noOfTreatments;
	}
	public int getPreDoseHr() {
		return preDoseHr;
	}
	public void setPreDoseHr(int preDoseHr) {
		this.preDoseHr = preDoseHr;
	}
	public int getPostDoseHr() {
		return postDoseHr;
	}
	public void setPostDoseHr(int postDoseHr) {
		this.postDoseHr = postDoseHr;
	}
	public boolean isTreatmentWiseNoOfDosing() {
		return treatmentWiseNoOfDosing;
	}
	public void setTreatmentWiseNoOfDosing(boolean treatmentWiseNoOfDosing) {
		this.treatmentWiseNoOfDosing = treatmentWiseNoOfDosing;
	}
	public boolean isTreatmentSpecificSampleTimepoints() {
		return treatmentSpecificSampleTimepoints;
	}
	public void setTreatmentSpecificSampleTimepoints(boolean treatmentSpecificSampleTimepoints) {
		this.treatmentSpecificSampleTimepoints = treatmentSpecificSampleTimepoints;
	}
	public boolean isTreatmentWiseDoseParametres() {
		return treatmentWiseDoseParametres;
	}
	public void setTreatmentWiseDoseParametres(boolean treatmentWiseDoseParametres) {
		this.treatmentWiseDoseParametres = treatmentWiseDoseParametres;
	}
	public StatusMaster getStudyStatus() {
		return studyStatus;
	}
	public void setStudyStatus(StatusMaster studyStatus) {
		this.studyStatus = studyStatus;
	}
	public StatusMaster getStudyState() {
		return studyState;
	}
	public void setStudyState(StatusMaster studyState) {
		this.studyState = studyState;
	}
	public boolean isSubjectSamplesTimePoints() {
		return subjectSamplesTimePoints;
	}
	public void setSubjectSamplesTimePoints(boolean subjectSamplesTimePoints) {
		this.subjectSamplesTimePoints = subjectSamplesTimePoints;
	}
	public boolean isSubjectVitalsTimePoits() {
		return subjectVitalsTimePoits;
	}
	public void setSubjectVitalsTimePoits(boolean subjectVitalsTimePoits) {
		this.subjectVitalsTimePoits = subjectVitalsTimePoits;
	}
	public boolean isSubjectMealsTimePoits() {
		return subjectMealsTimePoits;
	}
	public void setSubjectMealsTimePoits(boolean subjectMealsTimePoits) {
		this.subjectMealsTimePoits = subjectMealsTimePoits;
	}
	public boolean isSubjectDoseingTimepoints() {
		return subjectDoseingTimepoints;
	}
	public void setSubjectDoseingTimepoints(boolean subjectDoseingTimepoints) {
		this.subjectDoseingTimepoints = subjectDoseingTimepoints;
	}
	public boolean isTreatmentWiseMealsTimepoints() {
		return treatmentWiseMealsTimepoints;
	}
	public void setTreatmentWiseMealsTimepoints(boolean treatmentWiseMealsTimepoints) {
		this.treatmentWiseMealsTimepoints = treatmentWiseMealsTimepoints;
	}
	public boolean isTreatmentWiseVitalTimepoints() {
		return treatmentWiseVitalTimepoints;
	}
	public void setTreatmentWiseVitalTimepoints(boolean treatmentWiseVitalTimepoints) {
		this.treatmentWiseVitalTimepoints = treatmentWiseVitalTimepoints;
	}
	public boolean isTreatmentWiseEcgTimepoints() {
		return treatmentWiseEcgTimepoints;
	}
	public void setTreatmentWiseEcgTimepoints(boolean treatmentWiseEcgTimepoints) {
		this.treatmentWiseEcgTimepoints = treatmentWiseEcgTimepoints;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	
	
}
