package in.ovaku.frame.framebackend.utils.converters.impl;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.dtos.responses.OtpResponseDto;
import in.ovaku.frame.framebackend.entities.Otp;
import in.ovaku.frame.framebackend.utils.converters.OtpResponseConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * This class implement an interface {@link OtpResponseConverter}
 * It contain converter logic for {@link Otp} and {@link OtpResponseDto}
 *
 * @author Nirupam
 * @version 1.0
 * @since 26/01/2023
 */
@Component
public class OtpResponseConverterImpl implements OtpResponseConverter {
    private final ModelMapper modelMapper;

    public OtpResponseConverterImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * This method converts {@link Otp} to {@link OtpResponseDto}
     *
     * @return {@link OtpResponseDto}
     */
    @Override
    public OtpResponseDto otpToOtpResponseDto(Otp otp) {
        return modelMapper.map(otp, OtpResponseDto.class);
    }
}
