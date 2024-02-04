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
@Table(name="ip_discard")
public class IPDiscard implements Serializable {
	
	private static final long serialVersionUID = -5480702676945050733L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="ip_discard_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="projectId")
	private Projects projectId;
	@Column(name="typeOfProduct")
	private String typeOfProduct;
	@Column(name="randamizationCode")
	private String randamizationCode;
	@Column(name="purpose")
	private String purpose;
	@Column(name="workingAreaClean")
	private String workingAreaClean;
	@Column(name="requiredDocumentsAvailable")
	private String requiredDocumentsAvailable;
	@Column(name="areaClean")
	private String areaClean;
	@ManyToOne
	@JoinColumn(name="projectIdTran")
	private Projects projectIdTran;
	@Column(name="quantity")
	private String quantity;
	@Column(name="quantityVerified")
	private String quantityVerified;
	@Column(name="noOfUnitsTransferred")
	private Integer noOfUnitsTransferred;
	@Column(name="comments")
	private String comments;
	@Column(name="attachRecordIfAny")
	private String attachRecordIfAny;
	
	@Column(name="created_by")
	private String createdBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatedOn;
	
	
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
	public String getTypeOfProduct() {
		return typeOfProduct;
	}
	public void setTypeOfProduct(String typeOfProduct) {
		this.typeOfProduct = typeOfProduct;
	}
	public String getRandamizationCode() {
		return randamizationCode;
	}
	public void setRandamizationCode(String randamizationCode) {
		this.randamizationCode = randamizationCode;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
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
	
	public Projects getProjectIdTran() {
		return projectIdTran;
	}
	public void setProjectIdTran(Projects projectIdTran) {
		this.projectIdTran = projectIdTran;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getQuantityVerified() {
		return quantityVerified;
	}
	public void setQuantityVerified(String quantityVerified) {
		this.quantityVerified = quantityVerified;
	}
	public Integer getNoOfUnitsTransferred() {
		return noOfUnitsTransferred;
	}
	public void setNoOfUnitsTransferred(Integer noOfUnitsTransferred) {
		this.noOfUnitsTransferred = noOfUnitsTransferred;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getAttachRecordIfAny() {
		return attachRecordIfAny;
	}
	public void setAttachRecordIfAny(String attachRecordIfAny) {
		this.attachRecordIfAny = attachRecordIfAny;
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
	
	
	
	
}
