package com.soumya.quizapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soumya.quizapp.models.Question;
import com.soumya.quizapp.models.QuestionForUser;
import com.soumya.quizapp.models.Quiz;
import com.soumya.quizapp.models.UserResponse;
import com.soumya.quizapp.services.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = QuizController.class)
@DisplayName("Testing Quiz Controller")
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuizService service;

    private QuestionForUser question;

    private Quiz quiz;

    @BeforeEach
    void setUp() {
        List<Question> questions= new ArrayList<>();
        questions.add(new Question(1,"test","op1","op2","op3","op4","op1","test","test"));
        quiz= new Quiz(1,"Quiz_test",questions);
    }

    @Test
    @DisplayName("Testing Create Quiz endpoint")
    void testCreateQuiz() throws Exception {

        ResponseEntity<String> response= new ResponseEntity<>("Created", HttpStatus.CREATED);
        when(service.createQuiz(Mockito.anyString(),Mockito.anyInt(),Mockito.anyString())).thenReturn(response);

        this.mockMvc.perform(post("/quiz/create/test/1/test")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(objectMapper.writeValueAsString(quiz)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Testing Get Quiz for User endpoint")
    void getQuizForUser() throws Exception {

        QuestionForUser questionForUser= new QuestionForUser();
        List<QuestionForUser> questions = List.of(questionForUser);
        ResponseEntity<List<QuestionForUser>> response= new ResponseEntity<>(questions,HttpStatus.OK);
        when(service.getQuestionsForUser(any())).thenReturn(response);

        this.mockMvc.perform(get("/quiz/get/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Testing Get User Response Endpoint")
    void testGetUserResponse() throws Exception {

        UserResponse response= new UserResponse(1,"");
        List<UserResponse> responses= List.of(response);
        ResponseEntity<String> responseEntity= new ResponseEntity<>("test",HttpStatus.OK);
        when(service.calculateResult(anyInt(),anyList())).thenReturn(responseEntity);

        this.mockMvc.perform(post("/quiz/submit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responses)))
                .andDo(print())
                .andExpect(status().isOk());

    }
}


