package com.example.common.model;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public class AutoIncrement {
	public static List getLastOfCollection(MongoRepository repository){
		List last = repository.findAll().stream().skip(repository.count()-1).toList();
		return last;
	}
}
