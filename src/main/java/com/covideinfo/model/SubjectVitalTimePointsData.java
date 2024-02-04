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
@Table(name = "epk_subject_vital_time_points_data")
public class SubjectVitalTimePointsData extends CommonMaster {

	private static final long serialVersionUID = 8506579629223909293L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_subject_vital_time_points_data_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "subjectVitalTimePointsDataId")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "subjectId")
	private StudySubjects subject;
	@ManyToOne
	@JoinColumn(name = "periodId")
	private StudyPeriodMaster period;
	@ManyToOne
	@JoinColumn(name = "timePointId")
	private VitalTimePoints timepoint;
	
	private Date actualtime;
	private Date scheduleTime;
	private Date startTime;
	private Date endTime;
	private String comments = "";

	private Date subjectScanTime;
	private Date submissionTime ;
	private String message;
	private String deviation;
	private String colltedPosition;
	@ManyToOne
	@JoinColumn(name = "collectedBy")
	private UserMaster collectedBy;
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public StudySubjects getSubject() {
		return subject;
	}
	public void setSubject(StudySubjects subject) {
		this.subject = subject;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public VitalTimePoints getTimepoint() {
		return timepoint;
	}
	public void setTimepoint(VitalTimePoints timepoint) {
		this.timepoint = timepoint;
	}
	public Date getActualtime() {
		return actualtime;
	}
	public void setActualtime(Date actualtime) {
		this.actualtime = actualtime;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
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
	public String getDeviation() {
		return deviation;
	}
	public void setDeviation(String deviation) {
		this.deviation = deviation;
	}
	public UserMaster getCollectedBy() {
		return collectedBy;
	}
	public void setCollectedBy(UserMaster collectedBy) {
		this.collectedBy = collectedBy;
	}
	public Date getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public String getColltedPosition() {
		return colltedPosition;
	}
	public void setColltedPosition(String colltedPosition) {
		this.colltedPosition = colltedPosition;
	}
	
	
}
