package com.covideinfo.model;

import java.util.List;

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
@Table(name="epk_Samples_Centrifugation")
public class SamplesCentrifugation extends CommonMaster{
	

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_Samples_Centrifugation_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "samplesCentrifugationId")
	private long id;

	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	private boolean centrifugationApplicable=true;
	@ManyToOne
	@JoinColumn(name = "centrifugationApplicableForId")
	private FromStaticData centrifugationApplicableFor;
	private int allowedTime; 
	private int speed; 
	private int processTime;
	private String temperature = "";
	private int temperatureWindow;
	private String temperatureWindowSign = "";
	public FromStaticData getCentrifugationApplicableFor() {
		return centrifugationApplicableFor;
	}
	public void setCentrifugationApplicableFor(FromStaticData centrifugationApplicableFor) {
		this.centrifugationApplicableFor = centrifugationApplicableFor;
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
	public boolean isCentrifugationApplicable() {
		return centrifugationApplicable;
	}
	public void setCentrifugationApplicable(boolean centrifugationApplicable) {
		this.centrifugationApplicable = centrifugationApplicable;
	}
	public int getAllowedTime() {
		return allowedTime;
	}
	public void setAllowedTime(int allowedTime) {
		this.allowedTime = allowedTime;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getProcessTime() {
		return processTime;
	}
	public void setProcessTime(int processTime) {
		this.processTime = processTime;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public int getTemperatureWindow() {
		return temperatureWindow;
	}
	public void setTemperatureWindow(int temperatureWindow) {
		this.temperatureWindow = temperatureWindow;
	}
	public String getTemperatureWindowSign() {
		return temperatureWindowSign;
	}
	public void setTemperatureWindowSign(String temperatureWindowSign) {
		this.temperatureWindowSign = temperatureWindowSign;
	}
	
	
}
