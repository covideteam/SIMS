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
@Table(name="activity_log")
public class ActivityLog implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1759611565634406777L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="activity_log_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="table_id")
	private Long tableId;
	@Column(name="tablename")
	private String tableName;
	@Column(name="activity_name", length=10000)
	private String activityName;
	@Column(name="actvity_description", length=10000)
	private String activityDescription;
	@Column(name="activity_done_by")
	private String activityDoneBy;
	@Column(name="activity_done_on")
	private Date activityDoneOn;
	@ManyToOne
	@JoinColumn(name="study_id")
	private StudyMaster sm;
	@ManyToOne
	@JoinColumn(name="subject_id")
	private StudySubjects subjectId;
	@ManyToOne
	@JoinColumn(name="volunteer_id")
	private StudyVolunteerReporting volId;
	public long getId() {
		return id;
	}
	public Long getTableId() {
		return tableId;
	}
	public String getTableName() {
		return tableName;
	}
	public String getActivityName() {
		return activityName;
	}
	public String getActivityDoneBy() {
		return activityDoneBy;
	}
	public Date getActivityDoneOn() {
		return activityDoneOn;
	}
	public StudyMaster getSm() {
		return sm;
	}
	public StudySubjects getSubjectId() {
		return subjectId;
	}
	public StudyVolunteerReporting getVolId() {
		return volId;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public void setActivityDoneBy(String activityDoneBy) {
		this.activityDoneBy = activityDoneBy;
	}
	public void setActivityDoneOn(Date activityDoneOn) {
		this.activityDoneOn = activityDoneOn;
	}
	public void setSm(StudyMaster sm) {
		this.sm = sm;
	}
	public void setSubjectId(StudySubjects subjectId) {
		this.subjectId = subjectId;
	}
	public void setVolId(StudyVolunteerReporting volId) {
		this.volId = volId;
	}
	public String getActivityDescription() {
		return activityDescription;
	}
	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}
	
	
	
	
	
	
	

}
