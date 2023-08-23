package com.soumya.quizapp.controllers;

import com.soumya.quizapp.models.Question;
import com.soumya.quizapp.services.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
@Tag(name = "Question Controller", description = "Controller to manage Question service related operations")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("getAllQuestions")
    @Operation(summary = "Gets all Questions", description = "Gets all the questions for User")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Not Found")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("getByCategory/{category}")
    @Operation(summary = "Gets questions by category", description = "Gets the questions for User on basis of category passed")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Not Found")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getByCategory(category);
    }

    @PostMapping("addQuestion")
    @Operation(summary = "Add Questions", description = "Adds a new question for User")
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

}
