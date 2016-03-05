package com.team202forever.sharemyweek.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.team202forever.sharemyweek.data.repository.UserRepository;
import com.team202forever.sharemyweek.data.repository.WeekRepository;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event extends ViewModel {

    public static WeekRepository weekRepository;

    public static UserRepository userRepository;

    @NotEmpty
    public String title;

    private String description;

    private Float maxBudget;

    private Float minBudget;

    @Valid
    @NotNull
    private DateTimeRange dateTimeRange;

    private String eventUrl;

    @Transient
    private Image image;

    @NotNull(message = "Week id must be valid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ObjectId weekId;

    @NotNull(message = "Owner id must be valid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ObjectId ownerId;

    public void setOwnerId(String ownerId) {
        this.ownerId = new HashId(ownerId).toObjectId();
    }

    public HashId getOwnerId() {
        if (ownerId == null) {
            return null;
        }
        return new HashId(ownerId);
    }

    public User getOwner() {
        if (ownerId == null) {
            return null;
        }
        return userRepository.findOne(getOwnerId());
    }

    public void setWeekId(String weekId) {
        this.weekId = new HashId(weekId).toObjectId();
    }

    public HashId getWeekId() {
        if (weekId == null) {
            return null;
        }
        return new HashId(weekId);
    }

    @JsonIgnore
    public Week getWeek() {
        if (weekId == null) {
            return null;
        }
        return weekRepository.findOne(getWeekId());
    }
}
