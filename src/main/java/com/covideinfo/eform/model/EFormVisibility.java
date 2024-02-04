package com.covideinfo.eform.model;

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
@Table(name="EForm_Visibility")
public class EFormVisibility {

	/**
	 * 
	 */
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="EForm_Visibility_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="eForm_VisibilityId")
	private long id;
	private String createdBy = "System";
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn = new Date();
	private String updatedBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedOn;
	private String updateReason;
	
	@Column(name="visiblity_name")
	private String name = "";
	@Column(name="name_desc")
	private String desc = "";
	@ManyToOne
	@JoinColumn(name="eform_id")
	private EForm crf;
	@ManyToOne
	@JoinColumn(name="sec_ele_id")
	private EFormSectionElement secEle;
	@ManyToOne
	@JoinColumn(name="group_ele_id")
	private EFormGroupElement groupEle;
	private String filedValue = "";
	private String condition = "";
	@Column(name="action_type")
	private String action = "Show"; //	Show/Hide
 	
	@ManyToOne
	@JoinColumn(name="target_crf_id")
	private EForm targetCrf;
	@ManyToOne
	@JoinColumn(name="section_id")
	private EFormSection section;
	@ManyToOne
	@JoinColumn(name="group_id")
	private EFormGroup group;
	@Column(name="active_status")
	private boolean status;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public EForm getCrf() {
		return crf;
	}
	public void setCrf(EForm crf) {
		this.crf = crf;
	}
	public EFormSectionElement getSecEle() {
		return secEle;
	}
	public void setSecEle(EFormSectionElement secEle) {
		this.secEle = secEle;
	}
	public EFormGroupElement getGroupEle() {
		return groupEle;
	}
	public void setGroupEle(EFormGroupElement groupEle) {
		this.groupEle = groupEle;
	}
	public String getFiledValue() {
		return filedValue;
	}
	public void setFiledValue(String filedValue) {
		this.filedValue = filedValue;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public EForm getTargetCrf() {
		return targetCrf;
	}
	public void setTargetCrf(EForm targetCrf) {
		this.targetCrf = targetCrf;
	}
	public EFormSection getSection() {
		return section;
	}
	public void setSection(EFormSection section) {
		this.section = section;
	}
	public EFormGroup getGroup() {
		return group;
	}
	public void setGroup(EFormGroup group) {
		this.group = group;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
