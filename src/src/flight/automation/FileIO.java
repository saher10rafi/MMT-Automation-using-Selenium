package flight.automation;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;


public class FileIO {
	
		private static XSSFSheet ExcelWSheet;
		private static XSSFWorkbook ExcelWBook;
		private static XSSFCell Cell;
		private static XSSFRow Row;
		
public static void setExcelFile(String Path,String SheetName) throws Exception {

		try {

		// Open the Excel file

			FileInputStream ExcelFile = new FileInputStream(Path);
			//For the required data sheet
			
			ExcelWBook = new XSSFWorkbook("flight.xlsx");
			ExcelWSheet = ExcelWBook.getSheet("flightAuto");
			} catch (Exception e)
			{
			  throw (e);
			}
 			}

	public static String getCellData(int RowNum, int ColNum) throws Exception{

			   try{

				Cell = ExcelWSheet.getRow(0).getCell(1);

				String CellData = Cell.getStringCellValue();

				return CellData;

				}catch (Exception e){

				return"";

				}

	}

	public static void setCellData(String Result,  int RowNum, int ColNum) throws Exception	{

			try{

				Row  = ExcelWSheet.getRow(1);

			Cell = Row.getCell(1);

			if (Cell == null) {

				Cell = Row.createCell(0);

				Cell.setCellValue(4);

				} else {

					Cell.setCellValue(Result);
				}


			FileOutputStream fileOut = new FileOutputStream("flight.xlsx");
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			}catch(Exception e){
			throw (e);
			}

		}

	}
