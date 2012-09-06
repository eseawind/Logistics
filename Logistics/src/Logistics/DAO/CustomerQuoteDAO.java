package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DAO.*;
import Logistics.DTO.*;

public class CustomerQuoteDAO extends BaseDAO {
	public CustomerQuoteDTO getDTOByID(Integer cid,Integer frid) throws Exception{
		//申明对象
		CustomerQuoteDTO cdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(cid==null || frid==null)
			return null;
		String sql="select * from CustomerQuotes where customerID=? and freightRouteID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, cid);
			pstmt.setInt(2, frid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				cdto= new CustomerQuoteDTO();
				cdto.setCustomerID(rs.getInt("CustomerID"));
				cdto.setFreightRouteID(rs.getInt("freightRouteID"));
				cdto.setPriceByAmount(rs.getDouble("PriceByAmount"));
				cdto.setPriceByVolume(rs.getDouble("priceByVolume"));
				cdto.setPriceByWeight(rs.getDouble("PriceByWeight"));
				cdto.setTakingQuote(rs.getDouble("TakingQuote"));
				cdto.setDeliveryQuote(rs.getDouble("DeliveryQuote"));
				
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
	public boolean insert(CustomerQuoteDTO cdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(cdto==null)
			return false;
		String sql="insert into " +
				" CustomerQuotes(CustomerID,FreightRouteID,priceByAmount,priceByVolume,priceByWeight,takingQuote,deliveryQuote) " +
				" values(?,?,?,?,?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, cdto.getCustomerID());
			pstmt.setInt(2, cdto.getFreightRouteID());
			pstmt.setDouble(3, cdto.getPriceByAmount());
			pstmt.setDouble(4, cdto.getPriceByVolume());
			pstmt.setDouble(5, cdto.getPriceByWeight());
			pstmt.setDouble(6, cdto.getTakingQuote());
			pstmt.setDouble(7, cdto.getDeliveryQuote());
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
	public boolean update(CustomerQuoteDTO cdto) throws Exception{
//		//判断是否已经初始化过
//		if(this.isInitiated==false)
//		{
//			throw new Exception("DAO没有初始化");
//		}
//		//传入参数的合法性
//		if(cdto==null)
//			return false;
//		String sql="update CustomerQuotes set " +
//			" Type=?,Name=?,Contact=?,Phone=?,Email=?,Address=?,Remarks" +
//			"=?,FreightCostPerCount=?,FreightCostPerVolume=?,FreightCostPerWeight" +
//			"=?,StockInCostPerCount=?,StockInCostPerVolume=?,StockInCostPerWeight" +
//			"=?,StockOutCostPerCount=?,StockOutCostPerVolume=?,StockOutCostPerWeight" +
//			"=?,StockCostPerCount=?,StockCostPerVolume=?,StockCostPerWeight=? " +
//			"where CustomerQuoteID=?";
//		Tools.print(sql);
//		try {
//			pstmt=mysqlTools.getPreparedStatement(sql);
//			pstmt.setString(1, cdto.getType());
//			pstmt.setString(2, cdto.getName());
//			pstmt.setString(3, cdto.getContact());
//			pstmt.setString(4, cdto.getPhone());
//			pstmt.setString(5, cdto.getEmail());
//			pstmt.setString(6, cdto.getAddress());
//			pstmt.setString(7, cdto.getRemarks());
//			pstmt.setDouble(8, cdto.getFreightCostPerCount());
//			pstmt.setDouble(9, cdto.getFreightCostPerVolume());
//			pstmt.setDouble(10, cdto.getFreightCostPerWeight());
//			pstmt.setDouble(11, cdto.getStockInCostPerCount());
//			pstmt.setDouble(12, cdto.getStockInCostPerVolume());
//			pstmt.setDouble(13, cdto.getStockInCostPerWeight());
//			pstmt.setDouble(14, cdto.getStockOutCostPerCount());
//			pstmt.setDouble(15, cdto.getStockOutCostPerVolume());
//			pstmt.setDouble(16, cdto.getStockOutCostPerWeight());
//			pstmt.setDouble(17, cdto.getStockCostPerCount());
//			pstmt.setDouble(18, cdto.getStockCostPerVolume());
//			pstmt.setDouble(19, cdto.getStockCostPerWeight());
//			pstmt.setInt(20, cdto.getCustomerQuoteID());
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		}
//		finally{
//			close();
//		}
		return true;
	}
	
	public boolean delete(Integer cid,Integer frid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(cid==null)
			return false;
		String sql="delete from CustomerQuotes where CustomerID=? and FreightRouteID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, cid);
			pstmt.setInt(2, frid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(ArrayList<CustomerQuoteDTO> ids) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(ids==null)
			return false;
		String sql="delete from CustomerQuotes where CustomerID=? and FreightRouteID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(CustomerQuoteDTO id:ids){
				pstmt.setInt(1, id.getCustomerID());
				pstmt.setInt(2, id.getFreightRouteID());
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Deprecated
	public ArrayList<CustomerQuoteDTO> queryNameID(String cusType) throws Exception{
		return null;
//		//判断是否已经初始化过
//		if(this.isInitiated==false)
//		{
//			throw new Exception("DAO没有初始化");
//		}
//		ArrayList<CustomerQuoteDTO> clist=new ArrayList<CustomerQuoteDTO>();
//		String sql="select * from CustomerQuotes where ( "+Tools.isVoid(cusType)+" or Type= ?)";
//		Tools.print(sql);
//		try {
//			pstmt=mysqlTools.getPreparedStatement(sql);
//			pstmt.setString(1, cusType);
//			rs=pstmt.executeQuery();
//			if(rs!=null)
//			{
//				while(rs.next()){
//					CustomerQuoteDTO cdto=new CustomerQuoteDTO();
//					cdto.setCustomerQuoteID(rs.getInt("CustomerQuoteID"));
//					cdto.setName(rs.getString("Name"));
//					cdto.setType(rs.getString("Type"));
//					clist.add(cdto);
//				}
//			}
//			return clist;
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//		finally{
//			close();
//		}
	}
	
	public ArrayList<CustomerQuoteDTO> queryByCondition(Integer cid,int start,int limit) throws Exception{
		
		Tools.print("queryByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<CustomerQuoteDTO> ctlist=new ArrayList<CustomerQuoteDTO>();
		String sql=" select * from CustomerQuotes " +
				" where CustomerID = ? " +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, cid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					CustomerQuoteDTO cdto=new CustomerQuoteDTO();
					cdto.setCustomerID(rs.getInt("CustomerID"));
					cdto.setFreightRouteID(rs.getInt("freightRouteID"));
					cdto.setPriceByAmount(rs.getDouble("PriceByAmount"));
					cdto.setPriceByVolume(rs.getDouble("priceByVolume"));
					cdto.setPriceByWeight(rs.getDouble("PriceByWeight"));
					cdto.setTakingQuote(rs.getDouble("TakingQuote"));
					cdto.setDeliveryQuote(rs.getDouble("DeliveryQuote"));
					ctlist.add(cdto);
				}
			}
			return ctlist;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	public int queryAmountByCondition(Integer cid) throws Exception{
		
		Tools.print("queryAmountByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from CustomerQuotes" +
		" where CustomerID = ? ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, cid);
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
	
	
}
