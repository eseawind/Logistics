package Logistics.DTO;

public class CarTypeQuoteDTO {
	Integer freightContractorID;
	Integer freightRouteID;
	Integer carTypeID;
	double price;
	FreightContractorDTO freightContractorDTO;
	FreightRouteDTO freightRouteDTO;
	CarTypeDTO carTypeDTO;
	
	public Integer getFreightContractorID() {
		return freightContractorID;
	}
	public void setFreightContractorID(Integer freightContractorID) {
		this.freightContractorID = freightContractorID;
	}
	public Integer getFreightRouteID() {
		return freightRouteID;
	}
	public void setFreightRouteID(Integer freightRouteID) {
		this.freightRouteID = freightRouteID;
	}
	public Integer getCarTypeID() {
		return carTypeID;
	}
	public void setCarTypeID(Integer carTypeID) {
		this.carTypeID = carTypeID;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	public CarTypeDTO getCarTypeDTO() {
		return carTypeDTO;
	}
	public void setCarTypeDTO(CarTypeDTO carTypeDTO) {
		this.carTypeDTO = carTypeDTO;
	}
	
}
