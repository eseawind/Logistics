package Logistics.DTO;

public class FreightContractorDTO {

	private Integer freightContractorID;
	private String name;
	private String contact;
	private String phone;
	private String email;
	private String address;
	private String remarks;
	public String jointName(){
		return name+"("+freightContractorID+")";
	}
	public Integer getFreightContractorID() {
		return freightContractorID;
	}
	public void setFreightContractorID(Integer freightContractorID) {
		this.freightContractorID = freightContractorID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
