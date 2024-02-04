package com.covideinfo.model;

import javax.persistence.CascadeType;
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
@Table(name = "epk_Time_Point_vital_test")
public class TimePointVitalTests extends CommonMaster {
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_Time_Point_vital_test_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "timePintVitalTestsId")
	private long id;

	@ManyToOne
	@JoinColumn(name = "testId")
	private VitalTest testId;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "vitalTimePointId")
	private VitalTimePoints vitalTimePointId;
	private String minimum;
	private String maximum;
	private boolean rangeRequired;
	
	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public boolean isRangeRequired() {
		return rangeRequired;
	}

	public void setRangeRequired(boolean rangeRequired) {
		this.rangeRequired = rangeRequired;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public VitalTest getTestId() {
		return testId;
	}

	public void setTestId(VitalTest testId) {
		this.testId = testId;
	}

	public VitalTimePoints getVitalTimePointId() {
		return vitalTimePointId;
	}

	public void setVitalTimePointId(VitalTimePoints vitalTimePointId) {
		this.vitalTimePointId = vitalTimePointId;
	}

}
