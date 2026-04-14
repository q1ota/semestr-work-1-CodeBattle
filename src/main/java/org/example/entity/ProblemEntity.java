package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.enums.ProblemDifficulty;

import java.sql.Timestamp;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProblemEntity {
    private long id;
    private String title;
    private String description;
    private String sampleInput;
    private String sampleOutput;
    private List<Test> examples;
    private List<Test> tests;
    private ProblemDifficulty difficulty;
    private Timestamp createdAt;
}
