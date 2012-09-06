package Logistics.Servlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

import Logistics.Common.Data;
import Logistics.Common.DtoToExcel;
import Logistics.Common.LExcel;
import Logistics.Common.Permission;
import Logistics.Common.Tools;
import Logistics.Common.Permission.MethodCode;
import Logistics.DAO.*;
import Logistics.DTO.*;
public class BarcodeAction extends FileUploadAction {
	//基本对象
	protected File upload;
	protected String uploadContentType;
	protected String uploadFileName;
	private InputStream download;
	private String downloadFileName;
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private BarcodeDAO bdao=null;
	
	//DTO对象
	private BarcodeDTO barcode=new BarcodeDTO();
	
	//输入对象
	ArrayList<Integer> ids=new ArrayList<Integer>();
	
	String startDate=null;
	String endDate=null;
	String dateType=null;
	String eid=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	ArrayList<Map> carTypeQuoteMapList=null;
	Map data=null;
	
	public BarcodeAction() {
		
		
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		bdao=new BarcodeDAO();
		bdao.initiate(mysqlTools);
				
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
			this.message="缺少条码编号，无法导出！";
			this.success=false;
			return "error";
		}
		LExcel le=null;
		try{
			String[] ids=eid.split("_");
			ArrayList<DtoToExcel> incomeList= new ArrayList<DtoToExcel>();
			for(String id:ids){
				Integer fmid=Integer.parseInt(id);
				BarcodeDTO income=this.bdao.getDTOByID(fmid);
				if(income!=null){
					incomeList.add(income);
				}
			}
			if(incomeList==null || incomeList.size()==0){
				this.message="缺少条码记录，无法导出！";
				this.success=false;
				return "error";
			}
			le=new LExcel();
			le.toSheet(incomeList, "条码");
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
	
	public String importBarcodes(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		
		if(!Permission.checkPermission(this, MethodCode.importFile)){
			this.message="请求失败，用户没有权限进行此项操作！";
			this.success=false;
			return "success";
		}
		ArrayList<BarcodeDTO> result=null;
		try{
			if(upload==null){
				this.message="导入失败,上传文件失败!";
				this.success=false;
				return "success";
			}
			result=LExcel.excel2Barcodes(new FileInputStream(upload));
			if(result==null || result.size()==0){
				this.message="没有合法的记录可供导入";
				this.success=false;
				return "success";
			}
			if(!bdao.insert(result)){
				mysqlTools.rollback();
				this.message="导入失败!";
				this.success=false;
				return "success";
			}
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
	
	public String downloadTemplate(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		if(!Permission.checkPermission(this, MethodCode.download)){
			this.message="请求失败，用户没有权限进行此项操作！";
			this.success=false;
			return "error";
		}
		
		try{
			download=new FileInputStream(new File(Data.barcodeTemplateFilePath()));
			
			if(download==null){
				this.message="下载失败,模板不存在！";
				this.success=false;
				return "error";
			}
			this.downloadFileName=new String("条码导入模板.xls".getBytes("UTF-8"),"iso-8859-1");
			this.message="成功!";
			this.success=true;
			return "success";
		}catch(Exception e){
			mysqlTools.rollback();
			e.printStackTrace();
			this.message="操作失败!";
			this.success=false;
			return "error";
		}
		finally{
			if(mysqlTools!=null)
				mysqlTools.close();
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

		if(this.start<0) start=0;
		if(this.limit<0) limit=0;
		Date startDay=null;
		Date endDay=null;
		if(startDate!=null && startDate.length()!=0)
			startDay=Date.valueOf(startDate);
		if(endDate!=null && endDate.length()!=0)
			endDay=Date.valueOf(endDate);
		ArrayList<BarcodeDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=bdao.queryAmountByCondition(barcode);
			res=bdao.queryByCondition(barcode, start, limit);
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
			for(BarcodeDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("barcodeID",iter.getBarcodeID());
				m.put("manifestID",iter.getManifestID());
				m.put("operationType",iter.getOperationType());
				m.put("barcode", iter.getBarcode());
				m.put("amount", iter.getAmount());
				m.put("itemID", iter.getItemID());
				m.put("itemName", iter.getItemName());
				m.put("itemNumber", iter.getItemNumber());
				m.put("batch", iter.getBatch());
				m.put("customerID", iter.getCustomerID());
				m.put("customer", iter.getCustomer());
				m.put("warehouseID", iter.getWarehouseID());
				m.put("warehouse", iter.getWarehouse());
				m.put("remarks", iter.getRemarks());
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

		
		if(barcode==null || barcode.getBarcodeID()==null )
		{
			this.message="缺少条码编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			barcode=bdao.getDTOByID(barcode.getBarcodeID());
			if(barcode==null)
			{
				mysqlTools.rollback();
				this.message="查找条码失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("barcode.barcodeID", barcode.getBarcodeID());
			this.data.put("barcode.manifestID", barcode.getManifestID());
			this.data.put("barcode.operationType", barcode.getOperationType());
			this.data.put("barcode.barcode", barcode.getBarcode());
			this.data.put("barcode.amount", barcode.getAmount());
			this.data.put("barcode.itemID", barcode.getItemID());
			this.data.put("barcode.itemName", barcode.getItemName());
			this.data.put("barcode.itemNumber", barcode.getItemNumber());
			this.data.put("barcode.batch", barcode.getBatch());
			this.data.put("barcode.warehouseID", barcode.getWarehouseID());
			this.data.put("barcode.warehouse", barcode.getWarehouse());
			this.data.put("barcode.customerID", barcode.getCustomerID());
			this.data.put("barcode.customer", barcode.getCustomer());
			this.data.put("barcode.remarks", barcode.getRemarks());
			
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

		if(barcode==null||barcode.getBarcodeID()==null
				)
		{
			this.message="缺少条码编号,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			
			if(!bdao.update(barcode))
			{
				mysqlTools.rollback();
				this.message="修改条码失败!";
				this.success=false;
				return "success";
			}
			
			
			mysqlTools.commit();
			this.message="修改条码成功！";
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

		if(this.ids==null || this.ids.size()==0)
		{
			this.message="缺少条码编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			if(!bdao.delete(ids)){
				mysqlTools.rollback();
				this.message+="删除条码信息失败！";
				this.success=false;
				return "success";
			}
			
			mysqlTools.commit();
			this.message="删除条码信息成功！";
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

	public BarcodeDAO getBdao() {
		return bdao;
	}

	public void setBdao(BarcodeDAO bdao) {
		this.bdao = bdao;
	}

	public BarcodeDTO getBarcode() {
		return barcode;
	}

	public void setBarcode(BarcodeDTO barcode) {
		this.barcode = barcode;
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

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}


	public String getEid() {
		return eid;
	}


	public void setEid(String eid) {
		this.eid = eid;
	}
	
	

}
