package com.team202forever.sharemyweek.data.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
public class UserColor {

    @NotNull
    @DBRef
    private Week week;

    @NotEmpty
    private String color;
}
