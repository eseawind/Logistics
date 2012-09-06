package Logistics.DTO;

import java.sql.Date;

import jxl.write.Label;

import Logistics.Common.DtoToExcel;

public class SpecialStockIncomeDTO implements DtoToExcel{
	Integer incomeID;
	Integer customerID;
	String customerName;
	double area;
	double quote;
	Date dateStart;
	Date dateEnd;
	Date dateCreated;
	String warehouseID;
	String warehouseName;
	String sellCenter;
	int daysStock;
	double stockFee;
	double extraFee;
	String financialState;
	String remarks;
	
	public int getColn() {
		return 16;
	}

	public boolean toCellName(Label[] cells) {
		if(cells.length!=this.getColn()){
			return false;
		}
		cells[0].setString("收入编号");
		cells[1].setString("客户编号");
		cells[2].setString("客户名称");
		cells[3].setString("面积");
		cells[4].setString("报价");
		cells[5].setString("仓储计算起始日期");
		cells[6].setString("仓储计算结束日期");
		cells[7].setString("创建日期");
		cells[8].setString("仓库编号");
		cells[9].setString("仓库名称");
		cells[10].setString("销售中心");
		cells[11].setString("存储天数");
		cells[12].setString("库存费用");
		cells[13].setString("额外费用");
		cells[14].setString("财务状态");
		cells[15].setString("财务备注");
		return true;
	}
	public boolean toCellValue(Label []cells){
		if(cells.length!=this.getColn())
			return false;
		cells[0].setString(""+this.incomeID);
		cells[1].setString(""+this.customerID);
		cells[2].setString(""+this.customerName);
		cells[3].setString(""+this.area);
		cells[4].setString(""+this.quote);
		cells[5].setString(""+this.dateStart);
		cells[6].setString(""+this.dateEnd);
		cells[7].setString(""+this.dateCreated);
		cells[8].setString(""+this.warehouseID);
		cells[9].setString(""+this.warehouseName);
		cells[10].setString(""+this.sellCenter);
		cells[11].setString(""+this.daysStock);
		cells[12].setString(""+this.stockFee);
		cells[13].setString(""+this.extraFee);
		cells[14].setString(""+this.financialState);
		cells[15].setString(""+this.remarks);
		
		return true;
	}
	
	public Integer getIncomeID() {
		return incomeID;
	}
	public void setIncomeID(Integer incomeID) {
		this.incomeID = incomeID;
	}
	public Integer getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public double getQuote() {
		return quote;
	}
	public void setQuote(double quote) {
		this.quote = quote;
	}
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	public Date getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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
	public String getSellCenter() {
		return sellCenter;
	}
	public void setSellCenter(String sellCenter) {
		this.sellCenter = sellCenter;
	}
	public int getDaysStock() {
		return daysStock;
	}
	public void setDaysStock(int daysStock) {
		this.daysStock = daysStock;
	}
	public double getStockFee() {
		return stockFee;
	}
	public void setStockFee(double stockFee) {
		this.stockFee = stockFee;
	}
	public double getExtraFee() {
		return extraFee;
	}
	public void setExtraFee(double extraFee) {
		this.extraFee = extraFee;
	}
	public String getFinancialState() {
		return financialState;
	}
	public void setFinancialState(String financialState) {
		this.financialState = financialState;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
