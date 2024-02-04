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
@Table(name="custom_activity_parameters")
public class CustomActivityParameters implements Serializable {
	
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="custom_activity_parameters_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="activity")
	private GlobalActivity activity;
	@ManyToOne
	@JoinColumn(name="parameter")
	private GlobalParameter parameter;
	@Column(name="parameter_value")
	private String parameterValue;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	public long getId() {
		return id;
	}
	public GlobalActivity getActivity() {
		return activity;
	}
	public GlobalParameter getParameter() {
		return parameter;
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
	public void setActivity(GlobalActivity activity) {
		this.activity = activity;
	}
	public void setParameter(GlobalParameter parameter) {
		this.parameter = parameter;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	

}
