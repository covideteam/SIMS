package com.covideinfo.dto;

import java.io.Serializable;

public class MealDietPlanItemDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7451078213628571764L;
	private Long id;
	private String itemName;
	private Double quantity;
	private Double totalCalries;
	private Long mealDietplanId;
	private String unitsCode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getTotalCalries() {
		return totalCalries;
	}
	public void setTotalCalries(Double totalCalries) {
		this.totalCalries = totalCalries;
	}
	
	public String getUnitsCode() {
		return unitsCode;
	}
	public void setUnitsCode(String unitsCode) {
		this.unitsCode = unitsCode;
	}
	public Long getMealDietplanId() {
		return mealDietplanId;
	}
	public void setMealDietplanId(Long mealDietplanId) {
		this.mealDietplanId = mealDietplanId;
	}
	
	
	

}
