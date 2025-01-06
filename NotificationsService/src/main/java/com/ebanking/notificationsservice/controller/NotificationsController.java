package com.ebanking.notificationsservice.controller;

import com.ebanking.notificationsservice.model.Customer;
import com.ebanking.notificationsservice.model.SMS;
import com.ebanking.notificationsservice.model.SendVerificationCodeRequest;
import com.ebanking.notificationsservice.model.SendVerificationCodeResponse;
import com.ebanking.notificationsservice.service.NotificationsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationsController {
    private final NotificationsService notificationsService;


    public NotificationsController(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

    @PostMapping("/send-sms")
    public ResponseEntity<String> sendSMS(@Valid @RequestBody SMS sms) {
        try {
            if (sms == null || sms.getPhone() == null) {
                return ResponseEntity.badRequest().body("Invalid SMS request");
            }

            if (!sms.getPhone().matches("\\+[0-9]+")) {
                return ResponseEntity.badRequest().body("Invalid phone number format");
            }

            String result = notificationsService.sendSMS(sms);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Failed to send SMS: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send SMS: " + e.getMessage());
        }
    }

    @PostMapping("/OTP")
    public ResponseEntity<SendVerificationCodeResponse> verifyIdentity(@Valid @RequestBody SendVerificationCodeRequest request) {
        try {
            if (request == null || request.getPhone() == null || request.getCode() == null) {
                return ResponseEntity.badRequest().body(new SendVerificationCodeResponse(null, "Invalid verification request"));
            }

            if (request.getPhone().isEmpty()) {
                return ResponseEntity.badRequest().body(new SendVerificationCodeResponse(null, "Phone number cannot be empty"));
            }

            if (!request.getPhone().matches("\\+[0-9]+")) {
                return ResponseEntity.badRequest().body(new SendVerificationCodeResponse(null, "Invalid phone number format"));
            }

            if (request.getCode().length() < 4 || request.getCode().length() > 6 || request.getCode().contains(" ")) {
                return ResponseEntity.badRequest().body(new SendVerificationCodeResponse(null, "Invalid verification code"));
            }

            SendVerificationCodeResponse response = notificationsService.verifyIdentity(request.getPhone(), request.getCode());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new SendVerificationCodeResponse(null, "Failed to verify identity: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SendVerificationCodeResponse(null, "Failed to verify identity: " + e.getMessage()));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Notifications service is running");
    }

    @PostMapping("/testt")
    public ResponseEntity<String> test(@RequestBody Customer customer) {
        try {
            return ResponseEntity.ok(notificationsService.test(customer));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process customer: " + e.getMessage());
        }
    }
}
