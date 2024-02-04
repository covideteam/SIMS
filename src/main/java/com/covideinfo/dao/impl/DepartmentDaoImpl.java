package com.covideinfo.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.DepartmentDao;
import com.covideinfo.model.DepartmentMaster;

@Repository("departmentDao")
public class DepartmentDaoImpl extends AbstractDao<Long, DepartmentMaster> implements DepartmentDao {

	@Override
	public long saveDepartmentMasterRecord(DepartmentMaster dept) {
		return (Long) getSession().save(dept);
	}

	@Override
	public DepartmentMaster getDepartmentMasterRecord(String deptName) {
		return (DepartmentMaster) getSession().createCriteria(DepartmentMaster.class)
				.add(Restrictions.eq("deptName", deptName)).uniqueResult();
	}

	
	
}
