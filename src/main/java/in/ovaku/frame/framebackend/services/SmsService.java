package in.ovaku.frame.framebackend.services;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.dtos.commons.SendOtpDto;
import in.ovaku.frame.framebackend.dtos.responses.OtpResponseDto;

/**
 * This interface provides send method for SMS.
 *
 * @author Nirupam
 * @version 1.0
 * @since 27/01/2023
 */
public interface SmsService {
    /**
     * This method sends SMS to the user.
     *
     * @param sendOtpDto - sent otp dto for registration.
     * @return - String
     */
    OtpResponseDto send(SendOtpDto sendOtpDto);
}
