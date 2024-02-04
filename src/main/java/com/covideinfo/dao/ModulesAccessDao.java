package com.covideinfo.dao;

import java.util.List;
import java.util.Map;

import com.covideinfo.model.ApplicationMenus;
import com.covideinfo.model.ApplictionSideMenus;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificSidemenu;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.RolesWiseModules;

public interface ModulesAccessDao {

	List<RoleMaster> getAllRoleMaster();

	RoleMaster getRoleMasterRecord(Long roleId);

	List<RolesWiseModules> getRolesWiseModulesRecordsList(Long roleId);

	ApplicationMenus getApplicationMenusWithId(Long sideMenu);

	boolean saveAddLinkNames(ApplictionSideMenus asm);

	List<RolesWiseModules> getRolesWiseModulesRecordsListBasedOnRole(Long roleId);

	List<ApplictionSideMenus> getSideMenusList();

	String saveOrUpdateRoleWiseModulesRecords(List<RolesWiseModules> updateList, List<RolesWiseModules> saveList);

	List<ApplictionSideMenus> getApplicationSideMenusList(Long roleId);

	List<RolesWiseModules> getRolesWiseModulesWithRoleId(Long roleId);

	List<ApplicationMenus> getApplicationMenusList();

	ApplictionSideMenus checkNameDuplication(String name);
	
	List<InternationalizaionLanguages> getInternationalizaionLanguages();

	List<LanguageSpecificSidemenu> getLanguageSpecificSidemenuList();

	String saveModuleAccessRecords(ApplictionSideMenus asm, Map<Long, String> langLinksMap,
			Map<Long, InternationalizaionLanguages> inlagMap);

	LanguageSpecificSidemenu getLanguageSpecificSidemenuWithLangidAndSideMenuId(ApplictionSideMenus aws,
			InternationalizaionLanguages language);

	List<LanguageSpecificSidemenu> getLanguageSpecificSidemenuWithLangid(InternationalizaionLanguages language);

	InternationalizaionLanguages getInternationalizaionLanguageRecord(Long langId);

	List<RoleMaster> getAllRoleMasterWithOutSuperadmin();

	

}
