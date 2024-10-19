package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BasePage {

    //WebDriver driver;

    /* Web Browser */
    public void openPageUrl(WebDriver driver, String pageUrl) {
        driver.get(pageUrl);
    }

    public String getPageTitle(WebDriver driver) {
       return driver.getTitle();
    }

    public String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    public void backToPage(WebDriver driver) {
         driver.navigate().back();
    }

    public void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public Alert waitForAlertPresence(WebDriver driver) {
        return  new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.alertIsPresent());
    }

    public void acceptToAlert(WebDriver driver) {
        waitForAlertPresence(driver).accept();
        //driver.switchTo().alert().accept();
    }

    public void cancelToAlert(WebDriver driver) {
        waitForAlertPresence(driver).dismiss();
    }

    public String getTextInAlert(WebDriver driver) {
        return waitForAlertPresence(driver).getText();
    }

    public void sendkeyToAlert(WebDriver driver, String keysToSend) {
        waitForAlertPresence(driver).sendKeys(keysToSend);
    }


    public void switchToWindowByID(WebDriver driver, String expectedID) {
        Set<String> allIDs=  driver.getWindowHandles();
        for (String id: allIDs) {
            if (!id.equals(expectedID)) {
                driver.switchTo().window(id);
                break;
            }
        }
    }

    public void switchToWindowByTitle(WebDriver driver, String expectedTitle) {

        Set<String> allIDs=  driver.getWindowHandles();

        for (String id: allIDs) {
            driver.switchTo().window(id);
            sleepInSeconds(2);
            String actualTitleTab = driver.getTitle();
            if (actualTitleTab.equals(expectedTitle)) {
                break;
            }
        }
    }

    public  void closeAllWindowWithoutParent(WebDriver driver, String parentID) {
        Set<String> allIDs=  driver.getWindowHandles();
        for (String id: allIDs) {
            if (!id.equals(parentID)) {
                driver.switchTo().window(id);
                driver.close();
            }
        }
        driver.switchTo().window(parentID);
    }

    public void sleepInSeconds(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Cookie> getBrowserCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }
    public void setCookies(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
    }

    public void deleteAllCookies(WebDriver driver){
            driver.manage().deleteAllCookies();
    }

    /* Web Element */
    public By getByXpath(String locator) {
        return By.xpath(locator);
    }

    public WebElement getWebElement(WebDriver driver, String locator){
        return  driver.findElement(getByXpath(locator));

    }

    public List<WebElement> getListWebElement(WebDriver driver, String locator){
        return  driver.findElements(getByXpath(locator));

    }

    public void clickToElement(WebDriver driver, String locator) {
        getWebElement(driver,locator).click();
    }

     public void sendKeyToElement(WebDriver driver, String locator, String valueToSend) {
         getWebElement(driver,locator).clear();
         getWebElement(driver,locator).sendKeys(valueToSend);
     }

     public String getElementText(WebDriver driver, String locator) {
        return getWebElement(driver,locator).getText();
     }

     public void selectItemInDefaultDropdown(WebDriver driver, String locator, String itemValue){
         new Select(getWebElement(driver,locator)).selectByVisibleText(itemValue);

     }

     public String  getFirstSelectedTextInDefaultDropdown(WebDriver driver, String locator){
       return new Select(getWebElement(driver,locator)).getFirstSelectedOption().getText();

     }

     public boolean isDefaultDropdownMultiple(WebDriver driver, String locator){
         return new Select(getWebElement(driver,locator)).isMultiple();
     }

    public void selectItemInDropdown(WebDriver driver,String parentLocator, String childLocator, String itemTextExpected) {
        getWebElement(driver, parentLocator).click(); //"span#number-button"
        sleepInSeconds(1);
        List<WebElement> allItems = new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.
                presenceOfAllElementsLocatedBy(getByXpath(childLocator)));
        for (WebElement item : allItems) {
            if (item.getText().equals(itemTextExpected)) {
                item.click();
                break;
            }
        }
    }

    public String getElementAttribute(WebDriver driver, String locator, String attributeName) {
       return getWebElement(driver, locator).getAttribute(attributeName);
    }

    public String getElementCssValue(WebDriver driver, String locator, String cssPropertyName){
        return getWebElement(driver, locator).getCssValue(cssPropertyName);
    }

    public String convertRGBAToHexaColor(WebDriver driver, String locator){
        return  Color.fromString(getElementCssValue(driver, locator, "background-color")).asHex();
    }

    public int getListElementSize(WebDriver driver, String locator){
        return getListWebElement(driver, locator).size();
    }

    public void checkToElement(WebDriver driver, String locator){
        if (!getWebElement(driver, locator).isSelected()){
            getWebElement(driver, locator).click();
        }
    }
}
