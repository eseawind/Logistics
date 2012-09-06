package Logistics.DTO;

import java.sql.Date;

import Logistics.Common.Tools;

public class StockoutItemDTO {
	Integer stockoutManifestID;
	Integer itemID;
	String batch;
	int amount;
	String itemName;
	String itemNumber;
	double unitWeight;
	double unitVolume;
	double weight;
	double volume;
	Date dateStockin;
	Date lastAccountDate;
	public void copyFrom(ItemDTO item){
		if(item==null)
			return ;
		this.itemID=item.getItemID();
		this.batch=item.getBatch();
		this.itemName=item.getName();
		this.itemNumber=item.getNumber();
		this.unitWeight=item.getUnitWeight();
		this.unitVolume=item.getUnitVolume();
		this.weight=amount*unitWeight;
		this.volume=amount*unitVolume;
	}
	
	public Date getLastAccountDate() {
		return lastAccountDate;
	}

	public void setLastAccountDate(Date lastAccountDate) {
		this.lastAccountDate = lastAccountDate;
	}
	public void setLastAccountDate(String s) {
		this.lastAccountDate = Tools.isVoid(s)?null:Date.valueOf(s);
	}

	public Date getDateStockin() {
		return dateStockin;
	}
	public void setDateStockin(Date dateStockin) {
		this.dateStockin = dateStockin;
	}
	public void setDateStockin(String s) {
		this.dateStockin = Tools.isVoid(s)?null:Date.valueOf(s);
	}
	public Integer getStockoutManifestID() {
		return stockoutManifestID;
	}
	public void setStockoutManifestID(Integer stockoutManifestID) {
		this.stockoutManifestID = stockoutManifestID;
	}
	public Integer getItemID() {
		return itemID;
	}
	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}
	
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public double getUnitWeight() {
		return unitWeight;
	}
	public void setUnitWeight(double unitWeight) {
		this.unitWeight = unitWeight;
	}
	public double getUnitVolume() {
		return unitVolume;
	}
	public void setUnitVolume(double unitVolume) {
		this.unitVolume = unitVolume;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
}
