package Logistics.Servlet;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Logistics.Common.Permission;
import Logistics.Common.Tools;
import Logistics.Common.Permission.MethodCode;
import Logistics.DAO.*;
import Logistics.DTO.CityDTO;
import Logistics.DTO.EmployeeProfileDTO;
import Logistics.DTO.EmployeeSalaryDTO;

public class CityAction {
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private CityDAO cdao=null;
	//DTO对象
	private CityDTO cdto=null;
	//输入对象
	ArrayList<String> cityIDList=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public CityAction() {
		
		cdao=new CityDAO();
		cdto= new CityDTO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		cdao.initiate(mysqlTools);
		cityIDList=new ArrayList<String>();
	}
	
	public String queryAllNameID(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		ArrayList<CityDTO> res=null;
		try{
			//查询
			res=cdao.queryAllCities();
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
				m.put("cityID",res.get(i).getCityID());
				m.put("jointName",res.get(i).getJointName());
				m.put("name", res.get(i).getName());
				m.put("nameID", res.get(i).getName()+"("+res.get(i).getCityID()+")");
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
	
	public String deleteCity(){
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

		if(this.cityIDList==null || this.cityIDList.size()==0)
		{
			this.message="缺少城市编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			for(int i=0;i<this.cityIDList.size();i++)
			{
				
				if(!cdao.delete(this.cityIDList.get(i))){
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
	
	public String updateCity(){
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

		
		if(cdto==null || cdto.getCityID()==null || cdto.getCityID().length()==0)
		{
			this.message="缺少城市编号,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			
			if(!cdao.update(cdto))
			{
				mysqlTools.rollback();
				this.message="修改城市信息失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="修改城市信息成功！";
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
	
	public String queryACity(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		
		if(cdto.getCityID()==null || cdto.getCityID().length()==0)
		{
			this.message="缺少城市编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			cdto=cdao.getDTOByID(cdto.getCityID());
			if(cdto==null)
			{
				mysqlTools.rollback();
				this.message="查找城市失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("cdto.CityID", cdto.getCityID());
			this.data.put("cdto.Name", cdto.getName());
			this.data.put("cdto.Province", cdto.getProvince());
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
	
	public String insertCity(){
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

		
		if(cdto.getCityID()==null ||cdto.getCityID().length()==0|| cdto.getName()==null ||
				cdto.getName().length()==0 || cdto.getProvince()==null || cdto.getProvince().length()==0)
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		try{
			CityDTO cdto1=null;
			cdto1=cdao.getDTOByID(cdto.getCityID());
			if(cdto1!=null)
			{
				this.message="已经有城市编号："+cdto1.getCityID()+" 存在！";
				this.success=false;
				return "success";
			}
			if(!cdao.insert(cdto))
			{
				mysqlTools.rollback();
				this.message="新增城市失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="新增城市成功！";
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
	
	public String queryCity(){
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
		ArrayList<CityDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=cdao.queryAmountByCondition(cdto);
			res=cdao.queryByCondition(cdto,start, limit);
			Tools.print(""+res.size()+"|"+this.qualifiedAmount);
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
				m.put("CityID",res.get(i).getCityID());
				m.put("Name",res.get(i).getName());
				m.put("Province",res.get(i).getProvince());
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

	public CityDAO getCdao() {
		return cdao;
	}

	public void setCdao(CityDAO cdao) {
		this.cdao = cdao;
	}

	public CityDTO getCdto() {
		return cdto;
	}

	public void setCdto(CityDTO cdto) {
		this.cdto = cdto;
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

	public ArrayList<String> getCityIDList() {
		return cityIDList;
	}

	public void setCityIDList(ArrayList<String> cityIDList) {
		this.cityIDList = cityIDList;
	}
	
	
}
