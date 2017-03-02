package test;

import java.util.Collection;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

public class PageObjectSample
{
	private WebDriverWait wait;
	private final WebDriver driver;
    
    public PageObjectSample(WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if (!driver.getCurrentUrl().equals("http://localhost/phpmyadmin/"))
        {
            throw new IllegalStateException("Неверная страница сайта.");
        }
    }

    public PageObjectSample createDB(String name)
    {
    	driver.findElement(By.id("text_create_db")).clear();
        driver.findElement(By.id("text_create_db")).sendKeys(name);
        driver.findElement(By.id("buttonGo")).click();
    	return this;
    }
    
    public PageObjectSample createTable(String name, String col)
    {
    	driver.switchTo().defaultContent();
    	driver.switchTo().frame("frame_content");
    	driver.findElement(By.name("table")).clear();
        driver.findElement(By.name("table")).sendKeys(name);
        driver.findElement(By.name("num_fields")).clear();
        driver.findElement(By.name("num_fields")).sendKeys(col);
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    	return this;
    }
    
    public PageObjectSample setCol(String numberOfCol, String name, String type, String length, boolean nNull, boolean primary, boolean ai)
    {
    	System.out.println(4);
    	driver.findElement(By.id("field_" +numberOfCol +"_1")).clear();
        driver.findElement(By.id("field_" +numberOfCol +"_1")).sendKeys(name);
        this.waiter();
        new Select(driver.findElement(By.id("field_" +numberOfCol +"_2"))).selectByVisibleText(type);
        this.waiter();
        driver.findElement(By.id("field_" +numberOfCol +"_3")).clear();
        driver.findElement(By.id("field_" +numberOfCol +"_3")).sendKeys(length);
        this.waiter();
        if(!nNull){
        	driver.findElement(By.id("field_" +numberOfCol +"_7")).click();
        	this.waiter();
        }
        if(primary){
        	new Select(driver.findElement(By.id("field_" +numberOfCol +"_8"))).selectByVisibleText("PRIMARY");
        	this.waiter();
        }
        if(ai){
        	driver.findElement(By.id("field_" +numberOfCol +"_9")).click();
        	this.waiter();
        } 
        System.out.println(5);
        return this;
    }
    
    public PageObjectSample openInsertData()
    {
    	driver.findElement(By.linkText("Вставить")).click();
    	return this;
    }
    
    public PageObjectSample insertData(String col, String data)
    {
    	driver.findElement(By.id("field_" + col + "_3")).clear();
        driver.findElement(By.id("field_" + col + "_3")).sendKeys(data);
    	return this;
    }
    
    public PageObjectSample submitInsertData()
    {
    	driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    	return this;
    }
    
    public PageObjectSample submitTable()
    {
    	driver.findElement(By.name("do_save_data")).click();
    	return this;
    }
    
    public PageObjectSample openToCheck(String DBName, String tableName)
    {
    	driver.switchTo().frame("frame_navigation");
    	driver.findElement(By.linkText(DBName)).click();
    	System.out.println(1);
    	//driver.switchTo().frame("frame_navigation");
    	System.out.println(2);
    	driver.findElement(By.linkText(tableName)).click();
    	System.out.println(3);
    	return this;
    }
    
    public String checkResults(String tr, String td)
    {
    	String a = driver.findElement(By.xpath("//table[@id=\"table_results\"]/tbody/tr["+ tr +"]/td[" + td +"]")).getText();
    	return a;
    }
    

    public void waiter()
    {
    	try
    	{
			Thread.sleep(200);
		}
    	 catch (InterruptedException e)
    	{
			e.printStackTrace();
		}
    }
    
    
    /*// Надёжный поиск формы.
    public boolean isFormPresentForReal()
    {
		// Первое (самое правильное) решение (работает примерно в 30-50% случаев)
    	// wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//html/body"), 1));
    	
		// Второе (самое интересное) решение (работает примерно в 20-30% случаев)
		// waitForLoad(driver);

    	// Третье (самое убогое, почти за гранью запрещённого) решение -- работает в 100% случаев
    	
    	try
    	{
			Thread.sleep(500);
		}
    	 catch (InterruptedException e)
    	{
			e.printStackTrace();
		}
		
    	
    	Collection<WebElement> forms = driver.findElements(By.tagName("form"));
		if (forms.isEmpty())
		{
			return false;
		}
		
		Iterator<WebElement> i = forms.iterator();
		boolean form_found = false;
		WebElement form = null;
		
		while (i.hasNext())
		{
			form = i.next();
			if 	((form.findElement(By.name("name")).getAttribute("type").equalsIgnoreCase("text"))&&
				 (form.findElement(By.name("height")).getAttribute("type").equalsIgnoreCase("text"))&&
				 (form.findElement(By.name("weight")).getAttribute("type").equalsIgnoreCase("text"))&&
				 (form.findElement(By.xpath("//input[@type=\"submit\"]")).getAttribute("value").equalsIgnoreCase("Рассчитать"))&&
				 (form.findElements(By.name("gender")).size()==2))
				{
					form_found = true;
					break;
				}
		}
		return form_found;
    }
    
    // Проверка вхождения подстроки в пользовательское сообщение.
    public boolean userMessageContains(String search_string)
    {
    	return driver.findElement(user_message_locator).getText().contains(search_string);
    }

    // Проверка равенства пользовательского сообщения строке.    
    public boolean userMessageEquals(String search_string)
    {
    	return driver.findElement(user_message_locator).getText().equals(search_string);
    }
    
    // Проверка вхождения подстроки в сообщение об ошибке.
    public boolean errorMessageContains(String search_string)
    {
    	return driver.findElement(error_message_locator).getText().contains(search_string);
    }

    // Проверка равенства сообщения об ошибке строке.    
    public boolean errorMessageEquals(String search_string)
    {
    	return driver.findElement(error_message_locator).getText().equals(search_string);
    }


	// Получение значения имени.
    public String getName()
    {
    	return driver.findElement(username_locator).getAttribute("value");
    }

    // Получение значения веса.
    public String getWeight()
    {
    	return driver.findElement(weight_locator).getAttribute("value");  
    }
    
    // Получение значения роста.
    public String getHeight()
    {
    	return driver.findElement(height_locator).getAttribute("value");
	}

    // Получение значения пола.
    public String getGender()
    {
        if (driver.findElement(gender_m_locator).isSelected())
        {
        	return "m";
        }
        else if(driver.findElement(gender_f_locator).isSelected())
        {
        	return "f";
        }
        else
        {
        	return "";
        }	    
    }        

    void waitForLoad(WebDriver driver)
    {

    	Predicate<WebDriver> pageLoaded = new Predicate<WebDriver>()
    	{

    		@Override
    		public boolean apply(WebDriver input)
    		{
    			return ((JavascriptExecutor) input).executeScript("return document.readyState").equals("complete");
    		}

    	};
    	new FluentWait<WebDriver>(driver).until(pageLoaded);
   	}  
   */
}

