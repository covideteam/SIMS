package com.covideinfo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.UnitsDao;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.UnitsMaster;
import com.covideinfo.service.UnitService;

@Service("unitService")
public class UnitServiceImpl implements UnitService {

	@Autowired
	UnitsDao unitsDao;

	@Override
	public List<InternationalizaionLanguages> getLanguagesList() {
		return unitsDao.getLanguagesList();
	}

	@Override
	public UnitsMaster checkUnitsName(String name) {
		return unitsDao.checkUnitsName(name);
	}

	@Override
	public String saveUnitsDetails(UnitsMaster units, String username) {
		UnitsMaster oldUnit = unitsDao.checkUnitsName(units.getUnitsCode());
		if(oldUnit==null) {
			/*String ds = units.getDisplayString();
			if(ds.contains("<sup>") && ds.contains("<sub>")) {
				units = generateBothScripts(units);
			}else {
				if(ds.contains("<sup>")) {
				    units = generateSuperScriptString(units);
				}else if(ds.contains("<sub>")) {
					units = generateSubScriptString(units);
				}	
			}*/
			units.setCreatedBy(username);
			units.setCreatedOn(new Date());
			boolean flag = unitsDao.saveUnitDetails(units);
			if(flag)
				return "success";
			else
				return "failed";
		}else {
			return "exist";
		}
	}
	
	/*private UnitsMaster generateSubScriptString(UnitsMaster unitEntity) {
		String ds = unitEntity.getDisplayString();
	    if(ds.contains("##")) {
	    	String mainBase = "";
	    	String mainSub ="";
	    	String finalString ="";
	    	String[] temp2 = ds.split("##");
	    	if(temp2.length > 0) {
	    		for(int k=0; k<temp2.length; k++) {
	    			if(temp2[k].contains("<sub>")) {
	    				String[] temp = temp2[k].split("<sub>");
						if(temp.length > 0) {
							for(int i=0; i<(temp.length-1); i++) {
								String base = temp[i];
							    String sub = temp[i+1];
							    if(mainBase.equals(""))
							    	mainBase = base;
							    else mainBase = mainBase+"##"+base;
							    boolean flag = convertInteger(sub.trim());
								String subStr = "";
								if(flag) {
									subStr = getSubString(sub.trim());
									if(finalString.equals(""))
										finalString = base+subStr;
									else finalString = finalString +" "+base+subStr;
									if(mainSub.equals(""))
										mainSub = sub;
									else mainSub = mainSub+"##"+sub;
								}
							}
						}
	    			}else {
	    				if(finalString.equals(""))
							finalString = temp2[k];
						else finalString = finalString +" "+temp2[k];
	    				if(mainSub.equals(""))
							mainSub = temp2[k];
						else mainSub = mainSub+"##"+temp2[k];
	    			}
	    			
	    		}
	    		unitEntity.setDisplayString(finalString);
				unitEntity.setSupBase(mainBase);
				unitEntity.setSub(mainSub);
	    	}
	    }else {
	    	String[] temp = ds.split("<sub>");
			if(temp.length > 0) {
				for(int i=0; i<(temp.length-1); i++) {
					String base = temp[i];
				    String sub = temp[i+1];
				    boolean flag = convertInteger(sub.trim());
					String subStr = "";
					if(flag) {
						subStr = getSubString(sub.trim());
						unitEntity.setDisplayString(base+subStr);
						unitEntity.setSupBase(base);
						unitEntity.setSub(sub);
					}
				}
			}
	    }
		
		
		return unitEntity;
	}*/

