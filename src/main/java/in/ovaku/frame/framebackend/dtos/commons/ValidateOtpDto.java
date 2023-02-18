package in.ovaku.frame.framebackend.dtos.commons;
/*
 * Copyright (c) 2022 Ovaku.
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is a dto class with 3 member variables.
 * It defines OTP validation details.
 *
 * @author Nirupam
 * @version 1.0
 * @since 26/01/2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "It contains OTP validation details")
public class ValidateOtpDto {
    /**
     * it represents the unique id of every record.
     */
    @ApiModelProperty(name = "id", notes = "OTP Id", required = true, value = "1")
    private Long id;
    /**
     * It represents users phone number.
     */
    @ApiModelProperty(name = "phoneNo", notes = "Users contact number", required = true, value = "91-XXXXXXXXXX")
    private Long phoneNo;
    /**
     * it represents the OTP number, send through message.
     */
    @ApiModelProperty(name = "otp", notes = "Message OTP for registration", required = true, value = "123456")
    private Integer otp;
}
