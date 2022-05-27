package org.baseclass;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HelperMethods {
	public static WebDriver driver;
	static Logger log = LogManager.getLogger(HelperMethods.class.getName());

	public HelperMethods(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * code to launch different browsers
	 *
	 */

	public static void launchChrome() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	public static void launchFireFox() {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
	}

	public static void launchEdge() {
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();

	}

	public static void windMax() {
		driver.manage().window().maximize();

	}

	public static void closeWindow() {
		driver.close();

	}

	public static void windowRefresh() {
		driver.navigate().refresh();

	}

	public static void loadUrl(String url) {
		driver.get(url);
	}

	public static void keyPress(int code) throws AWTException {
		Robot r = new Robot();
		r.keyPress(code);// KeyEvent.VK_
	}

	public static void keyRelease(int code) throws AWTException {
		Robot r = new Robot();
		r.keyRelease(code);// KeyEvent.VK_
	}

	public void clickOnWebElement(WebElement element) {

		try {
			element.click();
		} catch (Exception ElementClickInterceptedException) {
			log.error(element + " - Unable to click the element");

		}
	}

	/**
	 * Link Helper Methods
	 */

	public void clickLink(String linkText) {
		driver.findElement(By.linkText(linkText)).click();
	}

	public void clickPartialLink(String partialLinkText) {
		driver.findElement(By.partialLinkText(partialLinkText)).click();
	}

	/**
	 * screenshot
	 */
	public static void screenShot(String path) throws IOException {
		TakesScreenshot t = (TakesScreenshot) driver;
		File tem = t.getScreenshotAs(OutputType.FILE);
		File per = new File(path);
		FileUtils.copyFile(tem, per);
	}

	/**
	 * code to handle alert
	 */

	public Alert getAlert() {
		return driver.switchTo().alert();
	}

	public void AcceptAlert() {
		getAlert().accept();
	}

	public void DismissAlert() {
		getAlert().dismiss();
	}

	public String getAlertText() {
		String text = getAlert().getText();
		return text;
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	public void AcceptAlertIfPresent() {
		if (!isAlertPresent())
			AcceptAlert();
		else
			log.info("Alert is not present");
	}

	public void DismissAlertIfPresent() {

		if (!isAlertPresent())
			DismissAlert();

		else
			log.info("Alert is not present");
	}

	/**
	 * code for windows handling
	 */
	public Set<String> getWindowHandlens() {
		return driver.getWindowHandles();
	}

	public void SwitchToWindow(int index) {

		LinkedList<String> windowsId = new LinkedList<String>(getWindowHandlens());

		if (index < 0 || index > windowsId.size())
			throw new IllegalArgumentException("Invalid Index : " + index);

		driver.switchTo().window(windowsId.get(index));
	}

	public void switchToParentWindow() {
		LinkedList<String> windowsId = new LinkedList<String>(getWindowHandlens());
		driver.switchTo().window(windowsId.get(0));
	}

	public void switchToParentWithChildClose() {
		switchToParentWindow();
		LinkedList<String> windowsId = new LinkedList<String>(getWindowHandlens());

		for (int i = 1; i < windowsId.size(); i++) {
			log.info(windowsId.get(i));
			driver.switchTo().window(windowsId.get(i));
			driver.close();
		}

		switchToParentWindow();
	}

	/**
	 * browser helper code
	 * 
	 */
	public static void sendKey(WebElement element, String value) {
		try {

			element.sendKeys(value);
		} catch (Exception e) {
			log.error("Unable to send values to the Locator : " + element + " Value : " + value);
		}

	}

	public static void elementClick(WebElement element) {
		element.click();

	}

	public static void quitWindow() {
		driver.quit();
	}

	public static void printTitle() {
		String t = driver.getTitle();
		System.out.println(t);

	}

	public static void printCurrentUrl() {
		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl);
	}

	public static void printTxt(WebElement element) {
		String text = null;
		try {
			text = element.getText();
		} catch (Exception e) {
			log.error("Unable to get text for the " + element);
		}
		System.out.println(text);

	}

	public static void printAttribute(WebElement e, String name) {
		String attribute = e.getAttribute(name);
		System.out.println(attribute);
	}

	public static void clearAndSendKeys(WebElement element, String value) {
		try {
			elementClick(element);
			element.clear();
			sendKey(element, value);

		} catch (Exception e) {
			log.info("unable to clear and send text to = " + element);
			log.info("Alert is not present");
		}

	}

	/**
	 * Java Script execute method
	 * 
	 */

	public static void javaExeSendKeys(String script, WebElement element) {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript(script, element);// arugments[0].setAttribute('value','text')

	}

	public static void javaExePrint(String script, WebElement element) {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript(script, element);// arugments[0].getAttribute('value')

	}

	public static void javaExeClick(WebElement element) {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript("arguments[0].click()", element);

	}

	public static void javaExeScrollDwn(WebElement element) {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript("arguments[0].scrollIntoView(true)", element);

	}

	public static void javaExeScrollUp(WebElement element) {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript("arguments[0].scrollIntoView(false)", element);

	}

	public static void javaExeHighLight(String script, WebElement element) {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript(script, element);// arugments[0].setAttribute('style','background:yellow;font-size:4upx')
	}

	/**
	 * Wait Helper Methods
	 */
	@SuppressWarnings("deprecation")
	public void waitForElementInvisible(WebElement element, int timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	private static WebDriverWait getWait(int timeOutInSeconds) {

		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(NoSuchFrameException.class);
		return wait;
	}

	public void waitForElementVisible(WebElement element, int timeOutInSeconds) {
		WebDriverWait wait = getWait(timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void waitForElementClickable(WebElement element, int timeOutInSeconds) {
		WebDriverWait wait = getWait(timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void conditionWaitAlert(long seconds) {
		WebDriverWait w = new WebDriverWait(driver, seconds);
		w.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.dismiss();

	}

	/**
	 * Method to move to element
	 */
	public void moveToElement(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	/**
	 * Method to Double click on Webelement
	 */
	public void doubleClickOnElement(WebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).doubleClick().build().perform();
	}

	/**
	 * Method to get the text from webElement
	 */
	public String getAttributeValue(WebElement element, String text) {
		String value = null;
		value = element.getAttribute(text);
		if (value.isEmpty()) {
			value = text;

		}
		return value;
	}

	/**
	 * Method to move to the element and click on it
	 */
	public void moveToElementAndClickOnIt(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).click().build().perform();
	}

	/**
	 * Method to scroll page to particular element
	 */
	public void scrollToElement(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].scrollIntoView();", element);
	}

	public void pressKeyAndEnter(Keys key) {
		Actions actions = new Actions(driver);
		actions.sendKeys(key).build().perform();
		actions.sendKeys(Keys.ENTER).build().perform();

	}

	/**
	 * DropDownHelper helper
	 */

	public void SelectUsingVisibleText(WebElement element, String visibleText) {
		Select select = new Select(element);
		select.selectByVisibleText(visibleText);
	}

	public void SelectUsingValue(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
	}

	public void SelectUsingIndex(WebElement element, int index) {
		Select select = new Select(element);
		select.selectByIndex(index);
	}

	public String getSelectedValue(WebElement element) {
		String value = new Select(element).getFirstSelectedOption().getText();
		return value;
	}

	public static void deSelectIndex(WebElement element, int i) {
		Select s = new Select(element);
		s.deselectByIndex(i);
	}

	public static void deSelect(WebElement element) {
		Select s = new Select(element);
		s.deselectAll();
	}

	public static void dropDownSize(WebElement element) {
		Select s = new Select(element);
		List<WebElement> options = s.getOptions();
		System.out.println(options.size());
	}

	public static void dropDownGetAll(WebElement element) {
		Select s = new Select(element);
		List<WebElement> allOptions = s.getAllSelectedOptions();
		System.out.println(allOptions);
	}

	public static void dropDownGetFirst(WebElement element) {
		Select s = new Select(element);
		WebElement firstOption = s.getFirstSelectedOption();
		System.out.println(firstOption);
	}

	/**
	 * method to get data from excel sheet
	 */

	public static String getDataFromExcelSheet(int rowNumber, int cellNumber, String path, String sheetName)
			throws IOException {
		File f = new File(path);
		FileInputStream fin = new FileInputStream(f);
		Workbook w = new XSSFWorkbook(fin);
		Sheet s = w.getSheet(sheetName);
		Row row = s.getRow(rowNumber);
		Cell cell = row.getCell(cellNumber);
		int cellType = cell.getCellType();
		String value = "";
		if (cellType == 1) {
			value = cell.getStringCellValue();
		} else if (cellType == 0) {
			if (DateUtil.isCellDateFormatted(cell)) {
				Date d = cell.getDateCellValue();
				SimpleDateFormat sim = new SimpleDateFormat("dd/MM//YYYY");
				value = sim.format(d);
			} else {
				double d = cell.getNumericCellValue();
				long l = (long) d;
				value = String.valueOf(l);

			}
		}
		return value;

	}

	/**
	 * methods in webtable
	 * 
	 */
	public static void findrow(WebElement table, int a) {
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for (int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(a);
		}
	}

	public static void findHeading(WebElement row, int a) {
		List<WebElement> headings = row.findElements(By.tagName("tr"));
		for (int i = 0; i < headings.size(); i++) {
			WebElement heading = headings.get(a);
		}
	}

	public static void findData(WebElement row, int a) {
		List<WebElement> datas = row.findElements(By.tagName("td"));
		for (int j = 0; j < datas.size(); j++) {
			WebElement data = datas.get(a);
		}
	}

	public static void getParticularHeading(WebElement table, int a, int h) {
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for (int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(a);
			List<WebElement> headings = row.findElements(By.tagName("tr"));
			for (int j = 0; j < headings.size(); j++) {
				WebElement heading = headings.get(h);
			}
		}
	}

	public static void getParticularData(WebElement table, int a, int d) {
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for (int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(a);
			List<WebElement> datas = row.findElements(By.tagName("td"));
			for (int j = 0; j < datas.size(); j++) {
				WebElement data = datas.get(d);

			}
		}
	}

}
