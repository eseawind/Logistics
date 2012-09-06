package Logistics.DTO;

import java.sql.Date;

public class StockItemDTO {
	private String warehouseID;
	private Integer itemID;
	private Date	stockinDate;
	private Integer customerID;
	private int amount;
	private Date lastAccountDate;
	
	public CustomerDTO customer;
	public WarehouseDTO warehouse;
	public ItemDTO item;
	
	
	
	public void addAmount(int n){
		amount+=n;
	}
	public void subAmount(int n){
		amount-=n;
	}
	public String getWarehouseID() {
		return warehouseID;
	}
	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}
	public Integer getItemID() {
		return itemID;
	}
	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}
	public Date getStockinDate() {
		return stockinDate;
	}
	public void setStockinDate(Date stockinDate) {
		this.stockinDate = stockinDate;
	}
	public Integer getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getLastAccountDate() {
		return lastAccountDate;
	}
	public void setLastAccountDate(Date lastAccountDate) {
		this.lastAccountDate = lastAccountDate;
	}
	
	
	
}
