package com.covideinfo.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.PDFReportDao;
import com.covideinfo.model.StudyActivityData;

@Repository("pdfReportDao")
public class PDFReportDaoImpl extends AbstractDao<Long, StudyActivityData> implements PDFReportDao {

	@Override
	public StudyActivityData getStudyActivityDataWithId(long id) {
		
		StudyActivityData sad=null;
		sad=(StudyActivityData) getSession().createCriteria(StudyActivityData.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		return sad;
	}

}
