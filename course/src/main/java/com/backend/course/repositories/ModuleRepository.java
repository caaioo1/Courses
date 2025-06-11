package com.backend.course.repositories;

import com.backend.course.models.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ModuleRepository extends JpaRepository<ModuleModel, Integer> {

    @Query(value = "SELECT * FROM tb_modules m WHERE course.course_id = :courseId", nativeQuery = true)
    List<ModuleModel> findAllModulesIntoCourse(@Param("courseId") Integer courseId);

    @Query(value = "SELECT * FROM tb_modules m WHERE course.course_id = :courseId and module_id = :moduleId", nativeQuery = true)
    Optional<ModuleModel> findModulesIntoCourse(Integer courseId, Integer moduleId);
}