package Logistics.Common;

import jxl.write.Label;

public interface DtoToExcel {
	
	public int getColn();
	public  boolean toCellName(Label [] cells);
	public boolean toCellValue(Label [] cells);
}
