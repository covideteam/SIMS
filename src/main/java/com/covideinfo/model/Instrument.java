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
@Table(name="Instrument")
public class Instrument implements Serializable{


	
	private static final long serialVersionUID = 2286705329964124726L;

	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="Instrument_Data_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="instrumentModel")
	private InstrumentModel instrumentModel;
	@ManyToOne
	@JoinColumn(name="instrumentType")
	private InstrumentType instrumentType;
	@Column(name="instrumentNo")
	private String instrumentNo;
	@Column(name="calibrationFrequency")
	private String calibrationFrequency;
	@Column(name="manufacturingDate")
	private Date manufacturingDate;
	@Column(name="calibrationDate")
	private Date calibrationDate;
	
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
	public InstrumentModel getInstrumentModel() {
		return instrumentModel;
	}
	public void setInstrumentModel(InstrumentModel instrumentModel) {
		this.instrumentModel = instrumentModel;
	}
	public InstrumentType getInstrumentType() {
		return instrumentType;
	}
	public void setInstrumentType(InstrumentType instrumentType) {
		this.instrumentType = instrumentType;
	}
	public String getInstrumentNo() {
		return instrumentNo;
	}
	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}
	public String getCalibrationFrequency() {
		return calibrationFrequency;
	}
	public void setCalibrationFrequency(String calibrationFrequency) {
		this.calibrationFrequency = calibrationFrequency;
	}
	public Date getManufacturingDate() {
		return manufacturingDate;
	}
	public void setManufacturingDate(Date manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}
	public Date getCalibrationDate() {
		return calibrationDate;
	}
	public void setCalibrationDate(Date calibrationDate) {
		this.calibrationDate = calibrationDate;
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
	
		

}
