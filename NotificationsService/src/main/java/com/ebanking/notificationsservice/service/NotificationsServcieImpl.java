package com.ebanking.notificationsservice.service;

import org.springframework.stereotype.Service;
import com.ebanking.notificationsservice.model.SMS;
import com.ebanking.notificationsservice.model.SendVerificationCodeResponse;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;

@Service
public class NotificationsServcieImpl implements NotificationsServcie {
//    @Autowired
//    Producer producer;

    private final VonageClient vonageClient = VonageClient.builder().apiKey("c29b20a5").apiSecret("44tTUtl6ywxhRJhm").build();
    private final String BRAND_NAME = "Vonage APIs";

    @Override
    public String sendSMS(SMS sms) {
        try {
            TextMessage message = createSingleSMSMessage(sms);
            SmsSubmissionResponse response = vonageClient.getSmsClient().submitMessage(message);

            if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
                return "Message sent successfully to " + sms.getPhone();
            } else {
                return "Error with sending the message to " + sms.getPhone();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Add proper logging here
            return "Error occurred: " + e.getMessage();
        }
    } 
//TODO : Add logic to send  indicate  from where the transfer in forwarded : TO ATM or BANK
    private TextMessage createSingleSMSMessage(SMS sms) {
        String depositInfo = String.format("%s %s a déposé un montant de %.2f DH au GAB au profit de %s %s",
                sms.getCustomerFirstName(), sms.getCustomerLastName(),
                sms.getAmount(), sms.getBeneficiaryFirstName(), sms.getBeneficiaryLastName()); // Fix the typo here

        String messageText;
        if (sms.getSendRef()) {
            messageText = String.format("%s sous la référence %s. Veuillez utiliser le code de retrait %s.",
                    depositInfo, sms.getRef(), sms.getPin());
        } else {
            messageText = String.format("%s. Veuillez utiliser le code de retrait %s.", depositInfo, sms.getPin());
        }

        return new TextMessage(BRAND_NAME, sms.getPhone(), messageText);
    }

    @Override
    public SendVerificationCodeResponse verifyIdentity(String phone, String code) {
        String msg = "Votre code de vérification est : " + code;
        TextMessage message = new TextMessage(BRAND_NAME, phone, msg);
        SmsSubmissionResponse response = vonageClient.getSmsClient().submitMessage(message);

        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            return SendVerificationCodeResponse.builder().message(msg).code(code).build();
        } else {
            return SendVerificationCodeResponse.builder().message("error  occurred while sending the verification code "
                    ).code(code).build();
        }
    }

//    @Override
//    public String test(Customer customer) throws JsonProcessingException {
//        return producer.sendMessage(customer);
//    }
}
