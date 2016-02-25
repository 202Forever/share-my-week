package com.team202forever.sharemyweek.data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.hashids.Hashids;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
public class HashId implements Serializable {

    @Transient
    private String hashId;

    public HashId() {
        this(ObjectId.get());
    }

    @JsonCreator
    public HashId(@JsonProperty("hashId") String hashId) {
        this.hashId = hashId;
    }

    public HashId(ObjectId objectId) {
        hashId = new Hashids().encodeHex(objectId.toHexString());
    }

    public ObjectId toObjectId() {
        return new ObjectId(new Hashids().decodeHex(hashId));
    }

    @JsonProperty("hashId")
    public String getId() {
        return hashId;
    }

    public String toString() {
        return hashId;
    }
}
