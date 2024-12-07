package com.ebanking.notificationsservice.service;

import com.ebanking.notificationsservice.model.SMS;
import com.ebanking.notificationsservice.model.SendVerificationCodeResponse;

public interface NotificationsServcie {
    String  sendSMS( SMS sms );
    SendVerificationCodeResponse verifyIdentity(String phone , String code );
//    String test(Customer customer) throws JsonProcessingException;

}