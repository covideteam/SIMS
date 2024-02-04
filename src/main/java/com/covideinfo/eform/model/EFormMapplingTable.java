package com.covideinfo.eform.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "CRF_Mapping_Table")
public class EFormMapplingTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6338179529397030031L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="crfmapplingtable_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	private String createdBy = "System";
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn = new Date();
	private String updatedBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedOn;
	private String updateReason;
	
	
	private String tableName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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