	/*private UnitsMaster generateSuperScriptString(UnitsMaster unitEntity) {
		String ds = unitEntity.getDisplayString();
		if(ds.contains("##")) {
			String[] temp2 = ds.split("##");
			String mainBase ="";
			String mainPower ="";
			String finalStr ="";
			
			if(temp2.length > 0) {
				for(int l =0; l< temp2.length; l++) {
					String[] temp = temp2[l].split("<sup>");
					if(temp2[l].contains("<sup>")) {
						if(temp.length > 0) {
							for(int i=0; i<(temp.length-1); i++) {
								String base = temp[i];
							    String power = temp[i+1];
							    boolean flag = convertInteger(power.trim());
								String powerStr = "";
								if(mainPower.equals(""))
									mainPower = power;
								else mainPower = mainPower +"##"+power;
								if(mainBase.equals(""))
									mainBase = base;
								else mainBase = mainBase+"##"+base;
								if(flag) {
									powerStr = getPowerString(power.trim());
									if(finalStr.equals(""))
										finalStr = base+powerStr;
									else finalStr = finalStr+" "+base+powerStr;
								}
							}
						}
					}else {
							if(finalStr.equals(""))
								finalStr = temp2[l];
							else finalStr = finalStr +" "+temp2[l];
							if(mainBase.equals(""))
								mainBase = temp2[l];
							else mainBase = mainBase+"##"+temp2[l];
						
					}
					
				}
				unitEntity.setDisplayString(finalStr);
				unitEntity.setSupBase(mainBase);
				unitEntity.setPower(mainPower);
			}
		}else {
			String[] temp = ds.split("<sup>");
			if(temp.length > 0) {
				for(int i=0; i<(temp.length-1); i++) {
					String base = temp[i];
				    String power = temp[i+1];
				    boolean flag = convertInteger(power.trim());
					String powerStr = "";
					if(flag) {
						powerStr = getPowerString(power.trim());
						unitEntity.setDisplayString(base+powerStr);
						unitEntity.setSupBase(base);
						unitEntity.setPower(power);
					}
				}
			}
		}
		return unitEntity;
	}*/

	private String getPowerString(String power) {
		String powerStr ="";
		for(int j=0; j<power.length(); j++) {
			char ch = power.charAt(j);
			int no = Integer.parseInt(ch+"");
			if(no == 0) {
				if(powerStr.equals(""))
					powerStr = "&#x2070";
				else 
					powerStr = powerStr+"&#x2070";
			}else if(no == 1) {
				if(powerStr.equals(""))
					powerStr = "&#x00B9";
				else 
					powerStr = powerStr+"&#x00B9";
			}else if(no == 2) {
				if(powerStr.equals(""))
					powerStr = "&#x00B2";
				else 
					powerStr = powerStr+"&#x00B2";
			}else if(no == 3) {
				if(powerStr.equals(""))
					powerStr = "&#x00B3";
				else 
					powerStr = powerStr+"&#x00B3";
			}else if(no == 4) {
				if(powerStr.equals(""))
					powerStr = "&#x2074";
				else 
					powerStr = powerStr+"&#x2074";
			}else if(no == 5) {
				if(powerStr.equals(""))
					powerStr = "&#x2075";
				else 
					powerStr = powerStr+"&#x2075";
			}else if(no == 6) {
				if(powerStr.equals(""))
					powerStr ="&#x2076";
				else 
					powerStr = powerStr+"&#x2076";
			}else if(no == 7) {
				if(powerStr.equals(""))
					powerStr = "&#x2077";
				else 
					powerStr = powerStr+"&#x2077";
			}else if(no == 8) {
				if(powerStr.equals(""))
					powerStr ="&#x2078";
				else 
					powerStr = powerStr+"&#x2078";
			}else if(no == 9) {
				if(powerStr.equals(""))
					powerStr = "&#x2079";
				else 
					powerStr = powerStr+"&#x2079";
			}
		}
		return powerStr;
	}

	/*private UnitsMaster generateBothScripts(UnitsMaster unitEntity) {
		String ds = unitEntity.getDisplayString();
		String[] temp = ds.split("##");
		String finalBase ="";
		String finalDisStr ="";
		String finalPower ="";
		String finalSub ="";
		if(temp.length > 0) {
			for(int i=0; i<temp.length; i++) {
				if(temp[i].contains("<sup>")) {
					String st = temp[i];
					unitEntity = genarateSuperScript(unitEntity, st);
					if(finalBase.equals(""))
						finalBase = unitEntity.getSupBase();
					else finalBase = finalBase +"##"+unitEntity.getSupBase();
					if(finalDisStr.equals(""))
						finalDisStr = unitEntity.getDisplayString();
					else finalDisStr = finalDisStr +" "+unitEntity.getDisplayString();
					if(finalPower.equals(""))
						finalPower = unitEntity.getPower();
					else finalPower = finalPower +"##"+ unitEntity.getPower();
				}else if(temp[i].contains("<sub>")) {
					String st = temp[i];
					unitEntity = genarateSubScript(unitEntity, st);
					if(finalBase.equals(""))
						finalBase = unitEntity.getSupBase();
					else finalBase = finalBase +"##"+unitEntity.getSupBase();
					if(finalDisStr.equals(""))
						finalDisStr = unitEntity.getDisplayString();
					else finalDisStr = finalDisStr +" "+unitEntity.getDisplayString();
					if(finalSub.equals(""))
						finalSub = unitEntity.getSub();
					else finalSub = finalSub +"##"+ unitEntity.getSub();
					
				}else {
					if(finalDisStr.equals(""))
						finalDisStr = temp[i];
					else finalDisStr = finalDisStr +" "+temp[i];
				}
			}
			unitEntity.setSupBase(finalBase);
			unitEntity.setPower(finalPower);
			unitEntity.setDisplayString(finalDisStr);
			unitEntity.setSub(finalSub);
		}
		return unitEntity;
	}*/



