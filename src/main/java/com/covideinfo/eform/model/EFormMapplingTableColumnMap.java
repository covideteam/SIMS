package com.covideinfo.eform.model;

import java.util.Date;

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
@Table(name="EForm_Mapping_Table_column_map")
public class EFormMapplingTableColumnMap {
	private static final long serialVersionUID = -6338179529397030031L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="eformmapplingtablecolumnmap_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	private String createdBy = "System";
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn = new Date();
	private String updatedBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedOn;
	private String updateReason;
	
	@ManyToOne
	@JoinColumn(name="eform_id")
	private EForm crf;
	@ManyToOne
	@JoinColumn(name="eformSectionElement_id")
	private EFormSectionElement element;
	@ManyToOne
	@JoinColumn(name="eformMapplingTable_id")
	private EFormMapplingTable mappingTable;
	@ManyToOne
	@JoinColumn(name="eformMapplingTableColumn_id")
	private EFormMapplingTableColumn mappingColumn;
	
	public EFormMapplingTable getMappingTable() {
		return mappingTable;
	}
	public void setMappingTable(EFormMapplingTable mappingTable) {
		this.mappingTable = mappingTable;
	}
	public EFormMapplingTableColumn getMappingColumn() {
		return mappingColumn;
	}
	public void setMappingColumn(EFormMapplingTableColumn mappingColumn) {
		this.mappingColumn = mappingColumn;
	}
	
	public EForm getCrf() {
		return crf;
	}
	public void setCrf(EForm crf) {
		this.crf = crf;
	}
	public EFormSectionElement getElement() {
		return element;
	}
	public void setElement(EFormSectionElement element) {
		this.element = element;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
