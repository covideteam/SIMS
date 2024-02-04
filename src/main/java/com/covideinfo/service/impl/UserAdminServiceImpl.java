package com.covideinfo.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.UserAdminDao;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.UserAdminService;

@Service("userAdminService")
public class UserAdminServiceImpl implements UserAdminService {
	
	 @Autowired
	 UserAdminDao uadao;

	@Override
	public UserMaster getLoginUsersRecord(String username) {
		return uadao.getLoginUsersRecord(username);
	}

	@Override
	public boolean checkOldPwdMatching(long userId, String pwd) {
		UserMaster user = uadao.getLoginUsersRecordBasedOnId(userId);
		if(user != null) {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			return bCryptPasswordEncoder.matches(pwd, user.getPassword());
		}else {
			return false;
		}
		
	}

	@Override
	public boolean saveChangedPassword(long userId, String newPwd, String tranPwd, String username, String passwordExpDays) {
		boolean finalFlag = false;
		boolean flag = false;
		try {
			UserMaster userPojo = uadao.getLoginUsersRecordBasedOnId(userId);
			if(userPojo != null) {
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					userPojo.setPassword(passwordEncoder.encode(newPwd));
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, Integer.parseInt(passwordExpDays)); // Expire Date 60 days
					Date nextDate = cal.getTime();
					userPojo.setAccountexprie(nextDate);
					userPojo.setPwdChangeCundition(false);
					if(!tranPwd.equals("0"))
						userPojo.setTranPassword(passwordEncoder.encode(tranPwd));
					flag = uadao.updateUserDetails(userPojo);
					if(flag)
						finalFlag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalFlag;
	}

	@Override
	public UserMaster getLoginUsersListExceptSuperAdmin(Long userId) {
		   return uadao.getLoginUsersListExceptSuperAdmin(userId);
	}

	@Override
	public String saveResetPassword(long userId, String reason, String username, String passwordExpDays, String resetPassword) {
		String result ="Failed";
		long logNo = 0;
		boolean userFlag = false;
		try {
			UserMaster user = uadao.getLoginUsersRecordBasedOnId(userId);
			if(user != null) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, Integer.parseInt(passwordExpDays)); // Expire Date 60 days
				Date nextDate = cal.getTime();
				user.setAccountexprie(nextDate);
				user.setPassword(passwordEncoder.encode(resetPassword));
				user.setPwdChangeCundition(true);
				if(user.getTranPassword() != null && !user.getTranPassword().equals(""))
					user.setTranPassword(passwordEncoder.encode(resetPassword));
				user.setUpdateReason(reason);
				userFlag = uadao.updateUserDetails(user);
			}
			if(userFlag)
				result ="success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
