package Logistics.DTO;

import java.sql.Date;
import java.util.ArrayList;

import Logistics.Common.Data;
import Logistics.Common.Tools;

public class ShipmentManifestDTO {
	Integer shipmentManifestID;
	String originProvince;
	String originCity;
	String destinationProvince;
	String destinationCity;
	String contractor;
	String carID;
	String carType;
	String driverName;
	String driverPhone;
	String consigneeCompany;
	String consignee;
	String consigneePhone;
	String consigneeAddress;
	String chargeMode;
	String remarks;
	double freightCost;
	double otherCost;
	double loadUnloadCost;
	String originAgent;
	String destinationAgent;
	double unitQuote;
	String announcements;
	Date dateCreated;
	//新增
	String originAddress;
	String originPerson;
	String originPhone;
	public ArrayList<FreightManifestDTO> fms;
	
	public String replaceInATable(String os,int currPageN,int pageAmount,int itemAmount){
		if(os==null){
			return null;
		}
//		os=os.replace("__selfPhone__", ""+Data.companyPhone);
//		os=os.replace("__selfAddress__", ""+Data.companyAddress);
		os=os.replace("__smid__", ""+this.shipmentManifestID);
		os=os.replace("__page__", "第"+currPageN+"/"+pageAmount+"页");
		os=os.replace("__datec__", ""+this.dateCreated);
		os=os.replace("__datep__", ""+Tools.currDate());
		os=os.replace("__origin__", ""+this.originProvince+"  "+this.originCity);
		os=os.replace("__originAgent__", ""+this.originAgent);
		os=os.replace("__originAddress__", ""+this.originAddress);
		os=os.replace("__originPerson__", ""+this.originPerson);
		os=os.replace("__originPhone__", ""+this.originPhone);
		os=os.replace("__destination__", ""+this.destinationProvince+"  "+this.destinationCity);
		os=os.replace("__consigneeCompany__", ""+this.consigneeCompany);
		os=os.replace("__consigneeAddress__", ""+this.consigneeAddress);
		os=os.replace("__consigneePhone__", ""+this.consigneePhone);
		os=os.replace("__consignee__", ""+this.consignee);
		os=os.replace("__contractor__", ""+this.contractor);
		os=os.replace("__driverName__", ""+this.driverName);
		os=os.replace("__driverPhone__", ""+this.driverPhone);
		os=os.replace("__carID__", ""+this.carID);
		os=os.replace("__carType__", ""+this.carType);
		
		os=os.replace("__announcements__", ""+this.announcements);
		os=os.replace("__remarks__", ""+this.remarks);
		//os=os.replace("__", "送货方式： "+);
		
		
		
		for(int i=0;i<itemAmount;i++){
			int index=(currPageN-1)*itemAmount+i;
			if(fms==null || fms.size()<=index){
				os=os.replace("__fmid_"+i+"__", "");
				os=os.replace("__fcus_"+i+"__", "");
				os=os.replace("__fcsnph_"+i+"__", "");
				os=os.replace("__fsa_"+i+"__", "");
				os=os.replace("__fsvo_"+i+"__", "");
				os=os.replace("__fsw_"+i+"__", "");
				os=os.replace("__fcsec_"+i+"__", "");
				os=os.replace("__fcse_"+i+"__", "");
				os=os.replace("__fceph_"+i+"__", "");
				os=os.replace("__fcm_"+i+"__", "");
			}
			else{
				os=os.replace("__fmid_"+i+"__", ""+fms.get(index).getFreightManifestID());
				os=os.replace("__fcus_"+i+"__", ""+fms.get(index).getCustomer());
				os=os.replace("__fcsnph_"+i+"__", ""+fms.get(index).getConsignerPhone());
				os=os.replace("__fsa_"+i+"__", ""+fms.get(index).getSumAmount());
				os=os.replace("__fsvo_"+i+"__", ""+fms.get(index).getSumVolume());
				os=os.replace("__fsw_"+i+"__", ""+fms.get(index).getSumWeight());
				os=os.replace("__fcsec_"+i+"__", ""+fms.get(index).getConsigneeCompany());
				os=os.replace("__fcse_"+i+"__", ""+fms.get(index).getConsignee());
				os=os.replace("__fceph_"+i+"__", ""+fms.get(index).getConsigneePhone());
				os=os.replace("__fcm_"+i+"__", ""+fms.get(index).getChargeMode());
			}
		}
		return os;
		
	}
	public Integer getShipmentManifestID() {
		return shipmentManifestID;
	}
	public void setShipmentManifestID(Integer ipshipmentManifestIDmentManifestID) {
		this.shipmentManifestID = ipshipmentManifestIDmentManifestID;
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
	public String getContractor() {
		return contractor;
	}
	public void setContractor(String contractor) {
		this.contractor = contractor;
	}
	public String getCarID() {
		return carID;
	}
	public void setCarID(String carID) {
		this.carID = carID;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
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
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
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
	public double getOtherCost() {
		return otherCost;
	}
	public void setOtherCost(double otherCost) {
		this.otherCost = otherCost;
	}
	public double getLoadUnloadCost() {
		return loadUnloadCost;
	}
	public void setLoadUnloadCost(double loadUnloadCost) {
		this.loadUnloadCost = loadUnloadCost;
	}
	public String getOriginAgent() {
		return originAgent;
	}
	public void setOriginAgent(String originAgent) {
		this.originAgent = originAgent;
	}
	public String getDestinationAgent() {
		return destinationAgent;
	}
	public void setDestinationAgent(String destinationAgent) {
		this.destinationAgent = destinationAgent;
	}
	public double getUnitQuote() {
		return unitQuote;
	}
	public void setUnitQuote(double unitQuote) {
		this.unitQuote = unitQuote;
	}
	public String getAnnouncements() {
		return announcements;
	}
	public void setAnnouncements(String announcements) {
		this.announcements = announcements;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public void setDateCreated(String dateCreated){
		this.dateCreated=Date.valueOf(dateCreated);
	}
	public String getOriginAddress() {
		return originAddress;
	}
	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}
	public String getOriginPerson() {
		return originPerson;
	}
	public void setOriginPerson(String originPerson) {
		this.originPerson = originPerson;
	}
	public String getOriginPhone() {
		return originPhone;
	}
	public void setOriginPhone(String originPhone) {
		this.originPhone = originPhone;
	}
	
}
