package tests.mrbs;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class SearchMultipleEntriesTest {

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		driver = DriverProvider.getInstance().getDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(Properties.app_url);
	}

	@Test
	public void testSearchMultipleEntries() throws Exception {
		driver.findElement(By.xpath(".//*[@id='logon_box']/form/div/input[3]")).click();
		driver.findElement(By.name("NewUserName")).clear();
		driver.findElement(By.name("NewUserName")).sendKeys("administrator");
		driver.findElement(By.name("NewUserPassword")).clear();
		driver.findElement(By.name("NewUserPassword")).sendKeys("secret");
		driver.findElement(By.cssSelector("input.submit")).click();
		driver.findElement(By.name("search_str")).clear();
		driver.findElement(By.name("search_str")).sendKeys("MyEvent" + Keys.ENTER);
		driver.findElement(By.xpath(".//*[@id='search_results_wrapper']/div[6]/div[3]/div[1]/div/table/thead/tr/th[2]"))
				.click();
		assertEquals("MyEvent1",
				driver.findElement(
						By.xpath(".//*[@id='search_results_wrapper']/div[6]/div[2]/div[2]/table/tbody/tr[1]/td"))
						.getText());
		assertEquals("Description for MyEvent1",
				driver.findElement(By.xpath(".//*[@id='search_results']/tbody/tr[1]/td[3]")).getText());
		assertEquals("MyEvent2",
				driver.findElement(
						By.xpath(".//*[@id='search_results_wrapper']/div[6]/div[2]/div[2]/table/tbody/tr[2]/td"))
						.getText());
		assertEquals("Description for MyEvent2",
				driver.findElement(By.xpath(".//*[@id='search_results']/tbody/tr[2]/td[3]")).getText());
		assertEquals("MyEvent3",
				driver.findElement(
						By.xpath(".//*[@id='search_results_wrapper']/div[6]/div[2]/div[2]/table/tbody/tr[3]/td"))
						.getText());
		assertEquals("Description for MyEvent3",
				driver.findElement(By.xpath(".//*[@id='search_results']/tbody/tr[3]/td[3]")).getText());
		driver.findElement(By.linkText("Meeting Room Booking System")).click();
		driver.findElement(By.cssSelector("#logon_box > form > div > input[type=\"submit\"]")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}
