package com.ebanking.notificationsservice.config;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.SmsClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.SmsSubmissionResponseMessage;
import com.vonage.client.sms.messages.TextMessage;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.TestPropertySource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@TestConfiguration
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    SecurityAutoConfiguration.class
})
@Import(WebMvcAutoConfiguration.class)
@ComponentScan(
    basePackages = "com.ebanking.notificationsservice",
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {
            com.ebanking.notificationsservice.NotificationsServiceApplication.class
        }
    )
)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TestConfig {

    @Bean
    @Primary
    public VonageClient vonageClient() {
        VonageClient mockClient = Mockito.mock(VonageClient.class);
        SmsClient mockSmsClient = smsClient(mockClient);
        Mockito.when(mockClient.getSmsClient()).thenReturn(mockSmsClient);
        return mockClient;
    }

    @Bean
    @Primary
    public SmsClient smsClient(VonageClient vonageClient) {
        SmsClient mockSmsClient = Mockito.mock(SmsClient.class);
        
        // Configure default success behavior
        SmsSubmissionResponseMessage mockMessage = Mockito.mock(SmsSubmissionResponseMessage.class);
        SmsSubmissionResponse mockResponse = Mockito.mock(SmsSubmissionResponse.class);
        
        Mockito.when(mockMessage.getStatus()).thenReturn(MessageStatus.OK);
        Mockito.when(mockResponse.getMessages()).thenReturn(java.util.Collections.singletonList(mockMessage));
        Mockito.when(mockSmsClient.submitMessage(Mockito.any(TextMessage.class))).thenReturn(mockResponse);
        
        return mockSmsClient;
    }
}
