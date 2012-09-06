package Logistics.Servlet;
import java.io.File;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Logistics.Common.LHtml;
import Logistics.Common.Permission;
import Logistics.Common.Tools;
import Logistics.Common.Permission.MethodCode;
import Logistics.DAO.*;
import Logistics.DTO.*;
public class StockinManifestAction {
	//基本对象
	private long pst;
	protected File upload;
	protected String uploadContentType;
	protected String uploadFileName;
	private InputStream download;
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	//DAO对象
	private StockinManifestDAO simdao=null;
	private StockinItemDAO siidao=null;
	private CustomerDAO cusdao=null;
	private CityDAO citydao=null;
	private StockItemDAO sidao=null;
	private StockinFinanceDAO sfdao=null;
	private ItemDAO itemdao=null;
	private WarehouseDAO wdao=null;
	//DTO对象
	private StockinManifestDTO simdto=new StockinManifestDTO();
	private StockinItemDTO siidto=new StockinItemDTO();
	private CustomerDTO cusdto=new CustomerDTO();
	private ConsigneeDTO consigneedto=new ConsigneeDTO();
	private ConsignerDTO consignerdto=new ConsignerDTO();
	private CityDTO	citydto=new CityDTO();
	private FreightIncomeDTO fidto=new FreightIncomeDTO();
	private StockItemDTO sidto=new StockItemDTO();
	
	//输入对象
	ArrayList<Integer> simIDList=new ArrayList<Integer>();
	ArrayList<StockinItemDTO> itemList=new ArrayList<StockinItemDTO>();
	String startDate=null;
	String endDate=null;
	String dateType=null;
	String pid=null;
	//输出对象
	ArrayList<Map> resultMapList=null;
	ArrayList<Map> carTypeQuoteMapList=null;
	Map data=null;
	
