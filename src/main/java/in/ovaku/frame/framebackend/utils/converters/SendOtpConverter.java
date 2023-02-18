package in.ovaku.frame.framebackend.utils.converters;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.entities.Otp;
import in.ovaku.frame.framebackend.dtos.commons.SendOtpDto;

/**
 * This is a converter interface.
 * It is used to map {@link Otp} entity class with {@link SendOtpDto} dto class.
 *
 * @author Nirupam
 * @version 1.0
 * @since 26/01/2023
 */
public interface SendOtpConverter {
    /**
     * This method converts {@link Otp} to {@link SendOtpDto}
     *
     * @return {@link SendOtpDto}
     */
    SendOtpDto otpToSendOtpDto(Otp otp);
    /**
     * This method converts {@link SendOtpDto} to {@link Otp}
     *
     * @return {@link Otp}
     */
    Otp sendOtpDtoToOtp(SendOtpDto sendOtpDto);
}
