package com.shu.homework.respository;

import com.shu.homework.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findByCourseId(String courseId);

    @Query(value = "select count(1) from ems.course where courseId =?1", nativeQuery = true)
    int checkCourseId(String courseId);
}
