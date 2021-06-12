package com.example.repository.mongo;

import com.example.common.model.Role;
import com.example.common.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    List<User>findUserByRoleContains(List<Role> role);
}
