package Logistics.DAO;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.*;
public class CityDAO extends BaseDAO{
	
	public ArrayList<CityDTO> queryByCondition(CityDTO dto,int start,int limit) throws Exception{
		Tools.print("queryByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<CityDTO> clist=new ArrayList<CityDTO>();
		if(dto==null)
			dto=new CityDTO();
		String sql=" select * from Cities " +
				"where("+Tools.isVoid(dto.getCityID())+" or CityID=?)" +
				"and ("+Tools.isVoid(dto.getProvince())+" or province = ?)" +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getCityID());
			pstmt.setString(2, dto.getProvince());
			pstmt.setInt(3, start);
			pstmt.setInt(4, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					CityDTO cdto=new CityDTO();
					cdto.setCityID(rs.getString("CityID"));
					cdto.setName(rs.getString("Name"));
					cdto.setProvince(rs.getString("Province"));
					clist.add(cdto);
				}
			}
			return clist;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	public int queryAmountByCondition(CityDTO dto) throws Exception{
		Tools.print("queryAmountByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		int userAmount=0;
		String sql=" select count(*) Amount from Cities " +
		"where("+Tools.isVoid(dto.getCityID())+" or CityID=?)" +
		"and ("+Tools.isVoid(dto.getProvince())+" or province = ?)";
		
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getCityID());
			pstmt.setString(2, dto.getProvince());
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				userAmount=rs.getInt("Amount");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			close();
		}
		return userAmount;
	}
	public ArrayList<CityDTO> queryAllCities() throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<CityDTO> clist=new ArrayList<CityDTO>();
		String sql="select * from Cities";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					CityDTO cdto=new CityDTO();
					cdto.setCityID(rs.getString("CityID"));
					cdto.setName(rs.getString("Name"));
					cdto.setProvince(rs.getString("Province"));
					clist.add(cdto);
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
	
	public boolean delete(String cityID) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(cityID==null)
			return false;
		String sql="delete from Cities where CityID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, cityID);
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
	
	public boolean update(CityDTO cdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(cdto==null || cdto.getCityID()==null || cdto.getCityID().length()==0)
			return false;
		String sql="update Cities set Province=?, Name=?" +
				" where CityID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1,cdto.getProvince());
			pstmt.setString(2, cdto.getName());
			pstmt.setString(3, cdto.getCityID());
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
	
	
	public boolean insert(CityDTO cdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(cdto==null || cdto.getCityID()==null|| cdto.getCityID().length()==0)
			return false;
		String sql="insert into " +
				" Cities(CityID,Province,Name) " +
				" values(?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, cdto.getCityID());
			pstmt.setString(2, cdto.getProvince());
			pstmt.setString(3, cdto.getName());
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
	
	public CityDTO getDTOByID(String cityID) throws Exception{
		CityDTO cdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(cityID==null || cityID.length()==0)
			return null;
		String sql="select * from Cities where CityID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, cityID);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				cdto=new CityDTO();
				cdto.setCityID(rs.getString("CityID"));
				cdto.setName(rs.getString("Name"));
				cdto.setProvince(rs.getString("Province"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return cdto;
	}

}
