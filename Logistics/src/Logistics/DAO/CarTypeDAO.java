package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.CarTypeDTO;
import Logistics.DTO.CityDTO;
import Logistics.DTO.EmployeeSalaryDTO;
import Logistics.DTO.FreightRouteDTO;


public class CarTypeDAO extends BaseDAO {
	public CarTypeDTO getDTOByID(Integer ctid) throws Exception{
		//申明对象
		CarTypeDTO ctdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(ctid==null)
			return null;
		String sql="select * from CarTypes where CarTypeID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, ctid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				ctdto= new CarTypeDTO();
				ctdto.setCarTypeID(rs.getInt("CarTypeID"));
				ctdto.setCarTypeName(rs.getString("CarTypeName"));
				ctdto.setRemarks(rs.getString("Remarks"));
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return ctdto;
	}
	
	public boolean insert(CarTypeDTO ctdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(ctdto==null)
			return false;
		String sql="insert into " +
				" CarTypes(CarTypeID,CarTypeName,Remarks) " +
				" values(null,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, ctdto.getCarTypeName());
			pstmt.setString(2, ctdto.getRemarks());
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
	
	public boolean update(CarTypeDTO ctdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(ctdto==null)
			return false;
		String sql="update CarTypes set CarTypeName=?, Remarks=? where CarTypeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, ctdto.getCarTypeName());
			pstmt.setString(2, ctdto.getRemarks());
			pstmt.setInt(3, ctdto.getCarTypeID());
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
	
	public boolean delete(Integer ctid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(ctid==null)
			return false;
		String sql="delete from CarTypes where CarTypeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, ctid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public ArrayList<CarTypeDTO> queryNameID() throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<CarTypeDTO> clist=new ArrayList<CarTypeDTO>();
		String sql="select * from CarTypes";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					CarTypeDTO ctdto=new CarTypeDTO();
					ctdto.setCarTypeID(rs.getInt("CarTypeID"));
					ctdto.setCarTypeName(rs.getString("CarTypeName"));
					ctdto.setRemarks(rs.getString("Remarks"));
					clist.add(ctdto);
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
	public ArrayList<CarTypeDTO> queryByCondition(int start,int limit) throws Exception{
		Tools.print("queryByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<CarTypeDTO> ctlist=new ArrayList<CarTypeDTO>();
		String sql=" select * from CarTypes " +
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
					CarTypeDTO ctdto=new CarTypeDTO();
					ctdto.setCarTypeID(rs.getInt("CarTypeID"));
					ctdto.setCarTypeName(rs.getString("CarTypeName"));
					ctdto.setRemarks(rs.getString("Remarks"));
					ctlist.add(ctdto);
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
	
	public int queryAmountByCondition() throws Exception{
		Tools.print("queryAmountByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from CarTypes ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
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
