package in.ovaku.frame.framebackend.controllers;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.dtos.commons.ClientDto;
import in.ovaku.frame.framebackend.dtos.requests.ClientRegistrationDto;
import in.ovaku.frame.framebackend.dtos.responses.ApiResponseDto;
import in.ovaku.frame.framebackend.services.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * This class is a controller class of Client.
 * It processes the request and return the view as response.
 *
 * @author SOHAN
 * @version 1.0
 * @since 1/07/22
 */
@RestController
@RequestMapping("{businessId}/clients")
@Api(tags = "Client Controller", value = "ClientController", description = "Controller for Business's client")
public class ClientController {
    private final ClientService clientService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * This method is used to get all active or inactive {@link ClientDto} for business.
     * It by default get all active {@link ClientDto}.
     *
     * @param businessId -id of the business entity to get client.Must not be null.
     * @return json
     */
    @ApiOperation(value = "Get list of all Client for business",
            notes = "View list of all active or inactive Client information ", response = ArrayList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully data retrieved"),
            @ApiResponse(code = 404, message = "No data available!")
    })
    @GetMapping
    public ResponseEntity<Object> getAll(@PathVariable Long businessId,
                                         @RequestParam(required = false, defaultValue = "true") Boolean isActive) {
        logger.trace("Getting all client entity list of business");
        return new ApiResponseDto()
                .generateResponse(HttpStatus.OK, clientService.getAll(businessId, isActive),
                        "Successfully data retrieved");
    }

    /**
     * This method is used to get a specific business {@link ClientDto}
     * It by default get active {@link ClientDto}.
     *
     * @param businessId- id of the business entity to get client. Must not be null.
     * @param id          -id of the client entity to find. Must not be null.
     * @return json
     */
    @ApiOperation(value = "Find Client by Business id and Client id",
            notes = "Provide a specific business id and client id to get information ", response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully data retrieved"),
            @ApiResponse(code = 404, message = "No data available!")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getClient(@PathVariable Long businessId, @PathVariable Long id,
                                            @RequestParam(required = false, defaultValue = "true") Boolean isActive) {
        logger.trace("Getting client entity by business id=>{} and client id=> {}", businessId, id);
        return new ApiResponseDto()
                .generateResponse(HttpStatus.OK, clientService.getById(businessId, id, isActive)
                        , "Successfully data retrieved");
    }

    /**
     * This method register new client for business using phone number.
     *
     * @param businessId             - id of the business entity to register client. Must not be null.
     * @param clientRegistrationDto- dto to be registered
     * @return json
     */
    @ApiOperation(value = "Add new Client for business", notes = "Register new Client for Business using mobile number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered"),
            @ApiResponse(code = 409, message = "Already Exists!")
    })
    @PostMapping
    public ResponseEntity<Object> clientRegistration(@PathVariable Long businessId, @RequestBody ClientRegistrationDto clientRegistrationDto) {
        // TODO: 01/07/22 Validate phone number using OTP
        logger.trace("Creating new client entity by business id =>{} with registration data => {}", businessId, clientRegistrationDto);
        return new ApiResponseDto()
                .generateResponse(HttpStatus.CREATED, clientService.add(businessId, clientRegistrationDto)
                        , "Successfully registered");
    }

    /**
     * This method is used to update a specific client for business.
     *
     * @param businessId -id of the business entity to update client. Must not be null.
     * @param id         -id of the client entity to find. Must not be null.
     * @param clientDto  -dto to be updated
     * @return json
     */
    @ApiOperation(value = "Update a client by Business id and Client id", notes = "Provide a specific business id and client id to update information ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 404, message = "Resource doesn't exist!"),
            @ApiResponse(code = 500, message = "Operation failed!")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateClient(@PathVariable Long businessId, @PathVariable Long id, @RequestBody ClientDto clientDto) {
        logger.trace("Updating client entity by business id => {} and client id => {} with data => {}", businessId, id, clientDto);
        return new ApiResponseDto().generateResponse(
                HttpStatus.OK, clientService.update(businessId, id, clientDto), "Successfully updated");
    }

    /**
     * This method is used to delete a specific business {@link ClientDto}
     *
     * @param businessId -id of the business entity to delete client. Must not be null.
     * @param id         -id of the client entity to find. Must not be null.
     * @return json
     */
    @ApiOperation(value = "Delete a client by Business id and Client id",
            notes = "Provide a specific business id and client id to delete information ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Resource doesn't exist!")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable Long businessId, @PathVariable Long id) {
        logger.trace("Deleting client entity by business id => {} and client id =>{}", businessId, id);
        clientService.delete(businessId, id);
        return new ApiResponseDto().generateResponse(HttpStatus.OK, null, "Successfully deleted");
    }
}
