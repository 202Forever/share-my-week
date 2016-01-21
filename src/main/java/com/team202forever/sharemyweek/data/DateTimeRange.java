package com.team202forever.sharemyweek.data;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class DateTimeRange {

    @Id
    private Integer id;

    private Date start;

    private Date end;

}
