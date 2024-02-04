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
@Table(name="study_groups")
public class StudyGroups implements Serializable {
	
	private static final long serialVersionUID = -148062389524429980L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_groups_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="studyid")
	private StudyMaster study;
	private int noofSubjects;
	private int noofStandbys;
	private String status="Active"; //Active ,In Active
	
	@Column(name="createdBy")
	private String createdBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date updatedOn;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date groupDate;
	@Column(name="update_reason")
	public String updateReason;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public int getNoofSubjects() {
		return noofSubjects;
	}
	public void setNoofSubjects(int noofSubjects) {
		this.noofSubjects = noofSubjects;
	}
	public int getNoofStandbys() {
		return noofStandbys;
	}
	public void setNoofStandbys(int noofStandbys) {
		this.noofStandbys = noofStandbys;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Date getGroupDate() {
		return groupDate;
	}
	public void setGroupDate(Date groupDate) {
		this.groupDate = groupDate;
	}
	
	
	
	
	

}
