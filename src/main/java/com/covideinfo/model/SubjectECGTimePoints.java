package com.covideinfo.model;

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
@Table(name = "epk_subject_ecg_time_points")
public class SubjectECGTimePoints extends CommonMaster {

	private static final long serialVersionUID = 3137449981213660912L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_subject_ecg_time_points_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "subjectECGTimePointsId")
	private long id;

	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "volunteerId")
	private Volunteer volunteer;
	@ManyToOne
	@JoinColumn(name = "periodId")
	private StudyPeriodMaster period;
	@ManyToOne
	@JoinColumn(name = "treatmentInfoId")
	private TreatmentInfo treatmentInfo;
	@ManyToOne
	@JoinColumn(name = "ecgTimePointsId")
	private ECGTimePoints ecgTimePointsId;
	@ManyToOne
	@JoinColumn(name = "subjectECGTimePointsDataId")
	private SubjectECGTimePointsData subjectECGTimePointsData;
	private String timePoint = "";
	@ManyToOne
	@JoinColumn(name="timePointType")
	private FromStaticData timePointType;
	private String sign = "";
	private String windowPeriodSign = "";
	@ManyToOne
	@JoinColumn(name = "windowPeriodType")
	private FromStaticData windowPeriodType;
	private int windowPeriod;
	private String subjectNo;
	private int subjectOrder;
	private int timePointNo;
	private String scheduleTime = "";
	private String scheduleDate = "";
	private boolean collectionStatus;

	public boolean isCollectionStatus() {
		return collectionStatus;
	}

	public void setCollectionStatus(boolean collectionStatus) {
		this.collectionStatus = collectionStatus;
	}

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

	public SubjectECGTimePointsData getSubjectECGTimePointsData() {
		return subjectECGTimePointsData;
	}

	public void setSubjectECGTimePointsData(SubjectECGTimePointsData subjectECGTimePointsData) {
		this.subjectECGTimePointsData = subjectECGTimePointsData;
	}

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

	public Volunteer getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
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

	public ECGTimePoints getEcgTimePointsId() {
		return ecgTimePointsId;
	}

	public void setEcgTimePointsId(ECGTimePoints ecgTimePointsId) {
		this.ecgTimePointsId = ecgTimePointsId;
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

	public int getTimePointNo() {
		return timePointNo;
	}

	public void setTimePointNo(int timePointNo) {
		this.timePointNo = timePointNo;
	}

}
