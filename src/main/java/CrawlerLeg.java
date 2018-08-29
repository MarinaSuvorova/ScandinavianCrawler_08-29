import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.sql.SQLException;

public class CrawlerLeg {
    private int iterator = 0;
    private WebDriver driver;
    FlightInfoDB flightInfoDB;
    private String goodHandle;

    public void setDriver() {
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        this.driver = new FirefoxDriver();
    }

    public void loadPage(String url) {
        driver.get(url);
        hideMarketSelector();

    }

    private void hideMarketSelector() {
        driver.findElement(By.cssSelector(".closeMarketSelectorLeft")).click();
    }

    public void setFlightDirections(String depAirport, String arrAirport) {
        setDepartureAiport(depAirport);
        setArrivalAirport(arrAirport);
    }

    private void setDepartureAiport(String depAirport) {
        driver.findElement(By.id("ctl00_FullRegion_MainRegion_ContentRegion_ContentFullRegion_ContentLeftRegion_CEPGroup1_CEPActive_cepNDPRevBookingArea_predictiveSearch_txtFrom")).clear();
        driver.findElement(By.id("ctl00_FullRegion_MainRegion_ContentRegion_ContentFullRegion_ContentLeftRegion_CEPGroup1_CEPActive_cepNDPRevBookingArea_predictiveSearch_txtFrom")).sendKeys(depAirport);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (driver.findElements(By.id(depAirport)).size() != 0) {
            driver.findElement(By.id(depAirport)).click();
        } else {
            setDepartureAiport(depAirport);
        }
    }

    private void setArrivalAirport(String arrAirport) {
        driver.findElement(By.id("ctl00_FullRegion_MainRegion_ContentRegion_ContentFullRegion_ContentLeftRegion_CEPGroup1_CEPActive_cepNDPRevBookingArea_predictiveSearch_txtTo")).clear();
        driver.findElement(By.id("ctl00_FullRegion_MainRegion_ContentRegion_ContentFullRegion_ContentLeftRegion_CEPGroup1_CEPActive_cepNDPRevBookingArea_predictiveSearch_txtTo")).sendKeys(arrAirport);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (driver.findElements(By.id(arrAirport)).size() != 0) {
            driver.findElement(By.id(arrAirport)).click();
        } else {
            setArrivalAirport(arrAirport);
        }
    }

    public void setTripDates() throws InterruptedException {
        driver.findElement(By.id("lpcCheckbox")).click();
        driver.findElement(By.id("ctl00_FullRegion_MainRegion_ContentRegion_ContentFullRegion_ContentLeftRegion_CEPGroup1_CEPActive_cepNDPRevBookingArea_Searchbtn_ButtonLink")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setOutboundDate();
        setReturnDate();
    }

    private void setOutboundDate() throws InterruptedException {
        String monthCssSelector = "#outboundContainer > div:nth-child(1) > div:nth-child(2) > span:nth-child(2)";
        String dayCssSelector = "#outboundContainer > div:nth-child(3) > ol:nth-child(2) > li:nth-child(8)";
        if (driver.findElements(By.cssSelector(monthCssSelector)).size() != 0) {
            driver.findElement(By.cssSelector(monthCssSelector)).click();
            waitForCalendarPlaceholderDisplayed();
            if (driver.findElements(By.cssSelector(dayCssSelector)).size() != 0) {
                driver.findElement(By.cssSelector(dayCssSelector)).click();
            }
        } else {
            waitForCalendarPlaceholderDisplayed();
            setOutboundDate();
        }
    }

    private void setReturnDate() throws InterruptedException {
        String dayCssSelector = "#inboundContainer > div:nth-child(3) > ol:nth-child(2) > li:nth-child(14)";
        if (driver.findElements(By.cssSelector(dayCssSelector)).size() != 0) {
            driver.findElement(By.cssSelector(dayCssSelector)).click();
        } else {
            waitForCalendarPlaceholderDisplayed();
            setReturnDate();
        }

    }

    private void waitForCalendarPlaceholderDisplayed() throws InterruptedException {
        if (driver.findElement(By.id("calendarPlaceHolder")).isDisplayed()) {
            return;
        } else {
            Thread.sleep(1000);
            waitForCalendarPlaceholderDisplayed();
        }
    }

    public void startBooking() {
        driver.findElement(By.cssSelector(".actionButton")).click();
    }

    public void startFareDataCollection() throws SQLException {
        flightInfoDB = new FlightInfoDB();
        flightInfoDB.createFareDataTable();
    }

    public void testSearchButton() throws InterruptedException {
        waitForSearchPageToLoad();
//    if(driver.findElements(By.cssSelector("#reco_0_17_PREMB")).size()!=0){
//        System.out.println("element exists");
//    if(driver.findElement(By.cssSelector("#reco_0_17_PREMB")).isDisplayed()){
//        System.out.println("element is displayed");
//        driver.findElement(By.cssSelector("#reco_0_17_PREMB")).click();}
//        WebElement element = driver.findElement(By.cssSelector("#toggleId_0_17 > table:nth-child(1) > tbody:nth-child(2) > tr:nth-child(2) > td:nth-child(2) > span:nth-child(3) > span:nth-child(1) > span:nth-child(1)"));
//        if(element.isDisplayed()){
//            System.out.println(element.getText());
//        }
//    }


    }

    private void waitForSearchPageToLoad() throws InterruptedException {
        if (driver.findElements(By.cssSelector("#pricePanel > div:nth-child(2) > p:nth-child(1)")).size() != 0) {
            System.out.println("success!!!");
            driver.get("https://book.flysas.com/pl/SASC/wds/Override.action?SO_SITE_EXT_PSPURL=https://classic.sas.dk/SASCredits/SASCreditsPaymentMaster.aspx&SO_SITE_TP_TPC_POST_EOT_WT=50000&SO_SITE_USE_ACK_URL_SERVICE=TRUE&WDS_URL_JSON_POINTS=ebwsprod.flysas.com%2FEAJI%2FEAJIService.aspx&SO_SITE_EBMS_API_SERVERURL=%20https%3A%2F%2F1aebwsprod.flysas.com%2FEBMSPointsInternal%2FEBMSPoints.asmx&WDS_SERVICING_FLOW_TE_SEATMAP=TRUE&WDS_SERVICING_FLOW_TE_XBAG=TRUE&WDS_SERVICING_FLOW_TE_MEAL=TRUE");
        } else {
            Thread.sleep(5000);
            waitForSearchPageToLoad();

        }
    }
}

