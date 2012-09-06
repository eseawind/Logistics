package Logistics.DTO;

import java.sql.Date;

public class InventoryManifestDTO {
	
	Integer inventoryManifestID;
	String warehouseID;
	String warehouseName;
	Date dateInventoried;
	String inventoryPerson;
	String type;
	String result;
	public Integer getInventoryManifestID() {
		return inventoryManifestID;
	}
	public void setInventoryManifestID(Integer inventoryManifestID) {
		this.inventoryManifestID = inventoryManifestID;
	}
	
	public String getWarehouseID() {
		return warehouseID;
	}
	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public Date getDateInventoried() {
		return dateInventoried;
	}
	public void setDateInventoried(Date dateInventoried) {
		this.dateInventoried = dateInventoried;
	}
	public String getInventoryPerson() {
		return inventoryPerson;
	}
	public void setInventoryPerson(String inventoryPerson) {
		this.inventoryPerson = inventoryPerson;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
}
