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
public class FreightContractorAction {
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private FreightContractorDAO freightContractorDAO=null;
	private FreightRouteQuoteDAO frqdao=null;
	private CarTypeQuoteDAO ctqdao=null;
	private FreightRouteDAO frdao=null;
	private CityDAO citydao=null;
	private CarTypeDAO ctdao=null;
	//DTO对象
	private FreightContractorDTO freightContractorDTO=null;
	private FreightRouteQuoteDTO freightRouteQuoteDTO=null;
	private FreightRouteQuoteDTO frQuote=new FreightRouteQuoteDTO();
	private CarTypeQuoteDTO ctQuote=new CarTypeQuoteDTO();
	private CarTypeQuoteDTO carTypeQuoteDTO=null;
	//输入对象
	ArrayList<Integer> freightContractorIDList=null;
	ArrayList<FreightRouteQuoteDTO> frqList=null;
	ArrayList<CarTypeQuoteDTO> ctqList=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	ArrayList<Map> carTypeQuoteMapList=null;
	Map data=null;
	
	public FreightContractorAction(){
		freightContractorDAO=new FreightContractorDAO();
		this.frqdao=new FreightRouteQuoteDAO();
		this.ctqdao=new CarTypeQuoteDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		freightContractorDAO.initiate(mysqlTools);
		frqdao.initiate(mysqlTools);
		ctqdao.initiate(mysqlTools);
		freightContractorDTO=new FreightContractorDTO();
		freightRouteQuoteDTO=new FreightRouteQuoteDTO();
		frqList=new ArrayList<FreightRouteQuoteDTO>();
		ctqList=new ArrayList<CarTypeQuoteDTO>();
		freightContractorIDList=new ArrayList<Integer>();
		carTypeQuoteDTO=new CarTypeQuoteDTO();
		frdao=new FreightRouteDAO();
		frdao.initiate(mysqlTools);
		citydao=new CityDAO();
		citydao.initiate(mysqlTools);
		ctdao=new CarTypeDAO();
		ctdao.initiate(mysqlTools);
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
		if(freightContractorDTO==null)
		{
			this.message="服务器错误！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0) limit=0;
		ArrayList<FreightContractorDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=freightContractorDAO.queryAmountOnCondition(freightContractorDTO.getFreightContractorID(),freightContractorDTO.getName());
			res=freightContractorDAO.queryOnCondition(freightContractorDTO.getFreightContractorID(),freightContractorDTO.getName(),start, limit);
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
				m.put("freightContractorID",res.get(i).getFreightContractorID());
				m.put("name",res.get(i).getName());
				m.put("contact", res.get(i).getContact());
				m.put("phone", res.get(i).getPhone());
				m.put("email", res.get(i).getEmail());
				m.put("address", res.get(i).getAddress());
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
	public String queryFROnCondition(){
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
		if(frQuote==null || frQuote.getFreightContractorID()==null)
		{
			this.message="缺少必要参数！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0) limit=0;
		ArrayList<FreightRouteQuoteDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=frqdao.queryQualifiedAmount(frQuote.getFreightContractorID());
			res=frqdao.queryOnCondition(frQuote.getFreightContractorID(), start, limit);
			if(res==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			Tools.print(""+res.size()+"|"+this.qualifiedAmount);
			resultMapList=new ArrayList<Map>();
			for(int i=0;i<res.size();i++){
				String routeName=null;
				FreightRouteDTO route=frdao.getDTOByID(res.get(i).getFreightRouteID());
				if(route!=null){
					CityDTO ocity=citydao.getDTOByID(route.getOriginID());
					CityDTO dcity=citydao.getDTOByID(route.getDestinationID());
					routeName=ocity==null?"":ocity.getName();
					routeName+=" - ";
					routeName+=dcity==null?"":dcity.getName();
				}
				else{
					routeName="无运输路线的记录";
				}
				Map m=null;
				m=new HashMap();
				m.put("freightRouteID", res.get(i).getFreightRouteID());
				m.put("freightContractorID", res.get(i).getFreightContractorID());
				m.put("freightRoute", routeName);
				m.put("priceByAmount", res.get(i).getPriceByAmount());
				m.put("priceByVolume", res.get(i).getPriceByVolume());
				m.put("priceByWeight", res.get(i).getPriceByWeight());
				m.put("priceDirectly", res.get(i).getPriceDirectly());
				m.put("deliveryQuote", res.get(i).getDeliveryQuote());
				m.put("extraQuote", res.get(i).getExtraQuote());
				resultMapList.add(m);
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
	public String queryCTOnCondition(){
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
		if(ctQuote==null || ctQuote.getFreightContractorID()==null)
		{
			this.message="缺少必要参数！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0) limit=0;
		ArrayList<CarTypeQuoteDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=ctqdao.queryQualifiedAmount(ctQuote.getFreightContractorID());
			res=ctqdao.queryOnCondition(ctQuote.getFreightContractorID(), start, limit);
			if(res==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			Tools.print(""+res.size()+"|"+this.qualifiedAmount);
			resultMapList=new ArrayList<Map>();
			for(int i=0;i<res.size();i++){
				String routeName=null;
				FreightRouteDTO route=frdao.getDTOByID(res.get(i).getFreightRouteID());
				if(route!=null){
					CityDTO ocity=citydao.getDTOByID(route.getOriginID());
					CityDTO dcity=citydao.getDTOByID(route.getDestinationID());
					routeName=ocity==null?"":ocity.getName();
					routeName+=" - ";
					routeName+=dcity==null?"":dcity.getName();
				}
				else{
					routeName="无运输路线的记录";
				}
				String cartypeName=null;
				CarTypeDTO type=ctdao.getDTOByID(res.get(i).getCarTypeID());
				cartypeName=type==null?"没有车辆类型的记录":type.getCarTypeName();
				Map m=null;
				m=new HashMap();
				m.put("freightContractorID", res.get(i).getFreightContractorID());
				m.put("freightRouteID", res.get(i).getFreightRouteID());
				m.put("freightRoute", routeName);
				m.put("carTypeID", res.get(i).getCarTypeID());
				m.put("carTypeName", cartypeName);
				m.put("price", res.get(i).getPrice());
				resultMapList.add(m);
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

		if(freightContractorDTO==null || freightContractorDTO.getName()==null ||freightContractorDTO.getName().length()==0
				)
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		try{
			if(!freightContractorDAO.insert(freightContractorDTO))
			{
				mysqlTools.rollback();
				this.message="新增承运单位失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="新增承运单位成功！";
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
	
	
	
	public String insertFRQuote(){
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of insert
		if(!Permission.checkPermission(this, MethodCode.update)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
				}
		//---------------------------------------------------------------

		if(Tools.isVoid(frQuote.getFreightContractorID()) || Tools.isVoid(frQuote.getFreightRouteID())
				)
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		try{
			if(frqdao.getDTOByID(frQuote.getFreightContractorID(), frQuote.getFreightRouteID())!=null){
				mysqlTools.rollback();
				this.message="报价信息已存在！";
				this.success=false;
				return "success";
			}
			if(!frqdao.insert(frQuote)){
				mysqlTools.rollback();
				this.message="新增承运单位路线报价信息失败！";
				this.success=false;
				return "success";
			}
			
			mysqlTools.commit();
			this.message="新增承运单位路线报价信息成功！";
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
	public String insertCTQuote(){
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of insert
		if(!Permission.checkPermission(this, MethodCode.update)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
				}
		//---------------------------------------------------------------

		if(ctQuote==null || Tools.isVoid(ctQuote.getFreightContractorID()) || Tools.isVoid(ctQuote.getFreightRouteID()) 
				|| Tools.isVoid(ctQuote.getCarTypeID())
				)
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		try{
			if(ctqdao.getDTOByID(ctQuote.getFreightContractorID(), ctQuote.getFreightRouteID(), ctQuote.getCarTypeID())!=null){
				mysqlTools.rollback();
				this.message="报价信息已存在！";
				this.success=false;
				return "success";
			}
			if(!ctqdao.insert(ctQuote)){
				mysqlTools.rollback();
				this.message="新增承运单位车辆类型报价信息失败！";
				this.success=false;
				return "success";
			}
			
			mysqlTools.commit();
			this.message="新增承运单位车辆类型报价信息成功！";
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

		if(freightContractorDTO==null || freightContractorDTO.getFreightContractorID()==null )
		{
			this.message="缺少承运单位编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			freightContractorDTO=freightContractorDAO.getDTOByID(freightContractorDTO.getFreightContractorID());
			if(freightContractorDTO==null)
			{
				mysqlTools.rollback();
				this.message="查找承运单位失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("freightContractorDTO.freightContractorID", freightContractorDTO.getFreightContractorID());
			this.data.put("freightContractorDTO.name", freightContractorDTO.getName());
			this.data.put("freightContractorDTO.contact", freightContractorDTO.getContact());
			this.data.put("freightContractorDTO.phone", freightContractorDTO.getPhone());
			this.data.put("freightContractorDTO.email", freightContractorDTO.getEmail());
			this.data.put("freightContractorDTO.address", freightContractorDTO.getAddress());
			this.data.put("freightContractorDTO.remarks", freightContractorDTO.getRemarks());
			
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

		if(freightContractorDTO==null||freightContractorDTO.getFreightContractorID()==null
				|| freightContractorDTO.getName()==null ||freightContractorDTO.getName().length()==0
				)
		{
			this.message="缺少必要信息,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			Integer fcid=freightContractorDTO.getFreightContractorID();
			if( freightContractorDAO.getDTOByID(fcid)==null)
			{
				mysqlTools.rollback();
				Tools.printErr("修改承运单位的ID有错误");
				this.message="修改承运单位时，发生错误，请重试！";
				this.success=false;
				return "success";
			}
			if(!freightContractorDAO.update(freightContractorDTO))
			{
				mysqlTools.rollback();
				this.message="修改承运单位失败!";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="修改承运单位成功！";
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

		if(this.freightContractorIDList==null || this.freightContractorIDList.size()==0)
		{
			this.message="缺少承运单位编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			for(int i=0;i<this.freightContractorIDList.size();i++)
			{
				if(!freightContractorDAO.delete(this.freightContractorIDList.get(i))){
					mysqlTools.rollback();
					this.message+="删除承运单位信息失败！";
					this.success=false;
					return "success";
				}
				
			}
			mysqlTools.commit();
			this.message="删除承运单位信息成功！";
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
	public String deleteFRQuote(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of delete
		if(!Permission.checkPermission(this, MethodCode.update)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
				}
		//-----------------------------------------------------------------

		if(frqList==null || frqList.size()==0){
			this.message="缺少必要信息，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			if(!frqdao.delete(frqList)){
				mysqlTools.rollback();
				this.message="删除报价信息失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="删除承运单位信息成功！";
			this.success=true;
			return "success";
			
		}catch(Exception e){

			e.printStackTrace();
			mysqlTools.rollback();
			this.message="操作失败！";
			this.success=false;
			return "success";
		}
		finally{
			if(mysqlTools!=null)
				mysqlTools.close();
		}
	}
	public String deleteCTQuote(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of delete
		if(!Permission.checkPermission(this, MethodCode.update)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
				}
		//-----------------------------------------------------------------

		if(ctqList==null || ctqList.size()==0){
			this.message="缺少必要信息，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			if(!ctqdao.delete(ctqList)){
				mysqlTools.rollback();
				this.message="删除报价信息失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="删除承运单位信息成功！";
			this.success=true;
			return "success";
			
		}catch(Exception e){

			e.printStackTrace();
			mysqlTools.rollback();
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

		
		ArrayList<FreightContractorDTO> res=null;
		try{
			//查询
			res=freightContractorDAO.queryNameID();
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
				m.put("freightContractorID",res.get(i).getFreightContractorID());
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
	public FreightContractorDAO getFreightContractorDAO() {
		return freightContractorDAO;
	}
	public void setFreightContractorDAO(FreightContractorDAO freightContractorDAO) {
		this.freightContractorDAO = freightContractorDAO;
	}
	public FreightRouteQuoteDAO getFrqdao() {
		return frqdao;
	}
	public void setFrqdao(FreightRouteQuoteDAO frqdao) {
		this.frqdao = frqdao;
	}
	public CarTypeQuoteDAO getCtqdao() {
		return ctqdao;
	}
	public void setCtqdao(CarTypeQuoteDAO ctqdao) {
		this.ctqdao = ctqdao;
	}
	public FreightRouteDAO getFrdao() {
		return frdao;
	}
	public void setFrdao(FreightRouteDAO frdao) {
		this.frdao = frdao;
	}
	public CityDAO getCitydao() {
		return citydao;
	}
	public void setCitydao(CityDAO citydao) {
		this.citydao = citydao;
	}
	public CarTypeDAO getCtdao() {
		return ctdao;
	}
	public void setCtdao(CarTypeDAO ctdao) {
		this.ctdao = ctdao;
	}
	public FreightContractorDTO getFreightContractorDTO() {
		return freightContractorDTO;
	}
	public void setFreightContractorDTO(FreightContractorDTO freightContractorDTO) {
		this.freightContractorDTO = freightContractorDTO;
	}
	public FreightRouteQuoteDTO getFreightRouteQuoteDTO() {
		return freightRouteQuoteDTO;
	}
	public void setFreightRouteQuoteDTO(FreightRouteQuoteDTO freightRouteQuoteDTO) {
		this.freightRouteQuoteDTO = freightRouteQuoteDTO;
	}
	public FreightRouteQuoteDTO getFrQuote() {
		return frQuote;
	}
	public void setFrQuote(FreightRouteQuoteDTO frQuote) {
		this.frQuote = frQuote;
	}
	public CarTypeQuoteDTO getCtQuote() {
		return ctQuote;
	}
	public void setCtQuote(CarTypeQuoteDTO ctQuote) {
		this.ctQuote = ctQuote;
	}
	public CarTypeQuoteDTO getCarTypeQuoteDTO() {
		return carTypeQuoteDTO;
	}
	public void setCarTypeQuoteDTO(CarTypeQuoteDTO carTypeQuoteDTO) {
		this.carTypeQuoteDTO = carTypeQuoteDTO;
	}
	public ArrayList<Integer> getFreightContractorIDList() {
		return freightContractorIDList;
	}
	public void setFreightContractorIDList(
			ArrayList<Integer> freightContractorIDList) {
		this.freightContractorIDList = freightContractorIDList;
	}
	public ArrayList<FreightRouteQuoteDTO> getFrqList() {
		return frqList;
	}
	public void setFrqList(ArrayList<FreightRouteQuoteDTO> frqList) {
		this.frqList = frqList;
	}
	public ArrayList<CarTypeQuoteDTO> getCtqList() {
		return ctqList;
	}
	public void setCtqList(ArrayList<CarTypeQuoteDTO> ctqList) {
		this.ctqList = ctqList;
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
	
}
