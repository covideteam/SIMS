package com.covideinfo.model;

import java.util.Date;
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
import javax.persistence.Transient;

import org.springframework.web.bind.annotation.RequestParam;

@Entity
@Table(name = "epk_CentrifugationData")
public class CentrifugationData extends CommonMaster{
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_CentrifugationData_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "centrifugationDataId")
	private long id;
//	@ManyToOne
//	@joincolumn(name = "samplecentrifugationid")
//	private samplesCentrifugation sampleCentrifugation;

	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "periodId")
	private StudyPeriodMaster period;
	@ManyToOne
	@JoinColumn(name = "sampleTimePointsId")
	private SampleTimePoints sampleTimePoints;
	@ManyToOne
	@JoinColumn(name = "centrifugationDataMasterId")
	private CentrifugationDataMaster centrifugationDataMaster;
	@Transient
	List<LoadedVacutinersInCentrifuge> vacutaineres;

	public CentrifugationDataMaster getCentrifugationDataMaster() {
		return centrifugationDataMaster;
	}

	public void setCentrifugationDataMaster(CentrifugationDataMaster centrifugationDataMaster) {
		this.centrifugationDataMaster = centrifugationDataMaster;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

//	public SamplesCentrifugation getSampleCentrifugation() {
//		return sampleCentrifugation;
//	}
//
//	public void setSampleCentrifugation(SamplesCentrifugation sampleCentrifugation) {
//		this.sampleCentrifugation = sampleCentrifugation;
//	}

	public StudyMaster getStudy() {
		return study;
	}

	public void setStudy(StudyMaster study) {
		this.study = study;
	}

	public StudyPeriodMaster getPeriod() {
		return period;
	}

	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}

	public SampleTimePoints getSampleTimePoints() {
		return sampleTimePoints;
	}

	public void setSampleTimePoints(SampleTimePoints sampleTimePoints) {
		this.sampleTimePoints = sampleTimePoints;
	}

	public List<LoadedVacutinersInCentrifuge> getVacutaineres() {
		return vacutaineres;
	}

	public void setVacutaineres(List<LoadedVacutinersInCentrifuge> vacutaineres) {
		this.vacutaineres = vacutaineres;
	}
	
		
}
