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
@Table(name="study_sample_centrifugation")
public class StudySampleCentrifugation implements Serializable {
	
	
	private static final long serialVersionUID = 8830249829357034059L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_sample_centrifugation_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="study")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name="treatment")
	private TreatmentInfo treatment;
    @Column(name="centrifugation_applicable")
    private boolean centrifugationApplicable;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="created_on")
    private Date createdOn;
    @Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	@Transient
	public StudySampleCentrifugationDetails studySampleCentrifugationDetails;
	@Transient
	public boolean samplePeparation = false;;
	
	public boolean isSamplePeparation() {
		return samplePeparation;
	}
	public void setSamplePeparation(boolean samplePeparation) {
		this.samplePeparation = samplePeparation;
	}
	public StudySampleCentrifugationDetails getStudySampleCentrifugationDetails() {
		return studySampleCentrifugationDetails;
	}
	public void setStudySampleCentrifugationDetails(StudySampleCentrifugationDetails studySampleCentrifugationDetails) {
		this.studySampleCentrifugationDetails = studySampleCentrifugationDetails;
	}
	public long getId() {
		return id;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public boolean isCentrifugationApplicable() {
		return centrifugationApplicable;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public void setCentrifugationApplicable(boolean centrifugationApplicable) {
		this.centrifugationApplicable = centrifugationApplicable;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public TreatmentInfo getTreatment() {
		return treatment;
	}
	public void setTreatment(TreatmentInfo treatment) {
		this.treatment = treatment;
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
    
    

}
