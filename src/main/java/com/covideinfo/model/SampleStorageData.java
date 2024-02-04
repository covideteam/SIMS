package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Sample_Storage_Data")
public class SampleStorageData implements Serializable{
	
	
	private static final long serialVersionUID = -2122005475991082481L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "Sample_Storage_Data_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "sampleStorageDataId")
	private long id;
	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "periodId")
	private StudyPeriodMaster period;
	@ManyToOne
	@JoinColumn(name = "timePointId")
	private SampleTimePoints timePoint;
	private String timePoitnOnlye;
	private String aliquot;
	private String shelfBarocode;
	private Date shelfScanTime;
	private DeepfreezerShelf shelf;
	@ManyToOne
	@JoinColumn(name = "centrifugationDataMasterId")
	private CentrifugationDataMaster centrifugationDataMaster;
	private String storageMissedSubjects;
	
	private String depfreezerBarocode;
	private Date depfreezerScanTime;
	
	@ManyToOne
	@JoinColumn(name = "storedBy")
	private UserMaster storedBy;
	private Date storageTime;
	@ManyToOne
	@JoinColumn(name="created_by")
	private UserMaster createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	
	@Transient
	private List<SampleStorageDataRack> sampleStorageDataRack;
	
	public DeepfreezerShelf getShelf() {
		return shelf;
	}
	public void setShelf(DeepfreezerShelf shelf) {
		this.shelf = shelf;
	}
	public String getTimePoitnOnlye() {
		return timePoitnOnlye;
	}
	public void setTimePoitnOnlye(String timePoitnOnlye) {
		this.timePoitnOnlye = timePoitnOnlye;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public SampleTimePoints getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(SampleTimePoints timePoint) {
		this.timePoint = timePoint;
	}
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
	public String getStorageMissedSubjects() {
		return storageMissedSubjects;
	}
	public void setStorageMissedSubjects(String storageMissedSubjects) {
		this.storageMissedSubjects = storageMissedSubjects;
	}
	public String getAliquot() {
		return aliquot;
	}
	public void setAliquot(String aliquot) {
		this.aliquot = aliquot;
	}
	public String getDepfreezerBarocode() {
		return depfreezerBarocode;
	}
	public void setDepfreezerBarocode(String depfreezerBarocode) {
		this.depfreezerBarocode = depfreezerBarocode;
	}
	public Date getDepfreezerScanTime() {
		return depfreezerScanTime;
	}
	public void setDepfreezerScanTime(Date depfreezerScanTime) {
		this.depfreezerScanTime = depfreezerScanTime;
	}
	public String getShelfBarocode() {
		return shelfBarocode;
	}
	public void setShelfBarocode(String shelfBarocode) {
		this.shelfBarocode = shelfBarocode;
	}
	public Date getShelfScanTime() {
		return shelfScanTime;
	}
	public void setShelfScanTime(Date shelfScanTime) {
		this.shelfScanTime = shelfScanTime;
	}
	public UserMaster getStoredBy() {
		return storedBy;
	}
	public void setStoredBy(UserMaster storedBy) {
		this.storedBy = storedBy;
	}
	public Date getStorageTime() {
		return storageTime;
	}
	public void setStorageTime(Date storageTime) {
		this.storageTime = storageTime;
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
	public List<SampleStorageDataRack> getSampleStorageDataRack() {
		return sampleStorageDataRack;
	}
	public void setSampleStorageDataRack(List<SampleStorageDataRack> sampleStorageDataRack) {
		this.sampleStorageDataRack = sampleStorageDataRack;
	}
	
	
}
