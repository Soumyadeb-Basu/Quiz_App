package com.soumya.quizapp.services;

import com.soumya.quizapp.exception.ResourceNotFoundException;
import com.soumya.quizapp.repositories.QuestionRepository;
import com.soumya.quizapp.models.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository= questionRepository;
    }

    public ResponseEntity<List<Question>> getAllQuestions()throws ResourceNotFoundException {
        if(questionRepository.findAll().isEmpty()) {
            log.error("No Questions found to be displayed....");
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "No Questions Found to be displayed");
        }
        log.info("ALl Questions returned...");
        return ResponseEntity.ok(questionRepository.findAll());
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionRepository.save(question);
        log.info("Question added successfully....");
        return new ResponseEntity<>("Successfully Added Question!", HttpStatus.CREATED);
    }

    public ResponseEntity<List<Question>> getByCategory(String category) {
        if(questionRepository.getByCategory(category).isEmpty()) {
            log.error("No Questions found for category: "+ category);
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"No Questions are available for the given category: "+ category);
        }
        log.info("Questions for given category returned....");
        return ResponseEntity.ok(questionRepository.getByCategory(category));
    }
}
