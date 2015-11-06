package gov.samhsa.pcm.web.selenium;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SeleniumIntegrationTest {
	private Selenium selenium;
	 @Before
	 public void setUp() throws Exception {
	  selenium = new DefaultSelenium("localhost", 4444, "*firefox", "https://localhost:8444/");
	  selenium.start();
	 }

	@Test
	public void test2() throws Exception {
		/*
		// TODO: Use this api
		WebDriver driver = new FirefoxDriver();
		// And now use this to visit Google
        driver.get("http://www.google.com");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.name("q"));

        // Enter something to search for
        element.sendKeys("Cheese!");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());

     // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("cheese!");
            }
        });

        // Should see: "cheese! - Google Search"
        System.out.println("Page title is: " + driver.getTitle());

        //Close the browser
        driver.quit();
        */

		selenium.open("/consent2share/");
		selenium.type("id=j_username", "albert.smith");
		selenium.type("id=j_password", "P@rr0tf1$h");
		selenium.click("id=loginButton");
		selenium.waitForPageToLoad("30000");
		selenium.click("//body[@id='myhome-page']/div/header/div/div/div[2]/a[2]/span[2]");
		selenium.click("link=Logout");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
