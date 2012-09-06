package Logistics.DTO;

import java.sql.Date;

import Logistics.Common.DtoToExcel;

import jxl.Cell;
import jxl.write.Label;
import jxl.write.WritableCell;

public class FreightIncomeDTO implements DtoToExcel{
	Integer freightManifestID;
	Date dateCreated;
	String customerType;
	String customer;
	int sumAmount;
	double sumVolume;
	double sumWeight;
	double sumValue;
	String chargeMode;
	double freightIncome;
	double consignIncome;
	double insuranceIncome;
	double deliveryIncome;
	double extraIncome;
	String financialState;
	String financialRemarks;
	String sellCenter;
	double priceByAmount;
	double priceByVolume;
	double priceByWeight;
	double insuranceRate;
	private String originCity;
	private String originProvince;
	private String destinationCity;
	private String destinationProvince;
	private String consigneeCompany;
	private String consignee;
	private String customerNumber;
	
	public static FreightIncomeDTO valueOf(FreightManifestDTO fmdto,int sumAmount,double sumVolume,double sumWeight,double sumValue){
		FreightIncomeDTO res=new FreightIncomeDTO();
		res.freightManifestID=fmdto.getFreightManifestID();
		res.dateCreated=fmdto.getDateCreated();
		res.customer=fmdto.getCustomer();
		res.customerType=fmdto.getCustomerType();
		res.sumAmount=sumAmount;
		res.sumValue=sumValue;
		res.sumVolume=sumVolume;
		res.sumWeight=sumWeight;
		res.chargeMode=fmdto.getChargeMode();
		res.freightIncome=fmdto.getFreightFee();
		res.consignIncome=fmdto.getConsignFee();
		res.insuranceIncome=fmdto.getInsuranceFee();
		res.deliveryIncome=fmdto.getDeliveryFee();
		res.extraIncome=fmdto.getExtraFee();
		res.financialState="未归档";
		res.financialRemarks="";
		res.sellCenter=fmdto.getSellCenter();
		res.priceByAmount=fmdto.getPriceByAmount();
		res.priceByVolume=fmdto.getPriceByVolume();
		res.priceByWeight=fmdto.getPriceByWeight();
		res.insuranceRate=fmdto.getInsuranceRate();
		res.originCity=fmdto.getOriginCity();
		res.originProvince=fmdto.getOriginProvince();
		res.destinationCity=fmdto.getDestinationCity();
		res.destinationProvince=fmdto.getDestinationProvince();
		res.consigneeCompany=fmdto.getConsigneeCompany();
		res.consignee=fmdto.getConsignee();
		res.customerNumber=fmdto.getCustomerNumber();
		return res;
	}
	public int getColn() {
		return 28;
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
		cells[8].setString("计费方式");
		cells[9].setString("运输收入");
		cells[10].setString("提货费");
		cells[11].setString("保价费");
		cells[12].setString("送货费");
		cells[13].setString("额外费用");
		cells[14].setString("财务状态");
		cells[15].setString("财务备注");
		cells[16].setString("销售中心");
		cells[17].setString("数量保价");
		cells[18].setString("体积保价");
		cells[19].setString("重量保价");
		cells[20].setString("保价费率");
		cells[21].setString("始发地市");
		cells[22].setString("始发地省");
		cells[23].setString("目的地市");
		cells[24].setString("目的地省");
		cells[25].setString("收货单位");
		cells[26].setString("收货人");
		cells[27].setString("客户单号");
		return true;
	}
	public boolean toCellValue(Label []cells){
		if(cells.length!=this.getColn())
			return false;
		cells[0].setString(""+this.freightManifestID);
		cells[1].setString(""+this.dateCreated);
		cells[2].setString(""+this.customerType);
		cells[3].setString(""+this.customer);
		cells[4].setString(""+this.sumAmount);
		cells[5].setString(""+this.sumVolume);
		cells[6].setString(""+this.sumWeight);
		cells[7].setString(""+this.sumValue);
		cells[8].setString(""+this.chargeMode);
		cells[9].setString(""+this.freightIncome);
		cells[10].setString(""+this.consignIncome);
		cells[11].setString(""+this.insuranceIncome);
		cells[12].setString(""+this.deliveryIncome);
		cells[13].setString(""+this.extraIncome);
		cells[14].setString(""+this.financialState);
		cells[15].setString(""+this.financialRemarks);
		cells[16].setString(""+this.sellCenter);
		cells[17].setString(""+this.priceByAmount);
		cells[18].setString(""+this.priceByVolume);
		cells[19].setString(""+this.priceByWeight);
		cells[20].setString(""+this.insuranceRate);
		cells[21].setString(""+this.originCity);
		cells[22].setString(""+this.originProvince);
		cells[23].setString(""+this.destinationCity);
		cells[24].setString(""+this.destinationProvince);
		cells[25].setString(this.consigneeCompany);
		cells[26].setString(this.consignee);
		cells[27].setString(this.customerNumber);
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
	public String getChargeMode() {
		return chargeMode;
	}
	public void setChargeMode(String chargeMode) {
		this.chargeMode = chargeMode;
	}
	public double getFreightIncome() {
		return freightIncome;
	}
	public void setFreightIncome(double freightIncome) {
		this.freightIncome = freightIncome;
	}
	public double getConsignIncome() {
		return consignIncome;
	}
	public void setConsignIncome(double consignIncome) {
		this.consignIncome = consignIncome;
	}
	public double getInsuranceIncome() {
		return insuranceIncome;
	}
	public void setInsuranceIncome(double insuranceIncome) {
		this.insuranceIncome = insuranceIncome;
	}
	public double getDeliveryIncome() {
		return deliveryIncome;
	}
	public void setDeliveryIncome(double deliveryIncome) {
		this.deliveryIncome = deliveryIncome;
	}
	public double getExtraIncome() {
		return extraIncome;
	}
	public void setExtraIncome(double extraIncome) {
		this.extraIncome = extraIncome;
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
	public String getSellCenter() {
		return sellCenter;
	}
	public void setSellCenter(String sellCenter) {
		this.sellCenter = sellCenter;
	}
	public double getPriceByAmount() {
		return priceByAmount;
	}
	public void setPriceByAmount(double priceByAmount) {
		this.priceByAmount = priceByAmount;
	}
	public double getPriceByVolume() {
		return priceByVolume;
	}
	public void setPriceByVolume(double priceByVolume) {
		this.priceByVolume = priceByVolume;
	}
	public double getPriceByWeight() {
		return priceByWeight;
	}
	public void setPriceByWeight(double priceByWeight) {
		this.priceByWeight = priceByWeight;
	}
	public double getInsuranceRate() {
		return insuranceRate;
	}
	public void setInsuranceRate(double insuranceRate) {
		this.insuranceRate = insuranceRate;
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
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	
	
}
