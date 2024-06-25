package com.eco.environet.education.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "answered_question", schema = "education")
public class AnsweredQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToMany
    @JoinTable(
            name = "submitted_answers",
            schema = "education",
            joinColumns = { @JoinColumn(name = "submission_id")},
            inverseJoinColumns = { @JoinColumn(name = "answer_id")}
    )
    private Set<Answer> answers;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            @JoinColumn(name = "lecture_id", referencedColumnName = "lecture_id")
    })
    private TestExecution testExecution;

    @Column(name = "text_answer")
    private String textAnswer;
}
