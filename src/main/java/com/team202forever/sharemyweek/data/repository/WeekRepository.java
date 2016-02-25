package com.team202forever.sharemyweek.data.repository;


import com.team202forever.sharemyweek.data.models.HashId;
import com.team202forever.sharemyweek.data.models.Week;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@SuppressWarnings("unused")
public interface WeekRepository extends MongoRepository<Week, HashId> {

    List<Week> findAllByOrderByCreationDateAsc();

    List<Week> findAllByOrderByCreationDateDesc();
}
