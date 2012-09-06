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
import Logistics.DTO.StockTransferFinanceDTO;
import Logistics.DTO.StockinFinanceDTO;
import Logistics.DTO.StockinManifestDTO;
import Logistics.DTO.StockoutFinanceDTO;

public class StockTransferFinanceDAO extends BaseDAO {
	public boolean delete(Integer stmid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(stmid==null)
			return true;
		String sql="delete from StockTransferFinances where StockoutManifestID=?";
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
		String sql="delete from StockTransferFinances where StockTransferManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(int i=0;i<stmIDList.size();i++){
				pstmt.setInt(1, stmIDList.get(i));
				pstmt.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public StockTransferFinanceDTO getByID(Integer stmid)throws Exception{
		//申明对象
		StockTransferFinanceDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(stmid==null)
			return null;
		String sql="select * from StockTransferFinances where StockTransferManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1,stmid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new StockTransferFinanceDTO();
				resdto.setStockTransferManifestID(rs.getInt("StockTransferManifestID"));
				resdto.setSumAmount(rs.getInt("SumAmount"));
				resdto.setRemarks(rs.getString("Remarks"));
				resdto.setCostCenter(rs.getString("CostCenter"));
				resdto.setDateCreated(rs.getDate("DateCreated"));
				resdto.setDateTransfered(rs.getDate("DateTransfered"));
				resdto.setSumVolume(rs.getDouble("SumVolume"));
				resdto.setSumWeight(rs.getDouble("SumWeight"));
				resdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
				resdto.setFinancialState(rs.getString("FinancialState"));
				resdto.setExtraCost(rs.getDouble("ExtraCost"));
				resdto.setOutWarehouseName(rs.getString("OutWarehouseName"));
				resdto.setInWarehouseName(rs.getString("InWarehouseName"));
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
	
	public boolean insert(StockTransferFinanceDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" StockTransferFinances(" +
				"StockTransferManifestID," +
				"SumAmount," +
				"Remarks," +
				"CostCenter," +
				"DateCreated," +
				"DateTransfered," +
				"SumVolume," +
				"SumWeight," +
				"LoadUnloadCost," +
				"FinancialState," +
				"ExtraCost," +
				"InWarehouseName," +
				"OutWarehouseName," +
				"Operator)" +
				" values(?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getStockTransferManifestID());
			pstmt.setInt(2, dto.getSumAmount());
			pstmt.setString(3, dto.getRemarks());
			pstmt.setString(4, dto.getCostCenter());
			pstmt.setDate(5, dto.getDateCreated());
			pstmt.setDate(6, dto.getDateTransfered());
			pstmt.setDouble(7, dto.getSumVolume());
			pstmt.setDouble(8, dto.getSumWeight());
			pstmt.setDouble(9, dto.getLoadUnloadCost());
			pstmt.setString(10, "未归档");
			pstmt.setDouble(11, dto.getExtraCost());
			pstmt.setString(12, dto.getInWarehouseName());
			pstmt.setString(13, dto.getOutWarehouseName());
			pstmt.setString(14, dto.getOperator());
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
	
	
	public ArrayList<StockTransferFinanceDTO> queryOnCondition(
			StockTransferFinanceDTO dto,Date startDay,Date endDay, int start, int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(dto==null)
		{
			dto=new StockTransferFinanceDTO();
		}
		ArrayList<StockTransferFinanceDTO> resList=new ArrayList<StockTransferFinanceDTO>();
		String sql=" select * from StockTransferFinances " +
				" where "+!Tools.isVoid(dto.getStockTransferManifestID())+"  and StockTransferManifestID=? " +
				" or "+Tools.isVoid(dto.getStockTransferManifestID())+
				" and ("+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
				" and ("+Tools.isVoid(dto.getInWarehouseName())+" or InWarehouseName like ?)" +
				" and ("+Tools.isVoid(dto.getOutWarehouseName())+" or OutWarehouseName like ? )" +
				" and ("+Tools.isVoid(dto.getDateTransfered())+" or DateTransfered >= ?)" +
				" and ("+Tools.isVoid(dto.getDateTransfered())+" or DateTransfered <= ?) " +
				" and ("+Tools.isVoid(dto.getFinancialState())+" or FinancialState=? )" +
						" order by StockTransferManifestID desc " +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getStockTransferManifestID()));
			pstmt.setString(2, dto.getCostCenter());
			pstmt.setString(3,"%"+ dto.getInWarehouseName()+"%");
			pstmt.setString(4, "%"+dto.getOutWarehouseName()+"%");
			pstmt.setDate(5, startDay);
			pstmt.setDate(6, endDay);
			pstmt.setString(7, dto.getFinancialState());
			pstmt.setInt(8, start);
			pstmt.setInt(9, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					StockTransferFinanceDTO resdto=new StockTransferFinanceDTO();
					resdto.setStockTransferManifestID(rs.getInt("StockTransferManifestID"));
					resdto.setSumAmount(rs.getInt("SumAmount"));
					resdto.setRemarks(rs.getString("Remarks"));
					resdto.setCostCenter(rs.getString("CostCenter"));
					resdto.setDateCreated(rs.getDate("DateCreated"));
					resdto.setDateTransfered(rs.getDate("DateTransfered"));
					resdto.setSumVolume(rs.getDouble("SumVolume"));
					resdto.setSumWeight(rs.getDouble("SumWeight"));
					resdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
					resdto.setFinancialState(rs.getString("FinancialState"));
					resdto.setExtraCost(rs.getDouble("ExtraCost"));
					resdto.setOutWarehouseName(rs.getString("OutWarehouseName"));
					resdto.setInWarehouseName(rs.getString("InWarehouseName"));
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
			StockTransferFinanceDTO dto,Date startDay,Date endDay) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from StockTransferFinances " +
		" where "+!Tools.isVoid(dto.getStockTransferManifestID())+"  and StockTransferManifestID=? " +
		" or "+Tools.isVoid(dto.getStockTransferManifestID())+
		" and ("+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
		" and ("+Tools.isVoid(dto.getInWarehouseName())+" or InWarehouseName like ?)" +
		" and ("+Tools.isVoid(dto.getOutWarehouseName())+" or OutWarehouseName like ? )" +
		" and ("+Tools.isVoid(dto.getDateTransfered())+" or DateTransfered >= ?)" +
		" and ("+Tools.isVoid(dto.getDateTransfered())+" or DateTransfered <= ?) " +
		" and ("+Tools.isVoid(dto.getFinancialState())+" or FinancialState=? )" +
				" order by StockTransferManifestID desc ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getStockTransferManifestID()));
			pstmt.setString(2, dto.getCostCenter());
			pstmt.setString(3,"%"+ dto.getInWarehouseName()+"%");
			pstmt.setString(4, "%"+dto.getOutWarehouseName()+"%");
			pstmt.setDate(5, startDay);
			pstmt.setDate(6, endDay);
			pstmt.setString(7, dto.getFinancialState());
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
	public boolean updateMoney(StockTransferFinanceDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getStockTransferManifestID()==null)
			return false;
		String sql="update StockTransferFinances set " +
				"CostCenter=?," +
				"LoadUnloadCost=?," +
				"ExtraCost=?," +
				"Remarks=? where StockTransferManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getCostCenter());
			pstmt.setDouble(2, dto.getLoadUnloadCost());
			pstmt.setDouble(3, dto.getExtraCost());
			pstmt.setString(4, dto.getRemarks());
			pstmt.setInt(5, dto.getStockTransferManifestID());
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
	
	public boolean updateFinancialState(StockTransferFinanceDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getStockTransferManifestID()==null || dto.getFinancialState()==null)
			return false;
		String sql="update StockTransferFinances set FinancialState=?  where StockTransferManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getFinancialState());
			pstmt.setInt(2, dto.getStockTransferManifestID());
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
