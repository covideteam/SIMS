package com.covideinfo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "epk_Subject_Additional_Assessment_timeponts_data")
public class SubjectAdditionalAssessmentTimePointsData extends CommonMaster {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8503895699146634775L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_Subject_Additional_Assessment_timeponts_data_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "subjectAdditionalAssessmentTimePointsDataId")
	private long id;

	@ManyToOne
	@JoinColumn(name = "subjectAdditionalAssessmentTimePointsId")
	private SubjectAdditionalAssessmentTimePoints subjectAdditionalAssessmentTimePoints;

	private String scheduleTime = "";
	private String scheduleDate = "";
	private String actualtime = "";
	private String actualDate = "";
	private String comments = "";

	private String subjectScanTime = "";
	private String submissionTime = "";
	@ManyToOne
	@JoinColumn(name = "collectedBy")
	private UserMaster collectedBy;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public SubjectAdditionalAssessmentTimePoints getSubjectAdditionalAssessmentTimePoints() {
		return subjectAdditionalAssessmentTimePoints;
	}
	public void setSubjectAdditionalAssessmentTimePoints(
			SubjectAdditionalAssessmentTimePoints subjectAdditionalAssessmentTimePoints) {
		this.subjectAdditionalAssessmentTimePoints = subjectAdditionalAssessmentTimePoints;
	}
	public String getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getActualtime() {
		return actualtime;
	}
	public void setActualtime(String actualtime) {
		this.actualtime = actualtime;
	}
	public String getActualDate() {
		return actualDate;
	}
	public void setActualDate(String actualDate) {
		this.actualDate = actualDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getSubjectScanTime() {
		return subjectScanTime;
	}
	public void setSubjectScanTime(String subjectScanTime) {
		this.subjectScanTime = subjectScanTime;
	}
	public String getSubmissionTime() {
		return submissionTime;
	}
	public void setSubmissionTime(String submissionTime) {
		this.submissionTime = submissionTime;
	}
	public UserMaster getCollectedBy() {
		return collectedBy;
	}
	public void setCollectedBy(UserMaster collectedBy) {
		this.collectedBy = collectedBy;
	}
	
}
