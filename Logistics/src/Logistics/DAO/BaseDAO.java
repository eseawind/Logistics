package Logistics.DAO;
import java.sql.*;

import Logistics.Common.Tools;
public class BaseDAO{
	//protected Connection conn=null;
	//protected Statement stmt=null;
	protected PreparedStatement pstmt=null;
	protected MysqlTools mysqlTools=null;
	protected ResultSet rs=null;
	protected boolean isInitiated=false;
	public BaseDAO()
	{
	}
	public void initiate(MysqlTools mysqlTools){
		if(mysqlTools==null)
			this.isInitiated=false;
		else{
			this.mysqlTools=mysqlTools;
			isInitiated=true;
		}
	}
//	public void initiate() throws Exception
//	{
//		try {
//			conn=MysqlTools.getConnection();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new Exception("DAO初始化失败：无法获取数据库连接",e);
//		}
//		if(conn==null)
//		{
//			throw new Exception("DAO初始化失败：数据库连接为空");
//		}
//		try{
//			stmt=MysqlTools.getStatement(conn);
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new Exception("DAO初始化失败：无法获取表达式");
//		}
//		if(stmt==null)
//		{
//			throw new Exception("DAO初始化失败：表达式为空");
//		}
//		isInitiated=true;
//		
//	}
	protected boolean close()
	{
		
		MysqlTools.closeResult(rs);
		rs=null;
		MysqlTools.closePreparedStatement(pstmt);
		pstmt=null;
		return true;
	}
	
	public int queryLastInsertID() throws Exception
	{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int lastInsertID=0;
		String sql=" select last_insert_id() LastInsertID";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				lastInsertID=rs.getInt("LastInsertID");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("sql执行失败"+sql,e);
		}
		finally{
			close();
		}
		return lastInsertID;
	}
}
