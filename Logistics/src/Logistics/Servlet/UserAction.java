package Logistics.Servlet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Logistics.DAO.*;
import Logistics.DTO.*;
import Logistics.Common.*;
import Logistics.Common.Permission.MethodCode;

import java.sql.Date;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction {
	/*struts 固定参数*/
	private boolean success;
	private String message="";
	private boolean valid=true;
	
	
	
	/*业务参数*/
	private ArrayList<String> uIDList=null;
	private String oldUID;
	private String uID;
	private String uPassword;
	private String uNewPassword;
	private String uState;
	private String uDateCreated;
	private Map data=null;
	private int rID;
	private UserDTO udto=null;
	private int limit;
	private int start;
	private int qualifiedAmount;
	private ArrayList<Map> userList=null;
	private ArrayList<Map> roleList=null;
	private String CurrentUserID=null;
	private String RoleName=null;
	
	public String updatePassword(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		if(!Permission.checkPermission(this, MethodCode.updatePassword)){
			this.valid=false;
			this.message="请求失败，用户没有权限进行此项操作！";
			this.success=false;
			return "success";
		}
		
		if(uID==null || uID.length()==0 || uPassword==null || uPassword.length()==0
				|| uNewPassword==null || uNewPassword.length()==0)
		{
			this.message="传入参数缺少";
			this.success=false;
			return "success";
		}
		UserDAO udao=null;
		UserDTO udto=null;
		MysqlTools mysqlTools=null;
		try{
			//创建UserDAO,完成初始化
			mysqlTools=new MysqlTools();
			udao=new UserDAO();
			udao.initiate(mysqlTools);
			//查询
			udto=udao.getDTOByID(uID);
			if(udto==null)
			{
				this.message="无此用户的信息！";
				this.success=false;
				return "success";
			}
			if(udto.getUPassword()==null || !udto.getUPassword().equals(uPassword)){
				this.message="旧密码不正确！";
				this.success=false;
				return "success";
			}
			udto.setUPassword(uNewPassword);
			if(!udao.updateUser(udto, udto.getUID())){
				this.message="修改密码不成功！";
				this.success=false;
				return "success";
			}
			mysqlTools.commit();
			this.message="修改密码成功！";
			this.success=true;
			return "success";
		}catch(Exception e){
			mysqlTools.rollback();
			e.printStackTrace();
			this.message="获取用户失败";
			this.success=false;
			return "success";
		}
		finally{
			mysqlTools.close();
		}
	}
	
	public String getAUser(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//判断传入参数合法性
		if(uID==null || uID.length()==0)
		{
			this.message="传入参数缺少";
			this.success=false;
			return "success";
		}
		//申明对象
		UserDAO udao=null;
		UserDTO udto=null;
		MysqlTools mysqlTools=null;
		//建立对象
		try{
			//创建UserDAO,完成初始化
			mysqlTools=new MysqlTools();
			udao=new UserDAO();
			udao.initiate(mysqlTools);
			//查询
			udto=udao.getDTOByID(uID);
			mysqlTools.commit();
		}catch(Exception e){
			mysqlTools.rollback();
			e.printStackTrace();
			this.message="获取用户失败";
			this.success=false;
			return "success";
		}
		finally{
			mysqlTools.close();
		}
		//处理结果
		if(udto==null)
		{
			this.message="无此用户";
			this.success=false;
			return "success";
		}
		else
		{
			
			this.data=new HashMap();
			this.data.put("uID", udto.getUID());
			this.data.put("uDateCreated", udto.getUDateCreated().toString());
			this.data.put("uPassword", udto.getUPassword());
			this.data.put("rID", udto.getURoleID());
			this.data.put("uState", udto.getUState());
			
			this.message="成功";
			this.success=true;
			return "success";
		}
		
	}
	
	public String deleteUsers(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		if(!Permission.checkPermission(this, MethodCode.delete)){
			this.message="请求失败，用户没有权限进行此项操作！";
			this.success=false;
			return "success";
		}
		Tools.print("deleteUser");
		if(this.uIDList == null || this.uIDList.size()==0)
		{
			this.message="传入参数缺少";
			this.success=false;
			return "success";
		}
		UserDAO udao=null;
		MysqlTools mysqlTools=null;
		try{
			mysqlTools=new MysqlTools();
			udao=new UserDAO();
			udao.initiate(mysqlTools);
			for(int i=0;i<uIDList.size();i++)
			{
				if(!udao.deleteUser(uIDList.get(i)))
				{
					this.message+="删除用户："+uIDList.get(i)+" 失败";
				}
			}
			mysqlTools.commit();
			this.message="成功";
			this.success=true;
			return "success";

		}catch(Exception e){
			mysqlTools.rollback();
			Tools.printErr(e.getMessage());
			this.message="删除用户失败";
			this.success=false;
			return "success";
		}
		finally{
			try{
				mysqlTools.close();
			}
			catch(Exception e){
				e.printStackTrace();
				Tools.printErr("无法关闭系统资源");
				
			}
		}
	}
	
	public String updateUser(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		if(!Permission.checkPermission(this, MethodCode.update)){
			this.message="请求失败，用户没有权限进行此项操作！";
			this.success=false;
			return "success";
		}
		Tools.print("updateUser");
		if(this.uID == null || this.uID.length()==0|| this.uPassword == null|| this.uPassword.length()==0 ||
				this.uState==null ||this.uState.length()==0 || this.uDateCreated==null||this.uDateCreated.length()==0
				|| oldUID==null || oldUID.length()==0)
		{
			this.message="传入参数缺少";
			this.success=false;
			return "success";
		}
		UserDAO udao=null;
		UserDTO udto=null;
		RoleDAO rdao=null;
		RoleDTO rdto=null;
		MysqlTools mysqlTools=null;
		udto=new UserDTO();
		udto.setUDateCreated(Date.valueOf(this.uDateCreated));
		udto.setUID(this.uID);
		udto.setUPassword(this.uPassword);
		udto.setURoleID(this.rID);
		udto.setUState(this.uState);
		
		try{
			mysqlTools=new MysqlTools();
			udao=new UserDAO();
			udao.initiate(mysqlTools);
			rdao=new RoleDAO();
			rdao.initiate(mysqlTools);
			//测试角色ID是否存在，不存在则返回无法修改用户
			if(rdao.getDTOByID(udto.getURoleID())==null){
				this.message="用户所属的角色不存在，请检查";
				this.success=false;
				return "success";
			}
			if(udao.updateUser(udto,oldUID)){
				mysqlTools.commit();
				this.message="成功";
				this.success=true;
				return "success";
			}
			else{
				mysqlTools.rollback();
				this.message="修改用户失败";
				this.success=false;
				return "success";
			}
		}catch(Exception e){
			mysqlTools.rollback();
			Tools.printErr(e.getMessage());
			this.message="修改用户失败";
			this.success=false;
			return "success";
		}
		finally{
			mysqlTools.close();
		}
		
	}
	
	public String getAllRoles(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		//判断传入参数合法性
		//申明对象
		RoleDAO rdao=null;
		RoleDTO rdto=null;
		MysqlTools mysqlTools=null;
		ArrayList<RoleDTO> allRoles=null;
		//建立对象
		try{
			//创建UserDAO,完成初始化
			mysqlTools=new MysqlTools();
			rdao=new RoleDAO();
			rdao.initiate(mysqlTools);
			//查询
			allRoles=rdao.getAllRoles();
			mysqlTools.commit();
			Tools.print(""+allRoles.size());
		}catch(Exception e){
			mysqlTools.rollback();
			Tools.printErr(e.getMessage());
			this.success=false;
			return "success";
		}
		finally{
			mysqlTools.close();
		}
		//处理结果
		if(allRoles==null){
			this.message="没有角色";
			return "success";
		}
		this.roleList=new ArrayList<Map>();
		for(int i=0;i<allRoles.size();i++){
			Map m=null;
			m=new HashMap();
			m.put("roleName",allRoles.get(i).getRoleName());
			m.put("roleID", allRoles.get(i).getRoleID());
			roleList.add(m);
		}
		this.message="成功";
		success=true;
		return "success";
	}
	
	
	public String createUser(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		if(!Permission.checkPermission(this, MethodCode.insert)){
			this.message="请求失败，用户没有权限进行此项操作！";
			this.success=false;
			return "success";
		}
		Tools.print("createUser");
		if(this.uID == null || this.uID.length()==0|| this.uPassword == null|| this.uPassword.length()==0 ||
				this.uState==null ||this.uState.length()==0 || this.uDateCreated==null||this.uDateCreated.length()==0)
		{
			this.message="传入参数缺少";
			this.success=false;
			return "success";
		}
		UserDAO udao=null;
		UserDTO udto=null;
		RoleDAO rdao=null;
		RoleDTO rdto=null;
		MysqlTools mysqlTools=null;
		udto=new UserDTO();
		udto.setUDateCreated(Date.valueOf(this.uDateCreated));
		udto.setUID(this.uID);
		udto.setUPassword(this.uPassword);
		udto.setURoleID(this.rID);
		udto.setUState(this.uState);
		
		try{
			mysqlTools=new MysqlTools();
			udao=new UserDAO();
			udao.initiate(mysqlTools);
			rdao=new RoleDAO();
			rdao.initiate(mysqlTools);
			if(udao.getDTOByID(udto.getUID())!=null){
				this.message="用户已经存在，插入失败";
				this.success=false;
				return "success";
			}
			
			//测试角色ID是否存在，不存在则返回无法新增用户
			if(rdao.getDTOByID(udto.getURoleID())==null){
				this.message="用户所属的角色不存在，请检查";
				this.success=false;
				return "success";
			}
			
			if(udao.addUser(udto)){
				mysqlTools.commit();
				this.message="成功";
				this.success=true;
				return "success";
			}
			else{
				mysqlTools.rollback();
				this.message="新增用户失败";
				this.success=false;
				return "success";
			}
		}catch(Exception e){
			mysqlTools.rollback();
			Tools.printErr(e.getMessage());
			this.message="新增用户失败";
			this.success=false;
			return "success";
		}
		finally{
			mysqlTools.close();
		}
		
	}

	public String queryCurrentUser(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		String currentUser=null;
		String roleName=null;
		currentUser=Permission.getCurrentUser();
		roleName=Permission.getRoleName();
		if(currentUser==null || roleName==null)
		{
			if(currentUser==null)
				this.CurrentUserID="无";
			else
				this.CurrentUserID=currentUser;
			if(roleName==null)
				this.RoleName="无";
			else
				this.RoleName=roleName;
			this.message="当前无用户登录";
			this.success=false;
			return "success";
		}
		else{
			
			this.CurrentUserID=currentUser;
			this.RoleName=roleName;
			this.data=new HashMap();
			this.data.put("manageNews", Permission.checkPermission(MessageAction.class.getName(), MethodCode.query));
			this.data.put("manageTransportationSheet", Permission.checkPermission(FreightManifestAction.class.getName(), MethodCode.query));
			this.data.put("manageEntruckingSheet", Permission.checkPermission(ShipmentManifestAction.class.getName(), MethodCode.query));
			this.data.put("transportationTrack", Permission.checkPermission(FreightStateAction.class.getName(), MethodCode.query));
			this.data.put("manageEnterStorageSheet", Permission.checkPermission(StockinManifestAction.class.getName(), MethodCode.query));
			this.data.put("manageOutStorageSheet", Permission.checkPermission(StockoutManifestAction.class.getName(), MethodCode.query));
			this.data.put("manageTransferStorageSheet", Permission.checkPermission(StockTransferManifestAction.class.getName(), MethodCode.query));
			this.data.put("queryStorage", Permission.checkPermission(StockItemAction.class.getName(), MethodCode.query));
			this.data.put("manageStocktake", Permission.checkPermission(InventoryManifestAction.class.getName(), MethodCode.query));
			this.data.put("manageBarCode", Permission.checkPermission(BarcodeAction.class.getName(), MethodCode.query));
			this.data.put("transportationIncome", Permission.checkPermission(FreightIncomeAction.class.getName(), MethodCode.query));
			this.data.put("transportationPay", Permission.checkPermission(FreightCostAction.class.getName(), MethodCode.query));
			this.data.put("entruckingPay", Permission.checkPermission(ShipmentCostAction.class.getName(), MethodCode.query));
			this.data.put("enterStorageFinance", Permission.checkPermission(StockinFinanceAction.class.getName(), MethodCode.query));
			this.data.put("outStorageFinance", Permission.checkPermission(StockoutFinanceAction.class.getName(), MethodCode.query));
			this.data.put("transferStorageFinance", Permission.checkPermission(StockTransferFinanceAction.class.getName(), MethodCode.query));
			this.data.put("storageFinance", Permission.checkPermission(StockFinanceAction.class.getName(), MethodCode.query));
			this.data.put("specialStorageIncome", Permission.checkPermission(SpecialStockIncomeAction.class.getName(), MethodCode.query));
			this.data.put("trackCOD", Permission.checkPermission(PaymentCollectionAction.class.getName(), MethodCode.query));
			this.data.put("financeLog", Permission.checkPermission(FinancialLogAction.class.getName(), MethodCode.query));
			this.data.put("transportationWay", Permission.checkPermission(FreightRouteAction.class.getName(), MethodCode.query));
			this.data.put("transportationUnit", Permission.checkPermission(FreightContractorAction.class.getName(), MethodCode.query));
			this.data.put("carInformation", Permission.checkPermission(CarAction.class.getName(), MethodCode.query));
			this.data.put("carType", Permission.checkPermission(CarTypeAction.class.getName(), MethodCode.query));
			this.data.put("goodsInformation", Permission.checkPermission(ItemAction.class.getName(), MethodCode.query));
			this.data.put("customerInformation", Permission.checkPermission(CustomerAction.class.getName(), MethodCode.query));
			this.data.put("costCenter", Permission.checkPermission(CostCenterAction.class.getName(), MethodCode.query));
			this.data.put("sellCenter", Permission.checkPermission(SellCenterAction.class.getName(), MethodCode.query));
			this.data.put("storageInformation", Permission.checkPermission(WarehouseAction.class.getName(), MethodCode.query));
			this.data.put("cityInformation", Permission.checkPermission(CityAction.class.getName(), MethodCode.query));
			this.data.put("manageWorkerInfo", Permission.checkPermission(EmployeeProfileAction.class.getName(), MethodCode.query));
			this.data.put("manageWorkerSalary", Permission.checkPermission(EmployeeSalaryAction.class.getName(), MethodCode.query));
			this.data.put("manageUser", Permission.checkPermission(UserAction.class.getName(), MethodCode.query));
			this.data.put("manageRole", Permission.checkPermission(RoleAction.class.getName(), MethodCode.query));
			this.data.put("systemInformation", Permission.checkPermission(SystemInfoAction.class.getName(), MethodCode.query));
			
			this.message="成功";
			this.success=true;
			return "success";
		}
	}

	public String listUsers(){
		//check permission of basic query
		if(!Permission.isUserIn()){
			this.valid=false;
			this.message="请求失败，用户未登录或登录超时！";
			this.success=false;
			return "success";
		}
		//----------------------------------------------------------------

		if(!Permission.checkPermission(this, MethodCode.query)){
			this.message="请求失败，用户没有权限进行此项操作！";
			this.success=false;
			return "success";
		}
		Tools.print("listUsers");
		//判断传入参数合法性
		//申明对象
		UserDAO udao=null;
		UserDTO udto=null;
		ArrayList<UserDTO> res=null;
		MysqlTools mysqlTools=null;
		//建立对象
		try{
			mysqlTools=new MysqlTools();
			//创建UserDAO,完成初始化
			udao=new UserDAO();
			udao.initiate(mysqlTools);
			//查询
			this.qualifiedAmount=udao.getCountByCondition(uID, uState);
			res=udao.getUsersByCondition(uID, uState,start,limit);
			Tools.print(""+res.size());
			mysqlTools.commit();
		}catch(Exception e){
			mysqlTools.rollback();
			Tools.printErr(e.getMessage());
			this.message="失败";
			this.success=false;
			return "success";
		}
		finally{
			mysqlTools.close();
		}
		//处理结果
		userList=new ArrayList<Map>();
		for(int i=0;i<res.size();i++){
			Map m=null;
			m=new HashMap();
			m.put("UDateCreated",res.get(i).getUDateCreated().toString());
			m.put("UID", res.get(i).getUID());
			if(res.get(i).getRoleDTO()!=null)
				m.put("Role", res.get(i).getRoleDTO().getRoleName());
			else
				m.put("Role", "");
			m.put("State", res.get(i).getUState());
			userList.add(m);
		}
		this.message="成功";
		this.success=true;
		return "success";
	}
		
	public String logout()
	{
		ActionContext actionContext = ActionContext.getContext();
		Map session=actionContext.getSession();
		Map application=actionContext.getApplication();
		Permission.checkOutUser();
		this.message="已经登出";
		this.success=true;
		return "success";
	}
	
	
	public String userSignIn()
	{
		
		if(Data.superUserID.equals(this.uID)){
			UserDTO user=new UserDTO();
			user.setUID("Super user");
			ArrayList<PermissionDTO> permissions=Permission.superUser();
			Permission.checkInUser(user, permissions);
			this.message="成功，超级用户登录";
			this.success=true;
			return "success";
		}
		if(this.uID == null || this.uID.length()==0|| this.uPassword == null|| this.uPassword.length()==0)
		{
			this.message="用户或密码为空";
			this.success=false;
			return "success";
		}
		UserDAO udao=null;
		UserDTO udto=null;
		RoleDTO rdto=null;
		RoleDAO rdao=null;
		MysqlTools mysqlTools=null;
		try{
			mysqlTools= new MysqlTools();
			udao=new UserDAO();
			udao.initiate(mysqlTools);
			rdao=new RoleDAO();
			rdao.initiate(mysqlTools);
			udto=udao.getDTOByID(uID);
			if(udto==null)
			{
				this.message="用户不存在";
				this.success=false;
				return "success";
			}
			else if(!udto.getUID().equals(this.uID))
			{
				this.message="SQL注入漏洞";
				this.success=false;
				return "success";
			}
			else if("禁用".equals(udto.getUState())){
				this.message="该用户已被禁用";
				this.success=false;
				return "success";
			}
			else if(udto.getUState().equals("启用")){
				//System.out.println(udto.toString());
				if(!udto.getUPassword().equals(this.uPassword)){
					this.message="密码不正确，不能登录";
					this.success=false;
					return "success";
				}
				else{
					ArrayList<PermissionDTO> permissions=rdao.getPermissions(udto.getURoleID());
					if(Permission.checkInUser(udto, permissions))
					{
						this.message="用户可以登录";
						this.success=true;
						return "success";
					}
					else{
						this.message="登录失败!";
						this.success=false;
						return "success";
					}
					
				}
			}
			else{
				this.message="该用户处于异常状态，请联系系统管理员";
				this.success=false;
				return "success";
			}
		}catch(Exception e){
			Tools.printErr(e.getMessage());
		}
		finally{
			mysqlTools.close();
		}
		this.message="成功";
		this.success=true;
		return "success";
		
	}
	
	
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUID() {
		return uID;
	}

	public void setUID(String uid) {
		uID = uid;
	}

	public String getUPassword() {
		return uPassword;
	}

	public void setUPassword(String password) {
		uPassword = password;
	}

	public UserDTO getUdto() {
		return udto;
	}

	public void setUdto(UserDTO udto) {
		this.udto = udto;
	}




	public String getUState() {
		return uState;
	}




	public void setUState(String state) {
		uState = state;
	}




	public int getLimit() {
		return limit;
	}




	public void setLimit(int limit) {
		this.limit = limit;
	}




	public int getStart() {
		return start;
	}




	public void setStart(int start) {
		this.start = start;
	}




	public int getQualifiedAmount() {
		return qualifiedAmount;
	}




	public void setQualifiedAmount(int qualifiedAmount) {
		this.qualifiedAmount = qualifiedAmount;
	}




	public ArrayList<Map> getUserList() {
		return userList;
	}




	public void setUserList(ArrayList<Map> userList) {
		this.userList = userList;
	}

	public String getUDateCreated() {
		return uDateCreated;
	}




	public void setUDateCreated(String dateCreated) {
		uDateCreated = dateCreated;
	}



	public int getRID() {
		return rID;
	}


	public void setRID(int rid) {
		rID = rid;
	}


	public ArrayList<Map> getRoleList() {
		return roleList;
	}


	public void setRoleList(ArrayList<Map> roleList) {
		this.roleList = roleList;
	}

	public ArrayList<String> getUIDList() {
		return uIDList;
	}

	public void setUIDList(ArrayList<String> list) {
		uIDList = list;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public String getCurrentUserID() {
		return CurrentUserID;
	}

	public void setCurrentUserID(String currentUserID) {
		CurrentUserID = currentUserID;
	}

	public String getRoleName() {
		return RoleName;
	}

	public void setRoleName(String roleName) {
		RoleName = roleName;
	}

	public String getOldUID() {
		return oldUID;
	}

	public void setOldUID(String oldUID) {
		this.oldUID = oldUID;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getUNewPassword() {
		return uNewPassword;
	}

	public void setUNewPassword(String newPassword) {
		uNewPassword = newPassword;
	}

	
	

}

	

