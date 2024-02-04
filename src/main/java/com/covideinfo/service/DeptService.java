package com.covideinfo.service;

import com.covideinfo.model.DepartmentMaster;

public interface DeptService {

	long saveDepartMasterMasterRecord(DepartmentMaster dept, String userName);

	DepartmentMaster getDepartmentMasterRecord(String deptName);
	
	

}
