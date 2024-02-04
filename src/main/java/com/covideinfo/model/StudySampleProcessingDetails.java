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
@Table(name="study_sample_processing_details")
public class StudySampleProcessingDetails implements Serializable {
	
	
	private static final long serialVersionUID = -223496259307849479L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_sample_processing_details_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="processing")
	private StudySampleProcessing processing;
	@Column(name="aliquot_volume")
	private int aliquotVolume;
	@ManyToOne
	@JoinColumn(name="condition")
	private ConditionParameter conditon;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="created_on")
    private Date createdOn;
    @Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	public long getId() {
		return id;
	}
	public StudySampleProcessing getProcessing() {
		return processing;
	}
	public int getAliquotVolume() {
		return aliquotVolume;
	}
	public ConditionParameter getConditon() {
		return conditon;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setProcessing(StudySampleProcessing processing) {
		this.processing = processing;
	}
	public void setAliquotVolume(int aliquotVolume) {
		this.aliquotVolume = aliquotVolume;
	}
	public void setConditon(ConditionParameter conditon) {
		this.conditon = conditon;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
