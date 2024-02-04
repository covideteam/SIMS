package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.ProjectsDetails;

@SuppressWarnings("serial")
public class DosingInfoDto implements Serializable {

	private  List<ProjectsDetails> pdtpmList;

	private  List<ProjectsDetails> pdttmList;
	
	List<ProjectsDetails> vtpmList;
	List<ProjectsDetails> vttmList;
	
	List<ProjectsDetails> mtpmList ;
	List<ProjectsDetails> mttmList;
	
	List<ProjectsDetails> etpmList;
	List<ProjectsDetails> ettmList;

	public List<ProjectsDetails> getPdtpmList() {
		return pdtpmList;
	}

	public void setPdtpmList(List<ProjectsDetails> pdtpmList) {
		this.pdtpmList = pdtpmList;
	}

	public List<ProjectsDetails> getPdttmList() {
		return pdttmList;
	}

	public void setPdttmList(List<ProjectsDetails> pdttmList) {
		this.pdttmList = pdttmList;
	}

	public List<ProjectsDetails> getVtpmList() {
		return vtpmList;
	}

	public void setVtpmList(List<ProjectsDetails> vtpmList) {
		this.vtpmList = vtpmList;
	}

	public List<ProjectsDetails> getVttmList() {
		return vttmList;
	}

	public void setVttmList(List<ProjectsDetails> vttmList) {
		this.vttmList = vttmList;
	}

	public List<ProjectsDetails> getMtpmList() {
		return mtpmList;
	}

	public void setMtpmList(List<ProjectsDetails> mtpmList) {
		this.mtpmList = mtpmList;
	}

	public List<ProjectsDetails> getMttmList() {
		return mttmList;
	}

	public void setMttmList(List<ProjectsDetails> mttmList) {
		this.mttmList = mttmList;
	}

	public List<ProjectsDetails> getEtpmList() {
		return etpmList;
	}

	public void setEtpmList(List<ProjectsDetails> etpmList) {
		this.etpmList = etpmList;
	}

	public List<ProjectsDetails> getEttmList() {
		return ettmList;
	}

	public void setEttmList(List<ProjectsDetails> ettmList) {
		this.ettmList = ettmList;
	}
	
	
	
}
