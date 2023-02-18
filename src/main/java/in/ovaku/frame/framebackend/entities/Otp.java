package in.ovaku.frame.framebackend.entities;
/**
 * Copyright (c) 2022 Ovaku.
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * This class is an entity class with 4 member variables.
 * It defines OTP information.
 *
 * @author Nirupam
 * @version 1.0
 * @since 26/01/2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ApiModel(description = "It contents OTP details")
public class Otp {
    /**
     * it represents the unique id of every record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name = "id", notes = "OTP Id", required = true, value = "1")
    private Long id;
    /**
     * it represents the OTP number, send through message.
     */
    @ApiModelProperty(name = "otp", notes = "Message OTP for registration", required = true, value = "123456")
    private Integer otp;
    /**
     * It represents users phone number.
     */
    @Column(length = 15)
    @ApiModelProperty(name = "phoneNo", notes = "Users contact number", required = true, value = "91-XXXXXXXXXX")
    private Long phoneNo;
    /**
     * it represents record created date and time.
     */
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @ApiModelProperty(name = "creationTime", notes = "OTP creation date with time", required = true, value = "2022-12-22 17:11:06.513000")
    private Date creationTime;
}
