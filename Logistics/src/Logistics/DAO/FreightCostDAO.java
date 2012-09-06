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

public class FreightCostDAO extends BaseDAO {
	
	public boolean delete(Integer fmid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(fmid==null)
			return true;
		String sql="delete from FreightCosts where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, fmid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public FreightCostDTO getByID(Integer fmid)throws Exception{
		//申明对象
		FreightCostDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(fmid==null)
			return null;
		String sql="select * from FreightCosts where FreightManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1,fmid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new FreightCostDTO();
				resdto.setFreightManifestID(rs.getInt("FreightManifestID"));
				resdto.setCustomerType(rs.getString("CustomerType"));
				resdto.setCustomer(rs.getString("Customer"));
				resdto.setDateCreated(rs.getDate("DateCreated"));
				resdto.setSumAmount(rs.getInt("SumAmount"));
				resdto.setSumVolume(rs.getDouble("SumVolume"));
				resdto.setSumWeight(rs.getDouble("SumWeight"));
				resdto.setSumValue(rs.getDouble("SumValue"));
				resdto.setFreightCost(rs.getDouble("FreightCost"));
				resdto.setPrepaidCost(rs.getDouble("PrepaidCost"));
				resdto.setExtraCost(rs.getDouble("ExtraCost"));
				resdto.setExtraCostRemarks(rs.getString("ExtraCostRemarks"));
				resdto.setFinancialState(rs.getString("FinancialState"));
				resdto.setFinancialRemarks(rs.getString("FinancialRemarks"));
				resdto.setCostCenter(rs.getString("CostCenter"));
				resdto.setOriginCity(rs.getString("originCity"));
				resdto.setOriginProvince(rs.getString("originProvince"));
				resdto.setDestinationCity(rs.getString("destinationCity"));
				resdto.setDestinationProvince(rs.getString("destinationProvince"));
				resdto.setCustomerNumber(rs.getString("customerNumber"));
				resdto.setConsigneeCompany(rs.getString("consigneeCompany"));
				resdto.setConsignee(rs.getString("consignee"));
				
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

	public boolean insert(FreightCostDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" FreightCosts(" +
				"FreightManifestID," +
				"DateCreated," +
				"CustomerType," +
				"Customer," +
				"SumAmount," +
				"SumVolume," +
				"SumWeight," +
				"SumValue," +
				"FreightCost," +
				"PrepaidCost," +
				"ExtraCost," +
				"ExtraCostRemarks," +
				"FinancialState," +
				"FinancialRemarks," +
				"CostCenter," +
				"originCity," +
				"originProvince," +
				"destinationCity," +
				"destinationProvince," +
				"customerNumber," +
				"consigneeCompany," +
				"consignee)" +
				" values(?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?,?,?" +
				",?,?,?,?,?" +
				",?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getFreightManifestID());
			pstmt.setDate(2, dto.getDateCreated());
			pstmt.setString(3, dto.getCustomerType());
			pstmt.setString(4, dto.getCustomer());
			pstmt.setInt(5, dto.getSumAmount());
			pstmt.setDouble(6, dto.getSumVolume());
			pstmt.setDouble(7, dto.getSumWeight());
			pstmt.setDouble(8, dto.getSumValue());
			pstmt.setDouble(9, dto.getFreightCost());
			pstmt.setDouble(10, dto.getPrepaidCost());
			pstmt.setDouble(11, dto.getExtraCost());
			pstmt.setString(12, dto.getExtraCostRemarks());
			pstmt.setString(13, dto.getFinancialState());
			pstmt.setString(14, dto.getFinancialRemarks());
			pstmt.setString(15, dto.getCostCenter());
			pstmt.setString(16, dto.getOriginCity());
			pstmt.setString(17, dto.getOriginProvince());
			pstmt.setString(18, dto.getDestinationCity());
			pstmt.setString(19, dto.getDestinationProvince());
			pstmt.setString(20, dto.getCustomerNumber());
			pstmt.setString(21, dto.getConsigneeCompany());
			pstmt.setString(22, dto.getConsignee());
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
	public ArrayList<FreightCostDTO> queryOnCondition(
			FreightCostDTO dto,Date startDay,Date endDay, int start, int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<FreightCostDTO> resList=new ArrayList<FreightCostDTO>();
		String sql=" select * from FreightCosts " +
				" where  "+!Tools.isVoid(dto.getFreightManifestID())+" and FreightManifestID = ? " +
				" or " +Tools.isVoid(dto.getFreightManifestID())+
				" and ("+Tools.isVoid(dto.getCustomer())+" or Customer like ? )" +
				" and ("+Tools.isVoid(dto.getCustomerType())+" or CustomerType = ?)" +
				" and ("+Tools.isVoid(dto.getFinancialState())+" or FinancialState = ?)" +
				" and ("+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
				" and ("+Tools.isVoid(startDay)+" or DateCreated >= ?) " +
				" and ("+Tools.isVoid(endDay)+" or DateCreated <= ?)" +
						" and ("+Tools.isVoid(dto.getCustomerNumber())+" or customerNumber like ?)" +
						" order by freightManifestID desc" +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getFreightManifestID()));
			pstmt.setString(2, "%"+dto.getCustomer()+"%");
			pstmt.setString(3, dto.getCustomerType());
			pstmt.setString(4, dto.getFinancialState());
			pstmt.setString(5, dto.getCostCenter());
			pstmt.setDate(6, startDay);
			pstmt.setDate(7, endDay);
			pstmt.setString(8, "%"+dto.getCustomerNumber()+"%");
			pstmt.setInt(9, start);
			pstmt.setInt(10, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					FreightCostDTO resdto=new FreightCostDTO();
					resdto.setFreightManifestID(rs.getInt("FreightManifestID"));
					resdto.setCustomerType(rs.getString("CustomerType"));
					resdto.setCustomer(rs.getString("Customer"));
					resdto.setDateCreated(rs.getDate("DateCreated"));
					resdto.setSumAmount(rs.getInt("SumAmount"));
					resdto.setSumVolume(rs.getDouble("SumVolume"));
					resdto.setSumWeight(rs.getDouble("SumWeight"));
					resdto.setSumValue(rs.getDouble("SumValue"));
					resdto.setFreightCost(rs.getDouble("FreightCost"));
					resdto.setPrepaidCost(rs.getDouble("PrepaidCost"));
					resdto.setExtraCost(rs.getDouble("ExtraCost"));
					resdto.setExtraCostRemarks(rs.getString("ExtraCostRemarks"));
					resdto.setFinancialState(rs.getString("FinancialState"));
					resdto.setFinancialRemarks(rs.getString("FinancialRemarks"));
					resdto.setCostCenter(rs.getString("CostCenter"));
					resdto.setOriginCity(rs.getString("originCity"));
					resdto.setOriginProvince(rs.getString("originProvince"));
					resdto.setDestinationCity(rs.getString("destinationCity"));
					resdto.setDestinationProvince(rs.getString("destinationProvince"));
					resdto.setCustomerNumber(rs.getString("customerNumber"));
					resdto.setConsigneeCompany(rs.getString("consigneeCompany"));
					resdto.setConsignee(rs.getString("consignee"));
					resList.add(resdto);
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
	
	public int queryQualifiedAmountOnCondition(
			FreightCostDTO dto,Date startDay,Date endDay) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		int amount=0;
		String sql=" select count(*) Amount from FreightCosts " +
		" where  "+!Tools.isVoid(dto.getFreightManifestID())+" and FreightManifestID = ? " +
		" or " +Tools.isVoid(dto.getFreightManifestID())+
		" and ("+Tools.isVoid(dto.getCustomer())+" or Customer like ? )" +
		" and ("+Tools.isVoid(dto.getCustomerType())+" or CustomerType = ?)" +
		" and ("+Tools.isVoid(dto.getFinancialState())+" or FinancialState = ?)" +
		" and ("+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
		" and ("+Tools.isVoid(startDay)+" or DateCreated >= ?) " +
		" and ("+Tools.isVoid(endDay)+" or DateCreated <= ?)" +
				" and ("+Tools.isVoid(dto.getCustomerNumber())+" or customerNumber like ?)" +
				" order by freightManifestID desc";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getFreightManifestID()));
			pstmt.setString(2, "%"+dto.getCustomer()+"%");
			pstmt.setString(3, dto.getCustomerType());
			pstmt.setString(4, dto.getFinancialState());
			pstmt.setString(5, dto.getCostCenter());
			pstmt.setDate(6, startDay);
			pstmt.setDate(7, endDay);
			pstmt.setString(8, "%"+dto.getCustomerNumber()+"%");
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
	public boolean updateCosts(FreightCostDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getFreightManifestID()==null)
			return false;
		String sql="update FreightCosts set FreightCost=?," +
				"PrepaidCost=?," +
				"ExtraCost=?, " +
				"ExtraCostRemarks=?," +
				"FinancialRemarks=?," +
				"CostCenter=? where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDouble(1, dto.getFreightCost());
			pstmt.setDouble(2, dto.getPrepaidCost());
			pstmt.setDouble(3, dto.getExtraCost());
			pstmt.setString(4, dto.getExtraCostRemarks());
			pstmt.setString(5, dto.getFinancialRemarks());
			pstmt.setString(6, dto.getCostCenter());
			pstmt.setInt(7, dto.getFreightManifestID());
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
	public boolean updateCosts(double incFreightCost,int fmid) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		String sql="update FreightCosts set FreightCost=FreightCost+? where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDouble(1, incFreightCost);
			pstmt.setInt(2, fmid);
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
	public boolean updateFinancialState(FreightCostDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getFreightManifestID()==null || dto.getFinancialState()==null)
			return false;
		String sql="update FreightCosts set FinancialState=?  where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getFinancialState());
			pstmt.setInt(2, dto.getFreightManifestID());
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
