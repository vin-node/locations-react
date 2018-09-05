import { BaseEntity } from './../../shared';

export class Status implements BaseEntity {
    constructor(
        public id?: string,
        public createdAt?: any,
        public text?: string,
        public source?: string,
        public isTruncated?: boolean,
        public inReplyToStatusId?: number,
        public inReplyToUserId?: number,
        public isFavorited?: boolean,
        public isRetweeted?: boolean,
        public favoriteCount?: number,
        public inReplyToScreenName?: string,
        public retweetCount?: number,
        public isPossiblySensitive?: boolean,
        public langu?: string,
        public contributorsIDs?: number,
        public currentUserRetweetId?: number,
    ) {
        this.isTruncated = false;
        this.isFavorited = false;
        this.isRetweeted = false;
        this.isPossiblySensitive = false;
    }
}
