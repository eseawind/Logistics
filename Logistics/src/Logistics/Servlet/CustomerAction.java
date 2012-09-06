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

public class CustomerAction {
	
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private CustomerDAO customerDAO=null;
	private CustomerQuoteDAO cqdao=null;
	private FreightRouteDAO frdao=null;
	private CityDAO citydao=null;
	//DTO对象
	private CustomerDTO customerDTO=null;
	private CustomerQuoteDTO quote=new CustomerQuoteDTO();
	//输入对象
	ArrayList<Integer> customerIDList=null;
	ArrayList<CustomerQuoteDTO> ids=new ArrayList<CustomerQuoteDTO>();
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public CustomerAction(){
		customerDAO=new CustomerDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		customerDAO.initiate(mysqlTools);
		customerDTO=new CustomerDTO();
		customerIDList=new ArrayList<Integer>();
		cqdao=new CustomerQuoteDAO();
		cqdao.initiate(mysqlTools);
		frdao=new FreightRouteDAO();
		frdao.initiate(mysqlTools);
		citydao=new CityDAO();
		citydao.initiate(mysqlTools);
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
		if(customerDTO==null)
		{
			this.message="缺少查询条件！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0)	limit=0;
		ArrayList<CustomerDTO> res=null;
		try{
			//查询
			Tools.print(""+customerDTO.getName());
			Tools.print(""+customerDTO.getName().length());
			this.qualifiedAmount=customerDAO.queryAmountByCondition(customerDTO.getCustomerID(),customerDTO.getName());
			res=customerDAO.queryByCondition(customerDTO.getCustomerID(),customerDTO.getName(),start, limit);
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
				m.put("customerID",res.get(i).getCustomerID());
				m.put("name",res.get(i).getName());
				m.put("type", res.get(i).getType());
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
	public String queryQuoteOnCondition(){
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
		if(customerDTO==null || customerDTO.getCustomerID()==null)
		{
			this.message="缺少查询条件！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0)	limit=0;
		ArrayList<CustomerQuoteDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=cqdao.queryAmountByCondition(customerDTO.getCustomerID());
			res=cqdao.queryByCondition(customerDTO.getCustomerID(), start, limit);
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
				String routeName="";
				FreightRouteDTO tRoute=frdao.getDTOByID(res.get(i).getFreightRouteID());
				if(tRoute==null){
					routeName="不存在";
				}
				else{
					CityDTO ocity=citydao.getDTOByID(tRoute.getOriginID());
					CityDTO dcity=citydao.getDTOByID(tRoute.getDestinationID());
					routeName=ocity==null?"":ocity.getName();
					routeName+=" - ";
					routeName+=dcity==null?"":dcity.getName();
				}
				Map m=null;
				m=new HashMap();
				m.put("customerID",res.get(i).getCustomerID());
				m.put("freightRouteID",res.get(i).getFreightRouteID());
				m.put("routeName", routeName);
				m.put("priceByAmount", res.get(i).getPriceByAmount());
				m.put("priceByVolume", res.get(i).getPriceByVolume());
				m.put("priceByWeight", res.get(i).getPriceByWeight());
				m.put("takingQuote", res.get(i).getTakingQuote());
				m.put("deliveryQuote", res.get(i).getDeliveryQuote());
				
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

		
		if(customerDTO==null || customerDTO.getName()==null ||customerDTO.getName().length()==0
				||customerDTO.getType()==null||!(customerDTO.getType().equals("合同客户")||customerDTO.getType().equals("零散客户")))
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		try{
			
			
			if(!customerDAO.insert(customerDTO))
			{
				mysqlTools.rollback();
				this.message="新增客户失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="新增客户成功！";
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
	public String insertQuote(){
		//check permission of basic query
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

		
		if(quote==null || quote.getCustomerID()==null || quote.getFreightRouteID()==null)
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		try{
			
			if(customerDAO.getDTOByID(quote.getCustomerID())==null){
				mysqlTools.rollback();
				this.message="客户编号为"+quote.getCustomerID()+"的客户不存在!";
				this.success=false;
				return "success";
			}
			FreightRouteDTO route=null;
			route=frdao.getDTOByID(quote.getFreightRouteID());
			if(route==null){
				mysqlTools.rollback();
				this.message="路线编号为"+quote.getFreightRouteID()+"的路线不存在!";
				this.success=false;
				return "success";
			}
			if(cqdao.getDTOByID(quote.getCustomerID(), quote.getFreightRouteID())!=null){
				mysqlTools.rollback();
				this.message="该客户已存在此路线的报价，若要修改，请删除原有报价后再新增报价";
				this.success=false;
				return "success";
			}
			if(!cqdao.insert(quote))
			{
				mysqlTools.rollback();
				this.message="新增客户报价失败！";
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
			this.message="操作失败！";
			this.success=false;
			return "success";
		}
		finally{
			if(mysqlTools!=null)
				mysqlTools.close();
		}
	}
	public String deleteQuote(){
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

		if(this.ids==null || this.ids.size()==0)
		{
			this.message="缺少客户和路线编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			if(!cqdao.delete(ids)){
				mysqlTools.rollback();
				this.message="删除报价信息失败!";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="删除客户信息成功！";
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

		
		if(customerDTO==null || customerDTO.getCustomerID()==null )
		{
			this.message="缺少客户编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			customerDTO=customerDAO.getDTOByID(customerDTO.getCustomerID());
			if(customerDTO==null)
			{
				mysqlTools.rollback();
				this.message="查找客户失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("customerDTO.customerID", customerDTO.getCustomerID());
			this.data.put("customerDTO.type", customerDTO.getType());
			this.data.put("customerDTO.name", customerDTO.getName());
			this.data.put("customerDTO.contact", customerDTO.getContact());
			this.data.put("customerDTO.phone", customerDTO.getPhone());
			this.data.put("customerDTO.email", customerDTO.getEmail());
			this.data.put("customerDTO.address", customerDTO.getAddress());
			
			this.data.put("customerDTO.stockInCostPerCount", customerDTO.getStockInCostPerCount());
			this.data.put("customerDTO.stockInCostPerVolume", customerDTO.getStockInCostPerVolume());
			this.data.put("customerDTO.stockInCostPerWeight", customerDTO.getStockInCostPerWeight());
			this.data.put("customerDTO.stockOutCostPerCount", customerDTO.getStockOutCostPerCount());
			this.data.put("customerDTO.stockOutCostPerVolume", customerDTO.getStockOutCostPerVolume());
			this.data.put("customerDTO.stockOutCostPerWeight", customerDTO.getStockOutCostPerWeight());
			this.data.put("customerDTO.stockCostPerCount", customerDTO.getStockCostPerCount());
			this.data.put("customerDTO.stockCostPerVolume", customerDTO.getStockCostPerVolume());
			this.data.put("customerDTO.stockCostPerWeight", customerDTO.getStockCostPerWeight());
			
			this.data.put("customerDTO.remarks", customerDTO.getRemarks());
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

		if(customerDTO==null||customerDTO.getCustomerID()==null
				|| customerDTO.getName()==null ||customerDTO.getName().length()==0
				||customerDTO.getType()==null||!(customerDTO.getType().equals("合同客户")||customerDTO.getType().equals("零散客户"))
				)
		{
			this.message="缺少必要信息,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			if(!customerDAO.update(customerDTO))
			{
				mysqlTools.rollback();
				this.message="修改客户失败!";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="修改客户成功！";
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

		if(this.customerIDList==null || this.customerIDList.size()==0)
		{
			this.message="缺少客户编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			for(int i=0;i<this.customerIDList.size();i++)
			{
				if(!customerDAO.delete(this.customerIDList.get(i))){
					mysqlTools.rollback();
					this.message+="删除客户信息失败！";
					this.success=false;
					return "success";
				}
				
			}
			mysqlTools.commit();
			this.message="删除客户信息成功！";
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

		ArrayList<CustomerDTO> res=null;
		try{
			//查询
			res=customerDAO.queryNameID(customerDTO==null?null:customerDTO.getType());
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
				m.put("customerID",res.get(i).getCustomerID());
				//m.put("name", res.get(i).getName());
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
	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}
	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}
	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}
	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}
	public ArrayList<Integer> getCustomerIDList() {
		return customerIDList;
	}
	public void setCustomerIDList(ArrayList<Integer> customerIDList) {
		this.customerIDList = customerIDList;
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
	public CustomerQuoteDAO getCqdao() {
		return cqdao;
	}
	public void setCqdao(CustomerQuoteDAO cqdao) {
		this.cqdao = cqdao;
	}
	public FreightRouteDAO getFrdao() {
		return frdao;
	}
	public void setFrdao(FreightRouteDAO frdao) {
		this.frdao = frdao;
	}
	public CustomerQuoteDTO getQuote() {
		return quote;
	}
	public void setQuote(CustomerQuoteDTO quote) {
		this.quote = quote;
	}
	public CityDAO getCitydao() {
		return citydao;
	}
	public void setCitydao(CityDAO citydao) {
		this.citydao = citydao;
	}
	public ArrayList<CustomerQuoteDTO> getIds() {
		return ids;
	}
	public void setIds(ArrayList<CustomerQuoteDTO> ids) {
		this.ids = ids;
	}
	
	

}
