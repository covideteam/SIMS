package com.covideinfo.dto;

import java.io.Serializable;

public class StudySampleStorageDto implements Serializable {

	private static final long serialVersionUID = 8864480437190271103L;
	private boolean storageDifferent;

	public boolean isStorageDifferent() {
		return storageDifferent;
	}

	public void setStorageDifferent(boolean storageDifferent) {
		this.storageDifferent = storageDifferent;
	}
	
	
}
