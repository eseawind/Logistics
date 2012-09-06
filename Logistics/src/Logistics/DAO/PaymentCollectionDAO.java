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
import Logistics.DTO.PaymentCollectionDTO;

public class PaymentCollectionDAO extends BaseDAO {
	public boolean delete(Integer id) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(id==null)
			return true;
		String sql="delete from PaymentCollection where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public PaymentCollectionDTO getByID(Integer id)throws Exception{
		//申明对象
		PaymentCollectionDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(id==null)
			return null;
		String sql="select * from PaymentCollection where FreightManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1,id);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new PaymentCollectionDTO();
				resdto.setFreightManifestID(rs.getInt("FreightManifestID"));
				resdto.setCustomerID(rs.getInt("CustomerID"));
				resdto.setCustomer(rs.getString("Customer"));
				resdto.setOriginCity(rs.getString("OriginCity"));
				resdto.setDestinationCity(rs.getString("DestinationCity"));
				resdto.setOriginProvince(rs.getString("OriginProvince"));
				resdto.setDestinationProvince(rs.getString("DestinationProvince"));
				resdto.setDateCreated(rs.getDate("DateCreated"));
				resdto.setState(rs.getString("State"));
				resdto.setDateModified(rs.getDate("DateModified"));
				resdto.setExpectedPayment(rs.getDouble("ExpectedPayment"));
				resdto.setRecievedPayment(rs.getDouble("RecievedPayment"));
				resdto.setProcedureFeeRate(rs.getDouble("ProcedureFeeRate"));
				resdto.setProcedureFee(rs.getDouble("ProcedureFee"));
				resdto.setStateRemarks(rs.getString("StateRemarks"));
				resdto.setFinancialState(rs.getString("FinancialState"));
				resdto.setFinancialRemarks(rs.getString("FinancialRemarks"));
				resdto.setSellCenter(rs.getString("SellCenter"));
				resdto.setCostCenter(rs.getString("CostCenter"));
				resdto.setCustomerNumber(rs.getString("customerNumber"));
				
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
	
