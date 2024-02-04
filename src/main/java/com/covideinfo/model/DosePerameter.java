package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*@SuppressWarnings("serial")*/
@Entity
@Table(name = "study_dose_perameters")
public class DosePerameter implements Serializable{

	
	private static final long serialVersionUID = -8073155889339412382L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_dose_perameters_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "dosePerameterId")
	private long id;
	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "treatmentId")
	private TreatmentInfo treatment;
	@ManyToOne
	@JoinColumn(name = "parameter")
	private GlobalParameter parameter;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dosePerameterStatus")
	private StatusMaster dosePerameterStatus;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	public long getId() {
		return id;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public TreatmentInfo getTreatment() {
		return treatment;
	}
	public GlobalParameter getParameter() {
		return parameter;
	}
	public StatusMaster getDosePerameterStatus() {
		return dosePerameterStatus;
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
	public void setId(long id) {
		this.id = id;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public void setTreatment(TreatmentInfo treatment) {
		this.treatment = treatment;
	}
	public void setParameter(GlobalParameter parameter) {
		this.parameter = parameter;
	}
	public void setDosePerameterStatus(StatusMaster dosePerameterStatus) {
		this.dosePerameterStatus = dosePerameterStatus;
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
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	
	

}
