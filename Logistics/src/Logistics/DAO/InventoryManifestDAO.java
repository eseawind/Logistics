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
import Logistics.DTO.InventoryManifestDTO;
import Logistics.DTO.StockTransferManifestDTO;
import Logistics.DTO.StockinManifestDTO;
import Logistics.DTO.StockoutManifestDTO;

public class InventoryManifestDAO extends BaseDAO {
	public boolean delete(Integer imid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(imid==null)
			return true;
		String sql="delete from InventoryManifests where InventoryManifestID=?";
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
		String sql="delete from InventoryManifests where InventoryManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(Integer imid:imIDList){
				pstmt.setInt(1, imid);
				pstmt.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public InventoryManifestDTO getByID(Integer imid)throws Exception{
		//申明对象
		InventoryManifestDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(imid==null)
			return null;
		String sql="select * from InventoryManifests where InventoryManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1,imid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new InventoryManifestDTO();
				resdto.setInventoryManifestID(rs.getInt("InventoryManifestID"));
				resdto.setDateInventoried(rs.getDate("DateInventoried"));
				resdto.setInventoryPerson(rs.getString("InventoryPerson"));
				resdto.setType(rs.getString("Type"));
				resdto.setWarehouseID(rs.getString("WarehouseID"));
				resdto.setWarehouseName(rs.getString("WarehouseName"));
				resdto.setResult(rs.getString("Result"));
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
	
	public boolean insert(InventoryManifestDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		if(dto.getDateInventoried()==null){
			return false;
		}
		String sql="insert into " +
				"InventoryManifests(" +
				"InventoryManifestID," +
				"DateInventoried," +
				"inventoryPerson," +
				"warehouseName," +
				"warehouseID," +
				"result," +
				"type)" +
				" values(null,?,?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDate(1, dto.getDateInventoried());
			pstmt.setString(2, dto.getInventoryPerson());
			pstmt.setString(3, dto.getWarehouseName());
			pstmt.setString(4, dto.getWarehouseID());
			pstmt.setString(5, dto.getResult());
			pstmt.setString(6, dto.getType());
			
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
	
	
	public ArrayList<InventoryManifestDTO> queryOnCondition(
			InventoryManifestDTO dto,Date startDay,Date endDay, int start, int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(dto==null)
		{
			dto=new InventoryManifestDTO();
		}
		ArrayList<InventoryManifestDTO> resList=new ArrayList<InventoryManifestDTO>();
		String sql=" select * from InventoryManifests " +
		" where "+!Tools.isVoid(dto.getInventoryManifestID())+" and InventoryManifestID = ?" +
		" or " +Tools.isVoid(dto.getInventoryManifestID())+
		" and ("+Tools.isVoid(dto.getWarehouseID())+" or warehouseID = ? )" +
		" and ("+Tools.isVoid(dto.getInventoryPerson())+" or inventoryPerson like ? )" +
		" and ("+Tools.isVoid(dto.getType())+" or Type = ?)" +
		" and ("+Tools.isVoid(startDay)+" or DateInventoried >= ? ) " +
		" and ("+Tools.isVoid(endDay)+" or DateInventoried <= ?) "+
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getInventoryManifestID()));
			pstmt.setString(2, dto.getWarehouseID());
			pstmt.setString(3, "%"+dto.getInventoryPerson()+"%");
			pstmt.setString(4, dto.getType());
			pstmt.setDate(5, startDay);
			pstmt.setDate(6, endDay);
			pstmt.setInt(7, start);
			pstmt.setInt(8, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					InventoryManifestDTO resdto=new InventoryManifestDTO();
					resdto.setInventoryManifestID(rs.getInt("InventoryManifestID"));
					resdto.setDateInventoried(rs.getDate("DateInventoried"));
					resdto.setInventoryPerson(rs.getString("InventoryPerson"));
					resdto.setType(rs.getString("Type"));
					resdto.setWarehouseID(rs.getString("WarehouseID"));
					resdto.setWarehouseName(rs.getString("WarehouseName"));
					resdto.setResult(rs.getString("Result"));
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
			InventoryManifestDTO dto,Date startDay,Date endDay) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from InventoryManifests " +
		" where "+!Tools.isVoid(dto.getInventoryManifestID())+" and InventoryManifestID = ?" +
		" or " +Tools.isVoid(dto.getInventoryManifestID())+
		" and ("+Tools.isVoid(dto.getWarehouseID())+" or warehouseID = ? )" +
		" and ("+Tools.isVoid(dto.getInventoryPerson())+" or inventoryPerson like ? )" +
		" and ("+Tools.isVoid(dto.getType())+" or Type = ?)" +
		" and ("+Tools.isVoid(startDay)+" or DateInventoried >= ? ) " +
		" and ("+Tools.isVoid(endDay)+" or DateInventoried <= ?)";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getInventoryManifestID()));
			pstmt.setString(2, dto.getWarehouseID());
			pstmt.setString(3, "%"+dto.getInventoryPerson()+"%");
			pstmt.setString(4, dto.getType());
			pstmt.setDate(5, startDay);
			pstmt.setDate(6, endDay);
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
	public boolean update(InventoryManifestDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getInventoryManifestID()==null)
			return false;
		String sql="update InventoryManifests set " +
		"DateInventoried=?," +
		"inventoryPerson=?," +
		"warehouseName=?," +
		"warehouseID=?," +
		"result=?," +
		"type=?  where InventoryManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDate(1, dto.getDateInventoried());
			pstmt.setString(2, dto.getInventoryPerson());
			pstmt.setString(3, dto.getWarehouseName());
			pstmt.setString(4, dto.getWarehouseID());
			pstmt.setString(5, dto.getResult());
			pstmt.setString(6, dto.getType());
			pstmt.setInt(7, dto.getInventoryManifestID());
			
			
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
	
//	public boolean approve(Integer imid) throws Exception {
//		if(this.isInitiated==false)
//		{
//			throw new Exception("DAO没有初始化");
//		}
//		//传入参数的合法性
//		if(imid==null)
//			return false;
//		String sql="update InventoryManifests set " +
//				"ApprovalState='已批准' where InventoryManifestID=?";
//		Tools.print(sql);
//		try {
//			pstmt=mysqlTools.getPreparedStatement(sql);
//			pstmt.setInt(1, imid);
//			pstmt.executeUpdate();
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		}
//		finally{
//			close();
//		}
//		
//	}
//	public boolean audit(Integer imid) throws Exception {
//		if(this.isInitiated==false)
//		{
//			throw new Exception("DAO没有初始化");
//		}
//		//传入参数的合法性
//		if(imid==null)
//			return false;
//		String sql="update InventoryManifests set " +
//				"AuditState='已审核' where InventoryManifestID=?";
//		Tools.print(sql);
//		try {
//			pstmt=mysqlTools.getPreparedStatement(sql);
//			pstmt.setInt(1, imid);
//			pstmt.executeUpdate();
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		}
//		finally{
//			close();
//		}
//		
//	}
//	public boolean audit(ArrayList<Integer> imIDList) throws Exception {
//		if(this.isInitiated==false)
//		{
//			throw new Exception("DAO没有初始化");
//		}
//		//传入参数的合法性
//		if(imIDList==null)
//			return true;
//		String sql="update InventoryManifests set " +
//				"AuditState='已审核' where InventoryManifestID=?";
//		Tools.print(sql);
//		try {
//			pstmt=mysqlTools.getPreparedStatement(sql);
//			for(Integer imid:imIDList){
//				pstmt.setInt(1, imid);
//				pstmt.executeUpdate();
//			}
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		}
//		finally{
//			close();
//		}
//		
//	}
	
}
