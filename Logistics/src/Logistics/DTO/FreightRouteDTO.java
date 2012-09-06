package Logistics.DTO;

import Logistics.DTO.*;

public class FreightRouteDTO {
	private Integer freightRouteID;
	private String originID;
	private String destinationID;
	private String remarks;
	private Integer daysSpent;
	private CityDTO originCityDTO;
	private CityDTO destinationCityDTO;
	
	
	public Integer getFreightRouteID() {
		return freightRouteID;
	}
	public void setFreightRouteID(Integer freightRouteID) {
		this.freightRouteID = freightRouteID;
	}
	public String getOriginID() {
		return originID;
	}
	public void setOriginID(String originID) {
		this.originID = originID;
	}
	public String getDestinationID() {
		return destinationID;
	}
	public void setDestinationID(String destinationID) {
		this.destinationID = destinationID;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getDaysSpent() {
		return daysSpent;
	}
	public void setDaysSpent(Integer daysSpent) {
		this.daysSpent = daysSpent;
	}
	public CityDTO getOriginCityDTO() {
		return originCityDTO;
	}
	public void setOriginCityDTO(CityDTO originCityDTO) {
		this.originCityDTO = originCityDTO;
	}
	public CityDTO getDestinationCityDTO() {
		return destinationCityDTO;
	}
	public void setDestinationCityDTO(CityDTO destinationCityDTO) {
		this.destinationCityDTO = destinationCityDTO;
	}
	
	
}
