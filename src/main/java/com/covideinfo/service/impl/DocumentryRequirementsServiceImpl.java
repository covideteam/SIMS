package com.covideinfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.DocumentryRequirementsDao;
import com.covideinfo.model.DocumentryRequirements;
import com.covideinfo.service.DocumentryRequirementsService;

@Service("documentryRequirementsService")
public class DocumentryRequirementsServiceImpl implements DocumentryRequirementsService {

	@Autowired
	DocumentryRequirementsDao documentryRequirementsDao;
	
	@Override
	public boolean saveDocumentryRequ(DocumentryRequirements document) {
		return documentryRequirementsDao.saveDocumentryRequ(document);
	}

}
