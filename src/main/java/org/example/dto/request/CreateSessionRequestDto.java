package org.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public class CreateSessionRequestDto {
        private String title;
        private long taskId;
        private int duration;
        private boolean allowSolo;
    }
