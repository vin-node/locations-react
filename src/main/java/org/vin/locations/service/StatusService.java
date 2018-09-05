package org.vin.locations.service;

import org.vin.locations.domain.Status;
import java.util.List;

/**
 * Service Interface for managing Status.
 */
public interface StatusService {

    /**
     * Save a status.
     *
     * @param status the entity to save
     * @return the persisted entity
     */
    Status save(Status status);

    /**
     *  Get all the statuses.
     *
     *  @return the list of entities
     */
    List<Status> findAll();

    /**
     *  Get the "id" status.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Status findOne(String id);

    /**
     *  Delete the "id" status.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
