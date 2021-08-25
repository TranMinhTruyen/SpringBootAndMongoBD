package com.core.repository.mongo;

import com.core.model.ConfirmKey;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfirmKeyRepository extends MongoRepository<ConfirmKey, String> {
    ConfirmKey findByEmailEquals(String email);
    void deleteByEmail(String email);
}
