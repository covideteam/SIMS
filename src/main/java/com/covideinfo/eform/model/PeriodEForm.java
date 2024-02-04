package com.covideinfo.eform.model;

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

import org.springframework.format.annotation.DateTimeFormat;

import com.covideinfo.model.CommonMaster;
import com.covideinfo.model.StudyPeriodMaster;

@Entity
@Table(name="Period_EForm")
public class PeriodEForm extends CommonMaster implements Serializable{

	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="Period_EForm_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="periodEformId")
	private long id;
	
	@ManyToOne
	@JoinColumn
	private StudyPeriodMaster period;
	private Long periodId;
	@Column(name = "is_active")	
	private boolean active = true;//status
	private String  crfName = "";
	@ManyToOne
	@JoinColumn(name="eformId")
	private EForm eform; // it is StudyCrf id
	private String exitCrf = "No"; // Yes
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public Long getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getCrfName() {
		return crfName;
	}
	public void setCrfName(String crfName) {
		this.crfName = crfName;
	}
	public EForm getEform() {
		return eform;
	}
	public void setEform(EForm eform) {
		this.eform = eform;
	}
	public String getExitCrf() {
		return exitCrf;
	}
	public void setExitCrf(String exitCrf) {
		this.exitCrf = exitCrf;
	}
	
	
}
