package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.UserDao;
import com.covideinfo.enums.ParameterCodes;
import com.covideinfo.enums.StaticData;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.enums.StudyDesign;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.ActivityLog;
import com.covideinfo.model.ApplicationConfiguration;
import com.covideinfo.model.ApplicationMenus;
import com.covideinfo.model.ApplictionSideMenus;
import com.covideinfo.model.AuditLog;
import com.covideinfo.model.ControlType;
import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.DosageForm;
import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InstrumentModel;
import com.covideinfo.model.InstrumentType;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.RolesWiseModules;
import com.covideinfo.model.SponsorCode;
import com.covideinfo.model.StaticActivityDetails;
import com.covideinfo.model.StaticActivityValueDetails;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPhases;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.UserActivity;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.UserWiseStudiesAsignMaster;
import com.covideinfo.model.VitalTest;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Long, UserMaster> implements UserDao {

	static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Override
	public UserMaster findById(Long id) {
		UserMaster user = getByKey(id);
		return user;
	}

	@Override
	public UserMaster findByusername(String username) {
		logger.info("Username : {}" + username);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.ilike("username",username ,MatchMode.EXACT));
		UserMaster user = (UserMaster) crit.uniqueResult();
		return user;
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<UserMaster> findAllUsers() {
		/*
		 * Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
		 * criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid
		 * duplicates.
		 */ List<UserMaster> users = createEntityCriteria().add(Restrictions.ne("oldUser", "yes"))
				.addOrder(Order.asc("createdOn")).list();
		return users;
	}

	@Override
	public void updateStudy(UserMaster user) {
		// TODO Auto-generated method stub
		update(user);
	}

	@Override
	public void updateLoginCredentials(UserMaster checkLoginUser) {
		// TODO Auto-generated method stub
		getSession().update(checkLoginUser);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMaster> findAllActiveUsers() {
		// TODO Auto-generated method stub
		return createEntityCriteria().add(Restrictions.eq("accountNotLock", true))
				.add(Restrictions.eq("accountNotDisable", true)).add(Restrictions.ge("accountexprie", new Date()))
				.list();
	}

	@Override
	public UserMaster getUserdetails(String username) {
		// TODO Auto-generated method stub
		return (UserMaster) getSession().createCriteria(UserMaster.class).add(Restrictions.eq("username", username))
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMaster> findUserSerchList(String userid) {
		// TODO Auto-generated method stub
		return createEntityCriteria().add(Restrictions.eq("username", userid)).list();
	}

	@Override
	public UserMaster findUserDetails(String userid) {
		// TODO Auto-generated method stub
		return (UserMaster) getSession().createCriteria(UserMaster.class).add(Restrictions.eq("username", userid))
				.uniqueResult();
	}

	@Override
	public boolean passResetSave(UserMaster pojo) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			getSession().update(pojo);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<UserMaster> findUserSerchList2(String userid) {
		// TODO Auto-generated method stub
		String[] roles = { "User" };
		long sid = Long.parseLong("3");
		boolean pass = true;
		return createEntityCriteria().add(Restrictions.eq("username", userid)).add(Restrictions.ne("role.id", sid))
				.list();
	}

	@Override
	public boolean passTrsResetSave(UserMaster pojo) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			getSession().update(pojo);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public UserMaster findByUsername(String username) {
		// TODO Auto-generated method stub
		return (UserMaster) getSession().createCriteria(UserMaster.class).add(Restrictions.eq("username", username))
				.uniqueResult();
	}

	@Override
	public boolean accountDisableSave(UserMaster pojo) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			getSession().update(pojo);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMaster> findUserAllList() {
		// TODO Auto-generated method stub
		return createEntityCriteria().list();
	}

	@Override
	public boolean accountEnableSave(UserMaster pojo) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			getSession().update(pojo);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean generateLoginDetails(UserMaster loginUsers) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			getSession().save(loginUsers);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public void checkAndCreateConfiguredTables(StatusMaster activeStatus) {
		fromStaticDataTable(activeStatus);
		statusMasterTable();
		roleMasterTable();
//		studyMasterTable();
		userMasterTable();
		internationalLangTable();
		controlTypeTable();
		studyPhace();
		globalValues();
		applicationMenus();
		applicationSideMenus();
		applicationConfiguration();
		dyanamicActivities();
		deviationMessages();
		createStaticForms();
		instrumentType();
		instrumentModel();
	}


	private void instrumentModel() {
		Long count=null;
		try {
			count=(Long) getSession().createCriteria(InstrumentModel.class)
					.setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == null || count == 0) {
			InstrumentModel insModel = new InstrumentModel("Model One", "Superadmin", new Date());
			getSession().save(insModel);
		}
	}

	private void instrumentType() {
		Long count=null;
		try {
			count=(Long) getSession().createCriteria(InstrumentType.class)
					.setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == null || count == 0) {
			List<InstrumentType> instList = new ArrayList<>();
			instList.add(new InstrumentType("Centrifuge", "CENTRIFUGE", "superadmin", new Date()));
			instList.add(new InstrumentType("DEEPFREEZER", "DEEPFREEZER", "superadmin", new Date()));
			for(InstrumentType ins : instList) {
				getSession().save(ins);
			}
		}
		
	}

	private void createStaticForms() {
		Long count=null;
		try {
			count=(Long) getSession().createCriteria(StaticActivityDetails.class)
					.setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == null || count == 0) {
			List<StaticActivityDetails> sadList = new ArrayList<>();
			sadList.add(new StaticActivityDetails("Subject non-cooperative and undiscriplined",  "superadmin", new Date(), "Withdrawal"));
			sadList.add(new StaticActivityDetails("Subject entered the study in violation of the protocol requirements.",  "superadmin", new Date(), "Withdrawal"));
			sadList.add(new StaticActivityDetails("Emesis occurred at or before 2 times the median T max for immmediate release products.","superadmin", new Date(), "Withdrawal"));
			sadList.add(new StaticActivityDetails("Emesis occurred at any time during the labelled dosing interval for modified release products.",  "superadmin", new Date(), "Withdrawal"));
			sadList.add(new StaticActivityDetails("Subject suffered from any other clinically sifnificiant adverse event.",  "superadmin", new Date(), "Withdrawal"));
			sadList.add(new StaticActivityDetails("Subject received/requires any concomitant medication that interferes with the pharmacokinetics of the study drug.", "superadmin", new Date(), "Withdrawal"));
			sadList.add(new StaticActivityDetails("Subject underwent/requires hospitalization during the study.",  "superadmin", new Date(), "Withdrawal"));
			sadList.add(new StaticActivityDetails("Subject not meeting the protocol requirement","superadmin", new Date(), "Withdrawal"));
			sadList.add(new StaticActivityDetails("Investigator felt it is not in the intrest of the subject to continue in the study.",  "superadmin", new Date(), "Withdrawal"));
			sadList.add(new StaticActivityDetails("Subject tested positive for Alcohol consumption test.", "superadmin", new Date(), "Withdrawal"));
			sadList.add(new StaticActivityDetails("Subject tested positive for any one of the urine drugs of abuse.", "superadmin", new Date(), "Withdrawal"));
			sadList.add(new StaticActivityDetails("Female Subject tested positive in pregnancy test","superadmin", new Date(), "Withdrawal"));
			sadList.add(new StaticActivityDetails("Ohters (Please elaborate in the comments column)", "superadmin", new Date(), "Withdrawal"));
			if(sadList.size() > 0) {
				for(StaticActivityDetails sad : sadList) {
					getSession().save(sad);
				}
			}
			 
			Long sadNo= (Long) getSession().save(new StaticActivityDetails("Subject withdrew consent on his own accord or his intention his/her dropout form the study.","superadmin", new Date(), "DroupOut"));
			if(sadNo > 0) {
				StaticActivityDetails sadPojo = (StaticActivityDetails) getSession().createCriteria(StaticActivityDetails.class)
						                           .add(Restrictions.eq("id", sadNo)).uniqueResult();
				getSession().save(new StaticActivityValueDetails("In person.", sadPojo, "superadmin", new Date()));
				getSession().save(new StaticActivityValueDetails("By telephone.", sadPojo, "superadmin", new Date()));
				
			}
			getSession().save(new StaticActivityDetails("Subject failed to report to the facility for admission (for periods other than period 01).", "superadmin", new Date(), "DroupOut"));
			getSession().save(new StaticActivityDetails("Ohters (Please elaborate in the comments column)", "superadmin", new Date(), "DroupOut"));
			
			
		}
	}

	private void deviationMessages() {
		/*Long count=null;
		try {
			count=(Long) getSession().createCriteria(DeviationMessage.class)
					.setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
//		if (count == null || count == 0) {
			saveDeviationMessages(true, "FEDCANDTPD", "DOSING", "Fed Criteria And Time Point Deviation", "FEDCANDTPD");
			saveDeviationMessages(true, "FASTCDANDTPD", "DOSING", "Fasting Criteria And Time Point Deviation", "FASTCDANDTPD");
			saveDeviationMessages(true, "TPD", "ALL", "Time Point Time Deviation", "TPD");
			saveDeviationMessages(true, "FASTANDFEDCD", "DOSING", "Fast Criteria And Fed Criteria Failed", "FASTANDFEDCD");
			saveDeviationMessages(true, "FASTANDFEDCDANDTD", "DOSING", "Fast Criteria, Fed Criteria Failed And Time Point Deviation", "FASTANDFEDCDANDTD");
			saveDeviationMessages(true, "FEDCD", "DOSING", "Fed Criteria Filed", "FEDCD");
			saveDeviationMessages(true, "TPS", "ALL", "Time Point Skipping Deviation", "TPS");
			saveDeviationMessages(true, "FASTCD", "DOSING", "Fasting Criteria Failed", "FASTCD");
			saveDeviationMessages(true, "TPSKIP", "ALL", "Time Points Skip", "TPSKIP");
			saveDeviationMessages(true, "TPSKIPANDTP", "ALL", "Time Points Skip And Time Deviation", "TPSKIPANDTP");
			saveDeviationMessages(true, "NILL", "ALL", "NILL", "NILL");
			saveDeviationMessages(true, "DV", "SAMPLES", "Difficulty with Veins", "DV");
			saveDeviationMessages(true, "CB", "SAMPLES", "Cannula Blocked", "CB");
			saveDeviationMessages(true, "SRL", "SAMPLES", "Subject Reported Late", "SRL");
			saveDeviationMessages(true, "AOR", "SAMPLES", "Any Other Reason", "AOR");
			saveDeviationMessages(true, "CR", "SAMPLES", "Cannula Removed", "CR");
			saveDeviationMessages(true, "RCD", "SAMPLES", "Re-cannulation Done", "RCD");
			saveDeviationMessages(true, "SNR", "SAMPLES", "Subject Not Reported", "SNR");
			saveDeviationMessages(true, "TFP", "SAMPLES", "Taken by fresh prick", "TFP");
			saveDeviationMessages(true, "MLOD", "OTHER", "Meals Left Over Deviation", "MLOD");
//		}
		
	}

	private void saveDeviationMessages(boolean status, String code, String type, String message, String developerCode) {
		try {
			DeviationMessage dm = new DeviationMessage();
			dm.setActiveStatus(status);
			dm.setDeveloperCode(developerCode);
			dm.setCode(code);
			dm.setCollectionType(type);
			dm.setMessage(message);
			DeviationMessage dmPojo = (DeviationMessage) getSession().createCriteria(DeviationMessage.class)
					                      .add(Restrictions.eq("code", dm.getCode()))
					                      .add(Restrictions.eq("collectionType", dm.getCollectionType())).uniqueResult();
			if(dmPojo == null) {
				getSession().save(dm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unchecked")
	private void dyanamicActivities() {
		Long count=null;
		try {
			count=(Long) getSession().createCriteria(GlobalActivity.class)
					.setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == null || count == 0) {
			List<InternationalizaionLanguages> inlagList = getSession().createCriteria(InternationalizaionLanguages.class).list();
			List<RoleMaster> rmList = getSession().createCriteria(RoleMaster.class).list();
			String roleStr = "";
			if(rmList != null && rmList.size() >0) {
				for(RoleMaster rm : rmList) {
					if(roleStr.equals(""))
						roleStr = rm.getId()+"";
					else roleStr = roleStr +","+rm.getId();
				}
			}
			saveGlobalActivityData("Reporting", "Informes", StudyActivities.Reporting.toString(), "/reporting/getReportingForm", 1, roleStr, inlagList, false, false, true);
			saveGlobalActivityData("Subject Withdrawal/Droupout Record", "Registro de retiro/abandono de sujetos", StudyActivities.StudyWithDraw.toString(), "/disocontinue/studyWithDrawPage", 69, roleStr, inlagList, false, false, true);
			saveGlobalActivityData("Dosing Record", "Registro de dosificación", StudyActivities.DosingCollection.toString() , "/study/clinical/dosingCollection", 71, roleStr, inlagList, true, true, false);
			saveGlobalActivityData("Meal Record", "Registro de comidas", StudyActivities.MealsCollection.toString() , "cpuActivity/MEALCOLLECTION", 70, roleStr, inlagList, true, true, false);
			saveGlobalActivityData("Blood Sample Collection Record", "Registro de recolección de muestras de sangre", StudyActivities.SampleCollection.toString() , "cpuActivity/BLOODSAMPLECOLLECTION", 72, roleStr, inlagList, true, true, false);
			saveGlobalActivityData("Vital Collection", "Colección Vital", StudyActivities.StudyExecutionVitals.toString(), "cpuActivity/VITALCOLLECTION", 73, roleStr, inlagList, true, true, false);
			saveGlobalActivityData("Centrifuge", "Centrï¿½fugo", StudyActivities.Centrifuge.toString(), "cpuActivity/CENTRIFUGATION", 74, roleStr, inlagList, false, false, false);
			saveGlobalActivityData("Sample Separation", "Separación de muestras", StudyActivities.SampleSeparation.toString(), "cpuActivity/SAMPLESEPARATION", 75, roleStr, inlagList, false, false, false);
			saveGlobalActivityData("Re-cannulation", "Re-canulación", StudyActivities.Recannulation.toString(), "/reCannula/getRecannulationPage", 76, roleStr, inlagList, false, false, false);
			
			/*saveGlobalActivityData("Sample Storage", "Almacenamiento de muestras", StudyActivities.SampleStorage.toString(), "cpuActivity/SAMPLESTORAGE", 76, roleStr, inlagList, false, false, false);
			saveGlobalActivityData("Subject WiseSamples Containter", "Asunto Contenedor WiseSamples", StudyActivities.SubjectWiseSamplesContainter.toString(), "cpuActivity/SUBJECTWISESAMPLESCONTAINTER", 77, roleStr, inlagList, false, false, false);
			saveGlobalActivityData("Shipment", "Envï¿½o", StudyActivities.Shipment.toString(), "cpuActivity/SHIPMENT", 78, roleStr, inlagList, false, false, false);
			saveGlobalActivityData("Vial Rack", "Gradilla para viales", StudyActivities.VialRack.toString(), "cpuActivity/VIALRACK", 79, roleStr, inlagList, false, false, false);*/
		
			ControlType controlType = (ControlType)getSession().createCriteria(ControlType.class).add(Restrictions.eq("code", "RB")).uniqueResult();
			StudyPhases studyPhases = (StudyPhases)getSession().createCriteria(StudyPhases.class).add(Restrictions.eq("code", "SCI")).uniqueResult();
			List<String> yesNoValues = new ArrayList<>();
			yesNoValues.add("Yes");
			yesNoValues.add("No");
			
			List<String> eligibleIneligibleValues = new ArrayList<>();
			eligibleIneligibleValues.add("Fit for participation in the study.");
			eligibleIneligibleValues.add("Unfit for participation in the study.");
			
			saveGlobalParamtersData("Is eligible for next process", "Is eligible for next process", "Is eligible for next process", 9999999, inlagList, studyPhases.getId()+"", controlType, false, ParameterCodes.IENP.toString(), yesNoValues, null, false);
			saveGlobalParamtersData("Based on all the above criteria, the volunteer is certified to be", "Based on all the above criteria, the volunteer is certified to be", "Based on all the above criteria, the volunteer is certified to be", 99999999, inlagList, studyPhases.getId()+"", controlType, false, ParameterCodes.STUDYELIGIBLITY.toString(), eligibleIneligibleValues, null, false);
			//Subject Allotment Activity and parameters saving part
			GlobalActivity ga = saveGlobalActivityData("Subject Baggage Check, Body Search and Admission Record", "Sujeto Control de equipaje, registro corporal y registro de admisión", "", "", 8, roleStr, inlagList, true, true, true);
			if(ga != null) {
				ControlType etControlType = (ControlType)getSession().createCriteria(ControlType.class).add(Restrictions.eq("code", "ET")).uniqueResult();
				ControlType tbControlType = (ControlType)getSession().createCriteria(ControlType.class).add(Restrictions.eq("code", "TB")).uniqueResult();
				ControlType taControlType = (ControlType)getSession().createCriteria(ControlType.class).add(Restrictions.eq("code", "TA")).uniqueResult();
				saveGlobalParamtersData("Admission Data & Time", "Admission Data & Time", "Fecha y hora de admisión", 1, inlagList, studyPhases.getId()+"", etControlType, false, "", null, ga, true);
				saveGlobalParamtersData("Baggage check & body search done","Baggage check & body search done", "Comprobación de equipaje y registro corporal realizados", 2, inlagList, studyPhases.getId()+"", controlType, false, "", yesNoValues, ga, true);
				saveGlobalParamtersData("ID card given to Subject","ID card given to Subject", "DNI entregado al Sujeto", 3, inlagList, studyPhases.getId()+"", controlType, false, "", yesNoValues, ga, true);
				saveGlobalParamtersData("Utility Kit Issued", "Utility Kit Issued", "Kit de utilidades emitido", 4, inlagList, studyPhases.getId()+"", controlType, false, "", yesNoValues, ga, true);
				saveGlobalParamtersData("Locker No.", "Locker No.", "casillero no.", 6, inlagList, studyPhases.getId()+"", tbControlType, false, "", null, ga, true);
				saveGlobalParamtersData("subjectAllotComments", "Comments :", "Comentarios :", 12, inlagList, studyPhases.getId()+"", taControlType, false, "", null, ga, true);
			}
		}
	}

	private GlobalActivity saveGlobalActivityData(String name, String lsgaName, String actCode, String getUrl, int orderNo, String rolseStr, List<InternationalizaionLanguages> inlagList, boolean heading, boolean showPdf, boolean subInput)
	 {
		GlobalActivity ga = null;
		try {
			ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
		             .add(Restrictions.eq("name", name)).uniqueResult();
			if(ga == null) {
				ga = new GlobalActivity();
				ga.setActivityCode(actCode);
				ga.setAllowMultiple("No");
				ga.setCreatedBy("superadmin");
				ga.setCreatedOn(new Date());
				ga.setGetUrl(getUrl);
				ga.setGroupId(null);
				ga.setHeadding(heading);
				ga.setName(name);
				ga.setOrderNo(orderNo);
				ga.setRejectUrl("0");
				ga.setRoleIds(rolseStr);
				ga.setSaveUrl("0");
				ga.setShowInPDF(showPdf);
				ga.setSubjectsInput(subInput);
				long rgNo = (long) getSession().save(ga);
				
				if(inlagList != null && inlagList.size() > 0) {
					if(rgNo > 0) {
						for(InternationalizaionLanguages inlag : inlagList) {
							LanguageSpecificGlobalActivityDetails lsga = new LanguageSpecificGlobalActivityDetails();
							lsga.setCreatedBy("superadmin");
							lsga.setCreatedOn(new Date());
							lsga.setGlobalActivity(ga);
							lsga.setInlagId(inlag);
							if(inlag.getCountryCode().equals("US"))
								lsga.setName(name);
							else lsga.setName(lsgaName);
							lsga.setOrderNo(orderNo);
							getSession().save(lsga);
						}
					}
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ga;
	}

	private void saveGlobalParamtersData(String name, String lsengName, String lsesName, int orderNo, 
			List<InternationalizaionLanguages> inlagList, String parameterPhase, ControlType controlType,
			Boolean allowDelete,String parameterCode, List<String> values, GlobalActivity ga, boolean bindActivity){
		try {
			GlobalParameter gp = (GlobalParameter) getSession().createCriteria(GlobalParameter.class)
					                .add(Restrictions.eq("parameterName", name)).uniqueResult();
			if(gp == null) {
				GlobalParameter globalParameter = new GlobalParameter();
				globalParameter.setParameterName(name);
				globalParameter.setControlName("");
				globalParameter.setBindActivity(bindActivity);
				if(bindActivity == true)
					globalParameter.setActivity(ga);
				globalParameter.setOrderNo(orderNo);
				globalParameter.setParameterPhase(parameterPhase);
				globalParameter.setCreatedBy("superadmin");
				globalParameter.setCreatedOn(new Date());
				globalParameter.setContentType(controlType);
				globalParameter.setAllowDelete(allowDelete);
				globalParameter.setParameterCode(parameterCode);
				getSession().save(globalParameter);
				
				if(values != null && values.size() > 0) {
					int count =1;
					for(String value: values) {
						GlobalValues globalValue = (GlobalValues)getSession().createCriteria(GlobalValues.class)
								.add(Restrictions.eq("name", value)).uniqueResult();
						
						ParameterControlTypeValues parameterControlTypeValue = new ParameterControlTypeValues();
						parameterControlTypeValue.setCreatedBy("superadmin");
						parameterControlTypeValue.setCreatedOn(new Date());
						parameterControlTypeValue.setControlType(controlType);
						parameterControlTypeValue.setGlobalParameter(globalParameter);
						parameterControlTypeValue.setGlobalValues(globalValue);
						getSession().save(parameterControlTypeValue);
					}
				}
				long rgNo = (long) getSession().save(globalParameter);
				if(inlagList != null && inlagList.size() > 0) {
					if(rgNo > 0) {
						for(InternationalizaionLanguages inlag : inlagList) {
							LanguageSpecificGlobalParameterDetails lsga = new LanguageSpecificGlobalParameterDetails();
							lsga.setCreatedBy("superadmin");
							lsga.setCreatedOn(new Date());
							lsga.setParameterId(globalParameter);
							lsga.setInlagId(inlag);
							if(inlag.getCountryCode().equals("US"))
								lsga.setName(lsengName);
							else lsga.setName(lsesName);
							lsga.setOrderNo(orderNo);
							getSession().save(lsga);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void applicationConfiguration() {
		Long count=null;
		try {
			count=(Long) getSession().createCriteria(ApplicationConfiguration.class)
					.setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == null || count == 0) {
			List<ApplicationConfiguration> apConfigList = new ArrayList<>();
			StatusMaster activeStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
			
			StatusMaster inactiveStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.INACTIVE.toString())).uniqueResult();
			
			apConfigList.add(new ApplicationConfiguration("Pdf Header", "AVAN", inactiveStatus));
			apConfigList.add(new ApplicationConfiguration("Pdf Header", "ADV", activeStatus));
			
			for(ApplicationConfiguration apc : apConfigList)
				getSession().save(apc);
		}
		
	}

	@SuppressWarnings("unchecked")
	private void applicationSideMenus() {
		Long count=null;
		try {
			count=(Long) getSession().createCriteria(ApplictionSideMenus.class)
					.setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == null || count == 0) {
			List<ApplictionSideMenus> asmList = new ArrayList<>();
			List<ApplicationMenus> apmList = getSession().createCriteria(ApplicationMenus.class).list();
			Map<Long, ApplicationMenus> amMap = new HashMap<>();
			if(apmList != null && apmList.size() > 0) {
				for(ApplicationMenus apm : apmList)
					amMap.put(apm.getId(), apm);
				
				//Site Administration and User Coniguration
				asmList.add(new ApplictionSideMenus("Module Access", amMap.get(1L), "superadmin", new Date(), "/modulesAccess/getModuleAccessForm",true));
				asmList.add(new ApplictionSideMenus("User Creation", amMap.get(2L), "superadmin", new Date(), "/user/createUser",true));
				asmList.add(new ApplictionSideMenus("Role Creation", amMap.get(2L), "superadmin", new Date(), "/userRole/createRole",true));
				
				//Administration 
				asmList.add(new ApplictionSideMenus("Units", amMap.get(3L), "superadmin", new Date(), "/units/unitsForm",true));
				asmList.add(new ApplictionSideMenus("Values", amMap.get(3L), "superadmin", new Date(), "/values/valuesPage",true));
				asmList.add(new ApplictionSideMenus("Groups", amMap.get(3L), "superadmin", new Date(), "/groups/groupPage",true));
				asmList.add(new ApplictionSideMenus("Activity", amMap.get(3L), "superadmin", new Date(), "/globalAtivity/activityPage",true));
				asmList.add(new ApplictionSideMenus("Parameters", amMap.get(3L), "superadmin", new Date(), "/parameters/parmetersPage",true));
				asmList.add(new ApplictionSideMenus("Default Activity", amMap.get(3L), "superadmin", new Date(), "/defaultActivity/defaultActivityForm",true));
				asmList.add(new ApplictionSideMenus("Custom Activity Parameters", amMap.get(3L), "superadmin", new Date(), "/customActParams/createCustomActivityParameters",true));
				asmList.add(new ApplictionSideMenus("Rule", amMap.get(3L), "superadmin", new Date(), "/rules/rulesPage",true));
				asmList.add(new ApplictionSideMenus("Condition", amMap.get(3L), "superadmin", new Date(), "/condition/conditionForm",true));
				asmList.add(new ApplictionSideMenus("Group Design", amMap.get(3L), "superadmin", new Date(), "/groups/groupDesignActivity",true));
				asmList.add(new ApplictionSideMenus("Diet Plan", amMap.get(3L), "superadmin", new Date(), "/mealDiet/mealDietPlanList",true));
				
				//Instrument
				asmList.add(new ApplictionSideMenus("Instrument", amMap.get(4L), "superadmin", new Date(), "/instrument/instrumentForm",true));
				asmList.add(new ApplictionSideMenus("Deep Freezer", amMap.get(4L), "superadmin", new Date(), "/instrument/deepfreezerForm",false));
				//Pharmacy
				asmList.add(new ApplictionSideMenus("Drug List", amMap.get(5L), "superadmin", new Date(), "/drugReception/drugListForm",false));
				asmList.add(new ApplictionSideMenus("Drug Reception Approval", amMap.get(5L), "superadmin", new Date(), "/drugReception/drugApprovalList",false));
				asmList.add(new ApplictionSideMenus("Drug Reception", amMap.get(5L), "superadmin", new Date(), "/drugReception/drugReceptionForm",false));
				asmList.add(new ApplictionSideMenus("Documentary Requirements", amMap.get(5L), "superadmin", new Date(), "/documentryRequ/documentryRequirements",false));
				asmList.add(new ApplictionSideMenus("Drug Dispensing List", amMap.get(5L), "superadmin", new Date(), "/studyDrugDispensing/studyDrugDispensingList",false));
				asmList.add(new ApplictionSideMenus("Line Clearance Activity", amMap.get(5L), "superadmin", new Date(), "/pharmacyData/lineClearanceActivityList",false));
				asmList.add(new ApplictionSideMenus("Dispensed IP Handover", amMap.get(5L), "superadmin", new Date(), "/pharmacyData/dispensedIPHandoverList",false));
				asmList.add(new ApplictionSideMenus("IP Retention", amMap.get(5L), "superadmin", new Date(), "/pharmacyData/ipRetentionList",false));
				asmList.add(new ApplictionSideMenus("Status Of Unused Dispensed", amMap.get(5L), "superadmin", new Date(), "/pharmacyData/statusOfUnusedDispensedList",false));
				asmList.add(new ApplictionSideMenus("IP Discard", amMap.get(5L), "superadmin", new Date(), "/pharmacyData/ipDiscardList",false));
				//Study
				asmList.add(new ApplictionSideMenus("Randomization", amMap.get(6L), "superadmin", new Date(), "/randomization/uploadRandomizationForm",true));
				asmList.add(new ApplictionSideMenus("Project List", amMap.get(6L), "superadmin", new Date(), "/studydesign/studyDesignPage",true));
				asmList.add(new ApplictionSideMenus("Assign Study", amMap.get(6L), "superadmin", new Date(), "/delegation/delegationPage",true));
				asmList.add(new ApplictionSideMenus("Study Execution", amMap.get(6L), "superadmin", new Date(), "/studyExe/studyExePage",true));
				asmList.add(new ApplictionSideMenus("Deviations", amMap.get(6L), "superadmin", new Date(), "/deviations/deviationPage/Submit",true));
				asmList.add(new ApplictionSideMenus("Study Groups", amMap.get(6L), "superadmin", new Date(), "/studyGroups/studyGroupsForm",true));
				asmList.add(new ApplictionSideMenus("Meal Menu", amMap.get(6L), "superadmin", new Date(), "/stdMealDietConfig/studyMealDietConfiguration",true));
				asmList.add(new ApplictionSideMenus("Left Over Meal Record", amMap.get(6L), "superadmin", new Date(), "/subMealCon/studyMealConsumptionPage",true));
				asmList.add(new ApplictionSideMenus("Drug Dispensing Details", amMap.get(6L), "superadmin", new Date(), "/drugDispanse/drugDispanseEntry",true));
				asmList.add(new ApplictionSideMenus("Allow Meals", amMap.get(6L), "superadmin", new Date(), "/allowMeal/allowMealsPage",true));
				
				//Barcodes
				asmList.add(new ApplictionSideMenus("Barcodes", amMap.get(7L), "superadmin", new Date(), "/barcodelabels/barcodelabelsPage",true));
				asmList.add(new ApplictionSideMenus("Subject Container", amMap.get(7L), "superadmin", new Date(), "/barcodelabels/stdSubjectContainerPrint",false));
				asmList.add(new ApplictionSideMenus("Box", amMap.get(7L), "superadmin", new Date(), "/barcodelabels/boxBarCodePrintPage",false));
				asmList.add(new ApplictionSideMenus("Instrument", amMap.get(7L), "superadmin", new Date(), "/barcodelabels/instrumentBarCode",true));
				asmList.add(new ApplictionSideMenus("Deep Freezer", amMap.get(7L), "superadmin", new Date(), "/barcodelabels/deepFreezerBarCode",false));
				asmList.add(new ApplictionSideMenus("Centrifuge", amMap.get(7L), "superadmin", new Date(), "/barcodelabels/centrifugation",false));
				
				//Review
				asmList.add(new ApplictionSideMenus("Projects", amMap.get(8L), "superadmin", new Date(), "/studydesign/projectApprovalList",true));
				asmList.add(new ApplictionSideMenus("Randomization", amMap.get(8L), "superadmin", new Date(), "/randomization/randomizationApprovalList",true));
				asmList.add(new ApplictionSideMenus("Study Review", amMap.get(8L), "superadmin", new Date(), "/studyExe/studyReview",true));
				asmList.add(new ApplictionSideMenus("Response to Study Review", amMap.get(8L), "superadmin", new Date(), "/studyExe/responseToStudyReview",true));
				asmList.add(new ApplictionSideMenus("Deviations", amMap.get(8L), "superadmin", new Date(), "/deviations/deviationPage/Review",true));
				
				//Reports
				asmList.add(new ApplictionSideMenus("Drug Dispensing PDF", amMap.get(9L), "superadmin", new Date(), "/drugDispansePdf/generateDrugDispansePdf",true));
				asmList.add(new ApplictionSideMenus("Data Crf", amMap.get(9L), "superadmin", new Date(), "/crfData/studyCrfData",true));
				asmList.add(new ApplictionSideMenus("Meal Menu", amMap.get(9L), "superadmin", new Date(), "/mealMenuReports/generateMealMenuReport",true));
				asmList.add(new ApplictionSideMenus("Missed Timepoints", amMap.get(9L), "superadmin", new Date(), "/missedtpsReports/generateMissedTimepointsReport",true));
				
				//Logs
				asmList.add(new ApplictionSideMenus("Audit Log ", amMap.get(10L), "superadmin", new Date(), "/auditlog/auditLogTrigerDataSearch",true));
				asmList.add(new ApplictionSideMenus("Activity Log ", amMap.get(10L), "superadmin", new Date(), "/auditlog/activityLoginDataSearch",true));
				asmList.add(new ApplictionSideMenus("Login Log ", amMap.get(10L), "superadmin", new Date(), "/auditlog/userLogInfoPage",true));
			
				for(ApplictionSideMenus asm : asmList) {
					getSession().save(asm);
				}
			}
		}
	}
		
	private void applicationMenus() {
		
		Long count=null;
		try {
			count=(Long) getSession().createCriteria(ApplicationMenus.class)
					.setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == null || count == 0) {
			List<ApplicationMenus> amList= new ArrayList<>();
			
			amList.add(new ApplicationMenus("Site Administrator", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("User Configuration", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Administration", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Instrument", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Pharmacy", "superadmin", new Date(),false));
			amList.add(new ApplicationMenus("Study", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Barcode", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Review", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Reports", "superadmin", new Date(),true));
			amList.add(new ApplicationMenus("Logs", "superadmin", new Date(),true));
			
			
			for(ApplicationMenus am : amList) {
				getSession().save(am);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	private void globalValues() {
		Long count=null;
		try {
			count=(Long) getSession().createCriteria(GlobalValues.class)
					.setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == null || count == 0) {
			List<InternationalizaionLanguages> inlagList = getSession().createCriteria(InternationalizaionLanguages.class).list();
			if(inlagList != null && inlagList.size() > 0) {
				List<String> strList  = new ArrayList<>();
				strList.add("Male");
				strList.add("Female");
				
				strList.add("Yes");
				strList.add("No");
				
				strList.add("Fit for participation in the study.");
				strList.add("Unfit for participation in the study.");
				
				int gvCount = 1;
				for(String st : strList) {
					GlobalValues gv = new GlobalValues();
					gv.setName(st);
					gv.setOrderNo(gvCount);
					getSession().save(gv);
					gvCount++;
					if(gvCount > 2)
						gvCount = 1;
					for(InternationalizaionLanguages inlag : inlagList) {
						LanguageSpecificValueDetails lspv = new LanguageSpecificValueDetails();
						lspv.setGlobalValId(gv);
						switch (st) {
							case "Male":
									if(inlag.getLangCode().equals("en"))
										  lspv.setName("Male");
									else 
										lspv.setName("Masculina");
								break;
							case "Female":
									if(inlag.getLangCode().equals("en"))
										  lspv.setName("Female");
									else 
										lspv.setName("Femenina");
								break;
							case "Yes":
									if(inlag.getLangCode().equals("en"))
										  lspv.setName("Yes");
									else 
										lspv.setName("Si");
								break;
							case "No":
									if(inlag.getLangCode().equals("en"))
										  lspv.setName("No");
									else 
										lspv.setName("No");
								break;
							case "Fit for participation in the study.":
									if(inlag.getLangCode().equals("en"))
										  lspv.setName("Fit for participation in the study.");
									else 
										lspv.setName("Fit for participation in the study.");
								break;
							case "Unfit for participation in the study.":
									if(inlag.getLangCode().equals("en"))
										  lspv.setName("Unfit for participation in the study.");
									else 
										lspv.setName("Unfit for participation in the study.");
								break;
							default:
								break;
						}
						lspv.setInlagId(inlag);
						getSession().save(lspv);
					}
				}
			}
		}
		
	}

	private void studyPhace() {
		Long count=null;
		try {
			count=(Long) getSession().createCriteria(StudyPhases.class)
					.setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == null || count == 0) {
		
		List<StudyPhases>studyp=new ArrayList<>();
		
		studyp.add(new StudyPhases("Check In", "SCI"));
		studyp.add(new StudyPhases("Check Out", "SCO"));
		studyp.add(new StudyPhases("Study Execution","SE"));
		studyp.add(new StudyPhases("P1 Only", "P1O"));
		studyp.add(new StudyPhases("P2 Only", "P2O"));
		studyp.add(new StudyPhases("P3 Only", "P3O"));
		studyp.add(new StudyPhases("P4 Only", "P4O"));
		studyp.add(new StudyPhases("Study", "STDL"));//Study Level
		
		for(StudyPhases sp :studyp) {
			getSession().save(sp);
		 }
		}
		
	}

	private void controlTypeTable() {
		Long count=null;
		try {
			count=(Long) getSession().createCriteria(ControlType.class)
					.setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == null || count == 0) {
		
		List<ControlType>ctlist=new ArrayList<>();
		
		ctlist.add(new ControlType("RB", "Radio Button"));
		ctlist.add(new ControlType("TB", "Text Box"));
		ctlist.add(new ControlType("SB", "Select Box"));
		ctlist.add(new ControlType("TA", "Text Area"));
		ctlist.add(new ControlType("AC", "Auto Complete"));
		ctlist.add(new ControlType("CKE", "Ck Editor"));
		ctlist.add(new ControlType("CB", "Check Box"));
		ctlist.add(new ControlType("HIDDEN", "Hidden"));
		ctlist.add(new ControlType("LABEL", "Label"));
		ctlist.add(new ControlType("DP", "Date Picker"));
		ctlist.add(new ControlType("TP", "Time Picker"));
		ctlist.add(new ControlType("ST", "Start Time"));
		ctlist.add(new ControlType("ET", "End Time"));
		ctlist.add(new ControlType("FU", "File Upload"));
		for(ControlType sp :ctlist) {
			getSession().save(sp);
		 }
		}
	}

	private void internationalLangTable() {
		Long count = null;
		try {
			count = (Long) getSession().createCriteria(InternationalizaionLanguages.class)
					.setProjection(Projections.rowCount()).uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == null || count == 0) {
			List<InternationalizaionLanguages> inlagList = new ArrayList<>();
			FromStaticData en = (FromStaticData) getSession().createCriteria(FromStaticData.class)
					.add(Restrictions.eq("fieldName", StaticData.ENGLISH.toString()))
					.add(Restrictions.eq("code", StaticData.EN.toString())).uniqueResult();
			FromStaticData es = (FromStaticData) getSession().createCriteria(FromStaticData.class)
					.add(Restrictions.eq("fieldName", StaticData.MEXICO.toString()))
					.add(Restrictions.eq("code", StaticData.ES.toString())).uniqueResult();
			inlagList.add(new InternationalizaionLanguages(en.getFieldName(), en.getFieldValue(), StaticData.US.toString(),
					StaticData.UK.toString()));
			inlagList.add(new InternationalizaionLanguages(es.getFieldName(), es.getFieldValue(), StaticData.ES.toString(),
					StudyDesign.MEXICO.toString()));
			inlagList.stream().forEach((inlag) -> {
				getSession().save(inlag);
			});
		}
	}

	private void fromStaticDataTable(StatusMaster activeStatus) {
		Long count = null;
		try {
			count = (Long) getSession().createCriteria(FromStaticData.class).setProjection(Projections.rowCount())
					.uniqueResult();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (count == null || count == 0l) {
			List<FromStaticData> list = new ArrayList<FromStaticData>();
			// study
			list.add(new FromStaticData(StudyDesign.STUDYDESIGN.toString(), "Fast", StaticData.FAST.toString()));
			list.add(new FromStaticData(StudyDesign.STUDYDESIGN.toString(), "Fed", StaticData.FED.toString()));
			list.add(new FromStaticData(StudyDesign.STUDYDESIGN.toString(), "Fast & Fed",
					StaticData.FASTANDFED.toString()));
			list.add(new FromStaticData(StudyDesign.MASKING.toString(), "Open Label",
					StaticData.OPENLABEL.toString()));
			list.add(new FromStaticData(StudyDesign.MASKING.toString(), "Sigle Blind",
					StaticData.SINGLEBLIND.toString()));
			list.add(new FromStaticData(StudyDesign.MASKING.toString(), "Double Blind",
					StaticData.DOUBLEBLIND.toString()));
			list.add(
					new FromStaticData(StudyDesign.TREATMENTCODE.toString(), "Show", StaticData.SHOW.toString()));
			list.add(new FromStaticData(StudyDesign.TREATMENTCODE.toString(), "Hide", StaticData.HIDE.toString()));
			
			list.add(new FromStaticData(StudyDesign.TREATMENTWISENOOFDOSING.toString(), "Yes",
					StaticData.YES.toString()));
			list.add(
					new FromStaticData(StudyDesign.TREATMENTWISENOOFDOSING.toString(), "No", StaticData.NO.toString()));
			list.add(new FromStaticData(StudyDesign.TREATMENTSPECIFICSAMPLETIMEPOINTS.toString(), "Yes",
					StaticData.YES.toString()));
			list.add(new FromStaticData(StudyDesign.TREATMENTSPECIFICSAMPLETIMEPOINTS.toString(), "No",
					StaticData.NO.toString()));
			list.add(new FromStaticData(StudyDesign.TREATMENTWISEDOSEPERAMETER.toString(), "Yes",
					StaticData.YES.toString()));
			list.add(new FromStaticData(StudyDesign.TREATMENTWISEDOSEPERAMETER.toString(), "No",
					StaticData.NO.toString()));
			list.add(new FromStaticData(StudyDesign.STUDYCATEGORY.toString(), "Pilot", StaticData.PILOT.toString()));
			list.add(new FromStaticData(StudyDesign.STUDYCATEGORY.toString(), "Peyote", StaticData.PEYOTE.toString()));

			list.add(new FromStaticData(StudyDesign.CONTROLTYPE.toString(), "text", StaticData.TEXT.toString()));
			list.add(new FromStaticData(StudyDesign.CONTROLTYPE.toString(), "readio", StaticData.RADIO.toString()));
			list.add(
					new FromStaticData(StudyDesign.CONTROLTYPE.toString(), "checkbox", StaticData.CHECKBOX.toString()));
			list.add(new FromStaticData(StudyDesign.CONTROLTYPE.toString(), "select", StaticData.SELECT.toString()));
			list.add(
					new FromStaticData(StudyDesign.CONTROLTYPE.toString(), "textarea", StaticData.TEXTAREA.toString()));

			// period
			list.add(new FromStaticData(StudyDesign.PERIODTYPE.toString(), "Admission",
					StaticData.ADMISSION.toString()));
			list.add(new FromStaticData(StudyDesign.PERIODTYPE.toString(), "Period", StaticData.PERIOD.toString()));
			list.add(new FromStaticData(StudyDesign.PERIODTYPE.toString(), "Post Period",
					StaticData.POSTDOSE.toString()));
////		TreatmentInfo
			list.add(new FromStaticData(StudyDesign.TREATMENTTYPE.toString(), "Fast", StaticData.FAST.toString()));
			list.add(new FromStaticData(StudyDesign.TREATMENTTYPE.toString(), "Fed", StaticData.FED.toString()));

			// Dosing timepoints
			list.add(new FromStaticData(StudyDesign.FASTINGCRITERIATYPE.toString(), "Minutes",
					StaticData.MINUTES.toString()));
			list.add(new FromStaticData(StudyDesign.FASTINGCRITERIATYPE.toString(), "Hours",
					StaticData.HOURS.toString()));
			list.add(new FromStaticData(StudyDesign.FASTINGCRITERIATYPE.toString(), "Days",
					StaticData.TEXTAREA.toString()));
			list.add(new FromStaticData(StudyDesign.FEDCRITERIATYPE.toString(), "Minutes",
					StaticData.MINUTES.toString()));
			list.add(new FromStaticData(StudyDesign.FEDCRITERIATYPE.toString(), "Hours", StaticData.HOURS.toString()));
			list.add(
					new FromStaticData(StudyDesign.FEDCRITERIATYPE.toString(), "Days", StaticData.TEXTAREA.toString()));
			list.add(new FromStaticData(StudyDesign.WINDOWPERIODTYPE.toString(), "Minutes",
					StaticData.MINUTES.toString()));
			list.add(new FromStaticData(StudyDesign.WINDOWPERIODTYPE.toString(), "Hours", StaticData.HOURS.toString()));
			list.add(new FromStaticData(StudyDesign.WINDOWPERIODTYPE.toString(), "Days",
					StaticData.TEXTAREA.toString()));

			// Sample timepoints
			list.add(new FromStaticData(StudyDesign.LIGHTCONDITION.toString(), "Normal", StaticData.NORMAL.toString()));
			list.add(new FromStaticData(StudyDesign.LIGHTCONDITION.toString(), "Monochromatic",
					StaticData.MONOCHROMATIC.toString()));
			list.add(new FromStaticData(StudyDesign.TYPEOFVACUTAINERUSED.toString(), "K2EDTA",
					StaticData.K2EDTA.toString()));
			list.add(new FromStaticData(StudyDesign.BUFFER.toString(), "Yes", StaticData.YES.toString()));
			list.add(new FromStaticData(StudyDesign.BUFFER.toString(), "No", StaticData.NO.toString()));

			list.add(new FromStaticData(StudyDesign.TIMEPOINTTYPE.toString(), "Pre Dose",
					StaticData.PREDOSE.toString()));
			list.add(new FromStaticData(StudyDesign.TIMEPOINTTYPE.toString(), "Post Dose",
					StaticData.POSTDOSE.toString()));
			list.add(new FromStaticData(StudyDesign.TIMEPOINTTYPE.toString(), "Ambulatory",
					StaticData.AMBULATORY.toString()));

//		MEALSTTYPE
			list.add(new FromStaticData(StudyDesign.MEALSTTYPE.toString(), "Normal break fast",
					StaticData.NORMALBREAKFAST.toString()));
			list.add(new FromStaticData(StudyDesign.MEALSTTYPE.toString(), "Standard break fast",
					StaticData.STANDARDBREAKFAST.toString()));
			list.add(new FromStaticData(StudyDesign.MEALSTTYPE.toString(), "High fat break fast",
					StaticData.HIGHFATBREAKFAST.toString()));
			list.add(new FromStaticData(StudyDesign.MEALSTTYPE.toString(), "Lunch", StaticData.LUNCH.toString()));
			list.add(new FromStaticData(StudyDesign.MEALSTTYPE.toString(), "Snacks", StaticData.SNACKS.toString()));
			list.add(new FromStaticData(StudyDesign.MEALSTTYPE.toString(), "Dinner", StaticData.DINNER.toString()));
			list.add(new FromStaticData(StudyDesign.CONSUMPTION.toString(), "Full", StaticData.FULL.toString()));
			list.add(new FromStaticData(StudyDesign.CONSUMPTION.toString(), "Half", StaticData.HALF.toString()));
			list.add(new FromStaticData(StudyDesign.CONSUMPTION.toString(), "Others", StaticData.OTHERS.toString()));
			list.add(new FromStaticData(StudyDesign.CONSUMPTION.toString(), "Nothing", StaticData.NOTHING.toString()));
			list.add(new FromStaticData(StudyDesign.COMPLETIONTYPE.toString(), "Yes", StaticData.YES.toString()));
			list.add(new FromStaticData(StudyDesign.COMPLETIONTYPE.toString(), "No", StaticData.NO.toString()));

			// vital time points
			list.add(new FromStaticData(StudyDesign.VITALPOSITION.toString(), "Prone", StaticData.PRONE.toString()));
			list.add(new FromStaticData(StudyDesign.VITALPOSITION.toString(), "Supine", StaticData.SUPINE.toString()));
			list.add(
					new FromStaticData(StudyDesign.TEMPERATURE.toString(), "Forehead", StaticData.FOREHEAD.toString()));
			list.add(new FromStaticData(StudyDesign.ORTHOSTATIC.toString(), "Yes", StaticData.YES.toString()));
			list.add(new FromStaticData(StudyDesign.ORTHOSTATIC.toString(), "No", StaticData.NO.toString()));

			list.add(new FromStaticData(StudyDesign.TESTNAMES.toString(), "Forehead",
					StaticData.RESPIRATORYRATE.toString()));
			list.add(new FromStaticData(StudyDesign.TESTNAMES.toString(), "Forehead", StaticData.BP.toString()));
			list.add(new FromStaticData(StudyDesign.TESTNAMES.toString(), "Forehead", StaticData.PULSERATE.toString()));
			list.add(new FromStaticData(StudyDesign.TESTNAMES.toString(), "Forehead", StaticData.WELLBEING.toString()));
			list.add(new FromStaticData(StudyDesign.TESTNAMES.toString(), "Forehead",
					StaticData.TEMPERATURE.toString()));

			// ECG Time points
			list.add(new FromStaticData(StudyDesign.ECGAPPLICABLE.toString(), "Yes", StaticData.YES.toString()));
			list.add(new FromStaticData(StudyDesign.ECGAPPLICABLE.toString(), "No", StaticData.NO.toString()));
			list.add(new FromStaticData(StudyDesign.ECGPOSITION.toString(), "Prone", StaticData.PRONE.toString()));

//		Centrifugation
			list.add(new FromStaticData(StudyDesign.CENTRIFUGATIONAPPLICABLE.toString(), "Yes",
					StaticData.YES.toString()));
			list.add(new FromStaticData(StudyDesign.CENTRIFUGATIONAPPLICABLE.toString(), "No",
					StaticData.NO.toString()));
			list.add(new FromStaticData(StudyDesign.CENTRIFUGATIONAPPLICABLEFOR.toString(), "All",
					StaticData.ALL.toString()));
			list.add(new FromStaticData(StudyDesign.CENTRIFUGATIONAPPLICABLEFOR.toString(), "Vacutainer 1",
					StaticData.VACUTAINER1.toString()));
			list.add(new FromStaticData(StudyDesign.CENTRIFUGATIONAPPLICABLEFOR.toString(), "Vacutainer 2",
					StaticData.VACUTAINER2.toString()));
			list.add(new FromStaticData(StudyDesign.CENTRIFUGATIONAPPLICABLEFOR.toString(), "Vacutainer 3",
					StaticData.VACUTAINER3.toString()));
			list.add(new FromStaticData(StudyDesign.CENTRIFUGATIONAPPLICABLEFOR.toString(), "Vacutainer 4",
					StaticData.VACUTAINER4.toString()));
			list.add(new FromStaticData(StudyDesign.CENTRIFUGATIONAPPLICABLEFOR.toString(), "Vacutainer 5",
					StaticData.VACUTAINER5.toString()));

			list.add(new FromStaticData(StudyDesign.CENTRIFUGATIONCONDITION.toString(), "Buffer",
					StaticData.BUFFER.toString()));
			list.add(new FromStaticData(StudyDesign.PROCESSCONDITION.toString(), "Buffer",
					StaticData.BUFFER.toString()));
			list.add(new FromStaticData(StudyDesign.ISSTORAGEDIFF.toString(), "Buffer", StaticData.BUFFER.toString()));

			list.add(new FromStaticData(StudyDesign.ISSTORAGEDIFF.toString(), "Yes", StaticData.YES.toString()));
			list.add(new FromStaticData(StudyDesign.ISSTORAGEDIFF.toString(), "No", StaticData.NO.toString()));
			list.add(new FromStaticData(StudyDesign.PROCESSMATRIX.toString(), "Serum", StaticData.SERUM.toString()));
			list.add(new FromStaticData(StudyDesign.PROCESSMATRIX.toString(), "Plasma", StaticData.PLASMA.toString()));
			list.add(new FromStaticData(StudyDesign.PROCESSMATRIX.toString(), "Whole Blood",
					StaticData.WHOLEBLOOD.toString()));
			list.add(new FromStaticData(StudyDesign.PROCESSMATRIX.toString(), "Others", StaticData.OTHERS.toString()));
			list.add(new FromStaticData(StudyDesign.STORAGECONDITION.toString(), "condition 1",
					StaticData.CONDITION1.toString()));
			list.add(new FromStaticData(StudyDesign.STORAGECONDITION.toString(), "condition 2",
					StaticData.CONDITION2.toString()));
			list.add(new FromStaticData(StudyDesign.STORAGECONDITION.toString(), "condition 3",
					StaticData.CONDITION3.toString()));

			list.add(new FromStaticData(StudyDesign.ALLOWEDTIME.toString(), "Start Time",
					StaticData.STARTTIME.toString()));
			list.add(new FromStaticData(StudyDesign.ALLOWEDTIME.toString(), "End Time", StaticData.ENDTIME.toString()));
			list.add(new FromStaticData(StudyDesign.STORAGE.toString(), "Time Point Wise",
					StaticData.TIMEPOINTWISE.toString()));
			list.add(new FromStaticData(StudyDesign.STORAGE.toString(), "Subject Wise",
					StaticData.SUBJECTWISE.toString()));

//		Restrictions Compliance Monitoring
			list.add(new FromStaticData(StudyDesign.RESTRICTIONSFOR.toString(), "Others",
					StaticData.CHECKIN.toString()));
			list.add(new FromStaticData(StudyDesign.RESTRICTIONSFOR.toString(), "Buffer",
					StaticData.PREDOSE.toString()));
			list.add(new FromStaticData(StudyDesign.RESTRICTIONSFOR.toString(), "Start Time",
					StaticData.POSTDOSE.toString()));
			list.add(new FromStaticData(StudyDesign.RESTRICTIONSFOR.toString(), "End Time",
					StaticData.CHECKOUT.toString()));
			list.add(new FromStaticData(StudyDesign.RESTRICTIONSFOR.toString(), "Time Point Wise",
					StaticData.AMBULATORY.toString()));
			list.add(new FromStaticData(StudyDesign.RESTRICTIONSFOR.toString(), "Subject Wise",
					StaticData.OTHERS.toString()));

			list.add(new FromStaticData(StudyDesign.SAMPLECOLLECTIONDEVIATIONS.toString(), "Subject Wise",
					StaticData.CODE1.toString()));
			list.add(new FromStaticData(StudyDesign.SAMPLECOLLECTIONDEVIATIONS.toString(), "Subject Wise",
					StaticData.CODE2.toString()));
			list.add(new FromStaticData(StudyDesign.SAMPLECOLLECTIONDEVIATIONS.toString(), "Subject Wise",
					StaticData.CODE3.toString()));
			list.add(new FromStaticData(StudyDesign.SAMPLECOLLECTIONDEVIATIONS.toString(), "Subject Wise",
					StaticData.CODE4.toString()));
			list.add(new FromStaticData(StudyDesign.SAMPLECOLLECTIONDEVIATIONS.toString(), "Subject Wise",
					StaticData.CODE5.toString()));
			list.add(new FromStaticData(StudyDesign.SAMPLECOLLECTIONDEVIATIONS.toString(), "Subject Wise",
					StaticData.CODE6.toString()));

			list.add(new FromStaticData(StudyDesign.DOSEFROMCOMMENTS.toString(), "Comment1",
					StaticData.CODE1.toString()));
			list.add(new FromStaticData(StudyDesign.DOSEFROMCOMMENTS.toString(), "Comment1",
					StaticData.CODE2.toString()));
			
			list.add(new FromStaticData(StudyDesign.GENDER.toString(), "Male",
					StaticData.MALE.toString()));
			list.add(new FromStaticData(StudyDesign.GENDER.toString(), "Female",
					StaticData.FEMALE.toString()));
			list.add(new FromStaticData(StudyDesign.GENDER.toString(), "Both",
					StaticData.BOTH.toString()));
			

			list.add(new FromStaticData(StaticData.ENGLISH.toString(), "en", StaticData.EN.toString()));
			list.add(new FromStaticData(StaticData.MEXICO.toString(), "es", StaticData.ES.toString()));

			list.add(new FromStaticData(StudyDesign.WINDOWPERIODSIGN.toString(), "+/-", StaticData.PLUSANDMINUS.toString()));
			list.add(new FromStaticData(StudyDesign.WINDOWPERIODSIGN.toString(), "+", StaticData.MINUS.toString()));
			list.add(new FromStaticData(StudyDesign.WINDOWPERIODSIGN.toString(), "-", StaticData.PLUS.toString()));
			
			list.add(new FromStaticData(StudyDesign.DOSETYPE.toString(), "Single", StaticData.SINGLE.toString()));
			list.add(new FromStaticData(StudyDesign.DOSETYPE.toString(), "Multiple", StaticData.MULTIPLE.toString()));
			list.add(new FromStaticData(StudyDesign.DOSETYPE.toString(), "Single and Multiple", StaticData.SINGLEANDMULTIPLE.toString()));
						
			for (FromStaticData fsd : list)
				getSession().save(fsd);

			SponsorCode sp = new SponsorCode();
			sp.setCountry("IND");
			sp.setSponsorName("XXXXXXXX");
			sp.setSponsorCode("YYYYYYYYYYY");
			getSession().save(sp);

			DosageForm df = new DosageForm();
			df.setFieldName(StudyDesign.DOSAGEFORM.toString());
			df.setDosaveCode(StaticData.INJECTION.toString());
			df.setDoseForm("Injection");
			df.setActiveStatus(activeStatus);
			getSession().save(df);
			df = new DosageForm();
			df.setFieldName(StudyDesign.DOSAGEFORM.toString());
			df.setDosaveCode(StaticData.TABLET.toString());
			df.setDoseForm("Tablet");
			df.setActiveStatus(activeStatus);
			getSession().save(df);

			VitalTest test = new VitalTest();
			test.setTestName(StaticData.BP.toString());
			test.setMinimum("90/60");
			test.setMaximum("139/89");
			getSession().save(test);
			test = new VitalTest();
			test.setTestName(StaticData.WELLBEING.toString());
			test.setMinimum("");
			test.setMaximum("");
			getSession().save(test);
			test = new VitalTest();
			test.setTestName(StaticData.PULSERATE.toString());
			test.setMinimum("40");
			test.setMaximum("170");
			getSession().save(test);
			test = new VitalTest();
			test.setTestName(StaticData.TEMPERATURE.toString());
			test.setMinimum("95");
			test.setMaximum("99");
			getSession().save(test);
			test = new VitalTest();
			test.setTestName(StaticData.RESPIRATORYRATE.toString());
			test.setMinimum("12");
			test.setMaximum("16");
			getSession().save(test);
		}
	}

	private void statusMasterTable() {
		try {
			Long count = (Long) getSession().createCriteria(StatusMaster.class).setProjection(Projections.rowCount())
					.uniqueResult();
			if (count == 0) {
				List<StatusMaster> sts = new ArrayList<StatusMaster>();
				StatusMaster st = new StatusMaster();
				st.setStatusCode("STUDINFORMATION");
				st.setStatusDesc("Study Information Saved Successfully");
				sts.add(st);

				st = new StatusMaster();
				st.setStatusCode(StudyStatus.ACTIVE.toString());
				st.setStatusDesc("Active State");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.INACTIVE.toString());
				st.setStatusDesc("In-Active State");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.CANCELED.toString());
				st.setStatusDesc("Cancelled State");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.DRAFT.toString());
				st.setStatusDesc("Draft State");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.INREVIEW.toString());
				st.setStatusDesc("In-Review State");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.APPROVED.toString());
				st.setStatusDesc("Approved State");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.DESIGN.toString());
				st.setStatusDesc("Design State");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.INPROGRESS.toString());
				st.setStatusDesc("In-Progress State");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.ONHOLD.toString());
				st.setStatusDesc("On-Hold State");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.ARCHIVED.toString());
				st.setStatusDesc("Archived State");
				sts.add(st);

				st = new StatusMaster();
				st.setStatusCode(StudyStatus.SELECTED.toString());
				st.setStatusDesc("Selected");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.ENROLLED.toString());
				st.setStatusDesc("Enrolled");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.REPLACED.toString());
				st.setStatusDesc("Replaced");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.WITHDRAWN.toString());
				st.setStatusDesc("Withdrawn");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.DROPED.toString());
				st.setStatusDesc("Droped");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.COMPLETED.toString());
				st.setStatusDesc("Completed");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.NEW.toString());
				st.setStatusDesc("New");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.CLOSE.toString());
				st.setStatusDesc("Close");
				sts.add(st);
				st = new StatusMaster();
				st.setStatusCode(StudyStatus.OPEN.toString());
				st.setStatusDesc("Open");
				sts.add(st);
				

				for (StatusMaster ss : sts)
					getSession().save(ss);
			}
			System.out.println(count);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void roleMasterTable() {
		// TODO Auto-generated method stub
		try {
			Long count = (Long) getSession().createCriteria(RoleMaster.class).setProjection(Projections.rowCount())
					.uniqueResult();
			if (count == 0) {

				StatusMaster ss = (StatusMaster) getSession().createCriteria(StatusMaster.class)
						.add(Restrictions.eq("statusCode", "ACTIVE")).uniqueResult();
				List<RoleMaster> roles = new ArrayList<RoleMaster>();
				RoleMaster role = new RoleMaster();
				role.setRole("SUPERADMIN");
				role.setRoleDesc("SUPERADMIN");
				role.setRoleStatus(ss);
				roles.add(role);
				role = new RoleMaster();
				role.setRole("ADMIN");
				role.setRoleDesc("Administration");
				role.setRoleStatus(ss);
				roles.add(role);
				role = new RoleMaster();
				role.setRole("USER");
				role.setRoleDesc("USER");
				role.setRoleStatus(ss);
				roles.add(role);
				role = new RoleMaster();
				role.setRole("QA");
				role.setRoleDesc("QA");
				role.setRoleStatus(ss);
				roles.add(role);
				role = new RoleMaster();
				role.setRole("QC");
				role.setRoleDesc("QC");
				role.setRoleStatus(ss);
				roles.add(role);
				role = new RoleMaster();
				role.setRole("PI");
				role.setRoleDesc("Principal Investigater");
				role.setRoleStatus(ss);
				roles.add(role);
				for (RoleMaster rol : roles)
					getSession().save(rol);
			}
			System.out.println(count);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/*private void studyMasterTable() {
		try {
			Long count = (Long) getSession().createCriteria(StudyMaster.class).setProjection(Projections.rowCount())
					.uniqueResult();
			if (count == 0) {
				StudyMaster st = new StudyMaster();
				st.setProjectNo("default_study");
				st.setStudyStatus((StatusMaster) getSession().createCriteria(StatusMaster.class)
						.add(Restrictions.eq("statusCode", "Active")).uniqueResult());
				st.setStudyState((StatusMaster) getSession().createCriteria(StatusMaster.class)
						.add(Restrictions.eq("statusCode", "Draft")).uniqueResult());
				getSession().save(st);
			}
			System.out.println(count);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/

	private void userMasterTable() {
		try {
			Long count = (Long) getSession().createCriteria(UserMaster.class).setProjection(Projections.rowCount())
					.uniqueResult();
			if (count == 0) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.DATE, (int) 90);
				RoleMaster role = (RoleMaster) getSession().createCriteria(RoleMaster.class)
						.add(Restrictions.eq("role", "SUPERADMIN")).uniqueResult();
				UserMaster user = new UserMaster();
				user.setUsername("superadmin");
				user.setFullName("SuperAdmin");
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				user.setPassword(passwordEncoder.encode("1234"));
				user.setCreatedOn(new Date());
				user.setTranPassword(user.getPassword());
				user.setRole(role);
				user.setAccountexprie(cal.getTime());
				getSession().save(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InternationalizaionLanguages> getInternationalizaionLanguagesRecrdsList() {
		return getSession().createCriteria(InternationalizaionLanguages.class).list();
	}

	@Override
	public List<StudyMaster> allUserActiveStudys(Long userId) {
		StatusMaster ss = (StatusMaster) getSession().createCriteria(StatusMaster.class)
				.add(Restrictions.eq("statusCode", StudyStatus.CANCELED.toString())).uniqueResult();
		@SuppressWarnings("unchecked")
		List<Long> studyIds = getSession().createCriteria(UserWiseStudiesAsignMaster.class)
				.add(Restrictions.eq("userId.id", userId)).setProjection(Projections.property("studyMaster.id")).list();
		if (studyIds.size() > 0) {
			@SuppressWarnings("unchecked")
			List<StudyMaster> studIds = getSession().createCriteria(StudyMaster.class)
					.add(Restrictions.in("id", studyIds)).add(Restrictions.ne("studyStatus", ss)).list();
			return studIds;
		}

		return new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FromStaticData> fromStaticDataWithFieldName(String fieldName) {
		// TODO Auto-generated method stub
		if (!fieldName.equals(StudyDesign.DOSAGEFORM.toString())) {
			return getSession().createCriteria(FromStaticData.class).add(Restrictions.eq("fieldName", fieldName))
					.list();
		} else {
			List<FromStaticData> list = new ArrayList<>();
			List<DosageForm> doseforms = getSession().createCriteria(DosageForm.class)
					.add(Restrictions.eq("fieldName", fieldName)).list();
			for (DosageForm df : doseforms) {
				list.add(new FromStaticData(StudyDesign.DOSAGEFORM.toString(), df.getDoseForm(), df.getDosaveCode()));
			}
			return list;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RolesWiseModules> getApplicationMenus(UserMaster user) {
		return getSession().createCriteria(RolesWiseModules.class)
				.add(Restrictions.eq("role", user.getRole()))
				.add(Restrictions.eq("status", "active")).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FromStaticData> studyStaticFieldValues() {
		List<FromStaticData> list0 = getSession().createCriteria(FromStaticData.class).list();
		List<FromStaticData> list = new ArrayList<>();
		List<DosageForm> doseforms = getSession().createCriteria(DosageForm.class).list();
		for (DosageForm df : doseforms) {
			list.add(new FromStaticData(StudyDesign.DOSAGEFORM.toString(), df.getDoseForm(), df.getDosaveCode()));
		}
		list0.addAll(list);
		return list0;
	}

	@Override
	public long saveUserActivityRecord(UserActivity ua) {
		return (long) getSession().save(ua);
	}

	@Override
	public UserActivity getUserActivityRecord(long id) {
		return (UserActivity) getSession().createCriteria(UserActivity.class)
				.add(Restrictions.eq("sno", id)).uniqueResult();
	}

	@Override
	public boolean updateUserActivityRecord(UserActivity uaPojo) {
		boolean flag=false;
		try {
			getSession().update(uaPojo);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public UserMaster findByUsernameData(String username) {
		UserMaster user=null;
		user=(UserMaster) getSession().createCriteria(UserMaster.class)
				.add(Restrictions.eq("username", username)).uniqueResult();
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserActivity> getParticularUserActivityRecords(String userName, Date fdate, Date tdate) {
		List<UserActivity> uaList=null;
		if(!userName.equals("-1")) {
			uaList= getSession().createCriteria(UserActivity.class)
					.add(Restrictions.eq("userName", userName))
					.add(Restrictions.between("loginDate", fdate, tdate))
					.addOrder(Order.asc("id")).list();
		}else {
			uaList= getSession().createCriteria(UserActivity.class)
					.add(Restrictions.between("loginDate", fdate, tdate))
					.addOrder(Order.asc("id")).list();
		}
		
		return uaList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMaster> getUserMasterList() {
		return getSession().createCriteria(UserMaster.class).list();
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public List<AuditLog> getAuditLogList(Date fdate, Date tdate, Long studyid) {
		List<AuditLog> uaList=null;
		if(studyid!=-1) {
			uaList= getSession().createCriteria(AuditLog.class)
				.add(Restrictions.eq("studyId.id", studyid))
				.add(Restrictions.or(Restrictions.between("createdOn", fdate, tdate),Restrictions.between("updatedOn", fdate, tdate)))
				.addOrder(Order.asc("id")).list();
		}else {
			uaList= getSession().createCriteria(AuditLog.class)
					.add(Restrictions.or(Restrictions.between("createdOn", fdate, tdate),Restrictions.between("updatedOn", fdate, tdate)))
					.addOrder(Order.asc("id")).list();
		}
		return uaList;
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public List<ActivityLog> getActivityLogList(Long volunteer, Date fdate, Date tdate, Long studyid, Long subjectid) {
		List<ActivityLog> acList=null;
		if(studyid!=-1&& volunteer==-1&& subjectid==-1) {
			acList= getSession().createCriteria(ActivityLog.class)
					.add(Restrictions.eq("sm.id", studyid))
					.add(Restrictions.between("activityDoneOn", fdate, tdate))
					.addOrder(Order.asc("id")).list();
		}else if(studyid==-1&& volunteer!=-1&& subjectid==-1) {
			acList= getSession().createCriteria(ActivityLog.class)
					.add(Restrictions.eq("volId.id", volunteer))
					.add(Restrictions.between("activityDoneOn", fdate, tdate))
					.addOrder(Order.asc("id")).list();
		}else if(studyid==-1&& volunteer==-1&& subjectid!=-1) {
			acList= getSession().createCriteria(ActivityLog.class)
					.add(Restrictions.eq("subjectId.id", subjectid))
					.add(Restrictions.between("activityDoneOn", fdate, tdate))
					.addOrder(Order.asc("id")).list();
		}else if(studyid!=-1&& volunteer!=-1&& subjectid!=-1) {
			acList= getSession().createCriteria(ActivityLog.class)
					.add(Restrictions.eq("volId.id", volunteer))
					.add(Restrictions.eq("sm.id", studyid))
					.add(Restrictions.eq("subjectId.id", subjectid))
					.add(Restrictions.between("activityDoneOn", fdate, tdate))
					.addOrder(Order.asc("id")).list();
		}else {
			acList= getSession().createCriteria(ActivityLog.class)
					.add(Restrictions.between("activityDoneOn", fdate, tdate))
					.addOrder(Order.asc("id")).list();
		}
		return acList;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudySubjects> getSubjetDataWithStudyId(Long studyid) {
		return getSession().createCriteria(StudySubjects.class)
				.add(Restrictions.eq("study.id", studyid)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyVolunteerReporting> getVolunteerDataWithStudyId(Long studyid) {
		return getSession().createCriteria(StudyVolunteerReporting.class)
				.add(Restrictions.eq("sm.id", studyid)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RolesWiseModules> getRoleWiseListWithRoleId(Long roleid) {
		List<RolesWiseModules> list=null;
		list=(List<RolesWiseModules>) getSession().createCriteria(RolesWiseModules.class)
				.add(Restrictions.eq("role.id", roleid)).list();
		return list;
	}

	@Override
	public long getSidemenusCount() {
		return (long) getSession().createCriteria(ApplictionSideMenus.class)
				.setProjection(Projections.count("id")).uniqueResult();
	}


}
