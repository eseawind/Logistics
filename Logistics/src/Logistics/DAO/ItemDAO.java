package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.*;

public class ItemDAO extends BaseDAO {
	
	public ItemDTO getDTOByID(Integer iid) throws Exception{
		//申明对象
		ItemDTO idto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(iid==null)
			return null;
		String sql="select * from Items where ItemID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, iid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				idto= new ItemDTO();
				idto.setItemID(rs.getInt("ItemID"));
				idto.setNumber(rs.getString("Number"));
				idto.setName(rs.getString("Name"));
				idto.setBatch(rs.getString("Batch"));
				idto.setUnit(rs.getString("Unit"));
				idto.setUnitVolume(rs.getDouble("UnitVolume"));
				idto.setUnitWeight(rs.getDouble("UnitWeight"));
				idto.setRemarks(rs.getString("Remarks"));
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return idto;
	}
	
	public boolean insert(ItemDTO idto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(idto==null)
			return false;
		String sql="insert into " +
				" Items(ItemID,Number,Name,Batch,Unit,UnitVolume,UnitWeight,Remarks) " +
				" values(null,?,?,?,?,?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, idto.getNumber());
			pstmt.setString(2, idto.getName());
			pstmt.setString(3, idto.getBatch());
			pstmt.setString(4, idto.getUnit());
			pstmt.setDouble(5, idto.getUnitVolume());
			pstmt.setDouble(6, idto.getUnitWeight());
			pstmt.setString(7, idto.getRemarks());
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
	public boolean insert(ArrayList<ItemDTO> idtos) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(idtos==null)
			return false;
		String sql="insert into " +
				" Items(ItemID,Number,Name,Batch,Unit,UnitVolume,UnitWeight,Remarks) " +
				" values(null,?,?,?,?,?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(ItemDTO idto:idtos){
				pstmt.setString(1, idto.getNumber());
				pstmt.setString(2, idto.getName());
				pstmt.setString(3, idto.getBatch());
				pstmt.setString(4, idto.getUnit());
				pstmt.setDouble(5, idto.getUnitVolume());
				pstmt.setDouble(6, idto.getUnitWeight());
				pstmt.setString(7, idto.getRemarks());
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
	public boolean update(ItemDTO idto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(idto==null)
			return false;
		String sql="update Items set Number=?,Name=?,Batch=?,Unit=?,UnitVolume=?,UnitWeight=?,Remarks=? " +
				" where ItemID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, idto.getNumber());
			pstmt.setString(2, idto.getName());
			pstmt.setString(3, idto.getBatch());
			pstmt.setString(4, idto.getUnit());
			pstmt.setDouble(5, idto.getUnitVolume());
			pstmt.setDouble(6, idto.getUnitWeight());
			pstmt.setString(7, idto.getRemarks());
			pstmt.setInt(8, idto.getItemID());
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
	
	public boolean delete(Integer iid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(iid==null)
			return false;
		String sql="delete from Items where ItemID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, iid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public ArrayList<ItemDTO> queryNameID() throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<ItemDTO> clist=new ArrayList<ItemDTO>();
		String sql="select * from Items";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					ItemDTO idto=new ItemDTO();
					idto.setItemID(rs.getInt("ItemID"));
					idto.setNumber(rs.getString("Number"));
					idto.setName(rs.getString("Name"));
					idto.setBatch(rs.getString("Batch"));
					idto.setUnit(rs.getString("Unit"));
					clist.add(idto);
				}
			}
			return clist;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	
	public ArrayList<ItemDTO> queryByCondition(String inumber,String iname,int start,int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<ItemDTO> ilist=new ArrayList<ItemDTO>();
		String sql=" select * from Items " +
				" where ("+Tools.isVoid(inumber)+" or Number like ?)" +
						" and ("+Tools.isVoid(iname)+" or Name like ?)" +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, "%"+inumber+"%");
			pstmt.setString(2, "%"+iname+"%");
			pstmt.setInt(3, start);
			pstmt.setInt(4, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					ItemDTO idto=new ItemDTO();
					idto.setItemID(rs.getInt("ItemID"));
					idto.setNumber(rs.getString("Number"));
					idto.setName(rs.getString("Name"));
					idto.setBatch(rs.getString("Batch"));
					idto.setUnit(rs.getString("Unit"));
					idto.setUnitVolume(rs.getDouble("UnitVolume"));
					idto.setUnitWeight(rs.getDouble("UnitWeight"));
					idto.setRemarks(rs.getString("Remarks"));
					ilist.add(idto);
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
	
	public int queryAmountByCondition(String inumber,String iname) throws Exception{
		Tools.print("queryAmountByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from Items" +
		" where ("+Tools.isVoid(inumber)+" or Number like ?)" +
		" and ("+Tools.isVoid(iname)+" or Name like ?)";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, "%"+inumber+"%");
			pstmt.setString(2, "%"+iname+"%");
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
