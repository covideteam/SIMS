package com.covideinfo.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.InstrumentDao;
import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.InstrumentModel;
import com.covideinfo.model.InstrumentType;
import com.covideinfo.service.InstrumentService;


@Service("instrumentService")
public class InstrumentServiceImpl implements InstrumentService {

	@Autowired
	InstrumentDao instrumentDao;
	
	@Override
	public List<InstrumentModel> getInstrumentModelList() {
		return instrumentDao.getInstrumentModelList();
	}

	@Override
	public List<InstrumentType> getInstrumentTypeList() {
		return instrumentDao.getInstrumentTypeList();
	}

	@Override
	public boolean saveInstrumentForm(Instrument instrume, String manDate, String calDate) throws ParseException {
		boolean flag=false;
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 manDate = manDate +" 00:00:00";
		 calDate = calDate +" 00:00:00";
		 Date madate = sdf.parse(manDate);
		 Date caliDate = sdf.parse(calDate);
		 instrume.setManufacturingDate(madate);
		 instrume.setCalibrationDate(caliDate);
		 instrume.setCreatedOn(new Date());
		 flag = instrumentDao.saveInstrumentForm(instrume);
		
		return flag;
	}

	@Override
	public Instrument getInstrumentNoCheckExitOrNot(String value) {
		return instrumentDao.getInstrumentNoCheckExitOrNot(value);
	}

	@Override
	public List<Instrument> getInstrumentForDeepFrsszerList(List<Long> ids) {
	return instrumentDao.getInstrumentForDeepFrsszerList(ids);
	}

	@Override
	public List<Long> getInstrumentTypeForDeepfrezer(String deepf) {
		return instrumentDao.getInstrumentTypeForDeepfrezer(deepf);
	}

	
	@Override
	public boolean saveDeepfreserForm(Long instrument, Long shelfId, String rackCount, String userName) {
		
		boolean flag=false;
		List<Deepfreezer> defList=new ArrayList<>();
		int count=Integer.parseInt(rackCount);
		Instrument ins=instrumentDao.getInstrumentWithId(instrument);
		for(int i=1;i<=count;i++) {
			Deepfreezer racks = new Deepfreezer();
			racks.setCreatedBy(userName);
			racks.setCreatedOn(new Date());
			racks.setRackNo(i);
			racks.setInstrument(ins);
			defList.add(racks);
		}
		
		flag= instrumentDao.saveDeepfreserForm(defList);
		return flag;
	}
	
	@Override
	public boolean saveDeepfreserSeflfForm(Long instrument, String rackCount, String userName) {
		
		boolean flag=false;
		List<DeepfreezerShelf> defList=new ArrayList<>();
		int count=Integer.parseInt(rackCount);
		Instrument ins=instrumentDao.getInstrumentWithId(instrument);
		for(int i=1;i<=count;i++) {
			DeepfreezerShelf df=new DeepfreezerShelf();
			df.setCreatedBy(userName);
			df.setCreatedOn(new Date());
			df.setShelfNo(i);
			df.setInstrument(ins);
			defList.add(df);
		}
		flag= instrumentDao.saveDeepfreezerShelf(defList);
		return flag;
	}

	@Override
	public List<Deepfreezer> instrumentShelfs(Long insturmentId) {
		// TODO Auto-generated method stub
		return instrumentDao.instrumentShelfs(insturmentId);
	}

	

}
