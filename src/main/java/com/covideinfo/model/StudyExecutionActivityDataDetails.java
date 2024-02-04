package com.covideinfo.model;

import java.io.Serializable;
import java.sql.Blob;
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
@Table(name="study_activity_data_execution")
public class StudyExecutionActivityDataDetails implements Serializable {

	
	private static final long serialVersionUID = 2962113763028770211L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_execution_activity_data_details_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="sa_data")
	private StudyActivityData saData;
	@ManyToOne
	@JoinColumn(name="saparameter")
	private StudyActivityParameters saParameter;
	@ManyToOne
	@JoinColumn(name="global_values")
	private GlobalValues globalValues;
	@Column(name="value")
	private String value="";
	@Column(name="file")
	private Blob file;
	@Column(name="deviation")
	private boolean deviation=false;
	@Column(name="comments")
	private String comments;
	@Column(name="prefered_by")
	private String preferedBy;
	@Column(name="prefered_on")
	private Date preferedOn;
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
	public StudyActivityData getSaData() {
		return saData;
	}
	public void setSaData(StudyActivityData saData) {
		this.saData = saData;
	}
	public StudyActivityParameters getSaParameter() {
		return saParameter;
	}
	public void setSaParameter(StudyActivityParameters saParameter) {
		this.saParameter = saParameter;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isDeviation() {
		return deviation;
	}
	public void setDeviation(boolean deviation) {
		this.deviation = deviation;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
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
	public GlobalValues getGlobalValues() {
		return globalValues;
	}
	public void setGlobalValues(GlobalValues globalValues) {
		this.globalValues = globalValues;
	}
	public String getPreferedBy() {
		return preferedBy;
	}
	public Date getPreferedOn() {
		return preferedOn;
	}
	public void setPreferedBy(String preferedBy) {
		this.preferedBy = preferedBy;
	}
	public void setPreferedOn(Date preferedOn) {
		this.preferedOn = preferedOn;
	}
	public Blob getFile() {
		return file;
	}
	public void setFile(Blob file) {
		this.file = file;
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
