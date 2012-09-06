package Logistics.Servlet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Logistics.Common.Permission;
import Logistics.Common.Tools;
import Logistics.Common.Permission.MethodCode;
import Logistics.DAO.*;
import Logistics.DTO.*;
public class FreightStateAction {
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private FreightManifestDAO fmdao=null;
	ShipmentManifestDAO smdao=null;
	private FreightShipmentDAO fsdao=null;
	//DTO对象
	private FreightManifestDTO fmdto=new FreightManifestDTO();
	ShipmentManifestDTO smdto=null;
	//输入对象
	ArrayList<Integer> fmIDList=new ArrayList<Integer>();
	
	ArrayList<FreightManifestDTO> fmList=new ArrayList<FreightManifestDTO>();
	ArrayList<FreightStateDTO> fsList=new ArrayList<FreightStateDTO>();
	String startDate;
	String endDate;
	String dateType=null;
	String newState=null;
	String stateRemarks=null;
	String ackInfo=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	
	Map data=new HashMap();
	
	public FreightStateAction(){
		fmdao=new FreightManifestDAO();
		smdao=new ShipmentManifestDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		fmdao.initiate(mysqlTools);
		smdao.initiate(mysqlTools);
		fsdao=new FreightShipmentDAO();
		fsdao.initiate(mysqlTools);
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
		if(fmdto==null || fmdto.getFreightState()==null || fmdto.getFreightState().length()==0)
		{
			this.message="缺少必要参数！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0) limit=0;
		ArrayList<FreightManifestDTO> res=null;
		FreightManifestDTO fmdto2=new FreightManifestDTO();
		Date startDay=Tools.isVoid(startDate)?null:Date.valueOf(startDate);
		Date endDay=Tools.isVoid(endDate)?null:Date.valueOf(endDate);
		if("建单日期".equals(dateType)){
			fmdto.setDateCreated(startDay);
			fmdto2.setDateCreated(endDay);
		}
		if("发货日期".equals(dateType)){
			fmdto.setDateDelivered(startDay);
			fmdto2.setDateDelivered(endDay);
		}
		if("预计到货日期".equals(dateType)){
			fmdto.setDateExpected(startDay);
			fmdto2.setDateExpected(endDay);
		}
		if("到货签收日期".equals(dateType)){
			fmdto.setDateSigned(startDay);
			fmdto2.setDateSigned(endDay);
		}
		try{
			//查询
			if(smdto==null|| smdto.getShipmentManifestID()==null){
				this.qualifiedAmount=fmdao.queryQualifiedAmount2(fmdto, fmdto2,false);
				res=fmdao.queryOnCondition2(fmdto, fmdto2, start, limit,false);
			}
			else{
				qualifiedAmount=fmdao.queryQualifiedFMsOnCondition(smdto.getShipmentManifestID(),fmdto.getFreightState());
				res=fmdao.queryFMListOnCondtition(smdto.getShipmentManifestID(),fmdto.getFreightState(), start, limit);
			}
			if(res==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			resultMapList=new ArrayList<Map>();
			
			data=new HashMap();
			
			data.put("freightState", res.size()<1||Tools.isVoid(fmdto.getFreightManifestID())||res.get(0)==null?"":res.get(0).getFreightState());
			
			for(int i=0;i<res.size();i++){
				Map m=null;
				m=new HashMap();
				m.put("freightManifestID",res.get(i).getFreightManifestID());
				//添加装车单号
				String smid="";
				FreightShipmentDTO a=new FreightShipmentDTO();
				a.setFreightManifestID(res.get(i).getFreightManifestID());
				ArrayList<FreightShipmentDTO> fsdtos=fsdao.queryOnCondition(a);
				if(fsdtos!=null){
					for(FreightShipmentDTO iter:fsdtos){
						smid+="  "+iter.getShipmentManifestID();
					}
				}
				m.put("smid", smid);
				//
				m.put("freightState", res.get(i).getFreightState());
				m.put("dateCreated",res.get(i).getDateCreated()==null?null:res.get(i).getDateCreated().toString());
				m.put("dateExpected",res.get(i).getDateExpected()==null?
						null:res.get(i).getDateExpected().toString());
				m.put("dateDelivered", res.get(i).getDateDelivered()==null?
						null:res.get(i).getDateDelivered().toString());
				m.put("dateSigned", res.get(i).getDateSigned()==null?
						null:res.get(i).getDateSigned().toString());
				m.put("origin",res.get(i).getOriginCity()+"-"+res.get(i).getOriginProvince());
				m.put("destination",res.get(i).getDestinationCity()+"-"+res.get(i).getDestinationProvince());
				m.put("dateDelivered",res.get(i).getDateDelivered()==null?null:res.get(i).getDateDelivered().toString());
				m.put("dateSigned",res.get(i).getDateSigned()==null?null:res.get(i).getDateSigned().toString());
				m.put("consigneeCompany",res.get(i).getConsigneeCompany());
				m.put("consignee",res.get(i).getConsignee());
				m.put("transitionInfo",res.get(i).getTransitionInfo());
				m.put("ackInfo",res.get(i).getAckInfo());
				m.put("customerNumber", res.get(i).getCustomerNumber());
				m.put("customer", res.get(i).getCustomer());
				
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
		if(!Permission.checkPermission(this, MethodCode.update)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
				}
		//---------------------------------------------------------------

		if(newState==null || newState.length()==0 ||
				fsList==null || fsList.size()==0||
				fmList==null || fmList.size()==0)
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		if(!Tools.validFreightState.contains(this.newState)){
			this.message="新状态有错误！";
			this.success=false;
			return "success";
		}
		try{
			//对应每个运输单，增加状态。
			FreightStateDTO fsdto=new FreightStateDTO();
			fsdto.setDate(Tools.currTimestamp());
			fsdto.setState(this.newState);
			for(int i=0;i<fsList.size();i++){
				fsdto.setFreightManifestID(fsList.get(i).getFreightManifestID());
				fsdto.setStateRemarks(fsList.get(i).getStateRemarks()==null || fsList.get(i).getStateRemarks().length()==0
						?this.stateRemarks:fsList.get(i).getStateRemarks());
				fmdao.insertFreightState(fsdto);
			}
			//对应每个运输单，修改状态备注.
			fmdto.setFreightState(this.newState);
			for(int i=0;i<fmList.size();i++){
				fmdto.setFreightManifestID(fmList.get(i).getFreightManifestID());
				fmdto.setAckInfo(fmList.get(i).getAckInfo()==null ||fmList.get(i).getAckInfo().length()==0
						?this.ackInfo:fmList.get(i).getAckInfo());
				fmdto.setTransitionInfo(fsList.get(i).getStateRemarks()==null || fsList.get(i).getStateRemarks().length()==0
						?this.stateRemarks:fsList.get(i).getStateRemarks());
				fmdao.updateFreightState(fmdto);
				if(this.newState.equals("已发货")){
					fmdao.updateDate(FreightManifestDTO.dateType.dateDelivered, fmList.get(i).getFreightManifestID());
				}
				if(this.newState.equals("已签收")){
					fmdao.updateDate(FreightManifestDTO.dateType.dateSigned, fmList.get(i).getFreightManifestID());
				}
			}
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

		if(fmdto==null || fmdto.getFreightManifestID()==null )
		{
			this.message="缺少运输单编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			ArrayList<FreightStateDTO> res=null;
			res=fmdao.queryAllStates(fmdto.getFreightManifestID());
			if(res!=null)
			{
				resultMapList=new ArrayList<Map>();
				for(int i=0;i<res.size();i++){
					Map m=new HashMap();
					m.put("state", res.get(i).getState());
					m.put("date", Tools.toString(res.get(i).getDate()));
					m.put("stateRemarks", res.get(i).getStateRemarks());
					resultMapList.add(m);
				}
			}
			mysqlTools.commit();
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
	public String queryWarnings(){
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

		try{
			int[] overtimefms;
			int unusualfms=0;
			overtimefms=fmdao.queryOvertimeManifestAmount();
			unusualfms=fmdao.queryUnusualFreightManifestAmount();
			this.data=new HashMap();
			this.data.put("overtimefms", overtimefms);
			this.data.put("unusualfms", unusualfms);
			this.data.put("overtimefmsCreate", overtimefms[0]);
			this.data.put("overtimefmsPost", overtimefms[1]);
			this.data.put("overtimefmsArrive", overtimefms[2]);
			this.data.put("overtimefmsTranship", overtimefms[3]);
			
			mysqlTools.commit();
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
	public FreightManifestDAO getFmdao() {
		return fmdao;
	}
	public void setFmdao(FreightManifestDAO fmdao) {
		this.fmdao = fmdao;
	}
	public ShipmentManifestDAO getSmdao() {
		return smdao;
	}
	public void setSmdao(ShipmentManifestDAO smdao) {
		this.smdao = smdao;
	}
	public FreightManifestDTO getFmdto() {
		return fmdto;
	}
	public void setFmdto(FreightManifestDTO fmdto) {
		this.fmdto = fmdto;
	}
	public ShipmentManifestDTO getSmdto() {
		return smdto;
	}
	public void setSmdto(ShipmentManifestDTO smdto) {
		this.smdto = smdto;
	}
	public ArrayList<Integer> getFmIDList() {
		return fmIDList;
	}
	public void setFmIDList(ArrayList<Integer> fmIDList) {
		this.fmIDList = fmIDList;
	}
	public ArrayList<FreightManifestDTO> getFmList() {
		return fmList;
	}
	public void setFmList(ArrayList<FreightManifestDTO> fmList) {
		this.fmList = fmList;
	}
	public ArrayList<FreightStateDTO> getFsList() {
		return fsList;
	}
	public void setFsList(ArrayList<FreightStateDTO> fsList) {
		this.fsList = fsList;
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
	
}
