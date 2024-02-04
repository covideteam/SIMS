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
@Table(name = "EFORMRULE_WITH_OTHER")
public class EFormRuleWithOther {
	private static final long serialVersionUID = -8223252523347727374L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="eformrulewithother_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name = "eformrulewithotherId")	
	private long id;
	private String createdBy = "System";
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn = new Date();
	private String updatedBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedOn;
	private String updateReason;
	
	@ManyToOne
	@JoinColumn(name="crf_rule")
	private EFormRule crfRule;
	@ManyToOne
	@JoinColumn(name="crf")
	private EForm crf;
	@ManyToOne
	@JoinColumn(name="section_ele")
	private EFormSectionElement secEle;
	@ManyToOne
	@JoinColumn(name="group_ele")
	private EFormGroupElement groupEle;
	private int rowNo;
	private String compareWith;
	private String condtion;
	private String ncondtion;
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
	public EFormRule getCrfRule() {
		return crfRule;
	}
	public void setCrfRule(EFormRule crfRule) {
		this.crfRule = crfRule;
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
	public int getRowNo() {
		return rowNo;
	}
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}
	public String getCompareWith() {
		return compareWith;
	}
	public void setCompareWith(String compareWith) {
		this.compareWith = compareWith;
	}
	public String getCondtion() {
		return condtion;
	}
	public void setCondtion(String condtion) {
		this.condtion = condtion;
	}
	public String getNcondtion() {
		return ncondtion;
	}
	public void setNcondtion(String ncondtion) {
		this.ncondtion = ncondtion;
	}
	
	
	
}
