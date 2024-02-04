package com.covideinfo.security;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.covideinfo.dao.ActivityLockedDao;
import com.covideinfo.dao.StudyCreationDao;
import com.covideinfo.dto.DataPushingThread;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.ActivityLockedService;
import com.covideinfo.service.UserService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

	public static boolean dbcheck = true;
	static final Logger logger = Logger.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserService userService;
	@Autowired
	private StudyCreationDao studyCreationDao;
	@Autowired
	ActivityLockedService activityLockedService;
	
	@Autowired
	ActivityLockedDao activityLockedDao;
	
	@Autowired
	HttpServletRequest request;
	@Override
//	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException  {
		StatusMaster activeStatus = studyCreationDao.statusMaster(StudyStatus.ACTIVE.toString());
		if(dbcheck) {
			userService.checkAndCreateConfiguredTables(activeStatus);
			dbcheck = false;
			//Locking Thread Project In Live then Code On Comments
			//DataPushingThread.dataPushing(activityLockedService,activityLockedDao); 
		}
		UserMaster user = userService.findByUsername(username);
		logger.info("User : {}"+ user);
		if (user == null) {
			logger.info("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		boolean accountExpried = true;
		//if(user.getAccountexprie().after(new Date()))
		//	accountExpried = true;
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isAccountNotDisable(), accountExpried,
				true, user.isAccountNotLock(), getGrantedAuthorities(user));
	}

	private List<GrantedAuthority> getGrantedAuthorities(UserMaster user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRole()));
		logger.info("authorities : {}"+ authorities);
		return authorities;
	}

}
