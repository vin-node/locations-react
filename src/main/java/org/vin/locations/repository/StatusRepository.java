package org.vin.locations.repository;

import org.vin.locations.domain.Status;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the Status entity.
 */
@Repository
public class StatusRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Status> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public StatusRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Status.class);
        this.findAllStmt = session.prepare("SELECT * FROM status");
        this.truncateStmt = session.prepare("TRUNCATE status");
    }

    public List<Status> findAll() {
        List<Status> statusesList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Status status = new Status();
                status.setId(row.getUUID("id"));
                status.setCreatedAt(row.get("createdAt", Instant.class));
                status.setText(row.getString("text"));
                status.setSource(row.getString("source"));
                status.setIsTruncated(row.getBool("isTruncated"));
                status.setInReplyToStatusId(row.getLong("inReplyToStatusId"));
                status.setInReplyToUserId(row.getLong("inReplyToUserId"));
                status.setIsFavorited(row.getBool("isFavorited"));
                status.setIsRetweeted(row.getBool("isRetweeted"));
                status.setFavoriteCount(row.getInt("favoriteCount"));
                status.setInReplyToScreenName(row.getString("inReplyToScreenName"));
                status.setRetweetCount(row.getLong("retweetCount"));
                status.setIsPossiblySensitive(row.getBool("isPossiblySensitive"));
                status.setLangu(row.getString("langu"));
                status.setContributorsIDs(row.getLong("contributorsIDs"));
                status.setCurrentUserRetweetId(row.getLong("currentUserRetweetId"));
                return status;
            }
        ).forEach(statusesList::add);
        return statusesList;
    }

    public Status findOne(UUID id) {
        return mapper.get(id);
    }

    public Status save(Status status) {
        if (status.getId() == null) {
            status.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Status>> violations = validator.validate(status);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(status);
        return status;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
