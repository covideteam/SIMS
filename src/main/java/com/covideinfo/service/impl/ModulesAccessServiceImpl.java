package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.ModulesAccessDao;
import com.covideinfo.dto.ModuleAccessDto;
import com.covideinfo.model.ApplicationMenus;
import com.covideinfo.model.ApplictionSideMenus;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificSidemenu;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.RolesWiseModules;
import com.covideinfo.service.ModulesAccessService;

@Service("modulesAccessService")
public class ModulesAccessServiceImpl  implements ModulesAccessService {
	
	@Autowired
	ModulesAccessDao modulesAccessDao;
	@Autowired
	LanguageService languageService;
	
	@Override
	public List<InternationalizaionLanguages> getInternationalizaionLanguages() {
		return modulesAccessDao.getInternationalizaionLanguages();
	}

	@Override
	public List<RoleMaster> getAllRoleMaster() {
		return modulesAccessDao.getAllRoleMaster();
	}

	@Override
	public Map<String, Map<String, List<RolesWiseModules>>> getRolesWiseModulesRecordsList(Long roleId) {
		Map<String, Map<String, List<RolesWiseModules>>> rwmMap = new HashMap<>();
		List<RolesWiseModules> rwmList = null;
		List<RolesWiseModules> list = null;
		Map<String, List<RolesWiseModules>> map = null;
		try {
			RoleMaster rolePojo = modulesAccessDao.getRoleMasterRecord(roleId);
			if(rolePojo != null) {
				rwmList = modulesAccessDao.getRolesWiseModulesRecordsList(roleId);
				if(rwmList != null && rwmList.size() > 0) {
					for(RolesWiseModules rwm : rwmList) {
						if(rwmMap.containsKey(rwm.getRole().getRole())) {
							map = rwmMap.get(rwm.getRole().getRole());
							list = map.get(rwm.getUsermenu().getName());
							if(list == null)
								list = new ArrayList<>();
							list.add(rwm);
							map.put(rwm.getUsermenu().getName(), list);
							rwmMap.put(rwm.getRole().getRole(), map);
						}else {
							map = new HashMap<>();
							list = new ArrayList<>();
							list.add(rwm);
							map.put(rwm.getUsermenu().getName(), list);
							rwmMap.put(rwm.getRole().getRole(), map);
						}
					}
				}else {
					rwmMap.put(rolePojo.getRole(), map);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rwmMap;
	}
	@Override
	public boolean saveAddLinkNames(String linkName, Long sideMenu) {
		ApplicationMenus am=modulesAccessDao.getApplicationMenusWithId(sideMenu);
		ApplictionSideMenus asm=new ApplictionSideMenus();
		asm.setAppMenu(am);
		asm.setName(linkName);
		return modulesAccessDao.saveAddLinkNames(asm);
	}
	@Override
	public String saveAllSubLinksIds(List<String> finalstr, Long roleId, String userName) {
		String result ="Failed";
		Map<Long, ApplictionSideMenus> sindemenuMap =new HashMap<>();
		Map<Long, ApplicationMenus> menuMap =new HashMap<>();
		List<RolesWiseModules> saveList = new ArrayList<>();
		List<RolesWiseModules> updateList = new ArrayList<>();
		RoleMaster role = null;
		List<RolesWiseModules>  rwmList = null;
		Map<Long, Map<Long, RolesWiseModules>> rolwMMap = new HashMap<>();
		Map<Long, RolesWiseModules> mrMap = null;
		Map<Long, Map<Long, RolesWiseModules>> exRolwMap = new HashMap<>();
		try {
			role = modulesAccessDao.getRoleMasterRecord(roleId);
		    rwmList = modulesAccessDao.getRolesWiseModulesRecordsListBasedOnRole(roleId);
		    for(RolesWiseModules rw : rwmList) {
		    	if(rolwMMap.containsKey(rw.getAppsideMenu().getAppMenu().getId())) {
		    		mrMap = rolwMMap.get(rw.getAppsideMenu().getAppMenu().getId());
		    		mrMap.put(rw.getAppsideMenu().getId(), rw);
		    		rolwMMap.put(rw.getAppsideMenu().getAppMenu().getId(), mrMap);
		    	}else {
		    		if(mrMap == null)
		    			mrMap = new HashMap<>();
		    		mrMap.put(rw.getAppsideMenu().getId(), rw);
		    		rolwMMap.put(rw.getAppsideMenu().getAppMenu().getId(), mrMap);
		    	}
		    }
			List<ApplictionSideMenus> list = modulesAccessDao.getSideMenusList();
			for(ApplictionSideMenus sl : list) {
				sindemenuMap.put(sl.getId(), sl);
				if(!menuMap.containsKey(sl.getAppMenu().getId()))
					menuMap.put(sl.getAppMenu().getId(), sl.getAppMenu());
			}
		    if(finalstr.size() > 0) {
				for(String st : finalstr) {
					String[] menuArr = st.split("@@@");
					if(menuArr.length > 0) {
						
//						for(String str : menuArr) {
							String[] tempArr = menuArr[1].split("@@");
							if(tempArr.length > 0) {
//								for(String s : tempArr) {
									RolesWiseModules rwm = new RolesWiseModules();
									rwm.setAddStatus("active");
									rwm.setAppsideMenu(sindemenuMap.get(Long.parseLong(tempArr[0])));
									rwm.setCreatedBy(userName);
									rwm.setCreatedOn(new Date());
									rwm.setRole(role);
									rwm.setUsermenu(menuMap.get(Long.parseLong(menuArr[0])));
									rwm.setAddStatus(tempArr[1]);
									rwm.setUpdate(tempArr[2]);
									rwm.setReview(tempArr[3]);
									rwm.setDeactive(tempArr[4]);
									
									if(exRolwMap.containsKey(rwm.getAppsideMenu().getAppMenu().getId())) {
							    		mrMap = exRolwMap.get(rwm.getAppsideMenu().getAppMenu().getId());
							    		mrMap.put(rwm.getAppsideMenu().getId(), rwm);
							    		exRolwMap.put(rwm.getAppsideMenu().getAppMenu().getId(), mrMap);
							    	}else {
							    		mrMap = new HashMap<>();
							    		mrMap.put(rwm.getAppsideMenu().getId(), rwm);
							    		exRolwMap.put(rwm.getAppsideMenu().getAppMenu().getId(), mrMap);
							    	}
							}
					}
				}
			}
			if(rwmList != null && rwmList.size() > 0) {
				for(RolesWiseModules rwmPojo : rwmList) {
					Map<Long, RolesWiseModules> rMap = exRolwMap.get(rwmPojo.getAppsideMenu().getAppMenu().getId());
					if(rMap != null) {
						if(rMap.containsKey(rwmPojo.getAppsideMenu().getId())) {
							boolean flag = false;
							if(rwmPojo.getAppsideMenu().getId() == 3)
								System.out.println("value is : "+3);
				    		RolesWiseModules rwm = rMap.get(rwmPojo.getAppsideMenu().getId());
				    		if(!rwmPojo.getStatus().equalsIgnoreCase(rwm.getStatus())) {
				    			rwmPojo.setStatus(rwm.getStatus());
				    			flag = true;
				    		}
				    		if(!rwmPojo.getAddStatus().equalsIgnoreCase(rwm.getAddStatus())) {
				    			rwmPojo.setAddStatus(rwm.getAddStatus());
				    			
				    			flag = true;
				    		}
				    		if(!rwmPojo.getUpdate().equalsIgnoreCase(rwm.getUpdate())) {
				    			rwmPojo.setUpdate(rwm.getUpdate());
				    			flag = true;
				    		}
				    		if(!rwmPojo.getReview().equalsIgnoreCase(rwm.getReview())) {
				    			rwmPojo.setReview(rwm.getReview());
				    			flag = true;
				    		}
				    		if(!rwmPojo.getDeactive().equalsIgnoreCase(rwm.getDeactive())) {
				    			rwmPojo.setDeactive(rwm.getDeactive());
				    			flag = true;
				    		}
				    		if(flag) {
				    			rwmPojo.setUpdatedBy(userName);
				    			rwmPojo.setUpdatedOn(new Date());
				    			updateList.add(rwmPojo);	
				    		}
						}else {
							if(rwmPojo.getStatus().equals("active")) {
								rwmPojo.setAddStatus("inactive");
								rwmPojo.setDeactive("inactive");
								rwmPojo.setReview("inactive");
								rwmPojo.setStatus("inactive");
								rwmPojo.setUpdate("inactive");
								rwmPojo.setUpdatedBy(userName);
				    			rwmPojo.setUpdatedOn(new Date());
								updateList.add(rwmPojo);
							}
					   }
					}else {
						if(rwmPojo.getStatus().equals("active")) {
							rwmPojo.setAddStatus("inactive");
							rwmPojo.setDeactive("inactive");
							rwmPojo.setReview("inactive");
							rwmPojo.setStatus("inactive");
							rwmPojo.setUpdate("inactive");
							rwmPojo.setUpdatedBy(userName);
			    			rwmPojo.setUpdatedOn(new Date());
							updateList.add(rwmPojo);
						}
				   }
				}
				if(exRolwMap.size() > 0) {
					for(Map.Entry<Long, Map<Long, RolesWiseModules>> map : exRolwMap.entrySet()) {
						Map<Long, RolesWiseModules> rwmap = map.getValue();
						if(rwmap != null && rwmap.size() > 0) {
							for(Map.Entry<Long, RolesWiseModules> rw : rwmap.entrySet()) {
								saveList.add(rw.getValue());
							}
						}
					}
				}
			}else {
				if(exRolwMap.size() > 0) {
					for(Map.Entry<Long, Map<Long, RolesWiseModules>> map : exRolwMap.entrySet()) {
						Map<Long, RolesWiseModules> rwmap = map.getValue();
						if(rwmap != null && rwmap.size() > 0) {
							for(Map.Entry<Long, RolesWiseModules> rw : rwmap.entrySet()) {
								saveList.add(rw.getValue());
							}
						}
					}
				}
			}
			result = modulesAccessDao.saveOrUpdateRoleWiseModulesRecords(updateList, saveList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
    
	@Override
	public Map<String, List<ApplictionSideMenus>> getApplicationSideMenusList(Long roleId) {
		Map<String, List<ApplictionSideMenus>> map = new HashMap<>();
		List<ApplictionSideMenus> apsList = null;
		List<ApplictionSideMenus> list = null;
		Map<Long, RolesWiseModules> roleMap = new HashMap<>();
		Locale currentLocale = LocaleContextHolder.getLocale();
		try {
			InternationalizaionLanguages language =  languageService.getLanguage(currentLocale);
			apsList = modulesAccessDao.getApplicationSideMenusList(roleId);
			Map<Long,String> awslist=new HashMap<>();
			List<LanguageSpecificSidemenu> listdata=modulesAccessDao.getLanguageSpecificSidemenuWithLangid(language);
			for(LanguageSpecificSidemenu add:listdata) {
				awslist.put(add.getApplictionSideMenus().getId(), add.getLinkname());
			}
			for(ApplictionSideMenus aws:apsList){
				if(awslist.get(aws.getId())!=""&&awslist.get(aws.getId())!=null)
				aws.setName(awslist.get(aws.getId()));
			}
			List<RolesWiseModules> roleWishL=modulesAccessDao.getRolesWiseModulesWithRoleId(roleId);
			for(RolesWiseModules rl:roleWishL) {
				roleMap.put(rl.getAppsideMenu().getId(), rl);
			}
			for(ApplictionSideMenus asm : apsList) {
				if(roleMap.containsKey(asm.getId())) {
					RolesWiseModules rwm = roleMap.get(asm.getId());
					if(rwm.getAppsideMenu().getId() == 3)
						System.out.println("ap side menu :"+3);
					if(rwm.getStatus().equals("active")) {
						asm.setStatus("Active");
					}
					if(rwm.getAddStatus().equals("active")) {
						asm.setStatusAdd("Active");
					}
					if(rwm.getUpdate().equals("active")) {
						asm.setStatusUpdate("Active");
					}
					if(rwm.getReview().equals("active")) {
						asm.setStatusReview("Active");
					}
					if(rwm.getDeactive().equals("active")) {
						asm.setStatusDeactive("Active");
					}
					if(map.containsKey(asm.getAppMenu().getName())) {
						list = map.get(asm.getAppMenu().getName());
						list.add(asm);
						map.put(asm.getAppMenu().getName(), list);
					}else {
						list = new ArrayList<>();
						list.add(asm);
						map.put(asm.getAppMenu().getName(), list);
					}
				}else {
					if(map.containsKey(asm.getAppMenu().getName())) {
						list = map.get(asm.getAppMenu().getName());
						list.add(asm);
						map.put(asm.getAppMenu().getName(), list);
					}else {
						list = new ArrayList<>();
						list.add(asm);
						map.put(asm.getAppMenu().getName(), list);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}

	public Map<Long, List<ApplictionSideMenus>> getSideMenusList(){
		Map<Long, List<ApplictionSideMenus>> menuMap =new HashMap<>();
		List<ApplictionSideMenus> list = modulesAccessDao.getSideMenusList();
		for(ApplictionSideMenus sl : list) {
			if(menuMap.containsKey(sl.getAppMenu().getId())) {
				List<ApplictionSideMenus> list2 = menuMap.get(sl.getAppMenu().getId());
				list2.add(sl);
				menuMap.put(sl.getAppMenu().getId(), list2);
			}else
			{
				List<ApplictionSideMenus> list2 = new ArrayList<>();
				list2.add(sl);
				menuMap.put(sl.getAppMenu().getId(), list2);
			}
		}
		
		
		return menuMap;
	}

	@Override
	public Map<Long, Map<Long, List<RolesWiseModules>>> getRolesWiseModulesRecordsListBasedOnRole(Long roleId) {
		Map<Long, Map<Long, List<RolesWiseModules>>> rwmMap = new HashMap<>();
		List<RolesWiseModules> rwmList = null;
		List<RolesWiseModules> list = null;
		Map<Long, List<RolesWiseModules>> map = null;
		try {
			RoleMaster rolePojo = modulesAccessDao.getRoleMasterRecord(roleId);
			if(rolePojo != null) {
				rwmList = modulesAccessDao.getRolesWiseModulesRecordsList(roleId);
				if(rwmList != null && rwmList.size() > 0) {
					for(RolesWiseModules rwm : rwmList) {
						if(rwmMap.containsKey(rwm.getAppsideMenu().getAppMenu().getId())) {
							map = rwmMap.get(rwm.getAppsideMenu().getAppMenu().getId());
							if(map.containsKey(rwm.getAppsideMenu().getId())) {
								list = map.get(rwm.getAppsideMenu().getId());
								if(list == null)
									list = new ArrayList<>();
								list.add(rwm);
								map.put(rwm.getAppsideMenu().getId(), list);
								rwmMap.put(rwm.getAppsideMenu().getAppMenu().getId(), map);
							}else {
								list = new ArrayList<>();
								list.add(rwm);
								map.put(rwm.getAppsideMenu().getId(), list);
								rwmMap.put(rwm.getAppsideMenu().getAppMenu().getId(), map);
							}
							
						}else {
							map = new HashMap<>();
							list = new ArrayList<>();
							list.add(rwm);
							map.put(rwm.getAppsideMenu().getId(), list);
							rwmMap.put(rwm.getAppsideMenu().getAppMenu().getId(), map);
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rwmMap;
	}

	@Override
	public List<ApplicationMenus> getApplicationMenusList() {
		return modulesAccessDao.getApplicationMenusList();
	}

	@Override
	public ApplictionSideMenus checkNameDuplication(String name) {
		return modulesAccessDao.checkNameDuplication(name);
	}
	
	@Override
	public String saveAddLinkNames(String linkName, List<String> lagLinks, List<Long> lagList, Long sideMenu,String userName) {
		String result ="Failed";
		List<InternationalizaionLanguages> inlagList = null;
		Map<Long, InternationalizaionLanguages> inlagMap = new HashMap<>();
		ApplicationMenus am=modulesAccessDao.getApplicationMenusWithId(sideMenu);
		Map<Long, String> langLinksMap = new HashMap<>();
		try {
			inlagList = modulesAccessDao.getInternationalizaionLanguages();
			if(inlagList != null && inlagList.size() > 0) {
				for(InternationalizaionLanguages inl : inlagList) {
					inlagMap.put(inl.getId(), inl);
				}
			}
			if(lagLinks != null && lagLinks.size() > 0) {
				for(String st : lagLinks) {
					if(st != null && !st.equals("")) {
						String [] tempArr = st.split("\\@@");
						if(tempArr.length > 0)
							langLinksMap.put(Long.parseLong(tempArr[0]), tempArr[1]);
					}
				}
			}
			ApplictionSideMenus asm=new ApplictionSideMenus();
			asm.setAppMenu(am);
			asm.setName(linkName);
			asm.setCreatedBy(userName);
			asm.setCreatedOn(new Date());
			result = modulesAccessDao.saveModuleAccessRecords(asm, langLinksMap, inlagMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public ModuleAccessDto getLanguageDetails(MessageSource messageSource,
			List<InternationalizaionLanguages> inLagList, Locale currentLocale, Long langId) {
		ModuleAccessDto mDto = null;
		Map<Long, Map<String, String>> lanMap = new HashMap<>();
		Map<String, String> outlanMap = new HashMap<>();
		Map<String, String> tempMap = null;
		Map<Long, ApplictionSideMenus> gvMap = new HashMap<>();
		Map<Long, List<LanguageSpecificSidemenu>> lsMap = new HashMap<>();
		InternationalizaionLanguages inlag = modulesAccessDao.getInternationalizaionLanguageRecord(langId);
		try {
			if(inLagList.size() > 0) {
				for(InternationalizaionLanguages inl : inLagList) {
					Locale locale = new Locale(inlag.getLangCode(), inlag.getCountryCode());
					String label = messageSource.getMessage("label.gvName", null,locale);
					String langName = "";
					if(inl.getLanguage().equals(inlag.getLanguage())) {
						if(inl.getLanguage().equals("ENGLISH")) {
							langName = messageSource.getMessage("label.gvNameLag", null,locale);
							lanMap = getLanguageMap(inl, langName, lanMap, label);
						}else {
							langName = messageSource.getMessage("label.gvNameSpanish", null,locale);
							lanMap = getLanguageMap(inl, langName, lanMap, label);
						}
					}else {
						if(inl.getLanguage().equals("ENGLISH")) {
							langName = messageSource.getMessage("label.gvNameLag", null,locale);
							lanMap = getLanguageMap(inl, langName, lanMap, label);
						}else {
							langName = messageSource.getMessage("label.gvNameSpanish", null,locale);
							lanMap = getLanguageMap(inl, langName, lanMap, label);
						}
					}
				}
				String title =messageSource.getMessage("label.mlTitle", null,currentLocale);
				String addBtn = messageSource.getMessage("label.addBtn", null,currentLocale);
				String name =messageSource.getMessage("label.name", null,currentLocale);
				String sideMenuName =messageSource.getMessage("label.sideMenuName", null,currentLocale);
				String gvListTitle =messageSource.getMessage("label.mlListTitle", null,currentLocale);
				
				outlanMap.put("name", name);
				outlanMap.put("title", title);
				outlanMap.put("Submit", addBtn);
				outlanMap.put("sideMenuName", sideMenuName);
				outlanMap.put("gvListTitle", gvListTitle);
				
				List<LanguageSpecificSidemenu> lsvList = modulesAccessDao.getLanguageSpecificSidemenuList(); 
				List<LanguageSpecificSidemenu> tempList = null;
				if(lsvList != null && lsvList.size() > 0) {
					for(LanguageSpecificSidemenu lsvd : lsvList) {
						if(!gvMap.containsKey(lsvd.getApplictionSideMenus().getId())) {
							gvMap.put(lsvd.getApplictionSideMenus().getId(), lsvd.getApplictionSideMenus());
						}
						if(lsMap.containsKey(lsvd.getApplictionSideMenus().getId())) {
							tempList = lsMap.get(lsvd.getApplictionSideMenus().getId());
							tempList.add(lsvd);
							lsMap.put(lsvd.getApplictionSideMenus().getId(), tempList);
						}else {
							tempList = new ArrayList<>();
							tempList.add(lsvd);
							lsMap.put(lsvd.getApplictionSideMenus().getId(), tempList);
						}
					}
				}
				
				mDto = new ModuleAccessDto();
				mDto.setLanMap(lanMap);
				mDto.setOutLanMap(outlanMap);
				mDto.setGvMap(gvMap);
				mDto.setLsMap(lsMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mDto;
	}

	private Map<Long, Map<String, String>> getLanguageMap(InternationalizaionLanguages inl, String langName, 
			Map<Long, Map<String, String>> lanMap, String label) {
		Map<String, String> tempMap = null;
		try {
			if(lanMap.containsKey(inl.getId())) {
				tempMap = lanMap.get(inl.getId());
				tempMap.put("label", label);
				tempMap.put("langName", langName);
				lanMap.put(inl.getId(), tempMap);
			}else {
				tempMap = new HashMap<>();
				tempMap.put("label", label);
				tempMap.put("langName", langName);
				lanMap.put(inl.getId(), tempMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lanMap;
	}

	@Override
	public List<RoleMaster> getAllRoleMasterWithOutSuperadmin() {
		return modulesAccessDao.getAllRoleMasterWithOutSuperadmin();
	}


}
