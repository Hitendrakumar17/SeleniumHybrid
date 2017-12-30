package commonLibs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class KeywordUtility {

	CommonDriver driver;

	public KeywordUtility() {
		driver = new CommonDriver();
	}

	public String performAction(String actionName, By by, String value) {
		actionName = actionName.trim();//.toLowerCase();

		Select select;
		WebElement webElement;

		switch (actionName) {

		case "click":
			driver.clickElement(by);
			return "";

		case "OpenBrowser":
			driver.openBrowser(value, "about:blank");
			return "";

		case "setPageLoadTimeOut":
			driver.setPageLoadTimeOut(Long.valueOf(value));
			return "";

		case "setElementDetectionTimeout":
			driver.setElementDetectionTimeOut(Long.valueOf(value));
			return "";

		case "threadsleep":

			try {
				Thread.sleep(Long.parseLong(value));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "";

		case "navigateToUrl":

			driver.getDriver().get(value);
			return "";

		case "navigateBack":

			driver.getDriver().navigate().back();
			return "";

		case "navigateForward":

			driver.getDriver().navigate().forward();
			return "";

		case "closeAllBrowser":

			driver.getDriver().quit();
			return "";

		case "closeCurrentBrowser":

			driver.getDriver().close();
			return "";
		case "waitTillElementIsVisible":

			driver.waitTillElementIsVisible(by, Long.valueOf(value));
			return "";

		case "waitTillElementIsClickable":

			driver.waitTillElementIsVisible(by, Long.valueOf(value));
			return "";

		case "savepagesnapshot":

			String returnvalue = driver.savePageSnapshot(value);
			return returnvalue;

		case "submit":

			driver.getDriver().findElement(by).submit();
			return "";

		case "clear":

			driver.getDriver().findElement(by).clear();
			return "";

		case "selectParentWindow":

			driver.getDriver()
					.switchTo()
					.window(driver.getDriver().getWindowHandles().toArray()[0]
							.toString());
			return "";

		case "acceptAlert":

			driver.getDriver().switchTo().alert().accept();
			return "";

		case "rejectAlert":

			driver.getDriver().switchTo().alert().dismiss();
			return "";

		case "selectDefaultframe":
			driver.getDriver().switchTo().defaultContent();
			return "";

		case "gettext":
			return driver.getDriver().findElement(by).getText();

		case "getTextboxText":

			if (driver.getDriver().findElement(by).getAttribute("value")
					.equals(value)) {
				return "pass";
			} else {
				return "error";
			}

		case "verifytext":

			if (driver.getDriver().findElement(by).getText().equals(value)) {
				return "pass";
			} else {
				return "error";
			}

		case "getTitle":
			return driver.getDriver().getTitle();

		case "verifyTitle":
			if (driver.getDriver().getTitle().equals(value)) {
				return "pass";
			} else {
				return "error";
			}

		case "getUrl":
			return driver.getDriver().getCurrentUrl();

		case "verifyUrl":
			if (driver.getDriver().getCurrentUrl().equals(value)) {
				return "pass";
			} else {
				return "error";
			}

		case "setText":
			driver.setText(by, value);
			return "";

		case "getstatus":
			return String.valueOf(driver.getDriver().findElement(by)
					.isSelected());

		case "getSelectedItem":

			webElement = driver.getDriver().findElement(by);
			select = new Select(webElement);
			return select.getFirstSelectedOption().getText();

		case "selectitembytext":
			driver.selectItemInListBox(by, value);
			return "";

		case "selectitembyvalue":

			webElement = driver.getDriver().findElement(by);
			select = new Select(webElement);
			select.selectByValue(value);
			return "";

		case "getItemsCount":

			webElement = driver.getDriver().findElement(by);
			select = new Select(webElement);

			return String.valueOf(select.getOptions().size());

		case "isdisplayed":
			if (driver.getDriver().findElement(by).isDisplayed()) {
				return value + " is present";
			} else {
				return value + " is not present";
			}
		case "isenabled":
			driver.getDriver().findElement(by).isEnabled();
			return "";

		case "switchToNewWindow":
			driver.switchToWindow(Integer.parseInt(value));
			return "";

		case "scrollTo":
			driver.scrollTo(by);
			return "";

		default:
			return "Error: Unknown keyword..";
		}

	}
}
