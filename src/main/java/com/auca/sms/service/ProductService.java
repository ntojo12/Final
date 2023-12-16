package com.auca.sms.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.auca.sms.entity.Product;
import com.auca.sms.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

   
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public List<Product> searchProducts(String query) {
        // Assuming you have properties like 'name' in your Product entity
        return productRepository.findByNameContainingIgnoreCase(query);
    }
}
