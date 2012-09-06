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
public class MessageAction extends ActionSupport{
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
	private MessageDAO mdao=null;
	
	//DTO对象
	private MessageDTO mdto=new MessageDTO();
	
	//输入对象
	ArrayList<Integer> ids=new ArrayList<Integer>();
	
	String startDate=null;
	String endDate=null;
	String dateType=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	ArrayList<Map> newsList=null;
	ArrayList<Map> announcementList=null;
	ArrayList<Map> logisticsNews=null;
	ArrayList<Map> knowledgeList=null;
	ArrayList<Map> cultureList=null;
	ArrayList<Map> actionNews=null;
	
	Map data=null;
	
	public MessageAction() {
		
		
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		mdao=new MessageDAO();
		mdao.initiate(mysqlTools);
				
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

		
		try{
			if(upload!=null){
				File newFile=new File(Data.newUploadFilePath(uploadFileName));
				newFile.delete();
				upload.renameTo(newFile);
				mdto.setAttachment(newFile.getAbsolutePath());
				mdto.setOriginName(this.uploadFileName);
			}
			//If the type is empty, give it a default name
			if(Tools.isVoid(mdto.getType())){
				mdto.setType(MessageDTO.Type.news.toString());
			}
			
			if(!mdao.insert(mdto)){
				mysqlTools.rollback();
				this.message="新增失败!";
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
	
	public String download(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of download
		if(!Permission.checkPermission(this, MethodCode.download)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "error";
				}
		//---------------------------------------------------------------

		
		try{
			MessageDTO mess=null;
			if(mdto!=null)
				mess=mdao.getDTOByID(mdto.getMessageID());
			if(mess==null){
				this.message="所选信息不存在!";
				this.success=false;
				return "error";
			}
			if(Tools.isVoid(mess.getAttachment())){
				this.message="没有附件可供下载 !";
				this.success=false;
				return "error";
			}
			File file=new File(mess.getAttachment());
			if(!file.exists()){
				this.message="下载失败,文件不存在！";
				this.success=false;
				return "error";
			}
			download=new FileInputStream(file);
			this.downloadFileName=new String(mess.getOriginName().getBytes("UTF-8"),"iso-8859-1");
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
		ArrayList<MessageDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=mdao.queryAmountByCondition(mdto, startDay, endDay);
			res=mdao.queryOnCondition(mdto, startDay, endDay, start, limit);
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
			for(MessageDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("messageID",iter.getMessageID());
				m.put("type", iter.getType());
				m.put("datePosted", Tools.toString(iter.getDatePosted()));
				m.put("header", iter.getHeader());
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

		
		if(mdto==null || mdto.getMessageID()==null )
		{
			this.message="缺少编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			mdto=mdao.getDTOByID(mdto.getMessageID());
			if(mdto==null)
			{
				mysqlTools.rollback();
				this.message="查找失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("mdto.messageID", mdto.getMessageID());
			this.data.put("mdto.header", mdto.getHeader());
			this.data.put("mdto.datePosted", Tools.toString(mdto.getDatePosted()));
			this.data.put("mdto.type", mdto.getType());
			this.data.put("mdto.body", mdto.getBody());
			this.data.put("mdto.originName", mdto.getOriginName());
			
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

		if(mdto==null||mdto.getMessageID()==null
				)
		{
			this.message="缺少编号,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			MessageDTO mess=mdao.getDTOByID(mdto.getMessageID());
			if(mess==null){
				mysqlTools.rollback();
				this.message="修改失败,原纪录不存在!";
				this.success=false;
				return "success";
			}
			
			if(upload!=null){
				//删除旧文件
				if(mess.getAttachment()!=null){
					File oldFile=new File(mess.getAttachment());
					oldFile.delete();
				}
				File newFile=new File(Data.newUploadFilePath(uploadFileName));
				upload.renameTo(newFile);
				mdto.setAttachment(newFile.getAbsolutePath());
				mdto.setOriginName(this.uploadFileName);
			}
			else{
				mdto.setAttachment(mess.getAttachment());
				mdto.setOriginName(mess.getOriginName());
			}
			if(!mdao.update(mdto))
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
				mdto=mdao.getDTOByID(id);
				if(mdto!=null){
					//删除旧文件
					if(mdto.getAttachment()!=null){
						File oldFile=new File(mdto.getAttachment());
						oldFile.delete();
					}
				}
				if(!mdao.delete(id)){
					mysqlTools.rollback();
					this.message+="删除信息失败！";
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

	
	public String queryNews(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		ArrayList<MessageDTO> res=null;
		try{
			//查询
			res=mdao.queryNews(limit);
			if(res==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			
			this.announcementList=new ArrayList<Map>();
			this.newsList=new ArrayList<Map>();
			this.logisticsNews=new ArrayList<Map>();
			this.actionNews=new ArrayList<Map>();
			this.cultureList=new ArrayList<Map>();
			this.knowledgeList=new ArrayList<Map>();
			int acount=0,ncount=0,lcount=0,kcount=0,ccount=0,atcount=0;
			for(MessageDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("messageID",iter.getMessageID());
				m.put("datePosted", Tools.toString(iter.getDatePosted()));
				m.put("header", iter.getHeader().length()>12?iter.getHeader().substring(0, 11)+"...":iter.getHeader());
				switch(iter.getMsgType()){
				case announcement:
					announcementList.add(m);
					acount++;
					break;
				case news:
					newsList.add(m);
					ncount++;
					break;
				case logisticsNews:
					logisticsNews.add(m);
					lcount++;
					break;
				case knowledge:
					knowledgeList.add(m);
					kcount++;
					break;
				case culture:
					cultureList.add(m);
					ccount++;
					break;
				case actionNews:
					actionNews.add(m);
					atcount++;
					break;
					
				}
			}
			this.data=new HashMap();
			this.data.put("acount", acount);
			this.data.put("ncount", ncount);
			this.data.put("lcount", lcount);
			this.data.put("kcount", kcount);
			this.data.put("ccount", ccount);
			this.data.put("atcount", atcount);
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

	public MessageDAO getMdao() {
		return mdao;
	}

	public void setMdao(MessageDAO mdao) {
		this.mdao = mdao;
	}

	public MessageDTO getMdto() {
		return mdto;
	}

	public void setMdto(MessageDTO mdto) {
		this.mdto = mdto;
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

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public ArrayList<Map> getKnowledgeList() {
		return knowledgeList;
	}

	public void setKnowledgeList(ArrayList<Map> knowledgeList) {
		this.knowledgeList = knowledgeList;
	}

	public ArrayList<Map> getCultureList() {
		return cultureList;
	}

	public void setCultureList(ArrayList<Map> cultureList) {
		this.cultureList = cultureList;
	}

	public ArrayList<Map> getActionNews() {
		return actionNews;
	}

	public void setActionNews(ArrayList<Map> actionNews) {
		this.actionNews = actionNews;
	}
	

}
