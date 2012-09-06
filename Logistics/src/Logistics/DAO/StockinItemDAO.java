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

public class StockinItemDAO extends BaseDAO {

	public boolean delete(Integer smid,Integer iid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(smid==null || iid==null)
			return true;
		String sql="delete from StockinItems where StockinManifestID=? and " +
				" ItemID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, smid);
			pstmt.setInt(2, iid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(Integer smid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(smid==null)
			return true;
		String sql="delete from StockinItems where StockinManifestID=?";
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
	
	public boolean delete(ArrayList<Integer> smids) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(smids==null)
			return true;
		String sql="delete from StockinItems where StockinManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(int i=0;i<smids.size();i++)
			{
				pstmt.setInt(1, smids.get(i));
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public StockinItemDTO getDTOByID(Integer smid,Integer iid) throws Exception {
		StockinItemDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(smid==null || iid==null)
			return null;
		String sql="select * from StockinItems where ItemID=? and StockinManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, iid);
			pstmt.setInt(2, smid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new StockinItemDTO();
				resdto.setStockinManifestID(rs.getInt("StockinManifestID"));
				resdto.setItemID(rs.getInt("ItemID"));
				resdto.setBatch(rs.getString("Batch"));
				resdto.setAmount(rs.getInt("Amount"));
				resdto.setItemName(rs.getString("ItemName"));
				resdto.setItemNumber(rs.getString("ItemNumber"));
				resdto.setUnitWeight(rs.getDouble("UnitWeight"));
				resdto.setUnitVolume(rs.getDouble("UnitVolume"));
				resdto.setVolume(resdto.getAmount()*resdto.getUnitVolume());
				resdto.setWeight(resdto.getAmount()*resdto.getUnitWeight());
				resdto.setExpectedAmount(rs.getInt("ExpectedAmount"));
				resdto.setLabel(rs.getString("isLabel"));
				resdto.setIsSN(rs.getString("IsSN"));
				resdto.setRemarks(rs.getString("Remarks"));
				resdto.setUnit(rs.getString("Unit"));
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
	public boolean insert(StockinItemDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" StockinItems(StockinManifestID,ItemID," +
				"Batch,Amount,ItemName,ItemNumber,UnitWeight,UnitVolume,Weight,Volume,expectedAmount,islabel,isSN,remarks,unit)" +
				" values(?,?,?,?,?,?,?,?,?,?," +
				"?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getStockinManifestID());
			pstmt.setInt(2, dto.getItemID());
			pstmt.setString(3, dto.getBatch());
			pstmt.setInt(4, dto.getAmount());
			pstmt.setString(5, dto.getItemName());
			pstmt.setString(6, dto.getItemNumber());
			pstmt.setDouble(7, dto.getUnitWeight());
			pstmt.setDouble(8, dto.getVolume());
			pstmt.setDouble(9, dto.getUnitWeight());
			pstmt.setDouble(10, dto.getVolume());
			pstmt.setInt(11, dto.getExpectedAmount());
			pstmt.setString(12, dto.getLabel());
			pstmt.setString(13, dto.getIsSN());
			pstmt.setString(14, dto.getRemarks());
			pstmt.setString(15, dto.getUnit());
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
	public boolean insert(ArrayList<StockinItemDTO> dtos) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dtos==null)
			return true;
		String sql="insert into " +
		" StockinItems(StockinManifestID,ItemID," +
		"Batch,Amount,ItemName,ItemNumber,UnitWeight,UnitVolume,Weight,Volume,expectedAmount,islabel,isSN,remarks,unit)" +
		" values(?,?,?,?,?,?,?,?,?,?," +
		"?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(StockinItemDTO dto:dtos){
				pstmt.setInt(1, dto.getStockinManifestID());
				pstmt.setInt(2, dto.getItemID());
				pstmt.setString(3, dto.getBatch());
				pstmt.setInt(4, dto.getAmount());
				pstmt.setString(5, dto.getItemName());
				pstmt.setString(6, dto.getItemNumber());
				pstmt.setDouble(7, dto.getUnitWeight());
				pstmt.setDouble(8, dto.getVolume());
				pstmt.setDouble(9, dto.getUnitWeight());
				pstmt.setDouble(10, dto.getVolume());
				pstmt.setInt(11, dto.getExpectedAmount());
				pstmt.setString(12, dto.getLabel());
				pstmt.setString(13, dto.getIsSN());
				pstmt.setString(14, dto.getRemarks());
				pstmt.setString(15, dto.getUnit());
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
	
	public ArrayList<StockinItemDTO> queryOnCondition(Integer smid) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}

		ArrayList<StockinItemDTO> resList=new ArrayList<StockinItemDTO>();
		String sql=" select * from StockinItems where StockinManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, smid);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					StockinItemDTO resdto= new StockinItemDTO();
					resdto.setStockinManifestID(rs.getInt("StockinManifestID"));
					resdto.setItemID(rs.getInt("ItemID"));
					resdto.setBatch(rs.getString("Batch"));
					resdto.setAmount(rs.getInt("Amount"));
					resdto.setItemName(rs.getString("ItemName"));
					resdto.setItemNumber(rs.getString("ItemNumber"));
					resdto.setUnitWeight(rs.getDouble("UnitWeight"));
					resdto.setUnitVolume(rs.getDouble("UnitVolume"));
					resdto.setVolume(resdto.getAmount()*resdto.getUnitVolume());
					resdto.setWeight(resdto.getAmount()*resdto.getUnitWeight());
					resdto.setExpectedAmount(rs.getInt("ExpectedAmount"));
					resdto.setLabel(rs.getString("isLabel"));
					resdto.setIsSN(rs.getString("IsSN"));
					resdto.setRemarks(rs.getString("Remarks"));
					resdto.setUnit(rs.getString("Unit"));
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
	
//	public boolean update(StockinItemDTO dto) throws Exception {

}
