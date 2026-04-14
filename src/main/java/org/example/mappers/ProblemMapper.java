package org.example.mappers;

import org.example.entity.ProblemEntity;
import org.example.entity.Test;
import org.example.entity.enums.ProblemDifficulty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ProblemMapper {

    private ProblemMapper() {}

    public static ProblemEntity mapRow(ResultSet resultSet) throws SQLException {

        String sampleInput = resultSet.getString("sample_input");
        if (resultSet.wasNull())
            sampleInput = null;

        String sampleOutput = resultSet.getString("sample_output");
        if (resultSet.wasNull())
            sampleOutput = null;

        String examplesJson = resultSet.getString("examples");
        List<Test> example = new ArrayList<>();
        if (examplesJson != null)
            example = JsonMapper.fromJsonToListOfTests(examplesJson);

        String testsJson = resultSet.getString("tests");
        List<Test> testJson = JsonMapper.fromJsonToListOfTests(testsJson);

        return ProblemEntity.builder()
                .id(resultSet.getLong("id"))
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .sampleInput(sampleInput)
                .sampleOutput(sampleOutput)
                .examples(example)
                .tests(testJson)
                .difficulty(ProblemDifficulty.valueOf(resultSet.getString("difficulty")))
                .createdAt(resultSet.getTimestamp("created_at"))
                .build();

    }
}