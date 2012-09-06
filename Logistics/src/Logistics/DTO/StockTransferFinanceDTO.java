package Logistics.DTO;

import java.sql.Date;

import jxl.write.Label;

import Logistics.Common.DtoToExcel;
import Logistics.Common.Tools;

public class StockTransferFinanceDTO implements DtoToExcel{
	
	Integer stockTransferManifestID;
	int sumAmount;
	String remarks;
	String costCenter;
	Date dateCreated;
	Date dateTransfered;
	double sumVolume;
	double sumWeight;
	double loadUnloadCost;
	String financialState;
	double extraCost;
	String inWarehouseName;
	String outWarehouseName;
	String operator;
	public int getColn() {
		return 14;
	}

	public boolean toCellName(Label[] cells) {
		if(cells.length!=this.getColn()){
			return false;
		}
		cells[0].setString("移库单号");
		cells[1].setString("数量合计");
		cells[2].setString("备注");
		cells[3].setString("成本中心");
		cells[4].setString("建单日期");
		cells[5].setString("移库日期");
		cells[6].setString("总体积");
		cells[7].setString("总重量");
		cells[8].setString("装卸支出");
		cells[9].setString("财务状态");
		cells[10].setString("额外费用");
		cells[11].setString("移入仓库");
		cells[12].setString("移除仓库");
		cells[13].setString("操作员");
		
		return true;
	}

	public boolean toCellValue(Label[] cells) {
		if(cells.length!=this.getColn())
			return false;
		cells[0].setString(""+this.stockTransferManifestID);
		cells[1].setString(""+this.sumAmount);
		cells[2].setString(""+this.remarks);
		cells[3].setString(""+this.costCenter);
		cells[4].setString(""+this.dateCreated);
		cells[5].setString(""+this.dateTransfered);
		cells[6].setString(""+this.sumVolume);
		cells[7].setString(""+this.sumWeight);
		cells[8].setString(""+this.loadUnloadCost);
		cells[9].setString(""+this.financialState);
		cells[10].setString(""+this.extraCost);
		cells[11].setString(""+this.inWarehouseName);
		cells[12].setString(""+this.outWarehouseName);
		cells[13].setString(""+this.operator);
		
		return true;
	}
	public static StockTransferFinanceDTO valueOf(StockTransferManifestDTO stmdto){
		StockTransferFinanceDTO sfdto=new StockTransferFinanceDTO();
		if(stmdto.getStockTransferManifestID()==null || stmdto.getDateTransfered()==null)
			return null;
		sfdto.stockTransferManifestID=stmdto.getStockTransferManifestID();
		sfdto.sumAmount=stmdto.getSumAmount();
		sfdto.remarks=stmdto.getRemarks();
		sfdto.costCenter=stmdto.getCostCenter();
		sfdto.dateCreated=stmdto.getDateCreated();
		sfdto.sumVolume=stmdto.getSumVolume();
		sfdto.sumWeight=stmdto.getSumWeight();
		sfdto.loadUnloadCost=stmdto.getLoadUnloadCost();
		sfdto.inWarehouseName=stmdto.getInWarehouse();
		sfdto.outWarehouseName=stmdto.getOutWarehouse();
		sfdto.operator=stmdto.getOperator();
		sfdto.dateTransfered=stmdto.getDateTransfered();
		return sfdto;
	}

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

	public Date getDateTransfered() {
		return dateTransfered;
	}

	public void setDateTransfered(Date dateTransfered) {
		this.dateTransfered = dateTransfered;
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

	public double getExtraCost() {
		return extraCost;
	}

	public void setExtraCost(double extraCost) {
		this.extraCost = extraCost;
	}

	public String getInWarehouseName() {
		return inWarehouseName;
	}

	public void setInWarehouseName(String inWarehouseName) {
		this.inWarehouseName = inWarehouseName;
	}

	public String getOutWarehouseName() {
		return outWarehouseName;
	}

	public void setOutWarehouseName(String outWarehouseName) {
		this.outWarehouseName = outWarehouseName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	
	
}
