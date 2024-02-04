package com.covideinfo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="study_deviation_codes")
public class StudyDeviationCodes implements Serializable {
	
	private static final long serialVersionUID = -9051319475846443415L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_deviation_codes_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private long id;
	@Column(name="deviation_code")
	private String deviationCode;
	@Column(name="deviation_type")
	private String deviationType;//Place deviation Raised Activity Name
	public long getId() {
		return id;
	}
	public String getDeviationCode() {
		return deviationCode;
	}
	public String getDeviationType() {
		return deviationType;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setDeviationCode(String deviationCode) {
		this.deviationCode = deviationCode;
	}
	public void setDeviationType(String deviationType) {
		this.deviationType = deviationType;
	}

}
