package in.ovaku.frame.framebackend.controllers;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.dtos.commons.BusinessDto;
import in.ovaku.frame.framebackend.dtos.requests.BusinessRegistrationDto;
import in.ovaku.frame.framebackend.dtos.responses.ApiResponseDto;
import in.ovaku.frame.framebackend.services.BusinessServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * This class is a controller class of Business.
 * It processes the request and return the view as response.
 *
 * @author SOHAN
 * @version 1.0
 * @since 25/05/22
 */
@RestController
@RequestMapping("/businesses")
@Api(tags = "Business Controller", value = "BusinessController", description = "Controller for Business")
public class BusinessController {
    private final BusinessServices businessServices;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public BusinessController(BusinessServices businessServices) {
        this.businessServices = businessServices;
    }

    /**
     * This method is used to get all active or inactive {@link BusinessDto}.
     * It by default get all active {@link BusinessDto}.
     *
     * @return json
     */
    @ApiOperation(value = "Get list of all Business ",
            notes = "View list of all active or inactive Business information ", response = ArrayList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully data retrieved"),
            @ApiResponse(code = 404, message = "No data available!")
    })
    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam(required = false, defaultValue = "true") Boolean isActive) {
        logger.trace("Getting all business entity list");
        return new ApiResponseDto().generateResponse(
                HttpStatus.OK, businessServices.getAll(isActive), "Successfully data retrieved");
    }

    /**
     * This method is used to get a specific {@link BusinessDto}
     * It by default get active {@link BusinessDto}.
     *
     * @param id- id of the entity to find. Must not be null.
     * @return json
     */
    @ApiOperation(value = "Find business by id",
            notes = "Provide a specific business id to get information ", response = BusinessDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully data retrieved"),
            @ApiResponse(code = 404, message = "No data available!")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getBusiness(@PathVariable Long id,
                                              @RequestParam(required = false, defaultValue = "true") Boolean isActive) {
        logger.trace("Getting business entity by id => {}", id);
        return new ApiResponseDto().generateResponse(
                HttpStatus.OK, businessServices.getById(id, isActive), "Successfully data retrieved");
    }

    /**
     * This method register new business using phone number.
     *
     * @param businessRegistrationDto- dto to be registered
     * @return json
     */
    @ApiOperation(value = "Add new Business", notes = "Register new Business using mobile number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered"),
            @ApiResponse(code = 409, message = "Already Exists!")
    })
    @PostMapping
    public ResponseEntity<Object> businessRegistration(@Valid @RequestBody BusinessRegistrationDto businessRegistrationDto) {
        // TODO: 26/06/22 Validate phone number using OTP
        logger.trace("Creating new business entity with registration data => {}", businessRegistrationDto);
        return new ApiResponseDto().generateResponse(
                HttpStatus.CREATED, businessServices.add(businessRegistrationDto), "Successfully registered");
    }

    /**
     * This method is used to update a specific business
     *
     * @param id           - id of the entity to find. Must not be null.
     * @param businessDto- dto to be updated
     * @return json
     */
    @ApiOperation(value = "Update a business by id", notes = "Provide a specific business id to update information ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 404, message = "Resource doesn't exist!"),
            @ApiResponse(code = 500, message = "Operation failed!")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBusiness(@Valid @PathVariable Long id, @RequestBody BusinessDto businessDto) {
        logger.trace("Updating business entity by id => {} id with data => {}", id, businessDto);
        return new ApiResponseDto().generateResponse(
                HttpStatus.OK, businessServices.update(id, businessDto), "Successfully updated");
    }

    /**
     * This method is used to delete a specific business
     *
     * @param id - id of the entity to delete. Must not be null.
     * @return json
     */
    @ApiOperation(value = "Delete a business by id", notes = "Provide a specific business id to delete information ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Resource doesn't exist!")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBusiness(@PathVariable Long id) {
        logger.trace("Deleting business entity by id => {}", id);
        businessServices.delete(id);
        return new ApiResponseDto().generateResponse(HttpStatus.OK, null, "Successfully deleted");
    }
}
