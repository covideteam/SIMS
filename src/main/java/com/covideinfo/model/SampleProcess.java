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
@Table(name="epk_sample_Process")
public class SampleProcess extends CommonMaster{

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_sample_Process_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "sampleProcessId")
	private long id;

	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	private boolean matrixDiffenret;
	private String aliquotsVolume;
	private int allowedTime; 
	@ManyToOne
	@JoinColumn(name = "allowedTimeFrom")
	private FromStaticData allowedTimeFrom;
	@ManyToOne
	@JoinColumn(name = "aliquots1Matrix")
	private FromStaticData aliquots1Matrix;
	
	public FromStaticData getAliquots1Matrix() {
		return aliquots1Matrix;
	}
	public void setAliquots1Matrix(FromStaticData aliquots1Matrix) {
		this.aliquots1Matrix = aliquots1Matrix;
	}
	public String getAliquotsVolume() {
		return aliquotsVolume;
	}
	public void setAliquotsVolume(String aliquotsVolume) {
		this.aliquotsVolume = aliquotsVolume;
	}
	public int getAllowedTime() {
		return allowedTime;
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
	public void setAllowedTime(int allowedTime) {
		this.allowedTime = allowedTime;
	}
	public FromStaticData getAllowedTimeFrom() {
		return allowedTimeFrom;
	}
	public void setAllowedTimeFrom(FromStaticData allowedTimeFrom) {
		this.allowedTimeFrom = allowedTimeFrom;
	}
	public boolean isMatrixDiffenret() {
		return matrixDiffenret;
	}
	public void setMatrixDiffenret(boolean matrixDiffenret) {
		this.matrixDiffenret = matrixDiffenret;
	}
	
}
