package Logistics.Servlet;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Logistics.Common.Permission;
import Logistics.Common.Tools;
import Logistics.Common.Permission.MethodCode;
import Logistics.DAO.*;
import Logistics.DTO.*;
public class EmployeeSalaryAction {
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private EmployeeSalaryDAO esdao=null;
	private EmployeeProfileDAO epdao=null;
	//DTO对象
	private EmployeeSalaryDTO esdto=null;
	private EmployeeProfileDTO epdto=null;
	//输入对象
	private Date startDate=null;
	private Date endDate=null;
	ArrayList<Integer> employeeSalaryIDList=new ArrayList<Integer>();
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public EmployeeSalaryAction() {
		
		esdao=new EmployeeSalaryDAO();
		epdao=new EmployeeProfileDAO();
		esdto= new EmployeeSalaryDTO();
		epdto= new EmployeeProfileDTO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		esdao.initiate(mysqlTools);
		epdao.initiate(mysqlTools);
		
	}
	
	public String deleteEmployeeSalary(){
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

		
		if(this.employeeSalaryIDList==null || this.employeeSalaryIDList.size()==0)
		{
			this.message="缺少工资记录编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			for(int i=0;i<this.employeeSalaryIDList.size();i++)
			{
				
				if(!esdao.delete(this.employeeSalaryIDList.get(i))){
					mysqlTools.rollback();
					this.message+="删除员工工资记录失败！";
					this.success=false;
					return "success";
				}
				
			}
			mysqlTools.commit();
			this.message="删除工资记录成功！";
			this.success=true;
			return "success";
			
		}catch(Exception e){
			mysqlTools.rollback();
			Tools.printErr(e.getMessage());
			this.message="操作失败！";
			this.success=false;
			return "success";
		}
		finally{
			mysqlTools.close();
		}
	}
	
