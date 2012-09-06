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
public class PaymentCollectionAction {
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
	private PaymentCollectionDAO pcdao=null;
	//DTO对象
	private PaymentCollectionDTO pcdto=new PaymentCollectionDTO();
	//输入对象
	ArrayList<Integer> ids=new ArrayList<Integer>();
	
	int operType=0;
	ArrayList<PaymentCollectionDTO> pcList=new ArrayList<PaymentCollectionDTO>();
	String startDate;
	String endDate;
	String dateType=null;
	String newState=null;
	String stateRemarks=null;
	String ackInfo=null;
	String eid=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	
	Map data=new HashMap();
	
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
				PaymentCollectionDTO income=this.pcdao.getByID(fmid);
				if(income!=null){
					incomeList.add(income);
				}
			}
			if(incomeList==null || incomeList.size()==0){
				this.message="缺少代收货款的记录，无法导出！";
				this.success=false;
				return "error";
			}
			le=new LExcel();
			le.toSheet(incomeList, "代收货款");
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
	
	public PaymentCollectionAction(){
		pcdao=new PaymentCollectionDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		pcdao.initiate(mysqlTools);
		
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
		if(pcdto==null)
			pcdto=new PaymentCollectionDTO();
		if(this.start<0) start=0;
		if(this.limit<0) limit=0;
		ArrayList<PaymentCollectionDTO> res=null;
		Date startDay=startDate==null||startDate.length()==0?null:Date.valueOf(startDate);
		Date endDay=endDate==null||endDate.length()==0?null:Date.valueOf(endDate);
		try{
			//查询
			
			qualifiedAmount=pcdao.queryQualifiedAmountOnCondition(pcdto, startDay, endDay);
			res=pcdao.queryOnCondition(pcdto, startDay, endDay, start, limit);
			
			if(res==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			resultMapList=new ArrayList<Map>();
			data.put("state", res.size()<1||Tools.isVoid(pcdto.getFreightManifestID())||res.get(0)==null?"":res.get(0).getState());
			data.put("fState", res.size()<1||Tools.isVoid(pcdto.getFreightManifestID())||res.get(0)==null?"":res.get(0).getFinancialState());
			for(PaymentCollectionDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("freightManifestID", iter.getFreightManifestID());
				m.put("customerID", iter.getCustomerID());
				m.put("customer", iter.getCustomer());
				m.put("origin", iter.getOriginProvince()+"-"+iter.getOriginCity());
				m.put("destination", iter.getDestinationProvince()+"-"+iter.getDestinationCity());
				m.put("dateCreated", Tools.toString(iter.getDateCreated()));
				m.put("state", iter.getState());
				m.put("dateModified", Tools.toString(iter.getDateModified()));
				m.put("expectedPayment", iter.getExpectedPayment());
				m.put("recievedPayment", iter.getRecievedPayment());
				m.put("procedureFeeRate", iter.getProcedureFeeRate());
				m.put("procedureFee", iter.getProcedureFee());
				m.put("stateRemarks", iter.getStateRemarks());
				m.put("financialState", iter.getFinancialState());
				m.put("financialRemarks", iter.getFinancialRemarks());
				m.put("costCenter", iter.getCostCenter());
				m.put("sellCenter", iter.getSellCenter());
				m.put("customerNumber", iter.getCustomerNumber());
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
		if(!Permission.checkPermission(this, MethodCode.updateState)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
				}
		//---------------------------------------------------------------

		
		if(newState==null || newState.length()==0 ||
				pcList==null || pcList.size()==0)
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		if(!Tools.validPaymentCollectionState.contains(this.newState)){
			this.message="新状态有错误！";
			this.success=false;
			return "success";
		}
		try{
			//对应每个项目，修改状态备注.
			for(PaymentCollectionDTO pc:pcList){
				pc.setState(this.newState);
				if(Tools.isVoid(pc.getStateRemarks())){
					pc.setStateRemarks(this.stateRemarks);
				}
			}
			if(!pcdao.updateState(pcList)){
				mysqlTools.rollback();
				this.message="更新失败！";
				this.success=false;
				return "success";
			}
			String content="运输单编号：";
			for(PaymentCollectionDTO pc:pcList){
				content+=" "+pc.getFreightManifestID();
			}
			FinancialLog.log(Permission.getCurrentUser(), "代收货款-修改流转状态", content);
			mysqlTools.commit();
			this.message="更改运输单状态成功！";
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
			pcdto.setFinancialState("已归档");
			for(int i=0;i<ids.size();i++){
				pcdto.setFreightManifestID(ids.get(i));
				if(!pcdao.updateFinancialState(pcdto))
				{
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="运输单编号：";
			for(int id:ids){
				content+=" "+id;
			}
			FinancialLog.log(Permission.getCurrentUser(), "代收货款-归档", content);
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

		
		if(ids==null || ids.size()==0)
		{
			this.message="缺少必要信息，无法解除归档！";
			this.success=false;
			return "success";
		}
		try{
			pcdto.setFinancialState("未归档");
			for(int i=0;i<ids.size();i++){
				pcdto.setFreightManifestID(ids.get(i));
				if(!pcdao.updateFinancialState(pcdto))
				{
					mysqlTools.rollback();
					this.message="解除归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="运输单编号：";
			for(int id:ids){
				content+=" "+id;
			}
			FinancialLog.log(Permission.getCurrentUser(), "代收货款-解除归档", content);
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

		if(pcdto==null||pcdto.getFreightManifestID()==null)
		{
			this.message="缺少必要信息，无法修改！";
			this.success=false;
			return "success";
		}
		try{
			PaymentCollectionDTO finance=pcdao.getByID(pcdto.getFreightManifestID());
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
			if(!pcdao.updateIncomes(pcdto))
			{
				mysqlTools.rollback();
				this.message="修改运输收入信息失败!";
				this.success=false;
				return "success";
			}
			if(operType==1){
				pcdto.setFinancialState("已归档");
				if(!pcdao.updateFinancialState(pcdto)){
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="运输单编号：";
			content+=pcdto.getFreightManifestID();
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
	public PaymentCollectionDAO getPcdao() {
		return pcdao;
	}
	public void setPcdao(PaymentCollectionDAO pcdao) {
		this.pcdao = pcdao;
	}
	public PaymentCollectionDTO getPcdto() {
		return pcdto;
	}
	public void setPcdto(PaymentCollectionDTO pcdto) {
		this.pcdto = pcdto;
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
	public ArrayList<PaymentCollectionDTO> getPcList() {
		return pcList;
	}
	public void setPcList(ArrayList<PaymentCollectionDTO> pcList) {
		this.pcList = pcList;
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
	public String getNewState() {
		return newState;
	}
	public void setNewState(String newState) {
		this.newState = newState;
	}
	public String getStateRemarks() {
		return stateRemarks;
	}
	public void setStateRemarks(String stateRemarks) {
		this.stateRemarks = stateRemarks;
	}
	public String getAckInfo() {
		return ackInfo;
	}
	public void setAckInfo(String ackInfo) {
		this.ackInfo = ackInfo;
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
	
//	public String queryOne(){
//		if(pcdto==null || pcdto.getFreightManifestID()==null )
//		{
//			this.message="缺少运输单编号,无法查询！";
//			this.success=false;
//			return "success";
//		}
//		try{
//			ArrayList<FreightStateDTO> res=null;
//			res=pcdao.queryAllStates(pcdto.getFreightManifestID());
//			if(res!=null)
//			{
//				resultMapList=new ArrayList<Map>();
//				for(int i=0;i<res.size();i++){
//					Map m=new HashMap();
//					m.put("state", res.get(i).getState());
//					m.put("date", Tools.toString(res.get(i).getDate()));
//					m.put("stateRemarks", res.get(i).getStateRemarks());
//					resultMapList.add(m);
//				}
//			}
//			mysqlTools.commit();
//			this.message="成功";
//			this.success=true;
//			return "success";
//			
//		}catch(Exception e){
//			mysqlTools.rollback();
//			e.printStackTrace();
//			this.message="操作失败！";
//			this.success=false;
//			return "success";
//		}
//		finally{
//			if(mysqlTools!=null)
//				mysqlTools.close();
//		}
//	}
	
	
}
