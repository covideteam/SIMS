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

import org.springframework.format.annotation.DateTimeFormat;

/*@SuppressWarnings("serial")*/
@Entity
@Table(name="default_activity")
public class DefaultActivitys implements Serializable {
	
	
	private static final long serialVersionUID = -2792871666703097457L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "default_activity_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "global_activity")
	private GlobalActivity activity;
	@ManyToOne
	@JoinColumn(name = "study_phases")
	private StudyPhases studyPhases;
	@Column(name="tableName")
	private String tableName;
	@Column(name="submitControls")
	private String submitControls;
	@Column(name="subjectAllotment")
	private String subjectAllotment;
	
	@Column(name = "createdBy")
	private String createdBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	@Column(name="active_status")
	private boolean activeStatus = true;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public GlobalActivity getActivity() {
		return activity;
	}
	public void setActivity(GlobalActivity activity) {
		this.activity = activity;
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
	public StudyPhases getStudyPhases() {
		return studyPhases;
	}
	public void setStudyPhases(StudyPhases studyPhases) {
		this.studyPhases = studyPhases;
	}
	public String getTableName() {
		return tableName;
	}
	public String getSubmitControls() {
		return submitControls;
	}
	public String getSubjectAllotment() {
		return subjectAllotment;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void setSubmitControls(String submitControls) {
		this.submitControls = submitControls;
	}
	public void setSubjectAllotment(String subjectAllotment) {
		this.subjectAllotment = subjectAllotment;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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
	public boolean isActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	 
}
