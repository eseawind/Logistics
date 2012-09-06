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
public class StockoutManifestAction {
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private StockoutManifestDAO somdao=null;
	private StockoutItemDAO soidao=null;
	private CustomerDAO cusdao=null;
	private CityDAO citydao=null;
	private StockItemDAO sidao=null;
	private StockoutFinanceDAO sofdao=null;
	private WarehouseDAO wdao=null;
	private ItemDAO itemdao=null;
	private StockIncomeDAO incomedao=null;
	//DTO对象
	private StockoutManifestDTO somdto=new StockoutManifestDTO();
	private StockoutItemDTO soidto=new StockoutItemDTO();
	private CustomerDTO cusdto=new CustomerDTO();
	private ConsigneeDTO consigneedto=new ConsigneeDTO();
	private ConsignerDTO consignerdto=new ConsignerDTO();
	private CityDTO	citydto=new CityDTO();
	private FreightIncomeDTO fidto=new FreightIncomeDTO();
	private StockItemDTO sidto=new StockItemDTO();
	//输入对象
	ArrayList<Integer> somIDList=new ArrayList<Integer>();
	ArrayList<StockoutItemDTO> itemList=new ArrayList<StockoutItemDTO>();
	String startDate=null;
	String endDate=null;
	String dateType=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	ArrayList<Map> carTypeQuoteMapList=null;
	Map data=null;
	
