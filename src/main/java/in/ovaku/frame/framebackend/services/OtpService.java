package in.ovaku.frame.framebackend.services;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.dtos.commons.ValidateOtpDto;

/**
 * This interface extends SmsService interface.
 * This interface provides validate method for otp.
 *
 * @author Nirupam
 * @version 1.0
 * @since 27/01/2023
 */
public interface OtpService extends SmsService {
    /**
     * This method validates OTP send by the user.
     *
     * @param validateOtpDto - sent otp dto for validation.
     * @return - String
     */
    Boolean validate(ValidateOtpDto validateOtpDto);
}
