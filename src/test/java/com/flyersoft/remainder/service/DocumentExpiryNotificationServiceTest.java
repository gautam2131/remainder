package com.flyersoft.remainder.service;

import com.flyersoft.remainder.model.Customer;
import com.flyersoft.remainder.model.Document;
import com.flyersoft.remainder.repository.CustomerRepository;
import com.flyersoft.smtp.model.EmailDetails;
import com.flyersoft.smtp.service.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class DocumentExpiryNotificationServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private EmailServiceImpl emailService;

    @InjectMocks
    private DocumentExpiryNotificationService documentExpiryNotificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendNotifications() {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("test@example.com");

        Document document = new Document();
        document.setId(1L);
        document.setExpiryDate(LocalDate.now().plusDays(60)); // Set expiry date to 60 days from today

        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerRepository.findNearestExpiryDocumentByCustomerId(customer.getId())).thenReturn(document);

        //
        documentExpiryNotificationService.sendNotifications();

        //
        EmailDetails expectedEmailDetails = new EmailDetails();
        expectedEmailDetails.setRecipient(customer.getEmail());
        expectedEmailDetails.setSubject("Document Expiry");
        expectedEmailDetails.setMsgBody("Dear Customer, we've noticed that your documents will soon expire. To keep your records up to date and continue enjoying uninterrupted services, please check your registered email address with EDB for details. For further assistance, you can contact us at Business.Banking@edb.gov.ae or call 600-55-1216. If you've already updated your documents with us, kindly disregard this message.");

        verify(emailService, times(1)).sendSimpleMail(expectedEmailDetails);
    }
}
