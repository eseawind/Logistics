package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DAO.*;
import Logistics.DTO.*;

public class FreightRouteQuoteDAO extends BaseDAO {
	public FreightRouteQuoteDTO getDTOByID(Integer fcid,Integer frid) throws Exception{
		//申明对象
		FreightRouteQuoteDTO frqdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(fcid==null || frid==null)
			return null;
		String sql="select * from FreightRouteQuotes where FreightContractorID=? " +
				" and FreightRouteID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, fcid);
			pstmt.setInt(2, frid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				frqdto= new FreightRouteQuoteDTO();
				frqdto.setFreightContractorID(rs.getInt("FreightRouteQuotes.FreightContractorID"));
				frqdto.setFreightRouteID(rs.getInt("FreightRouteQuotes.FreightRouteID"));
				
				frqdto.setPriceByAmount(rs.getDouble("FreightRouteQuotes.PriceByAmount"));
				frqdto.setPriceByVolume(rs.getDouble("FreightRouteQuotes.PriceByVolume"));
				frqdto.setPriceByWeight(rs.getDouble("FreightRouteQuotes.PriceByWeight"));
				frqdto.setPriceDirectly(rs.getDouble("FreightRouteQuotes.PriceDirectly"));
				frqdto.setDeliveryQuote(rs.getDouble("DeliveryQuote"));
				frqdto.setExtraQuote(rs.getDouble("extraQuote"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return frqdto;
	}
	public boolean insert(FreightRouteQuoteDTO frqdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(frqdto==null)
			return false;
		String sql="insert into " +
				" FreightRouteQuotes(FreightContractorID,FreightRouteID,PriceByAmount,PriceByVolume," +
				" PriceByWeight,PriceDirectly,deliveryQuote,extraQuote)" +
				" values(?,?,?,?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, frqdto.getFreightContractorID());
			pstmt.setInt(2, frqdto.getFreightRouteID());
			pstmt.setDouble(3, frqdto.getPriceByAmount());
			pstmt.setDouble(4, frqdto.getPriceByVolume());
			pstmt.setDouble(5, frqdto.getPriceByWeight());
			pstmt.setDouble(6, frqdto.getPriceDirectly());
			pstmt.setDouble(7, frqdto.getDeliveryQuote());
			pstmt.setDouble(8, frqdto.getExtraQuote());
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
	public boolean update(FreightRouteQuoteDTO frqdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(frqdto==null)
			return false;
		String sql="update FreightRouteQuotes set " +
			" PriceByAmount=?, PriceByVolume=?,PriceByWeight=?, PriceDirectly=? " +
			" where FreightContractorID=? and FreightRouteID=? ";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDouble(1, frqdto.getPriceByAmount());
			pstmt.setDouble(2, frqdto.getPriceByVolume());
			pstmt.setDouble(3, frqdto.getPriceByWeight());
			pstmt.setDouble(4, frqdto.getPriceDirectly());
			pstmt.setInt(5, frqdto.getFreightContractorID());
			pstmt.setInt(6, frqdto.getFreightRouteID());
			
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
		String sql="delete from FreightRouteQuotes " +
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
	public boolean delete(Integer fcid,Integer frid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(fcid==null)
			return false;
		String sql="delete from FreightRouteQuotes " +
				" where FreightContractorID=? and FreightRouteID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, fcid);
			pstmt.setInt(2, frid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(ArrayList<FreightRouteQuoteDTO> frqList) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(frqList==null)
			return false;
		String sql="delete from FreightRouteQuotes " +
				" where FreightContractorID=? and FreightRouteID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(FreightRouteQuoteDTO id:frqList){
				pstmt.setInt(1, id.getFreightContractorID());
				pstmt.setInt(2, id.getFreightRouteID());
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	public ArrayList<FreightRouteQuoteDTO> queryOnCondition(Integer fcid,int start,int limit) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<FreightRouteQuoteDTO> clist=new ArrayList<FreightRouteQuoteDTO>();
		String sql="select * " +
				" from FreightRouteQuotes " +
				" where FreightContractorID=? " +
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
					
					FreightRouteQuoteDTO frqdto=new FreightRouteQuoteDTO();
					frqdto.setFreightContractorID(rs.getInt("FreightRouteQuotes.FreightContractorID"));
					frqdto.setFreightRouteID(rs.getInt("FreightRouteQuotes.FreightRouteID"));
					
					frqdto.setPriceByAmount(rs.getDouble("FreightRouteQuotes.PriceByAmount"));
					frqdto.setPriceByVolume(rs.getDouble("FreightRouteQuotes.PriceByVolume"));
					frqdto.setPriceByWeight(rs.getDouble("FreightRouteQuotes.PriceByWeight"));
					frqdto.setPriceDirectly(rs.getDouble("FreightRouteQuotes.PriceDirectly"));
					frqdto.setDeliveryQuote(rs.getDouble("DeliveryQuote"));
					frqdto.setExtraQuote(rs.getDouble("extraQuote"));
				
					clist.add(frqdto);
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
		String sql="select count(*) Amount " +
				" from FreightRouteQuotes " +
				" where FreightContractorID=? ";
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
