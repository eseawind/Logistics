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
public class StockTransferManifestAction {
	
	//基本对象
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private StockTransferManifestDAO stmdao=null;
	private StockTransferItemDAO stidao=null;
	private CustomerDAO cusdao=null;
	private CityDAO citydao=null;
	private StockItemDAO sidao=null;
	private StockTransferFinanceDAO stfdao=null;
	private WarehouseDAO wdao=null;
	private ItemDAO itemdao=null;
	private StockIncomeDAO incomedao=null;
	//DTO对象
	private StockTransferManifestDTO stmdto=new StockTransferManifestDTO();
	private StockTransferItemDTO stidto=new StockTransferItemDTO();
	private CustomerDTO cusdto=new CustomerDTO();
	private ConsigneeDTO consigneedto=new ConsigneeDTO();
	private ConsignerDTO consignerdto=new ConsignerDTO();
	private CityDTO	citydto=new CityDTO();
	private FreightIncomeDTO fidto=new FreightIncomeDTO();
	private StockItemDTO sidto=new StockItemDTO();
	//输入对象
	ArrayList<Integer> stmIDList=new ArrayList<Integer>();
	ArrayList<StockTransferItemDTO> itemList=new ArrayList<StockTransferItemDTO>();
	String startDate=null;
	String endDate=null;
	String dateType=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	ArrayList<Map> carTypeQuoteMapList=null;
	Map data=null;
	
