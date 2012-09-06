package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import Logistics.Common.Tools;
import Logistics.DTO.CargoDTO;
import Logistics.DTO.CityDTO;
import Logistics.DTO.ConsigneeDTO;
import Logistics.DTO.CustomerDTO;
import Logistics.DTO.FinancialLogDTO;
import Logistics.DTO.FreightCostDTO;
import Logistics.DTO.FreightIncomeDTO;
import Logistics.DTO.FreightManifestDTO;
import Logistics.DTO.FreightStateDTO;
import Logistics.DTO.ItemDTO;
import Logistics.DTO.StockItemDTO;
import Logistics.DTO.WarehouseDTO;

public class FinancialLogDAO extends BaseDAO {
	public boolean delete(Integer logID) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(logID==null)
			return true;
		String sql="delete from FinancialLogs where LogID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, logID);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(ArrayList<Integer> logIDs) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(logIDs==null)
			return true;
		String sql="delete from FinancialLogs where LogID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(Integer logID:logIDs){
				pstmt.setInt(1, logID);
				
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public FinancialLogDTO getByID(Integer logID)throws Exception{
		//申明对象
		FinancialLogDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(logID==null)
			return null;
		String sql="select * from FinancialLogs where LogID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, logID);
			
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new FinancialLogDTO();
				resdto.setLogID(rs.getInt("LogID"));
				resdto.setContent(rs.getString("Content"));
				resdto.setOperationTime(rs.getTimestamp("OperationTime"));
				resdto.setType(rs.getString("Type"));
				resdto.setUser(rs.getString("User"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return resdto;
		
	}
	
	
	
	
	public int queryQualifiedAmount(String user,Date startDay,Date endDay)throws Exception{
		//申明对象
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		int amount=0;
		String sql="select count(*) Amount";
		sql+=" from FinancialLogs ";
		sql+=" where ("+Tools.isVoid(user)+" or User=? )" +
		" and ("+Tools.isVoid(startDay)+" or OperationTime >=?)" +
		" and ("+Tools.isVoid(endDay)+ " or OperationTime <=?) " +
				" order by operationTime desc ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1,user);
			pstmt.setTimestamp(2, Tools.toTimestamp(startDay));
			pstmt.setTimestamp(3, Tools.toTimestamp(endDay));
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next() )
			{
				amount=rs.getInt("Amount");
			}
			return amount;
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
		finally{
			close();
		}
		
		
	}
	
	public ArrayList<FinancialLogDTO> queryOnCondition(String user,Date startDay,Date endDay,int start,int limit)throws Exception{
		//申明对象
		ArrayList<FinancialLogDTO> resList=new ArrayList<FinancialLogDTO>();
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		String sql="select * ";
			sql+=" from FinancialLogs ";
			sql+=" where ("+Tools.isVoid(user)+" or User=? )" +
					" and ("+Tools.isVoid(startDay)+" or OperationTime >=?)" +
					" and ("+Tools.isVoid(endDay)+ " or OperationTime <=?) " +
					" order by operationTime desc " +
					" limit ?,?";
				
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1,user);
			pstmt.setTimestamp(2, Tools.toTimestamp(startDay));
			pstmt.setTimestamp(3, Tools.toTimestamp(endDay));
			pstmt.setInt(4, start);
			pstmt.setInt(5,limit);
			rs=pstmt.executeQuery();
			if(rs!=null )
			{
				while(rs.next()){
					FinancialLogDTO resdto= new FinancialLogDTO();
					resdto.setLogID(rs.getInt("LogID"));
					resdto.setContent(rs.getString("Content"));
					resdto.setOperationTime(rs.getTimestamp("OperationTime"));
					resdto.setType(rs.getString("Type"));
					resdto.setUser(rs.getString("User"));
					
					
					resList.add(resdto);
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
		return resList;
		
	}
	
	public boolean insert(String userID,String type,String content) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		String sql="insert into " +
		" FinancialLogs(" +
		"LogID," +
		"Type," +
		"Content," +
		"OperationTime," +
		"User) " +
		" values(null,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, type);
			pstmt.setString(2, content);
			pstmt.setTimestamp(3, Tools.currTimestamp());
			pstmt.setString(4, userID);
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
	
	public boolean insert(FinancialLogDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
		" FinancialLogs(" +
		"LogID," +
		"Type," +
		"Content," +
		"OperationTime," +
		"User )" +
		" values(?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setNull(1, java.sql.Types.INTEGER);
			pstmt.setString(2, dto.getType());
			pstmt.setString(3, dto.getContent());
			pstmt.setTimestamp(4, Tools.currTimestamp());
			pstmt.setString(5, dto.getUser());
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
	
	
	
	
	
}
