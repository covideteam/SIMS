package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyActivityParameters;

@SuppressWarnings("serial")
public class StudyActivityDataDetailsDto implements Serializable {
	
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
	@Column(name="deviation")
	private boolean deviation=false;
	@Column(name="comments")
	private String comments;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="globalName")
	private String globalName;
	@Column(name="paramName")
	private String paramName;
	@Column(name="controleParametrData")
	private String controleParametrData;
	
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
	public String getGlobalName() {
		return globalName;
	}
	public void setGlobalName(String globalName) {
		this.globalName = globalName;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getControleParametrData() {
		return controleParametrData;
	}
	public void setControleParametrData(String controleParametrData) {
		this.controleParametrData = controleParametrData;
	}
	
	
	

}
