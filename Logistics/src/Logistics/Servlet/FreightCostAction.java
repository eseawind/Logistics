package Logistics.Servlet;
import java.io.File;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Logistics.Common.DtoToExcel;
import Logistics.Common.FinancialLog;
import Logistics.Common.LExcel;
import Logistics.Common.Permission;
import Logistics.Common.Tools;
import Logistics.Common.Permission.MethodCode;
import Logistics.DAO.*;
import Logistics.DTO.*;
public class FreightCostAction {
	
	
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	protected File upload;
	protected String uploadContentType;
	protected String uploadFileName;
	protected InputStream download;
	//DAO对象
	private FreightCostDAO fcdao=null;
	//DTO对象
	private FreightCostDTO fcdto=new FreightCostDTO();
	
	//输入对象
	ArrayList<Integer> fmIDList=new ArrayList<Integer>();
	int operType=0;
	String startDate=null;
	String endDate=null;
	String dateType=null;
	String eid=null;
	
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public FreightCostAction(){
		fcdao=new FreightCostDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			e.printStackTrace();
			mysqlTools=null;
		}
		fcdao.initiate(mysqlTools);
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
		if(fcdto==null )
		{
			this.message="缺少传入参数！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0) limit=0;
		ArrayList<FreightCostDTO> res=null;
		Date startDay=startDate==null||startDate.length()==0?null:Date.valueOf(startDate);
		Date endDay=endDate==null||endDate.length()==0?null:Date.valueOf(endDate);
		try{
			//查询
			this.qualifiedAmount=fcdao.queryQualifiedAmountOnCondition(fcdto, startDay, endDay);
			res=fcdao.queryOnCondition(fcdto, startDay, endDay, start, limit);
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
				m.put("freightManifestID",res.get(i).getFreightManifestID());
				m.put("costCenter", res.get(i).getCostCenter());
				m.put("dateCreated",res.get(i).getDateCreated()==null?null:res.get(i).getDateCreated().toString());
				m.put("customer",res.get(i).getCustomer());
				m.put("customerType", res.get(i).getCustomerType());
				m.put("sumAmount", res.get(i).getSumAmount());
				m.put("sumVolume", res.get(i).getSumVolume());
				m.put("sumWeight", res.get(i).getSumWeight());
				m.put("sumValue", res.get(i).getSumValue());
				m.put("freightCost", res.get(i).getFreightCost());
				m.put("PrepaidCost", res.get(i).getPrepaidCost());
				m.put("extraCost", res.get(i).getExtraCost());
				m.put("extraCostRemarks", res.get(i).getExtraCostRemarks());
				m.put("financialState", res.get(i).getFinancialState());
				m.put("financialRemarks", res.get(i).getFinancialRemarks());
				m.put("originCity", res.get(i).getOriginCity());
				m.put("originProvince", res.get(i).getOriginProvince());
				m.put("destinationCity", res.get(i).getDestinationCity());
				m.put("destinationProvince", res.get(i).getDestinationProvince());
				m.put("customerNumber", res.get(i).getCustomerNumber());
				m.put("consigneeCompany", res.get(i).getConsigneeCompany());
				m.put("consignee", res.get(i).getConsignee());
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
	
	
	public String export(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "error";
		}
		//----------------------------------------------------------------

		//Check permission of archive
		if(!Permission.checkPermission(this, MethodCode.export)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "error";
		}
//---------------------------------------------------------------

		
		if(Tools.isVoid(eid))
		{
			this.message="缺少运输单编号，无法导出！";
			this.success=false;
			return "error";
		}
		LExcel le=null;
		try{
			String[] ids=eid.split("_");
			ArrayList<DtoToExcel> incomeList= new ArrayList<DtoToExcel>();
			for(String id:ids){
				Integer fmid=Integer.parseInt(id);
				FreightCostDTO cost=fcdao.getByID(fmid);
				if(cost!=null){
					incomeList.add(cost);
				}
			}
			if(incomeList==null || incomeList.size()==0){
				this.message="缺少运输单收入的记录，无法导出！";
				this.success=false;
				return "error";
			}
			le=new LExcel();
			le.toSheet(incomeList, "运输支出");
			download=le.toInputStream();
			this.message="成功！";
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
			if(le!=null)
				le.close();
		}
	}
	
	public String archive(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of query
		if(!Permission.checkPermission(this, MethodCode.archive)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
				}
		//---------------------------------------------------------------

		if(fmIDList==null || fmIDList.size()==0)
		{
			this.message="缺少必要信息，无法归档！";
			this.success=false;
			return "success";
		}
		try{
			fcdto.setFinancialState("已归档");
			for(int i=0;i<fmIDList.size();i++){
				fcdto.setFreightManifestID(fmIDList.get(i));
				if(!fcdao.updateFinancialState(fcdto))
				{
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="运输单编号：";
			for(int id:fmIDList){
				content+=" "+id;
			}
			FinancialLog.log(Permission.getCurrentUser(), "运输单支出-归档", content);
			mysqlTools.commit();
			this.message="修改运输收入信息成功！";
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
	
	public String unarchive(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of unarchive
		if(!Permission.checkPermission(this, MethodCode.unarchive)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
		}
		//---------------------------------------------------------------

		if(fmIDList==null || fmIDList.size()==0)
		{
			this.message="缺少必要信息，无法解除归档！";
			this.success=false;
			return "success";
		}
		try{
			fcdto.setFinancialState("未归档");
			for(int i=0;i<fmIDList.size();i++){
				fcdto.setFreightManifestID(fmIDList.get(i));
				if(!fcdao.updateFinancialState(fcdto))
				{
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="运输单编号：";
			for(int id:fmIDList){
				content+=" "+id;
			}
			FinancialLog.log(Permission.getCurrentUser(), "运输单支出-解除归档", content);
			mysqlTools.commit();
			this.message="修改运输收入信息成功！";
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
	
	public String updateCost(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of updateFinance
		if(!Permission.checkPermission(this, MethodCode.update)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
				}
		//---------------------------------------------------------------

		
		if(fcdto==null||fcdto.getFreightManifestID()==null)
		{
			this.message="缺少必要信息，无法修改！";
			this.success=false;
			return "success";
		}
		try{
			FreightCostDTO finance=fcdao.getByID(fcdto.getFreightManifestID());
			if(finance==null){
				mysqlTools.rollback();
				this.message="修改失败，记录不存在!";
				this.success=false;
				return "success";
			}
			if("已归档".equals(finance.getFinancialState())){
				mysqlTools.rollback();
				this.message="修改失败，记录已归档!";
				this.success=false;
				return "success";
			}
			if(!fcdao.updateCosts(fcdto))
			{
				mysqlTools.rollback();
				this.message="修改运输收入信息失败!";
				this.success=false;
				return "success";
			}
			if(operType==1){
				fcdto.setFinancialState("已归档");
				if(!fcdao.updateFinancialState(fcdto)){
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="运输单编号：";
			content+=fcdto.getFreightManifestID();
			FinancialLog.log(Permission.getCurrentUser()
					, operType==1?"运输单支出-修改并归档":"运输单支出-修改", content);
			mysqlTools.commit();
			this.message="修改运输收入信息成功！";
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


	public FreightCostDAO getFcdao() {
		return fcdao;
	}


	public void setFcdao(FreightCostDAO fcdao) {
		this.fcdao = fcdao;
	}


	public FreightCostDTO getFcdto() {
		return fcdto;
	}


	public void setFcdto(FreightCostDTO fcdto) {
		this.fcdto = fcdto;
	}


	public ArrayList<Integer> getFmIDList() {
		return fmIDList;
	}


	public void setFmIDList(ArrayList<Integer> fmIDList) {
		this.fmIDList = fmIDList;
	}


	public int getOperType() {
		return operType;
	}


	public void setOperType(int operType) {
		this.operType = operType;
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


	public Map getData() {
		return data;
	}


	public void setData(Map data) {
		this.data = data;
	}


	public File getUpload() {
		return upload;
	}


	public void setUpload(File upload) {
		this.upload = upload;
	}


	public String getUploadContentType() {
		return uploadContentType;
	}


	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}


	public String getUploadFileName() {
		return uploadFileName;
	}


	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}


	public InputStream getDownload() {
		return download;
	}


	public void setDownload(InputStream download) {
		this.download = download;
	}


	public String getEid() {
		return eid;
	}


	public void setEid(String eid) {
		this.eid = eid;
	}


	
}
