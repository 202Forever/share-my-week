package com.team202forever.sharemyweek.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends ViewModel {

    private String email;

    private float maxBudget;

    private List<DateTimeRange> dateTimeRange;

}
