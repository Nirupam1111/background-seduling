package in.ovaku.frame.framebackend.utils.converters.impl;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.dtos.commons.ValidateOtpDto;
import in.ovaku.frame.framebackend.entities.Otp;
import in.ovaku.frame.framebackend.utils.converters.ValidateOtpConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * This class implement an interface {@link ValidateOtpConverter}
 * It contain converter logic for {@link Otp} and {@link ValidateOtpDto}
 *
 * @author Nirupam
 * @version 1.0
 * @since 26/01/2023
 */
@Component
public class ValidateOtpConverterImpl implements ValidateOtpConverter {
    private final ModelMapper modelMapper;

    public ValidateOtpConverterImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * This method converts {@link Otp} to {@link ValidateOtpDto}
     *
     * @return ValidateOtpDto
     */
    @Override
    public ValidateOtpDto otpToValidateOtpDto(Otp otp) {
        return modelMapper.map(otp, ValidateOtpDto.class);
    }

    /**
     * This method converts {@link ValidateOtpDto} to {@link Otp}
     *
     * @return Otp
     */
    @Override
    public Otp validateOtpDtoToOtp(ValidateOtpDto validateOtpDto) {
        return modelMapper.map(validateOtpDto, Otp.class);
    }
}
