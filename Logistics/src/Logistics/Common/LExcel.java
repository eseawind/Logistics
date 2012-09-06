package Logistics.Common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;
import java.io.*;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import Logistics.DTO.BarcodeDTO;
import Logistics.DTO.FreightIncomeDTO;
import Logistics.DTO.ItemDTO;
import jxl.*;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
public class LExcel {
	private WritableWorkbook wb=null;
	private OutputStream bos=null;
	private File file=null;
	private int sheetCount=0;
	public InputStream toInputStream() throws IOException{
		if(wb!=null){
			wb.write();
		}
		FileInputStream bis=new FileInputStream(file);
		return bis;
	}
	public LExcel() throws Exception{
		file=File.createTempFile("exportFile", null);
		bos=new FileOutputStream(file);
		wb=Workbook.createWorkbook(bos);
	}
	public void write() throws IOException{
		wb.write();
	}
	public void close(){
		
		try {
			wb.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(file.getAbsolutePath());
	}
	
	public static void main(String[] args) throws Exception {
		String eFileDir="workbook1.xls";
		String tFileDir="workbook1.sql";
		LExcel.excel2sql(eFileDir, tFileDir);
	}
	
	
	private Label[] getARow(WritableSheet ws,int rn,int clen){
		Label[] row=new Label[clen];
		for(int i=0;i<clen;i++){
			row[i]=new Label(i,rn,"");
		}
		return row;
	}
	private void writeToSheet(WritableSheet ws,Label[] lb) throws RowsExceededException, WriteException{
		if(lb==null)
			return ;
		for(int i=0;i<lb.length;i++){
			ws.addCell(lb[i]);
		}
	}
	public WritableSheet toSheet(ArrayList<DtoToExcel> incomes,String name) throws Exception{
		if(incomes==null || incomes.size()==0)
			return null;
		WritableSheet ws=wb.createSheet(name, sheetCount++);
		Label[] row=this.getARow(ws, 0, incomes.get(0).getColn());
		incomes.get(0).toCellName(row);
		writeToSheet(ws,row);
		for(int rn=0;rn<incomes.size();rn++){
			row=this.getARow(ws, rn+1, incomes.get(0).getColn());
			incomes.get(rn).toCellValue(row);
			writeToSheet(ws,row);
		}
		wb.write();
		return ws;
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
			sql+="'";
			sql+=values.get(i);
			sql+="'";
		}
		sql+=" );";
		return sql;
	}
	public static ArrayList<BarcodeDTO> excel2Barcodes(InputStream inputStream) throws Exception{
		Workbook wb=null;
		ArrayList<BarcodeDTO> result=new ArrayList<BarcodeDTO>();
		int count=13;
		try{
			wb=Workbook.getWorkbook(inputStream);
			Sheet sheet=wb.getSheet(0);
			for(int i=1;i<sheet.getRows();i++){
				Cell[] r= sheet.getRow(i);
				if(r.length < count){
					continue;
				}
				
				BarcodeDTO bdto=new BarcodeDTO();
				bdto.setBarcode(r[0].getContents());
				bdto.setItemID(r[1].getContents());
				bdto.setItemNumber(r[2].getContents());
				bdto.setItemName(r[3].getContents());
				bdto.setBatch(r[4].getContents());
				bdto.setAmount(r[5].getContents());
				bdto.setOperationType(r[6].getContents());
				bdto.setManifestID(r[7].getContents());
				bdto.setCustomerID(r[8].getContents());
				bdto.setCustomer(r[9].getContents());
				bdto.setWarehouseID(r[10].getContents());
				bdto.setWarehouse(r[11].getContents());
				bdto.setRemarks(r[12].getContents());
				result.add(bdto);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		finally{
			if(wb!=null)
				wb.close();
		}
		
		return result;
	}
	public static ArrayList<ItemDTO> excel2Items(InputStream inputStream) throws Exception{
		Workbook wb=null;
		ArrayList<ItemDTO> result=new ArrayList<ItemDTO>();
		int count=7;
		try{
			wb=Workbook.getWorkbook(inputStream);
			Sheet sheet=wb.getSheet(0);
			for(int i=1;i<sheet.getRows();i++){
				Cell[] r= sheet.getRow(i);
				if(r.length < count){
					continue;
				}
				
				ItemDTO idto=new ItemDTO();
				idto.setNumber(r[0].getContents());
				idto.setName(r[1].getContents());
				idto.setBatch(r[2].getContents());
				idto.setUnit(r[3].getContents());
				idto.setUnitVolume(Double.parseDouble(r[4].getContents()));
				idto.setUnitWeight(Double.parseDouble(r[5].getContents()));
				idto.setRemarks(r[6].getContents());
				result.add(idto);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		finally{
			if(wb!=null)
				wb.close();
		}
		
		return result;
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
				if("end".equals(name)){
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
					values.add(r[j].getContents());
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
		wb.close();
	}
	
}
