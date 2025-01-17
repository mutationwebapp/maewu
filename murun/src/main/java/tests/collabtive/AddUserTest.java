package tests.collabtive;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import utils.DriverProvider;
import utils.Properties;

public class AddUserTest {

	private WebDriver driver;

	@Before
	public void setUp() {
		driver = DriverProvider.getInstance().getDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		driver.get(Properties.app_url);
	}

	@Test
	public void testCollabtiveAddUser() throws Exception {
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("pass")).clear();
		driver.findElement(By.id("pass")).sendKeys("admin");
		driver.findElement(By.cssSelector("button.loginbutn")).click();
		driver.findElement(By.xpath(".//*[@id='mainmenue']/li[3]/a")).click();
		driver.findElement(By.xpath(".//*[@id='contentwrapper']/div[1]/ul/li[2]/a")).click();
		driver.findElement(By.id("add_butn_member")).click();
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("username001");
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("username001@username.it");
		driver.findElement(By.id("pass")).clear();
		driver.findElement(By.id("pass")).sendKeys("password001");
		new Select(driver.findElement(By.id("roleselect"))).selectByVisibleText("Client");
		driver.findElement(By.xpath("//*[@id=\"form_member\"]/div/form/fieldset/div[11]/div/button")).click();
		new Actions(driver).moveToElement(driver.findElement(By.xpath("(//table/tbody/tr[1]/td[2]/a/img)[1]"))).build()
				.perform();
		driver.findElement(By.cssSelector("a.edit")).click();
		assertEquals("username001", driver.findElement(By.id("name")).getAttribute("value"));
		assertEquals("username001@username.it", driver.findElement(By.id("email")).getAttribute("value"));
		assertEquals("Client", driver.findElement(By.xpath(
				"html/body/div[1]/div[2]/div[2]/div/div/div[1]/form/fieldset/table/tbody/tr/td[2]/div/div/table/tbody[23]/tr/td[2]/select/option[2]"))
				.getText());
		driver.findElement(By.xpath(".//*[@id='mainmenue']/li[4]/a")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

}
