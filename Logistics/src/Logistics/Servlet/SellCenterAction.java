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

public class SellCenterAction {
	
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private SellCenterDAO sellCenterDAO=null;
	//DTO对象
	private SellCenterDTO sellCenterDTO=null;
	//输入对象
	ArrayList<String> sellCenterList=new ArrayList<String>();
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public SellCenterAction(){
		sellCenterDAO=new SellCenterDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		sellCenterDAO.initiate(mysqlTools);
		sellCenterDTO=new SellCenterDTO();
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
		if(sellCenterDTO==null)
		{
			this.message="缺少查询条件！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0)	limit=0;
		ArrayList<SellCenterDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=sellCenterDAO.queryQualifiedAmount(null);
			res=sellCenterDAO.queryOnCondition(null, start, limit);
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
				m.put("sellCenterID",res.get(i).getSellCenterID());
				
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

		
		if(sellCenterDTO==null ||sellCenterDTO.getSellCenterID()==null || sellCenterDTO.getSellCenterID().length()==0)
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		try{
			
			
			if(!sellCenterDAO.insert(sellCenterDTO))
			{
				mysqlTools.rollback();
				this.message="新增销售中心失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="新增销售中心成功！";
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

		
		if(sellCenterDTO==null || sellCenterDTO.getSellCenterID()==null )
		{
			this.message="缺少销售中心编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			sellCenterDTO=sellCenterDAO.getDTOByID(sellCenterDTO);
			if(sellCenterDTO==null)
			{
				mysqlTools.rollback();
				this.message="查找销售中心失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("sellCenterDTO.sellCenterID", sellCenterDTO.getSellCenterID());
			
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
		return "success";
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

		
		if(this.sellCenterList==null || this.sellCenterList.size()==0)
		{
			this.message="缺少销售中心编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			for(int i=0;i<this.sellCenterList.size();i++)
			{
				sellCenterDTO.setSellCenterID(this.sellCenterList.get(i));
				if(!sellCenterDAO.delete(sellCenterDTO)){
					mysqlTools.rollback();
					this.message+="删除销售中心信息失败！";
					this.success=false;
					return "success";
				}
			}
			mysqlTools.commit();
			this.message="删除销售中心信息成功！";
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

		ArrayList<SellCenterDTO> res=null;
		try{
			//查询
			res=sellCenterDAO.queryNameID();
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
				m.put("sellCenterID",res.get(i).getSellCenterID());
				if("西安S2001".equals(res.get(i).getSellCenterID())){
					resultMapList.add(0, m);
				}
				else{
					resultMapList.add(m);
				}
				
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
	public SellCenterDAO getSellCenterDAO() {
		return sellCenterDAO;
	}
	public void setSellCenterDAO(SellCenterDAO sellCenterDAO) {
		this.sellCenterDAO = sellCenterDAO;
	}
	public SellCenterDTO getSellCenterDTO() {
		return sellCenterDTO;
	}
	public void setSellCenterDTO(SellCenterDTO sellCenterDTO) {
		this.sellCenterDTO = sellCenterDTO;
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
	public ArrayList<String> getSellCenterList() {
		return sellCenterList;
	}
	public void setSellCenterList(ArrayList<String> sellCenterList) {
		this.sellCenterList = sellCenterList;
	}
	
	

}