	public boolean insert(PaymentCollectionDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" PaymentCollection(" +
				   "freightManifestID,"  +
				   "CustomerID ,"   +
				   "Customer   ,"+
				   "OriginCity,"+
				   "DestinationCity,"+
				   "OriginProvince,"+
				   "DestinationProvince,"+
				   "DateCreated,"+
				   "State,"+
				   "DateModified,"+
				   "ExpectedPayment,"+
				   "RecievedPayment,"+
				   "ProcedureFeeRate,"+
				   "ProcedureFee,"+
				   "StateRemarks,"+
				   "FinancialState,"+
				   "FinancialRemarks,"+
				   "CostCenter,"+
				   "SellCenter," +
				   "customerNumber"+
				")" +
				" values(?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getFreightManifestID());
			pstmt.setInt(2, dto.getCustomerID());
			pstmt.setString(3, dto.getCustomer());
			pstmt.setString(4, dto.getOriginCity());
			pstmt.setString(5, dto.getDestinationCity());
			pstmt.setString(6, dto.getOriginProvince());
			pstmt.setString(7, dto.getDestinationProvince());
			pstmt.setDate(8, dto.getDateCreated());
			pstmt.setString(9, dto.getState());
			pstmt.setDate(10, dto.getDateModified());
			pstmt.setDouble(11, dto.getExpectedPayment());
			pstmt.setDouble(12, dto.getRecievedPayment());
			pstmt.setDouble(13, dto.getProcedureFeeRate());
			pstmt.setDouble(14, dto.getProcedureFee());
			pstmt.setString(15, dto.getStateRemarks());
			pstmt.setString(16, dto.getFinancialState());
			pstmt.setString(17, dto.getFinancialRemarks());
			pstmt.setString(18, dto.getCostCenter());
			pstmt.setString(19, dto.getSellCenter());
			pstmt.setString(20, dto.getCustomerNumber());
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
	
	
	public ArrayList<PaymentCollectionDTO> queryOnCondition(
			PaymentCollectionDTO dto,Date startDay,Date endDay, int start, int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<PaymentCollectionDTO> resList=new ArrayList<PaymentCollectionDTO>();
		String sql=" select * from PaymentCollection " +
				" where "+!Tools.isVoid(dto.getFreightManifestID())+" and FreightManifestID=? " +
				" or "+Tools.isVoid(dto.getFreightManifestID())+" " +
				" and ( "+Tools.isVoid(dto.getOriginProvince())+" or OriginProvince like ?)" +
				" and ( "+Tools.isVoid(dto.getOriginCity())+" or OriginCity like ?)" +
				" and ( "+Tools.isVoid(dto.getDestinationProvince())+" or DestinationProvince like ?)" +
				" and ( "+Tools.isVoid(dto.getDestinationCity())+" or DestinationCity like ?)" +
				" and ( "+Tools.isVoid(dto.getCustomer())+" or Customer like ?)" +
				" and ( "+Tools.isVoid(dto.getState())+" or State = ?)" +
				" and ( "+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
				" and ( "+Tools.isVoid(dto.getSellCenter())+" or SellCenter = ?)" +
				" and ( "+Tools.isVoid(dto.getFinancialState())+" or FinancialState=? ) " +
				" and ("+Tools.isVoid(dto.getCustomerNumber())+" or customerNumber like ?)" +
						" order by freightManifestid desc " +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getFreightManifestID()));
			pstmt.setString(2, dto.getOriginProvince()+"%");
			pstmt.setString(3, dto.getOriginCity()+"%");
			pstmt.setString(4, dto.getDestinationProvince()+"%");
			pstmt.setString(5, dto.getDestinationCity()+"%");
			pstmt.setString(6, dto.getCustomer()+"%");
			pstmt.setString(7, dto.getState());
			pstmt.setString(8, dto.getCostCenter());
			pstmt.setString(9, dto.getSellCenter());
			pstmt.setString(10, dto.getFinancialState());
			pstmt.setString(11, "%"+dto.getCustomerNumber()+"%");
			pstmt.setInt(12, start);
			pstmt.setInt(13, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					PaymentCollectionDTO resdto=new PaymentCollectionDTO();
					resdto.setFreightManifestID(rs.getInt("FreightManifestID"));
					resdto.setCustomerID(rs.getInt("CustomerID"));
					resdto.setCustomer(rs.getString("Customer"));
					resdto.setOriginCity(rs.getString("OriginCity"));
					resdto.setDestinationCity(rs.getString("DestinationCity"));
					resdto.setOriginProvince(rs.getString("OriginProvince"));
					resdto.setDestinationProvince(rs.getString("DestinationProvince"));
					resdto.setDateCreated(rs.getDate("DateCreated"));
					resdto.setState(rs.getString("State"));
					resdto.setDateModified(rs.getDate("DateModified"));
					resdto.setExpectedPayment(rs.getDouble("ExpectedPayment"));
					resdto.setRecievedPayment(rs.getDouble("RecievedPayment"));
					resdto.setProcedureFeeRate(rs.getDouble("ProcedureFeeRate"));
					resdto.setProcedureFee(rs.getDouble("ProcedureFee"));
					resdto.setStateRemarks(rs.getString("StateRemarks"));
					resdto.setFinancialState(rs.getString("FinancialState"));
					resdto.setFinancialRemarks(rs.getString("FinancialRemarks"));
					resdto.setSellCenter(rs.getString("SellCenter"));
					resdto.setCostCenter(rs.getString("CostCenter"));
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
			PaymentCollectionDTO dto,Date startDay,Date endDay) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		int amount=0;
		String sql=" select count(*) Amount from PaymentCollection " +
		" where "+!Tools.isVoid(dto.getFreightManifestID())+" and FreightManifestID=? " +
		" or "+Tools.isVoid(dto.getFreightManifestID())+" " +
		" and ( "+Tools.isVoid(dto.getOriginProvince())+" or OriginProvince like ?)" +
		" and ( "+Tools.isVoid(dto.getOriginCity())+" or OriginCity like ?)" +
		" and ( "+Tools.isVoid(dto.getDestinationProvince())+" or DestinationProvince like ?)" +
		" and ( "+Tools.isVoid(dto.getDestinationCity())+" or DestinationCity like ?)" +
		" and ( "+Tools.isVoid(dto.getCustomer())+" or Customer like ?)" +
		" and ( "+Tools.isVoid(dto.getState())+" or State = ?)" +
		" and ( "+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
		" and ( "+Tools.isVoid(dto.getSellCenter())+" or SellCenter = ?)" +
		" and ( "+Tools.isVoid(dto.getFinancialState())+" or FinancialState=? ) " +
		" and ("+Tools.isVoid(dto.getCustomerNumber())+" or customerNumber like ?)" +
				" order by freightManifestid desc ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getFreightManifestID()));
			pstmt.setString(2, dto.getOriginProvince()+"%");
			pstmt.setString(3, dto.getOriginCity()+"%");
			pstmt.setString(4, dto.getDestinationProvince()+"%");
			pstmt.setString(5, dto.getDestinationCity()+"%");
			pstmt.setString(6, dto.getCustomer()+"%");
			pstmt.setString(7, dto.getState());
			pstmt.setString(8, dto.getCostCenter());
			pstmt.setString(9, dto.getSellCenter());
			pstmt.setString(10, dto.getFinancialState());
			pstmt.setString(11, "%"+dto.getCustomerNumber()+"%");
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
	
