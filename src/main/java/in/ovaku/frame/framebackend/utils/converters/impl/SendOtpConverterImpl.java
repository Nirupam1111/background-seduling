package in.ovaku.frame.framebackend.utils.converters.impl;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.dtos.commons.SendOtpDto;
import in.ovaku.frame.framebackend.entities.Otp;
import in.ovaku.frame.framebackend.utils.converters.SendOtpConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * This class implement an interface {@link SendOtpConverter}
 * It contain converter logic for {@link Otp} and {@link SendOtpDto}
 *
 * @author Nirupam
 * @version 1.0
 * @since 26/01/2023
 */
@Component
public class SendOtpConverterImpl implements SendOtpConverter {
    private final ModelMapper modelMapper;

    public SendOtpConverterImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * This method converts {@link Otp} to {@link SendOtpDto}
     *
     * @return SendOtpDto
     */
    @Override
    public SendOtpDto otpToSendOtpDto(Otp otp) {
        return modelMapper.map(otp, SendOtpDto.class);
    }

    /**
     * This method converts {@link SendOtpDto} to {@link Otp}
     *
     * @return Otp
     */
    @Override
    public Otp sendOtpDtoToOtp(SendOtpDto sendOtpDto) {
        return modelMapper.map(sendOtpDto, Otp.class);
    }
}
