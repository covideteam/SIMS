package com.covideinfo.service;

import java.util.List;

import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.UnitsMaster;

public interface UnitService {

	List<InternationalizaionLanguages> getLanguagesList();

	UnitsMaster checkUnitsName(String name);

	String saveUnitsDetails(UnitsMaster units, String username);

	List<UnitsMaster> geUnitsMasterList();

}
