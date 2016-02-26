package com.team202forever.sharemyweek.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.*;

@Getter
@Setter
public class User extends ViewModel {

    @Email
    @NotEmpty
    private String email;

    private String name;
    
    private float maxBudget;
    
    @Valid
    @Setter(AccessLevel.NONE)
    private Set<DateTimeRange> availDateTimeRanges = new HashSet<>();

    @JsonIgnore
    private Set<ObjectId> weekIds = new HashSet<>();

    public boolean equalsUser(User user) {
        if (user == null) {
            return true;
        }

        if (email == null && user.getEmail() != null) {
            return false;
        }
        if (!email.equals(user.getEmail())) {
            return false;
        }

        if (name == null && user.getName() != null) {
            return false;
        }
        if (!name.equals(user.getName())) {
            return false;
        }

        if (maxBudget != user.getMaxBudget()) {
            return false;
        }

        if (!availDateTimeRanges.equals(user.getAvailDateTimeRanges())) {
            return false;
        }

        return true;
    }

}
