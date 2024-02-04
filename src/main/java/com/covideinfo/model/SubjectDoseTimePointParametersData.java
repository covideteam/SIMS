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

@Entity(name="subject_dose_time_point_parameters_data")
public class SubjectDoseTimePointParametersData implements Serializable {
	
	private static final long serialVersionUID = -1249766935710718040L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="subject_dose_time_point_parameters_data_id_seq", allocationSize=1)
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
	private SubjectDoseTimePoints subjectDoseTimePoint;
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
	public void setId(Long id) {
		this.id = id;
	}
	public GlobalParameter getParameter() {
		return parameter;
	}
	public void setParameter(GlobalParameter parameter) {
		this.parameter = parameter;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public GlobalValues getGlobalValue() {
		return globalValue;
	}
	public void setGlobalValue(GlobalValues globalValue) {
		this.globalValue = globalValue;
	}
	public SubjectDoseTimePoints getSubjectDoseTimePoint() {
		return subjectDoseTimePoint;
	}
	public void setSubjectDoseTimePoint(SubjectDoseTimePoints subjectDoseTimePoint) {
		this.subjectDoseTimePoint = subjectDoseTimePoint;
	}
	public UserMaster getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserMaster createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public UserMaster getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(UserMaster updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public Long getStudyId() {
		return studyId;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	
	
	
	
	
	
	
	
	
	
	

}
