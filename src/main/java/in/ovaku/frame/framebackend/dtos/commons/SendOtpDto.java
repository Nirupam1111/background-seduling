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
 * This class is a dto class with 2 member variables.
 * It defines OTP sending details.
 *
 * @author Nirupam
 * @version 1.0
 * @since 26/01/2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "It contains OTP sending details")
public class SendOtpDto {
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
}
