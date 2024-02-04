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
@Table(name="static_activity_data_details")
public class StaticActivityDataDetails implements Serializable {
	
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "static_activity_data_details_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private Long id;
	@ManyToOne
	@JoinColumn(name="static_activity_id")
	private StaticActivityDetails staticActDetailsId;
	@ManyToOne
	@JoinColumn(name="static_act_val_id")
	private StaticActivityValueDetails saticActValueDetailsId;
	@ManyToOne
	@JoinColumn(name="swd_id")
	private SubjectWithdrawDetails subjectWithDrawDetails;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="upated_on")
	private Date updatedOn;
	@Column(name="reason")
	private String reason;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public StaticActivityDetails getStaticActDetailsId() {
		return staticActDetailsId;
	}
	public void setStaticActDetailsId(StaticActivityDetails staticActDetailsId) {
		this.staticActDetailsId = staticActDetailsId;
	}
	public StaticActivityValueDetails getSaticActValueDetailsId() {
		return saticActValueDetailsId;
	}
	public void setSaticActValueDetailsId(StaticActivityValueDetails saticActValueDetailsId) {
		this.saticActValueDetailsId = saticActValueDetailsId;
	}
	public SubjectWithdrawDetails getSubjectWithDrawDetails() {
		return subjectWithDrawDetails;
	}
	public void setSubjectWithDrawDetails(SubjectWithdrawDetails subjectWithDrawDetails) {
		this.subjectWithDrawDetails = subjectWithDrawDetails;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	

}
