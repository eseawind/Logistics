package Logistics.DTO;

public class StockinItemDTO {
	Integer stockinManifestID;
	Integer itemID;
	String batch;
	int amount;
	String itemName;
	String itemNumber;
	double unitWeight;
	double unitVolume;
	double weight;
	double volume;
	//新增
	int expectedAmount;
	String label;
	String isSN;
	String remarks;
	String unit;
	public void copyFrom(ItemDTO item){
		if(item==null)
			return ;
		this.itemID=item.getItemID();
		this.batch=item.getBatch();
		this.itemName=item.getName();
		this.itemNumber=item.getNumber();
		this.unitWeight=item.getUnitWeight();
		this.unitVolume=item.getUnitVolume();
		this.unit=item.getUnit();
		this.weight=amount*unitWeight;
		this.volume=amount*unitVolume;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public Integer getStockinManifestID() {
		return stockinManifestID;
	}
	public void setStockinManifestID(Integer stockinManifestID) {
		this.stockinManifestID = stockinManifestID;
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
	public int getExpectedAmount() {
		return expectedAmount;
	}
	public void setExpectedAmount(int expectedAmount) {
		this.expectedAmount = expectedAmount;
	}
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getIsSN() {
		return isSN;
	}
	public void setIsSN(String isSN) {
		this.isSN = isSN;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
