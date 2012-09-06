package Logistics.DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.CargoDTO;
import Logistics.DTO.ConsigneeDTO;
import Logistics.DTO.FreightManifestDTO;
import Logistics.DTO.ItemDTO;

public class CargoDAO extends BaseDAO implements DAOInterface<CargoDTO>{

	public boolean delete(CargoDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null)
			return false;
		if(dto.getFreightManifestID()==null)
			return false;
		String sql="delete from Cargos where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getFreightManifestID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public CargoDTO getDTOByID(CargoDTO dto) throws Exception {
		CargoDTO cdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(dto==null || dto.getCargoID()==null || dto.getCargoID().length()==0 ||
				dto.getFreightManifestID()==null)
			return null;
		String sql="select * from Cargos where CargoID=? and FreightManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getCargoID());
			pstmt.setInt(2, dto.getFreightManifestID());
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				cdto= new CargoDTO();
				cdto.setCargoID(rs.getString("CargoID"));
				cdto.setAmount(rs.getInt("Amount"));
				cdto.setFreightManifestID(rs.getInt("FreightManifestID"));
				cdto.setValue(rs.getDouble("Value"));
				cdto.setVolume(rs.getDouble("Volume"));
				cdto.setWeight(rs.getDouble("Weight"));
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
	public boolean insert(CargoDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" Cargos(CargoID,FreightManifestID,Amount,Volume,Weight,Value)" +
				" values(?,?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getCargoID());
			pstmt.setInt(2, dto.getFreightManifestID());
			pstmt.setDouble(3, dto.getAmount());
			pstmt.setDouble(4, dto.getVolume());
			pstmt.setDouble(5, dto.getWeight());
			pstmt.setDouble(6, dto.getValue());
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
	public ArrayList<CargoDTO> queryAll() throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<CargoDTO> clist=new ArrayList<CargoDTO>();
		String sql="select distinct CargoID from Cargos ";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					CargoDTO cdto=new CargoDTO();
					cdto.setCargoID(rs.getString("CargoID"));
					if(!cdto.getCargoID().contains("-"))
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
	public ArrayList<CargoDTO> queryOnCondition(CargoDTO dto,
			int start, int limit) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}

		ArrayList<CargoDTO> clist=new ArrayList<CargoDTO>();
		String sql=" select * from Cargos where FreightManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			
			pstmt.setInt(1, dto.getFreightManifestID());
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					CargoDTO cdto=new CargoDTO();
					cdto.setCargoID(rs.getString("CargoID"));
					cdto.setAmount(rs.getInt("Amount"));
					cdto.setFreightManifestID(rs.getInt("FreightManifestID"));
					cdto.setValue(rs.getDouble("Value"));
					cdto.setVolume(rs.getDouble("Volume"));
					cdto.setWeight(rs.getDouble("Weight"));
					clist.add(cdto);
				}
			}
			return clist;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	public int queryQualifiedAmount(CargoDTO dto)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	public boolean update(CargoDTO dto) throws Exception {
		return false;
	}
	
	public CargoDTO querySum(CargoDTO dto) throws Exception{
		CargoDTO cdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(dto==null || dto.getFreightManifestID()==null)
			return null;
		String sql="select sum(Amount) SumAmount,sum(Weight) SumWeight," +
				"sum(Volume) SumVolume,sum(value) SumValue " +
				" from Cargos where FreightManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getFreightManifestID());
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				cdto= new CargoDTO();
				cdto.setAmount(rs.getInt("SumAmount"));
				cdto.setValue(rs.getDouble("SumValue"));
				cdto.setVolume(rs.getDouble("SumVolume"));
				cdto.setWeight(rs.getDouble("SumWeight"));
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
	
}