	public StockTransferManifestAction(){
		stmdao=new StockTransferManifestDAO();
		stidao=new StockTransferItemDAO();
		cusdao=new CustomerDAO();
		citydao=new CityDAO();
		sidao=new StockItemDAO();
		stfdao=new StockTransferFinanceDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		stmdao.initiate(mysqlTools);
		stidao.initiate(mysqlTools);
		cusdao.initiate(mysqlTools);
		citydao.initiate(mysqlTools);
		sidao.initiate(mysqlTools);
		stfdao.initiate(mysqlTools);
		wdao=new WarehouseDAO();
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
		ArrayList<StockTransferManifestDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=stmdao.queryQualifiedAmountOnCondition(stmdto, startDay, endDay);
			res=stmdao.queryOnCondition(stmdto, startDay, endDay, start, limit);
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
			for(StockTransferManifestDTO iter:res){
				Map m=null;
				m=new HashMap();
				m.put("stockTransferManifestID",iter.getStockTransferManifestID());
				m.put("costCenter",iter.getCostCenter());
				m.put("dateCreated",Tools.toString(iter.getDateCreated()));
				m.put("dateTransfered", Tools.toString(iter.getDateTransfered()));
				m.put("outWarehouse", iter.getOutWarehouse());
				m.put("inWarehouse", iter.getInWarehouse());
				m.put("operator", iter.getOperator());
				m.put("approvalState", iter.getApprovalState());
				m.put("auditState", iter.getAuditState());
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

		if(stmdto==null  || stmdto.getOutWarehouseID()==null
				||stmdto.getInWarehouseID()==null)
		{
			mysqlTools.rollback();
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		
		try{
			if(stmdto.getDateTransfered()==null){
				stmdto.setDateTransfered(Tools.currDate());
			}
			stmdto.setDateCreated(Tools.currDate());
			WarehouseDTO warehouse=wdao.getDTOByID(stmdto.getInWarehouseID());
			if(warehouse==null){
				mysqlTools.rollback();
				this.message="新增移库单失败,所选调入仓库不存在！";
				this.success=false;
				return "success";
			}
			String cityid=warehouse.getCityID();
			stmdto.setInWarehouse(warehouse.getName());
			warehouse=wdao.getDTOByID(stmdto.getOutWarehouseID());
			if(warehouse==null){
				mysqlTools.rollback();
				this.message="新增移库单失败,所选调出仓库不存在！";
				this.success=false;
				return "success";
			}
			if(!cityid.equals(warehouse.getCityID())){
				mysqlTools.rollback();
				this.message="新增移库单失败,所选调出仓库和调入仓库不在同一城市！";
				this.success=false;
				return "success";
			}
			stmdto.setOutWarehouse(warehouse.getName());
			if(!stmdao.insert(stmdto))
			{
				mysqlTools.rollback();
				this.message="新增移库单失败！";
				this.success=false;
				return "success";
			}
			int lastid=0;
			lastid=stmdao.queryLastInsertID();
			Tools.print(""+lastid);
			if(stmdao.getByID(lastid)==null)
			{
				mysqlTools.rollback();
				this.message="新增移库单时发生错误，请重试！";
				this.success=false;
				return "success";
			}
			//添加货物信息
			if(itemList!=null){
				for(StockTransferItemDTO iterator:itemList)
				{
					ItemDTO tempItem=itemdao.getDTOByID(iterator.getItemID());
					if(tempItem==null){
						mysqlTools.rollback();
						this.message="增加移库货物失败！编号为"+iterator.getItemID()+"的货物不存在";
						this.success=false;
						return "success";
					}
					
					iterator.setStockTransferManifestID(lastid);
					iterator.copyFrom(tempItem);
				}
				if(!stidao.insert(itemList))
				{
					mysqlTools.rollback();
					this.message="增加移库货物失败！";
					this.success=false;
					return "success";
				}
			}
				
			mysqlTools.commit();
			this.message="新增移库单成功！";
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

		if(stmdto==null || stmdto.getStockTransferManifestID()==null )
		{
			this.message="缺少移库单编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			stmdto=stmdao.getByID(stmdto.getStockTransferManifestID());
			if(stmdto==null)
			{
				mysqlTools.rollback();
				this.message="查找移库单失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("stmdto.stockTransferManifestID", stmdto.getStockTransferManifestID());
			this.data.put("stmdto.dateCreated", Tools.toString(stmdto.getDateCreated()));
			this.data.put("stmdto.dateTransfered", Tools.toString(stmdto.getDateTransfered()));
			this.data.put("stmdto.inWarehouseID", stmdto.getInWarehouseID());
			this.data.put("stmdto.inWarehouse", stmdto.getInWarehouse());
			this.data.put("stmdto.outWarehouseID", stmdto.getOutWarehouseID());
			this.data.put("stmdto.outWarehouse", stmdto.getOutWarehouse());
			this.data.put("stmdto.operator", stmdto.getOperator());
			this.data.put("stmdto.costCenter", stmdto.getCostCenter());
			this.data.put("stmdto.loadUnloadCost", stmdto.getLoadUnloadCost());
			this.data.put("stmdto.remarks", stmdto.getRemarks());
			double sumAmount=0;
			double sumVolume=0;
			double sumWeight=0;
			stidto.setStockTransferManifestID(stmdto.getStockTransferManifestID());
			itemList=stidao.queryOnCondition(stmdto.getStockTransferManifestID());
			//添加货物
			if(itemList!=null && itemList.size()>0)
			{
				this.resultMapList=new ArrayList<Map>();
				for(StockTransferItemDTO item:itemList)
				{
					
					StockItemDTO stockitem=this.sidao.getByID(stmdto.getOutWarehouseID(), item.getItemID(), item.getDateStockin(), item.getCustomerID());
					cusdto=cusdao.getDTOByID(item.getCustomerID());
					sumAmount+=item.getAmount();
					sumVolume+=item.getVolume();
					sumWeight+=item.getWeight();
					Map m=new HashMap();
					m.put("itemID", item.getItemID());
					m.put("itemName", item.getItemName());
					m.put("itemNumber", item.getItemNumber());
					m.put("batch", item.getBatch());
					m.put("unitWeight", item.getUnitWeight());
					m.put("unitVolume", item.getUnitVolume());
					m.put("amount", item.getAmount());
					m.put("weight", item.getWeight());
					m.put("volume", item.getVolume());
					m.put("stockAmount", stockitem==null?0:stockitem.getAmount());
					m.put("customerID", item.getCustomerID());
					m.put("customer", cusdto==null?null:cusdto.getName());
					m.put("dateStockin", Tools.toString(item.getDateStockin()));
					
					resultMapList.add(m);
				}
			}
			this.data.put("stmdto.sumAmount",sumAmount);
			this.data.put("stmdto.sumWeight", sumWeight);
			this.data.put("stmdto.sumVolume", sumVolume);
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
		
		if(stmdto==null||stmdto.getStockTransferManifestID()==null
				)
		{
			this.message="缺少移库单编号,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			if(stmdto.getDateTransfered()==null){
				stmdto.setDateTransfered(Tools.currDate());
			}
			WarehouseDTO warehouse=wdao.getDTOByID(stmdto.getInWarehouseID());
			if(warehouse==null){
				mysqlTools.rollback();
				this.message="修改移库单失败,所选调入仓库不存在！";
				this.success=false;
				return "success";
			}
			String cityid=warehouse.getCityID();
			stmdto.setInWarehouse(warehouse.getName());
			warehouse=wdao.getDTOByID(stmdto.getOutWarehouseID());
			if(warehouse==null){
				mysqlTools.rollback();
				this.message="修改移库单失败,所选调出仓库不存在！";
				this.success=false;
				return "success";
			}
			if(!cityid.equals(warehouse.getCityID())){
				mysqlTools.rollback();
				this.message="修改移库单失败,所选调出仓库和调入仓库不在同一城市！";
				this.success=false;
				return "success";
			}
			stmdto.setOutWarehouse(warehouse.getName());
			if(!stidao.delete(stmdto.getStockTransferManifestID()))
			{
				mysqlTools.rollback();
				this.message="删除当前移库单旧的货物信息失败！";
				this.success=false;
				return "success";
			}
			if(!stmdao.update(stmdto))
			{
				mysqlTools.rollback();
				this.message="修改移库单失败!";
				this.success=false;
				return "success";
			}
			
			if(itemList!=null){
				for(int i=0;i<this.itemList.size();i++)
				{
					stidto=this.itemList.get(i);
					stidto.setStockTransferManifestID(stmdto.getStockTransferManifestID());
					ItemDTO tempit=itemdao.getDTOByID(stidto.getItemID());
					stidto.setItemName(tempit.getName());
					stidto.setItemNumber(tempit.getNumber());
					stidto.setBatch(tempit.getBatch());
				}
				if(!stidao.insert(itemList))
				{
					mysqlTools.rollback();
					this.message="增加移库货物失败！";
					this.success=false;
					return "success";
				}
			}
			mysqlTools.commit();
			this.message="修改移库单成功！";
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

		if(this.stmIDList==null || this.stmIDList.size()==0)
		{
			this.message="缺少移库单编号，无法删除！";
			this.success=false;
			return "success";
		}
		if(null==stmdto)
			stmdto=new StockTransferManifestDTO();
		if(null==stidto)
			stidto=new StockTransferItemDTO();
		try{
			if(!stmdao.delete(stmIDList)){
				mysqlTools.rollback();
				this.message+="删除移库单信息失败！";
				this.success=false;
				return "success";
			}
			if(!stidao.delete(stmIDList)){
				mysqlTools.rollback();
				this.message+="删除移库物料失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="删除移库单信息成功！";
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
		if(stmIDList==null || stmIDList.size()==0
				)
		{
			this.message="缺少移库单编号,无法确认！";
			this.success=false;
			return "success";
		}
		try{
			for(Integer smid:stmIDList){
				stmdto=stmdao.getByID(smid);
				if(stmdto==null){
					mysqlTools.rollback();
					this.message="批准移库单失败，移库单不存在!";
					this.success=false;
					return "success";
				}
				if(!stmdto.getApprovalState().equals("未批准")){
					continue;
				}
				WarehouseDTO inw=wdao.getDTOByID(stmdto.getInWarehouseID());
				WarehouseDTO outw=wdao.getDTOByID(stmdto.getOutWarehouseID());
				if(inw==null ){
					mysqlTools.rollback();
					this.message="批准移库单失败，移库单"+stmdto.getStockTransferManifestID()+"的移入仓库选择错误!";
					this.success=false;
					return "success";
				}
				if(outw==null ){
					mysqlTools.rollback();
					this.message="批准移库单失败，移库单"+stmdto.getStockTransferManifestID()+"的移出仓库选择错误!";
					this.success=false;
					return "success";
				}
				if(!inw.getCityID().equals(outw.getCityID())){
					mysqlTools.rollback();
					this.message+="批准移库单失败，移库单"+stmdto.getStockTransferManifestID()+"的移出仓库和移入仓库不在同一个城市!";
					this.success=false;
					return "success";
				}
				//修改批准状态
				if(!stmdao.approve(stmdto.getStockTransferManifestID()))
				{
					mysqlTools.rollback();
					this.message="批准移库单失败，修改批准状态失败!";
					this.success=false;
					return "success";
				}
				//将物料移库
				itemList=stidao.queryOnCondition(stmdto.getStockTransferManifestID());
				if(itemList!=null){
					for(StockTransferItemDTO stockitem:itemList){
						sidto=sidao.getByID(stmdto.getOutWarehouseID(), stockitem.getItemID(),
								stockitem.getDateStockin(),stockitem.getCustomerID());
						if(sidto==null){
							mysqlTools.rollback();
							this.message="批准移库单失败，物料"+stockitem.getItemName()+"不存在!";
							this.success=false;
							return "success";
						}
						else{
							if(stockitem.getAmount()==0){
								mysqlTools.rollback();
								this.message="批准移库单失败，物料"+stockitem.getItemName()+"移库数量为0!";
								this.success=false;
								return "success";
							}
							if(sidto.getAmount()<stockitem.getAmount())
							{
								mysqlTools.rollback();
								this.message="批准移库单失败，库存物料"+stockitem.getItemName()+"数量不足!";
								this.success=false;
								return "success";
							}
							if(!sidao.updateAmount(stmdto.getOutWarehouseID(), stockitem.getItemID(),
								stockitem.getDateStockin(),stockitem.getCustomerID(), -stockitem.getAmount())){
								mysqlTools.rollback();
								this.message="批准移库单失败，物料移库失败!";
								this.success=false;
								return "success";
							}
							sidao.deleteZero();
							if(sidao.getByID(stmdto.getInWarehouseID(), 
									stockitem.getItemID(), stmdto.getDateTransfered(),stockitem.getCustomerID())!=null){
								if(!sidao.updateAmount(stmdto.getInWarehouseID(), 
									stockitem.getItemID(), stmdto.getDateTransfered(),stockitem.getCustomerID(), stockitem.getAmount())){
									mysqlTools.rollback();
									this.message="批准移库单失败，物料移库失败!";
									this.success=false;
									return "success";
								}
							}
							else{
								StockItemDTO newSidto=new StockItemDTO();
								newSidto.setAmount(stockitem.getAmount());
								newSidto.setItemID(stockitem.getItemID());
								newSidto.setStockinDate(stmdto.getDateTransfered());
								newSidto.setWarehouseID(stmdto.getInWarehouseID());
								newSidto.setCustomerID(stockitem.getCustomerID());
								newSidto.setLastAccountDate(stmdto.getDateTransfered());
								if(!sidao.insert(newSidto)){
									mysqlTools.rollback();
									this.message="批准移库单失败，物料移库失败!";
									this.success=false;
									return "success";
								}
							}
							stockitem.customerDTO=cusdao.getDTOByID(stockitem.getCustomerID());
							StockIncomeDTO income=StockIncomeDTO.valueOf(stmdto, stockitem, sidto.getLastAccountDate());
							if(!incomedao.insert(income)){
								mysqlTools.rollback();
								this.message+="无法将收入信息写入数据库，结算失败！";
								this.success=false;
								return "success";
							}
						}
					}
				}
				//将收支放出移库收支表
				StockTransferFinanceDTO sfdto=StockTransferFinanceDTO.valueOf(stmdto);
				if(!stfdao.insert(sfdto)){
					mysqlTools.rollback();
					this.message="批准移库单失败，生成移库收支信息失败!";
					this.success=false;
					return "success";
				}
				
			}//for
			mysqlTools.commit();
			this.message="批准移库单成功！";
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
		if(stmIDList==null || stmIDList.isEmpty()
				)
		{
			this.message="缺少移库单编号,无法审核！";
			this.success=false;
			return "success";
		}
		try{
			if(!stmdao.audit(stmIDList)){
				mysqlTools.rollback();
				this.message="审核移库单失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="审核移库单成功！";
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

		
		if(sidto==null || Tools.isVoid(sidto.getWarehouseID()))
		{
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		ArrayList<StockItemDTO> res=null;
		try{
			Date startDay=null;
			Date endDay=null;
			if(startDate!=null && startDate.length()!=0)
				startDay=Date.valueOf(startDate);
			if(endDate!=null && endDate.length()!=0)
				endDay=Date.valueOf(endDate);
			//查询
			res=sidao.queryItems(sidto.getWarehouseID(), sidto.getItemID()
					, sidto.getCustomerID(),startDay,endDay,StockItemDAO.JOIN_ITEM|StockItemDAO.JOIN_CUSTOMER);
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
				m.put("customer",iter.customer.getName());
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


	public StockTransferManifestDAO getStmdao() {
		return stmdao;
	}


	public void setStmdao(StockTransferManifestDAO stmdao) {
		this.stmdao = stmdao;
	}


	public StockTransferItemDAO getStidao() {
		return stidao;
	}


	public void setStidao(StockTransferItemDAO stidao) {
		this.stidao = stidao;
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


	public StockTransferFinanceDAO getStfdao() {
		return stfdao;
	}


	public void setStfdao(StockTransferFinanceDAO stfdao) {
		this.stfdao = stfdao;
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


	public StockTransferManifestDTO getStmdto() {
		return stmdto;
	}


	public void setStmdto(StockTransferManifestDTO stmdto) {
		this.stmdto = stmdto;
	}


	public StockTransferItemDTO getStidto() {
		return stidto;
	}


	public void setStidto(StockTransferItemDTO stidto) {
		this.stidto = stidto;
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


	public ArrayList<Integer> getStmIDList() {
		return stmIDList;
	}


	public void setStmIDList(ArrayList<Integer> stmIDList) {
		this.stmIDList = stmIDList;
	}


	public ArrayList<StockTransferItemDTO> getItemList() {
		return itemList;
	}


	public void setItemList(ArrayList<StockTransferItemDTO> itemList) {
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
