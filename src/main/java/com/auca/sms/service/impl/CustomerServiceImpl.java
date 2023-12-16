package com.auca.sms.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.auca.sms.entity.Customer;
import com.auca.sms.repository.CustomerRepository;
import com.auca.sms.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    public List<Customer> searchCustomers(String query) {
        List<Customer> byUserName = customerRepository.findByUserNameContainingIgnoreCase(query);
        List<Customer> byEmail = customerRepository.findByEmailContainingIgnoreCase(query);

        // Combine and remove duplicates (if needed)
        byUserName.addAll(byEmail);
        // You might want to use a Set to remove duplicates, depending on your requirements

        return byUserName;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
