package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.InternationalizaionLanguages;

@SuppressWarnings("serial")
public class RulesDto implements Serializable {
	
	private GlobalActivity sourceActivity;
	private GlobalParameter sourceParameter;
	private List<InternationalizaionLanguages> inlagList;
	private GlobalActivity destActivity;
	private List<GlobalParameter> gpList;
	private GlobalParameter destParm;
	private List<GlobalActivity> gaList;
	public GlobalActivity getSourceActivity() {
		return sourceActivity;
	}
	public void setSourceActivity(GlobalActivity sourceActivity) {
		this.sourceActivity = sourceActivity;
	}
	public GlobalParameter getSourceParameter() {
		return sourceParameter;
	}
	public void setSourceParameter(GlobalParameter sourceParameter) {
		this.sourceParameter = sourceParameter;
	}
	public List<InternationalizaionLanguages> getInlagList() {
		return inlagList;
	}
	public void setInlagList(List<InternationalizaionLanguages> inlagList) {
		this.inlagList = inlagList;
	}
	public GlobalActivity getDestActivity() {
		return destActivity;
	}
	public void setDestActivity(GlobalActivity destActivity) {
		this.destActivity = destActivity;
	}
	public List<GlobalParameter> getGpList() {
		return gpList;
	}
	public void setGpList(List<GlobalParameter> gpList) {
		this.gpList = gpList;
	}
	public GlobalParameter getDestParm() {
		return destParm;
	}
	public void setDestParm(GlobalParameter destParm) {
		this.destParm = destParm;
	}
	public List<GlobalActivity> getGaList() {
		return gaList;
	}
	public void setGaList(List<GlobalActivity> gaList) {
		this.gaList = gaList;
	}
	
	
	

}
