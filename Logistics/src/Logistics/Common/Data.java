package Logistics.Common;

import java.io.File;
import java.net.URL;
import java.util.Random;

public class Data {
	static public final int testDataSize=100000;
	static public final String superUserID="DFSfdsFSsfdsN290sfdsaEAIEadsWONE2432WFKDS";
	static public final String companyPhone="029-89164440";
	static public final String companyAddress="西安市太华北路延伸线与北三环立交东北角惠达物流B区华亿物流";
//	static public final String superUserID="202cb962ac59075b964b07152d234b70";
	static private Random randomNumber=new Random(System.currentTimeMillis());
	static private final String templateFolder="/Template";
	static private final String uploadFileFolder="/uploadFiles";
	static public final String printFolder="/print";
	static public final String fmPrintFile="fmprint.html";
	static public final String sofmPrintFile="sofmprint.html";
	static public final String simPrintFile="simprint.html";
	static public final String smPrintFile="smprint.html";
	static public String getRootPath(){
		URL url=Data.class.getResource("");
		String path=url.getPath();
		path=path.replace("%20", " ");
		System.out.println(path);
		int index=path.lastIndexOf("/WEB-INF/classes");
		return path.substring(1, index);
	}
	
	static public String barcodeTemplateFilePath(){
		String fileName="barcode template.xls";
		String path=getRootPath()+templateFolder;
		return path+"/"+fileName;
	}
	static public String itemTemplateFilePath(){
		String fileName="item template.xls";
		String path=getRootPath()+templateFolder;
		return path+"/"+fileName;
	}
	static public String newUploadFilePath(String _fileName){
		String fileName="upload_file_"+System.currentTimeMillis();
		fileName+="_";
		fileName+=_fileName;
		String path=getRootPath()+uploadFileFolder;
		File f=new File(path);
		f.mkdirs();
		return path+"/"+fileName;
	}
}
