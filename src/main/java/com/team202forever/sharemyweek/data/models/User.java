package com.team202forever.sharemyweek.data.models;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.Valid;
import java.util.*;

@Data
public class User extends ViewModel {

    @Email
    @NotEmpty
    private String email;

    private String name;
    
    private float maxBudget;
    
    @Valid
    private Set<DateTimeRange> availDateTimeRanges = new HashSet<>();
    
    @DBRef
    private Set<Week> weeks = new HashSet<>();

}
