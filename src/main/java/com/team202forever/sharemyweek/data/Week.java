package com.team202forever.sharemyweek.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Week extends ViewModel {

    private List<User> users;

}
