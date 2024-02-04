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
@Table(name="study_group_periods")
public class StudyGroupPeriodMaster implements Serializable{
		
	
	
	private static final long serialVersionUID = 5364122932797081092L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_group_periods_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "studyGroupPeriodMasterId")
	private long id;
    @Column(name="study_id")
    private Long studyId;
	@ManyToOne
	@JoinColumn(name = "studyGroupId")
	private StudyGroup studyGroup;
	@ManyToOne
	@JoinColumn(name = "periodId")
	private StudyPeriodMaster period;
	private String periodName;
	private int periodNo = 0;
	@ManyToOne
	@JoinColumn(name = "periodStatus")
	private StatusMaster periodStatus;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	public long getId() {
		return id;
	}
	public Long getStudyId() {
		return studyId;
	}
	public StudyGroup getStudyGroup() {
		return studyGroup;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public String getPeriodName() {
		return periodName;
	}
	public int getPeriodNo() {
		return periodNo;
	}
	public StatusMaster getPeriodStatus() {
		return periodStatus;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public void setStudyGroup(StudyGroup studyGroup) {
		this.studyGroup = studyGroup;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public void setPeriodNo(int periodNo) {
		this.periodNo = periodNo;
	}
	public void setPeriodStatus(StatusMaster periodStatus) {
		this.periodStatus = periodStatus;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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
