package com.ebanking.notificationsservice.service;

import com.ebanking.notificationsservice.model.Customer;
import com.ebanking.notificationsservice.model.SMS;
import com.ebanking.notificationsservice.model.SendVerificationCodeRequest;
import com.ebanking.notificationsservice.model.SendVerificationCodeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface NotificationsService {
    String sendSMS(SMS sms);
    SendVerificationCodeResponse verifyIdentity(String phone, String code);
    String test(Customer customer) throws JsonProcessingException;
}
