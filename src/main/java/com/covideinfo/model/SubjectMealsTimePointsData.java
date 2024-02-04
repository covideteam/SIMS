package com.covideinfo.model;

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
@Table(name = "epk_subject_meals_time_points_data")
public class SubjectMealsTimePointsData extends CommonMaster {

	private static final long serialVersionUID = 2113283036229889309L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_subject_meals_time_points_data_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "subjectMealsTimePointsDataId")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "studyPeriodMasterId")
	private StudyPeriodMaster studyPeriodMaster;
	@ManyToOne
	@JoinColumn(name = "mealsTimePointId")
	private MealsTimePoints mealsTimePoint;
	@ManyToOne
	@JoinColumn(name = "subjectId")
	private StudySubjects subject;
	
	private String deviation;
	private Date scheduleTime;
	private Date actualtime;
	private Date startTime;
	@Transient
	private String startTimeOnly;
	private Date endTime;
	@Transient
	private String endTimeOnly;
	@Column(name="consumption")
	private String consumption;
	private String comments = "";
	@ManyToOne
	@JoinColumn(name = "startedBy")
	private UserMaster startedBy;
	private Date startedOn;
	@ManyToOne
	@JoinColumn(name = "endBy")
	private UserMaster endBy;
	private Date endON;
	public Long getId() {
		return id;
	}
	public StudyPeriodMaster getStudyPeriodMaster() {
		return studyPeriodMaster;
	}
	public MealsTimePoints getMealsTimePoint() {
		return mealsTimePoint;
	}
	public StudySubjects getSubject() {
		return subject;
	}
	public String getDeviation() {
		return deviation;
	}
	public Date getScheduleTime() {
		return scheduleTime;
	}
	public Date getActualtime() {
		return actualtime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public String getStartTimeOnly() {
		return startTimeOnly;
	}
	public Date getEndTime() {
		return endTime;
	}
	public String getEndTimeOnly() {
		return endTimeOnly;
	}
	public String getComments() {
		return comments;
	}
	public UserMaster getStartedBy() {
		return startedBy;
	}
	public Date getStartedOn() {
		return startedOn;
	}
	public UserMaster getEndBy() {
		return endBy;
	}
	public Date getEndON() {
		return endON;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setStudyPeriodMaster(StudyPeriodMaster studyPeriodMaster) {
		this.studyPeriodMaster = studyPeriodMaster;
	}
	public void setMealsTimePoint(MealsTimePoints mealsTimePoint) {
		this.mealsTimePoint = mealsTimePoint;
	}
	public void setSubject(StudySubjects subject) {
		this.subject = subject;
	}
	public void setDeviation(String deviation) {
		this.deviation = deviation;
	}
	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public void setActualtime(Date actualtime) {
		this.actualtime = actualtime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setStartTimeOnly(String startTimeOnly) {
		this.startTimeOnly = startTimeOnly;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setEndTimeOnly(String endTimeOnly) {
		this.endTimeOnly = endTimeOnly;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setStartedBy(UserMaster startedBy) {
		this.startedBy = startedBy;
	}
	public void setStartedOn(Date startedOn) {
		this.startedOn = startedOn;
	}
	public void setEndBy(UserMaster endBy) {
		this.endBy = endBy;
	}
	public void setEndON(Date endON) {
		this.endON = endON;
	}
	public String getConsumption() {
		return consumption;
	}
	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}
	
	
	
	
	
	
}
