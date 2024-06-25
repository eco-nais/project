package com.eco.environet.education.repository;

import com.eco.environet.education.model.AnsweredQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnsweredQuestionRepository extends JpaRepository<AnsweredQuestion, Long> {
    List<AnsweredQuestion> findAllByQuestion_Id(Long questionId);
}
