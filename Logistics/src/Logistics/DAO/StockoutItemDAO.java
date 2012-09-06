package Logistics.DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.CargoDTO;
import Logistics.DTO.ConsigneeDTO;
import Logistics.DTO.FreightManifestDTO;
import Logistics.DTO.ItemDTO;
import Logistics.DTO.StockinItemDTO;
import Logistics.DTO.StockoutItemDTO;

public class StockoutItemDAO extends BaseDAO {

	public boolean delete(Integer stockoutMID,Integer itemID,Date dateIn) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(stockoutMID==null || itemID==null)
			return true;
		String sql="delete from StockoutItems where StockoutManifestID=? and " +
				" ItemID=? and DateStockin=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, stockoutMID);
			pstmt.setInt(2, itemID);
			pstmt.setDate(3, dateIn);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(Integer stockoutMID) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(stockoutMID==null)
			return true;
		String sql="delete from StockoutItems where StockoutManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, stockoutMID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean delete(ArrayList<Integer> stockoutMIDs) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(stockoutMIDs==null)
			return true;
		String sql="delete from StockoutItems where StockoutManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(int i=0;i<stockoutMIDs.size();i++)
			{
				pstmt.setInt(1, stockoutMIDs.get(i));
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public StockoutItemDTO getDTOByID(Integer stockoutMID,Integer itemID,Date dateIn) throws Exception {
		StockoutItemDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(stockoutMID==null || itemID==null)
			return null;
		String sql="select * from StockoutItems where ItemID=? and StockoutManifestID=? and DateStockin=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, itemID);
			pstmt.setInt(2, stockoutMID);
			pstmt.setDate(3, dateIn);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new StockoutItemDTO();
				resdto.setStockoutManifestID(rs.getInt("StockoutManifestID"));
				resdto.setItemID(rs.getInt("ItemID"));
				resdto.setBatch(rs.getString("Batch"));
				resdto.setAmount(rs.getInt("Amount"));
				resdto.setItemName(rs.getString("ItemName"));
				resdto.setItemNumber(rs.getString("ItemNumber"));
				resdto.setUnitWeight(rs.getDouble("UnitWeight"));
				resdto.setUnitVolume(rs.getDouble("UnitVolume"));
				resdto.setVolume(rs.getDouble("Volume"));
				resdto.setWeight(rs.getDouble("Weight"));
				resdto.setDateStockin(rs.getDate("DateStockin"));
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
	public boolean insert(StockoutItemDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" StockoutItems(StockoutManifestID,ItemID," +
				"Batch,Amount,ItemName,ItemNumber,UnitWeight,UnitVolume,Weight,Volume,DateStockin)" +
				" values(?,?,?,?,?,?,?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getStockoutManifestID());
			pstmt.setInt(2, dto.getItemID());
			pstmt.setString(3, dto.getBatch());
			pstmt.setInt(4, dto.getAmount());
			pstmt.setString(5, dto.getItemName());
			pstmt.setString(6, dto.getItemNumber());
			pstmt.setDouble(7, dto.getUnitWeight());
			pstmt.setDouble(8, dto.getVolume());
			pstmt.setDouble(9, dto.getUnitWeight());
			pstmt.setDouble(10, dto.getVolume());
			pstmt.setDate(11, dto.getDateStockin());
			
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
	public boolean insert(ArrayList<StockoutItemDTO> dtos) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dtos==null)
			return true;
		String sql="insert into " +
				" StockoutItems(StockoutManifestID,ItemID," +
				"Batch,Amount,ItemName,ItemNumber,UnitWeight,UnitVolume,Weight,Volume,DateStockin)" +
				" values(?,?,?,?,?,?,?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(int i=0;i<dtos.size();i++){
				pstmt.setInt(1, dtos.get(i).getStockoutManifestID());
				pstmt.setInt(2, dtos.get(i).getItemID());
				pstmt.setString(3, dtos.get(i).getBatch());
				pstmt.setInt(4, dtos.get(i).getAmount());
				pstmt.setString(5, dtos.get(i).getItemName());
				pstmt.setString(6, dtos.get(i).getItemNumber());
				pstmt.setDouble(7, dtos.get(i).getUnitWeight());
				pstmt.setDouble(8, dtos.get(i).getVolume());
				pstmt.setDouble(9, dtos.get(i).getUnitWeight());
				pstmt.setDouble(10, dtos.get(i).getVolume());
				pstmt.setDate(11, dtos.get(i).getDateStockin());
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally{
			close();
		}
		return true;
	}
	
	public ArrayList<StockoutItemDTO> queryOnCondition(Integer stockoutMID) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}

		ArrayList<StockoutItemDTO> resList=new ArrayList<StockoutItemDTO>();
		String sql=" select * from StockoutItems where StockoutManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, stockoutMID);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					StockoutItemDTO resdto=new StockoutItemDTO();
					resdto.setStockoutManifestID(rs.getInt("StockoutManifestID"));
					resdto.setItemID(rs.getInt("ItemID"));
					resdto.setBatch(rs.getString("Batch"));
					resdto.setAmount(rs.getInt("Amount"));
					resdto.setItemName(rs.getString("ItemName"));
					resdto.setItemNumber(rs.getString("ItemNumber"));
					resdto.setUnitWeight(rs.getDouble("UnitWeight"));
					resdto.setUnitVolume(rs.getDouble("UnitVolume"));
					resdto.setVolume(rs.getDouble("Volume"));
					resdto.setWeight(rs.getDouble("Weight"));
					resdto.setDateStockin(rs.getDate("DateStockin"));
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
	
//	public boolean update(StockoutItemDTO dto) throws Exception {
//		
//		return false;
//	}
	
//	public StockoutItemDTO querySum(StockoutItemDTO dto) throws Exception{
//		StockoutItemDTO resdto=null;
//		if(this.isInitiated==false)
//		{
//			throw new Exception("DAO没有初始化");
//		}
//		//判断传入参数有效性
//		if(dto==null || dto.getStockoutManifestID()==null)
//			return null;
//		String sql="select sum(Amount) SumAmount,sum(Weight) SumWeight," +
//				"sum(Volume) SumVolume,sum(value) SumValue " +
//				" from StockoutItems where StockoutManifestID=?";
//		Tools.print(sql);
//		try{
//			pstmt=mysqlTools.getPreparedStatement(sql);
//			pstmt.setInt(1, dto.getStockoutManifestID());
//			rs=pstmt.executeQuery();
//			if(rs!=null && rs.next())
//			{
//				resdto= new StockoutItemDTO();
//				resdto.setAmount(rs.getDouble("SumAmount"));
//				resdto.setValue(rs.getDouble("SumValue"));
//				resdto.setVolume(rs.getDouble("SumVolume"));
//				resdto.setWeight(rs.getDouble("SumWeight"));
//			}
//		}
//		catch(SQLException e){
//			e.printStackTrace();
//			return null;
//		}
//		finally{
//			close();
//		}
//		return resdto;
//	}
	
}
