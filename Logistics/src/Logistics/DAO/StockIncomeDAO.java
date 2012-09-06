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
import Logistics.DTO.StockIncomeDTO;
import Logistics.DTO.StockTransferFinanceDTO;
import Logistics.DTO.StockinFinanceDTO;
import Logistics.DTO.StockinManifestDTO;
import Logistics.DTO.StockoutFinanceDTO;


public class StockIncomeDAO extends BaseDAO {
	public boolean delete(Integer siid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(siid==null)
			return true;
		String sql="delete from StockIncomes where StockIncomeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(siid));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(ArrayList<Integer> siidList) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(siidList==null)
			return true;
		String sql="delete from StockIncomes where StockIncomeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(Integer siid:siidList){
				pstmt.setString(1, Tools.toString(siid));
				pstmt.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public StockIncomeDTO getByID(Integer siid)throws Exception{
		//申明对象
		StockIncomeDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(siid==null)
			return null;
		String sql="select * from StockIncomes where StockIncomeID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1,siid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new StockIncomeDTO();
				resdto.setStockIncomeID(rs.getInt("StockIncomeID"));
				resdto.setItemID(rs.getInt("ItemID"));
				resdto.setItemName(rs.getString("ItemName"));
				resdto.setItemNumber(rs.getString("ItemNumber"));
				resdto.setDateStockin(rs.getDate("DateStockin"));
				resdto.setDateStockout(rs.getDate("DateStockout"));
				resdto.setCustomerID(rs.getInt("CustomerID"));
				resdto.setCustomerName(rs.getString("CustomerName"));
				resdto.setWarehouseID(rs.getString("WarehouseID"));
				resdto.setWarehouseName(rs.getString("WarehouseName"));
				resdto.setAmount(rs.getInt("Amount"));
				resdto.setVolume(rs.getDouble("Volume"));
				resdto.setWeight(rs.getDouble("Weight"));
				resdto.setDateAccountStart(rs.getDate("DateAccountStart"));
				resdto.setDateAccountEnd(rs.getDate("DateAccountEnd"));
				resdto.setDaysStock(rs.getInt("DaysStock"));
				resdto.setChargeMode(rs.getString("ChargeMode"));
				resdto.setUnitPrice(rs.getDouble("UnitPrice"));
				resdto.setStockFee(rs.getDouble("StockFee"));
				resdto.setFinancialState(rs.getString("FinancialState"));
				resdto.setDateModified(rs.getDate("DateModified"));
				resdto.setSellCenter(rs.getString("SellCenter"));
				resdto.setExtraFee(rs.getDouble("ExtraFee"));
				resdto.setRemarks(rs.getString("Remarks"));
				
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
	
	public boolean insert(StockIncomeDTO dto) throws Exception {
		
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" StockIncomes(" +
				" stockIncomeID,ItemID,ItemName, " +
				"ItemNumber,DateStockin," +
				"DateStockout,	   " +
				"CustomerID   ,	   " +
				"CustomerName   ,	   " +
				"WarehouseID      ," +
				"WarehouseName    ,	   " +
				"Amount   ,	   " +
				"Volume    ," +
				"Weight     ,	   " +
				"DateAccountStart  ,	  " +
				"DateAccountEnd ,	   " +
				"DaysStock  ,	   " +
				"ChargeMode  ,	   " +
				"UnitPrice  ,	   " +
				"StockFee   ,	   " +
				"FinancialState   ,	   " +
				"DateModified ,	   " +
				"sellCenter    ,	  " +
				" extraFee   ,	   " +
				"remarks  )" +
				" values(null,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getItemID());
			pstmt.setString(2, dto.getItemName());
			pstmt.setString(3, dto.getItemNumber());
			pstmt.setDate(4, dto.getDateStockin());
			pstmt.setDate(5, dto.getDateStockout());
			pstmt.setInt(6, dto.getCustomerID());
			pstmt.setString(7, dto.getCustomerName());
			pstmt.setString(8, dto.getWarehouseID());
			pstmt.setString(9, dto.getWarehouseName());
			pstmt.setInt(10, dto.getAmount());
			pstmt.setDouble(11, dto.getVolume());
			pstmt.setDouble(12, dto.getWeight());
			pstmt.setDate(13, dto.getDateAccountStart());
			pstmt.setDate(14, dto.getDateAccountEnd());
			pstmt.setInt(15, dto.getDaysStock());
			pstmt.setString(16, dto.getChargeMode());
			pstmt.setDouble(17, dto.getUnitPrice());
			pstmt.setDouble(18, dto.getStockFee());
			pstmt.setString(19, "未归档");
			pstmt.setDate(20, Tools.currDate());
			pstmt.setString(21, dto.getSellCenter());
			pstmt.setDouble(22, dto.getExtraFee());
			pstmt.setString(23, dto.getRemarks());
			
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
	
	
	public ArrayList<StockIncomeDTO> queryOnCondition(
			StockIncomeDTO dto,Date startDay,Date endDay, int start, int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(dto==null)
		{
			dto=new StockIncomeDTO();
		}
		ArrayList<StockIncomeDTO> resList=new ArrayList<StockIncomeDTO>();
		String sql=" select * from StockIncomes " +
				" where "+!Tools.isVoid(dto.getStockIncomeID())+" and StockIncomeID = ?" +
				" or "+Tools.isVoid(dto.getStockIncomeID())+
				" and ("+Tools.isVoid(dto.getCustomerName())+"  or CustomerName like ? )" +
				" and ("+Tools.isVoid(dto.getWarehouseName())+"  or WarehouseName like ? )" +
				" and ("+Tools.isVoid(dto.getFinancialState())+"  or FinancialState =?)" +
				" and ("+Tools.isVoid(dto.getSellCenter())+"  or SellCenter = ?)" +
				" and ("+Tools.isVoid(dto.getDateModified())+"  or DateModified >=?)" +
				" and ("+Tools.isVoid(dto.getDateModified())+"  or DateModified <=? ) " +
						" order by StockIncomeID desc " +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getStockIncomeID()));
			pstmt.setString(2, "%"+dto.getCustomerName()+"%");
			pstmt.setString(3, "%"+dto.getWarehouseName()+"%");
			pstmt.setString(4, dto.getFinancialState());
			pstmt.setString(5, dto.getSellCenter());
			pstmt.setDate(6, dto.getDateModified());
			pstmt.setDate(7, dto.getDateModified());
			pstmt.setInt(8, start);
			pstmt.setInt(9, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					StockIncomeDTO resdto=new StockIncomeDTO();
					resdto.setStockIncomeID(rs.getInt("StockIncomeID"));
					resdto.setItemID(rs.getInt("ItemID"));
					resdto.setItemName(rs.getString("ItemName"));
					resdto.setItemNumber(rs.getString("ItemNumber"));
					resdto.setDateStockin(rs.getDate("DateStockin"));
					resdto.setDateStockout(rs.getDate("DateStockout"));
					resdto.setCustomerID(rs.getInt("CustomerID"));
					resdto.setCustomerName(rs.getString("CustomerName"));
					resdto.setWarehouseID(rs.getString("WarehouseID"));
					resdto.setWarehouseName(rs.getString("WarehouseName"));
					resdto.setAmount(rs.getInt("Amount"));
					resdto.setVolume(rs.getDouble("Volume"));
					resdto.setWeight(rs.getDouble("Weight"));
					resdto.setDateAccountStart(rs.getDate("DateAccountStart"));
					resdto.setDateAccountEnd(rs.getDate("DateAccountEnd"));
					resdto.setDaysStock(rs.getInt("DaysStock"));
					resdto.setChargeMode(rs.getString("ChargeMode"));
					resdto.setUnitPrice(rs.getDouble("UnitPrice"));
					resdto.setStockFee(rs.getDouble("StockFee"));
					resdto.setFinancialState(rs.getString("FinancialState"));
					resdto.setDateModified(rs.getDate("DateModified"));
					resdto.setSellCenter(rs.getString("SellCenter"));
					resdto.setExtraFee(rs.getDouble("ExtraFee"));
					resdto.setRemarks(rs.getString("Remarks"));
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
			StockIncomeDTO dto,Date startDay,Date endDay) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from StockIncomes " +
		" where "+!Tools.isVoid(dto.getStockIncomeID())+" and StockIncomeID = ?" +
		" or "+Tools.isVoid(dto.getStockIncomeID())+
		" and ("+Tools.isVoid(dto.getCustomerName())+"  or CustomerName like ? )" +
		" and ("+Tools.isVoid(dto.getWarehouseName())+"  or WarehouseName like ? )" +
		" and ("+Tools.isVoid(dto.getFinancialState())+"  or FinancialState =?)" +
		" and ("+Tools.isVoid(dto.getSellCenter())+"  or SellCenter = ?)" +
		" and ("+Tools.isVoid(dto.getDateModified())+"  or DateModified >=?)" +
		" and ("+Tools.isVoid(dto.getDateModified())+"  or DateModified <=? ) " +
				" order by StockIncomeID desc ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getStockIncomeID()));
			pstmt.setString(2, "%"+dto.getCustomerName()+"%");
			pstmt.setString(3, "%"+dto.getWarehouseName()+"%");
			pstmt.setString(4, dto.getFinancialState());
			pstmt.setString(5, dto.getSellCenter());
			pstmt.setDate(6, dto.getDateModified());
			pstmt.setDate(7, dto.getDateModified());
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
	public boolean updateMoney(StockIncomeDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getStockIncomeID()==null)
			return false;
		String sql="update StockIncomes set " +
				"ChargeMode=?," +
				"SellCenter=?," +
				"UnitPrice=?," +
				"StockFee=?," +
				"ExtraFee=?," +
//				"DaysStock=?," +
//				"DateAccountEnd=?," +
				"DateModified=?," +
				"Remarks=?"+
				" where StockIncomeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getChargeMode());
			pstmt.setString(2, dto.getSellCenter());
			pstmt.setDouble(3, dto.getUnitPrice());
			pstmt.setDouble(4, dto.getStockFee());
			pstmt.setDouble(5, dto.getExtraFee());
//			pstmt.setInt(6, dto.getDaysStock());
//			pstmt.setDate(7, dto.getDateAccountEnd());
			pstmt.setDate(6, Tools.currDate());
			pstmt.setString(7, dto.getRemarks());
			pstmt.setInt(8, dto.getStockIncomeID());
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
	
	public boolean updateFinancialState(StockIncomeDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getStockIncomeID()==null || dto.getFinancialState()==null)
			return false;
		String sql="update StockIncomes set FinancialState=?  where StockIncomeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getFinancialState());
			pstmt.setInt(2, dto.getStockIncomeID());
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
