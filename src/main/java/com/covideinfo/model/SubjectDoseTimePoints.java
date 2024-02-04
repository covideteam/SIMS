package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "subject_dose_timepoints")
public class SubjectDoseTimePoints implements Serializable {

	private static final long serialVersionUID = 6360791229895654871L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="subject_dose_timepoints_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name = "subjectDoseTimePointsId")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "periodId")
	private StudyPeriodMaster period;
	@ManyToOne
	@JoinColumn(name = "doseTimePointsId")
	private DoseTimePoints doseTimePoints;
	@ManyToOne
	@JoinColumn(name = "studySubjectsId")
	private StudySubjects studySubjects;

	private Date actualTime;
	private Date scheduleTime;
	private Date sachetScanTime;
	private Date subjectScanTime;
	private Date collectionSecTime;
	private Date submissionTime;
	@ManyToOne
	@JoinColumn(name="deviation_Msg")
	private DeviationMessage deviationMsg;
	private String diviation;
	@ManyToOne
	@JoinColumn(name = "doseDoneBy")
	private UserMaster doseDoneBy;
	@ManyToOne
	@JoinColumn(name = "supervisedBy")
	private UserMaster supervisedBy;
	private Date supervisedOn;

	private String fastCriteraComments = "";
	private String feadCriteraComments = "";
	@ManyToOne
	@JoinColumn(name="created_by")
	private UserMaster createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@ManyToOne
	@JoinColumn(name="updated_by")
	private UserMaster updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	private String updateReason;
	
	@Transient
	private List<SubejectDosePerameter> subjectDosePerametres;

	public Long getId() {
		return id;
	}

	public StudyPeriodMaster getPeriod() {
		return period;
	}

	public DoseTimePoints getDoseTimePoints() {
		return doseTimePoints;
	}

	public StudySubjects getStudySubjects() {
		return studySubjects;
	}

	public Date getActualTime() {
		return actualTime;
	}

	public Date getScheduleTime() {
		return scheduleTime;
	}

	public Date getSachetScanTime() {
		return sachetScanTime;
	}

	public Date getSubjectScanTime() {
		return subjectScanTime;
	}

	public Date getCollectionSecTime() {
		return collectionSecTime;
	}

	public Date getSubmissionTime() {
		return submissionTime;
	}

	public String getDiviation() {
		return diviation;
	}

	public UserMaster getDoseDoneBy() {
		return doseDoneBy;
	}

	public UserMaster getSupervisedBy() {
		return supervisedBy;
	}

	public Date getSupervisedOn() {
		return supervisedOn;
	}

	public String getFastCriteraComments() {
		return fastCriteraComments;
	}

	public String getFeadCriteraComments() {
		return feadCriteraComments;
	}

	public UserMaster getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public UserMaster getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public String getUpdateReason() {
		return updateReason;
	}

	public List<SubejectDosePerameter> getSubjectDosePerametres() {
		return subjectDosePerametres;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}

	public void setDoseTimePoints(DoseTimePoints doseTimePoints) {
		this.doseTimePoints = doseTimePoints;
	}

	public void setStudySubjects(StudySubjects studySubjects) {
		this.studySubjects = studySubjects;
	}

	public void setActualTime(Date actualTime) {
		this.actualTime = actualTime;
	}

	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public void setSachetScanTime(Date sachetScanTime) {
		this.sachetScanTime = sachetScanTime;
	}

	public void setSubjectScanTime(Date subjectScanTime) {
		this.subjectScanTime = subjectScanTime;
	}

	public void setCollectionSecTime(Date collectionSecTime) {
		this.collectionSecTime = collectionSecTime;
	}

	public void setSubmissionTime(Date submissionTime) {
		this.submissionTime = submissionTime;
	}
	public void setDiviation(String diviation) {
		this.diviation = diviation;
	}

	public void setDoseDoneBy(UserMaster doseDoneBy) {
		this.doseDoneBy = doseDoneBy;
	}

	public void setSupervisedBy(UserMaster supervisedBy) {
		this.supervisedBy = supervisedBy;
	}

	public void setSupervisedOn(Date supervisedOn) {
		this.supervisedOn = supervisedOn;
	}

	public void setFastCriteraComments(String fastCriteraComments) {
		this.fastCriteraComments = fastCriteraComments;
	}

	public void setFeadCriteraComments(String feadCriteraComments) {
		this.feadCriteraComments = feadCriteraComments;
	}

	public void setCreatedBy(UserMaster createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setUpdatedBy(UserMaster updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public void setSubjectDosePerametres(List<SubejectDosePerameter> subjectDosePerametres) {
		this.subjectDosePerametres = subjectDosePerametres;
	}

	public DeviationMessage getDeviationMsg() {
		return deviationMsg;
	}

	public void setDeviationMsg(DeviationMessage deviationMsg) {
		this.deviationMsg = deviationMsg;
	}

	@Override
	public String toString() {
		return "SubjectDoseTimePoints [id=" + id + ", period=" + period + ", actualTime=" + actualTime + "]";
	}

	

	
	

	
}
