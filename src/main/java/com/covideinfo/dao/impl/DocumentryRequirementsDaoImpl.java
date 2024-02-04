package com.covideinfo.dao.impl;

import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.DocumentryRequirementsDao;
import com.covideinfo.model.DocumentryRequirements;

@Repository("documentryRequirementsDao")
public class DocumentryRequirementsDaoImpl extends AbstractDao<Long, DocumentryRequirements> implements DocumentryRequirementsDao {

	@Override
	public boolean saveDocumentryRequ(DocumentryRequirements document) {
	boolean flag=false;
		try {
			getSession().save(document);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

}
