package Logistics.DTO;

import java.sql.Date;
import java.util.ArrayList;

import jxl.write.Label;

import Logistics.Common.DtoToExcel;
import Logistics.Common.Tools;

public class StockIncomeDTO implements DtoToExcel{
	Integer stockIncomeID;
	int itemID;
	String itemName;
	String itemNumber;
	Date dateStockin;
	Date dateStockout;
	int customerID;
	String customerName;
	String warehouseID;
	String warehouseName;
	int amount;
	double volume;
	double weight;
	Date dateAccountStart;
	Date dateAccountEnd;
	int daysStock;
	String chargeMode;
	double unitPrice;
	double stockFee;
	String FinancialState;
	Date dateModified;
	String sellCenter;
	double extraFee;
	String remarks;
	public int getColn() {
		return 24;
	}
	public boolean toCellName(Label[] cells) {
		if(cells.length!=this.getColn()){
			return false;
		}
		cells[0].setString("收支单号");
		cells[1].setString("物料编号");
		cells[2].setString("物料名称");
		cells[3].setString("物料代码");
		cells[4].setString("入库日期");
		cells[5].setString("出库日期");
		cells[6].setString("客户编号");
		cells[7].setString("客户名称");
		cells[8].setString("仓库编号");
		cells[9].setString("仓库名称");
		cells[10].setString("数量");
		cells[11].setString("体积");
		cells[12].setString("重量");
		cells[13].setString("结算起始日");
		cells[14].setString("结算结束日");
		cells[15].setString("存储天数");
		cells[16].setString("计费方式");
		cells[17].setString("单位报价");
		cells[18].setString("存储费用");
		cells[19].setString("财务状态");
		cells[20].setString("上次修改日期");
		cells[21].setString("销售中心");
		cells[22].setString("额外费用");
		cells[23].setString("备注");
		return true;
	}
	public boolean toCellValue(Label []cells){
		if(cells.length!=this.getColn())
			return false;
		cells[0].setString(""+this.stockIncomeID);
		cells[1].setString(""+this.itemID);
		cells[2].setString(""+this.itemName);
		cells[3].setString(""+this.itemNumber);
		cells[4].setString(""+this.dateStockin);
		cells[5].setString(""+this.dateStockout);
		cells[6].setString(""+this.customerID);
		cells[7].setString(""+this.customerName);
		cells[8].setString(""+this.warehouseID);
		cells[9].setString(""+this.warehouseName);
		cells[10].setString(""+this.amount);
		cells[11].setString(""+this.volume);
		cells[12].setString(""+this.weight);
		cells[13].setString(""+this.dateAccountStart);
		cells[14].setString(""+this.dateAccountEnd);
		cells[15].setString(""+this.daysStock);
		cells[16].setString(this.chargeMode);
		cells[17].setString(""+this.unitPrice);
		cells[18].setString(""+this.stockFee);
		cells[19].setString(""+this.FinancialState);
		cells[20].setString(""+this.dateModified);
		cells[21].setString(""+this.sellCenter);
		cells[22].setString(""+this.extraFee);
		cells[23].setString(this.remarks);
		return true;
	}
	public static StockIncomeDTO valueOF(StockItemDTO si){
		if(si==null || si.getCustomerID()==null || si.getWarehouseID()==null||
				si.getItemID()==null || si.getStockinDate()==null){
			return null;
		}
		StockIncomeDTO r=new StockIncomeDTO();
		r.itemID=si.getItemID();
		r.itemName=si.item.getName();
		r.itemNumber=si.item.getNumber();
		r.dateStockin=si.getStockinDate();
		r.dateStockout=null;
		r.customerID=si.getCustomerID();
		r.customerName=si.customer.getName();
		r.warehouseID=si.getWarehouseID();
		r.warehouseName=si.warehouse.getName();
		r.amount=si.getAmount();
		r.volume=si.getAmount()*si.item.getUnitVolume();
		r.weight=si.getAmount()*si.item.getUnitWeight();
		r.dateAccountStart=si.getLastAccountDate();
		r.dateAccountEnd=Tools.currDate();
		r.daysStock=Tools.diff(r.dateAccountEnd, r.dateAccountStart);
		r.chargeMode=null;
		r.unitPrice=0;
		r.stockFee=0;
		r.FinancialState=null;
		r.dateModified=Tools.currDate();
		r.sellCenter=null;
		r.extraFee=0;
		r.remarks=null;
		return r;
	}
	public static StockIncomeDTO valueOf(StockoutManifestDTO im,StockoutItemDTO it,Date startDate){
		if(im==null || it==null){
			return null;
		}
		StockIncomeDTO r=new StockIncomeDTO();
		r.itemID=it.getItemID();
		r.itemName=it.getItemName();
		r.itemNumber=it.getItemNumber();
		r.dateStockin=it.getDateStockin();
		r.dateStockout=im.getDateStockout();
		r.customerID=im.getCustomerID();
		r.customerName=im.getCustomer();
		r.warehouseID=im.getWarehouseID();
		r.warehouseName=im.getWarehouseName();
		r.amount=it.getAmount();
		r.volume=it.getVolume();
		r.weight=it.getWeight();
		r.dateAccountStart=startDate;
		r.dateAccountEnd=im.getDateStockout();
		r.daysStock=Tools.diff(im.getDateStockout(),startDate);
		r.chargeMode=null;
		r.unitPrice=0;
		r.stockFee=0;
		r.FinancialState=null;
		r.dateModified=Tools.currDate();
		r.sellCenter=im.getSellCenter();
		r.extraFee=0;
		r.remarks=null;
		return r;
	}
	public static StockIncomeDTO valueOf(StockTransferManifestDTO tm,StockTransferItemDTO it,Date startDate){
		if(tm==null || it==null){
			return null;
		}
		StockIncomeDTO r=new StockIncomeDTO();
		r.itemID=it.getItemID();
		r.itemName=it.getItemName();
		r.itemNumber=it.getItemNumber();
		r.dateStockin=it.getDateStockin();
		r.dateStockout=tm.getDateTransfered();
		r.customerID=it.getCustomerID();
		r.customerName=it.customerDTO.getName();
		r.warehouseID=tm.getOutWarehouseID();
		r.warehouseName=tm.getOutWarehouse();
		r.amount=it.getAmount();
		r.volume=it.getVolume();
		r.weight=it.getWeight();
		r.dateAccountStart=startDate;
		r.dateAccountEnd=tm.getDateTransfered();
		r.daysStock=Tools.diff(tm.getDateTransfered(),startDate);
		r.chargeMode=null;
		r.unitPrice=0;
		r.stockFee=0;
		r.FinancialState="未归档";
		r.dateModified=Tools.currDate();
		r.sellCenter=null;
		r.extraFee=0;
		r.remarks=null;
		return r;
	}
	
