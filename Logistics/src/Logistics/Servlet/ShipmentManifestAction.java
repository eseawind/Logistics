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
public class ShipmentManifestAction {
	//基本对象
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
	ShipmentManifestDAO smdao=null;
	FreightRouteDAO frdao=null;
	FreightContractorDAO fcdao=null;
	CarDAO cardao=null;
	CityDAO citydao=null;
	FreightShipmentDAO fsdao=null;
	FreightManifestDAO fmdao=null;
	FreightRouteQuoteDAO frqdao=null;
	CarTypeQuoteDAO ctqdao=null;
	ShipmentCostDAO scdao=null;
	//DTO对象
	ShipmentManifestDTO smdto=new ShipmentManifestDTO();
	FreightRouteDTO frdto=new FreightRouteDTO();
	FreightContractorDTO fcdto=new FreightContractorDTO();
	CarDTO cardto=new CarDTO();
	CityDTO citydto=new CityDTO();
	FreightShipmentDTO fsdto=new FreightShipmentDTO();
	FreightManifestDTO fmdto=new FreightManifestDTO();
	CargoDTO cargodto=new CargoDTO();
	//输入对象
	String startDate=null;
	String endDate=null;
	ArrayList<Integer> fmIDList=new ArrayList<Integer>();
	ArrayList<Integer> smIDList=new ArrayList<Integer>();
	String pid=null;
	//输出对象
	ArrayList<Map> resultMapList=new ArrayList<Map>();
	Map data=new HashMap();
	
