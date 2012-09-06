package Logistics.DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.CargoDTO;
import Logistics.DTO.ConsigneeDTO;
import Logistics.DTO.FreightManifestDTO;
import Logistics.DTO.ItemDTO;
import Logistics.DTO.StockTransferItemDTO;
import Logistics.DTO.StockinItemDTO;
import Logistics.DTO.StockoutItemDTO;

public class StockTransferItemDAO extends BaseDAO {

	
	public boolean delete(Integer stmid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(stmid==null)
			return true;
		String sql="delete from StockTransferItems where StockTransferManifestID=?";
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
		String sql="delete from StockTransferItems where StockTransferManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(int i=0;i<stmIDList.size();i++)
			{
				pstmt.setInt(1, stmIDList.get(i));
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	public boolean insert(StockTransferItemDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" StockTransferItems(StockTransferManifestID,ItemID," +
				"Batch,Amount,ItemName,ItemNumber,UnitWeight,UnitVolume,Weight,Volume,DateStockin,CustomerID)" +
				" values(?,?,?,?,?,?,?,?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getStockTransferManifestID());
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
			pstmt.setInt(12, dto.getCustomerID());
			
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
	public boolean insert(ArrayList<StockTransferItemDTO> dtos) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dtos==null)
			return true;
		String sql="insert into " +
				" StockTransferItems(StockTransferManifestID,ItemID," +
				"Batch,Amount,ItemName,ItemNumber,UnitWeight,UnitVolume,Weight,Volume,DateStockin,CustomerID)" +
				" values(?,?,?,?,?,?,?,?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(int i=0;i<dtos.size();i++){
				pstmt.setInt(1, dtos.get(i).getStockTransferManifestID());
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
				pstmt.setInt(12,dtos.get(i).getCustomerID());
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
	
	public ArrayList<StockTransferItemDTO> queryOnCondition(Integer stmid) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}

		ArrayList<StockTransferItemDTO> resList=new ArrayList<StockTransferItemDTO>();
		String sql=" select * from StockTransferItems where StockTransferManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, stmid);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					StockTransferItemDTO resdto=new StockTransferItemDTO();
					resdto.setStockTransferManifestID(rs.getInt("StockTransferManifestID"));
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
					resdto.setCustomerID(rs.getInt("CustomerID"));
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
	

	
}
