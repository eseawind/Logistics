package Logistics.Common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;
import jxl.*;
import jxl.read.biff.BiffException;
public class Excel2sql {
	public static void main(String[] args) throws Exception {
		String eFileDir="d:/share/initialData.xls";
		String tFileDir="d:/share/initialData.sql";
		Excel2sql.excel2sql(eFileDir, tFileDir);
		System.out.println("success");
	}
	
	private static String toInsertSql(String tableName,ArrayList<String> columnNames,ArrayList<String> values){
		if(columnNames==null || values==null|| columnNames.size()!=values.size() || columnNames.isEmpty()){
			return "";
		}
		String sql = "insert into ";
		sql+=tableName;
		sql+=" (";
		for(int i=0;i<columnNames.size();i++){
			sql+=i==0?"":",";
			sql+=columnNames.get(i);
		}
		sql+=") values (";
		for(int i=0;i<values.size();i++){
			sql+=i==0?"":",";
			sql+=Tools.isVoid(values.get(i))?"null":"'"+values.get(i)+"'";
		}
		sql+=" );";
		return sql;
	}
	public static void excel2sql(String eFileDir,String tFileDir) throws Exception{
		Workbook wb=Workbook.getWorkbook(new File(eFileDir));

		OutputStreamWriter fw=new OutputStreamWriter(new FileOutputStream(tFileDir),"utf-8");
		for(Sheet sheet:wb.getSheets()){
			String tableName=sheet.getName();
			Cell[] r1=sheet.getRow(0);
			ArrayList<String> columnNames=new ArrayList<String>();
			ArrayList<String> values=new ArrayList<String>();
			ArrayList<String> sqls=new ArrayList<String>();
			for(int i=0;i<r1.length;i++){
				String name=r1[i].getContents();
				if("end".equals(name) || Tools.isVoid(name)){
					break;
				}
				columnNames.add(name);
			}
			int count=columnNames.size();
			for(int i=1;i<sheet.getRows();i++){
				Cell[] r=sheet.getRow(i);
				if(r.length<count)
					continue;
				values.clear();
				for(int j=0;j<count && j<r.length;j++){
					String value=r[j].getContents();
					value=value.replaceAll("\n", "");
					values.add(value);
				}
				sqls.add(toInsertSql(tableName,columnNames,values));
			}
			fw.write("/*===================================================*/\n"); 
			fw.write("/*     Table:"+tableName+"*/\n");
			for(String sql:sqls){
				fw.write(sql);
				fw.write("\n");
			}
			
			fw.write("/*===================================================*/\n"); 
		}
		fw.close();
	}
}