	public boolean updateState(PaymentCollectionDTO dto)throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getFreightManifestID()==null)
			return false;
		String sql="update PaymentCollection set " +
				" StateRemarks=?" +
				" ,State=? " +
				"  where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getStateRemarks());
			pstmt.setString(2, dto.getState());
			pstmt.setInt(3, dto.getFreightManifestID());
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
	public boolean updateState(ArrayList<PaymentCollectionDTO> dtos)throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dtos==null || dtos.size()==0)
			return false;
		String sql="update PaymentCollection set " +
				" StateRemarks=?" +
				" ,State=? " +
				"  where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(PaymentCollectionDTO dto:dtos){
				pstmt.setString(1, dto.getStateRemarks());
				pstmt.setString(2, dto.getState());
				pstmt.setInt(3, dto.getFreightManifestID());
				pstmt.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally{
			close();
		}
		
	}
	public boolean updateIncomes(PaymentCollectionDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getFreightManifestID()==null)
			return false;
		String sql="update PaymentCollection set ExpectedPayment=?" +
				",RecievedPayment=?" +
				",ProcedureFeeRate=?" +
				",ProcedureFee=?" +
				",FinancialRemarks=?" +
				",CostCenter=?" +
				",SellCenter=?" +
				",DateModified=?" +
				"  where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDouble(1, dto.getExpectedPayment());
			pstmt.setDouble(2, dto.getRecievedPayment());
			pstmt.setDouble(3, dto.getProcedureFeeRate());
			pstmt.setDouble(4, dto.getProcedureFee());
			pstmt.setString(5, dto.getFinancialRemarks());
			pstmt.setString(6, dto.getCostCenter());
			pstmt.setString(7, dto.getSellCenter());
			pstmt.setDate(8, Tools.currDate());
			pstmt.setInt(9, dto.getFreightManifestID());
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
	public boolean updateFinancialState(PaymentCollectionDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getFreightManifestID()==null || dto.getFinancialState()==null)
			return false;
		String sql="update PaymentCollection set FinancialState=?  where FreightManifestID=?";
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
