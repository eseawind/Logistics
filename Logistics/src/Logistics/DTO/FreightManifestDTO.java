package Logistics.DTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Logistics.Common.Data;
import Logistics.Common.Tools;

public class FreightManifestDTO {
	public enum dateType{
		dateDelivered,dateSigned
	}
	Integer freightManifestID;
	String customerType;
	String customer;
	String customerNumber;
	String originProvince;
	String originCity;
	String destinationProvince;
	String destinationCity;
	String consigner;
	String consignerPhone;
	String consignerAddress;
	String consigneeCompany;
	String consignee;
	String consigneePhone;
	String consigneeAddress;
	Date dateCreated;
	Date dateExpected;
	Date dateSigned;
	Date dateDelivered;
	String remarks;
	double freightFee;
	double consignFee;
	double deliveryFee;
	double insuranceFee;
	double prepaidFee;
	String freightState;
	String ackRequirement;
	String freightType;
	double extraFee;
	String costCenter;
	String announcements;
	String paymentType;
	String isCollection;
	String isInsurance;
	String isPrepaid;
	double collectionFee;
	String sellCenter;
	String chargeMode;
	double priceByAmount;
	double priceByVolume;
	double priceByWeight;
	double insuranceRate;
	String transitionInfo;
	String ackInfo;
	int sumAmount;
	double sumWeight;
	double sumVolume;
	double sumValue;
	
	public CargoDTO cargoSum=new CargoDTO();
	public ArrayList<CargoDTO> cargos=null;
	
	
	public String replaceInATable(String os,int currPageN,int pageAmount,int itemAmount){
		if(os==null){
			return null;
		}
		os=os.replace("__selfPhone__", ""+Data.companyPhone);
		os=os.replace("__selfAddress__", ""+Data.companyAddress);
		os=os.replace("__fmid__", ""+this.freightManifestID);
		os=os.replace("__page__", "第"+currPageN+"/"+pageAmount+"页");
		os=os.replace("__datec__", ""+this.dateCreated);
		os=os.replace("__datep__", ""+Tools.currDate());
		os=os.replace("__origin__", ""+this.originProvince+"  "+this.originCity);
		os=os.replace("__customerNumber__", ""+this.customerNumber);
		os=os.replace("__customer__", ""+this.customer);
		os=os.replace("__consignerAddress__", ""+this.consignerAddress);
		os=os.replace("__consignerPhone__", ""+this.consignerPhone);
		os=os.replace("__consigner__", ""+this.consigner);
		os=os.replace("__destination__", ""+this.destinationProvince+"  "+this.destinationCity);
		os=os.replace("__consigneeCompany__", ""+this.consigneeCompany);
		os=os.replace("__consigneeAddress__", ""+this.consigneeAddress);
		os=os.replace("__consigneePhone__", ""+this.consigneePhone);
		os=os.replace("__consignee__", ""+this.consignee);
		os=os.replace("__dateExpected__", ""+this.dateExpected);
		os=os.replace("__freightFee__", ""+this.freightFee);
		os=os.replace("__consignFee__", ""+this.consignFee);
		os=os.replace("__insuranceFee__", ""+this.insuranceFee);
		os=os.replace("__deliveryFee__", ""+this.deliveryFee);
		int sumMoney=0;
		sumMoney+=freightFee;
		sumMoney+=consignFee;
		sumMoney+=insuranceFee;
		sumMoney+=deliveryFee;
		os=os.replace("__sumMoney__", ""+sumMoney);
		os=os.replace("__paymentType__", ""+this.paymentType);
		os=os.replace("__isInsurance__", ""+this.isInsurance);
		os=os.replace("__isCollection__", ""+this.isCollection);
		os=os.replace("__collectionFee__", ""+this.collectionFee);
		os=os.replace("__isPrepaid__", ""+this.isPrepaid);
		os=os.replace("__prepaidFee__", ""+this.prepaidFee);
		
		os=os.replace("__sa__", ""+cargoSum.getAmount());
		os=os.replace("__sv__", ""+cargoSum.getVolume());
		os=os.replace("__sw__", ""+cargoSum.getWeight());
		os=os.replace("__sva__", ""+cargoSum.getValue());
		os=os.replace("__ackR__", ""+this.ackRequirement);
		os=os.replace("__announcements__", ""+this.announcements);
		os=os.replace("__remarks__", ""+this.remarks);
		//os=os.replace("__", "送货方式： "+);
		
		
		
		for(int i=0;i<itemAmount;i++){
			int index=(currPageN-1)*itemAmount+i;
			if(cargos==null || cargos.size()<=index){
				os=os.replace("__cn_"+i+"__", "");
				os=os.replace("__ca_"+i+"__", "");
				os=os.replace("__cv_"+i+"__", "");
				os=os.replace("__cw_"+i+"__", "");
				os=os.replace("__cva_"+i+"__", "");
			}
			else{
				os=os.replace("__cn_"+i+"__", cargos.get(index).getCargoID());
				os=os.replace("__ca_"+i+"__", ""+cargos.get(index).getAmount());
				os=os.replace("__cv_"+i+"__", ""+cargos.get(index).getVolume());
				os=os.replace("__cw_"+i+"__", ""+cargos.get(index).getWeight());
				os=os.replace("__cva_"+i+"__", ""+cargos.get(index).getValue());
			}
		}
		return os;
	}
	
