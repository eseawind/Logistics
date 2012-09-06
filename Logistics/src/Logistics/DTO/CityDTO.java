package Logistics.DTO;

public class CityDTO {
	private String CityID;
	private String Name;
	private String Province;
	public String getJointName(){
		return Name+"("+CityID+")"+"-"+Province;
	}
	public String getCityID() {
		return CityID;
	}
	public void setCityID(String cityID) {
		CityID = cityID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getProvince() {
		return Province;
	}
	public void setProvince(String province) {
		Province = province;
	}
}
