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
@Table(name="epk_sample_process_configuration")
public class SampleProcessConfiguration extends CommonMaster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6160290681094968147L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_sample_process_configuration_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "sampleProcessConfigurationId")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "samplesCentrifugationId")
	private SamplesCentrifugation samplesCentrifugation;
	@ManyToOne
	@JoinColumn(name = "sampleStorageId")
	private SampleStorage sampleStorage;
	private boolean matrix;
	@ManyToOne
	@JoinColumn(name = "sampleProcessId")
	private SampleProcess sampleProcess;
	@ManyToOne
	@JoinColumn(name = "label")
	private FromStaticData label;
	private boolean activeStatus = true;
	
	public boolean isMatrix() {
		return matrix;
	}
	public void setMatrix(boolean matrix) {
		this.matrix = matrix;
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
	public SamplesCentrifugation getSamplesCentrifugation() {
		return samplesCentrifugation;
	}
	public void setSamplesCentrifugation(SamplesCentrifugation samplesCentrifugation) {
		this.samplesCentrifugation = samplesCentrifugation;
	}
	public SampleStorage getSampleStorage() {
		return sampleStorage;
	}
	public void setSampleStorage(SampleStorage sampleStorage) {
		this.sampleStorage = sampleStorage;
	}
	public SampleProcess getSampleProcess() {
		return sampleProcess;
	}
	public void setSampleProcess(SampleProcess sampleProcess) {
		this.sampleProcess = sampleProcess;
	}
	
	public FromStaticData getLabel() {
		return label;
	}
	public void setLabel(FromStaticData label) {
		this.label = label;
	}
	public boolean isActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	
	
}
