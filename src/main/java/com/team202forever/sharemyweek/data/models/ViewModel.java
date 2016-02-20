package com.team202forever.sharemyweek.data.models;

import lombok.Data;

import org.bson.types.ObjectId;
import org.hashids.Hashids;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

@Data
public abstract class ViewModel {

    @Id
    protected HashId hashId = new HashId();

    public static class HashId implements Serializable {

        @Transient
        private String hashId;

        public HashId() {
            this(ObjectId.get());
        }

        public HashId(String hashId) {
            this.hashId = hashId;
        }

        public HashId(ObjectId objectId) {
            hashId = new Hashids().encodeHex(objectId.toHexString());
        }

        public ObjectId toObjectId() {
            return new ObjectId(new Hashids().decodeHex(hashId));
        }

        public String toString() {
            return hashId;
        }
    }

}
