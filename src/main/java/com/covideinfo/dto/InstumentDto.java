package com.covideinfo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.covideinfo.model.Instrument;

public class InstumentDto implements Serializable{
	private List<Instrument> instruments = new ArrayList<>();

	public List<Instrument> getInstruments() {
		return instruments;
	}

	public void setInstruments(List<Instrument> instruments) {
		this.instruments = instruments;
	}
	 
}
