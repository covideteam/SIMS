package com.covideinfo.dao.impl;

import java.util.Map;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.audittrail.Auditable;
import com.covideinfo.dao.AuditTrailDao;
import com.covideinfo.model.AuditTrail;

@Repository("auditTrailDao")
public class AuditTrailDaoImpl extends AbstractDao<Integer, AuditTrail> implements AuditTrailDao{
	
	public static Long transactionId = 0L;
	
	private Long getIncrementalNumber() {
		if(transactionId == 0) {
		AuditTrail audit = (AuditTrail) createEntityCriteria().addOrder(Order.desc("auditId")).setMaxResults(1).uniqueResult();
		if(audit !=null) {
			transactionId = audit.getTransactionId();
			return transactionId = transactionId+1;
		}else
			return transactionId = 1L;
		}
		return transactionId++;
	}

	@Override
	public void saveAuditTrailDetails(Map<AuditTrail, Auditable> sample, String username) {
		// TODO Auto-generated method stub
		Long auditId = getIncrementalNumber();
		for (Map.Entry<AuditTrail, Auditable> entry : sample.entrySet()) {
			AuditTrail audit = (AuditTrail) entry.getKey();
			Auditable ids = (Auditable) entry.getValue();
			audit.setUserName(username);
			audit.setRowId(ids.getId());
			/*if (reasons != null && reasons.containsKey(audit.getTableClass()))
				audit.setReason(reasons.get(audit.getTableClass()));*/
			audit.setReason("");
			audit.setTransactionId(auditId);
//        	System.out.println(audit.getClass().getName());
			getSession().save(audit);
//			auditTrailDao.saveAuditTrail(audit);

		}
	}
	
	
}
