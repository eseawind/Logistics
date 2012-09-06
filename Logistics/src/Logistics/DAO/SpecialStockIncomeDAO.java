package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import Logistics.Common.Tools;
import Logistics.DTO.CargoDTO;
import Logistics.DTO.ConsigneeDTO;
import Logistics.DTO.CustomerDTO;
import Logistics.DTO.FreightCostDTO;
import Logistics.DTO.FreightIncomeDTO;
import Logistics.DTO.FreightManifestDTO;
import Logistics.DTO.FreightStateDTO;
import Logistics.DTO.SpecialStockIncomeDTO;
import Logistics.DTO.StockinFinanceDTO;
import Logistics.DTO.StockinManifestDTO;
import Logistics.DTO.StockoutFinanceDTO;

public class SpecialStockIncomeDAO extends BaseDAO {
	public boolean delete(Integer iid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(iid==null)
			return true;
		String sql="delete from SpecialStockIncomes where IncomeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, iid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(ArrayList<Integer> iidList) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(iidList==null)
			return true;
		String sql="delete from SpecialStockIncomes where IncomeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(int i=0;i<iidList.size();i++){
				pstmt.setInt(1, iidList.get(i));
				pstmt.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public SpecialStockIncomeDTO getByID(Integer iid)throws Exception{
		//申明对象
		SpecialStockIncomeDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(iid==null)
			return null;
		String sql="select * from SpecialStockIncomes where IncomeID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1,iid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new SpecialStockIncomeDTO();
				resdto.setIncomeID(rs.getInt("IncomeID"));
				resdto.setCustomerID(rs.getInt("CustomerID"));
				resdto.setCustomerName(rs.getString("CustomerName"));
				resdto.setArea(rs.getDouble("Area"));
				resdto.setQuote(rs.getDouble("Quote"));
				resdto.setDateStart(rs.getDate("DateStart"));
				resdto.setDateEnd(rs.getDate("DateEnd"));
				resdto.setDateCreated(rs.getDate("DateCreated"));
				resdto.setWarehouseID(rs.getString("WarehouseID"));
				resdto.setWarehouseName(rs.getString("WarehouseName"));
				resdto.setSellCenter(rs.getString("SellCenter"));
				resdto.setDaysStock(rs.getInt("DaysStock"));
				resdto.setStockFee(rs.getDouble("StockFee"));
				resdto.setExtraFee(rs.getDouble("ExtraFee"));
				resdto.setFinancialState(rs.getString("FinancialState"));
				resdto.setRemarks(rs.getString("Remarks"));
				
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
	
	public boolean insert(SpecialStockIncomeDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" SpecialStockIncomes(IncomeID," +
				"CustomerID," +
				"CustomerName," +
				"Area," +
				"Quote," +
				"DateStart," +
				"DateEnd," +
				"DateCreated," +
				"WarehouseID," +
				"WarehouseName," +
				"SellCenter," +
				"DaysStock," +
				"StockFee," +
				"ExtraFee," +
				"FinancialState," +
				"Remarks)" +
				" values(?,?,?,?,?" +
				",?,?,?,?,?" +
				",?,?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setNull(1, java.sql.Types.INTEGER);
			pstmt.setInt(2, dto.getCustomerID());
			pstmt.setString(3, dto.getCustomerName());
			pstmt.setDouble(4, dto.getArea());
			pstmt.setDouble(5, dto.getQuote());
			pstmt.setDate(6, dto.getDateStart());
			pstmt.setDate(7, dto.getDateEnd());
			pstmt.setDate(8, Tools.currDate());
			pstmt.setString(9, dto.getWarehouseID());
			pstmt.setString(10, dto.getWarehouseName());
			pstmt.setString(11, dto.getSellCenter());
			pstmt.setDouble(12, dto.getDaysStock());
			pstmt.setDouble(13, dto.getStockFee());
			pstmt.setDouble(14, dto.getExtraFee());
			pstmt.setString(15, "未归档");
			pstmt.setString(16, dto.getRemarks());
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
	
	
	public ArrayList<SpecialStockIncomeDTO> queryOnCondition(
			SpecialStockIncomeDTO dto,Date startDay,Date endDay, int start, int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(dto==null)
		{
			dto=new SpecialStockIncomeDTO();
		}
		ArrayList<SpecialStockIncomeDTO> resList=new ArrayList<SpecialStockIncomeDTO>();
		String sql=" select * from SpecialStockIncomes " +
				" where "+!Tools.isVoid(dto.getIncomeID())+" and IncomeID = ?" +
				" or "+Tools.isVoid(dto.getIncomeID())+
				" and ("+Tools.isVoid(dto.getCustomerName())+" or CustomerName like ? )" +
				" and ("+Tools.isVoid(dto.getWarehouseName())+" or WarehouseName like ? )" +
				" and ("+Tools.isVoid(dto.getSellCenter())+" or SellCenter = ?)" +
				" and ("+Tools.isVoid(startDay)+" or DateCreated >=?) " +
				" and ("+Tools.isVoid(endDay)+" or DateCreated <=?) " +
				" and ("+Tools.isVoid(dto.getFinancialState())+" or FinancialState=? )  " +
						" order by IncomeID desc  " +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getIncomeID()));
			pstmt.setString(2,dto.getCustomerName());
			pstmt.setString(3, "%"+dto.getWarehouseName()+"%");
			pstmt.setString(4, dto.getSellCenter());
			pstmt.setDate(5, startDay);
			pstmt.setDate(6, endDay);
			pstmt.setString(7, dto.getFinancialState());
			pstmt.setInt(8, start);
			pstmt.setInt(9, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					SpecialStockIncomeDTO resdto=new SpecialStockIncomeDTO();
					resdto.setIncomeID(rs.getInt("IncomeID"));
					resdto.setCustomerID(rs.getInt("CustomerID"));
					resdto.setCustomerName(rs.getString("CustomerName"));
					resdto.setArea(rs.getDouble("Area"));
					resdto.setQuote(rs.getDouble("Quote"));
					resdto.setDateStart(rs.getDate("DateStart"));
					resdto.setDateEnd(rs.getDate("DateEnd"));
					resdto.setDateCreated(rs.getDate("DateCreated"));
					resdto.setWarehouseID(rs.getString("WarehouseID"));
					resdto.setWarehouseName(rs.getString("WarehouseName"));
					resdto.setSellCenter(rs.getString("SellCenter"));
					resdto.setDaysStock(rs.getInt("DaysStock"));
					resdto.setStockFee(rs.getDouble("StockFee"));
					resdto.setExtraFee(rs.getDouble("ExtraFee"));
					resdto.setFinancialState(rs.getString("FinancialState"));
					resdto.setRemarks(rs.getString("Remarks"));
					resList.add(resdto);
				}
				return resList;
			}
			return null;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		
	}
	
