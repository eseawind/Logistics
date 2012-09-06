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
public class ShipmentCostAction {
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
	private ShipmentCostDAO scdao=null;
	private FreightCostDAO fcdao=null;
	private FreightShipmentDAO fsdao=null;
	//DTO对象
	private ShipmentCostDTO scdto=new ShipmentCostDTO();
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
	
	public ShipmentCostAction(){
		scdao=new ShipmentCostDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			e.printStackTrace();
			mysqlTools=null;
		}
		scdao.initiate(mysqlTools);
		fcdao=new FreightCostDAO();
		fcdao.initiate(mysqlTools);
		fsdao=new FreightShipmentDAO();
		fsdao.initiate(mysqlTools);
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
			this.message="缺少装车单编号，无法导出！";
			this.success=false;
			return "error";
		}
		LExcel le=null;
		try{
			String[] ids=eid.split("_");
			ArrayList<DtoToExcel> incomeList= new ArrayList<DtoToExcel>();
			for(String id:ids){
				Integer fmid=Integer.parseInt(id);
				ShipmentCostDTO income=this.scdao.getByID(fmid);
				if(income!=null){
					incomeList.add(income);
				}
			}
			if(incomeList==null || incomeList.size()==0){
				this.message="缺少装车单支出的记录，无法导出！";
				this.success=false;
				return "error";
			}
			le=new LExcel();
			le.toSheet(incomeList, "装车单支出");
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
		if(scdto==null)
		{
			this.message="缺少传入参数！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0) limit=0;
		ArrayList<ShipmentCostDTO> res=null;
		Date startDay=startDate==null||startDate.length()==0?null:Date.valueOf(startDate);
		Date endDay=endDate==null||endDate.length()==0?null:Date.valueOf(endDate);
		try{
			//查询
			this.qualifiedAmount=scdao.queryQualifiedAmountOnCondition(scdto, startDay, endDay);
			res=scdao.queryOnCondition(scdto, startDay, endDay, start, limit);
			if(res==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			resultMapList=new ArrayList<Map>();
			for(ShipmentCostDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("shipmentManifestID",iter.getShipmentManifestID());
				m.put("costCenter", iter.getCostCenter());
				m.put("dateCreated",iter.getDateCreated()==null?null:iter.getDateCreated().toString());
				m.put("sumAmount", iter.getSumAmount());
				m.put("sumVolume", iter.getSumVolume());
				m.put("sumWeight", iter.getSumWeight());
				m.put("sumValue", iter.getSumValue());
				m.put("extraCost", iter.getExtraCost());
				m.put("financialState", iter.getFinancialState());
				m.put("chargeMode", iter.getChargeMode());
				m.put("freightCost", iter.getFreightCost());
				m.put("loadUnloadCost", iter.getLoadUnloadCost());
				m.put("remarks", iter.getRemarks());
				m.put("unitQuote", iter.getUnitQuote());
				m.put("originCity", iter.getOriginCity());
				m.put("originProvince", iter.getOriginProvince());
				m.put("destinationCity", iter.getDestinationCity());
				m.put("destinationProvince", iter.getDestinationProvince());
				m.put("freightContractor", iter.getFreightContractor());
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

		
		if(mIDList==null || mIDList.size()==0)
		{
			this.message="缺少必要信息，无法归档！";
			this.success=false;
			return "success";
		}
		try{
			scdto.setFinancialState("已归档");
			for(Integer shipmentid:mIDList){
				ShipmentCostDTO shipmentCost=scdao.getByID(shipmentid);
				if(shipmentCost==null || "已归档".equals(shipmentCost.getFinancialState()))
				{
					continue;
				}
				double sumCost=shipmentCost.getExtraCost()+shipmentCost.getFreightCost()+shipmentCost.getLoadUnloadCost();
				scdto.setShipmentManifestID(shipmentid);
				if(!scdao.updateFinancialState(scdto))
				{
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
				ArrayList<FreightShipmentDTO> fsdtos;
				FreightShipmentDTO fsdto=new FreightShipmentDTO();
				fsdto.setShipmentManifestID(shipmentid);
				fsdto.setFreightManifestID(null);
				fsdtos=fsdao.queryOnCondition(fsdto);
				for(FreightShipmentDTO fsIter:fsdtos){
					FreightCostDTO freightCost=fcdao.getByID(fsIter.getFreightManifestID());
					if(freightCost!=null && "未归档".equals(freightCost.getFinancialState())){
						double incMoney=0;
						if("直接计费".equals(shipmentCost.getChargeMode())){
							incMoney=sumCost/fsdtos.size();
						}
						else if("车辆类型".equals(shipmentCost.getChargeMode())){
							incMoney=sumCost/fsdtos.size();
						}
						else if("以数量计".equals(shipmentCost.getChargeMode())){
							incMoney=sumCost*freightCost.getSumAmount()/shipmentCost.getSumAmount();
						}
						else if("以重量计".equals(shipmentCost.getChargeMode())){
							incMoney=sumCost*freightCost.getSumWeight()/shipmentCost.getSumWeight();
						}
						else if("以体积计".equals(shipmentCost.getChargeMode())){
							incMoney=sumCost*freightCost.getSumVolume()/shipmentCost.getSumVolume();
						}
						fcdao.updateCosts(incMoney, freightCost.getFreightManifestID());
					}//if
				}
			}
			String content="装车单编号：";
			for(int id:mIDList){
				content+=" "+id;
			}
			FinancialLog.log(Permission.getCurrentUser(), "装车单-归档", content);
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
			scdto.setFinancialState("未归档");
			for(int i=0;i<mIDList.size();i++){
				scdto.setShipmentManifestID(mIDList.get(i));
				if(!scdao.updateFinancialState(scdto))
				{
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
			}
			String content="装车单编号：";
			for(int id:mIDList){
				content+=" "+id;
			}
			FinancialLog.log(Permission.getCurrentUser(), "装车单支出-解除归档", content);
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

		//Check permission of update
		if(!Permission.checkPermission(this, MethodCode.update)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
				}
		//---------------------------------------------------------------

		
		if(scdto==null||scdto.getShipmentManifestID()==null)
		{
			this.message="缺少必要信息，无法修改！";
			this.success=false;
			return "success";
		}
		try{
			ShipmentCostDTO finance=scdao.getByID(scdto.getShipmentManifestID());
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
			if(!scdao.updateCosts(scdto))
			{
				mysqlTools.rollback();
				this.message="修改运输收入信息失败!";
				this.success=false;
				return "success";
			}
			if(operType==1){
				scdto.setFinancialState("已归档");
				if(!scdao.updateFinancialState(scdto)){
					mysqlTools.rollback();
					this.message="归档失败!";
					this.success=false;
					return "success";
				}
				Integer shipmentid=scdto.getShipmentManifestID();
				ShipmentCostDTO shipmentCost=scdao.getByID(shipmentid);
				double sumCost=shipmentCost.getExtraCost()+shipmentCost.getFreightCost()+shipmentCost.getLoadUnloadCost();
				scdto.setShipmentManifestID(shipmentid);
				ArrayList<FreightShipmentDTO> fsdtos;
				FreightShipmentDTO fsdto=new FreightShipmentDTO();
				fsdto.setShipmentManifestID(shipmentid);
				fsdto.setFreightManifestID(null);
				fsdtos=fsdao.queryOnCondition(fsdto);
				for(FreightShipmentDTO fsIter:fsdtos){
					FreightCostDTO freightCost=fcdao.getByID(fsIter.getFreightManifestID());
					if(freightCost!=null && "未归档".equals(freightCost.getFinancialState())){
						double incMoney=0;
						if("直接计费".equals(shipmentCost.getChargeMode())){
							incMoney=sumCost/fsdtos.size();
						}
						else if("车辆类型".equals(shipmentCost.getChargeMode())){
							incMoney=sumCost/fsdtos.size();
						}
						else if("以数量计".equals(shipmentCost.getChargeMode())){
							incMoney=sumCost*freightCost.getSumAmount()/shipmentCost.getSumAmount();
						}
						else if("以重量计".equals(shipmentCost.getChargeMode())){
							incMoney=sumCost*freightCost.getSumWeight()/shipmentCost.getSumWeight();
						}
						else if("以体积计".equals(shipmentCost.getChargeMode())){
							incMoney=sumCost*freightCost.getSumVolume()/shipmentCost.getSumVolume();
						}
						fcdao.updateCosts(incMoney, freightCost.getFreightManifestID());
					}//if
				}
				
			}
			String content="装车单支出编号：";
			content+=scdto.getShipmentManifestID();
			FinancialLog.log(Permission.getCurrentUser()
					, operType==1?"装车单支出-修改并归档":"装车单支出-修改", content);
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


	public ShipmentCostDAO getScdao() {
		return scdao;
	}


	public void setScdao(ShipmentCostDAO scdao) {
		this.scdao = scdao;
	}


	public FreightCostDAO getFcdao() {
		return fcdao;
	}


	public void setFcdao(FreightCostDAO fcdao) {
		this.fcdao = fcdao;
	}


	public FreightShipmentDAO getFsdao() {
		return fsdao;
	}


	public void setFsdao(FreightShipmentDAO fsdao) {
		this.fsdao = fsdao;
	}


	public ShipmentCostDTO getScdto() {
		return scdto;
	}


	public void setScdto(ShipmentCostDTO scdto) {
		this.scdto = scdto;
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
