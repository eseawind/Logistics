package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import Logistics.Common.Tools;
import Logistics.DTO.FreightShipmentDTO;
import Logistics.DTO.ShipmentManifestDTO;

public class FreightShipmentDAO extends BaseDAO {

	public boolean delete(FreightShipmentDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(null==dto)
			return false;
		if(null==dto.getFreightManifestID() && null==dto.getShipmentManifestID())
			return false;
		String sql="delete from FreightShipment where ShipmentManifestID like ? and FreightManifestID like ? ";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(dto.getShipmentManifestID()!=null)
				pstmt.setInt(1, dto.getShipmentManifestID());
			else
				pstmt.setString(1, "%");
			if(dto.getFreightManifestID()!=null)
				pstmt.setInt(2, dto.getFreightManifestID());
			else
				pstmt.setString(2, "%");
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	

	public boolean insert(FreightShipmentDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" FreightShipment(FreightManifestID,ShipmentManifestID)" +
				" values(?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			System.out.println(dto);
			pstmt.setInt(1, dto.getFreightManifestID());
			pstmt.setInt(2, dto.getShipmentManifestID());
			
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

	
	
	public ArrayList<FreightShipmentDTO> queryOnCondition(FreightShipmentDTO dto)throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<FreightShipmentDTO> resList=new ArrayList<FreightShipmentDTO>();
		String sql=" select * from FreightShipment " +
				" where FreightManifestID like ? " +
				" and ShipmentManifestID like ? ";
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(dto.getFreightManifestID()!=null)
				pstmt.setInt(1, dto.getFreightManifestID());
			else
				pstmt.setString(1, "%");
			
			if(dto.getShipmentManifestID()!=null)
				pstmt.setInt(2, dto.getShipmentManifestID());
			else
				pstmt.setString(2, "%");
			
			
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					FreightShipmentDTO fsdto=new FreightShipmentDTO();
					
					fsdto.setFreightManifestID(rs.getInt("FreightManifestID"));
					fsdto.setShipmentManifestID(rs.getInt("ShipmentManifestID"));
					
					resList.add(fsdto);
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
	
	
	
	
}
