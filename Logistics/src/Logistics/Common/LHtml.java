package Logistics.Common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.util.ArrayList;

import Logistics.DTO.CargoDTO;
import Logistics.DTO.FreightManifestDTO;
import Logistics.DTO.ShipmentManifestDTO;
import Logistics.DTO.StockinManifestDTO;

public class LHtml {
	public static final int capacity=1024*100;
	public static final int freightManifest=1;
	public static final int stockoutFreightManifest=2;
	public static final int shipmentManifest=3;
	public static final int stockinManifest=4;
	private File file;
	private File tempFile;
	private int type=0;
	private OutputStreamWriter fw=null;
	InputStreamReader fr=null;
	BufferedReader br=null;
	int itemNofaPage=0;
	private LHtml(){}
	public static LHtml getLHtml(int type){
		LHtml res=new LHtml();
		res.type=type;
		switch(type){
		case freightManifest:
			String path=Data.getRootPath()+Data.printFolder+"/"+Data.fmPrintFile;
			System.out.println(path);
			res.file=new File(path);
			if(!res.file.exists()){
				return null;
				
			}
			res.itemNofaPage=5;
			break;
		case stockoutFreightManifest:
			path=Data.getRootPath()+Data.printFolder+"/"+Data.sofmPrintFile;
			System.out.println(path);
			res.file=new File(path);
			if(!res.file.exists()){
				return null;
				
			}
			res.itemNofaPage=20;
			break;
		case shipmentManifest:
			path=Data.getRootPath()+Data.printFolder+"/"+Data.smPrintFile;
			System.out.println(path);
			res.file=new File(path);
			if(!res.file.exists()){
				return null;
				
			}
			res.itemNofaPage=20;
			break;
		case stockinManifest:
			path=Data.getRootPath()+Data.printFolder+"/"+Data.simPrintFile;
			System.out.println(path);
			res.file=new File(path);
			if(!res.file.exists()){
				return null;
				
			}
			res.itemNofaPage=15;
			break;
		default:
			return null;
		}
		return res;
	}
	public InputStream writeToHtml(ArrayList<FreightManifestDTO> fms) throws IOException{
		
		if(!this.open()){
			return null;
		}
		String buffer="";
		buffer=this.read();
		String[] tempArr=getTable(buffer);
		ArrayList<String> tableList=new ArrayList<String>();
		if(tempArr==null)
			return null;
		for(FreightManifestDTO fm:fms){
			if(fm==null)
				return null;
			int pageN=0;
			if(fm.cargos!=null)	
				pageN=fm.cargos.size()/this.itemNofaPage+(fm.cargos.size()%this.itemNofaPage==0?0:1);
			if(pageN<=0)
				pageN=1;
			Tools.print(""+pageN);
			for(int i=0;i<pageN;i++){
				String table=null;
				table=""+tempArr[1];
				table=fm.replaceInATable(table, i+1, pageN,this.itemNofaPage);
				tableList.add(table);
			}
			
		}
		buffer=concat(tempArr[0],tableList);
		fw.write(buffer);
		
		return new FileInputStream(tempFile);
	}
	
	public InputStream writeSMToHtml(ArrayList<ShipmentManifestDTO> sms) throws IOException{
		if(!this.open()){
			return null;
		}
		String buffer="";
		buffer=this.read();
		String[] tempArr=getTable(buffer);
		ArrayList<String> tableList=new ArrayList<String>();
		if(tempArr==null)
			return null;
		for(ShipmentManifestDTO sm:sms){
			if(sm==null)
				return null;
			int pageN=0;
			if(sm.fms!=null)
				pageN=sm.fms.size()/this.itemNofaPage+(sm.fms.size()%this.itemNofaPage==0?0:1);
			if(pageN<=0)
				pageN=1;
			for(int i=0;i<pageN;i++){
				String table=null;
				table=""+tempArr[1];
				table=sm.replaceInATable(table, i+1, pageN,this.itemNofaPage);
				tableList.add(table);
			}
			
		}
		buffer=concat(tempArr[0],tableList);
		fw.write(buffer);
		
		return new FileInputStream(tempFile);
	}
	public InputStream writeSimToHtml(ArrayList<StockinManifestDTO> sims) throws IOException{
		
		if(!this.open()){
			return null;
		}
		String buffer="";
		buffer=this.read();
		String[] tempArr=getTable(buffer);
		ArrayList<String> tableList=new ArrayList<String>();
		if(tempArr==null)
			return null;
		for(StockinManifestDTO sim:sims){
			if(sim==null)
				return null;
			
			int pageN=0;
			if(sim.stockinItems!=null)
				pageN=sim.stockinItems.size()/this.itemNofaPage+(sim.stockinItems.size()%this.itemNofaPage==0?0:1);
			if(pageN==0)
				pageN=1;
			for(int i=0;i<pageN;i++){
				String table=null;
				table=""+tempArr[1];
				table=sim.replaceInATable(table, i+1, pageN,this.itemNofaPage);
				tableList.add(table);
			}
			
		}
		buffer=concat(tempArr[0],tableList);
		fw.write(buffer);
		
		return new FileInputStream(tempFile);
	}
	public String read() throws IOException{
		String buffer="";
		String temp=null;
		char[] cbuf=new char[capacity];
		int count=0;
		
		while((count=br.read(cbuf))!=-1){
			buffer+=String.valueOf(cbuf, 0, count);
		}
		
		return buffer;
	}
	private boolean open() throws IOException{
		tempFile=File.createTempFile("print", "html");
		fw=new OutputStreamWriter(new FileOutputStream(tempFile),"utf-8");
		fr=new InputStreamReader(new FileInputStream(file),"utf-8");
		br=new BufferedReader(fr,1024);
		return fw!=null && fr!=null && br!=null;
	}
	public void close(){
		try {
			if(fw!=null)
				fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(fr!=null)
				fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(br!=null)
				br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static String[] getTable(String buffer){
		String[] res=new String[2];
		int start=buffer.indexOf("<table");
		int end=buffer.indexOf("</table>",start)+"</table>".length();
		res[1]=buffer.substring(start, end);
		res[0]=buffer.substring(0,start)+buffer.substring(end, buffer.length());
		return res;
	}
	public static final String splitPage="<div style=\"PAGE-BREAK-AFTER: always\"></div>";
	public static String concat(String htmlString,ArrayList<String> tables){
		String buffer="";
		if(tables==null ||tables.size()<1)
			return htmlString;
		buffer+=tables.get(0)+"\n";
		for(int i=1;i<tables.size();i++){
			buffer+=splitPage+"\n";
			buffer+=tables.get(i)+"\n";
		}
		return htmlString.replace("</body>",buffer+" \n</body>");
	}
	
}
