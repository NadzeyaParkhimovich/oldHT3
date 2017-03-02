package test;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SampleTest_with_PageObjectSample
{
	String base_url = "http://localhost/";
	StringBuffer verificationErrors = new StringBuffer();
    FirefoxProfile profile = new FirefoxProfile();
	WebDriver driver = null;
		
	@BeforeClass
	public void beforeClass() throws Exception
	{
		System.setProperty("webdriver.gecko.driver", "D:/Eclipse_Projects/driver/geckodriver.exe");
		profile.setPreference("browser.startup.homepage", "about:blank");
		profile.setPreference("xpinstall.signatures.required", false);
		driver = new FirefoxDriver(profile);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterClass
	public void afterClass()
	{
		driver.quit();
		driver = null;
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}  
	
	
	@Test (enabled = false)
	public void creationDB()
	{
		driver.get(base_url + "/phpmyadmin");
		PageObjectSample page = new PageObjectSample(driver);
		driver.switchTo().frame("frame_content");
		page.createDB("auth");
		page.createTable("users", "6");
		page.setCol("0", "u_id", "INT", "11", true, true, true);
		page.setCol("1", "u_login", "VARCHAR", "255", true, false, false);
		page.setCol("2", "u_password", "CHAR", "40", true, false, false);
		page.setCol("3", "u_email", "VARCHAR", "255", true, false, false);
		page.setCol("4", "u_name", "VARCHAR", "255", true, false, false);
		page.setCol("5", "u_remember", "CHAR", "40", true, false, false);
		System.out.println("Try");
		page.submitTable();
		page.openInsertData();
		page.insertData("1", "1");
		page.insertData("2", "user1");
		page.insertData("3", "e38ad214943daad1d64c102faec29de4afe9da3d");
		page.insertData("4", "user1@mail.com");
		page.insertData("5", "Pupkin");
		page.submitInsertData();
		page.openInsertData();
		page.insertData("1", "2");
		page.insertData("2", "user2");
		page.insertData("3", "2aa60a8ff7fcd473d321e0146afd9e26df395147");
		page.insertData("4", "user2@mail.com");
		page.insertData("5", "Smith");
		page.submitInsertData();
	}
	
	
	@Test
	public void testDB()
	{
		driver.get(base_url + "/phpmyadmin");
		PageObjectSample page = new PageObjectSample(driver);
		
		page.openToCheck("auth (1)", "users");
		driver.switchTo().defaultContent();
    	driver.switchTo().frame("frame_content");
		Assert.assertEquals(page.checkResults("1", "4"), "1");
		Assert.assertEquals(page.checkResults("1", "5"), "user1");
		Assert.assertEquals(page.checkResults("1", "6"), "e38ad214943daad1d64c102faec29de4afe9da3d");
		Assert.assertEquals(page.checkResults("1", "7"), "user1@mail.com");
		Assert.assertEquals(page.checkResults("1", "8"), "Pupkin");
		Assert.assertEquals(page.checkResults("1", "9"), " ");
		Assert.assertEquals(page.checkResults("2", "4"), "2");
		Assert.assertEquals(page.checkResults("2", "5"), "user2");
		Assert.assertEquals(page.checkResults("2", "6"), "2aa60a8ff7fcd473d321e0146afd9e26df395147");
		Assert.assertEquals(page.checkResults("2", "7"), "user2@mail.com");
		Assert.assertEquals(page.checkResults("2", "8"), "Smith");
		Assert.assertEquals(page.checkResults("2", "9"), " ");
	}

	/*public void sampleTest()
	{
		// 1 (action) Открыть http://svyatoslav.biz/testlab/wt
		driver.get(base_url + "/testlab/wt/");
		
		// С этого момента можно использовать PajeObject.
		PageObjectSample page = new PageObjectSample(driver);
		
		// 1 (check) Страница содержит форму с полями «Имя», «Рост», «Вес», радиокнопкой «Пол» и кнопкой отправки данных «Рассчитать».
		Assert.assertTrue(page.isFormPresentForReal(), "No suitable forms found!");
		
		// 2 (action) В поле «Имя» ввести «username».
		page.setName("username");
		
		// 2 (check) Значение появляется в поле.
		Assert.assertEquals(page.getName(), "username", "Unable to fill 'Имя' field");
		
		
		// 3 (action) В поле «Рост» ввести «50».
		page.setHeight("50");				
		
		// 3 (check) Значение появляется в поле.
		Assert.assertEquals(page.getHeight(), "50", "Unable to fill 'Рост' field");
				

		// 4 (action) В поле «Вес» ввести «3».
		page.setWeight("3");
				
		// 4 (check) Значение появляется в поле.
		Assert.assertEquals(page.getWeight(), "3", "Unable to fill 'Вес' field");
			
		
		// 5 (action) В радиокнопке «Пол» выбрать пол «М».
		page.setGender("m");
		
		// 5 (check) Вариант «М» выбран.
		Assert.assertEquals(page.getGender(), "m", "Unable select 'М' gender");
		
		// 6 (action) Нажать «Рассчитать».
		page.submitForm();
		
		// 6 (check) Форма исчезает, в центральной ячейке таблицы появляется надпись «Слишком мала масса тела».
		Assert.assertFalse(page.isFormPresentForReal(), "Form is on the page!");
		Assert.assertTrue(page.userMessageEquals("Слишком малая масса тела"), "Message 'Слишком малая масса тела' either is absent or is not in a proper place");  
	}*/
}

