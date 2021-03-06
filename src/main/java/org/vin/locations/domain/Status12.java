/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.vin.locations.domain;

import java.util.Arrays;
import java.util.Date;

import org.vin.locations.service.util.ParseUtil;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.HttpResponse;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.MediaEntity;
import twitter4j.MediaEntityJSONImpl;
import twitter4j.Place;
import twitter4j.ResponseList;
import twitter4j.Scopes;
import twitter4j.ScopesImpl;
import twitter4j.SymbolEntity;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;
import twitter4j.conf.Configuration;

/**
 * A data class representing one single status of a user.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
/*package*/ final class Status12 {
    private static final long serialVersionUID = -6461195536943679985L;

    private Date createdAt;
    private long id;
    private String text;
    private String source;
    private boolean isTruncated;
    private long inReplyToStatusId;
    private long inReplyToUserId;
    private boolean isFavorited;
    private boolean isRetweeted;
    private int favoriteCount;
    private String inReplyToScreenName;
    private GeoLocation geoLocation = null;
    private Place place = null;
    // this field should be int in theory, but left as long for the serialized form compatibility - TFJ-790
    private long retweetCount;
    private boolean isPossiblySensitive;
    private String lang;

    private long[] contributorsIDs;

    private Status12 retweetedStatus;
    private UserMentionEntity[] userMentionEntities;
    private URLEntity[] urlEntities;
    private HashtagEntity[] hashtagEntities;
    private MediaEntity[] mediaEntities;
    private MediaEntity[] extendedMediaEntities;
    private SymbolEntity[] symbolEntities;
    private long currentUserRetweetId = -1L;
    private Scopes scopes;
    private User user = null;

    /*package*/Status12(HttpResponse res, Configuration conf) throws TwitterException {
        JSONObject json = res.asJSONObject();
        init(json);
    }

    /*package*/Status12(JSONObject json, Configuration conf) throws TwitterException {
        super();
        init(json);
    }

    /*package*/ Status12(JSONObject json) throws TwitterException {
        super();
        init(json);
    }

    /* Only for serialization purposes. */
    /*package*/ Status12() {

    }

    private void init(JSONObject json) throws TwitterException {
        id = ParseUtil.getLong("id", json);
        source = ParseUtil.getUnescapedString("source", json);
        createdAt = ParseUtil.getDate("created_at", json);
        isTruncated = ParseUtil.getBoolean("truncated", json);
        inReplyToStatusId = ParseUtil.getLong("in_reply_to_status_id", json);
        inReplyToUserId = ParseUtil.getLong("in_reply_to_user_id", json);
        isFavorited = ParseUtil.getBoolean("favorited", json);
        isRetweeted = ParseUtil.getBoolean("retweeted", json);
        inReplyToScreenName = ParseUtil.getUnescapedString("in_reply_to_screen_name", json);
        retweetCount = ParseUtil.getLong("retweet_count", json);
        favoriteCount = ParseUtil.getInt("favorite_count", json);
        isPossiblySensitive = ParseUtil.getBoolean("possibly_sensitive", json);
        try {
            if (!json.isNull("user")) {
               // user = new UserJSONImpl(json.getJSONObject("user"));
            }
           // geoLocation = JSONImplFactory.createGeoLocation(json);
            if (!json.isNull("place")) {
                //place = new PlaceJSONImpl(json.getJSONObject("place"));
            }

            if (!json.isNull("retweeted_status")) {
                //retweetedStatus = new StatusJSONImpl(json.getJSONObject("retweeted_status"));
            }
            if (!json.isNull("contributors")) {
                JSONArray contributorsArray = json.getJSONArray("contributors");
                contributorsIDs = new long[contributorsArray.length()];
                for (int i = 0; i < contributorsArray.length(); i++) {
                    contributorsIDs[i] = Long.parseLong(contributorsArray.getString(i));
                }
            } else {
                contributorsIDs = new long[0];
            }
            if (!json.isNull("entities")) {
                JSONObject entities = json.getJSONObject("entities");
                int len;
                if (!entities.isNull("user_mentions")) {
                    JSONArray userMentionsArray = entities.getJSONArray("user_mentions");
                    len = userMentionsArray.length();
                    userMentionEntities = new UserMentionEntity[len];
                    for (int i = 0; i < len; i++) {
                        //userMentionEntities[i] = new UserMentionEntityJSONImpl(userMentionsArray.getJSONObject(i));
                    }
                }
                if (!entities.isNull("urls")) {
                    JSONArray urlsArray = entities.getJSONArray("urls");
                    len = urlsArray.length();
                    urlEntities = new URLEntity[len];
                    for (int i = 0; i < len; i++) {
                        //urlEntities[i] = new URLEntityJSONImpl(urlsArray.getJSONObject(i));
                    }
                }

                if (!entities.isNull("hashtags")) {
                    JSONArray hashtagsArray = entities.getJSONArray("hashtags");
                    len = hashtagsArray.length();
                    hashtagEntities = new HashtagEntity[len];
                    for (int i = 0; i < len; i++) {
                       // hashtagEntities[i] = new HashtagEntityJSONImpl(hashtagsArray.getJSONObject(i));
                    }
                }

                if (!entities.isNull("symbols")) {
                    JSONArray hashtagsArray = entities.getJSONArray("symbols");
                    len = hashtagsArray.length();
                    symbolEntities = new SymbolEntity[len];
                    for (int i = 0; i < len; i++) {
                        // HashtagEntityJSONImpl also implements SymbolEntities
                       // symbolEntities[i] = new HashtagEntityJSONImpl(hashtagsArray.getJSONObject(i));
                    }
                }

                if (!entities.isNull("media")) {
                    JSONArray mediaArray = entities.getJSONArray("media");
                    len = mediaArray.length();
                    mediaEntities = new MediaEntity[len];
                    for (int i = 0; i < len; i++) {
                       //mediaEntities[i] = new MediaEntityJSONImpl(mediaArray.getJSONObject(i));
                    }
                }
            }
            if (!json.isNull("extended_entities")) {
                JSONObject extendedEntities = json.getJSONObject("extended_entities");
                if (!extendedEntities.isNull("media")) {
                    JSONArray mediaArray = extendedEntities.getJSONArray("media");
                    final int len = mediaArray.length();
                    extendedMediaEntities = new MediaEntity[len];
                    for (int i = 0; i < len; i++) {
                      //  extendedMediaEntities[i] = new MediaEntityJSONImpl(mediaArray.getJSONObject(i));
                    }
                }
            }

            userMentionEntities = userMentionEntities == null ? new UserMentionEntity[0] : userMentionEntities;
            urlEntities = urlEntities == null ? new URLEntity[0] : urlEntities;
            hashtagEntities = hashtagEntities == null ? new HashtagEntity[0] : hashtagEntities;
            symbolEntities = symbolEntities == null ? new SymbolEntity[0] : symbolEntities;
            mediaEntities = mediaEntities == null ? new MediaEntity[0] : mediaEntities;
            extendedMediaEntities = extendedMediaEntities == null ? new MediaEntity[0] : extendedMediaEntities;
          //  text = HTMLEntity.unescapeAndSlideEntityIncdices(json.getString("text"), userMentionEntities,
                //    urlEntities, hashtagEntities, mediaEntities);
            if (!json.isNull("current_user_retweet")) {
                currentUserRetweetId = json.getJSONObject("current_user_retweet").getLong("id");
            }
            if (!json.isNull("lang")) {
                lang = ParseUtil.getUnescapedString("lang", json);
            }

            if (!json.isNull("scopes")) {
                JSONObject scopesJson = json.getJSONObject("scopes");
                if (!scopesJson.isNull("place_ids")) {
                    JSONArray placeIdsArray = scopesJson.getJSONArray("place_ids");
                    int len = placeIdsArray.length();
                    String[] placeIds = new String[len];
                    for (int i = 0; i < len; i++) {
                        placeIds[i] = placeIdsArray.getString(i);
                    }
                    scopes = new ScopesImpl(placeIds);
                }
            }
        } catch (JSONException jsone) {
            throw new TwitterException(jsone);
        }
    }

  //  
    public int compareTo(Status12 that) {
        long delta = this.id - that.getId();
        if (delta < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        } else if (delta > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) delta;
    }

   // 
    public Date getCreatedAt() {
        return this.createdAt;
    }

   // 
    public long getId() {
        return this.id;
    }

   // 
    public String getText() {
        return this.text;
    }

  //  
    public String getSource() {
        return this.source;
    }


   // 
    public boolean isTruncated() {
        return isTruncated;
    }

   // 
    public long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

   // 
    public long getInReplyToUserId() {
        return inReplyToUserId;
    }

   // 
    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

   /// 
    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

   // 
    public Place getPlace() {
        return place;
    }

   // 
    public long[] getContributors() {
        return contributorsIDs;
    }

    //
    public boolean isFavorited() {
        return isFavorited;
    }

   // 
    public boolean isRetweeted() {
        return isRetweeted;
    }

   // 
    public int getFavoriteCount() {
        return favoriteCount;
    }

   // 
    public User getUser() {
        return user;
    }

    //
    public boolean isRetweet() {
        return retweetedStatus != null;
    }

   // 
    public Status12 getRetweetedStatus() {
        return retweetedStatus;
    }

   // 
    public int getRetweetCount() {
        return (int) retweetCount;
    }

   // 
    public boolean isRetweetedByMe() {
        return currentUserRetweetId != -1L;
    }

    //
    public long getCurrentUserRetweetId() {
        return currentUserRetweetId;
    }

   // 
    public boolean isPossiblySensitive() {
        return isPossiblySensitive;
    }

    //
    public UserMentionEntity[] getUserMentionEntities() {
        return userMentionEntities;
    }

   // 
    public URLEntity[] getURLEntities() {
        return urlEntities;
    }

   // 
    public HashtagEntity[] getHashtagEntities() {
        return hashtagEntities;
    }

   // 
    public MediaEntity[] getMediaEntities() {
        return mediaEntities;
    }

    
    public MediaEntity[] getExtendedMediaEntities() {
        return extendedMediaEntities;
    }

    
    public SymbolEntity[] getSymbolEntities() {
        return symbolEntities;
    }

    public Scopes getScopes() {
        return scopes;
    }

    public String getLang() {
        return lang;
    }

    /*package*/
    static ResponseList<Status12> createStatusList(HttpResponse res, Configuration conf) throws TwitterException {
        try {
            if (conf.isJSONStoreEnabled()) {
                //TwitterObjectFactory.clearThreadLocalMap();
            }
            JSONArray list = res.asJSONArray();
            int size = list.length();
            ResponseList<Status12> statuses = null;// = new ResponseListImpl<Status>(size, res);
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                Status12 status = new Status12(json);
                if (conf.isJSONStoreEnabled()) {
                   // TwitterObjectFactory.registerJSONObject(status, json);
                }
                statuses.add(status);
            }
            if (conf.isJSONStoreEnabled()) {
                //TwitterObjectFactory.registerJSONObject(statuses, list);
            }
            return statuses;
        } catch (JSONException jsone) {
            throw new TwitterException(jsone);
        }
    }

    
    public int hashCode() {
        return (int) id;
    }

    
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return obj instanceof Status12 && ((Status12) obj).getId() == this.id;
    }

    
    public String toString() {
        return "StatusJSONImpl{" +
                "createdAt=" + createdAt +
                ", id=" + id +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                ", isTruncated=" + isTruncated +
                ", inReplyToStatusId=" + inReplyToStatusId +
                ", inReplyToUserId=" + inReplyToUserId +
                ", isFavorited=" + isFavorited +
                ", isRetweeted=" + isRetweeted +
                ", favoriteCount=" + favoriteCount +
                ", inReplyToScreenName='" + inReplyToScreenName + '\'' +
                ", geoLocation=" + geoLocation +
                ", place=" + place +
                ", retweetCount=" + retweetCount +
                ", isPossiblySensitive=" + isPossiblySensitive +
                ", lang='" + lang + '\'' +
                ", contributorsIDs=" + Arrays.toString(contributorsIDs) +
                ", retweetedStatus=" + retweetedStatus +
                ", userMentionEntities=" + Arrays.toString(userMentionEntities) +
                ", urlEntities=" + Arrays.toString(urlEntities) +
                ", hashtagEntities=" + Arrays.toString(hashtagEntities) +
                ", mediaEntities=" + Arrays.toString(mediaEntities) +
                ", symbolEntities=" + Arrays.toString(symbolEntities) +
                ", currentUserRetweetId=" + currentUserRetweetId +
                ", user=" + user +
                '}';
    }
}
