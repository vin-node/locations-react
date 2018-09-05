package org.vin.locations.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * A Status.
 */
@Table(name = "status")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;
    @PartitionKey
    private UUID id;

    private Instant createdAt;

    private String text;

    private String source;

    private Boolean isTruncated;

    private Long inReplyToStatusId;

    private Long inReplyToUserId;

    private Boolean isFavorited;

    private Boolean isRetweeted;

    private Integer favoriteCount;

    private String inReplyToScreenName;

    private Long retweetCount;

    private Boolean isPossiblySensitive;

    private String langu;

    private Long contributorsIDs;

    private Long currentUserRetweetId;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Status createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public Status text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public Status source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean isIsTruncated() {
        return isTruncated;
    }

    public Status isTruncated(Boolean isTruncated) {
        this.isTruncated = isTruncated;
        return this;
    }

    public void setIsTruncated(Boolean isTruncated) {
        this.isTruncated = isTruncated;
    }

    public Long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public Status inReplyToStatusId(Long inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
        return this;
    }

    public void setInReplyToStatusId(Long inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    public Long getInReplyToUserId() {
        return inReplyToUserId;
    }

    public Status inReplyToUserId(Long inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
        return this;
    }

    public void setInReplyToUserId(Long inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    public Boolean isIsFavorited() {
        return isFavorited;
    }

    public Status isFavorited(Boolean isFavorited) {
        this.isFavorited = isFavorited;
        return this;
    }

    public void setIsFavorited(Boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    public Boolean isIsRetweeted() {
        return isRetweeted;
    }

    public Status isRetweeted(Boolean isRetweeted) {
        this.isRetweeted = isRetweeted;
        return this;
    }

    public void setIsRetweeted(Boolean isRetweeted) {
        this.isRetweeted = isRetweeted;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public Status favoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
        return this;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public Status inReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
        return this;
    }

    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    public Long getRetweetCount() {
        return retweetCount;
    }

    public Status retweetCount(Long retweetCount) {
        this.retweetCount = retweetCount;
        return this;
    }

    public void setRetweetCount(Long retweetCount) {
        this.retweetCount = retweetCount;
    }

    public Boolean isIsPossiblySensitive() {
        return isPossiblySensitive;
    }

    public Status isPossiblySensitive(Boolean isPossiblySensitive) {
        this.isPossiblySensitive = isPossiblySensitive;
        return this;
    }

    public void setIsPossiblySensitive(Boolean isPossiblySensitive) {
        this.isPossiblySensitive = isPossiblySensitive;
    }

    public String getLangu() {
        return langu;
    }

    public Status langu(String langu) {
        this.langu = langu;
        return this;
    }

    public void setLangu(String langu) {
        this.langu = langu;
    }

    public Long getContributorsIDs() {
        return contributorsIDs;
    }

    public Status contributorsIDs(Long contributorsIDs) {
        this.contributorsIDs = contributorsIDs;
        return this;
    }

    public void setContributorsIDs(Long contributorsIDs) {
        this.contributorsIDs = contributorsIDs;
    }

    public Long getCurrentUserRetweetId() {
        return currentUserRetweetId;
    }

    public Status currentUserRetweetId(Long currentUserRetweetId) {
        this.currentUserRetweetId = currentUserRetweetId;
        return this;
    }

    public void setCurrentUserRetweetId(Long currentUserRetweetId) {
        this.currentUserRetweetId = currentUserRetweetId;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Status status = (Status) o;
        if (status.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), status.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Status{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", text='" + getText() + "'" +
            ", source='" + getSource() + "'" +
            ", isTruncated='" + isIsTruncated() + "'" +
            ", inReplyToStatusId='" + getInReplyToStatusId() + "'" +
            ", inReplyToUserId='" + getInReplyToUserId() + "'" +
            ", isFavorited='" + isIsFavorited() + "'" +
            ", isRetweeted='" + isIsRetweeted() + "'" +
            ", favoriteCount='" + getFavoriteCount() + "'" +
            ", inReplyToScreenName='" + getInReplyToScreenName() + "'" +
            ", retweetCount='" + getRetweetCount() + "'" +
            ", isPossiblySensitive='" + isIsPossiblySensitive() + "'" +
            ", langu='" + getLangu() + "'" +
            ", contributorsIDs='" + getContributorsIDs() + "'" +
            ", currentUserRetweetId='" + getCurrentUserRetweetId() + "'" +
            "}";
    }
}
