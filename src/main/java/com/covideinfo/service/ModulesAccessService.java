package com.covideinfo.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.ModuleAccessDto;
import com.covideinfo.model.ApplicationMenus;
import com.covideinfo.model.ApplictionSideMenus;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.RolesWiseModules;

public interface ModulesAccessService {

	List<RoleMaster> getAllRoleMaster();

	Map<String, Map<String, List<RolesWiseModules>>> getRolesWiseModulesRecordsList(Long roleId);

	boolean saveAddLinkNames(String linkName, Long sideMenu);

	String saveAllSubLinksIds(List<String> finalstr, Long roleId, String userName);

	Map<String, List<ApplictionSideMenus>> getApplicationSideMenusList(Long roleId);

	Map<Long, List<ApplictionSideMenus>> getSideMenusList();

	Map<Long, Map<Long, List<RolesWiseModules>>> getRolesWiseModulesRecordsListBasedOnRole(Long roleId);

	List<ApplicationMenus> getApplicationMenusList();

	ApplictionSideMenus checkNameDuplication(String name);
	
	String saveAddLinkNames(String linkName, List<String> lagLinks, List<Long> lagList, Long sideMenu,	String userName);

	ModuleAccessDto getLanguageDetails(MessageSource messageSource, List<InternationalizaionLanguages> inLagList,
			Locale currentLocale, Long langId);

	List<InternationalizaionLanguages> getInternationalizaionLanguages();

	List<RoleMaster> getAllRoleMasterWithOutSuperadmin();

	

}
