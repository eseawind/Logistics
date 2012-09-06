package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DAO.*;
import Logistics.DTO.*;

public class CarDAO extends BaseDAO {
	public CarDTO getDTOByID(String cid) throws Exception{
		//申明对象
		CarDTO cdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(cid==null)
			return null;
		String sql="select * from Cars where CarID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, cid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				cdto= new CarDTO();
				cdto.setCarID(rs.getString("CarID"));
				cdto.setCarTypeID(rs.getInt("CarTypeID"));
				cdto.setDriverName(rs.getString("DriverName"));
				cdto.setOwnerName(rs.getString("OwnerName"));
				cdto.setPhone(rs.getString("Phone"));
				cdto.setFreightContractorID(rs.getInt("FreightContractorID"));
				cdto.setEngineNo(rs.getString("EngineNo"));
				cdto.setVehicleIdentificationNo(rs.getString("VehicleIdentificationNo"));
				cdto.setRoadWorthyCertificateNo(rs.getString("RoadWorthyCertificateNo"));
				cdto.setRemarks(rs.getString("Remarks"));
				
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
	public boolean insert(CarDTO cdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(cdto==null)
			return false;
		String sql="insert into " +
				" Cars(CarID,CarTypeID,DriverName,OwnerName,Phone" +
				",FreightContractorID,EngineNo,VehicleIdentificationNo,RoadWorthyCertificateNo,Remarks)" +
				" values(?,?,?,?,?,?,?,?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, cdto.getCarID());
			pstmt.setInt(2, cdto.getCarTypeID());
			pstmt.setString(3, cdto.getDriverName());
			pstmt.setString(4, cdto.getOwnerName());
			pstmt.setString(5, cdto.getPhone());
			pstmt.setInt(6, cdto.getFreightContractorID());
			pstmt.setString(7, cdto.getEngineNo());
			pstmt.setString(8, cdto.getVehicleIdentificationNo());
			pstmt.setString(9, cdto.getRoadWorthyCertificateNo());
			pstmt.setString(10, cdto.getRemarks());
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
	

	public boolean update(CarDTO cdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(cdto==null)
			return false;
		String sql="update Cars set " +
			" CarTypeID=?,DriverName=?,OwnerName=?,Phone=?" +
			",FreightContractorID=?,EngineNo=?,VehicleIdentificationNo=?,RoadWorthyCertificateNo=?,Remarks=?"+
			" where CarID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, cdto.getCarTypeID());
			pstmt.setString(2, cdto.getDriverName());
			pstmt.setString(3, cdto.getOwnerName());
			pstmt.setString(4, cdto.getPhone());
			pstmt.setInt(5, cdto.getFreightContractorID());
			pstmt.setString(6, cdto.getEngineNo());
			pstmt.setString(7, cdto.getVehicleIdentificationNo());
			pstmt.setString(8, cdto.getRoadWorthyCertificateNo());
			pstmt.setString(9, cdto.getRemarks());
			pstmt.setString(10, cdto.getCarID());
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
	public boolean delete(String cid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(cid==null)
			return false;
		String sql="delete from Cars where CarID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, cid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public ArrayList<CarDTO> queryDriverNameID() throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<CarDTO> clist=new ArrayList<CarDTO>();
		String sql="select * from Cars";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					CarDTO cdto=new CarDTO();
					cdto.setCarID(rs.getString("CarID"));
					cdto.setDriverName(rs.getString("DriverName"));
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
	public ArrayList<CarDTO> queryByContractorID(Integer conid) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(conid==null){
			return null;
		}
		ArrayList<CarDTO> resList=new ArrayList<CarDTO>();
		String sql=" select * from Cars,CarTypes " +
				" where Cars.CarTypeID=CarTypes.CarTypeID  " +
				" and FreightContractorID = ?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, conid);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					CarDTO cdto=new CarDTO();
					cdto.setCarID(rs.getString("Cars.CarID"));
					cdto.setCarTypeID(rs.getInt("Cars.CarTypeID"));
					cdto.setDriverName(rs.getString("Cars.DriverName"));
					cdto.setPhone(rs.getString("Cars.Phone"));
					cdto.setFreightContractorID(rs.getInt("Cars.FreightContractorID"));
					CarTypeDTO ctdto=new CarTypeDTO();
					ctdto.setCarTypeID(rs.getInt("CarTypes.CarTypeID"));
					ctdto.setCarTypeName(rs.getString("CarTypes.CarTypeName"));
					
					cdto.setCarTypeDTO(ctdto);
					resList.add(cdto);
				}
			}
			return resList;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	public ArrayList<CarDTO> queryByCondition(String cid,int start,int limit) throws Exception{
		Tools.print("queryByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<CarDTO> ctlist=new ArrayList<CarDTO>();
		String sql=" select * from Cars,CarTypes,FreightContractors " +
				" where Cars.CarTypeID=CarTypes.CarTypeID and Cars.FreightContractorID=FreightContractors.FreightContractorID " +
				" and CarID like ?" +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(cid!=null && cid.length()!=0)
				pstmt.setString(1, "%"+cid+"%");
			else
				pstmt.setString(1, "%");
			
			pstmt.setInt(2, start);
			pstmt.setInt(3, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					CarDTO cdto=new CarDTO();
					cdto.setCarID(rs.getString("Cars.CarID"));
					cdto.setCarTypeID(rs.getInt("Cars.CarTypeID"));
					cdto.setDriverName(rs.getString("Cars.DriverName"));
					cdto.setOwnerName(rs.getString("Cars.OwnerName"));
					cdto.setPhone(rs.getString("Cars.Phone"));
					cdto.setFreightContractorID(rs.getInt("Cars.FreightContractorID"));
					cdto.setEngineNo(rs.getString("Cars.EngineNo"));
					cdto.setVehicleIdentificationNo(rs.getString("Cars.VehicleIdentificationNo"));
					cdto.setRoadWorthyCertificateNo(rs.getString("Cars.RoadWorthyCertificateNo"));
					cdto.setRemarks(rs.getString("Cars.Remarks"));
					CarTypeDTO ctdto=new CarTypeDTO();
					ctdto.setCarTypeID(rs.getInt("CarTypes.CarTypeID"));
					ctdto.setCarTypeName(rs.getString("CarTypes.CarTypeName"));
					FreightContractorDTO fcdto= new FreightContractorDTO();
					fcdto.setFreightContractorID(rs.getInt("FreightContractors.FreightContractorID"));
					fcdto.setContact(rs.getString("FreightContractors.Contact"));
					fcdto.setName(rs.getString("FreightContractors.Name"));
					fcdto.setPhone(rs.getString("FreightContractors.Phone"));
					fcdto.setEmail(rs.getString("FreightContractors.Email"));
					fcdto.setAddress(rs.getString("FreightContractors.Address"));
					fcdto.setRemarks(rs.getString("FreightContractors.Remarks"));
					cdto.setCarTypeDTO(ctdto);
					cdto.setFreightContractorDTO(fcdto);
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
	
	public int queryAmountByCondition(String cid) throws Exception{
		Tools.print("queryAmountByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from Cars" +
		" where  CarID like ?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(cid!=null && cid.length()!=0)
				pstmt.setString(1, "%"+cid+"%");
			else
				pstmt.setString(1, "%");
			
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
