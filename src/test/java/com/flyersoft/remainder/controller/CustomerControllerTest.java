package com.flyersoft.remainder.controller;

import com.flyersoft.remainder.model.Customer;
import com.flyersoft.remainder.service.CustomerService;
import com.flyersoft.remainder.service.DocumentExpiryNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private DocumentExpiryNotificationService documentExpiryNotificationService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        //

        when(customerService.getAllCustomers()).thenReturn(customers);

        ResponseEntity<List<Customer>> responseEntity = customerController.getAllCustomers();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customers, responseEntity.getBody());
    }

    @Test
    public void testGetCustomerById() {
        Long customerId = 1L;
        Customer customer = new Customer();
        //

        when(customerService.getCustomerById(customerId)).thenReturn(Optional.of(customer));

        ResponseEntity<?> responseEntity = customerController.getCustomerById(customerId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customer, responseEntity.getBody());
    }

    @Test
    public void testGetCustomerByIdNotFound() {
        Long customerId = 1L;

        when(customerService.getCustomerById(customerId)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = customerController.getCustomerById(customerId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        //

        when(customerService.saveCustomer(customer)).thenReturn(customer);

        ResponseEntity<Customer> responseEntity = customerController.createCustomer(customer);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(customer, responseEntity.getBody());
    }

    @Test
    public void testUpdateCustomer() {
        Long customerId = 1L;
        Customer customer = new Customer();
        //

        when(customerService.saveCustomer(customer)).thenReturn(customer);

        ResponseEntity<?> responseEntity = customerController.updateCustomer(customerId, customer);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customer, responseEntity.getBody());
    }

    @Test
    public void testDeleteCustomer() {
        Long customerId = 1L;

        ResponseEntity<Void> responseEntity = customerController.deleteCustomer(customerId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(customerService, times(1)).deleteCustomer(customerId);
    }
}

