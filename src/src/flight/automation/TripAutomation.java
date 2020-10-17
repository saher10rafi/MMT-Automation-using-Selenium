package flight.automation;

import java.awt.List;
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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import flight.automation.FlightDetails;

public class TripAutomation {

		public static void main(String[] args) throws InterruptedException {
			// TODO Auto-generated method stub
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\Saher Rafi\\Desktop\\MP-873048\\src\\resources\\drivers\\chromedriver.exe");
			String orgin = "DEL";
			String destin = "MAA";
			LocalDate dt = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
			String trDate = dt.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).format(formatter);
			WebDriver driver = new ChromeDriver();
			String baseDataUrl = "https://www.makemytrip.com/flight/search?tripType=O&itinerary=" + orgin + "-" + destin
					+ "-" + trDate
					+ "&paxType=A-1_C-0_I-0&cabinClass=E&sTime=1602002315678&forwardFlowRequired=true&mpo=&intl=false";
			System.out.println("Requesting URL: " + baseDataUrl);
			driver.get(baseDataUrl);
			driver.manage().window().maximize();
			System.out.println("Webpage found ...");
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