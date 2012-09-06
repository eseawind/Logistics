package Logistics.Common;

import Logistics.DAO.FinancialLogDAO;
import Logistics.DAO.MysqlTools;

public class FinancialLog {
	
	public static void log(String userID,String type,String content){
		
		MysqlTools mysqlTools=null;
		try {
			 mysqlTools=new MysqlTools();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
		FinancialLogDAO fdao=new FinancialLogDAO();
		fdao.initiate(mysqlTools);
		try {
			if(!fdao.insert(userID, type, content)){
				mysqlTools.rollback();
			}
			mysqlTools.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mysqlTools.rollback();
		}
		finally{
			if(mysqlTools!=null)
				mysqlTools.close();
			mysqlTools=null;
		}
	}
}
