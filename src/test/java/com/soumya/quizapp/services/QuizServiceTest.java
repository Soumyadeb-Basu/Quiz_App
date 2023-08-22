package com.soumya.quizapp.services;

import com.soumya.quizapp.Repositories.QuestionRepository;
import com.soumya.quizapp.Repositories.QuizRepository;
import com.soumya.quizapp.models.Question;
import com.soumya.quizapp.models.Quiz;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuizService quizService;

    private List<Question> quizQuestions;

    private Quiz quiz;


    @BeforeEach
    public void setUp() {

        Question question = new Question(1,"Who is the creator of Java?","James Gosling",
                "Linus Torvalds","G V Rossum","None","James Gosling",
                "Programming","easy");

        quizQuestions = new ArrayList<>();

        quizQuestions.add(question);

    }

    @Test
    @DisplayName("Testing Create Quiz")
    void testCreateQuiz() {

        Mockito.when(questionRepository.findRandomQuestionsByCategory("Programming", 1)).thenReturn(quizQuestions);

        quiz= new Quiz();
        quiz.setTitle("quiz1");
        quiz.setQuestions(quizQuestions);

        ResponseEntity<String> response= new ResponseEntity<>("Created Quiz successfully!", HttpStatus.CREATED);

        Assertions.assertEquals(quizService.createQuiz("Programming", 1,"quiz1"),response);

        Mockito.verify(quizRepository).save(quiz);

    }
}
