package com.backend.course.repositories;

import com.backend.course.models.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<LessonModel, Long> {
}
