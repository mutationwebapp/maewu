package tests;

import utils.DriverProvider;
import utils.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class RemoveEnrolMultipleUsersTest {

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		driver = DriverProvider.getInstance().getDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(Properties.app_url);
	}

	@Test
	public void testClarolineRemoveEnrolMultipleUsers() throws Exception {
		driver.findElement(By.id("login")).clear();
		driver.findElement(By.id("login")).sendKeys("admin");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id='loginBox']/form/fieldset/button")).click();
		driver.findElement(By.linkText("001 - Course001")).click();
		driver.findElement(By.id("CLUSR")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id='courseRightContent']/div[1]/span")).getText().contains("5"));
		driver.findElement(By.xpath("//*[@id='courseRightContent']/table/tbody/tr[3]/td[10]/a/img")).click();
		driver.switchTo().alert().accept();
		driver.findElement(By.xpath("//*[@id='courseRightContent']/table/tbody/tr[3]/td[10]/a/img")).click();
		driver.switchTo().alert().accept();
		driver.findElement(By.xpath("//*[@id='courseRightContent']/table/tbody/tr[3]/td[10]/a/img")).click();
		driver.switchTo().alert().accept();
		assertTrue(driver.findElement(By.xpath("//*[@id='courseRightContent']/div[1]/span")).getText().contains("2"));
		driver.findElement(By.linkText("Logout")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}
