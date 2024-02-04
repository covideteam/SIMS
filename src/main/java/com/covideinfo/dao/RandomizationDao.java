package com.covideinfo.dao;

import java.util.List;

import org.eclipse.core.internal.resources.Project;

import com.covideinfo.dto.RandomizationViewDto;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.ProjectDetailsRandomization;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.RandomizationFileStatus;
import com.covideinfo.model.RandomizationReviewAudit;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.TreatmentInfo;

public interface RandomizationDao {

	String saveUploadRandomization(List<ProjectDetailsRandomization> pdrdataFinal, RandomizationReviewAudit rra, Projects poject, RandomizationFileStatus rfs);

	List<Projects> getAllProjectsList();

	List<ProjectDetailsRandomization> getRandomExitOrNot(Long projectNoVal);

	boolean updateStatusRandomization(List<ProjectDetailsRandomization> randomList, String username);

	ProjectDetailsRandomization getProjectDetailsRandomizationWithSubjectAndPeriod(ProjectDetailsRandomization ch);

	String updateUploadRandomization(List<ProjectDetailsRandomization> addData,
			List<ProjectDetailsRandomization> updateData, RandomizationReviewAudit rra, Projects poject, RandomizationFileStatus rfsupdate);

	List<StudyMaster> getAllStudyList();

	StudyMaster getStudyMasterWithId(Long projectNoVal);

	List<TreatmentInfo> getTreatmentInfoWithStudyId(StudyMaster sm);

	List<ProjectDetailsRandomization> getProjectDetailsRandomizationWithStudyId(Long id);

	DraftReviewStage getRandomizationReviewStage(RoleMaster role);

	List<RandomizationReviewAudit> getRandomizationReviewAuditWithStudyId(Long id);

	DraftReviewStage getRandomizationReviewStageWithreviewState(long reviewState);

	DraftReviewStage getRandomizationReviewStageWithTypeFromRole(RoleMaster fromRole, String approvalType, long l);

	boolean saveRandomizationApproval(RandomizationReviewAudit rra, Projects poject, RandomizationFileStatus rfsupdate);

	StudyPeriodMaster getStudyPeriodMasterWithStudyAndPeriodName(Long id, String period);

	TreatmentInfo getTreatmentInfoRamdomizationCode(String radamizationCode, Long studyid);

	boolean saveRandomizationApprovalFinal(RandomizationReviewAudit rra, List<SubjectRandamization> subjectRamList, List<SubjectRandamization> subjectRamUpdate, Projects poject, long studyid);

	SubjectRandamization getSubjectRandamizationExitCheck(StudyPeriodMaster pd, String subjectNo, TreatmentInfo ti);

	DraftReviewStage getRandomizationReviewStageFirsyRow(Long rid);

	Projects getProjectsWithProjectNo(String projectNo);

	List<StudyMaster> getStudyMasterForRandomization(List<String> projectNo);

	List<Projects> getProjectForRandomization();

	List<Projects> getProjectListOnlySubjetcsAndPeriodsAndRandomizationInputCompletedProjects();

	List<ProjectDetailsRandomization> getProjectDetailsRandomizationWithProjectId(Long id);

	List<RandomizationReviewAudit> getRandomizationReviewAuditWithProjectId(Long id);

	Projects getProjectsWithProjectId(Long projectNoVal);

	ProjectsDetails getProjectsDetailsForNoofSubjectWithProject(Projects poject);

	ProjectsDetails getProjectsDetailsForNoofPeriodWithProject(Projects poject);

	StudyMaster getStudyMasterWithProjectNo(String projectNo);

	RandomizationFileStatus getRandomizationFileStatusWithProjectId(long projectId);

	List<ProjectsDetails> getProjectsDetailsRandomizationCode(Projects poject);

	List<ProjectDetailsRandomization> getProjectDetailsRandomizationForSplit(RandomizationViewDto data);

	ProjectsDetails getProjectsDetailsWithNoofPeriods(Long id);

	DraftReviewStage getRandomizationReviewStageWithTypeAndFromRole(RoleMaster toRole, String approvalType);
	

}
