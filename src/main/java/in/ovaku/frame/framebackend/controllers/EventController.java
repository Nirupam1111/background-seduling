package in.ovaku.frame.framebackend.controllers;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.dtos.commons.EventDto;
import in.ovaku.frame.framebackend.dtos.commons.StaffDto;
import in.ovaku.frame.framebackend.dtos.responses.ApiResponseDto;
import in.ovaku.frame.framebackend.services.EventService;
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
 * This class is a controller class of Event.
 * It processes the request and return the view as response.
 *
 * @author SOHAN
 * @version 1.0
 * @since 1/07/22
 */
@RestController
@RequestMapping("{clientId}/events")
@Api(tags = "Event Controller", value = "EventController", description = "Controller for Client's event")
public class EventController {
    private final EventService eventService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * This method is used to get all active or inactive {@link EventDto} for client.
     * It by default get all active {@link EventDto}.
     *
     * @param clientId -id of the client entity to get staff.Must not be null.
     * @return json
     */
    @ApiOperation(value = "Get list of all Event for client",
            notes = "View list of all active or inactive Event information ", response = ArrayList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully data retrieved"),
            @ApiResponse(code = 404, message = "No data available!")
    })
    @GetMapping
    public ResponseEntity<Object> getAll(@PathVariable Long clientId,
                                         @RequestParam(required = false, defaultValue = "true") Boolean isActive) {
        logger.trace("Getting all event entity list of client");
        return new ApiResponseDto()
                .generateResponse(HttpStatus.OK, eventService.getAll(clientId, isActive),
                        "Successfully data retrieved");
    }

    /**
     * This method is used to get a specific client {@link EventDto}
     * It by default get active {@link EventDto}.
     *
     * @param clientId- id of the client entity to get staff. Must not be null.
     * @param id        -id of the event entity to find. Must not be null.
     * @return json
     */
    @ApiOperation(value = "Find Event by Client id and Event id",
            notes = "Provide a specific client id and Event id to get information ", response = StaffDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully data retrieved"),
            @ApiResponse(code = 404, message = "No data available!")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEvent(@PathVariable Long clientId, @PathVariable Long id,
                                           @RequestParam(required = false, defaultValue = "true") Boolean isActive) {
        logger.trace("Getting event entity by client id=>{} and event id=> {}", clientId, id);
        return new ApiResponseDto()
                .generateResponse(HttpStatus.OK, eventService.getById(clientId, id, isActive)
                        , "Successfully data retrieved");
    }

    /**
     * This method create new event for client.
     *
     * @param clientId  - id of the client entity to create event. Must not be null.
     * @param eventDto- dto to be registered
     * @return json
     */
    @ApiOperation(value = "Create new Event for client", notes = "Create new Event for Client")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered"),
            @ApiResponse(code = 409, message = "Already Exists!")
    })
    @PostMapping
    public ResponseEntity<Object> createEvent(@PathVariable Long clientId, @RequestBody EventDto eventDto) {
        logger.trace("Creating new event entity by client id =>{} with creation data => {}", clientId, eventDto);
        return new ApiResponseDto()
                .generateResponse(HttpStatus.CREATED, eventService.add(clientId, eventDto)
                        , "Successfully created");
    }

    /**
     * This method is used to update a specific event for client.
     *
     * @param clientId -id of the client entity to update event. Must not be null.
     * @param id       -id of the event entity to find. Must not be null.
     * @param eventDto -dto to be updated
     * @return json
     */
    @ApiOperation(value = "Update a event by Client id and Event id", notes = "Provide a specific client id and event id to update information ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 404, message = "Resource doesn't exist!"),
            @ApiResponse(code = 500, message = "Operation failed!")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatEvent(@PathVariable Long clientId, @PathVariable Long id, @RequestBody EventDto eventDto) {
        logger.trace("Updating event entity by client id => {} and event id => {} with data => {}", clientId, id, eventDto);
        return new ApiResponseDto().generateResponse(
                HttpStatus.OK, eventService.update(clientId, id, eventDto), "Successfully updated");
    }

    /**
     * This method is used to delete a specific client {@link EventDto}
     *
     * @param clientId -id of the client entity to delete event. Must not be null.
     * @param id       -id of the event entity to find. Must not be null.
     * @return json
     */
    @ApiOperation(value = "Delete a event by Client id and Event id",
            notes = "Provide a specific client id and event id to delete information ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Resource doesn't exist!")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable Long clientId, @PathVariable Long id) {
        logger.trace("Deleting event entity by client id => {} and event id =>{}", clientId, id);
        eventService.delete(clientId, id);
        return new ApiResponseDto().generateResponse(HttpStatus.OK, null, "Successfully deleted");
    }
}
