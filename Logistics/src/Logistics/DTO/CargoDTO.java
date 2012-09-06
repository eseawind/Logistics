package Logistics.DTO;

public class CargoDTO {
	String cargoID;
	Integer freightManifestID;
	int amount;
	double volume;
	double weight;
	double value;
	public String getCargoID() {
		return cargoID;
	}
	public void setCargoID(String cargoID) {
		this.cargoID = cargoID;
	}
	public Integer getFreightManifestID() {
		return freightManifestID;
	}
	public void setFreightManifestID(Integer freightManifestID) {
		this.freightManifestID = freightManifestID;
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

}
