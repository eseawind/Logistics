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
public class InventoryManifestAction {
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private StockItemDAO sidao=null;
	private InventoryManifestDAO imdao=null;
	private WarehouseDAO wdao=null;
	private ItemDAO itemdao=null;
	private InventoryItemDAO iidao=null;
	//DTO对象
	private StockItemDTO sidto=new StockItemDTO();
	private InventoryManifestDTO imdto=new InventoryManifestDTO();
	//输入对象
	ArrayList<Integer> imIDList=new ArrayList<Integer>();
	ArrayList<InventoryItemDTO> itemList=new ArrayList<InventoryItemDTO>();
	String startDate=null;
	String endDate=null;
	String dateType=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	ArrayList<Map> carTypeQuoteMapList=null;
	Map data=null;
	
	public InventoryManifestAction(){
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		imdao=new InventoryManifestDAO();
		imdao.initiate(mysqlTools);
		sidao=new StockItemDAO();
		sidao.initiate(mysqlTools);
		itemdao=new ItemDAO();
		itemdao.initiate(mysqlTools);
		wdao=new WarehouseDAO();
		wdao.initiate(mysqlTools);
		iidao=new InventoryItemDAO();
		iidao.initiate(mysqlTools);
		
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
		ArrayList<InventoryManifestDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=imdao.queryQualifiedAmountOnCondition(imdto, startDay, endDay);
			res=imdao.queryOnCondition(imdto, startDay, endDay, start, limit);
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
			for(InventoryManifestDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("inventoryManifestID",iter.getInventoryManifestID());
				m.put("inventoryPerson",iter.getInventoryPerson());
				m.put("dateInventoried",Tools.toString(iter.getDateInventoried()));
				m.put("result",iter.getResult());
				m.put("type", iter.getType());
				m.put("warehouse", iter.getWarehouseName()+"("+iter.getWarehouseID()+")");
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

		if(imdto==null || Tools.isVoid(imdto.getInventoryPerson())
				|| Tools.isVoid(imdto.getType()) || Tools.isVoid(imdto.getWarehouseID()) )
		{
			mysqlTools.rollback();
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		
		try{
			if(imdto.getDateInventoried()==null){
				imdto.setDateInventoried(Tools.currDate());
			}
			WarehouseDTO warehouse=wdao.getDTOByID(imdto.getWarehouseID());
			if(warehouse==null){
				mysqlTools.rollback();
				this.message="新增盘点单失败,所选仓库不存在！";
				this.success=false;
				return "success";
			}
			imdto.setWarehouseName(warehouse.getName());
			if(!imdao.insert(imdto))
			{
				mysqlTools.rollback();
				this.message="新增盘点单失败！";
				this.success=false;
				return "success";
			}
			int lastid=0;
			lastid=imdao.queryLastInsertID();
			Tools.print(""+lastid);
			if(imdao.getByID(lastid)==null)
			{
				mysqlTools.rollback();
				this.message="新增盘点单时发生错误，请重试！";
				this.success=false;
				return "success";
			}
			//添加货物信息
			if(itemList!=null){
				for(InventoryItemDTO iterator:itemList)
				{
					ItemDTO tempItem=itemdao.getDTOByID(iterator.getItemID());
					if(tempItem==null){
						mysqlTools.rollback();
						this.message="增加盘点物料失败！编号为"+iterator.getItemID()+"的货物不存在";
						this.success=false;
						return "success";
					}
					iterator.setInventoryManifestID(lastid);
					iterator.copyFrom(tempItem);
				}
				if(!iidao.insert(itemList))
				{
					mysqlTools.rollback();
					this.message="增加盘点货物失败！";
					this.success=false;
					return "success";
				}
			}
				
			mysqlTools.commit();
			this.message="新增盘点单成功！";
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

		if(imdto==null || imdto.getInventoryManifestID()==null )
		{
			this.message="缺少盘点单编号,无法查询！";
			this.success=false;
			return "success";
		}
		
		try{
			imdto=imdao.getByID(imdto.getInventoryManifestID());
			if(imdto==null)
			{
				mysqlTools.rollback();
				this.message="查找盘点单失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			data.put("imdto.inventoryManifestID",imdto.getInventoryManifestID());
			data.put("imdto.inventoryPerson",imdto.getInventoryPerson());
			data.put("imdto.dateInventoried",Tools.toString(imdto.getDateInventoried()));
			data.put("imdto.result",imdto.getResult());
			data.put("imdto.type", imdto.getType());
			data.put("imdto.warehouseName", imdto.getWarehouseName());
			data.put("imdto.warehouseID", imdto.getWarehouseID());
			itemList=iidao.queryOnCondition(imdto.getInventoryManifestID());
			//添加货物
			if(itemList!=null && itemList.size()>0)
			{
				this.resultMapList=new ArrayList<Map>();
				for(InventoryItemDTO item:itemList)
				{
					Map m=new HashMap();
					m.put("itemID", item.getItemID());
					m.put("itemName", item.getItemName());
					m.put("itemNumber", item.getItemNumber());
					m.put("batch", item.getBatch());
					m.put("unit", item.getUnit());
					m.put("amountDifference", item.getAmountDifference());
					m.put("amountExisted", item.getAmountExisted());
					m.put("amountRecorded", item.getAmountRecorded());
					m.put("differenceRemarks", item.getDifferenceRemarks());
					resultMapList.add(m);
				}
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

		if(imdto==null||imdto.getInventoryManifestID()==null
				)
		{
			this.message="缺少盘点单编号,无法修改！";
			this.success=false;
			return "success";
		}
		if(imdto==null || Tools.isVoid(imdto.getInventoryPerson())
				|| Tools.isVoid(imdto.getType()) || Tools.isVoid(imdto.getWarehouseID()) )
		{
			mysqlTools.rollback();
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		try{
			if(imdto.getDateInventoried()==null){
				imdto.setDateInventoried(Tools.currDate());
			}
			WarehouseDTO warehouse=wdao.getDTOByID(imdto.getWarehouseID());
			if(warehouse==null){
				mysqlTools.rollback();
				this.message="新增盘点单失败,所选仓库不存在！";
				this.success=false;
				return "success";
			}
			imdto.setWarehouseName(warehouse.getName());
			if(!iidao.delete(imdto.getInventoryManifestID()))
			{
				mysqlTools.rollback();
				this.message="删除当前盘点单旧的货物信息失败！";
				this.success=false;
				return "success";
			}
			if(!imdao.update(imdto))
			{
				mysqlTools.rollback();
				this.message="修改盘点单失败!";
				this.success=false;
				return "success";
			}
			
			if(itemList!=null){
				for(InventoryItemDTO iterator:itemList)
				{
					ItemDTO tempItem=itemdao.getDTOByID(iterator.getItemID());
					if(tempItem==null){
						mysqlTools.rollback();
						this.message="修改盘点货物失败！编号为"+iterator.getItemID()+"的货物不存在";
						this.success=false;
						return "success";
					}
					iterator.setInventoryManifestID(imdto.getInventoryManifestID());
					iterator.copyFrom(tempItem);
				}
				if(!iidao.insert(itemList))
				{
					mysqlTools.rollback();
					this.message="增加盘点货物失败！";
					this.success=false;
					return "success";
				}
			}
			mysqlTools.commit();
			this.message="修改盘点单成功！";
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

		if(this.imIDList==null || this.imIDList.size()==0)
		{
			this.message="缺少盘点单编号，无法删除！";
			this.success=false;
			return "success";
		}
		if(null==imdto)
			imdto=new InventoryManifestDTO();
		try{
			if(!imdao.delete(imIDList)){
				mysqlTools.rollback();
				this.message+="删除盘点单信息失败！";
				this.success=false;
				return "success";
			}
			if(!iidao.delete(imIDList)){
				mysqlTools.rollback();
				this.message+="删除盘点物料失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="删除盘点单信息成功！";
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


	public StockItemDAO getSidao() {
		return sidao;
	}


	public void setSidao(StockItemDAO sidao) {
		this.sidao = sidao;
	}


	public InventoryManifestDAO getImdao() {
		return imdao;
	}


	public void setImdao(InventoryManifestDAO imdao) {
		this.imdao = imdao;
	}


	public WarehouseDAO getWdao() {
		return wdao;
	}


	public void setWdao(WarehouseDAO wdao) {
		this.wdao = wdao;
	}


	public ItemDAO getItemdao() {
		return itemdao;
	}


	public void setItemdao(ItemDAO itemdao) {
		this.itemdao = itemdao;
	}


	public InventoryItemDAO getIidao() {
		return iidao;
	}


	public void setIidao(InventoryItemDAO iidao) {
		this.iidao = iidao;
	}


	public StockItemDTO getSidto() {
		return sidto;
	}


	public void setSidto(StockItemDTO sidto) {
		this.sidto = sidto;
	}


	public InventoryManifestDTO getImdto() {
		return imdto;
	}


	public void setImdto(InventoryManifestDTO imdto) {
		this.imdto = imdto;
	}


	public ArrayList<Integer> getImIDList() {
		return imIDList;
	}


	public void setImIDList(ArrayList<Integer> imIDList) {
		this.imIDList = imIDList;
	}


	public ArrayList<InventoryItemDTO> getItemList() {
		return itemList;
	}


	public void setItemList(ArrayList<InventoryItemDTO> itemList) {
		this.itemList = itemList;
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
	
	
//	public String queryItems(){
//		if(sidto==null || Tools.isVoid(sidto.getWarehouseID()))
//		{
//			this.message="缺少必要信息！";
//			this.success=false;
//			return "success";
//		}
//		ArrayList<StockItemDTO> res=null;
//		try{
//			//查询
//			res=sidao.queryItems(sidto.getWarehouseID(), sidto.getItemID()
//					, sidto.getCustomerID(),StockItemDAO.JOIN_ITEM|StockItemDAO.JOIN_CUSTOMER);
//			Tools.print(""+res.size()+"|"+this.qualifiedAmount);
//			if(res==null)
//			{
//				mysqlTools.rollback();
//				this.message="没有记录";
//				this.success=false;
//				return "success";
//			}
//			mysqlTools.commit();
//			resultMapList=new ArrayList<Map>();
//			for(StockItemDTO iter:res){
//				Map m=null;
//				m=new HashMap();
//				m.put("stockinDate",Tools.toString(iter.getStockinDate()));
//				m.put("itemName",iter.item.getName());
//				m.put("itemNumber", iter.item.getNumber());
//				m.put("itemID", iter.getItemID());
//				m.put("batch",iter.item.getBatch());
//				m.put("unit",iter.item.getUnit());
//				m.put("unitWeight", iter.item.getUnitWeight());
//				m.put("unitVolume",iter.item.getUnitVolume());
//				m.put("amount",iter.getAmount());
//				m.put("weight", iter.item.getUnitWeight()*iter.getAmount());
//				m.put("volume", iter.item.getUnitVolume()*iter.getAmount());
//				m.put("customer",iter.customer.getName());
//				resultMapList.add(m);
//			}
//			
//			this.message="成功!";
//			this.success=true;
//			return "success";
//		}catch(Exception e){
//			mysqlTools.rollback();
//			e.printStackTrace();
//			this.message="操作失败!";
//			this.success=false;
//			return "success";
//		}
//		finally{
//			if(mysqlTools!=null)
//				mysqlTools.close();
//		}
//	}

	

}
