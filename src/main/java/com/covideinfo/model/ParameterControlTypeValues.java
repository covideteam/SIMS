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

/*@SuppressWarnings("serial")*/
@Entity
@Table(name="parameter_control_type_values")
public class ParameterControlTypeValues implements Serializable {

	
	private static final long serialVersionUID = -4375698175571761247L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="parameter_control_type_values_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="parameter_id")
	private GlobalParameter globalParameter;
	@ManyToOne
	@JoinColumn(name="control_type")
	private ControlType controlType;
	@ManyToOne
	@JoinColumn(name="global_values")
	private GlobalValues globalValues;
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
	public void setId(long id) {
		this.id = id;
	}
	public GlobalParameter getGlobalParameter() {
		return globalParameter;
	}
	public void setGlobalParameter(GlobalParameter globalParameter) {
		this.globalParameter = globalParameter;
	}
	public ControlType getControlType() {
		return controlType;
	}
	public void setControlType(ControlType controlType) {
		this.controlType = controlType;
	}
	public GlobalValues getGlobalValues() {
		return globalValues;
	}
	public void setGlobalValues(GlobalValues globalValues) {
		this.globalValues = globalValues;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
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