	/*private UnitsMaster genarateSuperScript(UnitsMaster unitEntity, String st) {
		String[] temp = st.split("<sup>");
		if(temp.length > 0) {
			for(int i=0; i<(temp.length-1); i++) {
				String base = temp[i];
			    String power = temp[i+1];
			    boolean flag = convertInteger(power.trim());
				String powerStr = "";
				if(flag) {
					powerStr = getPowerString(power.trim());
					unitEntity.setDisplayString(base+powerStr);
					unitEntity.setSupBase(base);
					unitEntity.setPower(power);
					String order = unitEntity.getScriptOrder();
					if(order.equals(""))
						unitEntity.setScriptOrder("P");
					else unitEntity.setScriptOrder(order+"##"+"P");
				}
			}
		}
		return unitEntity;
	}*/

	/*private boolean convertInteger(String power) {
		try {
			int no = Integer.parseInt(power);
			return true;
		} catch (Exception e) {
			System.out.println("String is not Numaric");
			return false;
		}
	}*/

	/*private UnitsMaster genarateSubScript(UnitsMaster unitEntity, String st) {
		String[] temp = st.split("<sub>");
		if(temp.length > 0) {
			for(int i=0; i<(temp.length-1); i++) {
				String base = temp[i];
			    String sub = temp[i+1];
			    boolean flag = convertInteger(sub.trim());
				String subStr = "";
				if(flag) {
					subStr = getSubString(sub.trim());
					unitEntity.setDisplayString(base+subStr);
					unitEntity.setSupBase(base);
					unitEntity.setSub(sub);
					String order = unitEntity.getScriptOrder();
					if(order.equals(""))
					    unitEntity.setScriptOrder("S");
					else unitEntity.setScriptOrder(order+"##"+"S");
				}
				
			}
		}
		return unitEntity;
				
	}*/

	private String getSubString(String sub) {
		String subStr ="";
		for(int j=0; j<sub.length(); j++) {
			char ch = sub.charAt(j);
			int no = Integer.parseInt(ch+"");
			if(no == 0) {
				if(subStr.equals(""))
					subStr = "&#x2080";
				else 
					subStr = subStr+"&#x2080";
			}else if(no == 1) {
				if(subStr.equals(""))
					subStr = "&#x2081";
				else 
					subStr = subStr+"&#x2081";
			}else if(no == 2) {
				if(subStr.equals(""))
					subStr = "&#x2082";
				else 
					subStr = subStr+"&#x2082";
			}else if(no == 3) {
				if(subStr.equals(""))
					subStr = "&#x2083";
				else 
					subStr = subStr+"&#x2083";
			}else if(no == 4) {
				if(subStr.equals(""))
					subStr = "&#x2084";
				else 
					subStr = subStr+"&#x2084";
			}else if(no == 5) {
				if(subStr.equals(""))
					subStr = "&#x2085";
				else 
					subStr = subStr+"&#x2085";
			}else if(no == 6) {
				if(subStr.equals(""))
					subStr = "&#x2086";
				else 
					subStr = subStr+"&#x2086";
			}else if(no == 7) {
				if(subStr.equals(""))
					subStr = "&#x2087";
				else 
					subStr = subStr+"&#x2087";
			}else if(no == 8) {
				if(subStr.equals(""))
					subStr ="&#x2088";
				else 
					subStr = subStr+"&#x2088";
			}else if(no == 9) {
				if(subStr.equals(""))
					subStr = "&#x2089";
				else 
					subStr = subStr+"&#x2089";
			}
		}
		return subStr;
	}

	@Override
	public List<UnitsMaster> geUnitsMasterList() {
		return unitsDao.geUnitsMasterList();
	}
	
}
