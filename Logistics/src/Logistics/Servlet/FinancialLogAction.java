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
public class FinancialLogAction {
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private FinancialLogDAO logdao=null;
	
	//DTO对象
	private FinancialLogDTO log=new FinancialLogDTO();
	
	//输入对象
	String user=null;
	ArrayList<Integer> ids=new ArrayList<Integer>();
	String startDate=null;
	String endDate=null;
	String dateType=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	ArrayList<Map> carTypeQuoteMapList=null;
	Map data=null;
	
	public FinancialLogAction(){
		
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		logdao=new FinancialLogDAO();
		logdao.initiate(mysqlTools);
		
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
		if(this.limit<0) limit=0;
		Date startDay=null;
		Date endDay=null;
		if(startDate!=null && startDate.length()!=0)
			startDay=Date.valueOf(startDate);
		if(endDate!=null && endDate.length()!=0)
			endDay=Date.valueOf(endDate);
		ArrayList<FinancialLogDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=logdao.queryQualifiedAmount(user, startDay, endDay);
			res=logdao.queryOnCondition(user, startDay, endDay, start, limit);
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
			for(FinancialLogDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("logID",iter.getLogID());
				m.put("operationTime",Tools.toString(iter.getOperationTime()));
				m.put("type",iter.getType());
				m.put("user", iter.getUser());
				m.put("content", iter.getContent());
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

		try{
			//查询
			FinancialLogDTO aLog=logdao.getByID(log.getLogID());
			if(aLog==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			this.data=new HashMap();
			this.data.put("logID", aLog.getLogID());
			this.data.put("operationTime", Tools.toString(aLog.getOperationTime()));
			this.data.put("type", aLog.getType());
			this.data.put("user", aLog.getUser());
			this.data.put("content", aLog.getContent());
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

		
		if(this.ids==null || this.ids.size()==0)
		{
			this.message="缺少编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			if(!logdao.delete(ids)){
				mysqlTools.rollback();
				this.message+="删除信息失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="删除信息成功！";
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


	public FinancialLogDAO getLogdao() {
		return logdao;
	}


	public void setLogdao(FinancialLogDAO logdao) {
		this.logdao = logdao;
	}


	public FinancialLogDTO getLog() {
		return log;
	}


	public void setLog(FinancialLogDTO log) {
		this.log = log;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public ArrayList<Integer> getIds() {
		return ids;
	}


	public void setIds(ArrayList<Integer> ids) {
		this.ids = ids;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getDateType() {
		return dateType;
	}


	public void setDateType(String dateType) {
		this.dateType = dateType;
	}


	public ArrayList<Map> getResultMapList() {
		return resultMapList;
	}


	public void setResultMapList(ArrayList<Map> resultMapList) {
		this.resultMapList = resultMapList;
	}


	public ArrayList<Map> getCarTypeQuoteMapList() {
		return carTypeQuoteMapList;
	}


	public void setCarTypeQuoteMapList(ArrayList<Map> carTypeQuoteMapList) {
		this.carTypeQuoteMapList = carTypeQuoteMapList;
	}


	public Map getData() {
		return data;
	}


	public void setData(Map data) {
		this.data = data;
	}
	
	
	


}
