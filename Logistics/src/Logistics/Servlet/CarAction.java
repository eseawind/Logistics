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

public class CarAction {
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private CarDAO carDAO=null;
	private CarTypeDAO carTypeDAO=null;
	private FreightContractorDAO freightContractorDAO=null;
	//DTO对象
	private CarDTO carDTO=null;
	private CarTypeDTO carTypeDTO=null;
	private FreightContractorDTO freightContractorDTO=null;
	//输入对象
	ArrayList<String> carIDList=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public CarAction(){
		carDAO=new CarDAO();
		this.carTypeDAO=new CarTypeDAO();
		this.freightContractorDAO=new FreightContractorDAO();
		
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		carDAO.initiate(mysqlTools);
		this.carTypeDAO.initiate(mysqlTools);
		this.freightContractorDAO.initiate(mysqlTools);
		carDTO=new CarDTO();
		carTypeDTO=new CarTypeDTO();
		freightContractorDTO=new FreightContractorDTO();
		carIDList=new ArrayList<String>();
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
		if(carDTO==null)
		{
			this.message="缺少查询条件！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0)	limit=0;
		ArrayList<CarDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=carDAO.queryAmountByCondition(carDTO.getCarID());
			res=carDAO.queryByCondition(carDTO.getCarID(),start, limit);
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
				m.put("carID",res.get(i).getCarID());
				m.put("driverName",res.get(i).getDriverName());
				m.put("ownerName", res.get(i).getOwnerName());
				m.put("carType", res.get(i).getCarTypeDTO().getCarTypeName());
				m.put("roadWorthyCertificateNo", res.get(i).getRoadWorthyCertificateNo());
				m.put("engineNo", res.get(i).getEngineNo());
				m.put("phone", res.get(i).getPhone());
				m.put("freightContractor", res.get(i).getFreightContractorDTO().getName());
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
		if(carDTO==null || carDTO.getCarID()==null ||carDTO.getCarID().length()==0
				||carDTO.getCarTypeID()==null ||carDTO.getFreightContractorID()==null)
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		try{
			if(carDAO.getDTOByID(carDTO.getCarID())!=null)
			{
				this.message="新增车辆失败,车牌号已经存在，请修改！";
				this.success=false;
				return "success";
			}
			if(carTypeDAO.getDTOByID(carDTO.getCarTypeID())==null)
			{
				Tools.print(""+carDTO.getCarTypeID());
				this.message="新增车辆失败,车辆类型不存在，请修改！";
				this.success=false;
				return "success";
			}
			if(this.freightContractorDAO.getDTOByID(carDTO.getFreightContractorID())==null)
			{
				this.message="新增车辆失败,承运单位不存在，请修改！";
				this.success=false;
				return "success";
			}
			if(!carDAO.insert(carDTO))
			{
				mysqlTools.rollback();
				this.message="新增车辆失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="新增车辆成功！";
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

		if(carDTO==null || carDTO.getCarID()==null  )
		{
			this.message="缺少车辆编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			carDTO=carDAO.getDTOByID(carDTO.getCarID());
			if(carDTO==null)
			{
				mysqlTools.rollback();
				this.message="查找车辆失败！";
				this.success=false;
				return "success";
			}
			carTypeDTO=carTypeDAO.getDTOByID(carDTO.getCarTypeID());
			freightContractorDTO=freightContractorDAO.getDTOByID(carDTO.getFreightContractorID());
			
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("carDTO.carID",carDTO.getCarID());
			this.data.put("carDTO.driverName",carDTO.getDriverName());
			this.data.put("carDTO.ownerName", carDTO.getOwnerName());
			this.data.put("carType", carTypeDTO.getCarTypeName());
			this.data.put("carDTO.carTypeID", carDTO.getCarTypeID());
			this.data.put("carDTO.vehicleIdentificationNo", carDTO.getVehicleIdentificationNo());
			this.data.put("carDTO.roadWorthyCertificateNo", carDTO.getRoadWorthyCertificateNo());
			this.data.put("carDTO.engineNo", carDTO.getEngineNo());
			this.data.put("carDTO.phone", carDTO.getPhone());
			this.data.put("freightContractor", freightContractorDTO.jointName());
			this.data.put("carDTO.freightContractorID", carDTO.getFreightContractorID());
			this.data.put("carDTO.remarks", carDTO.getRemarks());
			
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

		if(carDTO==null || carDTO.getCarID()==null ||carDTO.getCarID().length()==0
				||carDTO.getCarTypeID()==null ||carDTO.getFreightContractorID()==null)
		{
			this.message="缺少必要信息,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			if(carTypeDAO.getDTOByID(carDTO.getCarTypeID())==null)
			{
				this.message="修改车辆失败,车辆类型不存在，请修改！";
				this.success=false;
				return "success";
			}
			if(this.freightContractorDAO.getDTOByID(carDTO.getFreightContractorID())==null)
			{
				this.message="修改车辆失败,承运单位不存在，请修改！";
				this.success=false;
				return "success";
			}
			if(!carDAO.update(carDTO))
			{
				mysqlTools.rollback();
				this.message="修改车辆失败!";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="修改车辆成功！";
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

		if(this.carIDList==null || this.carIDList.size()==0)
		{
			this.message="缺少车辆编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			for(int i=0;i<this.carIDList.size();i++)
			{
				if(!carDAO.delete(this.carIDList.get(i))){
					mysqlTools.rollback();
					this.message+="删除车辆信息失败！";
					this.success=false;
					return "success";
				}
				
			}
			mysqlTools.commit();
			this.message="删除车辆信息成功！";
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
	public String queryDriverNameID(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		ArrayList<CarDTO> res=null;
		try{
			//查询
			res=carDAO.queryDriverNameID();
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
				m.put("carID",res.get(i).getCarID());
				m.put("jointName",res.get(i).jointName());
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
	
	public String queryCarByContractor(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		if(this.carDTO.getFreightContractorID()==null){
			this.message="缺少必要参数";
			this.success=false;
			return "success";
		}
		ArrayList<CarDTO> res=null;
		try{
			//查询
			res=this.carDAO.queryByContractorID(carDTO.getFreightContractorID());
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
				m.put("carID",res.get(i).getCarID());
				m.put("driverName",res.get(i).getDriverName());
				m.put("driverPhone",res.get(i).getPhone());
				m.put("carType",res.get(i).getCarTypeDTO().getCarTypeName());
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
	public CarDAO getCarDAO() {
		return carDAO;
	}
	public void setCarDAO(CarDAO carDAO) {
		this.carDAO = carDAO;
	}
	public CarTypeDAO getCarTypeDAO() {
		return carTypeDAO;
	}
	public void setCarTypeDAO(CarTypeDAO carTypeDAO) {
		this.carTypeDAO = carTypeDAO;
	}
	public FreightContractorDAO getFreightContractorDAO() {
		return freightContractorDAO;
	}
	public void setFreightContractorDAO(FreightContractorDAO freightContractorDAO) {
		this.freightContractorDAO = freightContractorDAO;
	}
	public CarDTO getCarDTO() {
		return carDTO;
	}
	public void setCarDTO(CarDTO carDTO) {
		this.carDTO = carDTO;
	}
	public CarTypeDTO getCarTypeDTO() {
		return carTypeDTO;
	}
	public void setCarTypeDTO(CarTypeDTO carTypeDTO) {
		this.carTypeDTO = carTypeDTO;
	}
	public FreightContractorDTO getFreightContractorDTO() {
		return freightContractorDTO;
	}
	public void setFreightContractorDTO(FreightContractorDTO freightContractorDTO) {
		this.freightContractorDTO = freightContractorDTO;
	}
	public ArrayList<String> getCarIDList() {
		return carIDList;
	}
	public void setCarIDList(ArrayList<String> carIDList) {
		this.carIDList = carIDList;
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
