package com.covideinfo.dao;

import java.util.Map;

import com.covideinfo.audittrail.Auditable;
import com.covideinfo.model.AuditTrail;

public interface AuditTrailDao {

	void saveAuditTrailDetails(Map<AuditTrail, Auditable> sample, String username);

}
