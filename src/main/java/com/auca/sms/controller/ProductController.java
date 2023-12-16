package com.auca.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.auca.sms.service.*;
import com.auca.sms.entity.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/addProductForm")
    public String addProductForm(Model model) {
        model.addAttribute("newProduct", new Product());
        return "addProductForm";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product) {
        try {
            productService.saveProduct(product);
            return "redirect:/admin";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    @GetMapping("/admin")
    public String adminPortal(Model model) {
        try {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);
            return "admin";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    @GetMapping("/homepage/search")
    public String searchProducts(@RequestParam(name = "query") String query, Model model) {
        try {
            List<Product> searchResults = productService.searchProducts(query);
            model.addAttribute("products", searchResults);
            return "homepage";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    @GetMapping("/editProduct/{productId}")
    public String editProduct(@PathVariable Long productId, Model model) {
        try {
            Product product = productService.getProductById(productId);
            model.addAttribute("product", product);
            return "editProduct";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    // Add method for handling the update form
    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product) {
        try {
            productService.saveProduct(product);
            return "redirect:/admin";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    @GetMapping("/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return "redirect:/admin";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }
}
