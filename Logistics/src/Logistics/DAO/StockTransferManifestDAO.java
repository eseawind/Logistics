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
import Logistics.DTO.StockTransferManifestDTO;
import Logistics.DTO.StockinManifestDTO;
import Logistics.DTO.StockoutManifestDTO;

public class StockTransferManifestDAO extends BaseDAO {
	public boolean delete(Integer stmid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(stmid==null)
			return true;
		String sql="delete from StockTransferManifests where StockTransferManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, stmid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(ArrayList<Integer> stmIDList) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(stmIDList==null)
			return true;
		String sql="delete from StockTransferManifests where StockTransferManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(Integer stmid:stmIDList){
				pstmt.setInt(1, stmid);
				pstmt.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public StockTransferManifestDTO getByID(Integer stmid)throws Exception{
		//申明对象
		StockTransferManifestDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(stmid==null)
			return null;
		String sql="select * from StockTransferManifests where StockTransferManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1,stmid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new StockTransferManifestDTO();
				resdto.setStockTransferManifestID(rs.getInt("StockTransferManifestID"));
				resdto.setSumAmount(rs.getInt("SumAmount"));
				resdto.setRemarks(rs.getString("Remarks"));
				resdto.setCostCenter(rs.getString("CostCenter"));
				resdto.setDateCreated(rs.getDate("DateCreated"));
				resdto.setDateTransfered(rs.getDate("DateTransfered"));
				resdto.setSumVolume(rs.getDouble("SumVolume"));
				resdto.setSumWeight(rs.getDouble("SumWeight"));
				resdto.setApprovalState(rs.getString("ApprovalState"));
				resdto.setAuditState(rs.getString("AuditState"));
				resdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
				resdto.setOutWarehouse(rs.getString("OutWarehouse"));
				resdto.setOutWarehouseID(rs.getString("OutWarehouseID"));
				resdto.setInWarehouse(rs.getString("InWarehouse"));
				resdto.setInWarehouseID(rs.getString("InWarehouseID"));
				resdto.setOperator(rs.getString("Operator"));
				
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
	
	public boolean insert(StockTransferManifestDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		if(dto.getDateTransfered()==null){
			return false;
		}
		String sql="insert into " +
				"StockTransferManifests(" +
				"StockTransferManifestID," +
				"SumAmount," +
				"Remarks," +
				"CostCenter," +
				"DateCreated," +
				"DateTransfered," +
				"SumVolume," +
				"SumWeight," +
				"LoadUnloadCost," +
				"ApprovalState," +
				"AuditState," +
				"OutWarehouse," +
				"OutWarehouseID," +
				"InWarehouse," +
				"InWarehouseID," +
				"Operator)" +
				" values(null,?,?,?,?," +
				"?,?,?,?,'未批准'," +
				"'未审核',?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getSumAmount());
			pstmt.setString(2, dto.getRemarks());
			pstmt.setString(3, dto.getCostCenter());
			pstmt.setDate(4, dto.getDateCreated());
			pstmt.setDate(5, dto.getDateTransfered());
			pstmt.setDouble(6, dto.getSumVolume());
			pstmt.setDouble(7, dto.getSumWeight());
			pstmt.setDouble(8, dto.getLoadUnloadCost());
			pstmt.setString(9, dto.getOutWarehouse());
			pstmt.setString(10, dto.getOutWarehouseID());
			pstmt.setString(11, dto.getInWarehouse());
			pstmt.setString(12, dto.getInWarehouseID());
			pstmt.setString(13, dto.getOperator());
			
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
	
	
	public ArrayList<StockTransferManifestDTO> queryOnCondition(
			StockTransferManifestDTO dto,Date startDay,Date endDay, int start, int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(dto==null)
		{
			dto=new StockTransferManifestDTO();
		}
		ArrayList<StockTransferManifestDTO> resList=new ArrayList<StockTransferManifestDTO>();
		String sql=" select * from StockTransferManifests " +
		" where "+!Tools.isVoid(dto.getStockTransferManifestID())+" and StockTransferManifestID = ?" +
		" or " +Tools.isVoid(dto.getStockTransferManifestID())+
		" and ("+Tools.isVoid(dto.getInWarehouse())+" or InWarehouse like ? )" +
		" and ("+Tools.isVoid(dto.getOutWarehouse())+" or OutWarehouse like ? )" +
		" and ("+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
		" and ("+Tools.isVoid(startDay)+" or DateTransfered >= ? ) " +
		" and ("+Tools.isVoid(endDay)+" or DateTransfered <= ?)" +
		" and ("+Tools.isVoid(dto.getApprovalState())+" or ApprovalState = ?)" +
		" and ("+Tools.isVoid(dto.getAuditState())+" or AuditState = ?) " +
				" order by StockTransferManifestID desc "+
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getStockTransferManifestID()));
			pstmt.setString(2, "%"+dto.getInWarehouse()+"%");
			pstmt.setString(3, "%"+dto.getOutWarehouse()+"%");
			pstmt.setString(4, dto.getCostCenter());
			pstmt.setDate(5, startDay);
			pstmt.setDate(6, endDay);
			pstmt.setString(7, dto.getApprovalState());
			pstmt.setString(8, dto.getAuditState());
			pstmt.setInt(9, start);
			pstmt.setInt(10, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					StockTransferManifestDTO resdto=new StockTransferManifestDTO();
					resdto.setStockTransferManifestID(rs.getInt("StockTransferManifestID"));
					resdto.setSumAmount(rs.getInt("SumAmount"));
					resdto.setRemarks(rs.getString("Remarks"));
					resdto.setCostCenter(rs.getString("CostCenter"));
					resdto.setDateCreated(rs.getDate("DateCreated"));
					resdto.setDateTransfered(rs.getDate("DateTransfered"));
					resdto.setSumVolume(rs.getDouble("SumVolume"));
					resdto.setSumWeight(rs.getDouble("SumWeight"));
					resdto.setApprovalState(rs.getString("ApprovalState"));
					resdto.setAuditState(rs.getString("AuditState"));
					resdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
					resdto.setOutWarehouse(rs.getString("OutWarehouse"));
					resdto.setOutWarehouseID(rs.getString("OutWarehouseID"));
					resdto.setInWarehouse(rs.getString("InWarehouse"));
					resdto.setInWarehouseID(rs.getString("InWarehouseID"));
					resdto.setOperator(rs.getString("Operator"));
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
			StockTransferManifestDTO dto,Date startDay,Date endDay) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from StockTransferManifests " +
		" where "+!Tools.isVoid(dto.getStockTransferManifestID())+" and StockTransferManifestID = ?" +
		" or " +Tools.isVoid(dto.getStockTransferManifestID())+
		" and ("+Tools.isVoid(dto.getInWarehouse())+" or InWarehouse like ? )" +
		" and ("+Tools.isVoid(dto.getOutWarehouse())+" or OutWarehouse like ? )" +
		" and ("+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
		" and ("+Tools.isVoid(startDay)+" or DateTransfered >= ? ) " +
		" and ("+Tools.isVoid(endDay)+" or DateTransfered <= ?)" +
		" and ("+Tools.isVoid(dto.getApprovalState())+" or ApprovalState = ?)" +
		" and ("+Tools.isVoid(dto.getAuditState())+" or AuditState = ?) " +
				" order by StockTransferManifestID desc ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getStockTransferManifestID()));
			pstmt.setString(2, "%"+dto.getInWarehouse()+"%");
			pstmt.setString(3, "%"+dto.getOutWarehouse()+"%");
			pstmt.setString(4, dto.getCostCenter());
			pstmt.setDate(5, startDay);
			pstmt.setDate(6, endDay);
			pstmt.setString(7, dto.getApprovalState());
			pstmt.setString(8, dto.getAuditState());
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
	public boolean update(StockTransferManifestDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getStockTransferManifestID()==null)
			return false;
		String sql="update StockTransferManifests set " +
		"SumAmount=?," +
		"Remarks=?," +
		"CostCenter=?," +
		"DateTransfered=?," +
		"SumVolume=?," +
		"SumWeight=?," +
		"LoadUnloadCost=?," +
		"OutWarehouse=?," +
		"OutWarehouseID=?," +
		"InWarehouse=?," +
		"InWarehouseID=?," +
		"Operator=? where StockTransferManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getSumAmount());
			pstmt.setString(2, dto.getRemarks());
			pstmt.setString(3, dto.getCostCenter());
			pstmt.setDate(4, dto.getDateTransfered());
			pstmt.setDouble(5, dto.getSumVolume());
			pstmt.setDouble(6, dto.getSumWeight());
			pstmt.setDouble(7, dto.getLoadUnloadCost());
			pstmt.setString(8, dto.getOutWarehouse());
			pstmt.setString(9, dto.getOutWarehouseID());
			pstmt.setString(10, dto.getInWarehouse());
			pstmt.setString(11, dto.getInWarehouseID());
			pstmt.setString(12, dto.getOperator());
			pstmt.setInt(13, dto.getStockTransferManifestID());
			
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
	public boolean approve(Integer stmid) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(stmid==null)
			return false;
		String sql="update StockTransferManifests set " +
				"ApprovalState='已批准' where StockTransferManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, stmid);
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
	public boolean audit(Integer stmid) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(stmid==null)
			return false;
		String sql="update StockTransferManifests set " +
				"AuditState='已审核' where StockTransferManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, stmid);
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
	public boolean audit(ArrayList<Integer> stmIDList) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(stmIDList==null)
			return true;
		String sql="update StockTransferManifests set " +
				"AuditState='已审核' where StockTransferManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(Integer stmid:stmIDList){
				pstmt.setInt(1, stmid);
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
