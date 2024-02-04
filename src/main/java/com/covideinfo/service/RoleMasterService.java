package com.covideinfo.service;

import java.util.List;

import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.RoleMaster;

public interface RoleMasterService {

	List<RoleMaster> findAll();

	RoleMaster findById(Long id);

	List<RoleMaster> findAllAciveRoleExceptSuperAdmin();

	RoleMaster checkRoleDuplication(String role);

	long saveRole(String username, RoleMaster roleMaster);

	List<DraftReviewStage> getDraftReviewStageList();

	boolean saveApprovalLevelsData(DraftReviewStage drs);

	List<DraftReviewStage> getDraftReviewStageWithFromRoleIdWithWorkName(Long id, String workName);

	List<DraftReviewStage> getDraftReviewStageWithFromRoleIdWithWorkNameTwo(Long id, String workName, String action);

	List<RoleMaster> findOnlyAdminRoles();

	List<RoleMaster> findRolesWithOutAdminRoles();

	RoleMaster getRoleMasterWithId(Long roleval);

	String checkWrokFlowRecordStatus(String workName, String actionName, Long fromRole, Long toRole);


}