	public String getTransitionInfo() {
		return transitionInfo;
	}
	public void setTransitionInfo(String transitionInfo) {
		this.transitionInfo = transitionInfo;
	}
	public String getAckInfo() {
		return ackInfo;
	}
	public void setAckInfo(String ackInfo) {
		this.ackInfo = ackInfo;
	}
	public String getChargeMode() {
		return chargeMode;
	}
	public void setChargeMode(String chargeMode) {
		this.chargeMode = chargeMode;
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
	public String getSellCenter() {
		return sellCenter;
	}
	public void setSellCenter(String sellCenter) {
		this.sellCenter = sellCenter;
	}
	public double getCollectionFee() {
		return collectionFee;
	}
	public void setCollectionFee(double collectionFee) {
		this.collectionFee = collectionFee;
	}
	public Integer getFreightManifestID() {
		return freightManifestID;
	}
	public void setFreightManifestID(Integer freightManifestID) {
		this.freightManifestID = freightManifestID;
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
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
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
	public String getConsigner() {
		return consigner;
	}
	public void setConsigner(String consigner) {
		this.consigner = consigner;
	}
	public String getConsignerPhone() {
		return consignerPhone;
	}
	public void setConsignerPhone(String consignerPhone) {
		this.consignerPhone = consignerPhone;
	}
	public String getConsignerAddress() {
		return consignerAddress;
	}
	public void setConsignerAddress(String consignerAddress) {
		this.consignerAddress = consignerAddress;
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
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public void setDateCreated(String dc){
		this.dateCreated=Tools.isVoid(dc)?null:Date.valueOf(dc);
	}
	public Date getDateExpected() {
		return dateExpected;
	}
	public void setDateExpected(Date dateExpected) {
		this.dateExpected = dateExpected;
	}
	public void setDateExpected(String de){
		this.dateExpected=Tools.isVoid(de)?null:Date.valueOf(de);
	}
	public Date getDateSigned() {
		return dateSigned;
	}
	public void setDateSigned(Date dateSigned) {
		this.dateSigned = dateSigned;
	}
	public void setDateSigned(String ds){
		this.dateSigned=Tools.isVoid(ds)?null:Date.valueOf(ds);
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setDateDelivered(String dd){
		this.dateDelivered=Tools.isVoid(dd)?null:Date.valueOf(dd);
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public double getFreightFee() {
		return freightFee;
	}
	public void setFreightFee(double freightFee) {
		this.freightFee = freightFee;
	}
	public double getConsignFee() {
		return consignFee;
	}
	public void setConsignFee(double consignFee) {
		this.consignFee = consignFee;
	}
	public double getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public double getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(double insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	public double getPrepaidFee() {
		return prepaidFee;
	}
	public void setPrepaidFee(double prepaidFee) {
		this.prepaidFee = prepaidFee;
	}
	
	public String getFreightState() {
		return freightState;
	}
	public void setFreightState(String freightState) {
		this.freightState = freightState;
	}
	public String getAckRequirement() {
		return ackRequirement;
	}
	public void setAckRequirement(String ackRequirement) {
		this.ackRequirement = ackRequirement;
	}
	public String getFreightType() {
		return freightType;
	}
	public void setFreightType(String freightType) {
		this.freightType = freightType;
	}
	public double getExtraFee() {
		return extraFee;
	}
	public void setExtraFee(double extraFee) {
		this.extraFee = extraFee;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getAnnouncements() {
		return announcements;
	}
	public void setAnnouncements(String announcements) {
		this.announcements = announcements;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getIsCollection() {
		return isCollection;
	}
	public void setIsCollection(String isCollection) {
		this.isCollection = isCollection;
	}
	public String getIsInsurance() {
		return isInsurance;
	}
	public void setIsInsurance(String isInsurance) {
		this.isInsurance = isInsurance;
	}
	public String getIsPrepaid() {
		return isPrepaid;
	}
	public void setIsPrepaid(String isPrepaid) {
		this.isPrepaid = isPrepaid;
	}
	public String getConsigneeCompany() {
		return consigneeCompany;
	}
	public void setConsigneeCompany(String consigneeCompany) {
		this.consigneeCompany = consigneeCompany;
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

	public CargoDTO getCargoSum() {
		return cargoSum;
	}

	public void setCargoSum(CargoDTO cargoSum) {
		this.cargoSum = cargoSum;
	}

	public ArrayList<CargoDTO> getCargos() {
		return cargos;
	}

	public void setCargos(ArrayList<CargoDTO> cargos) {
		this.cargos = cargos;
	}

	public double getSumWeight() {
		return sumWeight;
	}

	public void setSumWeight(double sumWeight) {
		this.sumWeight = sumWeight;
	}

	public double getSumVolume() {
		return sumVolume;
	}

	public void setSumVolume(double sumVolume) {
		this.sumVolume = sumVolume;
	}
	
}
