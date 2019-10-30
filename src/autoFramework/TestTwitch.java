package autoFramework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * These are some Selenium exercises - opening a page, activating some element, waiting. Then I try some command line runs from within the program. These are separate
 * The main idea is to get a hold of some of the possibilities of Selenium and TestNG - we did not integrate with JUnit this time 
 */

public class TestTwitch {
	// Like this
	@FindBy(xpath = "")
	WebElement clickStuff;

	public WebDriver driver;

	@BeforeMethod
	public void beforeMethod() {
		driver = new FirefoxDriver();
	}

	/**
	 * Navigate to a YT page and listen to some music
	 * 
	 * @throws InterruptedException
	 * @throws IllegalMonitorStateException
	 * @throws IOException
	 */
	@Test
	public void testYT() throws InterruptedException, IllegalMonitorStateException, IOException {
		driver.get("https://www.youtube.com/watch?v=oIscL-Bjsq4");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		System.out.println(System.getenv("PATH"));
		Assert.assertEquals(driver
				.findElement(
						By.xpath("(//yt-formatted-string[@class='style-scope ytd-video-primary-info-renderer'])[1]"))
				.getText(), "The End Of Evangelion - Komm Süsser Tod");

		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.SPACE).perform();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"toggleButton\"]")).click();

		while (!driver.findElement(By.className("ytp-time-current")).getText()
				.equals(driver.findElement(By.className("ytp-time-duration")).getText()
						)) {
			Thread.sleep(2000);
		}
		System.out.println("Done!");
	}

	/**
	 * Some playing around with command line calls
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test(enabled = false)
	public void testCmdLine() throws IOException, InterruptedException {

		Process process = Runtime.getRuntime().exec("cmd /c ipconfig");
		StringBuilder output = new StringBuilder();
		BufferedReader read = new BufferedReader(new InputStreamReader(process.getInputStream()));

		String line;
		while ((line = read.readLine()) != null) {
			output.append(line + "\n");
		}
		int exitVal = process.waitFor();
		if (exitVal == 0) {
			System.out.println("Success!");
			System.out.println(output);
			System.exit(0);
		} else {
			IllegalStateException e = new IllegalStateException();
			throw (e);
		}
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
