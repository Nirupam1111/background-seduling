package in.ovaku.frame.framebackend.controllers;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.dtos.commons.SendOtpDto;
import in.ovaku.frame.framebackend.dtos.commons.ValidateOtpDto;
import in.ovaku.frame.framebackend.dtos.responses.ApiResponseDto;
import in.ovaku.frame.framebackend.services.OtpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

/**
 * This class is a controller class of OTP.
 * It processes the request and return the view as response.
 *
 * @author Nirupam
 * @version 1.0
 * @since 26/01/2023
 */
@RestController
@RequestMapping("/otp")
@Transactional
@Api(tags = "Otp Controller", value = "OtpController", description = "Controller for Otp")
public class OtpController {
    private final OtpService otpService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    /**
     * This method sends OTP for user.
     *
     * @param sendOtpDto - dto to be created
     * @return json
     */
    @ApiOperation(value = "Send new OTP for user", notes = "Send new OTP for user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully sent"),
            @ApiResponse(code = 500, message = "Operation failed!")
    })
    @PostMapping("/send")
    public ResponseEntity<Object> send(@RequestBody SendOtpDto sendOtpDto) {
        logger.trace("Creating new OTP entity with creation data => {}", sendOtpDto);
        return new ApiResponseDto().generateResponse(HttpStatus.CREATED, otpService.send(sendOtpDto),
                "Successfully created");
    }

    /**
     * This method validates OTP of user.
     *
     * @param validateOtpDto - dto to be validate
     * @return json
     */
    @ApiOperation(value = "Send validation data for OTP", notes = "Send validation data for OTP")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully sent"),
            @ApiResponse(code = 500, message = "Operation failed!")
    })
    @PostMapping("/validate")
    public ResponseEntity<Object> validate(@RequestBody ValidateOtpDto validateOtpDto) {
        logger.trace("Validating OTP with dto data => {}", validateOtpDto);
        return new ApiResponseDto().generateResponse(HttpStatus.OK, otpService.validate(validateOtpDto),
                "Successfully sent");
    }
}
