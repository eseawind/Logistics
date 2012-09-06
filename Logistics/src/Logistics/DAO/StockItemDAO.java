package Logistics.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import Logistics.Common.Tools;
import Logistics.DTO.CargoDTO;
import Logistics.DTO.CityDTO;
import Logistics.DTO.ConsigneeDTO;
import Logistics.DTO.CustomerDTO;
import Logistics.DTO.FreightCostDTO;
import Logistics.DTO.FreightIncomeDTO;
import Logistics.DTO.FreightManifestDTO;
import Logistics.DTO.FreightStateDTO;
import Logistics.DTO.ItemDTO;
import Logistics.DTO.ItemHistoryDTO;
import Logistics.DTO.StockItemDTO;
import Logistics.DTO.WarehouseDTO;

public class StockItemDAO extends BaseDAO {
	public static final int JOIN_WAREHOUSE=0x1;
	public static final int JOIN_CUSTOMER=0x2;
	public static final int JOIN_ITEM=0x4;
	public boolean delete(String wid,Integer iid,Date sDate,Integer cid) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(wid==null || iid==null || sDate==null)
			return true;
		String sql="delete from StockItems where WarehouseID=? " +
				"and ItemID=? and StockinDate=? and CustomerID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, wid);
			pstmt.setInt(2, iid);
			pstmt.setDate(3, sDate);
			pstmt.setInt(4, cid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean deleteZero() throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		String sql="delete from StockItems where Amount=0";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public StockItemDTO getByID(String wid,Integer iid,Date sDate,Integer cid)throws Exception{
		//申明对象
		StockItemDTO resdto=null;
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断传入参数有效性
		if(wid==null || iid==null || sDate==null || cid==null)
			return null;
		String sql="select * from StockItems where WarehouseID=? and ItemID=?" +
				" and StockinDate=? and CustomerID=?";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, wid);
			pstmt.setInt(2, iid);
			pstmt.setDate(3, sDate);
			pstmt.setInt(4, cid);
			rs=pstmt.executeQuery();
			if(rs!=null && rs.next())
			{
				resdto= new StockItemDTO();
				resdto.setAmount(rs.getInt("Amount"));
				resdto.setStockinDate(rs.getDate("StockinDate"));
				resdto.setItemID(rs.getInt("ItemID"));
				resdto.setWarehouseID(rs.getString("WarehouseID"));
				resdto.setCustomerID(rs.getInt("CustomerID"));
				resdto.setLastAccountDate(rs.getDate("LastAccountDate"));
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
	
	public ArrayList<StockItemDTO> queryItems(String wid,Integer iid,Integer cid,Date startDay,Date endDay,int join_selection)throws Exception{
		//申明对象
		ArrayList<StockItemDTO> resList=new ArrayList<StockItemDTO>();
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		String sql="select Stockitems.* ";
			if((join_selection & StockItemDAO.JOIN_ITEM)!=0)
				sql+=" ,Items.* ";
			if((join_selection & StockItemDAO.JOIN_WAREHOUSE)!=0)
				sql+=" ,warehouses.Name ";
			if((join_selection & StockItemDAO.JOIN_CUSTOMER)!=0)
				sql+=" ,customers.name ";
			sql+=" from StockItems ";
			if((join_selection & StockItemDAO.JOIN_ITEM)!=0)
				sql+=" inner join Items on Stockitems.ItemID=Items.ItemID";
			if((join_selection & StockItemDAO.JOIN_WAREHOUSE)!=0)
				sql+=" inner join warehouses on warehouses.warehouseID=stockitems.warehouseID";
			if((join_selection & StockItemDAO.JOIN_CUSTOMER)!=0)
				sql+=" inner join customers on customers.customerid=stockitems.customerid";
			
			sql+=" where ("+Tools.isVoid(wid)+" or Stockitems.WarehouseID=? )" +
			" and ("+Tools.isVoid(iid)+" or Stockitems.ItemID=?)" +
			" and ("+Tools.isVoid(cid)+"  or Stockitems.CustomerID=?)" +
			" and ( "+Tools.isVoid(startDay)+" or StockItems.StockinDate >=?)" +
			" and ( "+Tools.isVoid(endDay)+" or StockItems.StockinDate <= ? )";
				
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, wid);
			pstmt.setString(2,Tools.toString( iid));
			pstmt.setString(3,Tools.toString(cid));
			pstmt.setDate(4, startDay);
			pstmt.setDate(5, endDay);
			rs=pstmt.executeQuery();
			if(rs!=null )
			{
				while(rs.next()){
					StockItemDTO resdto= new StockItemDTO();
					resdto.setAmount(rs.getInt("StockItems.Amount"));
					resdto.setStockinDate(rs.getDate("StockItems.StockinDate"));
					resdto.setItemID(rs.getInt("StockItems.ItemID"));
					resdto.setWarehouseID(rs.getString("StockItems.WarehouseID"));
					resdto.setCustomerID(rs.getInt("StockItems.CustomerID"));
					if((join_selection & StockItemDAO.JOIN_ITEM)!=0){
						resdto.item=new ItemDTO();
						resdto.item.setName(rs.getString("Items.name"));
						resdto.item.setNumber(rs.getString("Items.number"));
						resdto.item.setBatch(rs.getString("Items.batch"));
						resdto.item.setUnit(rs.getString("Items.unit"));
						resdto.item.setUnitVolume(rs.getDouble("Items.unitVolume"));
						resdto.item.setUnitWeight(rs.getDouble("Items.unitWeight"));
					}
					if((join_selection & StockItemDAO.JOIN_WAREHOUSE)!=0){
						resdto.warehouse=new WarehouseDTO();
						resdto.warehouse.setName(rs.getString("Warehouses.name"));
					}
					if((join_selection & StockItemDAO.JOIN_CUSTOMER)!=0){
						resdto.customer=new CustomerDTO();
						resdto.customer.setName(rs.getString("Customers.name"));
					}
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
	
	public ArrayList<StockItemDTO> queryQualifiedItems(WarehouseDTO warehouse,ItemDTO item,CustomerDTO cust
			,int start,int limit,boolean isGroupBy,Date startDay,Date endDay)throws Exception{
		//申明对象
		ArrayList<StockItemDTO> resList=new ArrayList<StockItemDTO>();
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(warehouse==null)
			warehouse=new WarehouseDTO();
		if(cust==null)
			cust=new CustomerDTO();
		if(item==null)
			item=new ItemDTO();
		String sql="select Stockitems.*,warehouses.name,customers.name,items.*";
		if(isGroupBy){
				sql+=", sum(stockitems.amount) sumAmount";
		}
			sql+=" from StockItems ";
			sql+=" inner join Items on Stockitems.ItemID=Items.ItemID";
			sql+=" inner join warehouses on warehouses.warehouseID=stockitems.warehouseID";
			sql+=" inner join customers on customers.customerid=stockitems.customerid";
			sql+=" where ("+!Tools.isVoid(warehouse.getWarehouseID())+" and warehouses.WarehouseID=? " +
						" or "+Tools.isVoid(warehouse.getWarehouseID()) +
						" and ( "+Tools.isVoid(warehouse.getName())+" or warehouses.name like ?) )" +
				" and ("+!Tools.isVoid(item.getItemID())+" and items.ItemID=? " +
						"or "+Tools.isVoid(item.getItemID())+
						" and ( "+Tools.isVoid(item.getName())+" or items.name like ?) " +
						" and ( "+Tools.isVoid(item.getBatch())+" or items.batch = ? )" +
						" and ( "+Tools.isVoid(item.getNumber())+" or items.number=? ) )" +
				" and ("+!Tools.isVoid(cust.getCustomerID())+" and customers.CustomerID=?" +
						" or  "+Tools.isVoid(cust.getCustomerID())
						+" and  ( "+Tools.isVoid(cust.getName())+" or customers.name like ?) )" +
				" and ( "+Tools.isVoid(startDay)+" or LastAccountDate >= ? )" +
				" and ( "+Tools.isVoid(endDay)+" or LastAccountDate <= ?)";
			if(isGroupBy){
				sql+=" group by stockitems.warehouseid,stockitems.customerid,stockitems.itemid ";
			}
			sql+=" order by StockinDate desc ";
			sql+=" limit ?,?";
				
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, warehouse.getWarehouseID());
			pstmt.setString(2, "%"+warehouse.getName()+"%");
			pstmt.setString(3,Tools.toString(item.getItemID()));
			pstmt.setString(4, "%"+item.getName()+"%");
			pstmt.setString(5, item.getBatch());
			pstmt.setString(6, item.getNumber());
			pstmt.setString(7, Tools.toString(cust.getCustomerID()));
			pstmt.setString(8, "%"+cust.getName()+"%");
			pstmt.setDate(9, startDay);
			pstmt.setDate(10, endDay);
			pstmt.setInt(11, start);
			pstmt.setInt(12, limit);
			rs=pstmt.executeQuery();
			if(rs!=null )
			{
				while(rs.next()){
					StockItemDTO resdto= new StockItemDTO();
					resdto.setAmount(rs.getInt("sumAmount"));
					resdto.setStockinDate(rs.getDate("StockItems.StockinDate"));
					resdto.setItemID(rs.getInt("StockItems.ItemID"));
					resdto.setWarehouseID(rs.getString("StockItems.WarehouseID"));
					resdto.setCustomerID(rs.getInt("StockItems.CustomerID"));
					resdto.setLastAccountDate(rs.getDate("LastAccountDate"));
					resdto.item=new ItemDTO();
					resdto.item.setName(rs.getString("Items.name"));
					resdto.item.setNumber(rs.getString("Items.number"));
					resdto.item.setBatch(rs.getString("Items.batch"));
					resdto.item.setUnit(rs.getString("Items.unit"));
					resdto.item.setUnitVolume(rs.getDouble("Items.unitVolume"));
					resdto.item.setUnitWeight(rs.getDouble("Items.unitWeight"));
					resdto.warehouse=new WarehouseDTO();
					resdto.warehouse.setName(rs.getString("Warehouses.name"));
					resdto.customer=new CustomerDTO();
					resdto.customer.setName(rs.getString("Customers.name"));
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
	public int queryQualifiedAmount(WarehouseDTO warehouse,ItemDTO item,CustomerDTO cust
			,boolean isGroupBy,Date startDay,Date endDay)throws Exception{
		//申明对象
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(warehouse==null)
			warehouse=new WarehouseDTO();
		if(cust==null)
			cust=new CustomerDTO();
		if(item==null)
			item=new ItemDTO();
		int amount=0;
		String sql="select count(*) Amount";
		sql+=" from StockItems ";
		sql+=" inner join Items on Stockitems.ItemID=Items.ItemID";
		sql+=" inner join warehouses on warehouses.warehouseID=stockitems.warehouseID";
		sql+=" inner join customers on customers.customerid=stockitems.customerid";
		sql+=" where ("+!Tools.isVoid(warehouse.getWarehouseID())+" and warehouses.WarehouseID=? " +
		" or "+Tools.isVoid(warehouse.getWarehouseID()) +
		" and ( "+Tools.isVoid(warehouse.getName())+" or warehouses.name like ?) )" +
			" and ("+!Tools.isVoid(item.getItemID())+" and items.ItemID=? " +
			"or "+Tools.isVoid(item.getItemID())+
			" and ( "+Tools.isVoid(item.getName())+" or items.name like ?) " +
			" and ( "+Tools.isVoid(item.getBatch())+" or items.batch = ? )" +
			" and ( "+Tools.isVoid(item.getNumber())+" or items.number=? ) )" +
			" and ("+!Tools.isVoid(cust.getCustomerID())+" and customers.CustomerID=?" +
			" or  "+Tools.isVoid(cust.getCustomerID())
			+" and  ( "+Tools.isVoid(cust.getName())+" or customers.name like ?) )" +
			" and ( "+Tools.isVoid(startDay)+" or LastAccountDate >= ? )" +
			" and ( "+Tools.isVoid(endDay)+" or LastAccountDate <= ?)";
		if(isGroupBy){
			sql+=" group by stockitems.warehouseid,stockitems.customerid,stockitems.itemid ";
		}
		sql+=" order by StockinDate desc ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, warehouse.getWarehouseID());
			pstmt.setString(2, "%"+warehouse.getName()+"%");
			pstmt.setString(3,Tools.toString(item.getItemID()));
			pstmt.setString(4, "%"+item.getName()+"%");
			pstmt.setString(5, item.getBatch());
			pstmt.setString(6, item.getNumber());
			pstmt.setString(7, Tools.toString(cust.getCustomerID()));
			pstmt.setString(8, "%"+cust.getName()+"%");
			pstmt.setDate(9, startDay);
			pstmt.setDate(10, endDay);
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
	
	public ArrayList<StockItemDTO> queryInventoryItems(String wid,Date startDay,Date endDay,boolean ifAll)throws Exception{
		//申明对象
		ArrayList<StockItemDTO> resList=new ArrayList<StockItemDTO>();
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		String sql="select Stockitems.*,items.*, sum(stockitems.amount) sumAmount";
			sql+=" from StockItems ";
			sql+=" inner join Items using (itemID)";
			sql+=" where Stockitems.warehouseID=? ";
			if(!ifAll)
			sql+=" and stockitems.itemID in  ( select itemid from stockinitems where stockinmanifestid in " +
					" (select stockinmanifestid from stockinmanifests " +
					" where approvalState='已批准' and " +
					"( datestockin between ? and ?)) " +
					" union select itemid from stockoutitems where stockoutmanifestid in" +
					" (select stockoutmanifestid from stockoutmanifests " +
					" where approvalState='已批准' and " +
					"( datestockout between ? and ?)))";
			sql+=" group by stockitems.itemid ";
				
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1,wid);
			if(!ifAll){
				
				pstmt.setDate(2,startDay);
				pstmt.setDate(3,endDay);
				pstmt.setDate(4, startDay);
				pstmt.setDate(5, endDay);
			}
			rs=pstmt.executeQuery();
			if(rs!=null )
			{
				while(rs.next()){
					StockItemDTO resdto= new StockItemDTO();
					resdto.setAmount(rs.getInt("sumAmount"));
					resdto.setStockinDate(rs.getDate("StockItems.StockinDate"));
					resdto.setItemID(rs.getInt("StockItems.ItemID"));
					resdto.setWarehouseID(rs.getString("StockItems.WarehouseID"));
					resdto.setCustomerID(rs.getInt("StockItems.CustomerID"));
					resdto.item=new ItemDTO();
					resdto.item.setName(rs.getString("Items.name"));
					resdto.item.setNumber(rs.getString("Items.number"));
					resdto.item.setBatch(rs.getString("Items.batch"));
					resdto.item.setUnit(rs.getString("Items.unit"));
					resdto.item.setUnitVolume(rs.getDouble("Items.unitVolume"));
					resdto.item.setUnitWeight(rs.getDouble("Items.unitWeight"));
					
					
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
	
	public boolean insert(StockItemDTO dto) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dto==null)
			return false;
		String sql="insert into " +
		" StockItems(" +
		"WarehouseID," +
		"ItemID," +
		"StockinDate," +
		"CustomerID," +
		"Amount," +
		"LastAccountDate)" +
		" values(?,?,?,?,?,?);";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, dto.getWarehouseID());
			pstmt.setInt(2, dto.getItemID());
			pstmt.setDate(3, dto.getStockinDate());
			pstmt.setInt(4, dto.getCustomerID());
			pstmt.setInt(5, dto.getAmount());
			pstmt.setDate(6, dto.getStockinDate());
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
	public boolean insert(ArrayList<StockItemDTO> dtos) throws Exception {
		//判断是否已经初始化过
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//判断要存储的DTO是否为空
		if(dtos==null)
			return false;
		String sql="insert into " +
		" StockItems(" +
		"WarehouseID," +
		"ItemID," +
		"StockinDate," +
		"CustomerID," +
		"Amount," +
		"LastAccountDate)" +
		" values(?,?,?,?,?,?)";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			for(StockItemDTO dto:dtos){
				pstmt.setString(1, dto.getWarehouseID());
				pstmt.setInt(2, dto.getItemID());
				pstmt.setDate(3, dto.getStockinDate());
				pstmt.setInt(4, dto.getCustomerID());
				pstmt.setInt(5, dto.getAmount());
				pstmt.setDate(6, dto.getStockinDate());
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
	
	
	
	public boolean update(StockItemDTO dto) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(dto==null || dto.getItemID()==null ||
				dto.getStockinDate()==null || dto.getWarehouseID()==null)
			return false;
		String sql="update StockItems set Amount=? " +
				" where warehouseID=? " +
				" and ItemID=? " +
				" and StockinDate=?" +
				" and CustomerID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, dto.getAmount());
			pstmt.setString(2, dto.getWarehouseID());
			pstmt.setInt(3, dto.getItemID());
			pstmt.setDate(4, dto.getStockinDate());
			pstmt.setInt(5, dto.getCustomerID());
			
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
	
	public boolean updateAmount(String wid,Integer iid,Date sd,Integer cid,int incAmount) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(wid==null || iid==null|| sd==null)
			return false;
		String sql="update StockItems set Amount=Amount+? " +
				" where warehouseID=? " +
				" and ItemID=? " +
				" and StockinDate=?" +
				" and CustomerID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setInt(1, incAmount);
			pstmt.setString(2, wid);
			pstmt.setInt(3, iid);
			pstmt.setDate(4, sd);
			pstmt.setInt(5,cid);
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
	public boolean updateAccountDate(String wid,Integer iid,Date sd,Integer cid,Date lastAccountDate) throws Exception {
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		//传入参数的合法性
		if(wid==null || iid==null|| sd==null || cid==null)
			return false;
		String sql="update StockItems set LastAccountDate=? " +
				" where warehouseID=? " +
				" and ItemID=? " +
				" and StockinDate=?" +
				" and CustomerID=?";
		Tools.print(sql);
		try {
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setDate(1, lastAccountDate);
			pstmt.setString(2, wid);
			pstmt.setInt(3, iid);
			pstmt.setDate(4, sd);
			pstmt.setInt(5,cid);
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
	
	public ArrayList<ItemHistoryDTO> queryItemHistory(String wid,Integer cid,int iid,Date startDay,Date endDay,int start,int limit)throws Exception{
		//申明对象
		ArrayList<ItemHistoryDTO> resList=new ArrayList<ItemHistoryDTO>();
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(Tools.isVoid(wid) || Tools.isVoid(iid)){
			return null;
		}
		String sql="select stockinManifestid id, '入库单' type, datestockin date " +
				" from stockinmanifests sim where  ("+Tools.isVoid(cid)+" or customerid=?) and warehouseid=?  " +
						" and ("+Tools.isVoid(startDay)+" or datestockin >=?) and ("+Tools.isVoid(endDay)+" or datestockin <= ? )and " +
				" exists (select * from stockinitems where stockinmanifestid=sim.stockinmanifestid and itemid=?) " +
				" union select stockoutmanifestid id,'出库单' type,datestockout date from stockoutmanifests som " +
				" where ("+Tools.isVoid(cid)+" or customerid=?) and warehouseid=? and ("+Tools.isVoid(startDay)+" or datestockout >=?)" +
						" and ("+Tools.isVoid(endDay)+" or datestockout <=?) and " +
				" exists (select * from stockoutitems where stockoutmanifestid=som.stockoutmanifestid and itemid=? ) " +
				" union select stocktransfermanifestid id,'移库单' type,datetransfered date from stocktransfermanifests stm  " +
				" where (inwarehouseid =? or outwarehouseid =?) and ("+Tools.isVoid(startDay)+" or  datetransfered >= ?) " +
						" and ("+Tools.isVoid(endDay)+" or datetransfered <= ?)" +
				" and exists (select * from stocktransferitems where stocktransfermanifestid=stm.stocktransfermanifestid " +
				" and ("+Tools.isVoid(cid)+" or customerid=?) and itemid=?) ";
		sql+=" limit ?,? ";
				
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(cid));
			pstmt.setString(2, wid);
			pstmt.setDate(3, startDay);
			pstmt.setDate(4, endDay);
			pstmt.setInt(5, iid);
			pstmt.setString(6, Tools.toString(cid));
			pstmt.setString(7, wid);
			pstmt.setDate(8, startDay);
			pstmt.setDate(9, endDay);
			pstmt.setInt(10, iid);
			pstmt.setString(11, wid);
			pstmt.setString(12, wid);
			pstmt.setDate(13, startDay);
			pstmt.setDate(14, endDay);
			pstmt.setString(15, Tools.toString(cid));
			pstmt.setInt(16, iid);
			pstmt.setInt(17, start);
			pstmt.setInt(18,limit);
			
			rs=pstmt.executeQuery();
			if(rs!=null )
			{
				while(rs.next()){
					ItemHistoryDTO resdto= new ItemHistoryDTO();
					resdto.setDate(rs.getString("date"));
					resdto.setId(rs.getString("id"));
					resdto.setType(rs.getString("type"));
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
	public int queryItemHistoryAmount(String wid,Integer cid,int iid,Date startDay,Date endDay)throws Exception{
		//申明对象
		if(this.isInitiated==false)
		{
			throw new Exception("DAO没有初始化");
		}
		if(Tools.isVoid(wid)){
			return 0;
		}
		int amount=0;
		String sql="select count(*) Amount,'1' type " +
		" from stockinmanifests sim where  ("+Tools.isVoid(cid)+" or customerid=?) and warehouseid=?  " +
		" and ("+Tools.isVoid(startDay)+" or datestockin >=?) and ("+Tools.isVoid(endDay)+" or datestockin <= ? )and " +
		" exists (select * from stockinitems where stockinmanifestid=sim.stockinmanifestid and itemid=?) " +
		" union select count(*) Amount,'2' type from stockoutmanifests som " +
		" where ("+Tools.isVoid(cid)+" or customerid=?) and warehouseid=? and ("+Tools.isVoid(startDay)+" or datestockout >=?)" +
		" and ("+Tools.isVoid(endDay)+" or datestockout <=?) and " +
		" exists (select * from stockoutitems where stockoutmanifestid=som.stockoutmanifestid and itemid=? ) " +
		" union select count(*) Amount,'3' type from stocktransfermanifests stm  " +
		" where (inwarehouseid =? or outwarehouseid =?) and ("+Tools.isVoid(startDay)+" or  datetransfered >= ?) " +
		" and ("+Tools.isVoid(endDay)+" or datetransfered <= ?)" +
		" and exists (select * from stocktransferitems where stocktransfermanifestid=stm.stocktransfermanifestid " +
		" and ("+Tools.isVoid(cid)+" or customerid=?) and itemid=?) ";
		Tools.print(sql);
		try{
			pstmt=mysqlTools.getPreparedStatement(sql);
			pstmt.setString(1, Tools.toString(cid));
			pstmt.setString(2, wid);
			pstmt.setDate(3, startDay);
			pstmt.setDate(4, endDay);
			pstmt.setInt(5, iid);
			pstmt.setString(6, Tools.toString(cid));
			pstmt.setString(7, wid);
			pstmt.setDate(8, startDay);
			pstmt.setDate(9, endDay);
			pstmt.setInt(10, iid);
			pstmt.setString(11, wid);
			pstmt.setString(12, wid);
			pstmt.setDate(13, startDay);
			pstmt.setDate(14, endDay);
			pstmt.setString(15, Tools.toString(cid));
			pstmt.setInt(16, iid);
			rs=pstmt.executeQuery();
			if(rs!=null )
			{
				while(rs.next()){
					amount+=rs.getInt("Amount");
				}
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
}
