package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dto.DelegationDto;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.UserWiseStudiesAsignMaster;

@Repository("delegationDaoImpl")
public class DelegationDaoImpl extends AbstractDao<Long, UserWiseStudiesAsignMaster> {
	
	@SuppressWarnings("unchecked")
	public DelegationDto getDelegationDtoDetails() {
		DelegationDto delDto = null;
		List<UserWiseStudiesAsignMaster> usmList = null;
		List<UserWiseStudiesAsignMaster> finalusmList = new ArrayList<>();
		Map<Long, String> rolesMap = new HashMap<>();
		List<RoleMaster> rolesList = null;
		
	    List<UserMaster> usersList = null;
	    List<StudyMaster> smList = null;
	    List<StatusMaster> stmList = null;
	    StatusMaster stm =(StatusMaster) getSession().createCriteria(StatusMaster.class)
				.add(Restrictions.eq("statusCode", "ACTIVE")).uniqueResult();
        usmList = getSession().createCriteria(UserWiseStudiesAsignMaster.class).list();
		rolesList = getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.eq("roleStatus", stm))
				.add(Restrictions.ne("role", "SUPERADMIN"))
				.add(Restrictions.ne("role", "ADMIN"))
				.list();
		usersList = getSession().createCriteria(UserMaster.class)
				.add(Restrictions.eq("accountNotLock", true))
				.add(Restrictions.eq("accountNotDisable", true))
				.add(Restrictions.ne("role.id", 1L))
				.add(Restrictions.ne("role.id", 2L)).list();
		
		stmList =(List<StatusMaster>)getSession().createCriteria(StatusMaster.class).add(Restrictions.disjunction()
				.add(Restrictions.eq("statusCode", "COMPLETED"))
				.add(Restrictions.eq("statusCode", "INACTIVE"))
				.add(Restrictions.eq("statusCode", "CANCELED"))
				.add(Restrictions.eq("statusCode", "DRAFT")))
				.list();
		
	    smList =  (List<StudyMaster>) getSession().createCriteria(StudyMaster.class).add(Restrictions.not(Restrictions.in("studyStatus", stmList))).list();	
	    
		if(usmList.size() > 0) {
			for(UserWiseStudiesAsignMaster usm : usmList) {
				String usmRoles = usm.getStudyRoles();
				List<Long> roleIds = new ArrayList<>();
				if(usmRoles != null) {
					String[] tempArr = usmRoles.split("\\,");
					if(tempArr.length > 0) {
						for(String st : tempArr) {
							roleIds.add(Long.parseLong(st));
						}
					}
				}
				usm.setRolesList(roleIds);
				finalusmList.add(usm);
			}
		}
		if(rolesList.size() > 0) {
			for(RoleMaster role : rolesList) {
				rolesMap.put(role.getId(), role.getRole());
			}
		}
		delDto = new DelegationDto();
		delDto.setUsmList(finalusmList);
		delDto.setRolesMap(rolesMap);
		delDto.setRoleList(rolesList);
		delDto.setUsersList(usersList);
		delDto.setSmList(smList);
		
		return delDto;
	}
	
	@SuppressWarnings("unchecked")
	public String saveDelegationDetails(long studyid,long userId,String studyRoles,String userName) {
		String result = "Failed";
		try {
			List<UserWiseStudiesAsignMaster> usmList = (List<UserWiseStudiesAsignMaster>) getSession()
					.createCriteria(UserWiseStudiesAsignMaster.class).list();

			StudyMaster study = null;
			UserMaster usersList = null;

			usersList = (UserMaster) getSession().createCriteria(UserMaster.class).add(Restrictions.eq("id", userId))
					.uniqueResult();

			study = (StudyMaster) getSession().createCriteria(StudyMaster.class).add(Restrictions.eq("id", studyid))
					.uniqueResult();

			UserWiseStudiesAsignMaster uws = new UserWiseStudiesAsignMaster();

			uws.setCreatedBy(userName);
			uws.setCreatedOn(new Date());
			uws.setUserId(usersList);
			uws.setStudy(study);
			uws.setStudyRoles(studyRoles);
			uws.setStatus(true);
			getSession().save(uws);

			result = "Success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public UserWiseStudiesAsignMaster getUserWiseStudiesAsignMasterRecord(Long uwsId) {
		return (UserWiseStudiesAsignMaster) getSession().createCriteria(UserWiseStudiesAsignMaster.class)
				.add(Restrictions.eq("id", uwsId)).uniqueResult();
	}

	public String updateDelegationDetails(Long study, Long uwsuserId, String uswRoles, String userName, Long uwsId,boolean status) {
		String result = "Failed";
		UserWiseStudiesAsignMaster uwsam = null;
		boolean flag = false;
		StudyMaster sm = null;
		UserMaster um = null;
		RoleMaster rl = null;
		
		try
		{
			uwsam = (UserWiseStudiesAsignMaster) getSession().createCriteria(UserWiseStudiesAsignMaster.class)
					.add(Restrictions.eq("id", uwsId)).uniqueResult();
			
			if(uwsam != null) {
				if(uwsam.getStudy().getId() != study) {
					sm = (StudyMaster) getSession().createCriteria(StudyMaster.class)
							.add(Restrictions.eq("id", study)).uniqueResult();
					uwsam.setStudy(sm);
					
					flag = true;
					
				}
				
				if(uwsam.getUserId().getId() != uwsuserId) {
					um = (UserMaster) getSession().createCriteria(UserMaster.class)
							.add(Restrictions.eq("id", uwsuserId)).uniqueResult();
					uwsam.setUserId(um);
					flag = true;
					
				}
				if(uwsam.getStudyRoles() != null) {
					String[] tempArr = uwsam.getStudyRoles().split("\\,");
					List<Long> roleIds = new ArrayList<>();
					if(tempArr.length > 0) {
						for(String st : tempArr)
							roleIds.add(Long.parseLong(st));
					}
					if(uswRoles != null && !uswRoles.equals("")) {
						String[] temp2 = uswRoles.split("\\,");
						if(temp2.length > 0) {
							if(roleIds.size() != temp2.length) {
								uwsam.setStudyRoles(uswRoles);
								flag = true;
							}else {
								for(String st1 : temp2) {
									if(!roleIds.contains(Long.parseLong(st1))) {
										uwsam.setStudyRoles(uswRoles);
										flag = true;
									}
								}
							}
						}
					}
				}
			}
				
			if(flag) {
				uwsam.setStatus(true);
				uwsam.setUpdatedBy(userName);
				uwsam.setUpdatedOn(new Date());
				getSession().update(uwsam);
				result = "success";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	

	public String deleteDelegationDetails(Long deluwsId , String userName, boolean statusuwsval) {
		String result = "Failed";
		UserWiseStudiesAsignMaster uwa = null;
		uwa = (UserWiseStudiesAsignMaster) getSession().createCriteria(UserWiseStudiesAsignMaster.class)
				.add(Restrictions.eq("id", deluwsId)).uniqueResult();
		if(uwa != null) {
			uwa.setStatus(statusuwsval);
			uwa.setUpdatedBy(userName);
			uwa.setUpdatedOn(new Date());
			getSession().update(uwa);
			result = "Success";
		}
		return result;
	}
	
	
	
	
	
}
