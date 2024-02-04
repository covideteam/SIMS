package com.covideinfo.model;

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

@Entity
@Table(name = "epk_CentrifugationDataMaster")
public class CentrifugationDataMaster extends CommonMaster{
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_CentrifugationDataMaster_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "centrifugationDataMasterId")
	private long id;
	@ManyToOne
	@JoinColumn(name="studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "periodId")
	private StudyPeriodMaster period;
	@ManyToOne
	@JoinColumn(name = "sampleTimePointsId")
	private SampleTimePoints sampleTimePoints;
	
	@ManyToOne
	@JoinColumn(name="instrumentId")
	private Instrument instrument;
	private Date centrifugeStartTime;
	private Date centrifugeEndTime;
	private Date centrifugeScanTime;
	private String centrifugeCondition;
	private String centrifugeSubjects;
	private String centrifugeMissedSubjects;
	private String timePoitns = "";
	private String subjects = "";
	@ManyToOne
	@JoinColumn(name = "centrifugeBy")
	private UserMaster centrifugeBy;
	private boolean storageCondtion;
	@ManyToOne
	@JoinColumn(name="separation_status")
	private StatusMaster status;
	
	private Date separationStartTime;
	private Date separationEndTime;
	private String missingSampleSeparation;
	private String missingSubjectsSeparation;
	private String commentSeparation;
	private String separationCondition;
	@ManyToOne
	@JoinColumn(name = "separatedBy")
	private UserMaster separatedBy;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public SampleTimePoints getSampleTimePoints() {
		return sampleTimePoints;
	}
	public void setSampleTimePoints(SampleTimePoints sampleTimePoints) {
		this.sampleTimePoints = sampleTimePoints;
	}
	public Instrument getInstrument() {
		return instrument;
	}
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	public Date getCentrifugeStartTime() {
		return centrifugeStartTime;
	}
	public void setCentrifugeStartTime(Date centrifugeStartTime) {
		this.centrifugeStartTime = centrifugeStartTime;
	}
	public Date getCentrifugeEndTime() {
		return centrifugeEndTime;
	}
	public void setCentrifugeEndTime(Date centrifugeEndTime) {
		this.centrifugeEndTime = centrifugeEndTime;
	}
	public Date getCentrifugeScanTime() {
		return centrifugeScanTime;
	}
	public void setCentrifugeScanTime(Date centrifugeScanTime) {
		this.centrifugeScanTime = centrifugeScanTime;
	}
	public String getCentrifugeCondition() {
		return centrifugeCondition;
	}
	public void setCentrifugeCondition(String centrifugeCondition) {
		this.centrifugeCondition = centrifugeCondition;
	}
	public String getCentrifugeSubjects() {
		return centrifugeSubjects;
	}
	public void setCentrifugeSubjects(String centrifugeSubjects) {
		this.centrifugeSubjects = centrifugeSubjects;
	}
	public String getCentrifugeMissedSubjects() {
		return centrifugeMissedSubjects;
	}
	public void setCentrifugeMissedSubjects(String centrifugeMissedSubjects) {
		this.centrifugeMissedSubjects = centrifugeMissedSubjects;
	}
	public String getTimePoitns() {
		return timePoitns;
	}
	public void setTimePoitns(String timePoitns) {
		this.timePoitns = timePoitns;
	}
	public String getSubjects() {
		return subjects;
	}
	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}
	public UserMaster getCentrifugeBy() {
		return centrifugeBy;
	}
	public void setCentrifugeBy(UserMaster centrifugeBy) {
		this.centrifugeBy = centrifugeBy;
	}
	public boolean isStorageCondtion() {
		return storageCondtion;
	}
	public void setStorageCondtion(boolean storageCondtion) {
		this.storageCondtion = storageCondtion;
	}
	public Date getSeparationStartTime() {
		return separationStartTime;
	}
	public void setSeparationStartTime(Date separationStartTime) {
		this.separationStartTime = separationStartTime;
	}
	public Date getSeparationEndTime() {
		return separationEndTime;
	}
	public void setSeparationEndTime(Date separationEndTime) {
		this.separationEndTime = separationEndTime;
	}
	public String getMissingSampleSeparation() {
		return missingSampleSeparation;
	}
	public void setMissingSampleSeparation(String missingSampleSeparation) {
		this.missingSampleSeparation = missingSampleSeparation;
	}
	public String getMissingSubjectsSeparation() {
		return missingSubjectsSeparation;
	}
	public void setMissingSubjectsSeparation(String missingSubjectsSeparation) {
		this.missingSubjectsSeparation = missingSubjectsSeparation;
	}
	public String getCommentSeparation() {
		return commentSeparation;
	}
	public void setCommentSeparation(String commentSeparation) {
		this.commentSeparation = commentSeparation;
	}
	public String getSeparationCondition() {
		return separationCondition;
	}
	public void setSeparationCondition(String separationCondition) {
		this.separationCondition = separationCondition;
	}
	public UserMaster getSeparatedBy() {
		return separatedBy;
	}
	public void setSeparatedBy(UserMaster separatedBy) {
		this.separatedBy = separatedBy;
	}
	public StatusMaster getStatus() {
		return status;
	}
	public void setStatus(StatusMaster status) {
		this.status = status;
	}
	
	
	
}
