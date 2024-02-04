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

import com.covideinfo.model.StudyMaster;
@Entity
@Table(name = "EForm_Ele_Caliculation")
public class EFormEleCaliculation {
	private static final long serialVersionUID = -1963344866011736659L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="eformelecaliculation_id_seq", allocationSize=1)
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
	@JoinColumn(name="crf")
	private EForm crf;
	private String resultField;
    private String caliculation;
    private String status = "active";
    @ManyToOne
    @JoinColumn(name="study_id")
    private StudyMaster study;
    
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public EForm getCrf() {
		return crf;
	}
	public void setCrf(EForm crf) {
		this.crf = crf;
	}
	public String getResultField() {
		return resultField;
	}
	public void setResultField(String resultField) {
		this.resultField = resultField;
	}
	public String getCaliculation() {
		return caliculation;
	}
	public void setCaliculation(String caliculation) {
		this.caliculation = caliculation;
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
