package com.team202forever.sharemyweek.data.repository;


import com.team202forever.sharemyweek.data.models.ViewModel;
import com.team202forever.sharemyweek.data.models.Week;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeekRepository extends MongoRepository<Week, ViewModel.HashId> {
}
