package com.team202forever.sharemyweek.data.repository;


import com.team202forever.sharemyweek.data.models.HashId;
import com.team202forever.sharemyweek.data.models.Week;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@SuppressWarnings("unused")
public interface WeekRepository extends MongoRepository<Week, HashId> {

    @Override
    @RestResource(exported = false)
    List<Week> findAll();

    @Override
    @RestResource(exported = false)
    List<Week> findAll(Sort sort);

    @Override
    @RestResource(exported = false)
    Page<Week> findAll(Pageable sort);
}
