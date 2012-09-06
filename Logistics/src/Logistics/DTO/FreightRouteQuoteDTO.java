package Logistics.DTO;

public class FreightRouteQuoteDTO {
	Integer freightContractorID;
	Integer FreightRouteID;
	double priceByAmount;
	double priceByVolume;
	double priceByWeight;
	double priceDirectly;
	double deliveryQuote;
	double extraQuote;
	FreightContractorDTO freightContractorDTO;
	FreightRouteDTO freightRouteDTO;
	public Integer getFreightContractorID() {
		return freightContractorID;
	}
	public void setFreightContractorID(Integer freightContractorID) {
		this.freightContractorID = freightContractorID;
	}
	public Integer getFreightRouteID() {
		return FreightRouteID;
	}
	public void setFreightRouteID(Integer freightRouteID) {
		FreightRouteID = freightRouteID;
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
	public double getPriceDirectly() {
		return priceDirectly;
	}
	public void setPriceDirectly(double priceDirectly) {
		this.priceDirectly = priceDirectly;
	}
	public FreightContractorDTO getFreightContractorDTO() {
		return freightContractorDTO;
	}
	public void setFreightContractorDTO(FreightContractorDTO freightContractorDTO) {
		this.freightContractorDTO = freightContractorDTO;
	}
	public FreightRouteDTO getFreightRouteDTO() {
		return freightRouteDTO;
	}
	public void setFreightRouteDTO(FreightRouteDTO freightRouteDTO) {
		this.freightRouteDTO = freightRouteDTO;
	}
	public double getDeliveryQuote() {
		return deliveryQuote;
	}
	public void setDeliveryQuote(double deliveryQuote) {
		this.deliveryQuote = deliveryQuote;
	}
	public double getExtraQuote() {
		return extraQuote;
	}
	public void setExtraQuote(double extraQuote) {
		this.extraQuote = extraQuote;
	}
	

}
