package com.covideinfo.eform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.covideinfo.model.CommonMaster;
import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;

@Entity
@Table(name="epk_study_Restrictions_Compliance_Monitoring")
public class StudyRestrictionsComplianceMonitoring extends CommonMaster{
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="epk_study_Restrictions_Compliance_Monitoring_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="studyRestrictionsComplianceMonitoringId")
	private long id;
	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name="restrictionFor")
	private FromStaticData restrictionFor; //Check in/Pre Dose/Post Dose/Check Out/Ambulatory/Other
	private String title;  // Check in/Pre Dose/Post Dose/Check Out/Ambulatory/Other/INCLUSION CRITERIA/EXCLUSION CRITERIA
	@ManyToOne
	@JoinColumn(name="activeStatus")
	private StatusMaster activeStatus;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public FromStaticData getRestrictionFor() {
		return restrictionFor;
	}
	public void setRestrictionFor(FromStaticData restrictionFor) {
		this.restrictionFor = restrictionFor;
	}
	public StatusMaster getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(StatusMaster activeStatus) {
		this.activeStatus = activeStatus;
	}
	
}
