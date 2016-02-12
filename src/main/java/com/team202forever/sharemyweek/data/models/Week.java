package com.team202forever.sharemyweek.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.Date;
import java.util.Set;

@Data
public class Week extends ViewModel {

    @NotEmpty
    @Valid
    private Set<User> users;

    @JsonIgnore
    private Date creationDate;

    public Week() {
        creationDate = new Date();
    }

    @JsonProperty
    public Date getCreatedOn() {
        return creationDate;
    }
}
