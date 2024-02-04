package com.covideinfo.dao;

import java.util.List;

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

public interface PharmacyDao {

	List<LineClearanceActivity> getLineClearanceActivityList();

	boolean saveLineClearanceActivity(LineClearanceActivity lineform);

	List<DispensedIPHandover> getDispensedIPHandoverList();

	boolean saveDispensedIPHandover(DispensedIPHandover ipHandform);

	List<IPRetention> getipRetentionList();

	boolean saveIPRetention(IPRetention ippojo);

	List<StatusOfUnusedDispensed> getStatusOfUnusedDispensedList();

	boolean saveStatusOfUnusedDispensed(StatusOfUnusedDispensed soupojo);

	List<IPDiscard> getIPDiscardList();

	boolean saveIPDiscard(IPDiscard ippojo);

	ProjectsDetails getSponsorCodeWithProjectId(Long proid);

	StudyDrugDispensing getStudyDrugDispensingWithId(Long id);

	boolean updateStudyDrugDispensingApproval(StudyDrugDispensing sdd);

	List<StudyPeriodMaster> getPeriodDataBasedonProjectId(long id);

	List<DrugReception> getDrugReceptionBasedonProjectId(long id);

	List<ProjectsDetails> getRandamizationCodeWithProjectId(long id);

}
