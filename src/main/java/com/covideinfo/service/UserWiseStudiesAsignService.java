package com.covideinfo.service;

import java.util.List;

import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserWiseStudiesAsignMaster;

public interface UserWiseStudiesAsignService {

	List<StudyMaster> allUserActiveStudys(Long userId);

	List<UserWiseStudiesAsignMaster> findUsersFindByStudy(Long studyId);

	void saveStudyAsignStudyCreationTime(List<UserWiseStudiesAsignMaster> usersave);

	void updateUserWiseStudiesAsignMaster(List<UserWiseStudiesAsignMaster> userUpdate);
	
}
