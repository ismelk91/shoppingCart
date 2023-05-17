package se.iths.shoppingcart.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.iths.shoppingcart.business.Category;
import se.iths.shoppingcart.business.Product;
import se.iths.shoppingcart.repository.ProductRepository;
import se.iths.shoppingcart.service.ShoppingCartService;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class RestShoppingCartController {

    @Autowired
    ShoppingCartService service;

    @Autowired
    ProductRepository productRepository;


    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/product/{name}")
    public List<Product> getProduct(@PathVariable String name) {
        return productRepository.findByName(name);
    }

    @GetMapping("/products/{category}")
    public List<Product> getProductsByCategory(@PathVariable Category category) {
        return service.getCategory(category);
    }

    @PostMapping("/addproduct")
    public String addProduct(@RequestBody Product product) {
        service.addProduct(product);
        return "Produkt sparad.";
    }

    @PutMapping("/update/{id}")
    public String updateProduct(@PathVariable long id, @RequestBody Product product) {
        Product updatedProduct = service.getProductById(id);
        updatedProduct.setName(product.getName());
        updatedProduct.setCategory(product.getCategory());
        updatedProduct.setPrice(product.getPrice());
        productRepository.save(updatedProduct);
        return "Produkt uppdaterad.";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        Product deleteProduct = service.getProductById(id);
        productRepository.delete(deleteProduct);
        return "Produkt bortagen.";
    }


}
