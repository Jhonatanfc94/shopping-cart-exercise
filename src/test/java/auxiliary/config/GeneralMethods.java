package auxiliary.config;

import auxiliary.Data;
import auxiliary.tools.SendEmail;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GeneralMethods {
    public String acceptAlert(WebDriver driver){
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }

    public void checkUncheck(WebElement webElement, WebDriver driver, WebDriverWait wait, boolean checkUncheck){
        if(isCheck(webElement, wait)){
            if(!checkUncheck){
                click(webElement, driver, wait);
            }
        }else{
            if(checkUncheck){
                click(webElement, driver, wait);
            }
        }
    }

    public void click(WebElement webElement, WebDriver driver, WebDriverWait wait){
        try{
            goToElement(webElement, driver);
            highlight(webElement, driver);
            wait.until(ExpectedConditions.visibilityOf(webElement));
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
            webElement.click();
            waitPageLoad(driver);
        }catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    public void clickJSElement(WebElement webElement, WebDriver driver){
        try{
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
            waitPageLoad(driver);
        }catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    public void clickList(List<WebElement> webElementsList, WebDriver driver, WebDriverWait wait){
        for (WebElement columnFields : webElementsList) {
            highlight(columnFields, driver);
            click(columnFields, driver, wait);
        }
    }

    public void clickXpathViaJS(String xpath, WebDriver driver, WebDriverWait wait){
        try{
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(xpath)));
            waitPageLoad(driver);
        }catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    public boolean compareFiles(File file1, File file2) {
        try {
            if (FileUtils.contentEquals(file1, file2)) {
                return true;
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return false;
    }

    public void deleteFile(String fileName) {
        File file = new File(Data.downloads.downloadDirectory + fileName);
        if(!file.delete()){
            System.out.println("No file deleted");
        }
    }

    public void deleteFilesFolderDownload(){
        String pathBase = System.getProperty("user.dir") + "\\downloads\\";
        File folder = new File(pathBase);
        String[] list = folder.list();
        if(list != null){
            for (String s : list) {
                deleteFile(s);
            }
        }
    }

    public List<WebElement> findListXPathElements(String xpath, WebDriver driver, WebDriverWait wait){
        List<WebElement> list = new LinkedList<>();
        try{
            list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
            goToElement(list.get(0), driver);
            for (WebElement columnFields : list) {
                highlight(columnFields, driver);
            }
        }catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return list;
    }

    public WebElement findXPathElement(String xpath, WebDriver driver, WebDriverWait wait){
        WebElement search = null;
        try{
            search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            goToElement(search, driver);
            highlight(search, driver);
        }catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return search;
    }

    public String getAttribute(WebElement webElement, String attribute, WebDriver driver, WebDriverWait wait){
        highlight(webElement, driver);
        wait.until(ExpectedConditions.visibilityOf(webElement));
        return webElement.getAttribute(attribute);
    }

    public String getAttributeNoVisible(WebElement webElement, String attribute, WebDriver driver){
        highlight(webElement, driver);
        return webElement.getAttribute(attribute);
    }

    public String getDownloadedFileContains(String nameContains){
        String pathBase = System.getProperty("user.dir") + "\\downloads\\";
        File folder = new File(pathBase);
        String[] list = folder.list();
        String contains = "";
        if (list == null || list.length == 0) {
            System.out.println("There are no items inside the current folder");
        }
        else {
            for (String s : list) {
                if (s.contains(nameContains)) {
                    contains = s;
                }
            }
        }
        return contains;
    }

    public String getFileContent(String name){
        String fileContent = "";
        String line;
        Path path = Paths.get(Data.downloads.downloadDirectory + "\\" + name);
        try(BufferedReader buffer = Files.newBufferedReader(path)){
            while ((line = buffer.readLine()) != null) {
                fileContent = fileContent.concat(line + "\n");
            }
        }catch(IOException e){
            Assert.fail(e.getMessage());
        }
        return fileContent;
    }

    public String getFirstSelected(WebElement select, WebDriver driver, WebDriverWait wait){
        String selection = "";
        try{
            goToElement(select, driver);
            highlight(select, driver);
            wait.until(ExpectedConditions.visibilityOf(select));
            Select options = new Select(select);
            selection = options.getFirstSelectedOption().getText();
        }catch(Exception e){
            Assert.fail(e.getMessage());
        }
        return selection;
    }

    public List<String> getOptionsSelect(WebElement webElement, WebDriver driver, WebDriverWait wait){
        Select select = new Select(webElement);
        List<String> optionsList = new ArrayList<>();
        try {
            goToElement(webElement, driver);
            wait.until(ExpectedConditions.visibilityOf(webElement));
            highlight(webElement, driver);
            List<WebElement> listOfElementsOfSelect = select.getOptions();
            for (WebElement campos : listOfElementsOfSelect) {
                highlight(campos, driver);
                optionsList.add(campos.getText().trim());
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return optionsList;
    }

    public int getRandomPosition(List<String> list) {
        return ThreadLocalRandom.current().nextInt(list.size());
    }

    public String getText(WebElement webElement, WebDriver driver, WebDriverWait wait){
        try{
            goToElement(webElement, driver);
            highlight(webElement, driver);
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
        }catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return webElement.getText();
    }

    public String getTextNonVisibleElement(WebElement webElement, WebDriver driver){
        try{
            goToElement(webElement, driver);
            highlight(webElement, driver);
        }catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return webElement.getText();
    }

    public void goToElement(WebElement webElement, WebDriver driver){
        try {
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: \"nearest\", inline: \"nearest\"});", webElement);
            Thread.sleep(500);
        }catch (Exception e){}
    }

    public void highlight(WebElement webElement, WebDriver driver){
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid green'", webElement);
        }
    }

    public boolean isCheck( WebElement webElement, WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOf(webElement)).getAttribute("checked")!= null;
    }

    public boolean isVisibleElement(List<WebElement> webElementList, WebDriverWait wait){
        try{
            wait.until(ExpectedConditions.visibilityOfAllElements(webElementList));
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean isVisibleElement(WebElement webElement, WebDriverWait wait){
        try{
            wait.until(ExpectedConditions.visibilityOf(webElement));
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public void loadUrl(WebDriver driver, String url){
        if (url.isEmpty()){
            Assert.fail("Empty URL given");
        }
        try {
            driver.get(url);
            waitPageLoad(driver);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    public void notifyRegression(String messageRegression) {
        String className = "";
        if (Thread.currentThread().getStackTrace().length > 2) {
            className = Thread.currentThread().getStackTrace()[2].getClassName();
        } else {
            className = "undefined";
        }
        String method = "";
        if (Thread.currentThread().getStackTrace().length > 2) {
            method = Thread.currentThread().getStackTrace()[2].getMethodName();
        } else {
            method = "undefined";
        }

        String subjectEmail = "REGRESSION: [" + className + "] --" + (new Date());
        String mailBody = "<strong> Class: </strong>[" + className + "]" + "<br />" +
                "<strong>Method:</strong>. [" + method + "]" + "<br />" +
                "<strong>Failure reason:</strong> " + messageRegression;
        SendEmail.sendEmailWithCapture(subjectEmail, mailBody);
        Assert.fail(messageRegression);
    }

    public void printDoubleList(List<Double> list, String header, String vineta) {
        System.out.println(header);
        for(Double element : list) {
            System.out.println(vineta + element);
        }
    }

    public void printListString(List<String> list, String header, String vineta) {
        System.out.println(header);
        for(String element : list) {
            System.out.println(vineta + element);
        }
    }

    public void removeReadOnlyAttribute(WebElement webElement, WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly','readonly')", webElement);
    }

    public void selectOptionByContainText(WebElement selector, String option, WebDriver driver, WebDriverWait wait) {
        Select select = new Select(selector);
        try {
            goToElement(selector, driver);
            wait.until(ExpectedConditions.visibilityOf(selector));
            highlight(selector, driver);
            List<WebElement> listOfElementsOfSelect = select.getOptions();
            for (WebElement elementOfSelect : listOfElementsOfSelect) {
                String txtOfElement = elementOfSelect.getText().trim();
                if (txtOfElement.toLowerCase().contains(option.trim().toLowerCase())) {
                    select.selectByVisibleText(txtOfElement);
                    break;
                }
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    public void selectOptionByText(WebElement select, String option, WebDriver driver, WebDriverWait wait){
        try{
            goToElement(select, driver);
            highlight(select, driver);
            wait.until(ExpectedConditions.visibilityOf(select));
            Select options = new Select(select);
            options.selectByVisibleText(option);
        }catch(Exception e){
            Assert.fail(e.getMessage());
        }
    }

    public void sendKeys(WebElement webElement, String text, WebDriver driver, WebDriverWait wait){
        try{
            goToElement(webElement, driver);
            highlight(webElement, driver);
            wait.until(ExpectedConditions.visibilityOf(webElement));
            webElement.clear();
            webElement.sendKeys(text);
        }catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    public boolean WaitForFileDownload(String name) {
        boolean flag = false;
        File downloadedFile = new File(Data.downloads.downloadDirectory);
        boolean downloadedFileBoolean = false;
        while(!downloadedFileBoolean){
            File[] dir_contents = downloadedFile.listFiles();
            waitSeconds(5);
            if(dir_contents.length == 2){
                downloadedFileBoolean = true;
            }
        }
        File[] dir_contents = downloadedFile.listFiles();

        for (int i = 0; i < dir_contents.length; i++) {
            if (dir_contents[i].getName().equals(name))
                flag = true;
        }
        return flag;
    }

    public void waitPageLoad(WebDriver driver) {
        WebDriverWait wait;
        ExpectedCondition<Boolean> pageLoadCondition;
        try {
            pageLoadCondition = new
                    ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("interactive");
                        }
                    };
            wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            wait.until(pageLoadCondition);
        }catch (Exception e) {}
        try {
            pageLoadCondition = new
                    ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                        }
                    };
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(pageLoadCondition);
        } catch (Exception e) {}
    }

    public void waitSeconds(int seconds){
        long milliseconds = (long)seconds * 1000;
        try{
            Thread.sleep(milliseconds);
        }catch(Exception e){
            Assert.fail(e.getMessage());
        }
    }

    public boolean xpathExists(String xpath, WebDriverWait wait){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}