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
@Table(name="study_drug_dispensing")
public class StudyDrugDispensing implements Serializable {
	
	private static final long serialVersionUID = -5480702676945050733L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_drug_dispensing_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="projectId")
	private Projects projectId;
	@ManyToOne
	@JoinColumn(name="periodId")
	private StudyPeriodMaster periodId;
	@Column(name="randamizationCode")
	private String randamizationCode;
	@Column(name="typeOfProduct")
	private String typeOfProduct;
	@Column(name="workingAreaClean")
	private String workingAreaClean;
	@Column(name="requiredDocumentsAvailable")
	private String requiredDocumentsAvailable;
	@Column(name="areaClean")
	private String areaClean;
	@ManyToOne
	@JoinColumn(name="applicableRegulationId")
	private Regulatories applicableRegulationId;
	@Column(name="sponsorStudyCode")
	private String sponsorStudyCode;
	@Column(name="dinstinctiveTradeName")
	private String dinstinctiveTradeName;
	@Column(name="genericName")
	private String genericName;
	@Column(name="pharmaceuticalForm")
	private String pharmaceuticalForm;
	@Column(name="specialCondition")
	private String specialCondition;
	@Column(name="strength")
	private String strength;
	@ManyToOne
	@JoinColumn(name="strengthUnitId")
	private UnitsMaster strengthUnitId;
	@Column(name="batchLotNumber")
	private String batchLotNumber;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date manufacturingDate;
	@Column(name="storageConditions")
	private String storageConditions;
	@Column(name="storageDegreesCelsius")
	private String storageDegreesCelsius;
	@Column(name="storageDegreesFahranneit")
	private String storageDegreesFahranneit;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expirationRatestDate;
	@Column(name="dose")
	private Integer dose;
	@Column(name="methodOfDispensing")
	private String methodOfDispensing;
	@Column(name="methodOfDispensingAttachIfAny")
	private String methodOfDispensingAttachIfAny;
	@Column(name="rhOfTheArea")
	private String rhOfTheArea;
	@Column(name="noOfSubjects")
	private Integer noOfSubjects;
	@Column(name="subjectsForUnitsDispensed")
	private Integer subjectsForUnitsDispensed;
	@ManyToOne
	@JoinColumn(name="subjectsUnitId")
	private UnitsMaster subjectsUnitId;
	@Column(name="noOfStandBy")
	private Integer noOfStandBy;
	@Column(name="standByForUnitsDispensed")
	private Integer standByForUnitsDispensed;
	@ManyToOne
	@JoinColumn(name="standByUnitId")
	private UnitsMaster standByUnitId;
	@Column(name="noOfUnits")
	private Integer noOfUnits;
	//Scan dispensing container to display subject numbers
	@Column(name="subjectNumbers")
	private String subjectNumbers;
	@Column(name="comments")
	private String comments;
	@Column(name="dispensingCompleted")
	private String dispensingCompleted;
	@Column(name="noOfBoxes")
	private Integer noOfBoxes; //Dispensed units Storage Container(Box)Labels
	@Column(name="toBeStoredInPharmacy")
	private String toBeStoredInPharmacy;
	@Column(name="proceedForAnotherProduct")
	private String proceedForAnotherProduct;
	@Column(name="status")
	private String status="InApproval";
	@Column(name="approval_by")
	private String approvalBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date approvlOn;
	@Column(name="finalStatus")
	private String finalStatus="InCompleted";
	
	@Column(name="created_by")
	private String createdBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cratedOn;
	@Column(name="updated_by")
	private String updatedBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateOn;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Projects getProjectId() {
		return projectId;
	}
	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}
	public StudyPeriodMaster getPeriodId() {
		return periodId;
	}
	public void setPeriodId(StudyPeriodMaster periodId) {
		this.periodId = periodId;
	}
	public String getTypeOfProduct() {
		return typeOfProduct;
	}
	public void setTypeOfProduct(String typeOfProduct) {
		this.typeOfProduct = typeOfProduct;
	}
	public String getWorkingAreaClean() {
		return workingAreaClean;
	}
	public void setWorkingAreaClean(String workingAreaClean) {
		this.workingAreaClean = workingAreaClean;
	}
	public String getRequiredDocumentsAvailable() {
		return requiredDocumentsAvailable;
	}
	public void setRequiredDocumentsAvailable(String requiredDocumentsAvailable) {
		this.requiredDocumentsAvailable = requiredDocumentsAvailable;
	}
	public String getAreaClean() {
		return areaClean;
	}
	public void setAreaClean(String areaClean) {
		this.areaClean = areaClean;
	}
	public Regulatories getApplicableRegulationId() {
		return applicableRegulationId;
	}
	public void setApplicableRegulationId(Regulatories applicableRegulationId) {
		this.applicableRegulationId = applicableRegulationId;
	}
	public String getSponsorStudyCode() {
		return sponsorStudyCode;
	}
	public void setSponsorStudyCode(String sponsorStudyCode) {
		this.sponsorStudyCode = sponsorStudyCode;
	}
	public String getDinstinctiveTradeName() {
		return dinstinctiveTradeName;
	}
	public void setDinstinctiveTradeName(String dinstinctiveTradeName) {
		this.dinstinctiveTradeName = dinstinctiveTradeName;
	}
	public String getGenericName() {
		return genericName;
	}
	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}
	public String getPharmaceuticalForm() {
		return pharmaceuticalForm;
	}
	public void setPharmaceuticalForm(String pharmaceuticalForm) {
		this.pharmaceuticalForm = pharmaceuticalForm;
	}
	public String getSpecialCondition() {
		return specialCondition;
	}
	public void setSpecialCondition(String specialCondition) {
		this.specialCondition = specialCondition;
	}
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	public UnitsMaster getStrengthUnitId() {
		return strengthUnitId;
	}
	public void setStrengthUnitId(UnitsMaster strengthUnitId) {
		this.strengthUnitId = strengthUnitId;
	}
	public String getBatchLotNumber() {
		return batchLotNumber;
	}
	public void setBatchLotNumber(String batchLotNumber) {
		this.batchLotNumber = batchLotNumber;
	}
	public Date getManufacturingDate() {
		return manufacturingDate;
	}
	public void setManufacturingDate(Date manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}
	public String getStorageConditions() {
		return storageConditions;
	}
	public void setStorageConditions(String storageConditions) {
		this.storageConditions = storageConditions;
	}
	public String getStorageDegreesCelsius() {
		return storageDegreesCelsius;
	}
	public void setStorageDegreesCelsius(String storageDegreesCelsius) {
		this.storageDegreesCelsius = storageDegreesCelsius;
	}
	public String getStorageDegreesFahranneit() {
		return storageDegreesFahranneit;
	}
	public void setStorageDegreesFahranneit(String storageDegreesFahranneit) {
		this.storageDegreesFahranneit = storageDegreesFahranneit;
	}
	public Date getExpirationRatestDate() {
		return expirationRatestDate;
	}
	public void setExpirationRatestDate(Date expirationRatestDate) {
		this.expirationRatestDate = expirationRatestDate;
	}
	public Integer getDose() {
		return dose;
	}
	public void setDose(Integer dose) {
		this.dose = dose;
	}
	public String getMethodOfDispensing() {
		return methodOfDispensing;
	}
	public void setMethodOfDispensing(String methodOfDispensing) {
		this.methodOfDispensing = methodOfDispensing;
	}
	public String getMethodOfDispensingAttachIfAny() {
		return methodOfDispensingAttachIfAny;
	}
	public void setMethodOfDispensingAttachIfAny(String methodOfDispensingAttachIfAny) {
		this.methodOfDispensingAttachIfAny = methodOfDispensingAttachIfAny;
	}
	public String getRhOfTheArea() {
		return rhOfTheArea;
	}
	public void setRhOfTheArea(String rhOfTheArea) {
		this.rhOfTheArea = rhOfTheArea;
	}
	public Integer getNoOfSubjects() {
		return noOfSubjects;
	}
	public void setNoOfSubjects(Integer noOfSubjects) {
		this.noOfSubjects = noOfSubjects;
	}
	public Integer getSubjectsForUnitsDispensed() {
		return subjectsForUnitsDispensed;
	}
	public void setSubjectsForUnitsDispensed(Integer subjectsForUnitsDispensed) {
		this.subjectsForUnitsDispensed = subjectsForUnitsDispensed;
	}
	public UnitsMaster getSubjectsUnitId() {
		return subjectsUnitId;
	}
	public void setSubjectsUnitId(UnitsMaster subjectsUnitId) {
		this.subjectsUnitId = subjectsUnitId;
	}
	public Integer getNoOfStandBy() {
		return noOfStandBy;
	}
	public void setNoOfStandBy(Integer noOfStandBy) {
		this.noOfStandBy = noOfStandBy;
	}
	public Integer getStandByForUnitsDispensed() {
		return standByForUnitsDispensed;
	}
	public void setStandByForUnitsDispensed(Integer standByForUnitsDispensed) {
		this.standByForUnitsDispensed = standByForUnitsDispensed;
	}
	public UnitsMaster getStandByUnitId() {
		return standByUnitId;
	}
	public void setStandByUnitId(UnitsMaster standByUnitId) {
		this.standByUnitId = standByUnitId;
	}
	public Integer getNoOfUnits() {
		return noOfUnits;
	}
	public void setNoOfUnits(Integer noOfUnits) {
		this.noOfUnits = noOfUnits;
	}
	public String getSubjectNumbers() {
		return subjectNumbers;
	}
	public void setSubjectNumbers(String subjectNumbers) {
		this.subjectNumbers = subjectNumbers;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDispensingCompleted() {
		return dispensingCompleted;
	}
	public void setDispensingCompleted(String dispensingCompleted) {
		this.dispensingCompleted = dispensingCompleted;
	}
	
	public Integer getNoOfBoxes() {
		return noOfBoxes;
	}
	public void setNoOfBoxes(Integer noOfBoxes) {
		this.noOfBoxes = noOfBoxes;
	}
	public String getToBeStoredInPharmacy() {
		return toBeStoredInPharmacy;
	}
	public void setToBeStoredInPharmacy(String toBeStoredInPharmacy) {
		this.toBeStoredInPharmacy = toBeStoredInPharmacy;
	}
	public String getProceedForAnotherProduct() {
		return proceedForAnotherProduct;
	}
	public void setProceedForAnotherProduct(String proceedForAnotherProduct) {
		this.proceedForAnotherProduct = proceedForAnotherProduct;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCratedOn() {
		return cratedOn;
	}
	public void setCratedOn(Date cratedOn) {
		this.cratedOn = cratedOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}
	public String getRandamizationCode() {
		return randamizationCode;
	}
	public void setRandamizationCode(String randamizationCode) {
		this.randamizationCode = randamizationCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApprovalBy() {
		return approvalBy;
	}
	public void setApprovalBy(String approvalBy) {
		this.approvalBy = approvalBy;
	}
	public Date getApprovlOn() {
		return approvlOn;
	}
	public void setApprovlOn(Date approvlOn) {
		this.approvlOn = approvlOn;
	}
	public String getFinalStatus() {
		return finalStatus;
	}
	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}
	
	
}
