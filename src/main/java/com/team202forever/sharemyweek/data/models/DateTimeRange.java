package com.team202forever.sharemyweek.data.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class DateTimeRange {

    @NotNull(message = "Start date must not be empty")
    private Date start;

    @NotNull(message = "End date must not be empty")
    private Date end;

}
