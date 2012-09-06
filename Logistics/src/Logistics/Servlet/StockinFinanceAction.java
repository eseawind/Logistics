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
public class StockinFinanceAction {
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
	private StockinFinanceDAO sifdao=null;
	
	//DTO对象
	private StockinFinanceDTO sifdto=new StockinFinanceDTO();
	//输入对象
	ArrayList<Integer> mIDList=new ArrayList<Integer>();
	int operType=0;
	String startDate=null;
	String endDate=null;
	String dateType=null;
	String eid=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public StockinFinanceAction(){
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		sifdao=new StockinFinanceDAO();
		sifdao.initiate(mysqlTools);
		
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
		if(sifdto==null)
		{
			this.message="缺少传入参数！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0) limit=0;
		ArrayList<StockinFinanceDTO> res=null;
		Date startDay=startDate==null||startDate.length()==0?null:Date.valueOf(startDate);
		Date endDay=endDate==null||endDate.length()==0?null:Date.valueOf(endDate);
		try{
			//查询
			this.qualifiedAmount=sifdao.queryQualifiedAmountOnCondition(sifdto, startDay, endDay);
			res=sifdao.queryOnCondition(sifdto, startDay, endDay, start, limit);
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
			for(StockinFinanceDTO stockinF:res){
				Map m=null;
				m=new HashMap();
				m.put("stockinManifestID",stockinF.getStockinManifestID());
				m.put("sellCenter", stockinF.getSellCenter());
				m.put("dateCreated",Tools.toString(stockinF.getDateCreated()));
				m.put("dateStockin", Tools.toString(stockinF.getDateStockin()));
				m.put("warehouseName", stockinF.getWarehouseName());
				m.put("customer",stockinF.getCustomer());
				m.put("sumAmount", stockinF.getSumAmount());
				m.put("sumVolume", stockinF.getSumVolume());
				m.put("sumWeight", stockinF.getSumWeight());
				m.put("chargeMode", stockinF.getChargeMode());
				m.put("costCenter", stockinF.getCostCenter());
				m.put("stockinFee", stockinF.getStockinFee());
				m.put("loadUnloadCost", stockinF.getLoadUnloadCost());
				m.put("extraCost", stockinF.getExtraCost());
				m.put("financialState", stockinF.getFinancialState());
				m.put("financialRemarks", stockinF.getRemarks());
				m.put("unitPrice", stockinF.getUnitPrice());
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
			this.message="缺少入库单编号，无法导出！";
			this.success=false;
			return "error";
		}
		LExcel le=null;
		try{
			String[] ids=eid.split("_");
			ArrayList<DtoToExcel> incomeList= new ArrayList<DtoToExcel>();
			for(String id:ids){
				Integer simid=Integer.parseInt(id);
				StockinFinanceDTO dto=sifdao.getByID(simid);
				if(dto!=null){
					incomeList.add(dto);
				}
			}
			if(incomeList==null || incomeList.size()==0){
				this.message="缺少入库单收支的记录，无法导出！";
				this.success=false;
				return "error";
			}
			le=new LExcel();
			le.toSheet(incomeList, "入库单收支");
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

		//Check permission of archive
		if(!Permission.checkPermission(this, MethodCode.archive)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
		}
//---------------------------------------------------------------

		if(mIDList==null || mIDList.size()==0)
		{
			this.message="缺少必要信息，无法归档！";
			this.success=false;
			return "success";
		}
		try{
			sifdto.setFinancialState("已归档");
			for(int i=0;i<mIDList.size();i++){
				sifdto.setStockinManifestID(mIDList.get(i));
				if(!sifdao.updateFinancialState(sifdto))
				{
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="入库单编号：";
			for(int id:mIDList){
				content+=" "+id;
			}
			FinancialLog.log(Permission.getCurrentUser(), "入库单收支-归档", content);
			
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

		
		if(mIDList==null || mIDList.size()==0)
		{
			this.message="缺少必要信息，无法解除归档！";
			this.success=false;
			return "success";
		}
		try{
			sifdto.setFinancialState("未归档");
			for(int i=0;i<mIDList.size();i++){
				sifdto.setStockinManifestID(mIDList.get(i));
				if(!sifdao.updateFinancialState(sifdto))
				{
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="入库单编号：";
			for(int id:mIDList){
				content+=" "+id;
			}
			FinancialLog.log(Permission.getCurrentUser(), "入库单收支-解除归档", content);
			mysqlTools.commit();
			this.message="修改入库收支信息成功！";
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

		
		if(sifdto==null||sifdto.getStockinManifestID()==null)
		{
			this.message="缺少必要信息，无法修改！";
			this.success=false;
			return "success";
		}
		try{
			StockinFinanceDTO finance=sifdao.getByID(sifdto.getStockinManifestID());
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
			if(!sifdao.updateMoney(sifdto))
			{
				mysqlTools.rollback();
				this.message="修改入库收支信息失败!";
				this.success=false;
				return "success";
			}
			if(operType==1){
				sifdto.setFinancialState("已归档");
				if(!sifdao.updateFinancialState(sifdto)){
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="入库单编号：";
			content+=sifdto.getStockinManifestID();
			FinancialLog.log(Permission.getCurrentUser()
					, operType==1?"入库单收支-修改并归档":"入库单收支-修改", content);
			mysqlTools.commit();
			this.message="修改入库收支信息成功！";
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


	public StockinFinanceDAO getSifdao() {
		return sifdao;
	}


	public void setSifdao(StockinFinanceDAO sifdao) {
		this.sifdao = sifdao;
	}


	public StockinFinanceDTO getSifdto() {
		return sifdto;
	}


	public void setSifdto(StockinFinanceDTO sifdto) {
		this.sifdto = sifdto;
	}


	public ArrayList<Integer> getMIDList() {
		return mIDList;
	}


	public void setMIDList(ArrayList<Integer> list) {
		mIDList = list;
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


	public String getEid() {
		return eid;
	}


	public void setEid(String eid) {
		this.eid = eid;
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


}
