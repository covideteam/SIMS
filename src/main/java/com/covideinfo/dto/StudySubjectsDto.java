package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.covideinfo.model.StudyGroup;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;

public class StudySubjectsDto implements Serializable{
	private long id;
	private String subjectNo;
	private StudyVolunteerReporting reportingId;
	private String subjectStatus = ""; //DropOut, Replace
	private Long stdSubjectId;
	private StudyGroup groupId;
	private StudyMaster study;
	private String subjectReplace="";
	private String subjectDiscontinue="";
	private String createdBy;
	private Date createdOn;
	private String upatedBy;
	private Date updatedOn;
	public String updateReason;
	
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public StudyVolunteerReporting getReportingId() {
		return reportingId;
	}
	public void setReportingId(StudyVolunteerReporting reportingId) {
		this.reportingId = reportingId;
	}
	public String getSubjectStatus() {
		return subjectStatus;
	}
	public void setSubjectStatus(String subjectStatus) {
		this.subjectStatus = subjectStatus;
	}
	public Long getStdSubjectId() {
		return stdSubjectId;
	}
	public void setStdSubjectId(Long stdSubjectId) {
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