	public double getExtraFee() {
		return extraFee;
	}
	public void setExtraFee(double extraFee) {
		this.extraFee = extraFee;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getStockIncomeID() {
		return stockIncomeID;
	}
	public void setStockIncomeID(Integer stockIncomeID) {
		this.stockIncomeID = stockIncomeID;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public Date getDateStockin() {
		return dateStockin;
	}
	public void setDateStockin(Date dateStockin) {
		this.dateStockin = dateStockin;
	}
	public Date getDateStockout() {
		return dateStockout;
	}
	public void setDateStockout(Date dateStockout) {
		this.dateStockout = dateStockout;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getWarehouseID() {
		return warehouseID;
	}
	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public Date getDateAccountStart() {
		return dateAccountStart;
	}
	public void setDateAccountStart(Date dateAccountStart) {
		this.dateAccountStart = dateAccountStart;
	}
	public Date getDateAccountEnd() {
		return dateAccountEnd;
	}
	public void setDateAccountEnd(Date dateAccountEnd) {
		this.dateAccountEnd = dateAccountEnd;
	}
	public int getDaysStock() {
		return daysStock;
	}
	public void setDaysStock(int daysStock) {
		this.daysStock = daysStock;
	}
	public String getChargeMode() {
		return chargeMode;
	}
	public void setChargeMode(String chargeMode) {
		this.chargeMode = chargeMode;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getStockFee() {
		return stockFee;
	}
	public void setStockFee(double stockFee) {
		this.stockFee = stockFee;
	}
	public String getFinancialState() {
		return FinancialState;
	}
	public void setFinancialState(String financialState) {
		FinancialState = financialState;
	}
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public String getSellCenter() {
		return sellCenter;
	}
	public void setSellCenter(String sellCenter) {
		this.sellCenter = sellCenter;
	}
	
	
}
