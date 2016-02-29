package com.team202forever.sharemyweek.data.repository;


import com.team202forever.sharemyweek.data.models.HashId;
import com.team202forever.sharemyweek.data.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends MongoRepository<User, HashId> {

    @RestResource(exported = false)
    User findByEmail(String email);

    @Override
    @RestResource(exported = false)
    void delete(HashId id);

    @Override
    @RestResource(exported = false)
    void delete(User user);

    @Override
    @RestResource(exported = false)
    List<User> findAll();

    @Override
    @RestResource(exported = false)
    List<User> findAll(Sort sort);

    @Override
    @RestResource(exported = false)
    Page<User> findAll(Pageable pageable);

    @Override
    @RestResource(exported = false)
    User insert(User user);

    @Override
    @RestResource(exported = false)
    User save(User user);
}
