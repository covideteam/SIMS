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

@Entity
@Table(name="epk_VOLUNTEER_PERIOD_Eform_STATUS")
public class VolunteerPeriodEformStatus implements Serializable {
	
	
	private static final long serialVersionUID = -6360123082311582707L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="volunteerperiodEformstatus_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="volunteerPeriodEformStatusId")
	private long id;
	private String createdBy = "System";
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn = new Date();
	private String updatedBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedOn;
	private String updateReason;
	
	private Long studyId;
	@ManyToOne
	@JoinColumn
	private StudyPeriodMaster period;
	@ManyToOne
	@JoinColumn
	private Volunteer vol;
	private String status = "NOT STARTED"; //  IN PROGRESS, IN REVIEW, COMPLETED
	
	private String withDraw="No"; // Yes
	private String withDrownBy = "";
	private Date withDrownDate;
	private String phaseContinue = "No"; // Continue
	private String allowDateEntry = "Yes"; // No;
	
	public String getWithDraw() {
		return withDraw;
	}
	public void setWithDraw(String withDraw) {
		this.withDraw = withDraw;
	}
	public String getWithDrownBy() {
		return withDrownBy;
	}
	public void setWithDrownBy(String withDrownBy) {
		this.withDrownBy = withDrownBy;
	}
	public Date getWithDrownDate() {
		return withDrownDate;
	}
	public void setWithDrownDate(Date withDrownDate) {
		this.withDrownDate = withDrownDate;
	}
	public String getPhaseContinue() {
		return phaseContinue;
	}
	public void setPhaseContinue(String phaseContinue) {
		this.phaseContinue = phaseContinue;
	}
	public String getAllowDateEntry() {
		return allowDateEntry;
	}
	public void setAllowDateEntry(String allowDateEntry) {
		this.allowDateEntry = allowDateEntry;
	}
	public Long getStudyId() {
		return studyId;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public Volunteer getVol() {
		return vol;
	}
	public void setVol(Volunteer vol) {
		this.vol = vol;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
