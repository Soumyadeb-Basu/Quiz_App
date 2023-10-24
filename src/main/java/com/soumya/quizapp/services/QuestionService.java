package com.soumya.quizapp.services;

import com.soumya.quizapp.repositories.QuestionRepository;
import com.soumya.quizapp.models.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> allQuestions = new ArrayList<>();
        try {
            allQuestions = questionRepository.findAll();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return ResponseEntity.ok(allQuestions);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionRepository.save(question);
        } catch (Exception exc) {
            log.info(exc.getMessage());
            return new ResponseEntity<>("Error occurred", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully Added Question!", HttpStatus.CREATED);
    }

    public ResponseEntity<List<Question>> getByCategory(String category) {
        return ResponseEntity.ok(questionRepository.getByCategory(category));
    }
}
