package com.team202forever.sharemyweek.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team202forever.sharemyweek.data.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class WeekUser {

    public static UserRepository userRepository;

    private String name;
    
    private String color;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    private ObjectId userId;

    @Transient
    private User userInfo;

    @NotNull
    private WeekUserRole role = WeekUserRole.USER;

    @RestResource(exported = false)
    public User getUserInfo() {
        if (userInfo == null && userId != null) {
            userInfo = userRepository.findOne(new HashId(userId));
        }
        return userInfo;
    }

    public void setUserInfo(User user) {
        User stored = userRepository.findOne(user.getHashId());
        if (stored != null) {
            userId = user.getHashId().toObjectId();
        }
        userInfo = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeekUser)) return false;

        WeekUser weekUser = (WeekUser) o;

        return userId != null ? userId.equals(weekUser.userId) : weekUser.userId == null;

    }

    public boolean equalsUser(WeekUser user) {
        if (user == null) {
            return false;
        }

        if (name == null && user.getName() != null) {
            return false;
        }
        if (name != null && !name.equals(user.getName())) {
            return false;
        }

        if (userInfo == null && user.getUserInfo() != null) {
            return false;
        }
        if (userInfo != null && !userInfo.equalsUser(user.getUserInfo())) {
            return false;
        }

        if (role == null && user.getRole() != null) {
            return false;
        }
        if (role != null && role != user.getRole()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return userInfo != null ? userInfo.hashCode() : 0;
    }
}
