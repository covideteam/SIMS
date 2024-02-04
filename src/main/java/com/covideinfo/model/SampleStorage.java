package com.covideinfo.model;

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
@Table(name="epk_sample_storage")
public class SampleStorage extends CommonMaster{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7548503236811867953L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_sample_storage_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "sampleStorageId")
	private long id;

	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	private boolean storateDifferent;
	@ManyToOne
	@JoinColumn(name = "storateCondition")
	private FromStaticData storateCondition;
	private String temperature = "";
	private int allowedTime; 
	@ManyToOne
	@JoinColumn(name = "storate")
	private FromStaticData storate;
	
	public FromStaticData getStorateCondition() {
		return storateCondition;
	}
	public void setStorateCondition(FromStaticData storateCondition) {
		this.storateCondition = storateCondition;
	}
	public boolean isStorateDifferent() {
		return storateDifferent;
	}
	public void setStorateDifferent(boolean storateDifferent) {
		this.storateDifferent = storateDifferent;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public int getAllowedTime() {
		return allowedTime;
	}
	public void setAllowedTime(int allowedTime) {
		this.allowedTime = allowedTime;
	}
	public FromStaticData getStorate() {
		return storate;
	}
	public void setStorate(FromStaticData storate) {
		this.storate = storate;
	}
	
}
