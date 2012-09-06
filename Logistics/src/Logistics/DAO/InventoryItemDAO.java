package Logistics.DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.CargoDTO;
import Logistics.DTO.ConsigneeDTO;
import Logistics.DTO.FreightManifestDTO;
import Logistics.DTO.InventoryItemDTO;
import Logistics.DTO.ItemDTO;
import Logistics.DTO.StockTransferItemDTO;
import Logistics.DTO.StockinItemDTO;
import Logistics.DTO.StockoutItemDTO;

public class InventoryItemDAO extends BaseDAO {

	
	public boolean delete(Integer imid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(imid==null)
			return true;
		String sql="delete from inventoryItems where InventoryManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, imid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean delete(ArrayList<Integer> imIDList) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(imIDList==null)
			return true;
		String sql="delete from inventoryItems where InventoryManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(int i=0;i<imIDList.size();i++)
			{
				pstmt.setInt(1, imIDList.get(i));
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	public boolean insert(InventoryItemDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into inventoryItems(" +
				"InventoryManifestID,ItemID," +
				"Batch,ItemName,ItemNumber,Unit,AmountRecorded,AmountExisted," +
				"AmountDifference,DifferenceRemarks" +
				" ) values(?,?,?,?,?,?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getInventoryManifestID());
			pstmt.setInt(2, dto.getItemID());
			pstmt.setString(3, dto.getBatch());
			pstmt.setString(4, dto.getItemName());
			pstmt.setString(5, dto.getItemNumber());
			pstmt.setString(6, dto.getUnit());
			pstmt.setInt(7, dto.getAmountRecorded());
			pstmt.setInt(8, dto.getAmountExisted());
			pstmt.setInt(9, dto.getAmountDifference());
			pstmt.setString(10, dto.getDifferenceRemarks());
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
	public boolean insert(ArrayList<InventoryItemDTO> dtos) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dtos==null)
			return true;
		String sql="insert into inventoryItems(" +
		"InventoryManifestID,ItemID," +
		"Batch,ItemName,ItemNumber,Unit,AmountRecorded,AmountExisted," +
		"AmountDifference,DifferenceRemarks" +
		" ) values(?,?,?,?,?,?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(InventoryItemDTO dto:dtos){
				pstmt.setInt(1, dto.getInventoryManifestID());
				pstmt.setInt(2, dto.getItemID());
				pstmt.setString(3, dto.getBatch());
				pstmt.setString(4, dto.getItemName());
				pstmt.setString(5, dto.getItemNumber());
				pstmt.setString(6, dto.getUnit());
				pstmt.setInt(7, dto.getAmountRecorded());
				pstmt.setInt(8, dto.getAmountExisted());
				pstmt.setInt(9, dto.getAmountDifference());
				pstmt.setString(10, dto.getDifferenceRemarks());
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
	
	public ArrayList<InventoryItemDTO> queryOnCondition(Integer imid) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}

		ArrayList<InventoryItemDTO> resList=new ArrayList<InventoryItemDTO>();
		String sql=" select * from inventoryItems where InventoryManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, imid);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					InventoryItemDTO resdto=new InventoryItemDTO();
					resdto.setInventoryManifestID(rs.getInt("InventoryManifestID"));
					resdto.setItemID(rs.getInt("ItemID"));
					resdto.setBatch(rs.getString("Batch"));
					resdto.setItemName(rs.getString("ItemName"));
					resdto.setItemNumber(rs.getString("ItemNumber"));
					resdto.setUnit(rs.getString("Unit"));
					resdto.setAmountDifference(rs.getInt("AmountDifference"));
					resdto.setAmountRecorded(rs.getInt("AmountRecorded"));
					resdto.setAmountExisted(rs.getInt("AmountExisted"));
					resdto.setDifferenceRemarks(rs.getString("differenceRemarks"));
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
