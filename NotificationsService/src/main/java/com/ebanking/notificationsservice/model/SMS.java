package com.ebanking.notificationsservice.model;

import lombok.*;

/**
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SMS {
    private TransferType transferType;
    private double amount;
    private String pin;
    private String ref;
    private String phone;
    private Boolean sendRef;
    private String customerFirstName;
    private String customerLastName;
    private String beneficiaryFirstName;
    private String beneficiaryLastName;

    /**
     * 
     * @return 
     */
    public String getMessage() {
        StringBuilder message = new StringBuilder();
        
        if (transferType != null) {
            message.append("Transfer Type: ").append(transferType).append("\n");
        }
        
        message.append("Dear ").append(customerFirstName).append(" ").append(customerLastName).append(",\n");
        message.append("Amount: ").append(amount).append(" MAD\n");
        
        if (beneficiaryFirstName != null && beneficiaryLastName != null) {
            message.append("To: ").append(beneficiaryFirstName).append(" ").append(beneficiaryLastName).append("\n");
        }
        
        if (pin != null) {
            message.append("PIN: ").append(pin).append("\n");
        }
        
        if (Boolean.TRUE.equals(sendRef) && ref != null) {
            message.append("Reference: ").append(ref);
        }
        
        return message.toString();
    }
}