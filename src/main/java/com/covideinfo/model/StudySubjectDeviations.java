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
import javax.persistence.Transient;

@Entity
@Table(name="study_subject_deviations")
public class StudySubjectDeviations implements Serializable {
	
	
	private static final long serialVersionUID = -1873414867610856012L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_subject_deviations_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private long id;
	@Column(name="study_id")
	private Long studyId;
	@Column(name="period_name")
	private String period;
	@Column(name="time_point")
	private String timePoint;
	@Column(name="deviation_record_id")
	private Long deviationRecordId; //dosing(SubjectDoseTimePoints), Meals(SubjectMealsTimePointsData), Samples(SubjectSamplesTimpointData), Vitals(VitalTimePointsData)
	@ManyToOne
	@JoinColumn(name="subject")
	private StudySubjects subject;
	@ManyToOne
	@JoinColumn(name="activity")
	private GlobalActivity activity;
	@ManyToOne
	@JoinColumn(name="dev_msg_id")
	private DeviationMessage devMsgId;
	@ManyToOne
	@JoinColumn(name="status")
	private StatusMaster status;
	@ManyToOne
	@JoinColumn(name="created_by")
	private UserMaster createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	@Transient
	private String deviationTime;
	
	public long getId() {
		return id;
	}
	public StudySubjects getSubject() {
		return subject;
	}
	public GlobalActivity getActivity() {
		return activity;
	}
	public UserMaster getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setSubject(StudySubjects subject) {
		this.subject = subject;
	}
	public void setActivity(GlobalActivity activity) {
		this.activity = activity;
	}
	public void setCreatedBy(UserMaster createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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
	public Long getStudyId() {
		return studyId;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public StatusMaster getStatus() {
		return status;
	}
	public void setStatus(StatusMaster status) {
		this.status = status;
	}
	public Long getDeviationRecordId() {
		return deviationRecordId;
	}
	public String getDeviationTime() {
		return deviationTime;
	}
	public void setDeviationTime(String deviationTime) {
		this.deviationTime = deviationTime;
	}
	public void setDeviationRecordId(Long deviationRecordId) {
		this.deviationRecordId = deviationRecordId;
	}
	public DeviationMessage getDevMsgId() {
		return devMsgId;
	}
	public void setDevMsgId(DeviationMessage devMsgId) {
		this.devMsgId = devMsgId;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
}
