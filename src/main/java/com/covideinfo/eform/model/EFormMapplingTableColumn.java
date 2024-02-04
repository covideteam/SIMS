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
@Table(name="EFORM_Mapping_Table_Column")
public class EFormMapplingTableColumn {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6338179529397030031L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="eformmapplingtablecolumn_id_seq", allocationSize=1)
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
	@JoinColumn(name="crf_mapping_table_id")
	private EFormMapplingTable mappingTable;
	private String cloumnName = "";
	private String cloumnType = "";
	public EFormMapplingTable getMappingTable() {
		return mappingTable;
	}
	public void setMappingTable(EFormMapplingTable mappingTable) {
		this.mappingTable = mappingTable;
	}
	public String getCloumnName() {
		return cloumnName;
	}
	public void setCloumnName(String cloumnName) {
		this.cloumnName = cloumnName;
	}
	public String getCloumnType() {
		return cloumnType;
	}
	public void setCloumnType(String cloumnType) {
		this.cloumnType = cloumnType;
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
