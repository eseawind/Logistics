package Logistics.Servlet;

import Logistics.Common.Permission;

import com.opensymphony.xwork2.ActionSupport;

public class TestAction extends ActionSupport{
	int testInt;
	Integer testInteger=null;
	boolean success;
	String message;
	public String test(){
		
		System.out.println(testInt);
		System.out.println(testInteger);
		
		return "success";
	}
	public void queryOnCondtition(){
		Permission.checkPermission(this,Permission.MethodCode.insert);
	}
	public int getTestInt() {
		return testInt;
	}
	public void setTestInt(int testInt) {
		this.testInt = testInt;
	}
	public Integer getTestInteger() {
		return testInteger;
	}
	public void setTestInteger(Integer testInteger) {
		this.testInteger = testInteger;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
