package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.model.DosageForm;

public interface DosageDao {

	boolean saveDosage(DosageForm df, String username);

	List<DosageForm> getDosageIdCheck(String dosid);


}
