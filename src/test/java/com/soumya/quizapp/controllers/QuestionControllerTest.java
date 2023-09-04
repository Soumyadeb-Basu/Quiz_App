package com.soumya.quizapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soumya.quizapp.models.Question;
import com.soumya.quizapp.services.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuestionController.class)
@DisplayName("Testing Question Controller")
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuestionService service;

    private List<Question> questionList;

    private Question question;

    @BeforeEach
    void setUp() {
        question= new Question(1,"test","opt1","opt2","opt3","opt4",
                "right","programming","easy");
        questionList= new ArrayList<>();
        questionList.add(question);
    }


    @Test
    @DisplayName("Testing Get All Questions endpoint")
    void testGetAllQuestions() throws Exception {

        ResponseEntity<List<Question>> response= new ResponseEntity<>(questionList, HttpStatus.OK);
        when(service.getAllQuestions()).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/questions/getAllQuestions")).
                andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].questionTitle", is("test")))
                .andExpect(jsonPath("$.[0].category", is("programming")));

    }

    @Test
    @DisplayName("Testing Get Questions By category endpoint")
    void testGetQuestionByCategory() throws Exception {

        ResponseEntity<List<Question>> response= new ResponseEntity<>(questionList, HttpStatus.OK);
        when(service.getByCategory("programming")).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/questions/getByCategory/programming")).
                andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].questionTitle", is("test")))
                .andExpect(jsonPath("$.[0].category", is("programming")));

    }

    @Test
    @DisplayName("Testing Add Question endpoint")
    void testAddQuestions() throws Exception {

        ResponseEntity<String> response= new ResponseEntity<>("Successfully Added Question!", HttpStatus.OK);
        when(service.addQuestion(question)).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/questions/addQuestion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(question))).
                andDo(print())
                .andExpect(status().isOk());

    }


}
