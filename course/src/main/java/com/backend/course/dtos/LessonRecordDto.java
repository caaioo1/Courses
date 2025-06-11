package com.backend.course.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LessonRecordDto(
        @NotBlank String title,
        String description,
        String videoUrl,
        @NotNull Long moduleId
) {}
