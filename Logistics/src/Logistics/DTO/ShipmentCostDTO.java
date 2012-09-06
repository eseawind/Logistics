package Logistics.DTO;

import java.sql.Date;

import jxl.write.Label;

import Logistics.Common.DtoToExcel;
import Logistics.Common.Tools;

public class ShipmentCostDTO implements DtoToExcel{
	
	Integer shipmentManifestID;
	String chargeMode;
	String remarks;
	double freightCost;
	double extraCost;
	double loadUnloadCost;
	double unitQuote;
	Date dateCreated;
	String costCenter;
	double sumVolume;
	double sumWeight;
	int sumAmount;
	double sumValue;
	String financialState;
	String originCity;
	String originProvince;
	String destinationCity;
	String destinationProvince;
	String freightContractor;
	
	public int getColn() {
		return 18;
	}
	public boolean toCellName(Label[] cells) {
		if(cells.length!=this.getColn()){
			return false;
		}
		cells[0].setString("装车单号");
		cells[1].setString("计费方式");
		cells[2].setString("备注");
		cells[3].setString("运输费");
		cells[4].setString("额外费用");
		cells[5].setString("装卸费");
		cells[6].setString("单位报价");
		cells[7].setString("建单日期");
		cells[8].setString("承运单位");
		cells[9].setString("总体积");
		cells[10].setString("总重量");
		cells[11].setString("总数量");
		cells[12].setString("总价值");
		cells[13].setString("财务状态");
		cells[14].setString("始发地市");
		cells[15].setString("始发地省");
		cells[16].setString("目的地市");
		cells[17].setString("目的地省");
		
		return true;
	}
	public boolean toCellValue(Label[] cells) {
		if(cells.length!=this.getColn()){
			return false;
		}
		cells[0].setString(Tools.toString(this.shipmentManifestID));
		cells[1].setString(""+this.chargeMode);
		cells[2].setString(""+this.remarks);
		cells[3].setString(""+this.freightCost);
		cells[4].setString(""+this.extraCost);
		cells[5].setString(""+this.loadUnloadCost);
		cells[6].setString(""+this.unitQuote);
		cells[7].setString(""+this.dateCreated);
		cells[8].setString(this.freightContractor);
		cells[9].setString(""+this.sumVolume);
		cells[10].setString(""+this.sumWeight);
		cells[11].setString(""+this.sumAmount);
		cells[12].setString(""+this.sumValue);
		cells[13].setString(this.financialState);
		cells[14].setString(this.originCity);
		cells[15].setString(this.originProvince);
		cells[16].setString(this.destinationCity);
		cells[17].setString(this.destinationProvince);
		
		return true;
	}
	
	static public ShipmentCostDTO valueOf(ShipmentManifestDTO smdto
			,int amount,double volume,double weight,double value){
		if(smdto==null || smdto.getShipmentManifestID()==null
				|| Tools.isVoid(smdto.getChargeMode()) ){
			return null;
		}
		ShipmentCostDTO resdto=new ShipmentCostDTO();
		resdto.shipmentManifestID=smdto.shipmentManifestID;
		resdto.chargeMode=smdto.chargeMode;
		resdto.remarks="";
		resdto.freightCost=smdto.getFreightCost();
		resdto.extraCost=0;
		resdto.loadUnloadCost=0;
		resdto.unitQuote=smdto.unitQuote;
		resdto.dateCreated=Tools.currDate();
		resdto.costCenter="";
		resdto.sumAmount=amount;
		resdto.sumValue=value;
		resdto.sumVolume=volume;
		resdto.sumWeight=weight;
		resdto.financialState="未归档";
		resdto.originCity=smdto.getOriginCity();
		resdto.originProvince=smdto.getOriginProvince();
		resdto.destinationCity=smdto.getDestinationCity();
		resdto.destinationProvince=smdto.getDestinationProvince();
		resdto.freightContractor=smdto.getContractor();
		return resdto;
	}
	public Integer getShipmentManifestID() {
		return shipmentManifestID;
	}
	public void setShipmentManifestID(Integer shipmentManifestID) {
		this.shipmentManifestID = shipmentManifestID;
	}
	public String getChargeMode() {
		return chargeMode;
	}
	public void setChargeMode(String chargeMode) {
		this.chargeMode = chargeMode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public double getFreightCost() {
		return freightCost;
	}
	public void setFreightCost(double freightCost) {
		this.freightCost = freightCost;
	}
	public double getExtraCost() {
		return extraCost;
	}
	public void setExtraCost(double extraCost) {
		this.extraCost = extraCost;
	}
	public double getLoadUnloadCost() {
		return loadUnloadCost;
	}
	public void setLoadUnloadCost(double loadUnloadCost) {
		this.loadUnloadCost = loadUnloadCost;
	}
	public double getUnitQuote() {
		return unitQuote;
	}
	public void setUnitQuote(double unitQuote) {
		this.unitQuote = unitQuote;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
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
	public int getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(int sumAmount) {
		this.sumAmount = sumAmount;
	}
	public double getSumValue() {
		return sumValue;
	}
	public void setSumValue(double sumValue) {
		this.sumValue = sumValue;
	}
	public String getFinancialState() {
		return financialState;
	}
	public void setFinancialState(String financialState) {
		this.financialState = financialState;
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
	public String getFreightContractor() {
		return freightContractor;
	}
	public void setFreightContractor(String freightContractor) {
		this.freightContractor = freightContractor;
	}
	
	
	
	
}
