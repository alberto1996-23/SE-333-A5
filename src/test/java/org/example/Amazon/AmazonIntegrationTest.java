package org.example.Amazon;

import org.example.Amazon.Cost.DeliveryPrice;
import org.example.Amazon.Cost.ExtraCostForElectronics;
import org.example.Amazon.Cost.ItemType;
import org.example.Amazon.Cost.RegularCost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmazonIntegrationTest {

    private Database database;
    private ShoppingCartAdaptor cart;
    private Amazon amazon;

    @BeforeEach
    void setUp() throws SQLException {
        database = new Database();
        database.resetDatabase();

        cart = new ShoppingCartAdaptor(database);

        amazon = new Amazon(
                cart,
                List.of(
                        new RegularCost(),
                        new DeliveryPrice(),
                        new ExtraCostForElectronics()
                )
        );
    }

    @Nested
    @DisplayName("specification-based")
    class SpecificationBasedTests {

        @Test
        void addToCart_shouldPersistItemInDatabase() throws SQLException {
            Item book = new Item(ItemType.OTHER, "Book", 2, 10.0);

            amazon.addToCart(book);

            List<Item> items = cart.getItems();

            assertEquals(1, items.size());
            assertEquals("Book", items.get(0).getName());
            assertEquals(ItemType.OTHER, items.get(0).getType());
            assertEquals(2, items.get(0).getQuantity());
            assertEquals(10.0, items.get(0).getPricePerUnit(), 0.001);
        }

        @Test
        void calculate_shouldReturnCorrectTotalForRegularItems() throws SQLException {
            Item book = new Item(ItemType.OTHER, "Book", 2, 10.0);

            amazon.addToCart(book);

            double result = amazon.calculate();

            assertEquals(25.0, result, 0.001);
        }
    }

    @Nested
    @DisplayName("structural-based")
    class StructuralBasedTests {

        @Test
        void calculate_shouldIncludeExtraCostForElectronicItems() throws SQLException {
            Item headphones = new Item(ItemType.ELECTRONIC, "Headphones", 1, 50.0);

            amazon.addToCart(headphones);

            double result = amazon.calculate();

            assertEquals(62.5, result, 0.001);
        }

        @Test
        void calculate_shouldReturnOnlyZeroForEmptyCart() throws SQLException {
            double result = amazon.calculate();

            assertEquals(0.0, result, 0.001);
        }
    }
}