	public StockinManifestAction(){
		simdao=new StockinManifestDAO();
		siidao=new StockinItemDAO();
		cusdao=new CustomerDAO();
		citydao=new CityDAO();
		sidao=new StockItemDAO();
		sfdao=new StockinFinanceDAO();
		itemdao=new ItemDAO();
		wdao=new WarehouseDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		simdao.initiate(mysqlTools);
		siidao.initiate(mysqlTools);
		cusdao.initiate(mysqlTools);
		citydao.initiate(mysqlTools);
		sidao.initiate(mysqlTools);
		sfdao.initiate(mysqlTools);
		itemdao.initiate(mysqlTools);
		wdao.initiate(mysqlTools);
	}
	
	
	public String queryOnCondition(){
		pst=System.currentTimeMillis();
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
		ArrayList<StockinManifestDTO> res=null;
		try{
			//查询
			this.qualifiedAmount=simdao.queryQualifiedAmountOnCondition(simdto, startDay, endDay);
			res=simdao.queryOnCondition(simdto, startDay, endDay, start, limit);
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
			for(StockinManifestDTO iterator:res){
				Map m=null;
				m=new HashMap();
				m.put("stockinManifestID",iterator.getStockinManifestID());
				m.put("dateCreated",Tools.toString(iterator.getDateCreated()));
				m.put("dateStockin",Tools.toString(iterator.getDateStockin()));
				m.put("warehouseName", iterator.getWarehouseName());
				m.put("deliveryPerson", iterator.getDeliveryPerson());
				m.put("costCenter",iterator.getCostCenter());
				m.put("sellCenter", iterator.getSellCenter());
				m.put("dateStockin",Tools.toString(iterator.getDateStockin()));
				m.put("customer",iterator.getCustomer());
				m.put("approvalState", iterator.getApprovalState());
				m.put("auditState", iterator.getAuditState());
				m.put("remarks", iterator.getRemarks());
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
	public String print(){
		//check permission of basic query
		pst=System.currentTimeMillis();
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "error";
		}
		//----------------------------------------------------------------

		//Check permission of query
		if(!Permission.checkPermission(this, MethodCode.print)){
					this.message="请求失败，用户没有权限进行此项操作！";
					this.success=false;
					return "error";
				}
		//---------------------------------------------------------------

		
		if(Tools.isVoid(pid))
		{
			this.message="缺少运入库单编号,无法打印！";
			this.success=false;
			return "error";
		}
		LHtml lhtml=null;
		try{
			
			String[] ids=pid.split("_");
			ArrayList<StockinManifestDTO> sims=new ArrayList<StockinManifestDTO>();
			for(String id:ids){
				Integer simid=Integer.parseInt(id);
				simdto=simdao.getByID(simid);
				if(simdto==null)
				{
					mysqlTools.rollback();
					this.message="找不到入库单，打印入库单失败！";
					this.success=false;
					return "error";
				}
				mysqlTools.commit();
				int sumAmount=0;
				double sumVolume=0;
				double sumWeight=0;
				simdto.stockinItems=siidao.queryOnCondition(simid);
				//添加货物
				if(simdto.stockinItems!=null && simdto.stockinItems.size()>0)
				{
					for(int i=0;i<simdto.stockinItems.size();i++)
					{
						sumAmount+=simdto.stockinItems.get(i).getAmount();
						sumVolume+=simdto.stockinItems.get(i).getVolume();
						sumWeight+=simdto.stockinItems.get(i).getWeight();
					}
				}
				simdto.setSumAmount(sumAmount);
				simdto.setSumVolume(sumVolume);
				simdto.setSumWeight(sumWeight);
				sims.add(simdto);
			}
			lhtml=LHtml.getLHtml(LHtml.stockinManifest);
			if(lhtml==null){
				this.message="无法打开模板文件！";
				this.success=false;
				return "error";
			}
			download=lhtml.writeSimToHtml(sims);
			if(download==null){
				this.message="打印失败";
				this.success=false;
				return "error";
			}
			Tools.print(""+(System.currentTimeMillis()-pst));
			this.message="成功";
			this.success=true;
			return "success";
			
		}catch(Exception e){
			mysqlTools.rollback();
			e.printStackTrace();
			this.message="操作失败！";
			this.success=false;
			return "error";
		}
		finally{
			if(mysqlTools!=null)
				mysqlTools.close();
			if(lhtml!=null)
				lhtml.close();
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

		
		if(simdto==null ||Tools.isVoid(simdto.getCostCenter()) || 
				Tools.isVoid(simdto.getSellCenter()) ||
				simdto.getWarehouseID()==null)
		{
			mysqlTools.rollback();
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		
		try{
			simdto.setDateCreated(Tools.currDate());
			if(simdto.getDateStockin()==null){
				simdto.setDateStockin(Tools.currDate());
			}
			WarehouseDTO warehouse=wdao.getDTOByID(simdto.getWarehouseID());
			if(warehouse==null){
				mysqlTools.rollback();
				this.message="新增入库单失败,所选仓库不存在！";
				this.success=false;
				return "success";
			}
			simdto.setWarehouseName(warehouse.getName());
			CustomerDTO cus=cusdao.getDTOByID(simdto.getCustomerID());
			if(cus==null){
				mysqlTools.rollback();
				this.message="新增入库单失败,所选客户不存在！";
				this.success=false;
				return "success";
			}
			simdto.setCustomer(cus.getName());
			if(!simdao.insert(simdto))
			{
				mysqlTools.rollback();
				this.message="新增入库单失败！";
				this.success=false;
				return "success";
			}
			int lastid=0;
			lastid=simdao.queryLastInsertID();
			Tools.print(""+lastid);
			if(simdao.getByID(lastid)==null)
			{
				mysqlTools.rollback();
				this.message="新增入库单时发生错误，请重试！";
				this.success=false;
				return "success";
			}
			//添加货物信息
			if(itemList!=null){
				for(StockinItemDTO iterator:itemList)
				{
					
					ItemDTO tempItem=itemdao.getDTOByID(iterator.getItemID());
					if(tempItem==null){
						mysqlTools.rollback();
						this.message="增加入库货物失败！编号为"+iterator.getItemID()+"的货物不存在";
						this.success=false;
						return "success";
					}
					iterator.setStockinManifestID(lastid);
					iterator.copyFrom(tempItem);
				}
				if(!siidao.insert(itemList))
				{
					mysqlTools.rollback();
					this.message="增加入库货物失败！";
					this.success=false;
					return "success";
				}
			}
				
			mysqlTools.commit();
			this.message="新增入库单成功！";
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

		
		if(simdto==null || simdto.getStockinManifestID()==null )
		{
			this.message="缺少入库单编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			simdto=simdao.getByID(simdto.getStockinManifestID());
			if(simdto==null)
			{
				mysqlTools.rollback();
				this.message="查找入库单失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("simdto.stockinManifestID", simdto.getStockinManifestID());
			this.data.put("simdto.customerID", simdto.getCustomerID());
			this.data.put("simdto.customer", simdto.getCustomer());
			this.data.put("simdto.dateCreated", Tools.toString(simdto.getDateCreated()));
			this.data.put("simdto.dateStockin", Tools.toString(simdto.getDateStockin()));
			this.data.put("simdto.warehouseID", simdto.getWarehouseID());
			this.data.put("simdto.warehouseName", simdto.getWarehouseName());
			this.data.put("simdto.deliveryPerson", simdto.getDeliveryPerson());
			this.data.put("simdto.deliveryPhone", simdto.getDeliveryPhone());
			this.data.put("simdto.takingAddress", simdto.getTakingAddress());
			this.data.put("simdto.costCenter", simdto.getCostCenter());
			this.data.put("simdto.sellCenter", simdto.getSellCenter());
			this.data.put("simdto.remarks", simdto.getRemarks());
			this.data.put("simdto.chargeMode", simdto.getChargeMode());
			this.data.put("simdto.stockinFee", simdto.getStockinFee());
			this.data.put("simdto.loadUnloadCost", simdto.getLoadUnloadCost());
			this.data.put("simdto.unitPrice", simdto.getUnitPrice());
			this.data.put("approvalState", simdto.getApprovalState());
			this.data.put("auditState", simdto.getAuditState());
			data.put("simdto.customerNumber", simdto.getCustomerNumber());
			data.put("simdto.originAgent", simdto.getOriginAgent());
			data.put("simdto.originAddress", simdto.getOriginAddress());
			data.put("simdto.originPerson", simdto.getOriginPerson());
			data.put("simdto.originPhone", simdto.getOriginPhone());
			double sumAmount=0;
			double sumVolume=0;
			double sumWeight=0;
			double sumValue=0;
			siidto.setStockinManifestID(simdto.getStockinManifestID());
			itemList=siidao.queryOnCondition(simdto.getStockinManifestID());
			//添加货物
			if(itemList!=null && itemList.size()>0)
			{
				this.resultMapList=new ArrayList<Map>();
				for(StockinItemDTO iter:itemList)
				{
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
					m.put("weight", iter.getWeight());
					m.put("volume", iter.getVolume());
					m.put("expectedAmount", iter.getExpectedAmount());
					m.put("label", iter.getLabel());
					m.put("isSN", iter.getIsSN());
					m.put("remarks", iter.getRemarks());
					m.put("unit", iter.getUnit());
					resultMapList.add(m);
				}
			}
			this.data.put("simdto.sumAmount", sumAmount);
			this.data.put("simdto.sumVolume", sumVolume);
			this.data.put("simdto.sumWeight", sumWeight);
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

		
		if(simdto==null ||Tools.isVoid(simdto.getCostCenter()) || 
				Tools.isVoid(simdto.getSellCenter()) ||
				simdto.getWarehouseID()==null)
		{
			this.message="缺少入库单编号,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			if(simdto.getDateStockin()==null){
				simdto.setDateStockin(Tools.currDate());
			}
			WarehouseDTO warehouse=wdao.getDTOByID(simdto.getWarehouseID());
			if(warehouse==null){
				mysqlTools.rollback();
				this.message="修改入库单失败,所选仓库不存在！";
				this.success=false;
				return "success";
			}
			simdto.setWarehouseName(warehouse.getName());
			CustomerDTO cus=cusdao.getDTOByID(simdto.getCustomerID());
			if(cus==null){
				mysqlTools.rollback();
				this.message="修改入库单失败,所选客户不存在！";
				this.success=false;
				return "success";
			}
			simdto.setCustomer(cus.getName());
			
			if(!siidao.delete(simdto.getStockinManifestID()))
			{
				mysqlTools.rollback();
				this.message="删除当前入库单旧的货物信息失败！";
				this.success=false;
				return "success";
			}
			if(!simdao.update(simdto))
			{
				mysqlTools.rollback();
				this.message="修改入库单失败!";
				this.success=false;
				return "success";
			}
			
			if(itemList!=null){
				for(int i=0;i<this.itemList.size();i++)
				{
					siidto=this.itemList.get(i);
					siidto.setStockinManifestID(simdto.getStockinManifestID());
				}
				if(!siidao.insert(itemList))
				{
					mysqlTools.rollback();
					this.message="增加入库货物失败！";
					this.success=false;
					return "success";
				}
			}
			mysqlTools.commit();
			this.message="修改入库单成功！";
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

		
		if(this.simIDList==null || this.simIDList.size()==0)
		{
			this.message="缺少入库单编号，无法删除！";
			this.success=false;
			return "success";
		}
		if(null==simdto)
			simdto=new StockinManifestDTO();
		if(null==siidto)
			siidto=new StockinItemDTO();
		try{
			if(!simdao.delete(simIDList)){
				mysqlTools.rollback();
				this.message+="删除入库单信息失败！";
				this.success=false;
				return "success";
			}
			if(!siidao.delete(simIDList)){
				mysqlTools.rollback();
				this.message+="删除入库物料失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="删除入库单信息成功！";
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

		
		
		if(simIDList==null || simIDList.isEmpty()
				)
		{
			this.message="缺少入库单编号,无法确认！";
			this.success=false;
			return "success";
		}
		try{
			for(Integer smid:simIDList){
				simdto=simdao.getByID(smid);
				if(simdto==null){
					mysqlTools.rollback();
					this.message="批准入库单失败，入库单不存在!";
					this.success=false;
					return "success";
				}
				CustomerDTO cus=cusdao.getDTOByID(simdto.getCustomerID());
				if(cus==null){
					this.message="批准入库单失败，所选客户不存在!";
					this.success=false;
					return "success";
				}
				simdto.setCustomer(cus.getName());
				if(!simdto.getApprovalState().equals("未批准")){
					continue;
				}
				//修改批准状态
				if(!simdao.approve(simdto.getStockinManifestID()))
				{
					mysqlTools.rollback();
					this.message="批准入库单失败，修改批准状态失败!";
					this.success=false;
					return "success";
				}
				//将物料入库
				itemList=siidao.queryOnCondition(simdto.getStockinManifestID());
				if(itemList!=null){
					for(StockinItemDTO stockitem:itemList){
						if(sidao.getByID(simdto.getWarehouseID(), stockitem.getItemID(),
								simdto.getDateStockin(),simdto.getCustomerID())==null){
							sidto.setAmount(stockitem.getAmount());
							sidto.setItemID(stockitem.getItemID());
							sidto.setStockinDate(simdto.getDateStockin());
							sidto.setWarehouseID(simdto.getWarehouseID());
							sidto.setCustomerID(simdto.getCustomerID());
							sidto.setLastAccountDate(simdto.getDateStockin());
							if(!sidao.insert(sidto)){
								mysqlTools.rollback();
								this.message="批准入库单失败，物料入库失败!";
								this.success=false;
								return "success";
							}
						}
						else{
							if(!sidao.updateAmount(simdto.getWarehouseID(), stockitem.getItemID(),
								simdto.getDateStockin(),simdto.getCustomerID(), stockitem.getAmount())){
								mysqlTools.rollback();
								this.message="批准入库单失败，物料入库失败!";
								this.success=false;
								return "success";
							}
						}
						//使用物料生成库存收入信息
						
					}
				}
				//将收支放入装入库收支表
				StockinFinanceDTO sfdto=StockinFinanceDTO.valueOf(simdto);
				if(!sfdao.insert(sfdto)){
					mysqlTools.rollback();
					this.message="批准入库单失败，生成入库收支信息失败!";
					this.success=false;
					return "success";
				}
				
			}//for
			mysqlTools.commit();
			this.message="批准入库单成功！";
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
		if(simIDList==null || simIDList.isEmpty()
				)
		{
			this.message="缺少入库单编号,无法审核！";
			this.success=false;
			return "success";
		}
		try{
			if(!simdao.audit(simIDList)){
				mysqlTools.rollback();
				this.message="审核入库单失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="审核入库单成功！";
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
			this.data.put("amountQuote", cusdto.getStockInCostPerCount());
			this.data.put("volumeQuote", cusdto.getStockInCostPerVolume());
			this.data.put("weightQuote", cusdto.getStockInCostPerWeight());
			
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


	public StockinManifestDAO getSimdao() {
		return simdao;
	}


	public void setSimdao(StockinManifestDAO simdao) {
		this.simdao = simdao;
	}


	public StockinItemDAO getSiidao() {
		return siidao;
	}


	public void setSiidao(StockinItemDAO siidao) {
		this.siidao = siidao;
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


	public StockinFinanceDAO getSfdao() {
		return sfdao;
	}


	public void setSfdao(StockinFinanceDAO sfdao) {
		this.sfdao = sfdao;
	}


	public ItemDAO getItemdao() {
		return itemdao;
	}


	public void setItemdao(ItemDAO itemdao) {
		this.itemdao = itemdao;
	}


	public WarehouseDAO getWdao() {
		return wdao;
	}


	public void setWdao(WarehouseDAO wdao) {
		this.wdao = wdao;
	}


	public StockinManifestDTO getSimdto() {
		return simdto;
	}


	public void setSimdto(StockinManifestDTO simdto) {
		this.simdto = simdto;
	}


	public StockinItemDTO getSiidto() {
		return siidto;
	}


	public void setSiidto(StockinItemDTO siidto) {
		this.siidto = siidto;
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


	public ArrayList<Integer> getSimIDList() {
		return simIDList;
	}


	public void setSimIDList(ArrayList<Integer> simIDList) {
		this.simIDList = simIDList;
	}


	public ArrayList<StockinItemDTO> getItemList() {
		return itemList;
	}


	public void setItemList(ArrayList<StockinItemDTO> itemList) {
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


	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}
	
}
