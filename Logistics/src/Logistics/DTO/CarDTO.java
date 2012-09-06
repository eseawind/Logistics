package Logistics.DTO;

public class CarDTO {
	private String carID;
	private String driverName;
	private String ownerName;
	private Integer carTypeID;
	private String phone;
	private Integer freightContractorID;
	private String engineNo;
	private String vehicleIdentificationNo;
	private String roadWorthyCertificateNo;
	private String remarks;
	private FreightContractorDTO freightContractorDTO;
	private CarTypeDTO carTypeDTO;
	public String jointName(){
		return carID;
	}
	public String getCarID() {
		return carID;
	}
	public void setCarID(String carID) {
		this.carID = carID;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public Integer getCarTypeID() {
		return carTypeID;
	}
	public void setCarTypeID(Integer carTypeID) {
		this.carTypeID = carTypeID;
	}
	public CarTypeDTO getCarTypeDTO() {
		return carTypeDTO;
	}
	public void setCarTypeDTO(CarTypeDTO carTypeDTO) {
		this.carTypeDTO = carTypeDTO;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getFreightContractorID() {
		return freightContractorID;
	}
	public void setFreightContractorID(Integer freightContractorID) {
		this.freightContractorID = freightContractorID;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public String getVehicleIdentificationNo() {
		return vehicleIdentificationNo;
	}
	public void setVehicleIdentificationNo(String vehicleIdentificationNo) {
		this.vehicleIdentificationNo = vehicleIdentificationNo;
	}
	public String getRoadWorthyCertificateNo() {
		return roadWorthyCertificateNo;
	}
	public void setRoadWorthyCertificateNo(String roadWorthyCertificateNo) {
		this.roadWorthyCertificateNo = roadWorthyCertificateNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public FreightContractorDTO getFreightContractorDTO() {
		return freightContractorDTO;
	}
	public void setFreightContractorDTO(FreightContractorDTO freightContractorDTO) {
		this.freightContractorDTO = freightContractorDTO;
	}
	

}
