package autoFramework;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.NoSuchElementException;


import org.omg.CORBA.DynAnyPackage.InvalidValue;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.FrameHandling;

@Listeners()
public class FirstTest extends BaseClass {

	@FindBy(xpath = "//a[contains(.,'Birthday Gifts for Her')]")
	private WebElement giftsForHer;
	@FindBy(xpath = "//a[contains(.,'Birthday Gifts for Him')]")
	private WebElement giftsForHim;
	@FindBy(xpath = "//a[contains(.,'Birthday Gifts for Girls')]")
	private WebElement giftsForGirls;
	@FindBy(xpath = "//a[contains(.,'Birthday Gifts for Boys')]")
	private WebElement giftsForBoys;
	@FindBy(xpath = "//a[contains(.,'21st Birthday Gifts')]")
	private WebElement birthday21;

	/**
	 * Open main page, check the gift options, click on "Birthday gifts for her"
	 * then register with an email
	 * 
	 * @throws InterruptedException
	 */
	@Test(groups = { "hasFollowUp", "hasStartUp" }, enabled = true)
	private void clickNew() throws InterruptedException {
		assertTrue(giftsForHim.isDisplayed());
		assertTrue(giftsForGirls.isDisplayed());
		assertTrue(giftsForBoys.isDisplayed());
		assertTrue(birthday21.isDisplayed());

		while (!giftsForHer.isEnabled())
			Thread.sleep(1000);
		giftsForHer.click();
		assertEquals(driver.findElement(By.xpath("//h1[contains(.,'Birthday Gifts For Her')]")).getText(),
				"Birthday Gifts For Her");

		WebElement email = driver.findElement(By.xpath("//input[@type='email']"));
		email.click();
		email.sendKeys("grommet7@mailinator.com");
		Thread.sleep(1000);

		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.ENTER);
		builder.perform();
		System.out.println("Shush");
	}

	/**
	 * Check footer bar then evaluate the confirmation message for the previous
	 * email sign-in
	 * 
	 * @throws InterruptedException
	 * @throws InvalidValue
	 */
	@Test(dependsOnMethods = "clickNew", groups = { "noStartUp", "noFollowUp" }, enabled = true)
	private void checkConfirmation() throws InterruptedException, InvalidValue {
		SoftAssert sassert = new SoftAssert();
		sassert.assertTrue(driver.findElement(By.xpath("//a[@href='https://www.thegrommet.com/press/press-coverage']"))
				.isDisplayed(), "Press coverage element was not found.");
		sassert.assertTrue(driver.findElement(By.xpath(
				"//a[@href='https://www.bbb.org/boston/business-reviews/online-retailer/the-grommet-in-cambridge-ma-125422']"))
				.isDisplayed(), "Business accreditation element was not found.");

		FrameHandling.switchToFrame(driver, "I0_");
		WebElement googleReview;
		String href;
		try {
			googleReview = driver.findElement(By.cssSelector(".gvncyc"));
			sassert.assertTrue(googleReview.isDisplayed(), "Google metrics element was not found.");
			href = googleReview.getAttribute("href");
			driver.switchTo().defaultContent();
		} catch (WebDriverException e) {
			driver.switchTo().defaultContent();	
			googleReview = driver.findElement(By.cssSelector(".gvncyc"));
			href = googleReview.getAttribute("href");
			sassert.assertTrue(driver.findElement(By.cssSelector(".gvncyc")).isDisplayed(),
					"Google metrics element was not found.");
		}

		sassert.assertTrue(
				(href.equals(
						"https://www.google.com/shopping/ratings/account/metrics?q=thegrommet.com&c=US&v=4&hl=en_US")),
				"Unexpected link for google metrics: " + href);
		driver.switchTo().defaultContent();
		sassert.assertTrue(driver.findElement(By.xpath("/html/body/footer/div/div[5]")).isDisplayed(),
				"Company trademark and address were not found.");
		sassert.assertEquals(driver.findElement(By.xpath("/html/body/footer/div/div[5]")).getText(),
				"The Grommet: The Birthplace of Citizen Commerce® | 87 Elmwood Street, Somerville, MA 02144",
				"Company trademark and address texts were not as expected.");
		sassert.assertTrue(driver.findElement(By.xpath("/html/body/footer/div/div[6]")).isDisplayed(),
				"Copyright text was not found.");
		sassert.assertEquals(driver.findElement(By.xpath("/html/body/footer/div/div[6]")).getText(),
				"The Grommet: The Birthplace of Citizen Commerce® | 87 Elmwood Street, Somerville, MA 02144",
				"Copyright text was not as expected.");
		sassert.assertTrue(driver.findElement(By.xpath("/html/body/footer/div/div[7]")).isDisplayed(),
				"Privacy and terms of use text was not found");
		sassert.assertEquals(driver.findElement(By.xpath("/html/body/footer/div/div[7]")).getText(),
				"Privacy Policy | Terms of Use", "Privacy and terms of use text was not as expected.");

		try {
			sassert.assertTrue(driver.findElement(By.xpath("//h1[contains(.,'Thank you for signing up!')]")).getText()
					.contains("Thank you for signing up!"), "You are not welcome");
		} catch (org.openqa.selenium.NoSuchElementException | NoSuchElementException e) {
			sassert.fail("The confirmation message was not displayed");
		}
		sassert.assertAll();
	}

}
