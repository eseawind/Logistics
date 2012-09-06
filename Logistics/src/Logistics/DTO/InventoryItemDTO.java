package Logistics.DTO;

import java.sql.Date;

public class InventoryItemDTO {
	Integer inventoryManifestID;
	Integer itemID;
	String itemNumber;
	String itemName;
	String batch;
	String unit;
	int amountRecorded;
	int amountExisted;
	int amountDifference;
	String differenceRemarks;
	public void copyFrom(ItemDTO item){
		if(item==null)
			return ;
		itemID=item.getItemID();
		batch=item.getBatch();
		itemName=item.getName();
		itemNumber=item.getNumber();
		unit=item.getUnit();
	}
	public Integer getInventoryManifestID() {
		return inventoryManifestID;
	}
	public void setInventoryManifestID(Integer inventoryManifestID) {
		this.inventoryManifestID = inventoryManifestID;
	}
	public Integer getItemID() {
		return itemID;
	}
	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getAmountRecorded() {
		return amountRecorded;
	}
	public void setAmountRecorded(int amountRecorded) {
		this.amountRecorded = amountRecorded;
	}
	public int getAmountExisted() {
		return amountExisted;
	}
	public void setAmountExisted(int amountExisted) {
		this.amountExisted = amountExisted;
	}
	public int getAmountDifference() {
		return amountDifference;
	}
	public void setAmountDifference(int amountDifference) {
		this.amountDifference = amountDifference;
	}
	public String getDifferenceRemarks() {
		return differenceRemarks;
	}
	public void setDifferenceRemarks(String differenceRemarks) {
		this.differenceRemarks = differenceRemarks;
	}
	
	
	
}