	public StockoutManifestAction(){
		
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		somdao=new StockoutManifestDAO();
		soidao=new StockoutItemDAO();
		cusdao=new CustomerDAO();
		citydao=new CityDAO();
		sidao=new StockItemDAO();
		sofdao=new StockoutFinanceDAO();
		wdao=new WarehouseDAO();
		somdao.initiate(mysqlTools);
		soidao.initiate(mysqlTools);
		cusdao.initiate(mysqlTools);
		citydao.initiate(mysqlTools);
		sidao.initiate(mysqlTools);
		sofdao.initiate(mysqlTools);
		wdao.initiate(mysqlTools);
		itemdao=new ItemDAO();
		itemdao.initiate(mysqlTools);
		incomedao=new StockIncomeDAO();
		incomedao.initiate(mysqlTools);
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
		ArrayList<StockoutManifestDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=somdao.queryQualifiedAmountOnCondition(somdto, startDay, endDay);
			res=somdao.queryOnCondition(somdto, startDay, endDay, start, limit);
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
			for(StockoutManifestDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("stockoutManifestID",iter.getStockoutManifestID());
				m.put("dateCreated",Tools.toString(iter.getDateCreated()));
				m.put("dateStockout", Tools.toString(iter.getDateStockout()));
				m.put("warehouseName", iter.getWarehouseName());
				m.put("customer",iter.getCustomer());
				m.put("consignee", iter.getConsignee());
				m.put("costCenter",iter.getCostCenter());
				m.put("sellCenter", iter.getSellCenter());
				m.put("auditState",iter.getAuditState());
				m.put("approvalState",iter.getApprovalState());
				m.put("remarks",iter.getRemarks());
				
				
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

		if(somdto==null ||Tools.isVoid(somdto.getCostCenter()) || 
				Tools.isVoid(somdto.getSellCenter()) ||
				somdto.getWarehouseID()==null)
		{
			mysqlTools.rollback();
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		
		try{
			if(somdto.getDateStockout()==null){
				somdto.setDateStockout(Tools.currDate());
			}
			somdto.setDateCreated(Tools.currDate());
			WarehouseDTO warehouse=wdao.getDTOByID(somdto.getWarehouseID());
			if(warehouse==null){
				mysqlTools.rollback();
				this.message="新增出库单失败,所选仓库不存在！";
				this.success=false;
				return "success";
			}
			somdto.setWarehouseName(warehouse.getName());
			CustomerDTO cus=cusdao.getDTOByID(somdto.getCustomerID());
			if(cus==null){
				mysqlTools.rollback();
				this.message="新增出库单失败,所选客户不存在！";
				this.success=false;
				return "success";
			}
			somdto.setCustomer(cus.getName());
			if(!somdao.insert(somdto))
			{
				mysqlTools.rollback();
				this.message="新增出库单失败！";
				this.success=false;
				return "success";
			}
			int lastid=0;
			lastid=somdao.queryLastInsertID();
			Tools.print(""+lastid);
			if(somdao.getByID(lastid)==null)
			{
				mysqlTools.rollback();
				this.message="新增出库单时发生错误，请重试！";
				this.success=false;
				return "success";
			}
			//添加货物信息
			if(itemList!=null){
				for(StockoutItemDTO iterator:itemList)
				{
					
					ItemDTO tempItem=itemdao.getDTOByID(iterator.getItemID());
					if(tempItem==null){
						mysqlTools.rollback();
						this.message="增加入库货物失败！编号为"+iterator.getItemID()+"的货物不存在";
						this.success=false;
						return "success";
					}
					System.out.println(iterator.getDateStockin());
					iterator.setStockoutManifestID(lastid);
					iterator.copyFrom(tempItem);
				}
				if(!soidao.insert(itemList))
				{
					mysqlTools.rollback();
					this.message="增加入库货物失败！";
					this.success=false;
					return "success";
				}
			}
				
			mysqlTools.commit();
			this.message=""+lastid;
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

		
		if(somdto==null || somdto.getStockoutManifestID()==null )
		{
			this.message="缺少出库单编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			somdto=somdao.getByID(somdto.getStockoutManifestID());
			if(somdto==null)
			{
				mysqlTools.rollback();
				this.message="查找出库单失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("somdto.stockoutManifestID", somdto.getStockoutManifestID());
			this.data.put("somdto.customer", somdto.getCustomer());
			this.data.put("somdto.dateCreated", Tools.toString(somdto.getDateCreated()));
			this.data.put("somdto.dateStockout", Tools.toString(somdto.getDateStockout()));
			this.data.put("somdto.warehouseID", somdto.getWarehouseID());
			this.data.put("somdto.warehouseName", somdto.getWarehouseName());
			this.data.put("somdto.customerID", somdto.getCustomerID());
			this.data.put("somdto.customer", somdto.getCustomer());
			this.data.put("somdto.consignee", somdto.getConsignee());
			this.data.put("somdto.consigneePhone", somdto.getConsigneePhone());
			this.data.put("somdto.consigneeCompany", somdto.getConsigneeCompany());
			this.data.put("somdto.consigneeAddress", somdto.getConsigneeAddress());
			this.data.put("somdto.costCenter", somdto.getCostCenter());
			this.data.put("somdto.sellCenter", somdto.getSellCenter());
			this.data.put("somdto.unitPrice", somdto.getUnitPrice());
			this.data.put("somdto.chargeMode", somdto.getChargeMode());
			this.data.put("somdto.stockoutFee", somdto.getStockoutFee());
			this.data.put("somdto.loadUnloadCost", somdto.getLoadUnloadCost());
			this.data.put("somdto.remarks", somdto.getRemarks());
			this.data.put("somdto.approvalState", somdto.getApprovalState());
			this.data.put("somdto.auditState", somdto.getAuditState());
			double sumAmount=0;
			double sumVolume=0;
			double sumWeight=0;
			double sumValue=0;
			soidto.setStockoutManifestID(somdto.getStockoutManifestID());
			itemList=soidao.queryOnCondition(somdto.getStockoutManifestID());
			//添加货物
			if(itemList!=null && itemList.size()>0)
			{
				this.resultMapList=new ArrayList<Map>();
				for(StockoutItemDTO iter:itemList)
				{
					
					StockItemDTO stockitem=this.sidao.getByID(somdto.getWarehouseID(), iter.getItemID(), iter.getDateStockin(), somdto.getCustomerID());
					
					sumAmount+=iter.getAmount();
					sumVolume+=iter.getVolume();
					sumWeight+=iter.getWeight();
					Map m=new HashMap();
					m.put("itemID", iter.getItemID());
					m.put("itemName", iter.getItemName());
					m.put("itemNumber", iter.getItemNumber());
					m.put("batch", iter.getBatch());
					m.put("unitWeight", iter.getUnitWeight());
					m.put("unitVolume", iter.getUnitVolume());
					m.put("amount", iter.getAmount());
					m.put("stockAmount", stockitem==null?null:stockitem.getAmount());
					m.put("weight", iter.getWeight());
					m.put("volume", iter.getVolume());
					m.put("dateStockin", Tools.toString(iter.getDateStockin()));
					resultMapList.add(m);
				}
			}
			this.data.put("somdto.sumAmount", sumAmount);
			this.data.put("somdto.sumVolume", sumVolume);
			this.data.put("somdto.sumWeight", sumWeight);
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

		
		if(somdto==null ||Tools.isVoid(somdto.getCostCenter()) || 
				Tools.isVoid(somdto.getSellCenter()) ||
				somdto.getWarehouseID()==null)
		{
			mysqlTools.rollback();
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		try{
			if(somdto.getDateStockout()==null){
				somdto.setDateStockout(Tools.currDate());
			}
			somdto.setDateCreated(Tools.currDate());
			WarehouseDTO warehouse=wdao.getDTOByID(somdto.getWarehouseID());
			if(warehouse==null){
				mysqlTools.rollback();
				this.message="修改出库单失败,所选仓库不存在！";
				this.success=false;
				return "success";
			}
			somdto.setWarehouseName(warehouse.getName());
			CustomerDTO cus=cusdao.getDTOByID(somdto.getCustomerID());
			if(cus==null){
				mysqlTools.rollback();
				this.message="修改出库单失败,所选客户不存在！";
				this.success=false;
				return "success";
			}
			somdto.setCustomer(cus.getName());
			if(!soidao.delete(somdto.getStockoutManifestID()))
			{
				mysqlTools.rollback();
				this.message="删除当前出库单旧的货物信息失败！";
				this.success=false;
				return "success";
			}
			if(!somdao.update(somdto))
			{
				mysqlTools.rollback();
				this.message="修改出库单失败!";
				this.success=false;
				return "success";
			}
			
			if(itemList!=null){
				for(StockoutItemDTO iterator:itemList)
				{
					
					ItemDTO tempItem=itemdao.getDTOByID(iterator.getItemID());
					if(tempItem==null){
						mysqlTools.rollback();
						this.message="增加出库货物失败！编号为"+iterator.getItemID()+"的货物不存在";
						this.success=false;
						return "success";
					}
					System.out.println(iterator.getDateStockin());
					iterator.setStockoutManifestID(somdto.getStockoutManifestID());
					iterator.copyFrom(tempItem);
				}
				if(!soidao.insert(itemList))
				{
					mysqlTools.rollback();
					this.message="增加出库货物失败！";
					this.success=false;
					return "success";
				}
			}
			mysqlTools.commit();
			this.message="修改出库单成功！";
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

		
		if(this.somIDList==null || this.somIDList.size()==0)
		{
			this.message="缺少出库单编号，无法删除！";
			this.success=false;
			return "success";
		}
		if(null==somdto)
			somdto=new StockoutManifestDTO();
		if(null==soidto)
			soidto=new StockoutItemDTO();
		try{
			if(!somdao.delete(somIDList)){
				mysqlTools.rollback();
				this.message+="删除出库单信息失败！";
				this.success=false;
				return "success";
			}
			if(!soidao.delete(somIDList)){
				mysqlTools.rollback();
				this.message+="删除出库物料失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="删除出库单信息成功！";
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
	
	public String approve(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of approve
		if(!Permission.checkPermission(this, MethodCode.approve)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
		}
//---------------------------------------------------------------
		
		if(somIDList==null || somIDList.isEmpty()
				)
		{
			this.message="缺少出库单编号,无法确认！";
			this.success=false;
			return "success";
		}
		try{
			for(Integer smid:somIDList){
				somdto=somdao.getByID(smid);
				if(somdto==null){
					mysqlTools.rollback();
					this.message="批准出库单失败，出库单不存在!";
					this.success=false;
					return "success";
				}
				if(!somdto.getApprovalState().equals("未批准")){
					continue;
				}
				//修改批准状态
				if(!somdao.approve(somdto.getStockoutManifestID()))
				{
					mysqlTools.rollback();
					this.message="批准出库单失败，修改批准状态失败!";
					this.success=false;
					return "success";
				}
				//将物料出库
				itemList=soidao.queryOnCondition(somdto.getStockoutManifestID());
				if(itemList!=null){
					for(StockoutItemDTO stockitem:itemList){
						
						sidto=sidao.getByID(somdto.getWarehouseID(), stockitem.getItemID(),
								stockitem.getDateStockin(),somdto.getCustomerID());
						if(sidto==null){
							mysqlTools.rollback();
							this.message="批准出库单失败，物料"+stockitem.getItemName()+"不存在!";
							this.success=false;
							return "success";
							
						}
						else{
							if(stockitem.getAmount()==0){
								mysqlTools.rollback();
								this.message="批准出库单失败，物料"+stockitem.getItemName()+"出库数量为0!";
								this.success=false;
								return "success";
							}
							if(sidto.getAmount()<stockitem.getAmount())
							{
								mysqlTools.rollback();
								this.message="批准出库单失败，库存物料"+stockitem.getItemName()+"数量不足!";
								this.success=false;
								return "success";
							}
							if(!sidao.updateAmount(somdto.getWarehouseID(), stockitem.getItemID(),
								stockitem.getDateStockin(),somdto.getCustomerID(), -stockitem.getAmount())){
								mysqlTools.rollback();
								this.message="批准出库单失败，物料出库失败!";
								this.success=false;
								return "success";
							}
							sidao.deleteZero();
							StockIncomeDTO income=StockIncomeDTO.valueOf(somdto, stockitem, sidto.getLastAccountDate());
							if(!incomedao.insert(income)){
								mysqlTools.rollback();
								this.message+="无法将收入信息写入数据库，结算失败！";
								this.success=false;
								return "success";
							}
						}
						
						
					}
				}
				//将收支放出装车收支表
				StockoutFinanceDTO sfdto=StockoutFinanceDTO.valueOf(somdto);
				if(!sofdao.insert(sfdto)){
					mysqlTools.rollback();
					this.message="批准出库单失败，生成出库收支信息失败!";
					this.success=false;
					return "success";
				}
				
			}//for
			mysqlTools.commit();
			this.message="批准出库单成功！";
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
	
	public String audit(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//Check permission of audit
		if(!Permission.checkPermission(this, MethodCode.audit)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "success";
		}
//---------------------------------------------------------------
		if(somIDList==null || somIDList.isEmpty()
				)
		{
			this.message="缺少出库单编号,无法审核！";
			this.success=false;
			return "success";
		}
		try{
			if(!somdao.audit(somIDList)){
				mysqlTools.rollback();
				this.message="审核出库单失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="审核出库单成功！";
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
	public String queryItems(){
		
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		
		if(sidto==null || Tools.isVoid(sidto.getCustomerID())||Tools.isVoid(sidto.getWarehouseID()))
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		
		ArrayList<StockItemDTO> res=null;
		try{
			//查询
			Date startDay=null;
			Date endDay=null;
			if(startDate!=null && startDate.length()!=0)
				startDay=Date.valueOf(startDate);
			if(endDate!=null && endDate.length()!=0)
				endDay=Date.valueOf(endDate);
			res=sidao.queryItems(sidto.getWarehouseID(), sidto.getItemID()
					, sidto.getCustomerID(),startDay,endDay,StockItemDAO.JOIN_ITEM);
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
				m.put("stockinDate",Tools.toString(iter.getStockinDate()));
				m.put("itemName",iter.item.getName());
				m.put("itemNumber", iter.item.getNumber());
				m.put("itemID", iter.getItemID());
				m.put("batch",iter.item.getBatch());
				m.put("unit",iter.item.getUnit());
				m.put("unitWeight", iter.item.getUnitWeight());
				m.put("unitVolume",iter.item.getUnitVolume());
				m.put("stockAmount",iter.getAmount());
				m.put("weight", iter.item.getUnitWeight()*iter.getAmount());
				m.put("volume", iter.item.getUnitVolume()*iter.getAmount());
				m.put("customerID", iter.getCustomerID());
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
	public String queryCustomerQuote(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		if(cusdto==null || cusdto.getCustomerID()==null )
		{
			this.message="缺少客户编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			cusdto=cusdao.getDTOByID(cusdto.getCustomerID());
			if(cusdto==null)
			{
				mysqlTools.rollback();
				this.message="查找客户失败！";
				this.success=false;
				return "success";
			}
			//ArrayList<ItemDTO> 
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("amountQuote", cusdto.getStockOutCostPerCount());
			this.data.put("volumeQuote", cusdto.getStockOutCostPerVolume());
			this.data.put("weightQuote", cusdto.getStockOutCostPerWeight());
			
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


	public StockoutManifestDAO getSomdao() {
		return somdao;
	}


	public void setSomdao(StockoutManifestDAO somdao) {
		this.somdao = somdao;
	}


	public StockoutItemDAO getSoidao() {
		return soidao;
	}


	public void setSoidao(StockoutItemDAO soidao) {
		this.soidao = soidao;
	}


	public CustomerDAO getCusdao() {
		return cusdao;
	}


	public void setCusdao(CustomerDAO cusdao) {
		this.cusdao = cusdao;
	}


	public CityDAO getCitydao() {
		return citydao;
	}


	public void setCitydao(CityDAO citydao) {
		this.citydao = citydao;
	}


	public StockItemDAO getSidao() {
		return sidao;
	}


	public void setSidao(StockItemDAO sidao) {
		this.sidao = sidao;
	}


	public StockoutFinanceDAO getSofdao() {
		return sofdao;
	}


	public void setSofdao(StockoutFinanceDAO sofdao) {
		this.sofdao = sofdao;
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


	public StockoutManifestDTO getSomdto() {
		return somdto;
	}


	public void setSomdto(StockoutManifestDTO somdto) {
		this.somdto = somdto;
	}


	public StockoutItemDTO getSoidto() {
		return soidto;
	}


	public void setSoidto(StockoutItemDTO soidto) {
		this.soidto = soidto;
	}


	public CustomerDTO getCusdto() {
		return cusdto;
	}


	public void setCusdto(CustomerDTO cusdto) {
		this.cusdto = cusdto;
	}


	public ConsigneeDTO getConsigneedto() {
		return consigneedto;
	}


	public void setConsigneedto(ConsigneeDTO consigneedto) {
		this.consigneedto = consigneedto;
	}


	public ConsignerDTO getConsignerdto() {
		return consignerdto;
	}


	public void setConsignerdto(ConsignerDTO consignerdto) {
		this.consignerdto = consignerdto;
	}


	public CityDTO getCitydto() {
		return citydto;
	}


	public void setCitydto(CityDTO citydto) {
		this.citydto = citydto;
	}


	public FreightIncomeDTO getFidto() {
		return fidto;
	}


	public void setFidto(FreightIncomeDTO fidto) {
		this.fidto = fidto;
	}


	public StockItemDTO getSidto() {
		return sidto;
	}


	public void setSidto(StockItemDTO sidto) {
		this.sidto = sidto;
	}


	public ArrayList<Integer> getSomIDList() {
		return somIDList;
	}


	public void setSomIDList(ArrayList<Integer> somIDList) {
		this.somIDList = somIDList;
	}


	public ArrayList<StockoutItemDTO> getItemList() {
		return itemList;
	}


	public void setItemList(ArrayList<StockoutItemDTO> itemList) {
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
	
	
}
