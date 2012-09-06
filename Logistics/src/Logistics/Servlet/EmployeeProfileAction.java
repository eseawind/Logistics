package Logistics.Servlet;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Logistics.DAO.*;
import Logistics.DTO.*;
import Logistics.Common.*;
import Logistics.Common.Permission.MethodCode;

public class EmployeeProfileAction {
	/*struts 固定参数*/
	private boolean success;//调用函数成功与否
	private String message="";//调用返回的信息
	
	//业务参数
	private Integer EmployeeID;
	private String Name;
	private String Department;
	private String Position;
	private String CellPhone;
	private String Email;
	private String IDCard;
	private String Remarks;
	private ArrayList<Integer> EmployeeIDList=new ArrayList<Integer>();
	private int limit;//分页的页面大小
	private int start;//分页起始点
	private int qualifiedAmount;//条件查询符合条目的数目
	private Map data;//返回的结果
	private ArrayList<Map>employeeList=null;
	
	private boolean valid=true;
	
	public String deleteEmployees(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of delete
		if(!Permission.checkPermission(this, MethodCode.delete)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
				}
		//-----------------------------------------------------------------

		
		if(this.EmployeeIDList == null || this.EmployeeIDList.size()==0)
		{
			this.message="传入参数缺少!";
			this.success=false;
			return "success";
		}
		EmployeeProfileDAO epdao=null;
		MysqlTools mysqlTools=null;
		try{
			mysqlTools=new MysqlTools();
			epdao=new EmployeeProfileDAO();
			epdao.initiate(mysqlTools);
			for(int i=0;i<EmployeeIDList.size();i++)
			{
				if(!epdao.delete(EmployeeIDList.get(i)))
				{
					this.message+="删除用户："+EmployeeIDList.get(i)+" 失败!";
				}
			}
			mysqlTools.commit();
			this.message="成功!";
			this.success=true;
			return "success";

		}catch(Exception e){
			mysqlTools.rollback();
			Tools.printErr(e.getMessage());
			this.message="删除用户失败!";
			this.success=false;
			return "success";
		}
		finally{
			mysqlTools.close();
		}
	}
	
	public String updateEmployee(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of update
		if(!Permission.checkPermission(this, MethodCode.update)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
				}
		//---------------------------------------------------------------

		if(this.EmployeeID==null||this.Name==null || this.Name.length()==0 || this.Department==null
				||this.Department.length()==0||this.Position==null||this.Position.length()==0)
		{
			this.message="传入参数缺少";
			this.success=false;
			return "success";
		}
		if(this.CellPhone==null) this.CellPhone="";
		if(this.Email==null) this.Email="";
		if(this.IDCard==null) this.IDCard="";
		if(this.Remarks==null) this.Remarks="";
		
		EmployeeProfileDAO epdao=null;
		EmployeeProfileDTO epdto=null;
		
		epdto=new EmployeeProfileDTO();
		epdto.setCellPhone(this.CellPhone);
		epdto.setDepartment(this.Department);
		epdto.setEID(this.EmployeeID);
		epdto.setEmail(this.Email);
		epdto.setIDCard(this.IDCard);
		epdto.setName(this.Name);
		epdto.setPosition(this.Position);
		epdto.setRemarks(this.Remarks);
		MysqlTools mysqlTools=null;
		try{
			mysqlTools=new MysqlTools();
			epdao=new EmployeeProfileDAO();
			epdao.initiate(mysqlTools);
			if(epdao.update(epdto)){
				mysqlTools.commit();
				this.message="成功";
				this.success=true;
				return "success";
			}
			else{
				mysqlTools.rollback();
				this.message="修改用户失败!";
				this.success=false;
				return "success";
			}
		}catch(Exception e){
			Tools.printErr(e.getMessage());
			mysqlTools.rollback();
			this.message="修改用户失败!";
			this.success=false;
			return "success";
		}
		finally{
			mysqlTools.close();
		}
	}
	
