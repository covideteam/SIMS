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
@Table(name="drug_reception_table")
public class DrugReception implements Serializable {
	
	private static final long serialVersionUID = -5480702676945050733L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="drug_reception_table_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="projectAvailable")
	private String projectAvailable;
	@ManyToOne
	@JoinColumn(name="projectId")
	private Projects projectId;
	@Column(name="sponsorStudyCode")
	private String sponsorStudyCode;
	@Column(name="workingAreaClean")
	private String workingAreaClean;
	@Column(name="requiredDocumentsAvailable")
	private String requiredDocumentsAvailable;
	@Column(name="areaClean")
	private String areaClean;
	@ManyToOne
	@JoinColumn(name="applicableRegulationId")
	private Regulatories applicableRegulationId;
	@Column(name="drungProductType")
	private String drungProductType;
	@Column(name="randamizationCode")
	private String randamizationCode;
	@Column(name="dinstinctiveTradeName")
	private String dinstinctiveTradeName;
	@Column(name="genericName")
	private String genericName;
	@Column(name="pharmaceuticalForm")
	private String pharmaceuticalForm;
	@Column(name="strength")
	private String strength;
	@ManyToOne
	@JoinColumn(name="strengthUnit")
	private UnitsMaster strengthUnit;
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
	
	//Containers Receiverd Status
	@Column(name="noofContainersReceived")
	private Integer noofContainersReceived;
	@Column(name="noofSealOpenedContainers")
	private Integer noofSealOpenedContainers;
	@Column(name="noofSealClosedContainers")
	private Integer noofSealClosedContainers;
	@Column(name="noofContainersOpened")
	private Integer noofContainersOpened;
	@Column(name="noofContainersNotOpened")
	private Integer noofContainersNotOpened;
	@Column(name="quantityReceivedInUnits")
	private Integer quantityReceivedInUnits;
	@Column(name="noofUnitsAsPerProtocol")
	private Integer noofUnitsAsPerProtocol;
	
	//Product Description
	@Column(name="productPharmaceuticalForm")
	private String productPharmaceuticalForm;
	@Column(name="textureFinish")
	private String textureFinish;
	@Column(name="shape")
	private String shape;
	@Column(name="brandIdentification")
	private String brandIdentification;
	@Column(name="productColor")
	private String productColor;
	@Column(name="productOther")
	private String productOther;
	@Column(name="unitsUsed")
	private String unitsUsed;
	@Column(name="noOfUnits")
	private String noOfUnits;
	@Column(name="productDescriptionComments")
	private String productDescriptionComments;
	
	//The Label of the drug product contains
	@Column(name="theLabelOfTheDrugProductContains")
	private String theLabelOfTheDrugProductContains;
	
	@Column(name="parcelCourierReceipt")
	private String parcelCourierReceipt;
	@Column(name="wayBillNumber")
	private String wayBillNumber;
	@Column(name="dataLogger")
	private String dataLogger;
	@Column(name="dataLoggerCode")
	private String dataLoggerCode;
	
	@Column(name="shippingCoditions")
	private String shippingCoditions;
	@Column(name="containerSleCondition")
	private String containerSleCondition;
	@Column(name="primaryContainerCondition")
	private String primaryContainerCondition;
	@Column(name="secondaryContainerCondition")
	private String secondaryContainerCondition;
	@Column(name="productContainerComments")
	private String productContainerComments;
	
	@Column(name="qurantineRequired")
	private String qurantineRequired;
	@Column(name="noofBoxsLabel")
	private Integer noofBoxsLabel; //Mapping table( DrugReceptionContainerBarcodes )
	@Column(name="status")
	private String status="InCompleted";
	@Column(name="reviewerStatus")
	private String reviewerStatus="InApproval";
	
