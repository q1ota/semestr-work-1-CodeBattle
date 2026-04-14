package org.example.dto.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.entity.enums.ParticipantRole;
import org.example.entity.enums.ParticipantStatus;

@AllArgsConstructor
@Data
public class ParticipantViewDto {
    private Long userId;
    private String username;
    private ParticipantRole role;
    private ParticipantStatus status;
}