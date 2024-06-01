package org.softserve.academy.login;

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
@DisplayName("Login Modal Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginModalTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";
    private static final String USER_ICON_CSS_SELECTOR = "svg[data-icon='user']";
    private static final String DROPDOWN_MENU_CLASS_NAME = "ant-dropdown-menu";
    private static final String LOGIN_MENU_ITEM_XPATH = "//li[@role='menuitem']//div[text()='Увійти']";
    private static final String LOGIN_HEADER_CLASS_NAME = "login-header";
    private static final String EMAIL_INPUT_ID = "basic_email";
    private static final String PASSWORD_INPUT_ID = "basic_password";
    private static final String LOGIN_BUTTON_CSS_SELECTOR = ".login-button";

    @BeforeAll
    public void setUpAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
    @Order(1)
    @DisplayName("1. Test clicking on user icon")
    public void testClickUserIcon() {
        WebElement userIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(USER_ICON_CSS_SELECTOR)));
        scrollToElement(userIcon);
        clickElementWithJS(userIcon);

        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(DROPDOWN_MENU_CLASS_NAME)));
        assertNotNull(dropdownMenu);
    }

    @Test
    @Order(2)
    @DisplayName("2. Test clicking on 'Login' menu item")
    public void testClickLoginMenuItem() {
        openModalWindow();
    }

    @Test
    @Order(3)
    @DisplayName("3. Test login modal header text")
    public void testLoginModalHeader() {
        //TODO
    }

    @Test
    @Order(4)
    @DisplayName("4. Test login form fields presence and placeholders")
    public void testLoginFormFieldsPresence() {
       //TODO
    }

    @Test
    @Order(5)
    @DisplayName("5. Test filling login form fields")
    public void testLoginFormFields() {
        //TODO
    }

    @Test
    @Order(6)
    @DisplayName("6. Test login button activation after filling form")
    public void testLoginButton() {
       //TODO
    }

    @Test
    @Order(7)
    @DisplayName("7. Test successful login")
    public void testSuccessfulLogin() {
       //TODO
    }

    private WebElement fillAndAssertField(String fieldId, String value) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(fieldId)));
        assertNotNull(field, "Field with ID '" + fieldId + "' should be present");
        field.sendKeys(value);
        return field;
    }

    private void openModalWindow() {
        //TODO
    }

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
