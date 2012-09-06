package Logistics.DTO;

import java.sql.Date;

import jxl.write.Label;

import Logistics.Common.DtoToExcel;
import Logistics.Common.Tools;

public class PaymentCollectionDTO implements DtoToExcel{
	Integer freightManifestID;
	Integer customerID;
	String customer;
	String originProvince;
	String originCity;
	String destinationProvince;
	String destinationCity;
	Date dateCreated;
	String state;
	Date dateModified;
	double expectedPayment;
	double recievedPayment;
	double procedureFeeRate;
	double procedureFee;
	String stateRemarks;
	String financialState;
	String financialRemarks;
	String costCenter;
	String sellCenter;
	String customerNumber;
	public int getColn() {
		return 19;
	}

	public boolean toCellName(Label[] cells) {
		if(cells.length!=this.getColn()){
			return false;
		}
		cells[0].setString("运输单号");
		cells[1].setString("客户编号");
		cells[2].setString("客户名称");
		cells[3].setString("始发地省");
		cells[4].setString("始发地市");
		cells[5].setString("目的地省");
		cells[6].setString("目的地市");
		cells[7].setString("建单日期");
		cells[8].setString("当前状态");
		cells[9].setString("上次修改日期");
		cells[10].setString("代收货款");
		cells[11].setString("已收货款");
		cells[12].setString("手续费率");
		cells[13].setString("手续费");
		cells[14].setString("财务状态");
		cells[15].setString("财务备注");
		cells[16].setString("成本中心");
		cells[17].setString("销售中心");
		cells[18].setString("客户单号");
		return true;
	}
	public boolean toCellValue(Label []cells){
		if(cells.length!=this.getColn())
			return false;
		cells[0].setString(""+this.freightManifestID);
		cells[1].setString(""+this.customerID);
		cells[2].setString(""+this.customer);
		cells[3].setString(""+this.originProvince);
		cells[4].setString(""+this.originCity);
		cells[5].setString(""+this.destinationProvince);
		cells[6].setString(""+this.destinationCity);
		cells[7].setString(""+this.dateCreated);
		cells[8].setString(""+this.state);
		cells[9].setString(""+this.dateModified);
		cells[10].setString(""+this.expectedPayment);
		cells[11].setString(""+this.recievedPayment);
		cells[12].setString(""+this.procedureFeeRate);
		cells[13].setString(""+this.procedureFee);
		cells[14].setString(""+this.financialState);
		cells[15].setString(""+this.financialRemarks);
		cells[16].setString(""+this.costCenter);
		cells[17].setString(""+this.sellCenter);
		cells[18].setString(this.customerNumber);
		return true;
	}
	public static PaymentCollectionDTO valueOf(FreightManifestDTO fmdto,Integer customerID){
		PaymentCollectionDTO res=new PaymentCollectionDTO();
		res.freightManifestID=fmdto.getFreightManifestID();
		res.customerID=customerID;
		res.customer=fmdto.getCustomer();
		res.originCity=fmdto.getOriginCity();
		res.originProvince=fmdto.getOriginProvince();
		res.destinationCity=fmdto.getDestinationCity();
		res.destinationProvince=fmdto.getDestinationProvince();
		res.dateCreated=fmdto.getDateCreated();
		res.expectedPayment=fmdto.getCollectionFee();
		res.recievedPayment=0;
		res.procedureFee=0;
		res.procedureFeeRate=0;
		res.state="未收";
		res.dateModified=Tools.currDate();
		res.stateRemarks="";
		res.costCenter=fmdto.getCostCenter();
		res.financialState="未归档";
		res.financialRemarks="";
		res.sellCenter=fmdto.getSellCenter();
		res.customerNumber=fmdto.getCustomerNumber();
		return res;
	}
	
	public Integer getFreightManifestID() {
		return freightManifestID;
	}
	public void setFreightManifestID(Integer freightManifestID) {
		this.freightManifestID = freightManifestID;
	}
	public Integer getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getOriginProvince() {
		return originProvince;
	}
	public void setOriginProvince(String originProvince) {
		this.originProvince = originProvince;
	}
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getDestinationProvince() {
		return destinationProvince;
	}
	public void setDestinationProvince(String destinationProvince) {
		this.destinationProvince = destinationProvince;
	}
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public double getExpectedPayment() {
		return expectedPayment;
	}
	public void setExpectedPayment(double expectedPayment) {
		this.expectedPayment = expectedPayment;
	}
	public double getRecievedPayment() {
		return recievedPayment;
	}
	public void setRecievedPayment(double recievedPayment) {
		this.recievedPayment = recievedPayment;
	}
	public double getProcedureFeeRate() {
		return procedureFeeRate;
	}
	public void setProcedureFeeRate(double procedureFeeRate) {
		this.procedureFeeRate = procedureFeeRate;
	}
	public double getProcedureFee() {
		return procedureFee;
	}
	public void setProcedureFee(double procedureFee) {
		this.procedureFee = procedureFee;
	}
	public String getStateRemarks() {
		return stateRemarks;
	}
	public void setStateRemarks(String stateRemarks) {
		this.stateRemarks = stateRemarks;
	}
	public String getFinancialState() {
		return financialState;
	}
	public void setFinancialState(String financialState) {
		this.financialState = financialState;
	}
	public String getFinancialRemarks() {
		return financialRemarks;
	}
	public void setFinancialRemarks(String financialRemarks) {
		this.financialRemarks = financialRemarks;
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

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	
	
}
