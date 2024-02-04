package com.covideinfo.triggers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.covideinfo.configuration.JdbcCollection;

public class TriggerCreationFunction {

	public TriggerCreationFunction() {

	}

	public String triggersCretionFunction() {
		String message = "success";
		Connection con = null;
		Statement stmt = null;

		try {
			con = new JdbcCollection().getJdbcConnection();
			if (con != null) {
				stmt = con.createStatement();
				deletetriggers(stmt);
				generateUnitMastarTrigger(con, stmt); // Working
				generateCustomActivityParameterTrigger(con, stmt); // Working
				generateCunditionsTrigger(con, stmt); // Working
				generateGlobalActivityTrigger(con, stmt); // Working
				generateGlobalGroupTrigger(con, stmt); // Working
				generateGlobalParameterTrigger(con, stmt); // Working
				generateGlobalValues(con, stmt); // Working
				generatedInstrumentModel(con, stmt); // Working
				generateInstrumentType(con, stmt); // Working
				generateLanguageSpecificGlobalActivityDetails(con, stmt); // Working
				generateLanguageSpecificGlobalParameterDetails(con, stmt); // Working
				generateLanguageSpecificGroupDetails(con, stmt); // Working
				generateLanguageSpecificValueDetails(con, stmt); // Working
				generateLanguageSpecificSubjectWithdrawActivity(con, stmt); // Working
				// generateProjectDetailsDiscrepancy (con, stmt); present No Use
//				generateProjectdetailsRandomization(con, stmt); // Working
				// Not Using //generateEpkSubjectReplacedInfo(con, stmt);
//				generateEpkSubjectSampleCollectionTimepointsData(con, stmt); // Working
				// Not Using //generateEpkSubjectTimePointVitalTest(con, stmt);
				generateEpkSubjectVitalTimePointsData(con, stmt); // Working
				// Not Using //generateEpkTimePointVitalTest(con, stmt);
				generateEpkUserMaster(con, stmt); // Working
				// Not Using //generateEpkVitalTest(con, stmt);
				generateEpkWorkFlowReviewStages(con, stmt); // Working
				// Not Using //generateSubjectVitalTimePoints(con, stmt);
				generateUserwiseAssignstudiesMaster(con, stmt); // Working
				// generateVialSeparationData(con, stmt); // Separa
				generateWorkFlows(con, stmt); // Working
				generateControlType(con, stmt); // Static Table
				generateDefaultActivity(con, stmt); // Working
				generateDefaultActivityParameters(con, stmt); // Working
				// generateepk_centrifugation_data(con, stmt); //Execution
				// generateEpkCentrifugationdatamaster(con, stmt); //Execution
				// generateEpkCentrifugation(con, stmt); //Execution
				generateStudySubjectDeviations(con, stmt); // Working
				generateStudySubjects(con, stmt); // Working
				// Not Using //generateStudySubjectReplacedInfo(con, stmt);
				generateStudyTreatments(con, stmt); // Working
//				generateProjectsDetails(con, stmt); // Working
				generateProjects(con, stmt); // Working
				generateProjectsDetailsLog(con, stmt); // Working
				generateProjectsLog(con, stmt); // Working
				generateRandomizationFileStatus(con, stmt); // Working
				generateRandomizationReviewAudit(con, stmt); // Working
				generateRoleWiseModules(con, stmt); // Working
				generateStudyActivityDataCheckIn(con, stmt); // Working
				generateStudyActivityDataCheckOut(con, stmt); // Workinjg
				generateStudyActDataExecution(con, stmt); // Working ---- studydate
				generateStudyActivityRules(con, stmt); // Working
				generateStudyActivityRuleMessages(con, stmt); // Working
				generateStudyActivityTimePoints(con, stmt); // Study Designing Other Activity
				generateStudyActivityTimepointsCompletiondata(con, stmt); // Study Designing Other Activity
				generateStudyActivites(con, stmt); // Working
				generateStudyActivityData(con, stmt); // Working
				generateStudyActivitesParameters(con, stmt); // Working
				// generateStudyActivityDataDiscrepancy(con, stmt); // Discrepancy
				// generateStudyDosageForm(con, stmt); // No idea
				generateStudyDosePerameters(con, stmt); // Working
				generateStudyDoseTimePoints(con, stmt); // Working
				// generateStudyEcgTimePoints(con, stmt); //Check Ones
				generateStudyGroup(con, stmt); // Working
				generateStudyMaster(con, stmt); // Working
				generateStudyMealsTimePoints(con, stmt); // Working
				generateStudyPeriodMaster(con, stmt); // Working
				// generateStudySampleCentrifugation(con, stmt); //Cen
				// Currently Not Using generateStudySampleProcessing(con, stmt);
				// Currently Not Using generateStudySampleProcessingDetails(con, stmt);
				// Currently Not Using generateStudySampleStorage(con, stmt);
				// Currently Not Using generateStudySampleStorageDetails(con, stmt);
				generateStudySampleTimepoints(con, stmt); // Working
				// generateStudySampleCentrifugationDetails(con, stmt); //Cen
				// generateDrugReceptionTable(con,stmt);
				// generateDrugDispensingContainerBarcodes (con,stmt);
				// generateDrugReceptionReviewAudit(con,stmt);
				generateStudySubjectPeriod(con, stmt); // Working
				generateStudyVolunteerReports(con, stmt); // Working
				// generateSubjectMealsTimePoints(con, stmt); //Study Desi
				generateSubjectDoseTimePoints(con, stmt); // Working
				generateSubjectDoseTimePointParameterData(con, stmt); // Working
				generateEpkSubjectMealstimePointData(con, stmt); // Working
//				generatrProjectdetailsrandomization(con, stmt);   //Working
				// generatrStudyVitalTimePoints(con, stmt); // Pedding
				con.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		return message;

	}

	private void deletetriggers(Statement stmt) {
		// TODO Auto-generated method stub
		dropriggers( "unit_mastar_trgr", "units_master", stmt);
		dropriggers( "custom_activity_parameters_trgr", "custom_activity_parameters", stmt);
		dropriggers( "cunditions_trgr", "Condition_Parameter", stmt);
		dropriggers( "global_act_trgr", "global_activity", stmt);
		dropriggers( "global_groups_trgr", "global_groups", stmt);
		dropriggers( "global_parameters_trgr", "global_parameter", stmt);
		dropriggers( "global_values_trgr", "global_values", stmt);
		dropriggers( "instrument_model_trgr", "instrument_model", stmt);
		dropriggers( "instrument_type_trgr", "instrument_type", stmt);
		dropriggers( "language_specific_global_activity_details_trgr", "language_specific_global_activity_details", stmt);
		dropriggers( "language_specific_global_parameter_details_trgr", "language_specific_global_parameter_details", stmt);
		dropriggers( "language_specific_group_details_trgr", "language_specific_group_details", stmt);
		dropriggers( "language_specific_value_details_trgr", "language_specific_value_details", stmt);
		dropriggers( "epk_subject_vital_time_points_data_trgr", "epk_subject_vital_time_points_data", stmt);
		dropriggers( "epk_user_master_trgr", "epk_user_master", stmt);
		dropriggers( "epk_work_flow_review_stages_trgr", "epk_work_flow_review_stages", stmt);
		dropriggers( "userwise_assignstudies_master_trgr", "userwise_assignstudies_master", stmt);
		dropriggers( "work_flows_trgr", "work_flows", stmt);
		dropriggers( "control_type_trgr", "control_type", stmt);
		dropriggers( "default_activity_trgr", "default_activity", stmt);
		dropriggers( "default_activity_parameters_trgr", "default_activity_parameters", stmt);
		dropriggers( "study_subject_deviations_trgr", "study_subject_deviations", stmt);
		dropriggers( "study_subjects_trgr", "study_subjects", stmt);
		dropriggers( "study_treatments_trgr", "study_treatments", stmt);
		dropriggers( "languagespecific_subjectwithdrawactivity_trgr", "languagespecific_subjectwithdrawactivity", stmt);
		dropriggers( "projectdetails_randomization_trgr", "projectdetails_randomization", stmt);
		dropriggers( "epk_subject_sample_collection_timepoints_data_trgr", "epk_subject_sample_collection_timepoints_data", stmt);
		dropriggers( "projects_details_trgr", "projects_details", stmt);
		dropriggers( "projects_trgr", "projects", stmt);
		dropriggers( "projects_details_log_trgr", "projects_details_log", stmt);
		dropriggers( "projects_log_trgr", "projects_log", stmt);
		dropriggers( "randomization_file_status_trgr", "randomization_file_status", stmt);
		dropriggers( "randomization_review_audit_trgr", "randomization_review_audit", stmt);
		dropriggers( "role_wise_modules_trgr", "role_wise_modules", stmt);
		dropriggers( "study_activity_data_check_in_trgr", "study_activity_data_check_in", stmt);
		dropriggers( "study_activity_data_check_out_trgr", "study_activity_data_check_out", stmt);
		dropriggers( "study_activity_data_execution_trgr", "study_activity_data_execution", stmt);
		dropriggers( "study_activity_rules_trgr", "study_activity_rules", stmt);
		dropriggers( "study_activity_rule_messages_trgr", "study_activity_rule_messages", stmt);
		dropriggers( "study_activity_time_points_trgr", "study_activity_time_points", stmt);
		dropriggers( "study_activity_timepoints_completiondata_trgr", "study_activity_timepoints_completiondata", stmt);
		dropriggers( "study_activites_trgr", "study_activites", stmt);
		dropriggers( "study_activity_data_trgr", "study_activity_data", stmt);
		dropriggers( "study_activites_parameters_trgr", "study_activites_parameters", stmt);
		dropriggers( "study_dose_perameters_trgr", "study_dose_perameters", stmt);
		dropriggers( "study_dose_time_points_trgr", "study_dose_time_points", stmt);
		dropriggers( "study_group_trgr", "study_group", stmt);
		dropriggers( "study_master_trgr", "study_master", stmt);
		dropriggers( "study_meals_time_points_trgr", "study_meals_time_points", stmt);
		dropriggers( "study_period_master_trgr", "study_period_master", stmt);
		dropriggers( "study_sample_time_points_trgr", "study_sample_time_points", stmt);
		dropriggers( "study_sub_periods_trgr", "study_subjects_periods", stmt);
		dropriggers( "study_volunteer_reporting_trgr", "study_volunteer_reporting", stmt);
		dropriggers( "sub_dose_time_points_trgr", "subject_dose_timepoints", stmt);
		dropriggers( "sub_dose_tym_pnts_parameter_data_trgr", "subject_dose_time_point_parameters_data", stmt);
		dropriggers( "epk_subject_meals_time_points_data_trgr", "epk_subject_meals_time_points_data", stmt);
		

	}

	private void dropriggers(String name, String table, Statement stmt) {
		// TODO Auto-generated method stub
		try {
			stmt.execute("DROP TRIGGER " + name + " on bedc." + table);
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
		}
	}

	private void generatrStudyVitalTimePoints(Connection con, Statement stmt) throws SQLException {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_vital_time_points_fun() RETURNS TRIGGER AS $$\r\n" + "\r\n"
				+ "\r\n" +

				"declare \r\n" + "incCount integer; \r\n" + "audid integer; \r\n"
				+ "newstudyidName character varying; \r\n" + "oldstudyidName character varying; \r\n" +

				"begin \r\n" + "select count(*) from bedc.activity_log into incCount; \r\n"
				+ "incCount := incCount +1; \r\n" + "if (new.vitalTimePointsId is null) then  \r\n"
				+ "insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on) \r\n"
				+ "values (new.createdBy,'create',new.id,'study_vital_time_points',null,null,null,'study_vital_time_points',new.createdon)RETURNING id INTO incCount; \r\n"
				+

				"IF (new.timepoint IS NOT NULL) THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)  \r\n"
				+ "values ('label.time_point','',new.timepoint,null,null,null,' ',new.created_by,incCount,new.created_on,'study_vital_time_points','static','audit_insert','timepoint'); \r\n"
				+ "END IF; \r\n" +

				"IF (new.sign IS NOT NULL) THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)  \r\n"
				+ "values ('label.sign','',new.sign,null,null,null,' ',new.created_by,incCount,new.created_on,'study_vital_time_points','static','audit_insert','sign'); \r\n"
				+ "END IF; \r\n" +

				"IF (new.timepointno IS NOT NULL) THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)  \r\n"
				+ "values ('label.timepointno','',new.timepointno,null,null,null,' ',new.created_by,incCount,new.created_on,'study_vital_time_points','static','audit_insert','timepointno'); \r\n"
				+ "END IF; \r\n" +

				"IF (new.windowperiodsign IS NOT NULL) THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)  \r\n"
				+ "values ('label.windowperiodsign','',new.windowperiodsign,null,null,null,' ',new.created_by,incCount,new.created_on,'study_vital_time_points','static','audit_insert','windowperiodsign'); \r\n"
				+ "END IF; \r\n" +

				"IF (new.studyid IS NOT NULL) THEN \r\n"
				+ "SELECT projectno FROM bedc.study_master where studyid = new.studyid into newstudyidName;\r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)  \r\n"
				+ "values ('label.studyidName','',newstudyidName,null,null,null,' ',new.created_by,incCount,new.created_on,'study_vital_time_points','static','audit_insert','studyid'); \r\n"
				+ "END IF; \r\n" + "RETURN new;\r\n" +

				// UPDATE TRIGGER

				"ELSE IF (new.vitalTimePointsId=old.vitalTimePointsId) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.id,'study_vital_time_points',null,null,null,'study_vital_time_points',new.updated_on)\r\n"
				+ "RETURNING vitalTimePointsId INTO incCount;\r\n" +

				"IF NULLIF(old.timepoint,'') <> NULLIF(new.timepoint,'') or new.timepoint is null then\r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)  \r\n"
				+ "values ('label.time_point',old.timepoint,new.timepoint,null,null,null,' ',new.created_by,incCount,new.created_on,'study_vital_time_points','static','audit_update','timepoint'); \r\n"
				+ "END IF;\r\n" +

				"IF NULLIF(old.sign,'') <> NULLIF(new.sign,'') or new.sign is null then\r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)  \r\n"
				+ "values ('label.sign',old.sign,new.sign,null,null,null,' ',new.created_by,incCount,new.created_on,'study_vital_time_points','static','audit_update','sign'); \r\n"
				+ "END IF;\r\n" +

				"IF NULLIF(old.timepointno,'') <> NULLIF(new.timepointno,'') or new.timepointno is null then\r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)  \r\n"
				+ "values ('label.timepointno',old.timepointno,new.timepointno,null,null,null,' ',new.created_by,incCount,new.created_on,'study_vital_time_points','static','audit_update','timepointno'); \r\n"
				+ "END IF;\r\n" +

				"IF NULLIF(old.windowperiodsign,'') <> NULLIF(new.windowperiodsign,'') or new.windowperiodsign is null then\r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)  \r\n"
				+ "values ('label.windowperiodsign',old.windowperiodsign,new.windowperiodsign,null,null,null,' ',new.created_by,incCount,new.created_on,'study_vital_time_points','static','audit_update','windowperiodsign'); \r\n"
				+ "END IF;\r\n" +

				"IF NULLIF(old.studyid,'') <> NULLIF(new.studyid,'') or new.studyid is null then\r\n"
				+ "SELECT projectno FROM bedc.study_master where studyid = new.studyid into newstudyidName;\r\n"
				+ "SELECT projectno FROM bedc.study_master where studyid = new.studyid into oldstudyidName;\r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)  \r\n"
				+ "values ('label.studyidName',oldstudyidName,newstudyidName,null,null,null,' ',new.created_by,incCount,new.created_on,'study_vital_time_points','static','audit_update','studyid'); \r\n"
				+ "END IF;\r\n" +

				"return new; \r\n" + "end if; \r\n" + "end if; \r\n" + "end; \r\n" + "$$ LANGUAGE 'plpgsql';");
		stmt.execute("CREATE TRIGGER study_vital_time_points_trgr"
				+ "   AFTER INSERT OR UPDATE  OF vitalTimePointsId,timepoint,sign,timepointno,windowperiodsign,studyid,created_by,created_on ON bedc.study_vital_time_points\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_vital_time_points_fun();");
	}

	private void generateCunditionsTrigger(Connection con, Statement stmt) throws SQLException {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.cunditions_fun() RETURNS TRIGGER AS $$\r\n" + "\r\n" + "\r\n" +

				"declare \r\n" + "incCount integer; \r\n" + "audid integer; \r\n" +

				"begin \r\n" + "select count(*) from bedc.activity_log into incCount; \r\n"
				+ "incCount := incCount +1; \r\n" + "if (old.id is null) then  \r\n"
				+ "insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on) \r\n"
				+ "values (new.createdBy,'create',new.id,'condition_parameter',null,null,null,'condition_parameter_creation',new.createdon)RETURNING id INTO incCount; \r\n"
				+

				"IF (new.dropdown IS NOT NULL) THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)  \r\n"
				+ "values ('label.ggType','',new.dropdown,null,null,null,' ',new.createdby,incCount,new.createdon,'condition_parameter','static','audit_insert','dropdown'); \r\n"
				+ "END IF; \r\n" +

				"IF (new.name IS NOT NULL) THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "values ('label.name','',new.name,null,null,null,' ',new.createdby,incCount,new.createdon,'condition_parameter','static','audit_insert','name'); \r\n"
				+ "END IF; \r\n" + "return new; \r\n" +

				"else if (new.id = old.id) then \r\n"
				+ "insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on) \r\n"
				+ "values (null,'update',new.id,'condition_parameter',null,null,null,'condition_parameter_updation',null)RETURNING id INTO incCount; \r\n"
				+

				"IF nullif(old.dropdown,'') <> NULLIF(new.dropdown,'') or new.dropdown is null THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname)  \r\n"
				+ "values ('label.ggType',old.dropdown,new.dropdown,null,null,null,' ',null,incCount,null,'condition_parameter','static','audit_update','dropdown'); \r\n"
				+ "END IF; \r\n" +

				"IF nullif(old.name,'') <> NULLIF(new.name,'') or new.name is null THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "values ('label.name',old.name,new.name,null,null,null,' ',null,incCount,null,'condition_parameter','static','audit_update','name'); \r\n"
				+ "END IF; \r\n" + "return new; \r\n" + "end if; \r\n" + "end if; \r\n" + "end; \r\n"
				+ "$$ LANGUAGE 'plpgsql';");
		
		stmt.execute("CREATE TRIGGER cunditions_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, activeandinactive,dropdown,name,createdBy,createdOn ON bedc.Condition_Parameter\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.cunditions_fun();");

	}

	private void generateCustomActivityParameterTrigger(Connection con, Statement stmt) throws SQLException {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.custom_activity_parameters_fun() RETURNS TRIGGER AS $$\r\n"
				+ "\r\n" + "\r\n" +

				"declare \r\n" + "incCount integer; \r\n" + "audid integer; \r\n" +

				"newact character varying;  \r\n" + "oldact character varying; \r\n" + "newparm character varying; \r\n"
				+ "oldparm character varying; \r\n" +

				"begin \r\n" + "select count(*) from bedc.activity_log into incCount;\r\n"
				+ "incCount := incCount +1; \r\n" + "if (old.id is null) then \r\n"
				+ "insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "values (new.created_by,'create',new.id,'custom_act_parameter',null,null,null,'custom_act_parameter_creation',new.created_on)RETURNING id INTO incCount;\r\n"
				+ "IF (new.parameter_value IS NOT NULL) THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "values ('label.cusparameterValue','',new.parameter_value,null,null,null,' ',new.created_by,incCount,new.created_on,'custom_act_parameter','static','audit_insert','parameter_value');\r\n"
				+ "END IF; \r\n" + "select name from bedc.global_activity into newact where id=new.activity; \r\n"
				+ "IF (new.activity IS NOT NULL) THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "values ('label.cusActParmAct','',newact,null,null,null,' ',new.created_by,incCount,new.created_on,'custom_act_parameter','Static','audit_insert','activity'); \r\n"
				+ "END IF; \r\n"
				+ "select parameter_name from bedc.global_parameter into newparm where id=new.parameter; \r\n"
				+ " IF (new.parameter IS NOT NULL) THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)  \r\n"
				+ "values ('label.cusActParmParm','',newparm,null,null,null,' ',new.created_by,incCount,new.created_on,'custom_act_parameter','Static','audit_insert','parmater'); \r\n"
				+ "END IF; \r\n" +

				"return new; \r\n" + "else if (new.id = old.id) then  \r\n"
				+ "insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on) \r\n"
				+ "values (null,'update',new.id,'custom_act_parameter',null,null,null,'custom_act_parameter_updation',null)RETURNING id INTO incCount;\r\n"
				+ "IF nullif(old.parameter_value,'') <> NULLIF(new.parameter_value,'') or new.parameter_value is null THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "values ('label.cusparameterValue',old.parameter_value,new.parameter_value,null,null,null,' ',null,incCount,null,'custom_act_parameter','Static','audit_update','parameter_value');\r\n"
				+ "END IF; \r\n" + "select name from bedc.global_activity into newact where id=new.activity; \r\n"
				+ "select name from bedc.global_activity into oldact where id=old.activity;\r\n"
				+ "IF nullif(old.activity,null) <> NULLIF(new.activity,null) or new.activity is null THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "values ('label.cusActParmAct',oldact,newact,null,null,null,' ',null,incCount,null,'custom_activity_parameter','Static','audit_update','activity'); \r\n"
				+ "END IF; \r\n"
				+ "select parameter_name from bedc.global_parameter into newparm where id=new.parameter;\r\n"
				+ "select parameter_name from bedc.global_parameter into oldparm where id=old.parameter;\r\n"
				+ "IF nullif(old.parameter,null) <> NULLIF(new.parameter,null) or new.parameter is null THEN\r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "values ('label.cusActParmParm',oldparm,newparm,null,null,null,' ',null,incCount,null,'custom_activity_parameter','Static','audit_update','parameter');\r\n"
				+ "END IF; \r\n" + "return new; \r\n" + "end if; \r\n" + "end if; \r\n" + "end; \r\n" +

				"$$ LANGUAGE 'plpgsql';");
		
		stmt.execute("CREATE TRIGGER custom_activity_parameters_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, parameter_value,activity,parameter,created_by,created_on ON bedc.custom_activity_parameters\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.custom_activity_parameters_fun();");

	}

	private void generateUnitMastarTrigger(Connection con, Statement stmt) throws SQLException {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.unit_mastar_fun() RETURNS TRIGGER AS $$\r\n" + "\r\n" + "\r\n"
				+ "declare \r\n" + "incCount integer; \r\n" + "audid integer; \r\n" + "begin \r\n"
				+ "select count(*) from bedc.activity_log into incCount;\r\n" + "incCount := incCount +1; \r\n"
				+ "if (old.id is null) then  \r\n"
				+ "insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on) \r\n"
				+ "values (null,'create',new.id,'unit_master',null,null,null,'unit_master_creation',null)RETURNING id INTO incCount; \r\n"
				+ "IF (new.unit_code IS NOT NULL) THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "values ('label.unit.units','',new.unit_code,null,null,null,null,new.created_by,incCount,new.created_on,'unit_master','Static','audit_insert','unit_code'); \r\n"
				+ "END IF; \r\n" +

				"return new; \r\n" +

				"else if (new.id = old.id) then  \r\n"
				+ "insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on) \r\n"
				+ "values (new.updated_by,'update',new.id,'unit_master',null,null,null,'unit_master_updation',new.updated_on)RETURNING id INTO incCount; \r\n"
				+ "IF nullif(old.unit_code,'') <> NULLIF(new.unit_code,'') or new.unit_code is null THEN \r\n"
				+ "insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "values ('label.unit.units',old.unit_code,new.unit_code,null,null,null,new.update_reason,new.updated_by,incCount,new.updated_on,'unit_master','Static','audit_update','unit_code'); \r\n"
				+ "END IF; \r\n" +

				"return new; \r\n" + "end if; \r\n" + "end if; \r\n" + "end; \r\n" +

				"$$ LANGUAGE 'plpgsql';");

		stmt.execute(
				"CREATE TRIGGER unit_mastar_trgr" + "   AFTER INSERT OR UPDATE  OF id, unit_code,created_by,created_on,"
						+ "updated_by,updated_on,update_reason ON bedc.units_master\r\n" + "    FOR EACH ROW\r\n"
						+ "    EXECUTE FUNCTION bedc.unit_mastar_fun();");
	}

	private void generatrProjectdetailsrandomization(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.projectdetails_randomization_fun() RETURNS TRIGGER AS $$\r\n"
				+ "\r\n" + "\r\n" + "declare \r\n" + "incCount integer;\r\n" + "actid integer;\r\n" + "\r\n"
				+ "BEGIN\r\n" + "SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ "incCount := incCount +1;\r\n" + "select count(*) from bedc.audit_log into actid;\r\n"
				+ "actid := actid +1;\r\n" + "IF (old.id IS NULL) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.createdby,'CREATE',new.id,'projectdetails_randomization',null,null,null,'projectdetails_randomization_creation',new.createdon) RETURNING id INTO incCount;\r\n"
				+ "IF (new.period IS NOT NULL) THEN	\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.period','',new.period,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'projectdetails_randomization','audit_insert','period');\r\n"
				+ "END IF;\r\n" + "IF (new.projectno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.projectno','',new.projectno,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'projectdetails_randomization','audit_insert','projectno');\r\n"
				+ "END IF;\r\n" + "IF (new.randomizationcode IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.randomizationcode','',new.randomizationcode,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'projectdetails_randomization','audit_insert','randomizationcode'); \r\n"
				+ "END IF;\r\n" + "IF (new.status IS NOT NULL) THEN	\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.status','',new.status,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'projectdetails_randomization','audit_insert','status'); \r\n"
				+ "END IF;\r\n" + "IF (new.subjectno IS NOT NULL) THEN		\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectno','',new.subjectno,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'projectdetails_randomization','audit_insert','subjectno'); \r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" +
				// update
				"ELSE IF (new.id=old.id) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updatedby,'UPDATE',new.id,'projectdetails_randomization',null,null,null,'projectdetails_randomization_updation',new.updatedon)\r\n"
				+ "RETURNING id INTO incCount;\r\n"
				+ "IF NULLIF(old.period,'') <> NULLIF(new.period,'') or new.period is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.period',old.period,new.period,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'projectdetails_randomization','audit_update','period');  \r\n"
				+ "END IF;\r\n"
				+ "IF cast(old.projectno as varchar) <> cast(new.projectno as varchar) or new.projectno is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.projectno',old.projectno,new.projectno,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'projectdetails_randomization','audit_update','projectno'); \r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.randomizationcode,'') <> NULLIF(new.randomizationcode,'') or new.randomizationcode is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.randomizationcode',old.randomizationcode,new.randomizationcode,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'projectdetails_randomization','audit_update','randomizationcode'); \r\n"
				+ "END IF;\r\n" + "IF NULLIF(old.status,'') <> NULLIF(new.status,'') or new.status is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.status',old.status,new.status,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'projectdetails_randomization','audit_update','status');  \r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.subjectno,'') <> NULLIF(new.subjectno,'') or new.subjectno is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name ,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectno',old.subjectno,new.subjectno,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'projectdetails_randomization','audit_update','subjectno');     \r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");
		stmt.execute("CREATE TRIGGER projectdetails_randomization_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, comments,createdby,createdon,period,projectno,randomizationcode,status,"
				+ "  subjectno, update_reason,updatedby,updatedon ON bedc.projectdetails_randomization\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.projectdetails_randomization_fun();");
	}

	private void generateEpkSubjectMealstimePointData(Connection con, Statement stmt) throws Exception {
		stmt.execute(
				"CREATE OR REPLACE FUNCTION bedc.epk_subject_meals_time_points_data_fun() RETURNS TRIGGER AS $$\r\n"
						+ "\r\n" + "\r\n" + "\r\n" + "declare \r\n" + "   incCount integer;\r\n"
						+ "   actid integer;\r\n" + "   \r\n" + "   newcreatedby character varying;\r\n"
						+ "   newupdatedby character varying;\r\n" + "   newmealstimepointsid character varying;\r\n"
						+ "   oldmealstimepointsid character varying;\r\n" + "   newstartedby  character varying;\r\n"
						+ "   oldstartedby  character varying;\r\n" + "   newendby  character varying;\r\n"
						+ "   oldendby  character varying;\r\n" + "   newstudyperiodmasterid  character varying;\r\n"
						+ "   oldstudyperiodmasterid  character varying;\r\n"
						+ "   newcollectedby  character varying;\r\n" + "   oldcollectedby character varying;\r\n"
						+ "   newcompletion  character varying;\r\n" + "   oldcompletion  character varying;\r\n"
						+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
						+ " incCount := incCount +1;\r\n"
						+ " select user_name from bedc.epk_user_master where userid =new.createdby into newcreatedby;\r\n"
						+ " select user_name from bedc.epk_user_master where userid =new.updatedby into newupdatedby;\r\n"
						+ "IF (old.subjectmealstimepointsdataid IS NULL) THEN\r\n"
						+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ "VALUES (newcreatedby,'CREATE',new.subjectmealstimepointsdataid,'epk_subject_meals_time_points_data',null,new.subjectid,null,'epk_subject_meals_time_points_data_creation',new.createdon)RETURNING id INTO incCount; \r\n"
						+ "IF (new.actualtime IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.actualtime','',new.actualtime,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_meals_time_points_data','audit_insert','actualtime');\r\n"
						+ "END IF;\r\n" + "IF (new.consumption IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.consumption','',new.consumption,'Static',null,null,new.subjectid,newcreatedby,new.createdon,incCount,'epk_subject_meals_time_points_data','audit_insert','consumption');\r\n"
						+ "END IF;\r\n" + "IF (new.deviation IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.deviation','',new.deviation,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_meals_time_points_data','audit_insert','scheduledate');\r\n"
						+ "END IF;\r\n" + "IF (new.endon IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.endon','',new.endon,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_meals_time_points_data','audit_insert','endon');\r\n"
						+ "END IF;\r\n" + "IF (new.endtime IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.endtime','',new.endtime,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_meals_time_points_data','audit_insert','endtime');\r\n"
						+ "END IF;\r\n" + "IF (new.scheduletime IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.scheduletime','',new.scheduletime,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_meals_time_points_data','audit_insert','scheduletime');\r\n"
						+ "END IF;\r\n" + "IF (new.starttime IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.starttime','',new.starttime,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_meals_time_points_data','audit_insert','starttime');\r\n"
						+ "END IF;\r\n"
						+ "SELECT user_name FROM bedc.epk_user_master where userid = new.endby into newendby;\r\n"
						+ "IF (new.endby IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('endby','',newendby,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_meals_time_points_data','audit_insert','endby');\r\n"
						+ "END IF;\r\n"
						+ "SELECT sign FROM bedc.study_meals_time_points where sampletimepointid = new.mealstimepointid into newmealstimepointsid;\r\n"
						+ "IF (new.mealstimepointid IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('mealstimepointid','',newmealstimepointsid,'Dynamic',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_meals_time_points_data','audit_insert','mealstimepointsid');\r\n"
						+ "END IF;\r\n"
						+ "SELECT user_name FROM bedc.epk_user_master where userid = new.startedby into newstartedby;\r\n"
						+ "IF (new.startedby IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('startedby','',newstartedby,'Dynamic',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_meals_time_points_data','audit_insert','startedby');\r\n"
						+ "END IF;\r\n"
						+ "SELECT periodname FROM bedc.study_period_master where periodid = new.studyperiodmasterid into newstudyperiodmasterid;\r\n"
						+ "IF (new.studyperiodmasterid IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.studyperiodmasterid','',newstudyperiodmasterid,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_meals_time_points_data','audit_insert','scheduledate');\r\n"
						+ "END IF;\r\n" + "\r\n" +

						"RETURN new;\r\n" + "\r\n"
						+ "ELSE IF (new.subjectmealstimepointsdataid =old.subjectmealstimepointsdataid) THEN\r\n"
						+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ "VALUES (newupdatedby,'UPDATE',new.subjectmealstimepointsdataid,'epk_subject_meals_time_points_data',null,new.subjectid,null,'epk_subject_meals_time_points_data_updation',new.updatedon)RETURNING id INTO incCount;\r\n"
						+ "IF nullif(old.actualtime,null) <> NULLIF(new.actualtime,null) or new.actualtime is not null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.actualtime',old.actualtime,new.actualtime,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_meals_time_points_data','audit_update','actualtime');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.consumption,'') <> NULLIF(new.consumption,'') or new.consumption is not null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.consumption',old.consumption,new.consumption,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_meals_time_points_data','audit_update','consumption');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.deviation,'') <> NULLIF(new.deviation,'') or new.deviation is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.deviation',old.deviation,new.deviation,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_meals_time_points_data','audit_update','deviation');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.endon,null) <> NULLIF(new.endon,null) or new.endon is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.endon',old.endon,new.endon,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_meals_time_points_data','audit_update','endon');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.endtime,null) <> NULLIF(new.endtime,null) or new.endtime is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.endtime',old.endtime,new.endtime,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_meals_time_points_data','audit_update','endtime');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.scheduletime,null) <> NULLIF(new.scheduletime,null) or new.scheduletime is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.scheduletime','',new.scheduletime,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_meals_time_points_data','audit_update','scheduletime');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.starttime,null) <> NULLIF(new.starttime,null) or new.starttime is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.starttime',old.starttime,new.starttime,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_meals_time_points_data','audit_update','starttime');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.startedon,null) <> NULLIF(new.startedon,null) or new.startedon is not null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.startedon',old.startedon,new.startedon,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_meals_time_points_data','audit_update','startedon');\r\n"
						+ "END IF;\r\n"
						+ "SELECT user_name FROM bedc.epk_user_master where userid = new.endby into newendby;\r\n"
						+ "SELECT user_name FROM bedc.epk_user_master where userid = old.endby into oldendby;\r\n"
						+ "\r\n"
						+ "IF nullif(old.endby,null) <> NULLIF(new.endby,null) or new.endby is not null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.endby',oldendby,newendby,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_meals_time_points_data','audit_update','endby');\r\n"
						+ "END IF;\r\n" + "\r\n"
						+ "SELECT sign FROM bedc.study_meals_time_points where sampletimepointid = new.mealstimepointid into newmealstimepointsid;\r\n"
						+ "SELECT sign FROM bedc.study_meals_time_points where sampletimepointid = old.mealstimepointid into oldmealstimepointsid;\r\n"
						+ "IF nullif(old.mealstimepointid,null) <> NULLIF(new.mealstimepointid,null) or new.mealstimepointid is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('mealstimepointid',oldmealstimepointsid,newmealstimepointsid,'Dynamic',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_meals_time_points_data','audit_update','mealstimepointsid');\r\n"
						+ "END IF;\r\n"
						+ "SELECT user_name FROM bedc.epk_user_master where userid = new.startedby into newstartedby;\r\n"
						+ "SELECT user_name FROM bedc.epk_user_master where userid = old.startedby into oldstartedby;\r\n"
						+ "IF nullif(old.startedby,null) <> NULLIF(new.startedby,null) or new.startedby is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('startedby',oldstartedby,newstartedby,'Dynamic',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_meals_time_points_data','audit_update','startedby');\r\n"
						+ "END IF;\r\n"
						+ "SELECT periodname FROM bedc.study_period_master where periodid = new.studyperiodmasterid into newstudyperiodmasterid;\r\n"
						+ "SELECT periodname FROM bedc.study_period_master where periodid = old.studyperiodmasterid into oldstudyperiodmasterid;\r\n"
						+ "IF nullif(old.studyperiodmasterid,null) <> NULLIF(new.studyperiodmasterid,null) or new.studyperiodmasterid is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('studyperiodmasterid',oldstudyperiodmasterid,newstudyperiodmasterid,'Dynamic',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_meals_time_points_data','audit_update','studyperiodmasterid');\r\n"
						+ "END IF;\r\n" +

						"RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n" + "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER epk_subject_meals_time_points_data_trgr"
				+ "   AFTER INSERT OR UPDATE  OF subjectmealstimepointsdataid, createdon,updatereason,updatedon,actualtime,comments,consumption,deviation, endon,endtime,scheduletime,starttime,startedon,updatedby,endby,mealstimepointid,startedby,studyperiodmasterid,subjectid,"
				+ "createdby  ON bedc.epk_subject_meals_time_points_data\r\n" + "FOR EACH ROW\r\n"
				+ "EXECUTE FUNCTION bedc.epk_subject_meals_time_points_data_fun();");
	}

	private void generateSubjectDoseTimePointParameterData(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.sub_dose_tym_pnts_parameter_data_fun() RETURNS TRIGGER AS $$\r\n"
				+ "\r\n" + "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   parameter   character varying;\r\n" + "   oldparameter character varying;\r\n"
				+ "   newglobalvalue  character varying;\r\n" + "   oldglobalvalue  character varying;\r\n"
				+ "   newsubdosetympnt  character varying;\r\n" + "   subdosetympnt  character varying;\r\n"
				+ "   createdby character varying;\r\n" + "   updatedby character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select  user_name from bedc.epk_user_master where userid=new.created_by into createdby;\r\n"
				+ " select  user_name from bedc.epk_user_master where userid=new.updated_by into updatedby;\r\n"
				+ "\r\n" + "IF (old.id IS NULL) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (createdby,'CREATE',new.id,'subject_dose_time_point_parameters_data',null,null,null,'subject_dose_time_point_parameters_data_creation',new.created_on) RETURNING id INTO incCount; \r\n"
				+ "IF (new.parameter_value IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.parameter_value','',new.parameter_value,'static',new.study_id,null,null,' ',createdby,new.created_on,incCount,'subject_dose_time_point_parameters_data','audit_insert','parameter_value');\r\n"
				+ "END IF;\r\n" + "\r\n"
				+ "SELECT name FROM bedc.global_values where id = new.global_value into newglobalvalue;\r\n"
				+ "IF (new.global_value IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.global_value','',newglobalvalue,'static',new.study_id,null,null,' ',createdby,new.created_on,incCount,'subject_dose_time_point_parameters_data','audit_insert','global_value');\r\n"
				+ "END IF;\r\n"
				+ " SELECT parameter_name FROM bedc.global_parameter where id = new.parameter into parameter;\r\n"
				+ "IF (new.parameter IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('parameter','',parameter,'Dynamic',new.study_id,null,null,' ',createdby,new.created_on,incCount,'subject_dose_time_point_parameters_data','audit_insert','parameter');\r\n"
				+ "END IF;\r\n"
				+ "SELECT fastcriteracomments FROM bedc.subject_dose_timepoints where subjectdosetimepointsid = new.subject_dose_time_point into newsubdosetympnt;\r\n"
				+ "IF (new.subject_dose_time_point IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('subject_dose_time_point','',newsubdosetympnt,'Dynamic',new.study_id,null,null,' ',createdby,new.created_on,incCount,'subject_dose_time_point_parameters_data','audit_update','subject_dose_time_point');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "\r\n" + "ELSE IF (new.id=old.id) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (updatedby,'UPDATE',new.id,'subject_dose_time_point_parameters_data',new.study_id,null,null,'subject_dose_time_point_parameters_data_updation',new.updated_on) RETURNING id INTO incCount;\r\n"
				+ "IF nullif(old.parameter_value,null) <> NULLIF(new.parameter_value,null) or new.parameter_value is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.parameter_value',old.parameter_value,new.parameter_value,'static',new.study_id,null,null,' ',updatedby,new.updated_on,incCount,'subject_dose_time_point_parameters_data','audit_update','parameter_value');\r\n"
				+ "END IF;\r\n" + "\r\n"
				+ "SELECT name FROM bedc.global_values where id = new.global_value into newglobalvalue;\r\n"
				+ "SELECT name FROM bedc.global_values where id = old.global_value into oldglobalvalue;\r\n"
				+ "IF nullif(old.global_value,null) <> NULLIF(new.global_value,null) or new.global_value is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('global_value',oldglobalvalue,newglobalvalue,'Dynamic',new.study_id,null,null,' ',updatedby,new.updated_on,incCount,'subject_dose_time_point_parameters_data','audit_update','global_value');\r\n"
				+ "END IF;\r\n" + "\r\n"
				+ "SELECT parameter_name FROM bedc.global_parameter where id = old.parameter into oldparameter;\r\n"
				+ "SELECT parameter_name FROM bedc.global_parameter where id = new.parameter into parameter;\r\n"
				+ "IF nullif(old.parameter,null) <> NULLIF(new.parameter,null) or new.parameter is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('parameter',oldparameter,parameter,'Dynamic',new.study_id,null,null,' ',updatedby,new.updated_on,incCount,'subject_dose_time_point_parameters_data','audit_update','parameter');\r\n"
				+ "END IF;\r\n"
				+ "SELECT fastcriteracomments FROM bedc.subject_dose_timepoints where subjectdosetimepointsid = new.subject_dose_time_point into newsubdosetympnt;\r\n"
				+ "SELECT fastcriteracomments FROM bedc.subject_dose_timepoints where subjectdosetimepointsid = old.subject_dose_time_point into subdosetympnt;\r\n"
				+ "IF nullif(old.subject_dose_time_point,null) <> NULLIF(new.subject_dose_time_point,null) or new.subject_dose_time_point is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('subject_dose_time_point',subdosetympnt,newsubdosetympnt,'Dynamic',new.study_id,null,null,' ',updatedby,new.updated_on,incCount,'subject_dose_time_point_parameters_data','audit_update','subject_dose_time_point');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER sub_dose_tym_pnts_parameter_data_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, created_on,parameter_value,study_id,global_value,created_by,updated_on,parameter,"
				+ " subject_dose_time_point, updated_by ON bedc.subject_dose_time_point_parameters_data\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.sub_dose_tym_pnts_parameter_data_fun();");
	}

	private void generateSubjectDoseTimePoints(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.sub_dose_time_points_fun() RETURNS TRIGGER AS $$\r\n" + "\r\n"
				+ "\r\n" + "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "  periodsName  character varying;\r\n" + "  oldPeriodsName character varying;\r\n"
				+ "   messageName  character varying;\r\n" + "   oldMessageName character varying;\r\n"
				+ " parametersName   character varying;\r\n" + " oldParametersName character varying;\r\n"
				+ "   SupervisedByName character varying; \r\n" + "   oldSupervisedByName character varying;\r\n"
				+ "   subjectnoName   character varying;\r\n" + "    oldSubjectnoName character varying;\r\n"
				+ "    fullName   character varying;\r\n" + "	oldFullName character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ "IF (old.subjectdosetimepointsid IS NULL) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.created_by,'CREATE',new.subjectdosetimepointsid,'subject_dose_timepoints',null,null,null,'subject_dose_timepoints_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+ "\r\n" + "IF (new.actualtime IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.actualtime','',new.actualtime,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','actualtime');\r\n"
				+ "END IF;\r\n" + "IF (new.collectionsectime IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.Collectionsectime','',new.collectionsectime,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','collectionsectime');\r\n"
				+ "END IF;\r\n" + "IF (new.diviation IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.diviation','',new.diviation,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','diviation');\r\n"
				+ "END IF;\r\n" + "IF (new.fastcriteracomments IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.fastcriteracomments','',new.fastcriteracomments,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','fastcriteracomments');\r\n"
				+ "END IF;\r\n" +

				"IF (new.sachetscantime IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.sachetscantime','',new.sachetscantime,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','sachetscantime');\r\n"
				+ "END IF;\r\n" + "IF (new.scheduletime IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.scheduletime','',new.scheduletime,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','scheduletime');\r\n"
				+ "END IF;\r\n" + "IF (new.subjectscantime IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectscantime','',new.subjectscantime,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','subjectscantime');\r\n"
				+ "END IF;\r\n" + "IF (new.submissiontime IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.submissiontime','',new.submissiontime,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','submissiontime');\r\n"
				+ "END IF;\r\n" + "IF (new.supervisedon IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.supervisedon','',new.supervisedon,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','supervisedon');\r\n"
				+ "END IF;\r\n" +

				"\r\n" + "\r\n" + "IF new.periodid IS NOT NULL THEN\r\n"
				+ "SELECT periodname FROM bedc.study_period_master where periodid = new.periodid into periodsName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('PeriodId','',periodsName,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','periodid');\r\n"
				+ "END IF;\r\n" + "IF new.dosetimepointsid IS NOT NULL THEN\r\n"
				+ "SELECT parameters FROM bedc.study_dose_time_points where dosetimepointsid = new.dosetimepointsid into parametersName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('DoseTimepointsId','',parametersName,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','dosetimepointsid');\r\n"
				+ "END IF;\r\n" + "IF new.deviation_msg IS NOT NULL THEN\r\n"
				+ "SELECT message FROM bedc.deviation_message where deviationmessageid = new.deviation_msg into messageName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('DeviationMsg','',messageName,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','deviation_msg');\r\n"
				+ "END IF;\r\n" + "IF new.supervisedby IS NOT NULL THEN\r\n"
				+ "SELECT full_name FROM bedc.epk_user_master where userid = new.supervisedby into SupervisedByName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('SupervisedBy','',SupervisedByName,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','supervisedby');\r\n"
				+ "END IF;\r\n" + "IF new.studysubjectsid IS NOT NULL THEN\r\n"
				+ "SELECT subject_no FROM bedc.study_subjects where id = new.studysubjectsid into subjectnoName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('StudySubjectsId','',subjectnoName,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','studysubjectsid');\r\n"
				+ "END IF;\r\n" + "IF new.dosedoneby IS NOT NULL THEN\r\n"
				+ "SELECT full_name FROM bedc.epk_user_master where userid = new.dosedoneby into fullName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('DosedOneby','',fullName,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'subject_dose_timepoints','audit_insert','dosedoneby');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "\r\n"
				+ "ELSE IF (new.subjectdosetimepointsid=old.subjectdosetimepointsid) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	 VALUES (new.updated_by,'UPDATE',new.subjectdosetimepointsid,'subject_dose_timepoints',null,null,null,'subject_dose_timepoints_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+ "IF nullif(old.actualtime,null) <> NULLIF(new.actualtime,null) or new.actualtime is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.actualtime',old.actualtime,new.actualtime,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','actualtime');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.collectionsectime,null) <> NULLIF(new.collectionsectime,null) or new.collectionsectime is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.collectionsectime',old.collectionsectime,new.collectionsectime,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','collectionsectime');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.diviation,null) <> NULLIF(new.diviation,null) or new.diviation is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.diviation',old.diviation,new.diviation,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','diviation');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.fastcriteracomments,null) <> NULLIF(new.fastcriteracomments,null) or new.fastcriteracomments is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.fastcriteracomments',old.fastcriteracomments,new.fastcriteracomments,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','fastcriteracomments');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.sachetscantime,null) <> NULLIF(new.sachetscantime,null) or new.sachetscantime is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.sachetscantime',old.sachetscantime,new.sachetscantime,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','sachetscantime');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.scheduletime,null) <> NULLIF(new.scheduletime,null) or new.scheduletime is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.scheduletime',old.scheduletime,new.scheduletime,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','scheduletime');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.subjectscantime,null) <> NULLIF(new.subjectscantime,null) or new.subjectscantime is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectscantime',old.subjectscantime,new.subjectscantime,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','subjectscantime');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.submissiontime,null) <> NULLIF(new.submissiontime,null) or new.submissiontime is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.submissiontime',old.submissiontime,new.submissiontime,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','submissiontime');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.supervisedon,null) <> NULLIF(new.supervisedon,null) or new.supervisedon is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.supervisedon',old.supervisedon,new.supervisedon,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','supervisedon');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.periodid,null) <> NULLIF(new.periodid,null) or new.periodid is null THEN\r\n"
				+ "SELECT periodname FROM bedc.study_period_master where periodid = new.periodid into periodsName;\r\n"
				+ "SELECT periodname FROM bedc.study_period_master where periodid = old.periodid into oldPeriodsName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('Periodid',oldPeriodsName,periodsName,'dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','periodid');\r\n"
				+ "END IF;\r\n" + "\r\n"
				+ "IF nullif(old.dosetimepointsid,null) <> NULLIF(new.dosetimepointsid,null) or new.dosetimepointsid is null THEN\r\n"
				+ "SELECT parameters FROM bedc.study_dose_time_points where dosetimepointsid = new.dosetimepointsid into parametersName;\r\n"
				+ "SELECT parameters FROM bedc.study_dose_time_points where dosetimepointsid = old.dosetimepointsid into oldParametersName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('Dosetimepointsid',oldParametersName,parametersName,'dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','dosetimepointsid');\r\n"
				+ "END IF;\r\n" + "\r\n"
				+ "IF nullif(old.deviation_msg,null) <> NULLIF(new.deviation_msg,null) or new.deviation_msg is null THEN\r\n"
				+ "SELECT message FROM bedc.deviation_message where deviationmessageid = new.deviation_msg into messageName;\r\n"
				+ "SELECT message FROM bedc.deviation_message where deviationmessageid = old.deviation_msg into oldMessageName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('deviation msg',oldMessageName,messageName,'dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','deviation_msg');\r\n"
				+ "END IF;\r\n" + "\r\n" + "\r\n"
				+ "IF nullif(old.supervisedby,null) <> NULLIF(new.supervisedby,null) or new.supervisedby is null THEN\r\n"
				+ "SELECT full_name FROM bedc.epk_user_master where userid = new.supervisedby into SupervisedByName;\r\n"
				+ "SELECT full_name FROM bedc.epk_user_master where userid = old.supervisedby into oldSupervisedByName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.supervisedby',oldSupervisedByName,SupervisedByName,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','supervisedby');\r\n"
				+ "END IF;\r\n" + "\r\n"
				+ "IF nullif(old.studysubjectsid,null) <> NULLIF(new.studysubjectsid,null) or new.studysubjectsid is null THEN\r\n"
				+ "SELECT subject_no FROM bedc.study_subjects where id = new.studysubjectsid into subjectnoName;\r\n"
				+ "SELECT subject_no FROM bedc.study_subjects where id = old.studysubjectsid into oldSubjectnoName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.studysubjectsid',oldSubjectnoName,subjectnoName,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','studysubjectsid');\r\n"
				+ "END IF;\r\n" + "\r\n"
				+ "IF nullif(old.dosedoneby,null) <> NULLIF(new.dosedoneby,null) or new.dosedoneby is null THEN\r\n"
				+ "SELECT full_name FROM bedc.epk_user_master where userid = new.dosedoneby into fullName;\r\n"
				+ "SELECT full_name FROM bedc.epk_user_master where userid = new.dosedoneby into oldFullName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.dosedoneby',oldFullName,fullName,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'subject_dose_timepoints','audit_update','dosedoneby');\r\n"
				+ "END IF;\r\n" + "\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER sub_dose_time_points_trgr"
				+ "   AFTER INSERT OR UPDATE  OF subjectdosetimepointsid, actualtime,collectionsectime,created_on,diviation,fastcriteracomments,feadcriteracomments"
				+ ",sachetscantime,scheduletime,subjectscantime, submissiontime,supervisedon,updatereason, updated_on,created_by,"
				+ " deviation_msg, dosedoneby,dosetimepointsid, periodid, "
				+ "studysubjectsid, updated_by  ON bedc.subject_dose_timepoints\r\n" + "    FOR EACH ROW\r\n"
				+ "    EXECUTE FUNCTION bedc.sub_dose_time_points_fun();");
	}

	private void generateSubjectMealsTimePoints(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.sub_meals_time_points_fun() RETURNS TRIGGER AS $$\r\n" + "\r\n"
				+ "   declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n" + "   \r\n"
				+ "   createdby character varying;\r\n" + "   updatedby character varying;\r\n"
				+ "   newmealstimepointsid character varying;\r\n" + "   oldmealstimepointsid character varying;\r\n"
				+ "   newperiod  character varying;\r\n" + "   oldperiod  character varying;\r\n"
				+ "   newsubmealstympnt  character varying;\r\n" + "   oldsubmealstympnt  character varying;\r\n"
				+ "   mealstypeName  character varying;\r\n" + "   oldtimepointtype  character varying;\r\n"
				+ "   timepointtype  character varying;\r\n" + "   oldmealstypeName  character varying;\r\n"
				+ "   treatmentinfoidName  character varying;\r\n" + "   oldtreatmentinfoidName character varying;\r\n"
				+ "   newwindowperiodtype  character varying;\r\n" + "   oldwindowperiodtype  character varying;\r\n"
				+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n"
				+ " select user_name from bedc.epk_user_master where userid =new.created_by into createdby;\r\n"
				+ " select user_name from bedc.epk_user_master where userid =new.updated_by into updatedby;\r\n"
				+ "IF (old.subjectmealstimepointsid IS NULL) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (createdby,'CREATE',new.subjectmealstimepointsid,'subject_meals_time_points',new.studyid,null,new.volunteerid,'subject_meals_time_points_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+ "IF (new.scheduledate IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.scheduledate','',new.scheduledate,'Static',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','scheduledate');\r\n"
				+ "END IF;\r\n" + "IF (new.scheduletime IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.scheduletime','',new.scheduletime,'Static',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','scheduletime');\r\n"
				+ "END IF;\r\n" + "IF (new.sign IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.sign','',new.sign,'Static',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','sign');\r\n"
				+ "END IF;\r\n" + "IF (new.subjectno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectno','',new.subjectno,'Static',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','subjectno');\r\n"
				+ "END IF;\r\n" + "IF (new.subjectorder IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectorder','',new.subjectorder,'Static',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','subjectorder');\r\n"
				+ "END IF;\r\n" + "IF (new.timePoint IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timePoint','',new.timePoint,'Static',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','timePoint');\r\n"
				+ "END IF;\r\n" + "IF (new.timePointcollectionstatus IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timePointcollectionstatus','',new.timePointcollectionstatus,'Static',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','timePointcollectionstatus');\r\n"
				+ "END IF;\r\n" + "IF (new.timepointno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointno','',new.timepointno,'Static',new.studyid,null,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','timepointno');\r\n"
				+ "END IF;\r\n" + "IF (new.windowperiod IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiod','',new.windowperiod,'Static',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','windowperiod');\r\n"
				+ "END IF;\r\n" + "IF (new.windowperiodsign IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiodsign','',new.windowperiodsign,'Static',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','windowperiodsign');\r\n"
				+ "END IF;\r\n"
				+ "SELECT sign FROM bedc.study_meals_time_points where sampletimepointid = new.mealstimepointsid into newmealstimepointsid;\r\n"
				+ "IF (new.mealstimepointsid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('mealstimepointsid','',newmealstimepointsid,'Dynamic',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','mealstimepointsid');\r\n"
				+ "END IF;\r\n"
				+ "SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.mealstype into mealstypeName;\r\n"
				+ "IF (new.mealstype IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('mealstype','',mealstypeName,'Dynamic',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','mealstype');\r\n"
				+ "END IF;\r\n"
				+ "SELECT periodname FROM bedc.study_period_master where periodid = new.periodid into newperiod;\r\n"
				+ "IF (new.periodid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('periodid','',newperiod,'Dynamic',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','periodid');\r\n"
				+ "END IF;\r\n"
				+ "SELECT consumption FROM bedc.epk_subject_meals_time_points_data where subjectmealstimepointsdataid = new.subjectmealstimepointsdata into newsubmealstympnt;\r\n"
				+ "IF (new.subjectmealstimepointsdata IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('subjectmealstimepointsdata','',newsubmealstympnt,'Dynamic',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','subjectmealstimepointsdata');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = new.timepointtype into timepointtype;\r\n"
				+ "IF (new.timepointtype IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('timepointtype','',timepointtype,'Dynamic',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','timepointtype');\r\n"
				+ "END IF;\r\n"
				+ " SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentinfoid into treatmentinfoidName;\r\n"
				+ "IF (new.treatmentinfoid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('treatmentinfoid','',treatmentinfoidName,'Dynamic',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','treatmentinfoid');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.windowperiodtype into newwindowperiodtype;\r\n"
				+ "IF (new.windowperiodtype IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('windowperiodtype','',newwindowperiodtype,'Dynamic',new.studyid,new.volunteerid,null,' ',createdby,new.created_on,incCount,'subject_meals_time_points','audit_insert','windowperiodtype');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "\r\n"
				+ "ELSE IF (new.subjectmealstimepointsid =old.subjectmealstimepointsid) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.subjectmealstimepointsid,'study_meals_time_points',new.studyid,null,null,'study_meals_time_points_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+ "IF nullif(old.scheduledate,'') <> NULLIF(new.scheduledate,'') or new.scheduledate is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.scheduledate',old.scheduledate,new.scheduledate,'Static',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','scheduledate');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.scheduletime,'') <> NULLIF(new.scheduletime,'') or new.scheduletime is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.scheduletime',old.scheduletime,new.scheduletime,'Static',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','scheduletime');\r\n"
				+ "END IF;\r\n" + "IF nullif(old.sign,'') <> NULLIF(new.sign,'') or new.sign is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.sign',old.sign,new.sign,'Static',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','sign');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.subjectno,'') <> NULLIF(new.subjectno,'') or new.subjectno is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectno',old.subjectno,new.subjectno,'Static',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','subjectno');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.subjectorder,0) <> NULLIF(new.subjectorder,0) or new.subjectorder is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectorder',old.subjectorder,new.subjectorder,'Static',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','subjectorder');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.timePoint,'') <> NULLIF(new.timePoint,'') or new.timePoint is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timePoint','',new.timePoint,'Static',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','timePoint');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.timepointcollectionstatus,0) <> NULLIF(new.timepointcollectionstatus,0) or new.timepointcollectionstatus is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointcollectionstatus',old.timepointcollectionstatus,new.timepointcollectionstatus,'Static',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','timepointcollectionstatus');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.timepointno,0) <> NULLIF(new.timepointno,0) or new.timepointno is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointno',old.timepointno,new.timepointno,'Static',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','timepointno');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.windowperiod,0) <> NULLIF(new.windowperiod,0) or new.windowperiod is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiod','',new.windowperiod,'Static',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','windowperiod');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.windowperiodsign,'') <> NULLIF(new.windowperiodsign,'') or new.windowperiodsign is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiodsign','',new.windowperiodsign,'Static',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','windowperiodsign');\r\n"
				+ "END IF;\r\n"
				+ "SELECT sign FROM bedc.study_meals_time_points where sampletimepointid = new.mealstimepointsid into newmealstimepointsid;\r\n"
				+ "SELECT sign FROM bedc.study_meals_time_points where sampletimepointid = old.mealstimepointsid into oldmealstimepointsid;\r\n"
				+ "IF nullif(old.mealstimepointsid,null) <> NULLIF(new.mealstimepointsid,null) or new.mealstimepointsid is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('mealstimepointsid',oldmealstimepointsid,newmealstimepointsid,'Dynamic',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','mealstimepointsid');\r\n"
				+ "END IF;\r\n"
				+ "SELECT fieldname FROM bedc.form_static_data where formstaticdataid = old.mealstype into oldmealstypeName;\r\n"
				+ "SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.mealstype into mealstypeName;\r\n"
				+ "IF nullif(old.mealstype,null) <> NULLIF(new.mealstype,null) or new.mealstype is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('mealstype',oldmealstypeName,mealstypeName,'Dynamic',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','mealstype');\r\n"
				+ "END IF;\r\n"
				+ "SELECT periodname FROM bedc.study_period_master where periodid = new.periodid into newperiod;\r\n"
				+ "SELECT periodname FROM bedc.study_period_master where periodid = old.periodid into oldperiod;\r\n"
				+ "IF nullif(old.periodid,null) <> NULLIF(new.periodid,null) or new.periodid is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('periodid',oldperiod,newperiod,'Dynamic',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','periodid');\r\n"
				+ "END IF;\r\n"
				+ "SELECT consumption FROM bedc.epk_subject_meals_time_points_data where subjectmealstimepointsdataid = new.subjectmealstimepointsdata into newsubmealstympnt;\r\n"
				+ "SELECT consumption FROM bedc.epk_subject_meals_time_points_data where subjectmealstimepointsdataid = old.subjectmealstimepointsdata into oldsubmealstympnt;\r\n"
				+ "IF nullif(old.subjectmealstimepointsdata,null) <> NULLIF(new.subjectmealstimepointsdata,null) or new.subjectmealstimepointsdata is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('subjectmealstimepointsdata',oldsubmealstympnt,newsubmealstympnt,'Dynamic',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','subjectmealstimepointsdata');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = old.timepointtype into oldtimepointtype;\r\n"
				+ " SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = new.timepointtype into timepointtype;\r\n"
				+ "IF nullif(old.timepointtype,null) <> NULLIF(new.timepointtype,null) or new.timepointtype is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('timepointtype',oldtimepointtype,timepointtype,'Dynamic',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','timepointtype');\r\n"
				+ "END IF;\r\n"
				+ " SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentinfoid into oldtreatmentinfoidName;\r\n"
				+ " SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentinfoid into treatmentinfoidName;\r\n"
				+ "IF nullif(old.treatmentinfoid,null) <> NULLIF(new.treatmentinfoid,null) or new.treatmentinfoid is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('treatmentinfoid',oldtreatmentinfoidName,treatmentinfoidName,'Dynamic',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','treatmentinfoid');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.windowperiodtype into newwindowperiodtype;\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = old.windowperiodtype into oldwindowperiodtype;\r\n"
				+ "IF nullif(old.windowperiodtype,null) <> NULLIF(new.windowperiodtype,null) or new.windowperiodtype is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('windowperiodtype',oldwindowperiodtype,newwindowperiodtype,'Dynamic',new.studyid,new.volunteerid,null,' ',updatedby,new.updated_on,incCount,'subject_meals_time_points','audit_update','windowperiodtype');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER sub_meals_time_points_trgr"
				+ "   AFTER INSERT OR UPDATE  OF subjectmealstimepointsid, created_on,scheduledate,scheduletime,sign,subjectno,subjectorder,timepoint,timepointcollectionstatus,timepointno,"
				+ " update_reason,updated_on,windowperiod,windowperiodsign,created_by,mealstimepointsid,mealstype, periodid,studyid,subjectmealstimepointsdata,"
				+ " timepointtype, treatmentinfoid, updated_by,volunteerid,windowperiodtype ON bedc.subject_meals_time_points\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.sub_meals_time_points_fun();");
	}

	private void generateStudyVolunteerReports(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_volunteer_reporting_fun() RETURNS TRIGGER AS $$\r\n"
				+ "\r\n" + "\r\n" + "\r\n" + "\r\n" + " declare \r\n" + " incCount integer;\r\n" + " audid integer;\r\n"
				+ " \r\n" + " newgender character varying;\r\n" + " oldgender character varying;\r\n"
				+ " newperiod character varying;\r\n" + " oldperiod character varying;\r\n"
				+ " newstatusid character varying;\r\n" + " oldstatusid character varying;\r\n" + " begin \r\n"
				+ " select count(*) from bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " if (old.id is null) then \r\n"
				+ " insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,volunteer_id,subject_id,actvity_description,activity_done_on)\r\n"
				+ " values (new.created_by,'create',new.id,'study_volunteer_reporting',new.study_id,null,null,'study_volunteer_reporting_creation',new.created_on)RETURNING id INTO incCount;\r\n"
				+ " IF (new.subject_no IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.subject_no','',new.subject_no,new.study_id,null,null,' ',new.created_by,incCount,new.created_on,'study_volunteer_reporting','static','audit_insert','subject_no');\r\n"
				+ " END IF;\r\n"
				+ " select name from bedc.language_specific_value_details where id=new.gender_id into newgender;\r\n"
				+ "  IF (new.gender_id IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('gender_id','',newgender,new.study_id,null,null,' ',new.created_by,incCount,new.created_on,'study_volunteer_reporting','dynamic','audit_insert','gender_id');\r\n"
				+ " END IF;\r\n" + " \r\n"
				+ " select statusdesc  from bedc.status_master where statusid=new.statusid into newstatusid;\r\n"
				+ " IF (new.statusid IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('statusid','',newstatusid,new.study_id,null,null,' ',new.created_by,incCount,new.created_on,'study_volunteer_reporting','dynamic','audit_insert','statusid');\r\n"
				+ " END IF;\r\n" + "\r\n"
				+ " select periodname from bedc.study_period_master where periodid=new.period into newperiod;\r\n"
				+ " IF (new.period IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('period','',newperiod,new.study_id,null,null,' ',new.created_by,incCount,new.created_on,'study_volunteer_reporting','dynamic','audit_insert','period');\r\n"
				+ " END IF;\r\n" + "\r\n" + " return new;\r\n" + " \r\n" + " else if (new.id = old.id) then \r\n"
				+ " insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " values (new.updated_by,'update',new.id,'study_volunteer_reporting',new.study_id,null,null,'study_volunteer_reporting_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+ " IF nullif(old.subject_no,'') <> NULLIF(new.subject_no,'') or new.subject_no is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('subject_no',old.subject_no,new.subject_no,new.study_id,null,null,' ',new.updated_by,incCount,new.updated_on,'study_volunteer_reporting','static','audit_update','subject_no');\r\n"
				+ " END IF;\r\n" + " \r\n"
				+ " select name from bedc.language_specific_value_details where id=new.gender_id into newgender;\r\n"
				+ " select name from bedc.language_specific_value_details where id=old.gender_id into oldgender;\r\n"
				+ " IF nullif(old.gender_id,null) <> NULLIF(new.gender_id,null) or new.gender_id is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('gender_id',oldgender,newgender,new.study_id,null,null,' ',new.updated_by,incCount,new.updated_on,'study_volunteer_reporting','dynamic','audit_update','gender_id');\r\n"
				+ " END IF;\r\n" + "  \r\n"
				+ " select periodname from bedc.study_period_master where periodid=new.period into newperiod;\r\n"
				+ " select periodname from bedc.study_period_master where periodid=old.period into oldperiod;\r\n"
				+ " IF nullif(old.period,null) <> NULLIF(new.period,null) or new.period is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('period',oldperiod,newperiod,new.study_id,null,null,' ',new.updated_by,incCount,new.updated_on,'study_volunteer_reporting','dynamic','audit_update','period');\r\n"
				+ " END IF;\r\n" + " \r\n"
				+ " select statusdesc  from bedc.status_master where statusid=new.statusid into newstatusid;\r\n"
				+ " select statusdesc  from bedc.status_master where statusid=old.statusid into oldstatusid;\r\n"
				+ " IF nullif(old.statusid,null) <> NULLIF(new.statusid,null) or new.statusid is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('statusid',oldstatusid,newstatusid,new.study_id,null,null,' ',new.updated_by,incCount,new.updated_on,'study_volunteer_reporting','dynamic','audit_update','statusid');\r\n"
				+ " END IF;\r\n" + " \r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_volunteer_reporting_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, subject_no,gender_id,period,statusid ON bedc.study_volunteer_reporting\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_volunteer_reporting_fun();");
	}

	private void generateStudySubjectPeriod(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_sub_periods_fun() RETURNS TRIGGER AS $$\r\n" + "\r\n"
				+ "\r\n" + " declare \r\n" + " incCount integer;\r\n" + " audid integer;\r\n" + " \r\n"
				+ " newperiodid character varying;\r\n" + " oldperiodid character varying;\r\n"
				+ " newstatus character varying;\r\n" + " oldstatus character varying;\r\n" + "\r\n" + "  \r\n"
				+ " begin\r\n" + " \r\n" + " select count(*) from bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n" + " if (old.id is null) then \r\n"
				+ " insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " values (new.created_by,'create',new.id,'study_subjects_periods',null,null,null,'study_sub_periods_creation',new.created_on)RETURNING id INTO incCount;\r\n"
				+ " \r\n"
				+ " select periodname from bedc.study_period_master where periodid=new.period_id into newperiodid;\r\n"
				+ " IF (new.period_id IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('period_id','',newperiodid,null,null,new.subject_id,' ',new.created_by,incCount,new.created_on,'study_subjects_periods','dynamic','audit_insert','period_id');\r\n"
				+ " END IF;\r\n" + " \r\n"
				+ " select statusdesc from bedc.status_master where statusid = new.status into newstatus;\r\n"
				+ " IF (new.status IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.status','',newstatus,null,null,new.subject_id,' ',new.created_by,incCount,new.created_on,'study_subjects_periods','dynamic','audit_insert','status');\r\n"
				+ " END IF;\r\n" + " return new;\r\n" + " else if (new.id = old.id) then \r\n"
				+ " insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " values (new.updated_by,'update',new.id,'study_subjects_periods',null,null,new.subject_id,'study_subjects_periods_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+ " select periodname from bedc.study_period_master where periodid=new.period_id into newperiodid;\r\n"
				+ " select periodname from bedc.study_period_master where periodid=old.period_id into oldperiodid;\r\n"
				+ " IF nullif(old.period_id,null) <> NULLIF(new.period_id,null) or new.period_id is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('period_id',oldperiodid,newperiodid,null,new.subject_id,new.update_reason,new.updated_by,incCount,new.updated_on,'study_subjects_periods','dynamic','audit_update','periodid');\r\n"
				+ " END IF;\r\n" + " \r\n"
				+ " select statusdesc from bedc.status_master where statusid = new.status into newstatus;\r\n"
				+ " select statusdesc from bedc.status_master where statusid = old.status into oldstatus;\r\n"
				+ " IF nullif(old.status,null) <> NULLIF(new.status,null) or new.status is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('status',oldstatus,newstatus,null,null,new.subject_id,new.update_reason,new.updated_by,incCount,new.updated_on,'study_subjects_periods','dynamic','audit_update','status');\r\n"
				+ " END IF;\r\n" + " return new;\r\n" + " end if;\r\n" + " end if;\r\n" + " end;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_sub_periods_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, period_id,status ON bedc.study_subjects_periods\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_sub_periods_fun();");

	}

	private void generateDrugReceptionReviewAudit(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.drug_reception_review_audit_fun() RETURNS TRIGGER AS $$\r\n" +

				"\r\n" + " declare \r\n" + " incCount integer;\r\n" + " audid integer;\r\n" + " \r\n"
				+ " oldrole character varying;\r\n" + " newrole character varying;\r\n" + " \r\n"
				+ " user character varying;\r\n" + " newuser character varying;\r\n" + " \r\n" + " begin \r\n"
				+ " select count(*) from bedc.activity_log into incCount;\r\n" + "\r\n"
				+ " if (old.id is null) then \r\n"
				+ " insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " values (null,'create',new.id,'drug_recptn_review_audit',null,null,null,'drug_recptn_review_audit_creation',null)RETURNING id INTO incCount;\r\n"
				+ " IF (new.date IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.date','',new.date,null,null,null,new.comment,null,incCount,null,'drug_recptn_review_audit','static','audit_insert','date');\r\n"
				+ " END IF;\r\n" + " IF (new.drugid IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.drugid','',new.drugid,null,null,null,new.comment,null,incCount,null,'drug_recptn_review_audit','static','audit_insert','drugid');\r\n"
				+ " END IF;\r\n" + " IF (new.in_the_flow IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.in_the_flow','',new.in_the_flow,null,null,null,new.comment,null,incCount,null,'drug_recptn_review_audit','static','audit_insert','in_the_flow');\r\n"
				+ " END IF;\r\n" + " IF (new.reviewstate IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.reviewstate','',new.reviewstate,null,null,null,new.comment,null,incCount,null,'drug_recptn_review_audit','static','audit_insert','reviewstate');\r\n"
				+ " END IF;\r\n" + " IF (new.status IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.status','',new.status,null,null,null,new.comment,null,incCount,null,'drug_recptn_review_audit','static','audit_insert','status');\r\n"
				+ " END IF;\r\n" + " select role from bedc.epk_role_master where roleid=new.role_id into newrole;\r\n"
				+ " IF (new.role_id IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('role_id','',newrole,null,null,null,new.comment,null,incCount,null,'drug_recptn_review_audit','dynamic','audit_insert','role_id');\r\n"
				+ " END IF;\r\n"
				+ "  select full_name from bedc.epk_user_master where userid=new.user_id into newuser;\r\n"
				+ " IF (new.user_id IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('user_id','',newuser,null,null,null,new.comment,null,incCount,null,'drug_recptn_review_audit','dynamic','audit_insert','user_id');\r\n"
				+ " END IF;\r\n" + " return new;\r\n" + " else if (new.id = old.id) then \r\n"
				+ " insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " values (null,'update',new.id,'drug_recptn_review_audit',null,null,null,'drug_recptn_review_audit_updation',null)RETURNING id INTO incCount;\r\n"
				+ "IF nullif(old.date,null) <> NULLIF(new.date,null) or new.date is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.date',old.date,new.date,null,null,null,new.comment,null,incCount,null,'drug_recptn_review_audit','static','audit_update','date');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.drugid,null) <> NULLIF(new.drugid,null) or new.drugid is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.drugid',old.drugid,new.drugid,null,null,null,new.comment,null,incCount,null,'drug_recptn_review_audit','static','audit_update','drugid');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.in_the_flow,null) <> NULLIF(new.in_the_flow,null) or new.in_the_flow is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.in_the_flow',old.in_the_flow,new.in_the_flow,null,null,null,new.comment,null,incCount,null,'drug_recptn_review_audit','static','audit_update','in_the_flow');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.reviewstate,null) <> NULLIF(new.reviewstate,null) or new.reviewstate is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.reviewstate',old.reviewstate,new.reviewstate,null,null,null,new.comment,null,incCount,null,'drug_recptn_review_audit','static','audit_update','reviewstate');\r\n"
				+ " END IF;\r\n" + " IF nullif(old.status,'') <> NULLIF(new.status,'') or new.status is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.status',old.status,new.status,null,null,null,new.comment,null,incCount,null,'drug_recptn_review_audit','static','audit_update','status');\r\n"
				+ " END IF;\r\n" + " select role from bedc.epk_role_master where roleid=old.role_id into oldrole;\r\n"
				+ " select role from bedc.epk_role_master where roleid=new.role_id into newrole;\r\n" + "\r\n"
				+ " IF nullif(old.role_id,null) <> NULLIF(new.role_id,null) or new.role_id is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.role_id',oldrole,newrole,null,null,null,new.comment,null,incCount,null,'drug_recptn_review_audit','static','audit_update','role_id');\r\n"
				+ " END IF;\r\n"
				+ "  select full_name from bedc.epk_user_master where userid=old.user_id into user;\r\n"
				+ "  select full_name from bedc.epk_user_master where userid=new.user_id into newuser;\r\n"
				+ " IF nullif(old.user_id,null) <> NULLIF(new.user_id,null) or new.user_id is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.user_id',user,newuser,null,null,null,new.comment,null,incCount,null,'drug_recptn_review_audit','static','audit_update','user_id');\r\n"
				+ " END IF;\r\n" + " return new;\r\n" + " end if;\r\n" + " end if;\r\n" + " end;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER drug_reception_review_audit_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, comment,date,drugid,in_the_flow,reviewstate,status,role_id,user_id  ON bedc.drug_reception_review_audit\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.drug_reception_review_audit_fun();");

	}

	private void generateDrugDispensingContainerBarcodes(Connection con, Statement stmt) throws Exception {
		stmt.execute(
				"CREATE OR REPLACE FUNCTION bedc.drug_dispensing_container_barcodes_fun() RETURNS TRIGGER AS $$\r\n"
						+ "\r\n" + " declare \r\n" + " incCount integer;\r\n"
						+ " studydrugdispensingid  character varying;\r\n"
						+ " newstudydrugdispensingid character varying;\r\n" + " begin \r\n"
						+ " select count(*) from bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
						+ " if (old.id is null) then \r\n"
						+ " insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ " values (null,'create',new.id,'drug_dispensing_container_barcodes',null,null,null,'drug_dispensing_container_barcodes_creation',null)RETURNING id INTO incCount;\r\n"
						+ " select genericname from bedc.study_drug_dispensing where new.id= new.studydrugdispensingid into newstudydrugdispensingid;\r\n"
						+ " IF (new.studydrugdispensingid IS NOT NULL) THEN\r\n"
						+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
						+ " values ('studydrugdispensingid','',newstudydrugdispensingid,null,null,null,' ',null,incCount,new.null,'drug_dispensing_container_barcodes','dynamic','audit_insert','studydrugdispensingid');\r\n"
						+ " END IF;\r\n" + " return new;\r\n" + " else if (new.id = old.id) then \r\n"
						+ " insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ " values (null,'update',new.id,'drug_dispensing_container_barcodes',null,null,null,'drug_dispensing_container_barcodes_updation',null)RETURNING id INTO incCount;\r\n"
						+ " select genericname from bedc.study_drug_dispensing where new.id= new.studydrugdispensingid into newstudydrugdispensingid;\r\n"
						+ " select genericname from bedc.study_drug_dispensing where new.id= old.studydrugdispensingid into studydrugdispensingid;\r\n"
						+ " IF nullif(old.studydrugdispensingid,null) <> NULLIF(new.studydrugdispensingid,null) or new.studydrugdispensingid is null THEN\r\n"
						+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
						+ " values ('studydrugdispensingid',studydrugdispensingid,newstudydrugdispensingid,null,null,null,' ',null,incCount,null,'drug_dispensing_container_barcodes','dynamic','audit_update','studydrugdispensingid');\r\n"
						+ " END IF;\r\n" + "\r\n" + " return new;\r\n" + " end if;\r\n" + " end if;\r\n" + " end;\r\n"
						+ "$$ LANGUAGE 'plpgsql';");
		stmt.execute("CREATE TRIGGER drug_dispensing_container_barcodes_trgr"
				+ "AFTER INSERT OR UPDATE  OF id,studydrugdispensingid ON bedc.drug_dispensing_container_barcodes\r\n"
				+ " FOR EACH ROW\r\n" + " EXECUTE FUNCTION bedc.drug_dispensing_container_barcodes_fun();");

	}

	private void generateDrugReceptionTable(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.drug_reception_table_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   newproject character varying;\r\n" + "   project character varying;\r\n"
				+ "   newapplicableregulationid character varying;\r\n"
				+ "   applicableregulationid character varying;\r\n" + "   newstrength character varying;\r\n"
				+ "   strength character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "  IF (old.id IS NULL) THEN\r\n"
				+ "	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " 	VALUES (new.created_by,'CREATE',new.id,'null',null,null,null,'null',new.created_on) RETURNING id INTO incCount; \r\n"
				+ " end if;\r\n" + "IF (new.areaclean IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.areaclean','',new.areaclean,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','areaclean');\r\n"
				+ " END IF;\r\n" + "IF (new.batchlotnumber IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.batchlotnumber','',new.batchlotnumber,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','batchlotnumber');\r\n"
				+ " END IF;\r\n" + " IF (new.brandidentification IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.brandidentification','',new.brandidentification,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','brandidentification');\r\n"
				+ " END IF;\r\n" + " IF (new.containerslecondition IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.containerslecondition','',new.containerslecondition,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','containerslecondition');\r\n"
				+ " END IF;\r\n" + " IF (new.datalogger IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.datalogger','',new.datalogger,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','datalogger');\r\n"
				+ " END IF;\r\n" + " IF (new.dataloggercode IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.dataloggercode','',new.dataloggercode,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','dataloggercode');\r\n"
				+ " END IF;\r\n" + " IF (new.dinstinctivetradename IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.dinstinctivetradename','',new.dinstinctivetradename,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','dinstinctivetradename');\r\n"
				+ " END IF;\r\n" + " IF (new.drungproducttype IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.drungproducttype','',new.drungproducttype,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','drungproducttype');\r\n"
				+ " END IF;\r\n" + " IF (new.expirationratestdate IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.expirationratestdate','',new.expirationratestdate,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','expirationratestdate');\r\n"
				+ " END IF;\r\n" + " IF (new.genericname IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.genericname','',new.genericname,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','genericname');\r\n"
				+ " END IF;\r\n" + " IF (new.manufacturingdate IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.manufacturingdate','',new.manufacturingdate,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','manufacturingdate');\r\n"
				+ " END IF;\r\n" + " IF (new.noofunits IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofunits','',new.noofunits,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','noofunits');\r\n"
				+ " END IF;\r\n" + "  IF (new.noofboxslabel IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofboxslabel','',new.noofboxslabel,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','noofboxslabel');\r\n"
				+ " END IF;\r\n" + "  IF (new.noofcontainersnotopened IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofcontainersnotopened','',new.noofcontainersnotopened,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','noofcontainersnotopened');\r\n"
				+ " END IF;\r\n" + "  IF (new.noofcontainersnotopened IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofcontainersnotopened','',new.noofcontainersnotopened,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','noofcontainersnotopened');\r\n"
				+ " END IF;\r\n" + "   IF (new.noofcontainersopened IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofcontainers','',new.noofcontainersopened,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','noofcontainersopened');\r\n"
				+ " END IF;\r\n" + "   IF (new.noofcontainersreceived IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofcontainersreceived','',new.noofcontainersreceived,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','noofcontainersreceived');\r\n"
				+ " END IF;\r\n" + "   IF (new.noofsealclosedcontainers IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofsealclosedcontainers','',new.noofsealclosedcontainers,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','noofsealclosedcontainers');\r\n"
				+ " END IF;\r\n" + "   IF (new.noofsealopenedcontainers IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofsealclosedcontainers','',new.noofsealclosedcontainers,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','noofsealclosedcontainers');\r\n"
				+ " END IF;\r\n" + "   IF (new.noofunitsasperprotocol IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofunitsasperprotocol','',new.noofunitsasperprotocol,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','noofunitsasperprotocol');\r\n"
				+ " END IF;\r\n" + "   IF (new.parcelcourierreceipt IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.parcelcourierreceipt','',new.parcelcourierreceipt,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','parcelcourierreceipt');\r\n"
				+ " END IF;\r\n" + "  IF (new.pharmaceuticalform IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.pharmaceuticalform','',new.pharmaceuticalform,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','pharmaceuticalform');\r\n"
				+ " END IF;\r\n" + "  IF (new.primarycontainercondition IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.primarycontainercondition','',new.primarycontainercondition,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','primarycontainercondition');\r\n"
				+ " END IF;\r\n" + " IF (new.productcolor IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.productcolor','',new.productcolor,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','productcolor');\r\n"
				+ " END IF;\r\n" + " IF (new.productcontainercomments IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.productcontainercomments','',new.productcontainercomments,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','productcontainercomments');\r\n"
				+ " END IF;\r\n" + " IF (new.productdescriptioncomments IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.productdescriptioncomments','',new.productdescriptioncomments,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','productdescriptioncomments');\r\n"
				+ " END IF;\r\n" + " IF (new.productother IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.productother','',new.productother,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','productother');\r\n"
				+ " END IF;\r\n" + "  IF (new.productpharmaceuticalform IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.productpharmaceuticalform','',new.productpharmaceuticalform,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','productpharmaceuticalform');\r\n"
				+ " END IF;\r\n" + "  IF (new.projectavailable IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.projectavailable','',new.projectavailable,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','projectavailable');\r\n"
				+ " END IF;\r\n" + "   IF (new.quantityreceivedinunits IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.quantityreceivedinunits','',new.quantityreceivedinunits,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','quantityreceivedinunits');\r\n"
				+ " END IF;\r\n" + "   IF (new.qurantinerequired IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.projectavailable','',new.projectavailable,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','projectavailable');\r\n"
				+ " END IF;\r\n" + "  IF (new.randamizationcode IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.randamizationcode','',new.randamizationcode,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','randamizationcode');\r\n"
				+ " END IF;\r\n" + "  IF (new.requireddocumentsavailable IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.requireddocumentsavailable','',new.requireddocumentsavailable,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','requireddocumentsavailable');\r\n"
				+ " END IF;\r\n" + "  IF (new.reviewer_by IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.reviewer_by','',new.reviewer_by,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','reviewer_by');\r\n"
				+ " END IF;\r\n" + "  IF (new.revieweron IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.revieweron','',new.revieweron,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','revieweron');\r\n"
				+ " END IF;\r\n" + "  IF (new.reviewerstatus IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.reviewerstatus','',new.reviewerstatus,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','reviewerstatus');\r\n"
				+ " END IF;\r\n" + " IF (new.secondarycontainercondition IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.secondarycontainercondition','',new.secondarycontainercondition,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','secondarycontainercondition');\r\n"
				+ " END IF;\r\n" + "  IF (new.shape IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.shape','',new.shape,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','shape');\r\n"
				+ " END IF;\r\n" + "  IF (new.shippingcoditions IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.shippingcoditions','',new.shippingcoditions,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','shippingcoditions');\r\n"
				+ " END IF;\r\n" + "  IF (new.sponsorstudycode IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.sponsorstudycode','',new.sponsorstudycode,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','secondarycontainercondition');\r\n"
				+ " END IF;\r\n" + " IF (new.status IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.status','',new.status,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','status');\r\n"
				+ " END IF;\r\n" + "  IF (new.storageconditions IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.storageconditions','',new.storageconditions,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','storageconditions');\r\n"
				+ " END IF;\r\n" + "  IF (new.storagedegreescelsius IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.storagedegreescelsius','',new.storagedegreescelsius,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','storagedegreescelsius');\r\n"
				+ " END IF;\r\n" + " IF (new.storagedegreesfahranneit IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.storagedegreesfahranneit','',new.storagedegreesfahranneit,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','storagedegreesfahranneit');\r\n"
				+ " END IF;\r\n" + " IF (new.strength IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.strength','',new.strengthstrength,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','strength');\r\n"
				+ " END IF;\r\n" + "  IF (new.texturefinish IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.texturefinish','',new.texturefinish,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','texturefinish');\r\n"
				+ " END IF;\r\n" + "  IF (new.thelabelofthedrugproductcontains IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.thelabelofthedrugproductcontains','',new.thelabelofthedrugproductcontains,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','thelabelofthedrugproductcontains');\r\n"
				+ " END IF;\r\n" + " IF (new.unitsused IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.unitsused','',new.unitsused,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','unitsused');\r\n"
				+ " END IF;\r\n" + " IF (new.waybillnumber IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.waybillnumber','',new.waybillnumber,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','waybillnumber');\r\n"
				+ " END IF;\r\n" + " IF (new.workingareaclean IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.workingareaclean','',new.workingareaclean,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','workingareaclean');\r\n"
				+ " END IF;\r\n"
				+ " select name from bedc.regulatories where id= new.applicableregulationid into  newapplicableregulationid;\r\n"
				+ " IF (new.applicableregulationid IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.applicableregulationid','',newapplicableregulationid,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','applicableregulationid');\r\n"
				+ " END IF;\r\n"
				+ " select projectno from bedc.projects where projectid= new.projectid into newproject;\r\n"
				+ " IF (new.projectid IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.projectid','',newproject,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','projectid');\r\n"
				+ " END IF;\r\n"
				+ " select unit_code  from bedc.units_master where id=new.strengthunit  into newstrength;\r\n"
				+ " IF (new.strengthunit IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.strengthunit','',newstrength,null,null,null,' ',new.created_by,incCount,new.cratedon,'drug_reception_table','static','audit_insert','strengthunit');\r\n"
				+ " END IF;\r\n" + "RETURN new;\r\n" + "else if(new.id =old.id )then \r\n"
				+ "insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "values (new.updated_by,'update',new.id,'drug_reception_table',null,null,null,'drug_reception_table_updation',new.updateon)RETURNING id INTO incCount;\r\n"
				+ " IF nullif(old.areaclean,'') <> NULLIF(new.areaclean,'') or new.areaclean is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.areaclean',old.areaclean,new.areaclean,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','areaclean');\r\n"
				+ " END IF;\r\n" +

				" IF nullif(old.batchlotnumber,'') <> NULLIF(new.batchlotnumber,'') or new.batchlotnumber is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.batchlotnumber',old.batchlotnumber,new.batchlotnumber,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','batchlotnumber');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.brandidentification,'') <> NULLIF(new.brandidentification,'') or new.brandidentification is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.brandidentification',old.brandidentification,new.brandidentification,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','brandidentification');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.containerslecondition,'') <> NULLIF(new.containerslecondition,'') or new.containerslecondition is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.containerslecondition',old.containerslecondition,new.containerslecondition,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','containerslecondition');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.datalogger,'') <> NULLIF(new.datalogger,'') or new.datalogger is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.datalogger',old.datalogger,new.datalogger,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','datalogger');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.dinstinctivetradename,'') <> NULLIF(new.dinstinctivetradename,'') or new.dinstinctivetradename is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.dinstinctivetradename',old.dinstinctivetradename,new.dinstinctivetradename,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','dinstinctivetradename');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.drungproducttype,'') <> NULLIF(new.drungproducttype,'') or new.drungproducttype is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.drungproducttype',old.drungproducttype,new.drungproducttype,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','drungproducttype');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.expirationratestdate,null) <> NULLIF(new.expirationratestdate,null) or new.expirationratestdate is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.expirationratestdate',old.expirationratestdate,new.expirationratestdate,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','expirationratestdate');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.genericname,'') <> NULLIF(new.genericname,'') or new.genericname is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.genericname',old.genericname,new.genericname,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','genericname');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.manufacturingdate,null) <> NULLIF(new.manufacturingdate,null) or new.manufacturingdate is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.manufacturingdate',old.manufacturingdate,new.manufacturingdate,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','manufacturingdate');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.noofunits,'') <> NULLIF(new.noofunits,'') or new.noofunits is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofunits',old.noofunits,new.noofunits,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','noofunits');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.noofboxslabel,0) <> NULLIF(new.noofboxslabel,0) or new.noofboxslabel is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofboxslabel',old.noofboxslabel,new.noofboxslabel,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','noofboxslabel');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.noofcontainersnotopened,0) <> NULLIF(new.noofcontainersnotopened,0) or new.noofcontainersnotopened is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofcontainersnotopened',old.noofcontainersnotopened,new.noofcontainersnotopened,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','noofcontainersnotopened');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.noofcontainersopened,0) <> NULLIF(new.noofcontainersopened,0) or new.noofcontainersopened is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofcontainersopened',old.noofcontainersopened,new.noofcontainersopened,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','noofcontainersopened');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.noofcontainersreceived,0) <> NULLIF(new.noofcontainersreceived,0) or new.noofcontainersreceived is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofcontainersreceived',old.noofcontainersreceived,new.noofcontainersreceived,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','noofcontainersreceived');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.noofsealclosedcontainers,0) <> NULLIF(new.noofsealclosedcontainers,0) or new.noofsealclosedcontainers is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofsealclosedcontainers',old.noofsealclosedcontainers,new.noofsealclosedcontainers,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','noofsealclosedcontainers');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.noofsealopenedcontainers,0) <> NULLIF(new.noofsealopenedcontainers,0) or new.noofsealopenedcontainers is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofsealopenedcontainers',old.noofsealopenedcontainers,new.noofsealopenedcontainers,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','noofsealopenedcontainers');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.noofunitsasperprotocol,0) <> NULLIF(new.noofunitsasperprotocol,0) or new.noofunitsasperprotocol is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.noofunitsasperprotocol',old.noofunitsasperprotocol,new.noofunitsasperprotocol,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','noofunitsasperprotocol');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.parcelcourierreceipt,'') <> NULLIF(new.parcelcourierreceipt,'') or new.parcelcourierreceipt is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.parcelcourierreceipt',old.parcelcourierreceipt,new.parcelcourierreceipt,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','parcelcourierreceipt');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.pharmaceuticalform,'') <> NULLIF(new.pharmaceuticalform,'') or new.pharmaceuticalform is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.pharmaceuticalform',old.pharmaceuticalform,new.pharmaceuticalform,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','pharmaceuticalform');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.primarycontainercondition,'') <> NULLIF(new.primarycontainercondition,'') or new.primarycontainercondition is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.primarycontainercondition',old.primarycontainercondition,new.primarycontainercondition,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','primarycontainercondition');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.productcolor,'') <> NULLIF(new.productcolor,'') or new.productcolor is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.productcolor',old.productcolor,new.productcolor,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','productcolor');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.productcontainercomments,'') <> NULLIF(new.productcontainercomments,'') or new.productcontainercomments is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.productcontainercomments',old.productcontainercomments,new.productcontainercomments,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','productcontainercomments');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.productdescriptioncomments,'') <> NULLIF(new.productdescriptioncomments,'') or new.productdescriptioncomments is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.productdescriptioncomments',old.productdescriptioncomments,new.productdescriptioncomments,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','productdescriptioncomments');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.productother,'') <> NULLIF(new.productother,'') or new.productother is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.productother',old.productother,new.productother,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','productother');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.productpharmaceuticalform,'') <> NULLIF(new.productpharmaceuticalform,'') or new.productpharmaceuticalform is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.productpharmaceuticalform',old.productpharmaceuticalform,new.productpharmaceuticalform,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','productpharmaceuticalform');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.projectavailable,'') <> NULLIF(new.projectavailable,'') or new.projectavailable is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.projectavailable',old.projectavailable,new.projectavailable,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','projectavailable');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.quantityreceivedinunits,0) <> NULLIF(new.quantityreceivedinunits,0) or new.quantityreceivedinunits is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.quantityreceivedinunits',old.quantityreceivedinunits,new.quantityreceivedinunits,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','quantityreceivedinunits');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.qurantinerequired,'') <> NULLIF(new.qurantinerequired,'') or new.qurantinerequired is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.qurantinerequired',old.qurantinerequired,new.qurantinerequired,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','qurantinerequired');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.randamizationcode,'') <> NULLIF(new.randamizationcode,'') or new.randamizationcode is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.randamizationcode',old.randamizationcode,new.randamizationcode,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','randamizationcode');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.requireddocumentsavailable,'') <> NULLIF(new.requireddocumentsavailable,'') or new.requireddocumentsavailable is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.requireddocumentsavailable',old.requireddocumentsavailable,new.requireddocumentsavailable,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','requireddocumentsavailable');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.reviewer_by,'') <> NULLIF(new.reviewer_by,'') or new.reviewer_by is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.reviewer_by',old.reviewer_by,new.reviewer_by,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','reviewer_by');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.revieweron,null) <> NULLIF(new.revieweron,null) or new.revieweron is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.revieweron',old.revieweron,new.revieweron,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','revieweron');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.reviewerstatus,'') <> NULLIF(new.reviewerstatus,'') or new.reviewerstatus is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.reviewerstatus',old.reviewerstatus,new.reviewerstatus,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','reviewerstatus');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.secondarycontainercondition,'') <> NULLIF(new.secondarycontainercondition,'') or new.secondarycontainercondition is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.secondarycontainercondition',old.secondarycontainercondition,new.secondarycontainercondition,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','secondarycontainercondition');\r\n"
				+ " END IF;\r\n" + " IF nullif(old.shape,'') <> NULLIF(new.shape,'') or new.shape is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.shape',old.shape,new.shape,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','shape');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.shippingcoditions,'') <> NULLIF(new.shippingcoditions,'') or new.shippingcoditions is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.shippingcoditions',old.shippingcoditions,new.shippingcoditions,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','shippingcoditions');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.sponsorstudycode,'') <> NULLIF(new.sponsorstudycode,'') or new.sponsorstudycode is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.sponsorstudycode',old.sponsorstudycode,new.sponsorstudycode,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','sponsorstudycode');\r\n"
				+ " END IF;\r\n" + " IF nullif(old.status,'') <> NULLIF(new.status,'') or new.status is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.status',old.status,new.status,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','status');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.storageconditions,'') <> NULLIF(new.storageconditions,'') or new.storageconditions is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.storageconditions',old.storageconditions,new.storageconditions,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','storageconditions');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.storagedegreescelsius,'') <> NULLIF(new.storagedegreescelsius,'') or new.storagedegreescelsius is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.storagedegreescelsius',old.storagedegreescelsius,new.storagedegreescelsius,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','storagedegreescelsius');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.storagedegreesfahranneit,'') <> NULLIF(new.storagedegreesfahranneit,'') or new.storagedegreesfahranneit is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.storagedegreesfahranneit',old.storagedegreesfahranneit,new.storagedegreesfahranneit,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','storagedegreesfahranneit');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.strength,'') <> NULLIF(new.strength,'') or new.strength is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.strength',old.strength,new.strength,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','strength');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.texturefinish,'') <> NULLIF(new.texturefinish,'') or new.texturefinish is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.texturefinish',old.texturefinish,new.texturefinish,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','texturefinish');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.thelabelofthedrugproductcontains,'') <> NULLIF(new.thelabelofthedrugproductcontains,'') or new.thelabelofthedrugproductcontains is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.thelabelofthedrugproductcontains',old.thelabelofthedrugproductcontains,new.thelabelofthedrugproductcontains,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','thelabelofthedrugproductcontains');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.unitsused,'') <> NULLIF(new.unitsused,'') or new.unitsused is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.unitsused',old.unitsused,new.unitsused,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','unitsused');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.waybillnumber,'') <> NULLIF(new.waybillnumber,'') or new.waybillnumber is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.waybillnumber',old.waybillnumber,new.waybillnumber,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','waybillnumber');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.workingareaclean,'') <> NULLIF(new.workingareaclean,'') or new.workingareaclean is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.workingareaclean',old.workingareaclean,new.workingareaclean,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','workingareaclean');\r\n"
				+ " END IF;\r\n"
				+ " select name from bedc.regulatories where id= old.applicableregulationid into  applicableregulationid;\r\n"
				+ " select name from bedc.regulatories where id= new.applicableregulationid into  newapplicableregulationid;\r\n"
				+ " IF nullif(old.applicableregulationid,null) <> NULLIF(new.applicableregulationid,null) or new.applicableregulationid is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.applicableregulationid',applicableregulationid,newapplicableregulationid,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','applicableregulationid');\r\n"
				+ " END IF;\r\n"
				+ " select projectno from bedc.projects where projectid= old.projectid into project;\r\n"
				+ " select projectno from bedc.projects where projectid= new.projectid into newproject;\r\n"
				+ " IF nullif(old.projectid,null) <> NULLIF(new.projectid,null) or new.projectid is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.projectid',project,newproject,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','projectid');\r\n"
				+ " END IF;\r\n"
				+ " select unit_code from bedc.units_master where id=old.strengthunit  into strength;\r\n"
				+ " select unit_code from bedc.units_master where id=new.strengthunit  into newstrength;\r\n"
				+ " IF nullif(old.strengthunit,null) <> NULLIF(new.strengthunit,null) or new.strengthunit is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.strengthunit',strength,newstrength,null,null,null,' ',new.updated_by,incCount,new.updateon,'drug_reception_table','static','audit_update','strengthunit');\r\n"
				+ " END IF;\r\n" + "END IF;\r\n" + " END;" + "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER drug_reception_table_trgr "
				+ "   AFTER INSERT OR UPDATE  OF id,areaclean,batchlotnumber,brandidentification,containerslecondition,cratedon,created_by,datalogger,\"\r\n"
				+ "        + \"dinstinctivetradename,drungproducttype,expeirationratestdate,genericname,manufacturingdate,noofunits,noofboxeslabel,nofcontainersnotopened,\"\r\n"
				+ "        + \"noofcontainersopened,noofcontainersreceived,noofsealclosedcontainers,noofsealopenedcontainers,noofunitsasperprotocol,parcelcourierreceipt,\"\r\n"
				+ "        + \"pharmaceuticalform,primarycontainerccondition,productcolor,productcontainercomments,productdescriptioncomments,\"\r\n"
				+ "        + \"productother,productpharmaceuticalform,projectavailable,qunatityreceivedinunits,qurantinerequired,randamizationcode,requireddocumentsavailable,\"\r\n"
				+ "        + \"reviewer_by,revieweron,reviewerstatus,secondarycontainercondition,shape,shippingconditions,sponsorstudycode,status,storagecondition,\"\r\n"
				+ "        + \"storagedegreescelsius,storagedegreesfahranneit,strength,texturefinish,thelabelofthedrugproductcontains,unitsused,updateon,updated_by,waybillnumber,\"\r\n"
				+ "        + \"workingareaclean,applicableregulationid,projectid,strengthunit ON bedc.drug_reception_table\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.drug_reception_table_fun();");

	}

	private void generateGlobalGroupTrigger(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.global_groups_fun() RETURNS TRIGGER AS $$\r\n" + "declare \r\n"
				+ "   incCount integer;\r\n" + "   actid integer;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "	IF (old.id IS NULL) THEN\r\n"
				+ "	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "		VALUES (new.created_by,'CREATE',new.id,'global_groups',null,null,null,'global_groups_creation',new.created_on) RETURNING id INTO incCount;\r\n"
				+ "	IF(new.name IS NOT NULL) THEN\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.name','',new.name,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_groups','audit_insert','name');\r\n"
				+ "	END IF;\r\n" + "	 IF (new.order_no IS NOT NULL) THEN\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.order','',new.order_no,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_groups','audit_insert','order_no');\r\n"
				+ "	 END IF;\r\n" + "	 IF (new.type IS NOT NULL) THEN\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.ggType','',new.type,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_groups','audit_insert','type');\r\n"
				+ "	 END IF;\r\n" + "	 IF (new.noofrows IS NOT NULL) THEN\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.ggRows','',new.noofrows,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_groups','audit_insert','type');\r\n"
				+ "	 END IF;\r\n" + "	 IF (new.noofcolums IS NOT NULL) THEN\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.ggColums','',new.noofcolums,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_groups','audit_insert','type');\r\n"
				+ "	 END IF;\r\n" + "	RETURN new;\r\n" + "	ELSE IF (new.id=old.id) THEN\r\n"
				+ "	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.updated_by,'UPDATE',new.id,'global_groups',null,null,null,'global_groups_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+ "	IF NULLIF(old.name,'') <> NULLIF(new.name,'') or new.name is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.name',old.name,new.name,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_groups','label.audit_update','name');\r\n"
				+ "	END IF;\r\n"
				+ " 	IF NULLIF(old.order_no,NULL) <> NULLIF(new.order_no,NULL) or new.order_no is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.order',old.order_no,new.order_no,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_groups','label.audit_update','order_no');\r\n"
				+ "	 END IF;\r\n" + "	 IF NULLIF(new.type,'') <> NULLIF(old.type,'') or new.type is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.ggType',old.type,new.type,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_groups','label.audit_update','type');\r\n"
				+ "	 END IF;\r\n"
				+ "	 IF NULLIF(new.noofrows,'') <> NULLIF(old.noofrows,'') or new.noofrows is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.ggRows',old.noofrows,new.noofrows,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_groups','label.audit_update','type');\r\n"
				+ "	 END IF;\r\n"
				+ "	 IF NULLIF(new.noofcolums,'') <> NULLIF(old.noofcolums,'') or new.noofcolums is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.ggColums',old.noofcolums,new.noofcolums,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_groups','label.audit_update','type');\r\n"
				+ "	 END IF;\r\n" + "	RETURN new;\r\n" + "	END IF;\r\n" + "	END IF;\r\n" + "	END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER global_groups_trgr "
				+ "   AFTER INSERT OR UPDATE  OF id, created_by, created_on, name, order_no, type, update_reason, updated_by, updated_on ON bedc.global_groups\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.global_groups_fun();");

	}

	private void generateGlobalActivityTrigger(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.global_activity_fun() RETURNS TRIGGER AS $$\r\n" + "declare \r\n"
				+ "   incCount integer;\r\n" + "   actid integer;\r\n" + "   newGroupId character varying;\r\n"
				+ "   oldGroupId character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.id IS NULL) THEN\r\n"
				+ "	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " 	VALUES (new.created_by,'CREATE',new.id,'global_activity',null,null,null,'global_activity_creation',new.created_on) RETURNING id INTO incCount; \r\n"
				+ "	IF (new.actvity_code IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.gaActivityCode','',new.actvity_code,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_activity','audit_insert','actvity_code');\r\n"
				+ "	END IF;\r\n" + " \r\n" + "	IF (new.allowmultiple IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.gaAllowMultipule','',new.allowmultiple,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_activity','audit_insert','allowmultiple');\r\n"
				+ "	END IF;\r\n" + "	IF (new.geturl IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.geturl','',new.geturl,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_activity','audit_insert','geturl');\r\n"
				+ "	END IF;\r\n" + "	IF (new.name IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.name','',new.name,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_activity','audit_insert','name');\r\n"
				+ "	END IF;\r\n" + "	IF (new.saveurl IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.saveurl','',new.saveurl,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_activity','audit_insert','saveurl');\r\n"
				+ "	END IF;\r\n" + "	IF (new.headding IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.gaShowHeadding','',new.headding,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_activity','audit_insert','headding');\r\n"
				+ "	END IF; \r\n" + "	IF (new.orderno IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.order','',new.orderno,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_activity','audit_insert','orderno');\r\n"
				+ "	END IF;\r\n" + "	IF (new.rejecturl IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.rejecturl','',new.rejecturl,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_activity','audit_insert','rejecturl');\r\n"
				+ "	END IF;\r\n" + "	IF (new.role_ids IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.gaAssigntoRole','',new.role_ids,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_activity','audit_insert','role_ids');\r\n"
				+ " 	END IF;\r\n"
				+ "	select name from bedc.global_groups where id= new.group_id into newGroupId;  \r\n"
				+ "	IF (newGroupId IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.gaGroupAct','',newGroupId,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_activity','audit_insert','group_id');\r\n"
				+ "	END IF;\r\n" + "     IF (new.showinpdf IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.gaShowInPdf','',new.showinpdf,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_activity','audit_insert','showinpdf');\r\n"
				+ "	END IF;\r\n" + "     IF (new.subjectsInput IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.gasubjectInput','',new.subjectsInput,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_activity','audit_insert','showinpdf');\r\n"
				+ "	END IF;\r\n" + "     IF (new.eligibleForNextProcess IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.gaEligibleForNextProcess','',new.eligibleForNextProcess,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_activity','audit_insert','showinpdf');\r\n"
				+ "	END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n"
				+ "	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.updated_by,'UPDATE',new.id,'global_activity',null,null,null,'global_activity_updation',new.updated_on)\r\n"
				+ "	RETURNING id INTO incCount;\r\n"
				+ "	IF nullif(old.actvity_code,'') <> NULLIF(new.actvity_code,'') or new.actvity_code is null THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.gaActivityCode',old.actvity_code,new.actvity_code,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_activity','audit_update','actvity_code');\r\n"
				+ "	END IF;\r\n"
				+ "	IF nullif(old.allowmultiple,'') <> NULLIF(new.allowmultiple,'') or new.allowmultiple is null then\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.allowmultiple',old.allowmultiple,new.allowmultiple,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_activity','label.audit_update','allowmultiple');\r\n"
				+ "	end if;\r\n"
				+ " 	IF NULLIF(old.geturl,'') <> NULLIF(new.geturl,'') or new.geturl is null then\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.geturl',old.geturl,new.geturl,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_activity','label.audit_update','geturl');\r\n"
				+ "	end if;\r\n" + "	IF NULLIF(old.name,'') <> NULLIF(new.name,'') or new.name is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.name',old.name,new.name,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_activity','label.audit_update','name');\r\n"
				+ "	end if;\r\n"
				+ "	IF NULLIF(new.saveurl,'') <> NULLIF(old.saveurl,'') or new.saveurl is null then\r\n"
				+ "		 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		 VALUES ('label.saveurl',old.saveurl,new.saveurl,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_activity','label.audit_update','saveurl');\r\n"
				+ "	end if;\r\n"
				+ "	IF cast(old.headding as varchar) <> cast(new.headding as varchar) or new.headding is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.gaShowHeadding',old.headding,new.headding,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_activity','label.audit_update','headding');\r\n"
				+ "	end if;\r\n"
				+ "	IF cast(old.orderno as varchar) <> cast(new.orderno as varchar) or new.orderno is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.order',old.orderno,new.orderno,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_activity','label.audit_update','orderno');\r\n"
				+ "	end if;\r\n"
				+ "	IF NULLIF(old.rejecturl,'') <> NULLIF(new.rejecturl,'') or new.rejecturl is null then\r\n"
				+ " 		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.rejecturl',old.rejecturl,new.rejecturl,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_activity','audit_update','rejecturl');\r\n"
				+ "	END IF;\r\n"
				+ "		IF NULLIF(old.role_ids,'') <> NULLIF(new.role_ids,'') or new.role_ids is null then\r\n"
				+ "			INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "			VALUES ('label.gaAssigntoRole',old.role_ids,new.role_ids,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_activity','label.audit_update','role_ids');\r\n"
				+ "		end if;\r\n"
				+ "	 select name from bedc.global_groups where id= new.group_id into newGroupId;\r\n"
				+ "	 select name from bedc.global_groups where id =old.group_id into oldGroupId;\r\n"
				+ "		IF NULLIF(old.group_id,'') <> NULLIF(new.group_id,'') or new.group_id is null then\r\n"
				+ "			INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "			VALUES ('label.gaGroupAct',oldGroupId,newGroupId,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_activity','label.audit_update','group_id');\r\n"
				+ "		    end if;\r\n"
				+ "     IF cast(old.showinpdf as varchar) <> cast(new.showinpdf as varchar) or new.showinpdf is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.gaShowInPdf',old.showinpdf,new.showinpdf,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_activity','label.audit_update','showinpdf');\r\n"
				+ "	 end if;\r\n"
				+ "     IF cast(old.subjectsInput as varchar) <> cast(new.subjectsInput as varchar) or new.subjectsInput is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.gasubjectInput',old.subjectsInput,new.subjectsInput,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_activity','label.audit_update','showinpdf');\r\n"
				+ "	 end if;\r\n"
				+ "     IF cast(old.eligibleForNextProcess as varchar) <> cast(new.eligibleForNextProcess as varchar) or new.eligibleForNextProcess is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.gaEligibleForNextProcess',old.eligibleForNextProcess,new.eligibleForNextProcess,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_activity','label.audit_update','showinpdf');\r\n"
				+ "	 end if;\r\n" + "	RETURN new;\r\n" + "	END IF;\r\n" + "	END IF;\r\n" + "	END;"
				+ "$$ LANGUAGE 'plpgsql';");
		
		stmt.execute("CREATE TRIGGER global_act_trgr "
				+ "   AFTER INSERT OR UPDATE  OF id, actvity_code, allowmultiple, created_by, created_on, geturl, headding, name  ON bedc.global_activity\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.global_activity_fun();");

	}

	private void generateGlobalParameterTrigger(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.global_parameter_fun() RETURNS TRIGGER AS $$\r\n" + "declare \r\n"
				+ "   incCount integer;\r\n" + "   actid integer;\r\n" + "   newContentType character varying;\r\n"
				+ "   oldContentType character varying;\r\n" + "   newName character varying;\r\n"
				+ "   oldName character varying;\r\n" + "   newGroupsName character varying;\r\n"
				+ "   oldGroupsName character varying;\r\n" + "   newunitsCode character varying;\r\n"
				+ "   oldunitsCode character varying;\r\n" +

				"BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n" + " \r\n" + " select count(*) from bedc.audit_log into actid;\r\n"
				+ " actid := actid +1;\r\n" + "	IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.created_by,'CREATE',new.id,'global_parameter',null,null,null,'global_parameter_creation',new.created_on) RETURNING id INTO incCount; \r\n"
				+ "	 IF (new.bind_activity IS NOT NULL) THEN\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.bindAct','',new.bind_activity,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_parameter','audit_insert','bind_activity');\r\n"
				+ "	 END IF;\r\n" + "  	 IF (new.order_no IS NOT NULL) THEN\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.order','',new.order_no,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_parameter','audit_insert','order_no');\r\n"
				+ "	 END IF;\r\n" + "	 IF (new.parameter_name IS NOT NULL) THEN\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.name','',new.parameter_name,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_parameter','audit_insert','parameter_name');\r\n"
				+ "	 END IF;\r\n" + "	IF (new.activity IS NOT NULL) THEN\r\n"
				+ "	 select name from bedc.global_activity where id= new.activity into newName;  \r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.gactivity','',newName,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_parameter','audit_insert','activity');\r\n"
				+ "	END IF;\r\n" + "	 IF (new.content_type IS NOT NULL) THEN\r\n"
				+ "	 select name from bedc.control_type where id= new.content_type into newContentType;  \r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.controlType','',newContentType,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_parameter','audit_insert','content_type');\r\n"
				+ "	 END IF;\r\n" + "	 IF (new.groups IS NOT NULL) THEN\r\n"
				+ "	 select name from bedc.global_groups where id= new.groups into newGroupsName;  \r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.group','',newGroupsName,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_parameter','audit_insert','groups');\r\n"
				+ "	 END IF;\r\n" + "     IF (new.control_name IS NOT NULL) THEN\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.controlName','',new.control_name,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_parameter','audit_insert','control_name');\r\n"
				+ "	 END IF;\r\n" + "     IF (new.parameter_phase IS NOT NULL) THEN\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.gaPhase','',new.parameter_phase,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_parameter','audit_insert','control_name');\r\n"
				+ "	 END IF;\r\n" + "     IF (new.units_id IS NOT NULL) THEN\r\n"
				+ "	 select unit_code from bedc.units_master where id= new.units_id into newunitsCode;  \r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.unit.units','',newunitsCode,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_parameter','audit_insert','control_name');\r\n"
				+ "	 END IF;\r\n" + "	RETURN new;\r\n" + "	ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.updated_by,'UPDATE',new.id,'global_parameter',null,null,null,'global_parameter_updation',new.updated_on) RETURNING id INTO incCount;\r\n"
				+ "	 IF cast(old.bind_activity as varchar) <> cast(new.bind_activity as varchar) or new.bind_activity is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.bindAct','',new.bind_activity,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_parameter','audit_update','actvity_code');\r\n"
				+ "	 END IF;\r\n"
				+ "	 IF cast(old.order_no as varchar) <> cast(new.order_no as varchar) or new.order_no is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.order',old.order_no,new.order_no,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_parameter','audit_update','order_no');\r\n"
				+ "	 END IF;\r\n"
				+ "	 IF NULLIF(old.parameter_name,'') <> NULLIF(new.parameter_name,'') or new.parameter_name is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.name',old.parameter_name,new.parameter_name,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_parameter','audit_update','parameter_name');\r\n"
				+ "	 END IF;\r\n"
				+ "	  IF cast(old.activity as varchar) <> cast(new.activity as varchar) or new.activity is null then\r\n"
				+ "	  select name from bedc.global_activity where id= new.activity into newName;  \r\n"
				+ "	  select name from bedc.global_activity where id= old.activity into oldName; \r\n"
				+ "	  INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	  VALUES ('label.gactivity',oldName,newName,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_parameter','audit_update','activity');\r\n"
				+ "	  END IF;\r\n"
				+ "	 IF cast(old.content_type as varchar) <> cast(new.content_type as varchar) or new.content_type is null then\r\n"
				+ "	 select name from bedc.control_type where id= new.content_type into newContentType;  \r\n"
				+ "	  select name from bedc.control_type where id= old.content_type into oldContentType;  \r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.controlType',oldContentType,newContentType,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_parameter','audit_update','content_type');\r\n"
				+ "	 END IF;\r\n"
				+ "	IF cast(old.groups as varchar) <> cast(new.groups as varchar) or new.groups is null then	 \r\n"
				+ "	select name from bedc.global_groups where id= new.groups into newGroupsName;  \r\n"
				+ "	select name from bedc.global_groups where id= old.groups into oldGroupsName;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.group',oldGroupsName,newGroupsName,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_parameter','audit_update','groups');\r\n"
				+ "	END IF;\r\n"
				+ "    IF NULLIF(old.control_name,'') <> NULLIF(new.control_name,'') or new.control_name is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.controlName',old.control_name,new.control_name,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_parameter','audit_update','control_name');\r\n"
				+ "	 END IF;\r\n"
				+ "    IF NULLIF(old.parameter_phase,'') <> NULLIF(new.parameter_phase,'') or new.parameter_phase is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.gaPhase',old.parameter_phase,new.parameter_phase,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_parameter','audit_update','control_name');\r\n"
				+ "	 END IF;\r\n"
				+ "	IF cast(old.units_id as varchar) <> cast(new.units_id as varchar) or new.units_id is null then	 \r\n"
				+ "	 select unit_code from bedc.units_master where id= new.units_id into newunitsCode;  \r\n"
				+ "	 select unit_code from bedc.units_master where id= old.units_id into oldunitsCode;  \r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.unit.units',oldunitsCode,newunitsCode,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_parameter','audit_update','control_name');\r\n"
				+ "	 END IF;\r\n" +

				" 	RETURN new;\r\n" + "	END IF;\r\n" + "	END IF;\r\n" + "	END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER global_parameters_trgr "
				+ "   AFTER INSERT OR UPDATE  OF id, bind_activity,control_name,created_by,created_on,order_no,parameter_name,update_reason,updated_by,updated_on,activity,content_type,groups,units_id  ON bedc.global_parameter\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.global_parameter_fun();");
	}

	private void generateGlobalValues(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.global_values_fun() RETURNS TRIGGER AS $$\r\n" + "declare \r\n"
				+ "   incCount integer;\r\n" + "   actid integer;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "	IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.created_by,'CREATE',new.id,'global_values',null,null,null,'global_values_creation',new.created_on) RETURNING id INTO incCount; \r\n"
				+ "	IF (new.name IS NOT NULL) THEN	\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.name','',new.name,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_values','audit_insert','name');\r\n"
				+ "	END IF;\r\n" + "	IF (new.order_no IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.order','',new.order_no,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'global_values','audit_insert','order_no');\r\n"
				+ "	END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	 VALUES (new.updated_by,'UPDATE',new.id,'global_values',null,null,null,'global_values_updation',new.created_on) RETURNING id INTO incCount; \r\n"
				+ "IF NULLIF(old.name,'') <> NULLIF(new.name,'') or new.name is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.name',old.name,new.name,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_values','label.audit_update','name');\r\n"
				+ "	end if;\r\n"
				+ "IF cast(old.order_no as varchar) <> cast(new.order_no as varchar) or new.order_no is null then\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.order_no',old.order_no,new.order_no,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'global_values','label.audit_update','order_no');\r\n"
				+ "	end if;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER global_values_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, created_on,name,order_no,update_reason,updated_by,updated_on,created_by  ON bedc.global_values\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.global_values_fun();");
	}

	private void generatedInstrumentModel(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.instrument_model_fun() RETURNS TRIGGER AS $$\r\n" + "declare \r\n"
				+ "   incCount integer;\r\n" + "   actid integer;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "	IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "		VALUES (new.created_by,'CREATE',new.id,'instrument_model',null,null,null,'instrument_model_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+ "	IF (new.instrumentmodel IS NOT NULL) THEN	\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.instrument.moduleLa','',new.instrumentmodel,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'instrument_model','audit_insert','instrumentmodel');\r\n"
				+ "	END IF;\r\n" + "    RETURN new;\r\n" + "	ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.updated_by,'UPDATE',new.id,'instrument_model',null,null,null,'instrument_model_updation',new.updated_on) RETURNING id INTO incCount;\r\n"
				+ "	END IF;\r\n"
				+ "	IF NULLIF(old.instrumentmodel,'') <> NULLIF(new.instrumentmodel,'') or new.instrumentmodel is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.instrument.moduleLa',old.instrumentmodel,new.instrumentmodel,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'instrument_model','label.audit_update','instrumentmodel');\r\n"
				+ "	RETURN new;\r\n" + "	END IF;\r\n" + "	END IF;\r\n" + "	END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER instrument_model_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, created_by,created_on,instrumentmodel,update_reason,updated_by,updated_on  ON bedc.instrument_model\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.instrument_model_fun();");
	}

	private void generateInstrumentType(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.instrument_type_fun() RETURNS TRIGGER AS $$\r\n" + "declare \r\n"
				+ "   incCount integer;\r\n" + "   actid integer;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "	IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "		VALUES (new.created_by,'CREATE',new.id,'instrument_type',null,null,null,'instrument_type_creation',new.created_on) RETURNING id INTO incCount; \r\n"
				+ "	IF (new.instrumenttype IS NOT NULL) THEN\r\n"
				+ "			INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "			VALUES ('label.instrument.typeLa','',new.instrumenttype,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'instrument_type','audit_insert','instrumenttype');\r\n"
				+ "		END IF;\r\n" + "IF (new.instrumentcode IS NOT NULL) THEN\r\n"
				+ "			INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "			VALUES ('label.instrument.typeLaCode','',new.instrumentcode,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'instrument_type','audit_insert','instrumentcode');\r\n"
				+ "		END IF;\r\n" + "RETURN new;	  \r\n" + "	 ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.updated_by,'UPDATE',new.id,'instrument_type',null,null,null,'instrument_type_updation',new.updated_on)\r\n"
				+ "	RETURNING id INTO incCount;\r\n"
				+ "	  IF nullif(old.instrumenttype,'') <> NULLIF(new.instrumenttype,'') or new.instrumenttype is null THEN\r\n"
				+ "  		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.instrument.typeLa',old.instrumenttype,new.instrumenttype,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'instrument_type','audit_update','instrumenttype');\r\n"
				+ "	END IF;\r\n"
				+ "	IF nullif(old.instrumentcode,'') <> NULLIF(new.instrumentcode,'') or new.instrumentcode is null then\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('label.instrument.typeLaCode',old.instrumentcode,new.instrumentcode,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'instrument_type','label.audit_update','instrumentcode');\r\n"
				+ "	END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");
		stmt.execute("CREATE TRIGGER instrument_type_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, created_by,created_on,instrumentcode,instrumenttype,update_reason,updated_by,updated_on  ON bedc.instrument_type\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.instrument_type_fun();");

	}

	private void generateLanguageSpecificGlobalActivityDetails(Connection con, Statement stmt) throws Exception {
		stmt.execute(
				"CREATE OR REPLACE FUNCTION bedc.language_specific_global_activity_details_fun() RETURNS TRIGGER AS $$\r\n"
						+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
						+ "   newGlobalActivity character varying;\r\n" + "   oldGlobalActivity character varying;\r\n"
						+ "  newlangId character varying;\r\n" + "  oldlangId character varying;\r\n" + "BEGIN\r\n"
						+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
						+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
						+ "	IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
						"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ "	VALUES (new.created_by,'CREATE',new.id,'language_specific_global_activity_details',null,null,null,'language_specific_global_activity_details_creation',new.created_on)\r\n"
						+ "	RETURNING id INTO incCount; \r\n" + "	 IF (new.name IS NOT NULL) THEN\r\n"
						+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "		VALUES ('label.name','',new.name,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'language_specific_global_activity_details','audit_insert','name');\r\n"
						+ "	END IF;\r\n" + "	 IF (new.order_no IS NOT NULL) THEN\r\n"
						+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "		VALUES ('label.order','',new.order_no,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'language_specific_global_activity_details','audit_insert','order_no');\r\n"
						+ "	END IF;\r\n" + "	IF (new.global_activity IS NOT NULL) THEN\r\n"
						+ "	select name from bedc.global_activity where id= new.global_activity into newGlobalActivity;\r\n"
						+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "		VALUES ('label.name','',newGlobalActivity,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'language_specific_global_activity_details','audit_insert','global_activity');\r\n"
						+ "		END IF;\r\n" + "	 IF (new.lang_id IS NOT NULL) THEN\r\n"
						+ "	 select country from bedc.internationalizaion_languages where id= new.lang_id into newlangId;\r\n"
						+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "		VALUES ('label.language','',newlangId,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'language_specific_global_activity_details','audit_insert','lang_id');\r\n"
						+ "	END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
						"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ "	VALUES (new.updated_by,'UPDATE',new.id,'global_parameter',null,null,null,'language_specific_global_activity_details_updation',new.updated_on)\r\n"
						+ "	RETURNING id INTO incCount;\r\n"
						+ "   IF nullif(old.name,'') <> NULLIF(new.name,'') or new.name is null THEN\r\n"
						+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "		VALUES ('label.name',old.name,new.name,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'language_specific_global_activity_details','audit_update','name');\r\n"
						+ "	END IF;\r\n"
						+ "	 IF cast(old.order_no as varchar) <> cast(new.order_no as varchar) or new.order_no is null then\r\n"
						+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "	 VALUES ('label.order',old.order_no,new.order_no,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'language_specific_global_activity_details','label.audit_update','order_no');\r\n"
						+ "	end if;\r\n"
						+ "    IF cast(old.global_activity as varchar) <> cast(new.global_activity as varchar) or new.global_activity is null then\r\n"
						+ "   select name from bedc.global_activity where id= new.global_activity into newGlobalActivity;\r\n"
						+ "   select name from bedc.global_activity where id= old.global_activity into oldGlobalActivity;\r\n"
						+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "	 VALUES ('label.name',oldGlobalActivity,newGlobalActivity,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'language_specific_global_activity_details','label.audit_update','global_activity');\r\n"
						+ "	end if;\r\n"
						+ "	IF cast(old.lang_id as varchar) <> cast(new.lang_id as varchar) or new.lang_id is null then\r\n"
						+ "	 select country from bedc.internationalizaion_languages where id= new.lang_id into newlangId;  \r\n"
						+ "	  select country from bedc.internationalizaion_languages where id= old.lang_id into oldlangId;  \r\n"
						+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "	 VALUES ('label.language',oldlangId,newlangId,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'language_specific_global_activity_details','label.audit_update','lang_id');\r\n"
						+ "	end if;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
						+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER language_specific_global_activity_details_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, created_by,created_on,name,order_no,update_reason,updated_by,updated_on,global_activity,lang_id  ON bedc.language_specific_global_activity_details\r\n"
				+ "    FOR EACH ROW\r\n"
				+ "    EXECUTE FUNCTION bedc.language_specific_global_activity_details_fun();");

	}

	private void generateLanguageSpecificGlobalParameterDetails(Connection con, Statement stmt) throws Exception {
		stmt.execute(
				"CREATE OR REPLACE FUNCTION bedc.language_specific_global_parameter_details_fun() RETURNS TRIGGER AS $$\r\n"
						+

						"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
						+ "  newLangId character varying;\r\n" + "  oldLangId character varying;\r\n"
						+ "  newParameterId character varying;\r\n" + "  oldParameterId character varying;\r\n"
						+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
						+ " incCount := incCount +1;\r\n" + " select count(*) from bedc.audit_log into actid;\r\n"
						+ " actid := actid +1;\r\n" + "IF (old.id IS NULL) THEN\r\n" + // INSET TRIGGER
						"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ " 	VALUES (new.created_by,'CREATE',new.id,'language_specific_global_activity_details',null,null,null,'language_specific_global_parameter_details_creation',null) RETURNING id INTO incCount; \r\n"
						+ "	IF (new.name IS NOT NULL) THEN\r\n"
						+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "	 VALUES ('label.name','',new.name,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'language_specific_global_parameter_details','audit_insert','name');\r\n"
						+ "	 END IF;\r\n" + "  IF (new.order_no IS NOT NULL) THEN\r\n"
						+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "		VALUES ('label.order','',new.order_no,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'language_specific_global_parameter_details','audit_insert','order_no');\r\n"
						+ "	END IF;\r\n" + "	IF (new.lang_id IS NOT NULL) THEN\r\n"
						+ "	 select country_code from bedc.internationalizaion_languages where id= new.lang_id into newLangId;\r\n"
						+ "     INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "	 VALUES ('label.language',oldLangId,newLangId,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'language_specific_global_parameter_details','audit_insert','lang_id');\r\n"
						+ "	 END IF;\r\n" + "	 IF (new.parameter_id IS NOT NULL) THEN\r\n"
						+ "	 select parameter_name from bedc.global_parameter where id= new.parameter_id into newParameterId;  \r\n"
						+ "	  INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "	 VALUES ('label.name','',newParameterId,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'language_specific_global_parameter_details','audit_insert','parameter_id');\r\n"
						+ "	END IF; \r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
						"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ "	VALUES (new.updated_by,'UPDATE',new.id,'language_specific_global_parameter_details',null,null,null,'language_specific_global_parameter_details_updation',new.updated_on)\r\n"
						+ "	RETURNING id INTO incCount;\r\n"
						+ "	IF NULLIF(old.name,'') <> NULLIF(new.name,'') or new.name is null then\r\n"
						+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "	 VALUES ('label.name',old.name,new.name,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'language_specific_global_parameter_details','label.audit_update','name');\r\n"
						+ "	 END IF;\r\n"
						+ "  IF cast(old.order_no as varchar) <> cast(new.order_no as varchar) or new.order_no is null then\r\n"
						+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "	 VALUES ('label.order',old.order_no,new.order_no,'Static',null,new.updated_by,new.updated_on,' ',incCount,'language_specific_global_parameter_details','label.audit_update','order_no');\r\n"
						+ "	end if;\r\n"
						+ "   IF cast(old.lang_id as varchar) <> cast(new.lang_id as varchar) or new.lang_id is null then\r\n"
						+ "  select country_code from bedc.internationalizaion_languages where id= new.lang_id into newLangId; \r\n"
						+ "  select country_code from bedc.internationalizaion_languages where id= old.lang_id into oldLangId;    \r\n"
						+ "  INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "  VALUES ('label.language',oldLangId,newLangId,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'language_specific_global_parameter_details','label.audit_update','lang_id');\r\n"
						+ "  end if;\r\n"
						+ " IF cast(old.parameter_id as varchar) <> cast(new.parameter_id as varchar) or new.parameter_id is null then\r\n"
						+ " select parameter_name from bedc.global_parameter where id= new.parameter_id into newParameterId; \r\n"
						+ " select parameter_name from bedc.global_parameter where id= old.parameter_id into oldParameterId;  \r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.name',oldParameterId,newParameterId,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'language_specific_global_parameter_details','label.audit_update','parameter_id');\r\n"
						+ " END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
						+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER language_specific_global_parameter_details_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, created_by,created_on,name,order_no,update_reason,updated_by,updated_on,lang_id,parameter_id  ON bedc.language_specific_global_parameter_details\r\n"
				+ "    FOR EACH ROW\r\n"
				+ "    EXECUTE FUNCTION bedc.language_specific_global_parameter_details_fun();");
	}

	private void generateLanguageSpecificGroupDetails(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.language_specific_group_details_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "  newLangId character varying;\r\n" + "  oldLangId character varying;\r\n"
				+ "  newGlobalGroupId character varying;\r\n" + "  oldGlobalGroupId character varying;\r\n"
				+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n" + " select count(*) from bedc.audit_log into actid;\r\n"
				+ " actid := actid +1;\r\n" + "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " 	VALUES (new.created_by,'CREATE',new.id,'language_specific_group_details',null,null,null,'language_specific_group_details_creation',new.created_on) RETURNING id INTO incCount; \r\n"
				+ "	IF (new.name IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.name','',new.name,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'language_specific_group_details','audit_insert','name');\r\n"
				+ "	END IF;\r\n" + "	IF (new.global_group_id IS NOT NULL) THEN  \r\n"
				+ "	select name from bedc.global_groups where id= new.global_group_id into newGlobalGroupId;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.name','',newGlobalGroupId,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'language_specific_group_details','audit_insert','global_group_id');\r\n"
				+ "    END IF;\r\n" + "	  IF (new.lang_id IS NOT NULL) THEN\r\n"
				+ "	 select lang_code from bedc.internationalizaion_languages where id= new.lang_id into newLangId;  \r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.language','',newLangId,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'language_specific_group_details','label.audit_update','lang_id');\r\n"
				+ "	 END IF;\r\n" + "  RETURN new;\r\n" + "  ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.updated_by,'UPDATE',new.id,'language_specific_group_details',null,null,null,'language_specific_group_details_updation',new.updated_on)\r\n"
				+ "	RETURNING id INTO incCount;\r\n"
				+ "	IF NULLIF(old.name,'') <> NULLIF(new.name,'') or new.name is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name ,action,databasefieldname)\r\n"
				+ "	VALUES ('label.name',old.name,new.name,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'language_specific_group_details','label.audit_update','name');\r\n"
				+ "	END IF;\r\n"
				+ "    IF cast(old.global_group_id as varchar) <> cast(new.global_group_id as varchar) or new.global_group_id is null then\r\n"
				+ "	 select name from bedc.global_groups where id= new.global_group_id into newGlobalGroupId;\r\n"
				+ "	select name from bedc.global_groups where id= old.global_group_id into oldGlobalGroupId; 	 \r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.name',oldGlobalGroupId,newGlobalGroupId,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'language_specific_group_details','label.audit_update',' global_group_id');   \r\n"
				+ "	END IF; \r\n"
				+ "IF cast(old.lang_id as varchar) <> cast(new.lang_id as varchar) or new.lang_id is null then\r\n"
				+ "	 select lang_code from bedc.internationalizaion_languages where id= new.lang_id into newLangId;  \r\n"
				+ "	  select lang_code from bedc.internationalizaion_languages where id= old.lang_id into oldLangId; \r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.language',oldLangId,newLangId,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'language_specific_group_details','label.audit_update',' lang_id');   \r\n"
				+ "	 END IF;\r\n" + "    RETURN new;\r\n" + "	END IF;\r\n" + "	END IF;\r\n" + "	END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");
		stmt.execute("CREATE TRIGGER language_specific_group_details_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, created_by,created_on,name,update_reason,updated_by,updated_on,global_group_id,lang_id  ON bedc.language_specific_group_details\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.language_specific_group_details_fun();");

	}

	private void generateLanguageSpecificValueDetails(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.language_specific_value_details_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "  newGlobalValId character varying;\r\n" + "  oldGlobalValId character varying;\r\n"
				+ "  newLangId character varying;\r\n" + "  oldLangId character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "	IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.created_by,'CREATE',new.id,'language_specific_value_details',null,null,null,'language_specific_value_details_creation',new.created_on) RETURNING id INTO incCount; \r\n"
				+ "	IF (new.name IS NOT NULL) THEN	\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.name','',new.name,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'language_specific_value_details','audit_insert','name');\r\n"
				+ "	END IF;\r\n" + "	 IF (new.global_val_id IS NOT NULL) THEN	\r\n"
				+ "	 select name from bedc.global_values where id= new.global_val_id into newGlobalValId;  \r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.name','',newGlobalValId,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'language_specific_value_details','audit_insert','global_val_id');\r\n"
				+ "	 END IF;\r\n" + "	IF (new.lang_id IS NOT NULL) THEN	\r\n"
				+ "	 select language from bedc.internationalizaion_languages where id= new.lang_id into newLangId;  \r\n"
				+ "	  INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.language','',newLangId,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'language_specific_value_details','audit_insert','lang_id');\r\n"
				+ "	 END IF;\r\n" + "RETURN new;\r\n" + "  ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.updated_by,'UPDATE',new.id,'language_specific_value_details',null,null,null,'language_specific_value_details_updation',new.updated_on)\r\n"
				+ "	RETURNING id INTO incCount;\r\n"
				+ "	IF NULLIF(old.name,'') <> NULLIF(new.name,'') or new.name is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.name',old.name,new.name,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'language_specific_value_details','label.audit_update','name');\r\n"
				+ "	 END IF;\r\n"
				+ "     IF cast(old.global_val_id as varchar) <> cast(new.global_val_id as varchar) or new.global_val_id is null then\r\n"
				+ "	 select name from bedc.global_values where id= new.global_val_id into newGlobalValId;  \r\n"
				+ "	  select name from bedc.global_values where id= old.global_val_id into oldGlobalValId;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.name',oldGlobalValId,newGlobalValId,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'language_specific_value_details','label.audit_update','global_val_id');\r\n"
				+ "    END IF;\r\n"
				+ "    IF cast(old.lang_id as varchar) <> cast(new.lang_id as varchar) or new.lang_id is null then\r\n"
				+ "	 select language from bedc.internationalizaion_languages where id= new.lang_id into newLangId;  \r\n"
				+ "	 select language from bedc.internationalizaion_languages where id= old.lang_id into oldLangId;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.language',oldLangId,newLangId,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'language_specific_value_details','label.audit_update','lang_id');   \r\n"
				+ "	END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER language_specific_value_details_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, created_on,name,update_reason,updated_by,updated_on,created_by,global_val_id,lang_id  ON bedc.language_specific_value_details\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.language_specific_value_details_fun();");

	}

	private void generateLanguageSpecificSubjectWithdrawActivity(Connection con, Statement stmt) throws Exception {
		stmt.execute(
				"CREATE OR REPLACE FUNCTION bedc.languagespecific_subjectwithdrawactivity_fun() RETURNS TRIGGER AS $$\r\n"
						+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
						+ "  newLangId character varying;\r\n" + "  oldLangId character varying;\r\n"
						+ "  newGlobalGroupId character varying;\r\n" + "  oldGlobalGroupId character varying;\r\n"
						+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
						+ " incCount := incCount +1;\r\n" + " select count(*) from bedc.audit_log into actid;\r\n"
						+ " actid := actid +1;\r\n" + "IF (old.id IS NULL) THEN\r\n" + // INSET TRIGGER
						"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ " 	VALUES (new.created_by,'CREATE',new.id,'languagespecific_subjectwithdrawactivity',null,null,null,'languagespecific_subjectwithdrawactivity_creation',new.created_on) RETURNING id INTO incCount; \r\n"
						+ "	IF (new.name IS NOT NULL) THEN\r\n"
						+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "	VALUES ('label.name','',new.name,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'languagespecific_subjectwithdrawactivity','audit_insert','name');\r\n"
						+ "	END IF;\r\n" + "	 IF (new.lang_id IS NOT NULL) THEN\r\n"
						+ "	 select lang_code from bedc.internationalizaion_languages where id= new.lang_id into newLangId;  \r\n"
						+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "	 VALUES ('label.language','',newLangId,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'languagespecific_subjectwithdrawactivity','label.audit_update','lang_id');\r\n"
						+ "	 END IF;\r\n" + "  RETURN new;\r\n" + "  ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE
																											// TRIGGER
						"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ "	VALUES (new.updated_by,'UPDATE',new.id,'languagespecific_subjectwithdrawactivity',null,null,null,'languagespecific_subjectwithdrawactivity_updation',new.updated_on)\r\n"
						+ "	RETURNING id INTO incCount;\r\n"
						+ "	IF NULLIF(old.name,'') <> NULLIF(new.name,'') or new.name is null then\r\n"
						+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name ,action,databasefieldname)\r\n"
						+ "	VALUES ('label.name',old.name,new.name,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'languagespecific_subjectwithdrawactivity','label.audit_update','name');\r\n"
						+ "	END IF;\r\n"
						+ " IF cast(old.lang_id as varchar) <> cast(new.lang_id as varchar) or new.lang_id is null then\r\n"
						+ "	 select lang_code from bedc.internationalizaion_languages where id= new.lang_id into newLangId;  \r\n"
						+ "	  select lang_code from bedc.internationalizaion_languages where id= old.lang_id into oldLangId; \r\n"
						+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "	 VALUES ('label.language',oldLangId,newLangId,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'languagespecific_subjectwithdrawactivity','label.audit_update',' lang_id');   \r\n"
						+ "	 END IF;\r\n" + "    RETURN new;\r\n" + "	END IF;\r\n" + "	END IF;\r\n"
						+ "	END;\r\n" + "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER languagespecific_subjectwithdrawactivity_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, created_by,created_on,name,update_reason,updated_by,updated_on,lang_id,sujectwithdrawactivity  ON bedc.languagespecific_subjectwithdrawactivity\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.languagespecific_subjectwithdrawactivity_fun();");

	}

	private void generateProjectDetailsDiscrepancy(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.project_details_discrepancy_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare  \r\n" + "incCount integer; \r\n" + "newprojectid character varying;\r\n"
				+ "projectid character varying;\r\n" +

				"BEGIN \r\n" + "SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ "incCount := incCount +1; \r\n" + "IF (old.id IS NULL) THEN  \r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.createdby,'CREATE',new.id,'project_details_discrepancy',null,null,null,'project_details_discrepancy_creation',new.createdon) RETURNING id INTO incCount;  \r\n"
				+ "IF (new.isresponsesubmitted IS NOT NULL) THEN	\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.isresponsesubmitted','',new.isresponsesubmitted,'Static',null,null,null,new.comments,new.createdby,new.createdon,incCount,'project_details_discrepancy','audit_insert','isresponsesubmitted');\r\n"
				+ "END IF; \r\n" + "IF (new.response IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.response','',new.response,'Static',null,null,null,new.comments,new.createdby,new.createdon,incCount,'project_details_discrepancy','audit_insert','response');   \r\n"
				+ "END IF; \r\n" + "IF (new.status IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.status','',new.status,'Static',null,null,null,new.comments,new.createdby,new.createdon,incCount,'project_details_discrepancy','audit_insert','status');\r\n"
				+ "END IF;\r\n"
				+ "select projectno from bedc.projects where projectid= new.project_projectid into newprojectid;   \r\n"
				+ "IF (new.project_projectid IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname) \r\n"
				+ "VALUES ('project_projectid','',newprojectid,'Dynamic',null,null,null,new.comments,new.createdby,new.createdon,incCount,'project_details_discrepancy','audit_insert','project_projectid');\r\n"
				+ "END IF;\r\n" + "\r\n" + "RETURN new;\r\n" + "\r\n" + "ELSE IF (new.id=old.id) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on) \r\n"
				+ "VALUES (new.updateby,'UPDATE',new.id,'project_details_discrepancy',null,null,null,'project_details_discrepancy_updation',new.updateon) \r\n"
				+ "RETURNING id INTO incCount; \r\n"
				+ "IF NULLIF(old.isresponsesubmitted,'') <> NULLIF(new.isresponsesubmitted,'') or new.isresponsesubmitted is null then	 \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname) \r\n"
				+ "VALUES ('isresponsesubmitted',old.isresponsesubmitted,new.isresponsesubmitted,'Static',null,null,null,new.comments,new.updateby,new.updateon,incCount,'project_details_discrepancy','audit_update','isresponsesubmitted'); \r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.response,'') <> NULLIF(new.response,'') or new.response is null then	 \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('response',old.response,new.response,'Static',null,null,null,new.comments,new.updateby,new.updateon,incCount,'project_details_discrepancy','audit_update','response');  \r\n"
				+ "END IF; \r\n" + "IF NULLIF(old.status,'') <> NULLIF(new.status,'') or new.status is null then \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname) \r\n"
				+ "VALUES ('label.status',old.status,new.status,'Static',null,null,null,new.comments,new.updateby,new.updateon,incCount,'project_details_discrepancy','audit_update','status'); \r\n"
				+ "END IF; \r\n"
				+ " IF cast(old.project_projectid as varchar) <> cast(new.project_projectid as varchar) or new.project_projectid is null then \r\n"
				+ "select projectno from bedc.projects where projectid= new.project_projectid into newprojectid;   \r\n"
				+ "select projectno from bedc.projects where projectid= old.project_projectid into projectid;   \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('project_projectid',projectid,newprojectid,'Dynamic',null,null,null,new.comments,new.updateby,new.updateon,incCount,'project_details_discrepancy','audit_update','project_projectid');  \r\n"
				+ "END IF; \r\n" + "RETURN new; \r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER project_details_discrepancy_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, comments,createdby,createdon,isresponsesubmitted,response,status,updateby,updateon,"
				+ "project_projectid ON bedc.project_details_discrepancy\r\n" + "    FOR EACH ROW\r\n"
				+ "    EXECUTE FUNCTION bedc.project_details_discrepancy_fun();");

	}

	private void generateProjectdetailsRandomization(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.projectdetails_randomization_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "	IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "		VALUES (new.created_by,'CREATE',new.id,'projectdetails_randomization',null,null,null,'projectdetails_randomization_creation',new.created_on) RETURNING id INTO incCount;\r\n"
				+ "	IF (new.period IS NOT NULL) THEN	\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.period','',new.period,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'projectdetails_randomization','audit_insert','period');\r\n"
				+ "	END IF;\r\n" + "	 IF (new.projectno IS NOT NULL) THEN\r\n"
				+ "	 INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.projectno','',new.projectno,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'projectdetails_randomization','audit_insert','projectno');\r\n"
				+ "	 END IF;\r\n" + "	  IF (new.randomizationcode IS NOT NULL) THEN\r\n"
				+ "	  INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	  VALUES ('label.randomizationcode','',new.randomizationcode,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'projectdetails_randomization','audit_insert','randomizationcode'); \r\n"
				+ "	  END IF;\r\n" + "	IF (new.status IS NOT NULL) THEN	\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.status','',new.status,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'projectdetails_randomization','audit_insert','status'); \r\n"
				+ "	END IF;\r\n" + "	IF (new.subjectno IS NOT NULL) THEN		\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.subjectno','',new.subjectno,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'projectdetails_randomization','audit_insert','subjectno'); \r\n"
				+ "	END IF;\r\n" + "RETURN new;\r\n" + "  ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGR
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.updated_by,'UPDATE',new.id,'projectdetails_randomization',null,null,null,'projectdetails_randomization_updation',new.updated_on)\r\n"
				+ "	RETURNING id INTO incCount;\r\n"
				+ "	IF NULLIF(old.period,'') <> NULLIF(new.period,'') or new.period is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.period',old.period,new.period,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'projectdetails_randomization','label.audit_update','period');  \r\n"
				+ "	 END IF;\r\n"
				+ "	IF cast(old.projectno as varchar) <> cast(new.projectno as varchar) or new.projectno is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.projectno',old.projectno,new.projectno,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'projectdetails_randomization','label.audit_update','projectno'); \r\n"
				+ "    END IF;\r\n"
				+ "	 IF NULLIF(old.randomizationcode,'') <> NULLIF(new.randomizationcode,'') or new.randomizationcode is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.randomizationcode',old.randomizationcode,new.randomizationcode,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'projectdetails_randomization','label.audit_update','randomizationcode'); \r\n"
				+ "	END IF;\r\n"
				+ "	IF NULLIF(old.status,'') <> NULLIF(new.status,'') or new.status is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.status',old.status,new.status,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'projectdetails_randomization','label.audit_update','status');  \r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.subjectno,'') <> NULLIF(new.subjectno,'') or new.subjectno is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name ,action,databasefieldname)\r\n"
				+ "	 VALUES ('label.subjectno',old.subjectno,new.subjectno,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'projectdetails_randomization','label.audit_update','subjectno');     \r\n"
				+ "	END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER projectdetails_randomization_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id, comments,createdby,createdon,period,projectno,randomizationcode,status,subjectno,update_reason,updatedby,updatedon  ON bedc.projectdetails_randomization\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.projectdetails_randomization_fun();");

	}

	private void generateEpkSubjectReplacedInfo(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.epk_subject_replaced_info_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   periodstatusName character varying;\r\n" + "   oldperiodstatusName character varying;\r\n"
				+ "   periodtypeName  character varying;\r\n" + "   oldperiodtypeName  character varying;\r\n"
				+ "   userName character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ "  select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.subjectreplacedinfoid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"  SELECT full_name FROM bedc.epk_user_master WHERE userid = NEW.createdby INTO userName;\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (userName,'CREATE',new.subjectreplacedinfoid,'epk_sub_replaced_info',new.study_id,null,new.volunteer_id,'epk_sub_replaced_info_creation',new.createdon) RETURNING id INTO incCount; \r\n"
				+ "IF (new.updatereason IS NOT NULL) THEN \r\n"
				+ "  SELECT full_name FROM bedc.epk_user_master WHERE userid = NEW.createdby INTO userName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.updatereason','',new.updatereason,'Static',new.study_id,new.volunteer_id,null,'',userName,new.createdon,incCount,'epk_sub_replaced_info','audit_insert','updatereason');\r\n"
				+ "END IF;\r\n" + "IF (new.replacedwithbedno IS NOT NULL) THEN \r\n"
				+ " SELECT full_name FROM bedc.epk_user_master WHERE userid = NEW.createdby INTO userName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.replacedwithbedno','',new.replacedwithbedno,'Static',new.study_id,new.volunteer_id,null,'',userName,new.createdon,incCount,'epk_sub_replaced_info','audit_insert','replacedwithbedno');\r\n"
				+ "END IF;\r\n" + "IF (new.replacedwithseqno IS NOT NULL) THEN \r\n"
				+ "SELECT full_name FROM bedc.epk_user_master WHERE userid = NEW.createdby INTO userName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.replacedwithseqno','',new.replacedwithseqno,'Static',new.study_id,new.volunteer_id,null,'',userName,new.createdon,incCount,'epk_sub_replaced_info','audit_insert','replacedwithseqno');\r\n"
				+ "END IF;\r\n" + "IF (new.seqno IS NOT NULL) THEN\r\n"
				+ "SELECT full_name FROM bedc.epk_user_master WHERE userid = NEW.createdby INTO userName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.seqno','',new.seqno,'Static',new.study_id,new.volunteer_id,null,'',userName,new.createdon,incCount,'epk_sub_replaced_info','audit_insert','seqno');\r\n"
				+ "END IF;\r\n" + "IF (new.subjectno IS NOT NULL) THEN\r\n"
				+ "SELECT full_name FROM bedc.epk_user_master WHERE userid = NEW.createdby INTO userName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectno','',new.subjectno,'Static',new.study_id,new.volunteer_id,null,'',userName,new.createdon,incCount,'epk_sub_replaced_info','audit_insert','subjectno');\r\n"
				+ "END IF;\r\n" + "IF (new.activestatus IS NOT NULL) THEN\r\n"
				+ "SELECT full_name FROM bedc.epk_user_master WHERE userid = NEW.createdby INTO userName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.activestatus','',new.activestatus,'Dynamic',new.study_id,new.volunteer_id,null,'',userName,new.createdon,incCount,'epk_sub_replaced_info','audit_insert','activestatus');\r\n"
				+ "END IF;\r\n" + "\r\n" + "IF (new.replacedwith IS NOT NULL) THEN \r\n"
				+ "SELECT full_name FROM bedc.epk_user_master WHERE userid = NEW.createdby INTO userName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.replacedwith','',new.replacedwith,'Dynamic',new.study_id,new.volunteer_id,null,' ',userName,new.createdon,incCount,'epk_sub_replaced_info','audit_insert','replacedwith');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n"
				+ "ELSE IF (new.subjectreplacedinfoid=old.subjectreplacedinfoid) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updatedby,'UPDATE',new.subjectreplacedinfoid,'study_period_master',new.study_id,null,new.volunteer_id,'study_period_master_updation',new.createdon)RETURNING id INTO incCount;\r\n"
				+ "IF NULLIF(old.updatereason,'') <> NULLIF(new.updatereason,'') or new.updatereason is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.updatereason',old.updatereason,new.updatereason,'Static',new.study_id,new.volunteer_id,null,' ',null,null,incCount,'epk_sub_replaced_info','audit_update','updatereason');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.replacedwithbedno,'') <> NULLIF (new.replacedwithbedno,'') or new.replacedwithbedno is NULL THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.replacedwithbedno',old.replacedwithbedno,new.replacedwithbedno,'Static',new.study_id,new.volunteer_id,null,' ',null,null,incCount,'epk_sub_replaced_info','audit_update','replacedwithbedno');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.replacedwithseqno, 0) <> NULLIF(new.replacedwithseqno, 0) or new.replacedwithseqno is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.replacedwithseqno',old.replacedwithseqno,new.replacedwithseqno,'Static',new.study_id,new.volunteer_id,null,' ',null,null,incCount,'epk_sub_replaced_info','audit_update','replacedwithseqno');\r\n"
				+ "END IF;\r\n" + "IF NULLIF (old.seqno, 0) <> NULLIF(new.seqno,0) or new.seqno is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.seqno',old.seqno,new.seqno,'Static',new.study_id,new.volunteer_id,null,' ',null,null,incCount,'epk_sub_replaced_info','audit_update','seqno');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF (old.subjectno,'') <> NULLIF(new.subjectno,'') or new.subjectno is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectno',old.subjectno,new.subjectno,'Static',new.study_id,new.volunteer_id,null,' ',null,null,incCount,'epk_sub_replaced_info','audit_update','subjectno');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF (old.activestatus, null) <> NULLIF(new.activestatus,null) or new.activestatus is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.activestatus',old.activestatus,new.activestatus,'Dynamic',new.study_id,new.volunteer_id,null,' ',null,null,incCount,'epk_sub_replaced_info','audit_update','activestatus');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF (old.replacedwith,null) <> NULLIF(new.replacedwith,null) or new.replacedwith is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.replacedwith',old.replacedwith,new.replacedwith,'Dynamic',new.study_id,new.volunteer_id,null,' ',null,null,incCount,'epk_sub_replaced_info','audit_update','replacedwith');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER epk_subject_replaced_info_trgr"
				+ "   AFTER INSERT OR UPDATE  OF subjectreplacedinfoid,createdon,updatereason,updatedon,replacedwithbedno,replacedwithseqno,seqno,subjectno,createdby,updatedby,activestatus,replacedwith,study_id,volunteer_id  ON bedc.epk_subject_replaced_info\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.epk_subject_replaced_info_fun();");

	}

	private void generateEpkSubjectSampleCollectionTimepointsData(Connection con, Statement stmt) throws Exception {
		stmt.execute(
				"CREATE OR REPLACE FUNCTION bedc.epk_subject_sample_collection_timepoints_data_fun() RETURNS TRIGGER AS $$\r\n"
						+ "\r\n" + "\r\n" + "\r\n" + "\r\n" + "\r\n" + "\r\n" + "\r\n" + "declare \r\n"
						+ "   incCount integer;\r\n" + "   actid integer;\r\n" + "   \r\n"
						+ "   newcreatedby character varying;\r\n" + "   newupdatedby character varying;\r\n"
						+ "   newdeviationmesg character varying;\r\n" + "   oldseparatedby character varying;\r\n"
						+ "   newseparatedby character varying;\r\n" + "   olddeviationmesg character varying;\r\n"
						+ "   newstudyperiodmasterid  character varying;\r\n"
						+ "   oldstudyperiodmasterid  character varying;\r\n"
						+ "   newdiviatnmsgid  character varying;\r\n" + "   olddiviatnmsgid  character varying;\r\n"
						+ "   newsampletimepontid  character varying;\r\n"
						+ "   oldsampletimepontid  character varying;\r\n" + "   newcollectedby  character varying;\r\n"
						+ "   oldcollectedby character varying;\r\n" + "   newsmpldevcmnts  character varying;\r\n"
						+ "   oldsmpldevcmnts  character varying;\r\n" + "   newsign character varying;\r\n"
						+ "   oldsign character varying;\r\n" + "   newtimepoint character varying;\r\n"
						+ "   oldtimepoint character varying;\r\n" + "BEGIN\r\n"
						+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
						+ " SELECT sign FROM bedc.study_sample_time_points where sampletimepointid = new.sampletimepointid into newsign;\r\n"
						+ " SELECT timepoint FROM bedc.study_sample_time_points where sampletimepointid = new.sampletimepointid into newtimepoint;\r\n"
						+ "  newsampletimepontid := newsign|| ' ' || newtimepoint;  \r\n"
						+ " SELECT sign FROM bedc.study_sample_time_points where sampletimepointid = old.sampletimepointid into oldsign;\r\n"
						+ " SELECT timepoint FROM bedc.study_sample_time_points where sampletimepointid = old.sampletimepointid into oldtimepoint;\r\n"
						+ "  oldsampletimepontid := oldsign || ' ' || oldtimepoint;  \r\n" + "\r\n" + " \r\n"
						+ " select user_name from bedc.epk_user_master where userid =new.createdby into newcreatedby;\r\n"
						+ " select user_name from bedc.epk_user_master where userid =new.updatedby into newupdatedby;\r\n"
						+ "IF (old.subjectsamplecollectiondataid IS NULL) THEN\r\n"
						+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ "VALUES (newcreatedby,'CREATE',new.subjectsamplecollectiondataid,'epk_subject_sample_collection_time_points_data',null,new.subjectid,null,'epk_subject_sample_collection_time_points_data_creation',new.createdon)RETURNING id INTO incCount; \r\n"
						+ "IF (new.actualtime IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.actualtime','',new.actualtime,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','actualtime');\r\n"
						+ "END IF;\r\n" + "IF (new.deviation IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.deviation','',new.deviation,'static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','deviation');\r\n"
						+ "END IF;\r\n" +

						"IF (new.modeofcollection IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.modeofcollection','',new.modeofcollection,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','endtime');\r\n"
						+ "END IF;\r\n" + "IF (new.scheduletime IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.scheduletime','',new.scheduletime,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','scheduletime');\r\n"
						+ "END IF;\r\n" + "IF (new.subjectscantime IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.subjectscantime','',new.subjectscantime,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','starttime');\r\n"
						+ "END IF;\r\n" + "IF (new.submissiontime IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.submissiontime','',new.submissiontime,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','submissiontime');\r\n"
						+ "END IF;\r\n" + "IF (new.vacutinerscantime IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('vacutinerscantime','',new.vacutinerscantime,'Dynamic',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','vacutinerscantime');\r\n"
						+ "END IF;\r\n" + "IF (new.vialactualtime IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('vialactualtime','',new.vialactualtime,'Dynamic',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','vialactualtime');\r\n"
						+ "END IF;\r\n"
						+ "SELECT user_name FROM bedc.epk_user_master where userid = new.collectedby into newcollectedby;\r\n"
						+ "IF (new.collectedby IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.collectedby','',newcollectedby,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','collectedby');\r\n"
						+ "END IF;\r\n"
						+ "SELECT message FROM bedc.deviation_message where deviationmessageid = new.diviationmessageid into newdiviatnmsgid;\r\n"
						+ "IF (new.diviationmessageid IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('diviationmessageid','',diviationmessageid,'Dynamic',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','diviationmessageid');\r\n"
						+ "END IF;\r\n" + "IF (new.sampletimepointid IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.sampletimepointid','',newsampletimepontid,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','sampletimepointid');\r\n"
						+ "END IF;\r\n"
						+ "SELECT user_name FROM bedc.epk_user_master where userid = new.separatedby into newseparatedby;\r\n"
						+ "IF (new.separatedby IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.separatedby','',newseparatedby,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','separatedby');\r\n"
						+ "END IF;\r\n"
						+ "SELECT periodname FROM bedc.study_period_master where periodid = new.studyperiodmasterid into newstudyperiodmasterid;\r\n"
						+ "IF (new.studyperiodmasterid IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.studyperiodmasterid','',newstudyperiodmasterid,'Dynamic',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','studyperiodmasterid');\r\n"
						+ "END IF;\r\n"
						+ "SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.studyperiodmasterid into newdeviationmesg;\r\n"
						+ "IF (new.diviationmessageid IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('diviationmessage','',newdeviationmesg,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','diviationmessage');\r\n"
						+ "END IF;\r\n"
						+ "SELECT message FROM bedc.deviation_message where deviationmessageid = new.sample_dev_comments into newsmpldevcmnts;\r\n"
						+ "IF (new.sample_dev_comments IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.sample_dev_comments','',newsmpldevcmnts,'Static',null,null,new.subjectid,' ',newcreatedby,new.createdon,incCount,'epk_subject_sample_collection_time_points_data','audit_insert','submissiontime');\r\n"
						+ "END IF;\r\n" + "\r\n" + "RETURN new;\r\n" + "\r\n"
						+ "ELSE IF (new.subjectsampelcollectiondataid =old.subjectsampelcollectiondataid) THEN\r\n"
						+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ "VALUES (newupdatedby,'UPDATE',new.subjectsampelcollectiondataid,'epk_subject_meals_time_points_data',null,new.subjectid,null,'epk_subject_sample_collection_time_points_data',new.updatedon)RETURNING id INTO incCount;\r\n"
						+ "IF nullif(old.actualtime,null) <> NULLIF(new.actualtime,null) or new.actualtime is not null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.actualtime',old.actualtime,new.actualtime,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','actualtime');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.deviation,'') <> NULLIF(new.deviation,'') or new.deviation is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.deviation',old.deviation,new.deviation,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','deviation');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.diviationMessageId,null) <> NULLIF(new.diviationMessageId,null) or new.diviationMessageId is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.message',old.message,new.message,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','endon');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.modeofcollection,null) <> NULLIF(new.modeofcollection,null) or new.modeofcollection is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.modeofcollection',old.modeofcollection,new.modeofcollection,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','endtime');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.scheduletime,null) <> NULLIF(new.scheduletime,null) or new.scheduletime is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.scheduletime','',new.scheduletime,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','scheduletime');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.subjectsscantime,null) <> NULLIF(new.subjectsscantime,null) or new.subjectsscantime is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.subjectsscantime',old.starttime,new.starttime,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','subjectsscantime');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.submissiontime,null) <> NULLIF(new.submissiontime,null) or new.submissiontime is not null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.submissiontime',old.submissiontime,new.submissiontime,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','submissiontime');\r\n"
						+ "END IF;\r\n" + "\r\n"
						+ "IF nullif(old.vacutinerscantime,null) <> NULLIF(new.vacutinerscantime,null) or new.vacutinerscantime is not null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.vacutinerscantime',old.vacutinerscantime,new.vacutinerscantime,'Static',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','vacutinerscantime');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.vialactualtime,null) <> NULLIF(new.vialactualtime,null) or new.vialactualtime is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('vialactualtime',old.vialactualtime,new.vialactualtime,'Dynamic',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','vialactualtime');\r\n"
						+ "END IF;\r\n"
						+ "SELECT user_name FROM bedc.epk_user_master where userid = new.collectedby into newcollectedby;\r\n"
						+ "SELECT user_name FROM bedc.epk_user_master where userid = old.collectedby into oldcollectedby;\r\n"
						+ "IF nullif(old.collectedby,null) <> NULLIF(new.collectedby,null) or new.collectedby is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('collectedby',oldcollectedby,newcollectedby,'Dynamic',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','collectedy');\r\n"
						+ "END IF;\r\n"
						+ "SELECT periodname FROM bedc.study_period_master where periodid = new.studyperiodmasterid into newstudyperiodmasterid;\r\n"
						+ "SELECT periodname FROM bedc.study_period_master where periodid = old.studyperiodmasterid into oldstudyperiodmasterid;\r\n"
						+ "IF nullif(old.diviationmessageid,null) <> NULLIF(new.diviationmessageid,null) or new.diviationmessageid is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('diviationmessageid',oldstudyperiodmasterid,newstudyperiodmasterid,'Dynamic',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','diviationmessageid');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.sampletimepointid,null) <> NULLIF(new.sampletimepointid,null) or new.sampletimepointid is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('sampletimepointid',oldsampletimepontid,newsampletimepontid,'Dynamic',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','sampletimepointid');\r\n"
						+ "END IF;\r\n"
						+ "SELECT user_name FROM bedc.epk_user_master where userid = new.separatedby into newseparatedby;\r\n"
						+ "SELECT user_name FROM bedc.epk_user_master where userid = old.separatedby into oldseparatedby;\r\n"
						+ "IF nullif(old.separatedby,null) <> NULLIF(new.separatedby,null) or new.separatedby is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('separatedby',oldseparatedby,newseparatedby,'Dynamic',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','separatedby');\r\n"
						+ "END IF;\r\n"
						+ "SELECT periodname FROM bedc.study_period_master where periodid = new.studyperiodmasterid into newstudyperiodmasterid;\r\n"
						+ "SELECT periodname FROM bedc.study_period_master where periodid = old.studyperiodmasterid into oldstudyperiodmasterid;\r\n"
						+ "IF nullif(old.studyperiodmasterid,'') <> NULLIF(new.studyperiodmasterid,'') or new.studyperiodmasterid is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('studyperiodmasterid',oldstudyperiodmasterid,newstudyperiodmasterid,'Dynamic',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','studyperiodmasterid');\r\n"
						+ "END IF;\r\n" + "\r\n" +

						"SELECT fieldname FROM bedc.form_static_data where periodid = new.studyperiodmasterid into newdeviationmesg;\r\n"
						+ "SELECT fieldname FROM bedc.form_static_data where periodid = old.studyperiodmasterid into olddeviationmesg;\r\n"
						+ "\r\n"
						+ "IF nullif(old.diviatiomessage,'') <> NULLIF(new.diviatiomessage,'') or new.diviatiomessage is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('diviatiomessage',olddeviationmesg,newdeviationmesg,'Dynamic',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','diviatiomessage');\r\n"
						+ "END IF;\r\n"
						+ "SELECT message FROM bedc.deviation_message where periodid = new.sample_dev_comments into newsmpldevcmnts;\r\n"
						+ "SELECT message FROM bedc.deviation_message where periodid = old.sample_dev_comments into oldsmpldevcmnts;\r\n"
						+ "IF nullif(old.sample_dev_comments,'') <> NULLIF(new.sample_dev_comments,'') or new.sample_dev_comments is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('sample_dev_comments',oldsmpldevcmnts,oldsmpldevcmnts,'Dynamic',null,null,new.subjectid,' ',newupdatedby,new.updatedon,incCount,'epk_subject_sample_collection_time_points_data','audit_update','sample_dev_comments');\r\n"
						+ "END IF;\r\n" + "\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
						+ "$$ LANGUAGE 'plpgsql';");
		stmt.execute("CREATE TRIGGER epk_subject_sample_collection_timepoints_data_trgr"
				+ "   AFTER INSERT OR UPDATE  OF subjectsamplecollectiondataid,createdon,updatereason,updatedon,actualtime,deviation,diviationMessageId,modeofcollection,"
				+ "scheduletime,subjectscantime,submissiontime,vacutinerscantime,vialactualtime,createdby,updatedby,collectedby,"
				+ "sampletimepointid,separatedby,studyperiodmasterid,subjectid ON bedc.epk_subject_sample_collection_timepoints_data\r\n"
				+ " FOR EACH ROW\r\n" + "  EXECUTE FUNCTION bedc.epk_subject_sample_collection_timepoints_data_fun();");
	}

	private void generateEpkSubjectTimePointVitalTest(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.epk_subject_time_point_vital_test_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "  newTestId character varying;\r\n" + "  oldTestId character varying;\r\n"
				+ "  newUpdatedBy character varying;\r\n" + "  oldUpdatedBy character varying;\r\n"
				+ "  newCreatedBy character varying;\r\n" + "  oldCreatedBy character varying;\r\n"
				+ "  newVitalTimePointId character varying;\r\n" + "  oldVitalTimePointId character varying;\r\n"
				+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n" + " select count(*) from bedc.audit_log into actid;\r\n"
				+ " actid := actid +1;\r\n" + " IF (old.subjecttimepointvtialtestsid IS NULL) THEN\r\n" + // INSERT
																											// TRIGGER
				" INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " VALUES (new.createdby,'CREATE',new.subjecttimepointvtialtestsid,'epk_subject_time_point_vital_test',null,null,null,'epk_subject_time_point_vital_test_creation',new.createdon) RETURNING id INTO incCount; \r\n"
				+ " IF (new.updatereason IS NOT NULL) THEN \r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.updatereason','',new.updatereason,'Static',null,null,null,'',new.createdby,new.createdon,incCount,'epk_subject_time_point_vital_test','audit_insert','updatereason');\r\n"
				+ " END IF;\r\n" + " IF (new.maximum IS NOT NULL) THEN \r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.maximum','',new.maximum,'Static',null,null,null,'',new.createdby,new.createdon,incCount,'epk_subject_time_point_vital_test','audit_insert','maximum');\r\n"
				+ "  END IF;\r\n" + " IF (new.rangerequired IS NOT NULL) THEN \r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.rangerequired','',new.rangerequired,'Static',null,null,null,'',new.createdby,new.createdon,incCount,'epk_subject_time_point_vital_test','audit_insert','rangerequired');\r\n"
				+ " END IF;\r\n" + "  IF (new.subjectvitaltimepointsid IS NOT NULL) THEN \r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.subjectvitaltimepointsid','',new.subjectvitaltimepointsid,'Dynamic',null,null,null,'',new.createdby,new.createdon,incCount,'epk_subject_time_point_vital-test','audit_insert','subjectvitaltimepointsid');\r\n"
				+ " END IF;\r\n" + " IF (new.testid IS NOT NULL) THEN \r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.testid','',new.testid,'Dynamic',null,null,null,'',new.createdby,new.createdon,incCount,'epk_subject_time_point_vital_test','audit_insert','testid');\r\n"
				+ " END IF;\r\n" + " IF (new.timepointvitaltestsid IS NOT NULL) THEN \r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.timepointvitaltestsid','',new.timepointvitaltestsid,'Dynamic',null,null,null,'',new.createdby,new.createdon,incCount,'epk_subject_time_point_vital_test','audit_insert','timepointvitaltestsid');\r\n"
				+ " END IF;\r\n" + " IF (new.vitaltimepointid IS NOT NULL) THEN \r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.vitaltimepointid','',new.vitaltimepointid,'Dynamic',null,null,null,'',new.createdby,new.createdon,incCount,'epk_subject_time_point_vital_test','audit_insert','vitaltimepointid');\r\n"
				+ " END IF;\r\n" + "RETURN new;\r\n"
				+ "ELSE IF (new.subjecttimepointvtialtestsid=old.subjecttimepointvtialtestsid) THEN\r\n" + // UPDATE
																											// TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updatedby,'UPDATE',new.subjecttimepointvtialtestsid,'epk_subject_time_point_vital-test',null,null,null,'epk_subject_time_point_vital-test_updation',new.updatedon)RETURNING id INTO incCount; \r\n"
				+ " IF NULLIF(old.updatereason,'') <> NULLIF(new.updatereason,'') or new.updatereason is null THEN \r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.updatereason','',new.updatereason,'Static',null,null,null,'',new.updatedby,new.updatedon,incCount,'epk_subject_time_point_vital_test','audit_update','updatereason');\r\n"
				+ " END IF; \r\n"
				+ " IF NULLIF(old.maximum,'') <> NULLIF(new.maximum,'') or new.maximum is null THEN \r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.maximum','',new.maximum,'Static',null,null,null,'',new.updatedby,new.updatedon,incCount,'epk_subject_time_point_vital_test','audit_update','maximum');\r\n"
				+ " END IF;\r\n"
				+ "  IF NULLIF(old.minimum,'') <> NULLIF(new.minimum,'') or new.minimum is null THEN \r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.minimum','',new.minimum,'Static',null,null,null,'',new.updatedby,new.updatedon,incCount,'epk_subject_time_point_vital_test','audit_update','maximum');\r\n"
				+ " END IF;\r\n"
				+ " IF cast(old.rangerequired as varchar) <> cast(new.rangerequired as varchar) or new.rangerequired is null then	\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.rangerequired','',new.rangerequired,'Static',null,null,null,'',new.updatedby,new.updatedon,incCount,'epk_subject_time_point_vital_test','audit_update','rangerequired');\r\n"
				+ " END IF;\r\n"
				+ " IF NULLIF(old.vitaltestvalue,'') <> NULLIF(new.vitaltestvalue,'') or new.vitaltestvalue is null THEN 	\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.vitaltestvalue','',new.vitaltestvalue,'Static',null,null,null,'',new.updatedby,new.updatedon,incCount,'epk_subject_time_point_vital_test','audit_update','vitaltestvalue');\r\n"
				+ " END IF;\r\n"
				+ " IF NULLIF(old.subjectvitaltimepointsid,NULL) <> NULLIF(new.subjectvitaltimepointsid,NULL) or new.subjectvitaltimepointsid is null THEN 	\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.subjectvitaltimepointsid','',new.subjectvitaltimepointsid,'Dynamic',null,null,null,'',new.updatedby,new.updatedon,incCount,'epk_subject_time_point_vital_test','audit_update','subjectvitaltimepointsid');\r\n"
				+ " END IF;\r\n"
				+ " IF NULLIF(old.testid,NULL) <> NULLIF(new.testid,NULL) or new.testid is null THEN 	\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.testid','',new.testid,'Dynamic',null,null,null,'',new.updatedby,new.updatedon,incCount,'epk_subject_time_point_vital_test','audit_update','testid');\r\n"
				+ " END IF;\r\n"
				+ " IF NULLIF(old.timepointvitaltestsid,NULL) <> NULLIF(new.timepointvitaltestsid,NULL) or new.timepointvitaltestsid is null THEN 	\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.timepointvitaltestsid','',new.timepointvitaltestsid,'Dynamic',null,null,null,'',new.updatedby,new.updatedon,incCount,'epk_subject_time_point_vital_test','audit_update','timepointvitaltestsid');\r\n"
				+ " END IF;\r\n"
				+ " IF NULLIF(old.vitaltimepointid,NULL) <> NULLIF(new.vitaltimepointid,NULL) or new.vitaltimepointid is null THEN 	\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.vitaltimepointid','',new.vitaltimepointid,'Dynamic',null,null,null,'',new.updatedby,new.updatedon,incCount,'epk_subject_time_point_vital_test','audit_update','vitaltimepointid');\r\n"
				+ " END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER epk_subject_time_point_vital_test_trgr"
				+ "   AFTER INSERT OR UPDATE  OF subjecttimepointvtialtestsid,createdon,updatereason,updatedon,maximum,minimum,rangerequired,vitaltestvalue,createdby,updatedby,subjectvitaltimepointsid,testid,timepointvitaltestsid,vitaltimepointid  ON bedc.epk_subject_time_point_vital_test\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.epk_subject_time_point_vital_test_fun();");

	}

	private void generateEpkSubjectVitalTimePointsData(Connection con, Statement stmt) throws Exception {
		stmt.execute(
				"CREATE OR REPLACE FUNCTION bedc.epk_subject_vital_time_points_data_fun() RETURNS TRIGGER AS $$\r\n"
						+ "\r\n" + "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
						+ "  newcreateduser character varying;\r\n" + "  oldcreateduser character varying;\r\n"
						+ "  newUpdatedBy character varying;\r\n" + "  oldUpdatedBy character varying;\r\n"
						+ "  newCollectedby character varying;\r\n" + "  oldCollectedby character varying;\r\n"
						+ "  newVolunteerId character varying;\r\n" + "  oldVolunteerId character varying;\r\n"
						+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
						+ " incCount := incCount +1;\r\n" + " select count(*) from bedc.audit_log into actid;\r\n"
						+ " actid := actid +1;\r\n" + " IF (old.subjectvitaltimepointsdataid IS NULL) THEN\r\n"
						+ " INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ " VALUES (new.createdby,'CREATE',new.subjectvitaltimepointsdataid,'epk_subject_vital_time_points_data',new.studyid,new.subjectid,null,'epk_subject_time_points_data_creation',new.createdon)RETURNING id INTO incCount; \r\n"
						+ " IF (new.updatereason IS NOT NULL) THEN actid := actid +1;	\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.updatereason','',new.updatereason,'Static',new.studyid,null,new.subjectid,new.comments,new.createdby,new.createdon,incCount,'epk_subject_vital_time_points_data','audit_insert','updatereason');\r\n"
						+ " END IF;\r\n" + " IF (new.actualtime IS NOT NULL) THEN actid := actid +1;	\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.actualtime','',new.actualtime,'Static',new.studyid,null,new.subjectid,new.comments,new.createdby,new.createdon,incCount,'epk_subject_vital_time_points_data','audit_insert','actualtime');\r\n"
						+ "  END IF;\r\n" + " IF (new.deviation IS NOT NULL) THEN actid := actid +1;	\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.deviation','',new.deviation,'Static',new.studyid,null,new.subjectid,new.comments,new.createdby,new.createdon,incCount,'epk_subject_vital_time_points_data','audit_insert','deviation');\r\n"
						+ " END IF;\r\n" + " IF (new.endtime IS NOT NULL) THEN actid := actid +1;	\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.endtime','',new.endtime,'Static',new.studyid,null,new.subjectid,new.comments,new.createdby,new.createdon,incCount,'epk_subject_vital_time_points_data','audit_insert','endtime');\r\n"
						+ " END IF;\r\n" + " IF (new.message IS NOT NULL) THEN actid := actid +1;	\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.message','',new.message,'Static',new.studyid,null,new.subjectid,new.comments,new.createdby,new.createdon,incCount,'epk_subject_vital_time_points_data','audit_insert','message');\r\n"
						+ " END IF;\r\n" + " IF (new.scheduletime IS NOT NULL) THEN actid := actid +1;	\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.scheduletime','',new.scheduletime,'Static',new.studyid,null,new.subjectid,new.comments,new.createdby,new.createdon,incCount,'epk_subject_vital_time_points_data','audit_insert','scheduletime');\r\n"
						+ " END IF;\r\n" + " IF (new.starttime IS NOT NULL) THEN actid := actid +1;	\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.starttime','',new.starttime,'Static',new.studyid,null,new.subjectid,new.comments,new.createdby,new.createdon,incCount,'epk_subject_vital_time_points_data','audit_insert','starttime');\r\n"
						+ " END IF;\r\n" + " IF (new.subjectscantime IS NOT NULL) THEN actid := actid +1;	\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.subjectscantime','',new.subjectscantime,'Static',new.studyid,null,new.subjectid,new.comments,new.createdby,new.createdon,incCount,'epk_subject_vital_time_points_data','audit_insert','subjectscantime');\r\n"
						+ " END IF;\r\n" + " IF (new.submissiontime IS NOT NULL) THEN actid := actid +1;	\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.submissiontime','',new.submissiontime,'Static',new.studyid,null,new.subjectid,new.comments,new.createdby,new.createdon,incCount,'epk_subject_vital_time_points_data','audit_insert','submissiontime');\r\n"
						+ " END IF;\r\n" + " IF (new.collectedby IS NOT NULL) THEN actid := actid +1;	\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.collectedby','',new.collectedby,'Dynamic',new.studyid,null,new.subjectid,new.comments,new.createdby,new.createdon,incCount,'epk_subject_vital_time_points_data','audit_insert','collectedby');\r\n"
						+ " END IF;\r\n" + " IF (new.periodid IS NOT NULL) THEN actid := actid +1;	\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.periodName','',new.periodid,'Static',new.studyid,null,new.subjectid,new.comments,new.createdby,new.createdon,incCount,'epk_subject_vital_time_points_data','audit_insert','periodid');\r\n"
						+ " END IF;\r\n" + " IF (new.timepointid IS NOT NULL) THEN actid := actid +1;	\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.time_point','',new.timepointid,'Static',new.studyid,null,new.subjectid,new.comments,new.createdby,new.createdon,incCount,'epk_subject_vital_time_points_data','audit_insert','timepointid');\r\n"
						+ " END IF;\r\n" + "RETURN new;\r\n"
						+ "ELSE IF (new.subjectvitaltimepointsdataid=old.subjectvitaltimepointsdataid) THEN\r\n"
						+ " INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ " VALUES (new.updatedby,'UPDATE',new.subjectvitaltimepointsdataid,'epk_subject_vital_time_points_data',new.studyid,new.subjectid,null,'epk_subject_time_point_vital_test_data_updation',new.updatedon)RETURNING id INTO incCount; \r\n"
						+ "IF NULLIF(old.updatereason,'') <> NULLIF(new.updatereason,'') or new.updatereason is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.updatereason','',new.updatereason,'Static',new.studyid,null,new.subjectid,new.comments,new.updatedby,new.updatedon,incCount,'epk_subject_vital_time_points_data','audit_update','updatereason');\r\n"
						+ "END IF;\r\n"
						+ "IF NULLIF(old.actualtime,NULL) <> NULLIF(new.actualtime,NULL) or new.actualtime is null THEN\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.actualtime','',new.actualtime,'Static',new.studyid,null,new.subjectid,new.comments,new.updatedby,new.updatedon,incCount,'epk_subject_vital_time_points_data','audit_update','actualtime');\r\n"
						+ "END IF;\r\n"
						+ "IF NULLIF(old.deviation,'') <> NULLIF(new.deviation,'') or new.deviation is null THEN\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.deviation','',new.deviation,'Static',new.studyid,null,new.subjectid,new.comments,new.updatedby,new.updatedon,incCount,'epk_subject_vital_time_points_data','audit_update','deviation');\r\n"
						+ "END IF;\r\n"
						+ "IF NULLIF(old.endtime,NULL) <> NULLIF(new.endtime,NULL) or new.endtime is null THEN\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.endtime','',new.endtime,'Static',new.studyid,null,new.subjectid,new.comments,new.updatedby,new.updatedon,incCount,'epk_subject_vital_time_points_data','audit_update','endtime');\r\n"
						+ "END IF;\r\n"
						+ "IF NULLIF(old.message,'') <> NULLIF(new.message,'') or new.message is null THEN\r\n"
						+ " INSERT INTO bedc.audit_log (id,field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES (actid,'label.message','',new.message,'Static',new.studyid,null,new.subjectid,new.comments,new.updatedby,new.updatedon,incCount,'epk_subject_vital_time_points_data','audit_update','message');\r\n"
						+ "END IF;\r\n"
						+ "IF NULLIF(old.scheduletime,NULL) <> NULLIF(new.scheduletime,NULL) or new.scheduletime is null THEN\r\n"
						+ " INSERT INTO bedc.audit_log (id,field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES (actid,'label.scheduletime','',new.scheduletime,'Static',new.studyid,null,new.subjectid,new.comments,new.updatedby,new.updatedon,incCount,'epk_subject_vital_time_points_data','audit_update','scheduletime');\r\n"
						+ "END IF;\r\n"
						+ "IF NULLIF(old.starttime,NULL) <> NULLIF(new.starttime,NULL) or new.starttime is null THEN\r\n"
						+ " INSERT INTO bedc.audit_log (id,field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES (actid,'label.starttime','',new.starttime,'Static',new.studyid,null,new.subjectid,new.comments,new.updatedby,new.updatedon,incCount,'epk_subject_vital_time_points_data','audit_update','starttime');\r\n"
						+ "END IF;\r\n"
						+ "IF NULLIF(old.subjectscantime,NULL) <> NULLIF(new.subjectscantime,NULL) or new.subjectscantime is null THEN\r\n"
						+ " INSERT INTO bedc.audit_log (id,field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES (actid,'label.subjectscantime','',new.subjectscantime,'Static',new.studyid,null,new.subjectid,new.comments,new.updatedby,new.updatedon,incCount,'epk_subject_vital_time_points_data','audit_update','subjectscantime');\r\n"
						+ "END IF;\r\n"
						+ "IF NULLIF(old.submissiontime,NULL) <> NULLIF(new.submissiontime,NULL) or new.submissiontime is null THEN\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.submissiontime','',new.submissiontime,'Static',new.studyid,null,new.subjectid,new.comments,new.updatedby,new.updatedon,incCount,'epk_subject_vital_time_points_data','audit_update','submissiontime');\r\n"
						+ "END IF;\r\n"
						+ "IF NULLIF(old.collectedby,NULL) <> NULLIF(new.collectedby,NULL) or new.collectedby is null THEN\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.collectedby','',new.collectedby,'Static',new.studyid,null,new.subjectid,new.comments,new.updatedby,new.updatedon,incCount,'epk_subject_vital_time_points_data','audit_update','collectedby');\r\n"
						+ "END IF;\r\n"
						+ "IF NULLIF(old.periodid,NULL) <> NULLIF(new.periodid,NULL) or new.periodid is null THEN\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.periodName','',new.periodid,'Static',new.studyid,null,new.subjectid,new.comments,new.updatedby,new.updatedon,incCount,'epk_subject_vital_time_points_data','audit_update','periodid');\r\n"
						+ "END IF;\r\n" + "\r\n" + "\r\n"
						+ "IF NULLIF(old.timepointid,NULL) <> NULLIF(new.timepointid,NULL) or new.timepointid is null THEN\r\n"
						+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ " VALUES ('label.time_point','',new.timepointid,'Static',new.studyid,null,new.subjectid,new.comments,new.updatedby,new.updatedon,incCount,'epk_subject_vital_time_points_data','audit_update','timepointid');\r\n"
						+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n" +

						"$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER epk_subject_vital_time_points_data_trgr"
				+ "   AFTER INSERT OR UPDATE  OF subjectvitaltimepointsdataid,createdon,updatereason,updatedon,actualtime,comments,deviation,endtime,message,scheduletime,starttime,subjectscantime,submissiontime,createdby,updatedby,collectedby,periodid,studyid,subjectid,timepointid  ON bedc.epk_subject_vital_time_points_data\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.epk_subject_vital_time_points_data_fun();");

	}

	private void generateEpkTimePointVitalTest(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.epk_time_point_vital_test_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "  newcreateduser character varying;\r\n" + "  oldcreateduser character varying;\r\n"
				+ "  newUpdatedBy character varying;\r\n" + "  oldUpdatedBy character varying;\r\n"
				+ "  newCollectedby character varying;\r\n" + "  oldCollectedby character varying;\r\n"
				+ "  newVolunteerId character varying;\r\n" + "  oldVolunteerId character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ " IF (old.timepintvitaltestsid IS NULL) THEN\r\n" + // INSERT TRIGGER
				" INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " VALUES (new.createdby,'CREATE',new.timepintvitaltestsid,'epk_time_point_vital_test',null,null,null,'epk_time_point_vital_test_creation',new.createdon)RETURNING id INTO incCount; \r\n"
				+ " IF (new.updatereason IS NOT NULL) THEN actid := actid +1;	\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.updatereason','',new.updatereason,'Static',null,null,null,null,new.createdby,new.createdon,incCount,'epk_time_point_vital_test','audit_insert','updatereason');\r\n"
				+ " END IF;\r\n" + " IF (new.maximum IS NOT NULL) THEN actid := actid +1;	\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.maximum','',new.maximum,'Static',null,null,null,null,new.createdby,new.createdon,incCount,'epk_time_point_vital_test','audit_insert','maximum');\r\n"
				+ "  END IF;\r\n" + " IF (new.minimum IS NOT NULL) THEN actid := actid +1;	\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.minimum','',new.minimum,'Static',null,null,null,null,new.createdby,new.createdon,incCount,'epk_time_point_vital_test','audit_insert','minimum');\r\n"
				+ " END IF;\r\n"
				+ " IF cast(old.rangerequired as varchar) <> cast(new.rangerequired as varchar) or new.rangerequired is null then\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.rangerequired','',new.rangerequired,'Static',null,null,null,null,new.createdby,new.createdon,incCount,'epk_time_point_vital_test','audit_insert','rangerequired');\r\n"
				+ " END IF;\r\n" + " IF (new.testid IS NOT NULL) THEN actid := actid +1;	\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.testid','',new.testid,'Dynamic',null,null,null,null,new.createdby,new.createdon,incCount,'epk_time_point_vital_test','audit_insert','testid');\r\n"
				+ " END IF;\r\n" + " IF (new.vitaltimepointid IS NOT NULL) THEN actid := actid +1;	\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.vitaltimepointid','',new.vitaltimepointid,'Dynamic',null,null,null,null,new.createdby,new.createdon,incCount,'epk_time_point_vital_test','audit_insert','vitaltimepointid');\r\n"
				+ " END IF;\r\n" + "RETURN new;\r\n"
				+ "ELSE IF (new.timepintvitaltestsid=old.timepintvitaltestsid) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updatedby,'UPDATE',new.timepintvitaltestsid,'epk_time_point_vital_test',null,null,null,'epk_time_point_vital_test_updation',new.updatedon)RETURNING id INTO incCount; \r\n"
				+ "IF NULLIF(old.updatereason,'') <> NULLIF(new.updatereason,'') or new.updatereason is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.updatereason','',new.updatereason,'Static',null,null,null,null,new.updatedby,new.updatedon,incCount,'epk_time_point_vital_test','audit_update','updatereason');\r\n"
				+ "END IF;\r\n" + "IF NULLIF(old.maximum,'') <> NULLIF(new.maximum,'') or new.maximum is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.maximum','',new.maximum,'Static',null,null,null,null,new.updatedby,new.updatedon,incCount,'epk_time_point_vital_test','audit_update','maximum');\r\n"
				+ "END IF;\r\n" + "IF NULLIF(old.minimum,'') <> NULLIF(new.minimum,'') or new.minimum is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.minimum','',new.minimum,'Static',null,null,null,null,new.updatedby,new.updatedon,incCount,'epk_time_point_vital_test','audit_update','minimum');\r\n"
				+ "END IF;\r\n"
				+ "IF cast(old.rangerequired as varchar) <> cast(new.rangerequired as varchar) or new.rangerequired is null then\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.rangerequired','',new.rangerequired,'Static',null,null,null,null,new.updatedby,new.updatedon,incCount,'epk_time_point_vital_test','audit_update','rangerequired');\r\n"
				+ "END IF;\r\n" + "IF NULLIF(old.testid,NULL) <> NULLIF(new.testid,NULL) or new.testid is null THEN\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.testid','',new.testid,'Dynamic',null,null,null,null,new.updatedby,new.updatedon,incCount,'epk_time_point_vital_test','audit_update','testid');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.vitaltimepointid,NULL) <> NULLIF(new.vitaltimepointid,NULL) or new.vitaltimepointid is null THEN\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.vitaltimepointid','',new.vitaltimepointid,'Dynamic',null,null,null,null,new.updatedby,new.updatedon,incCount,'epk_time_point_vital_test','audit_update','vitaltimepointid');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER epk_time_point_vital_test_trgr"
				+ "   AFTER INSERT OR UPDATE  OF timepintvitaltestsid,createdon,updatereason,updatedon,maximum,minimum,rangerequired,createdby,updatedby,testid,vitaltimepointid  ON bedc.epk_time_point_vital_test\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.epk_time_point_vital_test_fun();");

	}

	private void generateEpkUserMaster(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.epk_user_master_fun() RETURNS TRIGGER AS $$\r\n" + "\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   activestatusName character varying;\r\n" + "   oldactivestatusName character varying;\r\n"
				+ "   activestudy_idName  character varying;\r\n" + "   oldactivestudy_idName  character varying;\r\n"
				+ "   role_roleidName  character varying;\r\n" + "   oldrole_roleidName  character varying;\r\n"
				+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n" +

				"IF (old.userid IS NULL) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.createdby,'CREATE',new.userid,'epk_user_master',null,null,null,'epk_user_master_creation',new.createdon)RETURNING id INTO incCount; \r\n"
				+ "IF (new.update_reason IS NOT NULL) THEN  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.update_reason','',new.update_reason,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_user_master','audit_insert','update_reason');\r\n"
				+ "END IF;\r\n" + "IF (new.updatedon IS NOT NULL) THEN  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.updatedon','',new.updatedon,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_user_master','audit_insert','updatedon');\r\n"
				+ "END IF;\r\n" +

				"IF (new.full_name IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.full_name','',new.full_name,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_user_master','audit_insert','full_name');\r\n"
				+ "END IF; \r\n" +

				"IF (new.user_name IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.userName','',new.user_name,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_user_master','audit_insert','user_name');\r\n"
				+ "END IF;\r\n" + "IF (new.role_roleid IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.userCrRole','',new.role_roleid,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_user_master','audit_insert','role_roleid');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.userid=old.userid) THEN \r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.createdby,'UPDATE',new.userid,'epk_user_master',null,null,null,'epk_user_master_updation',new.createdon)RETURNING id INTO incCount; \r\n"
				+ "IF NULLIF(old.update_reason,'') <> NULLIF(new.update_reason,'') or new.update_reason is null THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.update_reason',old.update_reason,new.update_reason,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_user_master','audit_update','update_reason');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.updatedon,NULL) <> NULLIF(new.updatedon,NULL) or new.updatedon is null THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.updatedon',old.updatedon,new.updatedon,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_user_master','audit_update','updatedon');\r\n"
				+ "END IF;\r\n" +

				"IF NULLIF(old.full_name,'') <> NULLIF(new.full_name,'') or new.full_name is null THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.full_name',old.full_name,new.full_name,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_user_master','audit_update','full_name');\r\n"
				+ "END IF;\r\n" +

				"IF NULLIF(old.user_name,NULL) <> NULLIF(new.user_name,NULL) or new.user_name is null THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.userName',old.user_name,new.user_name,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_user_master','audit_update','user_name');\r\n"
				+ "END IF; \r\n"
				+ "IF NULLIF(old.role_roleid,NULL) <> NULLIF(new.role_roleid,NULL) or new.role_roleid is null THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.userCrRole',old.role_roleid,new.role_roleid,'Dynamic',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_user_master','audit_update','role_roleid');\r\n"
				+ "END IF; \r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n" +

				"$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER epk_user_master_trgr"
				+ "   AFTER INSERT OR UPDATE  OF userid,createdon,update_reason,updatedon,account_not_disable,account_not_lock,account_expire_date,full_name,password,tranpassword,user_name,createdby,updatedby,role_roleid  ON bedc.epk_user_master\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.epk_user_master_fun();");

	}

	private void generateEpkVitalTest(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.epk_vital_test_fun() RETURNS TRIGGER AS $$\r\n" + "declare \r\n"
				+ "   incCount integer;\r\n" + "   actid integer;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ "IF (old.vitaltestid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.createdby,'CREATE',new.vitaltestid,'epk_vital_test',null,null,null,'epk_vital_test_creation',new.createdon)RETURNING id INTO incCount;  \r\n"
				+ "IF (new.updatereason IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.updatereason','',new.updatereason,'Static',null,null,null,' ',null,new.createdon,incCount,'epk_vital_test','audit_insert','updatereason');\r\n"
				+ "END IF;\r\n"
				+ "IF cast(old.activestatus as varchar) <> cast(new.activestatus as varchar) or new.activestatus is null then\r\n"
				+ "INSERT INTO bedc.audit_log (id,field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES (actid,'label.activestatus','',new.activestatus,'Static',null,null,null,' ',null,new.createdon,incCount,'epk_vital_test','audit_insert','activestatus');\r\n"
				+ "END IF;\r\n" + "IF (new.maximum IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.maximum','',new.maximum,'Static',null,null,null,' ',null,new.createdon,incCount,'epk_vital_test','audit_insert','maximum');\r\n"
				+ "END IF;\r\n" + "IF (new.maximumcondition IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.maximumcondition','',new.maximumcondition,'Static',null,null,null,' ',null,new.createdon,incCount,'epk_vital_test','audit_insert','maximumcondition');\r\n"
				+ "END IF;\r\n" + "IF (new.minimum IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.minimum','',new.minimum,'Static',null,null,null,' ',null,new.createdon,incCount,'epk_vital_test','audit_insert','minimum');\r\n"
				+ "END IF;\r\n" + "IF (new.minimumcondition IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.minimumcondition','',new.minimumcondition,'Static',null,null,null,' ',null,new.createdon,incCount,'epk_vital_test','audit_insert','minimumcondition');\r\n"
				+ "END IF;\r\n" + "IF (new.testname IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.testname','',new.testname,'Static',null,null,null,' ',null,new.createdon,incCount,'epk_vital_test','audit_insert','testname');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.vitaltestid=old.vitaltestid) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updatedby,'UPDATE',new.vitaltestid,'epk_vital_test',null,null,null,'epk_vital_test_updation',new.updatedon)RETURNING id INTO incCount; \r\n"
				+ "IF NULLIF(old.updatereason,'') <> NULLIF(new.updatereason,'') or new.updatereason is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.updatereason',old.updatereason,new.updatereason,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_vital_test','audit_update','updatereason');\r\n"
				+ "END IF;\r\n"
				+ "IF cast(old.activestatus as varchar) <> cast(new.activestatus as varchar) or new.activestatus is null then \r\n"
				+ "INSERT INTO bedc.audit_log (id,field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES (actid,'label.activestatus',old.activestatus,new.activestatus,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_vital_test','audit_update','activestatus');\r\n"
				+ "END IF;\r\n" + "IF NULLIF(old.maximum,'') <> NULLIF(new.maximum,'') or new.maximum is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.maximum',old.maximum,new.maximum,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_vital_test','audit_update','maximum');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.maximumcondition,'') <> NULLIF(new.maximumcondition,'') or new.maximumcondition is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.maximumcondition',old.maximumcondition,new.maximumcondition,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_vital_test','audit_update','maximumcondition');\r\n"
				+ "END IF;\r\n" + "IF NULLIF(old.minimum,'') <> NULLIF(new.minimum,'') or new.minimum is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.minimum',old.minimum,new.minimum,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_vital_test','audit_update','minimum');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.minimumcondition,'') <> NULLIF(new.minimumcondition,'') or new.minimumcondition is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.minimumcondition',old.minimumcondition,new.minimumcondition,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_vital_test','audit_update','minimumcondition');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.testname,'') <> NULLIF(new.testname,'') or new.testname is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.testname',old.testname,new.testname,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_vital_test','audit_update','testname');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER epk_vital_test_trgr"
				+ "   AFTER INSERT OR UPDATE  OF vitaltestid,createdon,updatereason,updatedon,activestatus,maximum,maximumcondition,minimum,minimumcondition,testname,createdby,updatedby  ON bedc.epk_vital_test\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.epk_vital_test_fun();");

	}

	private void generateEpkWorkFlowReviewStages(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.epk_work_flow_review_stages_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   fromrole_idName character varying;\r\n" + "   oldfromrole_idName character varying;\r\n"
				+ "   torole_idName  character varying;\r\n" + "   oldtorole_idName  character varying;\r\n"
				+ "   workflow_idName  character varying;\r\n" + "   oldworkflow_idName  character varying;\r\n"
				+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n" + "IF (old.workflowreviewstagesid IS NULL) THEN\r\n" + // INSERT
																											// TRIGGE
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.createdby,'CREATE',new.workflowreviewstagesid,'epk_work_flow_review_stages',null,null,null,'epk_work_flow_review_stages_creation',new.createdon)RETURNING id INTO incCount; \r\n"
				+ "IF (new.updatereason IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.updatereason','',new.updatereason,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_work_flow_review_stages','audit_insert','updatereason');\r\n"
				+ "END IF;\r\n" + "IF (new.action_perform IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.action_perform','',new.action_perform,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_work_flow_review_stages','audit_insert','action_perform');\r\n"
				+ "END IF;\r\n"
				+ "IF cast(old.active_status as varchar) <> cast(new.active_status as varchar) or new.active_status is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.active_status','',new.active_status,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_work_flow_review_stages','audit_insert','active_status');\r\n"
				+ "END IF;\r\n" + "IF (new.fromrole_id IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.fromrole_id','',new.fromrole_id,'Dynamic',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_work_flow_review_stages','audit_insert','fromrole_id');\r\n"
				+ "END IF;\r\n" + "IF (new.torole_id IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.torole_id','',new.torole_id,'Dynamic',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_work_flow_review_stages','audit_insert','torole_id');\r\n"
				+ "END IF;\r\n" + "IF (new.workflow_id IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.workflow_id','',new.workflow_id,'Dynamic',null,null,null,' ',new.createdby,new.createdon,incCount,'epk_work_flow_review_stages','audit_insert','workflow_id');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n"
				+ "ELSE IF (new.workflowreviewstagesid=old.workflowreviewstagesid) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updatedby,'UPDATE',new.workflowreviewstagesid,'epk_work_flow_review_stages',null,null,null,'epk_work_flow_review_stages_updation',new.updatedon)RETURNING id INTO incCount; \r\n"
				+ "IF NULLIF(old.updatereason,'') <> NULLIF(new.updatereason,'') or new.updatereason is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.updatereason','',new.updatereason,'Static',null,null,null,'',new.createdby,new.createdon,incCount,'epk_work_flow_review_stages','audit_update','updatereason');\r\n"
				+ "END IF;\r\n"
				+ "IF cast(old.active_status as varchar) <> cast(new.active_status as varchar) or new.active_status is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.active_status','',new.active_status,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_work_flow_review_stages','audit_update','active_status');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.fromrole_id,NULL) <> NULLIF(new.fromrole_id,NULL) or new.fromrole_id is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.fromrole_id','',new.fromrole_id,'Dynamic',null,null,null,'',new.updatedby,new.updatedon,incCount,'epk_work_flow_review_stages','audit_update','fromrole_id');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.torole_id,NULL) <> NULLIF(new.torole_id,NULL) or new.torole_id is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.torole_id','',new.torole_id,'Dynamic',null,null,null,'',new.updatedby,new.updatedon,incCount,'epk_work_flow_review_stages','audit_update','torole_id');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.workflow_id,NULL) <> NULLIF(new.workflow_id,NULL) or new.workflow_id is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.workflow_id','',new.workflow_id,'Dynamic',null,null,null,'',new.updatedby,new.updatedon,incCount,'epk_work_flow_review_stages','audit_update','workflow_id');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER epk_work_flow_review_stages_trgr"
				+ "   AFTER INSERT OR UPDATE  OF workflowreviewstagesid,createdon,updatereason,updatedon,action_perform,active_status,createdby,updatedby,fromrole_id,torole_id,workflow_id  ON bedc.epk_work_flow_review_stages\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.epk_work_flow_review_stages_fun();");

	}

	private void generateSubjectVitalTimePoints(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.subject_vital_time_points_fun() RETURNS TRIGGER AS $$\r\n"
				+ "\r\n" + "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ "IF (old.subjectvitaltimepointsid IS NULL) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.created_by,'CREATE',new.subjectvitaltimepointsid,'subject_vital_time_points',new.studyid,null,new.volunteerid,'subject_vital_time_points_creation',new.created_on)RETURNING id INTO incCount;  \r\n"
				+ "IF (new.collectionstatus IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.collectionstatus','',new.collectionstatus,'Static',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','collectionstatus');\r\n"
				+ "END IF;\r\n" + "IF (new.deviationsign IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.deviationsign','',new.deviationsign,'Static',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','deviationsign');\r\n"
				+ "END IF;\r\n" + "IF (new.orthostatic IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.orthostatic','',new.orthostatic,'Static',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','orthostatic');\r\n"
				+ "END IF;\r\n" + "IF (new.scheduledate IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.scheduledate','',new.scheduledate,'Static',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','scheduledate');\r\n"
				+ "END IF;\r\n" + "IF (new.scheduletime IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.scheduletime','',new.scheduletime,'Static',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','scheduletime');\r\n"
				+ "END IF;\r\n" + "IF (new.sign IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.sign','',new.sign,'Static',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','sign');\r\n"
				+ "END IF;\r\n" + "IF (new.subjectno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectno','',new.subjectno,'Static',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','subjectno');\r\n"
				+ "END IF;\r\n" + "IF (new.subjectorder IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectorder','',new.subjectorder,'Static',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','subjectorder');\r\n"
				+ "END IF;\r\n" + "IF (new.timepoint IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepoint','',new.timepoint,'Static',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','timepoint');\r\n"
				+ "END IF;\r\n" + "IF (new.timepointno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointno','',new.timepointno,'Static',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','timepointno');\r\n"
				+ "END IF;\r\n" + "IF (new.windowperiod IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiod','',new.windowperiod,'Static',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','windowperiod');\r\n"
				+ "END IF;\r\n" + "IF (new.windowperiodsign IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiodsign','',new.windowperiodsign,'Static',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','windowperiodsign');\r\n"
				+ "END IF;\r\n" + "IF (new.windowperiodtype IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiodtype','',new.windowperiodtype,'Static',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','windowperiodtype');\r\n"
				+ "END IF;\r\n" + "IF (new.orthostaticposition IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.orthostaticposition','',new.orthostaticposition,'Static',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','orthostaticposition');\r\n"
				+ "END IF;\r\n" + "IF (new.periodid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.periodid','',new.periodid,'Dynamic',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','periodid');\r\n"
				+ "END IF;\r\n" + "IF (new.subjectvitaltimepointsdataid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectvitaltimepointsdataid','',new.subjectvitaltimepointsdataid,'Dynamic',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','subjectvitaltimepointsdataid');\r\n"
				+ "END IF;\r\n" + "IF (new.timepointtype IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointtype','',new.timepointtype,'Dynamic',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','timepointtype');\r\n"
				+ "END IF;\r\n" + "IF (new.treatmentinfoid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatmentinfoid','',new.treatmentinfoid,'Dynamic',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','treatmentinfoid');\r\n"
				+ "END IF;\r\n" + "IF (new.vitaltimepointsid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.vitaltimepointsid','',new.vitaltimepointsid,'Dynamic',new.studyid,new.volunteerid,null,' ',new.created_by,new.created_on,incCount,'subject_vital_time_points','audit_insert','vitaltimepointsid');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n"
				+ "ELSE IF (new.subjectvitaltimepointsid=old.subjectvitaltimepointsid) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.subjectvitaltimepointsid,'subject_vital_time_points',new.studyid,null,new.volunteerid,'subject_vital_time_points_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+ "IF cast(old.collectionstatus as varchar) <> cast(new.collectionstatus as varchar) or new.collectionstatus is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.collectionstatus',old.collectionstatus,new.collectionstatus,'Static',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','collectionstatus');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.deviationsign,NULL) <> NULLIF(new.deviationsign,NULL) or new.deviationsign is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.deviationsign',old.deviationsign,new.deviationsign,'Static',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','deviationsign');\r\n"
				+ "END IF;\r\n"
				+ "IF cast(old.orthostatic as varchar) <> cast(new.orthostatic as varchar) or new.orthostatic is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.orthostatic',old.orthostatic,new.orthostatic,'Static',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','orthostatic');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.scheduledate,NULL) <> NULLIF(new.scheduledate,NULL) or new.scheduledate is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.scheduledate',old.scheduledate,new.scheduledate,'Static',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','scheduledate');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.scheduletime,NULL) <> NULLIF(new.scheduletime,NULL) or new.scheduletime is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.scheduletime',old.scheduletime,new.scheduletime,'Static',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','scheduletime');\r\n"
				+ "END IF;\r\n" + "IF NULLIF(old.sign,NULL) <> NULLIF(new.sign,NULL) or new.sign is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.sign',old.sign,new.sign,'Static',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','sign');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.subjectno,NULL) <> NULLIF(new.subjectno,NULL) or new.subjectno is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectno',old.subjectno,new.subjectno,'Static',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','subjectno');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.subjectorder,0) <> NULLIF(new.subjectorder,0) or new.subjectorder is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectorder',old.subjectorder,new.subjectorder,'Static',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','subjectorder');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.timepoint,NULL) <> NULLIF(new.timepoint,NULL) or new.timepoint is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepoint',old.timepoint,new.timepoint,'Static',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','timepoint');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.timepointno,0) <> NULLIF(new.timepointno,0) or new.timepointno is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointno',old.timepointno,new.timepointno,'Static',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','timepointno');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.windowperiod,0) <> NULLIF(new.windowperiod,0) or new.windowperiod is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiod',old.windowperiod,new.windowperiod,'Static',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','windowperiod');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.windowperiodsign,NULL) <> NULLIF(new.windowperiodsign,NULL) or new.windowperiodsign is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiodsign',old.windowperiodsign,new.windowperiodsign,'Static',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','windowperiodsign');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.windowperiodtype,NULL) <> NULLIF(new.windowperiodtype,NULL) or new.windowperiodtype is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiodtype',old.windowperiodtype,new.windowperiodtype,'Static',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','windowperiodtype');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.orthostaticposition,NULL) <> NULLIF(new.orthostaticposition,NULL) or new.orthostaticposition is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.orthostaticposition',old.orthostaticposition,new.orthostaticposition,'Dynamic',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','orthostaticposition');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.periodid,NULL) <> NULLIF(new.periodid,NULL) or new.periodid is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.periodid',old.periodid,new.periodid,'Dynamic',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','periodid');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.subjectvitaltimepointsdataid,NULL) <> NULLIF(new.subjectvitaltimepointsdataid,NULL) or new.subjectvitaltimepointsdataid is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectvitaltimepointsdataid',old.subjectvitaltimepointsdataid,new.subjectvitaltimepointsdataid,'Dynamic',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','subjectvitaltimepointsdataid');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.timepointtype,NULL) <> NULLIF(new.timepointtype,NULL) or new.timepointtype is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointtype',old.timepointtype,new.timepointtype,'Dynamic',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','timepointtype');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.treatmentinfoid,NULL) <> NULLIF(new.treatmentinfoid,NULL) or new.treatmentinfoid is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatmentinfoid',old.treatmentinfoid,new.treatmentinfoid,'Dynamic',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','treatmentinfoid');\r\n"
				+ "END IF;\r\n"
				+ "IF NULLIF(old.vitaltimepointsid,NULL) <> NULLIF(new.vitaltimepointsid,NULL) or new.vitaltimepointsid is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.vitaltimepointsid',old.vitaltimepointsid,new.vitaltimepointsid,'Dynamic',new.studyid,new.volunteerid,null,' ',new.updated_by,new.updated_on,incCount,'subject_vital_time_points','label.audit_update','vitaltimepointsid');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER subject_vital_time_points_trgr"
				+ "   AFTER INSERT OR UPDATE  OF subjectvitaltimepointsid,collectionstatus,created_on,deviationsign,orthostatic,scheduledate,scheduletime,sign,subjectno,subjectorder,timepoint,timepointno,update_reason,updated_on,windowperiod,windowperiodsign,windowperiodtype,created_by,orthostaticposition,periodid,studyid,subjectvitaltimepointsdataid,timepointtype,treatmentinfoid,updated_by,vitaltimepointsid,volunteerid  ON bedc.subject_vital_time_points\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.subject_vital_time_points_fun();");

	}

	private void generateUserwiseAssignstudiesMaster(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.userwise_assignstudies_master_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "  newSubjectId character varying;\r\n" + "  oldSubjectId character varying;\r\n"
				+ "  newVpcId character varying;\r\n" + "  oldVpcId character varying;\r\n"
				+ "  newRoleId character varying;\r\n" + "  oldRoleId character varying;\r\n"
				+ "  newEformId character varying;\r\n" + "  oldEformId character varying;\r\n"
				+ "  newCreatedBy  character varying;\r\n" + "  oldCreatedBy  character varying;\r\n"
				+ "  newStudyId character varying;\r\n" + "  oldStudyId character varying;\r\n"
				+ "  newReviewStagesId character varying;\r\n" + "  oldReviewStagesId character varying;\r\n"
				+ "  newUpdatedBy character varying;\r\n" + "  oldUpdatedBy character varying;\r\n"
				+ "  newUserId character varying;\r\n" + "  oldUserId character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ " IF (old.userwisestudiesasignmasterid IS NULL) THEN\r\n" + // INSERT TRIGGER
				" INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " VALUES (new.created_by,'CREATE',new.userwisestudiesasignmasterid,'userwise_assignstudies_master',null,null,null,'userwise_assignstudies_master_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+ "IF (new.status IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.status','',new.status,'Static',new.study,null,null,null,new.created_by,new.created_on,incCount,'userwise_assignstudies_master','audit_insert','status');\r\n"
				+ "END IF;\r\n" + "IF (new.study_roles IS NOT NULL) THEN\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.study_roles','',new.study_roles,'Static',new.study,null,null,null,new.created_by,new.created_on,incCount,'userwise_assignstudies_master','audit_insert','study_roles');\r\n"
				+ "END IF;\r\n" + "IF (new.userid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.userid','',new.userid,'Dynamic',new.study,null,null,null,new.created_by,new.created_on,incCount,'userwise_assignstudies_master','audit_insert','userid');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n"
				+ "ELSE IF (new.userwisestudiesasignmasterid=old.userwisestudiesasignmasterid) THEN\r\n" + // UPDATE
																											// TRIGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.userwisestudiesasignmasterid,'userwise_assignstudies_master',new.study,null,null,'userwise_assignstudies_master_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+ "IF cast(old.status as varchar) <> cast(new.status as varchar) or new.status is null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.status',old.status,new.status,'Static',new.study,null,new.subject_id,new.comment,new.updatedby,new.updatedon,incCount,'userwise_assignstudies_master','audit_update','status');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.study_roles,NULL) <> NULLIF(new.study_roles,NULL) or new.study_roles is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.study_roles',old.study_roles,new.study_roles,'Static',new.study,null,null,null,new.updated_by,new.updated_on,incCount,'userwise_assignstudies_master','audit_update','study_roles');\r\n"
				+ "END IF;\r\n" + "IF nullif(old.userid,NULL) <> NULLIF(new.userid,NULL) or new.userid is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.userid',old.userid,new.userid,'Dynamic',new.study,null,null,null,new.updated_by,new.updated_on,incCount,'userwise_assignstudies_master','audit_update','userid');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER userwise_assignstudies_master_trgr"
				+ "   AFTER INSERT OR UPDATE  OF userwisestudiesasignmasterid,created_by,created_on,status,study_roles,update_reason,updated_by,updated_on,study,userid  ON bedc.userwise_assignstudies_master\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.userwise_assignstudies_master_fun();");
	}

	private void generateVialSeparationData(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.vial_separation_data_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + " incCount integer;\r\n" + " audid integer;\r\n" + " createdby character varying;\r\n"
				+ " oldCreatedby character varying;\r\n" + " centrifugationName character varying;\r\n"
				+ " oldCentrifugationName character varying;\r\n" + " loadedVacutainer character varying;\r\n"
				+ " oldLoadedVacutainer character varying;\r\n" + " subjectData character varying;\r\n"
				+ " oldSubjectData character varying;\r\n" + " begin \r\n"
				+ " select count(*) from bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " \r\n" + "\r\n" + " if (old.vialseparationdataid is null) then \r\n"
				+ " insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " values (null,'create',new.vialseparationdataid,'vial_separation_data',null,null,null,'vial_separation_data_creation',null)RETURNING id INTO incCount; \r\n"
				+ " \r\n" + "IF (new.aliquot IS NOT NULL) THEN\r\n"
				+ "    INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "    VALUES ('label.aliquot', '',new.aliquot, null, null, null,' ', null, incCount,null ,'vial_separation_data', 'static', 'audit_insert', 'aliquot');\r\n"
				+ "    END IF;\r\n" + "    \r\n" + " IF (new.scantime IS NOT NULL) THEN\r\n"
				+ "    INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "    VALUES ('label.scantime', '', new.scantime, null, null, null,' ', null, incCount,null ,'vial_separation_data', 'static', 'audit_insert', 'scantime');\r\n"
				+ "    END IF;\r\n" + "    \r\n" + "    IF (new.scaned_by IS NOT NULL) THEN\r\n"
				+ "	select user_name from bedc.epk_user_master where userid=new.scaned_by into createdby;\r\n"
				+ "    INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "    VALUES ('scaned_by', '', createdby, null, null, null,' ', null, incCount,null ,'vial_separation_data', 'dynamic', 'audit_insert', 'scaned_by');\r\n"
				+ "    END IF;\r\n" + "    \r\n" + "    IF (new.centrifugationdatamasterid IS NOT NULL) THEN\r\n"
				+ "	select centrifugesubjects from bedc.epk_centrifugationdatamaster where centrifugationdatamasterid=new.centrifugationdatamasterid into centrifugationName;\r\n"
				+ "    INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "    VALUES ('centrifugationdatamasterid', '', centrifugationName, null, null, null,' ', null, incCount,null ,'vial_separation_data', 'dynamic', 'audit_insert', 'centrifugationdatamasterid');\r\n"
				+ "    END IF;\r\n" + "    \r\n" + "     IF (new.loadedvacutinersincentrifugeid IS NOT NULL) THEN\r\n"
				+ "	select scan_by from bedc.epk_loadedvacutinersincentrifuge where loadedvacutinersincentrifugeid=new.loadedvacutinersincentrifugeid into loadedVacutainer;\r\n"
				+ "    INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "    VALUES ('loadedvacutinersincentrifugeid', '', loadedVacutainer, null, null, null,' ', null, incCount,null ,'vial_separation_data', 'dynamic', 'audit_insert', 'loadedvacutinersincentrifugeid');\r\n"
				+ "    END IF;\r\n" + "    \r\n"
				+ "    IF (new.subjectsamplecollectiontimepointsdataid IS NOT NULL) THEN\r\n"
				+ "	select message from bedc.epk_subject_sample_collection_timepoints_data where subjectsamplecollectiondataid=new.subjectsamplecollectiontimepointsdataid into subjectData;\r\n"
				+ "    INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "    VALUES ('subjectsamplecollectiontimepointsdataid', '', subjectData, null, null, null,' ', null, incCount,null ,'vial_separation_data', 'dynamic', 'audit_insert', 'subjectsamplecollectiontimepointsdataid');\r\n"
				+ "    END IF;\r\n" + "    \r\n" + " return new;\r\n" + " \r\n"
				+ " else if (new.vialseparationdataid = old.vialseparationdataid) then \r\n"
				+ " insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " values (null,'update',new.vialseparationdataid,'vial_separation_data', null,null,null,'vial_separation_data_updation',null)RETURNING id INTO incCount;\r\n"
				+ " \r\n"
				+ " IF nullif(old.scantime,null) <> NULLIF(new.scantime,null) or new.scantime is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.scantime',old.scantime,new.scantime,null,null,null,' ',null,incCount,null,'vial_separation_data','static','audit_update','scantime');\r\n"
				+ " END IF;\r\n" + " \r\n"
				+ " IF nullif(old.aliquot,'') <> NULLIF(new.aliquot,'') or new.aliquot is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.aliquot',old.aliquot,new.aliquot,null,null,null,' ',null,incCount,null,'vial_separation_data','static','audit_update','aliquot');\r\n"
				+ " END IF;\r\n" + "\r\n"
				+ "    IF nullif(old.scaned_by,null) <> NULLIF(new.scaned_by,null) or new.scaned_by is null THEN\r\n"
				+ "    select user_name from bedc.epk_user_master where userid=new.scaned_by into createdby;\r\n"
				+ "    select user_name from bedc.epk_user_master where userid=old.scaned_by into oldCreatedby;\r\n"
				+ "    insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "    values ('scaned_by',oldCreatedby,createdby,null,null,null,' ',null,incCount,null,'vial_separation_data','dynamic','audit_update','scaned_by');\r\n"
				+ "    END IF;\r\n" + "     \r\n"
				+ "      IF nullif(old.centrifugationdatamasterid,null) <> NULLIF(new.centrifugationdatamasterid,null) or new.centrifugationdatamasterid is null THEN\r\n"
				+ "    select centrifugesubjects from bedc.epk_centrifugationdatamaster where centrifugationdatamasterid=new.centrifugationdatamasterid into centrifugationName;\r\n"
				+ "    select centrifugesubjects from bedc.epk_centrifugationdatamaster where centrifugationdatamasterid=old.centrifugationdatamasterid into oldCentrifugationName;\r\n"
				+ "    insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "    values ('centrifugationdatamasterid',oldCentrifugationName,centrifugationName,null,null,null,' ',null,incCount,null,'vial_separation_data','dynamic','audit_update','centrifugationdatamasterid');\r\n"
				+ "    END IF;\r\n" + "    \r\n"
				+ "    IF nullif(old.loadedvacutinersincentrifugeid,null) <> NULLIF(new.loadedvacutinersincentrifugeid,null) or new.loadedvacutinersincentrifugeid is null THEN\r\n"
				+ "    select scan_by from bedc.epk_loadedvacutinersincentrifuge where loadedvacutinersincentrifugeid=new.loadedvacutinersincentrifugeid into loadedVacutainer;\r\n"
				+ "    select scan_by from bedc.epk_loadedvacutinersincentrifuge where loadedvacutinersincentrifugeid=old.loadedvacutinersincentrifugeid into oldLoadedVacutainer;\r\n"
				+ "    insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "    values ('loadedvacutinersincentrifugeid',oldLoadedVacutainer,loadedVacutainer,null,null,null,' ',null,incCount,null,'vial_separation_data','dynamic','audit_update','loadedvacutinersincentrifugeid');\r\n"
				+ "    END IF;\r\n" + "    \r\n"
				+ "    IF nullif(old.subjectsamplecollectiontimepointsdataid,null) <> NULLIF(new.subjectsamplecollectiontimepointsdataid,null) or new.subjectsamplecollectiontimepointsdataid is null THEN\r\n"
				+ "    select message from bedc.epk_subject_sample_collection_timepoints_data where subjectsamplecollectiondataid=new.subjectsamplecollectiontimepointsdataid into subjectData;\r\n"
				+ "    select message from bedc.epk_subject_sample_collection_timepoints_data where subjectsamplecollectiondataid=old.subjectsamplecollectiontimepointsdataid into oldSubjectData;\r\n"
				+ "    insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "    values ('subjectsamplecollectiontimepointsdataid',oldSubjectData,subjectData,null,null,null,' ',null,incCount,null,'vial_separation_data','dynamic','audit_update','subjectsamplecollectiontimepointsdataid');\r\n"
				+ "    END IF;\r\n" + "    \r\n" + " return new;\r\n" + " end if;\r\n" + " end if;\r\n" + " end;"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER vial_separation_data_trgr"
				+ "   AFTER INSERT OR UPDATE  OF vialseparationdataid,aliquot,scaned_by,scantime,centrifugationdatamasterid,loadedvacutinersincentrifugeid,subjectsamplecollectiontimepointsdataid  ON bedc.vial_separation_data\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.vial_separation_data_fun();");

	}

	private void generateWorkFlows(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.work_flows_fun() RETURNS TRIGGER AS $$\r\n" + "declare \r\n"
				+ "	  incCount integer; \r\n" + "     actid integer; \r\n" + "	  newtorole character varying; \r\n"
				+ "	  torole character varying; \r\n" + "	  newfromrole character varying;\r\n"
				+ "	  fromrole character varying; \r\n" +

				"	BEGIN \r\n" + "	 SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ "	 incCount := incCount +1;\r\n" + "	 select count(*) from bedc.audit_log into actid;\r\n"
				+ "	 actid := actid +1; \r\n" + "\r\n" + "	 IF (old.id IS NULL) THEN\r\n"
				+ "	 INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	 VALUES (new.createdby,'CREATE',new.id,'work_flows',null,null,null,'work_flows_creation',new.createdon)RETURNING id INTO incCount;  \r\n"
				+ "	IF (new.action_name IS NOT NULL) THEN\r\n"
				+ "   INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.workFlow','',new.action_name,'Static',null,null,null,'',new.createdby,new.createdon,incCount,'work_flows','audit_insert','action_name'); \r\n"
				+ "	END IF;\r\n" + "	IF (new.work_flow_name IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname) \r\n"
				+ "	VALUES ('label.workFlowName','',new.work_flow_name,'Static',null,null,null,null,new.createdby,new.createdon,incCount,'work_flows','audit_insert','work_flow_name'); \r\n"
				+ "	END IF;\r\n"
				+ "    select role from bedc.epk_role_master where roleid=new.to_role into newtorole;\r\n"
				+ "	IF (new.to_role IS NOT NULL) THEN \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('to_role','',newtorole,'Dynamic',null,null,null,null,new.createdby,new.createdon,incCount,'work_flows','audit_insert','to_role'); \r\n"
				+ "	END IF;\r\n"
				+ "    select role from bedc.epk_role_master where roleid=new.from_role into newfromrole;\r\n"
				+ "	IF (new.from_role IS NOT NULL) THEN \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname) \r\n"
				+ "   VALUES ('from_role','',newfromrole,'Dynamic',null,null,null,null,new.createdby,new.createdon,incCount,'work_flows','audit_insert','from_role'); \r\n"
				+ "   END IF;\r\n" + "	RETURN new; \r\n" +
				// Update
				"	ELSE IF (new.id=old.id) THEN\r\n"
				+ "	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on) \r\n"
				+ "	VALUES (new.updatedby,'UPDATE',new.id,'work_flows',null,null,null,'work_flows_updation',new.updatedon)RETURNING id INTO incCount; \r\n"
				+ "	IF nullif(old.action_name,NULL) <> NULLIF(new.action_name,NULL) or new.action_name is null THEN \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.workFlow',old.action_name,new.action_name,'Static',null,null,null,new.update_reason,new.updatedby,new.updatedon,incCount,'work_flows','audit_update','action_name'); \r\n"
				+ "	END IF; \r\n"
				+ "	IF cast(old.work_flow_name as varchar) <> cast(new.work_flow_name as varchar) or new.work_flow_name is null then \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname) \r\n"
				+ "	VALUES ('label.workFlowName',old.work_flow_name,new.work_flow_name,'Static',null,null,null,new.update_reason,new.updatedby,new.updatedon,incCount,'work_flows','audit_update','work_flow_name'); \r\n"
				+ "	END IF;\r\n"
				+ "    select role from bedc.epk_role_master where roleid=new.to_role into newtorole;\r\n"
				+ "    select role from bedc.epk_role_master where roleid=old.to_role into torole;\r\n"
				+ "	IF nullif(old.from_role,NULL) <> NULLIF(new.from_role,NULL) or new.from_role is null THEN \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('from_role',fromrole,newfromrole,'Dynamic',null,null,null,new.update_reason,new.updatedby,new.updatedon,incCount,'work_flows','audit_update','from_role');\r\n"
				+ "	END IF; \r\n"
				+ "    select role from bedc.epk_role_master where roleid=new.to_role into newfromrole;\r\n"
				+ "    select role from bedc.epk_role_master where roleid=old.to_role into fromrole;\r\n"
				+ "	IF nullif(old.to_role,NULL) <> NULLIF(new.to_role,NULL) or new.to_role is null THEN \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname) \r\n"
				+ "	VALUES ('label.to_role',torole,newtorole,'Dynamic',null,null,null,new.update_reason,new.updatedby,new.updatedon,incCount,'work_flows','audit_update','to_role');\r\n"
				+ "	END IF;\r\n" + "	RETURN new; \r\n" + "	END IF;\r\n" + "	END IF;\r\n" + "	END;"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER work_flows_trgr"
				+ "   AFTER INSERT OR UPDATE  OF action_name,work_flow_name,createdon,update_reason,updatedon,createdby,updatedby,from_role,to_role  ON bedc.work_flows\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.work_flows_fun();");

	}

	private void generateActivityDraftReviewAudit(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.Activity_draft_review_audit_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "incCount integer;\r\n" + "actid integer;\r\n"
				+ "workflowreviewstagesidName   character varying;\r\n"
				+ "oldworkflowreviewstagesidName character varying;\r\n" + "user_idName    character varying;\r\n"
				+ "olduser_idName   character varying;\r\n" + "role_idName      character varying;\r\n"
				+ "oldrole_idName   character varying;\r\n" + "begin\r\n"
				+ "SELECT COUNT(*) FROM bedc.activity_log INTO incCount;\r\n" + "incCount := incCount +1;\r\n"
				+ "select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "	IF (old.id is null) THEN\r\n" + // INSERT TRIGGER
				"	INSERT INTO bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "    VALUES(new.created_by,'CREATE',new.id,'activity_draft_review_audit',null,null,null,'activity_draft_review_audit_creation',new.created_on) RETURNING id INTO incCount;\r\n"
				+ "	IF new.date_of_activity IS NOT NULL THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.date_of_activity','',new.date_of_activity,null,null,null,new.comment,new.created_by,incCount,new.created_on,'activity_draft_review_audit','static','audit_insert','date_of_activity');\r\n"
				+ "	END IF;\r\n" + "	IF new.in_the_flow IS NOT NULL THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.in_the_flow','',new.in_the_flow,null,null,null,new.comment,new.created_by,incCount,new.created_on,'activity_draft_review_audit','static','audit_insert','in_the_flow');\r\n"
				+ "	END IF;\r\n" + "	IF new.projectid IS NOT NULL THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.projectid','',new.projectid,null,null,null,new.comment,new.created_by,incCount,new.created_on,'activity_draft_review_audit','static','audit_insert','projectid');\r\n"
				+ "	END IF;\r\n" + "	IF new.status IS NOT NULL THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.status','',new.status,null,null,null,new.comment,new.created_by,incCount,new.created_on,'activity_draft_review_audit','static','audit_insert','status');\r\n"
				+ "	END IF;\r\n" +

				"	IF new.user_id IS NOT NULL THEN\r\n"
				+ "	SELECT full_name FROM bedc.epk_user_master where userid = new.user_id into user_idName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('userid','',user_idName,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'activity_draft_review_audit','audit_insert','userid');\r\n"
				+ "	END IF;\r\n" + "	IF new.role_id IS NOT NULL THEN\r\n"
				+ "	SELECT role FROM bedc.epk_role_master where roleid = new.role_id into role_idName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('roleid','',role_idName,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'activity_draft_review_audit','audit_insert','roleid');\r\n"
				+ "	END IF;\r\n" +

				"RETURN new;\r\n" + "ELSE IF(new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"	INSERT INTO bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES(new.updated_by,'update',new.id,'activity_draft_review_audit',null,null,null,'activity_draft_review_audit_updation',new.updated_on)\r\n"
				+ "	RETURNING id INTO incCount;\r\n"
				+ "	IF nullif(old.date_of_activity,'') <> NULLIF(new.date_of_activity,'') or new.date_of_activity is null THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.date_of_activity',old.date_of_activity,new.date_of_activity,null,null,null,new.comment,new.updated_by,incCount,new.updated_on,'activity_draft_review_audit','static','audit_update','date_of_activity');\r\n"
				+ "	END IF;\r\n"
				+ "	IF nullif(old.in_the_flow,'') <> NULLIF(new.in_the_flow,'') or new.in_the_flow is null THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.in_the_flow',old.in_the_flow,new.in_the_flow,null,null,null,new.comment,new.updated_by,incCount,new.updated_on,'activity_draft_review_audit','static','audit_update','in_the_flow');\r\n"
				+ "	END IF;\r\n"
				+ "	IF nullif(old.reviewstate,'') <> NULLIF(new.reviewstate,'') or new.reviewstate is null THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.reviewstate',old.reviewstate,new.reviewstate,null,null,null,new.comment,new.updated_by,incCount,new.updated_on,'activity_draft_review_audit','static','audit_update','reviewstate');\r\n"
				+ "	END IF;\r\n"
				+ "	IF nullif(old.status,'') <> NULLIF(new.status,'') or new.status is null THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.status',old.status,new.status,null,null,null,new.comment,new.updated_by,incCount,new.updated_on,'activity_draft_review_audit','static','audit_update','status');\r\n"
				+ "	END IF;\r\n"
				+ "	IF nullif(old.projectid,'') <> NULLIF(new.projectid,'') or new.projectid is null THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.projectid',old.projectid,new.projectid,null,null,null,new.comment,new.updated_by,incCount,new.updated_on,'activity_draft_review_audit','static','audit_update','projectid');\r\n"
				+ "	END IF;\r\n" +

				"	IF nullif(old.user_id,'') <> NULLIF(new.user_id,'') or new.user_id is null THEN\r\n"
				+ "	SELECT full_name FROM bedc.epk_user_master where userid = old.user_id into olduser_idName;\r\n"
				+ "	SELECT full_name FROM bedc.epk_user_master where userid = new.user_id into user_idName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES ('user_id',olduser_idName,user_idName,'Dynamic',null,null,null,new.comment,new.updated_by,new.updated_on,incCount,'activity_draft_review_audit','audit_update','user_id');\r\n"
				+ "	END IF;\r\n"
				+ "	IF nullif(old.role_id,'') <> NULLIF(new.role_id,'') or new.role_id is null THEN\r\n"
				+ "	SELECT role FROM bedc.epk_role_master where roleid = old.role_id into oldrole_idName;\r\n"
				+ "	SELECT role FROM bedc.epk_role_master where roleid = new.role_id into role_idName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES ('roleid',oldrole_idName,role_idName,'Dynamic',null,null,null,new.comment,new.updated_by,new.updated_on,incCount,'activity_draft_review_audit','audit_update','role_id');\r\n"
				+ "	END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER Activity_draft_review_audit_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,comment,created_on,date_of_activity,in_the_flow,projectid,reviewstate,status,update_reason,updated_by,updated_on,created_by,role_id,user_id  ON bedc.Activity_draft_review_audit\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.Activity_draft_review_audit_fun();");

	}

	private void generateControlType(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.control_type_fun() RETURNS TRIGGER AS $$\r\n" + " declare \r\n"
				+ " incCount integer;\r\n" + " audid integer;\r\n" + " begin \r\n"
				+ " select count(*) from bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " if (old.id is null) then \r\n" + // INSERT TRIGGER
				" insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " values (null,'create',new.id,'control_type',null,null,null,'control_type_creation',null)RETURNING id INTO incCount;\r\n"
				+ " IF (new.code IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.code','',new.code,null,null,null,' ',new.created_by,incCount,new.created_on,'control_type','static','audit_insert','code');\r\n"
				+ " END IF;\r\n" + " IF (new.name IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.name','',new.name,null,null,null,' ',new.created_by,incCount,new.created_on,'control_type','static','audit_insert','name');\r\n"
				+ " END IF;\r\n" + " return new;\r\n" + " else if (new.id = old.id) then \r\n" + // UPDATE INSERT
				" insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " values (new.updated_by,'update',new.id,'control_type',null,null,null,'control_type_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+ " IF nullif(old.code,'') <> NULLIF(new.code,'') or new.code is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.code',old.code,new.code,null,null,null,' ',new.updated_by,incCount,new.updated_on,'control_type','static','audit_update','code');\r\n"
				+ " END IF;\r\n" + " IF nullif(old.name,'') <> NULLIF(new.name,'') or new.name is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.name',old.name,new.name,null,null,null,' ',new.updated_by,incCount,new.updated_on,'control_type','static','audit_update','name');\r\n"
				+ " END IF;\r\n" + " return new;\r\n" + " end if;\r\n" + " end if;\r\n" + " end;\r\n" +

				"$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER control_type_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,code,name  ON bedc.control_type\r\n" + "    FOR EACH ROW\r\n"
				+ "    EXECUTE FUNCTION bedc.control_type_fun();");

	}

	private void generateDefaultActivity(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.default_activity_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "incCount integer;\r\n" + "actid integer;\r\n"
				+ "global_activityName   character varying;\r\n" + "oldglobal_activityName character varying;\r\n"
				+ "study_phasesName   character varying;\r\n" + "oldstudy_phasesName  character varying;\r\n"
				+ "newglobalacti  character varying;\r\n" + "oldglobalacti  character varying;\r\n"
				+ "newphace  character varying;\r\n" + "oldphace  character varying;\r\n" +

				"begin\r\n" + "SELECT COUNT(*) FROM bedc.activity_log INTO incCount;\r\n"
				+ "incCount := incCount +1;\r\n" + "SELECT COUNT(*) FROM bedc.audit_log INTO actid;\r\n"
				+ "actid := actid +1;\r\n" + "IF (old.id is null) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES(new.createdby,'CREATE',new.id,'default_activity',null,null,null,'default_activity_creation',new.createdon)RETURNING id INTO incCount;\r\n"
				+ " IF (new.subjectallotment IS NOT NULL) THEN\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ " VALUES('label.subjectallotment','',new.subjectallotment,null,null,null,' ',new.createdby,incCount,new.createdon,'default_activity','static','audit_insert','subjectallotment');\r\n"
				+ " END IF;\r\n" +

				"  IF (new.submitcontrols IS NOT NULL) THEN\r\n"
				+ "  INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "  VALUES('label.submitcontrols','',new.submitcontrols,null,null,null,' ',new.createdby,incCount,new.createdon,'default_activity','static','audit_insert','submitcontrols');\r\n"
				+ "  END IF;\r\n" + "			 IF (new.tableName IS NOT NULL) THEN\r\n"
				+ "			 INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "			 VALUES('label.tablename','',new.tableName,null,null,null,' ',new.createdby,incCount,new.createdon,'default_activity','static','audit_insert','tablename');\r\n"
				+ "			 END IF;\r\n" +

				"			 IF (new.global_activity IS NOT NULL) THEN\r\n"
				+ "	         select name from bedc.global_activity where id= new.global_activity into newglobalacti;  \r\n"
				+ "			 INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "			 VALUES('label.global_activity','',newglobalacti,null,null,null,' ',new.createdby,incCount,new.createdon,'default_activity','static','audit_insert','global_activity');\r\n"
				+ "			 END IF;\r\n" + "			 \r\n" + "			 IF (new.study_phases IS NOT NULL) THEN\r\n"
				+ "	         select name from bedc.study_phases where id= new.study_phases into newphace;  \r\n"
				+ "			 INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "			 VALUES('label.study_phases','',newphace,null,null,null,' ',new.createdby,incCount,new.createdon,'default_activity','static','audit_insert','study_phases');\r\n"
				+ "			 END IF;\r\n" +

				"RETURN new;\r\n" + "ELSE IF(new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"             INSERT INTO bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on,action,databasefieldname)\r\n"
				+ "             VALUES(new.updated_by,'update',new.id,'default_activity',null,null,null,'default_activity_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+

				"			 IF nullif(old.subjectallotment,'') <> NULLIF(new.subjectallotment,'') or new.subjectallotment is null THEN\r\n"
				+ "			 INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "			 VALUES('label.subjectallotment',old.subjectallotment,new.subjectallotment,null,null,null,' ',new.updated_by,incCount,new.updated_on,'default_activity','static','audit_update','subjectallotment');\r\n"
				+ "			 END IF;\r\n"
				+ "			 IF nullif(old.submitcontrols,'') <> NULLIF(new.submitcontrols,'') or new.submitcontrols is null THEN\r\n"
				+ "			 INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "			 VALUES('label.submitcontrols',old.submitcontrols,new.submitcontrols,null,null,null,' ',new.updated_by,incCount,new.updated_on,'default_activity','static','audit_update','submitcontrols');\r\n"
				+ "			 END IF;\r\n"
				+ "			 IF nullif(old.tableName,'') <> NULLIF(new.tableName,'') or new.tableName is null THEN\r\n"
				+ "			 INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "			 VALUES('label.tablename',old.tableName,new.tableName,null,null,null,' ',new.updated_by,incCount,new.updated_on,'default_activity','static','audit_update','tablename');\r\n"
				+ "			 END IF;\r\n"
				+ "			 IF nullif(old.global_activity,'') <> NULLIF(new.global_activity,'') or new.global_activity is null THEN\r\n"
				+ "	         select name from bedc.global_activity where id= new.global_activity into newglobalacti;  \r\n"
				+ "	         select name from bedc.global_activity where id= old.global_activity into oldglobalacti;  \r\n"
				+ "			 INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "			 VALUES('label.global_activity',oldglobalacti,newglobalacti,null,null,null,' ',new.updated_by,incCount,new.updated_on,'default_activity','static','audit_update','global_activity');\r\n"
				+ "			 END IF;\r\n"
				+ "			 IF nullif(old.study_phases,'') <> NULLIF(new.study_phases,'') or new.study_phases is null THEN\r\n"
				+ "	         select name from bedc.study_phases where id= new.study_phases into newphace;  \r\n"
				+ "	         select name from bedc.study_phases where id= old.study_phases into oldphace;  \r\n"
				+ "			 INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "			 VALUES('label.study_phases',oldphace,newphace,null,null,null,' ',new.updated_by,incCount,new.updated_on,'default_activity','static','audit_update','study_phases');\r\n"
				+ "			 END IF;\r\n" +

				"RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n" + "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER default_activity_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,createdby,createdon,subjectallotment,submitcontrols,tablename,update_reason,updated_by,updated_on,global_activity,study_phases  ON bedc.default_activity\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.default_activity_fun();");
	}

	private void generateDefaultActivityParameters(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.default_activity_parameters_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "incCount integer;\r\n" + "actid integer;\r\n"
				+ "olddefault_act_id   character varying;\r\n" + "newdefault_act_id character varying;\r\n"
				+ "oldparameter   character varying;\r\n" + "newparameter  character varying;\r\n" + "begin\r\n"
				+ "SELECT COUNT(*) FROM bedc.activity_log INTO incCount;\r\n" + "incCount := incCount +1;\r\n"
				+ "SELECT COUNT(*) FROM bedc.audit_log INTO actid;\r\n" + "actid := actid +1;\r\n"
				+ "IF (old.id is null) THEN\r\n" + // INSERT TRIGGER
				"	INSERT INTO bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES(new.created_by,'CREATE',new.id,'default_activity_parameters',null,null,null,'default_activity_parameters_creation',new.created_on)RETURNING id INTO incCount;\r\n"
				+

				"    IF (new.parameter IS NOT NULL) THEN\r\n"
				+ "	select parameter_name from bedc.global_parameter where id=new.parameter into newparameter;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	values ('parameter','',newparameter,null,null,null,'',new.created_by,incCount,new.created_on,'default_activity_parameters','dynamic','audit_insert','parameter');\r\n"
				+ "    END IF;\r\n" + "RETURN new;\r\n" + "	ELSE IF(new.id=old.id) THEN\r\n"
				+ "	INSERT INTO bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES(new.updated_by,'update',new.id,'default_activity_parameters',null,null,null,'default_activity_parameters_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+ "    IF nullif(old.parameter,'') <> NULLIF(new.parameter,'') or new.parameter is null THEN\r\n"
				+ "	select parameter_name from bedc.global_parameter where id=OLD.parameter into oldparameter;\r\n"
				+ "	select parameter_name from bedc.global_parameter where id=NEW.parameter into newparameter;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	values ('parameter',oldparameter,newparameter,null,null,null,'',new.updated_by,incCount,new.updated_on,'default_activity_parameters','dynamic','audit_update','parameter');\r\n"
				+ "    END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER default_activity_parameters_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,created_by,created_on,update_reason,updated_by,updated_on,defalult_act_id,parameter  ON bedc.default_activity_parameters\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.default_activity_parameters_fun();");
	}

	private void generateepk_centrifugation_data(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.epk_centrifugationdata_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "     centifirgitiomname character varying;\r\n" + "	 periodnamevalue character varying;\r\n"
				+ "	 oldperiodValue character varying;\r\n" + "	 processtimevalue character varying;\r\n"
				+ "	 oldprocesstimevalue character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.centrifugationdataid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"     INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "     VALUES (new.createdby,'CREATE',new.centrifugationdataid,'epk_centrifugationdata',new.studyid,null,null,'epk_centrifugationdata_creation',new.createdon)RETURNING id INTO incCount; \r\n"
				+

				"     IF (new.centrifugationdatamasterid IS NOT NULL) THEN\r\n"
				+ "     SELECT commentseparation FROM bedc.epk_centrifugationdatamaster where centrifugationdatamasterid = new.centrifugationdatamasterid into centifirgitiomname; \r\n"
				+ "     INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "     VALUES ('centrifugationdatamasterid','',centifirgitiomname,'Dynamic',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdata','audit_insert','centrifugationdatamasterid');\r\n"
				+ "     END IF;\r\n" +

				"     IF (new.periodid IS NOT NULL) THEN\r\n"
				+ "     SELECT periodname FROM bedc.study_period_master where periodid = new.periodid into periodnamevalue;\r\n"
				+ "     INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "     VALUES ('periodid','',periodnamevalue,'Dynamic',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdata','audit_insert','periodid');\r\n"
				+ "     END IF;\r\n" +

				"     IF (new.sampletimepointsid IS NOT NULL) THEN\r\n"
				+ "     INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "     VALUES ('label.sampletimepointsid','',new.sampletimepointsid,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdata','audit_insert','sampletimepointsid');\r\n"
				+ "     END IF;\r\n" + "	 IF (new.updatereason IS NOT NULL) THEN\r\n"
				+ "     INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "     VALUES ('label.updatereason','',new.updatereason,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdata','audit_insert','updatereason');\r\n"
				+ "     END IF;\r\n" + "RETURN new;\r\n" + // UPDATE TRIGGER
				"     ELSE IF (new.centrifugationdataid=old.centrifugationdataid) THEN\r\n"
				+ "     INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	 VALUES (new.updatedby,'UPDATE',new.centrifugationdataid,'epk_centrifugationdata',new.studyid,null,null,'c_updation',new.updatedon)RETURNING id INTO incCount;\r\n"
				+

				"     IF NULLIF(old.centrifugationdatamasterid,'') <> NULLIF(new.centrifugationdatamasterid,'') or new.centrifugationdatamasterid is null then\r\n"
				+ "     SELECT commentseparation FROM bedc.epk_centrifugationdatamaster where centrifugationdatamasterid = new.centrifugationdatamasterid into centifirgitiomname; \r\n"
				+ "     INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updatedby,updatedon,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "     VALUES ('centrifugationdatamasterid',old.periodid,new.periodid,'Dynamic',new.studyid,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_centrifugationdata','audit_update','centrifugationdatamasterid');\r\n"
				+ "     END IF;\r\n" +

				"     IF NULLIF(old.periodid,'') <> NULLIF(new.periodid,'') or new.periodid is null then\r\n"
				+ "     SELECT periodname FROM bedc.study_period_master where periodid = old.periodid into oldperiodValue;\r\n"
				+ "     SELECT periodname FROM bedc.study_period_master where periodid = new.periodid into periodnamevalue;\r\n"
				+ "     INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updatedby,updatedon,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "     VALUES ('periodid',oldperiodValue,periodnamevalue,'Dynamic',new.studyid,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_centrifugationdata','audit_update','periodname');\r\n"
				+ "     END IF;\r\n" +

				"     IF NULLIF(old.sampletimepointsid,'') <> NULLIF(new.sampletimepointsid,'') or new.sampletimepointsid is null then\r\n"
				+ "     INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updatedby,updatedon,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "     VALUES ('label.sampletimepointsid',old.sampletimepointsid,new.sampletimepointsid,'Static',new.studyid,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_centrifugationdata','audit_update','sampletimepointsid');\r\n"
				+ "     END IF;\r\n"
				+ "	 IF NULLIF(old.updatereason,'') <> NULLIF(new.updatereason,'') or new.updatereason is null then\r\n"
				+ "     INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updatedby,updatedon,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "     VALUES ('label.updatereason',old.updatereason,new.updatereason,'Static',new.studyid,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_centrifugationdata','audit_update','updatereason');\r\n"
				+ "     END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER epk_centrifugationdata_trgr"
				+ "   AFTER INSERT OR UPDATE  OF centrifugationdataid,createdon,updatereason,updatedon,createdby,updatedby,centrifugationdatamasterid,periodid,sampletimepointsid,studyid  ON bedc.epk_centrifugationdata\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.epk_centrifugationdata_fun();");

	}

	private void generateEpkCentrifugationdatamaster(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.epk_centrifugationdatamaster_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   centrifugeidName character varying;\r\n" + "   oldcentrifugeidName character varying;\r\n"
				+ "   separatedbyName character varying;\r\n" + "   oldseparatedbyName character varying;\r\n"
				+ "   centrifugebyName character varying;\r\n" + "   oldcentrifugebyName character varying;\r\n"
				+ "   instrumentidName character varying;\r\n" + "   oldinstrumentidName character varying;\r\n"
				+ "   periodidName character varying;\r\n" + "   oldperiodidName character varying;\r\n"
				+ "   sampletimepointidName  character varying;\r\n"
				+ "   oldsampletimepointidName character varying;\r\n" + "   studyidName character varying;\r\n"
				+ "   oldstudyidName character varying;\r\n" + "BEGIN\r\n"
				+ "	 SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + "	 incCount := incCount +1;\r\n"
				+ "	 select count(*) from bedc.audit_log into actid;\r\n" + "	 actid := actid +1;\r\n"
				+ "IF (old.centrifugationdatamasterid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.createdby,'CREATE',new.centrifugationdatamasterid,'epk_centrifugationdatamaster',new.studyid,null,null,'epk_centrifugationdatamaster_creation',new.createdon) RETURNING id INTO incCount; \r\n"
				+

				"    IF (new.centrifugecondition IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugecondition','',new.centrifugecondition,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','centrifugecondition');\r\n"
				+ "    END IF;\r\n" + "    IF (new.centrifugeendtime IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugeendtime','',new.centrifugeendtime,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','centrifugeendtime');\r\n"
				+ "    END IF;\r\n" + "    IF (new.centrifugescantime IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugescantime','',new.centrifugescantime,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','centrifugescantime');\r\n"
				+ "    END IF;\r\n" + "    IF (new.centrifugestarttime IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugestarttime','',new.centrifugestarttime,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','centrifugestarttime');\r\n"
				+ "    END IF;\r\n" + "    IF (new.commentseparation IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.commentseparation','',new.commentseparation,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','commentseparation');\r\n"
				+ "    END IF;\r\n" + "    IF (new.commentseparation IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.commentseparation','',new.commentseparation,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','commentseparation');\r\n"
				+ "    END IF;\r\n" +

				"    IF (new.missingsampleseparation IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.missingsampleseparation','',new.missingsampleseparation,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','missingsampleseparation');\r\n"
				+ "    END IF;\r\n" + "    IF (new.missingsubjectsseparation IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.missingsubjectsseparation','',new.missingsubjectsseparation,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','missingsubjectsseparation');\r\n"
				+ "    END IF;\r\n" + "    IF (new.separationendtime IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.separationendtime','',new.separationendtime,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','separationendtime');\r\n"
				+ "    END IF;\r\n" + "    IF (new.separationendtime IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.separationstarttime','',new.separationstarttime,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','separationstarttime');\r\n"
				+ "    END IF;\r\n" +

				"    IF (new.separatedby IS NOT NULL) THEN\r\n"
				+ "	SELECT user_name FROM bedc.epk_user_master where userid = new.separatedby into separatedbyName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('separatedby','',separatedbyName,'Dynamic',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','separatedby');\r\n"
				+ "    END IF;\r\n" + "    IF (new.centrifugemissedsubjects IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugemissedsubjects','',new.centrifugemissedsubjects,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','centrifugemissedsubjects');\r\n"
				+ "    END IF;\r\n" + "	IF (new.centrifugesubjects IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugesubjects','',new.centrifugesubjects,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','centrifugesubjects');\r\n"
				+ "    END IF;\r\n" + "	IF (new.separationcondition IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.separationcondition','',new.separationcondition,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','separationcondition');\r\n"
				+ "    END IF;\r\n" + "	IF (new.storagecondtion IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.storagecondtion','',new.storagecondtion,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','storagecondtion');\r\n"
				+ "    END IF;\r\n" + "	IF (new.subjects IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subjects','',new.subjects,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','subjects');\r\n"
				+ "    END IF;\r\n" + "	\r\n" + "	IF (new.timepoitns IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.timepoitns','',new.timepoitns,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','timepoitns');\r\n"
				+ "    END IF;\r\n" + "	IF (new.centrifugeby IS NOT NULL) THEN\r\n"
				+ "	SELECT user_name FROM bedc.epk_user_master where userid = new.centrifugeby into centrifugebyName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('centrifugeby','',centrifugebyName,'Dynamic',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','centrifugeby');\r\n"
				+ "    END IF;\r\n" + "	IF (new.instrumentid IS NOT NULL) THEN\r\n"
				+ "	SELECT instrumentno FROM bedc.instrument where id = new.instrumentid into instrumentidName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('instrumentid','',instrumentidName,'Dynamic',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','instrumentid');\r\n"
				+ "    END IF;\r\n" + "	IF (new.periodid IS NOT NULL) THEN\r\n"
				+ "	SELECT periodname FROM bedc.study_period_master where periodid = new.periodid into periodidName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('periodid','',periodidName,'Dynamic',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','periodid');\r\n"
				+ "    END IF;\r\n" + "	IF (new.sampletimepointsid IS NOT NULL) THEN\r\n"
				+ "	SELECT timepointno FROM bedc.study_sample_time_points where sampletimepointid = new.sampletimepointsid into sampletimepointidName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('sampletimepointsid','',sampletimepointidName,'Dynamic',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','sampletimepointsid');\r\n"
				+ "    END IF;\r\n" + "	IF (new.studyid IS NOT NULL) THEN\r\n"
				+ "	SELECT projectno FROM bedc.study_master where studyid = new.studyid into studyidName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('studyid','',sampletimepointidName,'Dynamic',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','studyid');\r\n"
				+ "    END IF;\r\n" + "	IF (new.updatereason IS NOT NULL) THEN\r\n"
				+ "    INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "    VALUES ('label.updatereason','',new.updatereason,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_insert','updatereason');\r\n"
				+ "    END IF;\r\n" + "RETURN new;\r\n"
				+ "	ELSE IF (new.centrifugationdatamasterid=old.centrifugationdatamasterid) THEN\r\n" + // UPDATE
																										// TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.createdby,'UPDATE',new.centrifugationdatamasterid,'epk_centrifugationdatamaster',null,null,null,'epk_centrifugationdatamaster_updation',new.createdon) RETURNING id INTO incCount;\r\n"
				+

				"    IF NULLIF(old.centrifugecondition,'') <> NULLIF(new.centrifugecondition,'') or new.centrifugecondition is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugecondition',old.centrifugecondition,new.centrifugecondition,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','centrifugecondition');\r\n"
				+ "    END IF;\r\n" +

				"    IF NULLIF(old.centrifugeendtime,'') <> NULLIF(new.centrifugeendtime,'') or new.centrifugeendtime is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugeendtime',old.centrifugeendtime,new.centrifugeendtime,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','centrifugeendtime');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.centrifugecondition,'') <> NULLIF(new.centrifugecondition,'') or new.centrifugecondition is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugecondition',old.centrifugecondition,new.centrifugecondition,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','centrifugecondition');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.centrifugescantime,'') <> NULLIF(new.centrifugescantime,'') or new.centrifugescantime is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugescantime',old.centrifugescantime,new.centrifugescantime,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','centrifugescantime');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.centrifugestarttime,'') <> NULLIF(new.centrifugestarttime,'') or new.centrifugestarttime is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugestarttime',old.centrifugestarttime,new.centrifugestarttime,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','centrifugestarttime');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.commentseparation,'') <> NULLIF(new.commentseparation,'') or new.commentseparation is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.commentseparation',old.commentseparation,new.commentseparation,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','commentseparation');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.commentseparation,'') <> NULLIF(new.commentseparation,'') or new.commentseparation is null then \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.commentseparation',old.commentseparation,new.commentseparation,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','commentseparation');\r\n"
				+ "    END IF;\r\n" +

				"    IF NULLIF(old.missingsampleseparation,'') <> NULLIF(new.missingsampleseparation,'') or new.missingsampleseparation is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.missingsampleseparation',old.missingsampleseparation,new.missingsampleseparation,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','missingsampleseparation');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.missingsubjectsseparation,'') <> NULLIF(new.missingsubjectsseparation,'') or new.missingsubjectsseparation is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.missingsubjectsseparation',old.missingsubjectsseparation,new.missingsubjectsseparation,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','missingsubjectsseparation');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.separationendtime,'') <> NULLIF(new.separationendtime,'') or new.separationendtime is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.separationendtime',old.separationendtime,new.separationendtime,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','separationendtime');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.separationstarttime,'') <> NULLIF(new.separationstarttime,'') or new.separationstarttime is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.separationstarttime',old.separationstarttime,new.separationstarttime,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','separationstarttime');\r\n"
				+ "    END IF;\r\n" +

				"    IF NULLIF(old.seperatedby,'') <> NULLIF(new.seperatedby,'') or new.seperatedby is null then\r\n"
				+ "	SELECT user_name FROM bedc.epk_user_master where userid = old.seperatedby into oldseparatedbyName;\r\n"
				+ "	SELECT user_name FROM bedc.epk_user_master where userid = new.seperatedby into separatedbyName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('seperatedby',oldseparatedbyName,separatedbyName,'Dynamic',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','seperatedby');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.centrifugemissedsubjects,'') <> NULLIF(new.centrifugemissedsubjects,'') or new.centrifugemissedsubjects is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugemissedsubjects',old.centrifugemissedsubjects,new.centrifugemissedsubjects,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','centrifugemissedsubjects');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.centrifugesubjects,'') <> NULLIF(new.centrifugesubjects,'') or new.centrifugesubjects is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugesubjects',old.centrifugesubjects,new.centrifugesubjects,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','centrifugesubjects');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.seperationcondtion,'') <> NULLIF(new.seperationcondtion,'') or new.seperationcondtion is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.seperationcondtion',old.seperationcondtion,new.seperationcondtion,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','seperationcondtion');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.storagecondition,'') <> NULLIF(new.storagecondition,'') or new.storagecondition is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.storagecondition',old.storagecondition,new.storagecondition,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','storagecondition');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.subjects,'') <> NULLIF(new.subjects,'') or new.subjects is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subjects',old.subjects,new.subjects,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','subjects');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.timepoints,'') <> NULLIF(new.timepoints,'') or new.timepoints is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.timepoints',old.timepoints,new.timepoints,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','timepoints');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.centrifugeby,'') <> NULLIF(new.centrifugeby,'') or new.centrifugeby is null then\r\n"
				+ "	SELECT user_name FROM bedc.epk_user_master where userid = old.centrifugeby into oldcentrifugebyName;\r\n"
				+ "	SELECT user_name FROM bedc.epk_user_master where userid = new.centrifugeby into centrifugebyName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('centrifugeby',oldcentrifugebyName,centrifugebyName,'Dynamic',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','centrifugeby');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.instrumentid,'') <> NULLIF(new.instrumentid,'') or new.instrumentid is null then\r\n"
				+ "	SELECT instrumentno FROM bedc.instrument where id = old.instrumentid into oldinstrumentidName;\r\n"
				+ "	SELECT instrumentno FROM bedc.instrument where id = new.instrumentid into instrumentidName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('instrumentid',oldinstrumentidName,instrumentidName,'Dynamic',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','instrumentid');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.periodid,'') <> NULLIF(new.periodid,'') or new.periodid is null then\r\n"
				+ "	SELECT periodname FROM bedc.study_period_master where periodid = old.periodid into oldperiodidName;\r\n"
				+ "	SELECT periodname FROM bedc.study_period_master where periodid = new.periodid into periodidName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('periodid',oldperiodidName,periodidName,'Dynamic',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','periodid');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.sampletimepointsid,'') <> NULLIF(new.sampletimepointsid,'') or new.sampletimepointsid is null then\r\n"
				+ "	SELECT timepointno FROM bedc.study_sample_time_points where sampletimepointid = old.sampletimepointsid into oldsampletimepointidName;\r\n"
				+ "	SELECT timepointno FROM bedc.study_sample_time_points where sampletimepointid = new.sampletimepointsid into sampletimepointidName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('sampletimepointsid',oldsampletimepointidName,sampletimepointidName,'Dynamic',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','sampletimepointsid');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.studyid,'') <> NULLIF(new.studyid,'') or new.studyid is null then\r\n"
				+ "	SELECT projectno FROM bedc.study_master where studyid = old.studyid into oldstudyidName;\r\n"
				+ "	SELECT projectno FROM bedc.study_master where studyid = new.studyid into studyidName;\r\n" +

				"	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('studyid',oldstudyidName,studyidName,'Dynamic',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','studyid');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.updatereason,'') <> NULLIF(new.updatereason,'') or new.updatereason is null then\r\n"
				+ "    INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "    VALUES ('label.updatereason',old.updatereason,new.updatereason,'Static',new.studyid,null,null,' ',null,new.createdon,incCount,'epk_centrifugationdatamaster','audit_update','updatereason');\r\n"
				+ "    END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER epk_centrifugationdatamaster_trgr"
				+ "   AFTER INSERT OR UPDATE  OF centrifugationdatamasterid,createdon,updatereason,updatedon,centrifugecondition,centrifugeendtime,centrifugemissedsubjects,centrifugescantime,centrifugestarttime,centrifugesubjects,commentseparation,missingsampleseparation,missingsubjectsseparation,separationcondition,separationendtime,separationstarttime,storagecondtion,subjects,timepoitns,createdby,updatedby,centrifugeby,instrumentid,periodid,sampletimepointsid,separatedby,studyid  ON bedc.epk_centrifugationdatamaster\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.epk_centrifugationdatamaster_fun();");

	}

	private void generateEpkCentrifugation(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.epk_centrifugation_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.centrifugationid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.createdby,'CREATE',new.centrifugationid,'epk_centrifugation',null,null,null,'epk_centrifugation_creation',new.createdon)RETURNING id INTO incCount; \r\n"
				+

				"    IF (new.updatereason IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.updatereason','',new.updatereason,'Static',null,null,null,' ',null,new.createdon,incCount,'updatereason','audit_insert','updatereason');\r\n"
				+ "    END iF;\r\n" + "    IF (new.activestatus IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.activestatus','',new.activestatus,'Static',null,null,null,' ',null,new.createdon,incCount,'activestatus','audit_insert','activestatus');\r\n"
				+ "    END iF;\r\n" + "    IF (new.centrifugationbarcode IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugationbarcode','',new.centrifugationbarcode,'Static',null,null,null,' ',null,new.createdon,incCount,'epk_centrifugation','audit_insert','centrifugationbarcode');\r\n"
				+ "    END iF;\r\n" + "    IF (new.code IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.code','',new.code,'Static',null,null,null,' ',null,new.createdon,incCount,'epk_centrifugation','audit_insert','code');\r\n"
				+ "    END IF;\r\n" + "    IF (new.insturmentdesc IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.insturmentdesc','',new.insturmentdesc,'Static',null,null,null,' ',null,new.createdon,incCount,'epk_centrifugation','audit_insert','insturmentdesc');\r\n"
				+ "    END IF;\r\n" + "    IF (new.name IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.name','',new.name,'Static',null,null,null,' ',null,new.createdon,incCount,'epk_centrifugation','audit_insert','name');\r\n"
				+ "    END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.centrifugationid=old.centrifugationid) THEN\r\n"
				+ // UPDATE TRIGGER

				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.updatedby,'UPDATE',new.centrifugationid,'epk_centrifugation',null,null,null,'epk_centrifugation_updation',new.updatedon)RETURNING id INTO incCount;\r\n"
				+

				"    IF NULLIF(old.updatereason,'') <> NULLIF(new.updatereason,'') or new.updatereason is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updatedby,updatedon,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.updatereason',old.updatereason,new.updatereason,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_centrifugation','audit_update','updatereason');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.activestatus,'') <> NULLIF(new.activestatus,'') or new.activestatus is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updatedby,updatedon,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.activestatus',old.activestatus,new.activestatus,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_centrifugation','audit_update','activestatus');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.centrifugationbarcode,'') <> NULLIF(new.centrifugationbarcode,'') or new.centrifugationbarcode is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updatedby,updatedon,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.centrifugationbarcode',old.centrifugationbarcode,new.centrifugationbarcode,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_centrifugation','audit_update','centrifugationbarcode');\r\n"
				+ "    END IF;\r\n" + "    IF NULLIF(old.code,'') <> NULLIF(new.code,'') or new.code is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updatedby,updatedon,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.code',old.code,new.code,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_centrifugation','audit_update','code');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.insturmentdesc,'') <> NULLIF(new.insturmentdesc,'') or new.insturmentdesc is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updatedby,updatedon,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.insturmentdesc',old.insturmentdesc,new.insturmentdesc,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_centrifugation','audit_update','insturmentdesc');\r\n"
				+ "    END IF;\r\n" + "    IF NULLIF(old.name,'') <> NULLIF(new.name,'') or new.name is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updatedby,updatedon,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.name',old.name,new.name,'Static',null,null,null,' ',new.updatedby,new.updatedon,incCount,'epk_centrifugation','audit_update','name');\r\n"
				+ "    END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER epk_centrifugation_trgr"
				+ "   AFTER INSERT OR UPDATE  OF centrifugationid,createdon,updatereason,updatedon,activestatus,centrifugationbarcode,code,insturmentdesc,name,createdby,updatedby  ON bedc.epk_centrifugation\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.epk_centrifugation_fun();");

	}

	private void generateStudySubjectDeviations(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_subject_deviations_fun() RETURNS TRIGGER AS $$\r\n" +

				"\r\n" + " declare \r\n" + " incCount integer;\r\n" + " audid integer;\r\n"
				+ " createdby character varying;\r\n" + " activityName character varying;\r\n"
				+ " oldActivityName character varying;\r\n" + " deviationMessage character varying;\r\n"
				+ " oldDeviationMessage character varying;\r\n" + " statusName character varying;\r\n"
				+ " oldStatusName character varying;\r\n" + " subjectName character varying;\r\n"
				+ " oldSubjectName character varying;\r\n" +

				" \r\n" + " begin \r\n" + " select count(*) from bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n"
				+ " select user_name from bedc.epk_user_master where userid=new.created_by into createdby;\r\n" + "\r\n"
				+ " if (old.id is null) then \r\n"
				+ " insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " values (createdby,'create',new.id,'study_subject_deviations',new.study_id,null,null,'study_subject_deviations_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+ " \r\n" + " IF (new.activity IS NOT NULL) THEN\r\n"
				+ "	select name from bedc.global_activity  where id=new.activity into activityName;\r\n"
				+ "    INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "    VALUES ('activity', '', activityName, new.study_id, null, null,' ', createdby, incCount,new.created_on ,'study_subject_deviations', 'dynamic', 'audit_insert', 'activity');\r\n"
				+ "    END IF;\r\n" + "    \r\n" + "     IF (new.dev_msg_id IS NOT NULL) THEN\r\n"
				+ "	 select code from bedc.deviation_message  where deviationmessageid=new.dev_msg_id into deviationMessage;\r\n"
				+ "    INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "    VALUES ('dev msg id', '', deviationMessage, new.study_id, null, null,' ', createdby, incCount,new.created_on ,'study_subject_deviations', 'dynamic', 'audit_insert', 'dev_msg_id');\r\n"
				+ "    END IF;\r\n" + "    \r\n" + "     IF (new.status IS NOT NULL) THEN\r\n"
				+ "	 select statuscode from bedc.status_master  where statusid=new.status into statusName;\r\n"
				+ "    INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "    VALUES ('status', '', statusName, new.study_id, null, null,' ', createdby, incCount,new.created_on ,'study_subject_deviations', 'dynamic', 'audit_insert', 'status');\r\n"
				+ "    END IF;\r\n" + "    \r\n" + "    IF (new.subject IS NOT NULL) THEN\r\n"
				+ "	 select subject_status from bedc.study_subjects  where id=new.subject into subjectName;\r\n"
				+ "    INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "    VALUES ('subject', '', subjectName, new.study_id, null, null,' ', createdby, incCount,new.created_on ,'study_subject_deviations', 'dynamic', 'audit_insert', 'subject');\r\n"
				+ "    END IF;\r\n" + "    \r\n" + " IF (new.deviation_record_id IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.deviation_record_id','',new.deviation_record_id,new.study_id,null,null,' ',createdby,incCount,new.created_on,'study_subject_deviations','static','audit_insert','deviation_record_id');\r\n"
				+ " END IF;\r\n" + " IF (new.time_point IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.time_point','',new.time_point,new.study_id,null,null,' ',createdby,incCount,new.created_on,'study_subject_deviations','static','audit_insert','time_point');\r\n"
				+ " END IF;\r\n" + " IF (new.period_name IS NOT NULL) THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.period_name','',new.period_name,new.study_id,null,null,' ',createdby,incCount,new.created_on,'study_subject_deviations','static','audit_insert','period_name');\r\n"
				+ " END IF;\r\n" + " return new;\r\n" + " \r\n" + " else if (new.id = old.id) then \r\n"
				+ " insert into bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " values (new.updated_by,'update',new.id,'control_type',new.study_id,null,null,'study_subject_deviations_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+ " IF nullif(old.deviation_record_id,null) <> NULLIF(new.deviation_record_id,null) or new.deviation_record_id is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.deviation_record_id',old.deviation_record_id,new.deviation_record_id,new.study_id,null,null,' ',new.updated_by,incCount,new.updated_on,'study_subject_deviations','static','audit_update','deviation_record_id');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.time_point,'') <> NULLIF(new.time_point,'') or new.time_point is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.time_point',old.time_point,new.time_point,new.study_id,null,null,' ',new.updated_by,incCount,new.updated_on,'study_subject_deviations','static','audit_update','time_point');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.period_name,'') <> NULLIF(new.period_name,'') or new.period_name is null THEN\r\n"
				+ " insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ " values ('label.period_name',old.period_name,new.period_name,new.study_id,null,null,' ',new.updated_by,incCount,new.updated_on,'study_subject_deviations','static','audit_update','period_name');\r\n"
				+ " END IF;\r\n" +

				"     IF nullif(old.subject,null) <> NULLIF(new.subject,null) or new.subject is null THEN\r\n"
				+ "    select subject_status from bedc.study_subjects  where id=new.subject into subjectName;\r\n"
				+ "    select subject_status from bedc.study_subjects  where id=old.subject into oldSubjectName;\r\n"
				+ "     insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "     values ('subject',oldSubjectName,subjectName,new.study_id,null,null,' ',new.updated_by,incCount,new.updated_on,'study_subject_deviations','static','audit_update','subject');\r\n"
				+ "     END IF;\r\n" + "     \r\n"
				+ "      IF nullif(old.status,null) <> NULLIF(new.status,null) or new.status is null THEN\r\n"
				+ "     select statuscode from bedc.status_master  where statusid=new.status into statusName;\r\n"
				+ "     select statuscode from bedc.status_master  where statusid=old.status into oldStatusName;\r\n"
				+ "     insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "     values ('status',oldStatusName,statusName,new.study_id,null,null,' ',new.updated_by,incCount,new.updated_on,'study_subject_deviations','static','audit_update','status');\r\n"
				+ "     END IF;\r\n" + "     \r\n"
				+ "      IF nullif(old.dev_msg_id,null) <> NULLIF(new.dev_msg_id,null) or new.dev_msg_id is null THEN\r\n"
				+ "     select code from bedc.deviation_message  where deviationmessageid=new.dev_msg_id into deviationMessage;\r\n"
				+ "     select code from bedc.deviation_message  where deviationmessageid=old.dev_msg_id into oldDeviationMessage;\r\n"
				+ "     insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "     values ('dev msg id',oldDeviationMessage,deviationMessage,new.study_id,null,null,' ',new.updated_by,incCount,new.updated_on,'study_subject_deviations','static','audit_update','dev_msg_id');\r\n"
				+ "     END IF;\r\n" + "    \r\n"
				+ "     IF nullif(old.activity,null) <> NULLIF(new.activity,null) or new.activity is null THEN\r\n"
				+ "     select name from bedc.global_activity  where id=new.activity into activityName;\r\n"
				+ "    select name from bedc.global_activity  where id=old.activity into oldActivityName;\r\n"
				+ "     insert into bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,updated_by,activity_log_id,updated_on,table_name,field_type,action,databasefieldname) \r\n"
				+ "     values ('activity',oldActivityName,activityName,new.study_id,null,null,' ',new.updated_by,incCount,new.updated_on,'study_subject_deviations','static','audit_update','activity');\r\n"
				+ "     END IF;\r\n" + "     \r\n" + " return new;\r\n" + " end if;\r\n" + " end if;\r\n" + " end;\r\n"
				+

				" $$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_subject_deviations_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,created_on,deviation_record_id,period_name,study_id,time_point,update_reason,updated_by,updated_on,activity,created_by,dev_msg_id,status,subject  ON bedc.study_subject_deviations\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_subject_deviations_fun();");

	}

	private void generateStudySubjects(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_subjects_fun() RETURNS TRIGGER AS $$\r\n" + "declare \r\n"
				+ "   incCount integer;\r\n" + "   actid integer;\r\n" + "  newStudyGroupId character varying;\r\n"
				+ "  oldStudyGroupId character varying;\r\n" + "  newStdSubjectId character varying;\r\n"
				+ "  oldStdSubjectId character varying;\r\n" + "  newGlobalValId character varying;\r\n"
				+ "  oldGlobalValId character varying;\r\n" + "  newReportingId character varying;\r\n"
				+ "  oldReportingId character varying;\r\n" + "  newstudyid character varying;\r\n"
				+ "  oldstudyid character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.created_by,'CREATE',new.id,'study_subjects',new.studyid,null,null,'study_subjects_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+ "    IF (new.subject_discontinue IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_discontinue','',new.subject_discontinue,'Static',new.studyid,null,null,'',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','subject_discontinue');\r\n"
				+ "    END IF;\r\n" + "	IF (new.subject_replace IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_replace','',new.subject_replace,'Static',new.studyid,null,null,'',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','subject_replace');\r\n"
				+ "    END IF;\r\n" + "    IF (new.subject_no IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_no','',new.subject_no,'Static',new.studyid,null,null,'',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','subject_no');\r\n"
				+ "    END IF;\r\n" + "	IF (new.subject_status IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_status','',new.subject_status,'Static',new.studyid,null,null,'',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','subject_status');\r\n"
				+ "    END IF;\r\n" + "    IF (new.studygroupid IS NOT NULL) THEN\r\n"
				+ "	select groupname from bedc.study_group where studygroupid= new.studygroupid into newStudyGroupId;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('studygroupid','',newStudyGroupId,'Dynamic',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','studygroupid');\r\n"
				+ "    END IF;\r\n" + "    IF (new.std_subject_id IS NOT NULL) THEN\r\n"
				+ "	select subject_status from bedc.study_subjects where id= new.std_subject_id into newStdSubjectId;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('std_subject_id','',newStdSubjectId,'Dynamic',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','std_subject_id');\r\n"
				+ "    END IF;\r\n" + "    IF (new.reporting_id IS NOT NULL) THEN\r\n"
				+ "	select statusid from bedc.study_volunteer_reporting where id= new.reporting_id into newReportingId;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('reporting_id','',newReportingId,'Dynamic',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','reporting_id');\r\n"
				+ "    END IF;\r\n" + "	IF (new.studyid IS NOT NULL) THEN\r\n"
				+ "	select projectno from bedc.study_master where studyid= new.studyid into newstudyid;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('studyid','',newstudyid,'Dynamic',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','studyid');\r\n"
				+ "    END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.updated_by,'UPDATE',new.id,'study_subjects',new.studyid,null,null,'study_subjects_updation',new.update_on)RETURNING id INTO incCount;\r\n"
				+ "    IF NULLIF(old.subject_discontinue,'') <> NULLIF(new.subject_discontinue,'') or new.subject_discontinue is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_discontinue',old.subject_discontinue,new.subject_discontinue,'Static',new.studyid,null,null,'',new.updated_by,new.updated_on,incCount,'study_subjects','audit_update','subject_discontinue');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.subject_replace,'') <> NULLIF(new.subject_replace,'') or new.subject_replace is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_replace',old.subject_replace,new.subject_replace,'Static',new.studyid,null,null,'',new.updated_by,new.updated_on,incCount,'study_subjects','audit_update','subject_replace');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.subject_no,'') <> NULLIF(new.subject_no,'') or new.subject_no is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_no',old.subject_no,new.subject_no,'Static',new.studyid,null,null,'',new.updated_by,new.updated_on,incCount,'study_subjects','audit_update','subject_no');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.subject_status,'') <> NULLIF(new.subject_status,'') or new.subject_status is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_status',old.subject_status,new.subject_status,'Static',new.studyid,null,null,'',new.updated_by,new.upated_on,incCount,'study_subjects','audit_update','subject_status');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.studygroupid,'') <> NULLIF(new.studygroupid,'') or new.studygroupid is null then\r\n"
				+ "	select groupname from bedc.study_group where studygroupid= new.studygroupid into newStudyGroupId;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('studygroupid',oldStudyGroupId,newStudyGroupId,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_subjects','audit_update','studygroupid');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.std_subject_id,'') <> NULLIF(new.std_subject_id,'') or new.std_subject_id is null then\r\n"
				+ "	select subject_status from bedc.study_subjects where id= new.std_subject_id into newStdSubjectId;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('std_subject_id',oldStdSubjectId,newStdSubjectId,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_subjects','audit_update','std_subject_id');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.reporting_id,'') <> NULLIF(new.reporting_id,'') or new.reporting_id is null then\r\n"
				+ "	select statusid from bedc.study_volunteer_reporting where id= new.reporting_id into newReportingId;\r\n"
				+ "	select statusid from bedc.study_volunteer_reporting where id= old.reporting_id into oldReportingId;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('reporting_id',oldReportingId,newReportingId,'Dynamic',new.studyid,null,null,' ',new.updted_by,new.updated_on,incCount,'study_subjects','audit_update','reporting_id');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.studyid,'') <> NULLIF(new.studyid,'') or new.studyid is null then\r\n"
				+ "	select projectno from bedc.study_master where studyid= new.studyid into newstudyid;  \r\n"
				+ "	select projectno from bedc.study_master where studyid= old.studyid into oldstudyid;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('studyid',oldReportingId,newReportingId,'Dynamic',new.studyid,null,null,' ',new.updted_by,new.updated_on,incCount,'study_subjects','audit_update','studyid');\r\n"
				+ "    END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_subjects_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,created_by,created_on,subject_discontinue,subject_no,subject_replace,subject_status,updated_by,update_reason,updated_on,studygroupid,reporting_id,std_subject_id,studyid  ON bedc.study_subjects\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_subjects_fun();");

	}

	private void generateStudySubjectReplacedInfo(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_subject_replaced_info_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "  newStudyGroupId character varying;\r\n" + "  oldStudyGroupId character varying;\r\n"
				+ "  newStdSubjectId character varying;\r\n" + "  oldStdSubjectId character varying;\r\n"
				+ "  newGlobalValId character varying;\r\n" + "  oldGlobalValId character varying;\r\n"
				+ "  newReportingId character varying;\r\n" + "  oldReportingId character varying;\r\n"
				+ "  newstudyid character varying;\r\n" + "  oldstudyid character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.created_by,'CREATE',new.id,'study_subjects',new.studyid,null,null,'study_subjects_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+

				"    IF (new.subject_discontinue IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_discontinue','',new.subject_discontinue,'Static',new.studyid,null,null,'',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','subject_discontinue');\r\n"
				+ "    END IF;\r\n" +

				"	IF (new.subject_replace IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_replace','',new.subject_replace,'Static',new.studyid,null,null,'',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','subject_replace');\r\n"
				+ "    END IF;\r\n" + "    IF (new.subject_no IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_no','',new.subject_no,'Static',new.studyid,null,null,'',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','subject_no');\r\n"
				+ "    END IF;\r\n" + "	IF (new.subject_status IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_status','',new.subject_status,'Static',new.studyid,null,null,'',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','subject_status');\r\n"
				+ "    END IF;\r\n" + "    IF (new.studygroupid IS NOT NULL) THEN\r\n"
				+ "	select groupname from bedc.study_group where studygroupid= new.studygroupid into newStudyGroupId;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('studygroupid','',newStudyGroupId,'Dynamic',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','studygroupid');\r\n"
				+ "    END IF;\r\n" + "    IF (new.std_subject_id IS NOT NULL) THEN\r\n"
				+ "	select subject_status from bedc.study_subjects where id= new.std_subject_id into newStdSubjectId;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('std_subject_id','',newStdSubjectId,'Dynamic',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','std_subject_id');\r\n"
				+ "    END IF;\r\n" + "    IF (new.reporting_id IS NOT NULL) THEN\r\n"
				+ "	select status from bedc.study_volunteer_reporting where id= new.reporting_id into newReportingId;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('reporting_id','',newReportingId,'Dynamic',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','reporting_id');\r\n"
				+ "    END IF;\r\n" + "	IF (new.studyid IS NOT NULL) THEN\r\n"
				+ "	select projectno from bedc.study_master where studyid= new.studyid into newstudyid;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('studyid','',newstudyid,'Dynamic',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_subjects','audit_insert','studyid');\r\n"
				+ "    END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.updated_by,'UPDATE',new.id,'study_subjects',new.studyid,null,null,'study_subjects_updation',new.update_on)RETURNING id INTO incCount;\r\n"
				+

				"    IF NULLIF(old.subject_discontinue,'') <> NULLIF(new.subject_discontinue,'') or new.subject_discontinue is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_discontinue',old.subject_discontinue,new.subject_discontinue,'Static',new.studyid,null,null,'',new.updated_by,new.updated_on,incCount,'study_subjects','audit_update','subject_discontinue');\r\n"
				+ "    END IF;\r\n"
				+ "	IF NULLIF(old.subject_replace,'') <> NULLIF(new.subject_replace,'') or new.subject_replace is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_replace',old.subject_replace,new.subject_replace,'Static',new.studyid,null,null,'',new.updated_by,new.updated_on,incCount,'study_subjects','audit_update','subject_replace');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.subject_no,'') <> NULLIF(new.subject_no,'') or new.subject_no is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_no',old.subject_no,new.subject_no,'Static',new.studyid,null,null,'',new.updated_by,new.updated_on,incCount,'study_subjects','audit_update','subject_no');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.subject_status,'') <> NULLIF(new.subject_status,'') or new.subject_status is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.subject_status',old.subject_status,new.subject_status,'Static',new.studyid,null,null,'',new.updated_by,new.upated_on,incCount,'study_subjects','audit_update','subject_status');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.studygroupid,'') <> NULLIF(new.studygroupid,'') or new.studygroupid is null then\r\n"
				+ "	select groupname from bedc.study_group where studygroupid= new.studygroupid into newStudyGroupId;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('studygroupid',oldStudyGroupId,newStudyGroupId,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_subjects','audit_update','studygroupid');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.std_subject_id,'') <> NULLIF(new.std_subject_id,'') or new.std_subject_id is null then\r\n"
				+ "	select subject_status from bedc.study_subjects where id= new.std_subject_id into newStdSubjectId;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('std_subject_id',oldStdSubjectId,newStdSubjectId,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_subjects','audit_update','std_subject_id');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.reporting_id,'') <> NULLIF(new.reporting_id,'') or new.reporting_id is null then\r\n"
				+ "	select status from bedc.study_volunteer_reporting where id= new.reporting_id into newReportingId;\r\n"
				+ "	select status from bedc.study_volunteer_reporting where id= old.reporting_id into oldReportingId;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('reporting_id',oldReportingId,newReportingId,'Dynamic',new.studyid,null,null,' ',new.updted_by,new.updated_on,incCount,'study_subjects','audit_update','reporting_id');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.studyid,'') <> NULLIF(new.studyid,'') or new.studyid is null then\r\n"
				+ "	select projectno from bedc.study_master where studyid= new.studyid into newstudyid;  \r\n"
				+ "	select projectno from bedc.study_master where studyid= old.studyid into oldstudyid;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('studyid',oldReportingId,newReportingId,'Dynamic',new.studyid,null,null,' ',new.updted_by,new.updated_on,incCount,'study_subjects','audit_update','studyid');\r\n"
				+ "    END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_subject_replaced_info_trgr"
				+ "   AFTER INSERT OR UPDATE  OF studysubjectreplacedinfoid,createdon,updatereason,updatedon,activity,createdby,updatedby,replacewithsubjectid,subjectid  ON bedc.study_subject_replaced_info\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_subject_replaced_info_fun();");

	}

	private void generateStudyTreatments(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_treatments_fun() RETURNS TRIGGER AS $$\r\n" + "declare \r\n"
				+ "   incCount integer;\r\n" + "   actid integer;\r\n" + "   fastinginfoName character varying;\r\n"
				+ "   oldfastinginfoName character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1; \r\n"
				+ "IF (old.treatmentinfoid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.created_by,'CREATE',new.treatmentinfoid,'study_treatments',new.studyid,null,null,'study_treatments_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+

				"    IF (new.dose IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.dose','',new.dose,'Static',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_treatments','audit_insert','dose');\r\n"
				+ "    END IF;\r\n" + "    IF (new.mountofwaterconsumedwiththedose IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.mountofwaterconsumedwiththedose','',new.mountofwaterconsumedwiththedose,'Static',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_treatments','audit_insert','mountofwaterconsumedwiththedose');\r\n"
				+ "    END IF;\r\n" + "    IF (new.noofdosings IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.noofdosings','',new.noofdosings,'Static',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_treatments','audit_insert','noofdosings');\r\n"
				+ "    END IF;\r\n" + "    IF (new.noofsachetlables IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.noofsachetlables','',new.noofsachetlables,'Static',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_treatments','audit_insert','noofsachetlables');\r\n"
				+ "    END IF;\r\n" + "    IF (new.randamizationcode IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.randamizationcode','',new.randamizationcode,'Static',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_treatments','audit_insert','randamizationcode');\r\n"
				+ "    END IF;\r\n" + "    IF (new.streanth IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.streanth','',new.streanth,'Static',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_treatments','audit_insert','streanth');\r\n"
				+ "    END IF;\r\n" + "    IF (new.treatmentcount IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.treatmentcount','',new.treatmentcount,'Static',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_treatments','audit_insert','treatmentcount');\r\n"
				+ "    END IF;\r\n" + "    IF (new.treatmentname IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.treatmentname','',new.treatmentname,'Static',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_treatments','audit_insert','treatmentname');\r\n"
				+ "    END IF;\r\n" + "    IF (new.treatmentno IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.treatmentno','',new.treatmentno,'Static',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_treatments','audit_insert','treatmentno');\r\n"
				+ "    END IF;\r\n" + "    IF (new.fastinginfo IS NOT NULL) THEN\r\n"
				+ "	SELECT code FROM bedc.form_static_data where formstaticdataid = new.fastinginfo into fastinginfoName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('fastinginfo','',fastinginfoName,'Dynamic',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_treatments','audit_insert','fastinginfo');\r\n"
				+ "    END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.treatmentinfoid=old.treatmentinfoid) THEN\r\n" + // UPDATE
																															// TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES (new.updated_by,'UPDATE',new.treatmentinfoid,'study_treatments',new.studyid,null,null,'study_treatments_updation',new.updated_on)RETURNING id INTO incCount; \r\n"
				+

				"    IF NULLIF(old.dose,'') <> NULLIF(new.dose,'') or new.dose is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.dose',old.dose,new.dose,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_treatments','audit_update','dose');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.mountofwaterconsumedwiththedose,'') <> NULLIF(new.mountofwaterconsumedwiththedose,'') or new.mountofwaterconsumedwiththedose is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.mountofwaterconsumedwiththedose',old.mountofwaterconsumedwiththedose,new.mountofwaterconsumedwiththedose,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_treatments','audit_update','mountofwaterconsumedwiththedose');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.noofdosings,'') <> NULLIF(new.noofdosings,'') or new.noofdosings is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.noofdosings',old.noofdosings,new.noofdosings,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_treatments','audit_update','noofdosings');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.noofsachetlables,'') <> NULLIF(new.noofsachetlables,'') or new.noofsachetlables is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.noofsachetlables',old.noofsachetlables,new.noofsachetlables,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_treatments','audit_update','noofsachetlables');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.randamizationcode,'') <> NULLIF(new.randamizationcode,'') or new.randamizationcode is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.randamizationcode',old.randamizationcode,new.randamizationcode,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_treatments','audit_update','randamizationcode');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.streanth,'') <> NULLIF(new.streanth,'') or new.streanth is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.streanth',old.streanth,new.streanth,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_treatments','audit_update','streanth');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.treatmentcount,'') <> NULLIF(new.treatmentcount,'') or new.treatmentcount is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.treatmentcount',old.treatmentcount,new.treatmentcount,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_treatments','audit_update','treatmentcount');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.treatmentname,'') <> NULLIF(new.treatmentname,'') or new.treatmentname is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.treatmentname',old.treatmentname,new.treatmentname,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_treatments','audit_update','treatmentname');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.treatmentno,'') <> NULLIF(new.treatmentno,'') or new.treatmentno is null then\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.treatmentno',old.treatmentno,new.treatmentno,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_treatments','audit_update','treatmentno');\r\n"
				+ "    END IF;\r\n"
				+ "    IF NULLIF(old.fastinginfo,'') <> NULLIF(new.fastinginfo,'') or new.fastinginfo is null then\r\n"
				+ "	SELECT code FROM bedc.form_static_data where formstaticdataid = old.fastinginfo into oldfastinginfoName;\r\n"
				+ "	SELECT code FROM bedc.form_static_data where formstaticdataid = new.fastinginfo into fastinginfoName;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,updated_by,updated_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('fastinginfo',oldfastinginfoName,fastinginfoName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_treatments','audit_update','fastinginfo');\r\n"
				+ "    END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_treatments_trgr"
				+ "   AFTER INSERT OR UPDATE  OF treatmentinfoid,created_by,created_on,dose,mountofwaterconsumedwiththedose,noofdosings,noofsachetlables,randamizationcode,streanth,treatmentcount,treatmentname,treatmentno,update_reason,updated_by,updated_on,fastinginfo,studyid  ON bedc.study_treatments\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_treatments_fun();");

	}

	private void generateProjectsDetails(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.projects_details_fun() RETURNS TRIGGER AS $$\r\n" + "\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   newProjectsId character varying;\r\n" + "   oldProjectsId character varying;\r\n"
				+ "   newStatusId character varying;\r\n" + "   oldStatusId character varying;\r\n" + "BEGIN\r\n"
				+ "SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + "incCount := incCount +1;\r\n"
				+ "select count(*) from bedc.audit_log into actid;\r\n" + "actid := actid +1;\r\n"
				+ "IF (old.projectdetailsid IS NULL) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.createdby,'CREATE',new.projectdetailsid,'projects_details',null,null,null,'projects_details_creation',new.createdon)RETURNING id INTO incCount; \r\n"
				+ "\r\n" + "IF (new.displayvalue IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.displayvalue','',new.displayvalue,'Static',null,null,null,null,new.createdby,new.createdon,incCount,'projects_details','audit_insert','displayvalue');\r\n"
				+ "END IF; \r\n" + "\r\n" + "IF (new.fieldname IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.fieldname','',new.fieldname,'Static',null,null,null,null,new.createdby,new.createdon,incCount,'projects_details','audit_insert','fieldname');\r\n"
				+ "END IF;\r\n" + "	\r\n" + "IF (new.fieldorder IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.fieldorder','',new.fieldorder,'Static',null,null,null,null,new.createdby,new.createdon,incCount,'projects_details','audit_insert','fieldorder');\r\n"
				+ "END IF;\r\n" + "	\r\n" + " IF (new.fieldvalue IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.fieldvalue','',new.fieldvalue,'Static',null,null,null,null,new.createdby,new.createdon,incCount,'projects_details','audit_insert','fieldvalue');\r\n"
				+ "END IF;\r\n" + "\r\n" + "IF (new.rowno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.rowno','',new.rowno,'Static',null,null,null,null,new.createdby,new.createdon,incCount,'projects_details','audit_insert','rowno');\r\n"
				+ "END IF;\r\n" + "\r\n" + "IF (new.subrowno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.subrowno','',new.subrowno,'Static',null,null,null,null,new.createdby,new.createdon,incCount,'projects_details','audit_insert','subrowno');\r\n"
				+ "END IF;\r\n" + "\r\n" + "IF (new.treatment_no IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.treatmentNo','',new.treatment_no,'Static',null,null,null,null,new.createdby,new.createdon,incCount,'projects_details','audit_insert','treatment_no');\r\n"
				+ "END IF;\r\n" + "\r\n" + "IF (new.type IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.tyep','',new.type,'Static',null,null,null,null,new.createdby,new.createdon,incCount,'projects_details','audit_insert','type');\r\n"
				+ "END IF; \r\n" + "\r\n" +

				" IF (new.projectsid_projectid IS NOT NULL) THEN\r\n"
				+ " select projectno from bedc.projects where projectid= new.projectsid_projectid into newProjectsId;  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('projectsid_projectid','',newProjectsId,'Dynamic',null,null,null,'',new.createdby,new.createdon,incCount,'projects_details','audit_insert','projectsid_projectid');\r\n"
				+ " END IF;   \r\n" + " \r\n" + " IF (new.statusid_statusid IS NOT NULL) THEN\r\n"
				+ " select statuscode from bedc.status_master where statusid= new.statusid_statusid into newStatusId;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('statusid_statusid','',newStatusId,'Dynamic',null,null,null,'',new.createdby,new.createdon,incCount,'projects_details','audit_insert','statusid_statusid');\r\n"
				+ "END IF;\r\n" + "\r\n" + "RETURN NEW;\r\n" + "\r\n"
				+ "ELSE IF(new.projectdetailsid=old.projectdetailsid) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.projectdetailsid,'projects_details',null,null,null,'projects_details_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+ "\r\n"
				+ "IF nullif(old.displayvalue,'') <> NULLIF(new.displayvalue,'') or new.displayvalue is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('displayvalue',old.displayvalue,new.displayvalue,'Static',null,null,null,new.update_reason,new.updated_by,new.updated_on,incCount,'projects_details','audit_update','displayvalue');\r\n"
				+ "END IF;\r\n" + "\r\n"
				+ "IF nullif(old.fieldname,'') <> NULLIF(new.fieldname,'') or new.fieldname is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES (actid,'fieldname',old.fieldname,new.fieldname,'Static',null,null,null,new.update_reason,new.updated_by,new.updated_on,incCount,'projects_details','audit_update','fieldname');\r\n"
				+ "END IF;\r\n" + "\r\n"
				+ "IF nullif(old.fieldorder,0) <> NULLIF(new.fieldorder,0) or new.fieldorder is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (id,field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.displyee.fieldorder',old.fieldorder,new.fieldorder,'Static',null,null,null,new.update_reason,new.updated_by,new.updated_on,incCount,'projects_details','audit_update','projectid','audit_update','fieldorder');\r\n"
				+ " END IF;\r\n"
				+ " IF nullif(old.fieldvalue,'') <> NULLIF(new.fieldvalue,'') or new.fieldvalue is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.fieldvalue',old.fieldvalue,new.fieldvalue,'Static',null,null,null,new.update_reason,old.createdby,old.createdon,incCount,'projects_details','audit_update','fieldvalue');\r\n"
				+ " END IF;\r\n" + " IF nullif(old.rowno,0) <> NULLIF(new.rowno,0) or new.rowno is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.displyee.rowno',old.rowno,new.rowno,'Static',null,null,null,new.update_reason,new.updated_by,new.updated_on,incCount,'projects_details','audit_update','rowno');\r\n"
				+ "END IF; \r\n" + " \r\n"
				+ " IF nullif(old.subrowno,0) <> NULLIF(new.subrowno,0) or new.subrowno is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.displyee.subrowno',old.subrowno,new.subrowno,'Static',null,null,null,new.update_reason,new.updated_by,new.updated_on,incCount,'projects_details','audit_update','subrowno');\r\n"
				+ "END IF; \r\n"
				+ "IF nullif(old.treatment_no,null) <> NULLIF(new.treatment_no,null) or new.treatment_no is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.treatmentNo',old.treatment_no,new.treatment_no,'Static',null,null,null,new.update_reason,old.createdby,old.createdon,incCount,'projects_details','audit_update','treatment_no');\r\n"
				+ "END IF;\r\n" + "IF nullif(old.type,'') <> NULLIF(new.type,'') or new.type is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.tyep',old.type,new.type,'Static',null,null,null,new.update_reason,new.updated_by,new.updated_on,incCount,'projects_details','audit_update','type');\r\n"
				+ "END IF;\r\n" +

				"select projectno from bedc.projects where projectid= new.projectsid_projectid into newProjectsId;  \r\n"
				+ "select projectno from bedc.projects where projectid= old.projectsid_projectid into oldProjectsId;  \r\n"
				+ "IF nullif(old.projectsid_projectid,null) <> NULLIF(new.projectsid_projectid,null) or new.projectsid_projectid is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('projectsid_projectid',oldProjectsId,newProjectsId,'Dynamic',null,null,null,new.update_reason,new.updated_by,new.updated_on,incCount,'projects_details','audit_update','projectsid_projectid');\r\n"
				+ "END IF;\r\n"
				+ " select statuscode from bedc.status_master where statusid= new.statusid_statusid into newStatusId;  \r\n"
				+ " select statuscode from bedc.status_master where statusid= old.statusid_statusid into oldStatusId;  \r\n"
				+ "IF nullif(old.statusid_statusid,null) <> NULLIF(new.statusid_statusid,null) or new.statusid_statusid is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('statusid_statusid',oldStatusId,newStatusId,'Dynamic',null,null,null,new.update_reason,new.updated_by,old.createdby,old.createdon,'projects_details','audit_update','statusid_statusid');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");
		stmt.execute("CREATE TRIGGER projects_details_trgr"
				+ "   AFTER INSERT OR UPDATE  OF projectdetailsid,createdby,createdon,displayvalue,fieldname,fieldorder,fieldvalue,rowno,status,subrowno,type,update_reason,updated_by,updated_on,projectsid_projectid,statusid_statusid  ON bedc.projects_details\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.projects_details_fun();");

	}

	private void generateProjects(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.projects_fun() RETURNS TRIGGER AS $$\r\n" + "declare \r\n"
				+ "   incCount integer;\r\n" + "   actid integer;\r\n" + "  newBuildStudyStatus character varying;\r\n"
				+ "  oldBuildStudyStatus character varying;\r\n" + "  newProjectStatus character varying;\r\n"
				+ "  oldProjectStatus character varying;\r\n" + "   randamstatus character varying;\r\n"
				+ "   newrandomstatus character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.projectid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"	 INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	 VALUES (null,'CREATE',new.projectid,'projects',null,null,null,'projects_creation',null)RETURNING id INTO incCount; \r\n"
				+ "	IF (new.projectno IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name)\r\n"
				+ "	VALUES ('label.projectno','',new.projectno,'static',null,null,null,' ',new.createdby,new.createdon,incCount,'projects');\r\n"
				+ "	end if;\r\n" + "	IF (new.randamization_role IS NOT NULL) THEN \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.displyee.randamization_role','',new.randamization_role,'static',null,null,null,' ',new.createdby,new.createdon,incCount,'projects','audit_insert','randomization_role');\r\n"
				+ "	end if;\r\n" + "	IF (new.roleid IS NOT NULL) THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.displyee.roleid','',new.roleid,'static',null,null,null,' ',new.createdby,new.createdon,incCount,'projects','audit_insert','roleid');\r\n"
				+ "	end if;\r\n" + "	if(new.buildstudystatus is not null) then		\r\n"
				+ "	select statuscode from bedc.status_master where statusid= new.buildstudystatus into newBuildStudyStatus;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('buildstudystatus','',newBuildStudyStatus,'dynamic',null,null,null,' ',new.createdby,new.createdon,incCount,'projects','audit_insert','buildstudystatus');\r\n"
				+ "	end if;\r\n" + "	if(new.randamizaion_status is not null) then\r\n"
				+ "	select statuscode from bedc.status_master where statusid= new.randamizaion_status into newrandomstatus;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('randamizaion_status','',newrandomstatus,'dynamic',null,null,null,' ',new.createdby,new.createdon,incCount,'projects','audit_insert','randamizaion_status');\r\n"
				+ "	end if;\r\n" + "	if(new.project_status is not null) then\r\n"
				+ "	select statuscode from bedc.status_master where statusid= new.project_status into newProjectStatus;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('project_status','',newProjectStatus,'dynamic',null,null,null,' ',new.createdby,new.createdon,incCount,'projects','audit_insert','project_status');\r\n"
				+ "	end if;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.projectid=old.projectid) THEN\r\n" + // UPDATE
																											// TRIGGER
				"	 INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	 VALUES (new.updated_by,'UPDATE',new.projectid,'projects',null,null,null,'projects_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+

				"	IF nullif(old.randamization_role,null) <> NULLIF(new.randamization_role,null) or new.roleid is null THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.displyee.randamization_role',old.randamization_role,new.randamization_role,'static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'projects','audit_update','randamization_role');\r\n"
				+ "	end if;\r\n"
				+ "	IF nullif(old.roleid, null) <> NULLIF(new.roleid, null) or new.roleid is null THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('label.displyee.roleid',old.roleid,new.roleid,'static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'projects','audit_update','roleid');\r\n"
				+ "	end if;\r\n"
				+ "    IF nullif(old.buildstudystatus,null) <> NULLIF(new.buildstudystatus,null)  THEN\r\n"
				+ "	select statuscode from bedc.status_master where statusid= new.buildstudystatus into newBuildStudyStatus;  \r\n"
				+ "	select statuscode from bedc.status_master where statusid= old.buildstudystatus into oldBuildStudyStatus;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('buildstudystatus',oldBuildStudyStatus,newBuildStudyStatus,'dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'projects','audit_update','buildstudystatus');\r\n"
				+ "    end if;\r\n"
				+ "	IF nullif(old.randamizaion_status,null) <> NULLIF(new.randamizaion_status,null)  THEN\r\n"
				+ "	select statuscode from bedc.status_master where statusid= new.randamizaion_status into newrandomstatus;  \r\n"
				+ "	select statuscode from bedc.status_master where statusid= old.randamizaion_status into randamstatus;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('randamizaion_status',randamstatus,newrandomstatus,'dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'projects','audit_update','randamizaion_status');\r\n"
				+ "	end if;\r\n" + "	IF nullif(old.project_status,null) <> NULLIF(new.project_status,null) THEN\r\n"
				+ "	select statuscode from bedc.status_master where statusid= old.project_status into oldProjectStatus;  \r\n"
				+ "	select statuscode from bedc.status_master where statusid= new.project_status into newProjectStatus;  \r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "	VALUES ('project_status',oldProjectStatus,newProjectStatus,'dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'projects','audit_update','project_status');\r\n"
				+ "	end if;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER projects_trgr"
				+ "   AFTER INSERT OR UPDATE  OF projectid,createdby,createdon,projectno,randamization_role,roleid,update_reason,updated_by,updated_on,buildstudystatus,randamizaion_status,project_status  ON bedc.projects\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.projects_fun();");

	}

	private void generateProjectsDetailsLog(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.projects_details_log_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   newProjectDetailsId character varying;\r\n" + "   oldProjectDetailsId character varying;\r\n"
				+ "   newStatusId character varying;\r\n" + "   oldStatusId character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.projectdetailslogid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.createdby,'CREATE',new.projectdetailslogid,'Project_details_log',null,null,null,'project_details_log_creation',new.createdon) RETURNING id INTO incCount; \r\n"
				+ "IF (new.fieldname IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.fieldname','',new.fieldname,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'project_details_log_creation','audit_insert','actvity_code');\r\n"
				+ "END IF;\r\n" + "IF (new.fieldvalue IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.fieldvalue','',new.fieldvalue,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'project_details_log_creation','audit_insert','fieldvalue');\r\n"
				+ "END IF;\r\n" + "IF (new.projectno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.projectno','',new.projectno,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'project_details_log_creation','audit_insert','projectno');\r\n"
				+ "END IF;\r\n" + "IF (new.rowno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.rowno','',new.rowno,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'project_details_log_creation','audit_insert','rowno');\r\n"
				+ "END IF;\r\n" + "IF (new.subrowno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.subrowno','',new.subrowno,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'project_details_log_creation','audit_insert','subrowno');\r\n"
				+ "END IF;\r\n" + "IF (new.type IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.tyep','',new.type,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'project_details_log_creation','audit_insert','type');\r\n"
				+ "END IF;\r\n"
				+ "select fieldvalue from bedc.projects_details where projectdetailsid= new.projectdeatailsid_projectdetailsid into newProjectDetailsId;  \r\n"
				+ "IF (new.projectdeatailsid_projectdetailsid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('projectdeatailsid_projectdetailsid','',newProjectDetailsId,'Dynamic',null,null,null,' ',new.createdby,new.createdon,incCount,'project_details_log_creation','audit_insert','projectdeatailsid_projectdetailsid');\r\n"
				+ "END IF;\r\n"
				+ "select statuscode from bedc.status_master where statusid= new.statusid_statusid into newStatusId;\r\n"
				+ "IF (new.statusid_statusid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('statusid_statusid','',newStatusId,'Dynamic',null,null,null,' ',new.createdby,new.createdon,incCount,'project_details_log_creation','audit_insert','statusid_statusid');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n"
				+ "ELSE IF (new.projectdetailslogid=old.projectdetailslogid) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.projectdetailslogid,'project_details',null,null,null,'project_details_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+

				" IF nullif(old.fieldname,'') <> NULLIF(new.fieldname,'') or new.fieldname is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('fieldname','',new.fieldname,'Static',null,null,null,null,new.updated_by,new.updated_on,incCount,'project_details_log','audit_update','fieldname');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.fieldvalue,'') <> NULLIF(new.fieldvalue,'') or new.fieldvalue is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('fieldvalue','',new.fieldvalue,'Static',null,null,null,null,new.updated_by,new.updated_on,incCount,'project_details_log','audit_update','fieldname');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.projectno,'') <> NULLIF(new.projectno,'') or new.projectno is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('projectno','',new.projectno,'Static',null,null,null,null,new.updated_by,new.updated_on,incCount,'project_details_log','audit_update','projectno');\r\n"
				+ "end if;\r\n" + "IF nullif(old.rowno,'') <> NULLIF(new.rowno,'') or new.rowno is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (id,field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name)\r\n"
				+ "VALUES ('label.displyee.rowno','',new.rowno,'Static',null,null,null,null,new.updated_by,new.updated_on,incCount,'project_details_log','audit_update','rowno');\r\n"
				+ "end if;\r\n"
				+ "IF nullif(old.subrowno,'') <> NULLIF(new.subrowno,'') or new.subrowno is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (id,field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name)\r\n"
				+ "VALUES ('label.displyee.subrowno','',new.subrowno,'Static',null,null,null,null,new.updated_by,new.updated_on,incCount,'project_details_log','audit_update','subrowno');\r\n"
				+ "end if;\r\n" + " IF nullif(old.type,'') <> NULLIF(new.type,'') or new.type is null THEN\r\n"
				+ " INSERT INTO bedc.audit_log (id,field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name)\r\n"
				+ " VALUES ('label.displyee.tyep','',new.type,'Static',null,null,null,null,new.updated_by,new.updated_on,incCount,'project_details_log','audit_update','type');\r\n"
				+ "  end if;\r\n" + " \r\n"
				+ "  IF nullif(old.statusid_statusid,null) <> NULLIF(new.statusid_statusid,null) or new.statusid is null THEN\r\n"
				+ " select statuscode from bedc.status_master where statusid= new.statusid_statusid into newStatusId;  \r\n"
				+ " select statuscode from bedc.status_master where statusid= new.statusid_statusid into oldStatusId;  \r\n"
				+ " INSERT INTO bedc.audit_log (id,field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name)\r\n"
				+ " VALUES ('statusid_statusid',oldStatusId,newStatusId,'Dynamic',null,null,null,'',new.updated_by,new.updated_on,incCount,'project_details_log''audit_update','statusid_statusid');\r\n"
				+ " end if;\r\n" + " \r\n"
				+ "   IF nullif(old.projectdeatailsid_projectdetailsid,null) <> NULLIF(new.projectdeatailsid_projectdetailsid,null) or new.projectno is null THEN\r\n"
				+ " select fieldvalue from bedc.projects_details where projectdetailsid= new.projectdeatailsid_projectdetailsid into newProjectDetailsId; \r\n"
				+ " select fieldvalue from bedc.projects_details where projectdetailsid= new.projectdeatailsid_projectdetailsid into oldProjectDetailsId; \r\n"
				+ "  INSERT INTO bedc.audit_log (id,field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name)\r\n"
				+ "  VALUES ('projectdetailsid',oldProjectDetailsId,newProjectDetailsId,'Dynamic',null,null,null,'',new.updated_by,new.updated_on,incCount,'project_details_log''audit_update','projectdeatailsid_projectdetailsid');\r\n"
				+ "  end if;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER projects_details_log_trgr"
				+ "   AFTER INSERT OR UPDATE  OF projectdetailslogid,createdby,createdon,fieldname,fieldvalue,projectno,rowno,subrowno,type,update_reason,updated_by,updated_on,projectdeatailsid_projectdetailsid,statusid_statusid  ON bedc.projects_details_log\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.projects_details_log_fun();");

	}

	private void generateProjectsLog(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.projects_log_fun() RETURNS TRIGGER AS $$\r\n" + "declare \r\n"
				+ "   incCount integer;\r\n" + "   actid integer;\r\n" + "   oldprojectsid character varying;\r\n"
				+ "   newprojectsid character varying;\r\n" + "   oldstatusid character varying;\r\n"
				+ "   newstatusid character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.projectlogid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.createdby,'CREATE',new.projectlogid,'projects_log',null,null,null,'projects_log_creation',new.createdon) RETURNING id INTO incCount; \r\n"
				+

				"IF (new.projectno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.projectno','',new.projectno,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'projects_log','audit_insert','projectno');\r\n"
				+ "END IF;\r\n"
				+ "select project_status from bedc.projects where projectid= new.projectsid_projectid into newprojectsid;\r\n"
				+ "IF (new.projectsid_projectid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('Project Id','',newprojectsid,'Dynamic',null,null,null,' ',new.createdby,new.createdon,incCount,'projects_log','audit_insert','projectsid_projectid');\r\n"
				+ "END IF;\r\n"
				+ "select statuscode from bedc.status_master where statusid= new.statusid_statusid into newstatusid;\r\n"
				+ "IF (new.statusid_statusid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.statusid_statusid','',new.statusid_statusid,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'projects_log','audit_insert','statusid_statusid');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.projectlogid=old.projectlogid) THEN\r\n" + // UPDATE
																												// TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.projectlogid,'projects_log',null,null,null,'projects_log_updation',new.updated_on)\r\n"
				+ "RETURNING id INTO incCount;\r\n" + "IF (new.projectno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.projectno','',new.projectno,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'projects_log','audit_update','projectno');\r\n"
				+ "END IF;\r\n"
				+ "select project_status from bedc.projects where projectid= old.projectsid_projectid into oldprojectsid;\r\n"
				+ "select project_status from bedc.projects where projectid= new.projectsid_projectid into newprojectsid;\r\n"
				+ "IF (new.projectsid_projectid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('Project Id','',new.projectsid_projectid,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'projects_log','audit_update','projectsid_projectid');\r\n"
				+ "END IF;\r\n"
				+ "select statuscode from bedc.status_master where statusid= old.statusid_statusid into oldstatusid;\r\n"
				+ "select statuscode from bedc.status_master where statusid= new.statusid_statusid into newstatusid;\r\n"
				+ "IF (new.statusid_statusid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.statusid_statusid','',new.statusid_statusid,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'projects_log','audit_update','statusid_statusid');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER projects_log_trgr"
				+ "   AFTER INSERT OR UPDATE  OF projectlogid,createdby,createdon,projectno,update_reason,updated_by,updated_on,projectsid_projectid,statusid_statusid\r\n"
				+ "  ON bedc.projects_log\r\n" + "    FOR EACH ROW\r\n"
				+ "    EXECUTE FUNCTION bedc.projects_log_fun();");
	}

	private void generateRandomizationFileStatus(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.randomization_file_status_fun() RETURNS TRIGGER AS $$\r\n"
				+ "\r\n" + "declare \r\n" + " incCount integer;\r\n" + " actid integer;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.id IS NULL) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (null,'CREATE',new.id,'randomization_file_status',null,null,null,'randomization_file_status_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+ "IF (new.codetype IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.codetype','',new.codetype,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'randomization_file_status','audit_insert','codetype');\r\n"
				+ "end if;\r\n" + "IF (new.noofperiod IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.noofperiods','',new.noofperiod,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'randomization_file_status','audit_insert','noofperiod');\r\n"
				+ "end if;\r\n" + "IF (new.noofsubject IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.noofsubjects','',new.noofsubject,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'randomization_file_status','audit_insert','noofsubject');\r\n"
				+ "end if; \r\n" + "IF (new.projectid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.projectno','',new.projectid,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'randomization_file_status','audit_insert','projectid');\r\n"
				+ "end if;\r\n" + "IF (new.status IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.status','',new.status,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'randomization_file_status','audit_insert','status');\r\n"
				+ "end if;\r\n" + "RETURN new;\r\n" +
				// update
				"ELSE IF (new.id=old.id) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.update_by,'UPDATE',new.id,'randomization_file_status',null,null,null,'randomization_file_status_updation',new.update_on)RETURNING id INTO incCount;\r\n"
				+ "IF nullif(old.codetype,'') <> NULLIF(new.codetype,'') or new.codetype is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.codetype',old.codetype,new.codetype,'Static',null,null,null,new.update_reason,new.update_by,new.update_on,incCount,'randomization_file_status','audit_update','codetype');\r\n"
				+ "end if;\r\n"
				+ "IF nullif(old.noofperiod,null) <> NULLIF(new.noofperiod,null) or new.noofperiod is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.noofperiods',old.noofperiod,new.noofperiod,'Static',null,null,null,new.update_reason,new.update_by,new.update_on,incCount,'randomization_file_status','audit_update','noofperiod');\r\n"
				+ "end if;\r\n"
				+ "IF nullif(old.noofsubject,'') <> NULLIF(new.noofsubject,'') or new.noofsubject is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.noofsubjects',old.noofsubject,new.noofsubject,'Static',null,null,null,new.update_reason,new.update_by,new.update_on,incCount,'randomization_file_status','audit_update','noofsubject');\r\n"
				+ "end if;\r\n"
				+ "IF nullif(old.projectid,null) <> NULLIF(new.projectid,null) or new.projectid is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.projectno',old.projectid,new.projectid,'Static',null,null,null,new.update_reason,new.update_by,new.update_on,incCount,'randomization_file_status','audit_update','projectid');\r\n"
				+ "end if;\r\n" + "IF nullif(old.status,'') <> NULLIF(new.status,'') or new.status is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.status',old.status,new.status,'Static',null,null,null,new.update_reason,new.update_by,new.update_on,incCount,'randomization_file_status','audit_update','status');\r\n"
				+ "end if;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n" +

				"$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER randomization_file_status_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,codetype,created_by,created_on,noofperiod,noofsubject,projectid,status,update_by,update_on,update_reason  ON bedc.randomization_file_status\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.randomization_file_status_fun();");

	}

	private void generateRandomizationReviewAudit(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.randomization_review_audit_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   newUserId character varying;\r\n" + "   oldUserId character varying;\r\n"
				+ "   newRoleId character varying;\r\n" + "   oldRoleId character varying;\r\n"
				+ "   newProject character varying;\r\n" + "   oldProject character varying;\r\n"
				+ "   newrole character varying;\r\n" + "   oldrole character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (null,'CREATE',new.id,'randomization_review_audit',null,null,null,'randomization_review_audit_creation',null)RETURNING id INTO incCount; \r\n"
				+

				"IF (new.in_the_flow IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.in_the_flow','',new.in_the_flow,'Static',null,null,null,new.comment,null,now(),incCount,'randomization_review_audit','audit_insert','in_the_flow');\r\n"
				+ "end if;\r\n" + "IF (new.projectid IS NOT NULL) THEN\r\n"
				+ "select projectNo from bedc.projects where projectId= new.projectid into newProject;  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.crfData.study','',newProject,'Static',null,null,null,new.comment,null,now(),incCount,'randomization_review_audit','audit_insert','projectid');\r\n"
				+ "end if;\r\n" + "IF (new.reviewstate IS NOT NULL) THEN\r\n"
				+ "select role from bedc.epk_role_master where roleId= new.reviewstate into newrole;  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.reviewstate','',newrole,'Static',null,null,null,new.comment,null,now(),incCount,'randomization_review_audit','audit_insert','reviewstate');\r\n"
				+ "end if;\r\n" + "IF (new.status IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.swastatus','',new.status,'Static',null,null,null,new.comment,null,now(),incCount,'randomization_review_audit','audit_insert','status');\r\n"
				+ "end if;\r\n" + "IF (new.user_id IS NOT NULL) THEN\r\n"
				+ "select user_name from bedc.epk_user_master where userid= new.user_id into newUserId;  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('User','',newUserId,'Dynamic',null,null,null,'',null,now(),incCount,'randomization_review_audit','audit_insert','user_id');\r\n"
				+ "end if;\r\n" + "IF (new.role_id IS NOT NULL) THEN\r\n"
				+ "select role from bedc.epk_role_master where roleid= new.role_id into newRoleId;  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('Role','',newRoleId,'Dynamic',null,null,null,'',null,now(),incCount,'randomization_review_audit','audit_insert','role_id');\r\n"
				+ "end if;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (null,'UPDATE',new.id,'randomization_review_audit',null,null,null,'randomization_review_audit_updation',null)RETURNING id INTO incCount;\r\n"
				+

				"IF nullif(old.in_the_flow,null) <> NULLIF(new.in_the_flow,null) or new.in_the_flow is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.in_the_flow','',new.in_the_flow,'Static',null,null,null,new.comment,null,now(),incCount,'randomization_review_audit','audit_update','in_the_flow');\r\n"
				+ "    END IF;\r\n"
				+ "IF nullif(old.reviewstate,null) <> NULLIF(new.reviewstate,null) or new.reviewstate is null THEN\r\n"
				+ "select role from bedc.epk_role_master where roleId= new.reviewstate into newrole;  \r\n"
				+ "select role from bedc.epk_role_master where roleId= old.reviewstate into oldrole;  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.displyee.reviewstate','oldrole',newrole,'Static',null,null,null,new.comment,null,now(),incCount,'randomizatio_review_audit','audit_update','reviewstate');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.projectid,null) <> NULLIF(new.projectid,null) or new.projectid is null THEN\r\n"
				+ "select projectNo from bedc.projects where projectId= new.projectid into newProject;  \r\n"
				+ "select projectNo from bedc.projects where projectId= old.projectid into oldProject;  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.crfData.study','oldProject',newProject,'Static',null,null,null,new.comment,null,now(),incCount,'randomization_review_audit','audit_update','projectid');\r\n"
				+ "END IF;\r\n" + "IF nullif(old.status,'') <> NULLIF(new.status,'') or new.status is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.swastatus','',new.status,'Static',null,null,null,new.comment,null,now(),incCount,'randomization_review_audit','audit_update','status');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.user_id,null) <> NULLIF(new.user_id,null) or new.user_id is null THEN\r\n"
				+ "select user_name from bedc.epk_user_master where userid= new.user_id into newUserId;  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('User','',newUserId,'Dynamic',null,null,null,new.comment,null,now(),incCount,'randomization_review_audit','audit_update','user_id');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.role_id,null) <> NULLIF(new.role_id,null) or new.role_id is null THEN\r\n"
				+ "select role from bedc.epk_role_master where roleid= new.role_id into newRoleId;  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('Role','',newRoleId,'Dynamic',null,null,null,new.comment,null,now(),incCount,'randomization_review_audit','audit_update','role_id');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER randomization_review_audit_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,comment,date,in_the_flow,projectid,reviewstate,status,role_id,user_id  ON bedc.randomization_review_audit\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.randomization_review_audit_fun();");

	}

	private void generateRoleWiseModules(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.role_wise_modules_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   newAppSideMenus character varying;\r\n" + "   oldAppSideMenus character varying;\r\n"
				+ "   newUserMain character varying;\r\n" + "   oldUserMain character varying;\r\n"
				+ "   newRoleId character varying;\r\n" + "   oldRoleId character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " 	VALUES (null,'CREATE',new.id,'role_wise_modules',null,null,null,'role_wise_modules_creation',null)RETURNING id INTO incCount; \r\n"
				+ "	IF (new.addstatus IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "        VALUES ('label.displyee.addstatus','',new.addstatus,'Static',null,null,null,null,new.created_by,new.created_on,incCount,'role_wise_modules','audit_insert','addstatus');\r\n"
				+ "    END IF;\r\n" + "	IF (new.deactive IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "        VALUES ('label.displyee.deactive','',new.deactive,'Static',null,null,null,null,new.created_by,new.created_on,incCount,'role_wise_modules','audit_insert','deactive');\r\n"
				+ "    END IF;\r\n" + "	IF (new.review IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "       VALUES ('label.displyee.review','',new.review,'Static',null,null,null,null,new.created_by,new.created_on,incCount,'role_wise_modules','audit_insert','review');\r\n"
				+ "    END IF;\r\n" + "	IF (new.status IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "        VALUES ('label.status','',new.status,'Static',null,null,null,null,new.created_by,new.created_on,incCount,'role_wise_modules','audit_insert','status');\r\n"
				+ "    END IF;\r\n" + "	IF (new.update IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "        VALUES ('label.displyee.update','',new.update,'Static',null,null,null,null,new.created_by,new.created_on,incCount,'role_wise_modules','audit_insert','update');\r\n"
				+ "    END IF;\r\n"
				+ "     select name from bedc.app_side_menus where id= new.app_side_menu into newAppSideMenus;  \r\n"
				+ "	 IF (new.app_side_menu IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "        VALUES ('app_side_menu','',newAppSideMenus,'Dynamic',null,null,null,'',new.created_by,new.created_on,incCount,'role_wise_modules','audit_insert','app_side_menu');\r\n"
				+ "     END IF;\r\n"
				+ "    select name from bedc.app_menus where  id= new.user_main into newUserMain;  \r\n"
				+ "	IF (new.user_main IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "        VALUES ('user_main','',newUserMain,'Dynamic',null,null,null,'',new.created_by,new.created_on,incCount,'role_wise_modules','audit_insert','user_main');\r\n"
				+ "     END IF;\r\n"
				+ "     select roledesc from bedc.epk_role_master where roleid= new.role_id into newRoleId;  \r\n"
				+ "     IF (new.role_id IS NOT NULL) THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "        VALUES ('role_id','',newRoleId,'Dynamic',null,null,null,'',new.created_by,new.created_on,incCount,'role_wise_modules','audit_insert','role_id');\r\n"
				+ "     END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	 VALUES (new.updated_by,'UPDATE',new.id,'project_details',null,null,null,'project_details_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+

				"	IF nullif(old.addstatus,'') <> NULLIF(new.addstatus,'') or new.addstatus is null THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "       VALUES ('label.displyee.addstatus',old.addstatus,new.addstatus,'Static',null,null,null,null,new.updated_by,new.updated_on,incCount,'role_wise_modules','action_update','addstatus');\r\n"
				+ "     END IF;\r\n"
				+ "	IF nullif(old.deactive,'') <> NULLIF(new.deactive,'') or new.deactive is null THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "        VALUES ('label.displyee.deactive',old.deactive,new.deactive,'Static',null,null,null,null,new.updated_by,new.updated_on,incCount,'role_wise_modules','action_update','deactive');\r\n"
				+ "     END IF;\r\n"
				+ "	IF nullif(old.review,'') <> NULLIF(new.review,'') or new.review is null THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "        VALUES ('label.displyee.review',old.review,new.review,'Static',null,null,null,null,new.updated_by,new.updated_on,incCount,'role_wise_modules','action_update','review');\r\n"
				+ "     END IF;\r\n"
				+ "	IF nullif(old.status,'') <> NULLIF(new.status,'') or new.status is null THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "        VALUES ('label.status','',new.status,'Static',null,null,null,null,new.updated_by,new.updated_on,incCount,'role_wise_modules','action_update','status');\r\n"
				+ "     END IF;\r\n"
				+ "	IF nullif(old.update,'') <> NULLIF(new.update,'') or new.update is null THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "        VALUES ('label.displyee.update',old.update,new.update,'Static',null,null,null,nullnew.updated_by,new.updated_on,incCount,'role_wise_modules','action_update','update');\r\n"
				+ "     END IF;\r\n"
				+ "    select name from bedc.app_side_menus where id= new.app_side_menu into newAppSideMenus;  \r\n"
				+ "  	IF nullif(old.app_side_menu,null) <> NULLIF(new.app_side_menu,null) or new.app_side_menu is null THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "        VALUES ('app_side_menu','',newAppSideMenus,'Dynamic',null,null,null,'',new.updated_by,new.updated_on,incCount,'role_wise_modules','action_update','app_side_menu');\r\n"
				+ "      END IF;\r\n"
				+ "    select name from bedc.app_menus where  id= new.user_main into newUserMain;  \r\n"
				+ "    IF nullif(old.user_main,null) <> NULLIF(new.user_main,null) or new.user_main is null THEN\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "        VALUES ('user_main','',newUserMain,'Dynamic',null,null,null,'',new.updated_by,new.updated_on,incCount,'role_wise_modules','action_update','user_main');\r\n"
				+ "      END IF;\r\n"
				+ "    select roledesc from bedc.epk_role_master where roleid= new.role_id into newRoleId;  \r\n"
				+ "    IF nullif(old.role_id,null) <> NULLIF(new.role_id,null) or new.role_id is null THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "    VALUES ('role_id','',newRoleId,'Dynamic',null,null,null,'',new.updated_by,new.updated_on,incCount,'role_wise_modules','action_update','role_id');\r\n"
				+ "    END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER role_wise_modules_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,addstatus,created_by,created_on,deactive,review,status,update,update_reason,updated_by,updated_on,app_side_menu,role_id,user_main  ON bedc.role_wise_modules\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.role_wise_modules_fun();");

	}

	private void generateStudyActivityDataCheckIn(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_activity_data_check_in_fun() RETURNS TRIGGER AS $$\r\n"
				+ "\r\n" + "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "  newGlobalValues character varying;\r\n" + "  oldGlobalValues character varying;\r\n"
				+ "  newSaParameter character varying;\r\n" + "  oldSaParameter character varying;\r\n"
				+ "  newSaData character varying;\r\n" + "  oldSaData character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.id IS NULL) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " VALUES (null,'CREATE',new.id,'study_activity_data_check_in',null,null,null,'study_activity_data_check_in_creation',null)RETURNING id INTO incCount; \r\n"
				+ "IF (new.deviation IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.deviation','',new.deviation,'static',null,null,null,new.comments,new.created_by,new.created_on,incCount,'study_activity_data_check_in','audit_insert','deviation');\r\n"
				+ "END IF;\r\n" + "IF (new.prefered_by IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.prefered_by','',new.prefered_by,'static',null,null,null,new.comments,new.created_by,new.created_on,incCount,'study_activity_data_check_in','audit_insert','prefered_by');\r\n"
				+ " END IF;\r\n" + "IF (new.prefered_on IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.prefered_on','',new.prefered_on,'static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_check_in','audit_insert','prefered_on');\r\n"
				+ "END IF;\r\n" + "IF (new.value IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.values','',new.value,'static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_check_in','audit_insert','value');\r\n"
				+ "END IF;\r\n"
				+ "select name from bedc.global_values where id= new.global_values into newGlobalValues;   \r\n"
				+ "IF (new.global_values IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('global_values','',newGlobalValues,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_check_in','audit_insert','global_values');\r\n"
				+ "END IF;\r\n"
				+ "select status from bedc.study_activity_data where id= new.sa_data into newSaData;   \r\n"
				+ "IF (new.sa_data IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('sa_data','',newSaData,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_check_in','audit_insert','sa_data');\r\n"
				+ "END IF;\r\n"
				+ "select study_activity from bedc.study_activites_parameters where id= new.saparameter into newSaParameter;    \r\n"
				+ "IF (new.saparameter IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('saparameter','',newSaParameter,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_check_in','audit_insert','saparameter');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (null,'UPDATE',new.id,'study_activity_data_check_in',null,null,null,'study_activity_data_check_in_updation',null)RETURNING id INTO incCount;\r\n"
				+ "IF cast(old.deviation as varchar) <> cast(new.deviation as varchar) or new.deviation is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.deviation',old.deviation,new.deviation,'static',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_check_in','audit_update','deviation');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.prefered_by,'') <> NULLIF(new.prefered_by,'') or new.prefered_by is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.prefered_by',old.prefered_by,new.prefered_by,'static',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_check_in','audit_update','prefered_by');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.prefered_on,null) <> NULLIF(new.prefered_on,null) or new.prefered_on is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.prefered_on',old.prefered_on,new.prefered_on,'static',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_check_in','audit_update','prefered_on');\r\n"
				+ "END IF;\r\n" + "IF nullif(old.value,'') <> NULLIF(new.value,'') or new.value is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.values',old.value,new.value,'static',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_check_in','audit_update','value');\r\n"
				+ "END IF;\r\n"
				+ "select name from bedc.global_values where id= new.global_values into newGlobalValues;\r\n"
				+ "select name from bedc.global_values where id= new.global_values into oldGlobalValues;   \r\n"
				+ "IF nullif(old.global_values,null) <> NULLIF(new.global_values,null) or new.global_values is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('global_values',oldGlobalValues,newGlobalValues,'Dynamic',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_check_in','audit_update','global_values');\r\n"
				+ "END IF;\r\n"
				+ "select status from bedc.study_activity_data where id= new.sa_data into newSaData; \r\n"
				+ "select status from bedc.study_activity_data where id= new.sa_data into oldSaData;   \r\n"
				+ "IF nullif(old.sa_data,null) <> NULLIF(new.sa_data,null) or new.sa_data is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('sa_data',oldSaData,newSaData,'Dynamic',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_check_in','audit_update','sa_data');\r\n"
				+ "END IF;\r\n"
				+ "select study_activity from bedc.study_activites_parameters where id= new.saparameter into newSaParameter;    \r\n"
				+ "select study_activity from bedc.study_activites_parameters where id= new.saparameter into oldSaParameter;    \r\n"
				+ "IF nullif(old.saparameter,null) <> NULLIF(new.saparameter,null) or new.saparameter is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('saparameter',oldSaParameter,newSaParameter,'Dynamic',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_check_in','audit_update','saparameter');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;"
				+ "$$ LANGUAGE 'plpgsql';");
		
		stmt.execute("CREATE TRIGGER study_activity_data_check_in_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,comments,created_by,created_on,deviation,file,prefered_by,prefered_on,update_reason,updated_by,updated_on,value,global_values,sa_data,saparameter  ON bedc.study_activity_data_check_in\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_activity_data_check_in_fun();");

	}

	private void generateStudyActivityDataCheckOut(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_activity_data_check_out_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "  newGlobalValues character varying;\r\n" + "  oldGlobalValues character varying;\r\n"
				+ "  newSaParameter character varying;\r\n" + "  oldSaParameter character varying;\r\n"
				+ "  newSaData character varying;\r\n" + "  oldSaData character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " VALUES (null,'CREATE',new.id,'study_activity_data_check_out',null,null,null,'study_activity_data_check_in_creation',null)RETURNING id INTO incCount; \r\n"
				+

				"IF (new.deviation IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.deviation','',new.deviation,'static',null,null,null,new.comments,new.created_by,new.created_on,incCount,'study_activity_data_check_out','audit_insert','deviation');\r\n"
				+ "END IF;\r\n" + "IF (new.prefered_by IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.prefered_by','',new.prefered_by,'static',null,null,null,new.comments,new.created_by,new.created_on,incCount,'study_activity_data_check_out','audit_insert','prefered_by');\r\n"
				+ " END IF;\r\n" + "IF (new.prefered_on IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.prefered_on','',new.prefered_on,'static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_check_out','audit_insert','prefered_on');\r\n"
				+ "END IF;\r\n" + "IF (new.value IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.values','',new.value,'static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_check_out','audit_insert','value');\r\n"
				+ "END IF;\r\n"
				+ "select name from bedc.global_values where id= new.global_values into newGlobalValues;   \r\n"
				+ "IF (new.global_values IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('global_values','',newGlobalValues,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_check_out','audit_insert','global_values');\r\n"
				+ "END IF;\r\n"
				+ "select status from bedc.study_activity_data where id= new.sa_data into newSaData;   \r\n"
				+ "IF (new.sa_data IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('sa_data','',newSaData,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_check_out','audit_insert','sa_data');\r\n"
				+ "END IF;\r\n"
				+ "select study_activity from bedc.study_activites_parameters where id= new.saparameter into newSaParameter;    \r\n"
				+ "IF (new.saparameter IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('saparameter','',newSaParameter,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_check_out','audit_insert','saparameter');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (null,'UPDATE',new.id,'study_activity_data_check_in',null,null,null,'study_activity_data_check_in_updation',null)RETURNING id INTO incCount;\r\n"
				+

				"IF cast(old.deviation as varchar) <> cast(new.deviation as varchar) or new.deviation is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.deviation',old.deviation,new.deviation,'static',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_check_out','audit_update','deviation');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.prefered_by,'') <> NULLIF(new.prefered_by,'') or new.prefered_by is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.prefered_by',old.value,new.value,'static',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_check_out','audit_update','prefered_by');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.prefered_on,null) <> NULLIF(new.prefered_on,null) or new.prefered_on is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.prefered_on',old.prefered_on,new.prefered_on,'static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_activity_data_check_out','audit_update','prefered_on');\r\n"
				+ "END IF;\r\n" + "IF nullif(old.value,'') <> NULLIF(new.value,'') or new.value is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.values',old.value,new.value,'static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_activity_data_check_out','audit_update','value');\r\n"
				+ "END IF;\r\n"
				+ "select name from bedc.global_values where id= new.global_values into newGlobalValues;\r\n"
				+ "select name from bedc.global_values where id= new.global_values into oldGlobalValues;   \r\n"
				+ "IF nullif(old.global_values,null) <> NULLIF(new.global_values,null) or new.global_values is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('global_value',oldGlobalValues,newGlobalValues,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_activity_data_check_out','audit_update','global_values');\r\n"
				+ "END IF;\r\n"
				+ "select status from bedc.study_activity_data where id= new.sa_data into newSaData; \r\n"
				+ "select status from bedc.study_activity_data where id= new.sa_data into oldSaData;   \r\n"
				+ "IF nullif(old.sa_data,null) <> NULLIF(new.sa_data,null) or new.sa_data is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('sa_data',oldSaData,newSaData,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_activity_data_check_out','audit_update','sa_data');\r\n"
				+ "END IF;\r\n"
				+ "select study_activity from bedc.study_activites_parameters where id= new.saparameter into newSaParameter;    \r\n"
				+ "select study_activity from bedc.study_activites_parameters where id= new.saparameter into oldSaParameter;    \r\n"
				+ "IF nullif(old.saparameter,null) <> NULLIF(new.saparameter,null) or new.saparameter is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('saparameter',oldSaParameter,newSaParameter,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_activity_data_check_out','audit_update','saparameter');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_activity_data_check_out_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,comments,created_by,created_on,deviation,file,prefered_by,prefered_on,update_reason,updated_by,updated_on,value,global_values,sa_data,saparameter  ON bedc.study_activity_data_check_out\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_activity_data_check_out_fun();");

	}

	private void generateStudyActDataExecution(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_activity_data_execution_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "  newGlobalValues character varying;\r\n" + "  oldGlobalValues character varying;\r\n"
				+ "  newSaParameter character varying;\r\n" + "  oldSaParameter character varying;\r\n"
				+ "  newSaData character varying;\r\n" + "  oldSaData character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n" +

				"IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " VALUES (null,'CREATE',new.id,'study_activity_data_check_out',null,null,null,'study_activity_data_check_in_creation',null)RETURNING id INTO incCount; \r\n"
				+

				"IF (new.deviation IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.deviation','',new.deviation,'static',null,null,null,new.comments,new.created_by,new.created_on,incCount,'study_activity_data_execution','audit_insert','deviation');\r\n"
				+ "END IF;\r\n" +

				"IF (new.prefered_by IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.prefered_by','',new.prefered_by,'static',null,null,null,new.comments,new.created_by,new.created_on,incCount,'study_activity_data_execution','audit_insert','prefered_by');\r\n"
				+ " END IF;\r\n" + "IF (new.prefered_on IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.prefered_on','',new.prefered_on,'static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_execution','audit_insert','prefered_on');\r\n"
				+ "END IF;\r\n" + "IF (new.value IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.values','',new.value,'static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_execution','audit_insert','value');\r\n"
				+ "END IF;\r\n"
				+ "select name from bedc.global_values where id= new.global_values into newGlobalValues;   \r\n"
				+ "IF (new.global_values IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('global_value','',newGlobalValues,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_execution','audit_insert','global_values');\r\n"
				+ "END IF;\r\n"
				+ "select status from bedc.study_activity_data where id= new.sa_data into newSaData;   \r\n"
				+ "IF (new.sa_data IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('sa_data','',newSaData,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_execution','audit_insert','sa_data');\r\n"
				+ "END IF;\r\n"
				+ "select study_activity from bedc.study_activites_parameters where id= new.saparameter into newSaParameter;    \r\n"
				+ "IF (new.saparameter IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('saparameter','',newSaParameter,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_data_execution','audit_insert','saparameter');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (null,'UPDATE',new.id,'study_activity_data_check_in',null,null,null,'study_activity_data_check_in_updation',null)RETURNING id INTO incCount;\r\n"
				+

				"IF cast(old.deviation as varchar) <> cast(new.deviation as varchar) or new.deviation is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.deviation',old.deviation,new.deviation,'static',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_execution','audit_update','deviation');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.prefered_by,'') <> NULLIF(new.prefered_by,'') or new.prefered_by is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.prefered_by',old.value,new.value,'static',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_execution','audit_update','prefered_by');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.prefered_on,null) <> NULLIF(new.prefered_on,null) or new.prefered_on is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.prefered_on',old.prefered_on,new.prefered_on,'static',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_execution','audit_update','prefered_on');\r\n"
				+ "END IF;\r\n" + "IF nullif(old.value,'') <> NULLIF(new.value,'') or new.value is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.values',old.value,new.value,'static',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_execution','audit_update','value');\r\n"
				+ "END IF;\r\n"
				+ "select name from bedc.global_values where id= new.global_values into newGlobalValues;\r\n"
				+ "select name from bedc.global_values where id= new.global_values into oldGlobalValues;   \r\n"
				+ "IF nullif(old.global_values,null) <> NULLIF(new.global_values,null) or new.global_values is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('global_value',oldGlobalValues,newGlobalValues,'Dynamic',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_execution','audit_update','global_values');\r\n"
				+ "END IF;\r\n"
				+ "select status from bedc.study_activity_data where id= new.sa_data into newSaData; \r\n"
				+ "select status from bedc.study_activity_data where id= new.sa_data into oldSaData;   \r\n"
				+ "IF nullif(old.sa_data,null) <> NULLIF(new.sa_data,null) or new.sa_data is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('sa_data',oldSaData,newSaData,'Dynamic',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_execution','audit_update','sa_data');\r\n"
				+ "END IF;\r\n"
				+ "select study_activity from bedc.study_activites_parameters where id= new.saparameter into newSaParameter;    \r\n"
				+ "select study_activity from bedc.study_activites_parameters where id= new.saparameter into oldSaParameter;    \r\n"
				+ "IF nullif(old.saparameter,null) <> NULLIF(new.saparameter,null) or new.saparameter is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('saparameter',oldSaParameter,newSaParameter,'Dynamic',null,null,null,new.comments,new.updated_by,new.updated_on,incCount,'study_activity_data_execution','audit_update','saparameter');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_activity_data_execution_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,comments,created_by,created_on,deviation,file,prefered_by,prefered_on,update_reason,updated_by,updated_on,value,global_values,sa_data,saparameter  ON bedc.study_activity_data_execution\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_activity_data_execution_fun();");

	}

	private void generateStudyActivityRules(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_activity_rules_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "  newSourceParameter character varying;\r\n" + "  oldSourceParameter character varying;\r\n"
				+ "  newSourceActivity character varying;\r\n" + "  oldSourceActivity character varying;\r\n"
				+ "  newGlobalValId character varying;\r\n" + "  oldGlobalValId character varying;\r\n"
				+ "  newDestParameter character varying;\r\n" + "  oldDestParameter character varying;\r\n"
				+ "   newDestactivity character varying;\r\n" + "   oldDestactivity character varying;\r\n"
				+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n" + " select count(*) from bedc.audit_log into actid;\r\n"
				+ " actid := actid +1;\r\n" + "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				" INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " VALUES (null,'CREATE',new.id,'study_activity_rules',null,null,null,'study_activity_rules_creation',null)RETURNING id INTO incCount; \r\n"
				+ "IF (new.con_firstval IS NOT NULL) THEN\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.con_firstval','',new.con_firstval,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','con_firstval');\r\n"
				+ "END IF;\r\n" +

				"IF (new.con_secondval IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.con_secondval','',new.con_secondval,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','con_secondval');\r\n"
				+ "END IF; \r\n" + "IF (new.condition IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.condition','',new.condition,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','condition');\r\n"
				+ "END IF; \r\n" + "IF (new.depndent_activities IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.depndent_activities','',new.depndent_activities,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','depndent_activities');\r\n"
				+ "END IF;\r\n" +

				"IF (new.dependent_apply_for IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.dependent_apply_for','',new.dependent_apply_for,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','dependent_apply_for');\r\n"
				+ "END IF;\r\n" +

				"IF (new.dest_parameter_global_val IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.dest_parameter_global_val','',new.dest_parameter_global_val,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','dest_parameter_global_val');\r\n"
				+ "END IF;\r\n" +

				"IF (new.dest_prameter_global_lsp_id IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.dest_prameter_global_lsp_id','',new.dest_prameter_global_lsp_id,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','dest_prameter_global_lsp_id');\r\n"
				+ "END IF;\r\n" +

				"IF (new.from_range IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.from_range','',new.from_range,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','from_range');\r\n"
				+ "END IF;\r\n" +

				"IF (new.key_name IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.key_name','',new.key_name,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','key_name');\r\n"
				+ "END IF;\r\n" +

				"IF (new.key_value IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.key_value','',new.key_value,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','key_value');\r\n"
				+ "END IF;\r\n" +

				"IF (new.multi_param IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.multi_param','',new.multi_param,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','multi_param');\r\n"
				+ "END IF;\r\n" +

				"IF (new.parameter_action IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.parameter_action','',new.parameter_action,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','parameter_action');\r\n"
				+ "END IF;\r\n" +

				" IF (new.prameter_global_lsp_id IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.prameter_global_lsp_id','',new.prameter_global_lsp_id,'static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','prameter_global_lsp_id');\r\n"
				+ "END IF;\r\n" +

				"IF (new.rule_type IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.rule_type','',new.rule_type,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','rule_type');\r\n"
				+ "END IF;\r\n" +

				"IF (new.tb_condition IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.tb_condition','',new.tb_condition,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','tb_condition');\r\n"
				+ "END IF;\r\n" +

				"IF (new.to_range IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.to_range','',new.to_range,'Static',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','to_range');\r\n"
				+ "END IF;\r\n" +

				"select parameter_name from bedc.global_parameter where id= new.dest_parameter into newDestParameter;  \r\n"
				+ "IF (new.dest_parameter IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.dest_parameter','',new.dest_parameter,'Dynamic',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','dest_parameter');\r\n"
				+ "END IF; \r\n" + "IF (new.dest_activity IS NOT NULL) THEN\r\n"
				+ " select name from bedc.global_activity where id= new.dest_activity into newDestactivity;  \r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('dest_activity','',newDestactivity,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','dest_activity');\r\n"
				+ "END IF;\r\n" +

				"IF (new.global_val_id IS NOT NULL) THEN\r\n"
				+ " select name from bedc.global_values where id= new.global_val_id into newGlobalValId;    \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('global_val_id','',newGlobalValId,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','global_val_id');\r\n"
				+ "END IF;\r\n" +

				"IF (new.source_activity IS NOT NULL) THEN\r\n"
				+ " select name from bedc.global_activity where id= new.source_activity into newSourceActivity;    \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('source_activity','',newSourceActivity,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','source_activity');\r\n"
				+ "END IF; \r\n" +

				"IF (new.source_parameter IS NOT NULL) THEN\r\n"
				+ " select parameter_name from bedc.global_parameter where id= new.source_parameter into newSourceParameter;    \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('source_parameter','',newSourceParameter,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_rules','audit_insert','source_parameter');\r\n"
				+ "END IF; \r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	 VALUES (new.updated_by,'UPDATE',new.id,'study_activity_rule_messages',null,null,null,'study_activity_rules_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+

				"IF nullif(old.con_firstval,'') <> NULLIF(new.con_firstval,'') or new.con_firstval is null THEN\r\n"
				+ " INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.con_firstval','',new.con_firstval,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','con_firstval');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.con_secondval,'') <> NULLIF(new.con_secondval,'') or new.con_secondval is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.con_secondval','',new.con_secondval,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','con_secondval');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.condition,'') <> NULLIF(new.condition,'') or new.condition is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.condition','',new.condition,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','condition');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.depndent_activities,'') <> NULLIF(new.depndent_activities,'') or new.depndent_activities is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.depndent_activities','',new.dependent_activities,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','depndent_activities');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.dependent_apply_for,'') <> NULLIF(new.dependent_apply_for,'') or new.dependent_apply_for is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.dependent_apply_for','',new.dependent_apply_for,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','dependent_apply_for');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.dest_parameter_global_val,'') <> NULLIF(new.dest_parameter_global_val,'') or new.dest_parameter_global_val is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.dest_parameter_global_val','',new.dest_parameter_global_val,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','dest_parameter_global_val');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.dest_prameter_global_lsp_id,null) <> NULLIF(new.dest_prameter_global_lsp_id,null) or new.dest_prameter_global_lsp_id is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.dest_prameter_global_lsp_id','',new.dest_prameter_global_lsp_id,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','dest_prameter_global_lsp_id');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.from_range,'') <> NULLIF(new.from_range,'') or new.from_range is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.from_range','',new.from_range,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','from_range');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.key_name,'') <> NULLIF(new.key_name,'') or new.key_name is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.key_name','',new.key_name,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','key_name');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.key_value,'') <> NULLIF(new.key_value,'') or new.key_value is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.key_value','',new.key_value,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','key_value');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.multi_param,'') <> NULLIF(new.multi_param,'') or new.multi_param is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.multi_param','',new.multi_param,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','multi_param');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.parameter_action,'') <> NULLIF(new.parameter_action,'') or new.parameter_action is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.parameter_action','',new.parameter_action,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','parameter_action');\r\n"
				+ " END IF;\r\n" +

				" IF  nullif(old.prameter_global_lsp_id,null) <> NULLIF(new.prameter_global_lsp_id,null) or new.prameter_global_lsp_id is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.prameter_global_lsp_id','',new.prameter_global_lsp_id,'static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','prameter_global_lsp_id');\r\n"
				+ " END IF;\r\n"
				+ "IF nullif(old.rule_type,'') <> NULLIF(new.rule_type,'') or new.rule_type is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.rule_type','',new.rule_type,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','rule_type');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.tb_condition,'') <> NULLIF(new.tb_condition,'') or new.tb_condition is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.tb_condition','',new.tb_condition,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','tb_condition');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.to_range,'') <> NULLIF(new.to_range,'') or new.to_range is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.to_range','',new.to_range,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','to_range');\r\n"
				+ "END IF;  \r\n"
				+ "select parameter_name from bedc.global_parameter where id= new.dest_parameter into newDestParameter; \r\n"
				+ "select parameter_name from bedc.global_parameter where id= new.dest_parameter into oldDestParameter;  \r\n"
				+ "IF nullif(old.dest_parameter,null) <> NULLIF(new.dest_parameter,null) or new.dest_parameter is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.dest_parameter',oldDestParameter,newDestParameter,'Dynamic',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','dest_parameter');\r\n"
				+ "END IF; \r\n"
				+ "IF nullif(old.dest_activity,null) <> NULLIF(new.dest_activity,null) or new.dest_activity is null THEN\r\n"
				+ "select name from bedc.global_activity where id= new.dest_activity into newDestactivity;  \r\n"
				+ "select name from bedc.global_activity where id= new.dest_activity into oldDestactivity;  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('dest_activity',oldDestactivity,newDestactivity,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','dest_activity');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.global_val_id,null) <> NULLIF(new.global_val_id,null) or new.global_val_id is null THEN\r\n"
				+ "select name from bedc.global_values where id= new.global_val_id into newGlobalValId;    \r\n"
				+ "select name from bedc.global_values where id= new.global_val_id into oldGlobalValId;    \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('global_val_id',oldGlobalValId,newGlobalValId,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','global_val_id');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.source_activity,null) <> NULLIF(new.source_activity,null) or new.source_activity is null THEN\r\n"
				+ "select name from bedc.global_activity where id= new.source_activity into newSourceActivity;    \r\n"
				+ "select name from bedc.global_activity where id= new.source_activity into oldSourceActivity;    \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('source_activity',oldSourceActivity,newSourceActivity,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','source_activity');\r\n"
				+ "END IF; \r\n"
				+ "IF nullif(old.source_parameter,null) <> NULLIF(new.source_parameter,null) or new.source_parameter is null THEN\r\n"
				+ "select parameter_name from bedc.global_parameter where id= new.source_parameter into newSourceParameter;    \r\n"
				+ "select parameter_name from bedc.global_parameter where id= new.source_parameter into oldSourceParameter;    \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('source_parameter',oldSourceParameter,newSourceParameter,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_activity_rules','audit_update','source_parameter');\r\n"
				+ "END IF; \r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_activity_rules_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,con_firstval,con_secondval,condition,created_by,created_on,depndent_activities,dependent_apply_for,deptbvalue,dest_parm_condition,dest_parameter_global_val,dest_prameter_global_lsp_id,from_range,key_name,key_value,multi_param,parameter_action,prameter_global_lsp_id,rule_type,tb_condition,to_range,update_reason,updated_by,updated_on,dest_parameter,dest_activity,global_val_id,source_activity,source_parameter  ON bedc.study_activity_rules\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_activity_rules_fun();");

	}

	private void generateStudyActivityRuleMessages(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_activity_rule_messages_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "  newStudyPeriod character varying;\r\n" + "  oldStudyPeriod character varying;\r\n"
				+ "  newActivityId character varying;\r\n" + "  oldActivityId character varying;\r\n"
				+ "  newTreatment character varying;\r\n" + "  oldTreatment character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (null,'CREATE',null,'study_activites',null,null,null,'study_activites_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+

				"IF (new.study_act_rule IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('ruleId','',newTreatment,'Dynamic',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rule_messages','audit_insert','treatment');\r\n"
				+ "end if;\r\n" + "IF (new.ruleMessage IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('ruleMessage','',new.ruleMessage,'Dynamic',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rule_messages','audit_insert','treatment');\r\n"
				+ "end if;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (null,'UPDATE',null,'study_activites',null,null,null,'study_activity_rule_messages_updation',new.created_on)RETURNING id INTO incCount;\r\n"
				+

				"IF nullif(old.study_act_rule,null) <> NULLIF(new.study_act_rule,null) or new.study_act_rule is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('ruleId',oldTreatment,newTreatment,'Dynamic',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rule_messages','audit_update','treatment');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.ruleMessage,null) <> NULLIF(new.treatment,null) or new.treatment is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('ruleMessage',old.ruleMessage,new.ruleMessage,'Dynamic',null,null,null,'',new.created_by,new.created_on,incCount,'study_activity_rule_messages','audit_update','treatment');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" +

				"END IF;\r\n" + "END IF;\r\n" + "END;\r\n" + "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_activity_rule_messages_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,created_by,created_on,rule_message,update_reason,updated_by,updated_on,lang,study_act_rule  ON bedc.study_activity_rule_messages\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_activity_rule_messages_fun();");

	}

	private void generateStudyActivityTimePoints(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_activity_time_points_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   study_activityName character varying;\r\n" + "   oldstudy_activityName character varying;\r\n"
				+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n" + " select count(*) from bedc.audit_log into actid;\r\n"
				+ " actid := actid +1; \r\n" + "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " 	VALUES (new.created_by,'CREATE',new.id,'study_activity_time_points',null,null,null,'study_activity_time_points_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+ "IF (new.orthostatic IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.orthostatic','',new.orthostatic,'Static',null,null,null,' ',null,new.created_on,incCount,'study_activity_time_points','audit_insert','orthostatic');\r\n"
				+ "END IF;\r\n" + "IF (new.orthostatic_position IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.orthostatic_position','',new.orthostatic_position,'Static',null,null,null,' ',null,new.created_on,incCount,'study_activity_time_points','audit_insert','orthostatic_position');\r\n"
				+ "END IF;\r\n" + "IF (new.position IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.position','',new.position,'Static',null,null,null,' ',null,new.created_on,incCount,'study_activity_time_points','audit_insert','position');\r\n"
				+ "END IF;\r\n" + "IF (new.time_point IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.time_point','',new.time_point,'Static',null,null,null,' ',null,new.created_on,incCount,'study_activity_time_points','audit_insert','time_point');\r\n"
				+ "END IF;\r\n" + "IF (new.timpoint_sign IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timpoint_sign','',new.timpoint_sign,'Static',null,null,null,' ',null,new.created_on,incCount,'study_activity_time_points','audit_insert','timpoint_sign');\r\n"
				+ "END IF;\r\n" + "IF (new.window_period IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.window_period','',new.window_period,'Static',null,null,null,' ',null,new.created_on,incCount,'study_activity_time_points','audit_insert','window_period');\r\n"
				+ "END IF;\r\n" + "IF (new.window_period_sign IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.window_period_sign','',new.window_period_sign,'Static',null,null,null,' ',null,new.created_on,incCount,'study_activity_time_points','audit_insert','window_period_sign');\r\n"
				+ "END IF;\r\n" + "IF (new.window_period_type IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.window_period_type','',new.window_period_type,'Static',null,null,null,' ',null,new.created_on,incCount,'study_activity_time_points','audit_insert','window_period_type');\r\n"
				+ "END IF;\r\n" + "IF (new.study_activity IS NOT NULL) THEN\r\n"
				+ "SELECT activity_id FROM bedc.study_activites where suty_activity = new.study_activity into study_activityName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('study_activity','',study_activityName,'Dynamic',null,null,null,' ',null,new.created_on,incCount,'study_activity_time_points','audit_insert','study_activity');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.id,'study_activity_time_points',null,null,null,'study_activity_time_points_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+

				"IF nullif(old.orthostatic,'') <> NULLIF(new.orthostatic,'') or new.orthostatic is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.orthostatic',old.orthostatic,new.orthostatic,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_time_points','audit-update','orthostatic');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.orthostatic_position,'') <> NULLIF(new.orthostatic_position,'') or new.orthostatic_position is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.orthostatic_position',old.orthostatic_position,new.orthostatic_position,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_time_points','audit-update','orthostatic_position');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.position,'') <> NULLIF(new.position,'') or new.position is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.position',old.position,new.position,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_time_points','audit-update','position');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.time_point,'') <> NULLIF(new.time_point,'') or new.time_point is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.time_point',old.time_point,new.time_point,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_time_points','audit-update','time_point');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.timpoint_sign,'') <> NULLIF(new.timpoint_sign,'') or new.timpoint_sign is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timpoint_sign',old.timpoint_sign,new.timpoint_sign,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_time_points','audit-update','timpoint_sign');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.window_period,0) <> NULLIF(new.window_period,0) or new.window_period is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.window_period',old.window_period,new.window_period,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_time_points','audit-update','window_period');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.window_period_sign,'') <> NULLIF(new.window_period_sign,'') or new.window_period_sign is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES (actid,'label.window_period_sign',old.window_period_sign,new.window_period_sign,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_time_points','audit-update','window_period_sign');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.window_period_type,'') <> NULLIF(new.window_period_type,'') or new.window_period_type is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.window_period_type',old.window_period_type,new.window_period_type,'Static',null,null,null,'',new.updated_by,new.updated_on,incCount,'study_activity_time_points','audit-update','window_period_type');\r\n"
				+ "END IF;\r\n"
				+ "SELECT activity_id FROM bedc.study_activites where suty_activity = old.study_activity into oldstudy_activityName;\r\n"
				+ "SELECT activity_id FROM bedc.study_activites where suty_activity = new.study_activity into study_activityName;\r\n"
				+ "IF nullif(old.study_activity,null) <> NULLIF(new.study_activity,null) or new.study_activity is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('study_activity',oldstudy_activityName,study_activityName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_activity_time_points','audit-update','study_activity');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_activity_time_points_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,created_by,created_on,orthostatic,orthostatic_position,position,time_point,timpoint_sign,update_reason,updated_by,updated_on,window_period,window_period_sign,window_period_type,study_activity  ON bedc.study_activity_time_points\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_activity_time_points_fun();");

	}

	private void generateStudyActivityTimepointsCompletiondata(Connection con, Statement stmt) throws Exception {
		stmt.execute(
				"CREATE OR REPLACE FUNCTION bedc.study_activity_timepoints_completiondata_fun() RETURNS TRIGGER AS $$\r\n"
						+ "declare \r\n" + "   incCount integer;\r\n" + "BEGIN\r\n"
						+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
						+ "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
						"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ " 	VALUES (new.created_by,'CREATE',new.id,'study_activity_timepoints_completiondata',new.study_id,null,null,'study_activity_timepoints_completiondata_creation',new.created_on)RETURNING id INTO incCount; \r\n"
						+

						"IF (new.period_id IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.period_id','',new.period_id,'Static',new.study_id,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_timepoints_completiondata','audit_insert','period_id');\r\n"
						+ "END IF;\r\n" + "IF (new.status IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.status','',new.status,'Static',new.study_id,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_timepoints_completiondata','audit_insert','status');\r\n"
						+ "END IF;\r\n" + "IF (new.study_activity_id IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.study_activity_id','',new.study_activity_id,'Static',new.study_id,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_timepoints_completiondata','audit_insert','study_activity_id');\r\n"
						+ "END IF;\r\n" + "IF (new.study_activity_timepoint_id IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.study_activity_timepoint_id','',new.study_activity_timepoint_id,'Static',new.study_id,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_timepoints_completiondata','audit_insert','study_activity_timepoint_id');\r\n"
						+ "END IF;\r\n" + "IF (new.treatment_id IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.treatment_id','',new.treatment_id,'Static',new.study_id,null,null,' ',new.created_by,new.created_on,incCount,'study_activity_timepoints_completiondata','audit_insert','treatment_id');\r\n"
						+ "END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
						"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ "VALUES (new.updated_by,'UPDATE',new.id,'study_activity_timepoints_completiondata',new.study_id,null,null,'study_activity_timepoints_completiondata_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
						+

						"IF nullif(old.period_id,null) <> NULLIF(new.period_id,null) or new.period_id is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.period_id',old.period_id,new.period_id,'Static',new.study_id,null,null,' ',new.updated_by,new.updated_on,incCount,'study_activity_timepoints_completiondata','audit_insert','period_id');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.status,'') <> NULLIF(new.status,'') or new.status is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.status',old.status,new.status,'Static',new.study_id,null,null,' ',new.updated_by,new.updated_on,incCount,'study_activity_timepoints_completiondata','audit_insert','status');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.study_activity_id,null) <> NULLIF(new.study_activity_id,null) or new.study_activity_id is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.study_activity_id',old.study_activity_id,new.study_activity_id,'Static',new.study_id,null,null,' ',new.updated_by,new.updated_on,incCount,'study_activity_timepoints_completiondata','audit_insert','study_activity_id');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.study_activity_timepoint_id,null) <> NULLIF(new.study_activity_timepoint_id,null) or new.study_activity_timepoint_id is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.study_activity_timepoint_id',new.updated_by,new.updated_on,incCount,'study_activity_timepoints_completiondata','audit_insert','study_activity_timepoint_id');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.treatment_id,null) <> NULLIF(new.treatment_id,null) or new.treatment_id is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.treatment_id',old.treatment_id,new.treatment_id,'Static',new.study_id,null,null,' ',new.updated_by,new.updated_on,incCount,'study_activity_timepoints_completiondata','audit_insert','treatment_id');\r\n"
						+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
						+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_activity_timepoints_completiondata_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,created_by,created_on,period_id,status,study_activity_id,study_id,study_activity_timepoint_id,treatment_id,update_reason,updated_by,updated_on  ON bedc.study_activity_timepoints_completiondata\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_activity_timepoints_completiondata_fun();");

	}

	private void generateStudyActivites(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_activites_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "  newStudyPeriod character varying;\r\n" + "  oldStudyPeriod character varying;\r\n"
				+ "  newActivityId character varying;\r\n" + "  oldActivityId character varying;\r\n"
				+ "  newTreatment character varying;\r\n" + "  oldTreatment character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.study_id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (null,'CREATE',new.study_id,'study_activites',null,null,null,'study_activites_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+

				"select name from bedc.global_activity where id= new.activity_id  into newActivityId;   \r\n"
				+ "IF (new.activity_id IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('activity_id','',newActivityId,'Dynamic',new.study_id,null,null,'',new.created_by,new.created_on,incCount,'study_activites','audit_insert','activity_id');\r\n"
				+ "end if;\r\n"
				+ "select periodname from bedc.study_period_master  where periodid= new.study_period into newStudyPeriod ;    \r\n"
				+ "IF (new.study_period IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('study_period','',newStudyPeriod,'Dynamic',new.study_id,null,null,'',new.created_by,new.created_on,incCount,'study_activites','audit_insert','study_period');\r\n"
				+ "end if;\r\n"
				+ "select treatmentname from bedc.study_treatments where treatmentinfoid= new.treatment into newTreatment;    \r\n"
				+ "IF (new.treatment IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('treatment','',newTreatment,'Dynamic',new.study_id,null,null,'',new.created_by,new.created_on,incCount,'study_activites','audit_insert','treatment');\r\n"
				+ "end if;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.study_id=old.study_id) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (null,'UPDATE',new.study_id,'study_activites',null,null,null,'study_activites_updation',new.created_on)RETURNING id INTO incCount;\r\n"
				+

				"select name from bedc.global_activity where id= new.activity_id into newActivityId;    \r\n"
				+ "select name from bedc.global_activity where id= new.activity_id into oldActivityId;    \r\n"
				+ "IF nullif(old.activity_id,null) <> NULLIF(new.activity_id,null) or new.activity_id is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('activity_id',oldStudyPeriod,newStudyPeriod,'Dynamic',new.study_id,null,null,'',new.created_by,new.created_on,incCount,'study_activites','audit_update','activity_id');\r\n"
				+ "END IF;\r\n"
				+ "select periodname from bedc.study_period_master  where periodid= new.study_period into newStudyPeriod ;    \r\n"
				+ "select periodname from bedc.study_period_master  where periodid= new.study_period into oldStudyPeriod ;    \r\n"
				+ "IF nullif(old.study_period,null) <> NULLIF(new.study_period,null) or new.study_period is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('study_period',oldActivityId,newActivityId,'Dynamic',new.study_id,null,null,'',new.created_by,new.created_on,incCount,'study_activites','audit_update','study_period');\r\n"
				+ "END IF;\r\n"
				+ "select treatmentname from bedc.study_treatments where treatmentinfoid= new.treatment into newTreatment;    \r\n"
				+ "select treatmentname from bedc.study_treatments where treatmentinfoid= new.treatment into oldTreatment;    \r\n"
				+ "IF nullif(old.treatment,null) <> NULLIF(new.treatment,null) or new.treatment is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('treatment',oldTreatment,newTreatment,'Dynamic',new.study_id,null,null,'',new.created_by,new.created_on,incCount,'study_activites','audit_update','treatment');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_activites_trgr"
				+ "   AFTER INSERT OR UPDATE  OF suty_activity,created_by,created_on,update_reason,updated_by,updated_on,activity_id,study_id,study_period,treatment  ON bedc.study_activites\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_activites_fun();");

	}

	private void generateStudyActivityData(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_activity_data_fun() RETURNS TRIGGER AS $$\r\n" +

				"\r\n" + "declare \r\n" + "incCount integer;\r\n" + "actid integer;\r\n"
				+ "newrole character varying;\r\n" + "oldrole character varying;\r\n" + "\r\n" + "begin\r\n"
				+ "SELECT COUNT(*) FROM bedc.activity_log INTO incCount;\r\n" + "incCount := incCount +1;\r\n"
				+ "select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "	IF (old.id is null) THEN\r\n"
				+ "	INSERT INTO bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "    VALUES(new.created_by,'CREATE',new.id,'study_activity_data',null,null,null,'study_activity_data_creation',new.created_on) RETURNING id INTO incCount;\r\n"
				+ "	\r\n" + "\r\n" + "	IF new.activity_start_date IS NOT NULL THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.activityStartDate','',new.activity_start_date,null,null,null,' ',new.created_by,incCount,new.created_on,'study_activity_data','static','audit_insert','activity_start_date');\r\n"
				+ "	END IF;\r\n" + "	IF new.reportstatus IS NOT NULL THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.reportStatus','',new.reportstatus,null,null,null,' ',new.created_by,incCount,new.created_on,'study_activity_data','static','audit_insert','reportstatus');\r\n"
				+ "	END IF;\r\n" + "	IF new.roleid IS NOT NULL THEN\r\n"
				+ " select role from bedc.epk_role_master where roleid=new.roleid into newrole;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.roleId','',newrole,null,null,null,' ',new.created_by,incCount,new.created_on,'study_activity_data','static','audit_insert','roleid');\r\n"
				+ "	END IF;\r\n" + "	IF new.status IS NOT NULL THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.swastatus','',new.status,null,null,null,' ',new.created_by,incCount,new.created_on,'study_activity_data','static','audit_insert','status');\r\n"
				+ "	END IF; \r\n" + "	/*	This is not required\r\n"
				+ "IF (new.study_group_period IS NOT NULL) THEN\r\n"
				+ "	select name from bedc.created_by where id= new.created_by into newcreated_by;\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('StudyActivity','',newcreated_by,'Static',null,new.volunteer_id,new.subject_id,' ',new.created_by,new.created_on,incCount,'study_activity_data','audit_insert','newcreated_by');\r\n"
				+ "		END IF;\r\n" + "*/\r\n" + "\r\n" + "/*	Need to discuss\r\n"
				+ "IF (new.study_activity IS NOT NULL) THEN\r\n"
				+ "	    select name from bedc.created_by where id= new.created_by into newcreated_by;\r\n"
				+ "		INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "		VALUES ('StudyActivity','',newcreated_by,'Static',null,new.volunteer_id,new.subject_id,' ',new.created_by,new.created_on,incCount,'study_activity_data','audit_insert','newcreated_by');\r\n"
				+ "		END IF;*/\r\n" + "\r\n" + "RETURN new;\r\n" + "ELSE IF(new.id=old.id) THEN\r\n"
				+ "	INSERT INTO bedc.activity_log(activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	VALUES(new.upddated_by,'update',new.id,'study_activity_data',null,null,null,'study_activity_data_updation',new.updated_on)\r\n"
				+ "	RETURNING id INTO incCount;\r\n" + "	\r\n"
				+ "	IF nullif(old.activity_start_date,null) <> NULLIF(new.activity_start_date,null) or new.activity_start_date is null THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.activityStartDate',old.activity_start_date,new.activity_start_date,null,null,null,' ',new.upddated_by,incCount,new.updated_on,'study_activity_data','static','audit_update','activity_start_date');\r\n"
				+ "	END IF;\r\n"
				+ "	IF nullif(old.reportstatus,null) <> NULLIF(new.reportstatus,null) or new.reportstatus is null THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.reportStatus',old.reportstatus,new.reportstatus,null,null,null,' ',new.upddated_by,incCount,new.updated_on,'study_activity_data','static','audit_update','reportstatus');\r\n"
				+ "	END IF;\r\n"
				+ "	IF nullif(old.roleid,null) <> NULLIF(new.roleid,null) or new.roleid is null THEN\r\n"
				+ " select role from bedc.epk_role_master where roleid=new.roleid into newrole;\r\n"
				+ " select role from bedc.epk_role_master where roleid=new.roleid into oldrole;\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.roleId',oldrole,newrole,null,null,null,' ',new.upddated_by,incCount,new.updated_on,'study_activity_data','static','audit_update','roleid');\r\n"
				+ "	END IF;\r\n"
				+ "	IF nullif(old.status,'') <> NULLIF(new.status,'') or new.status is null THEN\r\n"
				+ "	INSERT INTO bedc.audit_log (field_name,old_value,new_value,study_id,volunteer_id,subject_id,comments,created_by,activity_log_id,created_on,table_name,field_type,action,databasefieldname)\r\n"
				+ "	VALUES('label.swastatus',old.status,new.status,null,null,null,' ',new.upddated_by,incCount,new.updated_on,'study_activity_data','static','audit_update','status');\r\n"
				+ "	END IF;\r\n" + "\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n" +

				"$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_activity_data_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,activity_start_date,created_by,created_on,reportstatus,status,update_reason,upddated_by,updated_on,study_group_period,subject_id,study_activity,volunteer_id  ON bedc.study_activity_data\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_activity_data_fun();");

	}

	private void generateStudyActivitesParameters(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_activites_parameters_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "  newParameterId character varying;\r\n" + "  oldParameterId character varying;\r\n"
				+ "  newStudyActivity character varying;\r\n" + "  oldStudyActivity character varying;\r\n"
				+ "  newtympont character varying;\r\n" + "  oldtympont character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (null,'CREATE',new.id,'study_activites_parameters',null,null,null,'study_activites_parameters_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+

				"select parameter_name from bedc.global_parameter where id= new.parameter_id into newParameterId;  \r\n"
				+ "IF (new.parameter_id IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('parameter_id','',newParameterId,'Dynamic',null,null,null,'',new.created_by,new.created_on,incCount,'study_activites_parameters','audit_insert','parameter_id');\r\n"
				+ "END IF;\r\n" +

				"select treatment from bedc.study_activites where suty_activity= new.study_activity into newStudyActivity;  \r\n"
				+ "IF (new.study_activity IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('study_activity','',newStudyActivity,'Dynamic',null,null,null,'',new.created_by,new.created_on,incCount,'study_activites_parameters','audit_insert','study_activity');\r\n"
				+ "END IF;\r\n" +

				"select time_point from bedc.study_activity_time_points where id= new.study_activity_timepoint into newStudyActivity;  \r\n"
				+ "IF (new.study_activity_timepoint IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('study_activity_timepoint','',newStudyActivity,'Dynamic',null,null,null,'',new.created_by,new.created_on,incCount,'study_activites_parameters','audit_insert','study_activity_timepoint');\r\n"
				+ " END IF;\r\n" +

				"RETURN new;\r\n" + "\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (null,'UPDATE',new.id,'study_activites_parameters',null,null,null,'study_activites_parameters_updation',new.created_on)RETURNING id INTO incCount;\r\n"
				+

				"select parameter_name from bedc.global_parameter where id= new.parameter_id into newParameterId;    \r\n"
				+ "IF nullif(old.parameter_id,null) <> NULLIF(new.parameter_id,null) or new.parameter_id is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('parameter_id',oldParameterId,newParameterId,'Dynamic',null,null,null,'',new.created_by,new.created_on,incCount,'study_activites_parameters','audit_update','parameter_id');\r\n"
				+ "END IF;\r\n" +

				"select treatment from bedc.study_activites where suty_activity= new.study_activity into newStudyActivity;    \r\n"
				+ "IF nullif(old.study_activity,null) <> NULLIF(new.study_activity,null) or new.study_activity is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('study_activity',oldStudyActivity,newStudyActivity,'Dynamic',null,null,null,'',new.created_by,new.created_on,incCount,'study_activites_parameters','audit_update','study_activity');\r\n"
				+ "END IF;\r\n"
				+ "select time_point from bedc.study_activity_time_points where id= new.study_activity_timepoint into newtympont;  \r\n"
				+ "IF nullif(old.study_activity_timepoint,null) <> NULLIF(new.study_activity_timepoint,null) or new.study_activity_timepoint is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('study_activity_timepoint','',newStudyActivity,'Dynamic',null,null,null,'',new.created_by,new.created_on,incCount,'study_activites_parameters','audit_update','study_activity_timepoint');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_activites_parameters_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,created_by,created_on,update_reason,updated_by,updated_on,parameter_id,study_activity,study_activity_timepoint  ON bedc.study_activites_parameters\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_activites_parameters_fun();");

	}

	private void generateStudyActivityDataDiscrepancy(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_activity_data_discrepancy_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   newstdactiondata character varying; \r\n" + "  stdactiondata character varying; \r\n"
				+ "  studycheckinactvitydataid character varying;\r\n"
				+ "  newstudycheckinactvitydataid character varying;\r\n"
				+ "  studyactexecutiondataid character varying;\r\n"
				+ "  newstudyactexecutiondataid character varying;\r\n"
				+ "  studycheckoutactivitydataid  character varying;\r\n"
				+ "  newstudycheckoutactivitydataid character varying;\r\n" +

				" BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n" + " select count(*) from bedc.audit_log into actid;\r\n"
				+ " actid := actid +1;\r\n" + "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.createdby,'CREATE',new.id,'study_activity_data_discrepancy',null,null,null,'study_activity_data_discrepancy_creation',new.createdon) RETURNING id INTO incCount; \r\n"
				+

				"IF (new.response IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.response','',new.response,'Static',null,null,null,new.comments,new.createdby,new.createdon,incCount,'study_activity_data_discrepancy','audit_insert','response');\r\n"
				+ "END IF;\r\n" + "IF (new.status IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.status','',new.status,'Static',null,null,null,new.comments,new.createdby,new.createdon,incCount,'study_activity_data_discrepancy','audit_insert','status');\r\n"
				+ "END IF;\r\n"
				+ "select status from bedc.study_activity_data where id= new.studyactiondata_id into newstdactiondata;  	\r\n"
				+ "IF (new.studyactiondata_id IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('studyactiondata_id','',newstdactiondata,'Static',null,null,null,' ',new.createdby,new.createdon,incCount,'study_activity_data_discrepancy','audit_insert','studyactiondata_id');\r\n"
				+ "END IF;\r\n"
				+ "select value from bedc.study_activity_data_checkin where id= new.studycheckinactivitydata_id into newstudycheckinactvitydataid;  	\r\n"
				+ "IF (new.studycheckinactvitydata_id IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('studycheckinactvitydata_id','',newstudycheckinactvitydataid,'dynamic',null,null,null,' ',new.createdby,new.createdon,incCount,'study_activity_data_discrepancy','audit_insert','studycheckinactvitydata_id');\r\n"
				+ "END IF;\r\n"
				+ "select value from bedc.study_activity_data_execution where id= new.studyexecutionactivitydata_id into newstudyactexecutiondataid;  	\r\n"
				+ "IF (new.studyexecutionactivitydata_id IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('studyexecutionactivitydata_id','',newstudyactexecutiondataid,'dynamic',null,null,null,' ',new.createdby,new.createdon,incCount,'study_activity_data_discrepancy','audit_insert','studyexecutionactivitydata_id');\r\n"
				+ "END IF;\r\n"
				+ "select vlaue from bedc.study_activity_data_check_out where id= new.studycheckoutactivitydata_id into newstudycheckoutactivitydataid;  	\r\n"
				+ "IF (new.studycheckoutactivitydata_id IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('studycheckoutactivitydata_id','',newstudycheckoutactivitydataid,'dynamic',null,null,null,' ',new.createdby,new.createdon,incCount,'study_activity_data_discrepancy','audit_insert','studycheckoutactivitydata_id');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updateby,'UPDATE',new.id,'global_activity',null,null,null,'global_activity_updation',new.updateon)\r\n"
				+ "RETURNING id INTO incCount;\r\n"
				+ "IF nullif(old.status,'') <> NULLIF(new.status,'') or new.status is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.status',old.status,new.status,'Static',null,null,null,' ',new.updateby,new.updateon,incCount,'study_activity_data_discrepancy_updation','audit_update','status');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.response,'') <> NULLIF(new.response,'') or new.response is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.response',old.response,new.response,'Static',null,null,null,' ',new.updateby,new.updateon,incCount,'study_activity_data_discrepancy','audit_update','response');\r\n"
				+ "END IF;\r\n" +

				"select status from bedc.study_activity_data where id= new.studyactiondata_id into stdactiondata;  	\r\n"
				+ "select status from bedc.study_activity_data where id= new.studyactiondata_id into newstdactiondata;  	\r\n"
				+ "IF nullif(old.studyactiondata_id,null) <> NULLIF(new.studyactiondata_id,null) or new.studyactiondata_id is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('studyactiondata_id',stdactiondata,newstdactiondata,'Dynamic',null,null,null,' ',new.updateby,new.updateon,incCount,'study_activity_data_discrepancy','audit_update','studyactiondata_id');\r\n"
				+ "END IF;\r\n" +

				"select value from bedc.study_activity_data_checkin where id= new.studycheckinactivitydata_id into newstudycheckinactvitydataid;  	\r\n"
				+ "select value from bedc.study_activity_data_checkin where id= new.studycheckinactivitydata_id into studycheckinactvitydataid;  	\r\n"
				+ "IF nullif(old.studycheckinactivitydata_id,null) <> NULLIF(new.studycheckinactivitydata_id,null) or new.studycheckinactivitydata_id is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('studycheckinactivitydata_id',studycheckinactvitydataid,newstudycheckinactvitydataid,'Dynamic',null,null,null,' ',new.updateby,new.updateon,incCount,'study_activity_data_discrepancy','audit_update','studycheckinactivitydata_id');\r\n"
				+ "END IF;\r\n"
				+ "select value from bedc.study_activity_data_check_out where id= new.studycheckoutactivitydata_id into newstudycheckoutactivitydataid;  	\r\n"
				+ "select value from bedc.study_activity_data_check_out where id= old.studycheckoutactivitydata_id into studycheckoutactivitydataid;  	\r\n"
				+ "IF nullif(old.studycheckoutactivitydata_id,null) <> NULLIF(new.studycheckoutactivitydata_id,null) or new.studycheckoutactivitydata_id is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('studycheckoutactivitydata_id',studycheckoutactivitydataid,newstudycheckoutactivitydataid,'Dynamic',null,null,null,' ',new.updateby,new.updateon,incCount,'study_activity_data_discrepancy','audit_update','studycheckoutactivitydata_id');\r\n"
				+ "END IF;\r\n"
				+ "select value from bedc.study_activity_data_execution where id= new.studyexecutionactivitydata_id into newstudyactexecutiondataid;  	\r\n"
				+ "select vlaue from bedc.study_activity_data_execution where id= old.studyexecutionactivitydata_id into studyactexecutiondataid;  	\r\n"
				+ "IF nullif(old.studyexecutionactivitydata_id,null) <> NULLIF(new.studyexecutionactivitydata_id,null) or new.studyexecutionactivitydata_id is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('studyexecutionactivitydata_id',studyexecutionactivitydataid,newstudyexecutionactivitydataid,'Dynamic',null,null,null,' ',new.updateby,new.updateon,incCount,'study_activity_data_discrepancy','audit_update','studyexecutionactivitydata_id');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "	END IF;\r\n" + "	END IF;\r\n" + "	END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_activity_data_discrepancy_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,comments,createdby,createdon,response,status,updateby,updateon,studyactiondata_id,"
				+ "studycheckinactivitydata_id,studycheckoutactivitydata_id,studyexecutionactivitydata_id  ON bedc.study_activity_data_discrepancy\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_activity_data_discrepancy_fun();");

	}

	private void generateStudyDosageForm(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_dosage_form_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + " \r\n" + "   actid integer;\r\n" + "   \r\n"
				+ "   activestatusName   character varying;\r\n" + "   oldactivestatusName character varying;\r\n"
				+ "   \r\n" + "   \r\n" + "     \r\n" + "	 \r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " \r\n" + " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n" + " \r\n"
				+ " \r\n" + "IF (old.dosagefromid IS NULL) THEN\r\n"
				+ "	INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " 	VALUES (null,'CREATE',new.dosagefromid,'study_dosage_form',null,null,null,'study_dosage_form_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+ "\r\n" + "IF (new.dosavecode IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.dosavecode','',new.dosavecode,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_dosage_form','audit_insert','dosavecode');\r\n"
				+ "END IF;\r\n" + "\r\n" + "IF (new.doseform IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.doseform','',new.doseform,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_dosage_form','audit_insert','doseform');\r\n"
				+ "END IF;\r\n" + "\r\n" + "IF (new.fieldname IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.fieldname','',new.fieldname,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_dosage_form','audit_insert','fieldname');\r\n"
				+ "END IF;\r\n" + "\r\n"
				+ "SELECT statuscode FROM bedc.status_master where statusid = new.activestatus into activestatusName;\r\n"
				+ "IF (new.activestatus IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('activestatus','',activestatusName,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_dosage_form','audit_insert','activestatus');\r\n"
				+ "END IF;\r\n" + "\r\n" + "RETURN new;\r\n" + "ELSE IF (new.dosagefromid=old.dosagefromid) THEN\r\n"
				+ "INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (null,'UPDATE',new.dosagefromid,'study_dosage_form',null,null,null,'study_dosage_form_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+ "\r\n"
				+ "IF nullif(old.dosavecode,'') <> NULLIF(new.dosavecode,'') or new.dosavecode is null then  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.dosavecode',old.dosavecode,new.dosavecode,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dosage_form','audit_update','dosavecode');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.doseform,'') <> NULLIF(new.doseform,'') or new.doseform is null then  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.doseform',old.doseform,new.doseform,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dosage_form','audit_update','doseform');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.fieldname,'') <> NULLIF(new.fieldname,'') or new.fieldname is null then  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.fieldname',old.fieldname,new.fieldname,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dosage_form','audit_update','fieldname');\r\n"
				+ "END IF;\r\n"
				+ "SELECT statuscode FROM bedc.status_master where statusid = old.activestatus into oldactivestatusName;\r\n"
				+ "SELECT statuscode FROM bedc.status_master where statusid = new.activestatus into activestatusName;\r\n"
				+ "IF nullif(old.activestatus,'') <> NULLIF(new.activestatus,'') or new.activestatus is null then  \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('activestatus',oldactivestatusName,activestatusName,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dosage_form','audit_update','activestatus');\r\n"
				+ "END IF; \r\n" + " RETURN new;\r\n" + "\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_dosage_form_trgr"
				+ "   AFTER INSERT OR UPDATE  OF dosagefromid,created_by,created_on,dosavecode,doseform,fieldname,update_reason,updated_by,updated_on,activestatus  ON bedc.study_dosage_form\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_dosage_form_fun();");

	}

	private void generateStudyDosePerameters(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_dose_perameters_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   parameter   character varying;\r\n" + "   oldparameter character varying;\r\n"
				+ "   doseperameterstatus  character varying;\r\n" + "   olddoseperameterstatus  character varying;\r\n"
				+ "  treatmentidName  character varying;\r\n" + "   oldtreatmentidName  character varying;\r\n"
				+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n" + "IF (old.doseperameterid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " 	VALUES (new.created_by,'CREATE',new.doseperameterid,'study_dose_perameters',new.studyid,null,null,'study_dose_perameters_creation',new.created_on) RETURNING id INTO incCount; \r\n"
				+

				"SELECT statuscode FROM bedc.status_master where statusid = new.doseperameterstatus into doseperameterstatus;\r\n"
				+ "IF (new.doseperameterstatus IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.doseperameterstatus','',doseperameterstatus,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_perameters','audit_insert','doseperameterstatus');\r\n"
				+ "END IF;\r\n"
				+ " SELECT parameter_name FROM bedc.global_parameter where id = new.doseperameterstatus into parameter;\r\n"
				+ "IF (new.parameter IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('parameter','',parameter,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_perameters','audit_insert','parameter');\r\n"
				+ "END IF;\r\n"
				+ "SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentid into treatmentidName;\r\n"
				+ "IF (new.treatmentid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('treatmentid','',treatmentidName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_perameters','audit_update','treatmentid');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" + "ELSE IF (new.doseperameterid=old.doseperameterid) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updateD_by,'UPDATE',new.doseperameterid,'study_dose_perameters',new.studyid,null,null,'study_dose_perameters_updation',new.updated_on) RETURNING id INTO incCount;\r\n"
				+

				"SELECT statuscode FROM bedc.status_master where statusid = new.doseperameterstatus into olddoseperameterstatus;\r\n"
				+ "SELECT statuscode FROM bedc.status_master where statusid = new.doseperameterstatus into doseperameterstatus;\r\n"
				+ "IF nullif(old.doseperameterstatus,null) <> NULLIF(new.doseperameterstatus,null) or new.doseperameterstatus is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.doseperameterstatus',olddoseperameterstatus,doseperameterstatus,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_perameters','audit_update','doseperameterstatus');\r\n"
				+ "END IF;\r\n"
				+ "SELECT parameter_name FROM bedc.global_parameter where id = new.doseperameterstatus into oldparameter;\r\n"
				+ "SELECT parameter_name FROM bedc.global_parameter where id = new.doseperameterstatus into parameter;\r\n"
				+ "IF nullif(old.parameter,null) <> NULLIF(new.parameter,null) or new.parameter is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('parameter',oldparameter,parameter,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_perameters','audit_update','parameter');\r\n"
				+ "END IF;\r\n" +

				"SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentid into oldtreatmentidName;\r\n"
				+ "SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentid into treatmentidName;\r\n"
				+ "IF nullif(old.treatmentid,null) <> NULLIF(new.treatmentid,null) or new.treatmentid is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('treatmentid',oldtreatmentidName,treatmentidName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_perameters','audit_update','treatmentid');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_dose_perameters_trgr"
				+ "   AFTER INSERT OR UPDATE  OF doseperameterid,created_by,created_on,update_reason,updated_by,updated_on,doseperameterstatus,parameter,studyid,treatmentid  ON bedc.study_dose_perameters\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_dose_perameters_fun();");

	}

	private void generateStudyDoseTimePoints(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_dose_time_points_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   activestatusName   character varying;\r\n" + "   oldactivestatusName character varying;\r\n"
				+ "  fastingcriteriatypeName  character varying;\r\n"
				+ "   oldfastingcriteriatypeName  character varying; \r\n"
				+ "  fedcriteriatypeName  character varying;\r\n" + "   oldfedcriteriatypeName  character varying;\r\n"
				+ " treatmentinfoidName   character varying;\r\n" + "   oldtreatmentinfoidName   character varying;\r\n"
				+ "  windowperiodtypeName   character varying;\r\n"
				+ "   oldwindowperiodtypeName  character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ "IF (old.dosetimepointsid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.created_by,'CREATE',new.dosetimepointsid,'study_dose_time_points',new.studyid,null,null,'study_dose_time_points_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+

				"IF (new.fastingcriteria IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.fastingcriteria','',new.fastingcriteria,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_time_points','audit_insert','fastingcriteria');\r\n"
				+ "END IF;\r\n" +

				"IF (new.fedcriteria IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.fedcriteria','',new.fedcriteria,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_time_points','audit_insert','fedcriteria');\r\n"
				+ "END IF;\r\n" +

				"IF (new.noofsachet IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.noofsachet','',new.noofsachet,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_time_points','audit_insert','noofsachet');\r\n"
				+ "END IF;\r\n" +

				"IF (new.parameters IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.parameters','',new.timepoint,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_time_points','audit_insert','parameters');\r\n"
				+ "END IF;\r\n" +

				"IF (new.timepoint IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepoints','',new.timepoint,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_time_points','audit_insert','timepoint');\r\n"
				+ "END IF;\r\n" +

				"IF (new.timepointno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointno','',new.timepointno,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_time_points','audit_insert','timepointno');\r\n"
				+ "END IF;\r\n" +

				"IF (new.windowperiod IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiod','',new.windowperiod,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_time_points','audit_insert','windowperiod');\r\n"
				+ "END IF;\r\n" +

				"IF (new.windowperiodsign IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiodsign','',new.windowperiodsign,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_time_points','audit_insert','windowperiodsign');\r\n"
				+ "END IF;\r\n" +

				"SELECT statuscode FROM bedc.status_master where statusid = new.activestatus into activestatusName;\r\n"
				+ "IF (new.activestatus IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('activestatus','',activestatusName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_time_points','audit_insert','activestatus');\r\n"
				+ "END IF;\r\n" +

				"SELECT code FROM bedc.form_static_data where formstaticdataid = new.fastingcriteriatype into fastingcriteriatypeName;\r\n"
				+ "IF (new.fastingcriteriatype IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('fastingcriteriatype','',fastingcriteriatypeName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_time_points','audit_insert','fastingcriteriatype');\r\n"
				+ "END IF;\r\n" +

				" SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.fedcriteriatype into fedcriteriatypeName;\r\n"
				+ "IF (new.fedcriteriatype IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('fedcriteriatype','',fedcriteriatypeName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_time_points','audit_insert','fedcriteriatype');\r\n"
				+ "END IF;\r\n" +

				" SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentinfoid into treatmentinfoidName;\r\n"
				+ "IF (new.treatmentinfoid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('treatmentinfoid','',treatmentinfoidName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_time_points','audit_insert','treatmentinfoid');\r\n"
				+ "END IF;\r\n" +

				" SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = new.windowperiodtype into windowperiodtypeName;\r\n"
				+ "IF (new.windowperiodtype IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('windowperiodtype','',windowperiodtypeName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_time_points','audit_insert','windowperiodtype');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" + "ELSE IF (new.dosetimepointsid=old.dosetimepointsid) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	 VALUES (new.updated_by,'UPDATE',new.dosetimepointsid,'study_dose_time_points',new.studyid,null,null,'study_dose_time_points_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+

				"IF nullif(old.fastingcriteria,'') <> NULLIF(new.fastingcriteria,'') or new.fastingcriteria is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.fastingcriteria',old.fastingcriteria,new.fastingcriteria,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_time_points','audit_insert','fastingcriteria');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.fedcriteria,'') <> NULLIF(new.fedcriteria,'') or new.fedcriteria is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.fedcriteria',old.fedcriteria,new.fedcriteria,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_time_points','audit_insert','fedcriteria');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.noofsachet,0)<> NULLIF(new.noofsachet,0)or new.noofsachet is not null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.noofsachet',old.noofsachet,new.noofsachet,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_time_points','audit_insert','noofsachet');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.parameters,'') <> NULLIF(new.parameters,'') or new.parameters is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.parameters',old.parameters,new.parameters,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_time_points','audit_insert','parameters');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.timepoint,'') <> NULLIF(new.timepoint,'') or new.timepoint is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepoints',old.timepoint,new.timepoint,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_time_points','audit_insert','timepoint');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.timepointno,0) <> NULLIF(new.timepointno,0) or new.timepointno is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointno',old.timepointno,new.timepointno,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_time_points','audit_insert','timepointno');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.windowperiod,0)<> NULLIF(new.windowperiod,0)or new.windowperiod is not null then\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiod',old.windowperiod,new.windowperiod,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_time_points','audit_insert','windowperiod');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.windowperiodsign,'') <> NULLIF(new.windowperiodsign,'') or new.windowperiodsign is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiodsign',old.windowperiodsign,new.windowperiodsign,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_time_points','audit_insert','windowperiodsign');\r\n"
				+ "END IF;\r\n" +

				"SELECT statuscode FROM bedc.status_master where statusid = old.activestatus into oldactivestatusName;\r\n"
				+ "SELECT statuscode FROM bedc.status_master where statusid = new.activestatus into activestatusName;\r\n"
				+ "IF nullif(old.activestatus,null) <> NULLIF(new.activestatus,null) or new.activestatus is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('activestatus',oldactivestatusName,activestatusName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_time_points','audit_insert','activestatus');\r\n"
				+ "END IF;\r\n" +

				"SELECT code FROM bedc.form_static_data where formstaticdataid = old.fastingcriteriatype into oldfastingcriteriatypeName;\r\n"
				+ "SELECT code FROM bedc.form_static_data where formstaticdataid = new.fastingcriteriatype into fastingcriteriatypeName;\r\n"
				+ "IF nullif(old.fastingcriteriatype,null) <> NULLIF(new.fastingcriteriatype,null) or new.fastingcriteriatype is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('fastingcriteriatype',oldfastingcriteriatypeName,fastingcriteriatypeName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_time_points','audit_insert','fastingcriteriatype');\r\n"
				+ "END IF;\r\n" +

				" SELECT fieldname FROM bedc.form_static_data where formstaticdataid = old.fedcriteriatype into oldfedcriteriatypeName;\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.fedcriteriatype into fedcriteriatypeName;\r\n"
				+ "IF nullif(old.fedcriteriatype,null) <> NULLIF(new.fedcriteriatype,null) or new.fedcriteriatype is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('fedcriteriatype',oldfedcriteriatypeName,fedcriteriatypeName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_time_points','audit_insert','fedcriteriatype');\r\n"
				+ "END IF;\r\n" +

				" SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = old.treatmentinfoid into oldtreatmentinfoidName;\r\n"
				+ " SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentinfoid into treatmentinfoidName;\r\n"
				+ "IF nullif(old.treatmentinfoid,null) <> NULLIF(new.treatmentinfoid,null) or new.treatmentinfoid is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('treatmentinfoid',oldtreatmentinfoidName,treatmentinfoidName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_time_points','audit_insert','treatmentinfoid');\r\n"
				+ "END IF;\r\n" +

				" SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = old.windowperiodtype into oldwindowperiodtypeName;\r\n"
				+ " SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = new.windowperiodtype into windowperiodtypeName;\r\n"
				+ "IF nullif(old.windowperiodtype,null) <> NULLIF(new.windowperiodtype,null) or new.windowperiodtype is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('windowperiodtype',oldwindowperiodtypeName,windowperiodtypeName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_time_points','audit_insert','windowperiodtype');\r\n"
				+ "END IF;\r\n" +

				" RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n" + "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_dose_time_points_trgr"
				+ "   AFTER INSERT OR UPDATE  OF dosetimepointsid,created_by,created_on,fastingcriteria,fedcriteria,noofsachet,parameters,timepoint,timepointno,update_reason,updated_by,updated_on,windowperiod,windowperiodsign,activestatus,fastingcriteriatype,fedcriteriatype,studyid,treatmentinfoid,windowperiodtype  ON bedc.study_dose_time_points\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_dose_time_points_fun();");
	}

	private void generateStudyEcgTimePoints(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_ecg_time_points_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   ecgpositionName   character varying;\r\n" + "   oldecgpositionName character varying;\r\n"
				+ "   timepointtypeName   character varying;\r\n" + "   oldtimepointtypeName   character varying;\r\n"
				+ "   treatmentinfoidName   character varying;\r\n"
				+ "   oldtreatmentinfoidName   character varying;\r\n"
				+ "  windowperiodtypeName   character varying;\r\n"
				+ "   oldwindowperiodtypeName  character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ "IF (old.ecgtimepointsid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.created_by,'CREATE',new.ecgtimepointsid,'study_ecg_time_points',new.studyid,null,null,'study_ecg_time_points_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+

				"IF (new.parameters IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.parameters','',new.parameters,'static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_ecg_time_points','audit_insert','parameters');\r\n"
				+ "END IF;\r\n" +

				"IF (new.sign IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.sign','',new.sign,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_ecg_time_points','audit_insert','sign');\r\n"
				+ "END IF;\r\n" +

				"IF (new.timepoint IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepoint','',new.timepoint,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_ecg_time_points','audit_insert','timepoint');\r\n"
				+ "END IF;\r\n" +

				"IF (new.timepointno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointno','',new.timepointno,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_ecg_time_points','audit_insert','timepointno');\r\n"
				+ "END IF;\r\n" +

				"IF (new.windowperiod IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiod','',new.windowperiod,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_ecg_time_points','audit_insert','windowperiod');\r\n"
				+ "END IF;\r\n" +

				"IF (new.windowperiodsign IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiodsign','',new.windowperiodsign,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_ecg_time_points','audit_insert','windowperiodsign');\r\n"
				+ "END IF;\r\n" +

				"IF (new.ecgposition IS NOT NULL) THEN\r\n"
				+ " SELECT code FROM bedc.form_static_data where formstaticdataid = new.ecgposition into ecgpositionName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('ecgposition','',ecgpositionName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_ecg_time_points','audit_insert','ecgposition');\r\n"
				+ "END IF;\r\n" + "IF (new.timepointtype IS NOT NULL) THEN\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.timepointtype into timepointtypeName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('timepointtype','',timepointtypeName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_ecg_time_points','audit_insert','timepointtype');\r\n"
				+ "END IF;\r\n" +

				"IF (new.treatmentinfoid IS NOT NULL) THEN\r\n"
				+ " SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentinfoid into treatmentinfoidName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('treatmentinfoid','',treatmentinfoidName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_ecg_time_points','audit_insert','treatmentinfoid');\r\n"
				+ "END IF;\r\n" +

				"IF (new.windowperiodtype IS NOT NULL) THEN\r\n"
				+ " SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = new.windowperiodtype into windowperiodtypeName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('windowperiodtype','',windowperiodtypeName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_dose_time_points','audit_insert','windowperiodtype');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" +

				"ELSE IF (new.ecgtimepointsid=old.ecgtimepointsid) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.ecgtimepointsid,'study_ecg_time_points',new.studyid,null,null,'study_ecg_time_points_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+

				"IF nullif(old.parameters,'') <> NULLIF(new.parameters,'') or new.parameters is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.parameters',old.parameters,new.parameters,'static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_ecg_time_points','audit_update','parameters');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.sign,'') <> NULLIF(new.sign,'') or new.sign is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.sign',old.sign,new.sign,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_ecg_time_points','audit_update','sign');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.timepoint,'') <> NULLIF(new.timepoint,'') or new.timepoint is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepoint',old.timepoint,new.timepoint,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_ecg_time_points','audit_update','timepoint');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.timepointno,0) <> NULLIF(new.timepointno,0) or new.timepointno is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointno',old.timepointno,new.timepointno,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_ecg_time_points','audit_update','timepointno');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.windowperiod,0) <> NULLIF(new.windowperiod,0) or new.windowperiod is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiod',new.windowperiod,new.windowperiod,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_ecg_time_points','audit_update','windowperiod');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.windowperiodsign,'') <> NULLIF(new.windowperiodsign,'') or new.windowperiodsign is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiodsign',old.windowperiodsign,new.windowperiodsign,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_ecg_time_points','audit_update','windowperiodsign');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.ecgposition,null) <> NULLIF(new.ecgposition,null) or new.ecgposition is null THEN\r\n"
				+ " SELECT code FROM bedc.form_static_data where formstaticdataid = new.ecgposition into oldecgpositionName;\r\n"
				+ " SELECT code FROM bedc.form_static_data where formstaticdataid = new.ecgposition into ecgpositionName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('ecgposition',oldecgpositionName,ecgpositionName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_ecg_time_points','audit_update','ecgposition');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.timepointtype,null) <> NULLIF(new.timepointtype,null) or new.timepointtype is null THEN\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.timepointtype into oldtimepointtypeName;\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.timepointtype into timepointtypeName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('timepointtype',oldtimepointtypeName,timepointtypeName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_ecg_time_points','audit_update','timepointtype');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.treatmentinfoid,null) <> NULLIF(new.treatmentinfoid,null) or new.treatmentinfoid is null THEN\r\n"
				+ " SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentinfoid into oldtreatmentinfoidName;\r\n"
				+ "  SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentinfoid into treatmentinfoidName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('treatmentinfoid',oldtreatmentinfoidName,treatmentinfoidName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_ecg_time_points','audit_update','treatmentinfoid');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.windowperiodtype,null) <> NULLIF(new.windowperiodtype,null) or new.windowperiodtype is null THEN\r\n"
				+ " SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = new.windowperiodtype into oldwindowperiodtypeName;\r\n"
				+ "  SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = new.windowperiodtype into windowperiodtypeName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('windowperiodtype',oldwindowperiodtypeName,windowperiodtypeName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_dose_time_points','audit_update','windowperiodtype');\r\n"
				+ "END IF;\r\n" +

				" RETURN new;\r\n" + "\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n" + "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_ecg_time_points_trgr"
				+ "   AFTER INSERT OR UPDATE  OF ecgtimepointsid,created_by,created_on,parameters,sign,timepoint,timepointno,update_reason,updated_by,updated_on,windowperiod,windowperiodsign,ecgposition,studyid,timepointtype,treatmentinfoid,windowperiodtype  ON bedc.study_ecg_time_points\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_ecg_time_points_fun();");

	}

	private void generateStudyGroup(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_group_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.studygroupid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " 	VALUES (new.created_by,'CREATE',new.studygroupid,'study_group',new.studyid,null,null,'study_group_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+

				"IF (new.group_close IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.group_close','',new.group_close,'Static',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_group','audit_insert','group_close');\r\n"
				+ "END IF;\r\n" +

				"IF (new.groupname IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.groupname','',new.groupname,'Static',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_group','audit_insert','groupname');\r\n"
				+ "END IF;\r\n" +

				"IF (new.groupno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.groupno','',new.groupno,'Static',new.studyid,null,null,' ',new.created_by,new.created_on,incCount,'study_group','audit_insert','groupno');\r\n"
				+ "END IF;\r\n" + "\r\n" + "RETURN new;\r\n" + "ELSE IF (new.studygroupid=old.studygroupid) THEN\r\n" + // UPDATE
																														// TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "	 VALUES (new.updated_by,'UPDATE',new.studygroupid,'study_group',null,null,null,'study_group_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+

				"IF nullif(old.group_close,null) <> NULLIF(new.group_close,null) or new.group_close is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.group_close','',new.group_close,'Static',new.studyid,null,null,'',new.updated_by,new.updated_on,incCount,'study_group','audit_insert','group_close');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.groupname,'') <> NULLIF(new.groupname,'') or new.groupname is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.groupname','',new.groupname,'Static',new.studyid,null,null,'',new.updated_by,new.updated_on,incCount,'study_group','audit_insert','groupname');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.groupno,0) <> NULLIF(new.groupno,0) or new.groupno is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('label.groupno','',new.groupno,'Static',new.studyid,null,null,'',new.updated_by,new.updated_on,incCount,'study_group','audit_insert','groupno');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_group_trgr"
				+ "   AFTER INSERT OR UPDATE  OF studygroupid,created_by,created_on,group_close,groupname,groupno,update_reason,updated_by,updated_on,studyid  ON bedc.study_group\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_group_fun();");

	}

	private void generateStudyMaster(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_master_fun() RETURNS TRIGGER AS $$\r\n" + "declare \r\n"
				+ "   incCount integer;\r\n" + "   actid integer;\r\n" + "   dosagefromName character varying;\r\n"
				+ "   olddosagefromName character varying;\r\n" + "   dosetypeName   character varying;\r\n"
				+ "	olddosetypeName  character varying;\r\n" + "	studycategoryName  character varying;\r\n"
				+ "	oldstudycategoryName  character varying;	\r\n" + "	studydesingName  character varying;\r\n"
				+ "	oldstudydesingName  character varying;\r\n" + "	studystateName  character varying;\r\n"
				+ "	oldstudystateName  character varying;\r\n" + "   studystatusName   character varying;\r\n"
				+ "	oldstudystatusName  character varying;\r\n" + "	studytypeName  character varying;\r\n"
				+ "	oldstudytypeName  character varying;\r\n" + "	treatmentcodeonsachetName  character varying;\r\n"
				+ "	oldtreatmentcodeonsachetName  character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1; \r\n"
				+ "IF (old.studyid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.created_by,'CREATE',new.studyid,'study_master',null,null,null,'study_master_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+

				"IF (new.noofperiods IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.noofperiods','',new.noofperiods,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','noofperiods');\r\n"
				+ "END IF;\r\n" + "IF (new.noofstandbysubjects IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.noofstandbysubjects','',new.noofstandbysubjects,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','noofstandbysubjects');\r\n"
				+ "END IF;\r\n" + "IF (new.noofsubjects IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.noofsubjects','',new.noofsubjects,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','noofsubjects');\r\n"
				+ "END IF;\r\n" + "IF (new.nooftreatments IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.nooftreatments','',new.nooftreatments,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','nooftreatments');\r\n"
				+ "END IF;\r\n" + "IF (new.postdosehr IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.postdosehr','',new.postdosehr,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','postdosehr');\r\n"
				+ "END IF;\r\n" + "IF (new.predosehr IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.predosehr','',new.predosehr,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','predosehr');\r\n"
				+ "END IF;\r\n" + "IF (new.projectno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.projectno','',new.projectno,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','projectno');\r\n"
				+ "END IF;\r\n" + "IF (new.seqno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.seqno','',new.seqno,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','seqno');\r\n"
				+ "END IF;\r\n" + "IF (new.sponsorcode IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.sponsorcode','',new.sponsorcode,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','sponsorcode');\r\n"
				+ "END IF;\r\n" + "IF (new.studytitle IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.studytitle','',new.studytitle,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','studytitle');\r\n"
				+ "END IF;\r\n" + "IF (new.subjectdoseingtimepoints IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectdoseingtimepoints','',new.subjectdoseingtimepoints,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','subjectdoseingtimepoints');\r\n"
				+ "END IF;\r\n" + "IF (new.subjectecgtimepoits IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectecgtimepoits','',new.subjectecgtimepoits,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','subjectecgtimepoits');\r\n"
				+ "END IF;\r\n" + "IF (new.subjectmealstimepoits IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectmealstimepoits','',new.subjectmealstimepoits,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','treatmentcodeonsachet');\r\n"
				+ "END IF;\r\n" + "IF (new.subjectsamplestimepoints IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectsamplestimepoints','',new.subjectsamplestimepoints,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','subjectsamplestimepoints');\r\n"
				+ "END IF;\r\n" + "IF (new.subjectvitalstimepoits IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectvitalstimepoits','',new.subjectvitalstimepoits,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','subjectvitalstimepoits');\r\n"
				+ "END IF;\r\n" + "IF (new.treatmentspecificsampletimepoints IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatmentspecificsampletimepoints','',new.treatmentspecificsampletimepoints,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','treatmentspecificsampletimepoints');\r\n"
				+ "END IF;\r\n" + "IF (new.treatmentwisedoseparametres IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatmentwisedoseparametres','',new.treatmentwisedoseparametres,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','treatmentwisedoseparametres');\r\n"
				+ "END IF;\r\n" + "IF (new.treatmentwiseecgtimepoints IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatmentwiseecgtimepoints','',new.treatmentwiseecgtimepoints,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','treatmentwiseecgtimepoints');\r\n"
				+ "END IF;\r\n" + "IF (new.treatmentwisemealstimepoints IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatmentwisemealstimepoints','',new.treatmentwisemealstimepoints,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','treatmentwisemealstimepoints');\r\n"
				+ "END IF;\r\n" + "IF (new.treatmentwisenoofdosing IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatmentwisenoofdosing','',new.treatmentwisenoofdosing,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','treatmentwisenoofdosing');\r\n"
				+ "END IF;\r\n" + "IF (new.treatmentwisevitaltimepoints IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatmentwisevitaltimepoints','',new.treatmentwisevitaltimepoints,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','treatmentwisevitaltimepoints');\r\n"
				+ "END IF;\r\n" + "IF (new.versionno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.versionno','',new.versionno,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','versionno');\r\n"
				+ "END IF;\r\n" + "IF (new.washoutperiod IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.washoutperiod','',new.washoutperiod,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','washoutperiod');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldname FROM bedc.study_dosage_form where dosagefromid = new.dosagefrom into dosagefromName;\r\n"
				+ "IF (new.dosagefrom IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('dosagefrom','',dosagefromName,'Dynamic',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','dosagefrom');\r\n"
				+ "END IF;\r\n"
				+ " SELECT code FROM bedc.form_static_data where formstaticdataid = new.dosetype into dosetypeName;\r\n"
				+ "IF (new.dosetype IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('dosetype','',dosetypeName,'Dynamic',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','dosetype');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.studycategory into studycategoryName;\r\n"
				+ "IF (new.studycategory IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('studycategory','',studycategoryName,'Dynamic',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','studycategory');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = new.studydesing into studydesingName;\r\n"
				+ "IF (new.studydesing IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('studydesing','',studydesingName,'Dynamic',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','studydesing');\r\n"
				+ "END IF;\r\n"
				+ " SELECT statuscode FROM bedc.status_master where statusid = new.studystate into studystateName;\r\n"
				+ "IF (new.studystate IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('studystate','',studystateName,'Dynamic',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','studystate');\r\n"
				+ "END IF;\r\n"
				+ " SELECT statusdesc FROM bedc.status_master where statusid = new.studystatus into studystatusName;\r\n"
				+ "IF (new.studystatus IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('studystatus','',studystatusName,'Dynamic',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','studystatus');\r\n"
				+ "END IF; \r\n"
				+ " SELECT code FROM bedc.form_static_data where formstaticdataid = new.studytype into studytypeName;\r\n"
				+ "IF (new.studytype IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('studytype','',studytypeName,'Dynamic',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','studytype');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.treatmentcodeonsachet into treatmentcodeonsachetName;\r\n"
				+ "IF (new.treatmentcodeonsachet IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('treatmentcodeonsachet','',treatmentcodeonsachetName,'Dynamic',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','treatmentcodeonsachet');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" + "ELSE IF (new.studyid=old.studyid) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.studyid,'study_master',null,null,null,'study_master_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+

				"IF nullif(old.noofperiods,0) <> NULLIF(new.noofperiods,0) or new.noofperiods is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.noofperiods','',new.noofperiods,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','noofperiods');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.noofstandbysubjects,0) <> NULLIF(new.noofstandbysubjects,0) or new.noofstandbysubjects is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.noofstandbysubjects','',new.noofstandbysubjects,'Static',null,null,null,' ',null,new.created_on,incCount,'study_master','audit_insert','noofstandbysubjects');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.noofsubjects,0) <> NULLIF(new.noofsubjects,0) or new.noofsubjects is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.noofsubjects','',new.noofsubjects,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','noofsubjects');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.nooftreatments,0) <> NULLIF(new.nooftreatments,0) or new.nooftreatments is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.nooftreatments','',new.nooftreatments,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','nooftreatments');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.postdosehr,0) <> NULLIF(new.postdosehr,0) or new.postdosehr is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.postdosehr','',new.postdosehr,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','postdosehr');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.predosehr,0) <> NULLIF(new.predosehr,0) or new.predosehr is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.predosehr','',new.predosehr,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','predosehr');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.projectno,'') <> NULLIF(new.projectno,'') or new.projectno is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.projectno','',new.projectno,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','projectno');\r\n"
				+ "END IF;\r\n" + "IF nullif(old.seqno,0) <> NULLIF(new.seqno,0) or new.seqno is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.seqno','',new.seqno,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','seqno');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.sponsorcode,'') <> NULLIF(new.sponsorcode,'') or new.sponsorcode is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.sponsorcode','',new.sponsorcode,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','sponsorcode');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.studytitle,'') <> NULLIF(new.studytitle,'') or new.studytitle is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.studytitle','',new.studytitle,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','studytitle');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.subjectdoseingtimepoints,null) <> NULLIF(new.subjectdoseingtimepoints,null) or new.subjectdoseingtimepoints is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectdoseingtimepoints','',new.subjectdoseingtimepoints,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','subjectdoseingtimepoints');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.subjectecgtimepoits,null) <> NULLIF(new.subjectecgtimepoits,null) or new.subjectecgtimepoits is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectecgtimepoits','',new.subjectecgtimepoits,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','subjectecgtimepoits');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.subjectmealstimepoits,null) <> NULLIF(new.subjectmealstimepoits,null) or new.subjectmealstimepoits is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectmealstimepoits','',new.subjectmealstimepoits,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','subjectmealstimepoits');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.subjectsamplestimepoints,null) <> NULLIF(new.subjectsamplestimepoints,null) or new.subjectsamplestimepoints is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES (actid,'label.subjectsamplestimepoints','',new.subjectsamplestimepoints,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','subjectsamplestimepoints');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.subjectvitalstimepoits,null) <> NULLIF(new.subjectvitalstimepoits,null) or new.subjectvitalstimepoits is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.subjectvitalstimepoits','',new.subjectvitalstimepoits,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','subjectvitalstimepoits');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.treatmentspecificsampletimepoints,null) <> NULLIF(new.treatmentspecificsampletimepoints,null) or new.treatmentspecificsampletimepoints is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatmentspecificsampletimepoints','',new.treatmentspecificsampletimepoints,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','treatmentspecificsampletimepoints');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.treatmentwisedoseparametres,null) <> NULLIF(new.treatmentwisedoseparametres,null) or new.treatmentwisedoseparametres is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatmentwisedoseparametres','',new.treatmentwisedoseparametres,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','treatmentwisedoseparametres');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.treatmentwiseecgtimepoints,null) <> NULLIF(new.treatmentwiseecgtimepoints,null) or new.treatmentwiseecgtimepoints is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatmentwiseecgtimepoints','',new.treatmentwiseecgtimepoints,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','treatmentwiseecgtimepoints');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.treatmentwisemealstimepoints,null) <> NULLIF(new.treatmentwisemealstimepoints,null) or new.treatmentwisemealstimepoints is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatmentwisemealstimepoints','',new.treatmentwisemealstimepoints,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','treatmentwisemealstimepoints');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.treatmentwisenoofdosing,null) <> NULLIF(new.treatmentwisenoofdosing,null) or new.treatmentwisenoofdosing is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatmentwisenoofdosing','',new.treatmentwisenoofdosing,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','treatmentwisenoofdosing');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.treatmentwisevitaltimepoints,null) <> NULLIF(new.treatmentwisevitaltimepoints,null) or new.treatmentwisevitaltimepoints is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatmentwisevitaltimepoints','',new.treatmentwisevitaltimepoints,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','treatmentwisevitaltimepoints');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.versionno,'') <> NULLIF(new.versionno,'') or new.versionno is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.versionno','',new.versionno,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','versionno');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.washoutperiod,0) <> NULLIF(new.washoutperiod,0) or new.washoutperiod is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.washoutperiod','',new.washoutperiod,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','washoutperiod');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldname FROM bedc.study_dosage_form where dosagefromid = new.dosagefrom into olddosagefromName;\r\n"
				+ "  SELECT fieldname FROM bedc.study_dosage_form where dosagefromid = new.dosagefrom into dosagefromName;\r\n"
				+ "IF nullif(old.dosagefrom,null) <> NULLIF(new.dosagefrom,null) or new.dosagefrom is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('dosagefrom',olddosagefromName,dosagefromName,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','dosagefrom');\r\n"
				+ "END IF;\r\n" + "\r\n"
				+ " SELECT code FROM bedc.form_static_data where formstaticdataid = new.dosetype into olddosetypeName;\r\n"
				+ "  SELECT code FROM bedc.form_static_data where formstaticdataid = new.dosetype into dosetypeName;\r\n"
				+ "IF nullif(old.dosetype,null) <> NULLIF(new.dosetype,null) or new.dosetype is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('dosetype',olddosetypeName,dosetypeName,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','dosetype');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.studycategory into oldstudycategoryName;\r\n"
				+ "  SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.studycategory into studycategoryName;\r\n"
				+ "IF nullif(old.studycategory,null) <> NULLIF(new.studycategory,null) or new.studycategory is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('studycategory',oldstudycategoryName,studycategoryName,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','studycategory');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = new.studydesing into oldstudydesingName;\r\n"
				+ "  SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = new.studydesing into studydesingName;\r\n"
				+ "IF nullif(old.studydesing,null) <> NULLIF(new.studydesing,null) or new.studydesing is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('studydesing',oldstudydesingName,studydesingName,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','studydesing');\r\n"
				+ "END IF;\r\n"
				+ " SELECT statuscode FROM bedc.status_master where statusid = new.studystate into oldstudystateName;\r\n"
				+ "  SELECT statuscode FROM bedc.status_master where statusid = new.studystate into studystateName;\r\n"
				+ "IF nullif(old.studystate,null) <> NULLIF(new.studystate,null) or new.studystate is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('studystate',oldstudystateName,studystateName,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','studystate');\r\n"
				+ "END IF;\r\n"
				+ " SELECT statusdesc FROM bedc.status_master where statusid = new.studystatus into oldstudystatusName;\r\n"
				+ "  SELECT statusdesc FROM bedc.status_master where statusid = new.studystatus into studystatusName;\r\n"
				+ "IF nullif(old.studystatus,null) <> NULLIF(new.studystatus,null) or new.studystatus is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES('studystatus',oldstudystatusName,studystatusName,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','studystatus');\r\n"
				+ "END IF; \r\n"
				+ " SELECT code FROM bedc.form_static_data where formstaticdataid = new.studytype into oldstudytypeName;\r\n"
				+ "  SELECT code FROM bedc.form_static_data where formstaticdataid = new.studytype into studytypeName;\r\n"
				+ "IF nullif(old.studytype,null) <> NULLIF(new.studytype,null) or new.studytype is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('studytype',oldstudytypeName,studytypeName,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','studytype');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.treatmentcodeonsachet into oldtreatmentcodeonsachetName;\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.treatmentcodeonsachet into treatmentcodeonsachetName;\r\n"
				+ "IF nullif(old.treatmentcodeonsachet,null) <> NULLIF(new.treatmentcodeonsachet,null) or new.treatmentcodeonsachet is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('treatmentcodeonsachet',oldtreatmentcodeonsachetName,treatmentcodeonsachetName,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_master','audit_insert','treatmentcodeonsachet');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_master_trgr"
				+ "   AFTER INSERT OR UPDATE  OF studyid,created_by,created_on,noofperiods,noofstandbysubjects,noofsubjects,nooftreatments,postdosehr,predosehr,projectno,seqno,sponsorcode,studytitle,subjectdoseingtimepoints,subjectecgtimepoits,subjectmealstimepoits,subjectsamplestimepoints,subjectvitalstimepoits,treatmentspecificsampletimepoints,treatmentwisedoseparametres,treatmentwiseecgtimepoints,treatmentwisemealstimepoints,treatmentwisenoofdosing,treatmentwisevitaltimepoints,update_reason,updated_by,updated_on,versionno,washoutperiod,dosagefrom,dosetype,studycategory,studydesing,studystate,studystatus,studytype,treatmentcodeonsachet  ON bedc.study_master\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_master_fun();");

	}

	private void generateStudyMealsTimePoints(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_meals_time_points_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   completionName character varying;\r\n" + "   oldcompletionName character varying;\r\n"
				+ "   mealstypeName  character varying;\r\n" + "   oldmealstypeName  character varying;\r\n"
				+ "   timepointmetadataidName  character varying;\r\n"
				+ "    oldtimepointmetadataidName  character varying;\r\n"
				+ "	timepointtypeName  character varying;\r\n" + "	oldtimepointtypeName  character varying;\r\n"
				+ "	treatmentinfoidName  character varying;\r\n" + "	oldtreatmentinfoidName character varying;\r\n"
				+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n" + "IF (old.sampletimepointid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " 	VALUES (new.created_by,'CREATE',new.sampletimepointid,'study_meals_time_points',new.studyid,null,null,'study_meals_time_points_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+

				"IF (new.completiontime IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.completiontime','',new.completiontime,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_meals_time_points','audit_insert','completiontime');\r\n"
				+ "END IF;\r\n" + "IF (new.sign IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.sign','',new.sign,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_meals_time_points','audit_insert','sign');\r\n"
				+ "END IF;\r\n" + "IF (new.timePoint IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timePoint','',new.timePoint,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_meals_time_points','audit_insert','timePoint');\r\n"
				+ "END IF;\r\n" + "IF (new.timepointno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointno','',new.timepointno,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_meals_time_points','audit_insert','timepointno');\r\n"
				+ "END IF;\r\n" + "IF (new.windowperiod IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiod','',new.windowperiod,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_meals_time_points','audit_insert','windowperiod');\r\n"
				+ "END IF;\r\n" + "IF (new.windowperiodsign IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiodsign','',new.windowperiodsign,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_meals_time_points','audit_insert','windowperiodsign');\r\n"
				+ "END IF;\r\n"
				+ " SELECT code FROM bedc.form_static_data where formstaticdataid = new.completion into completionName;\r\n"
				+ "IF (new.completion IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('completion','',completionName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_meals_time_points','audit_insert','completion');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.mealstype into mealstypeName;\r\n"
				+ "IF (new.mealstype IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('mealstype','',mealstypeName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_meals_time_points','audit_insert','mealstype');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = new.timepointtype into timepointtypeName;\r\n"
				+ "IF (new.timepointtype IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('timepointtype','',timepointtypeName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_meals_time_points','audit_insert','timepointtype');\r\n"
				+ "END IF;\r\n"
				+ " SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentinfoid into treatmentinfoidName;\r\n"
				+ "IF (new.treatmentinfoid IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('treatmentinfoid','',treatmentinfoidName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_meals_time_points','audit_insert','treatmentinfoid');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.sampletimepointid=old.sampletimepointid) THEN\r\n" + // UPDATE
																															// TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.sampletimepointid,'study_meals_time_points',new.studyid,null,null,'study_meals_time_points_updation',new.updated_on)RETURNING id INTO incCount;\r\n"
				+

				"IF nullif(old.completiontime,0) <> NULLIF(new.completiontime,0) or new.completiontime is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.completiontime','',new.completiontime,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_meals_time_points','audit_update','completiontime');\r\n"
				+ "END IF;\r\n" + "IF nullif(old.sign,'') <> NULLIF(new.sign,'') or new.sign is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.sign','',new.sign,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_meals_time_points','audit_update','sign');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.timePoint,'') <> NULLIF(new.timePoint,'') or new.timePoint is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timePoints','',new.timePoint,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_meals_time_points','audit_update','timePoint');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.timepointno,0) <> NULLIF(new.timepointno,0) or new.timepointno is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointno','',new.timepointno,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_meals_time_points','audit_update','timepointno');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.windowperiod,0) <> NULLIF(new.windowperiod,0) or new.windowperiod is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiod','',new.windowperiod,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_meals_time_points','audit_update','windowperiod');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.windowperiodsign,'') <> NULLIF(new.windowperiodsign,'') or new.windowperiodsign is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiodsign','',new.windowperiodsign,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_meals_time_points','audit_update','windowperiodsign');\r\n"
				+ "END IF;\r\n"
				+ " SELECT code FROM bedc.form_static_data where formstaticdataid = new.completion into oldcompletionName;\r\n"
				+ " SELECT code FROM bedc.form_static_data where formstaticdataid = new.completion into completionName;\r\n"
				+ "IF nullif(old.completion,null) <> NULLIF(new.completion,null) or new.completion is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('completion',oldcompletionName,completionName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_meals_time_points','audit_update','completion');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.mealstype into oldmealstypeName;\r\n"
				+ "SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.mealstype into mealstypeName;\r\n"
				+ "IF nullif(old.mealstype,null) <> NULLIF(new.mealstype,null) or new.mealstype is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('mealstype',oldmealstypeName,mealstypeName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_meals_time_points','audit_update','mealstype');\r\n"
				+ "END IF;\r\n"
				+ " SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = new.timepointtype into oldtimepointtypeName;\r\n"
				+ " SELECT fieldvalue FROM bedc.form_static_data where formstaticdataid = new.timepointtype into timepointtypeName;\r\n"
				+ "IF nullif(old.timepointtype,null) <> NULLIF(new.timepointtype,null) or new.timepointtype is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('timepointtype',oldtimepointtypeName,timepointtypeName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_meals_time_points','audit_update','timepointtype');\r\n"
				+ "END IF;\r\n"
				+ " SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentinfoid into oldtreatmentinfoidName;\r\n"
				+ " SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentinfoid into treatmentinfoidName;\r\n"
				+ "IF nullif(old.treatmentinfoid,null) <> NULLIF(new.treatmentinfoid,null) or new.treatmentinfoid is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('treatmentinfoid',oldtreatmentinfoidName,treatmentinfoidName,'Dynamic',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_meals_time_points','audit_update','treatmentinfoid');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_meals_time_points_trgr"
				+ "   AFTER INSERT OR UPDATE  OF sampletimepointid,completiontime,created_by,created_on,sign,timepoint,timepointno,updated_by,\r\n"
				+ "updated_on,windowperiod,windowperiodsign,completion,mealstype,studyid,timepointtype,treatmentinfoid\r\n"
				+ "  ON bedc.study_meals_time_points\r\n" + "    FOR EACH ROW\r\n"
				+ "    EXECUTE FUNCTION bedc.study_meals_time_points_fun();");

	}

	private void generateStudyPeriodMaster(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_period_master_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   periodstatusName character varying;\r\n" + "   oldperiodstatusName character varying;\r\n"
				+ "   periodtypeName  character varying;\r\n" + "   oldperiodtypeName  character varying;\r\n"
				+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n" + " select count(*) from bedc.audit_log into actid;\r\n"
				+ " actid := actid +1;\r\n" + "IF (old.periodid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ " 	VALUES (new.created_by,'CREATE',new.periodid,'study_period_master',new.studyid,null,null,'study_period_master_creation',new.created_on)returning id into incCount; \r\n"
				+

				"IF (new.periodname IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.periodname','',new.periodname,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_period_master','audit_insert','periodname');\r\n"
				+ "END IF;\r\n" + "IF (new.periodno IS NOT NULL) THEN \r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.periodno','',new.periodno,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_period_master','audit_insert','periodno');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" + "ELSE IF (new.periodid=old.periodid) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.periodid,'study_period_master',new.studyid,null,null,'study_period_master_updation',new.updated_on)returning id into incCount;\r\n"
				+ "\r\n"
				+ "IF nullif(old.periodname,'') <> NULLIF(new.periodname,'') or new.periodname is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.periodname',old.periodname,new.periodname,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_period_master','audit_update','periodname');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.periodno,0) <> NULLIF(new.periodno,0) or new.periodno is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.periodno',old.periodno,new.periodno,'Static',new.studyid,null,null,' ',new.updated_by,new.updated_on,incCount,'study_period_master','audit_update','periodno');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n" + "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_period_master_trgr"
				+ "   AFTER INSERT OR UPDATE  OF periodid,created_by,created_on,periodname,periodno,update_reason,updated_by,updated_on,studyid   ON bedc.study_period_master\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_period_master_fun();");

	}

	private void generateStudySampleCentrifugation(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_sample_centrifugation_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   newstudy character varying;\r\n" + "   oldstudy character varying;\r\n"
				+ "   newtreatmentid character varying;\r\n" + "   oldtreatmentid character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.created_by,'CREATE',new.id,'study_sample_centrifugation',null,null,null,'study_sample_centrifugation_creation',new.created_on) RETURNING id INTO incCount; \r\n"
				+

				"IF (new.centrifugation_applicable IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.centrifugation_applicable','',new.centrifugation_applicable,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_centrifugation','audit_insert','centrifugation_applicable');\r\n"
				+ "END IF;\r\n" +

				"select studytitle from bedc.study_master where studyid= new.study into newstudy;  \r\n"
				+ "IF (new.study IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.study','',newstudy,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_centrifugation','audit_insert','study');\r\n"
				+ "END IF;\r\n" +

				"select treatmentname from bedc.study_treatments where treatmentinfoid= new.treatment into newtreatmentid;  \r\n"
				+ "IF (new.treatment IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatment','',newtreatmentid,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_centrifugation','audit_insert','treatment');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" +

				"ELSE IF (new.id=old.id) THEN\r\n" + /// UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.id,'study_sample_centrifugation',null,null,null,'study_sample_centrifugation_updation',new.updated_on)\r\n"
				+ "RETURNING id INTO incCount;\r\n" +

				"IF nullif(old.centrifugation_applicable,null) <> NULLIF(new.centrifugation_applicable,null) or new.centrifugation_applicable is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.centrifugation_applicable','',new.centrifugation_applicable,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_centrifugation','audit_update','centrifugation_applicable');\r\n"
				+ "END IF;\r\n" +

				"select studytitle from bedc.study_master where studyid= old.study into oldstudy;  \r\n"
				+ "select studytitle from bedc.study_master where studyid= new.study into newstudy;  \r\n"
				+ "IF nullif(old.study,null) <> NULLIF(new.study,null) or new.study is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.study',oldstudy,newstudy,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_centrifugation','audit_update','study');\r\n"
				+ "END IF;\r\n" +

				"select treatmentname from bedc.study_treatments where treatmentinfoid= old.treatment into oldtreatmentid;  \r\n"
				+ "select treatmentname from bedc.study_treatments where treatmentinfoid= new.treatment into newtreatmentid;  \r\n"
				+ "IF nullif(old.treatment,null) <> NULLIF(new.treatment,null) or new.treatment is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatment',oldtreatmentid,newtreatmentid,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_centrifugation','audit_update','treatment');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" + "	END IF;\r\n" + "	END IF;\r\n" + "	END;" + "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_sample_centrifugation_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,centrifugation_applicable,created_by,created_on,update_reason,updated_by,updated_on,study,treatment   ON bedc.study_sample_centrifugation\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_sample_centrifugation_fun();");

	}

	private void generateStudySampleProcessing(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_sample_processing_fun() RETURNS TRIGGER AS $$\r\n"
				+ "declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   newstudy character varying;\r\n" + "   oldstudy character varying;\r\n"
				+ "   newtreatmentid character varying;\r\n" + "   oldtreatmentid character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.created_by,'CREATE',new.id,'study_sample_processing',null,null,null,'study_sample_processing_creation',new.created_on) RETURNING id INTO incCount; \r\n"
				+

				"IF (new.allowed_time IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.allowed_time','',new.allowed_time,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_processing','audit_insert','allowed_time');\r\n"
				+ "END IF;\r\n" +

				"IF (new.allowed_timefrom IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.allowed_timefrom','',new.allowed_timefrom,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_processing','audit_insert','allowed_timefrom');\r\n"
				+ "END IF;\r\n" +

				"IF (new.conditions IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.conditions','',new.conditions,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_processing','audit_insert','conditions');\r\n"
				+ "END IF;\r\n" +

				"IF (new.matrix_different IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.matrix_different','',new.matrix_different,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_processing','audit_insert','matrix_different');\r\n"
				+ "END IF;\r\n" +

				"select studytitle from bedc.study_master where studyid= new.study into newstudy;  \r\n"
				+ "IF (new.study IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('study','',newstudy,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_processing','audit_insert','study');\r\n"
				+ "END IF;\r\n" +

				"select treatmentname from bedc.study_treatments where treatmentinfoid= new.treatment into newtreatmentid;  \r\n"
				+ "IF (new.treatment IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatment','',newtreatmentid,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_processing','audit_insert','treatment');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.id,'study_sample_processing',null,null,null,'study_sample_processing_updation',new.updated_on)\r\n"
				+ "RETURNING id INTO incCount;\r\n" +

				"IF nullif(old.allowed_time,0) <> NULLIF(new.allowed_time,0) or new.allowed_time is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.allowed_time',old.allowed_time,new.allowed_time,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_processing','audit_update','allowed_time');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.allowed_timefrom,null) <> NULLIF(new.allowed_timefrom,null) or new.allowed_timefrom is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.allowed_timefrom',old.allowed_timefrom,new.storage_different,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_processing','audit_update','allowed_timefrom');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.conditions,null) <> NULLIF(new.conditions,null) or new.conditions is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.conditions',old.conditions,new.conditions,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_processing','audit_update','conditions');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.matrix_different,null) <> NULLIF(new.matrix_different,null) or new.matrix_different is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.matrix_different',old.matrix_different,new.matrix_different,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_processing','audit_update','storage_different');\r\n"
				+ "END IF;\r\n" +

				"select studytitle from bedc.study_master where studyid= old.study into oldstudy;  \r\n"
				+ "select studytitle from bedc.study_master where studyid= new.study into newstudy;  \r\n"
				+ "IF nullif(old.study,null) <> NULLIF(new.study,null) or new.study is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('study',oldstudy,newstudy,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_processing','audit_update','study');\r\n"
				+ "END IF;\r\n" +

				"select treatmentname from bedc.study_treatments where treatmentinfoid= old.treatment into oldtreatmentid;  \r\n"
				+ "select treatmentname from bedc.study_treatments where treatmentinfoid= new.treatment into newtreatmentid;  \r\n"
				+ "IF nullif(old.treatment,null) <> NULLIF(new.treatment,null) or new.treatment is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('treatment',oldtreatmentid,newtreatmentid,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_processing','audit_update','treatment');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" + "	END IF;\r\n" + "	END IF;\r\n" + "	END;" + "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_sample_processing_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,allowed_time,allowed_timefrom,conditions,created_by,created_on,matrix_different,update_reason,updated_by,updated_on,study,treatment   ON bedc.study_sample_processing\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_sample_processing_fun();");

	}

	private void generateStudySampleProcessingDetails(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_sample_processing_details_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   oldcondition character varying;\r\n" + "   newcondition character varying;\r\n"
				+ "   oldprocessing character varying;\r\n" + "   newprocessing character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.created_by,'CREATE',new.id,'study_sample_processing_details',null,null,null,'study_sample_processing_details_creation',new.created_on) RETURNING id INTO incCount; \r\n"
				+

				"IF (new.aliquot_volume IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.aliquot_volume','',new.aliquot_volume,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_processing_details','audit_insert','aliquot_volume');\r\n"
				+ "END IF;\r\n" +

				"select name from bedc.condition_parameter where id= new.condition into newcondition;  \r\n"
				+ "IF (new.condition IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('condition','',newcondition,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_processing_details','audit_insert','condition');\r\n"
				+ "END IF;\r\n" +

				"select conditions from bedc.study_sample_processing where id= new.processing into newprocessing;  \r\n"
				+ "IF (new.processing IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('processing','',newprocessing,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_processing_details','audit_insert','processing');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" +

				"ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.id,'study_sample_processing_details',null,null,null,'study_sample_processing_details_updation',new.updated_on)\r\n"
				+ "RETURNING id INTO incCount;\r\n" +

				"IF nullif(old.aliquot_volume,null) <> NULLIF(new.aliquot_volume,null) or new.aliquot_volume is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.aliquot_volume','',new.storage_different,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_processing_details','audit_update','aliquot_volume');\r\n"
				+ "END IF;\r\n" +

				"select name from bedc.condition_parameter where id= old.condition into oldcondition;  \r\n"
				+ "select name from bedc.condition_parameter where id= new.condition into newcondition;  \r\n"
				+ "IF nullif(old.condition,null) <> NULLIF(new.condition,null) or new.condition is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('condition',oldcondition,newcondition,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_processing_details','audit_update','condition');\r\n"
				+ "END IF;\r\n" +

				"select conditions from bedc.study_sample_processing where id= old.processing into oldprocessing;  \r\n"
				+ "select conditions from bedc.study_sample_processing where id= new.processing into newprocessing;  \r\n"
				+ "IF nullif(old.processing,null) <> NULLIF(new.processing,null) or new.processing is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('processing',oldprocessing,newprocessing,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_processing_details','audit_update','processing');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" + "	END IF;\r\n" + "	END IF;\r\n" + "	END;" + "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_sample_processing_details_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,aliquot_volume,created_by,created_on,update_reason,updated_by,updated_on,condition,processing  ON bedc.study_sample_processing_details\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_sample_processing_details_fun();");

	}

	private void generateStudySampleStorage(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_sample_storage_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   newstudy character varying;\r\n" + "   oldstudy character varying;\r\n"
				+ "   newtreatmentid character varying;\r\n" + "   oldtreatmentid character varying;\r\n" + "BEGIN\r\n"
				+ " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n" + " incCount := incCount +1;\r\n"
				+ " select count(*) from bedc.audit_log into actid;\r\n" + " actid := actid +1;\r\n"
				+ "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.created_by,'CREATE',new.id,'study_sample_centrifugation',null,null,null,'study_sample_centrifugation_creation',new.createdon) RETURNING id INTO incCount; \r\n"
				+

				"IF (new.storage_different IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.storage_different','',new.storage_different,'Static',null,null,null,' ',new.created_by,new.createdon,incCount,'study_sample_storage','audit_insert','storage_different');\r\n"
				+ "END IF;\r\n" +

				"select studytitle from bedc.study_master where studyid= new.study into newstudy;  \r\n"
				+ "IF (new.study IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('study','',newstudy,'Static',null,null,null,' ',new.created_by,new.createdon,incCount,'study_sample_storage','audit_insert','study');\r\n"
				+ "END IF;\r\n" +

				"select treatmentname from bedc.study_treatments where treatmentinfoid= new.treatment into newtreatmentid;  \r\n"
				+ "IF (new.treatment IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatment','',newtreatmentid,'Static',null,null,null,' ',new.created_by,new.createdon,incCount,'study_sample_storage','audit_insert','treatment');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" +

				"ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.id,'study_sample_storage_updation',new.updated_on)\r\n"
				+ "RETURNING id INTO incCount;\r\n" +

				"IF nullif(old.storage_different,null) <> NULLIF(new.storage_different,null) or new.storage_different is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.storage_different','',new.storage_different,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_storage','audit_update','storage_different');\r\n"
				+ "END IF;\r\n" +

				"select studytitle from bedc.study_master where studyid= old.study into oldstudy;  \r\n"
				+ "select studytitle from bedc.study_master where studyid= new.study into newstudy;  \r\n"
				+ "IF nullif(old.study,null) <> NULLIF(new.study,null) or new.study is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.study',oldstudy,newstudy,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_storage','audit_update','study');\r\n"
				+ "END IF;\r\n" +

				"select treatmentname from bedc.study_treatments where treatmentinfoid= old.treatment into oldtreatmentid;  \r\n"
				+ "select treatmentname from bedc.study_treatments where treatmentinfoid= new.treatment into newtreatmentid;  \r\n"
				+ "IF nullif(old.treatment,null) <> NULLIF(new.treatment,null) or new.treatment is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.treatment',oldtreatmentid,newtreatmentid,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_storage','audit_update','treatment');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" + "	END IF;\r\n" + "	END IF;\r\n" + "	END;" + "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_sample_storage_details_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,created_by,createdon,storage_different,update_reason,updated_by,updated_on,study,treatment  ON bedc.study_sample_storage\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_sample_storage_fun();");

	}

	private void generateStudySampleStorageDetails(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_sample_storage_details_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "   newstoragecondition character varying;\r\n" + "   oldstoragecondition character varying;\r\n"
				+ "   newstorageid character varying;\r\n" + "   oldstorageid character varying;\r\n"
				+ "   newtimepointcondition character varying;\r\n" + "   oldtimepointcondition character varying;\r\n"
				+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n" + " select count(*) from bedc.audit_log into actid;\r\n"
				+ " actid := actid +1;\r\n" + "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.created_by,'CREATE',new.id,'study_sample_storage_details',null,null,null,'study_sample_storage_details_creation',new.created_on) RETURNING id INTO incCount; \r\n"
				+

				"IF (new.allowed_time IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.allowed_time','',new.allowed_time,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_storage_details','audit_insert','allowed_time');\r\n"
				+ "END IF;\r\n" +

				"IF (new.temparature IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.temparature','',new.temparature,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_storage_details','audit_insert','temparature');\r\n"
				+ "END IF;\r\n" +

				"select name from bedc.condition_parameter where id= new.storage_condition into newstoragecondition;  \r\n"
				+ "IF (new.storage_condition IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('storage_condition','',new.storage_condition,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_storage_details','audit_insert','storage_condition');\r\n"
				+ "END IF;\r\n" +

				"select created_by from bedc.study_sample_storage where id= new.storage_id into newstorageid;  \r\n"
				+ "IF (new.storage_id IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('storage_id','',new.storage_id,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_storage_details','audit_insert','storage_id');\r\n"
				+ "END IF;\r\n" +

				"select name from bedc.condition_parameter where id= new.timepoint_condition into newtimepointcondition;  \r\n"
				+ "IF (new.timepoint_condition IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('timepoint_condition','',new.timepoint_condition,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_storage_details','audit_insert','timepoint_condition');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" +

				"ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.updated_by,'UPDATE',new.id,'study_sample_storage_details',null,null,null,'study_sample_storage_details_updation',new.updated_on)\r\n"
				+ "RETURNING id INTO incCount;\r\n" +

				"IF nullif(old.allowed_time,0) <> NULLIF(new.allowed_time,0) or new.allowed_time is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.allowed_time','',new.allowed_time,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_storage_details','audit_insert','allowed_time');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.temparature,0) <> NULLIF(new.temparature,0) or new.temparature is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.temparature','',new.temparature,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_storage_details','audit_insert','temparature');\r\n"
				+ "END IF;\r\n" +

				"select name from bedc.condition_parameter where id= new.storage_condition into newstoragecondition;  \r\n"
				+ "select name from bedc.condition_parameter where id= new.storage_condition into oldstoragecondition;  \r\n"
				+ "IF nullif(old.storage_condition,null) <> NULLIF(new.storage_condition,null) or new.storage_condition is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('storage_condition','',new.storage_condition,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_storage_details','audit_insert','storage_condition');\r\n"
				+ "END IF;\r\n" +

				"select created_by from bedc.study_sample_storage where id= new.storage_id into newstorageid;  \r\n"
				+ "select created_by from bedc.study_sample_storage where id= new.storage_id into oldstorageid;  \r\n"
				+ "IF nullif(old.storage_id,null) <> NULLIF(new.storage_id,null) or new.storage_id is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('storage_id','',new.storage_id,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_storage_details','audit_insert','storage_id');\r\n"
				+ "END IF;\r\n" +

				"select name from bedc.condition_parameter where id= new.timepoint_condition into newtimepointcondition;  \r\n"
				+ "select name from bedc.condition_parameter where id= new.timepoint_condition into oldtimepointcondition;  \r\n"
				+ "IF nullif(old.timepoint_condition,null) <> NULLIF(new.timepoint_condition,null) or new.timepoint_condition is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('timepoint_condition','',new.timepoint_condition,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_storage_details','audit_insert','timepoint_condition');\r\n"
				+ "END IF;\r\n" +

				"RETURN new;\r\n" + "	END IF;\r\n" + "	END IF;\r\n" + "	END;\r\n" + "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_sample_storage_details_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,allowedtime,created_by,createdon,temperature,update_reason,updated_by,updated_on,storage_condition,storage_id,timepoint_condition  ON bedc.study_sample_storage_details\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_sample_storage_details_fun();");
	}

	private void generateStudySampleTimepoints(Connection con, Statement stmt) throws Exception {
		stmt.execute("CREATE OR REPLACE FUNCTION bedc.study_sample_time_points_fun() RETURNS TRIGGER AS $$\r\n" +

				"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
				+ "    timepointtypeName   character varying;\r\n" + "	oldtimepointtypeName  character varying;\r\n"
				+ "	treatmentinfoidName  character varying;\r\n"
				+ "	oldtreatmentinfoidName  character varying;	\r\n"
				+ "	windowperiodtypeName  character varying;\r\n" + "	oldwindowperiodtypeName  character varying;\r\n"
				+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
				+ " incCount := incCount +1;\r\n" + " select count(*) from bedc.audit_log into actid;\r\n"
				+ " actid := actid +1;\r\n" + "IF (old.sampletimepointid IS NULL) THEN\r\n" + // INSERT TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.created_by,'CREATE',new.sampletimepointid,'study_sample_time_points',new.studyid,null,null,'study_sample_time_points_creation',new.created_on)RETURNING id INTO incCount; \r\n"
				+

				"IF (new.noofvacutainer IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.noofvacutainer','',new.noofvacutainer,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_insert','noofvacutainer');\r\n"
				+ "END IF;\r\n" +

				"IF (new.vial_count IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.vial_count','',new.vial_count,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_insert','vial_count');\r\n"
				+ "END IF;\r\n" +

				"IF (new.sign IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.sign','',new.sign,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_insert','sign');\r\n"
				+ "END IF;\r\n" +

				"IF (new.timepoint IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepoints','',new.timepoint,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_insert','timepoint');\r\n"
				+ "END IF;\r\n" +

				"IF (new.timepointno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointno','',new.timepointno,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_insert','timepointno');\r\n"
				+ "END IF;\r\n" +

				"IF (new.vacutainerno IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.vacutainerNo','',new.windowperiod,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_insert','vacutainerno');\r\n"
				+ "END IF;\r\n" +

				"IF (new.windowperiod IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiod','',new.windowperiod,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_insert','windowperiod');\r\n"
				+ "END IF;\r\n" +

				"IF (new.windowperiodsign IS NOT NULL) THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiodsign','',new.windowperiodsign,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_insert','windowperiodsign');\r\n"
				+ "END IF;\r\n" +

				"IF (new.timepointtype IS NOT NULL) THEN\r\n"
				+ " SELECT code FROM bedc.form_static_data where formstaticdataid = new.timepointtype into timepointtypeName;\r\n"
				+

				"INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('timepointtype','',timepointtypeName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_insert','timepointtype');\r\n"
				+ "END IF;\r\n" +

				"IF (new.treatmentinfoid IS NOT NULL) THEN\r\n"
				+ " SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentinfoid into treatmentinfoidName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('treatmentinfoid','',treatmentinfoidName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_insert','treatmentinfoid');\r\n"
				+ "END IF;\r\n" +

				"IF (new.windowperiodtype IS NOT NULL) THEN\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.windowperiodtype into windowperiodtypeName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('windowperiodtype','',windowperiodtypeName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_insert','windowperiodtype');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" +

				"ELSE IF (new.sampletimepointid=old.sampletimepointid) THEN\r\n" + // UPDATE TRIGGER
				"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
				+ "VALUES (new.created_by,'UPDATE',new.sampletimepointid,'study_sample_time_points',new.studyid,null,null,'study_sample_time_points_updation',new.created_on)RETURNING id INTO incCount;\r\n"
				+

				"IF nullif(old.noofvacutainer,0) <> NULLIF(new.noofvacutainer,0) or new.noofvacutainer is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.noofvacutainer',old.noofvacutainer,new.noofvacutainer,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_update','noofvacutainer');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.vial_count,0) <> NULLIF(new.vial_count,0) or new.vial_count is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.vial_count',old.vial_count,new.vial_count,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_update','vial_count');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.sign,'') <> NULLIF(new.sign,'') or new.sign is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.sign',old.sign,new.sign,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_update','sign');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.timepoint,'') <> NULLIF(new.timepoint,'') or new.timepoint is null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepoints',old.timepoint,new.timepoint,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_update','timepoint');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.timepointno,0) <> NULLIF(new.timepointno,0) or new.timepointno is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.timepointno',old.timepointno,new.timepointno,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_update','timepointno');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.vacutainerno,0) <> NULLIF(new.vacutainerno,0) or new.vacutainerno is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.vacutainerNo',old.vacutainerno,new.vacutainerno,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_update','vacutainerno');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.windowperiod,0) <> NULLIF(new.windowperiod,0) or new.windowperiod is not null THEN\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('label.windowperiod',old.windowperiod,new.windowperiodsign,'Static',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_update','windowperiod');\r\n"
				+ "END IF;\r\n" +

				"IF nullif(old.timepointtype,null) <> NULLIF(new.timepointtype,null) or new.timepointtype is null THEN\r\n"
				+ " SELECT code FROM bedc.form_static_data where formstaticdataid = old.timepointtype into oldtimepointtypeName;\r\n"
				+ " SELECT code FROM bedc.form_static_data where formstaticdataid = new.timepointtype into timepointtypeName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('timepointtype',oldtimepointtypeName,timepointtypeName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_update','timepointtype');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.treatmentinfoid,null) <> NULLIF(new.treatmentinfoid,null) or new.treatmentinfoid is null THEN\r\n"
				+ " SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = old.treatmentinfoid into oldtreatmentinfoidName;\r\n"
				+ " SELECT treatmentname FROM bedc.study_treatments where treatmentinfoid = new.treatmentinfoid into treatmentinfoidName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ " VALUES ('treatmentinfoid',oldtreatmentinfoidName,treatmentinfoidName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_update','treatmentinfoid');\r\n"
				+ "END IF;\r\n"
				+ "IF nullif(old.windowperiodtype,null) <> NULLIF(new.windowperiodtype,null) or new.windowperiodtype is null THEN\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = old.windowperiodtype into oldwindowperiodtypeName;\r\n"
				+ " SELECT fieldname FROM bedc.form_static_data where formstaticdataid = new.windowperiodtype into windowperiodtypeName;\r\n"
				+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
				+ "VALUES ('windowperiodtype',oldwindowperiodtypeName,windowperiodtypeName,'Dynamic',new.studyid,null,null,' ',null,new.created_on,incCount,'study_sample_time_points','audit_update','windowperiodtype');\r\n"
				+ "END IF;\r\n" + "RETURN new;\r\n" + "END IF;\r\n" + "END IF;\r\n" + "END;\r\n"
				+ "$$ LANGUAGE 'plpgsql';");
		stmt.execute("CREATE TRIGGER study_sample_time_points_trgr"
				+ "   AFTER INSERT OR UPDATE  OF sampletimepointid,created_by,created_on,noofvacutainer,vial_count,sign,timepoint,timepointno,update_reason,updated_by,updated_on,vacutainerno,windowperiod,windowperiodsign,studyid,timepointtype,treatmentinfoid,windowperiodtype  ON bedc.study_sample_time_points\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_sample_time_points_fun();");

	}

	private void generateStudySampleCentrifugationDetails(Connection con, Statement stmt) throws Exception {
		stmt.execute(
				"CREATE OR REPLACE FUNCTION bedc.study_sample_centrifugation_details_fun() RETURNS TRIGGER AS $$\r\n" +

						"declare \r\n" + "   incCount integer;\r\n" + "   actid integer;\r\n"
						+ "   newcentrifugation character varying;\r\n" + "   oldcentrifugation character varying;\r\n"
						+ "BEGIN\r\n" + " SELECT COUNT(*) FROM bedc.activity_log into incCount;\r\n"
						+ " incCount := incCount +1;\r\n" + " select count(*) from bedc.audit_log into actid;\r\n"
						+ " actid := actid +1;\r\n" + "IF (old.id IS NULL) THEN\r\n" + // INSERT TRIGGER
						"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ "VALUES (new.created_by,'CREATE',new.id,'study_sample_centrifugation_details',null,null,null,'study_sample_centrifugation_details_creation',new.created_on) RETURNING id INTO incCount; \r\n"
						+

						"IF (new.applicableto IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.applicableto','',new.applicableto,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_centrifugation_details','audit_insert','applicableto');\r\n"
						+ "END IF;\r\n" +

						"IF (new.centrifugation_allowedtime IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.centrifugation_allowedtime','',new.centrifugation_allowedtime,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_centrifugation_details','audit_insert','centrifugation_allowedtime');\r\n"
						+ "END IF;\r\n" + "IF (new.centrifugation_processtime IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.centrifugation_processtime','',new.centrifugation_processtime,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_centrifugation_details','audit_insert','centrifugation_processtime');\r\n"
						+ "END IF;\r\n" + "IF (new.centrifugationspeed IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.centrifugationspeed','',new.centrifugationspeed,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_centrifugation_details','audit_insert','centrifugationspeed');\r\n"
						+ "END IF;\r\n" + "IF (new.centrifugation_temparature IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.centrifugation_temparature','',new.centrifugation_temparature,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_centrifugation_details','audit_insert','centrifugation_temparature');\r\n"
						+ "END IF;\r\n" + "IF (new.conditions IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.conditions','',new.conditions,'Static',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_centrifugation_details','audit_insert','conditions');\r\n"
						+ "END IF;\r\n"
						+ "select created_by from bedc.study_sample_centrifugation where id= new.centrifugation into newcentrifugation;  \r\n"
						+ "IF (new.centrifugation IS NOT NULL) THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('centrifugation','',newcentrifugation,'Dynamic',null,null,null,' ',new.created_by,new.created_on,incCount,'study_sample_centrifugation_details','audit_insert','centrifugation');\r\n"
						+ "END IF;\r\n" + "RETURN new;\r\n" + "ELSE IF (new.id=old.id) THEN\r\n" + // UPDATE TRIGGER
						"INSERT INTO bedc.activity_log (activity_done_by,activity_name,table_id,tablename,study_id,subject_id,volunteer_id,actvity_description,activity_done_on)\r\n"
						+ "VALUES (new.updated_by,'UPDATE',new.id,'study_sample_centrifugation_details',null,null,null,'study_sample_centrifugation_details_updation',new.updated_on)\r\n"
						+ "RETURNING id INTO incCount;\r\n"
						+ "IF nullif(old.applicableto,'') <> NULLIF(new.applicableto,'') or new.applicableto is not null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.applicableto',old.applicableto,new.applicableto,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_centrifugation_details','audit_update','applicableto');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.centrifugation_allowedtime,0) <> NULLIF(new.centrifugation_allowedtime,0) or new.centrifugation_allowedtime is not null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.centrifugation_allowedtime',old.centrifugation_allowedtime,new.centrifugation_allowedtime,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_centrifugation_details','audit_update','centrifugation_allowedtime');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.centrifugation_processtime,0) <> NULLIF(new.centrifugation_processtime,0) or new.conditions is not null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.centrifugation_processtime',old.centrifugation_processtime,new.centrifugation_processtime,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_centrifugation_details','audit_update','centrifugation_processtime');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.centrifugationspeed,0) <> NULLIF(new.centrifugationspeed,0) or new.centrifugationspeed is not null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.centrifugationspeed',old.centrifugationspeed,new.centrifugationspeed,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_centrifugation_details','audit_update','centrifugationspeed');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.centrifugation_temparature,0) <> NULLIF(new.centrifugation_temparature,0) or new.centrifugation_temparature is not null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.centrifugation_temparature',old.centrifugation_temparature,new.centrifugation_temparature,'Static',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_centrifugation_details','audit_update','centrifugation_temparature');\r\n"
						+ "END IF;\r\n"
						+ "IF nullif(old.conditions,'') <> NULLIF(new.conditions,'') or new.conditions is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('label.conditions',oldconditions,newconditions,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_centrifugation_details','audit_update','conditions');\r\n"
						+ "END IF;\r\n"
						+ "select created_by from bedc.study_sample_centrifugation where id= old.centrifugation into oldcentrifugation;  \r\n"
						+ "select created_by from bedc.study_sample_centrifugation where id= new.centrifugation into newcentrifugation;  \r\n"
						+ "IF nullif(old.centrifugation,null) <> NULLIF(new.centrifugation,null) or new.centrifugation is null THEN\r\n"
						+ "INSERT INTO bedc.audit_log (field_name,old_value,new_value,field_type,study_id,volunteer_id,subject_id,comments,created_by,created_on,activity_log_id,table_name,action,databasefieldname)\r\n"
						+ "VALUES ('centrifugation',oldcentrifugation,newcentrifugation,'Dynamic',null,null,null,' ',new.updated_by,new.updated_on,incCount,'study_sample_centrifugation_details','audit_update','centrifugation');\r\n"
						+ "END IF;\r\n" + "RETURN new;\r\n" + "	END IF;\r\n" + "	END IF;\r\n" + "	END;"
						+ "$$ LANGUAGE 'plpgsql';");

		stmt.execute("CREATE TRIGGER study_sample_centrifugation_details_trgr"
				+ "   AFTER INSERT OR UPDATE  OF id,applicableto,centrifugation_allowedtime,centrifugation_processtime,centrifugationspeed,centrifugation_temparature,conditions,created_by,created_on,update_reason,updated_by,updated_on,centrifugation  ON bedc.study_sample_centrifugation_details\r\n"
				+ "    FOR EACH ROW\r\n" + "    EXECUTE FUNCTION bedc.study_sample_centrifugation_details_fun();");
	}
}