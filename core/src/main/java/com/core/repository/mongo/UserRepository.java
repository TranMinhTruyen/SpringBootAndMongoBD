package com.core.repository.mongo;

import com.core.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * @author Tran Minh Truyen
 */
public interface UserRepository extends MongoRepository<User, Integer>{
    List findUserByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    User findUsersByAccountEqualsAndPasswordContains(String account, String password);
    User findUserByAccount(String account);
}
