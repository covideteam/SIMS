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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="groupDesignActivityDetails")
public class GroupDesignActivityDetails implements Serializable {
	
	private static final long serialVersionUID = 763643707584536827L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="groupDesignActivityDetails_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="groupDesignId")
	private GroupDesignActivity groupDesignId;
	@ManyToOne
	@JoinColumn(name="parameterId")
	private GlobalParameter parameterId;
	@ManyToOne
	@JoinColumn(name="activityId")
	private GlobalActivity activityId;
	@Column(name="rowNumber")
	private Integer rowNumber;
	@Column(name="columNumber")
	private Integer columNumber;
	
	@Column(name="createdBy")
	private String createdBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdOn = new Date();
	@Column(name="updatedBy")
	private String updatedBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatedOn;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public GroupDesignActivity getGroupDesignId() {
		return groupDesignId;
	}
	public void setGroupDesignId(GroupDesignActivity groupDesignId) {
		this.groupDesignId = groupDesignId;
	}
	public GlobalParameter getParameterId() {
		return parameterId;
	}
	public void setParameterId(GlobalParameter parameterId) {
		this.parameterId = parameterId;
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
	public Integer getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}
	public Integer getColumNumber() {
		return columNumber;
	}
	public void setColumNumber(Integer columNumber) {
		this.columNumber = columNumber;
	}
	public GlobalActivity getActivityId() {
		return activityId;
	}
	public void setActivityId(GlobalActivity activityId) {
		this.activityId = activityId;
	}
	
	
	

}
