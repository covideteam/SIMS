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
import com.covideinfo.model.StatusMaster;

@Entity
@Table(name="epk_study_Restrictions_Compliance_Monitoring_Lables")
public class StudyRestrictionsComplianceMonitoringLables extends CommonMaster{
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="epk_study_Restrictions_Compliance_Monitoring_Lables_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="studyRestrictionsComplianceMonitoringLablesId")
	private long id;
	@ManyToOne
	@JoinColumn(name="studyRestrictionsComplianceMonitoringId")
	private StudyRestrictionsComplianceMonitoring studyRestrictionsComplianceMonitoring;
	private String label;
	@ManyToOne
	@JoinColumn(name="active_status")
	private StatusMaster activeStatus;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public StudyRestrictionsComplianceMonitoring getStudyRestrictionsComplianceMonitoring() {
		return studyRestrictionsComplianceMonitoring;
	}
	public void setStudyRestrictionsComplianceMonitoring(
			StudyRestrictionsComplianceMonitoring studyRestrictionsComplianceMonitoring) {
		this.studyRestrictionsComplianceMonitoring = studyRestrictionsComplianceMonitoring;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public StatusMaster getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(StatusMaster activeStatus) {
		this.activeStatus = activeStatus;
	}
}
