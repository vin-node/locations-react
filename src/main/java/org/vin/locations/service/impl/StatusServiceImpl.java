package org.vin.locations.service.impl;

import org.vin.locations.service.StatusService;
import org.vin.locations.domain.Status;
import org.vin.locations.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service Implementation for managing Status.
 */
@Service
public class StatusServiceImpl implements StatusService{

    private final Logger log = LoggerFactory.getLogger(StatusServiceImpl.class);

    private final StatusRepository statusRepository;
    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    /**
     * Save a status.
     *
     * @param status the entity to save
     * @return the persisted entity
     */
    @Override
    public Status save(Status status) {
        log.debug("Request to save Status : {}", status);
        return statusRepository.save(status);
    }

    /**
     *  Get all the statuses.
     *
     *  @return the list of entities
     */
    @Override
    public List<Status> findAll() {
        log.debug("Request to get all Statuses");
        return statusRepository.findAll();
    }

    /**
     *  Get one status by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Status findOne(String id) {
        log.debug("Request to get Status : {}", id);
        return statusRepository.findOne(UUID.fromString(id));
    }

    /**
     *  Delete the  status by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Status : {}", id);
        statusRepository.delete(UUID.fromString(id));
    }
}
