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

public class CarTypeAction {
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private CarTypeDAO carTypeDAO=null;
	//DTO对象
	private CarTypeDTO carTypeDTO=null;
	//输入对象
	ArrayList<Integer> carTypeIDList=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public CarTypeAction(){
		carTypeDAO=new CarTypeDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		carTypeDAO.initiate(mysqlTools);
		carTypeDTO=new CarTypeDTO();
		carTypeIDList=new ArrayList<Integer>();
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

		//判断传入参数合法性
		if(carTypeDTO==null)
		{
			Tools.printErr("严重的错误"+this.getClass().toString());
			this.message="请求无法完成！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0)	limit=0;
		ArrayList<CarTypeDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=carTypeDAO.queryAmountByCondition();
			res=carTypeDAO.queryByCondition(start, limit);
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
				m.put("carTypeID",res.get(i).getCarTypeID());
				m.put("carTypeName",res.get(i).getCarTypeName());
				m.put("remarks", res.get(i).getRemarks());
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

		if(carTypeDTO==null || carTypeDTO.getCarTypeName()==null ||carTypeDTO.getCarTypeName().length()==0)
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		try{
			
			
			if(!carTypeDAO.insert(carTypeDTO))
			{
				mysqlTools.rollback();
				this.message="新增车辆类型失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="新增车辆类型成功！";
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

		
		if(carTypeDTO==null || carTypeDTO.getCarTypeID()==null )
		{
			this.message="缺少车辆类型编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			carTypeDTO=carTypeDAO.getDTOByID(carTypeDTO.getCarTypeID());
			if(carTypeDTO==null)
			{
				mysqlTools.rollback();
				this.message="查找车辆类型失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("carTypeDTO.carTypeID", carTypeDTO.getCarTypeID());
			this.data.put("carTypeDTO.carTypeName", carTypeDTO.getCarTypeName());
			this.data.put("carTypeDTO.remarks", carTypeDTO.getRemarks());
			this.message="成功";
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

		
		if(carTypeDTO==null||carTypeDTO.getCarTypeID()==null || carTypeDTO.getCarTypeName()==null ||carTypeDTO.getCarTypeName().length()==0
				)
		{
			this.message="缺少必要信息,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			if(!carTypeDAO.update(carTypeDTO))
			{
				mysqlTools.rollback();
				this.message="修改车辆类型失败!";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="修改车辆类型成功！";
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
			if(mysqlTools!=null)
			mysqlTools.close();
		}
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

		
		if(this.carTypeIDList==null || this.carTypeIDList.size()==0)
		{
			this.message="缺少车辆类型编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			for(int i=0;i<this.carTypeIDList.size();i++)
			{
				if(!carTypeDAO.delete(this.carTypeIDList.get(i))){
					mysqlTools.rollback();
					this.message+="删除运输车辆类型信息失败！";
					this.success=false;
					return "success";
				}
				
			}
			mysqlTools.commit();
			this.message="删除运输车辆类型信息成功！";
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
			if(mysqlTools!=null)
				mysqlTools.close();
		}
	}
	
	public String queryNameID(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		ArrayList<CarTypeDTO> res=null;
		try{
			//查询
			res=carTypeDAO.queryNameID();
			mysqlTools.commit();
			if(res==null)
			{
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			resultMapList=new ArrayList<Map>();
			for(int i=0;i<res.size();i++){
				Map m=null;
				m=new HashMap();
				m.put("carTypeID",res.get(i).getCarTypeID());
				m.put("carTypeName",res.get(i).getCarTypeName());
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

	public CarTypeDAO getCarTypeDAO() {
		return carTypeDAO;
	}

	public void setCarTypeDAO(CarTypeDAO carTypeDAO) {
		this.carTypeDAO = carTypeDAO;
	}

	public CarTypeDTO getCarTypeDTO() {
		return carTypeDTO;
	}

	public void setCarTypeDTO(CarTypeDTO carTypeDTO) {
		this.carTypeDTO = carTypeDTO;
	}

	public ArrayList<Integer> getCarTypeIDList() {
		return carTypeIDList;
	}

	public void setCarTypeIDList(ArrayList<Integer> carTypeIDList) {
		this.carTypeIDList = carTypeIDList;
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
