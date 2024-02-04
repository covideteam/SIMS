package com.covideinfo.dto;

import java.io.Serializable;

public class StudySampleCentrifugationDto implements Serializable {
	private static final long serialVersionUID = -1287645756735339035L;
	private boolean centrifugationApplicable;

	public boolean isCentrifugationApplicable() {
		return centrifugationApplicable;
	}

	public void setCentrifugationApplicable(boolean centrifugationApplicable) {
		this.centrifugationApplicable = centrifugationApplicable;
	}
	
	

}