	public ShipmentManifestAction(){
		smdao=new ShipmentManifestDAO();
		frdao=new FreightRouteDAO();
		fcdao=new FreightContractorDAO();
		cardao=new CarDAO();
		citydao=new CityDAO();
		fsdao=new FreightShipmentDAO();
		fmdao=new FreightManifestDAO();
		frqdao=new FreightRouteQuoteDAO();
		ctqdao=new CarTypeQuoteDAO();
		try {
			mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools=null;
		}
		smdao.initiate(mysqlTools);
		frdao.initiate(mysqlTools);
		fcdao.initiate(mysqlTools);
		cardao.initiate(mysqlTools);
		citydao.initiate(mysqlTools);
		fsdao.initiate(mysqlTools);
		fmdao.initiate(mysqlTools);
		frqdao.initiate(mysqlTools);
		ctqdao.initiate(mysqlTools);
		scdao=new ShipmentCostDAO();
		scdao.initiate(mysqlTools);
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
		if(smdto==null)
		{
			this.message="服务器错误！";
			this.success=false;
			return "success";
		}
		if(this.start<0) start=0;
		if(this.limit<0) limit=0;
		ArrayList<ShipmentManifestDTO> res=null;
		ShipmentManifestDTO smdto2=new ShipmentManifestDTO();
		//设置开始和结束日期
		if(startDate!=null && startDate.length()!=0)
			smdto.setDateCreated(startDate);
		if(endDate!=null && endDate.length()!=0)
			smdto2.setDateCreated(endDate);
		try{
			//查询
			this.qualifiedAmount=smdao.queryQualifiedAmount2(smdto, smdto2);
			res=smdao.queryOnCondition2(smdto, smdto2, start, limit);
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
				m.put("shipmentManifestID",res.get(i).getShipmentManifestID());
				m.put("dateCreated",res.get(i).getDateCreated()==null?null:res.get(i).getDateCreated().toString());
				m.put("contractor", res.get(i).getContractor());
				m.put("origin",res.get(i).getOriginCity()+"-"+res.get(i).getOriginProvince());
				m.put("destination",res.get(i).getDestinationCity()+"-"+res.get(i).getDestinationProvince());
				m.put("consigneeCompany",res.get(i).getConsigneeCompany());
				m.put("consignee",res.get(i).getConsignee());
				m.put("consigneePhone",res.get(i).getConsigneePhone());
				m.put("carID",res.get(i).getCarID());
				m.put("driverName",res.get(i).getDriverName());
				m.put("driverPhone",res.get(i).getDriverPhone());
				m.put("carType",res.get(i).getCarType());
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
			this.message="缺少装车单号编号,无法打印！";
			this.success=false;
			return "error";
		}
		LHtml lhtml=null;
		try{
			
			String[] ids=pid.split("_");
			ArrayList<ShipmentManifestDTO> sms=new ArrayList<ShipmentManifestDTO>();
			for(String id:ids){
				Integer smid=Integer.parseInt(id);
				smdto.setShipmentManifestID(smid);
				smdto=smdao.getDTOByID(smdto);
				if(smdto==null)
				{
					mysqlTools.rollback();
					this.message="找不到装车单，打印失败！";
					this.success=false;
					return "error";
				}
				int sumAmount=0;
				double sumVolume=0;
				double sumWeight=0;
				double sumValue=0;
				FreightShipmentDTO dto=new FreightShipmentDTO();
				ArrayList<FreightShipmentDTO> fsdtos=fsdao.queryOnCondition(dto);
				if(fsdtos==null){
					mysqlTools.rollback();
					this.message="查找装车单所属运输单失败，打印失败！";
					this.success=false;
					return "error";
				}
				smdto.fms=new ArrayList<FreightManifestDTO>();
				for(FreightShipmentDTO fsdto:fsdtos){
					fmdto.setFreightManifestID(fsdto.getFreightManifestID());
					fmdto=fmdao.getDTOByID(fmdto);
					
					if(fmdto!=null){
						CargoDTO sum=fmdao.querySum(fmdto.getFreightManifestID());
						fmdto.setSumAmount(sum.getAmount());
						fmdto.setSumVolume(sum.getVolume());
						fmdto.setSumWeight(sum.getWeight());
						fmdto.setSumValue(sum.getValue());
						smdto.fms.add(fmdto);
					}
						
				}
				sms.add(smdto);
			}
			lhtml=LHtml.getLHtml(LHtml.shipmentManifest);
			if(lhtml==null){
				this.message="无法打开模板文件！";
				this.success=false;
				return "error";
			}
			
			download=lhtml.writeSMToHtml(sms);
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

		
		if(smdto==null ||fcdto.getFreightContractorID()==null || smdto.getCarID()==null)
		{
			mysqlTools.rollback();
			this.message="缺少必要信息！";
			this.success=false;
			return "success";
		}
		
		try{
			//设置建单日期
			smdto.setDateCreated(Tools.currDate());
			//通过承运单位编号，获取承运单位DTO
			fcdto=fcdao.getDTOByID(fcdto.getFreightContractorID());
			if(fcdto==null){
				mysqlTools.rollback();
				this.message="新增装车单失败,选择的承运单位不存在！";
				this.success=false;
				return "success";
			}
			smdto.setContractor(fcdto.getName());
			//通过运输路线编号，获取运输路线DTO
			frdto=frdao.getDTOByID(frdto.getFreightRouteID());
			if(frdto==null){
				mysqlTools.rollback();
				this.message="新增装车单失败,选择的运输路线不存在！";
				this.success=false;
				return "success";
			}
			//通过运输路线DTO，获取始发地，目的地DTO
			citydto=citydao.getDTOByID(frdto.getOriginID());
			smdto.setOriginCity(citydto.getName());
			smdto.setOriginProvince(citydto.getProvince());
			citydto=citydao.getDTOByID(frdto.getDestinationID());
			smdto.setDestinationCity(citydto.getName());
			smdto.setDestinationProvince(citydto.getProvince());
			//新增装车单
			if(!smdao.insert(smdto)){
				mysqlTools.rollback();
				this.message="新增装车单失败！";
				this.success=false;
				return "success";
			}
			
			int smid=smdao.queryLastInsertID();
			smdto.setShipmentManifestID(smid);
			if(smid==0 || smdao.getDTOByID(smdto)==null){
				mysqlTools.rollback();
				this.message="新增装车单时发生错误，请重试！";
				this.success=false;
				return "success";
			}
			//新增运输装车信息，将运输单列表填入数据库
			fsdto.setShipmentManifestID(smid);
			for(int i=0;i<fmIDList.size();i++)
			{
				fsdto.setFreightManifestID(fmIDList.get(i));
				if(!fsdao.insert(fsdto)){
					mysqlTools.rollback();
					this.message="新增装车单失败，无法添加运输单列表！";
					this.success=false;
					return "success";
				}
			}
			CargoDTO sum=smdao.querySum(smdto.getShipmentManifestID());
			ShipmentCostDTO shipmentCost=ShipmentCostDTO.valueOf(smdto, sum.getAmount()
					, sum.getVolume(), sum.getWeight(), sum.getValue());
			if(!scdao.insert(shipmentCost)){
				mysqlTools.rollback();
				this.message="生成装车单财务信息失败！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="新增装车单成功！";
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
		
		if(smdto==null || smdto.getShipmentManifestID()==null )
		{
			this.message="缺少装车单编号,无法查询！";
			this.success=false;
			return "success";
		}
		try{
			smdto=smdao.getDTOByID(smdto);
			if(smdto==null)
			{
				mysqlTools.rollback();
				this.message="查找装车单失败！";
				this.success=false;
				return "success";
			}
			
			mysqlTools.commit();
			this.data=new HashMap();
			this.data.put("smdto.shipmentManifestID", smdto.getShipmentManifestID());
			this.data.put("smdto.dateCreated", null==smdto.getDateCreated()?null:smdto.getDateCreated().toString());
			this.data.put("smdto.contractor", smdto.getContractor());
			this.data.put("smdto.carID", smdto.getCarID());
			this.data.put("smdto.carType", smdto.getCarType());
			this.data.put("smdto.driverName", smdto.getDriverName());
			this.data.put("smdto.driverPhone", smdto.getDriverPhone());
			
			this.data.put("smdto.consigneeCompany", smdto.getConsigneeCompany());
			this.data.put("smdto.consignee", smdto.getConsignee());
			this.data.put("smdto.consigneePhone", smdto.getConsigneePhone());
			this.data.put("smdto.consigneeAddress", smdto.getConsigneeAddress());
			
			this.data.put("smdto.originCity", smdto.getOriginCity());
			this.data.put("smdto.originProvince", smdto.getOriginProvince());
			this.data.put("smdto.destinationCity", smdto.getDestinationCity());
			this.data.put("smdto.destinationProvince", smdto.getDestinationProvince());
			this.data.put("smdto.announcements", smdto.getAnnouncements());
			this.data.put("smdto.remarks", smdto.getRemarks());
			this.data.put("smdto.chargeMode", smdto.getChargeMode());
			this.data.put("smdto.freightCost",smdto.getFreightCost());
			this.data.put("smdto.loadUnloadCost",smdto.getLoadUnloadCost());
			this.data.put("smdto.otherCost",smdto.getOtherCost());
			this.data.put("smdto.originAgent", smdto.getOriginAgent());
			this.data.put("smdto.destinationAgent", smdto.getDestinationAgent());
			this.data.put("smdto.originAddress", smdto.getOriginAddress());
			this.data.put("smdto.originPerson", smdto.getOriginPerson());
			this.data.put("smdto.originPhone", smdto.getOriginPhone());
			double sumAmount=0;
			double sumVolume=0;
			double sumWeight=0;
			fsdto.setFreightManifestID(null);
			ArrayList<FreightShipmentDTO> fsList=null;
			fsdto.setShipmentManifestID(smdto.getShipmentManifestID());
			fsList=fsdao.queryOnCondition(fsdto);
			if(fsList!=null){
				for(int i=0;i<fsList.size();i++){
					fsdto=fsList.get(i);
					fmdto.setFreightManifestID(fsdto.getFreightManifestID());
					fmdto=fmdao.getDTOByID(fmdto);
					if(fmdto!=null){
						Map m=new HashMap();
						m.put("freightManifestID", fmdto.getFreightManifestID());
						m.put("origin", fmdto.getOriginCity()+"-"+fmdto.getOriginProvince());
						m.put("destination", fmdto.getDestinationCity()+"-"+fmdto.getDestinationProvince());
						m.put("customer", fmdto.getCustomer());
						CargoDTO cdto=fmdao.querySum(fmdto.getFreightManifestID());
						if(cdto!=null){
							sumAmount+=cdto.getAmount();
							sumWeight+=cdto.getWeight();
							sumVolume+=cdto.getVolume();
							m.put("amount", cdto.getAmount());
							m.put("weight", cdto.getWeight());
							m.put("volume", cdto.getVolume());
						}
						else{
							m.put("amount", 0);
							m.put("weight", 0);
							m.put("volume", 0);
						}
						resultMapList.add(m);
					}
				}
			}
			this.data.put("sumAmount", sumAmount);
			this.data.put("sumVolume", sumVolume);
			this.data.put("sumWeight", sumWeight);
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

		
		if(smdto==null||smdto.getShipmentManifestID()==null
				)
		{
			this.message="缺少装车单编号,无法修改！";
			this.success=false;
			return "success";
		}
		try{
			fsdto.setShipmentManifestID(smdto.getShipmentManifestID());
			fsdto.setFreightManifestID(null);
			if(!fsdao.delete(fsdto))
			{
				mysqlTools.rollback();
				this.message="删除当前装车单旧的运输单信息失败！";
				this.success=false;
				return "success";
			}
			if(!smdao.update(smdto))
			{
				mysqlTools.rollback();
				this.message="修改装车单失败!";
				this.success=false;
				return "success";
			}
			
			if(fmIDList!=null){
				for(int i=0;i<this.fmIDList.size();i++)
				{
					fsdto.setFreightManifestID(fmIDList.get(i));
					if(!fsdao.insert(fsdto))
					{
						mysqlTools.rollback();
						this.message="修改装车单失败，增加运输单列表失败！";
						this.success=false;
						return "success";
					}
				}
			}
			mysqlTools.commit();
			this.message="修改装车单成功！";
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

		
		if(this.smIDList==null || this.smIDList.size()==0)
		{
			this.message="缺少装车单编号，无法删除！";
			this.success=false;
			return "success";
		}
		try{
			for(int i=0;i<this.smIDList.size();i++)
			{
				smdto.setShipmentManifestID(smIDList.get(i));
				if(!smdao.delete(smdto)){
					mysqlTools.rollback();
					this.message="删除装车单信息失败！";
					this.success=false;
					return "success";
				}
				fsdto.setShipmentManifestID(smIDList.get(i));
				fsdto.setFreightManifestID(null);
				if(!fsdao.delete(fsdto)){
					mysqlTools.rollback();
					this.message="删除装车单的运输单列表失败！";
					this.success=false;
					return "success";
				}
			}
			mysqlTools.commit();
			this.message="删除装车单信息成功！";
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
	
	public String queryConsigneeCompanies(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------
		ArrayList<String> res=null;
		try{
			//查询
			res=smdao.queryConsigneeCompany();
			if(res==null)
			{
				mysqlTools.rollback();
				this.message="没有记录";
				this.success=false;
				return "success";
			}
			resultMapList=new ArrayList<Map>();
			for(int i=0;i<res.size();i++){
				Map m=null;
				m=new HashMap();
				m.put("consigneeCompany",res.get(i));
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
		if(smdto==null || smdto.getConsigneeCompany()==null)
		{
			this.message="缺少传入参数！";
			this.success=false;
			return "success";
		}
		ArrayList<ShipmentManifestDTO> res=null;
		try{
			//查询
			res=smdao.queryConsignee(smdto.getConsigneeCompany());
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
				m.put("consignee",res.get(i).getConsignee());
				m.put("consigneePhone",res.get(i).getConsigneePhone());
				m.put("consigneeAddress",res.get(i).getConsigneeAddress());
				m.put("originAgent", res.get(i).getOriginAgent());
				m.put("destinationAgent", res.get(i).getDestinationAgent());
				m.put("originAddress", res.get(i).getOriginAddress());
				m.put("originPerson", res.get(i).getOriginPerson());
				m.put("originPhone", res.get(i).getOriginPhone());
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
	
	public String calculateFreightCost(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		
		//判断传入参数合法性
		if(smdto==null ||smdto.getChargeMode()==null ||
				fcdto==null || fcdto.getFreightContractorID()==null)
		{
			this.message="缺少传入参数！";
			this.success=false;
			return "success";
		}
		double cost=0;
		String chargeMode=smdto.getChargeMode();
		try{
			if("直接计费".equals(chargeMode))
			{
				if(frdto==null || frdto.getFreightRouteID()==null){
					this.message="缺少传入参数,缺少路线编号！";
					this.success=false;
					return "success";
				}
				FreightRouteQuoteDTO frqdto=frqdao.getDTOByID(
						fcdto.getFreightContractorID(), frdto.getFreightRouteID());
				if(frqdto==null){
					this.message="计算失败,缺少路线直接报价信息！";
					this.success=false;
					return "success";
				}
				cost=frqdto.getPriceDirectly();
			}
			else if("车辆类型".equals(chargeMode)){
				if(cardto==null || cardto.getCarID()==null){
					this.message="缺少传入参数,缺少车牌号！";
					this.success=false;
					return "success";
				}
				cardto=cardao.getDTOByID(cardto.getCarID());
				if(cardto==null){
					this.message="计算失败,此车牌号没有注册，请先在基础数据中添加！";
					this.success=false;
					return "success";
				}
				CarTypeQuoteDTO ctqdto=ctqdao.getDTOByID(
						fcdto.getFreightContractorID(), frdto.getFreightRouteID(), cardto.getCarTypeID());
				if(ctqdto==null){
					this.message="计算失败,缺少车辆类型报价信息！";
					this.success=false;
					return "success";
				}
				cost=ctqdto.getPrice();
			}
			else if("以数量计".equals(chargeMode)){
				if(frdto==null || frdto.getFreightRouteID()==null){
					this.message="缺少传入参数,缺少路线编号！";
					this.success=false;
					return "success";
				}
				FreightRouteQuoteDTO frqdto=frqdao.getDTOByID(
						fcdto.getFreightContractorID(), frdto.getFreightRouteID());
				if(frqdto==null){
					this.message="计算失败,缺少路线数量报价信息！";
					this.success=false;
					return "success";
				}
				cost=frqdto.getPriceByAmount()*cargodto.getAmount();
			}
			else if("以重量计".equals(chargeMode)){
				if(frdto==null || frdto.getFreightRouteID()==null){
					this.message="缺少传入参数,缺少路线编号！";
					this.success=false;
					return "success";
				}
				FreightRouteQuoteDTO frqdto=frqdao.getDTOByID(
						fcdto.getFreightContractorID(), frdto.getFreightRouteID());
				if(frqdto==null){
					this.message="计算失败,缺少路线重量报价信息！";
					this.success=false;
					return "success";
				}
				cost=frqdto.getPriceByWeight()*cargodto.getWeight();
			}
			else if("以体积计".equals(chargeMode)){
				if(frdto==null || frdto.getFreightRouteID()==null){
					this.message="缺少传入参数,缺少路线编号！";
					this.success=false;
					return "success";
				}
				FreightRouteQuoteDTO frqdto=frqdao.getDTOByID(
						fcdto.getFreightContractorID(), frdto.getFreightRouteID());
				if(frqdto==null){
					this.message="计算失败,缺少路线体积报价信息！";
					this.success=false;
					return "success";
				}
				cost=frqdto.getPriceByVolume()*cargodto.getVolume();
			}
			else{
				this.message="计算运输费用失败,计费方式不存在";
				this.success=false;
				return "success";
			}
			FreightRouteQuoteDTO frqdto=frqdao.getDTOByID(
					fcdto.getFreightContractorID(), frdto.getFreightRouteID());
			if(frqdto==null){
				this.message="计算失败,缺少路线报价信息！";
				this.success=false;
				return "success";
			}
			
			this.data=new HashMap();
			this.data.put("FreightCost", cost);
			this.data.put("deliveryQuote", frqdto.getDeliveryQuote());
			data.put("extraQuote", frqdto.getExtraQuote());
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
	
	public String queryOneFreightManifest(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		
		//判断传入参数合法性
		if(fmdto==null || fmdto.getFreightManifestID()==null)
		{
			this.message="缺少传入参数！";
			this.success=false;
			return "success";
		}
		try{
			//查询
			fmdto=fmdao.getDTOByID(fmdto);
			if(fmdto==null)
			{
				mysqlTools.rollback();
				this.message="所查询的运输单不存在";
				this.success=true;
				return "success";
			}
			CargoDTO cdto=fmdao.querySum(fmdto.getFreightManifestID());
			data=new HashMap();
			data.put("freightManifestID",fmdto.getFreightManifestID() );
			data.put("customer", fmdto.getCustomer());
			data.put("origin", fmdto.getOriginCity()+"-"+fmdto.getOriginProvince());
			data.put("destination", fmdto.getDestinationCity()+"-"+fmdto.getDestinationProvince());
			data.put("amount", cdto==null?0:cdto.getAmount());
			data.put("weight", cdto==null?0:cdto.getWeight());
			data.put("volume", cdto==null?0:cdto.getVolume());
			
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
	public ShipmentManifestDAO getSmdao() {
		return smdao;
	}
	public void setSmdao(ShipmentManifestDAO smdao) {
		this.smdao = smdao;
	}
	public FreightRouteDAO getFrdao() {
		return frdao;
	}
	public void setFrdao(FreightRouteDAO frdao) {
		this.frdao = frdao;
	}
	public FreightContractorDAO getFcdao() {
		return fcdao;
	}
	public void setFcdao(FreightContractorDAO fcdao) {
		this.fcdao = fcdao;
	}
	public CarDAO getCardao() {
		return cardao;
	}
	public void setCardao(CarDAO cardao) {
		this.cardao = cardao;
	}
	public CityDAO getCitydao() {
		return citydao;
	}
	public void setCitydao(CityDAO citydao) {
		this.citydao = citydao;
	}
	public FreightShipmentDAO getFsdao() {
		return fsdao;
	}
	public void setFsdao(FreightShipmentDAO fsdao) {
		this.fsdao = fsdao;
	}
	public FreightManifestDAO getFmdao() {
		return fmdao;
	}
	public void setFmdao(FreightManifestDAO fmdao) {
		this.fmdao = fmdao;
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
	public ShipmentManifestDTO getSmdto() {
		return smdto;
	}
	public void setSmdto(ShipmentManifestDTO smdto) {
		this.smdto = smdto;
	}
	public FreightRouteDTO getFrdto() {
		return frdto;
	}
	public void setFrdto(FreightRouteDTO frdto) {
		this.frdto = frdto;
	}
	public FreightContractorDTO getFcdto() {
		return fcdto;
	}
	public void setFcdto(FreightContractorDTO fcdto) {
		this.fcdto = fcdto;
	}
	public CarDTO getCardto() {
		return cardto;
	}
	public void setCardto(CarDTO cardto) {
		this.cardto = cardto;
	}
	public CityDTO getCitydto() {
		return citydto;
	}
	public void setCitydto(CityDTO citydto) {
		this.citydto = citydto;
	}
	public FreightShipmentDTO getFsdto() {
		return fsdto;
	}
	public void setFsdto(FreightShipmentDTO fsdto) {
		this.fsdto = fsdto;
	}
	public FreightManifestDTO getFmdto() {
		return fmdto;
	}
	public void setFmdto(FreightManifestDTO fmdto) {
		this.fmdto = fmdto;
	}
	public CargoDTO getCargodto() {
		return cargodto;
	}
	public void setCargodto(CargoDTO cargodto) {
		this.cargodto = cargodto;
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
	public ArrayList<Integer> getFmIDList() {
		return fmIDList;
	}
	public void setFmIDList(ArrayList<Integer> fmIDList) {
		this.fmIDList = fmIDList;
	}
	public ArrayList<Integer> getSmIDList() {
		return smIDList;
	}
	public void setSmIDList(ArrayList<Integer> smIDList) {
		this.smIDList = smIDList;
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
	public ShipmentCostDAO getScdao() {
		return scdao;
	}
	public void setScdao(ShipmentCostDAO scdao) {
		this.scdao = scdao;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	
}
