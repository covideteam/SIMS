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
@Table(name = "Sample_Storage_Data_rack")
public class SampleStorageDataRack implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4208123810292645822L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "Sample_Storage_Data_rack_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "id")
	private long id;
	@ManyToOne
	@JoinColumn(name = "sampleStorageDataId")
	private SampleStorageData sampleStorageData;
	private RackWithVials rackWithVials;
	private Long studyId;
	private Date scanTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public SampleStorageData getSampleStorageData() {
		return sampleStorageData;
	}
	public void setSampleStorageData(SampleStorageData sampleStorageData) {
		this.sampleStorageData = sampleStorageData;
	}
	public RackWithVials getRackWithVials() {
		return rackWithVials;
	}
	public void setRackWithVials(RackWithVials rackWithVials) {
		this.rackWithVials = rackWithVials;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public Long getStudyId() {
		return studyId;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	
	
}
