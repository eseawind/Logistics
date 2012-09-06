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

public class FreightIncomeDAO extends BaseDAO {
	public boolean delete(Integer fmid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(fmid==null)
			return true;
		String sql="delete from FreightIncomes where FreightManifestID=?";
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
	
	public FreightIncomeDTO getByID(Integer fmid)throws Exception{
		//申明对象
		FreightIncomeDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(fmid==null)
			return null;
		String sql="select * from FreightIncomes where FreightManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1,fmid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new FreightIncomeDTO();
				resdto.setFreightManifestID(rs.getInt("FreightManifestID"));
				resdto.setCustomerType(rs.getString("CustomerType"));
				resdto.setCustomer(rs.getString("Customer"));
				resdto.setDateCreated(rs.getDate("DateCreated"));
				resdto.setSumAmount(rs.getInt("SumAmount"));
				resdto.setSumVolume(rs.getDouble("SumVolume"));
				resdto.setSumWeight(rs.getDouble("SumWeight"));
				resdto.setSumValue(rs.getDouble("SumValue"));
				resdto.setFreightIncome(rs.getDouble("FreightIncome"));
				resdto.setConsignIncome(rs.getDouble("ConsignIncome"));
				resdto.setDeliveryIncome(rs.getDouble("DeliveryIncome"));
				resdto.setInsuranceIncome(rs.getDouble("InsuranceIncome"));
				resdto.setExtraIncome(rs.getDouble("ExtraIncome"));
				resdto.setFinancialState(rs.getString("FinancialState"));
				resdto.setFinancialRemarks(rs.getString("FinancialRemarks"));
				resdto.setSellCenter(rs.getString("SellCenter"));
				resdto.setChargeMode(rs.getString("ChargeMode"));
				resdto.setPriceByAmount(rs.getDouble("PriceByAmount"));
				resdto.setPriceByVolume(rs.getDouble("PriceByVolume"));
				resdto.setPriceByWeight(rs.getDouble("PriceByWeight"));
				resdto.setInsuranceRate(rs.getDouble("InsuranceRate"));
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
	
	public boolean insert(FreightIncomeDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" FreightIncomes(" +
				"FreightManifestID," +
				"DateCreated," +
				"CustomerType," +
				"Customer," +
				"SumAmount," +
				"SumVolume," +
				"SumWeight," +
				"SumValue," +
				"FreightIncome," +
				"ConsignIncome," +
				"DeliveryIncome," +
				"InsuranceIncome," +
				"ExtraIncome," +
				"FinancialState," +
				"FinancialRemarks," +
				"SellCenter," +
				"ChargeMode," +
				"PriceByAmount," +
				"PriceByVolume," +
				"PriceByWeight," +
				"InsuranceRate," +
				"originCity," +
				"originProvince," +
				"destinationCity," +
				"destinationProvince," +
				"customerNumber," +
				"consigneeCompany," +
				"consignee)" +
				" values(?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?);";
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
			pstmt.setDouble(9, dto.getFreightIncome());
			pstmt.setDouble(10, dto.getConsignIncome());
			pstmt.setDouble(11, dto.getDeliveryIncome());
			pstmt.setDouble(12, dto.getInsuranceIncome());
			pstmt.setDouble(13, dto.getExtraIncome());
			pstmt.setString(14, dto.getFinancialState());
			pstmt.setString(15, dto.getFinancialRemarks());
			pstmt.setString(16, dto.getSellCenter());
			pstmt.setString(17, dto.getChargeMode());
			pstmt.setDouble(18, dto.getPriceByAmount());
			pstmt.setDouble(19, dto.getPriceByVolume());
			pstmt.setDouble(20, dto.getPriceByWeight());
			pstmt.setDouble(21, dto.getInsuranceRate());
			pstmt.setString(22, dto.getOriginCity());
			pstmt.setString(23, dto.getOriginProvince());
			pstmt.setString(24, dto.getDestinationCity());
			pstmt.setString(25, dto.getDestinationProvince());
			pstmt.setString(26, dto.getCustomerNumber());
			pstmt.setString(27, dto.getConsigneeCompany());
			pstmt.setString(28, dto.getConsignee());
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
	
	
	public ArrayList<FreightIncomeDTO> queryOnCondition(
			FreightIncomeDTO dto,Date startDay,Date endDay, int start, int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<FreightIncomeDTO> resList=new ArrayList<FreightIncomeDTO>();
		String sql=" select * from FreightIncomes " +
				" where "+!Tools.isVoid(dto.getFreightManifestID())+" and FreightManifestID = ? " +
				" or " +Tools.isVoid(dto.getFreightManifestID())+
				" and ("+Tools.isVoid(dto.getCustomer())+" or Customer like ? )" +
				" and ("+Tools.isVoid(dto.getCustomerType())+" or CustomerType = ?)" +
				" and ("+Tools.isVoid(dto.getFinancialState())+" or FinancialState = ?)" +
				" and ("+Tools.isVoid(dto.getSellCenter())+" or SellCenter = ?)" +
				" and ("+Tools.isVoid(dto.getDateCreated())+" or DateCreated >= ?)" +
				" and ("+Tools.isVoid(dto.getDateCreated())+" or DateCreated <= ?)" +
				" and ("+Tools.isVoid(dto.getCustomerNumber())+" or customerNumber like ?)" +
						" order by freightmanifestid desc" +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getFreightManifestID()));
			pstmt.setString(2, "%"+dto.getCustomer()+"%");
			pstmt.setString(3, dto.getCustomerType());
			pstmt.setString(4, dto.getFinancialState());
			pstmt.setString(5, dto.getSellCenter());
			pstmt.setDate(6, startDay);
			pstmt.setDate(7, endDay);
			pstmt.setString(8, "%"+dto.getCustomerNumber()+"%");
			pstmt.setInt(9, start);
			pstmt.setInt(10, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					FreightIncomeDTO resdto=new FreightIncomeDTO();
					resdto.setFreightManifestID(rs.getInt("FreightManifestID"));
					
					resdto.setCustomerType(rs.getString("CustomerType"));
					resdto.setCustomer(rs.getString("Customer"));
					resdto.setDateCreated(rs.getDate("DateCreated"));
					resdto.setSumAmount(rs.getInt("SumAmount"));
					resdto.setSumVolume(rs.getDouble("SumVolume"));
					resdto.setSumWeight(rs.getDouble("SumWeight"));
					resdto.setSumValue(rs.getDouble("SumValue"));
					resdto.setFreightIncome(rs.getDouble("FreightIncome"));
					resdto.setConsignIncome(rs.getDouble("ConsignIncome"));
					resdto.setDeliveryIncome(rs.getDouble("DeliveryIncome"));
					resdto.setInsuranceIncome(rs.getDouble("InsuranceIncome"));
					resdto.setExtraIncome(rs.getDouble("ExtraIncome"));
					resdto.setFinancialState(rs.getString("FinancialState"));
					resdto.setFinancialRemarks(rs.getString("FinancialRemarks"));
					resdto.setSellCenter(rs.getString("SellCenter"));
					resdto.setChargeMode(rs.getString("ChargeMode"));
					resdto.setPriceByAmount(rs.getDouble("PriceByAmount"));
					resdto.setPriceByVolume(rs.getDouble("PriceByVolume"));
					resdto.setPriceByWeight(rs.getDouble("PriceByWeight"));
					resdto.setInsuranceRate(rs.getDouble("InsuranceRate"));
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
			FreightIncomeDTO dto,Date startDay,Date endDay) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		int amount=0;
		String sql=" select count(*) Amount from FreightIncomes " +
		" where "+!Tools.isVoid(dto.getFreightManifestID())+" and FreightManifestID = ? " +
		" or " +Tools.isVoid(dto.getFreightManifestID())+
		" and ("+Tools.isVoid(dto.getCustomer())+" or Customer like ? )" +
		" and ("+Tools.isVoid(dto.getCustomerType())+" or CustomerType = ?)" +
		" and ("+Tools.isVoid(dto.getFinancialState())+" or FinancialState = ?)" +
		" and ("+Tools.isVoid(dto.getSellCenter())+" or SellCenter = ?)" +
		" and ("+Tools.isVoid(dto.getDateCreated())+" or DateCreated >= ?)" +
		" and ("+Tools.isVoid(dto.getDateCreated())+" or DateCreated <= ?)" +
		" and ("+Tools.isVoid(dto.getCustomerNumber())+" or customerNumber like ?)" +
				" order by freightmanifestid desc";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getFreightManifestID()));
			pstmt.setString(2, "%"+dto.getCustomer()+"%");
			pstmt.setString(3, dto.getCustomerType());
			pstmt.setString(4, dto.getFinancialState());
			pstmt.setString(5, dto.getSellCenter());
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
	public boolean updateIncomes(FreightIncomeDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getFreightManifestID()==null)
			return false;
		String sql="update FreightIncomes set FreightIncome=?," +
				"ConsignIncome=?," +
				"DeliveryIncome=?," +
				"InsuranceIncome=?," +
				"ExtraIncome=?, " +
				"ChargeMode=?," +
				"FinancialRemarks=?," +
				"PriceByAmount=?," +
				"PriceByVolume=?," +
				"PriceByWeight=?," +
				"InsuranceRate=?," +
				"SellCenter=? where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDouble(1, dto.getFreightIncome());
			pstmt.setDouble(2, dto.getConsignIncome());
			pstmt.setDouble(3, dto.getDeliveryIncome());
			pstmt.setDouble(4, dto.getInsuranceIncome());
			pstmt.setDouble(5, dto.getExtraIncome());
			pstmt.setString(6, dto.getChargeMode());
			pstmt.setString(7, dto.getFinancialRemarks());
			pstmt.setDouble(8, dto.getPriceByAmount());
			pstmt.setDouble(9, dto.getPriceByVolume());
			pstmt.setDouble(10, dto.getPriceByWeight());
			pstmt.setDouble(11, dto.getInsuranceRate());
			pstmt.setString(12, dto.getSellCenter());
			pstmt.setInt(13, dto.getFreightManifestID());
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
	public boolean updateFinancialState(FreightIncomeDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getFreightManifestID()==null || dto.getFinancialState()==null)
			return false;
		String sql="update FreightIncomes set FinancialState=?  where FreightManifestID=?";
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
