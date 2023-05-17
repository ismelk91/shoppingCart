package se.iths.shoppingcart;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.iths.shoppingcart.business.Category;
import se.iths.shoppingcart.business.OrderLine;
import se.iths.shoppingcart.business.Product;
import se.iths.shoppingcart.service.ShoppingCartService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShoppingCartApplicationTests {

    @Autowired
    ShoppingCartService service;

    Product product1;
    Product product2;
    List<OrderLine> shoppingCart;

    @BeforeEach
    public void init() {
        shoppingCart = new ArrayList<>();
        product1 = new Product("Bregott", Category.Mejeri, 50);
        product2 = new Product("Melon", Category.Frukt, 40);
    }

    @Test
    void shouldAddItemToShoppingCart() {
        OrderLine orderLine1 = new OrderLine(product1,1);
        OrderLine orderLine2 = new OrderLine(product2,1);
        shoppingCart.add(orderLine1);
        shoppingCart.add(orderLine2);
        int numOfOrderLines = shoppingCart.size();
        assertEquals(2, numOfOrderLines);
    }


    @Test
    void shouldCalculateTotalSumOfItemsInShoppingCart() {
        OrderLine orderLine1 = new OrderLine(product1,10);
        OrderLine orderLine2 = new OrderLine(product2,2);
        shoppingCart.add(orderLine1);
        shoppingCart.add(orderLine2);
        double total = service.getTotalCost(shoppingCart);
        assertEquals(580, total);
    }

}
