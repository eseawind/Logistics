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
public class FreightManifestAction {
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
	private FreightManifestDAO fmdao=null;
	private CargoDAO cargodao=null;
	private CustomerDAO cusdao=null;
	private ConsigneeDAO consigneedao=null;
	private ConsignerDAO consignerdao=null;
	private FreightRouteDAO frdao=null;
	private CityDAO citydao=null;
	private FreightIncomeDAO incomedao=null;
	private FreightCostDAO costdao=null;
	private PaymentCollectionDAO pcdao=null;
	private CustomerQuoteDAO cqdao=null;
	private FreightShipmentDAO fsdao=null;
	//DTO对象
	private FreightManifestDTO fmdto=new FreightManifestDTO();
	private CargoDTO cargodto=new CargoDTO();
	private FreightRouteDTO frdto=new FreightRouteDTO();
	private CustomerDTO cusdto=new CustomerDTO();
	private ConsigneeDTO consigneedto=new ConsigneeDTO();
	private ConsignerDTO consignerdto=new ConsignerDTO();
	private CityDTO	citydto=new CityDTO();
	private FreightIncomeDTO fidto=new FreightIncomeDTO();
	//输入对象
	ArrayList<Integer> fmIDList=new ArrayList<Integer>();
	ArrayList<CargoDTO> cargoList=new ArrayList<CargoDTO>();
	String startDate=null;
	String endDate=null;
	String dateType=null;
	String pid=null;
	private boolean isntShipped=false;
	//输出对象
	ArrayList<Map> resultMapList=null;
	ArrayList<Map> carTypeQuoteMapList=null;
	Map data=null;
	
