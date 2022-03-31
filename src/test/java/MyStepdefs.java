import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class MyStepdefs{

    private WebDriver driver;

    @Given("I have started {string}")
    public void iHaveStarted(String browser) {
        DriveCreator creator = new DriveCreator();
        driver = creator.createBrowser(browser);
        driver.get("https://login.mailchimp.com/signup/");
        WebElement button = driver.findElement(By.id("onetrust-accept-btn-handler"));
        button.click();

    }

    @And("I enter my {string}")
    public void iEnterMyEmail(String email) {
        if (email.equals("hannahM")) {
            randomEmail(driver, By.id("email"), email);
        }else{
            sendKeys(driver, By.id("email"), email);
        }
    }

    @And("I enter a username {string}")
    public void iEnterAUsername(String username) {
        if (username.equals("Hannahbanana")){
            sendKeys(driver, By.id("new_username"), username);
        }else if(username.equals("Hannahlong")){
            longUsername(driver, By.id("new_username"), username);
        }else{
            randomUsername(driver, By.id("new_username"), username);
        }
    }

    @And("I enter a {string}")
    public void iEnterAPassword(String password) {
        sendKeys(driver, By.id("new_password"), password);
    }

    @When("I click on SignUp button")
    public void iClickOnSignUpButton() {
        WebElement signUp = driver.findElement(By.id("create-account"));
        signUp.click();
    }

    @Then("I get registration successful")
    public void iGetRegistrationSuccessful() {

        String actual = driver.getTitle();
        String expected = "Success | Mailchimp";

        assertEquals(actual, expected);
        driver.quit();
    }

    @Then("I get registration taken")
    public void iGetRegistrationTaken() {

        String actual = driver.findElement(By.cssSelector(".invalid-error")).getText();
        String expected = "Another user with this username already exists. Maybe it's your evil twin. Spooky.";

        assertEquals(actual, expected);
        driver.quit();
    }

    @Then("I get registration email missing")
    public void iGetRegistrationEmailMissing() {

        String actual = driver.findElement(By.cssSelector(".invalid-error")).getText();
        String expected = "Please enter a value";
        assertEquals(actual, expected);
        driver.quit();
    }

    @Then("I get registration username to long")
    public void iGetRegistrationUsernameToLong() {

        String actual = driver.findElement(By.cssSelector(".invalid-error")).getText();
        String expected = "Enter a value less than 100 characters long";
        assertEquals(actual, expected);
        driver.quit();
    }

    private static void sendKeys (WebDriver driver, By by, String text) {
        WebDriverWait slow = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement email = slow.until(ExpectedConditions.presenceOfElementLocated(by));
        email.sendKeys(text);
    }

    private static void randomEmail (WebDriver driver, By by, String name){
        WebDriverWait slow = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = slow.until(ExpectedConditions.presenceOfElementLocated(by));
        Random random = new Random();
        int randomNumber = random.nextInt(100000);
        element.sendKeys(name+randomNumber +"@gmail.com");
    }

    private static void randomUsername (WebDriver driver, By by, String name){
        WebDriverWait slow = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = slow.until(ExpectedConditions.presenceOfElementLocated(by));
        Random random = new Random();
        int randomNumber = random.nextInt(100000);
        element.sendKeys(name+randomNumber);
    }

    private static void longUsername (WebDriver driver, By by, String username) {
        WebDriverWait slow = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = slow.until(ExpectedConditions.presenceOfElementLocated(by));
        Random random = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String randomString ="";
        for (int i = 0; i < 100; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            randomString+=randomChar;
        }
        element.sendKeys(username + randomString);

    }
}
