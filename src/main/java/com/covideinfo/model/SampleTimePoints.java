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

/*@SuppressWarnings("serial")*/
@Entity
@Table(name = "study_sample_time_points")
public class SampleTimePoints  implements Serializable, Comparable<SampleTimePoints>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2451398185189312131L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_sample_time_points_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "sampleTimePointId")
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
	private FromStaticData timePointType ;
	private String sign = "";
	private String windowPeriodSign = "";
	private int windowPeriod;
	@ManyToOne
	@JoinColumn(name = "windowPeriodType")
	private FromStaticData windowPeriodType;
	@Column(name="vacutainerNo")
	private int vacutainerNo;
	private int noOfVacutainer;
	private int timePointNo;
	@Column(name="vial_count")
	private int noOfVials;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	@Column(name="update_reason")
	private String updateReason;
	@Transient
	private Double stp;
	@Column(name="differenceBetweenSubjects")
	private int differenceBetweenSubjects;
	@Transient
	private String tpWithScheduleTime;
	
	
	//time point converted in to minutes
	@Transient
	private int timeDesimal;

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

	public int getVacutainerNo() {
		return vacutainerNo;
	}

	public void setVacutainerNo(int vacutainerNo) {
		this.vacutainerNo = vacutainerNo;
	}

	public int getNoOfVacutainer() {
		return noOfVacutainer;
	}

	public void setNoOfVacutainer(int noOfVacutainer) {
		this.noOfVacutainer = noOfVacutainer;
	}

	public int getTimePointNo() {
		return timePointNo;
	}

	public void setTimePointNo(int timePointNo) {
		this.timePointNo = timePointNo;
	}

	public int getNoOfVials() {
		return noOfVials;
	}

	public void setNoOfVials(int noOfVials) {
		this.noOfVials = noOfVials;
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

	public Double getStp() {
		return stp;
	}

	public void setStp(Double stp) {
		this.stp = stp;
	}

	public int getDifferenceBetweenSubjects() {
		return differenceBetweenSubjects;
	}

	public void setDifferenceBetweenSubjects(int differenceBetweenSubjects) {
		this.differenceBetweenSubjects = differenceBetweenSubjects;
	}

	public int getTimeDesimal() {
		return timeDesimal;
	}

	public void setTimeDesimal(int timeDesimal) {
		this.timeDesimal = timeDesimal;
	}

	@Override
	public int compareTo(SampleTimePoints o) {
		// TODO Auto-generated method stub
		return this.getTimePointNo()-o.getTimePointNo();
	}

	public String getTpWithScheduleTime() {
		return tpWithScheduleTime;
	}

	public void setTpWithScheduleTime(String tpWithScheduleTime) {
		this.tpWithScheduleTime = tpWithScheduleTime;
	}
	
	
	

	
}
