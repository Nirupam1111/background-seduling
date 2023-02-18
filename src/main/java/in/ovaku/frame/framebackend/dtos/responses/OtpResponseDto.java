package in.ovaku.frame.framebackend.dtos.responses;
/*
 * Copyright (c) 2022 Ovaku.
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * This class is a dto class with 5 member variables.
 * It defines OTP details for response.
 *
 * @author Nirupam
 * @version 1.0
 * @since 26/01/2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "It contains OTP details for response")
public class OtpResponseDto {
    /**
     * it represents the unique id of every record.
     */
    @ApiModelProperty(name = "id", notes = "OTP Id", value = "1")
    private Long id;
    /**
     * It represents users phone number.
     */
    @ApiModelProperty(name = "phoneNo", notes = "Users contact number", value = "91-XXXXXXXXXX")
    private Long phoneNo;
    /**
     * it represents the OTP number, send through message.
     */
    @ApiModelProperty(name = "otp", notes = "Message OTP for registration", value = "123456")
    private Integer otp;
    /**
     * it represents otp creation date and time.
     */
    @ApiModelProperty(name = "creationTime", notes = "OTP creation date with time", value = "2022-12-22 17:11:06.513000")
    private Date creationTime;
    /**
     * it represents otp expired date time.
     */
    @ApiModelProperty(name = "expireTime", notes = "OTP expire date time", value = "2022-12-22 17:11:06.513000")
    private Date expireTime;
}
