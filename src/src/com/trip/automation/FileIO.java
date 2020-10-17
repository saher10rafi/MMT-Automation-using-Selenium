package com.trip.automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileIO {
	
		//Declaring Variables
		FileInputStream fis = null;
		FileOutputStream fos = null;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		XSSFRow row = null;
		Properties prop = null;

		//Setting properties file for input
		public Properties inputSetup() {
		File file = new File("C:/Users/Saher Rafi/Desktop/MP-873048/src/resources/config.properties");

			try {
				fis = new FileInputStream(file);
				prop = new Properties();
				prop.load(fis);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return prop;
		}

		//Printing output to an excel file
		public void output(String[] name, String[] price) {
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("output");
			//iterating rows and printing the headphones with price
			for (int i = 0; i < 5; i++) {
				row = sheet.createRow(i);
				row.createCell(0).setCellValue(name[i]);
				row.createCell(1).setCellValue(price[i]);
			}
			sheet.autoSizeColumn(0);
			//Writing the output to Excel file using FileOutputStream
			try {
				fos = new FileOutputStream("output.xlsx");
				workbook.write(fos);
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		//to return the URL from properties file
		public String getURL() {
			inputSetup();
			String URL = prop.getProperty("url");
			return URL;
		}

		//to return the ChromeDriver location from properties file
		public String getChromeDriverLocation() {
			inputSetup();
			String location = prop.getProperty("chrome");
			return location;
		}

		//to return the GeckoDriver location from prperties file
		public String getGeckoDriverLocation() {
			inputSetup();
			String location = prop.getProperty("firefox");
			return location;
		}

	}
