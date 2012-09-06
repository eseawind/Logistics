package Logistics.DTO;

public class CustomerQuoteDTO {
	private Integer customerID;
	private Integer freightRouteID;
	public FreightRouteDTO freightRoute;
	private double priceByAmount;
	private double priceByVolume;
	private double priceByWeight;
	private double takingQuote;
	private double deliveryQuote;
	public Integer getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}
	public Integer getFreightRouteID() {
		return freightRouteID;
	}
	public void setFreightRouteID(Integer freightRouteID) {
		this.freightRouteID = freightRouteID;
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
	public FreightRouteDTO getFreightRoute() {
		return freightRoute;
	}
	public void setFreightRoute(FreightRouteDTO freightRoute) {
		this.freightRoute = freightRoute;
	}
	public double getTakingQuote() {
		return takingQuote;
	}
	public void setTakingQuote(double takingQuote) {
		this.takingQuote = takingQuote;
	}
	public double getDeliveryQuote() {
		return deliveryQuote;
	}
	public void setDeliveryQuote(double deliveryQuote) {
		this.deliveryQuote = deliveryQuote;
	}
	
	
}