	public FreightManifestAction(){
		fmdao=new FreightManifestDAO();
		cargodao=new CargoDAO();
		cusdao=new CustomerDAO();
		consigneedao=new ConsigneeDAO();
		consignerdao=new ConsignerDAO();
		frdao=new FreightRouteDAO();
		citydao=new CityDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		fmdao.initiate(mysqlTools);
		cargodao.initiate(mysqlTools);
		cusdao.initiate(mysqlTools);
		consigneedao.initiate(mysqlTools);
		consignerdao.initiate(mysqlTools);
		frdao.initiate(mysqlTools);
		citydao.initiate(mysqlTools);
		incomedao=new FreightIncomeDAO();
		incomedao.initiate(mysqlTools);
		costdao=new FreightCostDAO();
		costdao.initiate(mysqlTools);
		pcdao=new PaymentCollectionDAO();
		pcdao.initiate(mysqlTools);
		cqdao=new CustomerQuoteDAO();
		cqdao.initiate(mysqlTools);
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
		if(fmdto==null)
		{
			this.message="服务器错误！";
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
			this.qualifiedAmount=fmdao.queryQualifiedAmount2(fmdto, fmdto2,this.isntShipped);
			res=fmdao.queryOnCondition2(fmdto, fmdto2, start, limit,this.isntShipped);
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
				CargoDTO cargoes=fmdao.querySum(res.get(i).getFreightManifestID());
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
				m.put("costCenter",res.get(i).getCostCenter());
				m.put("sellCenter", res.get(i).getSellCenter());
				m.put("dateCreated",res.get(i).getDateCreated()==null?null:res.get(i).getDateCreated().toString());
				
				m.put("dateExpected",Tools.toString(res.get(i).getDateExpected()));
				m.put("customer",res.get(i).getCustomer());
				m.put("origin",res.get(i).getOriginCity()+"-"+res.get(i).getOriginProvince());
				m.put("destination",res.get(i).getDestinationCity()+"-"+res.get(i).getDestinationProvince());
				m.put("dateDelivered",res.get(i).getDateDelivered()==null?null:res.get(i).getDateDelivered().toString());
				m.put("dateSigned",res.get(i).getDateSigned()==null?null:res.get(i).getDateSigned().toString());
				m.put("freightState",""+res.get(i).getFreightState());
				m.put("consigneeCompany",res.get(i).getConsigneeCompany());
				m.put("consignee",res.get(i).getConsignee());
				m.put("consigneePhone",res.get(i).getConsigneePhone());
				m.put("consigner",res.get(i).getConsigner());
				m.put("consignerPhone",res.get(i).getConsignerPhone());
				m.put("amount", cargoes==null?0:cargoes.getAmount());
				m.put("volume", cargoes==null?0:cargoes.getVolume());
				m.put("weight", cargoes==null?0:cargoes.getWeight());
				m.put("customerNumber", res.get(i).getCustomerNumber());
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

		if(fmdto==null || fmdto.getSellCenter()==null || fmdto.getSellCenter().length()==0)
		{
			mysqlTools.rollback();
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		if(Tools.isVoid(fmdto.getDateExpected())){
			this.message="缺少预计到货日期！";
			this.success=false;
			return "success";
		}
		
		try{
			Integer cusid=null;
			//判断是否是合同客户
			Tools.print(""+cusdto.getCustomerID());
			cusdto=cusdao.getDTOByID(cusdto.getCustomerID());
			if(cusdto==null)
			{
				if("零散客户".equals(fmdto.getCustomerType())){
					cusdto=new CustomerDTO();
					cusdto.setName(fmdto.getCustomer());
					cusdto.setType("零散客户");
					if(!cusdao.insert(cusdto)){
						mysqlTools.rollback();
						this.message="新增运输单失败,新增零散客户失败！";
						this.success=false;
						return "success";
					}
					cusid=cusdao.queryLastInsertID();
					if(cusid==0 || cusdao.getDTOByID(cusid)==null){
						mysqlTools.rollback();
						this.message="新增运输单失败,新增零散客户失败！";
						this.success=false;
						return "success";
					}
				}
				else if("合同客户".equals(fmdto.getCustomerType())){
					mysqlTools.rollback();
					this.message="新增运输单失败,没有选择合同客户！";
					this.success=false;
					return "success";
				}
				else{
					mysqlTools.rollback();
					this.message="新增运输单失败,提交的客户类型有误！";
					this.success=false;
					return "success";
				}
			}
			else{
				cusid=cusdto.getCustomerID();
				fmdto.setCustomerType(cusdto.getType());
				fmdto.setCustomer(cusdto.getName());
			}
			//增加客户常用提货人和收货人。
			consigneedto.setAddress(fmdto.getConsigneeAddress());
			consigneedto.setCompany(fmdto.getConsigneeCompany());
			consigneedto.setCustomerID(cusid);
			consigneedto.setName(fmdto.getConsignee());
			consigneedto.setPhone(fmdto.getConsigneePhone());
			
			consignerdto.setAddress(fmdto.getConsignerAddress());
			consignerdto.setCustomerID(cusid);
			consignerdto.setName(fmdto.getConsigner());
			consignerdto.setPhone(fmdto.getConsignerPhone());
			
			consigneedao.delete(consigneedto);
			consignerdao.delete(consignerdto);
			consigneedao.insert(consigneedto);
			consignerdao.insert(consignerdto);
			//根据运输路线编号，填入始发地，目的地信息。
			frdto=frdao.getDTOByID(frdto.getFreightRouteID());
			if(frdto==null)
			{
				mysqlTools.rollback();
				this.message="新增运输单失败,运输路线不存在！";
				this.success=false;
				return "success";
			}
			citydto=citydao.getDTOByID(frdto.getOriginID());
			if(citydto==null){
				mysqlTools.rollback();
				this.message="新增运输单失败,始发地不存在！";
				this.success=false;
				return "success";
			}
			fmdto.setOriginCity(citydto.getName());
			fmdto.setOriginProvince(citydto.getProvince());
			citydto=citydao.getDTOByID(frdto.getDestinationID());
			if(citydto==null){
				mysqlTools.rollback();
				this.message="新增运输单失败,目的地不存在！";
				this.success=false;
				return "success";
			}
			fmdto.setDestinationCity(citydto.getName());
			fmdto.setDestinationProvince(citydto.getProvince());
			fmdto.setFreightState("已创建");
			fmdto.setDateCreated(Tools.currDate());
			//建立运输单
			if(!fmdao.insert(fmdto))
			{
				mysqlTools.rollback();
				this.message="新增运输单失败！";
				this.success=false;
				return "success";
			}
			int lastfmid=0;
			lastfmid=fmdao.queryLastInsertID();
			Tools.print(""+lastfmid);
			fmdto.setFreightManifestID(lastfmid);
			if(lastfmid==0 || fmdao.getDTOByID(fmdto)==null)
			{
				mysqlTools.rollback();
				this.message="新增运输单时发生错误，请重试！";
				this.success=false;
				return "success";
			}
			//添加货物信息
			int sumAmount=0;
			double sumVolume=0;
			double sumWeight=0;
			double sumValue=0;
			if(cargoList!=null){
				for(int i=0;i<this.cargoList.size();i++)
				{
					cargodto=this.cargoList.get(i);
					cargodto.setFreightManifestID(lastfmid);
					if(!cargodao.insert(cargodto))
					{
						mysqlTools.rollback();
						this.message="增加运输货物失败！";
						this.success=false;
						return "success";
					}
					sumAmount+=cargodto.getAmount();
					sumVolume+=cargodto.getVolume();
					sumWeight+=cargodto.getWeight();
					sumValue+=cargodto.getValue();
					
				}
			}
			//增加一条运输状态
			FreightStateDTO state=new FreightStateDTO();
			state.setDate(Tools.currTimestamp());
			state.setFreightManifestID(lastfmid);
			state.setState("已创建");
			state.setStateRemarks("无");
			fmdao.insertFreightState(state);
			
			//---------------------------------------------
			//增加财务信息
			FreightIncomeDTO income=FreightIncomeDTO.valueOf(fmdto, sumAmount, sumVolume, sumWeight, sumValue);
			if(incomedao.getByID(income.getFreightManifestID())==null)
				if(!incomedao.insert(income)){
					mysqlTools.rollback();
					this.message="新增失败，增加运输收入信息失败！";
					this.success=false;
					return "success";

				}
			FreightCostDTO cost=FreightCostDTO.valueOf(fmdto, sumAmount, sumVolume, sumWeight, sumValue);
			if(costdao.getByID(cost.getFreightManifestID())==null)
				if(!costdao.insert(cost)){
					mysqlTools.rollback();
					this.message="新增失败，增加运输支出信息失败！";
					this.success=false;
					return "success";

				}
			//---------------------------------------------
			//根据有无代收货款，增加代收货款信息
			if("是".equals(fmdto.getIsCollection())){
				PaymentCollectionDTO pcdto=PaymentCollectionDTO.valueOf(fmdto, cusid);
				if(pcdao.getByID(pcdto.getFreightManifestID())==null){
					if(!pcdao.insert(pcdto)){
						mysqlTools.rollback();
						this.message="新增失败，增加代收货款信息失败！";
						this.success=false;
						return "success";
					}
				}
			}
			
			//---------------------------------------------
			mysqlTools.commit();
			this.message="新增运输单成功！";
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
			fmdto=fmdao.getDTOByID(fmdto);
			if(fmdto==null)
			{
				mysqlTools.rollback();
				this.message="查找运输单失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("fmdto.freightManifestID", fmdto.getFreightManifestID());
			this.data.put("fmdto.customerType", fmdto.getCustomerType());
			this.data.put("fmdto.customerNumber", fmdto.getCustomerNumber());
			this.data.put("fmdto.originCity", fmdto.getOriginCity());
			this.data.put("fmdto.originProvince", fmdto.getOriginProvince());
			this.data.put("fmdto.destinationCity", fmdto.getDestinationCity());
			this.data.put("fmdto.destinationProvince", fmdto.getDestinationProvince());
			this.data.put("fmdto.customer", fmdto.getCustomer());
			this.data.put("fmdto.consigner", fmdto.getConsigner());
			this.data.put("fmdto.consignerPhone", fmdto.getConsignerPhone());
			this.data.put("fmdto.consignerAddress", fmdto.getConsignerAddress());
			this.data.put("fmdto.consigneeCompany", fmdto.getConsigneeCompany());
			this.data.put("fmdto.consignee", fmdto.getConsignee());
			this.data.put("fmdto.consigneePhone", fmdto.getConsigneePhone());
			this.data.put("fmdto.consigneeAddress", fmdto.getConsigneeAddress());
			this.data.put("fmdto.dateExpected", null==fmdto.getDateExpected()?null:fmdto.getDateExpected().toString());
			this.data.put("fmdto.dateCreated", null==fmdto.getDateCreated()?null:fmdto.getDateCreated().toString());
			this.data.put("fmdto.dateDelivered", null==fmdto.getDateDelivered()?null:fmdto.getDateDelivered().toString());
			this.data.put("fmdto.dateSigned", null==fmdto.getDateSigned()?null:fmdto.getDateSigned().toString());
			this.data.put("fmdto.freightType", fmdto.getFreightType());
			this.data.put("fmdto.ackRequirement", fmdto.getAckRequirement());
			this.data.put("fmdto.announcements", fmdto.getAnnouncements());
			this.data.put("fmdto.costCenter", fmdto.getCostCenter());
			this.data.put("fmdto.sellCenter", fmdto.getSellCenter());
			this.data.put("fmdto.freightFee", fmdto.getFreightFee());
			this.data.put("fmdto.consignFee", fmdto.getConsignFee());
			this.data.put("fmdto.insuranceFee", fmdto.getInsuranceFee());
			this.data.put("fmdto.deliveryFee", fmdto.getDeliveryFee());
			this.data.put("fmdto.prepaidFee",fmdto.getPrepaidFee());
			this.data.put("fmdto.paymentType", fmdto.getPaymentType());
			this.data.put("fmdto.isCollection", fmdto.getIsCollection());
			this.data.put("fmdto.collectionFee", fmdto.getCollectionFee());
			this.data.put("fmdto.isInsurance", fmdto.getIsInsurance());
			this.data.put("fmdto.isPrepaid", fmdto.getIsPrepaid());
			this.data.put("fmdto.remarks", fmdto.getRemarks());
			this.data.put("fmdto.freightState", fmdto.getFreightState());
			this.data.put("fmdto.chargeMode", fmdto.getChargeMode());
			this.data.put("fmdto.priceByAmount", fmdto.getPriceByAmount());
			this.data.put("fmdto.priceByVolume", fmdto.getPriceByVolume());
			this.data.put("fmdto.priceByWeight", fmdto.getPriceByWeight());
			this.data.put("fmdto.insuranceRate", fmdto.getInsuranceRate());
			double sumAmount=0;
			double sumVolume=0;
			double sumWeight=0;
			double sumValue=0;
			cargodto.setFreightManifestID(fmdto.getFreightManifestID());
			cargoList=cargodao.queryOnCondition(cargodto, 0, 0);
			//添加货物
			if(cargoList!=null && cargoList.size()>0)
			{
				this.resultMapList=new ArrayList<Map>();
				for(int i=0;i<cargoList.size();i++)
				{
					sumAmount+=cargoList.get(i).getAmount();
					sumVolume+=cargoList.get(i).getVolume();
					sumWeight+=cargoList.get(i).getWeight();
					sumValue+=cargoList.get(i).getValue();
					Map m=new HashMap();
					m.put("cargoID", cargoList.get(i).getCargoID());
					m.put("amount", cargoList.get(i).getAmount());
					m.put("weight", cargoList.get(i).getWeight());
					m.put("volume", cargoList.get(i).getVolume());
					m.put("value", cargoList.get(i).getValue());
					resultMapList.add(m);
				}
			}
			this.data.put("sumAmount", sumAmount);
			this.data.put("sumVolume", sumVolume);
			this.data.put("sumWeight", sumWeight);
			this.data.put("sumValue", sumValue);
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
	public String print(){
		//check permission of basic query
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
			this.message="缺少运输单编号,无法打印！";
			this.success=false;
			return "error";
		}
		LHtml lhtml=null;
		try{
			
			String[] ids=pid.split("_");
			ArrayList<FreightManifestDTO> fms=new ArrayList<FreightManifestDTO>();
			for(String id:ids){
				Integer fmid=Integer.parseInt(id);
				fmdto.setFreightManifestID(fmid);
				fmdto=fmdao.getDTOByID(fmdto);
				if(fmdto==null)
				{
					mysqlTools.rollback();
					this.message="找不到运输单，打印运输单失败！";
					this.success=false;
					return "error";
				}
				mysqlTools.commit();
				int sumAmount=0;
				double sumVolume=0;
				double sumWeight=0;
				double sumValue=0;
				cargodto.setFreightManifestID(fmdto.getFreightManifestID());
				cargoList=cargodao.queryOnCondition(cargodto, 0, 0);
				//添加货物
				if(cargoList!=null && cargoList.size()>0)
				{
					for(int i=0;i<cargoList.size();i++)
					{
						sumAmount+=cargoList.get(i).getAmount();
						sumVolume+=cargoList.get(i).getVolume();
						sumWeight+=cargoList.get(i).getWeight();
						sumValue+=cargoList.get(i).getValue();
					}
				}
				fmdto.cargoSum.setAmount(sumAmount);
				fmdto.cargoSum.setVolume(sumVolume);
				fmdto.cargoSum.setWeight(sumWeight);
				fmdto.cargoSum.setValue(sumValue);
				fmdto.cargos=cargoList;
				fms.add(fmdto);
			}
			
			lhtml=LHtml.getLHtml(LHtml.freightManifest);
			if(lhtml==null){
				this.message="无法打开模板文件！";
				this.success=false;
				return "error";
			}
			download=lhtml.writeToHtml(fms);
			if(download==null){
				this.message="打印失败";
				this.success=false;
				return "success";
			}
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
	public String printsofm(){
		//check permission of basic query
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
			this.message="缺少运输单编号,无法打印！";
			this.success=false;
			return "error";
		}
		LHtml lhtml=null;
		try{
			String[] ids=pid.split("_");
			ArrayList<FreightManifestDTO> fms=new ArrayList<FreightManifestDTO>();
			for(String id:ids){
				Integer fmid=Integer.parseInt(id);
				fmdto.setFreightManifestID(fmid);
				fmdto=fmdao.getDTOByID(fmdto);
				if(fmdto==null)
				{
					mysqlTools.rollback();
					this.message="找不到运输单，打印运输单失败！";
					this.success=false;
					return "error";
				}
				mysqlTools.commit();
				
				int sumAmount=0;
				double sumVolume=0;
				double sumWeight=0;
				double sumValue=0;
				cargodto.setFreightManifestID(fmdto.getFreightManifestID());
				cargoList=cargodao.queryOnCondition(cargodto, 0, 0);
				//添加货物
				if(cargoList!=null && cargoList.size()>0)
				{
					for(int i=0;i<cargoList.size();i++)
					{
						sumAmount+=cargoList.get(i).getAmount();
						sumVolume+=cargoList.get(i).getVolume();
						sumWeight+=cargoList.get(i).getWeight();
						sumValue+=cargoList.get(i).getValue();
					}
				}
				fmdto.cargoSum.setAmount(sumAmount);
				fmdto.cargoSum.setVolume(sumVolume);
				fmdto.cargoSum.setWeight(sumWeight);
				fmdto.cargoSum.setValue(sumValue);
				fmdto.cargos=cargoList;
				fms.add(fmdto);
			}
			
			lhtml=LHtml.getLHtml(LHtml.stockoutFreightManifest);
			if(lhtml==null){
				this.message="无法打开模板文件！";
				this.success=false;
				return "error";
			}
			download=lhtml.writeToHtml(fms);
			if(download==null){
				this.message="打印失败";
				this.success=false;
				return "error";
			}
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

		
		if(fmdto==null||fmdto.getFreightManifestID()==null
				)
		{
			this.message="缺少运输单编号,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			cargodto.setFreightManifestID(fmdto.getFreightManifestID());
			if(!cargodao.delete(cargodto))
			{
				mysqlTools.rollback();
				this.message="删除当前运输单旧的货物信息失败！";
				this.success=false;
				return "success";
			}
			if(!fmdao.update(fmdto))
			{
				mysqlTools.rollback();
				this.message="修改运输单失败!";
				this.success=false;
				return "success";
			}
			
			if(cargoList!=null){
				for(int i=0;i<this.cargoList.size();i++)
				{
					cargodto=this.cargoList.get(i);
					cargodto.setFreightManifestID(fmdto.getFreightManifestID());
					if(!cargodao.insert(cargodto))
					{
						mysqlTools.rollback();
						this.message="修改运输单路线报价信息失败！";
						this.success=false;
						return "success";
					}
				}
			}
			mysqlTools.commit();
			this.message="修改运输单成功！";
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

		
		if(this.fmIDList==null || this.fmIDList.size()==0)
		{
			this.message="缺少运输单编号，无法删除！";
			this.success=false;
			return "success";
		}
		if(null==fmdto)
			fmdto=new FreightManifestDTO();
		if(null==cargodto)
			cargodto=new CargoDTO();
		try{
			for(int i=0;i<this.fmIDList.size();i++)
			{
				fmdto.setFreightManifestID(fmIDList.get(i));
				if(!fmdao.delete(fmdto)){
					mysqlTools.rollback();
					this.message+="删除运输单信息失败！";
					this.success=false;
					return "success";
				}
				cargodto.setFreightManifestID(fmIDList.get(i));
				if(!cargodao.delete(cargodto)){
					mysqlTools.rollback();
					this.message+="删除运输单路线报价信息失败！";
					this.success=false;
					return "success";
				}
			}
			mysqlTools.commit();
			this.message="删除运输单信息成功！";
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
	public String queryConsigners()
	{
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------
		//判断传入参数合法性
		if(cusdto==null || cusdto.getCustomerID()==null)
		{
			this.message="缺少传入参数！";
			this.success=false;
			return "success";
		}
		ArrayList<ConsignerDTO> res=null;
		try{
			//查询
			res=consignerdao.queryConsignerOnCustomerID(cusdto.getCustomerID());
			cusdto=cusdao.getDTOByID(cusdto.getCustomerID());
			if(null==cusdto)
			{
				mysqlTools.rollback();
				this.message="客户不存在";
				this.success=false;
				return "success";
			}
			CustomerQuoteDTO quote=cqdao.getDTOByID(cusdto.getCustomerID(), frdto.getFreightRouteID());
			if(res==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			data=new HashMap();
			
			data.put("priceByAmount",quote==null?0: quote.getPriceByAmount());
			data.put("priceByVolume",quote==null?0: quote.getPriceByVolume());
			data.put("priceByWeight", quote==null?0:quote.getPriceByWeight());
			data.put("deliveryQuote", quote==null?0:quote.getDeliveryQuote());
			data.put("takingQuote", quote==null?0:quote.getTakingQuote());
			/*
			priceByAmount=cusdto.getFreightCostPerCount();
			priceByVolume=cusdto.getFreightCostPerVolume();
			priceByWeight=cusdto.getFreightCostPerWeight();
			*/
			resultMapList=new ArrayList<Map>();
			for(int i=0;i<res.size();i++){
				Map m=null;
				m=new HashMap();
				m.put("consigner",res.get(i).getName());
				m.put("consignerPhone",res.get(i).getPhone());
				m.put("consignerAddress",res.get(i).getAddress());
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
	public String queryCustomerQuote()
	{
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------
		//判断传入参数合法性
		if(cusdto==null || cusdto.getCustomerID()==null ||
				frdto==null||frdto.getFreightRouteID()==null)
		{
			this.message="缺少传入参数！";
			this.success=false;
			return "success";
		}
		ArrayList<ConsignerDTO> res=null;
		try{
			//查询
			CustomerQuoteDTO quote=cqdao.getDTOByID(cusdto.getCustomerID(), frdto.getFreightRouteID());
			data=new HashMap();
			data.put("priceByAmount",quote==null?0: quote.getPriceByAmount());
			data.put("priceByVolume",quote==null?0: quote.getPriceByVolume());
			data.put("priceByWeight", quote==null?0:quote.getPriceByWeight());
			data.put("deliveryQuote", quote==null?0:quote.getDeliveryQuote());
			data.put("takingQuote", quote==null?0:quote.getTakingQuote());
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
	public String queryConsigneeCompanies(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------
		//判断传入参数合法性
		if(cusdto==null || cusdto.getCustomerID()==null)
		{
			this.message="缺少传入参数！";
			this.success=false;
			return "success";
		}
		ArrayList<ConsigneeDTO> res=null;
		try{
			//查询
			res=consigneedao.queryCompanyName(cusdto.getCustomerID());
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
				m.put("consigneeCompany",res.get(i).getCompany());
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
	public String queryConsignees(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------
		//判断传入参数合法性
		if(cusdto==null || cusdto.getCustomerID()==null || consigneedto.getCompany()==null || consigneedto.getCompany().length()==0)
		{
			this.message="缺少传入参数！";
			this.success=false;
			return "success";
		}
		ArrayList<ConsigneeDTO> res=null;
		try{
			//查询
			res=consigneedao.queryConsigneeName(cusdto.getCustomerID(), consigneedto.getCompany());
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
				m.put("consignee",res.get(i).getName());
				m.put("consigneePhone",res.get(i).getPhone());
				m.put("consigneeAddress",res.get(i).getAddress());
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
	
	public String queryCargoID(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//判断传入参数合法性
		
		ArrayList<CargoDTO> res=null;
		try{
			//查询
			res=cargodao.queryAll();
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
				m.put("cargoID",res.get(i).getCargoID());
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
	public FreightManifestDAO getFmdao() {
		return fmdao;
	}
	public void setFmdao(FreightManifestDAO fmdao) {
		this.fmdao = fmdao;
	}
	
	public FreightManifestDTO getFmdto() {
		return fmdto;
	}
	public void setFmdto(FreightManifestDTO fmdto) {
		this.fmdto = fmdto;
	}
	
	public ArrayList<Integer> getFmIDList() {
		return fmIDList;
	}
	public void setFmIDList(ArrayList<Integer> fmIDList) {
		this.fmIDList = fmIDList;
	}
	
	public CargoDAO getCargodao() {
		return cargodao;
	}
	public void setCargodao(CargoDAO cargodao) {
		this.cargodao = cargodao;
	}
	public CustomerDAO getCusdao() {
		return cusdao;
	}
	public void setCusdao(CustomerDAO cusdao) {
		this.cusdao = cusdao;
	}
	public ConsigneeDAO getConsigneedao() {
		return consigneedao;
	}
	public void setConsigneedao(ConsigneeDAO consigneedao) {
		this.consigneedao = consigneedao;
	}
	public ConsignerDAO getConsignerdao() {
		return consignerdao;
	}
	public void setConsignerdao(ConsignerDAO consignerdao) {
		this.consignerdao = consignerdao;
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
	public CargoDTO getCargodto() {
		return cargodto;
	}
	public void setCargodto(CargoDTO cargodto) {
		this.cargodto = cargodto;
	}
	public FreightRouteDTO getFrdto() {
		return frdto;
	}
	public void setFrdto(FreightRouteDTO frdto) {
		this.frdto = frdto;
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
	public ArrayList<CargoDTO> getCargoList() {
		return cargoList;
	}
	public void setCargoList(ArrayList<CargoDTO> cargoList) {
		this.cargoList = cargoList;
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


	public FreightIncomeDAO getIncomedao() {
		return incomedao;
	}


	public void setIncomedao(FreightIncomeDAO incomedao) {
		this.incomedao = incomedao;
	}


	public FreightCostDAO getCostdao() {
		return costdao;
	}


	public void setCostdao(FreightCostDAO costdao) {
		this.costdao = costdao;
	}


	public PaymentCollectionDAO getPcdao() {
		return pcdao;
	}


	public void setPcdao(PaymentCollectionDAO pcdao) {
		this.pcdao = pcdao;
	}


	public CustomerQuoteDAO getCqdao() {
		return cqdao;
	}


	public void setCqdao(CustomerQuoteDAO cqdao) {
		this.cqdao = cqdao;
	}


	public FreightIncomeDTO getFidto() {
		return fidto;
	}


	public void setFidto(FreightIncomeDTO fidto) {
		this.fidto = fidto;
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


	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}


	public boolean isIsntShipped() {
		return isntShipped;
	}


	public void setIsntShipped(boolean isntShipped) {
		this.isntShipped = isntShipped;
	}
	
	
	
}
