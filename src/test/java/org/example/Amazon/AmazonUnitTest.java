package org.example.Amazon;

import org.example.Amazon.Cost.DeliveryPrice;
import org.example.Amazon.Cost.ExtraCostForElectronics;
import org.example.Amazon.Cost.ItemType;
import org.example.Amazon.Cost.RegularCost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmazonUnitTest {

    @Nested
    @DisplayName("specification-based")
    class SpecificationBasedTests {

        @Test
        void regularCost_shouldReturnZeroForEmptyCart() {
            RegularCost rule = new RegularCost();

            double result = rule.priceToAggregate(List.of());

            assertEquals(0.0, result, 0.001);
        }

        @Test
        void regularCost_shouldMultiplyPriceByQuantity() {
            RegularCost rule = new RegularCost();

            Item book = new Item(ItemType.OTHER, "Book", 2, 10.0);

            double result = rule.priceToAggregate(List.of(book));

            assertEquals(20.0, result, 0.001);
        }

        @Test
        void extraCostForElectronics_shouldReturnZeroWhenNoElectronicItemExists() {
            ExtraCostForElectronics rule = new ExtraCostForElectronics();

            Item book = new Item(ItemType.OTHER, "Book", 1, 15.0);

            double result = rule.priceToAggregate(List.of(book));

            assertEquals(0.0, result, 0.001);
        }

        @Test
        void extraCostForElectronics_shouldAddFeeWhenElectronicItemExists() {
            ExtraCostForElectronics rule = new ExtraCostForElectronics();

            Item headphones = new Item(ItemType.ELECTRONIC, "Headphones", 1, 50.0);

            double result = rule.priceToAggregate(List.of(headphones));

            assertEquals(7.5, result, 0.001);
        }
    }

    @Nested
    @DisplayName("structural-based")
    class StructuralBasedTests {

        @Test
        void deliveryPrice_shouldReturnZeroWhenCartIsEmpty() {
            DeliveryPrice rule = new DeliveryPrice();

            double result = rule.priceToAggregate(List.of());

            assertEquals(0.0, result, 0.001);
        }

        @Test
        void deliveryPrice_shouldReturnFiveWhenCartHasOneToThreeItems() {
            DeliveryPrice rule = new DeliveryPrice();

            List<Item> cart = List.of(
                    new Item(ItemType.OTHER, "Pen", 1, 2.0),
                    new Item(ItemType.OTHER, "Notebook", 1, 5.0)
            );

            double result = rule.priceToAggregate(cart);

            assertEquals(5.0, result, 0.001);
        }

        @Test
        void deliveryPrice_shouldReturnTwelvePointFiveWhenCartHasFourToTenItems() {
            DeliveryPrice rule = new DeliveryPrice();

            List<Item> cart = List.of(
                    new Item(ItemType.OTHER, "Item1", 1, 1.0),
                    new Item(ItemType.OTHER, "Item2", 1, 1.0),
                    new Item(ItemType.OTHER, "Item3", 1, 1.0),
                    new Item(ItemType.OTHER, "Item4", 1, 1.0)
            );

            double result = rule.priceToAggregate(cart);

            assertEquals(12.5, result, 0.001);
        }

        @Test
        void deliveryPrice_shouldReturnTwentyWhenCartHasMoreThanTenItems() {
            DeliveryPrice rule = new DeliveryPrice();

            List<Item> cart = List.of(
                    new Item(ItemType.OTHER, "Item1", 1, 1.0),
                    new Item(ItemType.OTHER, "Item2", 1, 1.0),
                    new Item(ItemType.OTHER, "Item3", 1, 1.0),
                    new Item(ItemType.OTHER, "Item4", 1, 1.0),
                    new Item(ItemType.OTHER, "Item5", 1, 1.0),
                    new Item(ItemType.OTHER, "Item6", 1, 1.0),
                    new Item(ItemType.OTHER, "Item7", 1, 1.0),
                    new Item(ItemType.OTHER, "Item8", 1, 1.0),
                    new Item(ItemType.OTHER, "Item9", 1, 1.0),
                    new Item(ItemType.OTHER, "Item10", 1, 1.0),
                    new Item(ItemType.OTHER, "Item11", 1, 1.0)
            );

            double result = rule.priceToAggregate(cart);

            assertEquals(20.0, result, 0.001);
        }

        @Test
        void regularCost_shouldSumMultipleItemsCorrectly() {
            RegularCost rule = new RegularCost();

            List<Item> cart = List.of(
                    new Item(ItemType.OTHER, "Book", 2, 10.0),
                    new Item(ItemType.ELECTRONIC, "Mouse", 1, 25.0)
            );

            double result = rule.priceToAggregate(cart);

            assertEquals(45.0, result, 0.001);
        }
    }
}
