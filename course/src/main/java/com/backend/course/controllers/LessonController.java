package com.backend.course.controllers;

import com.backend.course.dtos.LessonRecordDto;
import com.backend.course.models.LessonModel;
import com.backend.course.models.ModuleModel;
import com.backend.course.services.LessonService;
import com.backend.course.services.ModuleServiceAPI;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/lessons")
public class LessonController {

    final LessonService lessonService;
    final ModuleServiceAPI moduleService;

    public LessonController(LessonService lessonService, ModuleServiceAPI moduleService) {
        this.lessonService = lessonService;
        this.moduleService = moduleService;
    }

    @PostMapping
    public ResponseEntity<Object> saveLesson(@RequestBody @Valid LessonRecordDto lessonDto) {
        Optional<ModuleModel> moduleOpt = moduleService.findById(Math.toIntExact(lessonDto.moduleId()));
        if (moduleOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found.");
        }

        LessonModel lesson = new LessonModel();
        lesson.setTitle(lessonDto.title());
        lesson.setDescription(lessonDto.description());
        lesson.setVideoUrl(lessonDto.videoUrl());
        lesson.setCreationDate(LocalDateTime.now());
        lesson.setModule(moduleOpt.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lesson));
    }

    @GetMapping
    public ResponseEntity<List<LessonModel>> getAllLessons() {
        return ResponseEntity.ok(lessonService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneLesson(@PathVariable Long id) {
        Optional<LessonModel> lessonOpt = lessonService.findById(id);
        return lessonOpt.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLesson(@PathVariable Long id) {
        Optional<LessonModel> lessonOpt = lessonService.findById(id);
        if (lessonOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found.");
        }

        lessonService.delete(lessonOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted successfully.");
    }
}