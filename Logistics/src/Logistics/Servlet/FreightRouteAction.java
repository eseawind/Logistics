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

public class FreightRouteAction {
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private FreightRouteDAO freightRouteDAO=null;
	private CityDAO cityDAO=null;
	//DTO对象
	private FreightRouteDTO freightRouteDTO=null;
	private CityDTO cityDTO=null;
	//输入对象
	ArrayList<Integer> freightRouteIDList=new ArrayList<Integer>();
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public FreightRouteAction(){
		freightRouteDAO=new FreightRouteDAO();
		cityDAO=new CityDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		freightRouteDAO.initiate(mysqlTools);
		cityDAO.initiate(mysqlTools);
		freightRouteDTO=new FreightRouteDTO();
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

		
		if(this.freightRouteIDList==null || this.freightRouteIDList.size()==0)
		{
			this.message="缺少路线编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			for(int i=0;i<this.freightRouteIDList.size();i++)
			{
				if(!freightRouteDAO.delete(this.freightRouteIDList.get(i))){
					mysqlTools.rollback();
					this.message+="删除运输路线信息失败！";
					this.success=false;
					return "success";
				}
				
			}
			mysqlTools.commit();
			this.message="删除运输路线信息成功！";
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

		
		if(freightRouteDTO==null||freightRouteDTO.getFreightRouteID()==null || freightRouteDTO.getOriginID()==null ||freightRouteDTO.getOriginID().length()==0||
				freightRouteDTO.getDestinationID()==null ||	freightRouteDTO.getDestinationID().length()==0)
		{
			this.message="缺少必要信息,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			CityDTO cdto=null;
			cdto=cityDAO.getDTOByID(freightRouteDTO.getOriginID());
			if(cdto==null)
			{
				this.message="始发地不存在！";
				this.success=false;
				return "success";
			}
			cdto=cityDAO.getDTOByID(freightRouteDTO.getDestinationID());
			if(cdto==null)
			{
				this.message="目的地不存在！";
				this.success=false;
				return "success";
			}
			if(!freightRouteDAO.update(freightRouteDTO))
			{
				mysqlTools.rollback();
				this.message="修改运输路线信息失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="修改运输路线信息成功！";
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

		if(freightRouteDTO==null || freightRouteDTO.getFreightRouteID()==null )
		{
			this.message="缺少运输路线编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			freightRouteDTO=freightRouteDAO.getDTOByID(freightRouteDTO.getFreightRouteID());
			if(freightRouteDTO==null)
			{
				mysqlTools.rollback();
				this.message="查找运输路线失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("freightRouteDTO.freightRouteID", freightRouteDTO.getFreightRouteID());
			this.data.put("freightRouteDTO.originID", freightRouteDTO.getOriginID());
			this.data.put("freightRouteDTO.destinationID", freightRouteDTO.getDestinationID());
			this.data.put("freightRouteDTO.daysSpent", freightRouteDTO.getDaysSpent());
			this.data.put("freightRouteDTO.remarks", freightRouteDTO.getRemarks());
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

		
		if(freightRouteDTO==null || freightRouteDTO.getOriginID()==null ||freightRouteDTO.getOriginID().length()==0||
				freightRouteDTO.getDestinationID()==null ||	freightRouteDTO.getDestinationID().length()==0)
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		if(freightRouteDTO.getDaysSpent()<0)
		{
			this.message="预计运输天数小于0！";
			this.success=false;
			return "success";
		}
		try{
			CityDTO cdto=null;
			cdto=cityDAO.getDTOByID(freightRouteDTO.getOriginID());
			if(cdto==null)
			{
				this.message="始发地不存在！";
				this.success=false;
				return "success";
			}
			cdto=cityDAO.getDTOByID(freightRouteDTO.getDestinationID());
			if(cdto==null)
			{
				this.message="目的地不存在！";
				this.success=false;
				return "success";
			}
			if(!freightRouteDAO.insert(freightRouteDTO))
			{
				mysqlTools.rollback();
				this.message="新增运输路线失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="新增运输路线成功！";
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
		if(freightRouteDTO==null)
		{
			this.message="缺少查询条件！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0)	limit=0;
		ArrayList<FreightRouteDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=freightRouteDAO.queryAmountByCondition(freightRouteDTO.getFreightRouteID(), freightRouteDTO.getOriginID(), freightRouteDTO.getDestinationID());
			res=freightRouteDAO.queryByCondition(freightRouteDTO.getFreightRouteID(), freightRouteDTO.getOriginID(), freightRouteDTO.getDestinationID(), start, limit);
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
				m.put("freightRouteID",res.get(i).getFreightRouteID());
				m.put("originID",res.get(i).getOriginID());
				m.put("originJointName",res.get(i).getOriginCityDTO().getJointName());
				m.put("destinationID",res.get(i).getDestinationID());
				m.put("destinationJointName",res.get(i).getDestinationCityDTO().getJointName());
				m.put("daysSpent",res.get(i).getDaysSpent());
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
	
	public String queryNameID(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		ArrayList<FreightRouteDTO> res=null;
		try{
			//查询
			res=freightRouteDAO.queryNameID();
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
				m.put("freightRouteID",res.get(i).getFreightRouteID());
				m.put("jointName", res.get(i).getOriginID()+"-"+res.get(i).getDestinationID()+"("
						+res.get(i).getOriginCityDTO().getProvince()+"-"+res.get(i).getOriginCityDTO().getName()+"-"+res.get(i).getDestinationCityDTO().getName()+"-"+
						res.get(i).getDestinationCityDTO().getProvince()+")");
//				m.put("jointName",res.get(i).getOriginCityDTO().getName()+"("+res.get(i).getOriginID()+")-"+res.get(i).getOriginCityDTO().getProvince()+"-"+
//						res.get(i).getDestinationCityDTO().getName()+"("+res.get(i).getDestinationID()+")-"+res.get(i).getDestinationCityDTO().getProvince());
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
	
	public String queryFreightDays()
	{
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//判断传入参数合法性
		if(freightRouteDTO==null || freightRouteDTO.getFreightRouteID()==null)
		{
			this.message="缺少查询条件！";
			this.success=false;
			return "success";
		}
		try{
			//查询
			freightRouteDTO=freightRouteDAO.getDTOByID(freightRouteDTO.getFreightRouteID());
			if(freightRouteDTO==null)
			{
				mysqlTools.rollback();
				this.message="获取运输路线预计天数信息失败!";
				this.success=false;
				return "success";
			}
			int days=freightRouteDTO.getDaysSpent();
			data=new HashMap();
			data.put("days", days);
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

	public FreightRouteDAO getFreightRouteDAO() {
		return freightRouteDAO;
	}

	public void setFreightRouteDAO(FreightRouteDAO freightRouteDAO) {
		this.freightRouteDAO = freightRouteDAO;
	}

	public CityDAO getCityDAO() {
		return cityDAO;
	}

	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	public FreightRouteDTO getFreightRouteDTO() {
		return freightRouteDTO;
	}

	public void setFreightRouteDTO(FreightRouteDTO freightRouteDTO) {
		this.freightRouteDTO = freightRouteDTO;
	}

	public CityDTO getCityDTO() {
		return cityDTO;
	}

	public void setCityDTO(CityDTO cityDTO) {
		this.cityDTO = cityDTO;
	}

	public ArrayList<Integer> getFreightRouteIDList() {
		return freightRouteIDList;
	}

	public void setFreightRouteIDList(ArrayList<Integer> freightRouteIDList) {
		this.freightRouteIDList = freightRouteIDList;
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
