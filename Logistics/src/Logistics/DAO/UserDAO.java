package Logistics.DAO;
import Logistics.Common.Tools;
import Logistics.DTO.*;
import java.sql.*;
import java.util.ArrayList;

public class UserDAO extends BaseDAO{
	
	public boolean deleteUser(String uid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(uid==null || uid.length()==0)
			return false;
		//传入参数的合法性
		String sql="delete from users where UID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, uid);
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
	
	public boolean updateUser(UserDTO udto,String oldUID) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(udto==null || oldUID==null)
			return false;
		String sql="update users set UPassword=?, UDateCreated=?, URoleID= ?, UState= ?, UID=? where UID=?";

		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, udto.getUPassword());
			pstmt.setDate(2, udto.getUDateCreated());
			pstmt.setInt(3, udto.getURoleID());
			pstmt.setString(4, udto.getUState());
			pstmt.setString(5, udto.getUID());
			pstmt.setString(6, oldUID);
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
	
	public boolean addUser(UserDTO udto) throws Exception
	{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(udto==null)
			return false;
		String sql="insert into users(UID,UPassword,UDateCreated,URoleID,UState) " +
				" values(?,?,?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, udto.getUID());
			pstmt.setString(2, udto.getUPassword());
			pstmt.setDate(3, udto.getUDateCreated());
			pstmt.setInt(4, udto.getURoleID());
			pstmt.setString(5, udto.getUState());
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
	
	/***
	 * 
	 * @param uID
	 * @return
	 * @throws Exception
	 */
	public UserDTO getDTOByID(String uID) throws Exception
	{
		UserDTO u=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(uID == null || uID.length()==0)
			return null;
		String sql="select * from Users where UID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, uID);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				u=new UserDTO();
				u.setUID(rs.getString("UID"));
				u.setUDateCreated(rs.getDate("UDateCreated"));
				u.setUPassword(rs.getString("UPassword"));
				u.setURoleID(rs.getInt("URoleID"));
	            u.setUState(rs.getString("UState"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		
		return u;
		
	}
	public  ArrayList<UserDTO> getAllUsers() throws ClassNotFoundException, Exception
	{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<UserDTO> allEPs=new ArrayList<UserDTO>();
		String sql="select * from Users";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.executeUpdate();
			if(rs!=null)
			{
				while(rs.next()) {
	                UserDTO u= new UserDTO ();
	                u.setUDateCreated(rs.getDate("UDateCreated"));
	                u.setUID(rs.getString("UID"));
	                u.setUPassword(rs.getString("UPassword"));
	                u.setURoleID(rs.getInt("URoleID"));
	                u.setUState(rs.getString("UState"));
	                allEPs.add(u);
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
		return allEPs;
	}
	public int getCountByCondition(String uID,String uState) throws Exception{
		
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		int userAmount=0;
		String sql=" select count(*) userAmount from Users,Roles " +
		"where ("+Tools.isVoid(uID)+" or UID= ?) " +
		" and ("+Tools.isVoid(uState)+" or UState = ? ) " +
		" and Users.URoleID=Roles.RoleID " +
		" group by UID ";
		
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, uID);
			pstmt.setString(2, uState);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				userAmount=rs.getInt("userAmount");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
		finally{
			close();
		}
		return userAmount;
	}
	
	public ArrayList<UserDTO> getUsersByCondition(String uID,String uState,int start,int limit) throws Exception
	{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(start<0)
			start=0;
		if(limit<0)
			limit=0;
		ArrayList<UserDTO> allEPs=null;
		String sql=" select * from Users,Roles " +
				"where ("+Tools.isVoid(uID)+" or UID= ?) " +
				" and ("+Tools.isVoid(uState)+" or UState = ? ) " +
				" and Users.URoleID=Roles.RoleID " +
				" group by UID " +
				"limit ?,?";
		Tools.print(sql);
		
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, uID);
			pstmt.setString(2, uState);
			pstmt.setInt(3, start);
			pstmt.setInt(4, limit);
			rs=pstmt.executeQuery();
			
			if(rs!=null)
			{
				//如果结果集不为空，则将结果集中的数据放入allEps中
				allEPs=new ArrayList<UserDTO>();
				while(rs.next()) {
	                UserDTO u= new UserDTO ();
	                u.setUDateCreated(rs.getDate("UDateCreated"));
	                u.setUID(rs.getString("UID"));
	                u.setUPassword(rs.getString("UPassword"));
	                u.setURoleID(rs.getInt("URoleID"));
	                u.setUState(rs.getString("UState"));
	                RoleDTO role=new RoleDTO();
	                role.setRoleID(rs.getInt("RoleID"));
	                role.setRoleName(rs.getString("RoleName"));
	                u.setRoleDTO(role);
	                allEPs.add(u);
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
		return allEPs;
		
	}

}
