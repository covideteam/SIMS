package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

public class MealsDetailsDto implements Serializable {

	private List<MealsDataDto> mealDataMap; //timepoint, Dtainformation
	public List<MealsDataDto> getMealDataMap() {
		return mealDataMap;
	}
	public void setMealDataMap(List<MealsDataDto> mealDataMap) {
		this.mealDataMap = mealDataMap;
	}
}
