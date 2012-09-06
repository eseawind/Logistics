package Logistics.DTO;

import java.sql.Date;

import Logistics.Common.Tools;

public class StockoutManifestDTO {
	
	Integer stockoutManifestID;
	String warehouseID;
	int sumAmount;
	String customer;
	String warehouseName;
	String remarks;
	String chargeMode;
	String costCenter;
	String sellCenter;
	Date dateCreated;
	Date dateStockout;
	double unitPrice;
	double stockoutFee;
	double sumVolume;
	double sumWeight;
	double loadUnloadCost;
	String approvalState;
	String auditState;
	String consignee;
	String consigneeCompany;
	String consigneePhone;
	String consigneeAddress;
	Integer customerID;
	
	public Integer getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}
	public Integer getStockoutManifestID() {
		return stockoutManifestID;
	}
	public void setStockoutManifestID(Integer stockoutManifestID) {
		this.stockoutManifestID = stockoutManifestID;
	}
	
	public String getWarehouseID() {
		return warehouseID;
	}
	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}
	public int getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(int sumAmount) {
		this.sumAmount = sumAmount;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getChargeMode() {
		return chargeMode;
	}
	public void setChargeMode(String chargeMode) {
		this.chargeMode = chargeMode;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getSellCenter() {
		return sellCenter;
	}
	public void setSellCenter(String sellCenter) {
		this.sellCenter = sellCenter;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getDateStockout() {
		return dateStockout;
	}
	public void setDateStockout(Date dateStockout) {
		this.dateStockout = dateStockout;
	}
	public void setDateStockout(String s) {
		this.dateStockout = Tools.isVoid(s)?null:Date.valueOf(s);
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getStockoutFee() {
		return stockoutFee;
	}
	public void setStockoutFee(double stockoutFee) {
		this.stockoutFee = stockoutFee;
	}
	public double getSumVolume() {
		return sumVolume;
	}
	public void setSumVolume(double sumVolume) {
		this.sumVolume = sumVolume;
	}
	public double getSumWeight() {
		return sumWeight;
	}
	public void setSumWeight(double sumWeight) {
		this.sumWeight = sumWeight;
	}
	public double getLoadUnloadCost() {
		return loadUnloadCost;
	}
	public void setLoadUnloadCost(double loadUnloadCost) {
		this.loadUnloadCost = loadUnloadCost;
	}
	public String getApprovalState() {
		return approvalState;
	}
	public void setApprovalState(String approvalState) {
		this.approvalState = approvalState;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getConsigneeCompany() {
		return consigneeCompany;
	}
	public void setConsigneeCompany(String consigneeCompany) {
		this.consigneeCompany = consigneeCompany;
	}
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	
	
	
	

}
