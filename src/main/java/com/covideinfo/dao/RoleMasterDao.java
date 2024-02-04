package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.RoleMaster;

public interface RoleMasterDao {

	List<RoleMaster> findAll();

	RoleMaster findById(long roleId);

	List<RoleMaster> findAllActiveRolesExceptSuperAdmin();

	RoleMaster checkRoleDuplication(String role);

	long saveRole(RoleMaster roleMaster);

	List<DraftReviewStage> getDraftReviewStageList();

	boolean saveApprovalLevelsData(DraftReviewStage drs);

	List<DraftReviewStage> getDraftReviewStageWithFromRoleIdWithWorkName(Long id, String workName);

	List<DraftReviewStage> getDraftReviewStageWithFromRoleIdWithWorkNameTwo(Long id, String workName, String action);

	List<RoleMaster> findOnlyAdminRoles();

	List<RoleMaster> findRolesWithOutAdminRoles();

	RoleMaster getRoleMasterWithId(Long roleval);

	String checkWrokFlowRecordStatus(String workName, String actionName, Long fromRole, Long toRole);

}
