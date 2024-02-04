package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

import com.covideinfo.dto.CommentsDto;
import com.covideinfo.dto.ProjectDetailsDto;
import com.covideinfo.enums.ActionTypes;
import com.covideinfo.model.ActivityDraftReviewAudit;
import com.covideinfo.model.DiscrepancyModel;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyDiscrepancy;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.dummy.ProjectDetailsKeyVal;
import com.covideinfo.model.dummy.ProjectKeyVal;

public interface StudyDesignService {

	boolean getAutoSaveData(Projects ps, List<ProjectsDetails> pdp);

	ProjectDetailsDto getProjectsDetails(Long projectId, Long languageId, String format);

	List<Projects> getProjectsList(Long roleid);

	Projects getProjectWithId(Long projectId);

	Projects getProjectsWithProjectNo(String prod1);

	ProjectsDetails getProjectsDetails(Projects pdpojo, String prod2, int prod4, int prod5,String type);

	boolean getUpdateAutoSaveData(ProjectsDetails pd);

	boolean getNewAutoSaveData(ProjectsDetails pdp);

	ProjectsDetails getProjectsDetailsWithpdId(long id);

	StudyDiscrepancy saveDiscrepancy(CommentsDto projectKeyValue,String username);

	StudyDiscrepancy getStudyDiscrepancyWithId(long id);
	
	List<DiscrepancyModel> getDescrepencies(ProjectKeyVal keyValue, Environment evnvironment) throws Exception;

	boolean updateDiscrepancy(StudyDiscrepancy sdpojo);

	DraftReviewStage getDraftReviewStageWithRoleidAndActionname(long roleid, String actionname);

	Projects saveActivityDraftReviewAudit(Long projectId, Long roleId, ActionTypes actionType, UserMaster checkLoginUser);
	
	Projects saveActivityDraftReviewAudit(ActivityDraftReviewAudit adra,Projects poject);

	List<StudyDiscrepancy> getStudyDiscrepancyExitOrNot(Long projectId);

	List<Projects> getEligibleProjectList();

	Projects  testSaveStudyInfo(int testNo, UserMaster userId);
	
	ProjectsDetails getProjectsDetails(long id, String prod2, int prod4, int prod5,String type);

	Projects getProjectsWithProjectId(long id);

	StatusMaster getStatusMasterForSubmit(String string);

	boolean getUpdateProjectData(Projects ppojo);

	List<Projects> getProjectAllList();

	boolean saveProjectClone(Projects ps, List<ProjectsDetails> pdList,String userName);
	
	void deActivateProjectDetailsData(long string,int i,int j, String type);

	List<Long> getDraftReviewStageDraftStage();

	List<Projects> getProjectsListForDraft(List<Long> list);

	String approveProject(ProjectDetailsKeyVal prod, String username, MessageSource messageSource, Locale currentLocale);

	List<Projects> getProjectWithProjectNo(String prono);
	
	void updateOtherActivityParameters(Long projectId,Long parameterId,Long activityId, boolean isDelete, String userName);
	
	boolean checkCustomActivityParameterExistsOrNot(Long activityId, Long parameterId);
	
	Integer addActivityParameter(long projectId, Long activityId, Long parameterId, String userName);

	ProjectDetailsDto getProjectsDetailsVal(long projectId, String format);
	
	void deleteComment(long commentId, String username);
}
