package com.eco.environet.education.services.impl;

import com.eco.environet.education.dto.EducatorQuestionDto;
import com.eco.environet.education.dto.QuestionStatistics;
import com.eco.environet.education.model.Answer;
import com.eco.environet.education.model.AnsweredQuestion;
import com.eco.environet.education.model.Question;
import com.eco.environet.education.model.QuestionType;
import com.eco.environet.education.repository.AnsweredQuestionRepository;
import com.eco.environet.education.repository.QuestionRepository;
import com.eco.environet.education.services.TestStatisticsService;
import com.eco.environet.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestStatisticsServiceImpl implements TestStatisticsService {

    private final QuestionRepository questionRepository;
    private final AnsweredQuestionRepository answeredQuestionRepository;

    @Override
    public List<QuestionStatistics> findTop3ByLectureIdAndCriteria(Long lectureId, String criteria) {
        var stats = new ArrayList<QuestionStatistics>();
        var questions = questionRepository.findAllByLecture_Id(lectureId);
        for (Question question : questions) {
            var answeredQuestions = answeredQuestionRepository.findAllByQuestion_Id(question.getId());

            var correctNum = 0;
            for (AnsweredQuestion answeredQuestion : answeredQuestions) {
                if (question.getType() == QuestionType.FILL_IN) {
                    if (answeredQuestion.getTextAnswer().equalsIgnoreCase(question.getAnswers().stream().findFirst().orElseThrow().getContent())) {
                        correctNum++;
                    }
                } else {
                    if (answeredQuestion.getAnswers().stream().allMatch(Answer::getIsCorrect)) {
                        correctNum++;
                    }
                }
            }

            stats.add(QuestionStatistics.builder()
                    .question(Mapper.map(question, EducatorQuestionDto.class))
                    .timesAnswered(answeredQuestions.size())
                    .timesCorrectlyAnswered(correctNum)
                    .build());
        }

        switch (criteria) {
            case "easiest" -> {
                return stats.stream()
                        .sorted(
                                Comparator.comparingDouble(
                                                (QuestionStatistics stat) ->
                                                        (double) stat.getTimesCorrectlyAnswered() / stat.getTimesAnswered()
                                        )
                                        .reversed()
                        )
                        .limit(3)
                        .collect(Collectors.toList());
            }
            case "hardest" -> {
                return stats.stream()
                        .sorted(
                                Comparator.comparingDouble(
                                        (QuestionStatistics stat) ->
                                                (double) stat.getTimesCorrectlyAnswered() / stat.getTimesAnswered()
                                )
                        )
                        .limit(3)
                        .collect(Collectors.toList());
            }
            case "most confusing" -> {
                return stats.stream()
                        .sorted(
                                Comparator.comparingInt(QuestionStatistics::getTimesAnswered)
                        )
                        .limit(3)
                        .collect(Collectors.toList());
            }
            default -> {
                return new ArrayList<>();
            }
        }

    }
}
