package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.*;

public class SystemInfoDAO extends BaseDAO {
	
	
	
	public ArrayList<SystemInfoDTO> getInfo() throws Exception{
		//申明对象
		ArrayList<SystemInfoDTO> res=new ArrayList<SystemInfoDTO>();
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		String[] sqls={"select '数据库中的运输单数目' name,count(*) value from freightManifests"
				,"select '数据库中的装车单数目' name,count(*) value from shipmentManifests"
				,"select '数据库中的入库单数目' name,count(*) value from stockinManifests"
				,"select '数据库中的出库单数目' name,count(*) value from stockoutManifests"
				,"select '数据库中的移库单数目' name,count(*) value from stocktransferManifests"
				,"select '数据库中的物料总数' name,count(*) value from items"
				,"select '数据库中的仓库总数' name,count(*) value from warehouses"
				,"select '数据库中的用户总数' name,count(*) value from users"
				,"select '数据库中的库存条目总数' name,count(*) value from stockitems"
				};
		try{
			for(String sql:sqls){
				pstmt=mysqlTools.getPreparedStatement(sql);
				rs=pstmt.executeQuery();
				if(rs!=null && rs.next())
				{
					SystemInfoDTO dto=new SystemInfoDTO();
					dto.setName(rs.getString("name"));
					dto.setValue(rs.getString("value"));
					res.add(dto);
				}
			}
			
			
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return res;
	}
	
	
}
