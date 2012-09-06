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
import Logistics.DTO.StockinFinanceDTO;
import Logistics.DTO.StockinManifestDTO;
import Logistics.DTO.StockoutFinanceDTO;

public class StockoutFinanceDAO extends BaseDAO {
	public boolean delete(Integer somid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(somid==null)
			return true;
		String sql="delete from StockoutFinances where StockoutManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, somid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(ArrayList<Integer> somidList) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(somidList==null)
			return true;
		String sql="delete from StockoutFinances where StockoutManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(int i=0;i<somidList.size();i++){
				pstmt.setInt(1, somidList.get(i));
				pstmt.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public StockoutFinanceDTO getByID(Integer somid)throws Exception{
		//申明对象
		StockoutFinanceDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(somid==null)
			return null;
		String sql="select * from StockoutFinances where StockoutManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1,somid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new StockoutFinanceDTO();
				resdto.setStockoutManifestID(rs.getInt("StockoutManifestID"));
				resdto.setSumAmount(rs.getInt("SumAmount"));
				resdto.setCustomer(rs.getString("Customer"));
				resdto.setWarehouseName(rs.getString("WarehouseName"));
				resdto.setRemarks(rs.getString("Remarks"));
				resdto.setChargeMode(rs.getString("ChargeMode"));
				resdto.setCostCenter(rs.getString("CostCenter"));
				resdto.setSellCenter(rs.getString("SellCenter"));
				resdto.setDateCreated(rs.getDate("DateCreated"));
				resdto.setDateStockout(rs.getDate("DateStockout"));
				resdto.setUnitPrice(rs.getDouble("UnitPrice"));
				resdto.setStockoutFee(rs.getDouble("StockoutFee"));
				resdto.setSumVolume(rs.getDouble("SumVolume"));
				resdto.setSumWeight(rs.getDouble("SumWeight"));
				resdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
				resdto.setFinancialState(rs.getString("FinancialState"));
				resdto.setExtraCost(rs.getDouble("ExtraCost"));
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
	
	public boolean insert(StockoutFinanceDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" StockoutFinances(" +
				"StockoutManifestID," +
				"SumAmount," +
				"Customer," +
				"WarehouseName," +
				"Remarks," +
				"ChargeMode," +
				"CostCenter," +
				"SellCenter," +
				"DateCreated," +
				"DateStockout," +
				"UnitPrice," +
				"StockoutFee," +
				"SumVolume," +
				"SumWeight," +
				"LoadUnloadCost," +
				"FinancialState," +
				"ExtraCost)" +
				" values(?,?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?,?,?," +
				"?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getStockoutManifestID());
			pstmt.setInt(2, dto.getSumAmount());
			pstmt.setString(3, dto.getCustomer());
			pstmt.setString(4, dto.getWarehouseName());
			pstmt.setString(5, dto.getRemarks());
			pstmt.setString(6, dto.getChargeMode());
			pstmt.setString(7, dto.getCostCenter());
			pstmt.setString(8, dto.getSellCenter());
			pstmt.setDate(9, dto.getDateCreated());
			pstmt.setDate(10, dto.getDateStockout());
			pstmt.setDouble(11, dto.getUnitPrice());
			pstmt.setDouble(12, dto.getStockoutFee());
			pstmt.setDouble(13, dto.getSumVolume());
			pstmt.setDouble(14, dto.getSumWeight());
			pstmt.setDouble(15, dto.getLoadUnloadCost());
			pstmt.setString(16, "未归档");
			pstmt.setDouble(17, dto.getExtraCost());
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
	
	
	public ArrayList<StockoutFinanceDTO> queryOnCondition(
			StockoutFinanceDTO dto,Date startDay,Date endDay, int start, int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(dto==null)
		{
			dto=new StockoutFinanceDTO();
		}
		ArrayList<StockoutFinanceDTO> resList=new ArrayList<StockoutFinanceDTO>();
		String sql=" select * from StockoutFinances " +
				" where "+!Tools.isVoid(dto.getStockoutManifestID())+" and StockoutManifestID = ?" +
				" or "+Tools.isVoid(dto.getStockoutManifestID())+
				" and ("+Tools.isVoid(dto.getCustomer())+" or Customer like ? )" +
				" and ("+Tools.isVoid(dto.getWarehouseName())+" or WarehouseName like ? )" +
				" and ("+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
				" and ("+Tools.isVoid(dto.getSellCenter())+" or SellCenter = ?)" +
				" and ("+Tools.isVoid(dto.getDateStockout())+" or DateStockout >=?) " +
				" and ("+Tools.isVoid(dto.getDateStockout())+" or DateStockout <=?) " +
				" and ("+Tools.isVoid(dto.getFinancialState())+" or FinancialState=? ) " +
						" order by StockoutManifestID desc " +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getStockoutManifestID()));
			pstmt.setString(2,"%"+dto.getCustomer()+"%");
			pstmt.setString(3, "%"+dto.getWarehouseName()+"%");
			pstmt.setString(4, dto.getCostCenter());
			pstmt.setString(5, dto.getSellCenter());
			pstmt.setDate(6, dto.getDateStockout());
			pstmt.setDate(7, dto.getDateStockout());
			pstmt.setString(8, dto.getFinancialState());
			pstmt.setInt(9, start);
			pstmt.setInt(10, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					StockoutFinanceDTO resdto=new StockoutFinanceDTO();
					resdto.setStockoutManifestID(rs.getInt("StockoutManifestID"));
					resdto.setSumAmount(rs.getInt("SumAmount"));
					resdto.setCustomer(rs.getString("Customer"));
					resdto.setWarehouseName(rs.getString("WarehouseName"));
					resdto.setRemarks(rs.getString("Remarks"));
					resdto.setChargeMode(rs.getString("ChargeMode"));
					resdto.setCostCenter(rs.getString("CostCenter"));
					resdto.setSellCenter(rs.getString("SellCenter"));
					resdto.setDateCreated(rs.getDate("DateCreated"));
					resdto.setDateStockout(rs.getDate("DateStockout"));
					resdto.setUnitPrice(rs.getDouble("UnitPrice"));
					resdto.setStockoutFee(rs.getDouble("StockoutFee"));
					resdto.setSumVolume(rs.getDouble("SumVolume"));
					resdto.setSumWeight(rs.getDouble("SumWeight"));
					resdto.setLoadUnloadCost(rs.getDouble("LoadUnloadCost"));
					resdto.setFinancialState(rs.getString("FinancialState"));
					resdto.setExtraCost(rs.getDouble("ExtraCost"));
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
			StockoutFinanceDTO dto,Date startDay,Date endDay) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from StockoutFinances " +
		" where "+!Tools.isVoid(dto.getStockoutManifestID())+" and StockoutManifestID = ?" +
		" or "+Tools.isVoid(dto.getStockoutManifestID())+
		" and ("+Tools.isVoid(dto.getCustomer())+" or Customer like ? )" +
		" and ("+Tools.isVoid(dto.getWarehouseName())+" or WarehouseName like ? )" +
		" and ("+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
		" and ("+Tools.isVoid(dto.getSellCenter())+" or SellCenter = ?)" +
		" and ("+Tools.isVoid(dto.getDateStockout())+" or DateStockout >=?) " +
		" and ("+Tools.isVoid(dto.getDateStockout())+" or DateStockout <=?) " +
		" and ("+Tools.isVoid(dto.getFinancialState())+" or FinancialState=? ) " +
				" order by StockoutManifestID desc ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getStockoutManifestID()));
			pstmt.setString(2, "%"+dto.getCustomer()+"%");
			pstmt.setString(3, "%"+dto.getWarehouseName()+"%");
			pstmt.setString(4, dto.getCostCenter());
			pstmt.setString(5, dto.getSellCenter());
			pstmt.setDate(6, dto.getDateStockout());
			pstmt.setDate(7, dto.getDateStockout());
			pstmt.setString(8, dto.getFinancialState());
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
	public boolean updateMoney(StockoutFinanceDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getStockoutManifestID()==null)
			return false;
		String sql="update StockoutFinances set " +
				"ChargeMode=?," +
				"CostCenter=?," +
				"SellCenter=?," +
				"UnitPrice=?," +
				"StockoutFee=?," +
				"LoadUnloadCost=?," +
				"ExtraCost=?," +
				"Remarks=? where StockoutManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getChargeMode());
			pstmt.setString(2, dto.getCostCenter());
			pstmt.setString(3, dto.getSellCenter());
			pstmt.setDouble(4, dto.getUnitPrice());
			pstmt.setDouble(5, dto.getStockoutFee());
			pstmt.setDouble(6, dto.getLoadUnloadCost());
			pstmt.setDouble(7, dto.getExtraCost());
			pstmt.setString(8, dto.getRemarks());
			pstmt.setInt(9, dto.getStockoutManifestID());
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
	public boolean updateFinancialState(StockoutFinanceDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getStockoutManifestID()==null || dto.getFinancialState()==null)
			return false;
		String sql="update StockoutFinances set FinancialState=?  where StockoutManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getFinancialState());
			pstmt.setInt(2, dto.getStockoutManifestID());
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
