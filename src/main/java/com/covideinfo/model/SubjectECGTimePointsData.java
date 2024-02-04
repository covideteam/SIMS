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
@Table(name = "epk_subject_ecg_time_points_data")
public class SubjectECGTimePointsData extends CommonMaster {

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_subject_ecg_time_points_data_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "subjectECGTimePointsDataId")
	private long id;

	
	private String startTime = "";
	private String endTime = "";
	private String actualDate = "";
	private String comments = "";
	private String deviation;
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
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getDeviation() {
		return deviation;
	}
	public void setDeviation(String deviation) {
		this.deviation = deviation;
	}
	
	
}
