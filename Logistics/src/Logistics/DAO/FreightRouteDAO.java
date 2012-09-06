package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.CityDTO;
import Logistics.DTO.FreightRouteDTO;
import Logistics.DTO.WarehouseDTO;

public class FreightRouteDAO extends BaseDAO{
	
	
	
	
	public ArrayList<FreightRouteDTO> queryByCondition(Integer frid,String oid,String did,int start,int limit) throws Exception{
		Tools.print("queryByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<FreightRouteDTO> frlist=new ArrayList<FreightRouteDTO>();
		String sql=" select * from FreightRoutes,cities city1,cities city2 " +
				" where FreightRouteID like ? and OriginID like ? and DestinationID like ? " +
				" and originid=city1.cityid and destinationid=city2.cityid" +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(frid!=null){
				pstmt.setInt(1, frid);
				pstmt.setString(2, "%");
				pstmt.setString(3, "%");
			}
			else
			{
				pstmt.setString(1, "%");
				if(oid==null || oid.length()==0)
					pstmt.setString(2, "%");
				else
					pstmt.setString(2, oid);
				if(did==null || did.length()==0)
					pstmt.setString(3, "%");
				else
					pstmt.setString(3, did);
			}
			pstmt.setInt(4, start);
			pstmt.setInt(5, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					FreightRouteDTO frdto=new FreightRouteDTO();
					frdto.setFreightRouteID(rs.getInt("FreightRouteID"));
					frdto.setOriginID(rs.getString("OriginID"));
					frdto.setDestinationID(rs.getString("DestinationID"));
					frdto.setRemarks(rs.getString("Remarks"));
					frdto.setDaysSpent(rs.getInt("DaysSpent"));
					CityDTO cdto=new CityDTO();
					cdto.setCityID(rs.getString("City1.CityID"));
					cdto.setName(rs.getString("City1.Name"));
					cdto.setProvince(rs.getString("city1.Province"));
					frdto.setOriginCityDTO(cdto);
					cdto=new CityDTO();
					cdto.setCityID(rs.getString("City2.CityID"));
					cdto.setName(rs.getString("City2.Name"));
					cdto.setProvince(rs.getString("city2.Province"));
					frdto.setDestinationCityDTO(cdto);
					frlist.add(frdto);
				}
			}
			return frlist;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	
	public int queryAmountByCondition(Integer frid,String oid,String did) throws Exception{
		Tools.print("queryAmountByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from FreightRoutes " +
				" where FreightRouteID like ? and OriginID like ? and DestinationID like ?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(frid!=null){
				pstmt.setInt(1, frid);
				pstmt.setString(2, "%");
				pstmt.setString(3, "%");
			}
			else
			{
				pstmt.setString(1, "%");
				if(oid==null || oid.length()==0)
					pstmt.setString(2, "%");
				else
					pstmt.setString(2, oid);
				if(did==null || did.length()==0)
					pstmt.setString(3, "%");
				else
					pstmt.setString(3, did);
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
	
	public boolean delete(Integer frid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(frid==null)
			return false;
		String sql="delete from FreightRoutes where FreightRouteID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, frid);
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
	
	public boolean update(FreightRouteDTO frdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(frdto==null || frdto.getFreightRouteID()==null)
			return false;
		String sql="update FreightRoutes set OriginID=?,DestinationID=?,Remarks=?,DaysSpent=?" +
				" where FreightRouteID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1,frdto.getOriginID());
			pstmt.setString(2, frdto.getDestinationID());
			pstmt.setString(3, frdto.getRemarks());
			pstmt.setInt(4, frdto.getDaysSpent());
			pstmt.setInt(5, frdto.getFreightRouteID());
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
	
	public boolean insert(FreightRouteDTO frdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(frdto==null)
			return false;
		String sql="insert into " +
				" FreightRoutes(FreightRouteID,OriginID,DestinationID,Remarks,DaysSpent) " +
				" values(null,?,?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1,frdto.getOriginID() );
			pstmt.setString(2, frdto.getDestinationID());
			pstmt.setString(3, frdto.getRemarks());
			pstmt.setInt(4, frdto.getDaysSpent());
			
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
	
	public FreightRouteDTO getDTOByID(Integer frid)throws Exception{
		FreightRouteDTO frdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(frid==null )
			return null;
		String sql="select * from FreightRoutes where FreightRouteID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, frid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				frdto=new FreightRouteDTO();
				frdto.setDaysSpent(rs.getInt("DaysSpent"));
				frdto.setFreightRouteID(rs.getInt("FreightRouteID"));
				frdto.setOriginID(rs.getString("OriginID"));
				frdto.setDestinationID(rs.getString("DestinationID"));
				frdto.setRemarks(rs.getString("Remarks"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return frdto;
	}
	
	public ArrayList<FreightRouteDTO> queryNameID() throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<FreightRouteDTO> frlist=new ArrayList<FreightRouteDTO>();
		String sql=" select * from FreightRoutes,cities city1,cities city2 " +
				"where originid=city1.cityid and destinationid=city2.cityid";
		
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					FreightRouteDTO frdto=new FreightRouteDTO();
					frdto.setFreightRouteID(rs.getInt("FreightRouteID"));
					frdto.setOriginID(rs.getString("OriginID"));
					frdto.setDestinationID(rs.getString("DestinationID"));
					CityDTO cdto=new CityDTO();
					cdto.setName(rs.getString("City1.Name"));
					cdto.setProvince(rs.getString("City1.Province"));
					frdto.setOriginCityDTO(cdto);
					cdto=new CityDTO();
					cdto.setName(rs.getString("City2.Name"));
					cdto.setProvince(rs.getString("City2.Province"));
					frdto.setDestinationCityDTO(cdto);
					frlist.add(frdto);
				}
			}
			return frlist;
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
