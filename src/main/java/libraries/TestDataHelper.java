package libraries;

import java.io.*;
import java.math.*;
import org.apache.logging.log4j.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.testng.Assert;

public class TestDataHelper {
	
	
	private static Logger logger=LogManager.getLogger(TestDataHelper.class.getName());
	/*Method to read data from the excel 
	 * @parameters: sheetName
	 * @return: TestData array
	 */
	@SuppressWarnings("resource")
	public static Object[][] getTestData(String sheetName) throws IOException
	{
		FileInputStream dataFilePath=null;
		try
		{
			//get the file path
			 dataFilePath=new FileInputStream("./resources/TMDBTestData.xlsx");
			
			//Get the excel workbook from file path
			XSSFWorkbook dataWorkBook=new XSSFWorkbook(dataFilePath);
			
			//get the excel sheet from workbook
			XSSFSheet dataSheet = dataWorkBook.getSheet(sheetName);
			
			//get number of rows that contain data in the sheet
			int rowsCount=dataSheet.getLastRowNum();
			Row firstRow=dataSheet.iterator().next();
			
			//get number of columns that contain data in a row
		   int columnCount = firstRow.getLastCellNum();
		   
		   //Array to store the excel cells data
		   Object Testdata[][] = new Object[rowsCount][columnCount];
		   int count = 0;
		   
		   //Iterate to get the data from cells and place in the array
		   for (int index1 = 1; index1<=rowsCount; index1++) 
		   {
			    for (int index2 = 0; index2 < columnCount; index2++) 
			    {
			    	
			    	Row row=dataSheet.getRow(index1);
			    	Cell cell = row.getCell(index2);
			    	switch(cell.getCellType())
			    	{
						case STRING:
							Testdata[count][index2] = cell.getStringCellValue();
							break;
						case NUMERIC:
						BigDecimal bd = new BigDecimal(( cell.getNumericCellValue() - Math.floor( cell.getNumericCellValue() )) * 100 );
						bd = bd.setScale(4,RoundingMode.HALF_DOWN);
						if(bd.intValue()>0)
							Testdata[count][index2] = cell.getNumericCellValue();
						else
							Testdata[count][index2]=(int)cell.getNumericCellValue();;
						default:
							break;
			    	}
		    }
		    count++;
		   }
		   return Testdata;
		}
		catch(Exception ex)
		{
			logger.log(Level.ERROR,"Exception occured while retriving data from excel",ex);
			Assert.fail(ex.getMessage());
		}
		finally{
			dataFilePath.close();
		}
		return null;
	}
}
