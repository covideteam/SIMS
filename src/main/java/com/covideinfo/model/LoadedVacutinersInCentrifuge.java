package com.covideinfo.model;

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
@Table(name = "epk_LoadedVacutinersInCentrifuge")
public class LoadedVacutinersInCentrifuge extends CommonMaster{

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_LoadedVacutinersInCentrifuge_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "loadedVacutinersInCentrifugeId")
	private long id;
	@ManyToOne
	@JoinColumn(name = "centrifugationDataMasterId")
	private CentrifugationDataMaster centrifugationDataMaster;
	@ManyToOne
	@JoinColumn(name = "subjectSampleCollectionTimePointsDataId")
	private SubjectSampleCollectionTimePointsData subjectSampleCollectionTimePointsData;
	private Date scanTime;
	@ManyToOne
	@JoinColumn(name= "scan_by")
	private UserMaster scanBy;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public CentrifugationDataMaster getCentrifugationDataMaster() {
		return centrifugationDataMaster;
	}
	public void setCentrifugationDataMaster(CentrifugationDataMaster centrifugationDataMaster) {
		this.centrifugationDataMaster = centrifugationDataMaster;
	}
	public SubjectSampleCollectionTimePointsData getSubjectSampleCollectionTimePointsData() {
		return subjectSampleCollectionTimePointsData;
	}
	public void setSubjectSampleCollectionTimePointsData(
			SubjectSampleCollectionTimePointsData subjectSampleCollectionTimePointsData) {
		this.subjectSampleCollectionTimePointsData = subjectSampleCollectionTimePointsData;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public UserMaster getScanBy() {
		return scanBy;
	}
	public void setScanBy(UserMaster scanBy) {
		this.scanBy = scanBy;
	}
	
	
}
