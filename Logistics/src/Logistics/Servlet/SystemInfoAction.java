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

public class SystemInfoAction {
	
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private SystemInfoDAO infodao=null;
	//DTO对象
	private SystemInfoDTO infodto=null;
	//输入对象
	
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public SystemInfoAction(){
		infodao=new SystemInfoDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		infodao.initiate(mysqlTools);
		infodto=new SystemInfoDTO();
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
		if(this.start<0) start=0;
		if(this.limit<0)	limit=0;
		ArrayList<SystemInfoDTO> res=null;
		try{
			res=infodao.getInfo();
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
				m.put("name",res.get(i).getName());
				m.put("value", res.get(i).getValue());
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
	public SystemInfoDAO getInfodao() {
		return infodao;
	}
	public void setInfodao(SystemInfoDAO infodao) {
		this.infodao = infodao;
	}
	public SystemInfoDTO getInfodto() {
		return infodto;
	}
	public void setInfodto(SystemInfoDTO infodto) {
		this.infodto = infodto;
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
