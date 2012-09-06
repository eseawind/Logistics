package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import Logistics.Common.Tools;
import Logistics.DTO.CargoDTO;
import Logistics.DTO.ConsigneeDTO;
import Logistics.DTO.CustomerDTO;
import Logistics.DTO.FreightCostDTO;
import Logistics.DTO.FreightIncomeDTO;
import Logistics.DTO.FreightManifestDTO;
import Logistics.DTO.FreightStateDTO;
import Logistics.DTO.ShipmentCostDTO;

public class ShipmentCostDAO extends BaseDAO {
	
	public boolean delete(Integer smid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(smid==null)
			return true;
		String sql="delete from ShipmentCosts where ShipmentManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, smid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public ShipmentCostDTO getByID(Integer smid)throws Exception{
		//申明对象
		ShipmentCostDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(smid==null)
			return null;
		String sql="select * from ShipmentCosts where ShipmentManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1,smid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new ShipmentCostDTO();
				resdto.setShipmentManifestID(rs.getInt("ShipmentManifestID"));
				resdto.setChargeMode(rs.getString("ChargeMode"));
				resdto.setDateCreated(rs.getDate("DateCreated"));
				resdto.setSumAmount(rs.getInt("SumAmount"));
				resdto.setSumVolume(rs.getDouble("SumVolume"));
				resdto.setSumWeight(rs.getDouble("SumWeight"));
				resdto.setSumValue(rs.getDouble("SumValue"));
				resdto.setFreightCost(rs.getDouble("FreightCost"));
				resdto.setExtraCost(rs.getDouble("ExtraCost"));
				resdto.setFinancialState(rs.getString("FinancialState"));
				resdto.setCostCenter(rs.getString("CostCenter"));
				resdto.setRemarks(rs.getString("Remarks"));
				resdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
				resdto.setUnitQuote(rs.getDouble("UnitQuote"));
				resdto.setOriginCity(rs.getString("originCity"));
				resdto.setOriginProvince(rs.getString("originprovince"));
				resdto.setDestinationCity(rs.getString("destinationcity"));
				resdto.setDestinationProvince(rs.getString("destinationProvince"));
				resdto.setFreightContractor(rs.getString("freightContractor"));
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return resdto;
		
	}

