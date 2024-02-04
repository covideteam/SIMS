package com.covideinfo.eform.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.covideinfo.model.StudyMaster;

@Entity
@Table(name = "EFORMRULE")
public class EFormRule {
	private static final long serialVersionUID = -6338179529397030031L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="eformrule_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name = "eformruleId")
	private long id;
	private String createdBy = "System";
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn = new Date();
	private String updatedBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedOn;
	private String updateReason;
	
	@ManyToOne
	@JoinColumn(name="study_id")
	private StudyMaster study;
	@Column(columnDefinition="TEXT")
	private String name;
	@Column(columnDefinition="TEXT")
	private String ruleDesc;
	@Column(columnDefinition="TEXT")
	private String message;
	@Column(name = "is_active")	
	private boolean active = true;//status
	@ManyToOne
	@JoinColumn(name="crf")
	private EForm crf;
	@ManyToOne
	@JoinColumn(name="section_ele")
	private EFormSectionElement secEle;
	@ManyToOne
	@JoinColumn(name="group_ele")
	private EFormGroupElement groupEle;
	private String compareWith;
	private String value1;
	private String condtion1;
	private String ncondtion1;
	private String value2;
	private String condtion2;
	private String ncondtion2;
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="crfRule")
	List<EFormRuleWithOther> otherCrf;
	private int rowNo;
	
	public int getRowNo() {
		return rowNo;
	}
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public String getCompareWith() {
		return compareWith;
	}
	public void setCompareWith(String compareWith) {
		this.compareWith = compareWith;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getCondtion1() {
		return condtion1;
	}
	public void setCondtion1(String condtion1) {
		this.condtion1 = condtion1;
	}
	public String getNcondtion1() {
		return ncondtion1;
	}
	public void setNcondtion1(String ncondtion1) {
		this.ncondtion1 = ncondtion1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getCondtion2() {
		return condtion2;
	}
	public void setCondtion2(String condtion2) {
		this.condtion2 = condtion2;
	}
	public String getNcondtion2() {
		return ncondtion2;
	}
	public void setNcondtion2(String ncondtion2) {
		this.ncondtion2 = ncondtion2;
	}
	public List<EFormRuleWithOther> getOtherCrf() {
		return otherCrf;
	}
	public void setOtherCrf(List<EFormRuleWithOther> otherCrf) {
		this.otherCrf = otherCrf;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
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
