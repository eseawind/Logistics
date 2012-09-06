package Logistics.Servlet;

import java.io.*;
import java.util.ArrayList;

import Logistics.Common.LExcel;
import Logistics.DTO.BarcodeDTO;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

public class FileUploadAction extends ActionSupport{
	
	protected File upload;
	protected String uploadContentType;
	protected String uploadFileName;
	

	public String method1() throws Exception{
		
		InputStream is=new FileInputStream(upload);
		String outDir="c:\\1.txt";
		byte[] buffer=new byte[256*1024];
		int len=0;
		OutputStream os=new FileOutputStream(new File(outDir));
		while((len=is.read(buffer))>0){
			os.write(buffer,0,len);
		}
		is.close();
		os.close();
		return SUCCESS;
	}

	
	
	


	



	

	public String getUploadContentType() {
		return uploadContentType;
	}



	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}



	public String getUploadFileName() {
		return uploadFileName;
	}



	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}












	public File getUpload() {
		return upload;
	}












	public void setUpload(File upload) {
		this.upload = upload;
	}












	
}
