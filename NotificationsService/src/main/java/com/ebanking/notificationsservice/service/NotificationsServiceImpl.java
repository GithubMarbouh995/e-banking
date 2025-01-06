// package com.ebanking.notificationsservice.service;

// import com.ebanking.notificationsservice.model.Customer;
// import com.ebanking.notificationsservice.model.SMS;
// import com.ebanking.notificationsservice.model.SendVerificationCodeResponse;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.vonage.client.VonageClient;
// import com.vonage.client.sms.MessageStatus;
// import com.vonage.client.sms.SmsSubmissionResponse;
// import com.vonage.client.sms.messages.TextMessage;
// import com.vonage.client.verify.CheckResponse;
// import com.vonage.client.verify.VerifyClient;
// import com.vonage.client.verify.VerifyStatus;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.core.env.Environment;
// import org.springframework.stereotype.Service;

// @Service
// public class NotificationsServiceImpl implements NotificationsService {

//     private final VonageClient vonageClient;
//     private final ObjectMapper objectMapper;
//     private final VerifyClient verifyClient;
//     private final com.vonage.client.sms.SmsClient smsClient;
//     private final Environment environment;
//     private final String BRAND_NAME = "E-Banking";

//     public NotificationsServiceImpl(@Value("${vonage.api.key}") String apiKey,
//                                   @Value("${vonage.api.secret}") String apiSecret,
//                                   Environment environment) {
//         this.environment = environment;
//         this.vonageClient = VonageClient.builder()
//                 .apiKey(apiKey)
//                 .apiSecret(apiSecret)
//                 .build();
//         this.objectMapper = new ObjectMapper();
//         this.verifyClient = vonageClient.getVerifyClient();
//         this.smsClient = vonageClient.getSmsClient();
//     }

//     @Override
//     public String sendSMS(SMS sms) {
//         try {
//             if (sms == null || sms.getPhone() == null) {
//                 throw new IllegalArgumentException("Invalid SMS request");
//             }

//             if (!sms.getPhone().matches("\\+[0-9]+")) {
//                 throw new IllegalArgumentException("Invalid phone number format");
//             }

//             TextMessage message = new TextMessage(
//                     BRAND_NAME,
//                     sms.getPhone(),
//                     sms.getMessage()
//             );

//             SmsSubmissionResponse response = smsClient.submitMessage(message);
//             if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
//                 return "Message sent successfully to " + sms.getPhone() + " with status: " + response.getMessages().get(0).getStatus();
//             } else {
//                 throw new RuntimeException("Failed to send SMS: " + response.getMessages().get(0).getErrorText());
//             }
//         } catch (com.vonage.client.VonageClientException | com.vonage.client.VonageResponseParseException e) {
//             throw new RuntimeException("Failed to send SMS: " + e.getMessage());
//         }
//     }

//     @Override
//     public SendVerificationCodeResponse verifyIdentity(String phone, String code) {
//         try {
//             if (phone == null || code == null) {
//                 throw new IllegalArgumentException("Invalid verification request");
//             }

//             if (!phone.matches("\\+[0-9]+")) {
//                 throw new IllegalArgumentException("Invalid phone number format");
//             }

//             if (code.length() < 4) {
//                 throw new IllegalArgumentException("Invalid verification code");
//             }

//             // Pour l'environnement de test, on accepte tous les codes
//             if (isTestEnvironment()) {
//                 return SendVerificationCodeResponse.builder()
//                         .code(code)
//                         .message("Code vérifié avec succès")
//                         .build();
//             }

//             // Vérification du code avec Vonage
//             CheckResponse response = verifyClient.check(phone, code);
//             if (response != null && response.getStatus() == VerifyStatus.OK) {
//                 return SendVerificationCodeResponse.builder()
//                         .code(code)
//                         .message("Code vérifié avec succès")
//                         .build();
//             } else {
//                 throw new RuntimeException("Failed to verify code");
//             }
//         } catch (com.vonage.client.VonageClientException | com.vonage.client.VonageResponseParseException e) {
//             throw new RuntimeException("Failed to verify identity: " + e.getMessage());
//         }
//     }

//     @Override
//     public String test(Customer customer) throws JsonProcessingException {
//         return objectMapper.writeValueAsString(customer);
//     }

//     private boolean isTestEnvironment() {
//         String[] activeProfiles = environment.getActiveProfiles();
//         return activeProfiles.length == 0 || // Si aucun profil n'est actif, considérer comme test
//                java.util.Arrays.stream(activeProfiles)
//                    .anyMatch(profile -> profile.equals("test") || profile.equals("default"));
//     }
// }


// --verions 2

package com.ebanking.notificationsservice.service;

import com.ebanking.notificationsservice.model.Customer;
import com.ebanking.notificationsservice.model.SMS;
import com.ebanking.notificationsservice.model.SendVerificationCodeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationsServiceImpl implements NotificationsService {

    private static final String API_URL = "https://api.infobip.com/sms/2/text/advanced";
    private static final String AUTHORIZATION_KEY = "App 9b355c1605110f1a67befc6764f51a3d-97b35c41-5a32-458d-87b1-27e1293fe3ec";
    private final OkHttpClient client;

    public NotificationsServiceImpl() {
        this.client = new OkHttpClient();
    }

    @Override
    public String sendSMS(SMS sms) {
        try {
            String messageText = createMessageText(sms);
            String response = sendRequest(sms.getPhone(), messageText);
            log.info("SMS envoyé avec succès à {}", sms.getPhone());
            return "Message envoyé avec succès à " + sms.getPhone();
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi du SMS: {}", e.getMessage());
            return "Erreur: " + e.getMessage();
        }
    }

    @Override
    public SendVerificationCodeResponse verifyIdentity(String phone, String code) {
        try {
            String messageText = "Votre code de vérification est : " + code;
            String response = sendRequest(phone, messageText);
            return SendVerificationCodeResponse.builder()
                    .message(messageText)
                    .code(code)
                    .build();
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi du code de vérification: {}", e.getMessage());
            return SendVerificationCodeResponse.builder()
                    .message("Erreur lors de l'envoi du code de vérification")
                    .code(code)
                    .build();
        }
    }

    private String createMessageText(SMS sms) {
        String depositInfo = String.format("%s %s a déposé un montant de %.2f DH au GAB au profit de %s %s",
                sms.getCustomerFirstName(), sms.getCustomerLastName(),
                sms.getAmount(), sms.getBeneficiaryFirstName(), sms.getBeneficiaryLastName());

        if (sms.getSendRef()) {
            return String.format("%s sous la référence %s. Veuillez utiliser le code de retrait %s.",
                    depositInfo, sms.getRef(), sms.getPin());
        } else {
            return String.format("%s. Veuillez utiliser le code de retrait %s.", 
                    depositInfo, sms.getPin());
        }
    }

    private String sendRequest(String phoneNumber, String message) throws Exception {
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = String.format(
                "{\"messages\":[{\"destinations\":[{\"to\":\"%s\"}],\"from\":\"EBanking\",\"text\":\"%s\"}]}",
                phoneNumber, message
        );

        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", AUTHORIZATION_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Échec de l'envoi. Code: " + response.code());
            }
            return response.body() != null ? response.body().string() : "";
        }
    }

    @Override
    public String test(Customer customer) throws JsonProcessingException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'test'");
    }
}