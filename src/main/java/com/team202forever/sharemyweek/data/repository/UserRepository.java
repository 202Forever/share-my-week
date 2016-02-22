package com.team202forever.sharemyweek.data.repository;


import com.team202forever.sharemyweek.data.models.User;
import com.team202forever.sharemyweek.data.models.ViewModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ViewModel.HashId> {

    public User findByEmail(String email);
}
