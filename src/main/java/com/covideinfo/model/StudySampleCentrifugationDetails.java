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
@Table(name="study_Sample_centrifugation_details")
public class StudySampleCentrifugationDetails implements Serializable {
	
	
	
	private static final long serialVersionUID = 5386877860949254994L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_sample_centrifugation_details_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="applicableto")
	private String applicableTo;
	@Column(name="centrifugationSpeed")
	private int centrifugationSpeed;
	@Column(name="centrifugation_processtime")
	private int centrifugationProcessTime;
	@Column(name="centrifugation_temparature")
	private int centrifugationTemparature;
	@Column(name="centrifugation_allowedtime")
	private int centrifugationAllowedTime;
	@Column(name="conditions")
	private String conditions;
	@ManyToOne
	@JoinColumn(name="centrifugation")
	private StudySampleCentrifugation centrifugation;
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
	public long getId() {
		return id;
	}
	public String getApplicableTo() {
		return applicableTo;
	}
	public int getCentrifugationSpeed() {
		return centrifugationSpeed;
	}
	public int getCentrifugationProcessTime() {
		return centrifugationProcessTime;
	}
	public int getCentrifugationTemparature() {
		return centrifugationTemparature;
	}
	public int getCentrifugationAllowedTime() {
		return centrifugationAllowedTime;
	}
	public String getConditions() {
		return conditions;
	}
	public StudySampleCentrifugation getCentrifugation() {
		return centrifugation;
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
	public void setApplicableTo(String applicableTo) {
		this.applicableTo = applicableTo;
	}
	public void setCentrifugationSpeed(int centrifugationSpeed) {
		this.centrifugationSpeed = centrifugationSpeed;
	}
	public void setCentrifugationProcessTime(int centrifugationProcessTime) {
		this.centrifugationProcessTime = centrifugationProcessTime;
	}
	public void setCentrifugationTemparature(int centrifugationTemparature) {
		this.centrifugationTemparature = centrifugationTemparature;
	}
	public void setCentrifugationAllowedTime(int centrifugationAllowedTime) {
		this.centrifugationAllowedTime = centrifugationAllowedTime;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public void setCentrifugation(StudySampleCentrifugation centrifugation) {
		this.centrifugation = centrifugation;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	
	
	

}
