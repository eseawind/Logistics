package Logistics.DTO;

import Logistics.Common.Tools;

public class CustomerDTO {
	private Integer customerID;
	private String type;
	private String name;
	private String contact;
	private String phone;
	private String email;
	private String address;
	private String remarks;
	private double freightCostPerCount;
    private double freightCostPerVolume;
    private double freightCostPerWeight;
    private double stockInCostPerCount ;
    private double stockInCostPerVolume;
    private double stockInCostPerWeight;
    private double stockOutCostPerCount;
    private double stockOutCostPerVolume;
    private double stockOutCostPerWeight;
    private double stockCostPerCount;
    private double stockCostPerVolume;
    private double stockCostPerWeight;
    public String jointName(){
    	return this.name+"("+this.customerID+")";
    }
    
	public Integer getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public double getFreightCostPerCount() {
		return freightCostPerCount;
	}
	public void setFreightCostPerCount(double freightCostPerCount) {
		this.freightCostPerCount = freightCostPerCount;
	}
	public double getFreightCostPerVolume() {
		return freightCostPerVolume;
	}
	public void setFreightCostPerVolume(double freightCostPerVolume) {
		this.freightCostPerVolume = freightCostPerVolume;
	}
	public double getFreightCostPerWeight() {
		return freightCostPerWeight;
	}
	public void setFreightCostPerWeight(double freightCostPerWeight) {
		this.freightCostPerWeight = freightCostPerWeight;
	}
	public double getStockInCostPerCount() {
		return stockInCostPerCount;
	}
	public void setStockInCostPerCount(double stockInCostPerCount) {
		this.stockInCostPerCount = stockInCostPerCount;
	}
	public double getStockInCostPerVolume() {
		return stockInCostPerVolume;
	}
	public void setStockInCostPerVolume(double stockInCostPerVolume) {
		this.stockInCostPerVolume = stockInCostPerVolume;
	}
	public double getStockInCostPerWeight() {
		return stockInCostPerWeight;
	}
	public void setStockInCostPerWeight(double stockInCostPerWeight) {
		this.stockInCostPerWeight = stockInCostPerWeight;
	}
	public double getStockOutCostPerCount() {
		return stockOutCostPerCount;
	}
	public void setStockOutCostPerCount(double stockOutCostPerCount) {
		this.stockOutCostPerCount = stockOutCostPerCount;
	}
	public double getStockOutCostPerVolume() {
		return stockOutCostPerVolume;
	}
	public void setStockOutCostPerVolume(double stockOutCostPerVolume) {
		this.stockOutCostPerVolume = stockOutCostPerVolume;
	}
	public double getStockOutCostPerWeight() {
		return stockOutCostPerWeight;
	}
	public void setStockOutCostPerWeight(double stockOutCostPerWeight) {
		this.stockOutCostPerWeight = stockOutCostPerWeight;
	}
	public double getStockCostPerCount() {
		return stockCostPerCount;
	}
	public void setStockCostPerCount(double stockCostPerCount) {
		this.stockCostPerCount = stockCostPerCount;
	}
	public double getStockCostPerVolume() {
		return stockCostPerVolume;
	}
	public void setStockCostPerVolume(double stockCostPerVolume) {
		this.stockCostPerVolume = stockCostPerVolume;
	}
	public double getStockCostPerWeight() {
		return stockCostPerWeight;
	}
	public void setStockCostPerWeight(double stockCostPerWeight) {
		this.stockCostPerWeight = stockCostPerWeight;
	}
	
    
    
}
