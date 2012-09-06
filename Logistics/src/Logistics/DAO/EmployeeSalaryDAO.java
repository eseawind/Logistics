package Logistics.DAO;
import java.sql.SQLException;

import java.sql.Date;
import java.util.ArrayList;

import Logistics.Common.Tools;
import Logistics.DTO.*;

public class EmployeeSalaryDAO extends BaseDAO{
	
	public ArrayList<EmployeeSalaryDTO> queryByCondition(Integer eid,String employeeName
			,Date startDate,Date endDate,int start, int limit) throws Exception
	{
		Tools.print("queryByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		ArrayList<EmployeeSalaryDTO> eslist=new ArrayList<EmployeeSalaryDTO>();
		EmployeeSalaryDTO esdto=null;
		EmployeeProfileDTO epdto=null;
		String sql=" select * from EmployeeSalary,EmployeeProfiles where EmployeeSalary.EmployeeID=EmployeeProfiles.EID" +
				" and EmployeeID like ? and Name like ? and PayDay >= ? and PayDay <= ? limit ?,?";
		
		Tools.print(sql);
		
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(eid!=null)
				pstmt.setInt(1, eid);
			else
				pstmt.setString(1, "%");
			if(employeeName!=null && employeeName.length()!=0)
				pstmt.setString(2, employeeName);
			else
				pstmt.setString(2, "%");
			if(startDate!=null)
				pstmt.setDate(3, startDate);
			else
				pstmt.setDate(3, Date.valueOf("1000-01-01"));
			if(endDate!=null)
				pstmt.setDate(4, endDate);
			else
				pstmt.setDate(4,Date.valueOf("9999-12-31"));
			pstmt.setInt(5, start);
			pstmt.setInt(6, limit);
			rs=pstmt.executeQuery();
			if(rs!=null)
			{
				while(rs.next()){
					esdto= new EmployeeSalaryDTO();
					epdto= new EmployeeProfileDTO();
					esdto.setAnnualBonus(rs.getDouble("AnnualBonus"));
					esdto.setBasicBonus(rs.getDouble("BasicBonus"));
					esdto.setCommunicationAllowance(rs.getDouble("CommunicationAllowance"));
					esdto.setDeductionMoney(rs.getDouble("DeductionMoney"));
					esdto.setEmployeeID(rs.getInt("EmployeeID"));
					esdto.setESID(rs.getInt("ESID"));
					esdto.setExtraMoney(rs.getDouble("ExtraMoney"));
					esdto.setMealAllowance(rs.getDouble("MealAllowance"));
					esdto.setOtherPayment(rs.getDouble("OtherPayment"));
					esdto.setOvertimeSalary(rs.getDouble("OvertimeSalary"));
					esdto.setPayDay(rs.getDate("PayDay"));
					esdto.setPerformanceSalary(rs.getDouble("PerformanceSalary"));
					esdto.setPostAllowance(rs.getDouble("PostAllowance"));
					esdto.setPostSalary(rs.getDouble("PostSalary"));
					esdto.setTransportAllowance(rs.getDouble("TransportAllowance"));
					esdto.setRemarks(rs.getString("EmployeeSalary.Remarks"));
					
					epdto.setEID(rs.getInt("EID"));
		            epdto.setCellPhone(rs.getString("CellPhone"));
		            epdto.setDepartment(rs.getString("Department"));
		            epdto.setEmail(rs.getString("Email"));
		            epdto.setIDCard(rs.getString("IDCard"));
		            epdto.setRemarks(rs.getString("EmployeeProfiles.Remarks"));
		            epdto.setName(rs.getString("Name"));
		            epdto.setPosition(rs.getString("Position"));
		            
		            esdto.setEPDTO(epdto);
		            
		            eslist.add(esdto);
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return eslist;
	}
	
	public int queryAmountByCondition(Integer eid,String employeeName
			,Date startDate,Date endDate) throws Exception
	{
		Tools.print("queryAmountByCondition");
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		
		int userAmount=0;
		String sql=" select count(*) Amount from EmployeeSalary,EmployeeProfiles where EmployeeSalary.EmployeeID=EmployeeProfiles.EID" +
				" and EmployeeID like ? and Name like ? and PayDay >= ? and PayDay <= ?";
		
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			if(eid!=null)
				pstmt.setInt(1, eid);
			else
				pstmt.setString(1, "%");
			if(employeeName!=null && employeeName.length()!=0)
				pstmt.setString(2, employeeName);
			else
				pstmt.setString(2, "%");
			if(startDate!=null)
				pstmt.setDate(3, startDate);
			else
				pstmt.setDate(3, Date.valueOf("1000-01-01"));
			if(endDate!=null)
				pstmt.setDate(4, endDate);
			else
				pstmt.setDate(4,Date.valueOf("9999-12-31"));
			
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				userAmount=rs.getInt("Amount");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			close();
		}
		return userAmount;
	}
	
	public boolean delete(Integer esid) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(esid==null)
			return false;
		String sql="delete from EmployeeSalary where ESID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, esid);
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
	
	public boolean update(EmployeeSalaryDTO esdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(esdto==null)
			return false;
		String sql="update EmployeeSalary set EmployeeID=?,PostSalary=?," +
				"PerformanceSalary=?,OvertimeSalary=?,PostAllowance=?,BasicBonus=?,AnnualBonus=?," +
				"ExtraMoney=?,DeductionMoney=?,TransportAllowance=?,CommunicationAllowance=?," +
				"MealAllowance=?,OtherPayment=?,PayDay=?,Remarks=? where ESID=?";
		Tools.print(sql);
		Tools.print(""+esdto.getESID());
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, esdto.getEmployeeID());
			pstmt.setDouble(2,esdto.getPostSalary());
			pstmt.setDouble(3, esdto.getPerformanceSalary());
			pstmt.setDouble(4, esdto.getOvertimeSalary());
			pstmt.setDouble(5, esdto.getPostAllowance());
			pstmt.setDouble(6, esdto.getBasicBonus());
			pstmt.setDouble(7, esdto.getAnnualBonus());
			pstmt.setDouble(8, esdto.getExtraMoney());
			pstmt.setDouble(9, esdto.getDeductionMoney());
			pstmt.setDouble(10, esdto.getTransportAllowance());
			pstmt.setDouble(11, esdto.getCommunicationAllowance());
			pstmt.setDouble(12, esdto.getMealAllowance());
			pstmt.setDouble(13, esdto.getOtherPayment());
			pstmt.setDate(14, esdto.getPayDay());
			pstmt.setString(15, esdto.getRemarks());
			pstmt.setInt(16, esdto.getESID());
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
	
	public boolean insert(EmployeeSalaryDTO esdto) throws Exception{
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(esdto==null)
			return false;
		String sql="insert into " +
				" EmployeeSalary(ESID,EmployeeID,PostSalary,PerformanceSalary,OvertimeSalary," +
				"PostAllowance,BasicBonus,AnnualBonus,ExtraMoney,DeductionMoney,TransportAllowance," +
				"CommunicationAllowance,MealAllowance,OtherPayment,PayDay,Remarks) " +
				" values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, esdto.getEmployeeID());
			pstmt.setDouble(2,esdto.getPostSalary());
			pstmt.setDouble(3, esdto.getPerformanceSalary());
			pstmt.setDouble(4, esdto.getOvertimeSalary());
			pstmt.setDouble(5, esdto.getPostAllowance());
			pstmt.setDouble(6, esdto.getBasicBonus());
			pstmt.setDouble(7, esdto.getAnnualBonus());
			pstmt.setDouble(8, esdto.getExtraMoney());
			pstmt.setDouble(9, esdto.getDeductionMoney());
			pstmt.setDouble(10, esdto.getTransportAllowance());
			pstmt.setDouble(11, esdto.getCommunicationAllowance());
			pstmt.setDouble(12, esdto.getMealAllowance());
			pstmt.setDouble(13, esdto.getOtherPayment());
			pstmt.setDate(14, esdto.getPayDay());
			pstmt.setString(15, esdto.getRemarks());
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
	
	public EmployeeSalaryDTO getDTOByID(Integer esid) throws Exception{
		//申明对象
		EmployeeSalaryDTO esdto=null;
		EmployeeProfileDTO epdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(esid==null)
			return null;
		String sql="select * from EmployeeSalary,EmployeeProfiles where ESID=? and EmployeeID=EID";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, esid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				esdto= new EmployeeSalaryDTO();
				epdto= new EmployeeProfileDTO();
				
				esdto.setAnnualBonus(rs.getDouble("AnnualBonus"));
				esdto.setBasicBonus(rs.getDouble("BasicBonus"));
				esdto.setCommunicationAllowance(rs.getDouble("CommunicationAllowance"));
				esdto.setDeductionMoney(rs.getDouble("DeductionMoney"));
				esdto.setEmployeeID(rs.getInt("EmployeeID"));
				esdto.setESID(rs.getInt("ESID"));
				esdto.setExtraMoney(rs.getDouble("ExtraMoney"));
				esdto.setMealAllowance(rs.getDouble("MealAllowance"));
				esdto.setOtherPayment(rs.getDouble("OtherPayment"));
				esdto.setOvertimeSalary(rs.getDouble("OvertimeSalary"));
				esdto.setPayDay(rs.getDate("PayDay"));
				esdto.setPerformanceSalary(rs.getDouble("PerformanceSalary"));
				esdto.setPostAllowance(rs.getDouble("PostAllowance"));
				esdto.setPostSalary(rs.getDouble("PostSalary"));
				esdto.setTransportAllowance(rs.getDouble("TransportAllowance"));
				esdto.setRemarks(rs.getString("EmployeeSalary.Remarks"));
				
				epdto.setEID(rs.getInt("EID"));
	            epdto.setCellPhone(rs.getString("CellPhone"));
	            epdto.setDepartment(rs.getString("Department"));
	            epdto.setEmail(rs.getString("Email"));
	            epdto.setIDCard(rs.getString("IDCard"));
	            epdto.setRemarks(rs.getString("EmployeeProfiles.Remarks"));
	            epdto.setName(rs.getString("Name"));
	            epdto.setPosition(rs.getString("Position"));
	            
	            esdto.setEPDTO(epdto);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
		return esdto;
	}
}
