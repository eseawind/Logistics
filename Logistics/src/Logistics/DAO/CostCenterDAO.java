package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.CostCenterDTO;
import Logistics.DTO.CustomerDTO;

import Logistics.DTO.*;

public class CostCenterDAO extends BaseDAO implements DAOInterface<CostCenterDTO>{
	public boolean insert(CostCenterDTO dto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" CostCenters(CostCenterID) " +
				" values(?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getCostCenterID());
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
	

	public boolean update(CostCenterDTO dto) throws Exception{
		return false;
	}
	
	
	public ArrayList<CostCenterDTO> queryNameID() throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<CostCenterDTO> clist=new ArrayList<CostCenterDTO>();
		String sql="select * from CostCenters";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					CostCenterDTO ccdto=new CostCenterDTO();
					ccdto.setCostCenterID(rs.getString("CostCenterID"));
					
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
	
	public boolean delete(CostCenterDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getCostCenterID()==null)
			return false;
		String sql="delete from CostCenters where CostCenterID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getCostCenterID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public CostCenterDTO getDTOByID(CostCenterDTO dto) throws Exception {
		//申明对象
		CostCenterDTO cdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(dto==null || dto.getCostCenterID()==null)
			return null;
		String sql="select * from CostCenters where CostCenterID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getCostCenterID());
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				cdto= new CostCenterDTO();
				cdto.setCostCenterID(rs.getString("CostCenterID"));
				
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
	public ArrayList<CostCenterDTO> queryAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public ArrayList<CostCenterDTO> queryOnCondition(CostCenterDTO dto,
			int start, int limit) throws Exception {

		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<CostCenterDTO> ctlist=new ArrayList<CostCenterDTO>();
		String sql=" select * from CostCenters " +
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
					CostCenterDTO ccdto=new CostCenterDTO();
					ccdto.setCostCenterID(rs.getString("CostCenterID"));
					
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
	public int queryQualifiedAmount(CostCenterDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from CostCenters  ";
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
