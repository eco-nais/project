package com.eco.environet.education.services;

import com.eco.environet.education.dto.QuestionStatistics;

import java.util.List;

public interface TestStatisticsService {
    List<QuestionStatistics> findTop3ByLectureIdAndCriteria(Long lectureId, String criteria);
}
