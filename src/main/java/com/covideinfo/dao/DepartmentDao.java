package com.covideinfo.dao;

import com.covideinfo.model.DepartmentMaster;

public interface DepartmentDao {

	long saveDepartmentMasterRecord(DepartmentMaster dept);

	DepartmentMaster getDepartmentMasterRecord(String deptName);

}