	public String queryAEmployee(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of query
		if(!Permission.checkPermission(this, MethodCode.query)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
				}
		//---------------------------------------------------------------

		//判断传入参数合法性
		if(EmployeeID==null)
		{
			this.message="传入参数缺少";
			this.success=false;
			return "success";
		}
		//申明对象
		EmployeeProfileDAO epdao=null;
		EmployeeProfileDTO epdto=null;
		MysqlTools mysqlTools=null;
		//建立对象
		try{
			mysqlTools=new MysqlTools();
			//创建UserDAO,完成初始化
			epdao=new EmployeeProfileDAO();
			epdao.initiate(mysqlTools);
			//查询
			epdto=epdao.getDTOByEID(EmployeeID);
			mysqlTools.commit();
		}catch(Exception e){
			mysqlTools.rollback();
			e.printStackTrace();
			this.message="获取用户失败!";
			this.success=false;
			return "success";
		}
		finally{
			mysqlTools.close();
		}
		//处理结果
		if(epdto==null)
		{
			this.message="无此用户";
			this.success=false;
			return "success";
		}
		else
		{
			
			this.data=new HashMap();
			this.data.put("EmployeeID", epdto.getEID());
			this.data.put("Name", epdto.getName());
			this.data.put("Department", epdto.getDepartment());
			this.data.put("Position", epdto.getPosition());
			this.data.put("CellPhone", epdto.getCellPhone());
			this.data.put("Email",epdto.getEmail());
			this.data.put("IDCard",epdto.getIDCard());
			this.data.put("Remarks",epdto.getRemarks());
			
			this.message="成功";
			this.success=true;
			return "success";
		}
	}
	
	
	public String insertEmployee(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of insert
		if(!Permission.checkPermission(this, MethodCode.insert)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
				}
		//---------------------------------------------------------------

		
		if(this.Name==null || this.Name.length()==0
			|| this.Department==null || this.Department.length()==0
				|| this.Position==null || this.Position.length()==0
			)
		{
			this.message="传入参数缺少";
			this.success=false;
			return "success";
		}
		if(this.CellPhone==null)	this.CellPhone="";
		if(this.Email==null) this.Email="";
		if(this.IDCard==null) this.IDCard="";
		if(this.Remarks==null) this.Remarks="";
		EmployeeProfileDAO epdao=null;
		EmployeeProfileDTO epdto=null;
		MysqlTools mysqlTools=null;
		epdto=new EmployeeProfileDTO();
		epdto.setCellPhone(this.CellPhone);
		epdto.setDepartment(this.Department);
		epdto.setEID(null);
		epdto.setEmail(this.Email);
		epdto.setIDCard(this.IDCard);
		epdto.setName(this.Name);
		epdto.setPosition(this.Position);
		epdto.setRemarks(this.Remarks);
		try{
			mysqlTools=new MysqlTools();
			epdao=new EmployeeProfileDAO();
			epdao.initiate(mysqlTools);
			if(!epdao.insert(epdto)){
				this.message="新增员工信息失败,请检查后重新操作";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="操作成功";
			this.success=true;
			return "success";
		}catch(Exception e){
			mysqlTools.rollback();
			Tools.printErr(e.getMessage());
			this.message="新增员工信息失败,请检查后重新操作";
			this.success=false;
			return "success";
		}
		finally{
			try{
				mysqlTools.close();
			}
			catch(Exception e){
				Tools.printErr(e.getMessage());
				Tools.printErr("无法关闭系统资源");
			}
		}
		
	}
	
	public String queryEmployee(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//判断传入参数合法性
		if(this.start<0) start=0;
		if(this.limit<0)	limit=0;
		//申明对象
		EmployeeProfileDAO epdao=null;
		EmployeeProfileDTO epdto=null;
		ArrayList<EmployeeProfileDTO> res=null;
		MysqlTools mysqlTools=null;
		//建立对象
		try{
			//创建DAO,完成初始化
			mysqlTools=new MysqlTools();
			epdao=new EmployeeProfileDAO();
			epdao.initiate(mysqlTools);
			//查询
			this.qualifiedAmount=epdao.getCountByConditions(this.EmployeeID,this.Name,this.Department,this.Position);
			res=epdao.getByConditions(this.EmployeeID,this.Name,this.Department,this.Position, start, limit);
			Tools.print(""+res.size()+"|"+this.qualifiedAmount);
			mysqlTools.commit();
		}catch(Exception e){
			mysqlTools.rollback();
			Tools.printErr(e.getMessage());
			this.message="操作失败!";
			this.success=false;
			return "success";
		}
		finally{
			mysqlTools.close();
		}
		//处理结果
		employeeList=new ArrayList<Map>();
		for(int i=0;i<res.size();i++){
			Map m=null;
			m=new HashMap();
			m.put("EmployeeID",res.get(i).getEID());
			m.put("Name", res.get(i).getName());
			m.put("Department", res.get(i).getDepartment());
			m.put("Position", res.get(i).getPosition());
			m.put("CellPhone", res.get(i).getCellPhone());
			m.put("Email", res.get(i).getEmail());
			m.put("IDCard", res.get(i).getIDCard());
			m.put("Remarks", res.get(i).getRemarks());
			employeeList.add(m);
		}
		this.message="成功!";
		this.success=true;
		return "success";
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
	public Integer getEmployeeID() {
		return EmployeeID;
	}
	public void setEmployeeID(Integer employeeID) {
		this.EmployeeID = employeeID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		this.Department = department;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		this.Position = position;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getQualifiedAmount() {
		return qualifiedAmount;
	}
	public void setQualifiedAmount(int qualifiedAmount) {
		this.qualifiedAmount = qualifiedAmount;
	}
	public Map getData() {
		return data;
	}
	public void setData(Map data) {
		this.data = data;
	}
	public ArrayList<Map> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(ArrayList<Map> employeeList) {
		this.employeeList = employeeList;
	}
	public String getCellPhone() {
		return CellPhone;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
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

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public ArrayList<Integer> getEmployeeIDList() {
		return EmployeeIDList;
	}

	public void setEmployeeIDList(ArrayList<Integer> employeeIDList) {
		EmployeeIDList = employeeIDList;
	}

	

}
