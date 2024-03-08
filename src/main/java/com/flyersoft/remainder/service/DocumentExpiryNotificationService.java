package com.flyersoft.remainder.service;

import com.flyersoft.remainder.model.Customer;
import com.flyersoft.remainder.model.Document;
import com.flyersoft.remainder.repository.CustomerRepository;
import com.flyersoft.smtp.model.EmailDetails;
import com.flyersoft.smtp.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DocumentExpiryNotificationService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmailServiceImpl emailService;
    //
    private static String caseOneSub = "Document Expiry";
    private static String caseTwoSub = "Grace period";
    private static String caseThreeSub = "Debit Freeze";

    //
    private static String caseOneMessage = "Dear Customer, we've noticed that your documents will soon expire. To keep your records up to date and continue enjoying uninterrupted services, please check your registered email address with EDB for details. For further assistance, you can contact us at Business.Banking@edb.gov.ae or call 600-55-1216. If you've already updated your documents with us, kindly disregard this message.";
    private static String caseTwoMessage = "Dear Customer, according to our records, some of your documents have expired. To continue with uninterrupted services, please check your registered EDB email for document details. Failure to submit documents may result in disabling outward transactions within [X] days. For assistance, contact us at Business.Banking@edb.gov.ae or 600-55-1216. I If you’ve already updated your documents with us, kindly disregard this message.";
    private static String caseThreeMessage = "Dear Customer, please note that your documents have expired. Consequently, all outward transactions and mobile app access has been disabled temporarily. To reactivate the services, kindly refer to the email sent to your registered email address with us for. For further assistance contact us on Business.Banking@edb.gov.ae and 600-55-1216. If you've already updated your documents with us, kindly disregard this message.";


    @Scheduled(cron = "0 0 0 * * *")
    public void sendNotifications() {
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            Document nearestExpiryDocument = customerRepository.findNearestExpiryDocumentByCustomerId(customer.getId());
            if (nearestExpiryDocument != null) {
                LocalDate expiryDate = nearestExpiryDocument.getExpiryDate();
                LocalDate today = LocalDate.now();

                //case 1
                LocalDate sixtyDaysBeforeExp = today.plusDays(60);
                LocalDate thirtyDaysBeforeExp = today.plusDays(30);
                LocalDate onDateExp = today;

                //case 2
                LocalDate oneDayAfterExpiry = today.minusDays(1);
                LocalDate thirtyDaysAfterExpiry = today.minusDays(30);
                LocalDate sixtyDaysAfterExpiry = today.minusDays(60);

                //case 3
                LocalDate ninetyDaysAfterExpiry = today.minusDays(90);


                if (sixtyDaysBeforeExp.equals(expiryDate) || thirtyDaysBeforeExp.equals(expiryDate) || onDateExp.equals(expiryDate)) {// expiry date is today, 30 days from today, or 60 days from today
                    handleCaseOne(nearestExpiryDocument, customer);
                } else if (oneDayAfterExpiry.equals(expiryDate) || thirtyDaysAfterExpiry.equals(expiryDate) || sixtyDaysAfterExpiry.equals(expiryDate)) {// expiry date was yesterday, 30 days ago, or 60 days ago
                    handleCaseTwo(nearestExpiryDocument, customer);
                } else if (ninetyDaysAfterExpiry.equals(expiryDate)) {// expiry date was 90 days ago
                    handleCaseThree(nearestExpiryDocument, customer);
                }

            }
        }
    }

    public void handleCaseOne(Document document, Customer customer) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(customer.getEmail());
        emailDetails.setSubject(caseOneSub);
        emailDetails.setMsgBody(caseOneMessage);
        emailService.sendSimpleMail(emailDetails);
    }

    public void handleCaseTwo(Document document, Customer customer) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(customer.getEmail());
        emailDetails.setSubject(caseTwoSub);
        emailDetails.setMsgBody(caseTwoMessage);
        emailService.sendSimpleMail(emailDetails);
    }

    public void handleCaseThree(Document document, Customer customer) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(customer.getEmail());
        emailDetails.setSubject(caseTwoSub);
        emailDetails.setMsgBody(caseThreeMessage);
        emailService.sendSimpleMail(emailDetails);
    }


}




