package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.InstrumentModel;
import com.covideinfo.model.InstrumentType;

public interface InstrumentDao {

	List<InstrumentModel> getInstrumentModelList();

	List<InstrumentType> getInstrumentTypeList();

	boolean saveInstrumentForm(Instrument instrume);

	Instrument getInstrumentNoCheckExitOrNot(String value);

	List<Instrument> getInstrumentForDeepFrsszerList(List<Long> ids);

	List<Long> getInstrumentTypeForDeepfrezer(String deepf);

	Instrument getInstrumentWithId(Long instrument);

	boolean saveDeepfreserForm(List<Deepfreezer> defList);

	List<Deepfreezer> instrumentShelfs(Long insturmentId);

	Deepfreezer sehelfDetails(Long shelfId);

	boolean saveDeepfreezerShelf(List<DeepfreezerShelf> defList);

}
