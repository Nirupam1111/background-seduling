package in.ovaku.frame.framebackend.utils.converters;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.dtos.commons.ValidateOtpDto;
import in.ovaku.frame.framebackend.entities.Otp;

/**
 * This is a converter interface.
 * It is used to map {@link Otp} entity class with {@link ValidateOtpDto} dto class.
 *
 * @author Nirupam
 * @version 1.0
 * @since 26/01/2023
 */
public interface ValidateOtpConverter {
    /**
     * This method converts {@link Otp} to {@link ValidateOtpDto}
     *
     * @return {@link ValidateOtpDto}
     */
    ValidateOtpDto otpToValidateOtpDto(Otp otp);
    /**
     * This method converts {@link ValidateOtpDto} to {@link Otp}
     *
     * @return {@link Otp}
     */
    Otp validateOtpDtoToOtp(ValidateOtpDto validateOtpDto);
}
