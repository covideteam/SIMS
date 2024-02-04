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
@Table(name = "Subject_Additional_Assessment_timepoints")
public class SubjectAdditionalAssessmentTimePoints implements Serializable {
	
	
	
	private static final long serialVersionUID = -8335821572070731565L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "Subject_Additional_Assessment_timepoints_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "subjectAdditionalAssessmentTimePointsId")
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
	@JoinColumn(name = "additionalAssesmentId")
	private AdditionalAssesment additionalAssesmentId;

	private String timePoint = "";
	private String timePointType = "";
	private String sign = "";
	private String deviationSign = "";
	private int deviation;
	private String deviationType = "";
	private String subjectNo;
	private int subjectOrder;
	private int timePointNo;
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
	public AdditionalAssesment getAdditionalAssesmentId() {
		return additionalAssesmentId;
	}
	public void setAdditionalAssesmentId(AdditionalAssesment additionalAssesmentId) {
		this.additionalAssesmentId = additionalAssesmentId;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public String getTimePointType() {
		return timePointType;
	}
	public void setTimePointType(String timePointType) {
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
	public int getDeviation() {
		return deviation;
	}
	public void setDeviation(int deviation) {
		this.deviation = deviation;
	}
	public String getDeviationType() {
		return deviationType;
	}
	public void setDeviationType(String deviationType) {
		this.deviationType = deviationType;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
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
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	

	}
