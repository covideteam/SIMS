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
@Table(name="EFrom_Test_Labels")
public class EFromTestLabels extends CommonMaster{

	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="EFrom_Test_Labels_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="efromTestLabelsId")
	private long id;
	@ManyToOne
	@JoinColumn(name="formTitle")
	private RestrictionsComplianceMonitoring formTitle;
	private String label;
	@ManyToOne
	@JoinColumn(name="active_status")
	private StatusMaster activeStatus;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public RestrictionsComplianceMonitoring getFormTitle() {
		return formTitle;
	}
	public void setFormTitle(RestrictionsComplianceMonitoring formTitle) {
		this.formTitle = formTitle;
	}
	public StatusMaster getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(StatusMaster activeStatus) {
		this.activeStatus = activeStatus;
	}
	
}
