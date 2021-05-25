package com.shu.homework.respository;

import com.shu.homework.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findByCourseId(String courseId);

    Course findByCourseName(String courseName);

    @Query(value = "select count(1) from ems.course where courseId =?1", nativeQuery = true)
    int checkCourseId(String courseId);

    @Query(value = "select * from ems.course", nativeQuery = true)
    Page<Course> findAllByPage(Pageable pageable);

    @Query(value = "select * from ems.course where courseName regexp ?1", nativeQuery = true)
    Page<Course> searchByCourseName(String keyWord, Pageable pageable);

    @Query(value = "select * from ems.course where ems.course.courseId not in (select courseId from ems.scourse where ems.scourse.studentId=?1)", nativeQuery = true)
    Page<Course> findUnselectCourseById(Long studentId, Pageable pageable);

    @Query(value = "select * from ems.course where ems.course.courseId in (select courseId from ems.scourse where ems.scourse.studentId=?1) and courseId regexp ?2", nativeQuery = true)
    Page<Course> searchSelectByCourseId(Long userId, String keyword, Pageable pageable);

    @Query(value = "select * from ems.course where ems.course.courseId not in (select courseId from ems.scourse where ems.scourse.studentId=?1) and courseId regexp ?2", nativeQuery = true)
    Page<Course> searchUnselectByCourseId(Long userId, String keyword, Pageable pageable);

    @Query(value = "select * from ems.course where ems.course.courseId in (select courseId from ems.scourse where ems.scourse.studentId=?1) and courseName regexp ?2", nativeQuery = true)
    Page<Course> searchSelectByCourseName(Long userId, String keyword, Pageable pageable);

    @Query(value = "select * from ems.course where ems.course.courseId not in (select courseId from ems.scourse where ems.scourse.studentId=?1) and courseName regexp ?2", nativeQuery = true)
    Page<Course> searchUnSelectByCourseName(Long userId, String keyword, Pageable pageable);

    @Query(value = "select * from ems.course where t_id = ?1", nativeQuery = true)
    Page<Course> findAllByTId(Long userId, Pageable pageable);

    @Query(value = "select courseId from ems.course", nativeQuery = true)
    List<String> findAllCourseId();

}
