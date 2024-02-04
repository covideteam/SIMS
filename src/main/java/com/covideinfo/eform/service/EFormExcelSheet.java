package com.covideinfo.eform.service;

import java.util.List;

public class EFormExcelSheet {

	private List<EFormSheet> eformSheet;
	private List<EFormSectionSheet> eformSectinSheet;
	private List<EFormGroupSheet> eformGroupSheet;
	private List<EFormSectionElementSheet> eformSectinElementSheet;
	private List<EFormGroupElementSheet> eformgroupElementSheet;
	
	
	public List<EFormSheet> getEformSheet() {
		return eformSheet;
	}


	public void setEformSheet(List<EFormSheet> eformSheet) {
		this.eformSheet = eformSheet;
	}


	public List<EFormSectionSheet> getEformSectinSheet() {
		return eformSectinSheet;
	}


	public void setEformSectinSheet(List<EFormSectionSheet> eformSectinSheet) {
		this.eformSectinSheet = eformSectinSheet;
	}


	public List<EFormGroupSheet> getEformGroupSheet() {
		return eformGroupSheet;
	}


	public void setEformGroupSheet(List<EFormGroupSheet> eformGroupSheet) {
		this.eformGroupSheet = eformGroupSheet;
	}


	public List<EFormSectionElementSheet> getEformSectinElementSheet() {
		return eformSectinElementSheet;
	}


	public void setEformSectinElementSheet(List<EFormSectionElementSheet> eformSectinElementSheet) {
		this.eformSectinElementSheet = eformSectinElementSheet;
	}


	public List<EFormGroupElementSheet> getEformgroupElementSheet() {
		return eformgroupElementSheet;
	}


	public void setEformgroupElementSheet(List<EFormGroupElementSheet> eformgroupElementSheet) {
		this.eformgroupElementSheet = eformgroupElementSheet;
	}


	public void setCrfgroupElementSheet(List<EFormGroupElementSheet> crfgroupElementSheet) {
		this.eformgroupElementSheet = crfgroupElementSheet;
		
	}


	public void setCrfSectinElementSheet(List<EFormSectionElementSheet> eles) {
		this.eformSectinElementSheet = eles;
		
	}


	public void setCrfSectinSheet(List<EFormSectionSheet> crfSheet) {
		this.eformSectinSheet = crfSheet;
		
	}


	public void setCrfGroupSheet(List<EFormGroupSheet> crfGroupSheet) {
		this.eformGroupSheet = crfGroupSheet;
		
	}


	
	
	
	
	
}
