package in.ovaku.frame.framebackend.repositories;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.entities.Client;
import in.ovaku.frame.framebackend.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * This is a repository interface which provides crud operation for {@link Event}.
 *
 * @author Sohan
 * @version 1.0
 * @since 06/07/22
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Find {@link Event} entity by {@link Client} id and {@link Event} id.
     *
     * @param clientId - id of the {@link Client} entity.Must not be null.
     * @param id       -id of the {@link Event} entity. Must not be null.
     * @return Optional
     */
    Optional<Event> findByClientIdAndId(Long clientId, Long id);

    /**
     * Find active {@link Event} entity {@link Client} id and {@link Event} id.
     *
     * @param clientId - id of the {@link Client} entity.Must not be null.
     * @param id       -id of the {@link Event} entity. Must not be null.
     * @return Optional
     */
    @Query("SELECT e from Event e where e.client.id=:clientId AND e.id=:id AND e.isActive=true")
    Optional<Event> findActiveById(@Param("clientId") Long clientId, @Param("id") Long id);


    /**
     * Find all {@link Event} entity by {@link Client} id.
     *
     * @param clientId - id of the {@link Client} entity.Must not be null.
     * @return list of event.
     */
    List<Event> findAllByClientId(Long clientId);

    /**
     * Find all active {@link Event} entity by {@link Client} id.
     *
     * @param clientId - id of the {@link Client} entity.Must not be null.
     * @return list of active event.
     */
    @Query("SELECT e from Event e where e.client.id=:id AND e.isActive=true")
    List<Event> findAllActiveByClientId(@Param("id") Long clientId);
}
