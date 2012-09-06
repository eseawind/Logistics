package Logistics.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Logistics.DTO.PermissionDTO;
import Logistics.DTO.RoleDTO;
import Logistics.DTO.UserDTO;
import Logistics.Servlet.*;
import com.opensymphony.xwork2.ActionContext;

public class Permission {
	
	public static final String[] actions={UserAction.class.getName(),BarcodeAction.class.getName()
		,CarAction.class.getName(),CarTypeAction.class.getName(),
		CityAction.class.getName(),CostCenterAction.class.getName(),
		CustomerAction.class.getName(),EmployeeProfileAction.class.getName(),
		EmployeeSalaryAction.class.getName(),FinancialLogAction.class.getName(),
		FreightContractorAction.class.getName(),FreightCostAction.class.getName(),
		FreightIncomeAction.class.getName(),FreightManifestAction.class.getName(),
		FreightRouteAction.class.getName(),FreightStateAction.class.getName(),
		InventoryManifestAction.class.getName(),ItemAction.class.getName(),
		MessageAction.class.getName(),PaymentCollectionAction.class.getName(),
		SellCenterAction.class.getName(),ShipmentCostAction.class.getName(),
		ShipmentManifestAction.class.getName(),SpecialStockIncomeAction.class.getName(),
		StockFinanceAction.class.getName(),StockinFinanceAction.class.getName(),
		StockinManifestAction.class.getName(),StockItemAction.class.getName(),
		StockoutFinanceAction.class.getName(),StockoutManifestAction.class.getName(),
		StockTransferFinanceAction.class.getName(),StockTransferManifestAction.class.getName(),
		WarehouseAction.class.getName(),RoleAction.class.getName(),SystemInfoAction.class.getName()};
	
	public static enum MethodCode{
		query(0x00000001),insert(0x00000002),
		update(0x00000004),delete(0x00000008),
		download(0x00000010),importFile(0x00000020),
		archive(0x00000040),unarchive(0x00000080),
		updateState(0x00000100),approve(0x00000200),
		audit(0x00000400),account(0x00000800),
		export(0x00001000),print(0x00002000),
		updatePassword(0x00004000);
		private int code=0;
		private MethodCode(int _code){
			this.code=_code;
		}
		public int getCode() {
			return code;
		}
		
		
		
	}
	public static int allPermission(){
		int p=0;
		for(MethodCode m:MethodCode.values()){
			p|=m.getCode();
		}
		return p;
	}
	private Permission(){
		
	}
	
	public static ArrayList<PermissionDTO> superUser(){
		ArrayList<PermissionDTO> res=new ArrayList<PermissionDTO>();
		for(String actName:actions){
			PermissionDTO permission=new PermissionDTO();
			permission.setRoleID(0);
			permission.setRoleName("SuperUser");
			permission.setActionName(actName);
			permission.setPermission(allPermission());
			res.add(permission);
		}
		return res;
	}
	public static boolean checkInUser(UserDTO user,ArrayList<PermissionDTO> permissions){
		ActionContext actionContext = ActionContext.getContext();
		Map session=actionContext.getSession();
		//Map application=actionContext.getApplication();
//		ArrayList currentUsers=null;
//		if(!application.containsKey("CurrentUsers"))
//			application.put("CurrentUsers", new ArrayList<String>());
//		currentUsers=(ArrayList)application.get("CurrentUsers");
//		currentUsers.add(userID+session.hashCode());
//		CommonTools.print(""+currentUsers.toString());
		session.put("CurrentUser", user.getUID());
		if(permissions!=null && permissions.size()!=0)
			session.put("RoleName",permissions.get(0).getRoleName() );
		else
			session.put("RoleName", "");
		Map<String,Integer> perms=new HashMap();
		for(PermissionDTO iter:permissions){
			perms.put(iter.getActionName(), iter.getPermission());
		}
		session.put("Permissions", perms);
		return true;
	}
	
	public static String getRoleName(){
		ActionContext actionContext = ActionContext.getContext();
		Map session=actionContext.getSession();
		//Map application=actionContext.getApplication();
		Tools.print("getRoleName");
		return (String)session.get("RoleName");
	}
	
	public static String getCurrentUser(){
		ActionContext actionContext = ActionContext.getContext();
		Map session=actionContext.getSession();
		//Map application=actionContext.getApplication();
		Tools.print("getCurrentUser");
		return (String)session.get("CurrentUser");
	}
	public static boolean isUserIn(){
		ActionContext actionContext = ActionContext.getContext();
		Map session=actionContext.getSession();
		return session.get("CurrentUser")!=null;
	}
	public static boolean checkPermission(Object action,Permission.MethodCode m){
		String name="";
		if(action instanceof String){
			name=(String)action;
		}
		else{
			name=action.getClass().getName();
		}	
		
		Map<String,Object> session =ActionContext.getContext().getSession();
		if(!session.containsKey("Permissions")){
			return false;
		}
		Map<String,Integer> permissions=(Map<String,Integer>)session.get("Permissions");
		if(!permissions.containsKey(name)){
			return false;
		}
		int code=permissions.get(name);
		if((code&m.getCode())!=0 ){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	
	public static void checkOutUser(){
		ActionContext actionContext = ActionContext.getContext();
		Map session=actionContext.getSession();
//		Map application=actionContext.getApplication();
//		String currentUser=(String)session.get("CurrentUser");
		session.remove("CurrentUser");
		session.remove("Permissions");
		session.remove("RoleName");
//		ArrayList<String> currentUsers=(ArrayList<String>)application.get("CurrentUsers");
//		if(currentUsers!=null)
//		{
//			currentUsers.remove(currentUser+session.hashCode());
//		}	
		
	}
	
}
