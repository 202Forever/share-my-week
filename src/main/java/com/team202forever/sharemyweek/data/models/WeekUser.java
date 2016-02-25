package com.team202forever.sharemyweek.data.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
public class WeekUser {

    private String name;
    
    private String color;
    
    @NotNull
    @Valid
    private User userInfo;
}
