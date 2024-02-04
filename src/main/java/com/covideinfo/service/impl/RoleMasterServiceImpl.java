package com.covideinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.RoleMasterDao;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.service.RoleMasterService;

@Service("roleMasterService")
public class RoleMasterServiceImpl implements RoleMasterService {

	@Autowired
	private RoleMasterDao roleMasterDao;
	
	@Override
	public List<RoleMaster> findAll() {
		// TODO Auto-generated method stub
		return roleMasterDao.findAll();
	}

	@Override
	public RoleMaster findById(Long id) {
		// TODO Auto-generated method stub
		return roleMasterDao.findById(id);
	}

	@Override
	public List<RoleMaster> findAllAciveRoleExceptSuperAdmin() {
		// TODO Auto-generated method stub
		return roleMasterDao.findAllActiveRolesExceptSuperAdmin();
	}

	@Override
	public RoleMaster checkRoleDuplication(String role) {
		return roleMasterDao.checkRoleDuplication(role);
	}

	@Override
	public long saveRole(String username, RoleMaster roleMaster) {
		return roleMasterDao.saveRole(roleMaster);
	}

	@Override
	public List<DraftReviewStage> getDraftReviewStageList() {
		return roleMasterDao.getDraftReviewStageList();
	}

	@Override
	public boolean saveApprovalLevelsData(DraftReviewStage drs) {
		return roleMasterDao.saveApprovalLevelsData(drs);
	}

	@Override
	public List<DraftReviewStage> getDraftReviewStageWithFromRoleIdWithWorkName(Long id, String workName) {
		return roleMasterDao.getDraftReviewStageWithFromRoleIdWithWorkName(id,workName);
	}

	@Override
	public List<DraftReviewStage> getDraftReviewStageWithFromRoleIdWithWorkNameTwo(Long id, String workName, String action) {
		// TODO Auto-generated method stub
		return roleMasterDao.getDraftReviewStageWithFromRoleIdWithWorkNameTwo(id,workName,action);
	}

	@Override
	public List<RoleMaster> findOnlyAdminRoles() {
		return roleMasterDao.findOnlyAdminRoles();
	}

	@Override
	public List<RoleMaster> findRolesWithOutAdminRoles() {
		return roleMasterDao.findRolesWithOutAdminRoles();
	}

	@Override
	public RoleMaster getRoleMasterWithId(Long roleval) {
		return roleMasterDao.getRoleMasterWithId(roleval);
	}

	@Override
	public String checkWrokFlowRecordStatus(String workName, String actionName, Long fromRole, Long toRole) {
		return roleMasterDao.checkWrokFlowRecordStatus(workName, actionName, fromRole, toRole);
	}


}
