package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.RoleMasterDao;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StatusMaster;

@Repository("roleMasterDao")
public class RoleMasterDaoImpl extends AbstractDao<Long, RoleMaster> implements RoleMasterDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMaster> findAll() {
		 StatusMaster stm =(StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", "ACTIVE")).uniqueResult();
		 List<RoleMaster> list=null;
		 list=getSession().createCriteria(RoleMaster.class)
				 .add(Restrictions.eq("roleStatus", stm)).list();
		
		 return list;
	}

	@Override
	public RoleMaster findById(long id) {
		// TODO Auto-generated method stub
		return (RoleMaster) createEntityCriteria().add(Restrictions.eq("id", id)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMaster> findAllActiveRolesExceptSuperAdmin() {
		// TODO Auto-generated method stub
		return createEntityCriteria()
				.add(Restrictions.eq("status", 'T'))
				.add(Restrictions.ne("role", "SUPERADMIN")).list();
	}

	@Override
	public RoleMaster checkRoleDuplication(String role) {
		RoleMaster rm=null;
		rm=(RoleMaster) getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.eq("role", role)).uniqueResult();
		 return rm;
	}

	@Override
	public long saveRole(RoleMaster roleMaster) {
		return (Long) getSession().save(roleMaster);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DraftReviewStage> getDraftReviewStageList() {
		List<DraftReviewStage> drl=null;
		 drl=getSession().createCriteria(DraftReviewStage.class)
				 .addOrder(Order.asc("id")).list();
		 return drl;
	}

	@Override
	public boolean saveApprovalLevelsData(DraftReviewStage drs) {
		boolean flag=false;
		try {
			if(drs.getFromRole().getId() == -1 || drs.getFromRole().getId() == 0) 
				drs.setFromRole(null);
			if(drs.getToRole().getId() == -1 || drs.getToRole().getId() == 0) 
				drs.setToRole(null);
			getSession().save(drs);
			flag=true;
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DraftReviewStage> getDraftReviewStageWithFromRoleIdWithWorkName(Long id, String workName) {
		List<String> actionList=new ArrayList<String>();
		actionList.add("SUBMIT");
		actionList.add("APPROVAL");
		actionList.add("SENDCOMMENTS");
		List<DraftReviewStage> drl=null;
		 drl=(List<DraftReviewStage>) getSession().createCriteria(DraftReviewStage.class)
				 .add(Restrictions.eq("fromRole.id", id))
				 .add(Restrictions.eq("name", workName))
				 .add(Restrictions.in("action", actionList)).list();
		 return drl;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DraftReviewStage> getDraftReviewStageWithFromRoleIdWithWorkNameTwo(Long id, String workName, String action) {
		List<String> actionList=new ArrayList<String>();
		actionList.add("SUBMIT");
		actionList.add(action);
		List<DraftReviewStage> drl=null;
		 drl=(List<DraftReviewStage>) getSession().createCriteria(DraftReviewStage.class)
				 .add(Restrictions.eq("fromRole.id", id))
				 .add(Restrictions.eq("name", workName))
				 .add(Restrictions.in("action", actionList)).list();
		 return drl;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMaster> findOnlyAdminRoles() {
		List<RoleMaster> list=null;
		list=getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.eq("role", "ADMIN")).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMaster> findRolesWithOutAdminRoles() {
		List<String> roList=new ArrayList<String>();
		roList.add("SUPERADMIN");
		roList.add("ADMIN");
		List<RoleMaster> list=null;
		list=getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.not(Restrictions.in("role", roList))).list();
		return list;
	}

	@Override
	public RoleMaster getRoleMasterWithId(Long roleval) {
		RoleMaster role=null;
		role=(RoleMaster) getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.eq("id", roleval)).uniqueResult();
		return role;
	}

	@Override
	public String checkWrokFlowRecordStatus(String workName, String actionName, Long fromRole, Long toRole) {
		String result = "NotExists";
		DraftReviewStage drs = null;
		try {
		     if(fromRole == -1) {
		    	 drs = (DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
		    			 .add(Restrictions.eq("name", workName))
		    			 .add(Restrictions.eq("action", actionName))
		    			 .add(Restrictions.isNull("fromRole"))
		    			 .add(Restrictions.eq("toRole.id", toRole)).uniqueResult();
		     }else if(toRole == -1){
		    	 drs = (DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
		    			 .add(Restrictions.eq("name", workName))
		    			 .add(Restrictions.eq("action", actionName))
		    			 .add(Restrictions.eq("fromRole.id", fromRole))
		    			 .add(Restrictions.isNull("toRole")).uniqueResult();
		     }else {
		    	 drs = (DraftReviewStage) getSession().createCriteria(DraftReviewStage.class)
		    			 .add(Restrictions.eq("name", workName))
		    			 .add(Restrictions.eq("action", actionName))
		    			 .add(Restrictions.eq("fromRole.id", fromRole))
		    			 .add(Restrictions.eq("toRole.id", toRole)).uniqueResult();
		     }
		     if(drs != null)
		    	 result = "Exists";
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return result;
	}

	
}
