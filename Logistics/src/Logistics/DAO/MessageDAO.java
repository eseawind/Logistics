package Logistics.DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.*;

public class MessageDAO extends BaseDAO {
	
	public MessageDTO getDTOByID(Integer mid) throws Exception{
		//申明对象
		MessageDTO bdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(Tools.isVoid(mid))
			return null;
		String sql="select * from Messages where MessageID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, mid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				bdto=new MessageDTO();
				bdto.setMessageID(rs.getInt("MessageID"));
				bdto.setHeader(rs.getString("Header"));
				bdto.setBody(rs.getString("Body"));
				bdto.setDatePosted(rs.getDate("DatePosted"));
				bdto.setType(rs.getString("Type"));
				bdto.setAttachment(rs.getString("Attachment"));
				bdto.setOriginName(rs.getString("OriginName"));
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return bdto;
	}
	
	public boolean insert(MessageDTO bdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(bdto==null)
			return false;
		String sql="insert into " +
				" Messages(MessageID" +
				",Header" +
				",Body" +
				",DatePosted" +
				",Type" +
				",Attachment" +
				",OriginName) " +
				" values(null,?,?,?,?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, bdto.getHeader());
			pstmt.setString(2, bdto.getBody());
			pstmt.setDate(3, Tools.currDate());
			pstmt.setString(4, bdto.getType());
			pstmt.setString(5, bdto.getAttachment());
			pstmt.setString(6, bdto.getOriginName());
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
	
	
	
	public boolean update(MessageDTO bdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(bdto==null)
			return false;
		String sql="update Messages set " +
				"Header=?" +
				",Body=?" +
				",Type=?" +
				",Attachment=?" +
				",OriginName=? where MessageID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, bdto.getHeader());
			pstmt.setString(2, bdto.getBody());
			pstmt.setString(3, bdto.getType());
			pstmt.setString(4, bdto.getAttachment());
			pstmt.setString(5, bdto.getOriginName());
			pstmt.setInt(6, bdto.getMessageID());
			
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
	
	public boolean delete(Integer mid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(mid==null)
			return false;
		String sql="delete from Messages where MessageID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, mid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(ArrayList<Integer> mids) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(mids==null)
			return false;
		String sql="delete from Messages where MessageID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(Integer mid:mids){
				pstmt.setInt(1, mid);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public ArrayList<MessageDTO> queryOnCondition(MessageDTO dto,Date startDay,Date endDay,int start,int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<MessageDTO> ilist=new ArrayList<MessageDTO>();
		String sql=" select * from Messages" +
				" where ("+Tools.isVoid(dto.getType())+" or Type = ? )" +
				" and ( "+Tools.isVoid(startDay)+" or DatePosted >= ? )" +
				" and ( "+Tools.isVoid(endDay)+" or DatePosted <= ? )" +
				" limit ?,?";
		Tools.print(sql);
		try{
			
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getType());
			pstmt.setDate(2, startDay);
			pstmt.setDate(3, endDay);
			pstmt.setInt(4, start);
			pstmt.setInt(5, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					MessageDTO bdto=new MessageDTO();
					bdto.setMessageID(rs.getInt("MessageID"));
					bdto.setHeader(rs.getString("Header"));
					bdto.setBody(rs.getString("Body"));
					bdto.setDatePosted(rs.getDate("DatePosted"));
					bdto.setType(rs.getString("Type"));
					bdto.setAttachment(rs.getString("Attachment"));
					bdto.setOriginName(rs.getString("OriginName"));
					ilist.add(bdto);
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
	
	public int queryAmountByCondition(MessageDTO dto,Date startDay,Date endDay) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from Messages " +
		" where ("+Tools.isVoid(dto.getType())+" or Type = ? )" +
		" and ( "+Tools.isVoid(startDay)+" or DatePosted >= ? )" +
		" and ( "+Tools.isVoid(endDay)+" or DatePosted <= ? )";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getType());
			pstmt.setDate(2, startDay);
			pstmt.setDate(3, endDay);
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
	
	public ArrayList<MessageDTO> queryNews(int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<MessageDTO> ilist=new ArrayList<MessageDTO>();
		String sql=" select * from Messages" +
				" where Type=? order by DatePosted desc" +
				" limit 0,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(MessageDTO.Type type:MessageDTO.Type.values()){
				pstmt.setString(1, type.toString());
				pstmt.setInt(2, limit);
				rs=pstmt.executeQuery();
				if(rs!=null)
				{
					while(rs.next()){
						MessageDTO bdto=new MessageDTO();
						bdto.setMessageID(rs.getInt("MessageID"));
						bdto.setHeader(rs.getString("Header"));
						bdto.setBody(rs.getString("Body"));
						bdto.setDatePosted(rs.getDate("DatePosted"));
						bdto.setType(rs.getString("Type"));
						bdto.setAttachment(rs.getString("Attachment"));
						bdto.setOriginName(rs.getString("OriginName"));
						bdto.setMsgType(type);
						ilist.add(bdto);
					}
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
}
