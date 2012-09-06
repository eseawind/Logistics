package Logistics.DTO;

import java.sql.Date;

import Logistics.Common.Tools;

public class StockTransferManifestDTO {
	
	Integer stockTransferManifestID;
	int sumAmount;
	String remarks;
	String costCenter;
	private Date dateCreated;
	private Date dateTransfered;
	double sumVolume;
	double sumWeight;
	double loadUnloadCost;
	String approvalState;
	String auditState;
	String outWarehouse;
	String outWarehouseID;
	String inWarehouse;
	String inWarehouseID;
	String operator;
	

	
	
	public Integer getStockTransferManifestID() {
		return stockTransferManifestID;
	}
	public void setStockTransferManifestID(Integer stockTransferManifestID) {
		this.stockTransferManifestID = stockTransferManifestID;
	}
	public int getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(int sumAmount) {
		this.sumAmount = sumAmount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		System.out.println("set string");
	}
	
	public Date getDateTransfered() {
		return dateTransfered;
	}
	public void setDateTransfered(Date dateTransfered) {
		this.dateTransfered = dateTransfered;
	}
	public void setDateTransfered(String dateTransfered) {
		this.dateTransfered=Date.valueOf(dateTransfered);
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
	public String getOutWarehouse() {
		return outWarehouse;
	}
	public void setOutWarehouse(String outWarehouse) {
		this.outWarehouse = outWarehouse;
	}
	
	public String getOutWarehouseID() {
		return outWarehouseID;
	}
	public void setOutWarehouseID(String outWarehouseID) {
		this.outWarehouseID = outWarehouseID;
	}
	public String getInWarehouseID() {
		return inWarehouseID;
	}
	public void setInWarehouseID(String inWarehouseID) {
		this.inWarehouseID = inWarehouseID;
	}
	public String getInWarehouse() {
		return inWarehouse;
	}
	public void setInWarehouse(String inWarehouse) {
		this.inWarehouse = inWarehouse;
	}

	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
