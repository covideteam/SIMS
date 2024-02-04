package com.covideinfo.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.UserWiseStudiesAsignDao;
import com.covideinfo.model.UserWiseStudiesAsignMaster;
@Repository("userWiseStudiesAsignDao")
public class UserWiseStudiesAsignDaoImpl extends AbstractDao<Long, UserWiseStudiesAsignMaster> implements UserWiseStudiesAsignDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<UserWiseStudiesAsignMaster> findByStudyWiseList(Long study) {
		// TODO Auto-generated method stub
		return createEntityCriteria().add(Restrictions.eq("studyMaster.id", study)).list();
	}

	@Override
	public void saveStudyAsignStudyCreationTime(List<UserWiseStudiesAsignMaster> usersave) {
		// TODO Auto-generated method stub
		for(UserWiseStudiesAsignMaster userwise : usersave)
			getSession().save(userwise);
	}

	@Override
	public void updateUserWiseStudiesAsignMaster(List<UserWiseStudiesAsignMaster> userUpdate) {
		// TODO Auto-generated method stub
		for (UserWiseStudiesAsignMaster userWiseStudiesAsignMaster : userUpdate) {
			getSession().update(userWiseStudiesAsignMaster);
		}
	}
	
	
}
