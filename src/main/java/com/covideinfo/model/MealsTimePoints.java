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
@Table(name = "study_meals_time_points")
public class MealsTimePoints implements Serializable, Comparable<MealsTimePoints>{
	private static final long serialVersionUID = -7640334662529864179L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_meals_time_points_id_seq", allocationSize = 1)
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
	private FromStaticData timePointType;
	private String sign = "";
	private String windowPeriodSign = "";
	private int windowPeriod;
	@ManyToOne
	@JoinColumn(name = "mealsType")
	private FromStaticData mealsType;
	@ManyToOne
	@JoinColumn(name = "completion")
	private FromStaticData completion;
	private int completionTime;
	private int timePointNo;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	@Transient
	private Double mealsTp;
	
	
	@Transient
	private int timeDesimal;
	
	public int getTimeDesimal() {
		return timeDesimal;
	}

	public void setTimeDesimal(int timeDesimal) {
		this.timeDesimal = timeDesimal;
	}

	public int getTimePointNo() {
		return timePointNo;
	}

	public void setTimePointNo(int timePointNo) {
		this.timePointNo = timePointNo;
	}

	public FromStaticData getCompletion() {
		return completion;
	}

	public void setCompletion(FromStaticData completion) {
		this.completion = completion;
	}

	public int getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(int completionTime) {
		this.completionTime = completionTime;
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

	public FromStaticData getMealsType() {
		return mealsType;
	}

	public void setMealsType(FromStaticData mealsType) {
		this.mealsType = mealsType;
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

	@Override
	public int compareTo(MealsTimePoints o) {
		return this.getTimePointNo()-o.getTimePointNo();
	}

	public Double getMealsTp() {
		return mealsTp;
	}

	public void setMealsTp(Double mealsTp) {
		this.mealsTp = mealsTp;
	}

}
