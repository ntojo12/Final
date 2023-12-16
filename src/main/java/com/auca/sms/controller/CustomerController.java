package com.auca.sms.controller;

import com.auca.sms.entity.*;
import com.auca.sms.service.CustomerService;



import com.auca.sms.repository.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.auca.sms.service.*;


import java.io.IOException;
import java.nio.file.Paths; 
import java.util.List;
import java.util.Objects;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private ProductService productService;
   
   
    public CustomerController(CustomerService customerService, CustomerRepository customerRepository, ProductService productService) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.productService = productService;
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        try {
            return "dashboard";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }
    
    @GetMapping("/upload")
    public String showUpload() {
        try {
            return "upload";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }
    
   
    @GetMapping("/menu")
    public String showMenu() {
        try {
            return "menu";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }
    
    @GetMapping("/view")
    public String showView() {
        try {
            return "view";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    @GetMapping("/homepage")
    public String showHomePage(Model model) {
        try {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);
            return "homepage";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    @GetMapping("/email")
    public String showEmailForm(Model model) {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            model.addAttribute("customers", customers);
            return "email";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    @GetMapping("/login")
    public String login() {
        try {
            return "login";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }
  
    @GetMapping("/index")
    public String index() {
        try {
            return "index";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    @GetMapping("/customers/search")
    public String searchCustomers(@RequestParam(name = "query") String query, Model model) {
        try {
            List<Customer> searchResults;

            // Check if the query is empty or null
            if (query != null && !query.isEmpty()) {
                // If the query is not empty, perform filtering
                searchResults = customerService.searchCustomers(query);
            } else {
                // If the query is empty, retrieve all customers (or handle it based on your use case)
                searchResults = customerService.getAllCustomers();
            }

            model.addAttribute("customers", searchResults);
            return "customers";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }


    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            Customer customer = customerRepository.findByUserNameAndPassword(username, password);

            if (customer != null) {
                return "redirect:/homepage";
            } else {
                model.addAttribute("error", "Invalid username or password");
                return "index1";
            }
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    @GetMapping("/customers")
    public String listCustomers(Model model) {
        try {
            model.addAttribute("customers", customerService.getAllCustomers());
            return "customers";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    @GetMapping("/customers/new")
    public String createCustomerForm(Model model) {
        try {
            Customer customer = new Customer();
            model.addAttribute("customer", customer);
            return "create_customer";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    @PostMapping("/customers")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        try {
            customerService.saveCustomer(customer);
            return "redirect:/index";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }
    
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            // Check if the file is not empty
            if (file.isEmpty()) {
                model.addAttribute("error", "Uploaded file is empty");
                return "error";
            }

            // Define the upload directory within the "static" folder
            String uploadDirectory = "static/uploads/";

            // Generate a unique file name to prevent overwriting existing files
            String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
            String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;

            // Construct the file path using Paths
            java.nio.file.Path uploadPath = Paths.get(uploadDirectory);
            java.nio.file.Path filePath = uploadPath.resolve(uniqueFileName);

            // Save the file to the server
            file.transferTo(filePath.toFile());

            // Store the uploaded file name in the model
            model.addAttribute("uploadedFileName", uniqueFileName);

            // Return the view for document display
            return "view-document";
        } catch (IOException e) {
            // Handle file processing errors and return an error view or message
            model.addAttribute("error", "An error occurred during file upload: " + e.getMessage());
            return "error";
        }
    }

    


    @GetMapping("/customers/edit/{id}")
    public String editCustomerForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("customer", customerService.getCustomerById(id));
            return "edit_customer";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    @PostMapping("/customers/{id}")
    public String updateCustomer(@PathVariable Long id,
                                 @ModelAttribute("customer") Customer customer,
                                 Model model) {
        try {
            Customer existingCustomer = customerService.getCustomerById(id);
            existingCustomer.setId(id);
            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());
            existingCustomer.setUserName(customer.getUserName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setNumber(customer.getNumber());
            existingCustomer.setAddress(customer.getAddress());
            existingCustomer.setPassword(customer.getPassword());

            customerService.updateCustomer(existingCustomer);
            return "redirect:/customers";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    @GetMapping("/customers/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomerById(id);
            return "redirect:/customers";
        } catch (Exception e) {
            return "redirect:/errorpage?error=" + e.getMessage();
        }
    }

    
}

