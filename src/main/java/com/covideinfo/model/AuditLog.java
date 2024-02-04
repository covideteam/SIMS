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

@SuppressWarnings("serial")
@Entity
@Table(name="audit_log")
public class AuditLog implements Serializable {
	
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="audit_log_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="activity_log_id")
	private ActivityLog activityLogId;
	@Column(name="field_name",length=10000)
	private String fieldName;
	@Column(name="old_value" ,length=10000)
	private String oldValue;
	@Column(name="new_value",length=10000)
	private String newValue;
	@Column(name="table_name")
	private String tableName;
	@ManyToOne
	@JoinColumn(name="study_id")
	private StudyMaster studyId;
	@ManyToOne
	@JoinColumn(name="volunteer_id")
	private StudyVolunteerReporting volunteerId;
	@ManyToOne
	@JoinColumn(name="subject_id")
	private StudySubjects subjectId;
	@Column(name="comments")
	private String comments;
	@Column(name="field_type")
	private String fieldType; // dynamic,static
	@Column(name="action")
	private String action;
	@Column(name="databasefieldname")
	private String databasefieldname;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	public long getId() {
		return id;
	}
	public ActivityLog getActivityLogId() {
		return activityLogId;
	}
	public String getOldValue() {
		return oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public StudyMaster getStudyId() {
		return studyId;
	}
	public StudyVolunteerReporting getVolunteerId() {
		return volunteerId;
	}
	public StudySubjects getSubjectId() {
		return subjectId;
	}
	public String getComments() {
		return comments;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setActivityLogId(ActivityLog activityLogId) {
		this.activityLogId = activityLogId;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public void setStudyId(StudyMaster studyId) {
		this.studyId = studyId;
	}
	public void setVolunteerId(StudyVolunteerReporting volunteerId) {
		this.volunteerId = volunteerId;
	}
	public void setSubjectId(StudySubjects subjectId) {
		this.subjectId = subjectId;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getAction() {
		return action;
	}
	public String getDatabasefieldname() {
		return databasefieldname;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public void setDatabasefieldname(String databasefieldname) {
		this.databasefieldname = databasefieldname;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
	
	
	

}
