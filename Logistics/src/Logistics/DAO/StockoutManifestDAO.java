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
import Logistics.DTO.StockoutManifestDTO;

public class StockoutManifestDAO extends BaseDAO {
	public boolean delete(Integer somid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(somid==null)
			return true;
		String sql="delete from StockoutManifests where StockoutManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, somid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(ArrayList<Integer> somidList) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(somidList==null)
			return true;
		String sql="delete from StockoutManifests where StockoutManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(int i=0;i<somidList.size();i++){
				pstmt.setInt(1, somidList.get(i));
				pstmt.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public StockoutManifestDTO getByID(Integer smid)throws Exception{
		//申明对象
		StockoutManifestDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(smid==null)
			return null;
		String sql="select * from StockoutManifests where StockoutManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1,smid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new StockoutManifestDTO();
				resdto.setStockoutManifestID(rs.getInt("StockoutManifestID"));
				resdto.setWarehouseID(rs.getString("WarehouseID"));
				resdto.setSumAmount(rs.getInt("SumAmount"));
				resdto.setCustomer(rs.getString("Customer"));
				resdto.setWarehouseName(rs.getString("WarehouseName"));
				resdto.setRemarks(rs.getString("Remarks"));
				resdto.setChargeMode(rs.getString("ChargeMode"));
				resdto.setCostCenter(rs.getString("CostCenter"));
				resdto.setSellCenter(rs.getString("SellCenter"));
				resdto.setDateCreated(rs.getDate("DateCreated"));
				resdto.setDateStockout(rs.getDate("DateStockout"));
				resdto.setUnitPrice(rs.getDouble("UnitPrice"));
				resdto.setStockoutFee(rs.getDouble("StockoutFee"));
				resdto.setSumVolume(rs.getDouble("SumVolume"));
				resdto.setSumWeight(rs.getDouble("SumWeight"));
				resdto.setApprovalState(rs.getString("ApprovalState"));
				resdto.setAuditState(rs.getString("AuditState"));
				resdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
				resdto.setConsignee(rs.getString("Consignee"));
				resdto.setConsigneeCompany(rs.getString("ConsigneeCompany"));
				resdto.setConsigneePhone(rs.getString("ConsigneePhone"));
				resdto.setConsigneeAddress(rs.getString("ConsigneeAddress"));
				resdto.setCustomerID(rs.getInt("CustomerID"));
				
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
	
	public boolean insert(StockoutManifestDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		if(dto.getDateStockout()==null){
			return false;
		}
		String sql="insert into " +
				" StockoutManifests(" +
				"StockoutManifestID," +
				"WarehouseID," +
				"SumAmount," +
				"Customer," +
				"WarehouseName," +
				"Remarks," +
				"ChargeMode," +
				"CostCenter," +
				"SellCenter," +
				"DateCreated," +
				"DateStockout," +
				"UnitPrice," +
				"StockoutFee," +
				"SumVolume," +
				"SumWeight," +
				"LoadUnloadCost," +
				"ApprovalState," +
				"AuditState," +
				"Consignee," +
				"ConsigneeCompany," +
				"ConsigneePhone," +
				"ConsigneeAddress," +
				"CustomerID)" +
				" values(null,?,?,?,?,?," +
				"?,?,?,?," +
				"?,?,?,?,?," +
				"?,'未批准','未审核',?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getWarehouseID());
			pstmt.setInt(2, dto.getSumAmount());
			pstmt.setString(3, dto.getCustomer());
			pstmt.setString(4, dto.getWarehouseName());
			pstmt.setString(5, dto.getRemarks());
			pstmt.setString(6, dto.getChargeMode());
			pstmt.setString(7, dto.getCostCenter());
			pstmt.setString(8, dto.getSellCenter());
			pstmt.setDate(9, dto.getDateCreated());
			pstmt.setDate(10, dto.getDateStockout());
			pstmt.setDouble(11, dto.getUnitPrice());
			pstmt.setDouble(12, dto.getStockoutFee());
			pstmt.setDouble(13, dto.getSumVolume());
			pstmt.setDouble(14, dto.getSumWeight());
			pstmt.setDouble(15, dto.getLoadUnloadCost());
			pstmt.setString(16, dto.getConsignee());
			pstmt.setString(17, dto.getConsigneeCompany());
			pstmt.setString(18, dto.getConsigneePhone());
			pstmt.setString(19, dto.getConsigneeAddress());
			pstmt.setInt(20, dto.getCustomerID());
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
	
	
	public ArrayList<StockoutManifestDTO> queryOnCondition(
			StockoutManifestDTO dto,Date startDay,Date endDay, int start, int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(dto==null)
		{
			dto=new StockoutManifestDTO();
		}
		ArrayList<StockoutManifestDTO> resList=new ArrayList<StockoutManifestDTO>();
		String sql=" select * from StockoutManifests " +
				" where "+!Tools.isVoid(dto.getStockoutManifestID())+" and StockoutManifestID = ?" +
		" or " +Tools.isVoid(dto.getStockoutManifestID())+
		" and ("+Tools.isVoid(dto.getCustomer())+" or Customer like ? )" +
		" and ("+Tools.isVoid(dto.getWarehouseName())+" or WarehouseName = ? )" +
		" and ("+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
		" and ("+Tools.isVoid(dto.getSellCenter())+" or SellCenter = ?)" +
		" and ("+Tools.isVoid(startDay)+" or DateStockout >= ? ) " +
		" and ("+Tools.isVoid(endDay)+" or DateStockout <= ?)" +
		" and ("+Tools.isVoid(dto.getApprovalState())+" or ApprovalState = ?)" +
		" and ("+Tools.isVoid(dto.getAuditState())+" or AuditState = ?) " +
		" order by StockoutManifestID desc"+
		" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getStockoutManifestID()));
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
					StockoutManifestDTO resdto=new StockoutManifestDTO();
					resdto.setStockoutManifestID(rs.getInt("StockoutManifestID"));
					resdto.setWarehouseID(rs.getString("WarehouseID"));
					resdto.setSumAmount(rs.getInt("SumAmount"));
					resdto.setCustomer(rs.getString("Customer"));
					resdto.setWarehouseName(rs.getString("WarehouseName"));
					resdto.setRemarks(rs.getString("Remarks"));
					resdto.setChargeMode(rs.getString("ChargeMode"));
					resdto.setCostCenter(rs.getString("CostCenter"));
					resdto.setSellCenter(rs.getString("SellCenter"));
					resdto.setDateCreated(rs.getDate("DateCreated"));
					resdto.setDateStockout(rs.getDate("DateStockout"));
					resdto.setUnitPrice(rs.getDouble("UnitPrice"));
					resdto.setStockoutFee(rs.getDouble("StockoutFee"));
					resdto.setSumVolume(rs.getDouble("SumVolume"));
					resdto.setSumWeight(rs.getDouble("SumWeight"));
					resdto.setApprovalState(rs.getString("ApprovalState"));
					resdto.setAuditState(rs.getString("AuditState"));
					resdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
					resdto.setConsignee(rs.getString("Consignee"));
					resdto.setConsigneeCompany(rs.getString("ConsigneeCompany"));
					resdto.setConsigneePhone(rs.getString("ConsigneePhone"));
					resdto.setConsigneeAddress(rs.getString("ConsigneeAddress"));
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
			StockoutManifestDTO dto,Date startDay,Date endDay) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(dto==null){
			dto=new StockoutManifestDTO();
		}
		int amount=0;
		String sql=" select count(*) Amount from StockoutManifests " +
				" where "+!Tools.isVoid(dto.getStockoutManifestID())+" and StockoutManifestID = ?" +
		" or " +Tools.isVoid(dto.getStockoutManifestID())+
		" and ("+Tools.isVoid(dto.getCustomer())+" or Customer like ? )" +
		" and ("+Tools.isVoid(dto.getWarehouseName())+" or WarehouseName = ? )" +
		" and ("+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
		" and ("+Tools.isVoid(dto.getSellCenter())+" or SellCenter = ?)" +
		" and ("+Tools.isVoid(startDay)+" or DateStockout >= ? ) " +
		" and ("+Tools.isVoid(endDay)+" or DateStockout <= ?)" +
		" and ("+Tools.isVoid(dto.getApprovalState())+" or ApprovalState = ?)" +
		" and ("+Tools.isVoid(dto.getAuditState())+" or AuditState = ?) " +
		" order by StockoutManifestID desc";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getStockoutManifestID()));
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
	public boolean update(StockoutManifestDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getStockoutManifestID()==null)
			return false;
		String sql="update StockoutManifests set " +
				"WarehouseID=?," +
				"SumAmount=?," +
				"Customer=?," +
				"WarehouseName=?," +
				"Remarks=?," +
				"ChargeMode=?," +
				"CostCenter=?," +
				"SellCenter=?," +
				"DateStockout=?," +
				"UnitPrice=?," +
				"StockoutFee=?," +
				"SumVolume=?," +
				"SumWeight=?," +
				"LoadUnloadCost=?," +
				"Consignee=?," +
				"ConsigneeCompany=?," +
				"ConsigneePhone=?," +
				"ConsigneeAddress=?," +
				"CustomerID=? where StockoutManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getWarehouseID());
			pstmt.setInt(2, dto.getSumAmount());
			pstmt.setString(3, dto.getCustomer());
			pstmt.setString(4, dto.getWarehouseName());
			pstmt.setString(5, dto.getRemarks());
			pstmt.setString(6, dto.getChargeMode());
			pstmt.setString(7, dto.getCostCenter());
			pstmt.setString(8, dto.getSellCenter());
			pstmt.setDate(9, dto.getDateStockout());
			pstmt.setDouble(10, dto.getUnitPrice());
			pstmt.setDouble(11, dto.getStockoutFee());
			pstmt.setDouble(12, dto.getSumVolume());
			pstmt.setDouble(13, dto.getSumWeight());
			pstmt.setDouble(14, dto.getLoadUnloadCost());
			pstmt.setString(15, dto.getConsignee());
			pstmt.setString(16, dto.getConsigneeCompany());
			pstmt.setString(17, dto.getConsigneePhone());
			pstmt.setString(18, dto.getConsigneeAddress());
			pstmt.setInt(19, dto.getCustomerID());
			pstmt.setInt(20, dto.getStockoutManifestID());
			
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
	public boolean approve(Integer smid) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(smid==null)
			return false;
		String sql="update StockoutManifests set " +
				"ApprovalState='已批准' where StockoutManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, smid);
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
	public boolean audit(Integer smid) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(smid==null)
			return false;
		String sql="update StockoutManifests set " +
				"AuditState='已审核' where StockoutManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, smid);
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
		String sql="update StockoutManifests set " +
				"AuditState='已审核' where StockoutManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(Integer smid:smIDList){
				pstmt.setInt(1, smid);
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
