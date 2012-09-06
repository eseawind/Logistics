package Logistics.DAO;

import java.sql.*;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.*;

public class EmployeeProfileDAO extends BaseDAO{
	
	
	public int getCountByConditions(
			Integer epID,String name,String department,String position) throws Exception
	{
		if(this.isInitiated==false){
			throw new Exception("没有初始化");
		}
		int amount=0;
		String sql="select count(*) amount from employeeprofiles where " +
				" EID like ? and Name like ? and Department like ? and Position like ?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(epID!=null){
				pstmt.setInt(1, epID);
				pstmt.setString(2, "%");
				pstmt.setString(3, "%");
				pstmt.setString(4, "%");
			}
			else{
				pstmt.setString(1, "%");
				if(name!=null && name.length()!=0)
					pstmt.setString(2, name);
				else
					pstmt.setString(2, "%");
				if(department!=null && department.length()!=0)
					pstmt.setString(3, department);
				else
					pstmt.setString(3, "%");
				if(position !=null && position.length()!=0)
					pstmt.setString(4, position);
				else
					pstmt.setString(4, "%");
			}
			rs = pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				amount=rs.getInt("amount");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		finally{
			close();
		}
		return amount;
	}
	
	public boolean delete(Integer epID) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(epID==null)
			return false;
		String sql="delete from EmployeeProfiles where EID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, epID);
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
	
	public boolean update(EmployeeProfileDTO epdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(epdto==null)
			return false;
		String sql="update EmployeeProfiles set Name=?,Department=?,Position=?,CellPhone=?,Email=?,IDCard=?," +
		"Remarks=? where EID=?";
		try {
			
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, epdto.getName());
			pstmt.setString(2,epdto.getDepartment());
			pstmt.setString(3,epdto.getPosition());
			pstmt.setString(4,epdto.getCellPhone());
			pstmt.setString(5,epdto.getEmail());
			pstmt.setString(6,epdto.getIDCard());
			pstmt.setString(7,epdto.getRemarks());
			pstmt.setInt(8,epdto.getEID());
			Tools.print(sql);
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
	
	public boolean insert(EmployeeProfileDTO epdto) throws Exception
	{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(epdto==null)
			return false;
		String sql="insert into " +
				" EmployeeProfiles(EID,Name,Department,Position,CellPhone,Email,IDCard,Remarks) " +
				" values(null,?,?,?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1,epdto.getName());
			pstmt.setString(2,epdto.getDepartment());
			pstmt.setString(3,epdto.getPosition());
			pstmt.setString(4,epdto.getCellPhone());
			pstmt.setString(5,epdto.getEmail());
			pstmt.setString(6,epdto.getIDCard());
			pstmt.setString(7,epdto.getRemarks());
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
	
	public ArrayList<EmployeeProfileDTO> getByConditions(
			Integer epID,String name,String department,String position,int start,int limit) throws Exception
	{
		if(this.isInitiated==false){
			throw new Exception("没有初始化");
		}
		ArrayList<EmployeeProfileDTO> allEPs=new ArrayList<EmployeeProfileDTO>();
		String sql="select * from employeeprofiles where " +
		" EID like ? and Name like ? and Department like ? and Position like ? limit ?,?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(epID!=null){
				pstmt.setInt(1, epID);
				pstmt.setString(2, "%");
				pstmt.setString(3, "%");
				pstmt.setString(4, "%");
			}
			else{
				pstmt.setString(1, "%");
				if(name!=null && name.length()!=0)
					pstmt.setString(2, name);
				else
					pstmt.setString(2, "%");
				if(department!=null && department.length()!=0)
					pstmt.setString(3, department);
				else
					pstmt.setString(3, "%");
				if(position !=null && position.length()!=0)
					pstmt.setString(4, position);
				else
					pstmt.setString(4, "%");
			}
			pstmt.setInt(5, start);
			pstmt.setInt(6, limit);
			rs = pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()) {
	                EmployeeProfileDTO ep= new EmployeeProfileDTO ();
	                ep.setEID(rs.getInt("EID"));
	                ep.setCellPhone(rs.getString("CellPhone"));
	                ep.setDepartment(rs.getString("Department"));
	                ep.setEmail(rs.getString("Email"));
	                ep.setIDCard(rs.getString("IDCard"));
	                ep.setRemarks(rs.getString("Remarks"));
	                ep.setName(rs.getString("Name"));
	                ep.setPosition(rs.getString("Position"));
	                
	                allEPs.add(ep);
	            }
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return allEPs;
	}
	
	
	public EmployeeProfileDTO getDTOByEID(Integer eid) throws Exception{
		//申明对象
		EmployeeProfileDTO ep=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(eid==null)
			return null;
		String sql="select * from employeeprofiles where EID like ?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1,eid);
			rs=pstmt.executeQuery();

			if(rs!=null && rs.next())
			{
				ep= new EmployeeProfileDTO ();
	            ep.setEID(rs.getInt("EID"));
	            ep.setCellPhone(rs.getString("CellPhone"));
	            ep.setDepartment(rs.getString("Department"));
	            ep.setEmail(rs.getString("Email"));
	            ep.setIDCard(rs.getString("IDCard"));
	            ep.setRemarks(rs.getString("Remarks"));
	            ep.setName(rs.getString("Name"));
	            ep.setPosition(rs.getString("Position"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return ep;
	}
	
	public ArrayList<EmployeeProfileDTO> getAllEPs() throws Exception
	{
		if(this.isInitiated==false){
			throw new Exception("没有初始化");
		}
		ArrayList<EmployeeProfileDTO> allEPs=new ArrayList<EmployeeProfileDTO>();
		
		String sql="select * from employeeprofiles";
		Tools.print(sql);
		try {
			pstmt=this.mysqlTools.getPreparedStatement(sql);
			rs= pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()) {
	                EmployeeProfileDTO ep= new EmployeeProfileDTO ();
	                ep.setEID(rs.getInt("EID"));
	                ep.setCellPhone(rs.getString("CellPhone"));
	                ep.setDepartment(rs.getString("Department"));
	                ep.setEmail(rs.getString("Email"));
	                ep.setIDCard(rs.getString("IDCard"));
	                ep.setRemarks(rs.getString("Remarks"));
	                ep.setName(rs.getString("Name"));
	                ep.setPosition(rs.getString("Position"));
	                
	                allEPs.add(ep);
	            }
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return allEPs;
	}

}
