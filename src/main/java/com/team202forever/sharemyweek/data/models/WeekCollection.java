package com.team202forever.sharemyweek.data.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeekCollection {

    List<Week> weeks = new ArrayList<>();
}
