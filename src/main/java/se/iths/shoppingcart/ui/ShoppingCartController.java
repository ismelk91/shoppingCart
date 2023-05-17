package se.iths.shoppingcart.ui;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.shoppingcart.business.Category;
import se.iths.shoppingcart.business.OrderLine;
import se.iths.shoppingcart.business.Product;
import se.iths.shoppingcart.service.ShoppingCartService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ShoppingCartController {

    @Autowired
    ShoppingCartService service;

    // Login

    @PostMapping("/login")
    public String login(@RequestParam("customername") String name, String password) {
        service.login(name, password);
        return "categories";
    }

    @GetMapping("/admin")
    public String adminhomepage() {
        return "admin";
    }

    @GetMapping("/customers")
    public String getAllCustomers(Model m) {
        m.addAttribute("customers", service.getCustomers());
        return "customers";
    }

    // Category

    @GetMapping("/categories")
    public String showCategories() {
        return "categories";
    }

    @GetMapping("/products/{category}")
    public String showProducts(@PathVariable Category category, Model model) {
        model.addAttribute("products", service.getCategory(category));
        return "products";
    }

    // Products

    @GetMapping("/products")
    public String showProducts(Model m) {
        m.addAttribute("products", service.getAllProducts());
        return "products";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam String product, Model m) {
        m.addAttribute("products", service.getProduct(product));
        return "products";
    }


    @GetMapping("/add")
    public String addProducts(Model m) {
        m.addAttribute("categories", List.of(Category.values()));
        m.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/save")
    public String addProduct(Product product, Model m) {
        service.addProduct(product);
        m.addAttribute("product", new Product());
        return "redirect:/products";
    }


    // Shopping Cart

    @GetMapping("/shoppingcart")
    public String showShoppingCart(Model m) {
        m.addAttribute("shoppingCart", service.getShoppingCart());
        m.addAttribute("totalPrice", service.getTotalCost(service.getShoppingCart()));
        return "shoppingcart";
    }

    @PostMapping("/shoppingcart")
    public String addToCart(@RequestParam Long id, @RequestParam int quantity, Model m) {
        service.putInCart(id, quantity);
        return "categories";
    }

    @PostMapping("/updatecart")
    public String AdjustCart(@RequestParam int index, @RequestParam int quantity, Model m) {
        service.updateCart(index, quantity);
        m.addAttribute("shoppingCart", service.getShoppingCart());
        return "redirect:/shoppingcart";
    }

    // Order

    @PostMapping("/order")
    public String submitOrder(Model m) {
        List<OrderLine> customerOrder = new ArrayList<>(service.getShoppingCart());
        service.addOrder();
        m.addAttribute("customerorder", customerOrder);
        m.addAttribute("totalPrice", service.getTotalCost(customerOrder));
        m.addAttribute("allorders", service.getAllOrders());
        return "order-confirmation";
    }

    @GetMapping("/allorders")
    public String getAllOrders(Model m) {
        m.addAttribute("allorders", service.getAllOrders());
        m.addAttribute("totalPrice", service.getTotalCost(service.getShoppingCart()));
        return "all-orders";
    }

    @PostMapping("/allorders")
    public String markOrder(@RequestParam Long id, Model m) {
        service.markOrder(id);
        m.addAttribute("order", service.getAllOrders());
        return "redirect:/allorders";
    }


}
