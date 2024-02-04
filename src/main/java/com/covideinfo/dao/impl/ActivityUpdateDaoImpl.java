package com.covideinfo.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.ActivityUpdateDao;
import com.covideinfo.dto.ActivityDetailsDto;
import com.covideinfo.dto.RolesDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.UserMaster;

@Repository("activityUpdateDao")
public class ActivityUpdateDaoImpl extends AbstractDao<Long, GlobalActivity> implements ActivityUpdateDao {

	@SuppressWarnings("unchecked")
	@Override
	public ActivityDetailsDto getActivityDetails(Long activityId) {
		ActivityDetailsDto acdDto = new ActivityDetailsDto();
		List<RolesDto> rolesList = null;
		GlobalActivity ga = null;
		List<Integer> ordersNoList = null;
		try {
			rolesList = getSession().createCriteria(RoleMaster.class)
					.setProjection(Projections.projectionList()
							.add(Projections.property("id"), "roleId")
							.add(Projections.property("role"), "roleName"))
					.setResultTransformer(Transformers.aliasToBean(RolesDto.class)).list();
			
			ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					  .add(Restrictions.eq("id", activityId)).uniqueResult();
			
			ordersNoList = getSession().createCriteria(GlobalActivity.class)
					        .setProjection(Projections.property("orderNo")).list();
			if(ga != null) {
				acdDto.setActId(ga.getId());
				acdDto.setActName(ga.getName());
				acdDto.setExistingRoles(ga.getRoleIds());
				acdDto.setOrderNo(ga.getOrderNo());
			}
			acdDto.setRolesList(rolesList);
			acdDto.setOrdersNoList(ordersNoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return acdDto;
	}

	@Override
	public GlobalActivity getActivityRecord(Long activityId) {
		return (GlobalActivity)getSession().createCriteria(GlobalActivity.class)
				 .add(Restrictions.eq("id", activityId)).uniqueResult();
	}

	@Override
	public String updateActivity(GlobalActivity ga, Long userId) {
		String result ="Failed";
		UserMaster user = null;
		try {
			user = (UserMaster) getSession().createCriteria(UserMaster.class)
					 .add(Restrictions.eq("id", userId)).uniqueResult();
			if(ga != null) {
				ga.setUpdatedBy(user.getUsername());
				ga.setUpdatedOn(new Date());
				getSession().update(ga);
				result ="success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	

}
