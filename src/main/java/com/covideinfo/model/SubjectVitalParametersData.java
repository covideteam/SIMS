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

@SuppressWarnings("serial")
@Entity
@Table(name="subject_vital_parameters_data")
public class SubjectVitalParametersData implements Serializable{
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="subject_vital_parameters_data_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private Long id;
	@ManyToOne
	@JoinColumn(name="parameter")
	private GlobalParameter parameter;
	@Column(name="parameter_value")
	private String parameterValue;
	@ManyToOne
	@JoinColumn(name="global_value")
	private GlobalValues globalValue;
	@Column(name="study_id")
	private Long studyId;
	@ManyToOne
	@JoinColumn(name="subject_dose_time_point")
	private SubjectVitalTimePointsData subjVitalTpData;
	@ManyToOne
	@JoinColumn(name="created_by")
	private UserMaster createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@ManyToOne
	@JoinColumn(name="updated_by")
	private UserMaster updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	public Long getId() {
		return id;
	}
	public GlobalParameter getParameter() {
		return parameter;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public GlobalValues getGlobalValue() {
		return globalValue;
	}
	public Long getStudyId() {
		return studyId;
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
	public void setId(Long id) {
		this.id = id;
	}
	public void setParameter(GlobalParameter parameter) {
		this.parameter = parameter;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public void setGlobalValue(GlobalValues globalValue) {
		this.globalValue = globalValue;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
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
	public SubjectVitalTimePointsData getSubjVitalTpData() {
		return subjVitalTpData;
	}
	public void setSubjVitalTpData(SubjectVitalTimePointsData subjVitalTpData) {
		this.subjVitalTpData = subjVitalTpData;
	}
	
	
		
}
