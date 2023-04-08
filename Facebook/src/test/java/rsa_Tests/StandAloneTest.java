package rsa_Tests;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandAloneTest {

	
	public static void main(String[] gs) throws InterruptedException, IOException {
		String product = "iphone";

		System.setProperty("webdriver.gecko.driver", "D:\\Grid\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();

		// System.setProperty("webdriver.chrome.driver","D:\\Grid\\chromedriver.exe");
		// WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();

		driver.get("https://rahulshettyacademy.com/client");
		driver.findElement(By.id("userEmail")).sendKeys("dhoni@yopmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Sachin@123");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.cssSelector("[aria-label='Login Successfully']"))));
		} catch (Exception e) {
			// Handle the exception here
			System.out.println("Exception caught: " + e.getMessage());
		}
		System.out.println("waiting");

		// String LoginSuccess = driver.findElement(By.cssSelector("[aria-label='Login
		// Successfully']")).getText();
		// System.out.println(LoginSuccess);
		// wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//*[@id=\"toast-container\"]/div/div"))));

		// Assert.assertEquals(LoginSuccess, "Login Successfully");

		/*
		 * Actions actions = new Actions(driver);
		 * 
		 * for (int i = 0; i < pics.size(); i++) { if
		 * (pics.get(i).getText().toLowerCase().contains(product)) { WebElement pic =
		 * pics.get(i);
		 * 
		 * actions.contextClick(pic) .moveByOffset(60, 20)
		 * 
		 * .sendKeys(Keys.ENTER) .build() .perform();
		 * 
		 * break; } else { System.out.println("Incorrect code"); } }
		 */
		String originalWindow = driver.getWindowHandle();
		assert driver.getWindowHandles().size() == 1;

		driver.switchTo().newWindow(WindowType.TAB);

		if (product.toLowerCase().contains("zara")) {
			driver.get("https://rahulshettyacademy.com/api/ecom/uploads/productImage_1650649434146.jpeg");
		} else if (product.toLowerCase().contains("adidas")) {
			driver.get("https://rahulshettyacademy.com/api/ecom/uploads/productImage_1650649488046.jpg");
		} else {
			driver.get("https://rahulshettyacademy.com/api/ecom/uploads/productImage_1650649561326.jpg");
		}

		String link = driver.findElement(By.xpath("//img")).getAttribute("src");
		System.out.println(link);
		driver.switchTo().window(originalWindow);

		List<WebElement> cartbodies = driver.findElements(By.cssSelector(".mb-3"));
		for (int i = 0; i < cartbodies.size(); i++) {
			if (cartbodies.get(i).getText().toLowerCase().contains(product)) {
				WebElement dproduct = cartbodies.get(i);
				dproduct.findElement(By.cssSelector(".card-body button:last-of-type")).click();
				break;
			}
		}

		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//ngx-spinner"))));
		WebElement productaddedtocartsuccessfully = driver
				.findElement(By.xpath("//div[@aria-label='Product Added To Cart']"));
		String prdtaddedtcart = productaddedtocartsuccessfully.getText();
		System.out.println(prdtaddedtcart);
		wait.until(
				ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[text()=' Product Added To Cart ']")));
		Assert.assertEquals(prdtaddedtcart, "Product Added To Cart");

		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();

		List<WebElement> cartlist = driver.findElements(By.cssSelector(".cartWrap"));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".cartWrap")));

		for (int i = 0; i < cartlist.size(); i++) {
			if (cartlist.get(i).getText().toLowerCase().contains(product)) {
				System.out.println(product);
				driver.findElement(By.xpath("//button[text()='Checkout']")).click();

				break;

			} else {
				System.out.println("product not found");
			}

		}

		// Select objSelect = new
		// Select(driver.findElement(By.xpath("//div[1]/select[1]")));
		Select objSelect = new Select(driver.findElement(By.className("ddl")));
		objSelect.selectByIndex(9);

		Select objSelect2 = new Select(driver.findElement(By.xpath("//div[1]/select[2]")));
		objSelect2.selectByIndex(22);

		driver.findElement(By.xpath("(//input[@type='text'])[1]")).sendKeys("9856856985214563");
		driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("254");
		driver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys("Dhoni");
		driver.findElement(By.cssSelector("[placeholder='Select Country']")).sendKeys("India");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()=' India']")));
		driver.findElement(By.xpath("//span[text()=' India']")).click();
		// driver.findElement(By.xpath("(//input[@type='text'])[4]']")).sendKeys("welcome");//div[text()='
		// Please Enter Coupon ']
		driver.findElement(By.xpath("//form/div/div[4]/div[2]/button")).click();
		WebElement incorrectC = driver.findElement(By.xpath("//div[text()= ' Please Enter Coupon ']"));

		wait.until(ExpectedConditions.visibilityOf(incorrectC));
		String coupon = incorrectC.getText();
		System.out.println(coupon);
		wait.until(ExpectedConditions.invisibilityOf(incorrectC));

		driver.findElement(By.className("action__submit")).click();
		System.out.println("waiting2");
		String thankyou = driver.findElement(By.cssSelector("[class='hero-primary']")).getText();
		System.out.println(thankyou);
		Assert.assertTrue(thankyou.toLowerCase().contains("thankyou"));

		driver.findElement(By.cssSelector(".fa-sign-out")).click();
		WebElement logout = driver.findElement(By.xpath("//div[text()=' Logout Successfully ']"));
		wait.until(ExpectedConditions.visibilityOf(logout));
		String out = logout.getText();
		System.out.println(out);
		driver.quit();

	}

}
