package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

public class SeparatedVacutinerVials implements Serializable{
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_LoadedVacutinersInCentrifuge_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "separatedVacutinerVialsId")
	private long id;
	@ManyToOne
	@JoinColumn(name = "centrifugationDataId")
	private CentrifugationData centrifugationData;
	@ManyToOne
	@JoinColumn(name = "subjectSampleCollectionTimePointsId")
	private SubjectSampleCollectionTimePoints subjectSampleCollectionTimePoints;
	private Date scanTime;
	private String aliquotNo;
	private int vialNo;
	
}
