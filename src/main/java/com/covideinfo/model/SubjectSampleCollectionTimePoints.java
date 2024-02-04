package com.covideinfo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "epk_Subject_Sample_Collection_timepoints")
public class SubjectSampleCollectionTimePoints extends CommonMaster {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8541711263455991390L;

	@Id
	@Column(name = "subjectSampleCollectionTimePointId")
	private String subjectSampleCollectionTimePointId;

	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "periodId")
	private StudyPeriodMaster period;
	@ManyToOne
	@JoinColumn(name = "treatmentInfoId")
	private TreatmentInfo treatmentInfo;
	@ManyToOne
	@JoinColumn(name = "sampleTimePointId")
	private SampleTimePoints sampleTimePointId;
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "volunteerId")
	@Transient
	private Volunteer volunteer;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subjectSampleCollectionTimePointsDataID")
	private SubjectSampleCollectionTimePointsData subjectSampleCollectionTimePointsData;
	private String sign = "";
	private String timePoint = "";
	@ManyToOne
	@JoinColumn(name = "timePointType")
	private FromStaticData timePointType ;
	private int timePointNo;
	private String subjectNo;
	private int subjectOrder;
	private String windowPeriodSign = "";
	@ManyToOne
	@JoinColumn(name = "windowPeriodType")
	private FromStaticData windowPeriodType;
	private int windowPeriod;
	private int vacutainer;
	private int batchNo;
	private String scheduleTime;
	private String scheduleDate;
	
	public String getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public int getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}
	public String getSubjectSampleCollectionTimePointId() {
		return subjectSampleCollectionTimePointId;
	}
	public void setSubjectSampleCollectionTimePointId(String subjectSampleCollectionTimePointId) {
		this.subjectSampleCollectionTimePointId = subjectSampleCollectionTimePointId;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public TreatmentInfo getTreatmentInfo() {
		return treatmentInfo;
	}
	public void setTreatmentInfo(TreatmentInfo treatmentInfo) {
		this.treatmentInfo = treatmentInfo;
	}
	public SampleTimePoints getSampleTimePointId() {
		return sampleTimePointId;
	}
	public void setSampleTimePointId(SampleTimePoints sampleTimePointId) {
		this.sampleTimePointId = sampleTimePointId;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
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
	public int getTimePointNo() {
		return timePointNo;
	}
	public void setTimePointNo(int timePointNo) {
		this.timePointNo = timePointNo;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public int getSubjectOrder() {
		return subjectOrder;
	}
	public void setSubjectOrder(int subjectOrder) {
		this.subjectOrder = subjectOrder;
	}
	public String getWindowPeriodSign() {
		return windowPeriodSign;
	}
	public void setWindowPeriodSign(String windowPeriodSign) {
		this.windowPeriodSign = windowPeriodSign;
	}
	
	public FromStaticData getWindowPeriodType() {
		return windowPeriodType;
	}
	public void setWindowPeriodType(FromStaticData windowPeriodType) {
		this.windowPeriodType = windowPeriodType;
	}
	public int getWindowPeriod() {
		return windowPeriod;
	}
	public void setWindowPeriod(int windowPeriod) {
		this.windowPeriod = windowPeriod;
	}
	public int getVacutainer() {
		return vacutainer;
	}
	public void setVacutainer(int vacutainer) {
		this.vacutainer = vacutainer;
	}
	public Volunteer getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}
	public SubjectSampleCollectionTimePointsData getSubjectSampleCollectionTimePointsData() {
		return subjectSampleCollectionTimePointsData;
	}
	public void setSubjectSampleCollectionTimePointsData(
			SubjectSampleCollectionTimePointsData subjectSampleCollectionTimePointsData) {
		this.subjectSampleCollectionTimePointsData = subjectSampleCollectionTimePointsData;
	}
	
}
