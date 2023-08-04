package com.computing.devopsassessmet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.computing.devopsassessmet.model.Tutorial;

@Repository
public interface TutorialRepository extends MongoRepository<Tutorial, String> {
	
	Optional<List<Tutorial>> findByAreaIn(List<String> gapAreas);  

}
