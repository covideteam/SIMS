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

import com.covideinfo.model.AdditionalAssesment;
import com.covideinfo.model.CommonMaster;
import com.covideinfo.model.StatusMaster;

@Entity
@Table(name="Additional_Assesment_EForm")
public class AdditionalAssesmentEForm extends CommonMaster{
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="Additional_Assesment_EForm_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="additionalAssesmentEFormId")
	private long id;
	
	
	@ManyToOne
	@JoinColumn(name="additionalAssesmentId")
	private AdditionalAssesment additionalAssesment;
	@ManyToOne
	@JoinColumn(name="eformId")
	private EForm eform;
	@ManyToOne
	@JoinColumn(name="active_status")
	private StatusMaster activeStatus;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public AdditionalAssesment getAdditionalAssesment() {
		return additionalAssesment;
	}
	public void setAdditionalAssesment(AdditionalAssesment additionalAssesment) {
		this.additionalAssesment = additionalAssesment;
	}
	public EForm getEform() {
		return eform;
	}
	public void setEform(EForm eform) {
		this.eform = eform;
	}
	
}
