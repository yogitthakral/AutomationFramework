package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;


public class Excel {
	public String path;
	FileInputStream fis;
	FileOutputStream fos;
	public XSSFWorkbook workbook;
	public XSSFSheet worksheet;
	public XSSFRow row;
	public XSSFCell cell;
	ArrayList<String> columnList= new ArrayList<String>();

	public Excel(String path, String sheet_name){
		try{
			this.path=path;
			fis= new FileInputStream(path);//put path in new fileinput stream
			workbook= new XSSFWorkbook(fis);//load fileinput stream into new workbook
			worksheet=workbook.getSheet(sheet_name);//get sheet from workbook
			fis.close();//close the file input stream
		}

		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void createExcel(String path,String sheet_name){
		try{
			this.path=path;
			workbook= new XSSFWorkbook();//create new workbook
			worksheet=workbook.createSheet(sheet_name);//create a new sheet in the new workbook
			fos = new FileOutputStream(path);//load the path in new file outputstream
			workbook.write(fos);//write the workbook into file output stream
			fos.close();//close the file output stream
			workbook.close();	//close the workbook
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public int getRowCount(String sheetName)
	{
		int index=workbook.getSheetIndex(sheetName);//get sheet index number

		if(index==-1)
		{
			return 0;	
		}

		else
		{
			worksheet=workbook.getSheetAt(index);
			int number=worksheet.getLastRowNum();//number will start from 0 , so we need to add 1 to it to get actual number
			return number+1;
		}
	}
	
	public void addSheet(String sheetName) throws IOException
	{
		workbook.createSheet(sheetName);
		fos= new FileOutputStream(path);
		workbook.write(fos);
		fos.close();

	}
	
	public String getStringCellValue(int rowNum,int cellNum)
	{
		
		row=worksheet.getRow(rowNum);
		cell= row.getCell(cellNum);
		String result = null;
		CellType type = cell.getCellTypeEnum();
		
		if (type == CellType.STRING) {

			result=cell.getStringCellValue();

		}

		else if (type == CellType.NUMERIC) {

			result= (Integer.toString((int) cell.getNumericCellValue()));
		}

		else if (type == CellType.BOOLEAN) {

			result= (Boolean.toString(cell.getBooleanCellValue()));

		}

		else if (type == CellType.BLANK) {

			Reporter.log("Blank cell found While Reading Excel");
			
			result= null;

		}
		return result;
		
	}
	
	
	public void setStringCellvalue(int rowNum,int columnNum,String value)
	{
		try{
		row=worksheet.getRow(rowNum);
		cell= row.createCell(columnNum);
		cell.setCellValue(value);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public ArrayList<String> readColumn(String sheetName,int column) throws IOException
	{
		columnList.clear();	



		worksheet= workbook.getSheet(sheetName);

		int rowCount= worksheet.getLastRowNum();

		for(int i=1;i<=rowCount;i++)
		{
			row=worksheet.getRow(i);
			cell= row.getCell(column);
			CellType type = cell.getCellTypeEnum();

			if (type == CellType.STRING) {

				columnList.add(cell.getStringCellValue());

			}

			else if (type == CellType.NUMERIC) {

				columnList.add(Double.toString(cell.getNumericCellValue()));
			}

			else if (type == CellType.BOOLEAN) {

				columnList.add(Boolean.toString(cell.getBooleanCellValue()));

			}

			else if (type == CellType.BLANK) {

				Reporter.log("Blank cell found While Reading Excel");
				continue;

			}

		}

		return columnList;

	}
	
	public void closeExcel() throws IOException
	{
		fos = new FileOutputStream(path);
		workbook.write(fos);
		fos.close();
		workbook.close();	
	}

	
}
