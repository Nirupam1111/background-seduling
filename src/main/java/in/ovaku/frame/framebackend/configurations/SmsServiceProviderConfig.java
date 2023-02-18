package in.ovaku.frame.framebackend.configurations;
/*
 * Copyright (c) 2022 Ovaku.
 */

import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

/**
 * This abstract class creates OTP and provides getOtp method for sms configuration class.
 *
 * @author Nirupam
 * @version 1.0
 * @since 27/01/2023
 */
public abstract class SmsServiceProviderConfig {
    @Value("${OTP_LENGTH}")
    private Integer OTP_LENGTH;
    /**
     * This default method creates new OTP.
     *
     * @return - returns Integer OTP.
     */
    protected Integer createOtp() {
        Random rand = new Random();
        String number = "";
        for (int i = 0; i < OTP_LENGTH; ++i) {
            int randomNumber = rand.nextInt(9);
            number = number.concat(String.valueOf(randomNumber));
        }
        return Integer.parseInt(number);
    }

    /**
     * This method calls create otp method and returns the OTP.
     *
     * @return - Integer OTP
     */
    protected abstract Integer getOtp();

    /**
     * This method sends Message to the registered Users.
     *
     * @param phoneNo - phoneNo, in which message has to be sent.
     * @param messageBody - message, that has to be sent.
     */
    protected abstract void sendMessage(Long phoneNo, String messageBody);
}
