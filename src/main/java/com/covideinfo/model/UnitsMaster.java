package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="units_master")
public class UnitsMaster implements Serializable {
	
	
	
	private static final long serialVersionUID = 6052205321459589996L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="units_master_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private Long id;
	@Column(name="unit_code", length=500, unique=true)
	private String unitsCode="";
	/*@Column(name="displaySting", length=500)
	private String displayString="";
	@Column(name="BASE")
	private char base = ' ';
	@Column(name="ofset" , length=500)
	private String ofset ="";
	@Column(name="factor" , length=500)
	private String factor ="";
	@Column(name="supBase" , length=500)
	private String supBase="";
	@Column(name="power" , length=500)
	private String power="";
	@Column(name="sub" , length=500)
	private String sub="";
	@Column(name="scriptOrder" , length=500)
	private String scriptOrder="";*/
	@Column(name="created_by")
	private String createdBy ;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	@Column(name="update_reason")
	private String updateReason="";
	public Long getId() {
		return id;
	}
	public String getUnitsCode() {
		return unitsCode;
	}
	/*public String getDisplayString() {
		return displayString;
	}
	public char getBase() {
		return base;
	}
	public String getOfset() {
		return ofset;
	}
	public String getFactor() {
		return factor;
	}
	public String getSupBase() {
		return supBase;
	}
	public String getPower() {
		return power;
	}
	public String getSub() {
		return sub;
	}
	public String getScriptOrder() {
		return scriptOrder;
	}*/
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
	public String getUpdateReason() {
		return updateReason;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUnitsCode(String unitsCode) {
		this.unitsCode = unitsCode;
	}
	/*public void setDisplayString(String displayString) {
		this.displayString = displayString;
	}
	public void setBase(char base) {
		this.base = base;
	}
	public void setOfset(String ofset) {
		this.ofset = ofset;
	}
	public void setFactor(String factor) {
		this.factor = factor;
	}
	public void setSupBase(String supBase) {
		this.supBase = supBase;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public void setScriptOrder(String scriptOrder) {
		this.scriptOrder = scriptOrder;
	}*/
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
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	
	
	

}
