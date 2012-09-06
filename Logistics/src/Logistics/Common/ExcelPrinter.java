package Logistics.Common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import Logistics.DTO.CargoDTO;
import Logistics.DTO.FreightManifestDTO;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelPrinter {
	public static final int freightManifest=1;
	public static final int shipmentManifest=2;
	public static final int stockinManifest=3;
	public static final int stockoutManifest=4;
	private WritableWorkbook wwb=null;
	private File tempFile=null;
	public void writeToSheet(FreightManifestDTO fm,ArrayList<CargoDTO> cargos,String sheetName){
		
		
		
	}
	public InputStream toInputStream() throws IOException{
		if(wwb!=null){
			wwb.write();
		}
		FileInputStream bis=new FileInputStream(this.tempFile);
		return bis;
	}
	
	public void close(){
		
		try {
			wwb.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static public ExcelPrinter getExcelPrinter(int templateType) throws FileNotFoundException, IOException, BiffException{
		ExcelPrinter ep=new ExcelPrinter();
		File originFile=null;
		switch(templateType){
		case freightManifest:
			originFile=new File(Data.getRootPath()+Data.printFolder+"/"+Data.fmPrintFile);
			break;
		case shipmentManifest:
			break;
		case stockinManifest:
			break;
		case stockoutManifest:
			break;
		
		}
		ep.tempFile=File.createTempFile(""+System.currentTimeMillis(), "xls");
		Workbook wbin=Workbook.getWorkbook(originFile);
		ep.wwb=Workbook.createWorkbook(ep.tempFile, wbin);
		return ep;
	}
	private ExcelPrinter(){}
	
}
