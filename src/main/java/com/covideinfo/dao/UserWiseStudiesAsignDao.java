package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.model.UserWiseStudiesAsignMaster;

public interface UserWiseStudiesAsignDao {

	List<UserWiseStudiesAsignMaster> findByStudyWiseList(Long study);

	void saveStudyAsignStudyCreationTime(List<UserWiseStudiesAsignMaster> usersave);

	void updateUserWiseStudiesAsignMaster(List<UserWiseStudiesAsignMaster> userUpdate);

}
