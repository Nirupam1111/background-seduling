package in.ovaku.frame.framebackend.configurations.impl;
/*
 * Copyright (c) 2022 Ovaku.
 */

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import in.ovaku.frame.framebackend.configurations.SmsServiceProviderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * This is a configuration class for Twilio sms provider.
 * It sends OTP to the users.
 *
 * @author Nirupam
 * @version 1.0
 * @since 27/01/2023
 */
@Configuration
public class TwilioConfiguration extends SmsServiceProviderConfig {
    @Value("${TWILIO_ACCOUNT_SID}")
    private String ACCOUNT_SID;
    @Value("${TWILIO_AUTH_TOKEN}")
    private String AUTH_TOKEN;
    @Value("${TWILIO_PHONE_NUMBER}")
    private String TWILIO_PHONE_NUMBER;

    /**
     * This method calls create otp method and returns the OTP.
     *
     * @return - Integer OTP
     */
    @Override
    public Integer getOtp() {
        return createOtp();
    }

    /**
     * This method sends Message to the registered Users.
     *
     * @param phoneNo - phoneNo, in which message has to be sent.
     */
    @Override
    public void sendMessage(Long phoneNo, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new PhoneNumber("+" + phoneNo),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        messageBody)
                .create();
    }
}
