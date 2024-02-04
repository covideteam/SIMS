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

/*@SuppressWarnings("serial")*/
@Entity
@Table(name="study_activity_time_points")
public class StudyActivityTimePoints implements Serializable {
	
	
	private static final long serialVersionUID = 8814604876928306121L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_activity_time_points_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private long id;
	@Column(name="time_point")
	private String timePoint;
	@Column(name="window_period_sign")
	private String windowPeriodSign;
	@Column(name="window_period")
	private int windowPeriod;
	@Column(name="window_period_type")
	private String windowPeriodType;
	@Column(name="position")
	private String position;
	@Column(name="orthostatic")
	private String orthoSatatic;
	@Column(name="orthostatic_position")
	private String orthoStaticPosition;
	@Column(name="timpoint_sign")
	private String timePointSign;
	@ManyToOne
	@JoinColumn(name="study_activity")
	private StudyActivities studyActivity;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	public long getId() {
		return id;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public String getWindowPeriodSign() {
		return windowPeriodSign;
	}
	public String getWindowPeriodType() {
		return windowPeriodType;
	}
	public String getPosition() {
		return position;
	}
	public String getOrthoSatatic() {
		return orthoSatatic;
	}
	public String getOrthoStaticPosition() {
		return orthoStaticPosition;
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
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public void setWindowPeriodSign(String windowPeriodSign) {
		this.windowPeriodSign = windowPeriodSign;
	}
	public void setWindowPeriodType(String windowPeriodType) {
		this.windowPeriodType = windowPeriodType;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public void setOrthoSatatic(String orthoSatatic) {
		this.orthoSatatic = orthoSatatic;
	}
	public void setOrthoStaticPosition(String orthoStaticPosition) {
		this.orthoStaticPosition = orthoStaticPosition;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public int getWindowPeriod() {
		return windowPeriod;
	}
	public void setWindowPeriod(int windowPeriod) {
		this.windowPeriod = windowPeriod;
	}
	public String getTimePointSign() {
		return timePointSign;
	}
	public void setTimePointSign(String timePointSign) {
		this.timePointSign = timePointSign;
	}
	public StudyActivities getStudyActivity() {
		return studyActivity;
	}
	public void setStudyActivity(StudyActivities studyActivity) {
		this.studyActivity = studyActivity;
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
	
	

}
