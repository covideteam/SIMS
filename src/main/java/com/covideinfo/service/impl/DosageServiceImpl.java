package com.covideinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.DosageDao;
import com.covideinfo.model.DosageForm;
import com.covideinfo.service.DosageService;

@Service("dosageService")
public class DosageServiceImpl implements DosageService {

	@Autowired
	DosageDao dosageDao;

	@Override
	public boolean saveDosage(DosageForm df, String username) {
		// TODO Auto-generated method stub
		return dosageDao.saveDosage(df,username);
	}

	@Override
	public List<DosageForm> getDosageIdCheck(String dosid) {
		// TODO Auto-generated method stub
		return dosageDao.getDosageIdCheck(dosid);
	}

	
}
