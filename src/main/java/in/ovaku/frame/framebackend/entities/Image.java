package in.ovaku.frame.framebackend.entities;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.entities.enums.ImageType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * This class is an entity class with 8 member variables.
 * It contains different image specification.
 *
 * @author Sohan
 * @version 1.0
 * @since 22/03/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ApiModel(description = "It contains Image details")
public class Image {
    /**
     * It represents the unique id of every record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name = "id", notes = "Image Id", required = true, value = "1")
    private Long id;
    /**
     * It has one-to-many relation with {@link Event} class.
     * It represents events images.
     */
    @ManyToOne
    @JoinColumn(name = "event_id")
    @ApiModelProperty(name = "event", notes = "Image event details")
    private Event event;
    /**
     * It represents image name.
     */
    @ApiModelProperty(name = "name", notes = "Image name", required = true, value = "xyz")
    private String name;
    /**
     * It represents image size.
     */
    @Column(length = 11)
    @ApiModelProperty(name = "size", notes = "Image size", required = true, value = "xyz")
    private Integer size;
    /**
     * It represents image extension.
     */
    @ApiModelProperty(name = "extension", notes = "Image extension", required = true, value = "xyz")
    private String extension;
    /**
     * It represents image type.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @ApiModelProperty(name = "imageType", notes = "Image type", required = true, value = "xyz")
    private ImageType imageType;
    /**
     * It represents record created date.
     */
    @ApiModelProperty(name = "createdDate", notes = "Image created date", required = true, value = "00/00/0000")
    private Date createdDate;
    /**
     * It represents record updated date.
     */
    @ApiModelProperty(name = "updatedDate", notes = "Event updated date", required = true, value = "00/00/0000")
    private Date updatedDate;
}
