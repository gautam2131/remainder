package com.flyersoft.smtp.service;

import com.flyersoft.smtp.model.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);


}
