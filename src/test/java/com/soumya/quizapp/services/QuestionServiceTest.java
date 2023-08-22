package com.soumya.quizapp.services;

import com.soumya.quizapp.Repositories.QuestionRepository;
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
public class QuestionServiceTest {

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

        Assertions.assertEquals(questionService.getAllQuestions(),response);

    }

    @Test
    @DisplayName("Testing Add Questions")
    void testAddQuestions() {

        ResponseEntity<String> response = new ResponseEntity<>("Successfully Added Question!", HttpStatus.CREATED);
        Assertions.assertEquals(questionService.addQuestion(question),response);
        verify(questionRepository).save(question);

    }

    @Test
    @DisplayName("Testing Get Question By category")
    void testGetQuestionByCategory() {

        ResponseEntity<List<Question>> responses = new ResponseEntity<>(questions,HttpStatus.OK);
        Assertions.assertEquals(questionService.getByCategory(category), responses);

    }
}
