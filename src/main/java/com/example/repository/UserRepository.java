package com.example.repository;

import com.example.common.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    List<User>findUserByFirstNameContainingOrLastNameContainingOrAddress_AndressOrAddress_DistricOrAddress_CityOrCitizenId
            (String firstName,
             String lastName,
             String address_andress,
             String address_distric,
             String address_city,
             String citizenId);
}
