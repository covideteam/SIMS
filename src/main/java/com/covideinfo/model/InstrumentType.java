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

@Entity
@Table(name="Instrument_Type")
public class InstrumentType implements Serializable {
	
	
	
	private static final long serialVersionUID = -4540533236424059296L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="instrument_Type_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column (name="instrumentType" ,unique = true, nullable = false)
	private String instrumentType;
	@Column (name="instrumentCode")
	private String instrumentCode;
	
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
	
	public InstrumentType() {
		
	}
	public InstrumentType(String instrumentType, String instrumentCode) {
		this.instrumentCode = instrumentCode;
		this.instrumentType = instrumentType;
	}
	
	public InstrumentType(String instrumentType, String instrumentCode, String createdby, Date createdOn) {
		this.instrumentCode = instrumentCode;
		this.instrumentType = instrumentType;
		this.createdBy = createdby;
		this.createdOn = createdOn;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getInstrumentType() {
		return instrumentType;
	}
	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
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
	public String getInstrumentCode() {
		return instrumentCode;
	}
	public void setInstrumentCode(String instrumentCode) {
		this.instrumentCode = instrumentCode;
	}
	
	
	

}
