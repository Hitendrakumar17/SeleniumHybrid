package commonLibs;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonDriver {

	private WebDriver driver;
	private long lngPageLoadTimeOut;
	private long lngElementDetectionTimeOut;
	String firstChildWindow;

	public CommonDriver() {
		lngPageLoadTimeOut = 60L;
		lngElementDetectionTimeOut = 30L;
	}

	public void setPageLoadTimeOut(long lngPageLoadTimeOut) {
		this.lngPageLoadTimeOut = lngPageLoadTimeOut;
	}

	public void setElementDetectionTimeOut(long lngElementDetectionTimeOut) {
		this.lngElementDetectionTimeOut = lngElementDetectionTimeOut;
	}

	public void openBrowser(String browserType, String url) {
		try {

			switch (getBrowserTypeIndexed(browserType)) {
			case 1:
				System.setProperty("webdriver.gecko.driver",
						"C:\\bin\\geckodriver.exe");
				driver = new FirefoxDriver();
				break;
			case 2:

				System.setProperty("webdriver.ie.driver",
						"C:\\bin\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				break;

			case 3:
				System.setProperty("webdriver.chrome.driver",
						"C:\\bin\\chromedriver.exe");
				driver = new ChromeDriver();
				break;
			default:
				throw new Exception("Unknow Browser Type = " + browserType);

			}

			if (url.isEmpty()) {

				url = "about:blank";
			}

			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();

			driver.manage().timeouts()
					.pageLoadTimeout(lngPageLoadTimeOut, TimeUnit.SECONDS);

			driver.manage()
					.timeouts()
					.implicitlyWait(lngElementDetectionTimeOut,
							TimeUnit.SECONDS);

			driver.get(url);

			Thread.sleep(2000);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// -----------------------------------------------------------------

	public void closeBrowser() {
		try {
			if (driver != null) {
				driver.quit();
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// ------------------------------------------------------------------

	public WebDriver getDriver() {

		return driver;
	}

	// ------------------------------------------------------------------
	private int getBrowserTypeIndexed(String browserType) {
		browserType = browserType.toLowerCase().trim();

		if (browserType.isEmpty()) {
			return -1;
		}

		if (browserType.equals("ff") || browserType.equals("firefox")
				|| browserType.equals("mozilla")
				|| browserType.equals("mozilla firefox")) {
			return 1;
		}

		if (browserType.equals("ie") || browserType.equals("explorer")
				|| browserType.equals("internet explorer")) {
			return 2;
		}

		if (browserType.equals("chrome") || browserType.equals("google")
				|| browserType.equals("google chrome")) {
			return 3;
		}

		return -1;
	}

	// ---------------------------------------------------------------------

	public void waitTillElementIsVisible(By by, long timeoutSeconds) {
		try {

			WebDriverWait oWait = new WebDriverWait(driver, timeoutSeconds);

			oWait.until(ExpectedConditions.visibilityOfElementLocated(by));

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// ---------------------------------------------------------------------

	public void waitTillElementIsClickable(By by, long timeoutSeconds) {
		try {

			WebDriverWait oWait = new WebDriverWait(driver, timeoutSeconds);

			oWait.until(ExpectedConditions.elementToBeClickable(by));

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// -------------------------------------------------------------------

	public String savePageSnapshot(String imagePath) {
		try {

			TakesScreenshot oCamera;
			File oTmpFile, oImageFile;
			oImageFile = new File(imagePath);

			if (new File(imagePath).exists()) {
				throw new Exception("Image File already Exists");
			}

			oCamera = (TakesScreenshot) driver;
			oTmpFile = oCamera.getScreenshotAs(OutputType.FILE);
			oCamera.getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(oTmpFile, oImageFile);

			return "File got saved";

		} catch (Throwable t) {
			t.printStackTrace();
			return "File already exists";
		}
	}

	// ---------------------------------------------------------------------

	public void setText(By by, String text) {
		try {

			driver.findElement(by).sendKeys(text);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// ----------------------------------------------------------------------

	public String getText(By by) {
		try {

			return driver.findElement(by).getText();

		} catch (Throwable t) {
			t.printStackTrace();
			return "";
		}
	}

	// ---------------------------------------
	public void clickElement(By by) {
		try {

			driver.findElement(by).click();

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void selectItemInListBox(By by, String itemVisibleText) {
		try {

			Select oListBox;

			oListBox = new Select(driver.findElement(by));

			oListBox.selectByVisibleText(itemVisibleText);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void switchToWindow(int x) {
		try {
			firstChildWindow = driver.getWindowHandles().toArray()[x]
					.toString();
			driver.switchTo().window(firstChildWindow);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void scrollTo(By by) {
		JavascriptExecutor jse;
		jse = (JavascriptExecutor) driver;
		int xPoint = driver.findElement(by).getLocation().x;
		int yPoint = driver.findElement(by).getLocation().y;
		String command = String.format("window.scrollTo(%d, %d)", xPoint,
				yPoint);
		jse.executeScript(command);
	}

}
