package com.covideinfo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "subject_vital_time_points")
public class SubjectVitalTimePoints implements Serializable {
	
	

	private static final long serialVersionUID = -8805505893789971278L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "subject_vital_time_points_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "subjectVitalTimePointsId")
	private long id;

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
	@JoinColumn(name = "vitalTimePointsId")
	private VitalTimePoints vitalTimePointsId;
	@ManyToOne
	@JoinColumn(name = "volunteerId")
	private Volunteer volunteer;
	
	private String timePoint = "";
	private String scheduleTime = "";
	private String scheduleDate = "";
	@ManyToOne
	@JoinColumn(name = "timePointType")
	private FromStaticData timePointType;
	private String sign = "";
	private String deviationSign = "";
	private String windowPeriodSign = "";
	private FromStaticData windowPeriodType;
	private int windowPeriod;
	private String subjectNo;
	private int subjectOrder;
	private int timePointNo;
	@ManyToOne
	@JoinColumn(name="subjectVitalTimePointsDataId")
	private SubjectVitalTimePointsData subjectVitalTimePointsData;
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "subjectVitalTimePoints")
	private List<SubjectTimePointVitalTests> test = new ArrayList<SubjectTimePointVitalTests>();
	private boolean orthostatic;
	@ManyToOne
	@JoinColumn(name = "orthostaticPosition")
	private FromStaticData orthostaticPosition;
	@ManyToOne
	@JoinColumn(name = "created_by")
	private UserMaster createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@ManyToOne
	@JoinColumn(name="updated_by")
	private UserMaster updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	
	private boolean collectionStatus;
	@Transient
	private String startTime = "";
	@Transient
	private String endTime = "";
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public boolean isCollectionStatus() {
		return collectionStatus;
	}
	public void setCollectionStatus(boolean collectionStatus) {
		this.collectionStatus = collectionStatus;
	}
	public SubjectVitalTimePointsData getSubjectVitalTimePointsData() {
		return subjectVitalTimePointsData;
	}
	public void setSubjectVitalTimePointsData(SubjectVitalTimePointsData subjectVitalTimePointsData) {
		this.subjectVitalTimePointsData = subjectVitalTimePointsData;
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
	public VitalTimePoints getVitalTimePointsId() {
		return vitalTimePointsId;
	}
	public void setVitalTimePointsId(VitalTimePoints vitalTimePointsId) {
		this.vitalTimePointsId = vitalTimePointsId;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	
	public UserMaster getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public UserMaster getUpdatedBy() {
		return updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setCreatedBy(UserMaster createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public void setUpdatedBy(UserMaster updatedBy) {
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
	public String getDeviationSign() {
		return deviationSign;
	}
	public void setDeviationSign(String deviationSign) {
		this.deviationSign = deviationSign;
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
	public List<SubjectTimePointVitalTests> getTest() {
		return test;
	}
	public void setTest(List<SubjectTimePointVitalTests> test) {
		this.test = test;
	}
	public boolean isOrthostatic() {
		return orthostatic;
	}
	public void setOrthostatic(boolean orthostatic) {
		this.orthostatic = orthostatic;
	}
	public FromStaticData getOrthostaticPosition() {
		return orthostaticPosition;
	}
	public void setOrthostaticPosition(FromStaticData orthostaticPosition) {
		this.orthostaticPosition = orthostaticPosition;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public Volunteer getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}
	
	
}
