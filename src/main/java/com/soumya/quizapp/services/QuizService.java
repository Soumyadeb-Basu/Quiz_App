package com.soumya.quizapp.services;


import com.soumya.quizapp.Repositories.QuestionRepository;
import com.soumya.quizapp.Repositories.QuizRepository;
import com.soumya.quizapp.models.Question;
import com.soumya.quizapp.models.QuestionForUser;
import com.soumya.quizapp.models.Quiz;
import com.soumya.quizapp.models.UserResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<String> createQuiz(String category, int numberOfQuestions, String title) {

        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category,numberOfQuestions);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizRepository.save(quiz);

        return new ResponseEntity<>("Created Quiz successfully!", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionForUser>> getQuestionsForUser(Integer id) {

        Quiz quiz = new Quiz();
        if(quizRepository.findById(id).isPresent())
            quiz= quizRepository.findById(id).get();
        else
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        List<Question> questions = quiz.getQuestions();
        List<QuestionForUser> questionForUsers = new ArrayList<>();

        for(Question q: questions) {
            QuestionForUser question = new QuestionForUser(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionForUsers.add(question);
        }

        return new ResponseEntity<>(questionForUsers,HttpStatus.OK);

    }

    public ResponseEntity<String> calculateResult(Integer id, List<UserResponse> response) {
        Quiz quiz= new Quiz();
        if(quizRepository.findById(id).isPresent())
            quiz = quizRepository.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int questionNumber = questions.size();
        int correctAnswers=0;
        int count=0;
        for(UserResponse r: response) {
            if(r.getResponse().equals(questions.get(count++).getRightAnswer()))
                correctAnswers++;
        }
        String finalResponse = String.format("Correct Answers : %s , out of %s questions." ,correctAnswers,questionNumber);
        return new ResponseEntity<>(finalResponse, HttpStatus.OK);
    }
}
