package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;

@SuppressWarnings("serial")
public class DefaulltActparamDto implements Serializable {
	
	private GlobalActivity ga;
	private List<GlobalParameter> gpList;
	public GlobalActivity getGa() {
		return ga;
	}
	public void setGa(GlobalActivity ga) {
		this.ga = ga;
	}
	public List<GlobalParameter> getGpList() {
		return gpList;
	}
	public void setGpList(List<GlobalParameter> gpList) {
		this.gpList = gpList;
	}
	
	

}
