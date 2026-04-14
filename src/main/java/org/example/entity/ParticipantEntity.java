package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.enums.ParticipantRole;
import org.example.entity.enums.ParticipantStatus;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParticipantEntity {
    private Long sessionId;
    private Long userId;
    private ParticipantRole role;
    private ParticipantStatus status;
    private Timestamp joinedAt;
}
