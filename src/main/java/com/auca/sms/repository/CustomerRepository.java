package com.auca.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auca.sms.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

    Customer findByUserNameAndPassword(String username, String password);
 // Add this custom search query method
    @Query("SELECT c FROM Customer c WHERE LOWER(c.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Customer> searchCustomers(@Param("query") String query);
    List<Customer> findByUserNameContainingIgnoreCase(String query);

    List<Customer> findByEmailContainingIgnoreCase(String query);

}