	public int queryQualifiedAmountOnCondition(
			SpecialStockIncomeDTO dto,Date startDay,Date endDay) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from SpecialStockIncomes " +
		" where "+!Tools.isVoid(dto.getIncomeID())+" and IncomeID = ?" +
		" or "+Tools.isVoid(dto.getIncomeID())+
		" and ("+Tools.isVoid(dto.getCustomerName())+" or CustomerName like ? )" +
		" and ("+Tools.isVoid(dto.getWarehouseName())+" or WarehouseName like ? )" +
		" and ("+Tools.isVoid(dto.getSellCenter())+" or SellCenter = ?)" +
		" and ("+Tools.isVoid(startDay)+" or DateCreated >=?) " +
		" and ("+Tools.isVoid(endDay)+" or DateCreated <=?) " +
		" and ("+Tools.isVoid(dto.getFinancialState())+" or FinancialState=? ) " +
				" order by IncomeID desc ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getIncomeID()));
			pstmt.setString(2,dto.getCustomerName());
			pstmt.setString(3,"%"+ dto.getWarehouseName()+"%");
			pstmt.setString(4, dto.getSellCenter());
			pstmt.setDate(5, startDay);
			pstmt.setDate(6, endDay);
			pstmt.setString(7, dto.getFinancialState());
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				amount=rs.getInt("Amount");
			}
			return amount;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			close();
		}
		
	}
	public boolean updateMoney(SpecialStockIncomeDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getIncomeID()==null)
			return false;
		String sql="update SpecialStockIncomes set " +
				"Quote=?," +
				"StockFee=?," +
				"ExtraFee=?," +
				"Remarks=? where IncomeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDouble(1, dto.getQuote());
			pstmt.setDouble(2, dto.getStockFee());
			pstmt.setDouble(3, dto.getExtraFee());
			pstmt.setString(4, dto.getRemarks());
			pstmt.setInt(5, dto.getIncomeID());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally{
			close();
		}
		
	}
	public boolean updateFinancialState(SpecialStockIncomeDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getIncomeID()==null || dto.getFinancialState()==null)
			return false;
		String sql="update SpecialStockIncomes set FinancialState=?  where IncomeID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getFinancialState());
			pstmt.setInt(2, dto.getIncomeID());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally{
			close();
		}
		
	}
	
	
}