	@Column(name="reviewer_by")
	private String reviewerBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date reviewerOn;
	
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
	public String getProjectAvailable() {
		return projectAvailable;
	}
	public void setProjectAvailable(String projectAvailable) {
		this.projectAvailable = projectAvailable;
	}
	public Projects getProjectId() {
		return projectId;
	}
	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}
	public String getSponsorStudyCode() {
		return sponsorStudyCode;
	}
	public void setSponsorStudyCode(String sponsorStudyCode) {
		this.sponsorStudyCode = sponsorStudyCode;
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
	public String getDrungProductType() {
		return drungProductType;
	}
	public void setDrungProductType(String drungProductType) {
		this.drungProductType = drungProductType;
	}
	public String getRandamizationCode() {
		return randamizationCode;
	}
	public void setRandamizationCode(String randamizationCode) {
		this.randamizationCode = randamizationCode;
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
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	public UnitsMaster getStrengthUnit() {
		return strengthUnit;
	}
	public void setStrengthUnit(UnitsMaster strengthUnit) {
		this.strengthUnit = strengthUnit;
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
	public Integer getNoofContainersReceived() {
		return noofContainersReceived;
	}
	public void setNoofContainersReceived(Integer noofContainersReceived) {
		this.noofContainersReceived = noofContainersReceived;
	}
	public Integer getNoofSealOpenedContainers() {
		return noofSealOpenedContainers;
	}
	public void setNoofSealOpenedContainers(Integer noofSealOpenedContainers) {
		this.noofSealOpenedContainers = noofSealOpenedContainers;
	}
	public Integer getNoofSealClosedContainers() {
		return noofSealClosedContainers;
	}
	public void setNoofSealClosedContainers(Integer noofSealClosedContainers) {
		this.noofSealClosedContainers = noofSealClosedContainers;
	}
	public Integer getNoofContainersOpened() {
		return noofContainersOpened;
	}
	public void setNoofContainersOpened(Integer noofContainersOpened) {
		this.noofContainersOpened = noofContainersOpened;
	}
	public Integer getNoofContainersNotOpened() {
		return noofContainersNotOpened;
	}
	public void setNoofContainersNotOpened(Integer noofContainersNotOpened) {
		this.noofContainersNotOpened = noofContainersNotOpened;
	}
	public Integer getQuantityReceivedInUnits() {
		return quantityReceivedInUnits;
	}
	public void setQuantityReceivedInUnits(Integer quantityReceivedInUnits) {
		this.quantityReceivedInUnits = quantityReceivedInUnits;
	}
	public Integer getNoofUnitsAsPerProtocol() {
		return noofUnitsAsPerProtocol;
	}
	public void setNoofUnitsAsPerProtocol(Integer noofUnitsAsPerProtocol) {
		this.noofUnitsAsPerProtocol = noofUnitsAsPerProtocol;
	}
	public String getProductPharmaceuticalForm() {
		return productPharmaceuticalForm;
	}
	public void setProductPharmaceuticalForm(String productPharmaceuticalForm) {
		this.productPharmaceuticalForm = productPharmaceuticalForm;
	}
	public String getTextureFinish() {
		return textureFinish;
	}
	public void setTextureFinish(String textureFinish) {
		this.textureFinish = textureFinish;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getBrandIdentification() {
		return brandIdentification;
	}
	public void setBrandIdentification(String brandIdentification) {
		this.brandIdentification = brandIdentification;
	}
	public String getProductColor() {
		return productColor;
	}
	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}
	public String getProductOther() {
		return productOther;
	}
	public void setProductOther(String productOther) {
		this.productOther = productOther;
	}
	
	public String getUnitsUsed() {
		return unitsUsed;
	}
	public void setUnitsUsed(String unitsUsed) {
		this.unitsUsed = unitsUsed;
	}
	public String getNoOfUnits() {
		return noOfUnits;
	}
	public void setNoOfUnits(String noOfUnits) {
		this.noOfUnits = noOfUnits;
	}
	public String getProductDescriptionComments() {
		return productDescriptionComments;
	}
	public void setProductDescriptionComments(String productDescriptionComments) {
		this.productDescriptionComments = productDescriptionComments;
	}
	public String getTheLabelOfTheDrugProductContains() {
		return theLabelOfTheDrugProductContains;
	}
	public void setTheLabelOfTheDrugProductContains(String theLabelOfTheDrugProductContains) {
		this.theLabelOfTheDrugProductContains = theLabelOfTheDrugProductContains;
	}
	public String getParcelCourierReceipt() {
		return parcelCourierReceipt;
	}
	public void setParcelCourierReceipt(String parcelCourierReceipt) {
		this.parcelCourierReceipt = parcelCourierReceipt;
	}
	public String getWayBillNumber() {
		return wayBillNumber;
	}
	public void setWayBillNumber(String wayBillNumber) {
		this.wayBillNumber = wayBillNumber;
	}
	public String getDataLogger() {
		return dataLogger;
	}
	public void setDataLogger(String dataLogger) {
		this.dataLogger = dataLogger;
	}
	public String getDataLoggerCode() {
		return dataLoggerCode;
	}
	public void setDataLoggerCode(String dataLoggerCode) {
		this.dataLoggerCode = dataLoggerCode;
	}
	public String getShippingCoditions() {
		return shippingCoditions;
	}
	public void setShippingCoditions(String shippingCoditions) {
		this.shippingCoditions = shippingCoditions;
	}
	public String getContainerSleCondition() {
		return containerSleCondition;
	}
	public void setContainerSleCondition(String containerSleCondition) {
		this.containerSleCondition = containerSleCondition;
	}
	public String getPrimaryContainerCondition() {
		return primaryContainerCondition;
	}
	public void setPrimaryContainerCondition(String primaryContainerCondition) {
		this.primaryContainerCondition = primaryContainerCondition;
	}
	public String getSecondaryContainerCondition() {
		return secondaryContainerCondition;
	}
	public void setSecondaryContainerCondition(String secondaryContainerCondition) {
		this.secondaryContainerCondition = secondaryContainerCondition;
	}
	public String getProductContainerComments() {
		return productContainerComments;
	}
	public void setProductContainerComments(String productContainerComments) {
		this.productContainerComments = productContainerComments;
	}
	public String getQurantineRequired() {
		return qurantineRequired;
	}
	public void setQurantineRequired(String qurantineRequired) {
		this.qurantineRequired = qurantineRequired;
	}
	public Integer getNoofBoxsLabel() {
		return noofBoxsLabel;
	}
	public void setNoofBoxsLabel(Integer noofBoxsLabel) {
		this.noofBoxsLabel = noofBoxsLabel;
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
	public String getReviewerStatus() {
		return reviewerStatus;
	}
	public void setReviewerStatus(String reviewerStatus) {
		this.reviewerStatus = reviewerStatus;
	}
	public String getReviewerBy() {
		return reviewerBy;
	}
	public void setReviewerBy(String reviewerBy) {
		this.reviewerBy = reviewerBy;
	}
	public Date getReviewerOn() {
		return reviewerOn;
	}
	public void setReviewerOn(Date reviewerOn) {
		this.reviewerOn = reviewerOn;
	}
	
	
	
	
}
