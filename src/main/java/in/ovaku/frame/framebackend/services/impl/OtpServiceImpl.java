package in.ovaku.frame.framebackend.services.impl;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.configurations.impl.TwilioConfiguration;
import in.ovaku.frame.framebackend.dtos.commons.SendOtpDto;
import in.ovaku.frame.framebackend.dtos.commons.ValidateOtpDto;
import in.ovaku.frame.framebackend.dtos.responses.OtpResponseDto;
import in.ovaku.frame.framebackend.entities.Otp;
import in.ovaku.frame.framebackend.exceptions.OperationFailedException;
import in.ovaku.frame.framebackend.exceptions.ResourceNotFoundException;
import in.ovaku.frame.framebackend.repositories.OtpRepository;
import in.ovaku.frame.framebackend.services.OtpService;
import in.ovaku.frame.framebackend.utils.converters.OtpResponseConverter;
import in.ovaku.frame.framebackend.utils.converters.SendOtpConverter;
import in.ovaku.frame.framebackend.utils.converters.ValidateOtpConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * This class implements an interface {@link OtpService}.
 * It contains logic for sending and validating otp.
 *
 * @author Nirupam
 * @version 1.0
 * @since 27/01/2023
 */
@Service
public class OtpServiceImpl implements OtpService {
    @Value("${OTP_WAITING_TIME}")
    private String OTP_WAITING_TIME;
    @Value("${OTP_MESSAGE}")
    private String OTP_MESSAGE;
    private final TwilioConfiguration twilioConfiguration;
    private final SendOtpConverter sendOtpConverter;
    private final ValidateOtpConverter validateOtpConverter;
    private final OtpRepository otpRepository;
    private final OtpResponseConverter otpResponseConverter;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public OtpServiceImpl(TwilioConfiguration twilioConfiguration, SendOtpConverter sendOtpConverter,
                          ValidateOtpConverter validateOtpConverter, OtpRepository otpRepository, OtpResponseConverter otpResponseConverter) {
        this.twilioConfiguration = twilioConfiguration;
        this.sendOtpConverter = sendOtpConverter;
        this.validateOtpConverter = validateOtpConverter;
        this.otpRepository = otpRepository;
        this.otpResponseConverter = otpResponseConverter;
    }

    /**
     * This method sends OTP sms to the user.
     *
     * @param sendOtpDto - dto send at the registration time.
     * @return - String
     */
    @Override
    public OtpResponseDto send(SendOtpDto sendOtpDto) {
        logger.debug("Into send service method with data =>{}", sendOtpDto);
        //extract Otp entity from sendOtpDto dto.
        Otp client = sendOtpConverter.sendOtpDtoToOtp(sendOtpDto);
        logger.debug("Converted otp entity => {} from SendOtpDto", client);

        //get OTP from twilio configuration.
        int otp = twilioConfiguration.getOtp();
        //send sms with OTP using twilio.
        twilioConfiguration.sendMessage(client.getPhoneNo(), OTP_MESSAGE + otp);

        logger.trace("sms is sent to the clients with OTP =>{}", otp);
        client.setOtp(otp);
        logger.trace("Set otp number to the Otp");
        client = otpRepository.save(client);
        logger.debug("Record saved =>{} in DB", client);

        //convert Otp to OtpResponseDto.
        OtpResponseDto otpResponseDto = otpResponseConverter.otpToOtpResponseDto(client);
        logger.debug("Converted otp entity => {} to OtpResponseDto", otpResponseDto);
        //get the expiry time of the specific otp using expireTime function.
        long expireTime = expireTime(client);
        otpResponseDto.setExpireTime(new Date(expireTime));
        logger.trace("Set expire time to the Otp");
        return otpResponseDto;
    }

    /**
     * This method validates OTP send by the user.
     *
     * @param validateOtpDto - dto send at the OTP validation time.
     * @return - String
     */
    @Override
    public Boolean validate(ValidateOtpDto validateOtpDto) {
        logger.debug("Into send entity service method with data =>{}", validateOtpDto);
        //extract Otp entity from validateOtpDto dto.
        Otp validationOtp = validateOtpConverter.validateOtpDtoToOtp(validateOtpDto);
        logger.debug("Converted otp entity => {} from validateOtpDto", validationOtp);
        Optional<Otp> otp = otpRepository.findByPhoneNoAndOtp(validationOtp.getPhoneNo(), validationOtp.getOtp());
        logger.debug("Fetch otp entity by phoneNo=>{} and otp=>{} from DB", validationOtp.getPhoneNo(), validationOtp.getOtp());

        //validate if otp is valid or not.
        if (otp.isPresent()) {
            //get the creation time stamp of the specific otp.
            Date otpCreationTimeStamp = otp.get().getCreationTime();
            //convert otp creation time stamp to local date time.
            LocalDateTime otpCreationDate = new Timestamp(otpCreationTimeStamp.getTime()).toLocalDateTime();
            //current local date time.
            LocalDateTime now = LocalDateTime.now();

            //if the OTP is sent in the same day or not.
            if (otpCreationDate.getDayOfMonth() == now.getDayOfMonth() && otpCreationDate.getMonthValue() == now.getMonthValue()
                    && otpCreationDate.getYear() == now.getYear()) {
                logger.trace("Otp is sent in the same day");

                //get the expiry time from expireTime function.
                long expireTime = expireTime(otp.get());
                //if fixed time for OTP validation expired or not.
                //if current time less or equal to expireTime, then valid else not.
                if (expireTime >= System.currentTimeMillis()) {
                    logger.trace("Otp is valid");
                    otpRepository.deleteByPhoneNoAndOtp(validationOtp.getPhoneNo(), validationOtp.getOtp());
                    logger.debug("Record deleted =>{} from DB", otp);
                    return true;
                } else {
                    logger.debug("OTP is expired!!!");
                    throw new OperationFailedException("OTP is expired!!!");
                }
            } else {
                logger.debug("OTP is expired!!!");
                throw new OperationFailedException("OTP is expired!!!");
            }
        } else {
            logger.debug("No otp entity found for given phone no and otp");
            throw new ResourceNotFoundException("No otp entity found for given phone no and otp");
        }
    }

    /**
     * This method calculates and returns expiry time of the OTP send by the user.
     *
     * @param otp - otp, for which we have to find expiry time.
     * @return Long
     */
    private Long expireTime(Otp otp) {
        logger.debug("Into expireTime method with data =>{}", otp);
        //get the creation time stamp of the specific otp.
        Date otpCreationTimeStamp = otp.getCreationTime();
        //calculates expiry time
        Long expireTime = otpCreationTimeStamp.getTime() + Long.parseLong(OTP_WAITING_TIME) * 60 * 1000;
        return expireTime;
    }
}
