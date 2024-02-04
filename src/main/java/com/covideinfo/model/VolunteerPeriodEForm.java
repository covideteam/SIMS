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

import com.covideinfo.eform.model.PeriodEForm;

@Entity
@Table(name="epk_VOLUNTEER_PERIOD_Eform")
public class VolunteerPeriodEForm extends CommonMaster{
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="VOLUNTEER_PERIOD_Eform_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="volunteerPeriodEFormId")
	private long id;
	
	@ManyToOne
	@JoinColumn
	private Volunteer vol;
	@ManyToOne
	@JoinColumn
	private StudyPeriodMaster period;
	@ManyToOne
	@JoinColumn
	private PeriodEForm stdEFrom;
	
	private long studyId;
	
	private String eformStatus = "NOT STARTED";  
	//'' or NOT STARTED, IN PROGRESS, IN REVIEW , COMPLETED
	//FOR DICREPENCY'S ---- OPEN , ON HOLD , CLOSED
	private String exiteform = "No"; // Yes
	@Transient
	private boolean reviewFlag = false;
	
	
	private String reviews = "0";
	private String reviewUser1;
	private Date reviewOn1;
	private String reviewComment1;
	private String reviewUser2;
	private Date reviewOn2;
	private String reviewComment2;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Volunteer getVol() {
		return vol;
	}
	public void setVol(Volunteer vol) {
		this.vol = vol;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public PeriodEForm getStdEFrom() {
		return stdEFrom;
	}
	public void setStdEFrom(PeriodEForm stdEFrom) {
		this.stdEFrom = stdEFrom;
	}
	public long getStudyId() {
		return studyId;
	}
	public void setStudyId(long studyId) {
		this.studyId = studyId;
	}
	public String geteformStatus() {
		return eformStatus;
	}
	public void seteformStatus(String eformStatus) {
		this.eformStatus = eformStatus;
	}
	public String getExiteform() {
		return exiteform;
	}
	public void setExiteform(String exiteform) {
		this.exiteform = exiteform;
	}
	public boolean isReviewFlag() {
		return reviewFlag;
	}
	public void setReviewFlag(boolean reviewFlag) {
		this.reviewFlag = reviewFlag;
	}
	public String getReviews() {
		return reviews;
	}
	public void setReviews(String reviews) {
		this.reviews = reviews;
	}
	public String getReviewUser1() {
		return reviewUser1;
	}
	public void setReviewUser1(String reviewUser1) {
		this.reviewUser1 = reviewUser1;
	}
	public Date getReviewOn1() {
		return reviewOn1;
	}
	public void setReviewOn1(Date reviewOn1) {
		this.reviewOn1 = reviewOn1;
	}
	public String getReviewComment1() {
		return reviewComment1;
	}
	public void setReviewComment1(String reviewComment1) {
		this.reviewComment1 = reviewComment1;
	}
	public String getReviewUser2() {
		return reviewUser2;
	}
	public void setReviewUser2(String reviewUser2) {
		this.reviewUser2 = reviewUser2;
	}
	public Date getReviewOn2() {
		return reviewOn2;
	}
	public void setReviewOn2(Date reviewOn2) {
		this.reviewOn2 = reviewOn2;
	}
	public String getReviewComment2() {
		return reviewComment2;
	}
	public void setReviewComment2(String reviewComment2) {
		this.reviewComment2 = reviewComment2;
	}


}
