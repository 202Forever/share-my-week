package com.team202forever.sharemyweek.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Week extends ViewModel {

    @NotEmpty
    @Valid
    private Set<User> users = new HashSet<>();

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
