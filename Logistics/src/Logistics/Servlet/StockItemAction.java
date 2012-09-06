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
public class StockItemAction {
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
	private StockIncomeDAO incomedao=null;
	private CustomerDAO cusdao=null;
	private WarehouseDAO wdao=null;
	private ItemDAO itemdao=null;
	//DTO对象
	private WarehouseDTO wdto=new WarehouseDTO();
	private CustomerDTO cusdto=new CustomerDTO();
	private StockItemDTO sidto=new StockItemDTO();
	private ItemDTO itemdto=new ItemDTO();
	//输入对象
	ArrayList<Integer> siIDList=new ArrayList<Integer>();
	ArrayList<StockItemDTO> itemList=new ArrayList<StockItemDTO>();
	String warehouseID=null;
	Integer customerID=null;
	Integer itemID=null;
	String startDate=null;
	String endDate=null;
	String dateType=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	Map data=null;
	
	public StockItemAction(){
		
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		
		sidao=new StockItemDAO();
		sidao.initiate(mysqlTools);
		incomedao=new StockIncomeDAO();
		incomedao.initiate(mysqlTools);
		wdao=new WarehouseDAO();
		wdao.initiate(mysqlTools);
		cusdao=new CustomerDAO();
		cusdao.initiate(mysqlTools);
		itemdao=new ItemDAO();
		itemdao.initiate(mysqlTools);
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
		ArrayList<StockItemDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=sidao.queryQualifiedAmount(wdto, itemdto, cusdto,true,null,null);
			res=sidao.queryQualifiedItems(wdto, itemdto, cusdto, start, limit,true,null,null);
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
			for(StockItemDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("itemID",iter.getItemID());
				m.put("itemName", iter.item.getName());
				m.put("itemNumber",iter.item.getNumber());
				m.put("batch", iter.item.getBatch());
				m.put("unit",iter.item.getUnit());
				m.put("unitVolume", iter.item.getUnitVolume());
				m.put("unitWeight",iter.item.getUnitWeight());
				m.put("warehouseID",iter.getWarehouseID());
				m.put("warehouseName",iter.warehouse.getName());
				m.put("customerID",iter.getCustomerID());
				m.put("customerName",iter.customer.getName());
				m.put("sumAmount", iter.getAmount());
				
				
				
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
	public String queryItemHistory(){
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

		if(Tools.isVoid(warehouseID)  || Tools.isVoid(itemID)){
			this.message="缺少库存地编号，或者物料编号";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0) limit=0;
		Date startDay=null;
		Date endDay=null;
		if(startDate!=null && startDate.length()!=0)
			startDay=Date.valueOf(startDate);
		if(endDate!=null && endDate.length()!=0)
			endDay=Date.valueOf(endDate);
		ArrayList<ItemHistoryDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=sidao.queryItemHistoryAmount(warehouseID, customerID, itemID,startDay,endDay);
			res=sidao.queryItemHistory(warehouseID, customerID, itemID,startDay,endDay, start, limit);
			if(res==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			resultMapList=new ArrayList<Map>();
			for(ItemHistoryDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("id", iter.getId());
				m.put("type", iter.getType());
				m.put("date", iter.getDate());
				
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
	public String queryChangedInventoryItems(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		
		if(Tools.isVoid(sidto.getWarehouseID()) 
				||Tools.isVoid(startDate)||Tools.isVoid(endDate)){
			this.message="缺少必要条件";
			this.success=false;
			return "success";
		}
		Date startDay=null;
		Date endDay=null;
		if(startDate!=null && startDate.length()!=0)
			startDay=Date.valueOf(startDate);
		if(endDate!=null && endDate.length()!=0)
			endDay=Date.valueOf(endDate);
		ArrayList<StockItemDTO> res=null;
		try{
			//查询
			res=sidao.queryInventoryItems(sidto.getWarehouseID(), startDay, endDay,false);
			if(res==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			resultMapList=new ArrayList<Map>();
			for(StockItemDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("itemID",iter.getItemID());
				m.put("itemName", iter.item.getName());
				m.put("itemNumber",iter.item.getNumber());
				m.put("batch", iter.item.getBatch());
				m.put("unit",iter.item.getUnit());
				m.put("unitVolume", iter.item.getUnitVolume());
				m.put("unitWeight",iter.item.getUnitWeight());
				m.put("amountRecorded", iter.getAmount());
				
				
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
	public String queryAllInventoryItems(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		if(Tools.isVoid(sidto.getWarehouseID()) ){
			this.message="缺少必要条件";
			this.success=false;
			return "success";
		}
		ArrayList<StockItemDTO> res=null;
		try{
			//查询
			res=sidao.queryInventoryItems(sidto.getWarehouseID(), null, null,true);
			if(res==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			resultMapList=new ArrayList<Map>();
			for(StockItemDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("itemID",iter.getItemID());
				m.put("itemName", iter.item.getName());
				m.put("itemNumber",iter.item.getNumber());
				m.put("batch", iter.item.getBatch());
				m.put("unit",iter.item.getUnit());
				m.put("unitVolume", iter.item.getUnitVolume());
				m.put("unitWeight",iter.item.getUnitWeight());
				m.put("amountRecorded", iter.getAmount());
				
				
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
	public String account(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of account
		if(!Permission.checkPermission(this, MethodCode.account)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
		}
		//---------------------------------------------------------------
		
		if(itemList==null || itemList.isEmpty())
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		try{
			for(StockItemDTO iter:itemList){
				StockItemDTO item=sidao.getByID(iter.getWarehouseID(),
						iter.getItemID(), iter.getStockinDate(), iter.getCustomerID());
				if(item==null){
					mysqlTools.rollback();
					this.message+="无法找到相应货物，结算失败！";
					this.success=false;
					return "success";
				}
				item.customer=cusdao.getDTOByID(item.getCustomerID());
				item.item=itemdao.getDTOByID(item.getItemID());
				item.warehouse=wdao.getDTOByID(item.getWarehouseID());
				StockIncomeDTO income=StockIncomeDTO.valueOF(item);
				if(income.getDaysStock()==0){
					mysqlTools.rollback();
					this.message+="有物料的存储天数为0，结算失败！";
					this.success=false;
					return "success";
				}
				if(!incomedao.insert(income)){
					mysqlTools.rollback();
					this.message+="无法将收入信息写入数据库，结算失败！";
					this.success=false;
					return "success";
				}
				if(!sidao.updateAccountDate(item.getWarehouseID(),
						item.getItemID(), item.getStockinDate(), item.getCustomerID(), Tools.currDate())){
					mysqlTools.rollback();
					this.message+="无法将修改信息写入数据库，结算失败！";
					this.success=false;
					return "success";
				}
				
			}
			
			mysqlTools.commit();
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


	public StockIncomeDAO getIncomedao() {
		return incomedao;
	}


	public void setIncomedao(StockIncomeDAO incomedao) {
		this.incomedao = incomedao;
	}


	public WarehouseDTO getWdto() {
		return wdto;
	}


	public void setWdto(WarehouseDTO wdto) {
		this.wdto = wdto;
	}


	public CustomerDTO getCusdto() {
		return cusdto;
	}


	public void setCusdto(CustomerDTO cusdto) {
		this.cusdto = cusdto;
	}


	public StockItemDTO getSidto() {
		return sidto;
	}


	public void setSidto(StockItemDTO sidto) {
		this.sidto = sidto;
	}


	public ItemDTO getItemdto() {
		return itemdto;
	}


	public void setItemdto(ItemDTO itemdto) {
		this.itemdto = itemdto;
	}


	public ArrayList<Integer> getSiIDList() {
		return siIDList;
	}


	public void setSiIDList(ArrayList<Integer> siIDList) {
		this.siIDList = siIDList;
	}


	public ArrayList<StockItemDTO> getItemList() {
		return itemList;
	}


	public void setItemList(ArrayList<StockItemDTO> itemList) {
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


	public Map getData() {
		return data;
	}


	public void setData(Map data) {
		this.data = data;
	}


	public CustomerDAO getCusdao() {
		return cusdao;
	}


	public void setCusdao(CustomerDAO cusdao) {
		this.cusdao = cusdao;
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


	public String getWarehouseID() {
		return warehouseID;
	}


	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}


	public Integer getCustomerID() {
		return customerID;
	}


	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}


	public Integer getItemID() {
		return itemID;
	}


	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}

//	
//	public String insert(){
//		if(somdto==null ||Tools.voidString(somdto.getCostCenter()) || 
//				Tools.voidString(somdto.getSellCenter()) ||
//				somdto.getWarehouseID()==null)
//		{
//			mysqlTools.rollback();
//			this.message="缺少必要信息！";
//			this.success=false;
//			return "success";
//		}
//		
//		try{
//			if(somdto.getDateStockout()==null){
//				somdto.setDateStockout(Tools.currDate());
//			}
//			somdto.setDateCreated(Tools.currDate());
//			WarehouseDTO warehouse=wdao.getDTOByID(somdto.getWarehouseID());
//			if(warehouse==null){
//				mysqlTools.rollback();
//				this.message="新增出库单失败,所选仓库不存在！";
//				this.success=false;
//				return "success";
//			}
//			somdto.setWarehouseName(warehouse.getName());
//			CustomerDTO cus=cusdao.getDTOByID(somdto.getCustomerID());
//			if(cus==null){
//				mysqlTools.rollback();
//				this.message="新增出库单失败,所选客户不存在！";
//				this.success=false;
//				return "success";
//			}
//			somdto.setCustomer(cus.getName());
//			if(!somdao.insert(somdto))
//			{
//				mysqlTools.rollback();
//				this.message="新增出库单失败！";
//				this.success=false;
//				return "success";
//			}
//			int lastid=0;
//			lastid=somdao.queryLastInsertID();
//			Tools.print(""+lastid);
//			if(somdao.getByID(lastid)==null)
//			{
//				mysqlTools.rollback();
//				this.message="新增出库单时发生错误，请重试！";
//				this.success=false;
//				return "success";
//			}
//			//添加货物信息
//			if(itemList!=null){
//				for(StockItemDTO iterator:itemList)
//				{
//					
//					ItemDTO tempItem=itemdao.getDTOByID(iterator.getItemID());
//					if(tempItem==null){
//						mysqlTools.rollback();
//						this.message="增加入库货物失败！编号为"+iterator.getItemID()+"的货物不存在";
//						this.success=false;
//						return "success";
//					}
//					iterator.setStockoutManifestID(lastid);
//					iterator.copyFrom(tempItem);
//				}
//				if(!soidao.insert(itemList))
//				{
//					mysqlTools.rollback();
//					this.message="增加入库货物失败！";
//					this.success=false;
//					return "success";
//				}
//			}
//				
//			mysqlTools.commit();
//			this.message="新增出库单成功！";
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
//	public String queryOne(){
//		if(somdto==null || somdto.getStockoutManifestID()==null )
//		{
//			this.message="缺少出库单编号,无法查询！";
//			this.success=false;
//			return "success";
//		}
//		try{
//			somdto=somdao.getByID(somdto.getStockoutManifestID());
//			if(somdto==null)
//			{
//				mysqlTools.rollback();
//				this.message="查找出库单失败！";
//				this.success=false;
//				return "success";
//			}
//			mysqlTools.commit();
//			this.data=new HashMap();
//			this.data.put("somdto.stockoutManifestID", somdto.getStockoutManifestID());
//			this.data.put("somdto.customer", somdto.getCustomer());
//			this.data.put("somdto.dateCreated", Tools.toString(somdto.getDateCreated()));
//			this.data.put("somdto.dateStockout", Tools.toString(somdto.getDateStockout()));
//			this.data.put("somdto.warehouseID", somdto.getWarehouseID());
//			this.data.put("somdto.warehouseName", somdto.getWarehouseName());
//			this.data.put("somdto.customerID", somdto.getCustomerID());
//			this.data.put("somdto.customer", somdto.getCustomer());
//			this.data.put("somdto.consignee", somdto.getConsignee());
//			this.data.put("somdto.consigneePhone", somdto.getConsigneePhone());
//			this.data.put("somdto.consigneeCompany", somdto.getConsigneeCompany());
//			this.data.put("somdto.consigneeAddress", somdto.getConsigneeAddress());
//			this.data.put("somdto.costCenter", somdto.getCostCenter());
//			this.data.put("somdto.sellCenter", somdto.getSellCenter());
//			this.data.put("somdto.unitPrice", somdto.getUnitPrice());
//			this.data.put("somdto.chargeMode", somdto.getChargeMode());
//			this.data.put("somdto.stockoutFee", somdto.getStockoutFee());
//			this.data.put("somdto.loadUnloadCost", somdto.getLoadUnloadCost());
//			this.data.put("somdto.remarks", somdto.getRemarks());
//			this.data.put("somdto.approvalState", somdto.getApprovalState());
//			this.data.put("somdto.auditState", somdto.getAuditState());
//			double sumAmount=0;
//			double sumVolume=0;
//			double sumWeight=0;
//			double sumValue=0;
//			soidto.setStockoutManifestID(somdto.getStockoutManifestID());
//			itemList=soidao.queryOnCondition(somdto.getStockoutManifestID());
//			//添加货物
//			if(itemList!=null && itemList.size()>0)
//			{
//				this.resultMapList=new ArrayList<Map>();
//				for(StockItemDTO iter:itemList)
//				{
//					sumAmount+=iter.getAmount();
//					sumVolume+=iter.getVolume();
//					sumWeight+=iter.getWeight();
//					Map m=new HashMap();
//					m.put("itemID", iter.getItemID());
//					m.put("itemName", iter.getItemName());
//					m.put("itemNumber", iter.getItemNumber());
//					m.put("batch", iter.getBatch());
//					m.put("unitWeight", iter.getUnitWeight());
//					m.put("unitVolume", iter.getUnitVolume());
//					m.put("amount", iter.getAmount());
//					m.put("weight", iter.getWeight());
//					m.put("volume", iter.getVolume());
//					m.put("dateStockin", iter.getDateStockin());
//					resultMapList.add(m);
//				}
//			}
//			this.data.put("simdto.sumAmount", sumAmount);
//			this.data.put("simdto.sumVolume", sumVolume);
//			this.data.put("simdto.sumWeight", sumWeight);
//			this.data.put("simdto.sumValue", sumValue);
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
//	
//	public String update(){
//		if(somdto==null ||Tools.voidString(somdto.getCostCenter()) || 
//				Tools.voidString(somdto.getSellCenter()) ||
//				somdto.getWarehouseID()==null)
//		{
//			mysqlTools.rollback();
//			this.message="缺少必要信息！";
//			this.success=false;
//			return "success";
//		}
//		try{
//			if(somdto.getDateStockout()==null){
//				somdto.setDateStockout(Tools.currDate());
//			}
//			somdto.setDateCreated(Tools.currDate());
//			WarehouseDTO warehouse=wdao.getDTOByID(somdto.getWarehouseID());
//			if(warehouse==null){
//				mysqlTools.rollback();
//				this.message="修改出库单失败,所选仓库不存在！";
//				this.success=false;
//				return "success";
//			}
//			somdto.setWarehouseName(warehouse.getName());
//			CustomerDTO cus=cusdao.getDTOByID(somdto.getCustomerID());
//			if(cus==null){
//				mysqlTools.rollback();
//				this.message="修改出库单失败,所选客户不存在！";
//				this.success=false;
//				return "success";
//			}
//			somdto.setCustomer(cus.getName());
//			if(!soidao.delete(somdto.getStockoutManifestID()))
//			{
//				mysqlTools.rollback();
//				this.message="删除当前出库单旧的货物信息失败！";
//				this.success=false;
//				return "success";
//			}
//			if(!somdao.update(somdto))
//			{
//				mysqlTools.rollback();
//				this.message="修改出库单失败!";
//				this.success=false;
//				return "success";
//			}
//			
//			if(itemList!=null){
//				for(int i=0;i<this.itemList.size();i++)
//				{
//					soidto=this.itemList.get(i);
//					soidto.setStockoutManifestID(somdto.getStockoutManifestID());
//				}
//				if(!soidao.insert(itemList))
//				{
//					mysqlTools.rollback();
//					this.message="增加出库货物失败！";
//					this.success=false;
//					return "success";
//				}
//			}
//			mysqlTools.commit();
//			this.message="修改出库单成功！";
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
//			mysqlTools.close();
//		}
//	}
	
//	
//	public String approve(){
//		if(siIDList==null || siIDList.isEmpty()
//				)
//		{
//			this.message="缺少出库单编号,无法确认！";
//			this.success=false;
//			return "success";
//		}
//		try{
//			for(Integer smid:siIDList){
//				somdto=somdao.getByID(smid);
//				if(somdto==null){
//					mysqlTools.rollback();
//					this.message="批准出库单失败，出库单不存在!";
//					this.success=false;
//					return "success";
//				}
//				if(!somdto.getApprovalState().equals("未批准")){
//					continue;
//				}
//				//修改批准状态
//				if(!somdao.approve(somdto.getStockoutManifestID()))
//				{
//					mysqlTools.rollback();
//					this.message="批准出库单失败，修改批准状态失败!";
//					this.success=false;
//					return "success";
//				}
//				//将物料出库
//				itemList=soidao.queryOnCondition(somdto.getStockoutManifestID());
//				if(itemList!=null){
//					for(StockItemDTO stockitem:itemList){
//						
//						sidto=sidao.getByID(somdto.getWarehouseID(), stockitem.getItemID(),
//								stockitem.getDateStockin(),somdto.getCustomerID());
//						if(sidto==null){
//							mysqlTools.rollback();
//							this.message="批准出库单失败，物料"+stockitem.getItemName()+"不存在!";
//							this.success=false;
//							return "success";
//							
//						}
//						else{
//							if(sidto.getAmount()<stockitem.getAmount())
//							{
//								mysqlTools.rollback();
//								this.message="批准出库单失败，库存物料"+stockitem.getItemName()+"数量不足!";
//								this.success=false;
//								return "success";
//							}
//							if(!sidao.updateAmount(somdto.getWarehouseID(), stockitem.getItemID(),
//								stockitem.getDateStockin(),somdto.getCustomerID(), -stockitem.getAmount())){
//								mysqlTools.rollback();
//								this.message="批准出库单失败，物料出库失败!";
//								this.success=false;
//								return "success";
//							}
//						}
//					}
//				}
//				//将收支放出装车收支表
//				StockoutFinanceDTO sfdto=StockoutFinanceDTO.valueOf(somdto);
//				if(!sofdao.insert(sfdto)){
//					mysqlTools.rollback();
//					this.message="批准出库单失败，生成出库收支信息失败!";
//					this.success=false;
//					return "success";
//				}
//				
//			}//for
//			mysqlTools.commit();
//			this.message="批准出库单成功！";
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
//			mysqlTools.close();
//		}
//	}
//	
//	public String audit(){
//		if(siIDList==null || siIDList.isEmpty()
//				)
//		{
//			this.message="缺少出库单编号,无法审核！";
//			this.success=false;
//			return "success";
//		}
//		try{
//			if(!somdao.audit(siIDList)){
//				mysqlTools.rollback();
//				this.message="审核出库单失败！";
//				this.success=false;
//				return "success";
//			}
//			mysqlTools.commit();
//			this.message="审核出库单成功！";
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
//			mysqlTools.close();
//		}
//	}
//	public String queryItems(){
//		if(sidto==null || Tools.voidCondition(sidto.getCustomerID())||Tools.voidCondition(sidto.getWarehouseID()))
//		{
//			this.message="缺少必要信息！";
//			this.success=false;
//			return "success";
//		}
//		
//		ArrayList<StockItemDTO> res=null;
//		try{
//			//查询
//			res=sidao.queryItems(sidto.getWarehouseID(), sidto.getItemID()
//					, sidto.getCustomerID(),StockItemDAO.JOIN_ITEM);
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
//	public String queryCustomerQuote(){
//		if(cusdto==null || cusdto.getCustomerID()==null )
//		{
//			this.message="缺少客户编号,无法查询！";
//			this.success=false;
//			return "success";
//		}
//		try{
//			cusdto=cusdao.getDTOByID(cusdto.getCustomerID());
//			if(cusdto==null)
//			{
//				mysqlTools.rollback();
//				this.message="查找客户失败！";
//				this.success=false;
//				return "success";
//			}
//			//ArrayList<ItemDTO> 
//			mysqlTools.commit();
//			this.data=new HashMap();
//			this.data.put("amountQuote", cusdto.getStockOutCostPerCount());
//			this.data.put("volumeQuote", cusdto.getStockOutCostPerVolume());
//			this.data.put("weightQuote", cusdto.getStockOutCostPerWeight());
//			
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
