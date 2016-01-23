package com.team202forever.sharemyweek.data.models;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

@Data
public class User extends ViewModel {

    @Email
    @NotEmpty
    private String email;

    private float maxBudget;

    @Valid
    private List<DateTimeRange> availDateTimeRanges;

}
