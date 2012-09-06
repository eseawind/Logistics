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
import Logistics.DTO.StockinManifestDTO;

public class StockinManifestDAO extends BaseDAO {
	public boolean delete(Integer simid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(simid==null)
			return true;
		String sql="delete from StockinManifests where StockinManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, simid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(ArrayList<Integer> simidList) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(simidList==null)
			return true;
		String sql="delete from StockinManifests where StockinManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(int i=0;i<simidList.size();i++){
				pstmt.setInt(1, simidList.get(i));
				pstmt.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public StockinManifestDTO getByID(Integer simid)throws Exception{
		//申明对象
		StockinManifestDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(simid==null)
			return null;
		String sql="select * from StockinManifests where StockinManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1,simid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new StockinManifestDTO();
				resdto.setStockinManifestID(rs.getInt("StockinManifestID"));
				resdto.setWarehouseID(rs.getString("WarehouseID"));
				resdto.setSumAmount(rs.getInt("SumAmount"));
				resdto.setCustomer(rs.getString("Customer"));
				resdto.setWarehouseName(rs.getString("WarehouseName"));
				resdto.setDeliveryPerson(rs.getString("DeliveryPerson"));
				resdto.setRemarks(rs.getString("Remarks"));
				resdto.setChargeMode(rs.getString("ChargeMode"));
				resdto.setCostCenter(rs.getString("CostCenter"));
				resdto.setSellCenter(rs.getString("SellCenter"));
				resdto.setDateCreated(rs.getDate("DateCreated"));
				resdto.setDateStockin(rs.getDate("DateStockin"));
				resdto.setUnitPrice(rs.getDouble("UnitPrice"));
				resdto.setStockinFee(rs.getDouble("StockinFee"));
				resdto.setSumVolume(rs.getDouble("SumVolume"));
				resdto.setSumWeight(rs.getDouble("SumWeight"));
				resdto.setApprovalState(rs.getString("ApprovalState"));
				resdto.setAuditState(rs.getString("AuditState"));
				resdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
				resdto.setDeliveryPhone("DeliveryPhone");
				resdto.setTakingAddress(rs.getString("TakingAddress"));
				resdto.setCustomerID(rs.getInt("CustomerID"));
				resdto.setCustomerNumber(rs.getString("CustomerNumber"));
				resdto.setOriginAgent(rs.getString("originAgent"));
				resdto.setOriginAddress(rs.getString("originAddress"));
				resdto.setOriginPerson(rs.getString("originPerson"));
				resdto.setOriginPhone(rs.getString("originPhone"));
				
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
	
	public boolean insert(StockinManifestDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		if(dto.getDateStockin()==null){
			return false;
		}
		String sql="insert into " +
				" StockinManifests(" +
				"StockinManifestID," +
				"WarehouseID," +
				"SumAmount," +
				"Customer," +
				"WarehouseName," +
				"DeliveryPerson," +
				"Remarks," +
				"ChargeMode," +
				"CostCenter," +
				"SellCenter," +
				"DateCreated," +
				"DateStockin," +
				"UnitPrice," +
				"StockinFee," +
				"SumVolume," +
				"SumWeight," +
				"LoadUnloadCost," +
				"ApprovalState," +
				"AuditState," +
				"DeliveryPhone," +
				"TakingAddress," +
				"CustomerID,customerNumber,originAgent,originAddress,originPerson,originPhone)" +
				" values(null,?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,'未批准','未审核',?,?,?,?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getWarehouseID());
			pstmt.setInt(2, dto.getSumAmount());
			pstmt.setString(3, dto.getCustomer());
			pstmt.setString(4, dto.getWarehouseName());
			pstmt.setString(5, dto.getDeliveryPerson());
			pstmt.setString(6, dto.getRemarks());
			pstmt.setString(7, dto.getChargeMode());
			pstmt.setString(8, dto.getCostCenter());
			pstmt.setString(9, dto.getSellCenter());
			pstmt.setDate(10, dto.getDateCreated());
			pstmt.setDate(11, dto.getDateStockin());
			pstmt.setDouble(12, dto.getUnitPrice());
			pstmt.setDouble(13, dto.getStockinFee());
			pstmt.setDouble(14, dto.getSumVolume());
			pstmt.setDouble(15, dto.getSumWeight());
			pstmt.setDouble(16, dto.getLoadUnloadCost());
			pstmt.setString(17, dto.getDeliveryPhone());
			pstmt.setString(18, dto.getTakingAddress());
			pstmt.setInt(19, dto.getCustomerID());
			pstmt.setString(20,dto.getCustomerNumber());
			pstmt.setString(21, dto.getOriginAgent());
			pstmt.setString(22, dto.getOriginAddress());
			pstmt.setString(23, dto.getOriginPerson());
			pstmt.setString(24, dto.getOriginPhone());
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
	
	
	public ArrayList<StockinManifestDTO> queryOnCondition(
			StockinManifestDTO dto,Date startDay,Date endDay, int start, int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(dto==null)
		{
			dto=new StockinManifestDTO();
		}
		ArrayList<StockinManifestDTO> resList=new ArrayList<StockinManifestDTO>();
		String sql=" select * from StockinManifests " +
		" where "+!Tools.isVoid(dto.getStockinManifestID())+" and StockinManifestID = ?" +
		" or " +Tools.isVoid(dto.getStockinManifestID())+
		" and ("+Tools.isVoid(dto.getCustomer())+" or Customer like ? )" +
		" and ("+Tools.isVoid(dto.getWarehouseName())+" or WarehouseName = ? )" +
		" and ("+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
		" and ("+Tools.isVoid(dto.getSellCenter())+" or SellCenter = ?)" +
		" and ("+Tools.isVoid(startDay)+" or DateStockin >= ? ) " +
		" and ("+Tools.isVoid(endDay)+" or DateStockin <= ?)" +
		" and ("+Tools.isVoid(dto.getApprovalState())+" or ApprovalState = ?)" +
		" and ("+Tools.isVoid(dto.getAuditState())+" or AuditState = ?)" +
		" order by StockinManifestID desc"+
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getStockinManifestID()));
			pstmt.setString(2, "%"+dto.getCustomer()+"%");
			pstmt.setString(3, dto.getWarehouseName());
			pstmt.setString(4, dto.getCostCenter());
			pstmt.setString(5, dto.getSellCenter());
			pstmt.setDate(6, startDay);
			pstmt.setDate(7, endDay);
			pstmt.setString(8, dto.getApprovalState());
			pstmt.setString(9, dto.getAuditState());
			pstmt.setInt(10, start);
			pstmt.setInt(11, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					StockinManifestDTO resdto=new StockinManifestDTO();
					resdto.setStockinManifestID(rs.getInt("StockinManifestID"));
					resdto.setWarehouseID(rs.getString("WarehouseID"));
					resdto.setSumAmount(rs.getInt("SumAmount"));
					resdto.setCustomer(rs.getString("Customer"));
					resdto.setWarehouseName(rs.getString("WarehouseName"));
					resdto.setDeliveryPerson(rs.getString("DeliveryPerson"));
					resdto.setRemarks(rs.getString("Remarks"));
					resdto.setChargeMode(rs.getString("ChargeMode"));
					resdto.setCostCenter(rs.getString("CostCenter"));
					resdto.setSellCenter(rs.getString("SellCenter"));
					resdto.setDateCreated(rs.getDate("DateCreated"));
					resdto.setDateStockin(rs.getDate("DateStockin"));
					resdto.setUnitPrice(rs.getDouble("UnitPrice"));
					resdto.setStockinFee(rs.getDouble("StockinFee"));
					resdto.setSumVolume(rs.getDouble("SumVolume"));
					resdto.setSumWeight(rs.getDouble("SumWeight"));
					resdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
					resdto.setApprovalState(rs.getString("ApprovalState"));
					resdto.setAuditState(rs.getString("AuditState"));
					resList.add(resdto);
				}
				return resList;
			}
			return null;
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
			StockinManifestDTO dto,Date startDay,Date endDay) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from StockinManifests " +
		" where "+!Tools.isVoid(dto.getStockinManifestID())+" and StockinManifestID = ?" +
		" or " +Tools.isVoid(dto.getStockinManifestID())+
		" and ("+Tools.isVoid(dto.getCustomer())+" or Customer like ? )" +
		" and ("+Tools.isVoid(dto.getWarehouseName())+" or WarehouseName = ? )" +
		" and ("+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
		" and ("+Tools.isVoid(dto.getSellCenter())+" or SellCenter = ?)" +
		" and ("+Tools.isVoid(startDay)+" or DateStockin >= ? ) " +
		" and ("+Tools.isVoid(endDay)+" or DateStockin <= ?)" +
		" and ("+Tools.isVoid(dto.getApprovalState())+" or ApprovalState = ?)" +
		" and ("+Tools.isVoid(dto.getAuditState())+" or AuditState = ?) " +
		" order by StockinManifestID desc";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getStockinManifestID()));
			pstmt.setString(2, "%"+dto.getCustomer()+"%");
			pstmt.setString(3, dto.getWarehouseName());
			pstmt.setString(4, dto.getCostCenter());
			pstmt.setString(5, dto.getSellCenter());
			pstmt.setDate(6, startDay);
			pstmt.setDate(7, endDay);
			pstmt.setString(8, dto.getApprovalState());
			pstmt.setString(9, dto.getAuditState());
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
	public boolean update(StockinManifestDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getStockinManifestID()==null)
			return false;
		String sql="update StockinManifests set " +
				"WarehouseID=?," +
				"SumAmount=?," +
				"Customer=?," +
				"WarehouseName=?," +
				"DeliveryPerson=?," +
				"Remarks=?," +
				"ChargeMode=?," +
				"CostCenter=?," +
				"SellCenter=?," +
				"DateStockin=?," +
				"UnitPrice=?," +
				"StockinFee=?," +
				"SumVolume=?," +
				"SumWeight=?," +
				"LoadUnloadCost=?," +
				"DeliveryPhone=?," +
				"TakingAddress=?," +
				"CustomerID=?," +
				"customerNumber=?," +
				"originAgent=?," +
				"originAddress=?," +
				"originPerson=?," +
				"originPhone=? where StockinManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getWarehouseID());
			pstmt.setInt(2, dto.getSumAmount());
			pstmt.setString(3, dto.getCustomer());
			pstmt.setString(4, dto.getWarehouseName());
			pstmt.setString(5, dto.getDeliveryPerson());
			pstmt.setString(6, dto.getRemarks());
			pstmt.setString(7, dto.getChargeMode());
			pstmt.setString(8, dto.getCostCenter());
			pstmt.setString(9, dto.getSellCenter());
			pstmt.setDate(10, dto.getDateStockin());
			pstmt.setDouble(11, dto.getUnitPrice());
			pstmt.setDouble(12, dto.getStockinFee());
			pstmt.setDouble(13, dto.getSumVolume());
			pstmt.setDouble(14, dto.getSumWeight());
			pstmt.setDouble(15, dto.getLoadUnloadCost());
			pstmt.setString(16, dto.getDeliveryPhone());
			pstmt.setString(17, dto.getTakingAddress());
			pstmt.setInt(18, dto.getCustomerID());
			pstmt.setString(19,dto.getCustomerNumber());
			pstmt.setString(20,dto.getOriginAgent());
			pstmt.setString(21,dto.getOriginAddress());
			pstmt.setString(22,dto.getOriginPerson());
			pstmt.setString(23,dto.getOriginPhone());
			pstmt.setInt(24, dto.getStockinManifestID());
			
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
	public boolean approve(Integer simid) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(simid==null)
			return false;
		String sql="update StockinManifests set " +
				"ApprovalState='已批准' where StockinManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, simid);
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
	public boolean audit(Integer simid) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(simid==null)
			return false;
		String sql="update StockinManifests set " +
				"AuditState='已审核' where StockinManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, simid);
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
	public boolean audit(ArrayList<Integer> smIDList) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(smIDList==null)
			return true;
		String sql="update StockinManifests set " +
				"AuditState='已审核' where StockinManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(Integer simid:smIDList){
				pstmt.setInt(1, simid);
				pstmt.executeUpdate();
			}
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
