package autoFramework;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * These are some Selenium exercises - opening a page, activating some element, waiting. Then I try some command line runs from within the program. These are separate
 * The main idea is to get a hold of some of the possibilities of Selenium and TestNG - we did not integrate with JUnit this time 
 */

public class TestTwitch {
	public WebDriver driver;

	@BeforeMethod
	public void beforeMethod() {
		// driver = new FirefoxDriver();
	}

	@Test
	public void testUrl() throws InterruptedException, IllegalMonitorStateException, IOException {
		
		/*
		 *Some playing around with youtube 
		 */
		driver.get("https://www.youtube.com/watch?v=oIscL-Bjsq4");
		Actions builder = new Actions(driver);
		Thread.sleep(5000);
		System.out.println(System.getenv("PATH"));
		Assert.assertEquals(driver
				.findElement(
						By.xpath("(//yt-formatted-string[@class='style-scope ytd-video-primary-info-renderer'])[1]"))
				.getText(), "The End Of Evangelion - Komm Süsser Tod");
		builder.sendKeys(Keys.SPACE).perform();
		Thread.sleep(20000);
		
		/*
		 * Some playing around with command line calls
		 */
//		Process process = Runtime.getRuntime().exec("cmd /c ipconfig");
//		StringBuilder output = new StringBuilder();
//		BufferedReader read = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//		String line;
//		while ((line = read.readLine()) != null) {
//			output.append(line + "\n");
//		}
//		int exitVal = process.waitFor();
//		if (exitVal == 0) {
//			System.out.println("Success!");
//			System.out.println(output);
//			System.exit(0);
//		} else {
//			IllegalStateException e = new IllegalStateException();
//			throw (e);
//		}

	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
