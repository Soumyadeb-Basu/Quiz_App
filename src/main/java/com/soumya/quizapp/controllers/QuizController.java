package com.soumya.quizapp.controllers;

import com.soumya.quizapp.models.Question;
import com.soumya.quizapp.models.QuestionForUser;
import com.soumya.quizapp.models.Quiz;
import com.soumya.quizapp.models.UserResponse;
import com.soumya.quizapp.services.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
@Tag(name = "Quiz Controller", description = "Controller to manage Quiz service related operations")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService= quizService;
    }

    @PostMapping("create/{category}/{numberOfQuestions}/{title}")
    @Operation(summary = "Create Quiz", description = "Creates a random quiz with user entering number of questions,quiz category and quiz title")
    @ApiResponse(responseCode = "201", description = "Created", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    public ResponseEntity<String> createQuiz(@PathVariable String category, @PathVariable int numberOfQuestions,@Valid @PathVariable String title) {
         return quizService.createQuiz(category,numberOfQuestions,title);
    }

    @GetMapping("get/{id}")
    @Operation(summary = "Get Quiz", description = "Gets the quiz for user based on quiz id")
    @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = QuestionForUser.class))})
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    public ResponseEntity<List<QuestionForUser>> getQuizQuestionsForUser(@PathVariable Integer id) {
        return  quizService.getQuestionsForUser(id);
    }

    @PostMapping("submit/{id}")
    @Operation(summary = "Get Quiz Response", description = "Gets the response in form of total number of correct questions attempted by user")
    @ApiResponse(responseCode = "200", description = "OK",content = @Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content = @Content)
    public ResponseEntity<String> submitQuiz(@PathVariable Integer id,@Valid @RequestBody List<@Valid UserResponse> response) {
        return quizService.calculateResult(id,response);
    }
}
