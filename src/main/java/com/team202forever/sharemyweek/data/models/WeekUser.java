package com.team202forever.sharemyweek.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class WeekUser {

    private String name;
    
    private String color;
    
    @NotNull
    @Valid
    private User userInfo;

    @NotNull
    private WeekUserRole role = WeekUserRole.USER;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeekUser)) return false;

        WeekUser weekUser = (WeekUser) o;

        return userInfo != null ? userInfo.equals(weekUser.userInfo) : weekUser.userInfo == null;

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
