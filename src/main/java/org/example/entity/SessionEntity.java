package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.enums.SessionStatus;
import java.sql.Timestamp;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionEntity {
    private Long id;
    private Long ownerId;
    private String inviteToken;
    private SessionStatus status;
    private Timestamp startTime;
    private Integer durationSeconds;
    private Timestamp createdAt;
    private long taskId;
}