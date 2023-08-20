package com.soumya.quizapp.controllers;

import com.soumya.quizapp.models.QuestionForUser;
import com.soumya.quizapp.models.UserResponse;
import com.soumya.quizapp.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create/{category}/{numberOfQuestions}/{title}")
    public ResponseEntity<String> createQuiz(@PathVariable String category, @PathVariable int numberOfQuestions, @PathVariable String title) {
         return quizService.createQuiz(category,numberOfQuestions,title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionForUser>> getQuizQuestionsForUser(@PathVariable Integer id) {
        return  quizService.getQuestionsForUser(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<String> submitQuiz(@PathVariable Integer id, @RequestBody List<UserResponse> response) {
        return quizService.calculateResult(id,response);
    }
}
