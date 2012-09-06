package Logistics.DTO;

import java.sql.Date;
import Logistics.DTO.*;
public class UserDTO {
	String UID;
	Date UDateCreated=null;
	String UPassword;
	int URoleID;
	String UState;
	RoleDTO RDTO=null;
	
	public String toString(){
		return UID+'\n'+(UDateCreated!=null?UDateCreated.toString():"无日期记录")+"\n"+UPassword+"\n"+URoleID+"\n"+UState+"\n"+(RDTO!=null?RDTO.toString():"无角色记录");
	}
	
	public RoleDTO getRoleDTO() {
		return RDTO;
	}
	public void setRoleDTO(RoleDTO roleDTO) {
		this.RDTO = roleDTO;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uid) {
		UID = uid;
	}
	public Date getUDateCreated() {
		return UDateCreated;
	}
	public void setUDateCreated(Date dateCreated) {
		UDateCreated = dateCreated;
	}
	public String getUPassword() {
		return UPassword;
	}
	public void setUPassword(String password) {
		UPassword = password;
	}
	public int getURoleID() {
		return URoleID;
	}
	public void setURoleID(int roleID) {
		URoleID = roleID;
	}
	public String getUState() {
		return UState;
	}
	public void setUState(String state) {
		UState = state;
	}
	
	

}
