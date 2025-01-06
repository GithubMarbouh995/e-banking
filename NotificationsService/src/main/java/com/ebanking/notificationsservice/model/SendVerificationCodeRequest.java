package com.ebanking.notificationsservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class SendVerificationCodeRequest {
    private  String phone ;
    private String  code;
}
