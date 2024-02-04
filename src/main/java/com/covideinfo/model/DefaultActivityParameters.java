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
@Table(name="default_activity_parameters")
public class DefaultActivityParameters implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5184848637547341476L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="default_activity_parameters_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="defalult_act_id")
	private DefaultActivitys defalutActId;
	@ManyToOne
	@JoinColumn(name="parameter")
	private GlobalParameter parameter;
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
	public DefaultActivitys getDefalutActId() {
		return defalutActId;
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
	public void setDefalutActId(DefaultActivitys defalutActId) {
		this.defalutActId = defalutActId;
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
