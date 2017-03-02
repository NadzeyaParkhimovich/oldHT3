package test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterClass;

public class NewTest {
  @Test
  public void f() {
	  FirefoxProfile profile = new FirefoxProfile();
	  System.setProperty("webdriver.gecko.driver", "D:/Eclipse_Projects/driver/geckodriver.exe");
	  profile.setPreference("browser.startup.homepage", "about:blank");
	  WebDriver driver = new FirefoxDriver(profile);
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  driver.get("http://127.0.0.1/phpmyadmin/");
	  
	  System.out.println(driver.getTitle());
	  try
  		{
			Thread.sleep(500);
		}
  	 catch (InterruptedException e)
  		{
			e.printStackTrace();
		}
	  System.out.println(driver.getPageSource());
	  driver.findElement(By.id("frame_navigation"));
	  driver.quit();
  }
  @BeforeClass
  public void beforeClass() {
  }

  @AfterClass
  public void afterClass() {
  }

}
