package com.trip.automation;

import java.util.List;
import java.util.Properties;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import flight.automation.FlightDetails;

public class Implementation {

	static WebDriver driver;
	static Properties prop;
	static FileIO fileio;
	String orgin = "DEL";
	String destin = "MAA";
	LocalDate dt = LocalDate.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	String trDate = dt.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).format(formatter);
	
	//constructor
		public Implementation() {
		fileio = new FileIO();
		prop = fileio.inputSetup();
		}

	//getting the driver from DriverSetup
		public void createDriver(int option) {
		driver = DriverSetup.getDriver(option);
}
		public void Imp() throws InterruptedException {
			String element_xpath = "//*[@id='left-side--wrapper']/div[2]";
			org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
					15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element_xpath)));
			System.out.println("Scrolling document upto bottom ...");
			JavascriptExecutor js = (JavascriptExecutor) driver;

			for (int i = 1; i < 10; i++) {
				Thread.sleep(500);
				js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
			}
			Thread.sleep(2000);
			System.out.println("Scrolling document upto bottom completed...");
			String body = driver.findElement(By.tagName("body")).getAttribute("innerHTML");
			System.out.println("Closing Chrome ...");
			driver.quit();
			System.out.println("Getting data from DOM ...");

			Document document = Jsoup.parse(body);
			Elements elements = document.select(".fli-list-body-section");
			java.util.List<FlightDetails> lstFlights = new ArrayList<FlightDetails>();
			for (Element row : elements) {
				Elements name = row.select(".airways-name");
				Elements price = row.select(".actual-price");
				Elements deptTime = row.select(".dept-time");
				FlightDetails objFD = new FlightDetails();
				objFD.setName(name.text());
				objFD.setPrice(extractInt(price.text()));
				objFD.setDeptTime(deptTime.text());
				lstFlights.add(objFD);
			}
			// Lambda comparator to sort the list with price
			Comparator<FlightDetails> priceComp = (FlightDetails F1,
					FlightDetails F2) -> (int) (F2.getPrice() - F1.getPrice());
			Collections.sort(lstFlights, priceComp);
			System.out.println("\nAfter sorting by descending price:");
			// If you want you can limit the list upto top 5 as per your requirement
			for (FlightDetails flight : lstFlights) {
				System.out.println("Flight Name: " + flight.getName() + "\t\t" + "Price: " + flight.getPrice() + "\t\t"
						+ "DeptTime: " + flight.DeptTime);
			}

		}

		static long extractInt(String str) {
			// Replacing every non-digit number
			// with a space(" ")
			str = str.replaceAll("[^\\d]", " ");

			// Remove extra spaces from the beginning
			// and the ending of the string
			str = str.trim();

			// Replace all the consecutive white
			// spaces with a empty space
			str = str.replaceAll(" +", "");

			if (str.equals(""))
				return 0;

			return Long.parseLong(str);
		}

		}