	public String updateEmployeeSalary(){
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

		if(esdto.getESID()==null)
		{
			this.message="缺少工资记录号,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			epdto=epdao.getDTOByEID(esdto.getEmployeeID());
			if(epdto==null)
			{
				this.message="没有该员工的信息,请重试！";
				this.success=false;
				return "success";
			}
			if(!esdao.update(esdto))
			{
				mysqlTools.rollback();
				this.message="修改工资记录失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="修改工资记录成功！";
			this.success=true;
			return "success";
			
		}catch(Exception e){
			mysqlTools.rollback();
			e.printStackTrace();
			this.message="操作失败！";
			this.success=false;
			return "success";
		}
		finally{
			mysqlTools.close();
		}
	}
	
	public String queryASalary(){
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

		
		if(esdto.getESID()==null)
		{
			this.message="缺少工资记录号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			esdto=esdao.getDTOByID(esdto.getESID());
			if(esdto==null)
			{
				mysqlTools.rollback();
				this.message="查找工资记录失败！";
				this.success=false;
				return "success";
			}
			epdto=epdao.getDTOByEID(esdto.getEmployeeID());
			if(epdto==null)
			{
				mysqlTools.rollback();
				this.message="此工资记录中的员工信息有误！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("esdto.ESID", esdto.getESID());
			this.data.put("esdto.EmployeeID", esdto.getEmployeeID());
			this.data.put("epdto.Name", epdto.getName());
			this.data.put("epdto.Department", epdto.getDepartment());
			this.data.put("epdto.Position", epdto.getPosition());
			
			this.data.put("esdto.PayDay", esdto.getPayDay().toString());
			this.data.put("esdto.PostSalary", esdto.getPostSalary());
			this.data.put("esdto.PerformanceSalary", esdto.getPerformanceSalary());
			this.data.put("esdto.OvertimeSalary", esdto.getOvertimeSalary());
			this.data.put("esdto.PostAllowance", esdto.getPostAllowance());
			this.data.put("esdto.BasicBonus", esdto.getBasicBonus());
			this.data.put("esdto.AnnualBonus", esdto.getAnnualBonus());
			this.data.put("esdto.TransportAllowance", esdto.getTransportAllowance());
			this.data.put("esdto.CommunicationAllowance", esdto.getCommunicationAllowance());
			this.data.put("esdto.MealAllowance", esdto.getMealAllowance());
			this.data.put("esdto.ExtraMoney", esdto.getExtraMoney());
			this.data.put("esdto.DeductionMoney", esdto.getDeductionMoney());
			this.data.put("esdto.OtherPayment", esdto.getOtherPayment());
			this.data.put("esdto.Remarks", esdto.getRemarks());
			
			this.message="成功";
			this.success=true;
			return "success";
			
		}catch(Exception e){
			mysqlTools.rollback();
			Tools.printErr(e.getMessage());
			this.message="操作失败！";
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
	
	public String insertEmployeeSalary(){
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

		if(esdto.getEmployeeID()==null)
		{
			this.message="缺少工号！";
			this.success=false;
			return "success";
		}
		try{
			epdto=epdao.getDTOByEID(esdto.getEmployeeID());
			if(epdto==null)
			{
				this.message="没有该员工的信息！";
				this.success=false;
				return "success";
			}
			if(!esdao.insert(esdto))
			{
				mysqlTools.rollback();
				this.message="新增工资记录失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="新增工资记录成功！";
			this.success=true;
			return "success";
			
		}catch(Exception e){
			mysqlTools.rollback();
			Tools.printErr(e.getMessage());
			this.message="操作失败！";
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
	public String queryAEmployeeProfile(){
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
		if(epdto.getEID()==null)
		{
			this.message="传入参数缺少";
			this.success=false;
			return "success";
		}
		try{
			//查询
			epdto=epdao.getDTOByEID(epdto.getEID());
			//处理结果
			if(epdto==null)
			{
				mysqlTools.rollback();
				this.message="无此用户";
				this.success=false;
				return "success";
			}
			else
			{
				mysqlTools.commit();
				this.data=new HashMap();
				this.data.put("Name", epdto.getName());
				this.data.put("Department", epdto.getDepartment());
				this.data.put("Position", epdto.getPosition());
				this.message="成功";
				this.success=true;
				return "success";
			}
		}catch(Exception e){
			mysqlTools.rollback();
			e.printStackTrace();
			this.message="操作失败!";
			this.success=false;
			return "success";
		}
		finally{
			mysqlTools.close();
		}
		
	}
	
	public String queryAllEmployeesName(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		try{
			//查询
			ArrayList<EmployeeProfileDTO> res=new ArrayList<EmployeeProfileDTO>();
			res=epdao.getAllEPs();
			
			resultMapList=new ArrayList<Map>();
			for(int i=0;i<res.size();i++){
				Map m=null;
				m=new HashMap();
				m.put("EmployeeID",res.get(i).getEID());
				m.put("NameID", res.get(i).getName()+"("+res.get(i).getEID()+")");
				resultMapList.add(m);
			}
			mysqlTools.commit();
			this.message="成功!";
			this.success=true;
			return "success";
		}catch(Exception e){
			mysqlTools.rollback();
			e.printStackTrace();
			this.message="操作失败!";
			this.success=false;
			return "success";
		}
		finally{
			if(mysqlTools!=null)
				mysqlTools.close();
			mysqlTools=null;
		}
	}
	
	public String queryEmployeeSalary(){
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
		if(this.start<0) start=0;
		if(this.limit<0)	limit=0;
		ArrayList<EmployeeSalaryDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=esdao.queryAmountByCondition(esdto.getEmployeeID()
					, epdto.getName(), startDate, endDate);
			res=esdao.queryByCondition(esdto.getEmployeeID(), epdto.getName()
					, startDate, endDate, start, limit);
			Tools.print(""+res.size()+"|"+this.qualifiedAmount);
			if(res==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			resultMapList=new ArrayList<Map>();
			for(int i=0;i<res.size();i++){
				Map m=null;
				m=new HashMap();
				m.put("ESID",res.get(i).getESID());
				m.put("EmployeeID",res.get(i).getEPDTO().getEID());
				m.put("Name", res.get(i).getEPDTO().getName());
				m.put("Department", res.get(i).getEPDTO().getDepartment());
				m.put("Position", res.get(i).getEPDTO().getPosition());
				m.put("TotalSalary", res.get(i).countTotalSalry());
				m.put("Remarks", res.get(i).getRemarks());
				m.put("PayDay", res.get(i).getPayDay().toString());
				resultMapList.add(m);
			}
			
			this.message="成功!";
			this.success=true;
			return "success";
		}catch(Exception e){
			mysqlTools.rollback();
			e.printStackTrace();
			this.message="操作失败!";
			this.success=false;
			return "success";
		}
		finally{
			if(mysqlTools!=null)
				mysqlTools.close();
			mysqlTools=null;
		}
	}

	public MysqlTools getMysqlTools() {
		return mysqlTools;
	}

	public void setMysqlTools(MysqlTools mysqlTools) {
		this.mysqlTools = mysqlTools;
	}

	public EmployeeSalaryDAO getEsdao() {
		return esdao;
	}

	public void setEsdao(EmployeeSalaryDAO esdao) {
		this.esdao = esdao;
	}

	public EmployeeProfileDAO getEpdao() {
		return epdao;
	}

	public void setEpdao(EmployeeProfileDAO epdao) {
		this.epdao = epdao;
	}

	public EmployeeSalaryDTO getEsdto() {
		return esdto;
	}

	public void setEsdto(EmployeeSalaryDTO esdto) {
		this.esdto = esdto;
	}

	public EmployeeProfileDTO getEpdto() {
		return epdto;
	}

	public void setEpdto(EmployeeProfileDTO epdto) {
		this.epdto = epdto;
	}

	

	public ArrayList<Map> getResultMapList() {
		return resultMapList;
	}

	public void setResultMapList(ArrayList<Map> resultMapList) {
		this.resultMapList = resultMapList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getQualifiedAmount() {
		return qualifiedAmount;
	}

	public void setQualifiedAmount(int qualifiedAmount) {
		this.qualifiedAmount = qualifiedAmount;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public ArrayList<Integer> getEmployeeSalaryIDList() {
		return employeeSalaryIDList;
	}

	public void setEmployeeSalaryIDList(ArrayList<Integer> employeeSalaryIDList) {
		this.employeeSalaryIDList = employeeSalaryIDList;
	}
	
	
}
