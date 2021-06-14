package com.example.common.model;

import com.example.repository.mongo.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Tran Minh Truyen
 */

public class AutoIncrement {

	private MongoRepository repository;

	public AutoIncrement(MongoRepository repository) {
		this.repository = repository;
	}

	public List getLastOfCollection(){
		List last = this.repository.findAll();
		if (!last.isEmpty()) return last.stream().skip(this.repository.count()-1).toList();
		else return null;
	}
}
