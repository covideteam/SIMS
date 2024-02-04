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


@Entity
@Table(name="dosing_info_details")
public class DosingInfoDetails implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4557197417024741091L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "dosing_info_details_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private Long id;
	@Column(name="no_of_units")
	private String noOfUnits;
	@Column(name="name_of_ip")
	private String nameOfIp;
	@Column(name="batch_no")
	private String batchNo;
	@Column(name="exp_date")
	private Date expDate;
	@ManyToOne
	@JoinColumn(name="treatment")
	private TreatmentInfo tinfo;
	@ManyToOne
	@JoinColumn(name="study")
	private StudyMaster study;
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
	@Column(name="update_reason")
	private String updateReason;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNoOfUnits() {
		return noOfUnits;
	}
	public void setNoOfUnits(String noOfUnits) {
		this.noOfUnits = noOfUnits;
	}
	public String getNameOfIp() {
		return nameOfIp;
	}
	public void setNameOfIp(String nameOfIp) {
		this.nameOfIp = nameOfIp;
	}
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
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
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public TreatmentInfo getTinfo() {
		return tinfo;
	}
	public void setTinfo(TreatmentInfo tinfo) {
		this.tinfo = tinfo;
	}
	
	
	
	
	

}
