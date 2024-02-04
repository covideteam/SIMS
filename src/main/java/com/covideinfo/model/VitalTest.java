package com.covideinfo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "epk_vital_test")
public class VitalTest extends CommonMaster {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3369839337203397847L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_vital_test_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "vitalTestId")
	private long id;
	private String testName;
	private String minimum;
	private String minimumCondition;
	private String maximum;
	private String maximumCondition;
	private boolean activeStatus = true;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public boolean isActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public String getMinimumCondition() {
		return minimumCondition;
	}

	public void setMinimumCondition(String minimumCondition) {
		this.minimumCondition = minimumCondition;
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public String getMaximumCondition() {
		return maximumCondition;
	}

	public void setMaximumCondition(String maximumCondition) {
		this.maximumCondition = maximumCondition;
	}

}
