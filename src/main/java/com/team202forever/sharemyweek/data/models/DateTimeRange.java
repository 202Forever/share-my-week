package com.team202forever.sharemyweek.data.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@EqualsAndHashCode
public class DateTimeRange {

    @NotNull(message = "Start date must not be empty")
    private Date start;

    @NotNull(message = "End date must not be empty")
    private Date end;

}
