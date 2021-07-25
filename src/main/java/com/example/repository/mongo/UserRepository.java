package com.example.repository.mongo;

import com.example.common.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author Tran Minh Truyen
 */


@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    List findUserByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    User findUsersByAccountEqualsAndPasswordContains(String account, String password);
    User findUserByAccount(String account);
}
