package com.covideinfo.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.ModulesAccessDao;
import com.covideinfo.model.ApplicationMenus;
import com.covideinfo.model.ApplictionSideMenus;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificSidemenu;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.RolesWiseModules;
import com.covideinfo.model.StatusMaster;

@Repository("/modulesAccessDao")
public class ModulesAccessDaoImpl extends AbstractDao<Long,RoleMaster> implements ModulesAccessDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMaster> getAllRoleMaster() {
		StatusMaster stm =(StatusMaster) getSession().createCriteria(StatusMaster.class)
				.add(Restrictions.eq("statusCode", "ACTIVE")).uniqueResult();
		return getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.eq("roleStatus", stm))
				.list();
	}

	@Override
	public RoleMaster getRoleMasterRecord(Long roleId) {
		return (RoleMaster) getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.eq("id", roleId)).uniqueResult();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RolesWiseModules> getRolesWiseModulesRecordsList(Long roleId) {
		return getSession().createCriteria(RolesWiseModules.class)
				.add(Restrictions.eq("role.id", roleId))
				.add(Restrictions.eq("status", "active")).list();
	}

	@Override
	public ApplicationMenus getApplicationMenusWithId(Long sideMenu) {
		ApplicationMenus asm=null;
		asm=(ApplicationMenus) getSession().createCriteria(ApplicationMenus.class)
				.add(Restrictions.eq("id", sideMenu)).uniqueResult();
		return asm;
	}

	@Override
	public boolean saveAddLinkNames(ApplictionSideMenus asm) {
		boolean flag=false;
		try {
			getSession().save(asm);
			flag=true;
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RolesWiseModules> getRolesWiseModulesRecordsListBasedOnRole(Long roleId) {
		return getSession().createCriteria(RolesWiseModules.class)
				.add(Restrictions.eq("role.id", roleId)).list();
	}

	@SuppressWarnings("unchecked")
	public List<ApplictionSideMenus> getSideMenusList() {
		return getSession().createCriteria(ApplictionSideMenus.class).list();
	}

	@Override
	public String saveOrUpdateRoleWiseModulesRecords(List<RolesWiseModules> updateList,
			List<RolesWiseModules> saveList) {
		String result = "Failed";
		String result1 = "Failed";
		String result2 = "Failed";
		try {
			if(updateList.size() > 0) {
				for(RolesWiseModules rml : updateList) {
					getSession().update(rml);
					result1 ="success";
				}
			}else result1 = "success";
			if(saveList.size() > 0) {
				for(RolesWiseModules rwm : saveList) {
					getSession().save(rwm);
					result2 = "success";
				}
			}else result2 = "success";
			if(result1.equals("success") && result2.equals("success"))
				result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplictionSideMenus> getApplicationSideMenusList(Long roleId) {
		
		List<ApplictionSideMenus> list=null;
		Long ids=3l;
		Long idss=1l;
		if(roleId<ids) {
			list=getSession().createCriteria(ApplictionSideMenus.class)
					.add(Restrictions.eq("linkStatus", true))
					.list();
		}else {
			list=getSession().createCriteria(ApplictionSideMenus.class)
					.add(Restrictions.ne("appMenu.id",idss))
					.add(Restrictions.eq("linkStatus", true))
					.list();
		}
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RolesWiseModules> getRolesWiseModulesWithRoleId(Long roleId) {
		return getSession().createCriteria(RolesWiseModules.class)
				.add(Restrictions.eq("role.id", roleId)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplicationMenus> getApplicationMenusList() {
		return getSession().createCriteria(ApplicationMenus.class).list();
	}

	@Override
    public ApplictionSideMenus checkNameDuplication(String name) {
        ApplictionSideMenus asm=null;
        asm=(ApplictionSideMenus) getSession().createCriteria(ApplictionSideMenus.class)
                .add(Restrictions.eq("name", name)).uniqueResult();
        return asm;
    }
	@SuppressWarnings("unchecked")
	@Override
	public List<InternationalizaionLanguages> getInternationalizaionLanguages() {
		return getSession().createCriteria(InternationalizaionLanguages.class).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificSidemenu> getLanguageSpecificSidemenuList() {
		return getSession().createCriteria(LanguageSpecificSidemenu.class).list();
	}
	
	@Override
	public String saveModuleAccessRecords(ApplictionSideMenus asm, Map<Long, String> langLinksMap,
			Map<Long, InternationalizaionLanguages> inlagMap) {
		String result ="Failed";
		long asmNo =0;
		try {
			asmNo = (long) getSession().save(asm);
			if(asmNo > 0) {
				for(Map.Entry<Long, InternationalizaionLanguages> inMap : inlagMap.entrySet()) {
					LanguageSpecificSidemenu lsvd = new LanguageSpecificSidemenu();
					lsvd.setCreatedBy(asm.getCreatedBy());
					lsvd.setCreatedOn(new Date());
					lsvd.setInlagId(inMap.getValue());
					lsvd.setLinkname(langLinksMap.get(inMap.getValue().getId()));
					lsvd.setApplictionSideMenus(asm);
					getSession().save(lsvd);
					result ="success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public LanguageSpecificSidemenu getLanguageSpecificSidemenuWithLangidAndSideMenuId(ApplictionSideMenus aws,
			InternationalizaionLanguages language) {
		LanguageSpecificSidemenu pojo=null;
		pojo=(LanguageSpecificSidemenu) getSession().createCriteria(LanguageSpecificSidemenu.class)
				.add(Restrictions.eq("applictionSideMenus", aws))
				.add(Restrictions.eq("inlagId", language)).uniqueResult();
		return pojo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificSidemenu> getLanguageSpecificSidemenuWithLangid(InternationalizaionLanguages language) {
		List<LanguageSpecificSidemenu> list=null;
		list=getSession().createCriteria(LanguageSpecificSidemenu.class)
				.add(Restrictions.eq("inlagId", language)).list();
		return list;
	}

	@Override
	public InternationalizaionLanguages getInternationalizaionLanguageRecord(Long langId) {
		return (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
				.add(Restrictions.eq("id", langId)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMaster> getAllRoleMasterWithOutSuperadmin() {
		StatusMaster stm =(StatusMaster) getSession().createCriteria(StatusMaster.class)
				.add(Restrictions.eq("statusCode", "ACTIVE")).uniqueResult();
		return getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.ne("role", "SUPERADMIN"))
				.add(Restrictions.eq("roleStatus", stm)).list();
	}

}
