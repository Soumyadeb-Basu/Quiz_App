package com.soumya.quizapp.services;

import com.soumya.quizapp.repositories.QuestionRepository;
import com.soumya.quizapp.repositories.QuizRepository;
import com.soumya.quizapp.models.Question;
import com.soumya.quizapp.models.QuestionForUser;
import com.soumya.quizapp.models.Quiz;
import com.soumya.quizapp.models.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test for QuizService")
class QuizServiceTest {

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

        ResponseEntity<String> response= new ResponseEntity<>("Created Quiz!", HttpStatus.CREATED);

        Assertions.assertEquals(response,quizService.createQuiz("Programming", 1,"quiz1"));

        Mockito.verify(quizRepository).save(quiz);

    }

    @Test
    @DisplayName("Testing Get Questions for User")
    void testGetQuestionsForUser() {

        quiz=new Quiz(1,"quiz2",quizQuestions);
        Mockito.when(quizRepository.findById(1)).thenReturn(Optional.ofNullable(quiz));

        List<QuestionForUser> userQuestions= quizService.getQuestionsForUser(1).getBody();
        Assertions.assertNotNull(userQuestions);
        Assertions.assertEquals(1,userQuestions.size());

    }

    @Test
    @DisplayName("Testing Calculate Result method")
    void testCalculateResult() {

        quiz= new Quiz(1,"quiz3",quizQuestions);
        Mockito.when(quizRepository.findById(1)).thenReturn(Optional.ofNullable(quiz));

        UserResponse response = new UserResponse(1,"James Gosling");
        List<UserResponse> responses = new ArrayList<>();
        responses.add(response);

        ResponseEntity<String> responseForUser= quizService.calculateResult(1,responses);
        Assertions.assertTrue(Objects.requireNonNull(responseForUser.getBody()).contains("Correct Answers : 1"));

    }
}