	public boolean insert(ShipmentCostDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" ShipmentCosts(" +
				"ShipmentManifestID," +
				"ChargeMode," +
				"remarks," +
				"FreightCost," +
				"ExtraCost," +
				"LoadUnloadCost,"+
				"UnitQuote,"+
				"DateCreated," +
				"CostCenter,"+
				"SumVolume," +
				"SumWeight," +
				"SumAmount," +
				"SumValue," +
				"FinancialState," +
				"originCity," +
				"originProvince," +
				"destinationcity," +
				"destinationprovince," +
				"freightContractor" +
				") values(" +
				"?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?,?," +
				"?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getShipmentManifestID());
			pstmt.setString(2, dto.getChargeMode());
			pstmt.setString(3, dto.getRemarks());
			pstmt.setDouble(4, dto.getFreightCost());
			pstmt.setDouble(5, dto.getExtraCost());
			pstmt.setDouble(6, dto.getLoadUnloadCost());
			pstmt.setDouble(7, dto.getUnitQuote());
			pstmt.setDate(8, dto.getDateCreated());
			pstmt.setString(9, dto.getCostCenter());
			pstmt.setDouble(10, dto.getSumVolume());
			pstmt.setDouble(11, dto.getSumWeight());
			pstmt.setInt(12, dto.getSumAmount());
			pstmt.setDouble(13, dto.getSumValue());
			pstmt.setString(14, dto.getFinancialState());
			pstmt.setString(15, dto.getOriginCity());
			pstmt.setString(16, dto.getOriginProvince());
			pstmt.setString(17, dto.getDestinationCity());
			pstmt.setString(18, dto.getDestinationProvince());
			pstmt.setString(19, dto.getFreightContractor());
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
	public ArrayList<ShipmentCostDTO> queryOnCondition(
			ShipmentCostDTO dto,Date startDay,Date endDay, int start, int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<ShipmentCostDTO> resList=new ArrayList<ShipmentCostDTO>();
		String sql=" select * from ShipmentCosts " +
				" where  "+!Tools.isVoid(dto.getShipmentManifestID())+" and ShipmentManifestID = ?" +
						"  or " +Tools.isVoid(dto.getShipmentManifestID())+
				" and ("+Tools.isVoid(dto.getFinancialState())+" or FinancialState = ? )" +
				" and ( "+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?) " +
				" and ( "+Tools.isVoid(startDay)+" or DateCreated >= ? ) " +
				" and ( "+Tools.isVoid(endDay)+" or  DateCreated <= ? ) " +
				" and ("+Tools.isVoid(dto.getFreightContractor())+" or freightContractor like ?) " +
				" and ("+Tools.isVoid(dto.getOriginProvince())+" or originProvince = ?)" +
				" and ("+Tools.isVoid(dto.getOriginCity())+" or originCity = ?)" +
				" and ("+Tools.isVoid(dto.getDestinationProvince())+" or destinationProvince = ?)" +
				" and ("+Tools.isVoid(dto.getDestinationCity())+" or destinationCity = ?)" +
						" order by ShipmentManifestID desc" +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			
			pstmt.setString(1, Tools.toString(dto.getShipmentManifestID()));
			pstmt.setString(2, dto.getFinancialState());
			pstmt.setString(3, dto.getCostCenter());
			pstmt.setDate(4, startDay);
			pstmt.setDate(5, endDay);
			pstmt.setString(6, "%"+dto.getFreightContractor()+"%");
			pstmt.setString(7, dto.getOriginProvince());
			pstmt.setString(8, dto.getOriginCity());
			pstmt.setString(9, dto.getDestinationProvince());
			pstmt.setString(10, dto.getDestinationCity());
			pstmt.setInt(11, start);
			pstmt.setInt(12, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					ShipmentCostDTO resdto=new ShipmentCostDTO();
					resdto.setShipmentManifestID(rs.getInt("ShipmentManifestID"));
					resdto.setChargeMode(rs.getString("ChargeMode"));
					resdto.setDateCreated(rs.getDate("DateCreated"));
					resdto.setSumAmount(rs.getInt("SumAmount"));
					resdto.setSumVolume(rs.getDouble("SumVolume"));
					resdto.setSumWeight(rs.getDouble("SumWeight"));
					resdto.setSumValue(rs.getDouble("SumValue"));
					resdto.setFreightCost(rs.getDouble("FreightCost"));
					resdto.setExtraCost(rs.getDouble("ExtraCost"));
					resdto.setFinancialState(rs.getString("FinancialState"));
					resdto.setCostCenter(rs.getString("CostCenter"));
					resdto.setRemarks(rs.getString("Remarks"));
					resdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
					resdto.setUnitQuote(rs.getDouble("UnitQuote"));
					resdto.setOriginCity(rs.getString("originCity"));
					resdto.setOriginProvince(rs.getString("originprovince"));
					resdto.setDestinationCity(rs.getString("destinationcity"));
					resdto.setDestinationProvince(rs.getString("destinationProvince"));
					resdto.setFreightContractor(rs.getString("freightContractor"));
					resList.add(resdto);
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
	
	public int queryQualifiedAmountOnCondition(
			ShipmentCostDTO dto,Date startDay,Date endDay) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		int amount=0;
		String sql=" select count(*) Amount from ShipmentCosts " +
		" where  "+!Tools.isVoid(dto.getShipmentManifestID())+" and ShipmentManifestID = ?" +
		"  or " +Tools.isVoid(dto.getShipmentManifestID())+
		" and ("+Tools.isVoid(dto.getFinancialState())+" or FinancialState = ? )" +
		" and ( "+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?) " +
		" and ( "+Tools.isVoid(startDay)+" or DateCreated >= ? ) " +
		" and ( "+Tools.isVoid(endDay)+" or  DateCreated <= ? ) " +
		" and ("+Tools.isVoid(dto.getFreightContractor())+" or freightContractor like ?) " +
		" and ("+Tools.isVoid(dto.getOriginProvince())+" or originProvince = ?)" +
		" and ("+Tools.isVoid(dto.getOriginCity())+" or originCity = ?)" +
		" and ("+Tools.isVoid(dto.getDestinationProvince())+" or destinationProvince = ?)" +
		" and ("+Tools.isVoid(dto.getDestinationCity())+" or destinationCity = ?)" +
		" order by ShipmentManifestID desc";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getShipmentManifestID()));
			pstmt.setString(2, dto.getFinancialState());
			pstmt.setString(3, dto.getCostCenter());
			pstmt.setDate(4, startDay);
			pstmt.setDate(5, endDay);
			pstmt.setString(6, "%"+dto.getFreightContractor()+"%");
			pstmt.setString(7, dto.getOriginProvince());
			pstmt.setString(8, dto.getOriginCity());
			pstmt.setString(9, dto.getDestinationProvince());
			pstmt.setString(10, dto.getDestinationCity());
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
	public boolean updateCosts(ShipmentCostDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getShipmentManifestID()==null)
			return false;
		String sql="update ShipmentCosts set " +
				" FreightCost=?," +
				"ExtraCost=?, " +
				"LoadUnloadCost=?,"+
				"ChargeMode=?,"+
				"UnitQuote=?,"+
				"CostCenter=?,"+
				"Remarks=? where ShipmentManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDouble(1, dto.getFreightCost());
			pstmt.setDouble(2, dto.getExtraCost());
			pstmt.setDouble(3, dto.getLoadUnloadCost());
			pstmt.setString(4, dto.getChargeMode());
			pstmt.setDouble(5, dto.getUnitQuote());
			pstmt.setString(6, dto.getCostCenter());
			pstmt.setString(7, dto.getRemarks());
			pstmt.setInt(8, dto.getShipmentManifestID());
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
	public boolean updateFinancialState(ShipmentCostDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getShipmentManifestID()==null || dto.getFinancialState()==null)
			return false;
		String sql="update ShipmentCosts set FinancialState=?  where ShipmentManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getFinancialState());
			pstmt.setInt(2, dto.getShipmentManifestID());
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
	
}
