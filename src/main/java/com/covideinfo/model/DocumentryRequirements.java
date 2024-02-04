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
@Table(name="documentry_requirements")
public class DocumentryRequirements implements Serializable {
	
	private static final long serialVersionUID = -7781335169921174383L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="documentry_requirements_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="drugReceptionId")
	private DrugReception drugReceptionId;
	@Column(name="peCertificatie")
	private String peCertificatie;
	@Column(name="documentAvailable")
	private String documentAvailable;
	@Column(name="assay")
	private String assay;
	@Column(name="assayReason")
	private String assayReason;
	@Column(name="uniformityOfTheDosage")
	private String uniformityOfTheDosage;
	@Column(name="uniformityOfTheDosageReason")
	private String uniformityOfTheDosageReason;
	@Column(name="dissolution")
	private String dissolution;
	@Column(name="dissolutionReason")
	private String dissolutionReason;
	@Column(name="endorsedBySanitary")
	private String endorsedBySanitary;
	@Column(name="endorsedBySanitaryReason")
	private String endorsedBySanitaryReason;
	@Column(name="productIsMoreThenPercentage")
	private String productIsMoreThenPercentage;
	@Column(name="productIsMoreThenPercentageReason")
	private String productIsMoreThenPercentageReason;
	@Column(name="presentationLetterOfDrugs")
	private String presentationLetterOfDrugs;
	@Column(name="presentationLetterOfDrugsReason")
	private String presentationLetterOfDrugsReason;
	@Column(name="letterOfGoodManufacturing")
	private String letterOfGoodManufacturing;
	@Column(name="letterOfGoodManufacturingReason")
	private String letterOfGoodManufacturingReason;
	@Column(name="letterOfQualitative")
	private String letterOfQualitative;
	@Column(name="letterOfQualitativeReason")
	private String letterOfQualitativeReason;
	@Column(name="theReferenceProductIsTheIndicated")
	private String theReferenceProductIsTheIndicated;
	@Column(name="theReferenceProductIsTheIndicatedReason")
	private String theReferenceProductIsTheIndicatedReason;
	@Column(name="referenceProduct")
	private String referenceProduct;
	@Column(name="referenceProductReason")
	private String referenceProductReason;
	@Column(name="theExpirationDate")
	private String theExpirationDate;
	@Column(name="theExpirationDateReason")
	private String theExpirationDateReason;
	@Column(name="theQuantityOfTheDrug")
	private String theQuantityOfTheDrug;
	@Column(name="theQuantityOfTheDrugReason")
	private String theQuantityOfTheDrugReason;
	@Column(name="theBatchLotNumber")
	private String theBatchLotNumber;
	@Column(name="theBatchLotNumberReason")
	private String theBatchLotNumberReason;
	@Column(name="transferLetter")
	private String transferLetter;
	@Column(name="transferLetterReason")
	private String transferLetterReason;
	@Column(name="proceedForAnotherProduct")
	private String proceedForAnotherProduct;
	@Column(name="proceedForAnotherProductReason")
	private String proceedForAnotherProductReason;
	
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
	public String getPeCertificatie() {
		return peCertificatie;
	}
	public void setPeCertificatie(String peCertificatie) {
		this.peCertificatie = peCertificatie;
	}
	public String getDocumentAvailable() {
		return documentAvailable;
	}
	public void setDocumentAvailable(String documentAvailable) {
		this.documentAvailable = documentAvailable;
	}
	public String getAssay() {
		return assay;
	}
	public void setAssay(String assay) {
		this.assay = assay;
	}
	public String getAssayReason() {
		return assayReason;
	}
	public void setAssayReason(String assayReason) {
		this.assayReason = assayReason;
	}
	public String getUniformityOfTheDosage() {
		return uniformityOfTheDosage;
	}
	public void setUniformityOfTheDosage(String uniformityOfTheDosage) {
		this.uniformityOfTheDosage = uniformityOfTheDosage;
	}
	public String getUniformityOfTheDosageReason() {
		return uniformityOfTheDosageReason;
	}
	public void setUniformityOfTheDosageReason(String uniformityOfTheDosageReason) {
		this.uniformityOfTheDosageReason = uniformityOfTheDosageReason;
	}
	public String getDissolution() {
		return dissolution;
	}
	public void setDissolution(String dissolution) {
		this.dissolution = dissolution;
	}
	public String getDissolutionReason() {
		return dissolutionReason;
	}
	public void setDissolutionReason(String dissolutionReason) {
		this.dissolutionReason = dissolutionReason;
	}
	public String getEndorsedBySanitary() {
		return endorsedBySanitary;
	}
	public void setEndorsedBySanitary(String endorsedBySanitary) {
		this.endorsedBySanitary = endorsedBySanitary;
	}
	public String getEndorsedBySanitaryReason() {
		return endorsedBySanitaryReason;
	}
	public void setEndorsedBySanitaryReason(String endorsedBySanitaryReason) {
		this.endorsedBySanitaryReason = endorsedBySanitaryReason;
	}
	public String getProductIsMoreThenPercentage() {
		return productIsMoreThenPercentage;
	}
	public void setProductIsMoreThenPercentage(String productIsMoreThenPercentage) {
		this.productIsMoreThenPercentage = productIsMoreThenPercentage;
	}
	public String getProductIsMoreThenPercentageReason() {
		return productIsMoreThenPercentageReason;
	}
	public void setProductIsMoreThenPercentageReason(String productIsMoreThenPercentageReason) {
		this.productIsMoreThenPercentageReason = productIsMoreThenPercentageReason;
	}
	public String getPresentationLetterOfDrugs() {
		return presentationLetterOfDrugs;
	}
	public void setPresentationLetterOfDrugs(String presentationLetterOfDrugs) {
		this.presentationLetterOfDrugs = presentationLetterOfDrugs;
	}
	public String getPresentationLetterOfDrugsReason() {
		return presentationLetterOfDrugsReason;
	}
	public void setPresentationLetterOfDrugsReason(String presentationLetterOfDrugsReason) {
		this.presentationLetterOfDrugsReason = presentationLetterOfDrugsReason;
	}
	public String getLetterOfGoodManufacturing() {
		return letterOfGoodManufacturing;
	}
	public void setLetterOfGoodManufacturing(String letterOfGoodManufacturing) {
		this.letterOfGoodManufacturing = letterOfGoodManufacturing;
	}
	public String getLetterOfGoodManufacturingReason() {
		return letterOfGoodManufacturingReason;
	}
	public void setLetterOfGoodManufacturingReason(String letterOfGoodManufacturingReason) {
		this.letterOfGoodManufacturingReason = letterOfGoodManufacturingReason;
	}
	public String getLetterOfQualitative() {
		return letterOfQualitative;
	}
	public void setLetterOfQualitative(String letterOfQualitative) {
		this.letterOfQualitative = letterOfQualitative;
	}
	public String getLetterOfQualitativeReason() {
		return letterOfQualitativeReason;
	}
	public void setLetterOfQualitativeReason(String letterOfQualitativeReason) {
		this.letterOfQualitativeReason = letterOfQualitativeReason;
	}
	public String getTheReferenceProductIsTheIndicated() {
		return theReferenceProductIsTheIndicated;
	}
	public void setTheReferenceProductIsTheIndicated(String theReferenceProductIsTheIndicated) {
		this.theReferenceProductIsTheIndicated = theReferenceProductIsTheIndicated;
	}
	public String getTheReferenceProductIsTheIndicatedReason() {
		return theReferenceProductIsTheIndicatedReason;
	}
	public void setTheReferenceProductIsTheIndicatedReason(String theReferenceProductIsTheIndicatedReason) {
		this.theReferenceProductIsTheIndicatedReason = theReferenceProductIsTheIndicatedReason;
	}
	public String getReferenceProduct() {
		return referenceProduct;
	}
	public void setReferenceProduct(String referenceProduct) {
		this.referenceProduct = referenceProduct;
	}
	public String getReferenceProductReason() {
		return referenceProductReason;
	}
	public void setReferenceProductReason(String referenceProductReason) {
		this.referenceProductReason = referenceProductReason;
	}
	public String getTheExpirationDate() {
		return theExpirationDate;
	}
	public void setTheExpirationDate(String theExpirationDate) {
		this.theExpirationDate = theExpirationDate;
	}
	public String getTheExpirationDateReason() {
		return theExpirationDateReason;
	}
	public void setTheExpirationDateReason(String theExpirationDateReason) {
		this.theExpirationDateReason = theExpirationDateReason;
	}
	public String getTheQuantityOfTheDrug() {
		return theQuantityOfTheDrug;
	}
	public void setTheQuantityOfTheDrug(String theQuantityOfTheDrug) {
		this.theQuantityOfTheDrug = theQuantityOfTheDrug;
	}
	public String getTheQuantityOfTheDrugReason() {
		return theQuantityOfTheDrugReason;
	}
	public void setTheQuantityOfTheDrugReason(String theQuantityOfTheDrugReason) {
		this.theQuantityOfTheDrugReason = theQuantityOfTheDrugReason;
	}
	public String getTheBatchLotNumber() {
		return theBatchLotNumber;
	}
	public void setTheBatchLotNumber(String theBatchLotNumber) {
		this.theBatchLotNumber = theBatchLotNumber;
	}
	public String getTheBatchLotNumberReason() {
		return theBatchLotNumberReason;
	}
	public void setTheBatchLotNumberReason(String theBatchLotNumberReason) {
		this.theBatchLotNumberReason = theBatchLotNumberReason;
	}
	public String getTransferLetter() {
		return transferLetter;
	}
	public void setTransferLetter(String transferLetter) {
		this.transferLetter = transferLetter;
	}
	public String getTransferLetterReason() {
		return transferLetterReason;
	}
	public void setTransferLetterReason(String transferLetterReason) {
		this.transferLetterReason = transferLetterReason;
	}
	public String getProceedForAnotherProduct() {
		return proceedForAnotherProduct;
	}
	public void setProceedForAnotherProduct(String proceedForAnotherProduct) {
		this.proceedForAnotherProduct = proceedForAnotherProduct;
	}
	public String getProceedForAnotherProductReason() {
		return proceedForAnotherProductReason;
	}
	public void setProceedForAnotherProductReason(String proceedForAnotherProductReason) {
		this.proceedForAnotherProductReason = proceedForAnotherProductReason;
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
	public DrugReception getDrugReceptionId() {
		return drugReceptionId;
	}
	public void setDrugReceptionId(DrugReception drugReceptionId) {
		this.drugReceptionId = drugReceptionId;
	}
	
	
	
	
	
}
