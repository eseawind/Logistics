package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import Logistics.Common.Tools;
import Logistics.DTO.CargoDTO;
import Logistics.DTO.ConsigneeDTO;
import Logistics.DTO.CustomerDTO;
import Logistics.DTO.FreightManifestDTO;
import Logistics.DTO.ShipmentManifestDTO;

public class ShipmentManifestDAO extends BaseDAO{

	public boolean delete(ShipmentManifestDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(null==dto)
			return false;
		if(null==dto.getShipmentManifestID())
			return false;
		String sql="delete from ShipmentManifests where ShipmentManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getShipmentManifestID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ShipmentManifestDTO getDTOByID(ShipmentManifestDTO dto)
			throws Exception {
		//申明对象
		ShipmentManifestDTO smdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(null==dto)
			return null;
		if(null==dto.getShipmentManifestID())
			return null;
		String sql="select * from ShipmentManifests where ShipmentManifests.ShipmentManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getShipmentManifestID());
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				smdto= new ShipmentManifestDTO();
				smdto.setShipmentManifestID(rs.getInt("ShipmentManifestID"));
				smdto.setOriginProvince(rs.getString("OriginProvince"));
				smdto.setOriginCity(rs.getString("OriginCity"));
				smdto.setDestinationProvince(rs.getString("DestinationProvince"));
				smdto.setDestinationCity(rs.getString("DestinationCity"));
				smdto.setContractor(rs.getString("Contractor"));
				smdto.setCarID(rs.getString("CarID"));
				smdto.setCarType(rs.getString("CarType"));
				smdto.setDriverName(rs.getString("DriverName"));
				smdto.setDriverPhone(rs.getString("DriverPhone"));
				smdto.setConsigneeCompany(rs.getString("ConsigneeCompany"));
				smdto.setConsignee(rs.getString("Consignee"));
				smdto.setConsigneePhone(rs.getString("ConsigneePhone"));
				smdto.setConsigneeAddress(rs.getString("ConsigneeAddress"));
				smdto.setChargeMode(rs.getString("ChargeMode"));
				smdto.setRemarks(rs.getString("Remarks"));
				smdto.setFreightCost(rs.getDouble("FreightCost"));
				smdto.setOtherCost(rs.getDouble("OtherCost"));
				smdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
				smdto.setOriginAgent(rs.getString("OriginAgent"));
				smdto.setDestinationAgent(rs.getString("DestinationAgent"));
				smdto.setUnitQuote(rs.getDouble("UnitQuote"));
				smdto.setAnnouncements(rs.getString("Announcements"));
				smdto.setDateCreated(rs.getDate("DateCreated"));
				smdto.setOriginAddress(rs.getString("originAddress"));
				smdto.setOriginPerson(rs.getString("originPerson"));
				smdto.setOriginPhone(rs.getString("originPhone"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return smdto;
	}

	public boolean insert(ShipmentManifestDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" ShipmentManifests(ShipmentManifestID,OriginProvince,OriginCity,DestinationProvince,DestinationCity" +
				",Contractor,CarID,CarType,DriverName,DriverPhone" +
				",ConsigneeCompany,Consignee,ConsigneePhone,ConsigneeAddress,ChargeMode" +
				",Remarks,FreightCost,OtherCost,LoadUnloadCost,OriginAgent" +
				",DestinationAgent,UnitQuote,Announcements,DateCreated,originAddress,originPerson,originPhone)" +
				" values(null,?,?,?,?" +
				",?,?,?,?,?" +
				",?,?,?,?,?" +
				",?,?,?,?,?" +
				",?,?,?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getOriginProvince());
			pstmt.setString(2, dto.getOriginCity());
			pstmt.setString(3, dto.getDestinationProvince());
			pstmt.setString(4, dto.getDestinationCity());
			pstmt.setString(5, dto.getContractor());
			pstmt.setString(6, dto.getCarID());
			pstmt.setString(7, dto.getCarType());
			pstmt.setString(8, dto.getDriverName());
			pstmt.setString(9, dto.getDriverPhone());
			pstmt.setString(10, dto.getConsigneeCompany());
			pstmt.setString(11, dto.getConsignee());
			pstmt.setString(12, dto.getConsigneePhone());
			pstmt.setString(13, dto.getConsigneeAddress());
			pstmt.setString(14, dto.getChargeMode());
			pstmt.setString(15, dto.getRemarks());
			pstmt.setDouble(16, dto.getFreightCost());
			pstmt.setDouble(17, dto.getOtherCost());
			pstmt.setDouble(18, dto.getLoadUnloadCost());
			pstmt.setString(19, dto.getOriginAgent());
			pstmt.setString(20, dto.getDestinationAgent());
			pstmt.setDouble(21, dto.getUnitQuote());
			pstmt.setString(22, dto.getAnnouncements());
			pstmt.setDate(23, dto.getDateCreated());
			pstmt.setString(24, dto.getOriginAddress());
			pstmt.setString(25, dto.getOriginPerson());
			pstmt.setString(26, dto.getOriginPhone());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally{
			close();
		}
		return true;
	}

	public ArrayList<ShipmentManifestDTO> queryAll() throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<ShipmentManifestDTO> resList=new ArrayList<ShipmentManifestDTO>();
		String sql="select * from ShipmentManifests";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					ShipmentManifestDTO smdto=new ShipmentManifestDTO();
					smdto.setShipmentManifestID(rs.getInt("ShipmentManifestID"));
					smdto.setOriginProvince(rs.getString("OriginProvince"));
					smdto.setOriginCity(rs.getString("OriginCity"));
					smdto.setDestinationProvince(rs.getString("DestinationProvince"));
					smdto.setDestinationCity(rs.getString("DestinationCity"));
					smdto.setContractor(rs.getString("Contractor"));
					smdto.setCarID(rs.getString("CarID"));
					smdto.setCarType(rs.getString("CarType"));
					smdto.setDriverName(rs.getString("DriverName"));
					smdto.setDriverPhone(rs.getString("DriverPhone"));
					smdto.setConsigneeCompany(rs.getString("ConsigneeCompany"));
					smdto.setConsignee(rs.getString("Consignee"));
					smdto.setConsigneePhone(rs.getString("ConsigneePhone"));
					smdto.setConsigneeAddress(rs.getString("ConsigneeAddress"));
					smdto.setChargeMode(rs.getString("ChargeMode"));
					smdto.setRemarks(rs.getString("Remarks"));
					smdto.setFreightCost(rs.getDouble("FreightCost"));
					smdto.setOtherCost(rs.getDouble("OtherCost"));
					smdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
					smdto.setOriginAgent(rs.getString("OriginAgent"));
					smdto.setDestinationAgent(rs.getString("DestinationAgent"));
					smdto.setUnitQuote(rs.getDouble("UnitQuote"));
					smdto.setAnnouncements(rs.getString("Announcements"));
					smdto.setDateCreated(rs.getDate("DateCreated"));
					resList.add(smdto);
				}
			}
			return resList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	public ArrayList<ShipmentManifestDTO> queryOnCondition2(
			ShipmentManifestDTO dto,ShipmentManifestDTO dto2, int start, int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<ShipmentManifestDTO> resList=new ArrayList<ShipmentManifestDTO>();
		String sql=" select * from ShipmentManifests " +
				" where "+!Tools.isVoid(dto.getShipmentManifestID())+" and ShipmentManifestID = ? " +
				" or " +Tools.isVoid(dto.getShipmentManifestID())+
				" and ("+Tools.isVoid(dto.getOriginProvince())+" or  OriginProvince = ?)" +
				" and ("+Tools.isVoid(dto.getOriginCity())+" or OriginCity = ?)" +
				" and ("+Tools.isVoid(dto.getDestinationProvince())+" or DestinationProvince = ?) " +
				" and ("+Tools.isVoid(dto.getDestinationCity())+" or DestinationCity = ?)" +
				" and ("+Tools.isVoid(dto.getContractor())+" or Contractor like ?) " +
				" and ("+Tools.isVoid(dto.getDateCreated())+" or DateCreated >= ?) " +
				" and ("+Tools.isVoid(dto2.getDateCreated())+" or DateCreated <= ?) " +
				" order by ShipmentManifestID desc" +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getShipmentManifestID()));
			pstmt.setString(2, dto.getOriginProvince());
			pstmt.setString(3, dto.getOriginCity());
			pstmt.setString(4, dto.getDestinationProvince());
			pstmt.setString(5, dto.getDestinationCity());
			pstmt.setString(6, "%"+dto.getContractor()+"%");
			pstmt.setDate(7, dto.getDateCreated());
			pstmt.setDate(8, dto2.getDateCreated());
			pstmt.setInt(9, start);
			pstmt.setInt(10, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					ShipmentManifestDTO smdto=new ShipmentManifestDTO();
					smdto.setShipmentManifestID(rs.getInt("ShipmentManifestID"));
					smdto.setOriginProvince(rs.getString("OriginProvince"));
					smdto.setOriginCity(rs.getString("OriginCity"));
					smdto.setDestinationProvince(rs.getString("DestinationProvince"));
					smdto.setDestinationCity(rs.getString("DestinationCity"));
					smdto.setContractor(rs.getString("Contractor"));
					smdto.setCarID(rs.getString("CarID"));
					smdto.setCarType(rs.getString("CarType"));
					smdto.setDriverName(rs.getString("DriverName"));
					smdto.setDriverPhone(rs.getString("DriverPhone"));
					smdto.setConsigneeCompany(rs.getString("ConsigneeCompany"));
					smdto.setConsignee(rs.getString("Consignee"));
					smdto.setConsigneePhone(rs.getString("ConsigneePhone"));
					smdto.setConsigneeAddress(rs.getString("ConsigneeAddress"));
					smdto.setChargeMode(rs.getString("ChargeMode"));
					smdto.setRemarks(rs.getString("Remarks"));
					smdto.setFreightCost(rs.getDouble("FreightCost"));
					smdto.setOtherCost(rs.getDouble("OtherCost"));
					smdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
					smdto.setOriginAgent(rs.getString("OriginAgent"));
					smdto.setDestinationAgent(rs.getString("DestinationAgent"));
					smdto.setUnitQuote(rs.getDouble("UnitQuote"));
					smdto.setAnnouncements(rs.getString("Announcements"));
					smdto.setDateCreated(rs.getDate("DateCreated"));
					smdto.setOriginAddress(rs.getString("originAddress"));
					smdto.setOriginPerson(rs.getString("originPerson"));
					smdto.setOriginPhone(rs.getString("originPhone"));
					resList.add(smdto);
				}
			}
			return resList;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		
	}



	public int queryQualifiedAmount2(
			ShipmentManifestDTO dto,ShipmentManifestDTO dto2) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		int amount=0;
		String sql=" select count(*) Amount from ShipmentManifests " +
		" where "+!Tools.isVoid(dto.getShipmentManifestID())+" and ShipmentManifestID = ? " +
		" or " +Tools.isVoid(dto.getShipmentManifestID())+
		" and ("+Tools.isVoid(dto.getOriginProvince())+" or  OriginProvince = ?)" +
		" and ("+Tools.isVoid(dto.getOriginCity())+" or OriginCity = ?)" +
		" and ("+Tools.isVoid(dto.getDestinationProvince())+" or DestinationProvince = ?) " +
		" and ("+Tools.isVoid(dto.getDestinationCity())+" or DestinationCity = ?)" +
		" and ("+Tools.isVoid(dto.getContractor())+" or Contractor like ?) " +
		" and ("+Tools.isVoid(dto.getDateCreated())+" or DateCreated >= ?) " +
		" and ("+Tools.isVoid(dto2.getDateCreated())+" or DateCreated <= ?) " +
		" order by ShipmentManifestID desc";
		
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getShipmentManifestID()));
			pstmt.setString(2, dto.getOriginProvince());
			pstmt.setString(3, dto.getOriginCity());
			pstmt.setString(4, dto.getDestinationProvince());
			pstmt.setString(5, dto.getDestinationCity());
			pstmt.setString(6, "%"+dto.getContractor()+"%");
			pstmt.setDate(7, dto.getDateCreated());
			pstmt.setDate(8, dto2.getDateCreated());
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				amount=rs.getInt("Amount");
			}
			return amount;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			close();
		}
	}

	public boolean update(ShipmentManifestDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null)
			return false;
		String sql="update ShipmentManifests set " +
				" OriginProvince=?,OriginCity=?,DestinationProvince=?,DestinationCity=?,Contractor=?," +
				"CarID=?,CarType=?,DriverName=?,DriverPhone=?,ConsigneeCompany=?," +
				"Consignee=?,ConsigneePhone=?,ConsigneeAddress=?,ChargeMode=?,Remarks=?," +
				"FreightCost=?,OtherCost=?,LoadUnloadCost=?,OriginAgent=?,DestinationAgent=?," +
				"UnitQuote=?,Announcements=?,originAddress=?,originPerson=?,originPhone=? " +
				" where ShipmentManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getOriginProvince());
			pstmt.setString(2, dto.getOriginCity());
			pstmt.setString(3, dto.getDestinationProvince());
			pstmt.setString(4, dto.getDestinationCity());
			pstmt.setString(5, dto.getContractor());
			pstmt.setString(6, dto.getCarID());
			pstmt.setString(7, dto.getCarType());
			pstmt.setString(8, dto.getDriverName());
			pstmt.setString(9, dto.getDriverPhone());
			pstmt.setString(10, dto.getConsigneeCompany());
			pstmt.setString(11, dto.getConsignee());
			pstmt.setString(12, dto.getConsigneePhone());
			pstmt.setString(13, dto.getConsigneeAddress());
			pstmt.setString(14, dto.getChargeMode());
			pstmt.setString(15, dto.getRemarks());
			pstmt.setDouble(16, dto.getFreightCost());
			pstmt.setDouble(17, dto.getOtherCost());
			pstmt.setDouble(18, dto.getLoadUnloadCost());
			pstmt.setString(19, dto.getOriginAgent());
			pstmt.setString(20, dto.getDestinationAgent());
			pstmt.setDouble(21, dto.getUnitQuote());
			pstmt.setString(22, dto.getAnnouncements());
			pstmt.setString(23, dto.getOriginAddress());
			pstmt.setString(24, dto.getOriginPerson());
			pstmt.setString(25,dto.getOriginPhone());
			pstmt.setInt(26, dto.getShipmentManifestID());
			
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally{
			close();
		}
		
	}
	
	public CargoDTO querySum(Integer shipmentManifestID) throws Exception{
		CargoDTO cdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(shipmentManifestID==null)
			return null;
		String sql="select sum(Amount) SumAmount,sum(Weight) SumWeight," +
				"sum(Volume) SumVolume,sum(value) SumValue " +
				" from Cargos where FreightManifestID in " +
				" (select FreightManifestID from FreightShipment where ShipmentManifestID=?)";
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, shipmentManifestID);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				cdto= new CargoDTO();
				cdto.setAmount(rs.getInt("SumAmount"));
				cdto.setValue(rs.getDouble("SumValue"));
				cdto.setVolume(rs.getDouble("SumVolume"));
				cdto.setWeight(rs.getDouble("SumWeight"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return cdto;
	}
	public ArrayList<String> queryConsigneeCompany() throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<String> resList=new ArrayList<String>();
		String sql="select ConsigneeCompany" +
				" from ShipmentManifests group by ConsigneeCompany";
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					resList.add(rs.getString("ConsigneeCompany"));
				}
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return resList;
	}
	
	public ArrayList<ShipmentManifestDTO> queryConsignee(String consigneeCompany) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<ShipmentManifestDTO> resList=new ArrayList<ShipmentManifestDTO>();
		String sql="select * " +
				" from (select * from ShipmentManifests where ConsigneeCompany=? order by DateCreated desc) sm1 " +
				" group by Consignee ";
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, consigneeCompany);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				
				while(rs.next()){
					ShipmentManifestDTO smdto=new ShipmentManifestDTO();
					smdto.setConsigneeCompany(consigneeCompany);
					smdto.setConsignee(rs.getString("Consignee"));
					smdto.setConsigneePhone(rs.getString("ConsigneePhone"));
					smdto.setConsigneeAddress(rs.getString("ConsigneeAddress"));
					smdto.setOriginAgent(rs.getString("originAgent"));
					smdto.setDestinationAgent(rs.getString("destinationAgent"));
					smdto.setOriginPerson(rs.getString("originPerson"));
					smdto.setOriginAddress(rs.getString("originAddress"));
					smdto.setOriginPhone(rs.getString("originPhone"));
					resList.add(smdto);
				}
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return resList;
	}
	
	
}
