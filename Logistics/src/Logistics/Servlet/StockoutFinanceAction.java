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
public class StockoutFinanceAction {
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
	private StockoutFinanceDAO sofdao=null;
	
	//DTO对象
	private StockoutFinanceDTO sofdto=new StockoutFinanceDTO();
	//输入对象
	ArrayList<Integer> midList=new ArrayList<Integer>();
	int operType=0;
	String startDate=null;
	String endDate=null;
	String dateType=null;
	String eid=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public StockoutFinanceAction(){
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		sofdao=new StockoutFinanceDAO();
		sofdao.initiate(mysqlTools);
		
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
			this.message="缺少出库单编号，无法导出！";
			this.success=false;
			return "error";
		}
		LExcel le=null;
		try{
			String[] ids=eid.split("_");
			ArrayList<DtoToExcel> incomeList= new ArrayList<DtoToExcel>();
			for(String id:ids){
				Integer fmid=Integer.parseInt(id);
				StockoutFinanceDTO income=sofdao.getByID(fmid);
				if(income!=null){
					incomeList.add(income);
				}
			}
			if(incomeList==null || incomeList.size()==0){
				this.message="缺少出库单收支的记录，无法导出！";
				this.success=false;
				return "error";
			}
			le=new LExcel();
			le.toSheet(incomeList, "出库收支");
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
		if(sofdto==null)
		{
			this.message="缺少传入参数！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0) limit=0;
		ArrayList<StockoutFinanceDTO> res=null;
		Date startDay=startDate==null||startDate.length()==0?null:Date.valueOf(startDate);
		Date endDay=endDate==null||endDate.length()==0?null:Date.valueOf(endDate);
		try{
			//查询
			this.qualifiedAmount=sofdao.queryQualifiedAmountOnCondition(sofdto, startDay, endDay);
			res=sofdao.queryOnCondition(sofdto, startDay, endDay, start, limit);
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
			for(StockoutFinanceDTO stockinF:res){
				Map m=null;
				m=new HashMap();
				
				m.put("stockoutManifestID",stockinF.getStockoutManifestID());
				m.put("sellCenter", stockinF.getSellCenter());
				m.put("dateCreated",Tools.toString(stockinF.getDateCreated()));
				m.put("customer",stockinF.getCustomer());
				m.put("sumAmount", stockinF.getSumAmount());
				m.put("sumVolume", stockinF.getSumVolume());
				m.put("sumWeight", stockinF.getSumWeight());
				m.put("chargeMode", stockinF.getChargeMode());
				m.put("costCenter", stockinF.getCostCenter());
				m.put("stockoutFee", stockinF.getStockoutFee());
				m.put("loadUnloadCost", stockinF.getLoadUnloadCost());
				m.put("extraCost", stockinF.getExtraCost());
				m.put("financialState", stockinF.getFinancialState());
				m.put("financialRemarks", stockinF.getRemarks());
				m.put("unitPrice", stockinF.getUnitPrice());
				m.put("warehouseName", stockinF.getWarehouseName());
				m.put("dateStockout", Tools.toString(stockinF.getDateStockout()));
				
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
	
	public String archive(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of archive
		if(!Permission.checkPermission(this, MethodCode.archive)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
		}
//---------------------------------------------------------------

		if(midList==null || midList.size()==0)
		{
			this.message="缺少必要信息，无法归档！";
			this.success=false;
			return "success";
		}
		try{
			sofdto.setFinancialState("已归档");
			for(int i=0;i<midList.size();i++){
				sofdto.setStockoutManifestID(midList.get(i));
				if(!sofdao.updateFinancialState(sofdto))
				{
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="出库单编号：";
			for(int id:midList){
				content+=" "+id;
			}
			FinancialLog.log(Permission.getCurrentUser(), "出库收支-归档", content);
			mysqlTools.commit();
			this.message="修改出库收支信息成功！";
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
		if(midList==null || midList.size()==0)
		{
			this.message="缺少必要信息，无法解除归档！";
			this.success=false;
			return "success";
		}
		try{
			sofdto.setFinancialState("未归档");
			for(int i=0;i<midList.size();i++){
				sofdto.setStockoutManifestID(midList.get(i));
				if(!sofdao.updateFinancialState(sofdto))
				{
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="出库单编号：";
			for(int id:midList){
				content+=" "+id;
			}
			FinancialLog.log(Permission.getCurrentUser(), "出库单收支-解除归档", content);
			mysqlTools.commit();
			this.message="修改出库收支信息成功！";
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
	public String updateFinance(){
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

		
		if(sofdto==null||sofdto.getStockoutManifestID()==null)
		{
			this.message="缺少必要信息，无法修改！";
			this.success=false;
			return "success";
		}
		try{
			StockoutFinanceDTO finance=sofdao.getByID(sofdto.getStockoutManifestID());
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
			if(!sofdao.updateMoney(sofdto))
			{
				mysqlTools.rollback();
				this.message="修改出库收支信息失败!";
				this.success=false;
				return "success";
			}
			if(operType==1){
				sofdto.setFinancialState("已归档");
				if(!sofdao.updateFinancialState(sofdto)){
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="出库单编号：";
			content+=sofdto.getStockoutManifestID();
			FinancialLog.log(Permission.getCurrentUser()
					, operType==1?"出库单收支-修改并归档":"出库单收支-修改", content);
			mysqlTools.commit();
			this.message="修改出库收支信息成功！";
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


	public StockoutFinanceDAO getSofdao() {
		return sofdao;
	}


	public void setSofdao(StockoutFinanceDAO sofdao) {
		this.sofdao = sofdao;
	}


	public StockoutFinanceDTO getSofdto() {
		return sofdto;
	}


	public void setSofdto(StockoutFinanceDTO sofdto) {
		this.sofdto = sofdto;
	}


	public ArrayList<Integer> getMidList() {
		return midList;
	}


	public void setMidList(ArrayList<Integer> midList) {
		this.midList = midList;
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
