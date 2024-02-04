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
@Entity
@Table(name = "dataset_Phasewise_eformss")
public class DataSetPhasewiseEForms  extends CommonMaster{
	
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="dataset_Phasewise_eformss_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="dataSetPhasewiseEFormsId")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="phaseId")
	private DataSetPhase phasesId;
	@ManyToOne
	@JoinColumn(name="eformId")
	private EForm eform;
	
	@Column(name="secEleIds", length=10000)
	private String secEleIds;
	@Column(name="groupEleIds", length=10000)
	private String groupEleIds;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public DataSetPhase getPhasesId() {
		return phasesId;
	}
	public void setPhasesId(DataSetPhase phasesId) {
		this.phasesId = phasesId;
	}
	public EForm getEform() {
		return eform;
	}
	public void setEform(EForm eform) {
		this.eform = eform;
	}
	public String getSecEleIds() {
		return secEleIds;
	}
	public void setSecEleIds(String secEleIds) {
		this.secEleIds = secEleIds;
	}
	public String getGroupEleIds() {
		return groupEleIds;
	}
	public void setGroupEleIds(String groupEleIds) {
		this.groupEleIds = groupEleIds;
	}
		
	
}
