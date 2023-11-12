package com.soumya.quizapp.services;


import com.soumya.quizapp.exception.ResourceNotFoundException;
import com.soumya.quizapp.models.Question;
import com.soumya.quizapp.models.QuestionForUser;
import com.soumya.quizapp.models.Quiz;
import com.soumya.quizapp.models.UserResponse;
import com.soumya.quizapp.repositories.QuestionRepository;
import com.soumya.quizapp.repositories.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<String> createQuiz(String category, int numberOfQuestions, String title) {

        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category,numberOfQuestions);

        if(questions.isEmpty()) {
            log.error("No questions found in given category");
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"No Questions are found in given category for creating quiz");
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizRepository.save(quiz);
        log.info("New Quiz created....");

        return new ResponseEntity<>("Created Quiz!", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionForUser>> getQuestionsForUser(Integer id)throws ResourceNotFoundException {

        Quiz quiz;
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if(optionalQuiz.isPresent())
            quiz= optionalQuiz.get();
        else {
            log.error("Quiz not found....");
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Quiz with the given Id not found");
        }
        if(quiz.getQuestions().isEmpty()) {
            log.error("Quiz has no questions...");
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Quiz is empty without any questions");
        }
        List<Question> questions = quiz.getQuestions();
        List<QuestionForUser> questionForUsers = new ArrayList<>();

        for(Question q: questions) {
            QuestionForUser question = new QuestionForUser(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionForUsers.add(question);
        }
        log.info("User Question returned....");
        return new ResponseEntity<>(questionForUsers,HttpStatus.OK);

    }

    public ResponseEntity<String> calculateResult(Integer id, List<UserResponse> response)throws ResourceNotFoundException {
        Quiz quiz;
        Optional<Quiz> optionalQuiz= quizRepository.findById(id);
        if(optionalQuiz.isPresent())
            quiz = optionalQuiz.get();
        else
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Response is invalid as Quiz with given ID not found");
        List<Question> questions = quiz.getQuestions();
        int questionNumber = questions.size();
        int correctAnswers=0;
        int count=0;
        for(UserResponse r: response) {
            if(r.getResponse()==null||r.getResponse().isEmpty()) {
                log.error("Empty Response Body....");
                throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Response is empty");
            }
            if(r.getResponse().equals(questions.get(count++).getRightAnswer()))
                correctAnswers++;
        }
        String finalResponse = String.format("Correct Answers : %s , out of %s questions." ,correctAnswers,questionNumber);
        log.info("Final Response returned to User....");
        return new ResponseEntity<>(finalResponse, HttpStatus.OK);
    }
}
