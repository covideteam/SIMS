package com.covideinfo.dao;

import java.util.List;

import org.springframework.core.env.Environment;

import com.covideinfo.dto.CommentsDto;
import com.covideinfo.dto.ProjectDetailsDto;
import com.covideinfo.dto.StudyDesignDto;
import com.covideinfo.model.ActivityDraftReviewAudit;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.DiscrepancyModel;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.RandomizationFileStatus;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyActivityRules;
import com.covideinfo.model.StudyDiscrepancy;
import com.covideinfo.model.dummy.ProjectDetailsKeyVal;
import com.covideinfo.model.dummy.ProjectKeyVal;

public interface StudyDesignDao {

	boolean getAutoSaveData(Projects ps, List<ProjectsDetails> pdp);

	ProjectDetailsDto getProjectsDetails(Long projectId, Long languageId, String format);

	List<Projects> getProjectsList(long roleid);

	Projects getProjectWithId(Long projectId);

	Projects getProjectsWithProjectNo(String prod1);

	ProjectsDetails getProjectsDetails(Projects pdpojo, String prod2, int prod4, int prod5,String type);

	boolean getUpdateAutoSaveData(ProjectsDetails pd);

	boolean getNewAutoSaveData(ProjectsDetails pdp);

	ProjectsDetails getProjectsDetailsWithpdId(long id);

	StudyDiscrepancy saveDiscrepancy(CommentsDto projectKeyValue,String username);

	StudyDiscrepancy getStudyDiscrepancyWithId(long id);
	
	List<DiscrepancyModel> getDescrepencies(ProjectKeyVal keyValue,Environment environment);

	boolean updateDiscrepancy(StudyDiscrepancy sdpojo);

	DraftReviewStage getDraftReviewStageWithRoleidAndActionname(long roleid, String actionname);

	Projects saveActivityDraftReviewAudit(ActivityDraftReviewAudit adra, Projects poject);

	List<StudyDiscrepancy> getStudyDiscrepancyExitOrNot(Long projectId);

	List<Projects> getEligibleProjectList();
	
	ProjectsDetails getProjectsDetails(long id, String prod2, int prod4, int prod5,String type);

	Projects getProjectsWithProjectId(long id);

	StatusMaster getStatusMasterForSubmit(String string);

	boolean getUpdateProjectData(Projects ppojo);

	List<Projects> getProjectAllList();

	boolean saveProjectClone(Projects ps, List<ProjectsDetails> pdList,String userName);
	
	void deActivateProjectDetailsData(long projectId,int rowNumber,int subRowNumber, String type);

	List<Long> getDraftReviewStageDraftStage();

	List<Projects> getProjectsListForDraft(List<Long> list);

	RandomizationFileStatus getRandomizationFileStatusProWithId(Long projectNoVal);

	StudyDesignDto getStudyDesignDtoDetails(ProjectDetailsKeyVal prod, String username, long res);

	List<ActivityDraftReviewAudit> getActivityDraftReviewAuditListWithProjectId(long projectId);

	DraftReviewStage getDraftReviewStageWithRoleidAndActionname2(Long roleId, String string, long res);
	
	void updateOtherActivityParameters(Long projectId,Long parameterId,Long activityId, boolean isDelete, String userName);

	CustomActivityParameters checkCustomActivityParameterExistsOrNot(Long activityId, Long parameterId);
	
	Integer addActivityParameter(long projectId, Long activityId, Long parameterId, String userName);

	ProjectDetailsDto getProjectsDetailsVal(long projectId, String format);

	void deleteComment(long commentId, String username);
}
