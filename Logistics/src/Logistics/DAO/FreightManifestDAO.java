package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.sql.Date;

import Logistics.Common.Data;
import Logistics.Common.Tools;
import Logistics.DTO.CargoDTO;
import Logistics.DTO.ConsigneeDTO;
import Logistics.DTO.CustomerDTO;
import Logistics.DTO.FreightCostDTO;
import Logistics.DTO.FreightIncomeDTO;
import Logistics.DTO.FreightManifestDTO;
import Logistics.DTO.FreightStateDTO;

public class FreightManifestDAO extends BaseDAO {

	public boolean delete(FreightManifestDTO dto) throws Exception {
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
		String sql="delete from FreightManifests where FreightManifestID=?";
		String sql1="delete from freightShipment where freightManifestid=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getFreightManifestID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		try{
			pstmt=mysqlTools.getPreparedStatement(sql1);
			pstmt.setInt(2, dto.getFreightManifestID());
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			return true;
		}
		return true;
	}

	public FreightManifestDTO getDTOByID(FreightManifestDTO dto)
			throws Exception {
		//申明对象
		FreightManifestDTO fmdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(dto==null)
			return null;
		if(dto.getFreightManifestID()==null)
			return null;
		String sql="select * from FreightManifests where FreightManifests.FreightManifestID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getFreightManifestID());
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				fmdto= new FreightManifestDTO();
				fmdto.setFreightManifestID(rs.getInt("FreightManifestID"));
				fmdto.setCustomerType(rs.getString("CustomerType"));
				fmdto.setCustomer(rs.getString("Customer"));
				fmdto.setCustomerNumber(rs.getString("CustomerNumber"));
				fmdto.setOriginProvince(rs.getString("OriginProvince"));
				fmdto.setOriginCity(rs.getString("OriginCity"));
				fmdto.setDestinationProvince(rs.getString("DestinationProvince"));
				fmdto.setDestinationCity(rs.getString("DestinationCity"));
				fmdto.setConsigner(rs.getString("Consigner"));
				fmdto.setConsignerPhone(rs.getString("ConsignerPhone"));
				fmdto.setConsignerAddress(rs.getString("ConsignerAddress"));
				
				fmdto.setConsigneeCompany(rs.getString("ConsigneeCompany"));
				fmdto.setConsignee(rs.getString("Consignee"));
				fmdto.setConsigneePhone(rs.getString("ConsigneePhone"));
				fmdto.setConsigneeAddress(rs.getString("ConsigneeAddress"));
				
				fmdto.setDateCreated(rs.getDate("DateCreated"));
				fmdto.setDateExpected(rs.getDate("DateExpected"));
				fmdto.setDateSigned(rs.getDate("DateSigned"));
				fmdto.setDateDelivered(rs.getDate("DateDelivered"));
				
				fmdto.setRemarks(rs.getString("Remarks"));
				
				fmdto.setFreightFee(rs.getDouble("FreightFee"));
				fmdto.setConsignFee(rs.getDouble("ConsignFee"));
				fmdto.setDeliveryFee(rs.getDouble("DeliveryFee"));
				fmdto.setInsuranceFee(rs.getDouble("InsuranceFee"));
				fmdto.setPrepaidFee(rs.getDouble("PrepaidFee"));
				fmdto.setExtraFee(rs.getDouble("ExtraFee"));
				fmdto.setCollectionFee(rs.getDouble("CollectionFee"));
				
				fmdto.setFreightState(rs.getString("FreightState"));
				fmdto.setAckRequirement(rs.getString("AckRequirement"));
				fmdto.setFreightType(rs.getString("FreightType"));
				fmdto.setCostCenter(rs.getString("CostCenter"));
				fmdto.setAnnouncements(rs.getString("Announcements"));
				fmdto.setPaymentType(rs.getString("PaymentType"));
				fmdto.setIsCollection(rs.getString("IsCollection"));
				fmdto.setIsInsurance(rs.getString("IsInsurance"));
				fmdto.setIsPrepaid(rs.getString("IsPrepaid"));
				
				fmdto.setChargeMode(rs.getString("ChargeMode"));
				fmdto.setPriceByAmount(rs.getDouble("PriceByAmount"));
				fmdto.setPriceByVolume(rs.getDouble("PriceByVolume"));
				fmdto.setPriceByWeight(rs.getDouble("PriceByWeight"));
				fmdto.setInsuranceRate(rs.getDouble("InsuranceRate"));
				fmdto.setSellCenter(rs.getString("SellCenter"));
				fmdto.setAckInfo(rs.getString("AckInfo"));
				fmdto.setTransitionInfo(rs.getString("TransitionInfo"));
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return fmdto;
	}
	
	
	public boolean insert(FreightManifestDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" FreightManifests(FreightManifestID,CustomerType,Customer,CustomerNumber" +
				",OriginProvince,OriginCity,DestinationProvince,DestinationCity,Consigner" +
				",ConsignerPhone,ConsignerAddress,ConsigneeCompany,Consignee,ConsigneePhone" +
				",ConsigneeAddress,DateCreated,DateExpected,DateSigned,DateDelivered,Remarks" +
				",FreightFee,ConsignFee,DeliveryFee,InsuranceFee,PrepaidFee,ExtraFee,FreightState" +
				",AckRequirement,FreightType,CostCenter,Announcements,PaymentType,isCollection" +
				",isInsurance,isPrepaid,CollectionFee,ChargeMode,PriceByAmount,PriceByVolume" +
				",PriceByWeight,InsuranceRate,SellCenter)" +
				" values(null,?,?,?,?,?,?,?,?,?,?" +
				",?,?,?,?,?,?,?,?,?,?" +
				",?,?,?,?,?,?,?,?,?,?" +
				",?,?,?,?,?" +
				",?,?,?,?,?" +
				",?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getCustomerType());
			pstmt.setString(2, dto.getCustomer());
			pstmt.setString(3, dto.getCustomerNumber());
			pstmt.setString(4, dto.getOriginProvince());
			pstmt.setString(5, dto.getOriginCity());
			pstmt.setString(6, dto.getDestinationProvince());
			pstmt.setString(7, dto.getDestinationCity());
			pstmt.setString(8, dto.getConsigner());
			pstmt.setString(9, dto.getConsignerPhone());
			pstmt.setString(10, dto.getConsignerAddress());
			pstmt.setString(11, dto.getConsigneeCompany());
			pstmt.setString(12, dto.getConsignee());
			pstmt.setString(13, dto.getConsigneePhone());
			pstmt.setString(14, dto.getConsigneeAddress());
			pstmt.setDate(15, dto.getDateCreated());
			pstmt.setDate(16, dto.getDateExpected());
			pstmt.setDate(17, dto.getDateSigned());
			pstmt.setDate(18, dto.getDateDelivered());
			pstmt.setString(19, dto.getRemarks());
			
			pstmt.setDouble(20, dto.getFreightFee());
			pstmt.setDouble(21, dto.getConsignFee());
			pstmt.setDouble(22, dto.getDeliveryFee());
			pstmt.setDouble(23, dto.getInsuranceFee());
			pstmt.setDouble(24, dto.getPrepaidFee());
			pstmt.setDouble(25, dto.getExtraFee());
			
			pstmt.setString(26, dto.getFreightState());
			pstmt.setString(27, dto.getAckRequirement());
			pstmt.setString(28, dto.getFreightType());
			pstmt.setString(29, dto.getCostCenter());
			pstmt.setString(30, dto.getAnnouncements());
			pstmt.setString(31, dto.getPaymentType());
			pstmt.setString(32, dto.getIsCollection());
			pstmt.setString(33, dto.getIsInsurance());
			pstmt.setString(34, dto.getIsPrepaid());
			pstmt.setDouble(35, dto.getCollectionFee());
			pstmt.setString(36, dto.getChargeMode());
			pstmt.setDouble(37, dto.getPriceByAmount());
			pstmt.setDouble(38, dto.getPriceByVolume());
			pstmt.setDouble(39, dto.getPriceByWeight());
			pstmt.setDouble(40, dto.getInsuranceRate());
			pstmt.setString(41, dto.getSellCenter());
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
	public boolean insert() throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		String sql="insert into " +
				" FreightManifests(FreightManifestID,CustomerType,Customer,CustomerNumber" +
				",OriginProvince,OriginCity,DestinationProvince,DestinationCity,Consigner" +
				",ConsignerPhone,ConsignerAddress,ConsigneeCompany,Consignee,ConsigneePhone" +
				",ConsigneeAddress,DateCreated,DateExpected,DateSigned,DateDelivered,Remarks" +
				",FreightFee,ConsignFee,DeliveryFee,InsuranceFee,PrepaidFee,ExtraFee,FreightState" +
				",AckRequirement,FreightType,CostCenter,Announcements,PaymentType,isCollection" +
				",isInsurance,isPrepaid,CollectionFee,ChargeMode,PriceByAmount,PriceByVolume" +
				",PriceByWeight,InsuranceRate,SellCenter)" +
				" values(null,?,?,?,?,?,?,?,?,?,?" +
				",?,?,?,?,?,?,?,?,?,?" +
				",?,?,?,?,?,?,?,?,?,?" +
				",?,?,?,?,?" +
				",?,?,?,?,?" +
				",?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			Random random=new Random();
			for(int j=0;j<Data.testDataSize;j++){
				if(j%1000==0)
					System.out.print(""+j+"");
				for(int i=1;i<15;i++)
					pstmt.setString(i, ""+random.nextInt(10));
				pstmt.setDate(15, Tools.currDate());
				pstmt.setDate(16, Tools.currDate());
				pstmt.setDate(17, Tools.currDate());
				pstmt.setDate(18, Tools.currDate());
				pstmt.setString(19, ""+random.nextInt(10));
				for(int i=20;i<26;i++){
					pstmt.setDouble(i,random.nextDouble());
				}
				for(int i=26;i<35;i++){
					pstmt.setString(i, ""+random.nextInt(10));
				}
				pstmt.setDouble(35,random.nextDouble());
				pstmt.setString(36, ""+random.nextInt(10));
				for(int i=37;i<41;i++){
					pstmt.setDouble(i,random.nextDouble());
				}
				pstmt.setString(41, ""+random.nextInt(10));
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally{
			close();
		}
		return true;
	}
	public ArrayList<FreightStateDTO> queryAllStates(Integer fmid) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<FreightStateDTO> resList=new ArrayList<FreightStateDTO>();
		String sql="select * from FreightStates where freightManifestID=? order by Date";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, fmid);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					FreightStateDTO dto=new FreightStateDTO();
					dto.setFreightManifestID(rs.getInt("FreightManifestID"));
					dto.setDate(rs.getTimestamp("Date"));
					dto.setState(rs.getString("State"));
					dto.setStateRemarks(rs.getString("StateRemarks"));
					resList.add(dto);
				}
			}
			return resList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	public ArrayList<FreightManifestDTO> queryAll() throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<FreightManifestDTO> clist=new ArrayList<FreightManifestDTO>();
		String sql="select * from FreightManifests";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					FreightManifestDTO fmdto=new FreightManifestDTO();
					fmdto.setFreightManifestID(rs.getInt("FreightManifestID"));
					fmdto.setCustomerType(rs.getString("CustomerType"));
					fmdto.setCustomer(rs.getString("Customer"));
					fmdto.setCustomerNumber(rs.getString("CustomerNumber"));
					fmdto.setOriginProvince(rs.getString("OriginProvince"));
					fmdto.setOriginCity(rs.getString("OriginCity"));
					fmdto.setDestinationProvince(rs.getString("DestinationProvince"));
					fmdto.setDestinationCity(rs.getString("DestinationCity"));
					fmdto.setConsigner(rs.getString("Consigner"));
					fmdto.setConsignerPhone(rs.getString("ConsignerPhone"));
					fmdto.setConsignerAddress(rs.getString("ConsignerAddress"));
					
					fmdto.setConsigneeCompany(rs.getString("ConsigneeCompany"));
					fmdto.setConsignee(rs.getString("Consignee"));
					fmdto.setConsigneePhone(rs.getString("ConsigneePhone"));
					fmdto.setConsigneeAddress(rs.getString("ConsigneeAddress"));
					
					fmdto.setDateCreated(rs.getDate("DateCreated"));
					fmdto.setDateExpected(rs.getDate("DateExpected"));
					fmdto.setDateSigned(rs.getDate("DateSigned"));
					fmdto.setDateDelivered(rs.getDate("DateDelivered"));
					
					fmdto.setRemarks(rs.getString("Remarks"));
					
					fmdto.setFreightFee(rs.getDouble("FreightFee"));
					fmdto.setConsignFee(rs.getDouble("ConsignFee"));
					fmdto.setDeliveryFee(rs.getDouble("DeliveryFee"));
					fmdto.setInsuranceFee(rs.getDouble("InsuranceFee"));
					fmdto.setPrepaidFee(rs.getDouble("PrepaidFee"));
					fmdto.setExtraFee(rs.getDouble("ExtraFee"));
					fmdto.setCollectionFee(rs.getDouble("CollectionFee"));
					
					fmdto.setFreightState(rs.getString("FreightState"));
					fmdto.setAckRequirement(rs.getString("AckRequirement"));
					fmdto.setFreightType(rs.getString("FreightType"));
					fmdto.setCostCenter(rs.getString("CostCenter"));
					fmdto.setAnnouncements(rs.getString("Announcements"));
					fmdto.setPaymentType(rs.getString("PaymentType"));
					fmdto.setIsCollection(rs.getString("IsCollection"));
					fmdto.setIsInsurance(rs.getString("IsInsurance"));
					fmdto.setIsPrepaid(rs.getString("IsPrepaid"));
					clist.add(fmdto);
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

	public ArrayList<FreightIncomeDTO> queryFreightIncomeOnCondition(
			FreightIncomeDTO dto,Date startDay,Date endDay, int start, int limit) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		ArrayList<FreightIncomeDTO> resList=new ArrayList<FreightIncomeDTO>();
		String sql=" select * from FreightManifests " +
				" where FreightManifestID like ?" +
				" and Customer like ? " +
				" and CustomerType like ?" +
				" and FinancialState like ?" +
				" and SellCenter like ?" +
				" and DateCreated between ? and ? " +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(dto.getFreightManifestID()!=null)
			{
				pstmt.setInt(1, dto.getFreightManifestID());
				pstmt.setString(2, "%");
				pstmt.setString(3, "%");
				pstmt.setString(4, "%");
				pstmt.setString(5, "%");
				
				pstmt.setDate(6, Tools.firstDate());
				pstmt.setDate(7, Tools.lastDate());
				
			}
			else
			{
				pstmt.setString(1, "%");
				if(dto.getCustomer()!=null && dto.getCustomer().length()!=0)
					pstmt.setString(2, dto.getCustomer());
				else
					pstmt.setString(2, "%");
				if(dto.getCustomerType()!=null && dto.getCustomerType().length()!=0)
					pstmt.setString(3, dto.getCustomerType());
				else
					pstmt.setString(3, "%");
				if(dto.getFinancialState()!=null && dto.getFinancialState().length()!=0)
					pstmt.setString(4, dto.getFinancialState());
				else
					pstmt.setString(4, "%");
				if(dto.getSellCenter()!=null && dto.getSellCenter().length()!=0)
					pstmt.setString(5, dto.getSellCenter());
				else
					pstmt.setString(5, "%");
				if(startDay!=null)
					pstmt.setDate(6, startDay);
				else
					pstmt.setDate(6, Date.valueOf("1000-01-01"));
				if(endDay!=null)
					pstmt.setDate(7, endDay);
				else
					pstmt.setDate(7, Date.valueOf("9999-12-31"));
				
				
			}
			pstmt.setInt(8, start);
			pstmt.setInt(9, limit);
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
	
	public int queryQualifiedAmountFreightIncomeOnCondition(
			FreightIncomeDTO dto,Date startDay,Date endDay) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		int amount=0;
		String sql=" select count(*) Amount from FreightManifests " +
				" where FreightManifestID like ?" +
				" and Customer like ? " +
				" and CustomerType like ?" +
				" and FinancialState like ?" +
				" and SellCenter like ?" +
				" and DateCreated between ? and ? ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(dto.getFreightManifestID()!=null)
			{
				pstmt.setInt(1, dto.getFreightManifestID());
				pstmt.setString(2, "%");
				pstmt.setString(3, "%");
				pstmt.setString(4, "%");
				pstmt.setString(5, "%");
				
				pstmt.setDate(6, Date.valueOf("1000-01-01"));
				pstmt.setDate(7, Date.valueOf("9999-12-31"));
				
			}
			else
			{
				pstmt.setString(1, "%");
				if(dto.getCustomer()!=null && dto.getCustomer().length()!=0)
					pstmt.setString(2, dto.getCustomer());
				else
					pstmt.setString(2, "%");
				if(dto.getCustomerType()!=null && dto.getCustomerType().length()!=0)
					pstmt.setString(3, dto.getCustomerType());
				else
					pstmt.setString(3, "%");
				if(dto.getFinancialState()!=null && dto.getFinancialState().length()!=0)
					pstmt.setString(4, dto.getFinancialState());
				else
					pstmt.setString(4, "%");
				if(dto.getSellCenter()!=null && dto.getSellCenter().length()!=0)
					pstmt.setString(5, dto.getSellCenter());
				else
					pstmt.setString(5, "%");
				if(startDay!=null)
					pstmt.setDate(6, startDay);
				else
					pstmt.setDate(6, Date.valueOf("1000-01-01"));
				if(endDay!=null)
					pstmt.setDate(7, endDay);
				else
					pstmt.setDate(7, Date.valueOf("9999-12-31"));
				
				
			}
			rs=pstmt.executeQuery();
			if(rs!=null)
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
	public ArrayList<FreightManifestDTO> queryOnCondition2(
			FreightManifestDTO dto,FreightManifestDTO dto2, int start, int limit,boolean isntShipped) throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(dto==null)
			dto=new FreightManifestDTO();
		if(dto2==null)
			dto2=new FreightManifestDTO();
		ArrayList<FreightManifestDTO> fmlist=new ArrayList<FreightManifestDTO>();
		String sql=" select * from FreightManifests " +
				" where "+!Tools.isVoid(dto.getFreightManifestID())+" and FreightManifestID = ? " +
				" or " +Tools.isVoid(dto.getFreightManifestID())+
				" and ("+Tools.isVoid(dto.getOriginProvince())+" or OriginProvince = ?)" +
				" and ("+Tools.isVoid(dto.getOriginCity())+" or OriginCity = ?)" +
				" and ("+Tools.isVoid(dto.getDestinationProvince())+" or DestinationProvince = ?) " +
				" and ("+Tools.isVoid(dto.getDestinationCity())+" or DestinationCity = ?)" +
				" and ("+Tools.isVoid(dto.getConsigneeCompany())+" or ConsigneeCompany like ?) " +
				" and ("+Tools.isVoid(dto.getConsignee())+" or Consignee like ? )" +
				" and ("+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
				" and ("+Tools.isVoid(dto.getFreightState())+" or FreightState = ?)" +
				" and ("+Tools.isVoid(dto.getDateCreated())+" or DateCreated >= ?)" +
				" and ("+Tools.isVoid(dto2.getDateCreated())+" or DateCreated <= ?)" +
				" and ("+Tools.isVoid(dto.getDateExpected())+" or DateExpected >= ?)" +
				" and ("+Tools.isVoid(dto2.getDateExpected())+" or DateExpected <= ?)" +
				" and ("+Tools.isVoid(dto.getDateSigned())+" or DateSigned >= ?)" +
				" and ("+Tools.isVoid(dto2.getDateSigned())+" or DateSigned <= ?)" +
				" and ("+Tools.isVoid(dto.getDateDelivered())+" or DateDelivered >= ?) " +
				" and ("+Tools.isVoid(dto2.getDateDelivered())+" or DateDelivered <= ?) " +
				" and ("+Tools.isVoid(dto.getSellCenter())+" or SellCenter = ?) " +
				" and ("+Tools.isVoid(dto.getCustomer())+" or Customer like ?) " +
				" and ("+Tools.isVoid(dto.getCustomerNumber())+" or CustomerNumber like ?) "+
				(isntShipped?" and freightmanifestid not in (select freightmanifestid from freightShipment) ":"")+
				" order by FreightManifestID desc" +
				" limit ?,?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getFreightManifestID()));
			pstmt.setString(2, dto.getOriginProvince());
			pstmt.setString(3, dto.getOriginCity());
			pstmt.setString(4, dto.getDestinationProvince());
			pstmt.setString(5, dto.getDestinationCity());
			pstmt.setString(6, "%"+dto.getConsigneeCompany()+"%");
			pstmt.setString(7, "%"+dto.getConsignee()+"%");
			pstmt.setString(8, dto.getCostCenter());
			pstmt.setString(9, dto.getFreightState());
			pstmt.setDate(10, dto.getDateCreated());
			pstmt.setDate(11, dto2.getDateCreated());
			pstmt.setDate(12, dto.getDateExpected());
			pstmt.setDate(13, dto2.getDateExpected());
			pstmt.setDate(14, dto.getDateSigned());
			pstmt.setDate(15, dto2.getDateSigned());
			pstmt.setDate(16, dto.getDateDelivered());
			pstmt.setDate(17, dto2.getDateDelivered());
			pstmt.setString(18, dto.getSellCenter());
			pstmt.setString(19, "%"+dto.getCustomer()+"%");
			pstmt.setString(20, "%"+dto.getCustomerNumber()+"%");
			pstmt.setInt(21, start);
			pstmt.setInt(22, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					FreightManifestDTO fmdto=new FreightManifestDTO();
					fmdto.setFreightManifestID(rs.getInt("FreightManifestID"));
					fmdto.setCustomerType(rs.getString("CustomerType"));
					fmdto.setCustomer(rs.getString("Customer"));
					fmdto.setCustomerNumber(rs.getString("CustomerNumber"));
					fmdto.setOriginProvince(rs.getString("OriginProvince"));
					fmdto.setOriginCity(rs.getString("OriginCity"));
					fmdto.setDestinationProvince(rs.getString("DestinationProvince"));
					fmdto.setDestinationCity(rs.getString("DestinationCity"));
					fmdto.setConsigner(rs.getString("Consigner"));
					fmdto.setConsignerPhone(rs.getString("ConsignerPhone"));
					fmdto.setConsignerAddress(rs.getString("ConsignerAddress"));
					
					fmdto.setConsigneeCompany(rs.getString("ConsigneeCompany"));
					fmdto.setConsignee(rs.getString("Consignee"));
					fmdto.setConsigneePhone(rs.getString("ConsigneePhone"));
					fmdto.setConsigneeAddress(rs.getString("ConsigneeAddress"));
					
					fmdto.setDateCreated(rs.getDate("DateCreated"));
					fmdto.setDateExpected(rs.getDate("DateExpected"));
					fmdto.setDateSigned(rs.getDate("DateSigned"));
					fmdto.setDateDelivered(rs.getDate("DateDelivered"));
					
					fmdto.setRemarks(rs.getString("Remarks"));
					
					fmdto.setFreightFee(rs.getDouble("FreightFee"));
					fmdto.setConsignFee(rs.getDouble("ConsignFee"));
					fmdto.setDeliveryFee(rs.getDouble("DeliveryFee"));
					fmdto.setInsuranceFee(rs.getDouble("InsuranceFee"));
					fmdto.setPrepaidFee(rs.getDouble("PrepaidFee"));
					fmdto.setExtraFee(rs.getDouble("ExtraFee"));
					
					fmdto.setFreightState(rs.getString("FreightState"));
					fmdto.setAckRequirement(rs.getString("AckRequirement"));
					fmdto.setFreightType(rs.getString("FreightType"));
					fmdto.setCostCenter(rs.getString("CostCenter"));
					fmdto.setSellCenter(rs.getString("SellCenter"));
					
					fmdto.setAnnouncements(rs.getString("Announcements"));
					fmdto.setPaymentType(rs.getString("PaymentType"));
					fmdto.setIsCollection(rs.getString("IsCollection"));
					fmdto.setIsInsurance(rs.getString("IsInsurance"));
					fmdto.setIsPrepaid(rs.getString("IsPrepaid"));
					fmdto.setAckInfo(rs.getString("AckInfo"));
					fmdto.setTransitionInfo(rs.getString("TransitionInfo"));
					fmlist.add(fmdto);
				}
			}
			return fmlist;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		
	}
	

	
	public int queryQualifiedAmount2(
			FreightManifestDTO dto,FreightManifestDTO dto2,boolean isntShipped) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(dto==null)
			dto=new FreightManifestDTO();
		if(dto2==null)
			dto2=new FreightManifestDTO();
		int amount=0;
		String sql=" select count(*) Amount from FreightManifests " +
		" where "+!Tools.isVoid(dto.getFreightManifestID())+" and FreightManifestID = ? " +
		" or " +Tools.isVoid(dto.getFreightManifestID())+
		" and ("+Tools.isVoid(dto.getOriginProvince())+" or OriginProvince = ?)" +
		" and ("+Tools.isVoid(dto.getOriginCity())+" or OriginCity = ?)" +
		" and ("+Tools.isVoid(dto.getDestinationProvince())+" or DestinationProvince = ?) " +
		" and ("+Tools.isVoid(dto.getDestinationCity())+" or DestinationCity = ?)" +
		" and ("+Tools.isVoid(dto.getConsigneeCompany())+" or ConsigneeCompany like ?) " +
		" and ("+Tools.isVoid(dto.getConsignee())+" or Consignee like ? )" +
		" and ("+Tools.isVoid(dto.getCostCenter())+" or CostCenter = ?)" +
		" and ("+Tools.isVoid(dto.getFreightState())+" or FreightState = ?)" +
		" and ("+Tools.isVoid(dto.getDateCreated())+" or DateCreated >= ?)" +
		" and ("+Tools.isVoid(dto2.getDateCreated())+" or DateCreated <= ?)" +
		" and ("+Tools.isVoid(dto.getDateExpected())+" or DateExpected >= ?)" +
		" and ("+Tools.isVoid(dto2.getDateExpected())+" or DateExpected <= ?)" +
		" and ("+Tools.isVoid(dto.getDateSigned())+" or DateSigned >= ?)" +
		" and ("+Tools.isVoid(dto2.getDateSigned())+" or DateSigned <= ?)" +
		" and ("+Tools.isVoid(dto.getDateDelivered())+" or DateDelivered >= ?) " +
		" and ("+Tools.isVoid(dto2.getDateDelivered())+" or DateDelivered <= ?) " +
		" and ("+Tools.isVoid(dto.getSellCenter())+" or SellCenter = ?) " +
		" and ("+Tools.isVoid(dto.getCustomer())+" or Customer like ?) " +
		" and ("+Tools.isVoid(dto.getCustomerNumber())+" or CustomerNumber like ?) "+
		(isntShipped?" and freightmanifestid not in (select freightmanifestid from freightShipment) ":"")+
		" order by FreightManifestID desc";
		
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(dto.getFreightManifestID()));
			pstmt.setString(2, dto.getOriginProvince());
			pstmt.setString(3, dto.getOriginCity());
			pstmt.setString(4, dto.getDestinationProvince());
			pstmt.setString(5, dto.getDestinationCity());
			pstmt.setString(6, "%"+dto.getConsigneeCompany()+"%");
			pstmt.setString(7, "%"+dto.getConsignee()+"%");
			pstmt.setString(8, dto.getCostCenter());
			pstmt.setString(9, dto.getFreightState());
			pstmt.setDate(10, dto.getDateCreated());
			pstmt.setDate(11, dto2.getDateCreated());
			pstmt.setDate(12, dto.getDateExpected());
			pstmt.setDate(13, dto2.getDateExpected());
			pstmt.setDate(14, dto.getDateSigned());
			pstmt.setDate(15, dto2.getDateSigned());
			pstmt.setDate(16, dto.getDateDelivered());
			pstmt.setDate(17, dto2.getDateDelivered());
			pstmt.setString(18, dto.getSellCenter());
			pstmt.setString(19, "%"+dto.getCustomer()+"%");
			pstmt.setString(20, "%"+dto.getCustomerNumber()+"%");
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
	public int[] queryOvertimeManifestAmount() throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int[] amount=new int[4];
		String sql=" select count(*) Amount,freightState from FreightManifests" +
				" where DateExpected < ? and FreightState in ('已创建','已发货','已到港','已中转') " +
				" group by freightState";
		
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDate(1,Tools.currDate());
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					if("已创建".equals(rs.getString("freightState")))
						amount[0]=rs.getInt("Amount");
					if("已发货".equals(rs.getString("freightState")))
						amount[1]=rs.getInt("Amount");
					if("已到港".equals(rs.getString("freightState")))
						amount[2]=rs.getInt("Amount");
					if("已中转".equals(rs.getString("freightState")))
						amount[3]=rs.getInt("Amount");
				}
			}
			return amount;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return amount;
		}
		finally{
			close();
		}
	}
	public int queryUnusualFreightManifestAmount() throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		int amount=0;
		String sql=" select count(*) Amount from FreightManifests" +
				" where  FreightState in ('异常') ";
		
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
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
	
	public boolean updateFreightState(FreightManifestDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null)
			return false;
		String sql="update FreightManifests set FreightState=?,AckInfo=?,TransitionInfo=? where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getFreightState());
			pstmt.setString(2, dto.getAckInfo());
			pstmt.setString(3, dto.getTransitionInfo());
			pstmt.setInt(4, dto.getFreightManifestID());
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
	public boolean updateFreightIncome(FreightIncomeDTO dto) throws Exception {
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
				"ExtraIncome=? where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDouble(1, dto.getFreightIncome());
			pstmt.setDouble(2, dto.getConsignIncome());
			pstmt.setDouble(3, dto.getInsuranceIncome());
			pstmt.setDouble(4, dto.getExtraIncome());
			pstmt.setInt(5, dto.getFreightManifestID());
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
	public boolean updateFreightCost(FreightCostDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getFreightManifestID()==null)
			return false;
		String sql="update FreightCosts set FreightCost=?," +
				"PrepaidCost=?," +
				"ExtraCost=?," +
				"ExtraCostRemarks=? " +
				" where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDouble(1, dto.getFreightCost());
			pstmt.setDouble(2, dto.getPrepaidCost());
			pstmt.setDouble(3, dto.getExtraCost());
			pstmt.setString(4, dto.getExtraCostRemarks());
			pstmt.setInt(5, dto.getFreightManifestID());
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
	public boolean archiveFreightIncome(FreightIncomeDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getFreightManifestID()==null)
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
	public boolean archiveFreightCost(FreightCostDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getFreightManifestID()==null)
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
	
	public boolean updateDate(FreightManifestDTO.dateType type,Integer fmid) throws Exception{
		
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(type==null || fmid==null)
			return false;
		String sql="update FreightManifests set "+type.toString()+"=? where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDate(1, Tools.currDate());
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
	public boolean update(FreightManifestDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null)
			return false;
		String sql="update FreightManifests set CustomerType=?,Customer=?,CustomerNumber=?" +
				",OriginProvince=?,OriginCity=?,DestinationProvince=?,DestinationCity=?,Consigner" +
				"=?,ConsignerPhone=?,ConsignerAddress=?,ConsigneeCompany=?,Consignee=?,ConsigneePhone" +
				"=?,ConsigneeAddress=?,DateExpected=?,DateSigned=?,DateDelivered=?,Remarks" +
				"=?,FreightFee=?,ConsignFee=?,DeliveryFee=?,InsuranceFee=?,PrepaidFee=?,ExtraFee=?,AckRequirement=?,FreightType=?,CostCenter=?,Announcements=?,PaymentType=?,isCollection" +
				"=?,isInsurance=?,isPrepaid=?,CollectionFee=?,ChargeMode=?,PriceByAmount=?,PriceByVolume=?" +
				",PriceByWeight=?,InsuranceRate=?,SellCenter=? where FreightManifestID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getCustomerType());
			pstmt.setString(2, dto.getCustomer());
			pstmt.setString(3, dto.getCustomerNumber());
			pstmt.setString(4, dto.getOriginProvince());
			pstmt.setString(5, dto.getOriginCity());
			pstmt.setString(6, dto.getDestinationProvince());
			pstmt.setString(7, dto.getDestinationCity());
			pstmt.setString(8, dto.getConsigner());
			pstmt.setString(9, dto.getConsignerPhone());
			pstmt.setString(10, dto.getConsignerAddress());
			pstmt.setString(11, dto.getConsigneeCompany());
			pstmt.setString(12, dto.getConsignee());
			pstmt.setString(13, dto.getConsigneePhone());
			pstmt.setString(14, dto.getConsigneeAddress());
			
			pstmt.setDate(15, dto.getDateExpected());
			pstmt.setDate(16, dto.getDateSigned());
			pstmt.setDate(17, dto.getDateDelivered());
			pstmt.setString(18, dto.getRemarks());
			
			pstmt.setDouble(19, dto.getFreightFee());
			pstmt.setDouble(20, dto.getConsignFee());
			pstmt.setDouble(21, dto.getDeliveryFee());
			pstmt.setDouble(22, dto.getInsuranceFee());
			pstmt.setDouble(23, dto.getPrepaidFee());
			pstmt.setDouble(24, dto.getExtraFee());
			pstmt.setString(25, dto.getAckRequirement());
			pstmt.setString(26, dto.getFreightType());
			pstmt.setString(27, dto.getCostCenter());
			pstmt.setString(28, dto.getAnnouncements());
			pstmt.setString(29, dto.getPaymentType());
			pstmt.setString(30, dto.getIsCollection());
			pstmt.setString(31, dto.getIsInsurance());
			pstmt.setString(32, dto.getIsPrepaid());
			pstmt.setDouble(33, dto.getCollectionFee());
			pstmt.setString(34, dto.getChargeMode());
			pstmt.setDouble(35, dto.getPriceByAmount());
			pstmt.setDouble(36, dto.getPriceByVolume());
			pstmt.setDouble(37, dto.getPriceByWeight());
			pstmt.setDouble(38, dto.getInsuranceRate());
			pstmt.setString(39, dto.getSellCenter());
			pstmt.setInt(40, dto.getFreightManifestID());
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
	
	public CargoDTO querySum(Integer freightManifestID) throws Exception{
		CargoDTO cdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(freightManifestID==null)
			return null;
		String sql="select sum(Amount) SumAmount,sum(Weight) SumWeight," +
				"sum(Volume) SumVolume,sum(value) SumValue " +
				" from Cargos where FreightManifestID=?";
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, freightManifestID);
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
	
	public int queryQualifiedFMsOnCondition(Integer shipmentManifestID,String freightState)
	throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(shipmentManifestID==null){
			return 0;
		}
		int amount=0;
		String sql="select count(*) Amount from FreightManifests " +
				" where FreightState=? and  FreightManifestID in (select FreightManifestID from FreightShipment" +
				" where ShipmentManifestID=? ) ";
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, freightState);
			pstmt.setInt(2, shipmentManifestID);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				
				amount=rs.getInt("Amount");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
		finally{
			close();
		}
		return amount;
		
	}
	public ArrayList<FreightManifestDTO> queryFMListOnCondtition(Integer shipmentManifestID,String freightState,int start,int limit)
	throws Exception{
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(shipmentManifestID==null){
			return null;
		}
		ArrayList<FreightManifestDTO> resList=new ArrayList<FreightManifestDTO>();
		String sql="select * from FreightManifests " +
				" where FreightState=? and FreightManifestID in (select FreightManifestID from FreightShipment" +
				" where ShipmentManifestID=? ) " +
				" limit ?,?";
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, freightState);
			pstmt.setInt(2, shipmentManifestID);
			pstmt.setInt(3, start);
			pstmt.setInt(4, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				
				while(rs.next()){
					FreightManifestDTO fmdto=new FreightManifestDTO();
					
					fmdto.setFreightManifestID(rs.getInt("FreightManifestID"));
					fmdto.setOriginProvince(rs.getString("OriginProvince"));
					fmdto.setOriginCity(rs.getString("OriginCity"));
					fmdto.setDestinationProvince(rs.getString("DestinationProvince"));
					fmdto.setDestinationCity(rs.getString("DestinationCity"));
					fmdto.setFreightState(rs.getString("FreightState"));
					
					fmdto.setConsigneeCompany(rs.getString("ConsigneeCompany"));
					fmdto.setConsignee(rs.getString("Consignee"));
					
					
					fmdto.setDateCreated(rs.getDate("DateCreated"));
					fmdto.setDateExpected(rs.getDate("DateExpected"));
					fmdto.setDateSigned(rs.getDate("DateSigned"));
					fmdto.setDateDelivered(rs.getDate("DateDelivered"));
					fmdto.setAckInfo(rs.getString("AckInfo"));
					fmdto.setTransitionInfo(rs.getString("TransitionInfo"));
					fmdto.setCustomerNumber(rs.getString("customerNumber"));
					resList.add(fmdto);
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
	
	public boolean insertFreightState(FreightStateDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
				" FreightStates(FreightManifestID,State,Date,StateRemarks)" +
				" values(?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getFreightManifestID());
			pstmt.setString(2, dto.getState());
			pstmt.setTimestamp(3, dto.getDate());
			pstmt.setString(4, dto.getStateRemarks());
			
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
