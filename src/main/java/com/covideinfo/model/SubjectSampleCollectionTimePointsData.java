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

@SuppressWarnings("serial")
@Entity
@Table(name = "epk_subject_sample_Collection_Timepoints_Data")
public class SubjectSampleCollectionTimePointsData extends CommonMaster {

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_epk_subject_sample_Collection_Data_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "subjectSampleCollectionDataId")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "studyPeriodMasterId")
	private StudyPeriodMaster studyPeriodMaster;
	@ManyToOne
	@JoinColumn(name = "sampleTimePointId")
	private SampleTimePoints sampleTimePoint;
	@ManyToOne
	@JoinColumn(name = "subjectId")
	private StudySubjects subject;
	private Date actualtime ;
	private String sampleReason;
	private String deviation;
	@ManyToOne
	@JoinColumn(name = "diviationMessageId")
	private DeviationMessage diviationMessage;

	private Date vacutinerScanTime;
	private Date subjectScanTime;
	private Date submissionTime;
	private Date scheduleTime;
	@ManyToOne
	@JoinColumn(name = "collectedBy")
	private UserMaster collectedBy;
	private String modeOfCollection;
	private Date vialActualTime;
	@ManyToOne
	@JoinColumn(name = "separatedBy")
	private UserMaster separatedBy;
	
	@ManyToOne
	@JoinColumn(name = "sample_dev_comments")
	private DeviationMessage sampleDevComments;
	@ManyToOne
	@JoinColumn(name = "sample_deviation_id")
	private DeviationMessage sampleDeviationId;
	
	
	public Date getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public StudyPeriodMaster getStudyPeriodMaster() {
		return studyPeriodMaster;
	}
	public void setStudyPeriodMaster(StudyPeriodMaster studyPeriodMaster) {
		this.studyPeriodMaster = studyPeriodMaster;
	}
	public SampleTimePoints getSampleTimePoint() {
		return sampleTimePoint;
	}
	public void setSampleTimePoint(SampleTimePoints sampleTimePoint) {
		this.sampleTimePoint = sampleTimePoint;
	}
	public StudySubjects getSubject() {
		return subject;
	}
	public void setSubject(StudySubjects subject) {
		this.subject = subject;
	}
	public UserMaster getSeparatedBy() {
		return separatedBy;
	}
	public void setSeparatedBy(UserMaster separatedBy) {
		this.separatedBy = separatedBy;
	}
	public Date getVialActualTime() {
		return vialActualTime;
	}
	public void setVialActualTime(Date vialActualTime) {
		this.vialActualTime = vialActualTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getActualtime() {
		return actualtime;
	}
	public void setActualtime(Date actualtime) {
		this.actualtime = actualtime;
	}
	
	public String getDeviation() {
		return deviation;
	}
	public String getSampleReason() {
		return sampleReason;
	}
	public void setSampleReason(String sampleReason) {
		this.sampleReason = sampleReason;
	}
	public void setDeviation(String deviation) {
		this.deviation = deviation;
	}
	
	public DeviationMessage getDiviationMessage() {
		return diviationMessage;
	}
	public void setDiviationMessage(DeviationMessage diviationMessage) {
		this.diviationMessage = diviationMessage;
	}
	public Date getVacutinerScanTime() {
		return vacutinerScanTime;
	}
	public void setVacutinerScanTime(Date vacutinerScanTime) {
		this.vacutinerScanTime = vacutinerScanTime;
	}
	public Date getSubjectScanTime() {
		return subjectScanTime;
	}
	public void setSubjectScanTime(Date subjectScanTime) {
		this.subjectScanTime = subjectScanTime;
	}

	public Date getSubmissionTime() {
		return submissionTime;
	}
	public void setSubmissionTime(Date submissionTime) {
		this.submissionTime = submissionTime;
	}
	public UserMaster getCollectedBy() {
		return collectedBy;
	}
	public void setCollectedBy(UserMaster collectedBy) {
		this.collectedBy = collectedBy;
	}
	public String getModeOfCollection() {
		return modeOfCollection;
	}
	public void setModeOfCollection(String modeOfCollection) {
		this.modeOfCollection = modeOfCollection;
	}
	public DeviationMessage getSampleDevComments() {
		return sampleDevComments;
	}
	public void setSampleDevComments(DeviationMessage sampleDevComments) {
		this.sampleDevComments = sampleDevComments;
	}
	public DeviationMessage getSampleDeviationId() {
		return sampleDeviationId;
	}
	public void setSampleDeviationId(DeviationMessage sampleDeviationId) {
		this.sampleDeviationId = sampleDeviationId;
	}

	

	
}
