package com.covideinfo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "epk_subject_time_Point_vital_test_data")
public class SubjectTimePointVitalTestsData extends CommonMaster{

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_subject_time_Point_vital_test_data_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "subjectTimePointVtialTestsDataId")
	private long id;
	@ManyToOne
	@JoinColumn(name="subjectTimePointVitalTestsId")
	private SubjectTimePointVitalTests subjectTimePointVitalTests;
	private String value = "";
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public SubjectTimePointVitalTests getSubjectTimePointVitalTests() {
		return subjectTimePointVitalTests;
	}
	public void setSubjectTimePointVitalTests(SubjectTimePointVitalTests subjectTimePointVitalTests) {
		this.subjectTimePointVitalTests = subjectTimePointVitalTests;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
