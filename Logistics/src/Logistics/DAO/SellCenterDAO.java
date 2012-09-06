package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.CostCenterDTO;
import Logistics.DTO.CustomerDTO;

import Logistics.DTO.*;

public class SellCenterDAO extends BaseDAO implements DAOInterface<SellCenterDTO>{
	public boolean insert(SellCenterDTO dto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" SellCenters(SellCenterID) " +
				" values(?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getSellCenterID());
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
	

	public boolean update(SellCenterDTO dto) throws Exception{
		return false;
	}
	
	
	public ArrayList<SellCenterDTO> queryNameID() throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<SellCenterDTO> clist=new ArrayList<SellCenterDTO>();
		String sql="select * from SellCenters";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					SellCenterDTO ccdto=new SellCenterDTO();
					ccdto.setSellCenterID(rs.getString("SellCenterID"));
					
					clist.add(ccdto);
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
	
	public boolean delete(SellCenterDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getSellCenterID()==null)
			return false;
		String sql="delete from SellCenters where SellCenterID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getSellCenterID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public SellCenterDTO getDTOByID(SellCenterDTO dto) throws Exception {
		//申明对象
		SellCenterDTO cdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(dto==null || dto.getSellCenterID()==null)
			return null;
		String sql="select * from SellCenters where SellCenterID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getSellCenterID());
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				cdto= new SellCenterDTO();
				cdto.setSellCenterID(rs.getString("SellCenterID"));
				
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
	public ArrayList<SellCenterDTO> queryAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public ArrayList<SellCenterDTO> queryOnCondition(SellCenterDTO dto,
			int start, int limit) throws Exception {

		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<SellCenterDTO> ctlist=new ArrayList<SellCenterDTO>();
		String sql=" select * from SellCenters " +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					SellCenterDTO ccdto=new SellCenterDTO();
					ccdto.setSellCenterID(rs.getString("SellCenterID"));
					
					ctlist.add(ccdto);
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
	public int queryQualifiedAmount(SellCenterDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from SellCenters  ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next()){
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
