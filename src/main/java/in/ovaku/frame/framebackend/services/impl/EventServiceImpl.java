package in.ovaku.frame.framebackend.services.impl;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.dtos.commons.EventDto;
import in.ovaku.frame.framebackend.entities.Client;
import in.ovaku.frame.framebackend.entities.Event;
import in.ovaku.frame.framebackend.exceptions.ResourceNotFoundException;
import in.ovaku.frame.framebackend.repositories.EventRepository;
import in.ovaku.frame.framebackend.services.ClientService;
import in.ovaku.frame.framebackend.services.EventService;
import in.ovaku.frame.framebackend.utils.converters.EventConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class implement an interface {@link EventService}
 * It contain different business logic for Event
 *
 * @author sohan
 * @version 1.0
 * @since 06/07/22
 */
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventConverter eventConverter;
    private final ClientService clientService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public EventServiceImpl(EventRepository eventRepository, EventConverter eventConverter, ClientService clientService) {
        this.eventRepository = eventRepository;
        this.eventConverter = eventConverter;
        this.clientService = clientService;
    }

    /**
     * This method return the list of {@link EventDto}.
     *
     * @param clientId -id of the {@link Client} entity to get event.Must not be null.
     * @param isActive - true or false to get active or inactive event.
     * @return list of eventDto
     */
    @Override
    public List<EventDto> getAll(Long clientId, Boolean isActive) {
        logger.debug("Into getAll service method with client id=> {}", clientId);
        List<Event> eventList;
        if (isActive) {
            eventList = eventRepository.findAllActiveByClientId(clientId);
        } else {
            eventList = eventRepository.findAllByClientId(clientId);
        }
        if (eventList.isEmpty()) {
            logger.error("No data available!");
            throw new ResourceNotFoundException("No data available!");
        }
        logger.debug("Fetched event list => {}", eventList);
        return eventList.stream()
                .map(eventConverter::eventToEventDto)
                .collect(Collectors.toList());
    }

    /**
     * This method return a specific {@link EventDto} entity identified by
     * the given {@link Client} id and {@link Event} id.
     *
     * @param clientId - id of the {@link Client} entity to get event.Must not be null.
     * @param id       -id of the event entity to find. Must not be null.
     * @param isActive - true or false to get active or inactive event.
     * @return eventDto
     */
    @Override
    public EventDto getById(Long clientId, Long id, Boolean isActive) {
        logger.debug("Into get entity service method with client id => {} and event id => {}", clientId, id);
        Optional<Event> optionalEvent;
        if (isActive) {
            optionalEvent = eventRepository.findActiveById(clientId, id);
        } else {
            optionalEvent = eventRepository.findByClientIdAndId(clientId, id);
        }
        if (optionalEvent.isEmpty()) {
            logger.error("No data available!");
            throw new ResourceNotFoundException("No data available!");
        }
        Event event = optionalEvent.get();
        logger.debug("Fetched event entity => {}", event);
        EventDto eventDto = eventConverter.eventToEventDto(event);
        logger.debug("Converted DTO => {} from event entity", eventDto);
        return eventDto;
    }

    /**
     * This method create new {@link Event} for {@link Client}.
     *
     * @param clientId - id of {@link Client} entity to add {@link Event}.Must not be null.
     * @param eventDto -eventDto to be created.
     * @return eventDto
     */
    @Override
    public EventDto add(Long clientId, EventDto eventDto) {
        logger.debug("Into add entity service method with client id => {} and data =>{}", clientId, eventDto);
        Event event = eventConverter.eventDtoToEvent(eventDto);
        logger.debug("Converted event entity => {} from DTO", event);
        event.setClient(clientService.getById(clientId));
        event.setStaff(null);
        event = eventRepository.save(event);
        logger.debug("Record saved =>{} in DB", event);
        EventDto createDto = eventConverter.eventToEventDto(event);
        logger.debug("Converted DTO => {} from event entity", createDto);
        return createDto;
    }

    /**
     * This method update {@link Event} identified by {@link Client} id and {@link Event} id.
     *
     * @param clientId - id of the {@link Client} entity to update event.Must not be null.
     * @param id       -id of the event entity to find. Must not be null.
     * @param eventDto - {@link EventDto} to be updated.
     * @return eventDto
     */
    @Override
    public EventDto update(Long clientId, Long id, EventDto eventDto) {
        logger.debug("Into update entity service method with client id => {}," +
                " staff id => {} and data => {}", clientId, id, eventDto);
        //Validate.
        if (!eventRepository.existsById(id)) {
            logger.error("Resource doesn't exist!");
            throw new ResourceNotFoundException("Resource doesn't exist!");
        }

        Event event = eventConverter.eventDtoToEvent(eventDto);
        logger.debug("Converted event entity => {} from DTO => {}", event, eventDto);
        event.setId(id);
        event.setClient(clientService.getById(clientId));
        event.setStaff(null);
        event = eventRepository.save(event);
        logger.debug("Event record updated in DB=>{}", event);
        EventDto dto = eventConverter.eventToEventDto(event);
        logger.debug("Converted DTO => {} from event entity", dto);
        return dto;
    }

    /**
     * This method delete {@link Event} entity identified by {@link Client} id and {@link Event} id.
     *
     * @param clientId - id of the {@link Client} entity to delete event.Must not be null.
     * @param id       -id of the event entity to delete. Must not be null.
     * @return inactive eventDto entity
     */
    @Override
    public Boolean delete(Long clientId, Long id) {
        logger.debug("Into delete entity service method with client id => {} and event id => {}", clientId, id);
        //Validate
        if (eventRepository.findActiveById(clientId, id).isEmpty()) {
            logger.error("Resource doesn't exist!");
            throw new ResourceNotFoundException("Resource doesn't exist!");
        }
        Event event = eventRepository.getById(id);
        logger.debug("Fetching staff entity by id => {} from DB using repository", id);
        event.setIsActive(false);
        event = eventRepository.save(event);
        logger.debug("Event entity deleted from DB=>{}", event);
        return true;
    }
}
