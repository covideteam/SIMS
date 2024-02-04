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

import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
@Entity
@Table(name="Condition_Parameter")
public class ConditionParameter implements Serializable{
	
	@Id
	@SequenceGenerator(name = "pk_sequence",sequenceName ="Condition_Parameter_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private long id;
	@Column(columnDefinition = "varchar(5000) default ''")
	private String name;
	private String dropDown;
	private String activeAndInactive="Active"; //Active ,Inactive
	
	@Column(name="createdBy")
	private String createdBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getDropDown() {
		return dropDown;
	}
	public void setDropDown(String dropDown) {
		this.dropDown = dropDown;
	}
	public String getActiveAndInactive() {
		return activeAndInactive;
	}
	public void setActiveAndInactive(String activeAndInactive) {
		this.activeAndInactive = activeAndInactive;
	}
	
	
	
	

}
