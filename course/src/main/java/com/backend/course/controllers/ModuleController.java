package com.backend.course.controllers;

import com.backend.course.dtos.ModuleRecordDto;
import com.backend.course.models.ModuleModel;
import com.backend.course.services.CourseServiceAPI;
import com.backend.course.services.ModuleServiceAPI;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ModuleController {

    final ModuleServiceAPI moduleService;
    final CourseServiceAPI courseService;

    public ModuleController(ModuleServiceAPI moduleService, CourseServiceAPI courseService) {
        this.moduleService = moduleService;
        this.courseService = courseService;
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> saveModule(
            @PathVariable(value = "courseId") Integer courseId,
            @RequestBody @Valid ModuleRecordDto moduleRecordDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                moduleService.save(moduleRecordDto, courseService.findById(courseId).get())
        );
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<List<ModuleModel>> getAllModules(
            @PathVariable(value = "courseId") Integer courseId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleService.findAllModulesIntoCourse(courseId));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> getModule(
            @PathVariable(value = "courseId") Integer courseId,
            @PathVariable(value = "moduleId") Integer moduleId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleService.findModulesIntoCourse(courseId, moduleId));
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> updateModule(
            @PathVariable(value = "courseId") Integer courseId,
            @PathVariable(value = "moduleId") Integer moduleId,
            @RequestBody @Valid ModuleRecordDto moduleRecordDto
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleService.update(moduleRecordDto,
                        moduleService.findModulesIntoCourse(courseId, moduleId).get()));
    }
}
