package Logistics.Servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jxl.*;

import org.apache.struts2.ServletActionContext;

import Logistics.Common.CommonTools;
import Logistics.DAO.*;
import Logistics.DTO.*;

public class UpLoadAction {
	//基本对象
	
	// myFile属性用来封装上传的文件  
    private File myFile;  
      
    // myFileContentType属性用来封装上传文件的类型  
    private String myFileContentType;  
  
    // myFileFileName属性用来封装上传文件的文件名  
    private String myFileFileName;

    
    public String upLoadFile() throws Exception
    {
    	//基于myFile创建一个文件输入流  
        InputStream is = new FileInputStream(myFile);  
          
        // 设置上传文件目录  
        String uploadPath = ServletActionContext.getServletContext()  
                .getRealPath("/upload");  
          
        // 设置目标文件  
        //File toFile = new File(uploadPath, this.getMyFileFileName());  
          
        // 创建一个输出流  
        //OutputStream os = new FileOutputStream(toFile);  
  
        //设置缓存  
        byte[] buffer = new byte[1024];  
  
        int length = 0;  
  
//        //读取myFile文件输出到toFile文件中  
//        while ((length = is.read(buffer)) > 0) {  
//            os.write(buffer, 0, length);  
//        }  
        
        Workbook workbook = Workbook.getWorkbook(is);
        
        Sheet sheet = workbook.getSheet(0);
        
        Cell a = sheet.getCell(0, 0);
        Cell b = sheet.getCell(1, 0);
        
        
        System.out.println("上传文件名"+myFileFileName);  
        System.out.println("上传文件内容"+a.getContents()+"  "+b.getContents());  
        //关闭输入流  
        is.close();  
        workbook.close();
        //关闭输出流  
        //os.close();  
    	
    	
    	
    	return "success";
    }
    
    
	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getMyFileContentType() {
		return myFileContentType;
	}

	public void setMyFileContentType(String myFileContentType) {
		this.myFileContentType = myFileContentType;
	}

	public String getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	} 
	
	
	
	

}
