package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "static_activity_details")
public class StaticActivityDetails implements Serializable {
	
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="static_activity_details_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private Long id;
	private String queryName;
	@Column(name="activity_type")
	private String activityType;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="created_on")
    private Date createdOn;
    public StaticActivityDetails() {
    	
    }
	public StaticActivityDetails(String queryName, String createdBy, Date createdOn, String activityType) {
		super();
		this.queryName = queryName;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.activityType = activityType;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
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
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
    
    
    
	
	


}
