package com.computing.devopsassessmet.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.computing.devopsassessmet.model.QuestionnaireResponse;

@Repository
public interface ResponseRepository extends MongoRepository<QuestionnaireResponse, String> {

	List<QuestionnaireResponse> findByEmail(String email); 

}
