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
@Table(name = "subject_Meals_time_points")
public class SubjectMealsTimePoints implements Serializable {


	private static final long serialVersionUID = 8177846081204021297L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "subject_Meals_time_points_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "subjectMealsTimePointsId")
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
	@JoinColumn(name = "mealsTimePointsId")
	private MealsTimePoints mealsTimePointsId;
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
	private String windowPeriodSign = "";
	@ManyToOne
	@JoinColumn(name = "windowPeriodType")
	private FromStaticData windowPeriodType;
	private int windowPeriod;
	@ManyToOne
	@JoinColumn(name = "mealsType")
	private FromStaticData mealsType;
	private String subjectNo;
	private int subjectOrder;
	private int timePointNo;
	private boolean timePointCollectionStatus;
	@ManyToOne
	@JoinColumn(name = "subjectMealsTimePointsData")
	private SubjectMealsTimePointsData subjectMealsTimePointsData;
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
	@Transient
	private String meal = "";
	
	public String getMeal() {
		return meal;
	}
	public void setMeal(String meal) {
		this.meal = meal;
	}
	public boolean isTimePointCollectionStatus() {
		return timePointCollectionStatus;
	}
	public void setTimePointCollectionStatus(boolean timePointCollectionStatus) {
		this.timePointCollectionStatus = timePointCollectionStatus;
	}
	public SubjectMealsTimePointsData getSubjectMealsTimePointsData() {
		return subjectMealsTimePointsData;
	}
	public void setSubjectMealsTimePointsData(SubjectMealsTimePointsData subjectMealsTimePointsData) {
		this.subjectMealsTimePointsData = subjectMealsTimePointsData;
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
	public MealsTimePoints getMealsTimePointsId() {
		return mealsTimePointsId;
	}
	public void setMealsTimePointsId(MealsTimePoints mealsTimePointsId) {
		this.mealsTimePointsId = mealsTimePointsId;
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
	public FromStaticData getMealsType() {
		return mealsType;
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
	public void setMealsType(FromStaticData mealsType) {
		this.mealsType = mealsType;
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
	public Volunteer getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}
	public FromStaticData getWindowPeriodType() {
		return windowPeriodType;
	}
	public void setWindowPeriodType(FromStaticData windowPeriodType) {
		this.windowPeriodType = windowPeriodType;
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
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	
}
