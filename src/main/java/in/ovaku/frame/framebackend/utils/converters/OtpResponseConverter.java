package in.ovaku.frame.framebackend.utils.converters;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.dtos.responses.OtpResponseDto;
import in.ovaku.frame.framebackend.entities.Otp;

/**
 * This is a converter interface.
 * It is used to map {@link Otp} entity class with {@link OtpResponseDto} dto class.
 *
 * @author Nirupam
 * @version 1.0
 * @since 26/01/2023
 */
public interface OtpResponseConverter {
    /**
     * This method converts {@link Otp} to {@link OtpResponseDto}
     *
     * @return {@link OtpResponseDto}
     */
    OtpResponseDto otpToOtpResponseDto(Otp otp);
}
