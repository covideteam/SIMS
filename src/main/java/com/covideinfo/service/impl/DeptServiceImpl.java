package com.covideinfo.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.DepartmentDao;
import com.covideinfo.model.DepartmentMaster;
import com.covideinfo.service.DeptService;

@Service("deptService")
public class DeptServiceImpl implements DeptService {
	
	@Autowired
	DepartmentDao deptDao;

	@Override
	public long saveDepartMasterMasterRecord(DepartmentMaster dept, String userName) {
		long no =0;
		try {
			dept.setCreatedBy(userName);
			dept.setCreatedOn(new Date());
			no = deptDao.saveDepartmentMasterRecord(dept);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return no;
	}

	@Override
	public DepartmentMaster getDepartmentMasterRecord(String deptName) {
		return deptDao.getDepartmentMasterRecord(deptName);
	}

}
