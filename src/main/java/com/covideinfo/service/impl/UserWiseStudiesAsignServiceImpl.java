package com.covideinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.UserDao;
import com.covideinfo.dao.UserWiseStudiesAsignDao;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserWiseStudiesAsignMaster;
import com.covideinfo.service.UserWiseStudiesAsignService;

@Service("userWiseStudiesAsignService")
public class UserWiseStudiesAsignServiceImpl implements UserWiseStudiesAsignService {
	@Autowired
	UserWiseStudiesAsignDao userWiseStudiesAsignDao;
	
	@Autowired
	UserDao userDao;

	@Override
	public List<StudyMaster> allUserActiveStudys(Long userId) {
		// TODO Auto-generated method stub
		return userDao.allUserActiveStudys(userId);
	}

	@Override
	public List<UserWiseStudiesAsignMaster> findUsersFindByStudy(Long study) {
		// TODO Auto-generated method stub
		return userWiseStudiesAsignDao.findByStudyWiseList(study);
	}

	@Override
	public void saveStudyAsignStudyCreationTime(List<UserWiseStudiesAsignMaster> usersave) {
		// TODO Auto-generated method stub
		userWiseStudiesAsignDao.saveStudyAsignStudyCreationTime(usersave);
	}

	@Override
	public void updateUserWiseStudiesAsignMaster(List<UserWiseStudiesAsignMaster> userUpdate) {
		// TODO Auto-generated method stub
		userWiseStudiesAsignDao.updateUserWiseStudiesAsignMaster(userUpdate);
	}
	
	
}
