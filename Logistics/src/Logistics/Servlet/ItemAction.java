package Logistics.Servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Logistics.Common.Data;
import Logistics.Common.LExcel;
import Logistics.Common.Permission;
import Logistics.Common.Tools;
import Logistics.Common.Permission.MethodCode;
import Logistics.DAO.*;
import Logistics.DTO.*;

public class ItemAction {
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
	private InputStream download;
	private String downloadFileName;
	//DAO对象
	private ItemDAO itemDAO=null;
	//DTO对象
	private ItemDTO itemDTO=null;
	//输入对象
	ArrayList<Integer> itemIDList=new ArrayList<Integer>();
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public ItemAction(){
		itemDAO=new ItemDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		itemDAO.initiate(mysqlTools);
		itemDTO=new ItemDTO();
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
		if(itemDTO==null)
		{
			this.message="缺少查询条件！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0)	limit=0;
		ArrayList<ItemDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=itemDAO.queryAmountByCondition(itemDTO.getNumber(),itemDTO.getName());
			res=itemDAO.queryByCondition(itemDTO.getNumber(),itemDTO.getName(),start, limit);
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
			for(int i=0;i<res.size();i++){
				Map m=null;
				m=new HashMap();
				m.put("itemID",res.get(i).getItemID());
				m.put("number", res.get(i).getNumber());
				m.put("name",res.get(i).getName());
				m.put("batch", res.get(i).getBatch());
				m.put("unit", res.get(i).getUnit());
				m.put("unitVolume", res.get(i).getUnitVolume());
				m.put("unitWeight", res.get(i).getUnitWeight());
				m.put("remarks", res.get(i).getRemarks());
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
			download=new FileInputStream(new File(Data.itemTemplateFilePath()));
			
			if(download==null){
				this.message="下载失败,模板不存在！";
				this.success=false;
				return "error";
			}
			this.downloadFileName=new String("物料导入模板.xls".getBytes("UTF-8"),"iso-8859-1");
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
	public String importFile(){
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
		ArrayList<ItemDTO> result=null;
		try{
			if(upload==null){
				this.message="导入失败,上传文件失败!";
				this.success=false;
				return "success";
			}
			result=LExcel.excel2Items(new FileInputStream(upload));
			if(result==null || result.size()==0){
				this.message="没有合法的记录可供导入";
				this.success=false;
				return "success";
			}
			if(!this.itemDAO.insert(result)){
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
	public String insert(){
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

		
		if(itemDTO==null || itemDTO.getName()==null ||itemDTO.getName().length()==0
				||itemDTO.getUnit()==null || itemDTO.getUnit().length()==0)
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		try{
			
			
			if(!itemDAO.insert(itemDTO))
			{
				mysqlTools.rollback();
				this.message="新增物料失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="新增物料成功！";
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

		
		if(itemDTO==null || itemDTO.getItemID()==null )
		{
			this.message="缺少物料编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			itemDTO=itemDAO.getDTOByID(itemDTO.getItemID());
			if(itemDTO==null)
			{
				mysqlTools.rollback();
				this.message="查找物料失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("itemDTO.itemID", itemDTO.getItemID());
			this.data.put("itemDTO.batch", itemDTO.getBatch());
			this.data.put("itemDTO.number", itemDTO.getNumber());
			this.data.put("itemDTO.name", itemDTO.getName());
			this.data.put("itemDTO.unit", itemDTO.getUnit());
			this.data.put("itemDTO.unitVolume", itemDTO.getUnitVolume());
			this.data.put("itemDTO.unitWeight", itemDTO.getUnitWeight());
			this.data.put("itemDTO.remarks", itemDTO.getRemarks());
			
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

		
		if(itemDTO==null||itemDTO.getItemID()==null
				|| itemDTO.getName()==null ||itemDTO.getName().length()==0
				||itemDTO.getUnit()==null || itemDTO.getUnit().length()==0
				)
		{
			this.message="缺少必要信息,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			if(!itemDAO.update(itemDTO))
			{
				mysqlTools.rollback();
				this.message="修改物料失败!";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="修改物料成功！";
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

		
		if(this.itemIDList==null || this.itemIDList.size()==0)
		{
			this.message="缺少物料编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			for(int i=0;i<this.itemIDList.size();i++)
			{
				if(!itemDAO.delete(this.itemIDList.get(i))){
					mysqlTools.rollback();
					this.message+="删除物料信息失败！";
					this.success=false;
					return "success";
				}
				
			}
			mysqlTools.commit();
			this.message="删除物料信息成功！";
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
	public String queryNameID(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------
		ArrayList<ItemDTO> res=null;
		try{
			//查询
			res=itemDAO.queryNameID();
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
				m.put("itemID",res.get(i).getItemID());
				m.put("jointName",res.get(i).jointName());
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
	public ItemDAO getItemDAO() {
		return itemDAO;
	}
	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}
	public ItemDTO getItemDTO() {
		return itemDTO;
	}
	public void setItemDTO(ItemDTO itemDTO) {
		this.itemDTO = itemDTO;
	}
	public ArrayList<Integer> getItemIDList() {
		return itemIDList;
	}
	public void setItemIDList(ArrayList<Integer> itemIDList) {
		this.itemIDList = itemIDList;
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
	public String getDownloadFileName() {
		return downloadFileName;
	}
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

}
