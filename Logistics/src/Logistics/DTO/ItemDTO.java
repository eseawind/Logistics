package Logistics.DTO;

public class ItemDTO {

	private Integer itemID;
	private String number;
	private String name;
	private String batch;
	private String unit;
	private double unitVolume;
	private double unitWeight;
	private String remarks;
	
	public String jointName(){
		return name+"("+this.itemID+")"+number;
	}
	public Integer getItemID() {
		return itemID;
	}
	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public double getUnitVolume() {
		return unitVolume;
	}
	public void setUnitVolume(double unitVolume) {
		this.unitVolume = unitVolume;
	}
	public double getUnitWeight() {
		return unitWeight;
	}
	public void setUnitWeight(double unitWeight) {
		this.unitWeight = unitWeight;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
