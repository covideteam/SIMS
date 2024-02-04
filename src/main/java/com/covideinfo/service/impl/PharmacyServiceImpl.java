package com.covideinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.PharmacyDao;
import com.covideinfo.model.DispensedIPHandover;
import com.covideinfo.model.DrugReception;
import com.covideinfo.model.IPDiscard;
import com.covideinfo.model.IPRetention;
import com.covideinfo.model.LineClearanceActivity;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.StatusOfUnusedDispensed;
import com.covideinfo.model.StudyDrugDispensing;
import com.covideinfo.model.StudyGroupPeriodMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.service.PharmacyService;

@Service("pharmacyService")
public class PharmacyServiceImpl implements PharmacyService{
	
	@Autowired
	PharmacyDao pharmacyDao;

	@Override
	public List<LineClearanceActivity> getLineClearanceActivityList() {
		return pharmacyDao.getLineClearanceActivityList();
	}

	@Override
	public boolean saveLineClearanceActivity(LineClearanceActivity lineform) {
		return pharmacyDao.saveLineClearanceActivity(lineform);
	}

	@Override
	public List<DispensedIPHandover> getDispensedIPHandoverList() {
		return pharmacyDao.getDispensedIPHandoverList();
	}

	@Override
	public boolean saveDispensedIPHandover(DispensedIPHandover ipHandform) {
		return pharmacyDao.saveDispensedIPHandover(ipHandform);
	}

	@Override
	public List<IPRetention> getipRetentionList() {
		return pharmacyDao.getipRetentionList();
	}

	@Override
	public boolean saveIPRetention(IPRetention ippojo) {
		return pharmacyDao.saveIPRetention(ippojo);
	}

	@Override
	public List<StatusOfUnusedDispensed> getStatusOfUnusedDispensedList() {
		return pharmacyDao.getStatusOfUnusedDispensedList();
	}

	@Override
	public boolean saveStatusOfUnusedDispensed(StatusOfUnusedDispensed soupojo) {
		return pharmacyDao.saveStatusOfUnusedDispensed(soupojo);
	}

	@Override
	public List<IPDiscard> getIPDiscardList() {
		return pharmacyDao.getIPDiscardList();
	}

	@Override
	public boolean saveIPDiscard(IPDiscard ippojo) {
		return pharmacyDao.saveIPDiscard(ippojo);
	}

	@Override
	public ProjectsDetails getSponsorCodeWithProjectId(Long proid) {
		return pharmacyDao.getSponsorCodeWithProjectId(proid);
	}

	@Override
	public StudyDrugDispensing getStudyDrugDispensingWithId(Long id) {
		return pharmacyDao.getStudyDrugDispensingWithId(id);
	}

	@Override
	public boolean updateStudyDrugDispensingApproval(StudyDrugDispensing sdd) {
		return pharmacyDao.updateStudyDrugDispensingApproval(sdd);
	}

	@Override
	public List<StudyPeriodMaster> getPeriodDataBasedonProjectId(long id) {
		return pharmacyDao.getPeriodDataBasedonProjectId(id);
	}

	@Override
	public List<DrugReception> getDrugReceptionBasedonProjectId(long id) {
		return pharmacyDao.getDrugReceptionBasedonProjectId(id);
	}

	@Override
	public List<ProjectsDetails> getRandamizationCodeWithProjectId(long id) {
		return pharmacyDao.getRandamizationCodeWithProjectId(id);
	}

}
