package Logistics.DTO;

import jxl.write.Label;
import Logistics.Common.DtoToExcel;

public class BarcodeDTO implements DtoToExcel{
	private Integer barcodeID;
	private String itemNumber;
	private String itemName;
	private String batch;
	private String amount;
	private String operationType;
	private String manifestID;
	private String customerID;
	private String customer;
	private String warehouseID;
	private String warehouse;
	private String barcode;
	private String itemID;
	private String remarks;
	
	public int getColn() {
		return 14;
	}

	public boolean toCellName(Label[] cells) {
		if(cells.length!=this.getColn()){
			return false;
		}
		cells[0].setString("条码编号");
		cells[1].setString("物料代码");
		cells[2].setString("物料名称");
		cells[3].setString("批次");
		cells[4].setString("数量");
		cells[5].setString("操作类型");
		cells[6].setString("单号");
		cells[7].setString("客户编号");
		cells[8].setString("客户");
		cells[9].setString("仓库编号");
		cells[10].setString("仓库");
		cells[11].setString("条码值");
		cells[12].setString("物料编号");
		cells[13].setString("备注");
		
		return true;
	}
	public boolean toCellValue(Label []cells){
		if(cells.length!=this.getColn())
			return false;
		cells[0].setString(""+this.barcodeID);
		cells[1].setString(this.itemNumber);
		cells[2].setString(this.itemName);
		cells[3].setString(this.batch);
		cells[4].setString(this.amount);
		cells[5].setString(this.operationType);
		cells[6].setString(this.manifestID);
		cells[7].setString(this.customerID);
		cells[8].setString(this.customer);
		cells[9].setString(this.warehouseID);
		cells[10].setString(this.warehouse);
		cells[11].setString(this.barcode);
		cells[12].setString(this.itemID);
		cells[13].setString(this.remarks);
		return true;
	}
	
	public Integer getBarcodeID() {
		return barcodeID;
	}
	public void setBarcodeID(Integer barcodeID) {
		this.barcodeID = barcodeID;
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getManifestID() {
		return manifestID;
	}
	public void setManifestID(String manifestID) {
		this.manifestID = manifestID;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getWarehouseID() {
		return warehouseID;
	}
	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	

}
