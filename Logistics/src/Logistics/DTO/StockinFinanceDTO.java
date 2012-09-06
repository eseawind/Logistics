package Logistics.DTO;

import java.sql.Date;

import jxl.write.Label;

import Logistics.Common.DtoToExcel;
import Logistics.Common.Tools;

public class StockinFinanceDTO implements DtoToExcel{
	
	Integer stockinManifestID;
	int sumAmount;
	String customer;
	String warehouseName;
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
	String financialState;
	double extraCost;
	public int getColn() {
		return 17;
	}
	public boolean toCellName(Label[] cells) {
		if(cells.length!=this.getColn()){
			return false;
		}
		cells[0].setString("入库单号");
		cells[1].setString("数量合计");
		cells[2].setString("客户");
		cells[3].setString("仓库");
		cells[4].setString("备注");
		cells[5].setString("计费方式");
		cells[6].setString("成本中心");
		cells[7].setString("销售中心");
		cells[8].setString("建单日期");
		cells[9].setString("入库日期");
		cells[10].setString("报价");
		cells[11].setString("入库费用");
		cells[12].setString("总体积");
		cells[13].setString("总重量");
		cells[14].setString("装卸费");
		cells[15].setString("财务状态");
		cells[16].setString("额外费用");
		return true;
	}
	public boolean toCellValue(Label[] cells) {
		if(cells.length!=this.getColn()){
			return false;
		}
		cells[0].setString(Tools.toString(this.stockinManifestID));
		cells[1].setString(""+this.sumAmount);
		cells[2].setString(""+this.customer);
		cells[3].setString(""+this.warehouseName);
		cells[4].setString(""+this.remarks);
		cells[5].setString(""+this.chargeMode);
		cells[6].setString(""+this.costCenter);
		cells[7].setString(""+this.sellCenter);
		cells[8].setString(""+this.dateCreated);
		cells[9].setString(""+this.dateStockin);
		cells[10].setString(""+this.unitPrice);
		cells[11].setString(""+this.stockinFee);
		cells[12].setString(""+this.sumVolume);
		cells[13].setString(""+this.sumWeight);
		cells[14].setString(""+this.loadUnloadCost);
		cells[15].setString(""+this.financialState);
		cells[16].setString(""+this.extraCost);
		return true;
	}
	public static StockinFinanceDTO valueOf(StockinManifestDTO smdto){
		StockinFinanceDTO sfdto=new StockinFinanceDTO();
		if(smdto.getStockinManifestID()==null || smdto.getDateStockin()==null)
			return null;
		sfdto.stockinManifestID=smdto.getStockinManifestID();
		sfdto.sumAmount=smdto.getSumAmount();
		sfdto.customer=smdto.getCustomer()+"("+smdto.getCustomerID()+")";
		sfdto.warehouseName=smdto.getWarehouseName()+"("+smdto.getWarehouseID()+")";
		sfdto.remarks=smdto.getRemarks();
		sfdto.chargeMode=smdto.getChargeMode();
		sfdto.costCenter=smdto.getCostCenter();
		sfdto.sellCenter=smdto.getSellCenter();
		sfdto.dateCreated=smdto.getDateCreated();
		sfdto.dateStockin=smdto.getDateStockin();
		sfdto.unitPrice=smdto.getUnitPrice();
		sfdto.stockinFee=smdto.getStockinFee();
		sfdto.sumVolume=smdto.getSumVolume();
		sfdto.sumWeight=smdto.getSumWeight();
		sfdto.loadUnloadCost=smdto.getLoadUnloadCost();
		return sfdto;
	}
	public double getExtraCost() {
		return extraCost;
	}
	public void setExtraCost(double extraCost) {
		this.extraCost = extraCost;
	}
	public Integer getStockinManifestID() {
		return stockinManifestID;
	}
	public void setStockinManifestID(Integer stockinManifestID) {
		this.stockinManifestID = stockinManifestID;
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
	public Date getDateStockin() {
		return dateStockin;
	}
	public void setDateStockin(Date dateStockin) {
		this.dateStockin = dateStockin;
	}
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
	public String getFinancialState() {
		return financialState;
	}
	public void setFinancialState(String financialState) {
		this.financialState = financialState;
	}
	
	
}
