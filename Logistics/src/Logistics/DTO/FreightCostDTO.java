package Logistics.DTO;

import java.sql.Date;

import jxl.write.Label;

import Logistics.Common.DtoToExcel;
import Logistics.Common.Tools;

public class FreightCostDTO implements DtoToExcel{
	Integer freightManifestID;
	Date dateCreated;
	String customerType;
	String customer;
	int sumAmount;
	double sumVolume;
	double sumWeight;
	double sumValue;
	double freightCost;
	double prepaidCost;
	double extraCost;
	String extraCostRemarks;
	String financialState;
	String financialRemarks;
	String costCenter;
	
	private String originCity;
	private String originProvince;
	private String destinationCity;
	private String destinationProvince;
	private String customerNumber;
	private String consigneeCompany;
	private String consignee;
	
	public static FreightCostDTO valueOf(FreightManifestDTO fmdto,int sumAmount,double sumVolume,double sumWeight,double sumValue){
		FreightCostDTO res=new FreightCostDTO();
		res.freightManifestID=fmdto.getFreightManifestID();
		res.dateCreated=fmdto.getDateCreated();
		res.customer=fmdto.getCustomer();
		res.customerType=fmdto.getCustomerType();
		res.sumAmount=sumAmount;
		res.sumValue=sumValue;
		res.sumVolume=sumVolume;
		res.sumWeight=sumWeight;
		res.freightCost=0;
		res.prepaidCost=fmdto.getPrepaidFee();
		res.extraCost=0;
		res.extraCostRemarks="";
		res.financialState="";
		res.financialState="未归档";
		res.financialRemarks="";
		res.costCenter=fmdto.getCostCenter();
		res.originCity=fmdto.getOriginCity();
		res.originProvince=fmdto.getOriginProvince();
		res.destinationCity=fmdto.getDestinationCity();
		res.destinationProvince=fmdto.getDestinationProvince();
		res.customerNumber=fmdto.getCustomerNumber();
		res.consignee=fmdto.getConsignee();
		res.consigneeCompany=fmdto.getConsigneeCompany();
		return res;
	}
	
	public int getColn() {
		return 21;
	}
	public boolean toCellName(Label[] cells) {
		if(cells.length!=this.getColn()){
			return false;
		}
		cells[0].setString("运输单号");
		cells[1].setString("建单日期");
		cells[2].setString("客户类型");
		cells[3].setString("客户");
		cells[4].setString("总数量");
		cells[5].setString("总体积");
		cells[6].setString("总重量");
		cells[7].setString("总价值");
		cells[8].setString("运输费");
		cells[9].setString("代垫费");
		cells[10].setString("额外费用");
		cells[11].setString("财务状态");
		cells[12].setString("财务备注");
		cells[13].setString("成本中心");
		cells[14].setString("始发地市");
		cells[15].setString("始发地省");
		cells[16].setString("目的地市");
		cells[17].setString("目的地省");
		cells[18].setString("客户编号");
		cells[19].setString("收货单位");
		cells[20].setString("收货人");
		return true;
	}
	public boolean toCellValue(Label[] cells) {
		if(cells.length!=this.getColn()){
			return false;
		}
		cells[0].setString(Tools.toString(this.freightManifestID));
		cells[1].setString(Tools.toString(this.dateCreated));
		cells[2].setString(this.customerType);
		cells[3].setString(this.customer);
		cells[4].setString(""+this.sumAmount);
		cells[5].setString(""+this.sumVolume);
		cells[6].setString(""+this.sumWeight);
		cells[7].setString(""+this.sumValue);
		cells[8].setString(""+this.freightCost);
		cells[9].setString(""+this.prepaidCost);
		cells[10].setString(""+this.extraCost);
		cells[11].setString(""+this.financialState);
		cells[12].setString(""+this.financialRemarks);
		cells[13].setString(""+this.costCenter);
		cells[14].setString(""+this.originCity);
		cells[15].setString(""+this.originProvince);
		cells[16].setString(""+this.destinationCity);
		cells[17].setString(""+this.destinationProvince);
		cells[18].setString(this.customerNumber);
		cells[19].setString(this.consigneeCompany);
		cells[20].setString(this.consignee);
		return true;
	}
	
	
	public Integer getFreightManifestID() {
		return freightManifestID;
	}
	public void setFreightManifestID(Integer freightManifestID) {
		this.freightManifestID = freightManifestID;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public int getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(int sumAmount) {
		this.sumAmount = sumAmount;
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
	public double getSumValue() {
		return sumValue;
	}
	public void setSumValue(double sumValue) {
		this.sumValue = sumValue;
	}
	public double getFreightCost() {
		return freightCost;
	}
	public void setFreightCost(double freightCost) {
		this.freightCost = freightCost;
	}
	public double getPrepaidCost() {
		return prepaidCost;
	}
	public void setPrepaidCost(double prepaidCost) {
		this.prepaidCost = prepaidCost;
	}
	
	public double getExtraCost() {
		return extraCost;
	}
	public void setExtraCost(double extraCost) {
		this.extraCost = extraCost;
	}
	public String getExtraCostRemarks() {
		return extraCostRemarks;
	}
	public void setExtraCostRemarks(String extraCostRemarks) {
		this.extraCostRemarks = extraCostRemarks;
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
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getOriginProvince() {
		return originProvince;
	}
	public void setOriginProvince(String originProvince) {
		this.originProvince = originProvince;
	}
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	public String getDestinationProvince() {
		return destinationProvince;
	}
	public void setDestinationProvince(String destinationProvince) {
		this.destinationProvince = destinationProvince;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getConsigneeCompany() {
		return consigneeCompany;
	}

	public void setConsigneeCompany(String consigneeCompany) {
		this.consigneeCompany = consigneeCompany;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	
	
	
}
