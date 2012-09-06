package Logistics.DTO;

import java.sql.Date;
import java.sql.Timestamp;

public class FreightStateDTO {
	Integer freightManifestID;
	String state;
	Timestamp date;
	String stateRemarks;
	public Integer getFreightManifestID() {
		return freightManifestID;
	}
	public void setFreightManifestID(Integer freightManifestID) {
		this.freightManifestID = freightManifestID;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getStateRemarks() {
		return stateRemarks;
	}
	public void setStateRemarks(String stateRemarks) {
		this.stateRemarks = stateRemarks;
	}
	
}
