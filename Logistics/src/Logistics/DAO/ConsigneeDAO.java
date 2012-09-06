package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DAO.*;
import Logistics.DTO.*;

public class ConsigneeDAO extends BaseDAO implements DAOInterface<ConsigneeDTO> {

	public boolean delete(ConsigneeDTO dto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null)
			return false;
		if(dto.getName()==null ||dto.getName().length()==0|| dto.getCustomerID()==null
				||dto.getCompany()==null||dto.getCompany().length()==0)
			return false;
		String sql="delete from Consignees where Name=? and CustomerID=? and Company=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getCustomerID());
			pstmt.setString(3,dto.getCompany());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public ConsigneeDTO getDTOByID(ConsigneeDTO dto) throws Exception {
		//申明对象
		ConsigneeDTO cdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(dto==null)
			return null;
		if(dto.getName()==null || dto.getName().length()==0||dto.getCustomerID()==null
				||dto.getCompany()==null||dto.getCompany().length()==0)
			return null;
		String sql="select * from Consignees where Consignees.CustomerID=? and Consignees.Name=? and Consignees.Company=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getCustomerID());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getCompany());
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				cdto= new ConsigneeDTO();
				cdto.setName(rs.getString("Consignees.Name"));
				cdto.setCompany(rs.getString("Consignees.Company"));
				cdto.setPhone(rs.getString("Consignees.Phone"));
				cdto.setAddress(rs.getString("Consignees.Address"));
				cdto.setCustomerID(rs.getInt("Consignees.CustomerID"));
				
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
	public boolean insert(ConsigneeDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" Consignees(Name,CustomerID,Phone,Address,Company)" +
				" values(?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getCustomerID());
			pstmt.setString(3, dto.getPhone());
			pstmt.setString(4, dto.getAddress());
			pstmt.setString(5, dto.getCompany());
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
	public ArrayList<ConsigneeDTO> queryAll() throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<ConsigneeDTO> clist=new ArrayList<ConsigneeDTO>();
		String sql="select * from Consignees";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					ConsigneeDTO cdto=new ConsigneeDTO();
					cdto.setName(rs.getString("Consignees.Name"));
					cdto.setCompany(rs.getString("Consignees.Company"));
					cdto.setPhone(rs.getString("Consignees.Phone"));
					cdto.setAddress(rs.getString("Consignees.Address"));
					cdto.setCustomerID(rs.getInt("Consignees.CustomerID"));
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
	public ArrayList<ConsigneeDTO> queryOnCondition(ConsigneeDTO dto,
			int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public int queryQualifiedAmount(ConsigneeDTO dto)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	public boolean update(ConsigneeDTO dto) throws Exception {
		return false;
	}
	public ArrayList<ConsigneeDTO> queryCompanyName(Integer customerID) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<ConsigneeDTO> clist=new ArrayList<ConsigneeDTO>();
		String sql="select Company from Consignees where CustomerID=? group by Company";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, customerID);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					ConsigneeDTO cdto=new ConsigneeDTO();
					cdto.setCompany(rs.getString("Company"));
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
	
	public ArrayList<ConsigneeDTO> queryConsigneeName(Integer customerID,String company) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<ConsigneeDTO> clist=new ArrayList<ConsigneeDTO>();
		String sql="select Name,Phone,Address from Consignees where CustomerID=? and Company=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.setString(2,company);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					ConsigneeDTO cdto=new ConsigneeDTO();
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
