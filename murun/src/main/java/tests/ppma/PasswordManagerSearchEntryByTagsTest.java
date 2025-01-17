package tests.ppma;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import utils.BasePageObject;
import utils.DriverProvider;
import utils.Properties;

public class PasswordManagerSearchEntryByTagsTest {

	private WebDriver driver;
	private BasePageObject basePageObject;

	@Before
	public void setUp() throws Exception {
		driver = DriverProvider.getInstance().getDriver();
		driver.get(Properties.app_url);
		basePageObject = new BasePageObject(driver);
	}

	@Test
	public void testPasswordManagerSearchEntryByTags() throws Exception {
		driver.findElement(By.id("LoginForm_username")).clear();
		driver.findElement(By.id("LoginForm_username")).sendKeys("admin");
		driver.findElement(By.id("LoginForm_password")).clear();
		driver.findElement(By.id("LoginForm_password")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id='login-form']/div/div[2]/a")).click();
		driver.findElement(By.linkText("Advanced Search")).click();
		basePageObject.waitForElementBeingClickable(By.id("Entry_tagList"));
		basePageObject.sendKeys(By.id("Entry_tagList"),"Email");
//		driver.findElement(By.id("Entry_tagList")).clear();
//		driver.findElement(By.id("Entry_tagList")).sendKeys("Email");
		basePageObject.waitForElementBeingVisibleOnPage(By.name("yt0"));
		basePageObject.click(By.name("yt0"));
//		driver.findElement(By.name("yt0")).click();
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[1]")).getText().contains("Google"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[2]")).getText().contains("myaccount@google.it"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[3]")).getText().contains("Email, Google"));
		driver.findElement(By.linkText("Advanced Search")).click();
		basePageObject.waitForElementBeingClickable(By.id("Entry_tagList"));
		basePageObject.sendKeys(By.id("Entry_tagList"),"Google");
//		driver.findElement(By.id("Entry_tagList")).clear();
//		driver.findElement(By.id("Entry_tagList")).sendKeys("Google");
		basePageObject.waitForElementBeingVisibleOnPage(By.name("yt0"));
		basePageObject.click(By.name("yt0"));
//		driver.findElement(By.name("yt0")).click();
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[1]")).getText().contains("Google"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[2]")).getText().contains("myaccount@google.it"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[3]")).getText().contains("Email, Google"));
		driver.findElement(By.linkText("Advanced Search")).click();
		basePageObject.waitForElementBeingClickable(By.id("Entry_tagList"));
		basePageObject.sendKeys(By.id("Entry_tagList"),"Email, Google");
//		driver.findElement(By.id("Entry_tagList")).clear();
//		driver.findElement(By.id("Entry_tagList")).sendKeys("Email, Google");
		basePageObject.waitForElementBeingVisibleOnPage(By.name("yt0"));
		basePageObject.click(By.name("yt0"));
//		driver.findElement(By.name("yt0")).click();
		assertTrue(driver.findElement(By.className("empty")).getText().contains("No results found."));
		driver.findElement(By.linkText("Profile")).click();
		driver.findElement(By.linkText("Logout")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

}
