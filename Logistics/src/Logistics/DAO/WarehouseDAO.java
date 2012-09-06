package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.CityDTO;
import Logistics.DTO.EmployeeProfileDTO;
import Logistics.DTO.EmployeeSalaryDTO;
import Logistics.DTO.WarehouseDTO;

public class WarehouseDAO extends BaseDAO {
	
	public ArrayList<WarehouseDTO> queryByCondition(int start,int limit) throws Exception{
		Tools.print("queryByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<WarehouseDTO> wlist=new ArrayList<WarehouseDTO>();
		String sql=" select * from Warehouses,Cities where Warehouses.CityID = Cities.CityID limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					WarehouseDTO wdto=new WarehouseDTO();
					wdto.setAddress(rs.getString("Address"));
					wdto.setCityID(rs.getString("Warehouses.CityID"));
					wdto.setName(rs.getString("Warehouses.Name"));
					wdto.setRemarks(rs.getString("Remarks"));
					wdto.setWarehouseID(rs.getString("WarehouseID"));
					CityDTO cdto=new CityDTO();
					cdto.setCityID(rs.getString("Cities.CityID"));
					cdto.setName(rs.getString("Cities.Name"));
					cdto.setProvince(rs.getString("Province"));
					wdto.setCityDTO(cdto);
					wlist.add(wdto);
				}
			}
			return wlist;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	public ArrayList<WarehouseDTO> queryNames() throws Exception{
		Tools.print("queryByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<WarehouseDTO> wlist=new ArrayList<WarehouseDTO>();
		String sql=" select * from Warehouses";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					WarehouseDTO wdto=new WarehouseDTO();
					wdto.setAddress(rs.getString("Address"));
					wdto.setCityID(rs.getString("Warehouses.CityID"));
					wdto.setName(rs.getString("Warehouses.Name"));
					wdto.setRemarks(rs.getString("Remarks"));
					wdto.setWarehouseID(rs.getString("WarehouseID"));
					
					wlist.add(wdto);
				}
			}
			return wlist;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	public int queryAmountByCondition() throws Exception{
		Tools.print("queryAmountByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		int amount=0;
		String sql=" select count(*) Amount from Warehouses";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
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
	
	public boolean delete(String wid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(wid==null)
			return false;
		String sql="delete from Warehouses where WarehouseID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, wid);
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
	
	public boolean update(WarehouseDTO wdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(wdto==null || wdto.getCityID()==null || wdto.getCityID().length()==0)
			return false;
		String sql="update Warehouses set Name=?, CityID=?, Address=?, Remarks=?" +
				" where WarehouseID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1,wdto.getName());
			pstmt.setString(2, wdto.getCityID());
			pstmt.setString(3, wdto.getAddress());
			pstmt.setString(4, wdto.getRemarks());
			pstmt.setString(5, wdto.getWarehouseID());
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
	
	public boolean insert(WarehouseDTO wdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(wdto==null || wdto.getWarehouseID()==null || wdto.getWarehouseID().length()==0)
			return false;
		String sql="insert into " +
				" Warehouses(WarehouseID,Name,CityID,Address,Remarks) " +
				" values(?,?,?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1,wdto.getWarehouseID() );
			pstmt.setString(2, wdto.getName());
			pstmt.setString(3, wdto.getCityID());
			pstmt.setString(4, wdto.getAddress());
			pstmt.setString(5, wdto.getRemarks());
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
	
	public WarehouseDTO getDTOByID(String wid)throws Exception{
		WarehouseDTO wdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(wid==null || wid.length()==0)
			return null;
		String sql="select * from Warehouses where WarehouseID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, wid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				wdto=new WarehouseDTO();
				wdto.setWarehouseID(rs.getString("WarehouseID"));
				wdto.setAddress(rs.getString("Address"));
				wdto.setCityID(rs.getString("CityID"));
				wdto.setName(rs.getString("Name"));
				wdto.setRemarks(rs.getString("Remarks"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return wdto;
	}
}
