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
import Logistics.Common.LExcel;
import Logistics.Common.Permission;
import Logistics.Common.Tools;
import Logistics.Common.Permission.MethodCode;
import Logistics.DAO.*;
import Logistics.DTO.*;
public class RoleAction extends ActionSupport{
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
	private RoleDAO rdao=null;
	
	//DTO对象
	private RoleDTO role=new RoleDTO();
	
	//输入对象
	ArrayList<Integer> ids=new ArrayList<Integer>();
	ArrayList<PermissionDTO> permissions=new ArrayList<PermissionDTO>();
	String startDate=null;
	String endDate=null;
	String dateType=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	ArrayList<Map> newsList=null;
	ArrayList<Map> announcementList=null;
	ArrayList<Map> logisticsNews=null;
	
	Map data=null;
	
	public RoleAction() {
		
		
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		rdao=new RoleDAO();
		rdao.initiate(mysqlTools);
				
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

		if(role==null ||Tools.isVoid(role.getRoleName()) || permissions==null || permissions.size()==0){
			this.message="缺少必要信息";
			this.success=false;
			return "success";
		}
		try{
			for(PermissionDTO piter:permissions){
				boolean isMemberof=false;
				for(String actionName:Permission.actions){
					if(actionName.equals(piter.getActionName())){
						isMemberof=true;
						break;
					}
				}
				if(!isMemberof){
					this.message="提交的ActionName有误！";
					this.success=false;
					return "success";
				}
			}
			if(!rdao.insert(role, permissions)){
				mysqlTools.rollback();
				this.message="新增角色失败！";
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
		ArrayList<RoleDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=rdao.queryQualifiedAmount();
			res=rdao.queryOnCondtition(start, limit);
			if(res==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			resultMapList=new ArrayList<Map>();
			for(RoleDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("roleID",iter.getRoleID());
				m.put("roleName", iter.getRoleName());
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

		
		if(role==null || role.getRoleID()==null )
		{
			this.message="缺少编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			role=rdao.getDTOByID(role.getRoleID());
			if(role==null)
			{
				mysqlTools.rollback();
				this.message="未找到！";
				this.success=false;
				return "success";
			}
			permissions=rdao.getPermissions(role.getRoleID());
			this.data=new HashMap();
			this.data.put("role.roleID", role.getRoleID());
			this.data.put("role.roleName", role.getRoleName());
			this.data.put("role.remarks", role.getRemarks());
			resultMapList=new ArrayList<Map>();
			
			for(PermissionDTO piter:permissions){
				Map m=new HashMap();
				m.put("actionName", piter.getActionName());
				m.put("permission", piter.getPermission());
				resultMapList.add(m);
			}
			
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

		
		if(role==null||role.getRoleID()==null || Tools.isVoid(role.getRoleName())
				)
		{
			this.message="缺少编号,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			RoleDTO oldRole=rdao.getDTOByID(role.getRoleID());
			if(oldRole==null){
				mysqlTools.rollback();
				this.message="修改失败,原纪录不存在!";
				this.success=false;
				return "success";
			}
			for(PermissionDTO piter:permissions){
				boolean isMemberof=false;
				for(String actionName:Permission.actions){
					if(actionName.equals(piter.getActionName())){
						isMemberof=true;
						break;
					}
				}
				if(!isMemberof){
					this.message="提交的ActionName有误！";
					this.success=false;
					return "success";
				}
			}
			if(!rdao.update(role,permissions))
			{
				mysqlTools.rollback();
				this.message="修改失败!";
				this.success=false;
				return "success";
			}
			
			
			mysqlTools.commit();
			this.message="修改成功！";
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
			this.message="缺少编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			for(Integer id:ids){
				if(!rdao.delete(id)){
					mysqlTools.rollback();
					this.message+="删除角色失败！";
					this.success=false;
					return "success";
				}
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

	public RoleDAO getRdao() {
		return rdao;
	}

	public void setRdao(RoleDAO rdao) {
		this.rdao = rdao;
	}

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}

	public ArrayList<Integer> getIds() {
		return ids;
	}

	public void setIds(ArrayList<Integer> ids) {
		this.ids = ids;
	}

	public ArrayList<PermissionDTO> getPermissions() {
		return permissions;
	}

	public void setPermissions(ArrayList<PermissionDTO> permissions) {
		this.permissions = permissions;
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

	public ArrayList<Map> getNewsList() {
		return newsList;
	}

	public void setNewsList(ArrayList<Map> newsList) {
		this.newsList = newsList;
	}

	public ArrayList<Map> getAnnouncementList() {
		return announcementList;
	}

	public void setAnnouncementList(ArrayList<Map> announcementList) {
		this.announcementList = announcementList;
	}

	public ArrayList<Map> getLogisticsNews() {
		return logisticsNews;
	}

	public void setLogisticsNews(ArrayList<Map> logisticsNews) {
		this.logisticsNews = logisticsNews;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}


}
