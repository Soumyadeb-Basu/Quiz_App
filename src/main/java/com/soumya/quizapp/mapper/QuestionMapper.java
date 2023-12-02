package com.soumya.quizapp.mapper;

import com.soumya.quizapp.models.Question;
import com.soumya.quizapp.models.QuestionForUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionMapper {

    QuestionMapper INSTANCE= Mappers.getMapper(QuestionMapper.class);

    QuestionForUser questionForUser(Question question);

}
