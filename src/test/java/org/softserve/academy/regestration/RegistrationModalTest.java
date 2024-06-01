package org.softserve.academy.regestration;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Registration Modal Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistrationModalTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";
    private static final String USER_ICON_CSS_SELECTOR = "svg[data-icon='user']";
    private static final String DROPDOWN_MENU_CLASS_NAME = "ant-dropdown-menu";
    private static final String REGISTER_MENU_ITEM_XPATH = "//li[@role='menuitem']//div[text()='Зареєструватися']";
    private static final String MODAL_CONTENT_CLASS_NAME = "ant-modal-content";
    private static final String CLOSE_BUTTON_CLASS_NAME = "ant-modal-close";
    private static final String MODAL_HEADER_CLASS_NAME = "registration-header";
    private static final String LAST_NAME_INPUT_ID = "lastName";
    private static final String FIRST_NAME_INPUT_ID = "firstName";
    private static final String PHONE_INPUT_ID = "phone";
    private static final String EMAIL_INPUT_ID = "email";
    private static final String PASSWORD_INPUT_ID = "password";
    private static final String CONFIRM_INPUT_ID = "confirm";
    private static final String REGISTER_BUTTON_CSS_SELECTOR = ".registration-button";

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
    @DisplayName("2. Test clicking on 'Register' menu item")
    public void testClickRegisterMenuItem() {
        openModalWindow();
    }

    @Test
    @Order(3)
    @DisplayName("3. Test modal content visibility")
    public void testModalContentVisibility() {
        openModalWindow();

        WebElement modalContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MODAL_CONTENT_CLASS_NAME)));
        assertTrue(modalContent.isDisplayed(), "Modal content should be visible");
    }

    @Test
    @Order(4)
    @DisplayName("4. Test modal close button presence and functionality")
    public void testCloseButton() {
        openModalWindow();

        WebElement closeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(CLOSE_BUTTON_CLASS_NAME)));
        assertNotNull(closeButton);
        clickElementWithJS(closeButton);

        assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(MODAL_CONTENT_CLASS_NAME))), "Modal should be closed");
    }

    @Test
    @Order(5)
    @DisplayName("5. Test modal header text")
    public void testModalHeader() {
        openModalWindow();

        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MODAL_HEADER_CLASS_NAME)));
        assertNotNull(header);
        assertEquals("Реєстрація", header.getText());
    }

    @Test
    @Order(6)
    @DisplayName("6. Test form fields presence and placeholders")
    public void testFormFieldsPresence() {
        openModalWindow();

        assertEquals("Введіть ваше прізвище", driver.findElement(By.id(LAST_NAME_INPUT_ID)).getAttribute("placeholder"));
        assertEquals("Введіть ваше ім`я", driver.findElement(By.id(FIRST_NAME_INPUT_ID)).getAttribute("placeholder"));
        assertEquals("__________", driver.findElement(By.id(PHONE_INPUT_ID)).getAttribute("placeholder"));
        assertEquals("Введіть ваш емейл", driver.findElement(By.id(EMAIL_INPUT_ID)).getAttribute("placeholder"));
        assertEquals("Введіть ваш пароль", driver.findElement(By.id(PASSWORD_INPUT_ID)).getAttribute("placeholder"));
        assertEquals("Підтвердіть ваш пароль", driver.findElement(By.id(CONFIRM_INPUT_ID)).getAttribute("placeholder"));
    }

    @Test
    @Order(7)
    @DisplayName("7. Test filling registration form fields")
    public void testFormFields() {
        openModalWindow();

        WebElement lastNameInput = fillAndAssertField(LAST_NAME_INPUT_ID, "TestLastName");
        WebElement firstNameInput = fillAndAssertField(FIRST_NAME_INPUT_ID, "TestFirstName");
        WebElement phoneInput = fillAndAssertField(PHONE_INPUT_ID, "0123456789");
        WebElement emailInput = fillAndAssertField(EMAIL_INPUT_ID, "test@example.com");
        WebElement passwordInput = fillAndAssertField(PASSWORD_INPUT_ID, "TestPassword123!");
        WebElement confirmInput = fillAndAssertField(CONFIRM_INPUT_ID, "TestPassword123!");

        assertEquals("TestLastName", lastNameInput.getAttribute("value"));
        assertEquals("TestFirstName", firstNameInput.getAttribute("value"));
        assertEquals("0123456789", phoneInput.getAttribute("value"));
        assertEquals("test@example.com", emailInput.getAttribute("value"));
        assertEquals("TestPassword123!", passwordInput.getAttribute("value"));
        assertEquals("TestPassword123!", confirmInput.getAttribute("value"));
    }

    @Test
    @Order(8)
    @DisplayName("8. Test register button activation after filling form")
    public void testRegisterButton() {
        openModalWindow();

        WebElement registerButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(REGISTER_BUTTON_CSS_SELECTOR)));
        assertFalse(registerButton.getAttribute("class").contains("ant-btn-disabled"), "Register button should be disabled initially");

        fillAndAssertField(LAST_NAME_INPUT_ID, "TestLastName");
        fillAndAssertField(FIRST_NAME_INPUT_ID, "TestFirstName");
        fillAndAssertField(PHONE_INPUT_ID, "0123456789");
        fillAndAssertField(EMAIL_INPUT_ID, "test@example.com");
        fillAndAssertField(PASSWORD_INPUT_ID, "TestPassword123!");
        fillAndAssertField(CONFIRM_INPUT_ID, "TestPassword123!");

        registerButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(REGISTER_BUTTON_CSS_SELECTOR)));
        assertFalse(registerButton.getAttribute("class").contains("ant-btn-disabled"), "Register button should be enabled after filling all fields");
    }

    @Test
    @Order(9)
    @DisplayName("9. Test successful registration")
    public void testSuccessfulRegistration() {
        openModalWindow();

        fillAndAssertField(LAST_NAME_INPUT_ID, "TestLastName");
        fillAndAssertField(FIRST_NAME_INPUT_ID, "TestFirstName");
        fillAndAssertField(PHONE_INPUT_ID, "0123456789");
        fillAndAssertField(EMAIL_INPUT_ID, "test" + System.currentTimeMillis() + "@example.com");
        fillAndAssertField(PASSWORD_INPUT_ID, "TestPassword123!");
        fillAndAssertField(CONFIRM_INPUT_ID, "TestPassword123!");

        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(REGISTER_BUTTON_CSS_SELECTOR)));
        clickElementWithJS(registerButton);

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ant-message-success")));
        assertTrue(successMessage.isDisplayed(), "Success message should be displayed");
    }

    private WebElement fillAndAssertField(String fieldId, String value) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(fieldId)));
        assertNotNull(field, "Field with ID '" + fieldId + "' should be present");
        field.sendKeys(value);
        return field;
    }

    private void openModalWindow() {
        WebElement userIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(USER_ICON_CSS_SELECTOR)));
        scrollToElement(userIcon);
        clickElementWithJS(userIcon);

        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(DROPDOWN_MENU_CLASS_NAME)));
        assertNotNull(dropdownMenu);

        WebElement registerMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(REGISTER_MENU_ITEM_XPATH)));
        scrollToElement(registerMenuItem);
        clickElementWithJS(registerMenuItem);
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