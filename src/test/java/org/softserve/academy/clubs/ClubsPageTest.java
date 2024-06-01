package org.softserve.academy.clubs;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Clubs Page Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClubsPageTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL = "http:speak-ukrainian.eastus2.cloudapp.azure.com/dev/";
    private static final String CLUBS_MENU_ITEM_XPATH = "span[@class='ant-menu-title-content']a[@href='/dev/clubs']";
    private static final String SECOND_PAGE_LINK_XPATH = "a[@rel='nofollow' and text()='2']";
    //Other constants

    @BeforeAll
    public void setUpAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get(BASE_URL);
    }

    @BeforeEach
    public void setUpEach() {
        driver.get(BASE_URL);
    }

    @AfterEach
    public void tearDownEach() {
        driver.manage().deleteAllCookies();
    }

    @Test
    @DisplayName("Test finding and clicking on 'Clubs' menu item")
    public void testClickClubsMenuItem() {
        WebElement clubsMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CLUBS_MENU_ITEM_XPATH)));
        scrollToElement(clubsMenuItem);
        clickElementWithJS(clubsMenuItem);

        String expectedUrl = BASE_URL + "clubs";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertEquals(expectedUrl, driver.getCurrentUrl(), "URL should be the clubs page URL");
    }

    @Test
    @DisplayName("Test navigating to second page")
    public void testNavigateToSecondPage() {
        testClickClubsMenuItem();

        WebElement secondPageLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SECOND_PAGE_LINK_XPATH)));
        scrollToElement(secondPageLink);
        clickElementWithJS(secondPageLink);

        WebElement currentPageIndicator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ant-pagination-item-active > a")));
        assertEquals("2", currentPageIndicator.getText(), "Current page should be 2");
    }

    @Test
    @DisplayName("Test clicking on club card and navigating to details page")
    public void testClickClubCard() {
        //TODO
    }

    @Test
    @DisplayName("Test finding and clicking 'Leave a Comment' button")
    public void testClickLeaveCommentButton() {
       //TODO
    }

    @Test
    @DisplayName("Test writing a comment and submitting")
    public void testWriteCommentAndSubmit() {
        //TODO
    }
//Other tests
    private void clickElementWithJS(WebElement element) {
        if (element != null && element.isDisplayed() && element.isEnabled()) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));", element);
        } else {
            throw new IllegalArgumentException("Element is not clickable: " + element);
        }
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @AfterAll
    public void tearDownAll() {
        if (driver != null) {
            driver.quit();
        }
    }
}


