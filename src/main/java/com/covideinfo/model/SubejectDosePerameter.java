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
@Table(name = "epk_subject_dose_perameter")
public class SubejectDosePerameter extends CommonMaster{
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_subject_dose_perameter_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "subejectDosePerameterId")
	private long id;
	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "periodId")
	private StudyPeriodMaster period;
	@ManyToOne
	@JoinColumn(name = "treatmentInfoId")
	private TreatmentInfo treatmentInfo;
	@ManyToOne
	@JoinColumn(name = "dosePerameterId")
	private DosePerameter dosePerameter;
	@ManyToOne
	@JoinColumn(name = "doseTimePointId")
	private SubjectDoseTimePoints doseTimePoint;
	private String subjectNo;
	private int subjectOrder;
	private String perameterValue;
	
	public SubjectDoseTimePoints getDoseTimePoint() {
		return doseTimePoint;
	}
	public void setDoseTimePoint(SubjectDoseTimePoints doseTimePoint) {
		this.doseTimePoint = doseTimePoint;
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
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public TreatmentInfo getTreatmentInfo() {
		return treatmentInfo;
	}
	public void setTreatmentInfo(TreatmentInfo treatmentInfo) {
		this.treatmentInfo = treatmentInfo;
	}
	public DosePerameter getDosePerameter() {
		return dosePerameter;
	}
	public void setDosePerameter(DosePerameter dosePerameter) {
		this.dosePerameter = dosePerameter;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public int getSubjectOrder() {
		return subjectOrder;
	}
	public void setSubjectOrder(int subjectOrder) {
		this.subjectOrder = subjectOrder;
	}
	public String getPerameterValue() {
		return perameterValue;
	}
	public void setPerameterValue(String perameterValue) {
		this.perameterValue = perameterValue;
	}
	
	
}
