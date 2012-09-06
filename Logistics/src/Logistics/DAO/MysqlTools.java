package Logistics.DAO;
import java.sql.*;

import Logistics.Common.Tools;
public class MysqlTools {
	//静态成员
	private static String username="root";
	private static String password="hywl20120604";
	//动态成员
	private Connection dbConnection=null;
	
	public MysqlTools() throws Exception{
		dbConnection=getConnection();
		dbConnection.setAutoCommit(false);
	}
	public void commit(){
		try {
			dbConnection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void rollback(){
		try {
			dbConnection.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close(){
		closeConnection(dbConnection);
		dbConnection=null;
	}
	public static void closePreparedStatement(PreparedStatement pstmt)
	{
		try{
    		if(pstmt!=null)
    		{
    			pstmt.close();
    		}	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		Tools.printErr("Can't close Statement");
    	}
	}
	public PreparedStatement getPreparedStatement(String sql) throws Exception{
		if(dbConnection==null)
			throw new Exception("未获得数据库连接，无法获得PreparedStatement");
		return dbConnection.prepareStatement(sql);
	}
	public static void closeConnection(Connection conn)
	{
		if(conn!=null)
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Tools.printErr("Can't close connection");
			}
		}
	}
    public static void closeStatement(Statement stmt)
    {
    	try{
    		if(stmt!=null)
    		{
    			stmt.close();
    		}
    			
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		Tools.printErr("Can't close Statement");
    	}
    }
    public static void closeResult(ResultSet rs)
    {
    	if(rs!=null)
    	{
    		try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Tools.printErr("Can't close result set.");
			}
    	}
    	
    }
    public static  Statement getStatement(Connection conn) throws Exception
    {
    	// 获取表达式
    	if(conn==null)
    	{
    		throw new Exception("无法获取statement，conn为空值");
    	}
    	Statement stmt = null;         //数据库表达式
        try {
        	if(conn!=null)
			stmt = conn.createStatement();
        	return stmt;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Tools.printErr("无法获取statement");
			throw new Exception("无法获取statement",e);
		}
    }
	public static Connection getConnection() throws Exception
	{
		//注册JDBC类
		try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException ex) {
            ex.printStackTrace();
            Tools.printErr("getConnection: Can't find name");
            throw new Exception("无法找到com.mysql.jdbc.Driver",ex);
        }
        //建立连接
        Connection conn = null;        //数据库连接
        try {
            //获取数据库的连接
            conn = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/logisticsdatabase?useUnicode=true&characterEncoding=UTF-8",username,password);
            return conn;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Tools.printErr("Method:getConnection Can't build connection");
            throw new Exception("无法建立连接",ex);
        }
	}

}

