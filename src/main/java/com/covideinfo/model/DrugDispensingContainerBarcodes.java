package com.covideinfo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="DrugDispensing_ContainerBarcodes")
public class DrugDispensingContainerBarcodes implements Serializable {
	
	private static final long serialVersionUID = -2076178688627767872L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="drugDispensing_ContainerBarcodes_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="studyDrugDispensingId")
	private StudyDrugDispensing studyDrugDispensingId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public StudyDrugDispensing getStudyDrugDispensingId() {
		return studyDrugDispensingId;
	}
	public void setStudyDrugDispensingId(StudyDrugDispensing studyDrugDispensingId) {
		this.studyDrugDispensingId = studyDrugDispensingId;
	}
	
	
	
	
	

}
