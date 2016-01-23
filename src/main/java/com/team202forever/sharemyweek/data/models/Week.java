package com.team202forever.sharemyweek.data.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

@Data
public class Week extends ViewModel {

    @NotEmpty
    @Valid
    private List<User> users;

}
