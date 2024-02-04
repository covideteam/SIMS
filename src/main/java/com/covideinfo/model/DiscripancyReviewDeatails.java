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

//import org.checkerframework.checker.fenum.qual.SwingTitleJustification;

@SuppressWarnings("serial")
@Entity
@Table(name="discripancy_review_deatails")
public class DiscripancyReviewDeatails implements Serializable {
	
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "discripancy_review_deatails_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private Long id;
	@Column(name="study_id")
	private Long studyId;
	@Column(name="activity_id")
	private Long activityId;
	@ManyToOne
	@JoinColumn(name="meals")
	private SubjectMealsTimePointsData smtpData;
	@ManyToOne
	@JoinColumn(name="samples_id")
	private SubjectSampleCollectionTimePointsData ssctpData;
	@ManyToOne
	@JoinColumn(name="vitals_id")
	private SubjectVitalTimePointsData svtpData;
	@ManyToOne
	@JoinColumn(name="vital_param_id")
	private SubjectVitalParametersData subVitalParmData;
	@ManyToOne
	@JoinColumn(name="dosing_id")
	private SubjectDoseTimePoints subjectDoseId;
	@ManyToOne
	@JoinColumn(name="dosing_param_id")
	private SubjectDoseTimePointParametersData dosingParamId;
	@Column(name="filed_name")
	private String filedName;
	@Column(name="comments", length=10000)
	private String comments;
	@ManyToOne
	@JoinColumn(name="commented_by")
	private UserMaster commentedBy;
	@Column(name="commented_on")
	private Date commentedOn;
	@Column(name="response", length=10000)
	private String response;
	@ManyToOne
	@JoinColumn(name="response_given_by")
	private UserMaster responseGivenBy;
	@Column(name="response_given_date")
	private Date responseGivenDate;
	@ManyToOne
	@JoinColumn(name="status")
	private StatusMaster status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStudyId() {
		return studyId;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public SubjectMealsTimePointsData getSmtpData() {
		return smtpData;
	}
	public void setSmtpData(SubjectMealsTimePointsData smtpData) {
		this.smtpData = smtpData;
	}
	public SubjectSampleCollectionTimePointsData getSsctpData() {
		return ssctpData;
	}
	public void setSsctpData(SubjectSampleCollectionTimePointsData ssctpData) {
		this.ssctpData = ssctpData;
	}
	public SubjectVitalTimePointsData getSvtpData() {
		return svtpData;
	}
	public void setSvtpData(SubjectVitalTimePointsData svtpData) {
		this.svtpData = svtpData;
	}
	public SubjectVitalParametersData getSubVitalParmData() {
		return subVitalParmData;
	}
	public void setSubVitalParmData(SubjectVitalParametersData subVitalParmData) {
		this.subVitalParmData = subVitalParmData;
	}
	public SubjectDoseTimePoints getSubjectDoseId() {
		return subjectDoseId;
	}
	public void setSubjectDoseId(SubjectDoseTimePoints subjectDoseId) {
		this.subjectDoseId = subjectDoseId;
	}
	public SubjectDoseTimePointParametersData getDosingParamId() {
		return dosingParamId;
	}
	public void setDosingParamId(SubjectDoseTimePointParametersData dosingParamId) {
		this.dosingParamId = dosingParamId;
	}
	public String getFiledName() {
		return filedName;
	}
	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public UserMaster getCommentedBy() {
		return commentedBy;
	}
	public void setCommentedBy(UserMaster commentedBy) {
		this.commentedBy = commentedBy;
	}
	public Date getCommentedOn() {
		return commentedOn;
	}
	public void setCommentedOn(Date commentedOn) {
		this.commentedOn = commentedOn;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public UserMaster getResponseGivenBy() {
		return responseGivenBy;
	}
	public void setResponseGivenBy(UserMaster responseGivenBy) {
		this.responseGivenBy = responseGivenBy;
	}
	public Date getResponseGivenDate() {
		return responseGivenDate;
	}
	public void setResponseGivenDate(Date responseGivenDate) {
		this.responseGivenDate = responseGivenDate;
	}
	public StatusMaster getStatus() {
		return status;
	}
	public void setStatus(StatusMaster status) {
		this.status = status;
	}
	
	
	
	
	


}
