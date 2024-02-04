package com.covideinfo.dao;

import java.util.List;

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

public interface DrugReceptionDao {

	boolean saveDrugReception(DrugReception drung, List<DrugReceptionContainerBarcodes> barList, DocumentryRequirements document, DrugReceptionReviewAudit drrpojo);

	List<Projects> getEligibleProjectList();

	List<Regulatories> getRegulatoriesList();

	List<UnitsMaster> getUnitsMasterList();

	List<DrugReception> getDrugReceptionList();

	List<StudyDrugDispensing> getStudyDrugDispensingList();

	boolean saveStudyDrugDispensing(StudyDrugDispensing dispensing);

	StudyDrugDispensing getStudyDrugDispensingDataWithId(Long id);

	boolean updateStudyDrugDispensingData(StudyDrugDispensing dispensing, List<DrugDispensingContainerBarcodes> barList);

	List<StudyDrugDispensing> getStudyDrugDispensingInApprovalList();

	List<DrugReception> getDrugReceptionWithProjectIdAndRandomizationCode(Projects projectId, String randamizationCode);

	List<StudyDrugDispensing> getStudyDrugDispensingApprovalAndNotUpdatedList();

	DrugReception getDrugReceptionWithId(Long id);

	DocumentryRequirements getDocumentryRequirementsWithDrugId(Long id);

	boolean updateDrugReception(DrugReception drung, DocumentryRequirements document, DrugReceptionReviewAudit drrpojo);

	List<DrugReception> getDrugReceptionOnlyCompletedList();

	boolean drugReceptionApprovalSave(DrugReception drugData);

	List<DrugReception> getDrugReceptionListOnlyApproval();

	List<Projects> getEligibleProjectListForDrugDispensing(List<Long> ids, StatusMaster approved);

	StatusMaster statusMaster(String string);

	List<Long> getDrugReceptionSubmitList();

	UserMaster findByUsername(String username);

	DraftReviewStage getDrugReceptionStage(RoleMaster role);

	List<DrugReceptionReviewAudit> getDrugReceptionReviewAuditWithDrugReceptionId(Long id);

	DraftReviewStage getDrugReceptionReviewStageFirsyRow(Long rid);

	DraftReviewStage getDrugReceptionReviewStageWithreviewState(long reviewState);

	DraftReviewStage getDrugReceptionReviewStageWithTypeAndFromRole(RoleMaster toRole, String approvalType);

}
