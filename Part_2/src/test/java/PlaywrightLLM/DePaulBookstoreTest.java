package PlaywrightLLM;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.microsoft.playwright.Locator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

public class DePaulBookstoreTest {
  private Playwright playwright;
  private Browser browser;
  private Page page;

  @BeforeEach
  public void setUp() {
    playwright = Playwright.create();
    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    page = browser.newPage();
  }

  @AfterEach
  public void tearDown() {
    if (page != null) {
      page.close();
    }
    if (browser != null) {
      browser.close();
    }
    if (playwright != null) {
      playwright.close();
    }
  }

  @Test
  public void shouldSearchAndAddJblEarbudsToCart() {
    // 1. Navigate to the DePaul bookstore
    page.navigate("https://depaul.bncollege.com/");

    // 2. Search for "earbuds"
    Locator searchBox = page.getByPlaceholder(
    "Enter your search details (product title, ISBN, keyword, etc.)");

    searchBox.click();
    searchBox.fill("earbuds");
    searchBox.press("Enter");

    // 3. Filter by Brand = JBL
    page.getByText("Brand").first().click();
    page.waitForTimeout(1000);

    Locator jblOption = page.locator("label").filter(
        new Locator.FilterOptions().setHasText("JBL")
    ).first();

    jblOption.scrollIntoViewIfNeeded();
    jblOption.click();
    page.waitForTimeout(2000);

    
    // 4. Filter by Color = Black
    page.getByText("Color").first().click();
    page.waitForTimeout(1000);

    Locator blackLabel = page.locator("label").filter(
        new Locator.FilterOptions().setHasText("Black")
    ).first();

    blackLabel.scrollIntoViewIfNeeded();
    blackLabel.click();
    page.waitForTimeout(2000);

    // 5. Filter by Price = Over $50
    page.getByText("Price").first().click();
    page.waitForTimeout(1000);

    Locator over50Option = page.locator("label").filter(
        new Locator.FilterOptions().setHasText("Over $50")
    ).first();

    over50Option.scrollIntoViewIfNeeded();
    over50Option.click();

    page.waitForTimeout(3000);

    // 6. Open the product
    String productName = "JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black";
    page.waitForSelector("text=" + productName, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
    page.click("text=" + productName);

    // 7. Assert the product details
    page.waitForSelector("text=" + productName, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
    assertTrue(page.isVisible("text=" + productName), "Expected product name to be visible");

    // SKU assertion
    String expectedSku = "668972707";
    assertTrue(page.textContent("body").contains(expectedSku), "Expected SKU " + expectedSku + " to appear on the product page");

    // Price and description assertions
    assertTrue(page.textContent("body").contains("$"), "Expected a price to be displayed");
    assertTrue(page.textContent("body").toLowerCase().contains("noise cancelling"), "Expected the product description to include expected keywords");

    // 8. Add 1 item to the cart
    page.click("text=Add to Cart");

    // 9. Assert the cart shows 1 item (badge or cart count)
    // 10. Click Cart
    page.waitForTimeout(5000);

    Locator cartLink = page.getByRole(
        AriaRole.LINK,
        new Page.GetByRoleOptions().setName(Pattern.compile("Cart", Pattern.CASE_INSENSITIVE))
    ).last();

    cartLink.scrollIntoViewIfNeeded();
    assertTrue(cartLink.isVisible(), "Expected cart link to be visible");
    cartLink.click();

    page.waitForTimeout(5000);

    // 11. Assert the shopping cart page
    Locator shoppingCartHeader = page.locator("h2.bned-cart-main-title").last();
    shoppingCartHeader.scrollIntoViewIfNeeded();
    assertTrue(shoppingCartHeader.isVisible(), "Expected to be on the cart page");
    assertTrue(shoppingCartHeader.textContent().contains("Your Shopping Cart"), "Expected cart header to contain 'Your Shopping Cart'");

    // 12. Assert the cart contains the correct product, quantity and price
    assertTrue(page.isVisible("text=" + productName), "Expected the product name in the cart");
    assertTrue(page.textContent("body").contains("1"), "Expected quantity to be 1 in the cart");
    assertTrue(page.textContent("body").contains("$"), "Expected price to be visible in the cart");
  }
}
