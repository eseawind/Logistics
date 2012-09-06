package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.*;

public class BarcodeDAO extends BaseDAO {
	
	public BarcodeDTO getDTOByID(Integer bid) throws Exception{
		//申明对象
		BarcodeDTO bdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(Tools.isVoid(bid))
			return null;
		String sql="select * from Barcodes where BarcodeID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, bid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				bdto=new BarcodeDTO();
				bdto.setBarcodeID(rs.getInt("BarcodeID"));
				bdto.setItemNumber(rs.getString("ItemNumber"));
				bdto.setItemName(rs.getString("ItemName"));
				bdto.setBatch(rs.getString("Batch"));
				bdto.setAmount(rs.getString("Amount"));
				bdto.setOperationType(rs.getString("OperationType"));
				bdto.setManifestID(rs.getString("ManifestID"));
				bdto.setCustomerID(rs.getString("CustomerID"));
				bdto.setCustomer(rs.getString("Customer"));
				bdto.setWarehouseID(rs.getString("WarehouseID"));
				bdto.setWarehouse(rs.getString("Warehouse"));
				bdto.setBarcode(rs.getString("Barcode"));
				bdto.setItemID(rs.getString("ItemID"));
				bdto.setRemarks(rs.getString("Remarks"));
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return bdto;
	}
	
	public boolean insert(BarcodeDTO bdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(bdto==null)
			return false;
		String sql="insert into " +
				" Barcodes(BarcodeID,Barcode," +
				"ItemNumber,ItemName,Batch,Amount," +
				"OperationType,ManifestID,CustomerID,Customer," +
				"WarehouseID,Warehouse,ItemID,Remarks) " +
				" values(null,?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, bdto.getBarcode());
			pstmt.setString(2, bdto.getItemNumber());
			pstmt.setString(3, bdto.getItemName());
			pstmt.setString(4, bdto.getBatch());
			pstmt.setString(5, bdto.getAmount());
			pstmt.setString(6, bdto.getOperationType());
			pstmt.setString(7, bdto.getManifestID());
			pstmt.setString(8, bdto.getCustomerID());
			pstmt.setString(9, bdto.getCustomer());
			pstmt.setString(10, bdto.getWarehouseID());
			pstmt.setString(11, bdto.getWarehouse());
			pstmt.setString(12, bdto.getItemID());
			pstmt.setString(13, bdto.getRemarks());
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
	
	public boolean insert(ArrayList<BarcodeDTO> bdtos) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(bdtos==null || bdtos.size()==0)
			return false;
		String sql="insert into " +
				" Barcodes(BarcodeID,Barcode," +
				"ItemNumber,ItemName,Batch,Amount," +
				"OperationType,ManifestID,CustomerID,Customer," +
				"WarehouseID,Warehouse,ItemID,Remarks) " +
				" values(null,?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(BarcodeDTO bdto:bdtos){
			pstmt.setString(1, bdto.getBarcode());
				pstmt.setString(2, bdto.getItemNumber());
				pstmt.setString(3, bdto.getItemName());
				pstmt.setString(4, bdto.getBatch());
				pstmt.setString(5, bdto.getAmount());
				pstmt.setString(6, bdto.getOperationType());
				pstmt.setString(7, bdto.getManifestID());
				pstmt.setString(8, bdto.getCustomerID());
				pstmt.setString(9, bdto.getCustomer());
				pstmt.setString(10, bdto.getWarehouseID());
				pstmt.setString(11, bdto.getWarehouse());
				pstmt.setString(12, bdto.getItemID());
				pstmt.setString(13, bdto.getRemarks());
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
	
	public boolean update(BarcodeDTO bdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(bdto==null)
			return false;
		String sql="update Barcodes set " +
				"ItemNumber=?,ItemName=?," +
				"Batch=?,Amount=?,OperationType=?,ManifestID=?," +
				"CustomerID=?,Customer=?,WarehouseID=?,Warehouse=?,Barcode=?,ItemID=?,Remarks=? " +
				" where BarcodeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, bdto.getItemNumber());
			pstmt.setString(2, bdto.getItemName());
			pstmt.setString(3, bdto.getBatch());
			pstmt.setString(4, bdto.getAmount());
			pstmt.setString(5, bdto.getOperationType());
			pstmt.setString(6, bdto.getManifestID());
			pstmt.setString(7, bdto.getCustomerID());
			pstmt.setString(8, bdto.getCustomer());
			pstmt.setString(9, bdto.getWarehouseID());
			pstmt.setString(10, bdto.getWarehouse());
			pstmt.setString(11, bdto.getBarcode());
			pstmt.setString(12, bdto.getItemID());
			pstmt.setString(13, bdto.getRemarks());
			pstmt.setInt(14, bdto.getBarcodeID());
			
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
	
	public boolean delete(Integer bid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(bid==null)
			return false;
		String sql="delete from Barcodes where BarcodeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, bid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(ArrayList<Integer> bids) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(bids==null)
			return false;
		String sql="delete from Barcodes where BarcodeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(Integer bid:bids){
				pstmt.setInt(1, bid);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public ArrayList<BarcodeDTO> queryByCondition(BarcodeDTO dto,int start,int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<BarcodeDTO> ilist=new ArrayList<BarcodeDTO>();
		String sql=" select * from Barcodes" +
				" where ("+Tools.isVoid(dto.getManifestID())+" or ManifestID = ? )" +
				" and ( "+Tools.isVoid(dto.getOperationType())+" or OperationType = ?)" +
				" limit ?,?";
		Tools.print(sql);
		try{
			
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getManifestID());
			pstmt.setString(2, dto.getOperationType());
			pstmt.setInt(3, start);
			pstmt.setInt(4, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					BarcodeDTO bdto=new BarcodeDTO();
					bdto.setBarcodeID(rs.getInt("BarcodeID"));
					bdto.setItemNumber(rs.getString("ItemNumber"));
					bdto.setItemName(rs.getString("ItemName"));
					bdto.setBatch(rs.getString("Batch"));
					bdto.setAmount(rs.getString("Amount"));
					bdto.setOperationType(rs.getString("OperationType"));
					bdto.setManifestID(rs.getString("ManifestID"));
					bdto.setCustomerID(rs.getString("CustomerID"));
					bdto.setCustomer(rs.getString("Customer"));
					bdto.setWarehouseID(rs.getString("WarehouseID"));
					bdto.setWarehouse(rs.getString("Warehouse"));
					bdto.setBarcode(rs.getString("Barcode"));
					bdto.setItemID(rs.getString("ItemID"));
					bdto.setRemarks(rs.getString("Remarks"));
					ilist.add(bdto);
				}
			}
			return ilist;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	
	public int queryAmountByCondition(BarcodeDTO dto) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from Barcodes " +
		" where ("+Tools.isVoid(dto.getManifestID())+" or ManifestID = ? )" +
		" and ( "+Tools.isVoid(dto.getOperationType())+" or OperationType = ?)";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getManifestID());
			pstmt.setString(2, dto.getOperationType());
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				amount=rs.getInt("Amount");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			close();
		}
		return amount;
	}
	
	
}
