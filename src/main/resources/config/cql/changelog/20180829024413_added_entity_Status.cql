
CREATE TABLE IF NOT EXISTS status (
    id uuid,
    createdAt timestamp,
    text text,
    source text,
    isTruncated boolean,
    inReplyToStatusId bigint,
    inReplyToUserId bigint,
    isFavorited boolean,
    isRetweeted boolean,
    favoriteCount int,
    inReplyToScreenName text,
    retweetCount bigint,
    isPossiblySensitive boolean,
    langu text,
    contributorsIDs bigint,
    currentUserRetweetId bigint,
    PRIMARY KEY(id)
);
