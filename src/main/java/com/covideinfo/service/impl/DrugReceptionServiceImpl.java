package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.DrugReceptionDao;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.DocumentryRequirements;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.DrugDispensingContainerBarcodes;
import com.covideinfo.model.DrugReception;
import com.covideinfo.model.DrugReceptionContainerBarcodes;
import com.covideinfo.model.DrugReceptionReviewAudit;
import com.covideinfo.model.Projects;
import com.covideinfo.model.Regulatories;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyDrugDispensing;
import com.covideinfo.model.UnitsMaster;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.DrugReceptionService;

@Service("/drugReceptionService")
public class DrugReceptionServiceImpl implements DrugReceptionService {
   
	 @Autowired
	 DrugReceptionDao drugReceptionDao;
	
	
	@Override
	public boolean saveDrugReception(DrugReception drung,DocumentryRequirements document,DrugReceptionReviewAudit drrpojo) {
		int labelsize=drung.getNoofBoxsLabel();
		
		List<DrugReceptionContainerBarcodes> barList=new ArrayList<>();
		for(int i=0 ; i<labelsize; i++) {
			DrugReceptionContainerBarcodes barpojo=new DrugReceptionContainerBarcodes();
			barpojo.setDrugReceptionId(drung);
			barList.add(barpojo);
		}
		document.setDrugReceptionId(drung);
		return drugReceptionDao.saveDrugReception(drung,barList,document,drrpojo);
	}


	@Override
	public List<Projects> getEligibleProjectList() {
		return drugReceptionDao.getEligibleProjectList();
	}


	@Override
	public List<Regulatories> getRegulatoriesList() {
		return drugReceptionDao.getRegulatoriesList();
	}

	@Override
	public List<UnitsMaster> getUnitsMasterList() {
		return drugReceptionDao.getUnitsMasterList();
	}


	@Override
	public List<DrugReception> getDrugReceptionList() {
		return drugReceptionDao.getDrugReceptionList();
	}


	@Override
	public List<StudyDrugDispensing> getStudyDrugDispensingList() {
		return drugReceptionDao.getStudyDrugDispensingList();
	}


	@Override
	public boolean saveStudyDrugDispensing(StudyDrugDispensing dispensing) {
		return drugReceptionDao.saveStudyDrugDispensing(dispensing);
	}


	@Override
	public StudyDrugDispensing getStudyDrugDispensingDataWithId(Long id) {
		return drugReceptionDao.getStudyDrugDispensingDataWithId(id);
	}


	@Override
	public boolean updateStudyDrugDispensingData(StudyDrugDispensing dispensing) {
        int labelsize=dispensing.getNoOfBoxes();
		
		List<DrugDispensingContainerBarcodes> barList=new ArrayList<>();
		for(int i=0 ; i<labelsize; i++) {
			DrugDispensingContainerBarcodes barpojo=new DrugDispensingContainerBarcodes();
			barpojo.setStudyDrugDispensingId(dispensing);
			barList.add(barpojo);
		}
		return drugReceptionDao.updateStudyDrugDispensingData(dispensing,barList);
	}


	@Override
	public List<StudyDrugDispensing> getStudyDrugDispensingInApprovalList() {
		return drugReceptionDao.getStudyDrugDispensingInApprovalList();
	}


	@Override
	public List<DrugReception> getDrugReceptionWithProjectIdAndRandomizationCode(Projects projectId,
			String randamizationCode) {
		return drugReceptionDao.getDrugReceptionWithProjectIdAndRandomizationCode(projectId,randamizationCode);
	}


	@Override
	public List<StudyDrugDispensing> getStudyDrugDispensingApprovalAndNotUpdatedList() {
		return drugReceptionDao.getStudyDrugDispensingApprovalAndNotUpdatedList();
	}


	@Override
	public DrugReception getDrugReceptionWithId(Long id) {
		return drugReceptionDao.getDrugReceptionWithId(id);
	}


	@Override
	public DocumentryRequirements getDocumentryRequirementsWithDrugId(Long id) {
		return drugReceptionDao.getDocumentryRequirementsWithDrugId(id);
	}


	@Override
	public boolean updateDrugReception(DrugReception drung, DocumentryRequirements document,DrugReceptionReviewAudit drrpojo) {
		return drugReceptionDao.updateDrugReception(drung,document,drrpojo);
	}


	@Override
	public List<DrugReception> getDrugReceptionOnlyCompletedList() {
		return drugReceptionDao.getDrugReceptionOnlyCompletedList();
	}


	@Override
	public boolean drugReceptionApprovalSave(DrugReception drugData) {
		return drugReceptionDao.drugReceptionApprovalSave(drugData);
	}


	@Override
	public List<DrugReception> getDrugReceptionListOnlyApproval() {
		return drugReceptionDao.getDrugReceptionListOnlyApproval();
	}

	@Override
	public List<Projects> getEligibleProjectListForDrugDispensing(List<Long> ids) {
		StatusMaster approved = drugReceptionDao.statusMaster(StudyStatus.APPROVED.toString());
		return drugReceptionDao.getEligibleProjectListForDrugDispensing(ids,approved);
	}


	@Override
	public List<Long> getDrugReceptionSubmitList() {
		return drugReceptionDao.getDrugReceptionSubmitList();
	}


	@Override
	public UserMaster findByUsername(String username) {
		return drugReceptionDao.findByUsername(username);
	}


	@Override
	public DraftReviewStage getDrugReceptionStage(RoleMaster role) {
		return drugReceptionDao.getDrugReceptionStage(role);
	}


	@Override
	public List<DrugReceptionReviewAudit> getDrugReceptionReviewAuditWithDrugReceptionId(Long id) {
		return drugReceptionDao.getDrugReceptionReviewAuditWithDrugReceptionId(id);
	}


	@Override
	public DraftReviewStage getDrugReceptionReviewStageFirsyRow(Long rid) {
		return drugReceptionDao.getDrugReceptionReviewStageFirsyRow(rid);
	}


	@Override
	public DraftReviewStage getDrugReceptionReviewStageWithreviewState(long reviewState) {
		return drugReceptionDao.getDrugReceptionReviewStageWithreviewState(reviewState);
	}


	@Override
	public DraftReviewStage getDrugReceptionReviewStageWithTypeAndFromRole(RoleMaster toRole, String approvalType) {
		return drugReceptionDao.getDrugReceptionReviewStageWithTypeAndFromRole(toRole,approvalType);
	}

}
