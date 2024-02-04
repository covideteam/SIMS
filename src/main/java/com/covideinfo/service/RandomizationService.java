package com.covideinfo.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.eclipse.core.internal.resources.Project;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.covideinfo.dto.RandomizationViewDto;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.ProjectDetailsRandomization;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.RandomizationFileStatus;
import com.covideinfo.model.RandomizationReviewAudit;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserMaster;

public interface RandomizationService {

	String saveUploadRandomization(CommonsMultipartFile file, HttpSession session, Long projectNoVal, String username, String commentsf) throws IOException;

	List<Projects> getAllProjectsList();

	List<StudyMaster> getAllStudyList();

	List<ProjectDetailsRandomization> getProjectDetailsRandomizationWithStudyId(Long id);

	List<RandomizationReviewAudit> getRandomizationReviewAuditWithStudyId(Long id);

	DraftReviewStage getRandomizationReviewStageWithreviewState(long reviewState);

	DraftReviewStage getRandomizationReviewStageWithTypeFromRole(RoleMaster fromRole, String approvalType, long l);

	boolean saveRandomizationApproval(DraftReviewStage stage, Long id, UserMaster checkLoginUser, String commentsval, String approvalType, Projects poject, RandomizationFileStatus rfsupdate);

	boolean saveRandomizationApprovalFinal(DraftReviewStage stage, Long id, UserMaster checkLoginUser,
			String commentsval, String approvalType, List<ProjectDetailsRandomization> randomList, Projects poject, long studyid);

	DraftReviewStage getRandomizationReviewStageFirsyRow(Long rid);

	Projects getProjectsWithProjectNo(String projectNo);

	List<StudyMaster> getStudyMasterForRandomization(List<String> projectNo);

	List<Projects> getProjectForRandomization();

	List<Projects> getProjectListOnlySubjetcsAndPeriodsAndRandomizationInputCompletedProjects();

	List<ProjectDetailsRandomization> getProjectDetailsRandomizationWithProjectId(Long id);

	List<RandomizationReviewAudit> getRandomizationReviewAuditWithProjectId(Long id);

	Projects getProjectsWithProjectId(Long id);

	List<ProjectDetailsRandomization> getProjectDetailsRandomizationForSplit(RandomizationViewDto data);

	StudyMaster getStudyMasterWithProjectNo(String projectNo);

	ProjectsDetails getProjectsDetailsWithNoofPeriods(Long id);

	DraftReviewStage getRandomizationReviewStageWithTypeAndFromRole(RoleMaster toRole, String approvalType);


}
