package com.team202forever.sharemyweek.data.repository;

import com.team202forever.sharemyweek.data.models.Event;
import com.team202forever.sharemyweek.data.models.HashId;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, HashId> {

    @Override
    @RestResource(exported = false)
    List<Event> findAll();

    @RestResource(exported = false)
    List<Event> findByWeekIdOrderByDateTimeRangeStartAscPriorityDesc(ObjectId weekId);

    @Override
    @RestResource(exported = false)
    List<Event> findAll(Sort sort);

    @Override
    @RestResource(exported = false)
    Page<Event> findAll(Pageable pageable);
}
