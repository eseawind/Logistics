package Logistics.Servlet;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Logistics.Common.Permission;
import Logistics.Common.Tools;
import Logistics.Common.Permission.MethodCode;
import Logistics.DAO.EmployeeProfileDAO;
import Logistics.DAO.EmployeeSalaryDAO;
import Logistics.DAO.MysqlTools;
import Logistics.DTO.CityDTO;
import Logistics.DTO.EmployeeProfileDTO;
import Logistics.DTO.EmployeeSalaryDTO;
import Logistics.DTO.WarehouseDTO;
import Logistics.DAO.*;

public class WarehouseAction {

	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private WarehouseDAO warehouseDAO=null;
	private CityDAO cityDAO=null;
	//DTO对象
	private WarehouseDTO warehouseDTO=null;
	private CityDTO cityDTO=null;
	//输入对象
	ArrayList<String> warehouseIDList=new ArrayList<String>();
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public WarehouseAction(){
		warehouseDAO=new WarehouseDAO();
		cityDAO=new CityDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		warehouseDAO.initiate(mysqlTools);
		warehouseDTO=new WarehouseDTO();
		cityDTO=new CityDTO();
	}
	
	public String delete(){
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

		if(this.warehouseIDList==null || this.warehouseIDList.size()==0)
		{
			this.message="缺少城市编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			for(int i=0;i<this.warehouseIDList.size();i++)
			{
				if(!warehouseDAO.delete(this.warehouseIDList.get(i))){
					mysqlTools.rollback();
					this.message+="删除城市信息失败！";
					this.success=false;
					return "success";
				}
				
			}
			mysqlTools.commit();
			this.message="删除城市信息成功！";
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
			if(mysqlTools!=null)
				mysqlTools.close();
		}
	}
	
	public String update(){
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

		
		if(warehouseDTO==null || warehouseDTO.getCityID()==null || warehouseDTO.getCityID().length()==0
				|| warehouseDTO.getWarehouseID()==null || warehouseDTO.getWarehouseID().length()==0||
				warehouseDTO.getName()==null || warehouseDTO.getName().length()==0)
		{
			this.message="缺少必要信息,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			
			if(!warehouseDAO.update(warehouseDTO))
			{
				mysqlTools.rollback();
				this.message="修改仓库信息失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="修改仓库信息成功！";
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
			if(mysqlTools!=null)
			mysqlTools.close();
		}
	}
	
	public String queryOne(){
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

		if(warehouseDTO==null || warehouseDTO.getWarehouseID()==null || warehouseDTO.getWarehouseID().length()==0)
		{
			this.message="缺少仓库编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			warehouseDTO=warehouseDAO.getDTOByID(warehouseDTO.getWarehouseID());
			if(warehouseDTO==null)
			{
				mysqlTools.rollback();
				this.message="查找城市失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("warehouseDTO.cityID", warehouseDTO.getCityID());
			this.data.put("warehouseDTO.name", warehouseDTO.getName());
			this.data.put("warehouseDTO.address", warehouseDTO.getAddress());
			this.data.put("warehouseDTO.warehouseID", warehouseDTO.getWarehouseID());
			this.data.put("warehouseDTO.remarks", warehouseDTO.getRemarks());
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
			if(mysqlTools!=null)
				mysqlTools.close();
		}
	}
	
	public String insert(){
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

		if(warehouseDTO==null || warehouseDTO.getWarehouseID()==null ||warehouseDTO.getWarehouseID().length()==0|| warehouseDTO.getName()==null ||
				warehouseDTO.getName().length()==0 || warehouseDTO.getCityID()==null || warehouseDTO.getCityID().length()==0)
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		if(warehouseDTO.getWarehouseID().length()>6)
		{
			this.message="仓库编号过长，应当不超过6位！";
			this.success=false;
			return "success";
		}
		if(warehouseDTO.getCityID().length()>4)
		{
			this.message="城市编号过长，应当不超过4位！";
			this.success=false;
			return "success";
		}
		try{
			WarehouseDTO wdto=null;
			wdto=warehouseDAO.getDTOByID(warehouseDTO.getWarehouseID());
			if(wdto!=null)
			{
				this.message="已经有仓库编号："+wdto.getWarehouseID()+" 存在！";
				this.success=false;
				return "success";
			}
			if(!warehouseDAO.insert(warehouseDTO))
			{
				mysqlTools.rollback();
				this.message="新增仓库失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="新增仓库成功！";
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
	
	public String queryOnCondition(){
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

		Tools.print("queryEmployeeSalary");
		//判断传入参数合法性
		if(this.start<0) start=0;
		if(this.limit<0)	limit=0;
		ArrayList<WarehouseDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=warehouseDAO.queryAmountByCondition();
			res=warehouseDAO.queryByCondition(start, limit);
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
				m.put("warehouseID",res.get(i).getWarehouseID());
				m.put("name",res.get(i).getName());
				m.put("remarks", res.get(i).getRemarks());
				m.put("address", res.get(i).getAddress());
				m.put("jointName", res.get(i).getCityDTO().getJointName());
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
		}
	}
	public String queryNames(){
		
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		
		ArrayList<WarehouseDTO> res=null;
		try{
			//查询
			res=warehouseDAO.queryNames();
			if(res==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			resultMapList=new ArrayList<Map>();
			for(WarehouseDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("warehouseID",iter.getWarehouseID());
				m.put("name",iter.getName());
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
		}
	}
	public MysqlTools getMysqlTools() {
		return mysqlTools;
	}

	public void setMysqlTools(MysqlTools mysqlTools) {
		this.mysqlTools = mysqlTools;
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

	public WarehouseDAO getWarehouseDAO() {
		return warehouseDAO;
	}

	public void setWarehouseDAO(WarehouseDAO warehouseDAO) {
		this.warehouseDAO = warehouseDAO;
	}

	public CityDAO getCityDAO() {
		return cityDAO;
	}

	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	public WarehouseDTO getWarehouseDTO() {
		return warehouseDTO;
	}

	public void setWarehouseDTO(WarehouseDTO warehouseDTO) {
		this.warehouseDTO = warehouseDTO;
	}

	public CityDTO getCityDTO() {
		return cityDTO;
	}

	public void setCityDTO(CityDTO cityDTO) {
		this.cityDTO = cityDTO;
	}



	public ArrayList<String> getWarehouseIDList() {
		return warehouseIDList;
	}

	public void setWarehouseIDList(ArrayList<String> warehouseIDList) {
		this.warehouseIDList = warehouseIDList;
	}

	public ArrayList<Map> getResultMapList() {
		return resultMapList;
	}

	public void setResultMapList(ArrayList<Map> resultMapList) {
		this.resultMapList = resultMapList;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}
	
	
}
