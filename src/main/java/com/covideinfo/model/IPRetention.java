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
@Table(name="ip_retention")
public class IPRetention implements Serializable {
	
	private static final long serialVersionUID = -5480702676945050733L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="ip_retention_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="projectId")
	private Projects projectId;
	@Column(name="typeOfProduct")
	private String typeOfProduct;
	@Column(name="randamizationCode")
	private String randamizationCode;
	@Column(name="workingAreaClean")
	private String workingAreaClean;
	@Column(name="requiredDocumentsAvailable")
	private String requiredDocumentsAvailable;
	@Column(name="areaClean")
	private String areaClean;
	@Column(name="totalUndispensedUnits")
	private String totalUndispensedUnits;
	@Column(name="totalDispensed")
	private String totalDispensed;
	@Column(name="grandTotal")
	private String grandTotal;
	
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
	public String getTotalUndispensedUnits() {
		return totalUndispensedUnits;
	}
	public void setTotalUndispensedUnits(String totalUndispensedUnits) {
		this.totalUndispensedUnits = totalUndispensedUnits;
	}
	public String getTotalDispensed() {
		return totalDispensed;
	}
	public void setTotalDispensed(String totalDispensed) {
		this.totalDispensed = totalDispensed;
	}
	public String getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
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