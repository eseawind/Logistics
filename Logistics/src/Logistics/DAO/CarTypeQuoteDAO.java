package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DAO.*;
import Logistics.DTO.*;

public class CarTypeQuoteDAO extends BaseDAO {
	public CarTypeQuoteDTO getDTOByID(Integer fcid,Integer frid,Integer ctid) throws Exception{
		//申明对象
		CarTypeQuoteDTO cqdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(fcid==null || frid==null || ctid==null)
			return null;
		String sql="select * from CarTypeQuotes where FreightContractorID=? " +
				" and FreightRouteID=? and CarTypeID=? ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, fcid);
			pstmt.setInt(2, frid);
			pstmt.setInt(3, ctid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				cqdto= new CarTypeQuoteDTO();
				cqdto.setFreightContractorID(rs.getInt("CarTypeQuotes.FreightContractorID"));
				cqdto.setFreightRouteID(rs.getInt("CarTypeQuotes.FreightRouteID"));
				cqdto.setCarTypeID(rs.getInt("CarTypeQuotes.CarTypeID"));
				cqdto.setPrice(rs.getDouble("CarTypeQuotes.Price"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return cqdto;
	}
	public boolean insert(CarTypeQuoteDTO cqdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(cqdto==null)
			return false;
		String sql="insert into " +
				" CarTypeQuotes(FreightContractorID,FreightRouteID,CarTypeID,Price)" +
				" values(?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, cqdto.getFreightContractorID());
			pstmt.setInt(2, cqdto.getFreightRouteID());
			pstmt.setInt(3, cqdto.getCarTypeID());
			pstmt.setDouble(4, cqdto.getPrice());
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
	
	@Deprecated
	public boolean update(CarTypeQuoteDTO cqdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(cqdto==null)
			return false;
		String sql="update CarTypeQuotes set " +
			" Price=?" +
			" where FreightContractorID=? and FreightRouteID=? and CarTypeID=? ";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDouble(1, cqdto.getPrice());
			pstmt.setInt(2, cqdto.getFreightContractorID());
			pstmt.setInt(3, cqdto.getFreightRouteID());
			pstmt.setInt(4, cqdto.getCarTypeID());
			
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
	public boolean delete(Integer fcid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(fcid==null)
			return false;
		String sql="delete from CarTypeQuotes " +
				" where FreightContractorID=? ";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, fcid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(Integer fcid,Integer frid,Integer ctid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(fcid==null || frid==null || ctid==null)
			return false;
		String sql="delete from CarTypeQuotes " +
				" where FreightContractorID=? and FreightRouteID=? and CarTypeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, fcid);
			pstmt.setInt(2, frid);
			pstmt.setInt(3, ctid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean delete(ArrayList<CarTypeQuoteDTO> ctqList) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(ctqList==null)
			return false;
		String sql="delete from CarTypeQuotes " +
				" where FreightContractorID=? and FreightRouteID=? and CarTypeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(CarTypeQuoteDTO id:ctqList){
				pstmt.setString(1, Tools.toString(id.getFreightContractorID()));
				pstmt.setString(2, Tools.toString(id.getFreightRouteID()));
				pstmt.setString(3, Tools.toString(id.getCarTypeID()));
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	public ArrayList<CarTypeQuoteDTO> queryOnCondition(Integer fcid,int start,int limit) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(fcid==null)
			return null;
		ArrayList<CarTypeQuoteDTO> clist=new ArrayList<CarTypeQuoteDTO>();
		String sql="select * " +
				" from CarTypeQuotes " +
				" where CarTypeQuotes.FreightContractorID=? " +
				" limit ?,?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, fcid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					CarTypeQuoteDTO cqdto=new CarTypeQuoteDTO();
					cqdto.setFreightContractorID(rs.getInt("CarTypeQuotes.FreightContractorID"));
					cqdto.setFreightRouteID(rs.getInt("CarTypeQuotes.FreightRouteID"));
					cqdto.setCarTypeID(rs.getInt("CarTypeQuotes.CarTypeID"));
					cqdto.setPrice(rs.getDouble("CarTypeQuotes.Price"));
					
					clist.add(cqdto);
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
	
	public int queryQualifiedAmount(Integer fcid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql="select count(*) Amount" +
				" from CarTypeQuotes " +
				" where CarTypeQuotes.FreightContractorID=? ";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, fcid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				amount=rs.getInt("Amount");
			}
			return amount;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			close();
		}
	}
	
	
}
