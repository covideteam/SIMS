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
@Table(name = "study_dose_time_points")
public class DoseTimePoints implements Serializable, Comparable<DoseTimePoints> {

	
	private static final long serialVersionUID = -4234587604009886046L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_dose_time_points_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "doseTimePointsId")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "treatmentInfoId")
	private TreatmentInfo treatmentInfo;
	private String timePoint;
	private String fastingCriteria;
	@ManyToOne
	@JoinColumn(name = "fastingCriteriaType")
	private FromStaticData fastingCriteriaType;
	private String fedCriteria;
	@ManyToOne
	@JoinColumn(name = "fedcriteriaType")
	private FromStaticData fedcriteriaType;
	private String windowPeriodSign;
	private int windowPeriod;
	@ManyToOne
	@JoinColumn(name = "windowPeriodType")
	private FromStaticData windowPeriodType;
	@ManyToOne
	@JoinColumn(name = "activeStatus")
	private StatusMaster activeStatus;
	@Column(name="parameters")
	private String parameters;
	private int timePointNo;
	private int noOfSachet = 1;
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
	@Transient
	private Double dosetp;
	
	public int getNoOfSachet() {
		return noOfSachet;
	}
	public void setNoOfSachet(int noOfSachet) {
		this.noOfSachet = noOfSachet;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public TreatmentInfo getTreatmentInfo() {
		return treatmentInfo;
	}
	public void setTreatmentInfo(TreatmentInfo treatmentInfo) {
		this.treatmentInfo = treatmentInfo;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public String getFastingCriteria() {
		return fastingCriteria;
	}
	public void setFastingCriteria(String fastingCriteria) {
		this.fastingCriteria = fastingCriteria;
	}
	public FromStaticData getFastingCriteriaType() {
		return fastingCriteriaType;
	}
	public void setFastingCriteriaType(FromStaticData fastingCriteriaType) {
		this.fastingCriteriaType = fastingCriteriaType;
	}
	public String getFedCriteria() {
		return fedCriteria;
	}
	public void setFedCriteria(String fedCriteria) {
		this.fedCriteria = fedCriteria;
	}
	public FromStaticData getFedcriteriaType() {
		return fedcriteriaType;
	}
	public void setFedcriteriaType(FromStaticData fedcriteriaType) {
		this.fedcriteriaType = fedcriteriaType;
	}
	public String getWindowPeriodSign() {
		return windowPeriodSign;
	}
	public void setWindowPeriodSign(String windowPeriodSign) {
		this.windowPeriodSign = windowPeriodSign;
	}
	public int getWindowPeriod() {
		return windowPeriod;
	}
	public void setWindowPeriod(int windowPeriod) {
		this.windowPeriod = windowPeriod;
	}
	public FromStaticData getWindowPeriodType() {
		return windowPeriodType;
	}
	public void setWindowPeriodType(FromStaticData windowPeriodType) {
		this.windowPeriodType = windowPeriodType;
	}
	public StatusMaster getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(StatusMaster activeStatus) {
		this.activeStatus = activeStatus;
	}
	public int getTimePointNo() {
		return timePointNo;
	}
	public void setTimePointNo(int timePointNo) {
		this.timePointNo = timePointNo;
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
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	public Double getDosetp() {
		return dosetp;
	}
	public void setDosetp(Double dosetp) {
		this.dosetp = dosetp;
	}
	@Override
	public int compareTo(DoseTimePoints o) {
		 return Double.compare(this.dosetp, o.dosetp);
	}
	
}