package com.flyersoft.remainder.service;

import com.flyersoft.remainder.model.Customer;
import com.flyersoft.remainder.model.Document;
import com.flyersoft.remainder.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        try {
            for (Document document : customer.getDocuments()) {
                document.setCustomer(customer);
            }
            return customerRepository.save(customer);
        } catch (Exception e) {
            // Log the exception or perform any other action
            throw new RuntimeException("Failed to save customer");
        }
    }

    public void deleteCustomer(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            // Log the exception or perform any other action
            throw new RuntimeException("Failed to delete customer");
        }
    }
}