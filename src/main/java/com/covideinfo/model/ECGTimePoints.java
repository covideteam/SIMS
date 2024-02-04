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
@Table(name = "study_ecg_time_points")
public class ECGTimePoints implements Serializable {

	
	private static final long serialVersionUID = -2864691398116979831L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_ecg_time_points_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "ecgTimePointsId")
	private long id;
	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "treatmentInfoId")
	private TreatmentInfo treatmentInfo;
	private String timePoint = "";
	@ManyToOne
	@JoinColumn(name = "timePointType")
	private FromStaticData timePointType;
	@ManyToOne
	@JoinColumn(name = "ecgPosition")
	private FromStaticData ecgPosition;
	private String sign = "";
	private String windowPeriodSign = "";
	private int windowPeriod;
	@ManyToOne
	@JoinColumn(name = "windowPeriodType")
	private FromStaticData windowPeriodType;
	private int timePointNo;
	@Column(name="parameters")
	private String parameters;
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
	public void setId(long id) {
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
	public FromStaticData getTimePointType() {
		return timePointType;
	}
	public void setTimePointType(FromStaticData timePointType) {
		this.timePointType = timePointType;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
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
	public int getTimePointNo() {
		return timePointNo;
	}
	public void setTimePointNo(int timePointNo) {
		this.timePointNo = timePointNo;
	}
	public FromStaticData getEcgPosition() {
		return ecgPosition;
	}
	public void setEcgPosition(FromStaticData ecgPosition) {
		this.ecgPosition = ecgPosition;
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
	
	
}
