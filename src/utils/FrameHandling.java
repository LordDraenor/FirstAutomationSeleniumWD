package utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.omg.CORBA.DynAnyPackage.InvalidValue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;


public class FrameHandling {	

	static public void switchToFrame(WebDriver driver,int frameIndex) throws InterruptedException {			
		Thread.sleep(5000);
		List<WebElement> frameList = driver.findElements(By.tagName("iframe"));
		for (int i = 0; i < frameList.size(); i++) {
			System.out.print("This is the " + i + "st frame id: ");
			System.out.println(frameList.get(i).getAttribute("id"));
		}
		driver.switchTo().frame(frameList.get(frameIndex).getAttribute("id"));
		try {
//			JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
//			String currentFrame = (String) jsExecutor.executeScript("return self.name");

			assertTrue(driver.findElement(By.cssSelector(".gvncyc")).isDisplayed(),
					"Google metrics element was not found.");
		} catch (WebDriverException e) {			
			driver.switchTo().defaultContent();		
			Thread.sleep(1000);
			assertTrue(driver.findElement(By.cssSelector(".gvncyc")).isDisplayed(),
					"Google metrics element was not found.");
		}
			assertEquals(driver.findElement(By.cssSelector(".gvncyc")).getAttribute("href"),
				"https://www.google.com/shopping/ratings/account/metrics?q=thegrommet.com&c=US&v=4&hl=en_US");
	}
	static public void switchToFrame(WebDriver driver,String expectedText) throws InterruptedException, InvalidValue {			
		Thread.sleep(5000);
		List<WebElement> frameList = driver.findElements(By.tagName("iframe"));
		String targetFrame = "";
		for (int i = 0; i < frameList.size(); i++) {
			System.out.print("This is the " + i + "st frame id: ");
			System.out.println(frameList.get(i).getAttribute("id"));
			targetFrame = frameList.get(i).getAttribute("id").contains(expectedText) ? frameList.get(i).getAttribute("id"): targetFrame;				
		}
		if(!targetFrame.equals(""))
			driver.switchTo().frame(targetFrame);
		else
			throw new InvalidValue(expectedText);
		try {
//			JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
//			String currentFrame = (String) jsExecutor.executeScript("return self.name");

			assertTrue(driver.findElement(By.cssSelector(".gvncyc")).isDisplayed(),
					"Google metrics element was not found.");
		} catch (WebDriverException e) {			
			driver.switchTo().defaultContent();		
			Thread.sleep(1000);
			assertTrue(driver.findElement(By.cssSelector(".gvncyc")).isDisplayed(),
					"Google metrics element was not found.");
		}
			assertEquals(driver.findElement(By.cssSelector(".gvncyc")).getAttribute("href"),
				"https://www.google.com/shopping/ratings/account/metrics?q=thegrommet.com&c=US&v=4&hl=en_US");
	}
}