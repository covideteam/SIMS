package com.covideinfo.service;

import java.text.ParseException;
import java.util.List;

import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.InstrumentModel;
import com.covideinfo.model.InstrumentType;

public interface InstrumentService {

	List<InstrumentModel> getInstrumentModelList();

	List<InstrumentType> getInstrumentTypeList();

	boolean saveInstrumentForm(Instrument instrume, String manDate, String calDate) throws ParseException;

	Instrument getInstrumentNoCheckExitOrNot(String value);

	List<Instrument> getInstrumentForDeepFrsszerList(List<Long> ids);

	List<Long> getInstrumentTypeForDeepfrezer(String deepf);

	boolean saveDeepfreserForm(Long instrument, Long shelfId, String rackCount, String userName);

	boolean saveDeepfreserSeflfForm(Long instrument, String rackCount, String userName);

	List<Deepfreezer> instrumentShelfs(Long insturmentId);

}
