package Logistics.Servlet;

import java.io.*;

import Logistics.DAO.MysqlTools;

import com.opensymphony.xwork2.ActionSupport;



public class FileDownloadAction extends ActionSupport{
	private MysqlTools mysqlTools=null;
	private String message;
	private int qualifiedAmount;
	private boolean valid=true;
	private boolean success;
	private int limit;
	private int start;
	private InputStream download;
	
	
	//Dao object
	
	public FileDownloadAction(){
	}
	
	public String method1() throws Exception{
		download=new FileInputStream(new File("c:\\barcodes.xls"));
		return SUCCESS;
	}

	public InputStream getDownload() {
		return download;
	}

	public void setDownload(InputStream download) {
		this.download = download;
	}
	
}
