package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.ActivityUpdateDao;
import com.covideinfo.dto.ActivityDetailsDto;
import com.covideinfo.dto.ActivityUpdateDataDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.service.ActivityUpdateService;

@Service("activityUpdateService")
public class ActivityUpdateServiceImpl implements ActivityUpdateService {
	
	@Autowired
	ActivityUpdateDao actUpdateDao;

	@Override
	public ActivityDetailsDto getActivityDetails(Long activityId) {
		return actUpdateDao.getActivityDetails(activityId);
	}

	@Override
	public String saveUpdatedActivityData(ActivityUpdateDataDto actUpDto, Long userId) {
		String result = "Failed";
		GlobalActivity ga = null;
		boolean updateFlag = false;
		List<Long> existingRoleIds = new ArrayList<>();
		List<Long> currentRoleIds = new ArrayList<>();
		boolean roleFlag = false;
		try {
			if(actUpDto.getActivityId() != null)
				ga = actUpdateDao.getActivityRecord(actUpDto.getActivityId());
			if(ga != null) {
				String[] existingRolesArr = ga.getRoleIds().split("\\,");
				if(existingRolesArr.length > 0) {
					for(String st : existingRolesArr) {
						if(st != null && !st.equals(""))
							existingRoleIds.add(Long.parseLong(st));
					}
				}
				
			}
			if(actUpDto.getRoleIds() != null && !actUpDto.getRoleIds().equals("")) {
				String[] currentRoleIdsArr = actUpDto.getRoleIds().split("\\,");
				if(currentRoleIdsArr.length > 0) {
					for(String st : currentRoleIdsArr) {
						if(st != null && !st.equals(""))
							currentRoleIds.add(Long.parseLong(st));
					}
				}
			}
			
			if(currentRoleIds.size() > 0) {
				if(currentRoleIds.size()  == existingRoleIds.size()) {
					for(Long roleId : currentRoleIds) {
						if(!existingRoleIds.contains(roleId)) {
							roleFlag = true;
							break;
						}
					}
				}
				if((currentRoleIds.size() != existingRoleIds.size()) || existingRoleIds.size() == 0) {
					roleFlag = true;
				}
			}
			if(roleFlag) {
				ga.setRoleIds(actUpDto.getRoleIds());
				updateFlag = true;
			}
			if(ga.getOrderNo() != actUpDto.getOrderNo()) {
				ga.setOrderNo(actUpDto.getOrderNo());
				updateFlag = true;
			}
			if(updateFlag)
				result = actUpdateDao.updateActivity(ga, userId);
			else result = "No Modification for activity "+ga.getName();
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

}
