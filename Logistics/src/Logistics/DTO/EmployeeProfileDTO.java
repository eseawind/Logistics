package Logistics.DTO;

public class EmployeeProfileDTO {
	private Integer EID;
	private String Name;
	private String Department;
	private String Position;
	private String CellPhone;
	private String Email;
	private String IDCard;
	private String Remarks;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	public String getCellPhone() {
		return CellPhone;
	}
	public void setCellPhone(String cellPhone) {
		CellPhone = cellPhone;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getIDCard() {
		return IDCard;
	}
	public void setIDCard(String card) {
		IDCard = card;
	}
	public Integer getEID() {
		return EID;
	}
	public void setEID(Integer eid) {
		EID = eid;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	
	
	
	
}
