package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.PermissionDTO;
import Logistics.DTO.RoleDTO;
import Logistics.DTO.UserDTO;

public class RoleDAO extends BaseDAO {
	public ArrayList<RoleDTO> getAllRoles() throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<RoleDTO> allRoles=null;
		RoleDTO rdto=null;
		String sql="select * from Roles";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				allRoles=new ArrayList<RoleDTO>();
				while(rs.next()) {
					rdto=new RoleDTO();
					rdto.setRoleID(rs.getInt("RoleID"));
					rdto.setRoleName(rs.getString("RoleName"));
	                allRoles.add(rdto);
	            }
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		
		return allRoles;
	}
	
	public ArrayList<RoleDTO> queryOnCondtition(int start,int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<RoleDTO> res=null;
		RoleDTO rdto=null;
		String sql="select * from Roles limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				res=new ArrayList<RoleDTO>();
				while(rs.next()) {
					rdto=new RoleDTO();
					rdto.setRoleID(rs.getInt("RoleID"));
					rdto.setRoleName(rs.getString("RoleName"));
					rdto.setRemarks(rs.getString("Remarks"));
	                res.add(rdto);
	            }
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		
		return res;
	}
	
	public int queryQualifiedAmount() throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql="select count(*) Amount from Roles ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				amount=rs.getInt("Amount");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
		finally{
			close();
		}
		
		return amount;
	}
	
	public RoleDTO getDTOByID(Integer rid) throws Exception
	{
		//申明对象
		RoleDTO res=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(Tools.isVoid(rid))
			return null;
		String sql="select * from Roles where RoleID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, rid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				res=new RoleDTO();
				res.setRoleID(rs.getInt("RoleID"));
				res.setRoleName(rs.getString("RoleName"));
				res.setRemarks(rs.getString("Remarks"));
				
			}
			return res;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		
		
	}
	
	public boolean delete(Integer rid) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(Tools.isVoid(rid))
			return false;
		
		String[] sqls={"delete from Roles where RoleID=?","delete from users where URoleID=?","delete from permissions where roleID=?"};
		try{
			for(String sql:sqls){
				pstmt=mysqlTools.getPreparedStatement(sql);
				pstmt.setInt(1, rid);
				pstmt.executeUpdate();
			}
			return true;
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		finally{
			close();
		}
	}

	public ArrayList<PermissionDTO> getPermissions(Integer rid) throws Exception{
		
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<PermissionDTO> res=null;
		PermissionDTO rdto=null;
		String sql="select * from Permissions where roleID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, rid);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				res=new ArrayList<PermissionDTO>();
				while(rs.next()) {
					rdto=new PermissionDTO();
					rdto.setRoleID(rs.getInt("RoleID"));
					rdto.setRoleName(rs.getString("RoleName"));
					rdto.setActionName(rs.getString("ActionName"));
					rdto.setPermission(rs.getInt("Permission"));
	                res.add(rdto);
	            }
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		
		return res;
	}
	
	
	public boolean insert(RoleDTO role,ArrayList<PermissionDTO> permissions) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(role==null || permissions==null)
			return false;
		
		try{
			String insertRolesql="insert into roles(roleID,rolename,remarks) values(null,?,?);";
			pstmt=mysqlTools.getPreparedStatement(insertRolesql);
			pstmt.setString(1, role.getRoleName());
			pstmt.setString(2, role.getRemarks());
			if(pstmt.executeUpdate()!=1)
				return false;
			int roleid=this.queryLastInsertID();
			String insertPermissionSql="insert into Permissions(roleID,roleName,actionName,permission) values(?,?,?,?)";
			for(PermissionDTO permission:permissions){
				pstmt=mysqlTools.getPreparedStatement(insertPermissionSql);
				pstmt.setInt(1, roleid);
				pstmt.setString(2, role.getRoleName());
				pstmt.setString(3, permission.getActionName());
				pstmt.setInt(4, permission.getPermission());
				if(pstmt.executeUpdate()!=1){
					return false;
				}
			}
			
			return true;
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		finally{
			close();
		}
	}
	
	public boolean update(RoleDTO role,ArrayList<PermissionDTO> permissions) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(role==null || permissions==null)
			return false;
		
		try{
			//update the role 
			String updateRoleSql="update roles set roleName=?,remarks=? where roleID=? ";
			pstmt=mysqlTools.getPreparedStatement(updateRoleSql);
			pstmt.setString(1, role.getRoleName());
			pstmt.setString(2, role.getRemarks());
			pstmt.setInt(3, role.getRoleID());
			if(pstmt.executeUpdate()!=1)
				return false;
			
			//delete the old permissions
			String deletePermissionSql="delete from Permissions where roleid=?";
			pstmt=mysqlTools.getPreparedStatement(deletePermissionSql);
			pstmt.setInt(1, role.getRoleID());
			if(0>=pstmt.executeUpdate()){
				return false;
			}
			//insert the new permissions to data base
			String insertPermissionSql="insert into Permissions(roleID,roleName,actionName,permission) values(?,?,?,?)";
			for(PermissionDTO permission:permissions){
				pstmt=mysqlTools.getPreparedStatement(insertPermissionSql);
				pstmt.setInt(1, role.getRoleID());
				pstmt.setString(2, role.getRoleName());
				pstmt.setString(3, permission.getActionName());
				pstmt.setInt(4, permission.getPermission());
				if(pstmt.executeUpdate()!=1){
					return false;
				}
			}
			
			return true;
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		finally{
			close();
		}
	}
	
	
}
