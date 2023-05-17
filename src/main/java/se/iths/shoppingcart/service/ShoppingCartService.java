package se.iths.shoppingcart.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import se.iths.shoppingcart.business.*;
import se.iths.shoppingcart.repository.CustomerRepository;
import se.iths.shoppingcart.repository.OrderRepository;
import se.iths.shoppingcart.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class ShoppingCartService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    List<OrderLine> shoppingCart = new ArrayList<>();

    Customer customer;

    // Login

    public void login(String name, String password) {
        List<Customer> customers = customerRepository.findByName(name);
        if (customers.isEmpty()) {
            customer = customerRepository.save(new Customer(name, password));
        } else {
            customer = customers.get(0);
        }
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    // Category

    public List<Product> getCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    // Product

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProduct(String name) {
        return productRepository.findByName(name);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    // Shopping Cart

    public List<OrderLine> getShoppingCart() {
        return shoppingCart;
    }

    public void putInCart(Long id, int quantity) {
        Product product = productRepository.findById(id).get();
        OrderLine orderLine = new OrderLine(product, quantity);
        shoppingCart.add(orderLine);
    }

    public void updateCart(int index, int quantity) {
        if (quantity <= 0) {
            shoppingCart.remove(index);
        } else {
            getShoppingCart().get(index).setQuantity(quantity);
        }
    }

    public double getTotalCost(List<OrderLine> shoppingCart) {
        double total = 0;
        for (OrderLine orderLine : shoppingCart) {
            double sumOfQtyOfAProduct = orderLine.getQuantity() * orderLine.getProduct().getPrice();
            orderLine.setTotal(sumOfQtyOfAProduct);
            total += sumOfQtyOfAProduct;
        }
        return total;
    }

    // Order

    public void addOrder() {
        Order order = new Order(shoppingCart, customer, LocalDateTime.now(), false);
        customer.addOrder(order);
        customer = customerRepository.save(customer);
        shoppingCart.clear();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    public void markOrder(Long id) {
        Order order = getOrderById(id);
        order.setShipped(true);
        orderRepository.save(order);
    }

}

