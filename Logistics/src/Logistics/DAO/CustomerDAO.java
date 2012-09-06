package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DAO.*;
import Logistics.DTO.*;

public class CustomerDAO extends BaseDAO {
	public CustomerDTO getDTOByID(Integer cid) throws Exception{
		//申明对象
		CustomerDTO cdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(cid==null)
			return null;
		String sql="select * from Customers where CustomerID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, cid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				cdto= new CustomerDTO();
				cdto.setCustomerID(rs.getInt("CustomerID"));
				cdto.setName(rs.getString("Name"));
				cdto.setRemarks(rs.getString("Remarks"));
				cdto.setType(rs.getString("Type"));
				cdto.setContact(rs.getString("Contact"));
				cdto.setPhone(rs.getString("Phone"));
				cdto.setEmail(rs.getString("Email"));
				cdto.setAddress(rs.getString("Address"));
				cdto.setFreightCostPerCount(rs.getDouble("FreightCostPerCount"));
				cdto.setFreightCostPerVolume(rs.getDouble("FreightCostPerVolume"));
				cdto.setFreightCostPerWeight(rs.getDouble("FreightCostPerWeight"));
				cdto.setStockInCostPerCount(rs.getDouble("StockInCostPerCount"));
				cdto.setStockInCostPerVolume(rs.getDouble("StockInCostPerVolume"));
				cdto.setStockInCostPerWeight(rs.getDouble("StockInCostPerWeight"));
				cdto.setStockOutCostPerCount(rs.getDouble("StockOutCostPerCount"));
				cdto.setStockOutCostPerVolume(rs.getDouble("StockOutCostPerVolume"));
				cdto.setStockOutCostPerWeight(rs.getDouble("StockOutCostPerWeight"));
				cdto.setStockCostPerCount(rs.getDouble("StockCostPerCount"));
				cdto.setStockCostPerVolume(rs.getDouble("StockCostPerVolume"));
				cdto.setStockCostPerWeight(rs.getDouble("StockCostPerWeight"));
				
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
	public boolean insert(CustomerDTO cdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(cdto==null)
			return false;
		String sql="insert into " +
				" Customers(CustomerID,Type,Name,Contact,Phone,Email,Address,Remarks" +
				",FreightCostPerCount,FreightCostPerVolume,FreightCostPerWeight" +
				",StockInCostPerCount,StockInCostPerVolume,StockInCostPerWeight" +
				",StockOutCostPerCount,StockOutCostPerVolume,StockOutCostPerWeight" +
				",StockCostPerCount,StockCostPerVolume,StockCostPerWeight) " +
				" values(null,?,?,?,?,?,?,?" +
				",?,?,?,?,?,?" +
				",?,?,?,?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, cdto.getType());
			pstmt.setString(2, cdto.getName());
			pstmt.setString(3, cdto.getContact());
			pstmt.setString(4, cdto.getPhone());
			pstmt.setString(5, cdto.getEmail());
			pstmt.setString(6, cdto.getAddress());
			pstmt.setString(7, cdto.getRemarks());
			pstmt.setDouble(8, cdto.getFreightCostPerCount());
			pstmt.setDouble(9, cdto.getFreightCostPerVolume());
			pstmt.setDouble(10, cdto.getFreightCostPerWeight());
			pstmt.setDouble(11, cdto.getStockInCostPerCount());
			pstmt.setDouble(12, cdto.getStockInCostPerVolume());
			pstmt.setDouble(13, cdto.getStockInCostPerWeight());
			pstmt.setDouble(14, cdto.getStockOutCostPerCount());
			pstmt.setDouble(15, cdto.getStockOutCostPerVolume());
			pstmt.setDouble(16, cdto.getStockOutCostPerWeight());
			pstmt.setDouble(17, cdto.getStockCostPerCount());
			pstmt.setDouble(18, cdto.getStockCostPerVolume());
			pstmt.setDouble(19, cdto.getStockCostPerWeight());
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
	

	public boolean update(CustomerDTO cdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(cdto==null)
			return false;
		String sql="update Customers set " +
			" Type=?,Name=?,Contact=?,Phone=?,Email=?,Address=?,Remarks" +
			"=?,FreightCostPerCount=?,FreightCostPerVolume=?,FreightCostPerWeight" +
			"=?,StockInCostPerCount=?,StockInCostPerVolume=?,StockInCostPerWeight" +
			"=?,StockOutCostPerCount=?,StockOutCostPerVolume=?,StockOutCostPerWeight" +
			"=?,StockCostPerCount=?,StockCostPerVolume=?,StockCostPerWeight=? " +
			"where CustomerID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, cdto.getType());
			pstmt.setString(2, cdto.getName());
			pstmt.setString(3, cdto.getContact());
			pstmt.setString(4, cdto.getPhone());
			pstmt.setString(5, cdto.getEmail());
			pstmt.setString(6, cdto.getAddress());
			pstmt.setString(7, cdto.getRemarks());
			pstmt.setDouble(8, cdto.getFreightCostPerCount());
			pstmt.setDouble(9, cdto.getFreightCostPerVolume());
			pstmt.setDouble(10, cdto.getFreightCostPerWeight());
			pstmt.setDouble(11, cdto.getStockInCostPerCount());
			pstmt.setDouble(12, cdto.getStockInCostPerVolume());
			pstmt.setDouble(13, cdto.getStockInCostPerWeight());
			pstmt.setDouble(14, cdto.getStockOutCostPerCount());
			pstmt.setDouble(15, cdto.getStockOutCostPerVolume());
			pstmt.setDouble(16, cdto.getStockOutCostPerWeight());
			pstmt.setDouble(17, cdto.getStockCostPerCount());
			pstmt.setDouble(18, cdto.getStockCostPerVolume());
			pstmt.setDouble(19, cdto.getStockCostPerWeight());
			pstmt.setInt(20, cdto.getCustomerID());
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
	public boolean delete(Integer cid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(cid==null)
			return false;
		String sql="delete from Customers where CustomerID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, cid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public ArrayList<CustomerDTO> queryNameID(String cusType) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<CustomerDTO> clist=new ArrayList<CustomerDTO>();
		String sql="select * from Customers where ( "+Tools.isVoid(cusType)+" or Type= ?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, cusType);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					CustomerDTO cdto=new CustomerDTO();
					cdto.setCustomerID(rs.getInt("CustomerID"));
					cdto.setName(rs.getString("Name"));
					cdto.setType(rs.getString("Type"));
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
	
	public ArrayList<CustomerDTO> queryByCondition(Integer cid,String cname,int start,int limit) throws Exception{
		Tools.print("queryByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<CustomerDTO> ctlist=new ArrayList<CustomerDTO>();
		String sql=" select * from Customers where CustomerID like ? and Name like ?" +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(cid!=null)
			{
				pstmt.setInt(1, cid);
				pstmt.setString(2, "%");
			}
			else
			{
				pstmt.setString(1, "%");
				if(cname!=null && cname.length()!=0)
					pstmt.setString(2, cname);
				else
					pstmt.setString(2, "%");
			}
			pstmt.setInt(3, start);
			pstmt.setInt(4, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					CustomerDTO cdto=new CustomerDTO();
					cdto.setCustomerID(rs.getInt("CustomerID"));
					cdto.setName(rs.getString("Name"));
					cdto.setRemarks(rs.getString("Remarks"));
					cdto.setType(rs.getString("Type"));
					cdto.setContact(rs.getString("Contact"));
					cdto.setPhone(rs.getString("Phone"));
					cdto.setEmail(rs.getString("Email"));
					cdto.setAddress(rs.getString("Address"));
					cdto.setFreightCostPerCount(rs.getDouble("FreightCostPerCount"));
					cdto.setFreightCostPerVolume(rs.getDouble("FreightCostPerVolume"));
					cdto.setFreightCostPerWeight(rs.getDouble("FreightCostPerWeight"));
					cdto.setStockInCostPerCount(rs.getDouble("StockInCostPerCount"));
					cdto.setStockInCostPerVolume(rs.getDouble("StockInCostPerVolume"));
					cdto.setStockInCostPerWeight(rs.getDouble("StockInCostPerWeight"));
					cdto.setStockOutCostPerCount(rs.getDouble("StockOutCostPerCount"));
					cdto.setStockOutCostPerVolume(rs.getDouble("StockOutCostPerVolume"));
					cdto.setStockOutCostPerWeight(rs.getDouble("StockOutCostPerWeight"));
					cdto.setStockCostPerCount(rs.getDouble("StockCostPerCount"));
					cdto.setStockOutCostPerVolume(rs.getDouble("StockCostPerVolume"));
					cdto.setStockCostPerWeight(rs.getDouble("StockCostPerWeight"));
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
	
	public int queryAmountByCondition(Integer cid,String cname) throws Exception{
		Tools.print("queryAmountByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from Customers where CustomerID like ? and Name like ? ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(cid!=null)
			{
				pstmt.setInt(1, cid);
				pstmt.setString(2, "%");
			}
			else
			{
				pstmt.setString(1, "%");
				if(cname!=null && cname.length()!=0)
					pstmt.setString(2, cname);
				else
					pstmt.setString(2, "%");
			}
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
