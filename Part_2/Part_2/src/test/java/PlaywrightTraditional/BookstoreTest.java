package PlaywrightTraditional;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BookstoreTest {

    @Test
    public void testCaseBookstore() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false)
            );

            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate("https://depaul.bncollege.com/JBL/JBL-Quantum-True-Wireless-Noise-Cancelling-Gaming-Earbuds--Black/p/668972707?currentCampus=85");

            // Assert product name
            Locator productName = page.locator("h1.name").first();
            assertThat(productName).containsText("JBL Quantum True Wireless");

            // Assert SKU
            Locator sku = page.locator("div.sku:visible")
                    .filter(new Locator.FilterOptions().setHasText("668972707"))
                    .first();
            assertThat(sku).containsText("668972707");

            // Assert price
            Locator price = page.locator("text=$164.98").first();
            assertThat(price).isVisible();

            // Assert description snippet
            Locator description = page.locator("text=Adaptive noise cancelling").first();
            assertThat(description).isVisible();

            // Add 1 to cart
            page.waitForTimeout(3000);
            Locator addToCartButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("ADD.*CART", Pattern.CASE_INSENSITIVE))).first();

            addToCartButton.scrollIntoViewIfNeeded();
            page.waitForTimeout(2000);
            assertThat(addToCartButton).isVisible();
            addToCartButton.click();

            // Wait a moment for cart count to update
            page.waitForTimeout(3000);

            // Assert cart shows 1 item
            // Wait for cart update
            page.waitForTimeout(5000);

            // Click cart
            Locator cartLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Cart", Pattern.CASE_INSENSITIVE))).first();

            assertThat(cartLink).isVisible();
            cartLink.click();

            page.waitForTimeout(5000);

            Locator shoppingCartHeader = page.locator("h2.bned-cart-main-title:visible");
            assertThat(shoppingCartHeader).containsText("Your Shopping Cart");

            Locator cartProductName = page.getByText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black").first();
            assertThat(cartProductName).isVisible();

            Locator quantity = page.locator("text=1").first();
            assertThat(quantity).isVisible();

            context.close();
            browser.close();
        }
    }
}