package com.soumya.quizapp.services;

import com.soumya.quizapp.exception.ResourceNotFoundException;
import com.soumya.quizapp.repositories.QuestionRepository;
import com.soumya.quizapp.models.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("QuestionServiceTest")
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    private Question question;

    private List<Question> questions;

    private String category;

    @BeforeEach
    public void setUp() {

        question = new Question(1,"Who is the creator of Java?","James Gosling",
                "Linus Torvalds","G V Rossum","None","James Gosling",
                "Programming","easy");

        questions =new ArrayList<>();

        category= "Programming";
    }

    @Test
    @DisplayName("Testing Get All Questions- Success scenario")
    void testGetAllQuestionsSuccessScenario() {

        questions.add(question);
        when(questionRepository.findAll()).thenReturn(questions);

        ResponseEntity<List<Question>> response = new ResponseEntity<>(questions, HttpStatus.OK);

        Assertions.assertEquals(response, questionService.getAllQuestions());

    }

    @Test
    @DisplayName("Testing Add Questions")
    void testAddQuestions() {

        ResponseEntity<String> response = new ResponseEntity<>("Successfully Added Question!", HttpStatus.CREATED);
        Assertions.assertEquals(response,questionService.addQuestion(question));
        verify(questionRepository).save(question);

    }

    @Test
    @DisplayName("Testing Get Question By category")
    void testGetQuestionByCategory() {

        questions.add(question);
        when(questionRepository.getByCategory(category)).thenReturn(questions);
        ResponseEntity<List<Question>> responses = new ResponseEntity<>(questions,HttpStatus.OK);
        Assertions.assertEquals(responses, questionService.getByCategory(category));

    }

    @Test
    @DisplayName("Testing Get Question By category: Exception Scenario")
    void testGetQuestionByCategoryException() {

        when(questionRepository.getByCategory(category)).thenReturn(List.of());
        Assertions.assertThrows(ResourceNotFoundException.class,()->questionService.getByCategory(category));

    }

    @Test
    @DisplayName("Testing Get All Questions- Exception scenario")
    void testGetAllQuestionsExceptionScenario() {

        when(questionRepository.findAll()).thenReturn(List.of());
        Assertions.assertThrows(ResourceNotFoundException.class,()->questionService.getAllQuestions());

    }
}
