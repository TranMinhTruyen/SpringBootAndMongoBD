package com.example.repository;

import com.example.common.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    List<User>findUserByFirstNameEqualsOrLastNameEqualsOrAndress_AndressOrAndress_DistricOrAndress_CityOrId(String keyword);
}
