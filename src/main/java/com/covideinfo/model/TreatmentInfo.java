package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name = "study_treatments")
public class TreatmentInfo implements Serializable{
	

	
	private static final long serialVersionUID = 3901836875257704190L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_treatments_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "treatmentInfoId")
	private long id;
	@ManyToOne(cascade = CascadeType.ALL )
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "fastingInfo")
	private FromStaticData fastingInfo;
	private int treatmentCount;
	private String treatmentNo;
	private String treatmentName;
	private String randamizationCode;
	private String streanth;
	private String dose;
	private int noOfDosings=1;
	private String mountOfWaterConsumedWithTheDose;
	private int noOfSachetLables;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	
	public int getNoOfSachetLables() {
		return noOfSachetLables;
	}

	public void setNoOfSachetLables(int noOfSachetLables) {
		this.noOfSachetLables = noOfSachetLables;
	}

	public int getTreatmentCount() {
		return treatmentCount;
	}

	public void setTreatmentCount(int treatmentCount) {
		this.treatmentCount = treatmentCount;
	}

	public int getNoOfDosings() {
		return noOfDosings;
	}

	public void setNoOfDosings(int noOfDosings) {
		this.noOfDosings = noOfDosings;
	}

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

	public String getTreatmentNo() {
		return treatmentNo;
	}

	public void setTreatmentNo(String treatmentNo) {
		this.treatmentNo = treatmentNo;
	}

	public String getTreatmentName() {
		return treatmentName;
	}

	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}

	public String getRandamizationCode() {
		return randamizationCode;
	}

	public void setRandamizationCode(String randamizationCode) {
		this.randamizationCode = randamizationCode;
	}

	public String getStreanth() {
		return streanth;
	}

	public void setStreanth(String streanth) {
		this.streanth = streanth;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}
	public String getMountOfWaterConsumedWithTheDose() {
		return mountOfWaterConsumedWithTheDose;
	}

	public void setMountOfWaterConsumedWithTheDose(String mountOfWaterConsumedWithTheDose) {
		this.mountOfWaterConsumedWithTheDose = mountOfWaterConsumedWithTheDose;
	}

	public FromStaticData getFastingInfo() {
		return fastingInfo;
	}

	public void setFastingInfo(FromStaticData fastingInfo) {
		this.fastingInfo = fastingInfo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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
