package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.ConsigneeDTO;
import Logistics.DTO.ConsignerDTO;

public class ConsignerDAO extends BaseDAO implements DAOInterface<ConsignerDTO>{

	public boolean delete(ConsignerDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null)
			return false;
		if(dto.getName()==null ||dto.getName().length()==0|| dto.getCustomerID()==null)
			return false;
		String sql="delete from Consigners where Name=? and CustomerID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getCustomerID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ConsignerDTO getDTOByID(ConsignerDTO dto) throws Exception {
		//申明对象
		ConsignerDTO cdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(dto==null)
			return null;
		if(dto.getName()==null || dto.getName().length()==0||dto.getCustomerID()==null)
			return null;
		String sql="select * from Consigners where Consigners.CustomerID=? and Consigners.Name=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getCustomerID());
			pstmt.setString(2, dto.getName());
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				cdto= new ConsignerDTO();
				cdto.setName(rs.getString("Consigners.Name"));
				cdto.setPhone(rs.getString("Consigners.Phone"));
				cdto.setAddress(rs.getString("Consigners.Address"));
				cdto.setCustomerID(rs.getInt("Consigners.CustomerID"));
				
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
	public boolean insert(ConsignerDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" Consigners(Name,CustomerID,Phone,Address)" +
				" values(?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getCustomerID());
			pstmt.setString(3, dto.getPhone());
			pstmt.setString(4, dto.getAddress());
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
	public ArrayList<ConsignerDTO> queryAll() throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<ConsignerDTO> clist=new ArrayList<ConsignerDTO>();
		String sql="select * from Consigners";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					ConsignerDTO cdto=new ConsignerDTO();
					cdto.setName(rs.getString("Consigners.Name"));
					cdto.setPhone(rs.getString("Consigners.Phone"));
					cdto.setAddress(rs.getString("Consigners.Address"));
					cdto.setCustomerID(rs.getInt("Consigners.CustomerID"));
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
	public ArrayList<ConsignerDTO> queryOnCondition(ConsignerDTO dto,
			int start, int limit) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<ConsignerDTO> clist=new ArrayList<ConsignerDTO>();
		String sql="select * from Consigners where CustomerID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getCustomerID());
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					ConsignerDTO cdto=new ConsignerDTO();
					cdto.setName(rs.getString("Consigners.Name"));
					cdto.setPhone(rs.getString("Consigners.Phone"));
					cdto.setAddress(rs.getString("Consigners.Address"));
					cdto.setCustomerID(rs.getInt("Consigners.CustomerID"));
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
	public int queryQualifiedAmount(ConsignerDTO dto)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	public boolean update(ConsignerDTO dto) throws Exception {
		return false;
	}
	
	
	public ArrayList<ConsignerDTO> queryConsignerOnCustomerID(Integer customerID) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<ConsignerDTO> clist=new ArrayList<ConsignerDTO>();
		String sql="select Name,Phone,Address from Consigners where CustomerID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, customerID);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					ConsignerDTO cdto=new ConsignerDTO();
					cdto.setName(rs.getString("Name"));
					cdto.setPhone(rs.getString("Phone"));
					cdto.setAddress(rs.getString("Address"));
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

}
