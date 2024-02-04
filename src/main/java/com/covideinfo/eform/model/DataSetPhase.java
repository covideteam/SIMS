package com.covideinfo.eform.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.covideinfo.model.CommonMaster;
import com.covideinfo.model.StudyPeriodMaster;

@Entity
@Table(name = "epk_dataset_phase")
public class DataSetPhase extends CommonMaster{
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="epk_dataset_phase_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="dataSetPhaseId")
	private long id;

	
	@ManyToOne
	@JoinColumn(name="dataSetId")
	private DataSet dataSetId;
	@ManyToOne
	@JoinColumn(name="phase")
	private StudyPeriodMaster phase;
	
	@OneToMany(cascade=CascadeType.PERSIST,  mappedBy="phasesId")
	private List<DataSetPhasewiseEForms> phases;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DataSet getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(DataSet dataSetId) {
		this.dataSetId = dataSetId;
	}

	public StudyPeriodMaster getPhase() {
		return phase;
	}

	public void setPhase(StudyPeriodMaster phase) {
		this.phase = phase;
	}

	public List<DataSetPhasewiseEForms> getPhases() {
		return phases;
	}

	public void setPhases(List<DataSetPhasewiseEForms> phases) {
		this.phases = phases;
	}
		
	
}
