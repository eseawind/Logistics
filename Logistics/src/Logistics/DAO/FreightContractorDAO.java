package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.*;

public class FreightContractorDAO extends BaseDAO{
	
	public FreightContractorDTO getDTOByID(Integer fcid) throws Exception{
		//申明对象
		FreightContractorDTO fcdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(fcid==null)
			return null;
		String sql="select * from FreightContractors where FreightContractorID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, fcid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				fcdto= new FreightContractorDTO();
				fcdto.setFreightContractorID(rs.getInt("FreightContractorID"));
				fcdto.setContact(rs.getString("Contact"));
				fcdto.setName(rs.getString("Name"));
				fcdto.setPhone(rs.getString("Phone"));
				fcdto.setEmail(rs.getString("Email"));
				fcdto.setAddress(rs.getString("Address"));
				fcdto.setRemarks(rs.getString("Remarks"));
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return fcdto;
	}
	
	public boolean insert(FreightContractorDTO fcdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(fcdto==null)
			return false;
		String sql="insert into " +
				" FreightContractors(FreightContractorID,Contact,Name,Phone,Email,Address,Remarks) " +
				" values(null,?,?,?,?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, fcdto.getContact());
			pstmt.setString(2, fcdto.getName());
			pstmt.setString(3, fcdto.getPhone());
			pstmt.setString(4, fcdto.getEmail());
			pstmt.setString(5, fcdto.getAddress());
			pstmt.setString(6, fcdto.getRemarks());
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
	
	public boolean update(FreightContractorDTO fcdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(fcdto==null)
			return false;
		String sql="update FreightContractors set Contact=?,Name=?,Phone=?,Email=?,Address=?,Remarks=? " +
				" where FreightContractorID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, fcdto.getContact());
			pstmt.setString(2, fcdto.getName());
			pstmt.setString(3, fcdto.getPhone());
			pstmt.setString(4, fcdto.getEmail());
			pstmt.setString(5, fcdto.getAddress());
			pstmt.setString(6, fcdto.getRemarks());
			pstmt.setInt(7, fcdto.getFreightContractorID());
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
		String[] sqls={"delete from FreightContractors where FreightContractorID=?; ",
				"delete from FreightRouteQuotes where FreightContractorID=?; ",
				"delete from CarTypeQuotes where FreightContractorID=?;"};
		try {
			for(String sql:sqls){
				pstmt=mysqlTools.getPreparedStatement(sql);
				pstmt.setInt(1, fcid);
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public ArrayList<FreightContractorDTO> queryNameID() throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<FreightContractorDTO> fclist=new ArrayList<FreightContractorDTO>();
		String sql="select FreightContractorID,Name from FreightContractors";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					FreightContractorDTO fcdto=new FreightContractorDTO();
					fcdto.setFreightContractorID(rs.getInt("FreightContractorID"));
					fcdto.setName(rs.getString("Name"));
					fclist.add(fcdto);
				}
			}
			return fclist;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	
	public ArrayList<FreightContractorDTO> queryOnCondition(Integer fcid,String fcname,int start,int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<FreightContractorDTO> ilist=new ArrayList<FreightContractorDTO>();
		String sql=" select * from FreightContractors where FreightContractorID like ? and Name like ?" +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(fcid!=null)
			{
				pstmt.setInt(1, fcid);
				pstmt.setString(2, "%");
			}
			else
			{
				pstmt.setString(1, "%");
				if(fcname!=null && fcname.length()!=0)
					pstmt.setString(2, fcname);
				else
					pstmt.setString(2, "%");
			}
			
			pstmt.setInt(3, start);
			pstmt.setInt(4, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					FreightContractorDTO fcdto=new FreightContractorDTO();
					fcdto.setFreightContractorID(rs.getInt("FreightContractorID"));
					fcdto.setContact(rs.getString("Contact"));
					fcdto.setName(rs.getString("Name"));
					fcdto.setPhone(rs.getString("Phone"));
					fcdto.setEmail(rs.getString("Email"));
					fcdto.setAddress(rs.getString("Address"));
					fcdto.setRemarks(rs.getString("Remarks"));
					ilist.add(fcdto);
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
	
	public int queryAmountOnCondition(Integer fcid,String fcname) throws Exception{
		Tools.print("queryAmountByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from FreightContractors where FreightContractorID like ? and Name like ?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(fcid!=null)
			{
				pstmt.setInt(1, fcid);
				pstmt.setString(2, "%");
			}
			else
			{
				pstmt.setString(1, "%");
				if(fcname!=null && fcname.length()!=0)
					pstmt.setString(2, fcname);
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
