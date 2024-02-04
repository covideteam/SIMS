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


@Entity
@Table(name="subject_randamization")
public class SubjectRandamization implements Serializable{
	
	
	private static final long serialVersionUID = -8502676882817936235L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="subject_randamization_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="subjectRandamizationId")
	private long id;
	private String subjectNo;
	private int seqNo;
	@ManyToOne
	@JoinColumn(name="periodId")
	private StudyPeriodMaster period;
	private String radamizationCode;
	@ManyToOne
	@JoinColumn(name="treatmentInfoId")
	private TreatmentInfo treatmentInfo;
	@Column(name="created_by")
	private String cretedBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public String getRadamizationCode() {
		return radamizationCode;
	}
	public void setRadamizationCode(String radamizationCode) {
		this.radamizationCode = radamizationCode;
	}
	public TreatmentInfo getTreatmentInfo() {
		return treatmentInfo;
	}
	public void setTreatmentInfo(TreatmentInfo treatmentInfo) {
		this.treatmentInfo = treatmentInfo;
	}
	public String getCretedBy() {
		return cretedBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCretedBy(String cretedBy) {
		this.cretedBy = cretedBy;
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
	
}
