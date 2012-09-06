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
public class StockFinanceAction {
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
	private StockIncomeDAO incomeDao=null;
	private StockItemDAO sidao=null;
	//DTO对象
	private StockIncomeDTO income=new StockIncomeDTO();
	private WarehouseDTO wdto=new WarehouseDTO();
	private CustomerDTO cusdto=new CustomerDTO();
	private StockItemDTO sidto=new StockItemDTO();
	private ItemDTO itemdto=new ItemDTO();
	//输入对象
	ArrayList<Integer> ids=new ArrayList<Integer>();
	int operType=0;
	String startDate=null;
	String endDate=null;
	String dateType=null;
	String eid=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public StockFinanceAction(){
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		incomeDao=new StockIncomeDAO();
		incomeDao.initiate(mysqlTools);
		sidao=new StockItemDAO();
		sidao.initiate(mysqlTools);
		
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
			this.message="缺少仓储收支编号，无法导出！";
			this.success=false;
			return "error";
		}
		LExcel le=null;
		try{
			String[] ids=eid.split("_");
			ArrayList<DtoToExcel> incomeList= new ArrayList<DtoToExcel>();
			for(String id:ids){
				Integer fmid=Integer.parseInt(id);
				StockIncomeDTO income=incomeDao.getByID(fmid);
				if(income!=null){
					incomeList.add(income);
				}
			}
			if(incomeList==null || incomeList.size()==0){
				this.message="缺少仓储收支的记录，无法导出！";
				this.success=false;
				return "error";
			}
			le=new LExcel();
			le.toSheet(incomeList, "仓储收支");
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
		if(income==null)
		{
			this.message="缺少传入参数！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0) limit=0;
		ArrayList<StockIncomeDTO> res=null;
		Date startDay=startDate==null||startDate.length()==0?null:Date.valueOf(startDate);
		Date endDay=endDate==null||endDate.length()==0?null:Date.valueOf(endDate);
		try{
			//查询
			this.qualifiedAmount=incomeDao.queryQualifiedAmountOnCondition(income, startDay, endDay);
			res=incomeDao.queryOnCondition(income, startDay, endDay, start, limit);
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
			for(StockIncomeDTO iter:res){
				Map m=null;
				m=new HashMap();
				if(iter.getDateAccountEnd()==null){
					iter.setDateAccountEnd(Tools.currDate());
					iter.setDaysStock(iter.getDateAccountEnd().compareTo(iter.getDateAccountStart()));
				}
				m.put("stockIncomeID",iter.getStockIncomeID());
				m.put("sellCenter", iter.getSellCenter());
				m.put("dateModified",Tools.toString(iter.getDateModified()));
				m.put("customer",iter.getCustomerName()+"("+iter.getCustomerID()+")");
				m.put("customerID", iter.getCustomerID());
				m.put("amount", iter.getAmount());
				m.put("volume", iter.getVolume());
				m.put("weight", iter.getWeight());
				m.put("chargeMode", iter.getChargeMode());
				m.put("itemName", iter.getItemName()+"("+iter.getItemID()+")");
				m.put("itemNumber", iter.getItemNumber());
				m.put("stockFee", iter.getStockFee());
				m.put("financialState", iter.getFinancialState());
				m.put("remarks", iter.getRemarks());
				m.put("unitPrice", iter.getUnitPrice());
				m.put("dateStockin", Tools.toString(iter.getDateStockin()));
				m.put("dateStockout", Tools.toString(iter.getDateStockout()));
				m.put("dateAccountStart", Tools.toString(iter.getDateAccountStart()));
				m.put("dateAccountEnd", Tools.toString(iter.getDateAccountEnd()));
				m.put("daysStock", iter.getDaysStock());
				m.put("warehouse",iter.getWarehouseName()+"("+iter.getWarehouseID()+")");
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

		
		if(ids==null || ids.size()==0)
		{
			this.message="缺少必要信息，无法归档！";
			this.success=false;
			return "success";
		}
		try{
			income.setFinancialState("已归档");
			for(int i=0;i<ids.size();i++){
				income.setStockIncomeID(ids.get(i));
				if(!incomeDao.updateFinancialState(income))
				{
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="库存收支编号：";
			for(int id:ids){
				content+=" "+id;
			}
			FinancialLog.log(Permission.getCurrentUser(), "库存收支-归档", content);
			mysqlTools.commit();
			this.message="修改收入信息成功！";
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

		
		if(ids==null || ids.size()==0)
		{
			this.message="缺少必要信息，无法解除归档！";
			this.success=false;
			return "success";
		}
		try{
			income.setFinancialState("未归档");
			for(int i=0;i<ids.size();i++){
				income.setStockIncomeID(ids.get(i));
				if(!incomeDao.updateFinancialState(income))
				{
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="库存收支编号：";
			for(int id:ids){
				content+=" "+id;
			}
			FinancialLog.log(Permission.getCurrentUser(), "库存收支-解除归档", content);
			mysqlTools.commit();
			this.message="修改收入信息成功！";
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
	public String updateIncome(){
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

		
		if(income==null||income.getStockIncomeID()==null)
		{
			this.message="缺少必要信息，无法修改！";
			this.success=false;
			return "success";
		}
		try{
			StockIncomeDTO finance=incomeDao.getByID(income.getStockIncomeID());
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
			if(!incomeDao.updateMoney(income))
			{
				mysqlTools.rollback();
				this.message="修改收入信息失败!";
				this.success=false;
				return "success";
			}
			if(operType==1){
				income.setFinancialState("已归档");
				if(!incomeDao.updateFinancialState(income)){
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="库存收支编号：";
			content+=income.getStockIncomeID();
			FinancialLog.log(Permission.getCurrentUser()
					, operType==1?"库存收支-修改并归档":"库存收支-修改", content);
			mysqlTools.commit();
			this.message="修改收入信息成功！";
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

	public String queryStockItems(){
		
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		
		if(this.start<0) start=0;
		if(this.limit<0) limit=0;
		Date startDay=null;
		Date endDay=null;
		if(startDate!=null && startDate.length()!=0)
			startDay=Date.valueOf(startDate);
		if(endDate!=null && endDate.length()!=0)
			endDay=Date.valueOf(endDate);
		ArrayList<StockItemDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=sidao.queryQualifiedAmount(wdto, itemdto, cusdto,true,startDay,endDay);
			res=sidao.queryQualifiedItems(wdto, itemdto, cusdto, start, limit,true,startDay,endDay);
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
			for(StockItemDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("itemID",iter.getItemID());
				m.put("stockinDate", Tools.toString(iter.getStockinDate()));
				m.put("lastAccountDate", Tools.toString(iter.getLastAccountDate()));
				m.put("itemName", iter.item.getName());
				m.put("itemNumber",iter.item.getNumber());
				m.put("batch", iter.item.getBatch());
				m.put("unit",iter.item.getUnit());
//				m.put("unitVolume", iter.item.getUnitVolume());
//				m.put("unitWeight",iter.item.getUnitWeight());
				m.put("warehouseID",iter.getWarehouseID());
				m.put("warehouse",iter.warehouse.getName()+"("+iter.getWarehouseID()+")");
				m.put("customerID",iter.getCustomerID());
				m.put("customer",iter.customer.getName()+"("+iter.getCustomerID()+")");
				m.put("amount", iter.getAmount());
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


	public StockIncomeDAO getIncomeDao() {
		return incomeDao;
	}


	public void setIncomeDao(StockIncomeDAO incomeDao) {
		this.incomeDao = incomeDao;
	}


	public StockItemDAO getSidao() {
		return sidao;
	}


	public void setSidao(StockItemDAO sidao) {
		this.sidao = sidao;
	}


	public StockIncomeDTO getIncome() {
		return income;
	}


	public void setIncome(StockIncomeDTO income) {
		this.income = income;
	}


	public WarehouseDTO getWdto() {
		return wdto;
	}


	public void setWdto(WarehouseDTO wdto) {
		this.wdto = wdto;
	}


	public CustomerDTO getCusdto() {
		return cusdto;
	}


	public void setCusdto(CustomerDTO cusdto) {
		this.cusdto = cusdto;
	}


	public StockItemDTO getSidto() {
		return sidto;
	}


	public void setSidto(StockItemDTO sidto) {
		this.sidto = sidto;
	}


	public ItemDTO getItemdto() {
		return itemdto;
	}


	public void setItemdto(ItemDTO itemdto) {
		this.itemdto = itemdto;
	}


	public ArrayList<Integer> getIds() {
		return ids;
	}


	public void setIds(ArrayList<Integer> ids) {
		this.ids = ids;
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
