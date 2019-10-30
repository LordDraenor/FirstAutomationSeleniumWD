package autoFramework;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Optional;

public class BaseClass {
	public static WebDriver driver;
	public static WebDriverWait wait;

	@BeforeGroups(groups = "hasStartUp")
	public void beforeMeth() {

		FirefoxProfile profile = new FirefoxProfile();
		FirefoxOptions options = new FirefoxOptions();
		options.setProfile(profile);
		profile.setPreference("browser.startup.homepage", "https://www.thegrommet.com/");
		profile.setPreference("dom.webnotifications.enable", "false");
		profile.setPreference("privacy.socialtracking.notification.enabled", "false");
		profile.setPreference("privacy.socialtracking.notification.max", "0");
		profile.setPreference("privacy.trackingprotection.socialtracking.enabled", "false");	
		profile.setPreference("intl.accept_languages", "en");
		
		driver = new FirefoxDriver(options);
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	// Standard
//	@AfterMethod
//	public void afterMeth() {
//		driver.quit();
//	}

	@AfterGroups(groups = "noFollowUp")
	public void afterGrp() {
		driver.quit();

	}

	// Perform some checks and wait for the element to be clickable
	public static void waitAndClick(WebElement element) {
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
	//Just wait a bit for the element to be visible
	public static void simpleWait(WebElement element,@Optional("10") int minDuration) {
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
}
