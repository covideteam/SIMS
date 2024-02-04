package com.covideinfo.service;

import java.util.List;

import com.covideinfo.model.DosageForm;

public interface DosageService {

	boolean saveDosage(DosageForm df, String username);

	List<DosageForm> getDosageIdCheck(String dosid);

	

}
