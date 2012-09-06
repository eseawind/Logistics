import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import Logistics.DAO.*;
import Logistics.DTO.*;
import Logistics.Servlet.*;
import Logistics.Common.*;
import Logistics.DTO.MessageDTO;
import java.io.StringReader;
import java.lang.reflect.Field;
public class Main {
	
	public static void insertTest() throws Exception{
		MysqlTools mysqlTools=new MysqlTools();
		FreightManifestDAO fmdao=new FreightManifestDAO();
		fmdao.initiate(mysqlTools);
		long t1=System.currentTimeMillis();
		fmdao.insert();
		long t2=System.currentTimeMillis();
		System.out.println(t2-t1);
		mysqlTools.commit();
	}
	
	static MysqlTools mysqlTools;
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		insertTest();
		System.out.println("success");
	}
		
	

}
