package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.UnitsMaster;

public interface UnitsDao {

	List<InternationalizaionLanguages> getLanguagesList();

	UnitsMaster checkUnitsName(String name);

	boolean saveUnitDetails(UnitsMaster units);

	List<UnitsMaster> geUnitsMasterList();

}
