package com.eco.environet.education.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionStatistics {
    private EducatorQuestionDto question;
    private Integer timesAnswered;
    private Integer timesCorrectlyAnswered;
}
