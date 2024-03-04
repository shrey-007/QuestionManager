package com.smart.model;

import com.smart.entities.QuestionExplaination;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionExplainationRepository extends MongoRepository<QuestionExplaination,Integer> {
    public QuestionExplaination findByQid(int qid);
}
