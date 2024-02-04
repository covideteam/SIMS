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


@Entity
@Table(name="study_subjects")
public class StudySubjects implements Serializable {
	
	
	private static final long serialVersionUID = 1768666710003928977L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_subjects_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private Long id;
	@ManyToOne
	@JoinColumn(name="reporting_id")
	private StudyVolunteerReporting reportingId;
	@Column(name="subject_no")
	private String subjectNo;
	@Column(name="subject_status")
	private String subjectStatus = "active"; //DropOut, Replace, inactive, active
	@ManyToOne
	@JoinColumn(name="std_subject_id")
	private StudySubjects stdSubjectId;
	@ManyToOne
	@JoinColumn(name = "studyGroupId")
	private StudyGroup groupId;
	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@Column(name="subject_replace")
	private String subjectReplace="";
	@Column(name="subject_discontinue")
	private String subjectDiscontinue="";
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String upatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public StudyVolunteerReporting getReportingId() {
		return reportingId;
	}
	public void setReportingId(StudyVolunteerReporting reportingId) {
		this.reportingId = reportingId;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public String getSubjectStatus() {
		return subjectStatus;
	}
	public void setSubjectStatus(String subjectStatus) {
		this.subjectStatus = subjectStatus;
	}
	public StudySubjects getStdSubjectId() {
		return stdSubjectId;
	}
	public void setStdSubjectId(StudySubjects stdSubjectId) {
		this.stdSubjectId = stdSubjectId;
	}
	public StudyGroup getGroupId() {
		return groupId;
	}
	public void setGroupId(StudyGroup groupId) {
		this.groupId = groupId;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public String getSubjectReplace() {
		return subjectReplace;
	}
	public void setSubjectReplace(String subjectReplace) {
		this.subjectReplace = subjectReplace;
	}
	public String getSubjectDiscontinue() {
		return subjectDiscontinue;
	}
	public void setSubjectDiscontinue(String subjectDiscontinue) {
		this.subjectDiscontinue = subjectDiscontinue;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getUpatedBy() {
		return upatedBy;
	}
	public void setUpatedBy(String upatedBy) {
		this.upatedBy = upatedBy;
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
