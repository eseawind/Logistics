package Logistics.DTO;

import java.sql.Date;
import java.util.ArrayList;

import jxl.write.Label;

import Logistics.Common.DtoToExcel;
import Logistics.Common.Tools;

public class StockinManifestDTO{
	
	Integer stockinManifestID;
	String warehouseID;
	int sumAmount;
	String customer;
	String warehouseName;
	String deliveryPerson;
	String remarks;
	String chargeMode;
	String costCenter;
	String sellCenter;
	Date dateCreated;
	Date dateStockin;
	double unitPrice;
	double stockinFee;
	double sumVolume;
	double sumWeight;
	double loadUnloadCost;
	String approvalState;
	String auditState;
	String deliveryPhone;
	String takingAddress;
	Integer customerID;
	//新加入的
	String customerNumber;
	String originAgent;
	String originAddress;
	String originPerson;
	String originPhone;
	public ArrayList<StockinItemDTO> stockinItems=null;
	
	
	
	public String replaceInATable(String os,int currPageN,int pageAmount,int itemAmount){
		if(os==null)
			return null;
		
		for(int i=0;i<itemAmount;i++){
			int index=(currPageN-1)*itemAmount+i;
			if(stockinItems ==null || index>=stockinItems.size()){
				os=os.replace("__itid_"+i+"__", "");
				os=os.replace("__itnam_"+i+"__", "");
				os=os.replace("__itnum_"+i+"__", "");
				os=os.replace("__unit_"+i+"__", "");
				os=os.replace("__exam_"+i+"__", "");
				os=os.replace("__amt_"+i+"__", "");
				os=os.replace("__batch_"+i+"__", "");
				os=os.replace("__uvol_"+i+"__", "");
				os=os.replace("__uwt_"+i+"__", "");
				os=os.replace("__lbl_"+i+"__", "");
				os=os.replace("__isSN_"+i+"__", "");
				os=os.replace("__rmk_"+i+"__", "");
			}
			else{
				StockinItemDTO it=stockinItems.get(index);
				os=os.replace("__itid_"+i+"__", Tools.ts(it.getItemID()));
				os=os.replace("__itnam_"+i+"__", ""+Tools.ts(it.getItemName(),20));
				os=os.replace("__itnum_"+i+"__", ""+Tools.ts(it.getItemNumber()));
				os=os.replace("__unit_"+i+"__", ""+Tools.ts(it.getUnit()));
				os=os.replace("__exam_"+i+"__", ""+Tools.ts(it.getExpectedAmount()));
				os=os.replace("__amt_"+i+"__", ""+Tools.ts(it.getAmount()));
				os=os.replace("__batch_"+i+"__", ""+Tools.ts(it.getBatch()));
				os=os.replace("__uvol_"+i+"__", ""+Tools.ts(it.getUnitVolume()));
				os=os.replace("__uwt_"+i+"__", ""+Tools.ts(it.getUnitWeight()));
				os=os.replace("__lbl_"+i+"__", ""+Tools.ts(it.getLabel()));
				os=os.replace("__isSN_"+i+"__", ""+Tools.ts(it.getIsSN()));
				os=os.replace("__rmk_"+i+"__", ""+Tools.ts(it.getRemarks()));
			}
			
			
		}
		os=os.replace("__page__", "第"+currPageN+"/"+pageAmount+"页");
		os=os.replace("__simid__", ""+Tools.ts(this.stockinManifestID));
		os=os.replace("__cusnum__", ""+Tools.ts(this.customerNumber));
		os=os.replace("__datcre__", ""+Tools.ts(this.dateCreated));
		os=os.replace("__datsti__", ""+Tools.ts(this.dateStockin));
		os=os.replace("__datprt__", ""+Tools.ts(Tools.currDate()));
		os=os.replace("__cusnam__", ""+Tools.ts(this.customer));
		os=os.replace("__cusid__", ""+Tools.ts(this.customerID));
		os=os.replace("__orgagt__", ""+Tools.ts(this.originAgent));
		os=os.replace("__orgadd__", ""+Tools.ts(this.originAddress));
		os=os.replace("__orgprs__", ""+Tools.ts(this.originPerson));
		os=os.replace("__orgph__", ""+Tools.ts(this.originPhone));
		os=os.replace("__dlvprs__", ""+Tools.ts(this.deliveryPerson));
		os=os.replace("__dlvph__", ""+Tools.ts(this.deliveryPhone));
		os=os.replace("__whnm__", ""+Tools.ts(this.warehouseName));
		os=os.replace("__whid__", ""+Tools.ts(this.warehouseID));
		os=os.replace("__sumamt__", ""+Tools.ts(this.sumAmount));
		os=os.replace("__sumvol__", ""+Tools.ts(this.sumVolume));
		os=os.replace("__sumwgt__", ""+Tools.ts(this.sumWeight));
		os=os.replace("__rmk__", ""+Tools.ts(this.remarks));
		return os;
	}
	public Integer getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}
	public String getTakingAddress() {
		return takingAddress;
	}
	public void setTakingAddress(String takingAddress) {
		this.takingAddress = takingAddress;
	}
	public String getDeliveryPhone() {
		return deliveryPhone;
	}
	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
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
	public Integer getStockinManifestID() {
		return stockinManifestID;
	}
	public void setStockinManifestID(Integer stockinManifestID) {
		this.stockinManifestID = stockinManifestID;
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
	public String getDeliveryPerson() {
		return deliveryPerson;
	}
	public void setDeliveryPerson(String deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
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
	public Date getDateStockin() {
		return dateStockin;
	}
	public void setDateStockin(Date dateStockin) {
		this.dateStockin = dateStockin;
	}
	
//	public void setDateStockin(String s){
//		this.dateStockin=Tools.isVoid(s)?null:Date.valueOf(s);
//	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getStockinFee() {
		return stockinFee;
	}
	public void setStockinFee(double stockinFee) {
		this.stockinFee = stockinFee;
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
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getOriginAgent() {
		return originAgent;
	}
	public void setOriginAgent(String originAgent) {
		this.originAgent = originAgent;
	}
	public String getOriginAddress() {
		return originAddress;
	}
	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}
	public String getOriginPerson() {
		return originPerson;
	}
	public void setOriginPerson(String originPerson) {
		this.originPerson = originPerson;
	}
	public String getOriginPhone() {
		return originPhone;
	}
	public void setOriginPhone(String originPhone) {
		this.originPhone = originPhone;
	}
	
	public ArrayList<StockinItemDTO> getStockinItems() {
		return stockinItems;
	}
	public void setStockinItems(ArrayList<StockinItemDTO> stockinItems) {
		this.stockinItems = stockinItems;
	}
	
	

}
